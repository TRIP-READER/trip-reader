package com.tripreader.notice.model.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.tripreader.common.util.PrettyTimeGenerator;
import com.tripreader.notice.model.dto.NoticeResponseDto;
import com.tripreader.notice.model.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    NoticeMapper noticeMapper;
    PrettyTimeGenerator prettyTimeGenerator;

    public NoticeService(NoticeMapper noticeMapper, PrettyTimeGenerator prettyTimeGenerator) {
        this.noticeMapper = noticeMapper;
        this.prettyTimeGenerator = prettyTimeGenerator;
    }

    public void addNotice(Map<String,Object> request) throws Exception {
        String noticeNano = NanoIdUtils.randomNanoId();
        request.put("noticeNano", noticeNano);
        noticeMapper.addNotice(request);
    }

    public void updateNotice(Map<String,Object> request) {
        noticeMapper.updateNotice(request);
    }

    public void deleteNotice(String noticeNano) {
        noticeMapper.deleteNotice(noticeNano);
    }

    public NoticeResponseDto getNotice(String noticeNano) throws Exception {
        NoticeResponseDto result = noticeMapper.getNotice(noticeNano);
        result.setPrettyTime(prettyTimeGenerator.getPrettyTimeResult(result.getPrettyTime()));
        return result;
    }

    public List<NoticeResponseDto> getNoticeList(int pageno) throws Exception {
        List<NoticeResponseDto> result = noticeMapper.getNoticeList(pageno);

        for (NoticeResponseDto noticeResponseDto : result) {
            noticeResponseDto.setPrettyTime(prettyTimeGenerator.getPrettyTimeResult(noticeResponseDto.getPrettyTime()));
        }
        return result;
    }

    public void addNoticeList(List<Map<String, Object>> request) {

        for (Map<String, Object> map : request) {
            String noticeNano = NanoIdUtils.randomNanoId();
            map.put("noticeNano", noticeNano);
            noticeMapper.addNotice(map);
        }

    }
}
