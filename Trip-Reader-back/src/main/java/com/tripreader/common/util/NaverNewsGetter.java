package com.tripreader.common.util;

import com.tripreader.news.model.dto.NewsDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class NaverNewsGetter {
    private final String URL ="https://openapi.naver.com/v1/search/news.json";
    @Value("${naver.news.client-id}")
    private String ID;
    @Value("${naver.news.client-secret}")
    private String SECRET;
    @Value("${naver.news.display-size}")
    public int DISPLAY_SIZE;

    public List<NewsDto> getResult(String query){
        List<NewsDto> ret = new LinkedList<>();
        try {
            String result = new HttpUtil().url(URL)
                    .header("X-Naver-Client-Id",ID)
                    .header("X-Naver-Client-Secret",SECRET)
                    .queryString("query", query)
                    .queryString("display", Integer.toString(DISPLAY_SIZE))
                    .build();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            String queryTmp = query.replaceAll(" ","");
            for (int i = 0; i < jsonArray.length(); i++) {
                NewsDto newsDto = new NewsDto();
                JSONObject json = jsonArray.getJSONObject(i);
                newsDto.setTitle(json.getString("title").replaceAll("<b>","").replaceAll("</b>",""));
                newsDto.setUrl(json.getString("originallink").replaceAll("<b>","").replaceAll("</b>",""));
                newsDto.setContent(json.getString("description"));

                String titleTmp = newsDto.getTitle().replaceAll(" ","");
                String contentTmp = newsDto.getContent().replaceAll(" ","");
//                System.out.println("titleTmp = " + titleTmp+" "+!titleTmp.contains(queryTmp));
//                System.out.println("contentTmp = " + contentTmp+" "+!contentTmp.contains(queryTmp));
                if(titleTmp.contains(queryTmp) || contentTmp.contains(queryTmp)) {
                    ret.add(newsDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
