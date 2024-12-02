package com.tripreader.attraction.model.dto;

import lombok.Data;

@Data
public class AttractionCommonDto {
    private int contentid;
    private int contenttypeid;
    private String title;
    private String firstimage;
    private int areaCode;
    private int sigunguCode;
    private String addr1;
    private String addr2;
}
