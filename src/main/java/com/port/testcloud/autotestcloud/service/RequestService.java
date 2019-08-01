package com.port.testcloud.autotestcloud.service;

import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.sun.deploy.net.HttpResponse;
import okhttp3.Response;

import java.io.IOException;

/**
 * @ClassName: RequestService
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 09:52
 * @Description: 请求方法
 */
public interface RequestService {

    Response get(TestCaseDto testCaseDto) throws IOException;

    Response post(TestCaseDto testCaseDto) throws IOException;

    Response put(TestCaseDto testCaseDto) throws IOException;

    Response delete(TestCaseDto testCaseDto) throws IOException;


}
