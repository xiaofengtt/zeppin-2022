package cn.product.treasuremall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;

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
	
	/**
	 * 获取金币明细
	 * @param frontUser
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String frontUser, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userHistoryService", "list");
		params.addParams("frontUser", null, frontUser);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 获取用户参与记录列表
	 * @param frontUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/partakelist",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "goodsIssue", message="前端商品ID", type = DataType.STRING)
	@ResponseBody
	public Result partakelist(String frontUser, Integer pageNum, Integer pageSize, String goodsIssue){
		InputParams params = new InputParams("userHistoryService", "partakelist");
		params.addParams("frontUser", null, frontUser);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("goodsIssue", null, goodsIssue);
		return this.execute(params);
	}
	
	/**
	 * 获取用户中奖纪录列表
	 * @param frontUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/winlist",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ResponseBody
	public Result winlist(String frontUser, Integer pageNum, Integer pageSize){
		InputParams params = new InputParams("userHistoryService", "winlist");
		params.addParams("frontUser", null, frontUser);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
	
	/**
	 * 获取用户充值记录列表
	 * @param frontUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/rechargelist",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ResponseBody
	public Result rechargelist(String frontUser, Integer pageNum, Integer pageSize){
		InputParams params = new InputParams("userHistoryService", "rechargelist");
		params.addParams("frontUser", null, frontUser);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
	
	/**
	 * 获取用户提现记录列表
	 * @param frontUser
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/withdrawlist",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ResponseBody
	public Result withdrawlist(String frontUser, Integer pageNum, Integer pageSize){
		InputParams params = new InputParams("userHistoryService", "withdrawlist");
		params.addParams("frontUser", null, frontUser);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
	
	/**
	 * 用户红包列表
	 * @param frontUser
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/voucherlist",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result voucherlist(String frontUser, String status, Integer pageNum, Integer pageSize){
		InputParams params = new InputParams("userHistoryService", "voucherlist");
		params.addParams("frontUser", null, frontUser);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
	
	/**
	 * 用户积分列表
	 * @param frontUser
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/scorelist",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="用户", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result scorelist(String frontUser, String status, Integer pageNum, Integer pageSize){
		InputParams params = new InputParams("userHistoryService", "scorelist");
		params.addParams("frontUser", null, frontUser);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		return this.execute(params);
	}
}
