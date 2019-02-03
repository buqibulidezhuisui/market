package com.market.util.PayWX;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.market.config.WXPayConfig;
import com.market.vo.PayWX;
import org.jdom.JDOMException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Auther: hanbing
 * @Description:
 */
public class OrderServlet {


    public JSONObject doGet(String openid,String title,int fee)
            throws ServletException, IOException {
        //时间戳
        String times = System.currentTimeMillis() + "";
        String appid = PayWX.APPID;
        String mch_id = PayWX.MCH_ID;//商户ID
        String apiKey = PayWX.API_KEY;//32位密钥
        String tradeNo = times + RandomNumUtil.getCard();
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", times);//时间戳
        packageParams.put("body", title);//支付主体
        packageParams.put("out_trade_no", tradeNo);//编号
        packageParams.put("total_fee", fee);//价格
        // packageParams.put("spbill_create_ip", getIp2(request));这里之前加了ip，但是总是获取sign失败，原因不明，之后就注释掉了
        packageParams.put("notify_url", "/notify");//支付返回地址，不用纠结这个东西，我就是随便写了一个接口，内容什么都没有
        packageParams.put("trade_type", "JSAPI");//这个api有，固定的
        packageParams.put("sign_type", "MD5");
        packageParams.put("openid", openid);//openid
        //获取sign
        String sign = PayCommonUtil.createSign("UTF-8", packageParams, apiKey);//最后这个是自己设置的32位密钥
        packageParams.put("sign", sign);
        System.out.println("sign="+sign);
        //转成XML
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        System.out.println(requestXML);
        //得到含有prepay_id的XML
        String resXml = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);
        System.out.println(resXml);
        //解析XML存入Map
        Map map = null;
        String json = "";
        JSONObject jsonObject = new JSONObject();
        try {
            map = XMLUtil.doXMLParse(resXml);
            System.out.println(map);
            // String return_code = (String) map.get("return_code");
            //得到prepay_id
            String prepay_id = (String) map.get("prepay_id");
            SortedMap<Object, Object> packageP = new TreeMap<Object, Object>();
            packageP.put("appId", appid);//！！！注意，这里是appId,上面是appid，真怀疑写这个东西的人。。。
            packageP.put("nonceStr", times);//时间戳
            packageP.put("package", "prepay_id=" + prepay_id);//必须把package写成 "prepay_id="+prepay_id这种形式
            packageP.put("signType", "MD5");//paySign加密
            packageP.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
            //得到paySign
            String paySign = PayCommonUtil.createSign("UTF-8", packageP, apiKey);
            packageP.put("paySign", paySign);
            //将packageP数据返回给小程序
            Gson gson = new Gson();
            json = gson.toJson(packageP);
            System.out.println(json);

            jsonObject.put("timeStamp",(System.currentTimeMillis() / 1000) + "");
            jsonObject.put("nonceStr",times);
            jsonObject.put("package","prepay_id=" + prepay_id);
            jsonObject.put("signType","MD5");
            jsonObject.put("paySign",paySign);
            jsonObject.put("tradeNo",tradeNo);

        } catch (JDOMException e) {
            System.out.println(e.getMessage());
        }
        return jsonObject;
    }

}
