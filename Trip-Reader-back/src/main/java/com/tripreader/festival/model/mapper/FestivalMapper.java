package com.tripreader.festival.model.mapper;

import com.tripreader.attraction.model.dto.FestivalEventsDto;
import com.tripreader.festival.model.dto.FestivalListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface FestivalMapper {

    int getFestivalCount(String date);
    FestivalEventsDto getFestivalDetail(int contentId);

    List<FestivalListResponseDto> getFestivalList(Map<String, Object> map);
}
