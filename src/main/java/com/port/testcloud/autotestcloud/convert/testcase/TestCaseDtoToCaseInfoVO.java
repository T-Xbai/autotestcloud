package com.port.testcloud.autotestcloud.convert.testcase;

import com.port.testcloud.autotestcloud.VO.CaseInfoVO;
import com.port.testcloud.autotestcloud.domain.TestCase;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TestCaseDtoToCaseInfoVO
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 12:33
 * @Description: TestCaseDto -> CaseInfoVO
 */
public class TestCaseDtoToCaseInfoVO {


    public static CaseInfoVO convert(TestCaseDto TestCaseDto) {
        if (TestCaseDto == null) {
            return null;
        }
        CaseInfoVO caseInfoVO = new CaseInfoVO();
        BeanUtils.copyProperties(TestCaseDto.getInfo(), caseInfoVO);
        BeanUtils.copyProperties(TestCaseDto, caseInfoVO);
        return caseInfoVO;
    }


    public static List<CaseInfoVO> convert(Page<TestCaseDto> testCaseDtoPage) {

        List<CaseInfoVO> caseInfoVOList = new ArrayList<>();

        testCaseDtoPage.forEach(testCase -> {
            CaseInfoVO moduleDto = new CaseInfoVO();
            BeanUtils.copyProperties(testCase, moduleDto);
            caseInfoVOList.add(moduleDto);
        });
        return caseInfoVOList;
    }


    public static List<CaseInfoVO> convert(List<TestCaseDto> testCaseDtoList) {

        List<CaseInfoVO> caseInfoVOList = new ArrayList<>();

        testCaseDtoList.forEach(testCaseDto -> {
            CaseInfoVO caseInfoVO = new CaseInfoVO();
            BeanUtils.copyProperties(testCaseDto, caseInfoVO);
            caseInfoVOList.add(caseInfoVO);
        });
        return caseInfoVOList;
    }


}
