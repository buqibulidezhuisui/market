package com.market.mapper;

import com.market.domain.Coupons;
import com.market.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName CouponsMapper
 * @Description TODO
 * @date 19-1-11 下午2:27
 * @Author hanbing
 */

@Repository
public interface CouponsMapper extends BaseMapper<Coupons> {

    //    根据id查找优惠卷
    public Coupons findCouponsById(Long id);
    //    根据用户ID查找优惠券
    public List<Coupons> findCouponsByUserId(String userId);
    //    根据商家id查找优惠券
    public List<Coupons> findCouponsByBussId(String bussId);
    //    根据商家id和用户ID查找优惠券
    public List<Coupons> findCouponsByBussIdAndUserId(String bussId,String userId);

}
