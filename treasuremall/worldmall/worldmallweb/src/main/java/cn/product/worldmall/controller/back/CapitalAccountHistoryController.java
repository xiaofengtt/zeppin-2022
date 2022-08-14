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
@RequestMapping(value = "/back/capitalAccountHistory")
public class CapitalAccountHistoryController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1189592965265460318L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("capitalAccountHistoryService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "capitalPlatform", message="渠道", type = DataType.STRING)
	@ActionParam(key = "capitalAccount", message="渠道账户", type = DataType.STRING)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "operator", message="经办人", type = DataType.STRING)
	@ActionParam(key = "type", message="type", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String capitalPlatform, String capitalAccount, String frontUser, String operator, String type, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("capitalAccountHistoryService", "list");
		params.addParams("capitalPlatform", null, capitalPlatform);
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("frontUser", null, frontUser);
		params.addParams("operator", null, operator);
		params.addParams("type", null, type);
		params.addParams("status", null, status);
		
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 分类型列表
	 * @return
	 */
	@RequestMapping(value = "/typeList", method = RequestMethod.GET)
	@ResponseBody
	public Result typeList() {
		InputParams params = new InputParams("capitalAccountHistoryService", "typeList");
		return this.execute(params);
	}
}
