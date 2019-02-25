package com.market.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.market.base.response.ServerResponse;
import com.market.config.WXPayConfig;
import com.market.domain.*;
import com.market.service.*;
import com.market.util.Arith;
import com.market.util.CommonUtil;
import com.market.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private BonusRefereeService bonusRefereeService;
    @Autowired
    private CouponsService couponsService;

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
    @GetMapping("/name/{name}/type/{type}/area/{area}/limit/{limit}")
    public ServerResponse<JSONArray> shopList(@PathVariable("name") String name,
                                              @PathVariable("type") String type,
                                              @PathVariable("area") String area,
                                              @PathVariable("limit") Long limit) {
        JSONArray jsonArray = new JSONArray();
        List<Shop> shopList = new ArrayList<>();
        Shop s = new Shop();
        s.setName(name);
        s.setType(type);
        s.setArea(area);
        s.setLimit(limit);
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
     * @param limit
     * @return
     * @author caoyong
     * @date 2019/2/1 10:21
     */
    @GetMapping("/shopList/name/{name}/type/{type}/area/{area}/distance/{distance}/longitude/{longitude}/latitude/{latitude}/limit/{limit}")
    public ServerResponse<JSONArray> getShopList(@PathVariable("name") String name,
                                                 @PathVariable("type") String type,
                                                 @PathVariable("area") String area,
                                                 @PathVariable("distance") double distance,
                                                 @PathVariable("longitude") double longitude,
                                                 @PathVariable("latitude") double latitude,
                                                 @PathVariable("limit") Long limit) {
        JSONArray jsonArray = new JSONArray();

        Shop shop = new Shop();
        shop.setName(name);
        shop.setType(type);
        shop.setDistance(distance);
        shop.setLongitude(longitude);
        shop.setLatitude(latitude);
        shop.setArea(area);
        shop.setLimit(limit);
        List<Shop> shopList = shopService.findShopByNameAndTypeAndAreaAndDiscount(shop);
        for(int i=0;i<shopList.size();i++) {
            Shop s = shopList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", s.getId());
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

    /**
     * 功能描述：查询小程序用户是否为商家（是，则显示商家；否，则反之）
     * @param openid
     * @return
     * @author caoyong
     * @date 2019/2/14 16:53
     */
    @GetMapping("/sellerManage/openid/{openid}")
    public ServerResponse<JSONObject> getAuthority(@PathVariable("openid") String openid) {
        JSONObject jsonObject = new JSONObject();
        Shop shop = shopService.findShopByOpenid(openid);
        if(shop == null) {//不是商家
            jsonObject.put("authority", 0);
        } else {//是商家
            jsonObject.put("authority", 1);
        }

        return ServerResponse.createBySuccess(jsonObject);
    }
    //小程序获取订单信息
    @GetMapping(value = "/getorder/openid/{openid}")
    public ServerResponse<JSONArray> getorder(@PathVariable("openid")String openid){
        List<OrderForMini> orderByOpenId = orderService.findOrderByOpenId(openid);
        JSONArray objects = new JSONArray();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OrderForMini o :
                orderByOpenId) {
            String format = format0.format(o.getCreateAt());

            Gson gson = new Gson();
            String json = gson.toJson(o);
            JSONObject jsonObject = JSONObject.parseObject(json);
            jsonObject.put("createAt",format);
            objects.add(jsonObject);

        }
        System.out.println(objects);
        return ServerResponse.createBySuccess(objects);
    }

    @GetMapping(value = "/getbusiness/businessid/{businessid}")
    public ServerResponse<JSONObject> getbusiness(@PathVariable("businessid")Long businessId){
        Shop byId = shopService.findById(businessId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("info",byId.getInfo());
        jsonObject.put("name",byId.getName());
        jsonObject.put("addr",byId.getAddr());
        jsonObject.put("pic",byId.getPic());
        return ServerResponse.createBySuccess(jsonObject);

    }
    @GetMapping(value = "/getbounsreferee/openid/{openid}")
    public ServerResponse<JSONArray> getBounsReferee(@PathVariable("openid")String openid){
        List<BounsReferee> bounRefereeByOpenId = bonusRefereeService.findBounRefereeByOpenId(openid);
        JSONArray objects = new JSONArray();
        for (BounsReferee b :
                bounRefereeByOpenId) {
            String nickname = "***"+ b.getNickname().substring(b.getNickname().length()-6);
            Date createAt = b.getCreateAt();
            Double bouns = b.getBonus();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            String date = df.format(createAt);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nickname",nickname);
            jsonObject.put("createAt",date);
            jsonObject.put("bonus",bouns);
            objects.add(jsonObject);
        }
        return ServerResponse.createBySuccess(objects);
    }
    @GetMapping(value = "/getbounsrefereesum/openid/{openid}")
    public ServerResponse<JSONObject> getBounsRefereeSum(@PathVariable("openid")String openid){
        List<BounsReferee> bounRefereeByOpenId = bonusRefereeService.findBounRefereeByOpenId(openid);
        double sumBouns = 0d;
        for (BounsReferee b :
                bounRefereeByOpenId) {
            sumBouns= Arith.round(Arith.add(sumBouns,b.getBonus()),2);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bonussum",sumBouns);

        return ServerResponse.createBySuccess(jsonObject);
    }
    @GetMapping(value = "/getbonusinfo/openid/{openid}")
    public ServerResponse<JSONObject> getbonusinfo(@PathVariable("openid")String openid){
//        已使用红包总额
//        个人消消费红包
        SumBonus orderSumCouponByOpenId = orderService.findOrderSumCouponByOpenId(openid);

//        赏金总额
        List<BounsReferee> bounRefereeByOpenId = bonusRefereeService.findBounRefereeByOpenId(openid);
        double sumBouns = 0d;
        for (BounsReferee b :
                bounRefereeByOpenId) {
            sumBouns= Arith.round(Arith.add(sumBouns,b.getBonus()),2);
        }
//        剩余红包
        Double money;
        List<Coupons> couponsByUserId = couponsService.findCouponsByUserId(openid);
        if(couponsByUserId.size()<1){
            money=Arith.round(0,2);
        }else {
            Coupons coupons = couponsByUserId.get(0);
            money = coupons.getMoney();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ysyr",orderSumCouponByOpenId.getSumDeCoupon());
        jsonObject.put("ppr",orderSumCouponByOpenId.getSumInCoupon());
        jsonObject.put("sjr",sumBouns);
        jsonObject.put("money",money);
        return ServerResponse.createBySuccess(jsonObject);
    }
}
