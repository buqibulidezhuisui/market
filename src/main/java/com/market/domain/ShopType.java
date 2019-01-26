package com.market.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName ShopType
 * @Description TODO
 * @date 19-1-25 下午5:07
 * @Author hanbing
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopType {
    private Long id;
    private String code;
    private String name;
    private String parentCode;
}
