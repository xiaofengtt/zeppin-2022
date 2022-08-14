package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;

@Controller
@RequestMapping(value = "/back/activityRecharge")
public class ActivityInfoRechargeController extends BaseController{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 715514200781243828L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("activityInfoRechargeService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/configlist",method=RequestMethod.GET)
	@ActionParam(key = "name", message="search", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result configlist(String name, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("activityInfoRechargeService", "configlist");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
}
