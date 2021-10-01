package com.search.service;

import joptsimple.internal.Strings;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author by chow
 * @Description 滚动搜索
 * @date 2021/10/1 下午8:16
 */
@Service
public class SearchScrollApiServiceImpl extends SearchApiBaseServiceImpl {

    /**
     * 滚动查询
     * @param indexName 索引名称
     * @param size 条数
     * @return 查询结果
     */
    public SearchResponse simpleScroll(String indexName, int size) {
        // 索引名称
        SearchRequest searchRequest = new SearchRequest(indexName);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 内容过滤
        searchSourceBuilder.query(QueryBuilders.matchQuery("content", "您"));

        // 查询条数
        searchSourceBuilder.size(size);

        // 设置参数
        searchRequest.source(searchSourceBuilder);

        // 设置滚动的过期时间
        searchRequest.scroll(TimeValue.timeValueMinutes(1));

        try {
            return client.search(searchRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过滚动ID，查询信息（查询一次后，就不再返回数据了）
     * @param scrollId 滚动ID
     * @return 查询内容
     */
    public SearchHits queryByScrollId(String scrollId) {
        SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
        searchScrollRequest.scroll(TimeValue.timeValueSeconds(30));
        try {
            SearchResponse searchResponse = client.scroll(searchScrollRequest, RequestOptions.DEFAULT);
            return searchResponse.getHits();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
