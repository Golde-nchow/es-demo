package com.document.util;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * @author by chow
 * @Description es请求工具类
 * @date 2021/9/25 上午9:42
 */
public class EsIndexRequestUtils {

    /**
     * 通过 jsonString 构建 IndexRequest
     * @param indexName 索引名称
     * @param documentId 文档id
     * @return IndexRequest
     */
    public static IndexRequest buildIndexRequestWithString(String indexName,
                                                           String documentId,
                                                           String jsonString) {
        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(documentId);
        return indexRequest.source(jsonString, XContentType.JSON);
    }

    /**
     * 通过 XContentBuilder 构建 IndexRequest
     * @param indexName 索引名称
     * @param documentId 文档ID
     * @param params 参数
     * @return IndexRequest
     */
    public static IndexRequest buildIndexRequestWithContentBuild(String indexName,
                                                                 String documentId,
                                                                 Map<String, Object> params) {

        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder();
            contentBuilder.startObject();

            // 其实这里可以直接使用 contentBuilder.map() 来直接放入参数
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                Object value = params.get(key);
                if (value instanceof LocalDateTime) {
                    contentBuilder.timeField(key, value);
                } else if (value instanceof String) {
                    contentBuilder.field(key, value);
                } else if (value.getClass().isArray()) {
                    contentBuilder.array(key, value);
                }
            }

            contentBuilder.endObject();
            return new IndexRequest(indexName).id(documentId).source(contentBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过键值对构建 IndexRequest（底层本质上使用了 XContentBuilder）
     * @param indexName 索引名称
     * @param documentId 文档ID
     * @param params 键值对参数
     * @return IndexRequest
     */
    public static IndexRequest buildIndexRequestWithKeyValue(String indexName,
                                                             String documentId,
                                                             Map<String, Object> params) {
        IndexRequest indexRequest = new IndexRequest(indexName).id(documentId);

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            Object value = params.get(key);
            indexRequest.source(key, value);
        }

        return indexRequest;
    }

}
