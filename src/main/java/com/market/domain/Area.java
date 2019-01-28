package com.market.domain;

import com.market.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Area extends BaseEntity {
    //区域名称
    private String name;

    //区域编码
    private String code;

    //城市编码
    private String parentCode;

    //城市等级：1省份，2城市，3区
    private Integer level;
}
