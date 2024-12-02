package com.tripreader.attraction.model.mapper;

import com.tripreader.attraction.model.dto.AttractionsDto;
import com.tripreader.attraction.model.dto.CulturalFacilitiesDto;
import com.tripreader.attraction.model.dto.FestivalEventsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionMapper {
    // SELECT: 관광지 정보 존재 여부 확인
    boolean existsAttractions(int contentid);

    // SELECT: 문화시설 정보 존재 여부 확인
    boolean existsCulturalFacilities(int contentid);

    // SELECT: 축제/공연/행사 정보 존재 여부 확인
    boolean existsFestivalEvents(int contentid);

    // SELECT: 관광지 정보 조회
    AttractionsDto getAttractions(int contentid);

    // SELECT: 문화시설 정보 조회
    CulturalFacilitiesDto getCulturalFacilities(int contentid);

    // SELECT: 축제/공연/행사 정보 조회
    FestivalEventsDto getFestivalEvents(int contentid);

    // INSERT: 관광지 데이터 추가
    void insertAttraction(AttractionsDto attraction);

    // INSERT: 문화시설 데이터 추가
    void insertCulturalFacility(CulturalFacilitiesDto culturalFacility);

    // INSERT: 축제/공연/행사 데이터 추가
    void insertFestivalEvent(FestivalEventsDto festivalEvent);

    // UPDATE: 관광지 데이터 수정
    void updateAttraction(AttractionsDto attraction);

    // UPDATE: 문화시설 데이터 수정
    void updateCulturalFacility(CulturalFacilitiesDto culturalFacility);

    // UPDATE: 축제/공연/행사 데이터 수정
    void updateFestivalEvent(FestivalEventsDto festivalEvent);

    // DELETE: 관광지 데이터 삭제
    void deleteAttraction(int contentid);

    // DELETE: 문화시설 데이터 삭제
    void deleteCulturalFacility(int contentid);

    // DELETE: 축제/공연/행사 데이터 삭제
    void deleteFestivalEvent(int contentid);

    List<FestivalEventsDto> getAllFestivals(int page);
}
