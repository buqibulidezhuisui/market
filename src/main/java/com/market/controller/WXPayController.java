package com.market.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.market.base.response.ServerResponse;
import com.market.domain.BonusPool;
import com.market.domain.Coupons;
import com.market.domain.Order;
import com.market.domain.PoolHistory;
import com.market.service.BonusPoolService;
import com.market.service.CouponsService;
import com.market.service.OrderService;
import com.market.service.PoolHistoryService;
import com.market.util.Arith;
import com.market.util.BonusUtil;
import com.market.util.PayWX.OrderServlet;
import com.market.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/market/weixin")
public class WXPayController {
    @Autowired
    OrderService orderService;
    @Autowired
    BonusPoolService bonusPoolService;
    @Autowired
    CouponsService couponsService;
    @Autowired
    PoolHistoryService poolHistoryService;

    @RequestMapping(value = "/pay/openid/{openid}/title/{title}/amountfee/{amountfee}")
    public ServerResponse<JSONObject> goPay(@PathVariable("openid") String openid,
                                            @PathVariable("title") String title, @PathVariable("amountfee") int amountfee) throws ServletException, IOException {
        //获取微信支付请求所需信息
        OrderServlet orderServlet = new OrderServlet();
        JSONObject jsonObject = orderServlet.doGet(openid, title, amountfee);
        String tradeNo = jsonObject.getString("tradeNo");//

        return ServerResponse.createBySuccess(jsonObject);
    }

    @RequestMapping(value = "/marketPay/openid/{openId}/tradeNo/{tradeNo}/amountfee/{amountfee}/coupons/{coupons}/amount/{amount}")
    public ResponseEntity<Response> marketPay(@PathVariable("openId") String openId, @PathVariable("tradeNo") String tradeNo, @PathVariable("amountfee") Double amountfee, @PathVariable("coupons") Double coupons, @PathVariable("amount") Double amount) {
        System.out.println("正在保存订单信息");
        Order order = new Order();
        order.setOpenId(openId);
        order.setPayFee(Arith.div(amountfee, 100));
        order.setTradeNo(tradeNo);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");
        String format = df.format(date);
        String radomNum = getRadomNum();
        System.out.println(radomNum);
        String orderNo = format + radomNum;
        order.setOrderNo(orderNo);
        order.setType("2");
        orderService.saveAsWX(order);
        System.out.println("订单信息保存完成");

        System.out.println("折扣金信息开始更新");
        String s = this.addBonus(amount);
        List<Coupons> couponsByUserId = couponsService.findCouponsByUserId(openId);
        String bonus = this.getBonus();
        if (couponsByUserId.size() < 1) {//新用户
            Coupons coupons1 = new Coupons();
            coupons1.setMoney(Double.parseDouble(bonus));
            coupons1.setCouponsNo(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            coupons1.setUserId(openId);
            coupons1.setCreateAt(new Date());
            coupons1.setModifyAt(new Date());
            coupons1.setType(0);
            couponsService.saveT(coupons1);
        } else {//至少使用过一次的用户
            Coupons coupons1 = couponsByUserId.get(0);
            Double money = Arith.add(Arith.div(coupons, 100), Double.parseDouble(bonus));
            coupons1.setMoney(money);
            couponsService.updateT(coupons1);
        }
        System.out.println("用户折扣金信息保存完成");
        System.out.println("奖励金数据更新开始");

        PoolHistory poolHistory = new PoolHistory();
        Date poolHistoryUpDate = new Date();
        poolHistory.setCreateAt(poolHistoryUpDate);
        poolHistory.setModifyAt(poolHistoryUpDate);
        poolHistory.setOrderNo(orderNo);
        poolHistory.setPoolDown(Double.parseDouble(bonus));
        poolHistory.setPoolUp(Double.parseDouble(s));
        poolHistory.setUserId(openId);
        int i = poolHistoryService.saveT(poolHistory);
        System.out.println("奖励金数据更新完成");


        return ResponseEntity.ok().body(new Response(true, "支付成功"));
    }

    String getRadomNum() {
        String radomNum = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            radomNum += random.nextInt(10);
        }
        return radomNum;
    }

    //    获取奖励金额
    public String getBonus() {
        BonusPool bonusPoolQuery = new BonusPool();
        BonusPool bonusPool = bonusPoolService.findT(bonusPoolQuery);
        Assert.notNull(bonusPool, "奖池对象为空");
        Double bonus = BonusUtil.getBonus(bonusPool.getMoney());
        bonusPool.setMoney(Arith.sub(bonusPool.getMoney(), bonus));//更新奖池金额
        bonusPoolService.updateT(bonusPool);
        return bonus.toString();
    }

    //    奖励金贡献
    public String addBonus(Double amountSum) {
        Double addFee = Arith.mul(amountSum, 0.03);
        BonusPool bonusPoolQuery = new BonusPool();
        BonusPool bonusPool = bonusPoolService.findT(bonusPoolQuery);
        Assert.notNull(bonusPool, "奖池对象为空");
        Double money = Arith.add(bonusPool.getMoney(), addFee);
//        Double bonus = BonusUtil.getBonus(bonusPool.getMoney());
        bonusPool.setMoney(money);//更新奖池金额
        bonusPoolService.updateT(bonusPool);
        return addFee.toString();
    }

    @RequestMapping(value = "/getcoupon/userId/{userId}")
    public ServerResponse<JSONObject> getCoupon(@PathVariable("userId") String userId) {
        List<Coupons> couponsByUserId = couponsService.findCouponsByUserId(userId);
        Coupons c = couponsByUserId.get(0);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("couponfee", c.getMoney());
        jsonObject.put("type", "2");//获取折扣金信息

        return ServerResponse.createBySuccess(jsonObject);
    }

}
