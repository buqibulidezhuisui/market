package com.market.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.market.config.ParamCommon;
import com.market.domain.Coupons;
import com.market.mapper.CouponsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CouponsServiceImpl
 * @Description TODO
 * @date 19-1-11 下午2:28
 * @Author hanbing
 */
@Service
public class CouponsServiceImpl implements CouponsService {

    @Autowired
    public CouponsMapper couponsMapper;

    @Override
    public Coupons findCouponsById(Long id) {
        Coupons coupons = couponsMapper.findCouponsById(id);
        return coupons;
    }

    @Override
    public List<Coupons> findCouponsByUserId(Long userId) {
        return couponsMapper.findCouponsByUserId(userId);
    }

    @Override
    public List<Coupons> findCouponsByBussId(Long bussId) {
        return couponsMapper.findCouponsByBussId(bussId);
    }

    @Override
    public List<Coupons> findCouponsByBussIdAndUserId(Long bussId, Long userId) {
        List<Coupons> couponsByBussIdAndUserId = couponsMapper.findCouponsByBussIdAndUserId(bussId, userId);
        return couponsByBussIdAndUserId;
    }

    @Override
    public int saveT(Coupons coupons) {
        return couponsMapper.saveT(coupons);
    }

    @Override
    public int updateT(Coupons coupons) {
        return 0;
    }

    @Override
    public PageInfo<Coupons> findAll(Coupons coupons, int pageNum) {
        PageHelper.startPage(pageNum, ParamCommon.QUERY_PAGE_SIZE);
        List<Coupons> list = couponsMapper.findAll(coupons);
        PageInfo<Coupons> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Coupons findT(Coupons coupons) {
        return couponsMapper.findT(coupons);
    }

    @Override
    public void removeT(Coupons coupons) {

    }
}
