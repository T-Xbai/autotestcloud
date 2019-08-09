package com.port.testcloud.autotestcloud.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.*;

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

    public static <T> T fromJson(String json, T t) {
        Gson gson = new Gson();
        return gson.fromJson(json, (Type) t);
    }

    public static Object fromJson(String json,Class c) {
        Gson gson = new Gson();
        return gson.fromJson(json, c);
    }

    public static Map<String, Object> toMap(String str) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        return gson.fromJson(str, map.getClass());
    }


}
