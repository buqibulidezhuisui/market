package com.market.domain;


import com.market.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order  {
    private Long id;
    private Double payFee;
    private String orderNo;//保存时生成的订单号（时间使用的是年月是时分秒）
    private String openId;
    private String aliId;
    private String tradeNo;//支付时生成的订单号(时间使用的是时间戳)
    private String type;
}
