package com.document.service.bulk;

import com.document.service.DocumentApiBaseServiceImpl;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author by chow
 * @Description 批量获取
 * @date 2021/9/29 下午11:16
 */
@Service
public class DocumentMultiGetServiceImpl extends DocumentApiBaseServiceImpl {

    public List<String> buildMultiGetRequest(String indexName, String[] documentIds) {
        if (documentIds == null || documentIds.length == 0) {
            return Collections.emptyList();
        }

        String[] includes = new String[]{"content"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);

        MultiGetRequest multiGetRequest = new MultiGetRequest();
        for (String documentId : documentIds) {
            multiGetRequest.add(
                    new MultiGetRequest
                            .Item(indexName, documentId)
                            .fetchSourceContext(fetchSourceContext)
                            .storedFields("author", "content", "createTime")
            );
        }

        List<String> result = new LinkedList<>();
        try {
            MultiGetResponse responses = client.mget(multiGetRequest, RequestOptions.DEFAULT);
            MultiGetItemResponse[] responseItemArr = responses.getResponses();
            for (MultiGetItemResponse multiGetItemResponse : responseItemArr) {
                GetResponse response = multiGetItemResponse.getResponse();
                if (response.isExists()) {
                    result.add(response.getSource().toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
