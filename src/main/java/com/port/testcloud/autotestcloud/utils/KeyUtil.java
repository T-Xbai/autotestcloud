package com.port.testcloud.autotestcloud.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @ClassName: KeyUtil
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 16:07
 * @Description: 生成 id
 */
public class KeyUtil {

    public static String unique() {
        Random random = new Random();

        String randomNumber = String.valueOf(random.nextInt(9000000) + 1000000);

        return randomNumber.concat(String.valueOf(System.currentTimeMillis()));

    }


}
