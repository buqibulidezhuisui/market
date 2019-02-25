package com.market.service;

import com.github.pagehelper.PageInfo;
import com.market.domain.BounsReferee;
import com.market.mapper.BonusRefereeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClaseName: BonusRefereeServiceImpl
 * @Description: TODO
 * @Author: 24878
 * @Date: 2019/2/21 10:23
 */
@Service
public class BonusRefereeServiceImpl implements BonusRefereeService {
    @Autowired
    BonusRefereeMapper bonusRefereeMapper;
    @Override
    public int saveT(BounsReferee bounsReferee) {
        int i = bonusRefereeMapper.saveT(bounsReferee);
        return i;
    }

    @Override
    public int updateT(BounsReferee bounsReferee) {
        return 0;
    }

    @Override
    public PageInfo<BounsReferee> findAll(BounsReferee bounsReferee, int pageNum) {
        return null;
    }

    @Override
    public BounsReferee findT(BounsReferee bounsReferee) {
        return null;
    }

    @Override
    public void removeT(BounsReferee bounsReferee) {

    }

    @Override
    public List<BounsReferee> findBounRefereeByOpenId(String openid) {
        List<BounsReferee> bounRefereeByOpenId = bonusRefereeMapper.findBounRefereeByOpenId(openid);
        return bounRefereeByOpenId;
    }
}
