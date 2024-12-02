package com.tripreader.news.controller;

import com.tripreader.news.model.dto.NewsSummaryPreviewResponseDto;
import com.tripreader.news.model.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
@Slf4j
@Tag(name = "여행 기사 컨트롤러", description = "뉴스 기사를 처리하는 클래스.")
public class NewsController {
    private final NewsService newsService;


    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }
    @Operation(summary = "전체 뉴스요약 가져오기", description = "전체 요약 뉴스를 가져옵니다.")
    @GetMapping("/debug")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            resultMap.put("result", newsService.getNewsSummaryList());
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

//    @Operation(summary = "지역으로 필터링해서 뉴스요약 가져오기", description = "지역코드, 시도코드로 요약 뉴스를 가져옵니다.")
//    @GetMapping("/{area_code}/{sigugun_code}")
//    public ResponseEntity<Map<String, Object>> getSummaryByAddr(@PathVariable(name = "area_code", required = true) String areaCode,
//                                                                @PathVariable(name = "sigugun_code", required = false) String sigugunCode) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        HttpStatus status = HttpStatus.ACCEPTED;
//        try {
//            Map<String, Integer> addrInfo = new HashMap<>();
//
//            addrInfo.put("sidoCode", Integer.parseInt(areaCode));
//            if (sigugunCode != null && sigugunCode.length() > 0)
//                addrInfo.put("gugunCode", Integer.parseInt(sigugunCode));
//            resultMap.put("result", newsService.getNewsSummaryListByAddr(addrInfo));
//        } catch (Exception e) {
//            e.printStackTrace();
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }

    @Operation(summary = "뉴스 요약 아이디로 뉴스요약 가져오기", description = "뉴스 요약 아이디로 뉴스요약 가져옵니다.")
    @GetMapping("/{news_summary_nano}")
    public ResponseEntity<?> getSummaryById(@PathVariable(name = "news_summary_nano", required = true) String newsSummaryNano) {
        try {
            return ResponseEntity.ok().body(newsService.getNewsSummaryByNano(newsSummaryNano));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getSummaryPreviewList(@RequestParam Map<String, Object> params) {
        log.info(params.toString());
        Map<String, Object> paramsMap = new HashMap<>();
        if(!haveAllParams(params, "sorting", "area_code", "pageno")){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
        String sorting = (String) params.get("sorting");
        if(!(sorting.equals("like") || sorting.equals("new"))){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }else {
            paramsMap.put("sorting", sorting);

        }
        if(!params.containsKey("sigungu_code")){
            paramsMap.put("sigungu_code", 0);
        }else {
            paramsMap.put("sigungu_code", Integer.parseInt((String) params.get("sigungu_code")));
        }
        paramsMap.put("area_code", Integer.parseInt((String) params.get("area_code")));
        paramsMap.put("pageno", (Integer.parseInt((String) params.get("pageno"))));


        List<NewsSummaryPreviewResponseDto> result = null;
        try {
            result = newsService.getNewsSummaryPreviewList(paramsMap);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 필요한 parameter들이 다 있다면 true
    private boolean haveAllParams(Map<String, ?>params, String... chkParam){
        for(String value : chkParam){
            if(!params.containsKey(value))
                return false;
        }
        return true;
    }

}
