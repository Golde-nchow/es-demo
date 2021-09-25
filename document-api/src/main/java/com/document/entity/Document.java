package com.document.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author by chow
 * @Description 文档
 * @date 2021/9/25 上午11:26
 */
@Data
@Builder
@ToString
public class Document {

    /**
     * 作者
     */
    private String author;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
