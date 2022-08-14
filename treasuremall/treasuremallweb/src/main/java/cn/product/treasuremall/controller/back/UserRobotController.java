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
import cn.product.treasuremall.entity.RobotSetting;

@Controller
@RequestMapping(value = "/back/robot")
public class UserRobotController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1908844808379870830L;

	/**
	 * 获取用户基本信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userRobotService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取列表
	 * @param realname
	 * @param mobile
	 * @param status
	 * @param showid
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "realname", message="真实姓名", type = DataType.STRING)
	@ActionParam(key = "mobile", message="手机号码", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "showid", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING)
	@ResponseBody
	public Result list(String realname, String mobile, String status, String showid, String gameType, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userRobotService", "list");
		params.addParams("realname", null, realname);
		params.addParams("mobile", null, mobile);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("showid", null, showid);
		params.addParams("gameType", null, gameType);
		return this.execute(params);
	}
	
	/**
	 * 添加机器人用户
	 * @param mobile
	 * @param nickname 不填默认手机号加密后信息，如186****1111
	 * @param image
	 * @param pwdstr 不填写默认自动生成8位随机密码
	 * @param ipstr 不填写默认自动生成国内随机省份IP
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "mobile", message="手机号码", type = DataType.STRING, required = true)
	@ActionParam(key = "nickname", message="昵称", type = DataType.STRING)
	@ActionParam(key = "image", message="头像", type = DataType.STRING)
	@ActionParam(key = "pwdstr", message="登录密码", type = DataType.STRING)
	@ActionParam(key = "ipstr", message="IP地址", type = DataType.STRING)
	@ResponseBody
	public Result add(String mobile, String nickname, String image, String pwdstr, String ipstr){
		InputParams params = new InputParams("userRobotService", "add");
		params.addParams("mobile", null, mobile);
		params.addParams("nickname", null, nickname);
		params.addParams("image", null, image);
		params.addParams("pwdstr", null, pwdstr);
		params.addParams("ipstr", null, ipstr);
		return this.execute(params);
	}
	
	/**
	 * 编辑一个机器人用户
	 * @param uuid
	 * @param nickname
	 * @param image
	 * @param pwdstr
	 * @param ipstr
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "nickname", message="昵称", type = DataType.STRING)
	@ActionParam(key = "image", message="头像", type = DataType.STRING)
	@ActionParam(key = "pwdstr", message="登录密码", type = DataType.STRING)
	@ActionParam(key = "ipstr", message="IP地址", type = DataType.STRING)
	@ResponseBody
	public Result edit(String uuid, String nickname, String image, String pwdstr, String ipstr){
		InputParams params = new InputParams("userRobotService", "edit");
		params.addParams("uuid", null, uuid);
		params.addParams("nickname", null, nickname);
		params.addParams("image", null, image);
		params.addParams("pwdstr", null, pwdstr);
		params.addParams("ipstr", null, ipstr);
		return this.execute(params);
	}
	
	/**
	 * 批量启用停止
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuids", message="uuid", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String[] uuids, String status){
		InputParams params = new InputParams("userRobotService", "changeStatus");
		params.addParams("uuids", null, uuids);
		params.addParams("status", null, status);
		return this.execute(params);
	}
	
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid){
		InputParams params = new InputParams("userRobotService", "delete");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 设置机器人工作信息
	 * @param uuids
	 * @param robot
	 * @return
	 */
	@RequestMapping(value="/settingadd",method=RequestMethod.POST)
	@ActionParam(key = "uuids", message="uuid", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "gameType", message="玩法类型", type = DataType.STRING, required = true)
	@ActionParam(key = "minPay", message="最小投注金额", type = DataType.STRING, required = true)
	@ActionParam(key = "maxPay", message="最大投注金额", type = DataType.STRING, required = true)
	@ActionParam(key = "periodMin", message="最小时间间隔", type = DataType.INTEGER, required = true)
	@ActionParam(key = "periodRandom", message="最大时间间隔", type = DataType.INTEGER, required = true)
	@ActionParam(key = "worktimeBegin", message="参与起始时间", type = DataType.STRING, required = true)
	@ActionParam(key = "worktimeEnd", message="参与结束时间", type = DataType.STRING, required = true)
	@ActionParam(key = "goods", message="奖品配置", type = DataType.STRING, required = true)
	@ActionParam(key = "goodsPriceMin", message="最小商品价格", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "goodsPriceMax", message="最大商品价格", type = DataType.POSITIVE_CURRENCY, required = true)
	@ResponseBody
	public Result settingadd(String[] uuids, RobotSetting robot){
		InputParams params = new InputParams("userRobotService", "settingadd");
		params.addParams("uuids", null, uuids);
		params.addParams("robot", null, robot);
		return this.execute(params);
	}
	
	/**
	 * 设置包尾
	 * @param uuid
	 * @param isAll true/false
	 * @return
	 */
	@RequestMapping(value="/isAll",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "isAll", message="是否包尾", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result isAll(String uuid, Boolean isAll){
		InputParams params = new InputParams("userRobotService", "isAll");
		params.addParams("uuid", null, uuid);
		params.addParams("isAll", null, isAll);
		return this.execute(params);
	}
	
	/**
	 * 获取机器人工作信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/settingget",method=RequestMethod.GET)
	@ActionParam(key = "uuids", message="uuid", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result settingget(String uuids){
		InputParams params = new InputParams("userRobotService", "settingget");
		params.addParams("uuids", null, uuids);
		return this.execute(params);
	}
	
	/**
	 * 加金币
	 * @param uuids
	 * @param fee
	 * @return
	 */
	@RequestMapping(value="/goldadd",method=RequestMethod.POST)
	@ActionParam(key = "uuids", message="uuids", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "fee", message = "金额", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result goldadd(String[] uuids, BigDecimal fee){
		InputParams params = new InputParams("userRobotService", "goldadd");
		params.addParams("uuids", null, uuids);
		params.addParams("fee", null, fee);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	/**
	 * 减金币
	 * @param uuids
	 * @param fee
	 * @return
	 */
	@RequestMapping(value="/goldsub",method=RequestMethod.POST)
	@ActionParam(key = "uuids", message="uuids", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "fee", message = "金额", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result goldsub(String[] uuids, BigDecimal fee){
		InputParams params = new InputParams("userRobotService", "goldsub");
		params.addParams("uuids", null, uuids);
		params.addParams("fee", null, fee);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
