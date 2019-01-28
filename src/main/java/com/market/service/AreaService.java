package com.market.service;

import com.market.domain.Area;

import java.util.List;

/**
 * @InterfaceName AreaService
 * @Description TODO
 * @date 19-1-25 上午10:27
 * @Author hanbing
 */
public interface AreaService extends BaseService<Area>{
   List<Area> findAllProvince();
   List<Area> findCityByParentCode(String parentCode);


}
