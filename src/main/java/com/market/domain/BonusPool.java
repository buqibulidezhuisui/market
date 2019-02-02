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
public class BonusPool extends BaseEntity {

  private Double money;//奖池总金额
}
