package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.market.domain.ShopType;
import com.market.service.ShopTypeService;
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
@RequestMapping(value = "/shoptype")
public class ShopTypeController {

    @Autowired
    ShopTypeService shopTypeService;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/big")
    public JSONArray findAllProvince(Model model) {
        List<ShopType> bigType = shopTypeService.findBigType();
        JSONArray array = new JSONArray();
//        model.addAttribute("areaList",allProvince);
        for (ShopType a : bigType) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", a.getCode());
            jsonObject.put("full_name", a.getName());
            array.add(jsonObject);
        }
        return array;

    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/parentcode/{parentcode}")
    public JSONArray findByParent(@PathVariable("parentcode") String parentcode) {
        List<ShopType> smailTypeByBigCode = shopTypeService.findSmailTypeByBigCode(parentcode);
        JSONArray array = new JSONArray();
//        model.addAttribute("areaList",allProvince);


        for (ShopType a : smailTypeByBigCode) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", a.getCode());
            jsonObject.put("full_name", a.getName());
            array.add(jsonObject);
        }

        return array;
    }
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/code/{code}")
    public JSONArray findBycode(@PathVariable("code") String parentcode) {
        ShopType a = shopTypeService.findByCode(parentcode);
        JSONArray array = new JSONArray();
//        model.addAttribute("areaList",allProvince);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", a.getCode());
            jsonObject.put("full_name", a.getName());
            array.add(jsonObject);


        return array;
    }

}
