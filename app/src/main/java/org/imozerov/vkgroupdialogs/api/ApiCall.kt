package org.imozerov.vkgroupdialogs.api

import android.arch.lifecycle.LiveData
import android.util.Log
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse


class ApiCall(private val apiRequest: VKRequest) : LiveData<ApiResponse>() {
    companion object {
        val TAG = "ApiCall"
    }

    override fun onActive() {
        apiRequest.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                Log.v(TAG, "onComplete $this with response $response")
                value = ApiResponse(response, null)
            }

            override fun attemptFailed(request: VKRequest, attemptNumber: Int, totalAttempts: Int) {
                Log.e(TAG, "attemptFailed $this with response $request")
                value = ApiResponse(null, null)
            }

            override fun onError(error: VKError) {
                Log.e(TAG, "attemptFailed $this with response $error")
                value = ApiResponse(null, error)
            }
        })
    }
}
