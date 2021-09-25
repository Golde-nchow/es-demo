package com.document;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author by chow
 * @Description 测试基类
 * @date 2021/9/25 下午1:24
 */
@SpringBootTest(classes = DocumentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

}
