package com.document.service;

import com.document.BaseTest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author by chow
 * @Description 测试文档校验性
 * @date 2021/9/28 下午8:46
 */
public class DocumentCheckExistServiceTest extends BaseTest {

    @Autowired
    private DocumentCheckExistApiServiceImpl documentCheckExistApiService;

    private Log log = LogFactory.getLog(DocumentCheckExistServiceTest.class);

    @Test
    public void checkExist() {
        log.info("文档是否存在：" + documentCheckExistApiService.checkExist("cjz", "cjz-1"));
    }

}
