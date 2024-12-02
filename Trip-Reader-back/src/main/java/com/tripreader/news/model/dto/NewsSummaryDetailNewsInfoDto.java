package com.tripreader.news.model.dto;

import lombok.Data;

@Data
public class NewsSummaryDetailNewsInfoDto {
    private int id;
    private String title;
    private String content;
    private String url;
}
