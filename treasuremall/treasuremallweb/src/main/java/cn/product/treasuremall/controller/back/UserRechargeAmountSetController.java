package cn.product.treasuremall.controller.back;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/amountSet")
public class UserRechargeAmountSetController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7125836467911692957L;

	/**
	 * 获取充值金额设置详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userRechargeAmountSetService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取充值金额设置列表
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userRechargeAmountSetService", "list");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 添加
	 * @param amount
	 * @param dAmount
	 * @param sort
	 * @param isPreferential
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "amount", message="充值金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dAmount", message="到账金币", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.INTEGER, required = true)
	@ActionParam(key = "isPreferential", message="是否展示优惠", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "giveDAmount", message="优惠金币", type = DataType.STRING)
	@ResponseBody
    public Result add(BigDecimal amount, BigDecimal dAmount, Integer sort, Boolean isPreferential, BigDecimal giveDAmount){
		InputParams params = new InputParams("userRechargeAmountSetService", "add");
		params.addParams("amount", null, amount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("sort", null, sort);
		params.addParams("isPreferential", null, isPreferential);
		params.addParams("giveDAmount", null, giveDAmount);
		return this.execute(params);
    }
	
	/**
	 * 编辑
	 * @param uuid
	 * @param amount
	 * @param dAmount
	 * @param sort
	 * @param isPreferential
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="UUID", type = DataType.STRING, required = true)
	@ActionParam(key = "amount", message="充值金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "dAmount", message="到账金币", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.INTEGER, required = true)
	@ActionParam(key = "isPreferential", message="是否展示优惠", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "giveDAmount", message="优惠金币", type = DataType.STRING)
	@ResponseBody
    public Result edit(String uuid, BigDecimal amount, BigDecimal dAmount, Integer sort, Boolean isPreferential, BigDecimal giveDAmount){
		InputParams params = new InputParams("userRechargeAmountSetService", "edit");
		params.addParams("uuid", null, uuid);
		params.addParams("amount", null, amount);
		params.addParams("dAmount", null, dAmount);
		params.addParams("sort", null, sort);
		params.addParams("isPreferential", null, isPreferential);
		params.addParams("giveDAmount", null, giveDAmount);
		return this.execute(params);
    }	
	
	/**
	 * 启用停用
	 * @param uuid
	 * @param status normal/disable
	 * @return
	 */
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="UUID", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
    public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("userRechargeAmountSetService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
    }
	
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="UUID", type = DataType.STRING, required = true)
	@ResponseBody
    public Result delete(String uuid){
		InputParams params = new InputParams("userRechargeAmountSetService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
    }
}
