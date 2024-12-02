package com.tripreader.news.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NewsSummaryDetailResponseDto {
    private String newsSummaryNano;
    private String title;
    private String image;
    private int contentId;

    private List<ContentDto> contentList;
    private List<NewsSummaryDetailVideoDto> videoList;
    private List<NewsSummaryDetailNewsInfoDto> newsList;
    private String prettyTime;
    private int scrapCount;

    private MarkerDto mainMarker;
    private List<MarkerDto> markerList;
}
