package com.document.service;

import com.document.BaseTest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author by chow
 * @Description 词向量测试
 * @date 2021/9/28 下午10:58
 */
public class DocumentVectorServiceTest extends BaseTest {

    @Autowired
    private DocumentVectorServiceImpl documentVectorService;

    private Log log = LogFactory.getLog(DocumentVectorServiceTest.class);

    @Test
    public void testVector() {
        List<TermVectorsResponse.TermVector> termVectorList = documentVectorService.getTermVector("cjz", "cjz-1", "content");
        for (TermVectorsResponse.TermVector termVector : termVectorList) {
            log.info("=============================");
            log.info(termVector.getFieldName());
            log.info(termVector.getFieldStatistics().toString());
            List<TermVectorsResponse.TermVector.Term> terms = termVector.getTerms();
            log.info("单词统计开始");
            for (TermVectorsResponse.TermVector.Term term : terms) {
                log.info(term.getTerm() + " ：" + term.getTermFreq());
            }
        }
    }

}
