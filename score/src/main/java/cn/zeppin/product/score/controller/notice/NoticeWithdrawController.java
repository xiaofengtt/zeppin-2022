package cn.zeppin.product.score.controller.notice;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.TransactionException;
import cn.zeppin.product.score.entity.PayNotifyInfo.PayNotifyInfoPayType;
import cn.zeppin.product.score.service.FrontUserHistoryCheckService;
import cn.zeppin.product.score.service.PayNotifyInfoService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.reapal.ReapalUtil;

/**
 * 	提现异步
 */

@Controller
@RequestMapping(value = "/notice/withdraw")
public class NoticeWithdrawController extends BaseController{
	
	@Autowired
    private FrontUserHistoryCheckService frontUserHistoryCheckService;
	
	@Autowired
	private PayNotifyInfoService payNotifyInfoService;
	
	/**
	 * 融宝提现异步
	 * @return
	 */
	@RequestMapping(value = "/byReapal", method = RequestMethod.POST)
	@ResponseBody
	public void byReapal(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception {
		String result = null;
		LoggerFactory.getLogger(getClass()).info("推送回调接口encryptkey==========>" + encryptkey);
        LoggerFactory.getLogger(getClass()).info("推送回调接口data==========>" + data);
        try {
        	result = ReapalUtil.pubkey(encryptkey,data);
        	LoggerFactory.getLogger(getClass()).info("推送回调接口payback_result:"+result);
        	/*
        	 * 根据解析后的result做后续处理
        	 * */
        	Map<String, Object> map = JSONUtils.json2map(result);
        	LoggerFactory.getLogger(getClass()).info(map.get("data")+"");
        	HashMap<String, Object> resultMap = this.payNotifyInfoService.insertPayNotifyInfo(map, PayNotifyInfoPayType.WITHDRAW_REAPAL_COM);
        	if((Boolean)resultMap.get("result")){
				response.getWriter().write("success");
				
			} else {
				response.getWriter().write("fail");
			}
			return;
        }  catch (TransactionException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		} catch (ParseException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("fail");
        }
	}
}
