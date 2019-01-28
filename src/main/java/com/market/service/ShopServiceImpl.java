package com.market.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.market.config.ParamCommon;
import com.market.domain.Shop;
import com.market.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ShopServiceImpl
 * @Description TODO
 * @date 19-1-16 上午10:06
 * @Author hanbing
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopMapper shopMapper;



    @Override
    public PageInfo<Shop> findLikeBussIdAndAddrAndName(Shop shop, int pageNum) {
        PageHelper.startPage(pageNum, ParamCommon.QUERY_PAGE_SIZE);
        List<Shop> likeAddr = shopMapper.findLikeBussIdAndAddrAndName(shop);
        PageInfo<Shop> shopPageInfo = new PageInfo<>(likeAddr);
        return shopPageInfo;
    }

    @Override
    public Shop findById(Long id) {
        Shop byId = shopMapper.findById(id);
        return byId;
    }

    @Override
    public int updateById(Shop t) {
        shopMapper.updateById(t);
        return 0;
    }


    @Override
    public void deleteUserById(Long id) {
        shopMapper.deleteById(id);
    }

    @Override
    public int saveT(Shop shop) {
        shopMapper.saveT(shop);
        return 0;
    }

    @Override
    public PageInfo<Shop> findAll(Shop shop, int pageNum) {
        PageHelper.startPage(pageNum, ParamCommon.QUERY_PAGE_SIZE);
        List<Shop> all = shopMapper.findAll(shop);
        PageInfo<Shop> shopPageInfo = new PageInfo<>(all);
        return shopPageInfo;
    }

    @Override
    public Shop findT(Shop shop) {
        return null;
    }

    @Override
    public void removeT(Shop shop) {


    }

    @Override
    public List<Shop> findShopByNameAndTypeAndArea(Shop shop) {
        return shopMapper.findShopByNameAndTypeAndArea(shop);
    }
}
