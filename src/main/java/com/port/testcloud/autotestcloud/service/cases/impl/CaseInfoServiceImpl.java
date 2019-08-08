package com.port.testcloud.autotestcloud.service.cases.impl;

import com.port.testcloud.autotestcloud.domain.CaseInfo;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.cases.CaseInfoRepository;
import com.port.testcloud.autotestcloud.service.cases.CaseInfoService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.port.testcloud.autotestcloud.convert.testcase.CaseInfoToInfoDto.convert;

/**
 * @ClassName: CaseInfoServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-30 17:11
 * @Description: 用例详情
 */
@Service
@Slf4j
public class CaseInfoServiceImpl implements CaseInfoService {

    @Autowired
    private CaseInfoRepository infoRepository;

    @Autowired
    private TestCaseService caseService;

    @Override
    public InfoDto findOne(String id) {
        CaseInfo caseInfo = infoRepository.findById(id).orElse(new CaseInfo());
        return convert(caseInfo);
    }

    @Override
    public InfoDto findByCaseId(String caseId) {
        CaseInfo byCaseId = infoRepository.findByCaseId(caseId);
        return convert(byCaseId);
    }

    @Override
    @Transactional
    public InfoDto save(InfoDto infoDto) {

        if (infoDto.getId() == null){
            infoDto.setId(KeyUtil.unique());
        }

        caseService.isExist(infoDto.getCaseId());
        CaseInfo byCaseId = infoRepository.findByCaseId(infoDto.getCaseId());
        if (byCaseId != null && byCaseId.getCaseId().equals(infoDto.getCaseId())
                && !byCaseId.getId().equals(infoDto.getId())) {
            log.error("【用例详情操作】caseId = {} , 已存在对应的详情信息", infoDto.getCaseId());
            throw new AutoTestException(ResultEnums.CASE_ID_RELEVANCE_ERROR);
        }

        CaseInfo caseInfo = new CaseInfo();
        BeanUtils.copyProperties(infoDto,caseInfo);
        infoRepository.save(caseInfo);
        return infoDto;
    }
}
