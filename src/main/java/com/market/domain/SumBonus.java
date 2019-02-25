package com.market.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClaseName: SumBonus
 * @Description: 个人消费红包和个人红包使用情况（均为总额）
 * @Author: 24878
 * @Date: 2019/2/21 13:52
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SumBonus {
    private Double sumInCoupon;
    private Double sumDeCoupon;
}
