package cn.product.treasuremall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.controller.BaseController;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;

@Controller
@RequestMapping(value = "/back/param")
public class SystemParamController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6440243964311425602L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "paramKey", message="参数名", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String paramKey){
		
		InputParams params = new InputParams("systemParamService", "get");
		params.addParams("paramKey", null, paramKey);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "description", message="描述", type = DataType.STRING)
	@ActionParam(key = "partitional", message="功能分类", type = DataType.STRING)
	@ActionParam(key = "type", message="参数类型", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String description, String partitional, String type, Integer pageNum, Integer pageSize, String sort){
		
		InputParams params = new InputParams("systemParamService", "list");
		params.addParams("description", null, description);
		params.addParams("partitional", null, partitional);
		params.addParams("type", null, type);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	@ActionParam(key = "paramKey", message="参数名", type = DataType.STRING, required = true)
	@ActionParam(key = "paramValues", message="参数值", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result edit(String paramKey, String[] paramValues){
		
		InputParams params = new InputParams("systemParamService", "edit");
		params.addParams("paramKey", null, paramKey);
		params.addParams("paramValues", null, paramValues);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
