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
@RequestMapping(value = "/back/userWithdraw")
public class UserWithdrawController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8492617160143410866L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userWithdrawService", "get");
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
		InputParams params = new InputParams("userWithdrawService", "list");
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
		InputParams params = new InputParams("userWithdrawService", "checkList");
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
		InputParams params = new InputParams("userWithdrawService", "checkStatusList");
		return this.execute(params);
	}
	
	/**
	 * 分状态列表
	 * @return
	 */
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	@ResponseBody
	public Result statusList() {
		InputParams params = new InputParams("userWithdrawService", "statusList");
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/rollback",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="退回原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result rollback(String uuid, String reason){
		InputParams params = new InputParams("userWithdrawService", "rollback");
		params.addParams("uuid", null, uuid);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/rerollback",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="退回原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result rerollback(String uuid, String reason){
		InputParams params = new InputParams("userWithdrawService", "rerollback");
		params.addParams("uuid", null, uuid);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="删除原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid, String reason){
		InputParams params = new InputParams("userWithdrawService", "delete");
		params.addParams("uuid", null, uuid);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/redelete",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="删除原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result redelete(String uuid, String reason){
		InputParams params = new InputParams("userWithdrawService", "redelete");
		params.addParams("uuid", null, uuid);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/apply",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "capitalAccount", message="企业账户", type = DataType.STRING, required = true)
	@ResponseBody
	public Result apply(String uuid, String capitalAccount){
		InputParams params = new InputParams("userWithdrawService", "apply");
		params.addParams("uuid", null, uuid);
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/reapply",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "capitalAccount", message="企业账户", type = DataType.STRING, required = true)
	@ResponseBody
	public Result reapply(String uuid, String capitalAccount){
		InputParams params = new InputParams("userWithdrawService", "reapply");
		params.addParams("uuid", null, uuid);
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/confirm",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "capitalAccount", message="企业账户", type = DataType.STRING, required = true)
	@ActionParam(key = "proof", message="凭证", type = DataType.STRING)
	@ResponseBody
	public Result confirm(String uuid, String capitalAccount, String proof){
		InputParams params = new InputParams("userWithdrawService", "confirm");
		params.addParams("uuid", null, uuid);
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("proof", null, proof);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/resubmit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "capitalAccount", message="企业账户", type = DataType.STRING, required = true)
	@ActionParam(key = "proof", message="凭证", type = DataType.STRING)
	@ResponseBody
	public Result resubmit(String uuid, String capitalAccount, String proof){
		InputParams params = new InputParams("userWithdrawService", "resubmit");
		params.addParams("uuid", null, uuid);
		params.addParams("capitalAccount", null, capitalAccount);
		params.addParams("proof", null, proof);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="原因", type = DataType.STRING)
	@ResponseBody
	public Result check(String uuid, String status, String reason){
		InputParams params = new InputParams("userWithdrawService", "check");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
