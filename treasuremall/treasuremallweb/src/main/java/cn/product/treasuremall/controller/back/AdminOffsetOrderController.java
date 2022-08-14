package cn.product.treasuremall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/adminOffsetOrder")
public class AdminOffsetOrderController extends BaseController{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7913422027379027515L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("adminOffsetOrderService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "userType", message="用户类型", type = DataType.STRING)
	@ActionParam(key = "showid", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "mobile", message="手机号", type = DataType.STRING)
	@ActionParam(key = "createtime", message="时间", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String frontUser, String type, String status, Integer pageNum, Integer pageSize, String sort
			, String userType, String showid, String mobile, String createtime){
		InputParams params = new InputParams("adminOffsetOrderService", "list");
		params.addParams("frontUser", null, frontUser);
		params.addParams("type", null, type);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("userType", null, userType);
		params.addParams("showid", null, showid);
		params.addParams("mobile", null, mobile);
		params.addParams("createtime", null, createtime);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="操作类型", type = DataType.STRING, required = true)
	@ActionParam(key = "dAmount", message="操作金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ResponseBody
    public Result add(AdminOffsetOrder adminOffsetOrder){
		InputParams params = new InputParams("adminOffsetOrderService", "add");
		params.addParams("adminOffsetOrder", null, adminOffsetOrder);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/close",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result close(String uuid){
		InputParams params = new InputParams("adminOffsetOrderService", "close");
		params.addParams("uuid", null, uuid);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="审核原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String uuid, String status, String reason){
		InputParams params = new InputParams("adminOffsetOrderService", "check");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
