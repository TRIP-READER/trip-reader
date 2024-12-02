package com.tripreader.news.model.service;

import com.tripreader.attraction.model.dto.FestivalEventsDto;
import com.tripreader.attraction.model.mapper.AttractionMapper;
import com.tripreader.common.util.ContentGetter;
import com.tripreader.common.util.PrettyTimeGetter;
import com.tripreader.news.model.dto.*;
import com.tripreader.news.model.mapper.NewsMapper;

import lombok.extern.slf4j.Slf4j;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class NewsService {
    NewsMapper newsMapper;
    AttractionMapper attractionMapper;

    public NewsService(AttractionMapper attractionMapper, NewsMapper newsMapper) {
        this.attractionMapper = attractionMapper;
        this.newsMapper = newsMapper;
    }

    public void updateNewsSummaryDarkTitle(Map<String,Object> map) {
        newsMapper.updateDarkTitle(map);
    }
    public void insertNews(List<NewsDto> newsDto){
        newsMapper.insertNews(newsDto);
    }
    public void insertNewsVideo(List<NewsVideoDto> newsVideoDto){
        newsMapper.insertNewsVideo(newsVideoDto);
    }
    public void insertNewsSummary(NewsSummaryDto newsSummaryDto){
        newsMapper.insertNewsSummary(newsSummaryDto);
    }

    public int getNewsSummaryCnt(Map<String, Integer> contentInfo){
        return newsMapper.getNewsSummaryCnt(contentInfo);
    }

    public List<NewsSummaryDto> getNewsSummaryListByAddr(Map<String, Integer> addrInfo){
        return newsMapper.getNewsSummaryListByAddr(addrInfo);
    }

    public List<NewsSummaryDto> getNewsSummaryList(){
        return newsMapper.getNewsSummaryList();
    }

    public NewsSummaryDto getNewsSummaryByContentInfo(Map<String, Integer> contentInfo){
        return newsMapper.getNewsSummaryByContentInfo(contentInfo);
    }

    public NewsSummaryDto getNewsSummaryById(int newsSummaryKey){
        return newsMapper.getNewsSummaryById(newsSummaryKey);
    }

    public NewsSummaryDetailResponseDto getNewsSummaryByNano(String newsSummaryNano)  throws ParseException {
        NewsSummaryDto newsSummaryDto =  newsMapper.getNewsSummaryByNano(newsSummaryNano);
        NewsSummaryDetailResponseDto newsSummaryDetailResponseDto = new NewsSummaryDetailResponseDto();

        newsSummaryDetailResponseDto.setNewsSummaryNano(newsSummaryDto.getNewsSummaryNano());
        newsSummaryDetailResponseDto.setTitle(newsSummaryDto.getTitle());
        newsSummaryDetailResponseDto.setImage(newsSummaryDto.getImage());

        MarkerDto mainMarker = new MarkerDto();

        FestivalEventsDto festivalEventsDto =  attractionMapper.getFestivalEvents(newsSummaryDto.getContentid());
        mainMarker.setKey(0);
        mainMarker.setTitle(newsSummaryDto.getTitle());
        mainMarker.setMapy(festivalEventsDto.getMapy());
        mainMarker.setMapx(festivalEventsDto.getMapx());
        newsSummaryDetailResponseDto.setMainMarker(mainMarker);
        newsSummaryDetailResponseDto.setContentId(festivalEventsDto.getContentid());

        newsSummaryDetailResponseDto.setContentList(ContentGetter.getList(newsSummaryDto.getContent()));
        newsSummaryDetailResponseDto.setScrapCount(newsSummaryDto.getScrapCount());

        newsSummaryDetailResponseDto.setNewsList(newsMapper.getNewsInfo(newsSummaryDto.getNewsSummaryKey()));
        List<NewsSummaryDetailNewsInfoDto> newsList =  newsSummaryDetailResponseDto.getNewsList();
        for (int i = 0; i < newsList.size(); i++) {
            NewsSummaryDetailNewsInfoDto newsInfo = newsList.get(i);
            newsInfo.setId(i+1);
        }

        newsSummaryDetailResponseDto.setVideoList(newsMapper.getNewsVideo(newsSummaryDto.getNewsSummaryKey()));
        // videoList의 id 설정
        List<NewsSummaryDetailVideoDto> videoList = newsSummaryDetailResponseDto.getVideoList();
        for (int i = 0; i < videoList.size(); i++) {
            NewsSummaryDetailVideoDto video = videoList.get(i);
            video.setId(i+1);
        }

        String prettyTimeResult = PrettyTimeGetter.getPrettyTime(newsSummaryDto.getCreatedTime());
        newsSummaryDetailResponseDto.setPrettyTime(prettyTimeResult);


        return newsSummaryDetailResponseDto;
    }


    public List<NewsDto> getNewsDtoBySummaryKey(int newsSummaryKey) {
        return newsMapper.getNewsDtoBySummaryKey(newsSummaryKey);
    }

    public int getNewsSummaryKeyByNewsSummaryNano(String newsSummaryNano){
        return newsMapper.getNewsSummaryKeyByNewsSummaryNano(newsSummaryNano);
    }

    public List<NewsSummaryPreviewResponseDto> getNewsSummaryPreviewList(Map<String, Object> params) throws ParseException {
        log.info(params.toString());

        int page = (int) params.getOrDefault("pageno", 1); // 요청된 페이지 번호, 기본값은 1
        int itemsPerPage = 20; // 한 페이지당 아이템 수
        int offset = (page - 1) * itemsPerPage; // SQL에서 사용할 오프셋 계산

        // SQL 쿼리에서 사용할 페이징 정보 추가
        params.put("offset", offset);
        params.put("limit", itemsPerPage);


        List<NewsSummaryDto> newsSummaryDtoList = newsMapper.getNewsSummaryPreviewList(params);

        List<NewsSummaryPreviewResponseDto> newsSummaryPreviewResponseDtoList = new ArrayList<>();

        for(NewsSummaryDto newsSummaryDto : newsSummaryDtoList){
            NewsSummaryPreviewResponseDto newsSummaryPreviewResponseDto = new NewsSummaryPreviewResponseDto();
            newsSummaryPreviewResponseDto.setNewsSummaryNano(newsSummaryDto.getNewsSummaryNano());
            newsSummaryPreviewResponseDto.setTitle(newsSummaryDto.getTitle());
            newsSummaryPreviewResponseDto.setImage(newsSummaryDto.getImage());

            ContentDto contentDto = ContentGetter.getContent(newsSummaryDto.getContent());

            newsSummaryPreviewResponseDto.setHeader(contentDto.getHeader());
            newsSummaryPreviewResponseDto.setContent(contentDto.getContent());
            newsSummaryPreviewResponseDto.setDarkTitle(newsSummaryDto.getDarkTitle());

            newsSummaryPreviewResponseDto.setScrapCount(newsSummaryDto.getScrapCount());
            // PrettyTime을 사용하여 차이 계산
            // 시간 변환
            String prettyTimeResult = PrettyTimeGetter.getPrettyTime(newsSummaryDto.getCreatedTime());

            newsSummaryPreviewResponseDto.setPrettyTime(prettyTimeResult);
            newsSummaryPreviewResponseDtoList.add(newsSummaryPreviewResponseDto);
        }

        return newsSummaryPreviewResponseDtoList;
    }

    public int getScrapCount(int newsSummaryKey) {
        return newsMapper.getScrapCount(newsSummaryKey);
    }

    public void setMainNews(String mainNano, List<String> leftNano, List<String> rightNano) throws Exception{
        List<NewsSummaryMainDto> newsSummaryMainDtoList = new ArrayList<>();
        newsSummaryMainDtoList.add(new NewsSummaryMainDto(mainNano, "MAIN", 1));

        for(int i = 0; i < leftNano.size(); i++){
            newsSummaryMainDtoList.add(new NewsSummaryMainDto(leftNano.get(i), "LEFT", i+1));
        }

        for(int i = 0; i < rightNano.size(); i++){
            newsSummaryMainDtoList.add(new NewsSummaryMainDto(rightNano.get(i), "RIGHT", i+1));
        }
        log.info("newsSummaryMainDtoList : " + newsSummaryMainDtoList);
        newsMapper.insertMainNews(newsSummaryMainDtoList);
    }

    public void updateNewsSummary(NewsSummaryDto newsSummaryDto) {
        newsMapper.updateNewsSummary(newsSummaryDto);
    }

    //메인 페이지에 노출될 뉴스를 가져오는 메서드
    public List<NewsSummaryMainResponseDto> getMainNews() throws Exception {

        return newsMapper.getMainNews();
    }
}

