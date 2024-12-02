package com.tripreader.news.model.service;

import com.tripreader.common.util.ContentGetter;
import com.tripreader.common.util.PrettyTimeGetter;
import com.tripreader.news.model.dto.ContentDto;
import com.tripreader.news.model.dto.NewsSummaryDto;
import com.tripreader.news.model.dto.NewsSummaryPreviewResponseDto;
import com.tripreader.news.model.mapper.NewsMapper;
import com.tripreader.news.model.mapper.ScrapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScrapService {
    @Autowired
    ScrapMapper scrapMapper;

    @Autowired
    NewsMapper newsMapper;

    //맴버 키를 이용하여 스크랩한 뉴스를 가져오는 메소드
    public List<NewsSummaryPreviewResponseDto> getScrap(int memberKey) throws ParseException {
        List<NewsSummaryDto> newsSummaryDtoList = scrapMapper.getScrap(memberKey);
        List<NewsSummaryPreviewResponseDto> newsSummaryPreviewResponseDtoList = new ArrayList<>();

        for(NewsSummaryDto newsSummaryDto : newsSummaryDtoList){
            NewsSummaryPreviewResponseDto newsSummaryPreviewResponseDto = new NewsSummaryPreviewResponseDto();
            newsSummaryPreviewResponseDto.setNewsSummaryNano(newsSummaryDto.getNewsSummaryNano());
            newsSummaryPreviewResponseDto.setTitle(newsSummaryDto.getTitle());
            newsSummaryPreviewResponseDto.setImage(newsSummaryDto.getImage());

            ContentDto contentDto = ContentGetter.getContent(newsSummaryDto.getContent());

            newsSummaryPreviewResponseDto.setHeader(contentDto.getHeader());
            newsSummaryPreviewResponseDto.setContent(contentDto.getContent());

            newsSummaryPreviewResponseDto.setScrapCount(newsSummaryDto.getScrapCount());
            // PrettyTime을 사용하여 차이 계산
            // 시간 변환
            String prettyTimeResult = PrettyTimeGetter.getPrettyTime(newsSummaryDto.getCreatedTime());

            newsSummaryPreviewResponseDto.setPrettyTime(prettyTimeResult);
            newsSummaryPreviewResponseDtoList.add(newsSummaryPreviewResponseDto);
        }
        return newsSummaryPreviewResponseDtoList;
    }

    //멤버 키를 이용하여 스크랩 여부를 확인하는 메소드
    public int isScraped(int memberKey, int newsKey) {
        Map<String, Object> map = Map.of("memberKey", memberKey, "newsSummaryKey", newsKey);
        return scrapMapper.isScraped(map);
    }

    //스크랩 상태를 변경하고 스크랩한 뉴스의 스크랩 수를 변경하는 메소드
    public boolean changeScrap(Map<String, Object> map) {
        boolean wantStatus = Boolean.parseBoolean(map.get("i_scrap_this").toString());

        //원하는 상태가 스크랩일때
        if(wantStatus) {
            //스크랩 한적이 없다면
            if(scrapMapper.isScraped(map)==0){
                scrapMapper.insertScrap(map);
                map.put("scrapCountChange", 1);
                newsMapper.updateScrapCnt(map);
                return true;
            }else {
                return true;
            }
        }else {
            //원하는 상태가 스크랩 해제일때
            if(scrapMapper.isScraped(map) == 0){
                return false;
            }else {
                scrapMapper.deleteScrap(map);
                map.put("scrapCountChange", -1);
                newsMapper.updateScrapCnt(map);
                return false;
            }
        }
    }
}
