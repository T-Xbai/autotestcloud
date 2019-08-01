package com.port.testcloud.autotestcloud.convert.testcase;

import com.port.testcloud.autotestcloud.domain.ProjectModules;
import com.port.testcloud.autotestcloud.domain.TestCase;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProjectModuleToModuleDto
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-26 11:45
 * @Description: TestCase -> TestCaseDto
 */
public class TestCaseToTestCaseDto {

    public static TestCaseDto convert(TestCase testCase) {
        if (testCase == null){
            return null;
        }
        TestCaseDto testCaseDto = new TestCaseDto();
        BeanUtils.copyProperties(testCase, testCaseDto);
        return testCaseDto;
    }



    public static Page<TestCaseDto> convert(Page<TestCase> testCasePage, Pageable pageable) {

        List<TestCaseDto> caseDtoList = new ArrayList<>();

        testCasePage.forEach(testCase -> {
            TestCaseDto moduleDto = new TestCaseDto();
            BeanUtils.copyProperties(testCase, moduleDto);
            caseDtoList.add(moduleDto);
        });
        return new PageImpl<>(caseDtoList,pageable,testCasePage.getTotalElements());
    }


    public static List<TestCaseDto> convert(List<TestCase> testCaseList) {

        List<TestCaseDto> caseDtoList = new ArrayList<>();

        testCaseList.forEach(testCase -> {
            TestCaseDto moduleDto = new TestCaseDto();
            BeanUtils.copyProperties(testCase, moduleDto);
            caseDtoList.add(moduleDto);
        });
        return caseDtoList;
    }

}
