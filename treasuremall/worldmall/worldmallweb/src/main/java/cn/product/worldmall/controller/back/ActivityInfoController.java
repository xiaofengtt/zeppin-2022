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
@RequestMapping(value = "/back/activity")
public class ActivityInfoController extends BaseController{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -294019030272595722L;

	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		InputParams params = new InputParams("activityInfoService", "get");
		params.addParams("name", null, uuid);
		return this.execute(params);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "name", message="search", type = DataType.STRING)
	@ActionParam(key = "status", message="status", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="pageNum", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="pageSize", type = DataType.STRING)
	@ActionParam(key = "sort", message="sort", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String status, Integer pageNum, Integer pageSize, String sort){
		InputParams params = new InputParams("activityInfoService", "list");
		params.addParams("name", null, name);
		params.addParams("status", null, status);
		params.addParams("pageNum", null, pageNum);
		params.addParams("pageSize", null, pageSize);
		params.addParams("sort", null, sort);
		return this.execute(params);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "starttime", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "endtime", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "configuration", message="????????????", type = DataType.STRING)
	@ActionParam(key = "details", message="????????????", type = DataType.STRING, required = true)
	@ActionParam(key = "bannerUrl", message="banner?????????", type = DataType.STRING)
	@ActionParam(key = "linkUrl", message="????????????", type = DataType.STRING)
	@ResponseBody
    public Result edit(String uuid, String starttime, String endtime, String bannerUrl, String linkUrl, String configuration, String details){
		InputParams params = new InputParams("activityInfoService", "edit");
		params.addParams("name", null, uuid);
		params.addParams("starttime", null, starttime);
		params.addParams("endtime", null, endtime);
		params.addParams("bannerUrl", null, bannerUrl);
		params.addParams("linkUrl", null, linkUrl);
		params.addParams("configuration", null, configuration);
		params.addParams("details", null, details);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
    }
    
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="status", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		InputParams params = new InputParams("activityInfoService", "changeStatus");
		params.addParams("name", null, uuid);
		params.addParams("status", null, status);
		params.addParams("admin", null, this.getCurrentOperator().getUuid());
		return this.execute(params);
	}
}
