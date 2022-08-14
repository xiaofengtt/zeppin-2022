package cn.product.treasuremall.controller.back;

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
@RequestMapping(value = "/back/userComment")
public class UserCommentController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9114865495708647456L;

	/**
	 * 获取详情
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("userCommentService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	/**
	 * 获取列表
	 * @param showId
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String showId, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("userCommentService", "list");
		params.addParams("showId", null, showId);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	/**
	 * 审核
	 * @param uuid
	 * @param status checked/nopass
	 * @param reason
	 * @return
	 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "reason", message="审核原因", type = DataType.STRING, required = true)
	@ResponseBody
	public Result check(String uuid, String status, String reason){
		InputParams params = new InputParams("userCommentService", "check");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		params.addParams("reason", null, reason);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
