package cn.product.worldmall.controller.back;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;

/**
 * demo
 */
@Controller
@RequestMapping(value = "/demo")
public class DemoController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6696297377080652288L;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(HttpServletRequest request, String uuid){
//		Map<String, String> reflectParams = new HashMap<String, String>();
//		reflectParams.put("serviceName", "demoService");
//		reflectParams.put("methodName", "sayHello");
//		request.setAttribute("reflect", reflectParams);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("uuid", uuid);
		InputParams params = new InputParams("demoService", "get", paramsMap);
		return this.execute(params);
	}
}
