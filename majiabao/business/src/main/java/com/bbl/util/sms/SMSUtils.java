package com.bbl.util.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bbl.cache.redis.StaticJedisUtils;
import com.bbl.util.HttpUtil2;
import com.bbl.util.encrypt.MD5Util;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/*
功能:		企信通PHP HTTP接口 发送短信
修改日期:	2014-03-19
说明:		http://api.cnsms.cn/?ac=send&uid=用户账号&pwd=MD5位32密码&mobile=号码&content=内容
状态:
	100 发送成功
	101 验证失败
	102 短信不足
	103 操作失败
	104 非法字符
	105 内容过多
	106 号码过多
	107 频率过快
	108 号码内容空
	109 账号冻结
	110 禁止频繁单条发送
	111 系统暂定发送
	112 号码不正确
	120 系统升级
*/
public class SMSUtils {

    public static String SMS_PREFIX = "sms.";

    private static Logger logger = LoggerFactory.getLogger(SMSUtils.class);

    //发送短信，uid，pwd，参数值请向企信通申请， tel：发送的手机号， content:发送的内容
    public static String send(String uid, String pwd, String tel, String content) throws IOException {

        // 创建StringBuffer对象用来操作字符串
        StringBuffer sb = new StringBuffer("http://api.cnsms.cn/?");

        // 向StringBuffer追加用户名
        sb.append("ac=send&uid=" + uid);//在此申请企信通uid，并进行配置用户名

        // 向StringBuffer追加密码（密码采用MD5 32位 小写）
        sb.append("&encode=utf8");

        // 向StringBuffer追加密码（密码采用MD5 32位 小写）
        sb.append("&pwd=" + MD5Util.string2MD5(pwd, null));//在此申请企信通uid，并进行配置密码

        // 向StringBuffer追加手机号码
        sb.append("&mobile=" + tel);
        // 向StringBuffer追加消息内容转URL标准码
        sb.append("&content=" + URLEncoder.encode(content, "utf8"));

        // 创建url对象
        URL url = new URL(sb.toString());

        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");

        // 发送
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        // 返回发送结果
        String inputline = in.readLine();
        return inputline;

    }


    /**
     * 发送短信
     * http://cr.1cloudsp.com  手机短信相关网站
     * @param mobile     手机号
     * @param content    手机内容
     * @param isAddRedis 是否插入到redis
     * @param expired    失效时间，单位秒  PS:只有isAddRedis为true时，该值设置才生效。
     * @return boolean
     */
    public static boolean send(String mobile, String content, boolean isAddRedis, Integer expired) {
        Map<String, Object> sendParam = Maps.newHashMap();
        sendParam.put("accesskey", "VlWat1BUjr606JEF");
        sendParam.put("secret", "2fvPIJGrDfpOyECnIh8DuzU7ZdiIga1u");
        sendParam.put("sign", "【此验证码90秒后过期】");
        sendParam.put("templateId", "426");
        sendParam.put("mobile", mobile);
        sendParam.put("content", content);

        try {

            String result = HttpUtil2.doPost("http://api.1cloudsp.com/intl/api/v2/send", sendParam);
            JSONObject data = JSON.parseObject(result, JSONObject.class);
//            JSONObject data =  JSONObject.fromObject(result);
            logger.error("手机验证码 手机号 ={} , 验证码 ={} ",mobile,content);
            if (isAddRedis &&"0".equals(data.getString("code")) &&"SUCCESS".equalsIgnoreCase(data.getString("msg"))) {
                String redisKey = SMS_PREFIX + mobile;
                if (StringUtils.isNotEmpty(StaticJedisUtils.get(redisKey))) {
                    StaticJedisUtils.del(redisKey);
                }
                if (expired == null) {
                    expired = 90;
                }
                StaticJedisUtils.set(redisKey, content, expired);
                return true;
            }

            if (!"0".equals(data.getString("code")) ||
                    !"SUCCESS".equalsIgnoreCase(data.getString("msg"))) {
                return false;
            }
        } catch (Exception e) {
            logger.error("发送验证码失败，异常信息：" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 发送短信验证码，默认会把短信插入到redis，key值为sms.手机号码，value值为content,过期时间90s
     * @param mobile 手机号
     * @param content 验证码
     * @return boolean
     */
    public static boolean send(String mobile, String content) {
        return send(mobile, content, true, null);
    }

}