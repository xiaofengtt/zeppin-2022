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
@RequestMapping(value = "/front/message")
@Api(tags= {"message"})
public class FrontUserMessageController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 177148920289320522L;

	/**
	 * 消息列表
	 * @param request
	 * @param sorts
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "消息列表", notes = "消息列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "sorts", message="sorts", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.NUMBER)
	@ResponseBody
	public Result list(HttpServletRequest request, String status, String sorts, Integer pageNum, Integer pageSize) {
		InputParams params = new InputParams("frontUserMessageService", "list");
		params.addParams("sorts", null, sorts);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}

	/**
	 * 消息详情
	 * @param request
	 * @param sorts
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "消息详情", notes = "消息详情")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(HttpServletRequest request, String uuid) {
		InputParams params = new InputParams("frontUserMessageService", "get");
		params.addParams("uuid", null, uuid);
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	/**
	 * 全部已读
	 * @param request
	 * @param sorts
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "全部已读", notes = "全部已读")
	@RequestMapping(value = "/readAll", method = RequestMethod.GET)
	@ResponseBody
	public Result readAll(HttpServletRequest request) {
		InputParams params = new InputParams("frontUserMessageService", "readAll");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}

	/**
	 * 未读数量
	 * @param request
	 * @param sorts
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "未读数量", notes = "未读数量")
	@RequestMapping(value = "/unRead", method = RequestMethod.GET)
	@ResponseBody
	public Result unRead(HttpServletRequest request) {
		InputParams params = new InputParams("frontUserMessageService", "unRead");
		params.addParams("frontUser", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
}
