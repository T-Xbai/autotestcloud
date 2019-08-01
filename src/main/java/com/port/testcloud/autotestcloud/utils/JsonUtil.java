package com.port.testcloud.autotestcloud.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: JsonUtil
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 17:31
 * @Description:
 */
public class JsonUtil {


    public static String toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Object json = mapper.readValue(String.valueOf(object),Object.class);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);


    }


    public static Map<String,Object> toMap(String str){
        Gson gson = new Gson();

        Map<String,Object> map = new HashMap<>();
        return gson.fromJson(str, map.getClass());
    }




}
