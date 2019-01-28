package com.market.mapper;

import com.market.domain.Shop;
import com.market.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName ShopMapper
 * @Description TODO
 * @date 19-1-16 上午10:07
 * @Author hanbing
 */
@Repository
public interface ShopMapper extends BaseMapper<Shop> {


    public Shop findById(Long id);

    //通过name,bussid,addr进行模糊查找
    public List<Shop> findLikeBussIdAndAddrAndName(Shop shop);


    int updateById(Shop t);
    public void deleteById(Long id);

    public List<Shop> findShopByNameAndTypeAndArea(Shop shop);
}
