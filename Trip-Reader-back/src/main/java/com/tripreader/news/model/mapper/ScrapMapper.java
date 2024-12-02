package com.tripreader.news.model.mapper;

import com.tripreader.news.model.dto.NewsSummaryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface ScrapMapper {
    List<NewsSummaryDto>  getScrap(int memberKey);
    void insertScrap(Map<String,Object> map);
    void deleteScrap(Map<String,Object> map);
    int isScraped(Map<String,Object> map);
}
