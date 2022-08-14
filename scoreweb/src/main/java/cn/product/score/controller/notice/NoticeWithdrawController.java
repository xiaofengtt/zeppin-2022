package cn.product.score.controller.notice;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.controller.BaseController;

/**
 * 	提现异步
 */

@Controller
@RequestMapping(value = "/notice/withdraw")
public class NoticeWithdrawController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1806497273571327329L;

	/**
	 * 融宝提现异步
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/byReapal", method = RequestMethod.POST)
	@ResponseBody
	public void byReapal(HttpServletRequest request,String encryptkey, String data,HttpServletResponse response) throws Exception {
		LoggerFactory.getLogger(getClass()).info("推送回调接口encryptkey==========>" + encryptkey);
        LoggerFactory.getLogger(getClass()).info("推送回调接口data==========>" + data);
        try {
        	InputParams params = new InputParams("noticeWithdrawService", "byReapal");
			params.addParams("encryptkey", null, encryptkey);
			params.addParams("data", null, data);
			DataResult<Object> result = (DataResult<Object>) this.execute(params);
        	//处理异步通知参数
			if(result.getData() != null) {
				HashMap<String, Object> resultMap = (HashMap<String, Object>) result.getData();
				if((Boolean)resultMap.get("result")){
					response.getWriter().write("success");
					
				} else {
					response.getWriter().write("fail");
				}
				return;
			} else {
				response.getWriter().write("fail");
				return;
			}
        	
        } catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		} catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("fail");
        }
	}
}
