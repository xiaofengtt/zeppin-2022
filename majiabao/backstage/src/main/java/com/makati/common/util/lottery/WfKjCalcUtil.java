package com.makati.common.util.lottery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.net.URL;


/**
 * 玩法开奖计算工具类
 */
public class WfKjCalcUtil {
    private static Logger logger = LoggerFactory.getLogger(WfKjCalcUtil.class);
    private static Invocable in;

    /**
     *  初始化需要用到的Invocable
     * @throws Exception
     */
    private static void init() throws Exception{
        logger.info("WfKjCalcUtil  初始化init");
//			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            URL url = Thread.currentThread().getContextClassLoader().getResource("");
            String path;
            if (url!=null){
                path = url.getPath();
                engine.eval(new FileReader(path+ "wf/parse-calc-count.js"));
                /*查看是否可以调用方法*/
                if (engine instanceof Invocable){
                    in = (Invocable) engine;
                }
            }
    }

    /**
     *  对用户所投注的记录，进行是否中奖的计算。若未中奖，返回0，若中奖返回中奖的注数，比如5，
     *  若是多赔率玩法，则返回中奖的位置，比如返回，0,1,0,0,0。此代表多赔率玩法赔率排序后的第二个赔率中奖。其他的为未中奖
     * @param wfFunctionName :玩法对应的函数名称，示例：zzmorebetwf5f
     * @param betNumber :投注号码，示例：0369,0369,0369,0369,0369
     * @param kjNumber  :开奖号码， 示例：5,2,1,3,9
     * @param xglhcAnimal :若是六合彩需要判断生肖的玩法，则把生肖传进来
     * @return
     */
    public static String getWfKjResult(String wfFunctionName,String betNumber,String kjNumber,String xglhcAnimal){
        String result =null;
        try {
            if(in==null){
                init();
            }
             Object kjValue=null;
            if(xglhcAnimal==null){
                kjValue=in.invokeFunction(wfFunctionName,betNumber,kjNumber);
            }else{
                kjValue=in.invokeFunction(wfFunctionName,betNumber,kjNumber,xglhcAnimal);
            }
             result=doCalcZhushu(kjValue);
        } catch (Exception e) {
            logger.info("计算开奖出错，玩法函数: {},投注号码:{},开奖号码:{}",wfFunctionName,betNumber,kjNumber);
            logger.error("计算开奖出错",e);
        }
        return result;

    }

    /**
     * 对开奖计算出来的数字进行处理
     * @param value
     * @return
     */
    private   static String  doCalcZhushu(Object value){
        String result=null;
        if(value instanceof Double){
            result=((Double) value).intValue()+"";
        }else{
            result=value.toString();
        }
        if(StringUtils.isBlank(result)){
            result="0";
        }
        return result;
    }
}
