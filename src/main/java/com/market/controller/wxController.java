package com.market.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.market.base.response.ServerResponse;
import com.market.config.WXPayConfig;
import com.market.domain.Area;
import com.market.domain.Shop;
import com.market.domain.User;
import com.market.service.AreaService;
import com.market.service.ShopService;
import com.market.service.UserService;
import com.market.util.CommonUtil;
import com.market.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/market/weixin")
public class wxController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    /**
     * 功能描述：获取微信openid
     * @param jscode
     * @return
     * @author caoyong
     * @date 2019/1/21 9:59
     */
    @GetMapping("/jscode/{jscode}")
    public String getOpenid(@PathVariable("jscode") String jscode) {
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

        String requestUrl = WX_URL.replace("APPID", WXPayConfig.APPID).
                replace("SECRET", WXPayConfig.SECRET).replace("JSCODE", jscode);
        // 发起GET请求获取凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (jsonObject != null) {
            try {
                return jsonObject.getString("openid");
            } catch (JSONException e) {
                // 获取token失败
            }
        } else {
        }
        return null;
    }

    /**
     * 功能描述：添加微信小程序用户
     * @param openid
     * @return
     * @author caoyong
     * @date 2019/1/21 10:07
     */
    @GetMapping("/openid/{openid}")
    public ResponseEntity<Response> addUser(@PathVariable("openid") String openid) {
        //根据openid查询User是否存在
        User user = userService.findUserByOpenid(openid);
        if(user == null) {
            user = new User();
            user.setCreateAt(new Date());
            user.setModifyAt(new Date());
            user.setAvatar(null);
            user.setEmail(null);
            user.setMobile(null);
            user.setName(null);
            user.setEncodePassword("123456");
            user.setUsername(openid);
            user.setAlipayid(null);
            user.setOpenid(openid);
            user.setType(1);//type:1 微信登录

            userService.saveT(user);
        }
        return ResponseEntity.ok().body(new Response(true, "添加用户成功！"));
    }

    /**
     * 功能描述：微信小程序根据店铺名、店铺类型和店铺位置查询商家列表
     * @param name
     * @return
     * @author caoyong
     * @date 2019/1/16 13:53
     */
    @GetMapping("/name/{name}/type/{type}/area/{area}")
    public ServerResponse<JSONArray> shopList(@PathVariable("name") String name,
                                              @PathVariable("type") String type,
                                              @PathVariable("area") String area) {
        JSONArray jsonArray = new JSONArray();
        List<Shop> shopList = new ArrayList<>();
        Shop s = new Shop();
        if("null".equals(name)) {
            s.setName(name);
            shopList = shopService.findShop(s);
        } else {
            shopList = shopService.findShopByName(name);
        }

        for(int i=0;i<shopList.size();i++) {
            Shop shop = shopList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("addr", shop.getAddr());
            jsonObject.put("name", shop.getName());
            jsonObject.put("pic", shop.getPic());
            jsonObject.put("info", shop.getInfo());
            jsonObject.put("mobile", shop.getMobile());
            jsonArray.add(jsonObject);
        }

        return ServerResponse.createBySuccess(jsonArray);
    }

    /**
     * 功能描述：微信小程序搜索商品页面下的点击“附近”查询市所属的所有区县
     * @param province
     * @return
     * @author caoyong
     * @date 2019/1/25 13:34
     */
    @GetMapping("/province/{province}")
    public ServerResponse<JSONArray> countryList(@PathVariable("province") String province) {
        JSONArray jsonArray = new JSONArray();
        //根据市name查询code
        Area area = areaService.findAreaByName(province);
        //根据市code(区县parent_code)查询区县列表
        List<Area> countryList = areaService.findAreaByCode(area.getCode());
        for(int i=0; i<countryList.size(); i++) {
            Area a = countryList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", a.getName());
            jsonObject.put("code", a.getCode());
            jsonObject.put("parentCode", a.getParentCode());
            jsonObject.put("level", a.getLevel());

            jsonArray.add(jsonObject);
        }

        return ServerResponse.createBySuccess(jsonArray);
    }

}
