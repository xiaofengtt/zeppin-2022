/** 
 * Project Name:Self_Cool 
 * File Name:JsonUtils.java 
 * Package Name:cn.zeppin.utility 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.product.utility;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class JSONUtils {
    private static SerializeConfig mapping = new SerializeConfig();  
    
    static{  
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));  
    }  
      
    /** 
     * javaBean、list、map convert to json string 
     */  
    public static String obj2json(Object obj){  
        return JSON.toJSONString(obj, mapping);  
    }  
      
    /** 
     * json string convert to javaBean、map 
     */  
    public static <T> T json2obj(String jsonStr,Class<T> clazz){  
        return JSON.parseObject(jsonStr, clazz);  
    }  
      
    /** 
     * json array string convert to list with javaBean 
     */  
    public static <T> List<T> json2list(String jsonArrayStr,Class<T> clazz){  
        return JSON.parseArray(jsonArrayStr, clazz);  
    }  
      
    /** 
     * json string convert to map 
     */  
    @SuppressWarnings("unchecked")
	public static <T> Map<String,Object> json2map(String jsonStr){  
        return json2obj(jsonStr, Map.class);  
    }  
      
    /** 
     * json string convert to map with javaBean 
     */  
    public static <T> Map<String,T> json2map(String jsonStr,Class<T> clazz){  
        Map<String,T> map = JSON.parseObject(jsonStr, new TypeReference<Map<String, T>>() {});  
        for (Entry<String, T> entry : map.entrySet()) {  
            JSONObject obj = (JSONObject) entry.getValue();  
            map.put(entry.getKey(), JSONObject.toJavaObject(obj, clazz));  
        }  
        return map;  
    }  	
    
    public static void main(String[] args) {
    	Coupon cp = new Coupon();
		cp.setUuid(UUID.randomUUID().toString());
		cp.setCreator("123");
		cp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		cp.setStatus(CouponStatus.NORMAL);
		cp.setCouponName("优惠券");
		cp.setCouponPrice(BigDecimal.ONE);
		cp.setCouponType("cash");
		cp.setMinInvestAccount(BigDecimal.ZERO);
		cp.setExpiryDate(Timestamp.valueOf("2017-12-14 16:21:30"));
		cp.setDeadline(30);
		List<Coupon> list = new ArrayList<Coupon>();
		list.add(cp);
		Coupon cp1 = new Coupon();
		cp1.setUuid(UUID.randomUUID().toString());
		cp1.setCreator("123");
		cp1.setCreatetime(new Timestamp(System.currentTimeMillis()));
		cp1.setStatus(CouponStatus.NORMAL);
		cp1.setCouponName("优惠券");
		cp1.setCouponPrice(BigDecimal.ONE);
		cp1.setCouponType("cash");
		cp1.setMinInvestAccount(BigDecimal.ZERO);
		cp1.setExpiryDate(Timestamp.valueOf("2017-12-14 16:21:30"));
		cp1.setDeadline(30);
		list.add(cp1);
		Coupon cp2 = new Coupon();
		cp2.setUuid(UUID.randomUUID().toString());
		cp2.setCreator("123");
		cp2.setCreatetime(new Timestamp(System.currentTimeMillis()));
		cp2.setStatus(CouponStatus.NORMAL);
		cp2.setCouponName("优惠券");
		cp2.setCouponPrice(BigDecimal.ONE);
		cp2.setCouponType("cash");
		cp2.setMinInvestAccount(BigDecimal.ZERO);
		cp2.setExpiryDate(Timestamp.valueOf("2017-12-14 16:21:30"));
		cp2.setDeadline(30);
		list.add(cp2);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "优惠券列表");
		map.put("couponList", list);
		System.out.println(obj2json(map));
	}
}
