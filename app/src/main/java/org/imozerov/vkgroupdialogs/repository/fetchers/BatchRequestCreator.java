package org.imozerov.vkgroupdialogs.repository.fetchers;


import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKRequest;

/**
 * Hack to be able to pass kotlin array as vararg to
 * {@link VKBatchRequest} constructor. Maybe there is a better way.
 */
public class BatchRequestCreator {
    public static VKBatchRequest createFrom(VKRequest[] requests) {
        return new VKBatchRequest(requests);
    }
}
