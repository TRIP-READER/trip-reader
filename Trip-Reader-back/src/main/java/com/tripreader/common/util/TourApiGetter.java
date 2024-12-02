package com.tripreader.common.util;

import com.tripreader.attraction.model.dto.*;
import com.tripreader.news.model.dto.NewsDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class TourApiGetter {
    private final String URL ="http://apis.data.go.kr/B551011/KorService1";

    @Value("${tour.api.key}")
    private String KEY;
    private String RESPONSE_TYPE = "JSON";

    @Value("${tour.api.mobile-OS}")
    private String MOBILE_OS;

    @Value("${tour.api.mobile-app}")
    private String MOBILE_APP;


    public String getResult(String method, Map<String,Object> params){
        StringBuilder methodUrl = new StringBuilder(URL);
        methodUrl.append("/");
        methodUrl.append(method);
        List<NewsDto> ret = new LinkedList<>();
        HttpUtil httpUtil = new HttpUtil().url(methodUrl.toString())
                .queryString("serviceKey", KEY)
                .queryString("_type", RESPONSE_TYPE)
                .queryString("MobileOS", MOBILE_OS)
                .queryString("MobileApp", MOBILE_APP);

        for(Map.Entry<String, Object> entry : params.entrySet())
            httpUtil.queryString(entry.getKey(), ""+entry.getValue());

        return httpUtil.build();
    }


    public List<TourApiCommonResponseDto> commonSearch(Map<String, Object> params) {
        if(params.containsKey("keyword")) {
            return getTourApiCommonResponseDtos(getResult("searchKeyword1", params));
        }else {
            return getTourApiCommonResponseDtos(getResult("areaBasedList1", params));
        }
    }

    private List<TourApiCommonResponseDto> getTourApiCommonResponseDtos(String result) {
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        List<TourApiCommonResponseDto> ret = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            //contenttypeid가 12, 14, 15인 경우에만 추가
            if(jsonArray.getJSONObject(i).getInt("contenttypeid") == 12 || jsonArray.getJSONObject(i).getInt("contenttypeid") == 14 || jsonArray.getJSONObject(i).getInt("contenttypeid") == 15){
                TourApiCommonResponseDto tourApiCommonResponseDto = new TourApiCommonResponseDto();
                JSONObject json = jsonArray.getJSONObject(i);
                tourApiCommonResponseDto.setAddr1(json.getString("addr1"));
                tourApiCommonResponseDto.setAddr2(json.getString("addr2"));
                tourApiCommonResponseDto.setAreaCode(json.getInt("areacode"));
                tourApiCommonResponseDto.setContentid(json.getInt("contentid"));
                tourApiCommonResponseDto.setContenttypeid(json.getInt("contenttypeid"));
                tourApiCommonResponseDto.setFirstimage(json.getString("firstimage"));
                tourApiCommonResponseDto.setFirstimage2(json.getString("firstimage2"));
                tourApiCommonResponseDto.setMapx(json.getDouble("mapx"));
                tourApiCommonResponseDto.setMapy(json.getDouble("mapy"));
                tourApiCommonResponseDto.setSigunguCode(json.getInt("sigungucode"));
                tourApiCommonResponseDto.setCreatedtime(json.getString("createdtime"));
                tourApiCommonResponseDto.setModifiedtime(json.getString("modifiedtime"));
                tourApiCommonResponseDto.setTel(json.getString("tel"));
                tourApiCommonResponseDto.setTitle(json.getString("title"));
                ret.add(tourApiCommonResponseDto);
            }
        }
        return ret;
    }

    public AttractionsDto getAttractionDetail(int contenttypeid, int contentid) {

        String result = getResult("detailIntro1", new HashMap<String, Object>(){{
            put("contentId", contentid);
            put("contentTypeId", contenttypeid);
        }});
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        JSONObject json = jsonArray.getJSONObject(0);
        AttractionsDto attractionsDto = new AttractionsDto();
        attractionsDto.setContentid(json.getInt("contentid"));
        attractionsDto.setContenttypeid(json.getInt("contenttypeid"));
        attractionsDto.setInfocenter(json.getString("infocenter"));
        attractionsDto.setOpendate(json.getString("opendate"));
        attractionsDto.setRestdate(json.getString("restdate"));
        attractionsDto.setExpguide(json.getString("expguide"));
        attractionsDto.setExpagerange(json.getString("expagerange"));
        attractionsDto.setAccomcount(json.getString("accomcount"));
        attractionsDto.setUseseason(json.getString("useseason"));
        attractionsDto.setUsetime(json.getString("usetime"));
        attractionsDto.setParking(json.getString("parking"));
        return attractionsDto;
    }

    public CulturalFacilitiesDto getCulturalFacilitiesDetail(int i, int contentid) {

        String result = getResult("detailIntro1", new HashMap<String, Object>(){{
            put("contentId", contentid);
            put("contentTypeId", i);
        }});
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        JSONObject json = jsonArray.getJSONObject(0);
        CulturalFacilitiesDto culturalFacilitiesDto = new CulturalFacilitiesDto();
        culturalFacilitiesDto.setContentid(json.getInt("contentid"));
        culturalFacilitiesDto.setContenttypeid(json.getInt("contenttypeid"));
        culturalFacilitiesDto.setScale(json.getString("scale"));
        culturalFacilitiesDto.setUsefee(json.getString("usefee"));
        culturalFacilitiesDto.setDiscountinfo(json.getString("discountinfo"));
        culturalFacilitiesDto.setSpendtime(json.getString("spendtime"));
        culturalFacilitiesDto.setParkingfee(json.getString("parkingfee"));
        culturalFacilitiesDto.setInfocenterculture(json.getString("infocenterculture"));
        culturalFacilitiesDto.setAccomcountculture(json.getString("accomcountculture"));
        culturalFacilitiesDto.setUsetimeculture(json.getString("usetimeculture"));
        culturalFacilitiesDto.setRestdateculture(json.getString("restdateculture"));
        culturalFacilitiesDto.setParkingculture(json.getString("parkingculture"));
        return culturalFacilitiesDto;
    }

    public FestivalEventsDto getFestivalEventDetail(int i, int contentid) {

        String result = getResult("detailIntro1", new HashMap<String, Object>(){{
            put("contentId", contentid);
            put("contentTypeId", i);
        }});
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        JSONObject json = jsonArray.getJSONObject(0);
        FestivalEventsDto festivalEventsDto = new FestivalEventsDto();
        festivalEventsDto.setContentid(json.getInt("contentid"));
        festivalEventsDto.setContenttypeid(json.getInt("contenttypeid"));
        festivalEventsDto.setSponsor1(json.getString("sponsor1"));
        festivalEventsDto.setSponsor1tel(json.getString("sponsor1tel"));
        festivalEventsDto.setSponsor2(json.getString("sponsor2"));
        festivalEventsDto.setSponsor2tel(json.getString("sponsor2tel"));
        festivalEventsDto.setEventenddate(json.getString("eventenddate"));
        festivalEventsDto.setPlaytime(json.getString("playtime"));
        festivalEventsDto.setEventplace(json.getString("eventplace"));
        festivalEventsDto.setEventhomepage(json.getString("eventhomepage"));
        festivalEventsDto.setAgelimit(json.getString("agelimit"));
        festivalEventsDto.setBookingplace(json.getString("bookingplace"));
        festivalEventsDto.setPlaceinfo(json.getString("placeinfo"));
        festivalEventsDto.setSubevent(json.getString("subevent"));
        festivalEventsDto.setProgram(json.getString("program"));
        festivalEventsDto.setEventstartdate(json.getString("eventstartdate"));
        festivalEventsDto.setUsetimefestival(json.getString("usetimefestival"));
        festivalEventsDto.setDiscountinfofestival(json.getString("discountinfofestival"));
        festivalEventsDto.setSpendtimefestival(json.getString("spendtimefestival"));
        festivalEventsDto.setFestivalgrade(json.getString("festivalgrade"));
        return festivalEventsDto;
    }

    public List<TourApiFestivalResponseDto> getFestivalList(Map<String, Object> params) {
        String result = getResult("searchFestival1", params);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        List<TourApiFestivalResponseDto> ret = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            TourApiFestivalResponseDto tourApiFestivalResponseDto = new TourApiFestivalResponseDto();
            JSONObject json = jsonArray.getJSONObject(i);
            tourApiFestivalResponseDto.setAddr1(json.getString("addr1"));
            tourApiFestivalResponseDto.setAddr2(json.getString("addr2"));
            tourApiFestivalResponseDto.setAreaCode(json.getInt("areacode"));
            tourApiFestivalResponseDto.setContentid(json.getInt("contentid"));
            tourApiFestivalResponseDto.setContenttypeid(json.getInt("contenttypeid"));
            tourApiFestivalResponseDto.setFirstimage(json.getString("firstimage"));
            tourApiFestivalResponseDto.setFirstimage2(json.getString("firstimage2"));
            tourApiFestivalResponseDto.setMapx(json.getDouble("mapx"));
            tourApiFestivalResponseDto.setMapy(json.getDouble("mapy"));
            tourApiFestivalResponseDto.setSigunguCode(json.getInt("sigungucode"));
            tourApiFestivalResponseDto.setCreatedtime(json.getString("createdtime"));
            tourApiFestivalResponseDto.setModifiedtime(json.getString("modifiedtime"));
            tourApiFestivalResponseDto.setTel(json.getString("tel"));
            tourApiFestivalResponseDto.setTitle(json.getString("title"));
            tourApiFestivalResponseDto.setEventenddate(json.getString("eventenddate"));
            tourApiFestivalResponseDto.setEventstartdate(json.getString("eventstartdate"));
            ret.add(tourApiFestivalResponseDto);
        }
        return ret;
    }

    public String getFestivalOverview(int contentid) {
        String result = getResult("detailCommon1", new HashMap<String, Object>(){{
            put("contentId", contentid);
            put("contentTypeId", 15);
            put("overviewYN", "Y");
        }});
        JSONObject jsonObject = new JSONObject(result);

        log.info("jsonObject = " + jsonObject);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        return jsonArray.getJSONObject(0).getString("overview");
    }
}
