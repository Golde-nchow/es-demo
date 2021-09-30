package com.search.service;

import com.search.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author by chow
 * @Description 搜索API测试
 * @date 2021/9/30 下午11:58
 */
public class SearchRequestApiServiceTest extends BaseTest {

    @Autowired
    private SearchRequestApiServiceImpl searchRequestApiService;

    @Test
    public void testSimpleSearch() {
        searchRequestApiService.buildSearchRequest();
    }

}
