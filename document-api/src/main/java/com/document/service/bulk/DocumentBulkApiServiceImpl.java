package com.document.service.bulk;

import com.document.service.DocumentApiBaseServiceImpl;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author by chow
 * @Description 批量处理文档
 * @date 2021/9/29 下午7:48
 */
@Service
public class DocumentBulkApiServiceImpl extends DocumentApiBaseServiceImpl {

    /**
     * 批量请求
     * @param indexName 索引名称
     * @param field 字段内容
     * @return 请求结果
     */
    public List<String> bulkRequest(String indexName, String field) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        // 新增请求
        bulkRequest.add(new IndexRequest(indexName).id("1").source(XContentType.JSON, field, "为文档建立索引后"));
        bulkRequest.add(new IndexRequest(indexName).id("2").source(XContentType.JSON, field, "您可以立即获取它"));
        // 修改请求
        bulkRequest.add(new UpdateRequest(indexName, "1").doc(XContentType.JSON, field, "因此，如果您像这样索引新文档"));

        List<String> results = new LinkedList<>();
        // 异步批量请求
        client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                results.addAll(processBulkResponses(bulkItemResponses));
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        return results;
    }

    /**
     * 处理批量请求结果
     * @param bulkItemResponses 批量请求响应
     * @return 结果列表
     */
    private List<String> processBulkResponses(BulkResponse bulkItemResponses) {
        List<String> results = new LinkedList<>();
        BulkItemResponse[] items = bulkItemResponses.getItems();

        for (BulkItemResponse item : items) {
            DocWriteRequest.OpType opType = item.getOpType();
            DocWriteResponse itemResponse = item.getResponse();
            switch (opType) {
                case INDEX:
                    results.add("查询索引状态，结果：" + itemResponse.getResult().toString());
                    break;
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    results.add("新增文档，结果：" + indexResponse.getResult().toString());
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    results.add("删除文档，结果：" + deleteResponse.getResult().toString());
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    results.add("更新文档，结果：" + updateResponse.getResult().toString());
                    break;
                default:
                    results.add("未知操作，结果：" + item.getResponse().getResult().toString());
                    break;
            }
        }
        return results;
    }

}
