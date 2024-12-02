package com.tripreader.common.util;

import com.tripreader.news.model.dto.ContentDto;

import java.util.*;

public class ContentGetter {
    public static List<ContentDto> getList(String rawString) {
        String[] splitString = rawString.split("\n");
        String line;

        List<ContentDto> contentList = new LinkedList<>();
        int nowLine = 0;
        boolean finish = false;
        for (int i = 1; !finish; i++) {
            ContentDto content = new ContentDto();
            content.setId(i);

            while (!finish && content.getContent() == null) {
                line = splitString[nowLine++];
                if (line.length() < 1)
                    continue;

                if (line.charAt(0) == '#') {
                    line = line.replace('#', ' ').trim();
                    content.setHeader(line);
                } else {
                    content.setContent(line);
                }

                if (nowLine == splitString.length)
                    finish = true;
            }
            if (!finish)
                contentList.add(content);

        }
        return contentList;
    }

    public static ContentDto getContent(String rawString) {
        String[] splitString = rawString.split("\n");
        String line;
        int nowLine = 0;
        boolean finish = false;
        ContentDto content = new ContentDto();

        content.setId(1);
        while (!finish && content.getContent() == null) {
            line = splitString[nowLine++];
            if (line.length() < 1)
                continue;

            if (line.charAt(0) == '#') {
                line = line.replace('#', ' ').trim();
                content.setHeader(line);
            } else {
                content.setContent(line);
            }

            if (nowLine == splitString.length)
                finish = true;
        }
        return content;
    }
}
