package com.market.service;

import com.market.domain.Order;

import java.util.List;

/**
 * @InterfaceName: OrderService
 * @Description: 订单相关方法
 * @Author: 24878
 * @Date: 2019/2/1 16:21
 */
public interface OrderService extends BaseService<Order> {
    List<Order> findOrderByOpenId(String openid);
    List<Order> findOrderByAliId(String aliId);
    List<Order> findOrderByBussnessId(String bussnessid);
    int saveAsWX(Order order);

}
