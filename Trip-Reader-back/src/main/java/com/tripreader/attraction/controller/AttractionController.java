package com.tripreader.attraction.controller;


import com.tripreader.attraction.model.service.AttractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/attraction")
public class AttractionController {

    AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }


    @GetMapping("/detail")
    ResponseEntity<?> getAttractionInfoWithId(@RequestParam(value = "contenttypeid") int contenttypeid,
                                              @RequestParam(value = "contentid") int contentid) {

        try {
            return ResponseEntity.ok(attractionService.getAttractionInfoWithId(contenttypeid, contentid));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAttractionList(@RequestParam(value = "row") int row,
                                               @RequestParam(value = "page") int page,
                                               @RequestParam(value = "sido", required = false) String sido,
                                               @RequestParam(value = "area", required = false) String area,
                                               @RequestParam(value = "query", required = false) String query) {
        try {
            // 필수 파라미터 검사
            if (row <= 0 || page <= 0) {
                return ResponseEntity.badRequest().body("Row와 Page는 필수 값이며 0보다 커야 합니다.");
            }

            // area가 있을 때 sido도 반드시 있어야 함
            if (sido != null && area == null) {
                return ResponseEntity.badRequest().body("Sido를 사용할 경우 Area는 필수값입니다.");
            }

            // 통합 검색
            Map<String, Object> params = new HashMap<>();
            if(query != null && !query.isEmpty()) {
                params.put("keyword", query);
            }
            if(area != null && !area.isEmpty()) {
                params.put("areaCode", area);
            }
            if(sido != null && !sido.isEmpty()) {
                params.put("sigunguCode", sido);
            }
            params.put("numOfRows", row);
            params.put("pageNo", page);
            return ResponseEntity.ok().body(attractionService.CommonSearch(params));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    @GetMapping("/festival")
    public ResponseEntity<?> getFestivalList(@RequestParam(value = "row") int row,
                                             @RequestParam(value = "page") int page,
                                             @RequestParam(value = "startdate") int startdate,
                                             @RequestParam(value = "enddate", required = false) int enddate) {
        Map<String, Object> params = new HashMap<>();
        params.put("numOfRows", row);
        params.put("pageNo", page);
        params.put("eventStartDate", startdate);
        if (enddate != 0) {
            params.put("eventEndDate", enddate);
        }

        try {
            return ResponseEntity.ok(attractionService.getFestivalList(params));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
