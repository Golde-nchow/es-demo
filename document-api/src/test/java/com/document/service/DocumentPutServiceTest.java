package com.document.service;

import com.alibaba.fastjson.JSON;
import com.document.BaseTest;
import com.document.entity.Document;
import com.document.service.single.DocumentPutApiServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author by chow
 * @Description 新增文档测试用例
 * @date 2021/9/25 下午1:18
 */
public class DocumentPutServiceTest extends BaseTest {

    @Autowired
    private DocumentPutApiServiceImpl documentPutApiService;

    @Test
    public void addJsonString() {
        Document document = Document
                .builder()
                .author("cjz")
                .content("单元测试的要求...网上很多。下面来分享一下我是如何写单元测试。首先我们项目一般都是MVC分层的，而单元测试主要是在Dao层和Service层上进行编写。从项目结构上来说，Service层是依赖Dao层的，但是从单元测试角度，对某个Service进行单元的时候，他所有依赖的类都应该进行Mock。而Dao层单元测试就比较简单了（下面Dao层就以Jdbc为例子），只依赖数据库中的数据。")
                .createTime(LocalDateTime.now())
                .build();
        documentPutApiService.addJsonString(JSON.toJSONString(document));
    }

}
