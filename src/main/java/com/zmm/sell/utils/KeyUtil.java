package com.zmm.sell.utils;

import java.util.Random;

/**
 * @Name KeyUtil
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
