package com.port.testcloud.autotestcloud.repository.cases;

import com.port.testcloud.autotestcloud.domain.CaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: CaseInfoRepository
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-30 00:33
 * @Description: 用例详情
 */
public interface CaseInfoRepository extends JpaRepository<CaseInfo,String> {

    CaseInfo findByCaseId(String caseId);



}
