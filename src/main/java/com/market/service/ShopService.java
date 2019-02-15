package com.market.service;

import com.github.pagehelper.PageInfo;
import com.market.domain.Shop;

import java.util.List;

/**
 * @InterfaceName ShopService
 * @Description TODO
 * @date 19-1-16 上午9:59
 * @Author hanbing
 */

public interface ShopService extends BaseService<Shop> {
    //    通过id找
    public PageInfo<Shop> findLikeBussIdAndAddrAndName(Shop shop, int pageNum);

    public Shop findById(Long id);

    int updateById(Shop t);
    public void deleteUserById(Long id);

    public List<Shop> findShopByNameAndTypeAndArea(Shop shop);

    public List<Shop> findShopByNameAndTypeAndAreaAndDiscount(Shop shop);

    public Shop findShopByOpenid(String openid);
}
