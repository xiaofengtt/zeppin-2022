package cn.product.treasuremall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.entity.GoodsType;
import cn.product.treasuremall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/goodsType")
public class GoodsTypeController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4499330222585317799L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "code", message="商品编码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String code){
		InputParams params = new InputParams("goodsTypeService", "get");
		params.addParams("code", null, code);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "parent", message="父级", type = DataType.STRING)
	@ActionParam(key = "level", message="级别", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String parent, String level, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("goodsTypeService", "list");
		params.addParams("name", null, name);
		params.addParams("parent", null, parent);
		params.addParams("level", null, level);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "code", message="商品编码", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "parent", message="父类型", type = DataType.STRING)
	@ActionParam(key = "icon", message="图标", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ResponseBody
    public Result add(GoodsType goodsType){
		InputParams params = new InputParams("goodsTypeService", "add");
		params.addParams("goodsType", null, goodsType);
		return this.execute(params);
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "code", message="商品编码", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "icon", message="图标", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result edit(GoodsType goodsType){
		InputParams params = new InputParams("goodsTypeService", "edit");
		params.addParams("goodsType", null, goodsType);
		return this.execute(params);
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "code", message="商品编码", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String code, String status){
		InputParams params = new InputParams("goodsTypeService", "changeStatus");
		params.addParams("code", null, code);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
