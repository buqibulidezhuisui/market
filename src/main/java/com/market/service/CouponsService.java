package com.market.service;

import com.market.domain.Coupons;

import java.util.List;

/**
 * @InterfaceName CouponsService
 * @Description TODO
 * @date 19-1-11 下午2:22
 * @Author hanbing
 */

public interface CouponsService extends BaseService<Coupons>{
//    根据id查找优惠卷
    public Coupons findCouponsById(Long id);
//    根据用户ID查找优惠券
    public List<Coupons> findCouponsByUserId(Long userId);
//    根据商家id查找优惠券
    public List<Coupons> findCouponsByBussId(Long bussId);
//    根据商家id和用户ID查找优惠券
    public List<Coupons> findCouponsByBussIdAndUserId(Long bussId,Long userId);
}
