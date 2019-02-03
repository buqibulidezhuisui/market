package com.market.service;

import com.github.pagehelper.PageInfo;
import com.market.domain.PoolHistory;
import com.market.mapper.PoolHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClaseName: PoolHistoryServiceImpl
 * @Description: TODO
 * @Author: 24878
 * @Date: 2019/2/3 14:51
 */
@Service
public class PoolHistoryServiceImpl implements PoolHistoryService {
    @Autowired
    PoolHistoryMapper poolHistoryMapper
    ;@Override
    public int saveT(PoolHistory poolHistory) {

        return poolHistoryMapper.saveT(poolHistory);
    }

    @Override
    public int updateT(PoolHistory poolHistory) {
        return 0;
    }

    @Override
    public PageInfo<PoolHistory> findAll(PoolHistory poolHistory, int pageNum) {
        return null;
    }

    @Override
    public PoolHistory findT(PoolHistory poolHistory) {
        return null;
    }

    @Override
    public void removeT(PoolHistory poolHistory) {

    }
}
