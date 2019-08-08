package com.port.testcloud.autotestcloud.convert.testcase;

import com.port.testcloud.autotestcloud.convert.DbOperationFormToDbOperation;
import com.port.testcloud.autotestcloud.domain.CaseInfo;
import com.port.testcloud.autotestcloud.domain.DbOperation;
import com.port.testcloud.autotestcloud.domain.DependCase;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.form.CaseInfoForm;
import com.port.testcloud.autotestcloud.form.DbOperationForm;
import com.port.testcloud.autotestcloud.form.DependCaseForm;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CaseInfoFormToTestCaseDto
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 15:01
 * @Description: CaseInfoForm -> TestCaseDto
 */
public class CaseInfoFormToTestCaseDto {

    public static TestCaseDto convert(CaseInfoForm caseInfoForm) {

        TestCaseDto testCaseDto = new TestCaseDto();
        return convert(caseInfoForm, testCaseDto);
    }

    public static TestCaseDto convert(CaseInfoForm caseInfoForm, TestCaseDto testCaseDto) {
        if (caseInfoForm == null) {
            return null;
        }
        InfoDto infoDto = testCaseDto.getInfo() == null ? new InfoDto() : testCaseDto.getInfo();
        BeanUtils.copyProperties(caseInfoForm, infoDto);

        // DependCaseForm -> DependCase
        List<DependCaseForm> dependCaseFormList = caseInfoForm.getDependCaseList();
        List<DependCase> dependCaseList = testCaseDto.getDependCaseList() == null ?
                new ArrayList<>() : testCaseDto.getDependCaseList();

        if (dependCaseFormList != null && dependCaseFormList.size() > 0) {
            DependCaseFormToDependCase.convert(dependCaseFormList, dependCaseList);
        }

        // DbOperationForm -> DbOperation
        List<DbOperationForm> dbOperationFormList = caseInfoForm.getDbOperationList();
        List<DbOperation> dbOperationList = testCaseDto.getDbOperationList() == null ?
                new ArrayList<>() : testCaseDto.getDbOperationList();

        if (dbOperationFormList!=null && dbOperationFormList.size() > 0) {
            DbOperationFormToDbOperation.convert(dbOperationFormList, dbOperationList);
        }


        BeanUtils.copyProperties(caseInfoForm, testCaseDto);
        testCaseDto.setInfo(infoDto);
        testCaseDto.setDependCaseList(dependCaseList);
        testCaseDto.setDbOperationList(dbOperationList);
        return testCaseDto;
    }
}
