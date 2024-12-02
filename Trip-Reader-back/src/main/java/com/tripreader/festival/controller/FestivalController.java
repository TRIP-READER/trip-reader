package com.tripreader.festival.controller;

import com.tripreader.festival.model.dto.FestivalListResponseDto;
import com.tripreader.festival.model.mapper.FestivalMapper;
import com.tripreader.festival.model.service.FestivalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/festival")
public class FestivalController {

    FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @GetMapping("/count")
    public ResponseEntity<?> getFestivalCount(@RequestParam(name = "startdate") String startDate,
                                       @RequestParam(name = "enddate")  String endDate) {


        Map<String, Integer> result = festivalService.getFestivalCount(startDate, endDate);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/{contentid}")
    public ResponseEntity<?> getFestivalDetail(@PathVariable(name = "contentid") int contentId) {
        try{
            return ResponseEntity.ok(festivalService.getFestivalDetail(contentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("해당 축제 정보가 없습니다.");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getFestivalList(@RequestParam(name = "date") String date,
                                             @RequestParam(name = "page") int page,
                                             @RequestParam(name = "area", required = false) String areaCode,
                                             @RequestParam(name = "sigungu", required = false) String sigunguCode) {

        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("page", page);
        map.put("areaCode", areaCode);
        if(sigunguCode != null) {
            map.put("sigunguCode", sigunguCode);
        }else {
            map.put("sigunguCode", 0);
        }
        List<FestivalListResponseDto> list = festivalService.getFestivalList(map);
        return ResponseEntity.ok(list);
    }
}
