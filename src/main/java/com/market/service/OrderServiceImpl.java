package com.market.service;

import com.github.pagehelper.PageInfo;
import com.market.domain.Order;
import com.market.domain.SumBonus;
import com.market.mapper.OrderMapper;
import com.market.domain.OrderForMini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClaseName: OrderServiceImpl
 * @Description: 订单相关方法实现
 * @Author: 24878
 * @Date: 2019/2/1 16:22
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;


    @Override
    public int saveT(Order order) {
        int i = orderMapper.saveT(order);
        return i;
    }

    @Override
    public int updateT(Order order) {
        return 0;
    }

    @Override
    public PageInfo<Order> findAll(Order order, int pageNum) {
        List<Order> all = orderMapper.findAll(order);

        return null;
    }

    @Override
    public Order findT(Order order) {
        return null;
    }

    @Override
    public void removeT(Order order) {

    }



    @Override
    public List<OrderForMini> findOrderByOpenId(String openid) {
        List<OrderForMini> orderByOpenId = orderMapper.findOrderByOpenId(openid);
        return orderByOpenId;
    }

    @Override
    public List<OrderForMini> findOrderByAliId(String aliId) {
        return orderMapper.findOrderByAliId(aliId);
    }

    @Override
    public List<Order> findOrderByBusinessId(String bussnessid) {
        return orderMapper.findOrderByBusinessId(bussnessid);
    }

    @Override
    public int saveAsWX(Order order) {
        int i = orderMapper.saveAsWX(order);
        return i;
    }

    @Override
    public Order findOrderByOrderNo(String orderNo){
        Order order = orderMapper.findOrderByOrderNo(orderNo);
        return order;
    }

    @Override
    public SumBonus findOrderSumCouponByOpenId(String openid) {
        SumBonus orderSumCouponByOpenId = orderMapper.findOrderSumCouponByOpenId(openid);
        return orderSumCouponByOpenId;
    }
}
