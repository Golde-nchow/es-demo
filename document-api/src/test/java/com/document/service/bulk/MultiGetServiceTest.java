package com.document.service.bulk;

import com.document.BaseTest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author by chow
 * @Description 批量获取测试
 * @date 2021/9/29 下午11:30
 */
public class MultiGetServiceTest extends BaseTest {

    @Autowired
    private DocumentMultiGetServiceImpl documentMultiGetService;

    Log log = LogFactory.getLog(MultiGetServiceTest.class);

    @Test
    public void multiGet() {
        log.info(documentMultiGetService.buildMultiGetRequest("cjz", new String[]{"cjz-1", "cjz-2", "cjz-3"}));
    }

}
