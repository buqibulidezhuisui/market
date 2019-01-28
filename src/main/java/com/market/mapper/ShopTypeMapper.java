package com.market.mapper;

import com.market.domain.ShopType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName ShopTypeMapper
 * @Description TODO
 * @date 19-1-25 下午5:02
 * @Author hanbing
 */

@Repository
public interface ShopTypeMapper {
    List<ShopType> findBigType();
    List<ShopType> findSmailTypeByBigCode(String parentCode);
    ShopType findByCode(String code);
}
