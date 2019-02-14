package com.market.domain;

import com.market.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName Shop
 * @Description TODO
 * @date 19-1-16 上午9:35
 * @Author hanbing
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shop extends BaseEntity {
//    商户id
    private String code;
//    商户地址
    private String addr;
//    店铺名称
    private String name;
//    店铺图片(头像)
    private String pic;
//    商户简介
    private String info;
//    联系方式
    private String mobile;
//    删除标记  del=1 为未删除 del=0 为删除
//    商店区域代码
    private String area;
//    店铺类型
    private String type;
    private String typeName;
    //经度
    private Double longitude;
    //纬度
    private Double latitude;
    //距离
    private Double distance;
    //商户openid
    private String openid;
}
