package com.market.domain;

import com.market.domain.common.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * @ClassName Coupons
 * @Description 优惠券相关信息存储
 * @date 19-1-11 下午2:21
 * @Author hanbing
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Coupons extends BaseEntity {
    private Date getTime;//最后一次领取时间
    private Date expirationTime;//过期时间
    private String userId;//用户id
    private String bussId;//商家id
    private Double money;//优惠总金额
    private String couponsNo;// 优惠券编码
    private Integer type; //优惠券类型:0全场通用,1指定商户可用,2仅领取人可用,3仅领取人可用的全场通用券.
}
