package com.port.testcloud.autotestcloud.service.cases;

import com.port.testcloud.autotestcloud.domain.CaseInfo;
import com.port.testcloud.autotestcloud.dto.InfoDto;

/**
 * 用例详情
 */
public interface CaseInfoService {

    InfoDto findOne(String caseId);


    InfoDto findByCaseId(String caseId);

    InfoDto save(InfoDto InfoDto);


}
