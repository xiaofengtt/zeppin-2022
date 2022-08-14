package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.entity.CapitalAccount;
import cn.product.worldmall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/capitalAccount")
public class CapitalAccountController extends BaseController{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5811453933941453884L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("capitalAccountService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "capitalPlatform", message="渠道", type = DataType.STRING)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "accountNum", message="账户", type = DataType.STRING)
	@ActionParam(key = "type", message="交易类型", type = DataType.STRING)
	@ActionParam(key = "auto", message="自动交易", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String capitalPlatform, String name, String accountNum, String type, Boolean auto, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("capitalAccountService", "list");
		params.addParams("capitalPlatform", null, capitalPlatform);
		params.addParams("name", null, name);
		params.addParams("accountNum", null, accountNum);
		params.addParams("type", null, type);
		params.addParams("auto", null, auto);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "capitalPlatform", message="所属渠道", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账号", type = DataType.STRING, required = true)
	@ActionParam(key = "min", message="充值下限", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "max", message="充值上限", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "totalMax", message="总限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "poundageRate", message="手续费", type = DataType.NUMBER)
	@ActionParam(key = "data", message="账户数据", type = DataType.STRING)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.NUMBER, required = true)
	@ActionParam(key = "logo", message="图标", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUserGroup", message="用户级别", type = DataType.STRING)
	@ActionParam(key = "frontUserStatus", message="用户状态", type = DataType.STRING)
	@ActionParam(key = "userPreReceive", message="最大收款次数", type = DataType.NUMBER)
	@ResponseBody
    public Result add(CapitalAccount capitalAccount){
		
		InputParams params = new InputParams("capitalAccountService", "add");
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账号", type = DataType.STRING, required = true)
	@ActionParam(key = "min", message="充值下限", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "max", message="充值上限", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "totalMax", message="总限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "poundageRate", message="手续费", type = DataType.NUMBER)
	@ActionParam(key = "data", message="账户数据", type = DataType.STRING)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="sort", type = DataType.NUMBER, required = true)
	@ActionParam(key = "logo", message="图标", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUserGroup", message="用户级别", type = DataType.STRING)
	@ActionParam(key = "frontUserStatus", message="用户状态", type = DataType.STRING)
	@ActionParam(key = "userPreReceive", message="最大收款次数", type = DataType.NUMBER)
	@ResponseBody
	public Result edit(CapitalAccount capitalAccount){
		InputParams params = new InputParams("capitalAccountService", "edit");
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("capitalAccountService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
