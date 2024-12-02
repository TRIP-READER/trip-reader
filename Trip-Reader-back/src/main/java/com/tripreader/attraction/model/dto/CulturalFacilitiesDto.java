package com.tripreader.attraction.model.dto;

import lombok.Data;

@Data
public class CulturalFacilitiesDto {
    private int culturalFacilitiesKey;
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
    private String scale;
    private String usefee;
    private String discountinfo;
    private String spendtime;
    private String parkingfee;
    private String infocenterculture;
    private String accomcountculture;
    private String usetimeculture;
    private String restdateculture;
    private String parkingculture;
}
