package com.tripreader.news.model.dto;
import java.util.*;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NewsSummaryMainResponseDto {
    private String newsSummaryNano;
    private String title;
    private String content;
    private String image;
}
