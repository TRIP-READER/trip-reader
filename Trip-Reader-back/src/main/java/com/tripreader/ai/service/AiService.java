package com.tripreader.ai.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.tripreader.ai.util.ChatUtil;
import com.tripreader.attraction.model.dto.FestivalEventsDto;
import com.tripreader.common.util.NaverNewsGetter;
import com.tripreader.common.util.YoutubeInfoGetter;
import com.tripreader.festival.model.service.FestivalService;
import com.tripreader.news.model.dto.NewsDto;
import com.tripreader.news.model.dto.NewsSummaryDto;
import com.tripreader.news.model.dto.NewsVideoDto;
import com.tripreader.news.model.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class AiService {
    private final ChatUtil chatUtil;
    private final NaverNewsGetter naverNewsGetter;
    private final NewsService newsService;
    private final FestivalService festivalService;
    private final YoutubeInfoGetter youtubeInfoGetter;

    private final String SUMMARY_CURATOR_SYSTEM = "prompt-system-trip-curator";
    private final String SUMMARY_WRITE_USER = "prompt-user-write-article";
    private final String DARK_TITLE_CURATOR_SYSTEM = "prompt-system-trip-dark-curator";
    private final String DARK_TITLE_WRITE_USER = "prompt-user-write-dark-title";
    private final String ATTRACTION_TEMPLATE =
"""
%s : {%s}
""";
    private final String ARTICLE_TAMPLATE =
"""
# 기사 {article%d}
title: {articleTitle%d}
content: {articleContent%d}
""";

    private boolean isInitialized = false;

    public AiService(ChatUtil chatUtil, NaverNewsGetter naverNewsGetter, NewsService newsService, YoutubeInfoGetter youtubeInfoGetter, FestivalService festivalService) {
        this.chatUtil = chatUtil;
        this.naverNewsGetter = naverNewsGetter;
        this.newsService = newsService;
        this.youtubeInfoGetter = youtubeInfoGetter;
        this.festivalService = festivalService;
    }

    private void initialize() {
        isInitialized = true;
        chatUtil.addPromptLoader("SUMMARY", SUMMARY_CURATOR_SYSTEM, SUMMARY_WRITE_USER);
        chatUtil.addPromptLoader("DARK_TITLE", DARK_TITLE_CURATOR_SYSTEM, DARK_TITLE_WRITE_USER);
    }
    public void updateNewsSummaryDarkTitle(List<Integer> newsSummaryKeyList) {
        if (!isInitialized) initialize();
        for (int newsSummaryKey : newsSummaryKeyList) {
            NewsSummaryDto newsSummaryDto = newsService.getNewsSummaryById(newsSummaryKey);
            String darkTitle = getDarkTitle(newsSummaryDto.getContent());
            Map<String, Object> map = new HashMap<>();
            map.put("newsSummaryKey", newsSummaryKey);
            map.put("darkTitle", darkTitle);
            newsService.updateNewsSummaryDarkTitle(map);
        }
    }

    public void updateNewsSummary(List<Integer> keyList){
        if (!isInitialized) initialize();
        for (int newsSummaryKey : keyList) {
            NewsSummaryDto newsSummaryDto = newsService.getNewsSummaryById(newsSummaryKey);
            FestivalEventsDto festivalEventsDto = festivalService.getFestivalDetail(newsSummaryDto.getContentid());


            Map<String, Object> userParams = new HashMap<>();
            Map<String, Object> systemParms = new HashMap<>();

            userParams.put("addr1", festivalEventsDto.getAddr1());
            userParams.put("addr2", festivalEventsDto.getAddr2());

            userParams.put("eventplace", festivalEventsDto.getEventplace());
            userParams.put("fee", festivalEventsDto.getUsetimefestival());
            userParams.put("startDate", festivalEventsDto.getEventstartdate());
            userParams.put("endDate", festivalEventsDto.getEventenddate());
            userParams.put("operatingHours", festivalEventsDto.getPlaytime());
            userParams.put("organizer", festivalEventsDto.getSponsor1());

            StringBuilder userPromptAdder = new StringBuilder();

            for (Map.Entry<String, Object> entry : userParams.entrySet())
                userPromptAdder.append(String.format(ATTRACTION_TEMPLATE, entry.getKey(), entry.getKey()));
            userParams.put("title", festivalEventsDto.getTitle());
            userParams.put("overview", festivalEventsDto.getOverview());

            String summary = chatUtil.runPrompt("SUMMARY", userParams, userPromptAdder.toString(), systemParms, "");

            newsSummaryDto.setContent(summary);

            newsService.updateNewsSummary(newsSummaryDto);
        }
    }

    public boolean insertArticleSummary(FestivalEventsDto festivalEventsDto) {
        if (!isInitialized) initialize();

        Map<String, Integer> map = new HashMap<>();
        map.put("contentid", festivalEventsDto.getContentid());
        map.put("contenttype",festivalEventsDto.getContenttypeid());
        if(newsService.getNewsSummaryCnt(map) != 0)
            return false;

        List<NewsDto> newsDtoList = naverNewsGetter.getResult(festivalEventsDto.getTitle());
//        List<NewsVideoDto> videoDtoList = youtubeInfoGetter.getResult(festivalEventsDto.getTitle());
        if(newsDtoList.isEmpty() )//|| videoDtoList.isEmpty())
            return false;
        Map<String, Object> userParams = new HashMap<>();
        Map<String, Object> systemParms = new HashMap<>();

        userParams.put("addr1", festivalEventsDto.getAddr1());
        userParams.put("addr2", festivalEventsDto.getAddr2());

        userParams.put("eventplace", festivalEventsDto.getEventplace());
        userParams.put("fee", festivalEventsDto.getUsetimefestival());
        userParams.put("startDate", festivalEventsDto.getEventstartdate());
        userParams.put("endDate", festivalEventsDto.getEventenddate());
        userParams.put("operatingHours", festivalEventsDto.getPlaytime());
        userParams.put("organizer", festivalEventsDto.getSponsor1());

        StringBuilder userPromptAdder = new StringBuilder();

        for (Map.Entry<String, Object> entry : userParams.entrySet())
            userPromptAdder.append(String.format(ATTRACTION_TEMPLATE, entry.getKey(), entry.getKey()));
        userParams.put("title", festivalEventsDto.getTitle());
        userParams.put("overview", festivalEventsDto.getOverview());
        for (int i = 0; i < newsDtoList.size(); i++) {
            userPromptAdder.append(String.format(ARTICLE_TAMPLATE, i, i, i));
            userParams.put("article" + i, i);
            userParams.put("articleTitle" + i, newsDtoList.get(i).getTitle());
            userParams.put("articleContent" + i, newsDtoList.get(i).getContent());
        }

        String summary = chatUtil.runPrompt("SUMMARY", userParams, userPromptAdder.toString(), systemParms, "");

        NewsSummaryDto newsSummaryDto = new NewsSummaryDto();
        newsSummaryDto.setTitle(festivalEventsDto.getTitle());
        newsSummaryDto.setContentid(festivalEventsDto.getContentid());
        newsSummaryDto.setContenttypeid(festivalEventsDto.getContenttypeid());
        newsSummaryDto.setContent(summary);
        newsSummaryDto.setNewsSummaryNano(NanoIdUtils.randomNanoId());
        //추가된 부분

        newsSummaryDto.setImage(festivalEventsDto.getFirstimage());
        newsSummaryDto.setScrapCount(0);
        newsSummaryDto.setAreaCode(festivalEventsDto.getAreaCode());
        newsSummaryDto.setSigunguCode(festivalEventsDto.getSigunguCode());
        newsSummaryDto.setNewsSummaryNano(NanoIdUtils.randomNanoId());
        newsSummaryDto.setDarkTitle(getDarkTitle(summary));
        newsService.insertNewsSummary(newsSummaryDto);
        int summaryKey = newsSummaryDto.getNewsSummaryKey();

        for(NewsDto newsDto : newsDtoList)
            newsDto.setNewsSummaryKey(summaryKey);
//        for(NewsVideoDto newsVideoDto : videoDtoList)
//            newsVideoDto.setNewsSummaryKey(summaryKey);
        if(!newsDtoList.isEmpty())
            newsService.insertNews(newsDtoList);
//        if(!videoDtoList.isEmpty())
//            newsService.insertNewsVideo(videoDtoList);
        return true;
    }

    public String getDarkTitle(String content){
        return chatUtil.runPrompt("DARK_TITLE", new HashMap<String,Object>(), content, new HashMap<String,Object>(), "").replaceAll("\"","");
    }
}
