package cn.zeppin.product.score.controller.back;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.zeppin.product.score.entity.CapitalAccount;
import cn.zeppin.product.score.entity.CapitalAccount.CapitalAccountStatus;
import cn.zeppin.product.score.entity.CapitalPlatform.CapitalPlatformTransType;
import cn.zeppin.product.score.entity.SystemParam;
import cn.zeppin.product.score.entity.SystemParam.SystemParamKey;
import cn.zeppin.product.score.entity.SystemParam.SystemParamType;
import cn.zeppin.product.score.service.AdminService;
import cn.zeppin.product.score.service.CapitalAccountService;
import cn.zeppin.product.score.service.SystemParamService;
import cn.zeppin.product.score.util.JSONUtils;
import cn.zeppin.product.score.util.Utlity;
import cn.zeppin.product.score.vo.back.SystemParamVO;

@Controller
@RequestMapping(value = "/back/param")
public class SystemParamController extends BaseController{
	
	@Autowired
    private SystemParamService systemParamService;
	
	@Autowired
    private CapitalAccountService capitalAccountService;
	
	@Autowired
    private AdminService adminService;
	
	@RequestMapping(value="/get",method=RequestMethod.GET)
	@ActionParam(key = "paramKey", message="参数名", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String paramKey){
		SystemParam sp = systemParamService.get(paramKey);
		if(sp == null){
			return ResultManager.createFailResult("参数不存在");
		}
		
		SystemParamVO spvo = new SystemParamVO(sp);
		
		Admin admin = this.adminService.get(sp.getCreator());
		if(admin != null){
			spvo.setCreatorName(admin.getRealname());
		}
		
		return ResultManager.createDataResult(spvo);
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
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("description", description);
		params.put("partitional", partitional);
		params.put("type", type);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("sort", sort);
		
		Integer totalCount = systemParamService.getCountByParams(params);
		List<SystemParam> spList = systemParamService.getListByParams(params);
		
		List<SystemParamVO> voList = new ArrayList<SystemParamVO>();
		for(SystemParam sp : spList){
			SystemParamVO spvo = new SystemParamVO(sp);
			
			Admin admin = this.adminService.get(sp.getCreator());
			if(admin != null){
				spvo.setCreatorName(admin.getRealname());
			}
			
			voList.add(spvo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	@ActionParam(key = "paramKey", message="参数名", type = DataType.STRING, required = true)
	@ActionParam(key = "paramValues", message="参数值", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result edit(String paramKey, String[] paramValues){
		Admin admin = this.getCurrentOperator();
		
		SystemParam sp = systemParamService.get(paramKey);
		if(sp == null){
			return ResultManager.createFailResult("参数不存在");
		}
		
		if(SystemParamType.NUMERIC.equals(sp.getType())){
			if(paramValues.length != 1 || !Utlity.isNumeric(paramValues[0])){
				return ResultManager.createFailResult("请输入数字类型参数");
			}
			sp.setParamValue(paramValues[0]);
		}else if(SystemParamType.CURRENCY.equals(sp.getType())){
			if(paramValues.length != 1 || !Utlity.isPositiveCurrency(paramValues[0])){
				return ResultManager.createFailResult("请输入货币类型参数");
			}
			sp.setParamValue(paramValues[0]);
		}else if(SystemParamType.BOOLEAN.equals(sp.getType())){
			if(paramValues.length != 1 || (!paramValues[0].equals("true") && !paramValues[0].equals("false"))){
				return ResultManager.createFailResult("请输入布尔类型参数");
			}
			sp.setParamValue(paramValues[0]);
		}else if(SystemParamType.MAP.equals(sp.getType())){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			for(String paramValue : paramValues){
				String[] params = paramValue.split("@_@");
				if(params == null || params.length != 2){
					return ResultManager.createFailResult("请输入Map类型参数");
				}
				paramMap.put(params[0].trim(), params[1].trim());
			}
			if(!Utlity.checkSystemParamMap(sp.getParamKey(), paramMap)){
				return ResultManager.createFailResult("请输参数有误，请仔细检查！");
			}
			sp.setParamValue(JSONUtils.obj2json(paramMap));
		}else if(SystemParamType.PRIMARYKEY.equals(sp.getType())){
			if(paramValues.length != 1){
				return ResultManager.createFailResult("请选择正确的参数值！");
			}
			if(SystemParamKey.WITHDRAW_DEFAULT_ACCOUNT.equals(paramKey)){
				CapitalAccount ca = this.capitalAccountService.get(paramValues[0]);
				if(ca == null || !CapitalAccountStatus.NORMAL.equals(ca.getStatus()) || !CapitalPlatformTransType.WITHDRAW.equals(ca.getTransType())){
					return ResultManager.createFailResult("请选择正确的参数值！");
				}
			}
			sp.setParamValue(paramValues[0]);
		}else{
			if(paramValues.length != 1){
				return ResultManager.createFailResult("请输入字符串类型参数");
			}
			sp.setParamValue(paramValues[0].trim());
		}
		
		sp.setCreator(admin.getUuid());
		sp.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.systemParamService.update(sp);
		return ResultManager.createSuccessResult("修改成功！");
	}
}
