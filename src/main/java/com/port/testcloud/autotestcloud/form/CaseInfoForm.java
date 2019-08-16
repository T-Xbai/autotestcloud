package com.port.testcloud.autotestcloud.form;

import com.google.gson.Gson;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import com.sun.org.glassfish.gmbal.NameValue;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName: CaseInfoForm
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 14:17
 * @Description: 用例表单
 */
@Data
public class CaseInfoForm {


    @NotEmpty(message = "moduleId 必传")
    private String moduleId;

    @NotEmpty(message = "caseName 必传")
    private String caseName;

    @NotNull
    private Integer indexs;

    private Integer isDelete = 0;

    @NotEmpty(message = "requestUrl 必传")
    private String requestUrl;

    @NotEmpty(message = "requestMethod 必传")
    private String requestMethod;

    private String requestBody;

    private String requestHeaders;

    private String checkResult;

    private List<DependCaseForm> dependCaseList;

    private List<DbOperationForm> dbOperationList;



}
