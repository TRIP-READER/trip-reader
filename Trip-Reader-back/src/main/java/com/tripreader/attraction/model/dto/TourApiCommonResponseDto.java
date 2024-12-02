package com.tripreader.attraction.model.dto;

import lombok.Data;

@Data
public class TourApiCommonResponseDto {
    private String addr1;
    private String addr2;
    private int areaCode;
    private int contentid;
    private int contenttypeid;
    private String firstimage;
    private String firstimage2;
    private double mapx;
    private double mapy;
    private int sigunguCode;
    private String createdtime;
    private String modifiedtime;
    private String tel;
    private String title;
}
