package com.market.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.market.base.response.ServerResponse;
import com.market.config.WXPayConfig;
import com.market.domain.Area;
import com.market.domain.Shop;
import com.market.domain.ShopType;
import com.market.domain.User;
import com.market.service.AreaService;
import com.market.service.ShopService;
import com.market.service.ShopTypeService;
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

    @Autowired
    private ShopTypeService shopTypeService;

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
     *           获取推荐人的openid，插入user表的"referee"字段中
     * @param openid
     * @param referee
     * @return
     * @author caoyong
     * @date 2019/1/21 10:07
     *       2019/2/13 10:47
     */
    @GetMapping("/openid/{openid}/referee/{referee}")
    public ResponseEntity<Response> addUser(@PathVariable("openid") String openid,
                                            @PathVariable("referee") String referee) {
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
            if("null".equals(referee)) {//打开小程序
                user.setReferee(null);
            } else {//打开好友转发的小程序
                user.setReferee(referee);//推荐人openid
            }

            userService.saveT(user);
        }
        return ResponseEntity.ok().body(new Response(true, "添加用户成功！"));
    }

    /**
     * 功能描述：微信小程序根据店铺名、店铺类型和店铺位置查询商家列表
     * @param name
     * @param type
     * @param area
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
        s.setName(name);
        s.setType(type);
        s.setArea(area);
        shopList = shopService.findShopByNameAndTypeAndArea(s);

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
     * 功能描述：查询商品的所有类型列表
     *           根据shop数据表查询店铺的类型，并且动态地显示在小程序的首页
     * @return
     * @author caoyong
     * @date 2019/1/28 13:21
     *       2019/2/11 13:47
     */
    @GetMapping("/shopType/{id}")
    public ServerResponse<JSONArray> shopTypeList(@PathVariable("id") Long id) {
        JSONArray jsonArray = new JSONArray();
        List<ShopType> shopTypeList = new ArrayList<>();
        if(id == 1) {//小程序首页
            shopTypeList = shopTypeService.findShopTypeListByShop();
            for(int i=0; i<shopTypeList.size();i++) {
                JSONObject jsonObject = new JSONObject();
                ShopType shopType = shopTypeList.get(i);
                if("01".equals(shopType.getCode())) {//热门
                    jsonObject.put("class", "icon-remen");
                } else if("02".equals(shopType.getCode())) {//美食
                    jsonObject.put("class", "icon-meishi");
                } else if("03".equals(shopType.getCode())) {//酒店
                    jsonObject.put("class", "icon-jiudian");
                } else if("04".equals(shopType.getCode())) {//周边游
                    jsonObject.put("class", "icon-zhoubian");
                } else if("05".equals(shopType.getCode())) {//休闲娱乐
                    jsonObject.put("class", "icon-xiuxian");
                } else if("06".equals(shopType.getCode())) {//学习培训
                    jsonObject.put("class", "icon-xuexi");
                } else if("07".equals(shopType.getCode())) {//购物
                    jsonObject.put("class", "icon-gouwu");
                } else if("08".equals(shopType.getCode())) {//度假
                    jsonObject.put("class", "icon-dujia");
                } else if("09".equals(shopType.getCode())) {//宴会
                    jsonObject.put("class", "icon-yanhui");
                } else if("10".equals(shopType.getCode())) {//时尚购
                    jsonObject.put("class", "icon-shishang");
                } else if("11".equals(shopType.getCode())) {//旅游
                    jsonObject.put("class", "icon-lvyou");
                } else if("12".equals(shopType.getCode())) {//丽人
                    jsonObject.put("class", "icon-liren");
                } else if("13".equals(shopType.getCode())) {//生活服务
                    jsonObject.put("class", "icon-shenghuo");
                } else if("14".equals(shopType.getCode())) {//运动健身
                    jsonObject.put("class", "icon-jianshen");
                } else if("15".equals(shopType.getCode())) {//母婴亲子
                    jsonObject.put("class", "icon-muying");
                } else if("16".equals(shopType.getCode())) {//宠物
                    jsonObject.put("class", "icon-chongwu");
                } else if("17".equals(shopType.getCode())) {//汽车服务
                    jsonObject.put("class", "icon-qiche");
                } else if("18".equals(shopType.getCode())) {//摄影写真
                    jsonObject.put("class", "icon-sheying");
                } else if("19".equals(shopType.getCode())) {//结婚
                    jsonObject.put("class", "icon-jiehun");
                } else if("20".equals(shopType.getCode())) {//家装
                    jsonObject.put("class", "icon-jiazhuang");
                } else if("21".equals(shopType.getCode())) {//医疗
                    jsonObject.put("class", "icon-yiliao");
                } else if("22".equals(shopType.getCode())) {//出境游
                    jsonObject.put("class", "icon-chujing");
                } else if("23".equals(shopType.getCode())) {//抽奖
                    jsonObject.put("class", "icon-choujiang");
                }
                jsonObject.put("code", shopType.getCode());
                jsonObject.put("name", shopType.getName());
                jsonObject.put("parentCode", shopType.getParentCode());

                jsonArray.add(jsonObject);
            }
        } else {//小程序搜索页面
            shopTypeList = shopTypeService.findBigType();
            for(int i=0; i<shopTypeList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                ShopType shopType = shopTypeList.get(i);
                jsonObject.put("code", shopType.getCode());
                jsonObject.put("name", shopType.getName());
                jsonObject.put("parentCode", shopType.getParentCode());

                jsonArray.add(jsonObject);
            }
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

    /**
     * 功能描述：根据经纬度计算各店铺与当前位置的距离
     * @param name
     * @param type
     * @param area
     * @param distance
     * @param longitude
     * @param latitude
     * @return
     * @author caoyong
     * @date 2019/2/1 10:21
     */
    @GetMapping("/shopList/name/{name}/type/{type}/area/{area}/distance/{distance}/longitude/{longitude}/latitude/{latitude}")
    public ServerResponse<JSONArray> getShopList(@PathVariable("name") String name,
                                                 @PathVariable("type") String type,
                                                 @PathVariable("area") String area,
                                                 @PathVariable("distance") double distance,
                                                 @PathVariable("longitude") double longitude,
                                                 @PathVariable("latitude") double latitude) {
        JSONArray jsonArray = new JSONArray();

        Shop shop = new Shop();
        shop.setName(name);
        shop.setType(type);
        shop.setDistance(distance);
        shop.setLongitude(longitude);
        shop.setLatitude(latitude);
        shop.setArea(area);
        List<Shop> shopList = shopService.findShopByNameAndTypeAndAreaAndDiscount(shop);
        for(int i=0;i<shopList.size();i++) {
            Shop s = shopList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", s.getCode());
            jsonObject.put("addr", s.getAddr());
            jsonObject.put("name", s.getName());
            jsonObject.put("pic", s.getPic());
            jsonObject.put("info", s.getInfo());
            jsonObject.put("mobile", s.getMobile());
            jsonObject.put("area", s.getArea());
            jsonObject.put("type", s.getType());
            jsonObject.put("distance", s.getDistance());
            jsonObject.put("longitude", s.getLongitude());
            jsonObject.put("latitude", s.getLatitude());

            jsonArray.add(jsonObject);
        }

        return ServerResponse.createBySuccess(jsonArray);
    }

}
