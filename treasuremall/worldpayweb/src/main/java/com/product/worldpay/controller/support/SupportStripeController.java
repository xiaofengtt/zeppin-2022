package com.product.worldpay.controller.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.worldpay.controller.BaseController;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.controller.base.Result;

@Controller
@RequestMapping(value = "/support/stripe")
public class SupportStripeController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2250907496067260514L;
	
	@RequestMapping(value = "/webhook", method = RequestMethod.GET)
	@ResponseBody
	public Result webhook(HttpServletRequest request, HttpServletResponse response) {
		InputParams params = new InputParams("supportStripeService", "webhook");
		
		
		return this.execute(params);
	}
}
