package com.market.service;

import com.market.domain.Area;
import com.market.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    public Area findAreaByName(String name) {
        return areaMapper.findAreaByName(name);
    }

    public List<Area> findAreaByCode(String code) {
        return areaMapper.findAreaByCode(code);
    }
}
