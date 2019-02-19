package com.market.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClaseName: OrderForMini
 * @Description: 用于小程序的order
 * @Author: 24878
 * @Date: 2019/2/15 15:47
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderForMini {
    private Date createAt; //支付时间
    private Double inputFee;//应付费用
    private Double inCoupon;//领取红包金额
    private Double deCoupon;//使用红包金额
    private Double payFee;//实际支付
    private String businessName;//商铺名称
    private String businessPic;//上铺头像
    private Long businessId;//商户id
}
