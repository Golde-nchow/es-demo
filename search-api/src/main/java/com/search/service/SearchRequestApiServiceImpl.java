package com.search.service;

import joptsimple.internal.Strings;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * <p>
 * 搜索 api - 基础
 * </p>
 *
 * @author caojinzhou
 * @since 2021/9/30 14:23
 */
@Service
public class SearchRequestApiServiceImpl extends SearchApiBaseServiceImpl {

    /**
     * 构建全搜索请求
     * @return 搜索结果
     */
    public String buildAllSearchRequest() {
        SearchRequest searchRequest = new SearchRequest();

        // 构建 source 搜索参数
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 全匹配
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        // 要从哪个 source 开始检索
        searchSourceBuilder.from(0);

        // 需要查询多少条
        searchSourceBuilder.size(3);

        // 将参数添加到请求中
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Strings.EMPTY;
    }

    /**
     * 构建部分字段搜索请求
     * @return 搜索结果
     */
    public String buildSearchRequest() {
        SearchRequest searchRequest = new SearchRequest();

        // 构建 source 搜索参数
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 匹配参数，检索 content 字段，值为：xx
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("content", "查询");

        // 开启模糊搜索
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);

        // 查询时，设置前缀长度
        matchQueryBuilder.prefixLength(3);

        // 最大拓展选项，控制模糊查询和前缀搜索
        matchQueryBuilder.maxExpansions(10);

        // 将匹配参数添加到搜索请求
        searchSourceBuilder.query(matchQueryBuilder);

        // 排序参数
        FieldSortBuilder fieldSortBuilder = new FieldSortBuilder("_createTime").order(SortOrder.DESC);
        searchSourceBuilder.sort(fieldSortBuilder);

        // 是否每次都返回，都命中 stored 的值
        searchSourceBuilder.fetchSource(false);

        // 支持通配符模式的包含与不包含数组
        String[] includeFields = new String[] {"content"};
        String[] excludeFields = new String[0];
        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 为 author 字段设置高亮
        HighlightBuilder.Field authorHighLight = new HighlightBuilder.Field("author");
        // 高亮类型
        authorHighLight.highlighterType("fvh");
        // 设置高亮
        searchSourceBuilder.highlighter(highlightBuilder);

        // 请求聚合（统计）
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("content").field("content.keyword");
        aggregationBuilder.subAggregation(AggregationBuilders.count("content_count").field("content"));
        searchSourceBuilder.aggregation(aggregationBuilder);

        // 建议请求
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        SuggestionBuilder suggestionBuilder = SuggestBuilders.termSuggestion("content").text("xx");
        // 添加生成器
        suggestBuilder.addSuggestion("content_suggestion", suggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);

        // 是否应该检索、聚合、过滤
        searchSourceBuilder.profile(true);

        // 请求设置参数
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return searchResponse.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Strings.EMPTY;
    }

}
