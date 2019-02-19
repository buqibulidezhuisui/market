package com.market.mapper;

import com.market.domain.Order;
import com.market.mapper.common.BaseMapper;
import com.market.domain.OrderForMini;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: OrderMapper
 * @Description: TODO
 * @Author: 24878
 * @Date: 2019/2/1 16:30
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {
    List<OrderForMini> findOrderByOpenId(String openid);
    List<OrderForMini> findOrderByAliId(String aliId);
    List<Order> findOrderByBusinessId(String bussnessid);
    int saveAsWX(Order order);
}
