package com.tripreader.attraction.model.dto;

import lombok.Data;

@Data
public class FestivalEventsDto {
    private int festivalEventsKey;
    private int contentid;
    private int contenttypeid;
    private String title;
    private String overview;
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
    private String sponsor1;
    private String sponsor1tel;
    private String sponsor2;
    private String sponsor2tel;
    private String eventenddate;
    private String playtime;
    private String eventplace;
    private String eventhomepage;
    private String agelimit;
    private String bookingplace;
    private String placeinfo;
    private String subevent;
    private String program;
    private String eventstartdate;
    private String usetimefestival;
    private String discountinfofestival;
    private String spendtimefestival;
    private String festivalgrade;
}
