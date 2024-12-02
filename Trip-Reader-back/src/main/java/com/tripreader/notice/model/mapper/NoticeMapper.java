package com.tripreader.notice.model.mapper;

import com.tripreader.notice.model.dto.NoticeResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {
    public void addNotice(Map<String,Object> request);

    void updateNotice(Map<String, Object> request);

    void deleteNotice(String noticeNano);

    NoticeResponseDto getNotice(String noticeNano);

    List<NoticeResponseDto> getNoticeList(int pageno);
}
