package com.product.worldpay.controller.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;

@Controller
@RequestMapping(value = "/support/paypal")
public class SupportPaypalController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3142112067376455315L;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/execute",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response){
		String paymentId = request.getParameter("token");
		String payerId = request.getParameter("PayerID");
		
		InputParams params = new InputParams("supportPaypalService", "execute");
		params.addParams("paymentId", null, paymentId);
		params.addParams("payerId", null, payerId);
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
