package com.tripreader.news.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NewsSummaryMainDto {
    private int newsSummaryMainKey;
    private String newsSummaryNano;
    private String section;
    private int sectionOrder;

    public NewsSummaryMainDto(String newsSummaryNano, String section, int sectionOrder) {
        this.newsSummaryNano = newsSummaryNano;
        this.section = section;
        this.sectionOrder = sectionOrder;
    }
}