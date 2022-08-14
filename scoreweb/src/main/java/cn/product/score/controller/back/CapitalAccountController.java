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
import cn.product.score.entity.CapitalAccount;

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
	@ActionParam(key = "transType", message="交易类型", type = DataType.STRING)
	@ActionParam(key = "auto", message="自动交易", type = DataType.BOOLEAN)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String capitalPlatform, String name, String accountNum, String transType, Boolean auto, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("capitalAccountService", "list");
		params.addParams("capitalPlatform", null, capitalPlatform);
		params.addParams("name", null, name);
		params.addParams("accountNum", null, accountNum);
		params.addParams("transType", null, transType);
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
	@ActionParam(key = "accountNum", message="账户号", type = DataType.STRING, required = true)
	@ActionParam(key = "min", message="最低金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "max", message="最高金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ActionParam(key = "privateKey", message="私钥", type = DataType.STRING)
	@ActionParam(key = "accountName", message="账户名", type = DataType.STRING)
	@ActionParam(key = "bankName", message="所属银行", type = DataType.STRING)
	@ActionParam(key = "branchBank", message="开户行", type = DataType.STRING)
	@ResponseBody
    public Result add(CapitalAccount capitalAccount, String privateKey, String accountName, String bankName, String branchBank){
		
		InputParams params = new InputParams("capitalAccountService", "add");
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("privateKey", null, privateKey);
		params.addParams("accountName", null, accountName);
		params.addParams("bankName", null, bankName);
		params.addParams("branchBank", null, branchBank);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "accountNum", message="账号", type = DataType.STRING, required = true)
	@ActionParam(key = "min", message="最低金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "max", message="最高金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dailyMax", message="每日限额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ActionParam(key = "privateKey", message="私钥", type = DataType.STRING)
	@ActionParam(key = "accountName", message="账户名", type = DataType.STRING)
	@ActionParam(key = "bankName", message="所属银行", type = DataType.STRING)
	@ActionParam(key = "branchBank", message="开户行", type = DataType.STRING)
	@ResponseBody
	public Result edit(CapitalAccount capitalAccount, String privateKey, String accountName, String bankName, String branchBank){
		InputParams params = new InputParams("capitalAccountService", "edit");
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("privateKey", null, privateKey);
		params.addParams("accountName", null, accountName);
		params.addParams("bankName", null, bankName);
		params.addParams("branchBank", null, branchBank);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("capitalAccountService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
