package cn.product.worldmall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.worldmall.api.base.ActionParam;
import cn.product.worldmall.api.base.ActionParam.DataType;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.api.base.Result;
import cn.product.worldmall.entity.Goods;
import cn.product.worldmall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/goods")
public class GoodsController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -302522100813056740L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("goodsService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "type", message="type", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ActionParam(key = "demoFlag", message="demoFlag", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String type, String status, Integer pageNum, Integer pageSize, String sort, String demoFlag){
		InputParams params = new InputParams("goodsService", "list");
		params.addParams("name", null, name);
		params.addParams("type", null, type);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		params.addParams("demoFlag", null, demoFlag);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "shortname", message="简称", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="type", type = DataType.STRING, required = true)
	@ActionParam(key = "code", message="商品编码", type = DataType.STRING, required = true)
	@ActionParam(key = "source", message="来源", type = DataType.STRING)
	@ActionParam(key = "sourceUrl", message="来源链接", type = DataType.STRING)
	@ActionParam(key = "price", message="价格", type = DataType.NUMBER, required = true)
	@ActionParam(key = "costs", message="成本", type = DataType.NUMBER, required = true)
	@ActionParam(key = "description", message="描述", type = DataType.STRING, required = true)
	@ActionParam(key = "video", message="视频", type = DataType.STRING)
	@ActionParam(key = "images", message="图片", type = DataType.STRING_ARRAY)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "demoFlag", message="是否马甲", type = DataType.BOOLEAN, required = true)
	@ResponseBody
    public Result add(Goods goods, String[] images){
		InputParams params = new InputParams("goodsService", "add");
		params.addParams("goods", null, goods);
		params.addParams("images", null, images);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "shortname", message="简称", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="type", type = DataType.STRING, required = true)
	@ActionParam(key = "code", message="商品编码", type = DataType.STRING, required = true)
	@ActionParam(key = "source", message="来源", type = DataType.STRING)
	@ActionParam(key = "sourceUrl", message="来源链接", type = DataType.STRING)
	@ActionParam(key = "price", message="价格", type = DataType.NUMBER, required = true)
	@ActionParam(key = "costs", message="成本", type = DataType.NUMBER, required = true)
	@ActionParam(key = "description", message="描述", type = DataType.STRING, required = true)
	@ActionParam(key = "video", message="视频", type = DataType.STRING)
	@ActionParam(key = "images", message="图片", type = DataType.STRING_ARRAY)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ActionParam(key = "demoFlag", message="是否马甲", type = DataType.BOOLEAN, required = true)
	@ResponseBody
	public Result edit(Goods goods, String[] images){
		InputParams params = new InputParams("goodsService", "edit");
		params.addParams("goods", null, goods);
		params.addParams("images", null, images);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("goodsService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
