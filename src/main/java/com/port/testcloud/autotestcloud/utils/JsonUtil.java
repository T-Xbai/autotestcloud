package com.port.testcloud.autotestcloud.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.port.testcloud.autotestcloud.dto.AssertResultDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: JsonUtil
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 17:31
 * @Description:
 */
public class JsonUtil {


    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);

    }


    public static Map<String,Object> toMap(String str){
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        return gson.fromJson(str, map.getClass());
    }



}
