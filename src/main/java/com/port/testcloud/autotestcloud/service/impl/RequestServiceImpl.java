package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.service.RequestService;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: RequestServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 09:54
 * @Description:
 */
@Service
public class RequestServiceImpl implements RequestService {


    @Autowired
    private OkHttpClient okHttpClient;

    private final String CONTENT_TYPE = "application/json";

    @Override
    public Response get(TestCaseDto testCaseDto) throws IOException {

        Request request = request(testCaseDto).get().build();
        return okHttpClient.newCall(request).execute();
    }

    @Override
    public Response post(TestCaseDto testCaseDto) throws IOException {

        InfoDto info = testCaseDto.getInfo();

        RequestBody requestBody = RequestBody.create(
                MediaType.parse(CONTENT_TYPE), info.getRequestBody());

        Request request = request(testCaseDto)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request).execute();
    }

    @Override
    public Response put(TestCaseDto testCaseDto) throws IOException {
        InfoDto info = testCaseDto.getInfo();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse(CONTENT_TYPE), info.getRequestBody());

        Request request = request(testCaseDto)
                .put(requestBody)
                .build();
        return okHttpClient.newCall(request).execute();
    }

    @Override
    public Response delete(TestCaseDto testCaseDto) throws IOException {
        Request request = request(testCaseDto).delete().build();
        return okHttpClient.newCall(request).execute();

    }


    private Request.Builder request(TestCaseDto testCaseDto) {
        Request.Builder request = new Request.Builder();
        InfoDto info = testCaseDto.getInfo();
        final String requestUrl = info.getRequestUrl();
        request.url(requestUrl);
        if (!StringUtils.isEmpty(info.getRequestHeaders())) {
            Map<String, Object> headerMap = JsonUtil.toMap(info.getRequestHeaders());
            for (String key : headerMap.keySet()) {
                request.header(key, headerMap.get(key).toString());
            }
        }


        return request;
    }
}
