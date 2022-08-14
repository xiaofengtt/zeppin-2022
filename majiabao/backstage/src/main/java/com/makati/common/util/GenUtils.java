package com.makati.common.util;


import com.alibaba.fastjson.JSONObject;
import com.makati.common.exception.BusinessException;
import com.makati.common.util.lottery.AssertUtil;
import com.makati.common.util.lottery.ErrorCodeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * 生成工具
 * @author Allan
 */
@Slf4j
public class GenUtils {

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 訂單號生成工具
     * @author Allan
     */
    public static String getOrderNum(String type){
        int hashCodeV = ((System.currentTimeMillis() + "").substring(1) +
                (System.nanoTime() + "").substring(7, 10).hashCode()).hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        String orderId=type + String.format("%013d", hashCodeV);
       // System.out.println(orderId);
        return orderId;
    }

    public static String getCode(){
        int  maxNum = 36;
        int i;
        int count = 0;
        char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while(count < 8){
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count ++;
            }
        }
        return pwd.toString();
    }

    public static String genNumCode() {
        String no="";
        int num[]=new int[6];
        int c=0;
        for (int i = 0; i < 6; i++) {
            num[i] = new Random().nextInt(10);
            c = num[i];
            for (int j = 0; j < i; j++) {
                if (num[j] == c) {
                    i--;
                    break;
                }
            }
        }
        if (num.length>0) {
            for (int i = 0; i < num.length; i++) {
                no+=num[i];
            }
        }
        return no;
    }

    public static String genShortUrl(String url){
        String result = "";
        try {
            AssertUtil.isNotBlank(url);

            URL urlClinet = new URL("http://suo.im/api.php?format=json&url="+url);
            HttpURLConnection connection = (HttpURLConnection) urlClinet.openConnection();
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            JSONObject json = JSONObject.parseObject(sb.toString());
            log.info("====> genShortUrl respone result=" + json.toString());

            if("".equals(json.get("url"))){  //  生成失败
                throw new BusinessException(ErrorCodeUtil.SHORT_URL_FAIL, "生成短链接失败！");
            }
            result = json.get("url").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        //System.out.println("orderNo=" + getOrderNum("TX"));
       // System.out.println("getCode="+getCode());
       /* for (int i = 0; i < 100; i++) {
            System.out.println("genNumCode"+i+"="+genNumCode());
        }*/
        System.out.println("shortUrl= " + genShortUrl("www.weinisi01.com/register?agentCode=5Q998VOZ"));
    }
}
