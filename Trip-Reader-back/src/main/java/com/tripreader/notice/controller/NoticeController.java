package com.tripreader.notice.controller;


import com.tripreader.notice.model.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notice")
@Slf4j
public class NoticeController {

    NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping()
    public ResponseEntity<?> addNotice(@RequestBody Map<String, Object> request) {
        try {
            if (!haveAllParams(request, "title", "content", "image")) {
                throw new IllegalArgumentException("title, content, image 는 필수값입니다.");
            }
            noticeService.addNotice(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list")
    public ResponseEntity<?> addNoticeList(@RequestBody List<Map<String, Object>> request) {
        try {
            noticeService.addNoticeList(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{noticeNano}")
    public ResponseEntity<?> updateNotice(@RequestBody Map<String, Object> request, @PathVariable(name = "noticeNano", required = true) String noticeNano) {
        if (!haveAllParams(request, "title", "content", "image")) {
            throw new IllegalArgumentException("noticeNano, title, content, image 는 필수값입니다.");
        }
        request.put("noticeNano", noticeNano);
        noticeService.updateNotice(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{noticeNano}")
    public ResponseEntity<?> deleteNotice(@PathVariable(name = "noticeNano", required = true) String noticeNano) {

        noticeService.deleteNotice(noticeNano);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{noticeNano}")
    public ResponseEntity<?> getNotice(@PathVariable(name = "noticeNano", required = true) String noticeNano) {
        try {
            return ResponseEntity.ok().body(noticeService.getNotice(noticeNano));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getNoticeList(@RequestParam(name = "pageno", required = false, defaultValue = "1") int pageno) {

        try {
            return ResponseEntity.ok().body(noticeService.getNoticeList(pageno-1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean haveAllParams(Map<String, ?> params, String... chkParam){
        for(String value : chkParam){
            if(!params.containsKey(value))
                return false;
        }
        return true;
    }
}
