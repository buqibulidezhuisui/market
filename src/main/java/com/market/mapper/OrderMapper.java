package com.market.mapper;

import com.market.domain.Order;
import com.market.mapper.common.BaseMapper;
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
    List<Order> findOrderByOpenId(String openid);
    List<Order> findOrderByAliId(String aliId);
    List<Order> findOrderByBussnessId(String bussnessid);
    int saveAsWX(Order order);
}
