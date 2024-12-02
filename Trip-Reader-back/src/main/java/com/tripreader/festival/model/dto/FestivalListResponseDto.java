package com.tripreader.festival.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class FestivalListResponseDto {
/*
"content_id": 12324,
"title": "쌈@뽕한 축제"
"overview" : "이것은 축제다"
"addr1": "강원특별자치도 강릉시 종합운동장길 84 (교동)",
"addr2": "강릉아트센터",
"eventstartdate": "20241101",
"eventenddate": "20241103",
"image": "http://image.com"
*/
    private int contentid;
    private String title;
    private String overview;
    private String addr1;
    private String addr2;
    private String eventstartdate;
    private String eventenddate;
    private String image;
}
