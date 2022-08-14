package cn.zeppin.product.utility.fuqianla.handler;


import java.util.List;

import cn.zeppin.product.utility.fuqianla.data.base.RequestData;
import cn.zeppin.product.utility.fuqianla.datastruct.IMsgList;

/**
 * MsgList处理-接口
 *
 * @author Sherlock Blaze
 * @create 2017-11-05-5:22 AM
 */

public interface IMsgListHandler {
//    TODO 如有其它需求请重新声明方法并实现

    /**
     * @param requestDataList
     * @param seed            秘钥 MD秘钥/RSA秘钥
     * @return IMsgList 获得加密后的请求参数
     * @throws Throwable 异常
     * @author Sherlock Blaze
     * @date 05/11/2017-5:23 AM
     * @description 主要对请求参数做加密处理
     */
    IMsgList<String, Object> returnReqPara(List<RequestData> requestDataList, String seed) throws Throwable;
}
