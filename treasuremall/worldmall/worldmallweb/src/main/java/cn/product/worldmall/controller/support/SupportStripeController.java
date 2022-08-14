package cn.product.worldmall.controller.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.controller.BaseController;


@Controller
@RequestMapping(value = "/support/stripe")
public class SupportStripeController extends BaseController{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1530905159692121884L;
	

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	@ResponseBody
	public Result load(String orderid) {
		InputParams params = new InputParams("supportStripeService", "load");
		params.addParams("orderid", null, orderid);
		return this.execute(params);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/checkout",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response){
		String orderid = request.getParameter("orderid");
		String stripeTokenType = request.getParameter("stripeTokenType");
		String stripeToken = request.getParameter("stripeToken");
		String stripeEmail = request.getParameter("stripeEmail");
		
		InputParams params = new InputParams("supportStripeService", "checkout");
		params.addParams("orderid", null, orderid);
		params.addParams("stripeTokenType", null, stripeTokenType);
		params.addParams("stripeToken", null, stripeToken);
		params.addParams("stripeEmail", null, stripeEmail);
		DataResult<Object> result = (DataResult<Object>) this.execute(params);
		
		try{
			if(result.getData() != null){
				String returnUrl = result.getData().toString();
				String resultStr = ResultStatusType.SUCCESS.equals(result.getStatus()) ? "1" : "0";
				if(returnUrl.indexOf("?") < 0){
					response.sendRedirect(returnUrl + "?result=" + resultStr);
				}else{
					response.sendRedirect(returnUrl + "&result=" + resultStr);
				}
			}
		} catch(Exception e){
			return null;
		}
		return null;
	}
}
