package com.port.testcloud.autotestcloud.convert.testcase;

import com.port.testcloud.autotestcloud.domain.DependCase;
import com.port.testcloud.autotestcloud.form.DependCaseForm;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: DependCaseFormToDependCase
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 18:19
 * @Description: DependCaseForm -> DependCase
 */
public class DependCaseFormToDependCase {


    public static List<DependCase> convert(List<DependCaseForm> dependCaseFormList, List<DependCase> dependCaseList) {
        dependCaseFormList.forEach(dependCaseForm -> {
            DependCase dependCase = new DependCase();
            if (StringUtils.isEmpty(dependCaseForm.getId())) {
                BeanUtils.copyProperties(dependCaseForm, dependCase);
                dependCaseList.add(dependCase);
            } else {
                dependCaseList.forEach(depend -> {
                            if (dependCaseForm.getId().equals(depend.getId())) {
                                BeanUtils.copyProperties(dependCaseForm, depend);
                            }
                        }
                );

            }
        });
        return dependCaseList;
    }
}
