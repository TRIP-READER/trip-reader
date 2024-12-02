package com.tripreader.attraction.model.dto;

import lombok.Data;

@Data
public class AttractionsDto {
    private int attractionsKey;
    private int contentid;
    private int contenttypeid;
    private String title;
    private String addr1;
    private String addr2;
    private int areaCode;
    private String createdtime;
    private String modifiedtime;
    private String firstimage;
    private String firstimage2;
    private double mapx;
    private double mapy;
    private int sigunguCode;
    private String tel;
    private String infocenter;
    private String opendate;
    private String restdate;
    private String expguide;
    private String expagerange;
    private String accomcount;
    private String useseason;
    private String usetime;
    private String parking;
}
