package com.port.testcloud.autotestcloud.convert.testcase;

import com.port.testcloud.autotestcloud.domain.CaseInfo;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.form.CaseInfoForm;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: CaseInfoToInfoDto
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 17:39
 * @Description: CaseInfo -> InfoDto
 */
public class CaseInfoToInfoDto {

    public static InfoDto convert(CaseInfo caseInfo) {
        if (caseInfo == null) {
            return null;
        }

        InfoDto infoDto = new InfoDto();
        BeanUtils.copyProperties(caseInfo,infoDto);
        return infoDto;
    }
}
