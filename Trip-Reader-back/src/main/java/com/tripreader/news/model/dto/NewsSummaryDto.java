package com.tripreader.news.model.dto;

import lombok.Data;

@Data
public class NewsSummaryDto {
    private int newsSummaryKey;
    private String newsSummaryNano;
    private int contentid;
    private int contenttypeid;
    private int areaCode;
    private int sigunguCode;

    private String title;
    private String darkTitle;
    private String content;
    private int scrapCount;
    private String image;
    private String createdTime;
}
