package cn.zeppin.product.score.controller.back;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.Admin;
import cn.zeppin.product.score.entity.CapitalPlatform;
import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformStatus;
import cn.zeppin.product.score.service.CapitalPlatformService;

@Controller
@RequestMapping(value = "/back/capitalPlatform")
public class CapitalPlatformController extends BaseController{
	
	@Autowired
    private CapitalPlatformService capitalPlatformService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid){
		CapitalPlatform capitalPlatform = capitalPlatformService.get(uuid);
		if(capitalPlatform == null || CapitalPlatformStatus.DELETE.equals(capitalPlatform.getStatus())){
			return ResultManager.createFailResult("渠道不存在");
		}
		return ResultManager.createDataResult(capitalPlatform);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "transType", message="交易类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.STRING)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.STRING)
	@ActionParam(key = "sort", message="排序", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String transType, String status, Integer pageNum, Integer pageSize, String sort){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("transType", transType);
		params.put("status", status);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = capitalPlatformService.getCountByParams(params);
		List<CapitalPlatform> capitalPlatformList = capitalPlatformService.getListByParams(params);
		
		return ResultManager.createDataResult(capitalPlatformList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "type", message="类型", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "transType", message="交易类型", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ResponseBody
    public Result add(CapitalPlatform capitalPlatform){
		Admin admin = this.getCurrentOperator();
		
		capitalPlatform.setUuid(UUID.randomUUID().toString());
		capitalPlatform.setCreator(admin.getUuid());
		capitalPlatform.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalPlatformService.insert(capitalPlatform);
		return ResultManager.createSuccessResult("添加成功！");
    }
    
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "remark", message="备注", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ActionParam(key = "sort", message="排序", type = DataType.NUMBER, required = true)
	@ResponseBody
	public Result edit(CapitalPlatform capitalPlatform){
		Admin admin = this.getCurrentOperator();
		
		CapitalPlatform cp = capitalPlatformService.get(capitalPlatform.getUuid());
		if(cp == null || CapitalPlatformStatus.DELETE.equals(cp.getStatus())){
			return ResultManager.createFailResult("渠道不存在");
		}
		cp.setName(capitalPlatform.getName());
		cp.setRemark(capitalPlatform.getRemark());
		cp.setStatus(capitalPlatform.getStatus());
		cp.setSort(capitalPlatform.getSort());
		cp.setCreator(admin.getUuid());
		cp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		capitalPlatformService.update(cp);
		return ResultManager.createSuccessResult("修改成功！");
	}
	
	@RequestMapping(value="/changeStatus",method=RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status){
		CapitalPlatform cp = capitalPlatformService.get(uuid);
		if(cp == null || CapitalPlatformStatus.DELETE.equals(cp.getStatus())){
			return ResultManager.createFailResult("渠道不存在");
		}
		
		cp.setStatus(status);
		
		capitalPlatformService.update(cp);
		return ResultManager.createSuccessResult("修改成功！");
	}
}
