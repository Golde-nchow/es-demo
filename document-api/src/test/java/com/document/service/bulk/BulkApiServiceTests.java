package com.document.service.bulk;

import com.document.BaseTest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @author by chow
 * @Description 批量处理 API
 * @date 2021/9/29 下午8:30
 */
public class BulkApiServiceTests extends BaseTest {

    @Autowired
    private DocumentBulkApiServiceImpl documentBulkApiService;

    Log log = LogFactory.getLog(BulkApiServiceTests.class);

    @Test
    public void bulkTest() throws IOException {
        List<String> resultList = documentBulkApiService.bulkRequest("jjj", "content");
        for (String s : resultList) {
            log.info(s);
        }
    }

}
