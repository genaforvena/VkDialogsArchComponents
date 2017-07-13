package org.imozerov.vkgroupdialogs.repository.fetchers

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import org.imozerov.vkgroupdialogs.Executors
import org.imozerov.vkgroupdialogs.api.ApiResponse
import org.imozerov.vkgroupdialogs.repository.Resource

/**
 * Kotlin version for class from https://github.com/googlesamples/android-architecture-components
 */
abstract class NetworkBoundResource<ResultType> @MainThread
    constructor(private val executors: Executors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        result.addSource<ApiResponse>(apiResponse) { response ->
            result.removeSource<ApiResponse>(apiResponse)
            result.removeSource(dbSource)

            if (response != null && response.response != null) {
                executors.diskIO.execute({
                    processApiResponse(response)
                    executors.mainThread.execute({
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb())
                                    { newData -> result.setValue(Resource.success(newData)) }
                    })
                })
            } else {
                onFetchFailed()
                result.addSource(dbSource)
                            { newData ->
                                result.setValue(Resource.error(response?.error?.errorMessage?: "No error message", newData)) }
            }
        }
    }

    protected fun onFetchFailed() {}

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    protected abstract fun processApiResponse(response: ApiResponse)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse>
}