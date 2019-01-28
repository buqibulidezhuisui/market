package com.market.config;

public class WXPayConfig {
    // 微信分配的小程序ID
    public static String APPID = "wx7eee36653da30f6c";
    //商户ID
    public static String MCHID = "1502097461";
    //密钥 32位
    public static String API_KEY = "zhangyuanzhinengyangming19810624";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String NOTIFY_URL = "";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String RETURN_URL = "";
    // 统一下单接口返回的 prepay_id 参数值
    public static String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // MD5
    public static String SIGNTYPE = "MD5";
    //
    public static final String SECRET ="f7e97e014e39b35b2319023f9a6329cc";// "b8dd3b51195c5de9d7d6d827b287f059";

    public static final String GATE_WAY = "https://api.weixin.qq.com/sns/jscode2session";



}
