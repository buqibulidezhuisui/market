package com.market.service;

import com.market.domain.Area;

import java.util.List;

public interface AreaService {
    public Area findAreaByName(String name);

    public List<Area> findAreaByCode(String code);
}
