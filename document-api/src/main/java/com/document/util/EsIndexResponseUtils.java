package com.document.util;

import joptsimple.internal.Strings;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;

import java.util.Arrays;

/**
 * @author by chow
 * @Description ES-IndexResponse 解析工具类
 * @date 2021/9/25 上午10:04
 */
public class EsIndexResponseUtils {

    private static Log LOG = LogFactory.getLog(EsIndexResponseUtils.class);

    /**
     * 解析 IndexResponse：文档索引响应
     * @param indexResponse 文档索引响应对象
     * @return 请求处理结果
     */
    public static String processIndexResponse(IndexResponse indexResponse) {
        String responseId = indexResponse.getId();
        String responseIndex = indexResponse.getIndex();

        LOG.info(String.format("index = %s, id = %s", responseIndex, responseId));

        // 文档创建或更新
        if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            LOG.info("文档更新");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            LOG.info("文档创建");
        }

        // 分片的处理情况（成功数量、失败信息）
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        LOG.info("分片处理情况：" + shardInfo.toString());

        StringBuilder result = new StringBuilder();
        if (shardInfo.getFailed() > 0) {
            ReplicationResponse.ShardInfo.Failure[] failures = shardInfo.getFailures();
            for (ReplicationResponse.ShardInfo.Failure failure : failures) {
                result.append(failure.getMessage()).append("，");
            }
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

}
