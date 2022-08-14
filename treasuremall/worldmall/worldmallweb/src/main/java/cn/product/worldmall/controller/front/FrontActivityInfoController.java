/**
 * 
 */
package cn.product.worldmall.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;

/**
 * 前端地区
 */

@Controller
@RequestMapping(value = "/front/activity")
@Api(tags= {"activity"})
public class FrontActivityInfoController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2705177802286548167L;

	@ApiOperation(value = "活动详情", notes = "活动详情，可以获取到单个活动的详细数据，包括用户可参与次数")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result get(String uuid, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "get");
		params.addParams("name", null, uuid);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	@ApiOperation(value = "活动列表", notes = "活动列表，可以获取到所有活动的状态，及基本配置情况")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "sort", type = DataType.STRING)
	@ActionParam(key = "status", message = "活动状态", type = DataType.STRING)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sort, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "list");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	@ApiOperation(value = "0元购商品详情", notes = "0元购商品详情")
	@RequestMapping(value = "/buyfreeGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result buyfreeGet(String uuid, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "buyfreeGet");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	@ApiOperation(value = "0元购商品列表", notes = "0元购商品列表")
	@RequestMapping(value = "/buyfreeList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "sort", type = DataType.STRING)
	@ActionParam(key = "status", message = "活动状态", type = DataType.STRING)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result buyfreeList(String name, String status, Integer pageNum, Integer pageSize, String sort, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "buyfreeList");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	@ApiOperation(value = "0元购参与记录列表", notes = "0元购参与记录列表")
	@RequestMapping(value = "/buyfreeHistoryList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "sort", type = DataType.STRING)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result buyfreeHistoryList(String status, Integer pageNum, Integer pageSize, String sort, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "buyfreeHistoryList");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	@ApiOperation(value = "0元购参与记录详情", notes = "0元购参与记录详情")
	@RequestMapping(value = "/buyfreeHistoryGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result buyfreeHistoryGet(String uuid, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "buyfreeHistoryGet");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	
	@ApiOperation(value = "签到活动详细信息列表", notes = "获取用户N天签到获得奖励的信息列表，包含用户已连续签到几天的参数")
	@RequestMapping(value = "/checkinList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "sort", message = "sort", type = DataType.STRING)
	@ActionParam(key = "status", message = "活动状态", type = DataType.STRING)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result checkinList(String name, String status, String sort, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "checkinList");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	@ApiOperation(value = "积分转盘活动详细信息列表", notes = "积分转盘活动详细信息列表")
	@RequestMapping(value = "/scorelotteryList", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "sort", message = "sort", type = DataType.STRING)
	@ActionParam(key = "status", message = "活动状态", type = DataType.STRING)
	@ResponseBody
	public Result scorelotteryList(String name, String status, String sort) {
		
		InputParams params = new InputParams("frontActivityInfoService", "scorelotteryList");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	
	@ApiOperation(value = "积分转盘摇奖参与记录列表", notes = "积分转盘摇奖购参与记录列表")
	@RequestMapping(value = "/scorelotteryHistoryList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "sort", type = DataType.STRING)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result scorelotteryHistoryList(String status, Integer pageNum, Integer pageSize, String sort, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "scorelotteryHistoryList");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
	
	@ApiOperation(value = "积分转盘摇奖参与记录详情", notes = "积分转盘摇奖参与记录详情")
	@RequestMapping(value = "/scorelotteryHistoryGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUser", message = "用户UUID", type = DataType.STRING)
	@ResponseBody
	public Result scorelotteryHistoryGet(String uuid, String frontUser) {
		
		InputParams params = new InputParams("frontActivityInfoService", "scorelotteryHistoryGet");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, frontUser);
		return this.execute(params);
	}
}
