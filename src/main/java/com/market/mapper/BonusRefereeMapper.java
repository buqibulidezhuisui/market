package com.market.mapper;

import com.market.domain.BounsReferee;
import com.market.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClaseName: BonusRefereeMapper
 * @Description:
 * @Author: 24878
 * @Date: 2019/2/21 10:25
 */
@Repository
public interface  BonusRefereeMapper extends BaseMapper<BounsReferee> {
   public List<BounsReferee> findBounRefereeByOpenId(String openid);
}
