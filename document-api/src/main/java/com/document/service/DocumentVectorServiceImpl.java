package com.document.service;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by chow
 * @Description 词向量业务（说白点，就是单词在文档中的统计信息）
 * @date 2021/9/28 下午10:33
 */
@Service
public class DocumentVectorServiceImpl extends DocumentApiBaseServiceImpl {

    /**
     * 查询文档的词向量
     * @param indexName 索引名称
     * @param documentId 文档ID
     * @param fields 查询的词
     * @return 结果
     */
    public List<TermVectorsResponse.TermVector> getTermVector(String indexName, String documentId, String... fields) {
        TermVectorsRequest termVectorsRequest = new TermVectorsRequest(indexName, documentId);
        termVectorsRequest.setFields(fields);

        // 字段统计设置，关闭时，忽略文档计数、文档频率总和、总术语频率
        termVectorsRequest.setFieldStatistics(true);
        // 是否输出单词的位置
        termVectorsRequest.setPositions(true);
        // 是否输出偏移量
        termVectorsRequest.setOffsets(true);
        // 是否显示有效荷载，也就是长度
        termVectorsRequest.setPayloads(true);
        // 分析器
        Map<String, String> preFieldAnalyzer = new HashMap<>(16);
        preFieldAnalyzer.put("user", "keyword");
        termVectorsRequest.setPerFieldAnalyzer(preFieldAnalyzer);
        // 是否开启准实时，开启则会在 n 秒内更新索引
        termVectorsRequest.setRealtime(true);

        try {
            TermVectorsResponse vectorsResponse = client.termvectors(termVectorsRequest, RequestOptions.DEFAULT);
            return vectorsResponse.getTermVectorsList();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

}
