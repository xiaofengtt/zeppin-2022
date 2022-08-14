package cn.zeppin.product.utility.fuqianla.datastruct.impl;

import cn.zeppin.product.utility.fuqianla.StringDealUtil;
import cn.zeppin.product.utility.fuqianla.datastruct.IMsgList;

import java.util.*;

/**
 * @author Sherlock Blaze
 * @date 03/11/2017-5:28 PM
 * @description
 */
public class MsgListForPay3_0Impl<K, V> implements IMsgList<K, V> {

    private List<KeyAndValue> list = new LinkedList<KeyAndValue>();
    private Map<K, V> map = new TreeMap<K, V>();
    private int alreadyIndex = 0;

    public MsgListForPay3_0Impl() {
    }

    private class KeyAndValue {
        private K key;
        private V value;

        public KeyAndValue(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public int put(K key, V value) {
        int flag = -1;
        try {
            this.list.add(new KeyAndValue(key, value));
            flag = 1;
        } catch (Exception e) {
            flag = -1;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * @author Sherlock Blaze
     * @date 05/11/2017-3:51 AM
     * @description 为MD5加密获取加密串：请求参数为有序拼接
     */
    @Override
    public StringBuffer getSignStrForMD5() {

        KeyAndValue kav;
        for (int i = alreadyIndex; i < list.size(); ++i, ++alreadyIndex) {
            kav = list.get(i);
            map.put(kav.key, kav.value);
        }

        ++alreadyIndex;

        return StringDealUtil.getSignStrForMD5(map);
    }
    
    /**
     * @author Sherlock Blaze
     * @date 05/11/2017-3:51 AM
     * @description 获取请求XML
     */
    @Override
    public StringBuffer getReqXmlStr() {
        Iterator<KeyAndValue> it = null;
        StringBuffer sb = new StringBuffer();

        if (list.size() == 0) {
            return null;
        } else {
            it = list.iterator();
            K key;
            V value;
            String fieldName;
            KeyAndValue kav;
            /*
            * 遍历
            * */
            while (it.hasNext()) {
                kav = it.next();
                key = kav.key;

                fieldName = key.toString();

                value = kav.value;

                sb.append("<" + fieldName + ">" + value.toString() + "</" + fieldName + ">");
                if ("signInfo".equals(key.toString()) && it.hasNext()) {
                    sb.append("</item><item>");
                }
            }

            return sb;
        }
    }

    /**
     * @author Sherlock Blaze
     * @date 05/11/2017-3:52 AM
     * @description 获取Post Form表单格式请求串
     */
    @Override
    public StringBuffer getReqPostFormStr() {
        Iterator<KeyAndValue> it = null;
        StringBuffer sb = new StringBuffer();

        if (list.size() == 0) {
            return null;
        } else {
            it = list.iterator();
            K key;
            V value;
            String fieldName;
            KeyAndValue kav;
            /*
            * 遍历
            * */
            while (it.hasNext()) {
                kav = it.next();
                key = kav.key;

                fieldName = key.toString();

                value = kav.value;

                sb.append(fieldName);
                sb.append("=");
                sb.append(value.toString());
                sb.append("&");
            }

            return sb;
        }
    }

    /**
     * @author Sherlock Blaze
     * @date 05/11/2017-3:52 AM
     * @description 获取JSON格式请求串
     */
    public StringBuffer getReqForJSONStr() {
        return null;
    }
}
