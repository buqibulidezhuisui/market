package com.market.service;

import com.market.domain.BounsReferee;

import java.util.List;

/**
 * @InterfaceName: BonusRefereeService
 * @Description: 推荐人赏金相关
 * @Author: 24878
 * @Date: 2019/2/21 10:22
 */
public interface BonusRefereeService extends BaseService<BounsReferee> {
    List<BounsReferee> findBounRefereeByOpenId(String openid);
}
