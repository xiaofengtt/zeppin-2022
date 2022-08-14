/**
 * 
 */
package cn.product.worldmall.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.util.Utlity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/userAccount")
@Api(tags= {"userAccount"})
public class FrontUserAccountController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7841938151051410448L;

	/**
	 * 获取用户账户信息
	 * @return
	 */
	@ApiOperation(value = "获取用户账户信息", notes = "获取用户账户信息")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get(HttpServletRequest request) {
		InputParams params = new InputParams("frontUserAccountService", "get");
		params.addParams("uuid", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}

	/**
	 * 获取用户有记录的月份列表
	 * @return
	 */
	@ApiOperation(value = "获取用户有记录的月份列表", notes = "获取用户有记录的月份列表")
	@RequestMapping(value = "/month", method = RequestMethod.GET)
	@ResponseBody
	public Result month(HttpServletRequest request) {
		InputParams params = new InputParams("frontUserAccountService", "month");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 用户金币交易列表
	 * @return
	 */
	@ApiOperation(value = "获取用户交易流水", notes = "获取用户交易流水")
	@RequestMapping(value = "/historyList", method = RequestMethod.GET)
	@ActionParam(key = "type", message="type", type = DataType.STRING)
	@ActionParam(key = "starttime", message="starttime", type = DataType.STRING)
	@ActionParam(key = "endtime", message="endtime", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ResponseBody
	public Result historyList(HttpServletRequest request, String type, String starttime, String endtime, Integer pageNum, Integer pageSize) {
		InputParams params = new InputParams("frontUserAccountService", "historyList");
		params.addParams("type", null, type);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 用户金币交易记录
	 * @return
	 */
	@ApiOperation(value = "获取用户交易详情", notes = "获取用户交易详情")
	@RequestMapping(value = "/historyGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="uuid", required = true)
	@ResponseBody
	public Result historyGet(HttpServletRequest request, String uuid) {
		InputParams params = new InputParams("frontUserAccountService", "historyGet");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	
	/**
	 * 获取用户代金券数目
	 * @return
	 */
	@ApiOperation(value = "获取用户代金券数目", notes = "获取用户代金券分状态数目")
	@RequestMapping(value = "/voucherStatusList", method = RequestMethod.GET)
	@ResponseBody
	public Result voucherStatusList(HttpServletRequest request) {
		InputParams params = new InputParams("frontUserAccountService", "voucherStatusList");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 用户代金券列表
	 * @return
	 */
	@ApiOperation(value = "获取用户代金券列表", notes = "获取用户红包列表：status取值（unstart-待生效/normal-可使用/used-已使用/overtime-已过期）")
	@RequestMapping(value = "/voucherList", method = RequestMethod.GET)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "goods", message="goods", type = DataType.STRING)
	@ActionParam(key = "goodsType", message="goodsType", type = DataType.STRING)
	@ActionParam(key = "payMin", message="payMin", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ResponseBody
	public Result voucherList(HttpServletRequest request, String status, String goods, String goodsType, String payMin, Integer pageNum, Integer pageSize) {
		InputParams params = new InputParams("frontUserAccountService", "voucherList");
		params.addParams("status", null, status);
		params.addParams("goods", null, goods);
		params.addParams("goodsType", null, goodsType);
		params.addParams("payMin", null, payMin);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 获取用户参与记录列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @param goodsIssue
	 * @return
	 */
	@ApiOperation(value = "获取用户参与记录列表", notes = "获取用户参与记录列表")
	@RequestMapping(value="/paymentList",method=RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "goodsIssue", message="goodsIssue", type = DataType.STRING)
	@ActionParam(key = "gameType", message="Game Type", type = DataType.STRING)
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ActionParam(key = "version", message="version", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ResponseBody
	public Result paymentList(HttpServletRequest request, Integer pageNum, Integer pageSize, String goodsIssue, String gameType, String demoFlag, String version, String channel){
		InputParams params = new InputParams("frontUserAccountService", "paymentList");
		params.addParams("uuid", null, getFrontUser(request).getUuid());
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("goodsIssue", null, goodsIssue);
		params.addParams("gameType", null, gameType);
		params.addParams("demoFlag", null, demoFlag);
		params.addParams("version", null, version);
		params.addParams("channel", null, channel);
		return this.execute(params);
	}
	
	/**
	 * 获取用户参与记录详情
	 * @param request
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "获取用户参与记录详情", notes = "获取用户参与记录详情")
	@RequestMapping(value="/paymentGet",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING ,required = true)
	@ResponseBody
	public Result paymentGet(HttpServletRequest request, String uuid){
		InputParams params = new InputParams("frontUserAccountService", "paymentGet");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取用户参与号码列表
	 * @param request
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "获取用户参与号码列表", notes = "获取用户参与号码列表")
	@RequestMapping(value="/luckyNumList",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result luckyNumList(HttpServletRequest request, String uuid){
		InputParams params = new InputParams("frontUserAccountService", "luckyNumList");
		params.addParams("uuid", null, getFrontUser(request).getUuid());
		params.addParams("luckygameGoodsIssue", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取用户中奖纪录列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "获取用户中奖纪录列表", notes = "获取用户中奖纪录列表")
	@RequestMapping(value="/winningInfoList",method=RequestMethod.GET)
	@ActionParam(key = "frontUser", message="frontUser", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "gameType", message="Game Type", type = DataType.STRING)
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ActionParam(key = "version", message="version", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ResponseBody
	public Result winningInfoList(HttpServletRequest request, Integer pageNum, Integer pageSize, String gameType, String demoFlag, String version, String channel){
		InputParams params = new InputParams("frontUserAccountService", "winningInfoList");
		params.addParams("uuid", null, getFrontUser(request).getUuid());
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("gameType", null, gameType);
		params.addParams("demoFlag", null, demoFlag);
		params.addParams("version", null, version);
		params.addParams("channel", null, channel);
		return this.execute(params);
	}
	
	/**
	 * 获取用户中奖纪录详情
	 * @param request
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = " 获取用户中奖纪录详情", notes = " 获取用户中奖纪录详情")
	@RequestMapping(value="/winningInfoGet",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING ,required = true)
	@ResponseBody
	public Result winningInfoGet(HttpServletRequest request, String uuid){
		InputParams params = new InputParams("frontUserAccountService", "winningInfoGet");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		params.addParams("uuid", null, uuid);
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
	@ActionParam(key = "winningInfo", message="winningInfo", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="receive type", type = DataType.STRING, required = true)
	@ActionParam(key = "frontUserAddress", message="frontUserAddress", type = DataType.STRING)
	@ResponseBody
	public Result receive(HttpServletRequest request, String winningInfo
			, String type, String frontUserAddress){
		InputParams params = new InputParams("frontUserAccountService", "receive");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		params.addParams("winningInfo", null, winningInfo);
		params.addParams("type", null, type);
		params.addParams("ip", null, Utlity.getIpAddr(request));
		params.addParams("frontUserAddress", null, frontUserAddress);
		return this.execute(params);
	}
	
	/**
	 * 兑奖列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@ApiOperation(value = "兑奖列表", notes = "兑奖列表")
	@RequestMapping(value="/receiveList",method=RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "gameType", message="Game Type", type = DataType.STRING)
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ActionParam(key = "version", message="version", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ResponseBody
	public Result receiveList(HttpServletRequest request, Integer pageNum, Integer pageSize, String sort, String gameType, String demoFlag, String version, String channel){
		InputParams params = new InputParams("frontUserAccountService", "receiveList");
		params.addParams("uuid", null, getFrontUser(request).getUuid());
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("gameType", null, gameType);
		params.addParams("demoFlag", null, demoFlag);
		params.addParams("version", null, version);
		params.addParams("channel", null, channel);
		return this.execute(params);
	}
	
	/**
	 * 兑奖详情
	 * @param request
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "兑奖详情", notes = "兑奖详情")
	@RequestMapping(value="/receiveGet",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result receiveGet(HttpServletRequest request, String uuid){
		InputParams params = new InputParams("frontUserAccountService", "receiveGet");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 个人晒单列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@ApiOperation(value = "我的晒单", notes = "个人晒单列表")
	@RequestMapping(value="/commentList",method=RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result commentList(HttpServletRequest request, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("frontUserAccountService", "commentList");
		params.addParams("uuid", null, getFrontUser(request).getUuid());
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 晒单详情
	 * @param request
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "我的晒单详情", notes = "个人晒单详情")
	@RequestMapping(value="/commentGet",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result commentGet(HttpServletRequest request, String uuid){
		InputParams params = new InputParams("frontUserAccountService", "commentGet");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 提交晒单
	 * @param request
	 * @param winningInfo
	 * @param imageList
	 * @param videoList
	 * @param detail
	 * @return
	 */
	@ApiOperation(value = "提交晒单", notes = "提交晒单（imageList格式：uuid,uuid;videoList格式同上）")
	@RequestMapping(value="/commentAdd",method=RequestMethod.POST)
	@ActionParam(key = "winningInfo", message="winningInfo", type = DataType.STRING, required = true)
	@ActionParam(key = "imageList", message="imageList", type = DataType.STRING)
	@ActionParam(key = "videoList", message="videoList", type = DataType.STRING)
	@ActionParam(key = "detail", message="detail", type = DataType.STRING, required = true)
	@ResponseBody
	public Result commentAdd(HttpServletRequest request, String winningInfo
			, String imageList, String videoList, String detail){
		InputParams params = new InputParams("frontUserAccountService", "commentAdd");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		params.addParams("winningInfo", null, winningInfo);
		params.addParams("imageList", null, imageList);
		params.addParams("videoList", null, videoList);
		params.addParams("detail", null, detail);
		return this.execute(params);
	}
	
	/**
	 * 用户积分明细列表
	 * @return
	 */
	@ApiOperation(value = "获取用户交易流水", notes = "获取用户交易流水")
	@RequestMapping(value = "/scoreList", method = RequestMethod.GET)
	@ActionParam(key = "type", message="type", type = DataType.STRING)
	@ActionParam(key = "starttime", message="starttime", type = DataType.STRING)
	@ActionParam(key = "endtime", message="endtime", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ResponseBody
	public Result scoreList(HttpServletRequest request, String type, String starttime, String endtime, Integer pageNum, Integer pageSize) {
		InputParams params = new InputParams("frontUserAccountService", "scoreList");
		params.addParams("type", null, type);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 用户积分明细记录
	 * @return
	 */
	@ApiOperation(value = "获取用户交易详情", notes = "获取用户交易详情")
	@RequestMapping(value = "/scoreGet", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, message="uuid", required = true)
	@ResponseBody
	public Result scoreGet(HttpServletRequest request, String uuid) {
		InputParams params = new InputParams("frontUserAccountService", "scoreGet");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
}
