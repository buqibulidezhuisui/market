package com.market.util;

import java.util.Random;

/**
 * @Auther: Ruizhi
 * @Description:奖池
 */
public class BonusUtil {

    public static int[] bonusSize = {100, 40, 20, 12, 6, 2, 1};

    public static Double getBonus(Double bonusPool){
        Double bonus = 0.0;
        Random random = new Random();
        int i = random.nextInt(1000);
        if (i < 1) {
            bonus = Arith.div(bonusPool * bonusSize[0], 1600);
        } else if (i < 4) {
            bonus = Arith.div(bonusPool * bonusSize[1], 1600);
        } else if (i < 10) {
            bonus = Arith.div(bonusPool * bonusSize[2], 1600);
        } else if (i < 20) {
            bonus = Arith.div(bonusPool * bonusSize[3], 1600);
        } else if (i < 40) {
            bonus = Arith.div(bonusPool * bonusSize[4], 1600);
        } else if (i < 100) {
            bonus = Arith.div(bonusPool * bonusSize[5], 1600);
        } else {
            bonus = Arith.div(bonusPool * bonusSize[6], 1600);
        }
        return bonus;
    }

}
