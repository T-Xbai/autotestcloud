package com.port.testcloud.autotestcloud.config;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HttpConfig
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 23:34
 * @Description: httpClient 用于 cookie 持久化
 */
@Configuration
public class HttpConfig {

    private Map<String, List<Cookie>> cookieMap = new HashMap<>();

    @Bean("okHttpClient")
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().cookieJar(new CookieJar() {

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieMap.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {

                List<Cookie> cookies = cookieMap.get(url.host());
                return cookies != null ? cookies : new ArrayList<>();
            }
        }).build();
    }
}
