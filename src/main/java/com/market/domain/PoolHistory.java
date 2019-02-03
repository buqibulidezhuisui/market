package com.market.domain;

import com.market.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClaseName: PoolHistory
 * @Description: 奖励池变更历史
 * @Author: 24878
 * @Date: 2019/2/2 16:36
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PoolHistory extends BaseEntity {
//    订单编号
    private String orderNo;
//    奖励池贡献金额
    private Double poolUp;
//    随机抽奖金额
    private Double poolDown;
//    用户编号
    private String userId;
}
