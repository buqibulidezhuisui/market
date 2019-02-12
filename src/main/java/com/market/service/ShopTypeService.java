package com.market.service;

import com.market.domain.ShopType;

import java.util.List;

/**
 * @InterfaceName ShopTypeService
 * @Description TODO
 * @date 19-1-25 下午5:10
 * @Author hanbing
 */
public interface ShopTypeService {
    List<ShopType> findBigType();
    List<ShopType> findSmailTypeByBigCode(String parentCode);
    ShopType findByCode(String code);
    List<ShopType> findShopTypeListByShop();
}
