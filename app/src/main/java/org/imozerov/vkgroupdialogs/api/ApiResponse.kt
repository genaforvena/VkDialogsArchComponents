package org.imozerov.vkgroupdialogs.api

import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKResponse

data class ApiResponse(val response: VKResponse?, val error: VKError?)