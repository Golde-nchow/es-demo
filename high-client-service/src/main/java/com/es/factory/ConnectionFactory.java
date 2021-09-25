package com.es.factory;

import org.apache.http.HttpHost;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Objects;

/**
 * @author by chow
 * @Description 连接工厂（双检查单例模式）
 * @date 2021/9/25 上午9:19
 */
public class ConnectionFactory {

    private volatile static RestHighLevelClient REST_CLIENT;

    private static Log LOG = LogFactory.getLog(ConnectionFactory.class);

    /**
     * 获取客户端
     * @return 客户端/opt/idea-IU-Practise/elasticsearch-demo/document-api/src/main/java/com/document
     */
    public static RestHighLevelClient getClient() {
        if (Objects.isNull(REST_CLIENT)) {
            synchronized (RestHighLevelClient.class) {
                if (Objects.isNull(REST_CLIENT)) {
                    REST_CLIENT = new RestHighLevelClient(
                            RestClient.builder(new HttpHost("172.17.0.2", 9200, "http"))
                    );
                    LOG.info("ES初始化完成================");
                }
            }
        }
        return REST_CLIENT;
    }

    /**
     * 关闭连接
     */
    public static void closeClient() {
        try {
            REST_CLIENT.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
