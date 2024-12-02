package com.tripreader.common.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    // 헤더
    private HashMap<String, String> headers;
    // 요청 바디 : <Key, Value> 쌍
    private MultiValueMap<String, String> body;

    // 요청 URL
    private StringBuilder urlBuilder;
    private boolean queryStringToken;
    private String url;
    // 요청 방식
    private String method;

    public HttpUtil() {
        this.headers = new HashMap<>();
        this.body = new LinkedMultiValueMap<>();
        this.queryStringToken = true;
    }

    public String build() {
        url = urlBuilder.toString();
        if ("POST".equalsIgnoreCase(method)) {
            return post(url, headers, body);
        } else {
            return get(url, headers);
        }
    }

    public HttpUtil url(String url) {
        this.urlBuilder = new StringBuilder(url);
        return this;
    }

    public HttpUtil queryString(String name, String value) {
        value = URLEncoder.encode(value, StandardCharsets.UTF_8);
        if (queryStringToken) {
            urlBuilder.append("?")
                    .append(name)
                    .append("=")
                    .append(value);
            queryStringToken = false;
        } else {
            urlBuilder.append("&")
                    .append(name)
                    .append("=")
                    .append(value);
        }
        return this;
    }

    public HttpUtil method(String method) {
        this.method = method.toUpperCase();
        return this;
    }

    public HttpUtil header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public HttpUtil body(String key, String value) {
        this.body.add(key, value);
        return this;
    }

    public HttpUtil body(HashMap<String, Object> params) {
        Iterator<String> itr = params.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            body.add(key, (String) params.get(key));
        }
        return this;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static String post(String apiUrl, Map<String, String> requestHeaders, MultiValueMap<String, String> requestBody) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("POST");
            con.setDoOutput(true); // Enable output for the request body
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            // Write the request body
            String bodyString = buildRequestBody(requestBody);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = bodyString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static String buildRequestBody(MultiValueMap<String, String> requestBody) {
        StringBuilder body = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : requestBody.entrySet()) {
            String key = entry.getKey();
            for (String value : entry.getValue()) {
                if (body.length() != 0) {
                    body.append("&");
                }
                body.append(URLEncoder.encode(key, StandardCharsets.UTF_8));
                body.append("=");
                body.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
            }
        }
        return body.toString();
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);
        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
