package com.port.testcloud.autotestcloud.utils;

/**
 * @ClassName: ExceptionUtil
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-14 17:18
 * @Description:
 */
public class ExceptionUtil {


    public static String getExpetionMsg(String prefixMsg, Exception e) {
        prefixMsg = prefixMsg.concat("\n").concat(e.toString());

        StackTraceElement[] stackTraces = e.getStackTrace();
        for (StackTraceElement stackTrace : stackTraces) {
            prefixMsg = prefixMsg.concat(stackTrace.toString()).concat("\n");

        }
        return prefixMsg;
    }

}
