package com.market.service;

import com.market.domain.Order;
import com.market.domain.OrderForMini;
import com.market.domain.SumBonus;

import java.util.List;

/**
 * @InterfaceName: OrderService
 * @Description: 订单相关方法
 * @Author: 24878
 * @Date: 2019/2/1 16:21
 */
public interface OrderService extends BaseService<Order> {
    List<OrderForMini> findOrderByOpenId(String openid);
    List<OrderForMini> findOrderByAliId(String aliId);
    List<Order> findOrderByBusinessId(String bussnessid);
    int saveAsWX(Order order);
    Order findOrderByOrderNo(String orderNo);
    SumBonus findOrderSumCouponByOpenId(String openid);

}
