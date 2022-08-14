package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.controller.BaseController;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;

@Controller
@RequestMapping(value = "/back/userRecommend")
public class UserRecommendController extends BaseController{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5896156390022168179L;

	/**
	 * 获取列表
	 * @param showId
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @param bettime
	 * @param userType
	 * @param status
	 * @param prizeType
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "showId", message="用户ID", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "mobile", message="用户手机号", type = DataType.STRING)
	@ActionParam(key = "createtime", message="创建时间", type = DataType.STRING)
	@ResponseBody
	public Result list(String showId, Integer pageNum, Integer pageSize, String sort, String mobile, String createtime){
		InputParams params = new InputParams("userRecommendService", "list");
		params.addParams("showId", null, showId);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("mobile", null, mobile);
		params.addParams("createtime", null, createtime);
		return this.execute(params);
	}
}
