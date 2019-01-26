package com.market.service;

import com.market.domain.ShopType;
import com.market.mapper.ShopTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ShopTypeServiceImpl
 * @Description TODO
 * @date 19-1-25 下午5:12
 * @Author hanbing
 */
@Service
public class ShopTypeServiceImpl implements ShopTypeService {

    @Autowired
    ShopTypeMapper shopTypeMapper;


    @Override
    public List<ShopType> findBigType() {
        return shopTypeMapper.findBigType();
    }

    @Override
    public List<ShopType> findSmailTypeByBigCode(String parentCode) {
        return shopTypeMapper.findSmailTypeByBigCode(parentCode);
    }
}
