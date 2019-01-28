package com.market.mapper;

import com.market.domain.Area;

import java.util.List;

public interface AreaMapper {
    public Area findAreaByName(String name);

    public List<Area> findAreaByCode(String code);
}
