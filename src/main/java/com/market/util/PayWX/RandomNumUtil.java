package com.market.util.PayWX;

import java.util.Random;

/**
 * @Auther: hanbing
 * @Description:生成随机8位数
 */
public class RandomNumUtil {
    public static String getCard() {
        Random rand = new Random();//生成随机数
        String cardNnumer = "";
        for (int a = 0; a < 8; a++) {
            cardNnumer += rand.nextInt(10);//生成6位数字
        }
        return cardNnumer;
    }
}
