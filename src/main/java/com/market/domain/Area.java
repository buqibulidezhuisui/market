package com.market.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName Area
 * @Description TODO
 * @date 19-1-25 上午10:23
 * @Author hanbing
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    private Long id;
    private String name;
    private String code;
    private String parentCode;
    private String leave;//type  1:省,2:市,3:县
}
