/**
 * 
 */
package cn.product.score.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.score.api.base.ActionParam;
import cn.product.score.api.base.ActionParam.DataType;
import cn.product.score.api.base.InputParams;
import cn.product.score.api.base.Result;
import cn.product.score.controller.BaseController;

/**
 * 竞猜信息
 */

@Controller
@RequestMapping(value = "/front/guessingMatch")
public class FrontGuessingMatchController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5927239636192047121L;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ActionParam(key = "category", message="联赛", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result list(String type, String[] category, String sort){
		
		InputParams params = new InputParams("frontGuessingMatchService", "list");
		params.addParams("category", null, category);
		params.addParams("type", null, type);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/categoryList",method=RequestMethod.GET)
	@ActionParam(key = "type", message="类型", type = DataType.STRING, required = true)
	@ResponseBody
	public Result categoryList(String type){
		
		InputParams params = new InputParams("frontGuessingMatchService", "categoryList");
		params.addParams("type", null, type);
		return this.execute(params);
	}
}
