/**
 * 
 */
package cn.product.score.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.api.base.ResultManager;
import cn.product.score.controller.BaseController;
import cn.product.score.entity.FrontUser;

/**
 * 用户竞猜信息
 */

@Controller
@RequestMapping(value = "/front/userBet")
public class FrontUserBetController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1976759489141986701L;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String status, Integer pageNum, Integer pageSize, String sort, HttpServletRequest request){
		
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		
		InputParams params = new InputParams("frontUserBetService", "list");
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("fu", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid, HttpServletRequest request){
		
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		
		InputParams params = new InputParams("frontUserBetService", "get");
		params.addParams("uuid", null, uuid);
		params.addParams("fu", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}
	
	
	/**
	 * 竞猜投注
	 * @param bet 
	 * @param price
	 * @param detail 
	 * @return
	 */
	@RequestMapping(value = "/bet", method = RequestMethod.POST)
	@ActionParam(key = "bet", type = DataType.STRING, message = "投注方式", required = true)
	@ActionParam(key = "price", type = DataType.STRING, message = "投注总额", required = true)
	@ActionParam(key = "type", type = DataType.STRING, message = "投注类型", required = true)
	@ActionParam(key = "details", type = DataType.STRING_ARRAY, message = "投注详情", required = true)
	@ResponseBody
	public Result bet(String bet, String price, String type, String[] details, HttpServletRequest request){
		
		FrontUser fu = getFrontUser(request);
		if(fu == null) {
			return ResultManager.createFailResult("用户未登录！");
		}
		
		InputParams params = new InputParams("frontUserBetService", "bet");
		params.addParams("bet", null, bet);
		params.addParams("price", null, price);
		params.addParams("type", null, type);
		params.addParams("details", null, details);
		params.addParams("fu", null, getFrontUser(request).getUuid());
		return this.execute(params);
	}

}
