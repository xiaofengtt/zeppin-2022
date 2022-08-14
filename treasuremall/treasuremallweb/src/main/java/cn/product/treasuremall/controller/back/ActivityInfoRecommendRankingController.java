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
@RequestMapping(value = "/back/recommendRanking")
public class ActivityInfoRecommendRankingController extends BaseController{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3054834393404666102L;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, String type, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("activityInfoRecommendRankingService", "list");
		params.addParams("status", null, status);
		params.addParams("type", null, type);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "data", message="数据", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(String data){
		InputParams params = new InputParams("activityInfoRecommendRankingService", "edit");
		params.addParams("data", null, data);
		return this.execute(params);
	}
}
