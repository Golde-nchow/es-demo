package com.document.service;

import com.alibaba.fastjson.JSON;
import com.document.BaseTest;
import com.document.entity.Document;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author by chow
 * @Description 测试文档更新
 * @date 2021/9/28 下午9:53
 */
public class DocumentUpdateServiceTest extends BaseTest {

    @Autowired
    private DocumentUpdateApiServiceImpl documentUpdateApiService;

    private Log log = LogFactory.getLog(DocumentUpdateServiceTest.class);

    @Test
    public void testUpdate() {
        Document document = Document
                .builder()
                .author("zjc")
                .createTime(LocalDateTime.now())
                .content("-p 3306:3306：将容器的3306端口映射到主机的3306端口\n" +
                        "-v /mydata/mysql/conf:/etc/mysql：将配置文件夹挂在到主机\n" +
                        "-v /mydata/mysql/log:/var/log/mysql：将日志文件夹挂载到主机\n" +
                        "-v /mydata/mysql/data:/var/lib/mysql/：将数据文件夹挂载到主机\n" +
                        "-e MYSQL_ROOT_PASSWORD=root：初始化root用户的密码")
                .build();

        String result = documentUpdateApiService.updateDocument("cjz", "cjz-1", JSON.toJSONString(document));
        log.info("更新结果：" + result);
    }

}
