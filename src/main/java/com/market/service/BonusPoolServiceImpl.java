package com.market.service;

import com.github.pagehelper.PageInfo;
import com.market.domain.BonusPool;
import com.market.mapper.BonusPoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: Ruizhi
 * @Description:
 */
@Service
public class BonusPoolServiceImpl implements BonusPoolService{

    @Autowired
    private BonusPoolMapper bonusPoolMapper;

    @Override
    public int saveT(BonusPool bonusPool) {
        return 0;
    }

    @Override
    @Transactional
    public int updateT(BonusPool bonusPool) {
        return bonusPoolMapper.updateT(bonusPool);
    }

    @Override
    public PageInfo<BonusPool> findAll(BonusPool bonusPool, int pageNum) {
        return null;
    }

    @Override
    public BonusPool findT(BonusPool bonusPool) {
        return bonusPoolMapper.findT(bonusPool);
    }

    @Override
    public void removeT(BonusPool bonusPool) {

    }
}
