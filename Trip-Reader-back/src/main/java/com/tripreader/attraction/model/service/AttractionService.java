package com.tripreader.attraction.model.service;

import com.tripreader.attraction.model.dto.*;
import com.tripreader.attraction.model.mapper.AttractionMapper;
import com.tripreader.common.util.AttractionInfoEnhancer;
import com.tripreader.common.util.TourApiGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AttractionService {

    AttractionMapper attractionMapper;
    TourApiGetter tourApiGetter;
    AttractionInfoEnhancer attractionInfoEnhancer;

    public AttractionService(AttractionMapper attractionMapper, TourApiGetter tourApiGetter, AttractionInfoEnhancer attractionInfoEnhancer) {
        this.attractionMapper = attractionMapper;
        this.tourApiGetter = tourApiGetter;
        this.attractionInfoEnhancer = attractionInfoEnhancer;
    }

    public List<FestivalEventsDto> getAllFestivals(int page){
        return attractionMapper.getAllFestivals(page);
    }

    public Object getAttractionInfoWithId(int contenttypeid, int contentid) throws Exception {
        //관광지
        if(contenttypeid == 12) {
            //DB에서 정보를 가져와 반환
            return attractionMapper.getAttractions(contentid);
        }
        //문화시설
        else if(contenttypeid == 14) {
            return attractionMapper.getCulturalFacilities(contentid);
        }
        //축제/공연/행사
        else if(contenttypeid == 15) {
            return attractionMapper.getFestivalEvents(contentid);
        }

        throw new Exception("잘못된 contenttypeid입니다.");
    }


    //통합 검색
    public Object CommonSearch(Map<String, Object> params) {
        List<TourApiCommonResponseDto> results = tourApiGetter.commonSearch(params);
        List<AttractionCommonDto> response = new ArrayList<>();
        //검색결과 정보가 db에 없다면 상세검색 정보를 추가하여 db에 저장후 반환
        for(TourApiCommonResponseDto result : results) {
            AttractionCommonDto responseDto = new AttractionCommonDto();
            switch (result.getContenttypeid()) {
                case 12:
                    if(!attractionMapper.existsAttractions(result.getContentid())) {
                        AttractionsDto attractionsDto = tourApiGetter.getAttractionDetail(12, result.getContentid());
                        AttractionsDto fullInfoAttractionsDto = AttractionInfoEnhancer.addDetailInfo(attractionsDto, result);
                        attractionMapper.insertAttraction(fullInfoAttractionsDto);

                        responseDto.setAddr1(fullInfoAttractionsDto.getAddr1());
                        responseDto.setAddr2(fullInfoAttractionsDto.getAddr2());
                        responseDto.setAreaCode(fullInfoAttractionsDto.getAreaCode());
                        responseDto.setContentid(fullInfoAttractionsDto.getContentid());
                        responseDto.setContenttypeid(fullInfoAttractionsDto.getContenttypeid());
                        responseDto.setFirstimage(fullInfoAttractionsDto.getFirstimage());
                        responseDto.setSigunguCode(fullInfoAttractionsDto.getSigunguCode());
                        responseDto.setTitle(fullInfoAttractionsDto.getTitle());
                        response.add(responseDto);
                    }else {
                        responseDto.setAddr1(result.getAddr1());
                        responseDto.setAddr2(result.getAddr2());
                        responseDto.setAreaCode(result.getAreaCode());
                        responseDto.setContentid(result.getContentid());
                        responseDto.setContenttypeid(result.getContenttypeid());
                        responseDto.setFirstimage(result.getFirstimage());
                        responseDto.setSigunguCode(result.getSigunguCode());
                        responseDto.setTitle(result.getTitle());
                        response.add(responseDto);
                    }

                    break;
                case 14:
                    if(!attractionMapper.existsCulturalFacilities(result.getContentid())) {
                        CulturalFacilitiesDto culturalFacilitiesDto = tourApiGetter.getCulturalFacilitiesDetail(14, result.getContentid());
                        CulturalFacilitiesDto fullInfoCulturalFacilitiesDto = AttractionInfoEnhancer.addDetailInfo(culturalFacilitiesDto, result);
                        attractionMapper.insertCulturalFacility(fullInfoCulturalFacilitiesDto);
                        responseDto.setAddr1(fullInfoCulturalFacilitiesDto.getAddr1());
                        responseDto.setAddr2(fullInfoCulturalFacilitiesDto.getAddr2());
                        responseDto.setAreaCode(fullInfoCulturalFacilitiesDto.getAreaCode());
                        responseDto.setContentid(fullInfoCulturalFacilitiesDto.getContentid());
                        responseDto.setContenttypeid(fullInfoCulturalFacilitiesDto.getContenttypeid());
                        responseDto.setFirstimage(fullInfoCulturalFacilitiesDto.getFirstimage());
                        responseDto.setSigunguCode(fullInfoCulturalFacilitiesDto.getSigunguCode());
                        responseDto.setTitle(fullInfoCulturalFacilitiesDto.getTitle());
                        response.add(responseDto);
                    }else {
                        responseDto.setAddr1(result.getAddr1());
                        responseDto.setAddr2(result.getAddr2());
                        responseDto.setAreaCode(result.getAreaCode());
                        responseDto.setContentid(result.getContentid());
                        responseDto.setContenttypeid(result.getContenttypeid());
                        responseDto.setFirstimage(result.getFirstimage());
                        responseDto.setSigunguCode(result.getSigunguCode());
                        responseDto.setTitle(result.getTitle());
                        response.add(responseDto);
                    }
                    break;
                case 15:
                    if(!attractionMapper.existsFestivalEvents(result.getContentid())) {
                        FestivalEventsDto festivalEventsDto = tourApiGetter.getFestivalEventDetail(15, result.getContentid());
                        FestivalEventsDto fullInfoFestivalEventsDto = AttractionInfoEnhancer.addDetailInfo(festivalEventsDto, result);
                        attractionMapper.insertFestivalEvent(fullInfoFestivalEventsDto);
                        responseDto.setAddr1(fullInfoFestivalEventsDto.getAddr1());
                        responseDto.setAddr2(fullInfoFestivalEventsDto.getAddr2());
                        responseDto.setAreaCode(fullInfoFestivalEventsDto.getAreaCode());
                        responseDto.setContentid(fullInfoFestivalEventsDto.getContentid());
                        responseDto.setContenttypeid(fullInfoFestivalEventsDto.getContenttypeid());
                        responseDto.setFirstimage(fullInfoFestivalEventsDto.getFirstimage());
                        responseDto.setSigunguCode(fullInfoFestivalEventsDto.getSigunguCode());
                        responseDto.setTitle(fullInfoFestivalEventsDto.getTitle());
                        response.add(responseDto);
                    }else {
                        responseDto.setAddr1(result.getAddr1());
                        responseDto.setAddr2(result.getAddr2());
                        responseDto.setAreaCode(result.getAreaCode());
                        responseDto.setContentid(result.getContentid());
                        responseDto.setContenttypeid(result.getContenttypeid());
                        responseDto.setFirstimage(result.getFirstimage());
                        responseDto.setSigunguCode(result.getSigunguCode());
                        responseDto.setTitle(result.getTitle());
                        response.add(responseDto);
                    }
                    break;
            }
        }
        return response;
    }

    public List<FestivalEventsDto> getFestivalList(Map<String, Object> params) {
        List<TourApiFestivalResponseDto> results = tourApiGetter.getFestivalList(params);

        List<FestivalEventsDto> response = new ArrayList<>();
        for(TourApiFestivalResponseDto result : results) {
            if(!attractionMapper.existsFestivalEvents(result.getContentid())) {
                FestivalEventsDto festivalEventsDto = tourApiGetter.getFestivalEventDetail(15, result.getContentid());
                FestivalEventsDto fullInfoFestivalEventsDto = AttractionInfoEnhancer.addDetailInfo(festivalEventsDto, result);

                //overview 호출 후 저장
                String overview  = tourApiGetter.getFestivalOverview(result.getContentid());
                fullInfoFestivalEventsDto.setOverview(overview);


                attractionMapper.insertFestivalEvent(fullInfoFestivalEventsDto);
                response.add(fullInfoFestivalEventsDto);
            }else {
                response.add(attractionMapper.getFestivalEvents(result.getContentid()));
            }
        }
        return response;
    }
}
