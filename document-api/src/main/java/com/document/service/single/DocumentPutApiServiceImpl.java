package com.document.service.single;

import com.document.service.DocumentApiBaseServiceImpl;
import com.document.util.EsIndexRequestUtils;
import com.document.util.EsIndexResponseUtils;
import joptsimple.internal.Strings;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * @author by chow
 * @Description 文档插入业务层
 * @date 2021/9/25 上午11:20
 */
@Service
public class DocumentPutApiServiceImpl extends DocumentApiBaseServiceImpl {

    /**
     * 添加 json 文档数据
     * @param contentJson json字符串
     * @return 文档数据
     */
    public String addJsonString(String contentJson) {
        IndexRequest indexRequest = EsIndexRequestUtils.buildIndexRequestWithString(
                "cjz", "cjz-1", contentJson
        );
        IndexResponse response;
        try {
            response = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return Strings.EMPTY;
        }
        return EsIndexResponseUtils.processIndexResponse(response);
    }

}
