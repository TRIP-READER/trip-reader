package com.tripreader.common.util;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PrettyTimeGenerator {
    public String getPrettyTimeResult(String time) throws ParseException {
        // PrettyTime을 사용하여 차이 계산
        // 시간 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date inputDate = dateFormat.parse(time);
        // 현재 시간
        Date now = new Date(System.currentTimeMillis());
        PrettyTime prettyTime = new PrettyTime(now);
        String prettyTimeResult = prettyTime.format(inputDate);
        return prettyTimeResult;
    }
}
