package com.tripreader.news.model.mapper;

import com.tripreader.news.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NewsMapper {
    void insertNews(List<NewsDto> newsDto);
    void insertNewsVideo(List<NewsVideoDto> newsVideoDto);
    void insertNewsSummary(NewsSummaryDto newsSummaryDto);

    int getNewsSummaryCnt(Map<String, Integer> contentInfo);

    List<NewsSummaryDto> getNewsSummaryListByAddr(Map<String, Integer> addrInfo);
    List<NewsSummaryDto> getNewsSummaryList();

    NewsSummaryDto getNewsSummaryByContentInfo(Map<String, Integer> contentInfo);
    NewsSummaryDto getNewsSummaryById(int newsSummaryKey);
    NewsSummaryDto getNewsSummaryByNano(String newsSummaryNano);

    List<NewsDto> getNewsDtoBySummaryKey(int newsSummaryKey);

    int getNewsSummaryKeyByNewsSummaryNano(String newsSummaryNano);

    List<NewsSummaryDto> getNewsSummaryPreviewList(Map<String, Object> params);

    void updateScrapCnt(Map<String, Object> map);

    void updateDarkTitle(Map<String, Object> map);
    void updateNewsSummary(NewsSummaryDto newsSummaryDto);

    int getScrapCount(int newsSummaryKey);
    List<NewsSummaryDetailVideoDto> getNewsVideo(int newsSummaryKey);

    List<NewsSummaryDetailNewsInfoDto> getNewsInfo(int newsSummaryKey);

    void insertMainNews(List<NewsSummaryMainDto> newsSummaryMainDtoList);

    List<NewsSummaryMainResponseDto> getMainNews();
}
