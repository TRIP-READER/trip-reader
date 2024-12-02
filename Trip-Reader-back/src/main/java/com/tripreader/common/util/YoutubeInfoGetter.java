package com.tripreader.common.util;

import com.tripreader.news.model.dto.NewsDto;
import com.tripreader.news.model.dto.NewsVideoDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class YoutubeInfoGetter {
    private final String URL ="https://www.googleapis.com/youtube/v3/search";
    @Value("${youtube.api.key}")
    private String KEY;
    @Value("${naver.news.display-size}")
    public int DISPLAY_SIZE;

    public List<NewsVideoDto> getResult(String query){
        List<NewsVideoDto> ret = new LinkedList<>();
        try {
            String result = new HttpUtil().url(URL)
                    .queryString("key",KEY)
                    .queryString("part", "id")
                    .queryString("regionCode", "kr")
                    .queryString("order", "relevance")
                    .queryString("q", query + " -논란 -바가지")
                    .queryString("maxResults", Integer.toString(DISPLAY_SIZE))
                    .build();
            JSONObject jsonObject = new JSONObject(result);
//            System.out.println("jsonObject = " + jsonObject);
            if(!jsonObject.has("items"))
                return ret;

            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                NewsVideoDto newsVideoDto = new NewsVideoDto();
                JSONObject json = jsonArray.getJSONObject(i).getJSONObject("id");
                try {
                    newsVideoDto.setUrl(json.getString("videoId"));
                }catch (Exception e){
                    continue;
                }
                ret.add(newsVideoDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
