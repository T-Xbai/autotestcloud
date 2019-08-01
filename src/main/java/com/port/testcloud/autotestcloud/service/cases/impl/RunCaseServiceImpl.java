package com.port.testcloud.autotestcloud.service.cases.impl;

import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.service.RequestService;
import com.port.testcloud.autotestcloud.service.cases.RunCaseService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName: RunCaseServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 22:55
 * @Description:
 */
@Service
public class RunCaseServiceImpl implements RunCaseService {


    @Autowired
    private RequestService requestService;

    @Autowired
    private TestCaseService caseService;

    @Override
    @Transactional
    public Response runCase(String caseId) {

        TestCaseDto testCaseDto = caseService.findOne(caseId);

        Response response = null;
        try {
            Class<? extends RequestService> aClass = requestService.getClass();
            Method method = aClass.getMethod(testCaseDto.getInfo().getRequestMethod(), TestCaseDto.class);
            response = (Response) method.invoke(requestService,testCaseDto);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }




}
