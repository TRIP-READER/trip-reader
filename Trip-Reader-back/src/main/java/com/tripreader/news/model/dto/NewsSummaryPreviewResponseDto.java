package com.tripreader.news.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NewsSummaryPreviewResponseDto {
    private String newsSummaryNano;
    private String title;
    private String darkTitle;
    private String image;
    private String header;
    private String content;
    private String prettyTime;
    private int scrapCount;
}
