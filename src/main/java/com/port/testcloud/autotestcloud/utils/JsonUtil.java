package com.port.testcloud.autotestcloud.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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

    public static void main(String[] args) throws IOException {
        String json = "\"attributes\":[{\"nm\":\"ACCOUNT\",\"lv\":[{\"v\":{\"Id\":null,\"State\":null},\"vt\":\"java.util.Map\",\"cn\":1}],\"vt\":\"java.util.Map\",\"status\":\"SUCCESS\",\"lmd\":13585},{\"nm\":\"PROFILE\",\"lv\":[{\"v\":{\"Party\":null,\"Ads\":null},\"vt\":\"java.util.Map\",\"cn\":2}],\"vt\":\"java.util.Map\",\"status\":\"SUCCESS\",\"lmd\":41962}]\n";
        System.out.println(JsonUtil.toJson(json));
    }
}
