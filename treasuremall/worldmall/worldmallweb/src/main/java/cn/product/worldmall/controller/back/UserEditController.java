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
@RequestMapping(value = "/back/userEdit")
public class UserEditController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8626195329585812511L;

	/**
	 * 获取详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userEditService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取列表
	 * @param showId
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING)
	@ActionParam(key = "name", message="search", type = DataType.STRING)
	@ActionParam(key = "createtime", message="申请时间", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String showId, String status, String mobile, String name, String createtime, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userEditService", "list");
		params.addParams("showId", null, showId);
		params.addParams("status", null, status);
		params.addParams("mobile", null, mobile);
		params.addParams("name", null, name);
		params.addParams("createtime", null, createtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 审核
	 * @param uuid
	 * @param status checked/nopass
	 * @param reason
	 * @return
	 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="审核原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String uuid, String status, String reason){
		InputParams params = new InputParams("userEditService", "check");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
