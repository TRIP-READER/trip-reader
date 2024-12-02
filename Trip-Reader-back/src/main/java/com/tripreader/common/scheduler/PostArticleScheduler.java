package com.tripreader.common.scheduler;

import com.tripreader.ai.service.AiService;
import com.tripreader.attraction.model.dto.FestivalEventsDto;
import com.tripreader.attraction.model.service.AttractionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PostArticleScheduler {
    private final AiService aiService;
    private final AttractionService attractionService;
    private final int POST_SIZE = 20;
    private int cnt = 1;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public PostArticleScheduler(AiService aiService, AttractionService attractionService) {
        this.aiService = aiService;
        this.attractionService = attractionService;
    }
//    @Scheduled(cron = "0 15 3 * * ?")
//    @Scheduled(fixedDelay = 20000)
    public void postArticle() {
        Map<String, Object> params = new HashMap<>();
        LocalDate today = LocalDate.now();

        params.put("numOfRows", POST_SIZE);
        params.put("pageNo", cnt++);
//        params.put("eventStartDate", today.format(formatter));
//        params.put("eventEndDate", today.plusDays(14).format(formatter));
        params.put("eventStartDate", "20241001");
        params.put("eventEndDate", "20241231");

        try {
            List<FestivalEventsDto> list = attractionService.getFestivalList(params);

            System.out.println("start");
            for(FestivalEventsDto festivalEventsDto : list)
                aiService.insertArticleSummary(festivalEventsDto);
            System.out.println("finish "+cnt +" page added : "+list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postAINews(int page) {
        try {
            List<FestivalEventsDto> list = attractionService.getAllFestivals(page);

            log.info("start "+page +"th page");
            int count = 0;
            for(FestivalEventsDto festivalEventsDto : list)
                if(aiService.insertArticleSummary(festivalEventsDto)) {
                    count++;
                    log.info("count : "+count+" title : "+festivalEventsDto.getTitle());
                }

            log.info("finish "+page +"th page");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
