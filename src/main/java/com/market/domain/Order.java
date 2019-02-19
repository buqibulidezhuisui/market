package com.market.domain;


import com.market.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order  {
    private Long id;
    private Double payFee;
    private String orderNo;//保存时生成的订单号（时间使用的是年月是时分秒）
    private String openId;
    private String alipayId;
    private String tradeNo;//支付时生成的订单号(时间使用的是时间戳)
    private Double inputFee;//小程序段用户输入金额
    private Double inCoupon;//用户抽取红包
    private Double deCoupon;//用户本次使用红包
    private Long businessId;//商户id
    private Date createAt;//支付时间

}
