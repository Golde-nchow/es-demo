package com.search;

import com.es.factory.ConnectionFactory;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author by chow
 * @Description 文档业务层
 * @date 2021/9/25 上午11:13
 */
public class SearchApiBaseServiceImpl {

    protected RestHighLevelClient client = ConnectionFactory.getClient();

}
