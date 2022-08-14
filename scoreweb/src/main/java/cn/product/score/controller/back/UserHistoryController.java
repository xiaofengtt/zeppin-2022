package cn.product.score.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;

@Controller
@RequestMapping(value = "/back/userHistory")
public class UserHistoryController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 441145370339774227L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userHistoryService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "capitalAccount", message="账户", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String frontUser, String capitalAccount, String type, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userHistoryService", "list");
		params.addParams("frontUser", null, frontUser);
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("type", null, type);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 获取分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ResponseBody
	public Result statusList(String type) {
		InputParams params = new InputParams("userHistoryService", "statusList");
		params.addParams("type", null, type);
		return this.execute(params);
	}
}
