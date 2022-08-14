package cn.product.treasuremall.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.treasuremall.api.base.ActionParam;
import cn.product.treasuremall.api.base.ActionParam.DataType;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.api.base.Result;
import cn.product.treasuremall.entity.CapitalPlatform;
import cn.product.treasuremall.controller.BaseController;

@Controller
@RequestMapping(value = "/back/capitalPlatform")
public class CapitalPlatformController extends BaseController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6616019882801692619L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("capitalPlatformService", "get");
		params.addParams("uuid", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String type, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("capitalPlatformService", "list");
		params.addParams("name", null, name);
		params.addParams("type", null, type);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="支付类型", type = DataType.STRING, required = true)
	@ActionParam(key = "transType", message="支付方式", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ActionParam(key = "isRecommend", message="是否推荐", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "isUniqueAmount", message="是否独特金额", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "isRandomAmount", message="是否随机金额", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "max", message="最大金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "min", message="最小金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "logo", message="图标", type = DataType.STRING, required = true)
	@ActionParam(key = "explanation", message="用户须知", type = DataType.STRING, required = true)
	@ActionParam(key = "explanImg", message="说明图片", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="描述", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
    public Result add(CapitalPlatform capitalPlatform){
		InputParams params = new InputParams("capitalPlatformService", "add");
		params.addParams("capitalPlatform", null, capitalPlatform);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ActionParam(key = "isRecommend", message="是否推荐", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "isUniqueAmount", message="是否独特金额", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "isRandomAmount", message="是否随机金额", type = DataType.BOOLEAN, required = true)
	@ActionParam(key = "max", message="最大金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "min", message="最小金额", type = DataType.POSITIVE_CURRENCY, required = true)
	@ActionParam(key = "logo", message="图标", type = DataType.STRING, required = true)
	@ActionParam(key = "explanation", message="用户须知", type = DataType.STRING, required = true)
	@ActionParam(key = "explanImg", message="说明图片", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="描述", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(CapitalPlatform capitalPlatform){
		InputParams params = new InputParams("capitalPlatformService", "edit");
		params.addParams("capitalPlatform", null, capitalPlatform);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("capitalPlatformService", "changeStatus");
		params.addParams("uuid", null, uuid);
		params.addParams("status", null, status);
		return this.execute(params);
	}
}
