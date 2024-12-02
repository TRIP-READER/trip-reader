package com.tripreader.news.model.dto;

import lombok.Data;

@Data
public class NewsDto {
    private int newsKey;
    private String url;
    private String title;
    private int newsSummaryKey;
    private String content;
}
