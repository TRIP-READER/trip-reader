package com.tripreader.ai;

import com.tripreader.ai.service.AiService;
import com.tripreader.attraction.model.dto.FestivalEventsDto;
import com.tripreader.common.scheduler.PostArticleScheduler;
import com.tripreader.member.model.dto.MemberDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aitest")
@Tag(name = "ai 테스트 컨트롤러", description = "ai 관련 클래스. 추후 삭제 예정")
public class AiController {
    private final AiService aiService;
    private PostArticleScheduler postArticleScheduler;
    public AiController(AiService aiService, PostArticleScheduler postArticleScheduler) {
        this.aiService = aiService;
        this.postArticleScheduler = postArticleScheduler;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> insert(@RequestBody @Parameter(description = "검색할 attraction 정보", required = true) FestivalEventsDto festivalEventsDto) {
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            aiService.insertArticleSummary(festivalEventsDto);
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(status);
    }
    @GetMapping("/start")
    public void startSummary(@RequestParam(name = "page") int page) {
        postArticleScheduler.postAINews(page);
    }

    @PostMapping("/updateSummaryDarkTitle")
    public void updateSummaryDarkTitle(@RequestBody List<Integer> keys) {
        aiService.updateNewsSummaryDarkTitle(keys);
    }

    @PostMapping("/updateSummary")
    public void updateSummary(@RequestBody List<Integer> keys) {
        aiService.updateNewsSummary(keys);
    }
}
