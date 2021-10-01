package com.search.service;

import com.search.BaseTest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author by chow
 * @Description 滚动查询
 * @date 2021/10/1 下午8:41
 */
public class SearchScrollServiceTest extends BaseTest {

    @Autowired
    private SearchScrollApiServiceImpl searchScrollApiService;

    @Test
    public void testScroll() {
        SearchResponse searchResponse = searchScrollApiService.simpleScroll("jjj", 1);
        if (Objects.isNull(searchResponse)) {
            return;
        }

        SearchHits searchHits = searchResponse.getHits();

        String scrollId = searchResponse.getScrollId();
        log.info("滚动 id：" + scrollId);
        while (searchHits != null && searchHits.getHits().length != 0) {
            searchHits = searchScrollApiService.queryByScrollId(searchResponse.getScrollId());

            SearchHit[] hits = searchHits.getHits();
            log.info("结果 =====================");
            for (SearchHit hit : hits) {
                log.info(hit.getSourceAsString());
            }

            log.info(searchHits.getTotalHits().value + " - " + searchHits.getHits().length);
            log.info("结束 =====================");
        }
    }

}
