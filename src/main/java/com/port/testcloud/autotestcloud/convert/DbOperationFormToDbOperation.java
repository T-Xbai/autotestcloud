package com.port.testcloud.autotestcloud.convert;

import com.port.testcloud.autotestcloud.domain.DbOperation;
import com.port.testcloud.autotestcloud.form.DbOperationForm;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: DbOperationFormToDbOperation
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-02 11:30
 * @Description: DbOperationForm -> DbOperation
 */
public class DbOperationFormToDbOperation {


    public static List<DbOperation> convert(List<DbOperationForm> dbOperationFormList, List<DbOperation> dbOperationList) {

        dbOperationFormList.forEach(dbOperationForm -> {
            if (StringUtils.isEmpty(dbOperationForm.getId())) {
                DbOperation dbOperation = new DbOperation();
                BeanUtils.copyProperties(dbOperationForm, dbOperation);
                dbOperationList.add(dbOperation);
            } else {
                dbOperationList.forEach(dbOperation -> {
                    if (dbOperationForm.getId().equals(dbOperation.getId())) {
                        BeanUtils.copyProperties(dbOperationForm, dbOperation);
                    }
                });
            }

        });
        return dbOperationList;

    }


}
