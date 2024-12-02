package com.tripreader.common.util;

import com.tripreader.attraction.model.dto.*;
import org.springframework.stereotype.Component;

@Component
public class AttractionInfoEnhancer {
    public static AttractionsDto addDetailInfo(AttractionsDto detail, TourApiCommonResponseDto common) {
        detail.setAddr1(common.getAddr1());
        detail.setAddr2(common.getAddr2());
        detail.setAreaCode(common.getAreaCode());
        detail.setContentid(common.getContentid());
        detail.setContenttypeid(common.getContenttypeid());
        detail.setFirstimage(common.getFirstimage());
        detail.setFirstimage2(common.getFirstimage2());
        detail.setMapx(common.getMapx());
        detail.setMapy(common.getMapy());
        detail.setSigunguCode(common.getSigunguCode());
        detail.setTel(common.getTel());
        detail.setTitle(common.getTitle());
        detail.setCreatedtime(common.getCreatedtime());
        detail.setModifiedtime(common.getModifiedtime());
        return detail;
    }

    public static CulturalFacilitiesDto addDetailInfo(CulturalFacilitiesDto detail, TourApiCommonResponseDto common) {
        detail.setAddr1(common.getAddr1());
        detail.setAddr2(common.getAddr2());
        detail.setAreaCode(common.getAreaCode());
        detail.setContentid(common.getContentid());
        detail.setContenttypeid(common.getContenttypeid());
        detail.setFirstimage(common.getFirstimage());
        detail.setFirstimage2(common.getFirstimage2());
        detail.setMapx(common.getMapx());
        detail.setMapy(common.getMapy());
        detail.setSigunguCode(common.getSigunguCode());
        detail.setTel(common.getTel());
        detail.setTitle(common.getTitle());
        detail.setCreatedtime(common.getCreatedtime());
        detail.setModifiedtime(common.getModifiedtime());
        return detail;
    }

    public static FestivalEventsDto addDetailInfo(FestivalEventsDto detail, TourApiCommonResponseDto common) {
        detail.setAddr1(common.getAddr1());
        detail.setAddr2(common.getAddr2());
        detail.setAreaCode(common.getAreaCode());
        detail.setContentid(common.getContentid());
        detail.setContenttypeid(common.getContenttypeid());
        detail.setFirstimage(common.getFirstimage());
        detail.setFirstimage2(common.getFirstimage2());
        detail.setMapx(common.getMapx());
        detail.setMapy(common.getMapy());
        detail.setSigunguCode(common.getSigunguCode());
        detail.setTel(common.getTel());
        detail.setTitle(common.getTitle());
        detail.setCreatedtime(common.getCreatedtime());
        detail.setModifiedtime(common.getModifiedtime());
        return detail;
    }

    public static FestivalEventsDto addDetailInfo(FestivalEventsDto detail, TourApiFestivalResponseDto common) {
        detail.setAddr1(common.getAddr1());
        detail.setAddr2(common.getAddr2());
        detail.setAreaCode(common.getAreaCode());
        detail.setContentid(common.getContentid());
        detail.setContenttypeid(common.getContenttypeid());
        detail.setFirstimage(common.getFirstimage());
        detail.setFirstimage2(common.getFirstimage2());
        detail.setMapx(common.getMapx());
        detail.setMapy(common.getMapy());
        detail.setSigunguCode(common.getSigunguCode());
        detail.setTel(common.getTel());
        detail.setTitle(common.getTitle());
        detail.setCreatedtime(common.getCreatedtime());
        detail.setModifiedtime(common.getModifiedtime());
        detail.setEventenddate(common.getEventenddate());
        detail.setEventstartdate(common.getEventstartdate());
        return detail;
    }
}
