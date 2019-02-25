package com.market.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClaseName: BounsReferee
 * @Description: 保存推荐赏金相关信息
 * @Author: 24878
 * @Date: 2019/2/21 9:36
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BounsReferee {
    private Long id ;//编号
    private String openId;//微信用户编号（推荐人）
    private Long orderId;//订单编号
    private Date createAt;//创建时间
    private Double bonus;//赏金
    private String nickname;//被邀请人的昵称

}
