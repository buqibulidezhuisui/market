package com.market.mapper;

import com.market.domain.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName AreaMapper
 * @Description TODO
 * @date 19-1-25 上午10:30
 * @Author hanbing
 */
@Repository
public interface AreaMapper {
    List<Area> findAllProvince();
    List<Area> findCityByParentCode(String parentCode);
    public Area findAreaByName(String name);
    public List<Area> findAreaByCode(String code);
}
