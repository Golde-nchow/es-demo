package com.document.service.single;

import com.document.BaseTest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author by chow
 * @Description 查询文档测试
 * @date 2021/9/28 上午12:07
 */
public class DocumentGetServiceTest extends BaseTest {

    @Autowired
    private DocumentGetApiServiceImpl documentGetApiService;

    private Log log = LogFactory.getLog(DocumentGetServiceTest.class);

    @Test
    public void testGet() throws IOException {
        String msg = documentGetApiService.getRequest("cjz", "cjz-1");
        log.info("msg : " + msg);
    }
}
