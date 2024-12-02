package com.tripreader.festival.model.service;

import com.tripreader.attraction.model.dto.FestivalEventsDto;
import com.tripreader.festival.model.dto.FestivalListResponseDto;
import com.tripreader.festival.model.mapper.FestivalMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FestivalService {

    FestivalMapper festivalMapper;

    public FestivalService(FestivalMapper festivalMapper) {
        this.festivalMapper = festivalMapper;
    }

    public Map<String, Integer> getFestivalCount(String startDate, String endDate) {

        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 문자열을 LocalDate로 변환
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        Map<String, Integer> result = new HashMap<>();
        while (!start.isAfter(end)) {
            result.put(start.format(formatter), festivalMapper.getFestivalCount(start.format(formatter)));
            start = start.plusDays(1); // 다음 날짜로 이동
        }

        return result;

    }

    public FestivalEventsDto getFestivalDetail(int contentId) {
        return festivalMapper.getFestivalDetail(contentId);
    }


    public List<FestivalListResponseDto> getFestivalList(Map<String, Object> map) {
        // 페이지 번호와 한 페이지당 아이템 수 설정
        int page = (int) map.getOrDefault("page", 1); // 요청된 페이지 번호, 기본값은 1
        int itemsPerPage = 20; // 한 페이지당 아이템 수
        int offset = (page - 1) * itemsPerPage; // SQL에서 사용할 오프셋 계산

        // SQL 쿼리에서 사용할 페이징 정보 추가
        map.put("offset", offset);
        map.put("limit", itemsPerPage);

        // 데이터 조회
        List<FestivalListResponseDto> result = festivalMapper.getFestivalList(map);

        log.info("result : {}", result);
        if (result == null) {
            return new ArrayList<>();
        }

        return result;
    }
}
