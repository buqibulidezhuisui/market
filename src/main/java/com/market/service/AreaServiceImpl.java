package com.market.service;

import com.github.pagehelper.PageInfo;
import com.market.domain.Area;
import com.market.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AreaServiceImpl
 * @Description TODO
 * @date 19-1-25 上午10:30
 * @Author hanbing
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaMapper areaMapper;

    @Override
    public List<Area> findAllProvince() {
        return areaMapper.findAllProvince();
    }

    @Override
    public List<Area> findCityByParentCode(String parentCode) {
        return areaMapper.findCityByParentCode(parentCode);
    }

    @Override
    public int saveT(Area area) {
        return 0;
    }

    @Override
    public PageInfo<Area> findAll(Area area, int pageNum) {
        return null;
    }

    @Override
    public Area findT(Area area) {
        return null;
    }

    @Override
    public void removeT(Area area) {

    }
    public Area findAreaByName(String name) {
        return areaMapper.findAreaByName(name);
    }

    public List<Area> findAreaByCode(String code) {
        return areaMapper.findAreaByCode(code);
    }

}
