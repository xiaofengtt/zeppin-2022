/**
 * 
 */
package cn.product.treasuremall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.util.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 前端地区
 */

@Controller
@RequestMapping(value = "/front/userActivity")
@Api(tags= {"userActivity"})
public class FrontUserActivityInfoController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2260431739159729799L;

	@ApiOperation(value = "用户0元购参与记录", notes = "用户0元购参与记录")
	@RequestMapping(value = "/buyfreeHistoryList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result buyfreeHistoryList(String status, Integer pageNum, Integer pageSize, String sort, HttpServletRequest request) {
		
		InputParams params = new InputParams("frontActivityInfoService", "buyfreeHistoryList");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@ApiOperation(value = "0元购参与记录详情", notes = "0元购参与记录详情")
	@RequestMapping(value = "/buyfreeHistoryGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result buyfreeHistoryGet(String uuid, HttpServletRequest request) {
		
		InputParams params = new InputParams("frontActivityInfoService", "buyfreeHistoryGet");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@ApiOperation(value = "0元购-用户参与抽奖", notes = "0元购用户参与抽奖，前端需判断是否有参与次数")
	@RequestMapping(value = "/buyfreePartake", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result buyfreePartake(String uuid, HttpServletRequest request) {
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontActivityInfoService", "buyfreePartake");
		params.addParams("uuid", null, uuid);
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@ApiOperation(value = "用户0元购参与记录", notes = "用户0元购参与记录")
	@RequestMapping(value = "/checkinHistoryList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result checkinHistoryList(String status, Integer pageNum, Integer pageSize, String sort, HttpServletRequest request) {
		
		InputParams params = new InputParams("frontActivityInfoService", "checkinHistoryList");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@ApiOperation(value = "0元购参与记录详情", notes = "0元购参与记录详情")
	@RequestMapping(value = "/checkinHistoryGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result checkinHistoryGet(String uuid, HttpServletRequest request) {
		
		InputParams params = new InputParams("frontActivityInfoService", "checkinHistoryGet");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@ApiOperation(value = "0元购-用户参与抽奖", notes = "0元购用户参与抽奖，前端需判断是否有参与次数")
	@RequestMapping(value = "/checkinPartake", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result checkinPartake(HttpServletRequest request) {
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontActivityInfoService", "checkinPartake");
//		params.addParams("uuid", null, uuid);
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@ApiOperation(value = "用户积分抽奖转盘参与记录", notes = "用户积分抽奖转盘参与记录")
	@RequestMapping(value = "/scorelotteryHistoryList", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ActionParam(key = "sort", message = "排序规则", type = DataType.STRING)
	@ResponseBody
	public Result scorelotteryHistoryList(String status, Integer pageNum, Integer pageSize, String sort, HttpServletRequest request) {
		
		InputParams params = new InputParams("frontActivityInfoService", "scorelotteryHistoryList");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@ApiOperation(value = "积分抽奖转盘参与记录详情", notes = "积分抽奖转盘参与记录详情")
	@RequestMapping(value = "/scorelotteryHistoryGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "UUID", type = DataType.STRING, required = true)
	@ResponseBody
	public Result scorelotteryHistoryGet(String uuid, HttpServletRequest request) {
		
		InputParams params = new InputParams("frontActivityInfoService", "scorelotteryHistoryGet");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@ApiOperation(value = "积分抽奖转盘-用户参与抽奖", notes = "积分抽奖转盘用户参与抽奖，前端需判断是否有参与次数")
	@RequestMapping(value = "/scorelotteryPartake", method = RequestMethod.POST)
	@ResponseBody
	public Result scorelotteryPartake(HttpServletRequest request) {
		
		//日活统计
		InputParams dailyParams = new InputParams("frontUserService", "dailyStatistics");
		dailyParams.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		this.execute(dailyParams);
		
		InputParams params = new InputParams("frontActivityInfoService", "scorelotteryPartake");
		params.addParams("ip", null, WebUtil.getRemoteHost(request));
		params.addParams("frontUser", null, this.getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 兑奖
	 * @param request
	 * @param winningInfo
	 * @param type
	 * @param frontUserAddress
	 * @return
	 */
	@ApiOperation(value = "兑奖", notes = "兑奖操作:type取值（gold-金币/entity-实物）")
	@RequestMapping(value="/receive",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="参与记录UUID", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="兑奖类型", type = DataType.STRING, required = true)
	@ActionParam(key = "activityInfo", message="活动ID", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUserAddress", message="用户地址ID", type = DataType.STRING)
	@ResponseBody
	public Result receive(HttpServletRequest request, String uuid
			, String type, String activityInfo, String frontUserAddress){
		InputParams params = new InputParams("frontActivityInfoService", "receive");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		params.addParams("uuid", null, uuid);
		params.addParams("type", null, type);
		params.addParams("activityInfo", null, activityInfo);
//		params.addParams("ip", null, Utlity.getIpAddr(request));
		params.addParams("frontUserAddress", null, frontUserAddress);
		return this.execute(params);
	}
}
