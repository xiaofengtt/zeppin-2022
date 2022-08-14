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
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户信息
 */

@Controller
@RequestMapping(value = "/front/comment")
@Api(tags= {"comment"})
public class FrontUserCommentController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1626838880561812567L;
	
	/**
	 * 拍拍圈列表
	 * @param request
	 * @param sorts
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "拍拍圈列表", notes = "拍拍圈列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "sorts", message="sorts", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ActionParam(key = "version", message="version", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ResponseBody
	public Result list(HttpServletRequest request, String sorts, Integer pageNum, Integer pageSize, String demoFlag, String version, String channel) {
		InputParams params = new InputParams("frontUserCommentService", "list");
		params.addParams("sorts", null, sorts);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("demoFlag", null, demoFlag);
		params.addParams("version", null, version);
		params.addParams("channel", null, channel);
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
	@ApiOperation(value = "根据用户ID获取用户参与记录列表", notes = "获取用户参与记录列表")
	@RequestMapping(value="/paymentList",method=RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "frontUser", message="frontUser", type = DataType.STRING, required = true)
	@ActionParam(key = "gameType", message="Game Type", type = DataType.STRING)
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ActionParam(key = "version", message="version", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ResponseBody
	public Result paymentList(HttpServletRequest request, Integer pageNum, Integer pageSize, String frontUser, String gameType, String demoFlag, String version, String channel){
		InputParams params = new InputParams("frontUserAccountService", "paymentList");
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("uuid", null, frontUser);
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
	 * @param frontUser
	 * @return
	 */
	@ApiOperation(value = "获取用户参与记录详情", notes = "获取用户参与记录详情")
	@RequestMapping(value="/paymentGet",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING ,required = true)
	@ActionParam(key = "frontUser", message="frontUser", type = DataType.STRING, required = true)
	@ResponseBody
	public Result paymentGet(HttpServletRequest request, String uuid, String frontUser){
		InputParams params = new InputParams("frontUserAccountService", "paymentGet");
		params.addParams("frontUser", null, frontUser);
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取用户中奖纪录列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "根据用户ID获取用户中奖纪录列表", notes = "获取用户中奖纪录列表")
	@RequestMapping(value="/winningInfoList",method=RequestMethod.GET)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "frontUser", message="frontUser", type = DataType.STRING, required = true)
	@ActionParam(key = "gameType", message="Game Type", type = DataType.STRING)
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ActionParam(key = "version", message="version", type = DataType.STRING)
	@ActionParam(key = "channel", message="channel", type = DataType.STRING)
	@ResponseBody
	public Result winningInfoList(HttpServletRequest request, Integer pageNum, Integer pageSize, String frontUser, String gameType, String demoFlag, String version, String channel){
		InputParams params = new InputParams("frontUserAccountService", "winningInfoList");
		params.addParams("uuid", null, frontUser);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("gameType", null, gameType);
		params.addParams("demoFlag", null, demoFlag);
		params.addParams("version", null, version);
		params.addParams("channel", null, channel);
		return this.execute(params);
	}
}
