package com.tripreader.notice.model.dto;

import lombok.Data;

@Data
public class NoticeResponseDto {
    private String noticeNano;
    private String title;
    private String content;
    private String image;
    private String prettyTime;
}
