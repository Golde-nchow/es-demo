package com.document.service;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author by chow
 * @Description 文档获取业务
 * @date 2021/9/27 下午11:53
 */
@Service
public class DocumentGetApiServiceImpl extends DocumentApiBaseServiceImpl {

    /**
     * 查询请求
     * @param indexName 索引名称
     * @param documentId 文档 id
     * @return 请求信息
     */
    public String getRequest(String indexName, String documentId) throws IOException {
        GetRequest getRequest = new GetRequest(indexName, documentId);
        getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);

        String[] includes = new String[]{"content"};
        String[] excludes = Strings.EMPTY_ARRAY;

        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);
        getRequest.storedFields("content");

        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        return (String) response.getSource().get("content");
    }

}
