package com.document.service;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author by chow
 * @Description 检查文档存在性
 * @date 2021/9/28 下午8:37
 */
@Service
public class DocumentCheckExistApiServiceImpl extends DocumentApiBaseServiceImpl {

    /**
     * 检查文档存在性
     * @param indexName 索引名称
     * @param documentId 文档ID
     * @return 是否存在
     */
    public boolean checkExist(String indexName, String documentId) {
        GetRequest getRequest = new GetRequest(indexName, documentId);
        // 禁止获取值
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        // 禁止存储值
        getRequest.storedFields("_none");

        try {
            return client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("校验失败");
    }

}
