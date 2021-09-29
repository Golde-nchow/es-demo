package com.document.service.single;

import com.document.entity.Document;
import com.document.service.DocumentApiBaseServiceImpl;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author by chow
 * @Description 文档更新业务
 * @date 2021/9/28 下午9:04
 */
@Service
public class DocumentUpdateApiServiceImpl extends DocumentApiBaseServiceImpl {

    /**
     * 更新文档
     * @param indexName 索引名称
     * @param documentId 文档ID
     * @param jsonString 更新信息
     * @return 更新结果
     */
    public String updateDocument(String indexName,
                               String documentId,
                               String jsonString) {
        UpdateRequest updateRequest = new UpdateRequest(indexName, documentId);
        // 若有其他操作，则进行重试
        updateRequest.retryOnConflict(3);
        // 禁用检索源
        updateRequest.fetchSource(false);
        // 源包含的字段
        String[] includes = new String[]{"content"};
        String[] excludes = Strings.EMPTY_ARRAY;
        updateRequest.fetchSource(new FetchSourceContext(true, includes, excludes));

        // 构建文档（和 GET-API非常相似）
        updateRequest.doc(jsonString, XContentType.JSON);

        try {
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            GetResult getResult = updateResponse.getGetResult();
            return getResult.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "更新失败";
    }

}
