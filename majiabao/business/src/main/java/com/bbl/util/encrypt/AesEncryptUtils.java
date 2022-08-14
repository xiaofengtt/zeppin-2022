package com.bbl.util.encrypt;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * AES前后端数据传输加密工具类
 * @author pibigstar
 *
 */
public class AesEncryptUtils {
    //可配置到Constant中，并读取配置文件注入,16位,自己定义
    private static final String KEY = "gamejiamichuanab";

    //参数分别代表 算法名称/加密模式/数据填充方式
    //http://tool.chacuo.net/cryptaes
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     * @param content 加密的字符串
     * @param encryptKey key值
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.encodeBase64String(b);

    }

    /**
     * 解密
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }
    public static String decrypt(String encryptStr) throws Exception {
        return decrypt(encryptStr, KEY);
    }


    public static void main(String[] args) throws Exception {
        Map map=new HashMap<String,Object>();

          //账变记录
//        map.put("pfmUserId","cs2d01");
//        map.put("page_no","1");
//        map.put("page_size","10");
//        map.put("cz_type","0");
//        map.put("startTime","2019-03-27 18:26:08");
//        map.put("endTime","2019-05-29 59:59:59");

        //游戏记录
//        map.put("playerId","name152");
//        map.put("page_no","0");
//        map.put("page_size","10");
//        map.put("gameType","");
//        map.put("platform","");
//        map.put("startTime","2019-03-10 00:00:00");
//        map.put("endTime","2019-05-22 59:59:59");

//        map.put("userId","test98");

        //添加
//        map.put("groupId",111);
//        map.put("min",111);
//        map.put("max",211);
//        map.put("dbrate",0.555);//
//
//
//        map.put("phone","cs2d01");
//        map.put("pwd","a11111");
//        map.put("platform",1);
//        map.put("device_model",1);
//        map.put("platform_flag",1);


//
//        //团队报表
        map.put("page_no","1");
        map.put("page_no1","2");




        String content = JSONObject.toJSONString(map);
        System.out.println("加密前：" + content);

        String encrypt = encrypt(content, KEY);
        System.out.println("加密后：" + encrypt);
        map = new HashMap();
        map.put("requestData",encrypt);
        System.out.println("加密后：" + JSONObject.toJSONString(map));

        String decrypt = decrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);
    }
}