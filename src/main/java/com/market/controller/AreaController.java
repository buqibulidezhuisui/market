package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.market.domain.Area;
import com.market.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName AreaController
 * @Description TODO
 * @date 19-1-25 上午10:33
 * @Author hanbing
 */
@RestController
@RequestMapping(value = "/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/province")
    public JSONArray findAllProvince(Model model){
        List<Area> allProvince = areaService.findAllProvince();
        JSONArray array = new JSONArray();
//        model.addAttribute("areaList",allProvince);
        for (Area a:allProvince) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",a.getCode());
            jsonObject.put("full_name",a.getName());
            array.add(jsonObject);
        }
        return array;

    }

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/parentcode/{parentcode}")
    public JSONArray findByParent(@PathVariable("parentcode")String parentcode){
        List<Area> cityByParentCode = areaService.findCityByParentCode(parentcode);
        JSONArray array = new JSONArray();
//        model.addAttribute("areaList",allProvince);
        if(!parentcode.endsWith("00")){
            for (Area a:cityByParentCode) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",a.getCode());
                jsonObject.put("full_name",a.getName());
//                jsonObject.put("code",a.getCode());
                array.add(jsonObject);
            }
        }else{
            for (Area a:cityByParentCode) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",a.getCode());
                jsonObject.put("full_name",a.getName());
                array.add(jsonObject);
            }
        }
        return array;
    }
}
