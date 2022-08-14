package cn.product.score.controller.back;

import java.math.BigDecimal;

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
@RequestMapping(value = "/back/userRecharge")
public class UserRechargeController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2349139947940383233L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userRechargeService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String type, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userRechargeService", "list");
		params.addParams("type", null, type);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/checkList",method=RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result checkList(String type, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userRechargeService", "checkList");
		params.addParams("type", null, type);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 审核者分状态列表
	 * @return
	 */
	@RequestMapping(value = "/checkStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result checkStatusList() {
		InputParams params = new InputParams("userRechargeService", "checkStatusList");
		return this.execute(params);
	}
	
	/**
	 * 分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		InputParams params = new InputParams("userRechargeService", "statusList");
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message="金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "poundage", message="手续费", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "capitalAccount", message="渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "transData", message="交易信息", type = DataType.STRING)
	@ActionParam(key = "proof", message="凭证", type = DataType.STRING)
	@ResponseBody
	public Result add(String frontUser, BigDecimal fee, BigDecimal poundage, String capitalAccount, String transData, String proof){
		InputParams params = new InputParams("userRechargeService", "add");
		params.addParams("frontUser", null, frontUser);
		params.addParams("fee", null, fee);
		params.addParams("poundage", null, poundage);
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("transData", null, transData);
		params.addParams("proof", null, proof);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "fee", message="金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "poundage", message="手续费", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "capitalAccount", message="渠道账户", type = DataType.STRING, required = true)
	@ActionParam(key = "transData", message="交易信息", type = DataType.STRING)
	@ActionParam(key = "proof", message="凭证", type = DataType.STRING)
	@ResponseBody
	public Result edit(String uuid, BigDecimal fee, BigDecimal poundage, String capitalAccount, String transData, String proof){
		InputParams params = new InputParams("userRechargeService", "edit");
		params.addParams("uuid", null, uuid);
		params.addParams("fee", null, fee);
		params.addParams("poundage", null, poundage);
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("transData", null, transData);
		params.addParams("proof", null, proof);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/confirm",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "proof", message="凭证", type = DataType.STRING)
	@ResponseBody
	public Result confirm(String uuid, String proof){
		InputParams params = new InputParams("userRechargeService", "confirm");
		params.addParams("uuid", null, uuid);
		params.addParams("proof", null, proof);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/resubmit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "proof", message="凭证", type = DataType.STRING)
	@ResponseBody
	public Result resubmit(String uuid, String proof){
		InputParams params = new InputParams("userRechargeService", "resubmit");
		params.addParams("uuid", null, uuid);
		params.addParams("proof", null, proof);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/close",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result close(String uuid){
		InputParams params = new InputParams("userRechargeService", "close");
		params.addParams("uuid", null, uuid);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="原因", type = DataType.STRING)
	@ResponseBody
	public Result check(String uuid, String status, String reason){
		InputParams params = new InputParams("userRechargeService", "check");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
