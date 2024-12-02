package com.tripreader.main.controller;

import com.tripreader.news.model.service.NewsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/main")
public class MainController {

    NewsService newsService;

    MainController(NewsService newsService) {
        this.newsService = newsService;
    }

    //메인화면 기사를 가져오는 API
    @GetMapping()
    public ResponseEntity<?> getMain() {
        try {
            return ResponseEntity.ok().body(newsService.getMainNews());

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
