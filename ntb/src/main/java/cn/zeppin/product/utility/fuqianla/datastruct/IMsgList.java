package cn.zeppin.product.utility.fuqianla.datastruct;

/**
 * 数据结构接口
 *
 * @author Sherlock Blaze
 * @create 2017-11-05-4:39 AM
 */

public interface IMsgList<K, V> {
    /**
     * 获取MD5加密串
     *
     * @return
     */
    StringBuffer getSignStrForMD5();

    /**
     * 获取XML请求串
     *
     * @return
     */
    StringBuffer getReqXmlStr();

    /**
     * 获取post请求form格式请求串
     *
     * @return
     */
    StringBuffer getReqPostFormStr();

    /**
     * 向数据结构中存入数据
     *
     * @param k
     * @param v
     * @return
     */
    int put(K k, V v);
}
