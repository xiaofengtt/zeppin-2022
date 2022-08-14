/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITSsLogService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorService;
import cn.zeppin.product.itic.backadmin.vo.TSsLogVO;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsRole.RoleId;
import cn.zeppin.product.utility.Utlity;

/**
 * 日志管理
 */

@Controller
@RequestMapping(value = "/backadmin/log")
public class TSsLogController extends BaseController {

	@Autowired
	private ITSsLogService tSsLogService;
	
	@Autowired
	private ITSsOperatorService tSsOperatorService;
	
	/**
	 * 查询日志列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "datatype", message = "数据类型", type = DataType.STRING)
	@ActionParam(key = "dataproduct", message = "信托项目", type = DataType.STRING)
	@ActionParam(key = "creator", message = "操作人", type = DataType.STRING)
	@ActionParam(key = "starttime", message = "起始时间", type = DataType.DATE)
	@ActionParam(key = "endtime", message = "结束时间", type = DataType.DATE)
	@ActionParam(key = "type", message = "操作类型", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String datatype, String dataproduct, String creator, String starttime, String endtime, String type, String status, 
			Integer pageNum, Integer pageSize, String sorts) {
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataproduct", dataproduct);
		inputParams.put("creator", creator);
		inputParams.put("starttime", starttime);
		inputParams.put("endtime", endtime);
		inputParams.put("type", type);
		inputParams.put("status", status);
		
		Integer count = tSsLogService.getCount(inputParams);
		List<TSsLog> list = tSsLogService.getListForPage(inputParams, pageNum, pageSize, sorts);
		List<TSsLogVO> voList = new ArrayList<TSsLogVO>();
		
		for(TSsLog t : list){
			TSsLogVO vo = new TSsLogVO(t);
			
			TSsOperator operator = this.tSsOperatorService.get(vo.getCreator());
			if(operator != null){
				vo.setCreatorName(operator.getName());
			}
			voList.add(vo);
		}
		
		return ResultManager.createDataResult(voList, count);
	}
	
	/**
	 * 获取信息
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "日志ID", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String id) {
		TSsLog log = tSsLogService.get(id);
		if(log == null){
			return ResultManager.createFailResult("数据不存在!");
		}
		
		TSsLogVO vo = new TSsLogVO(log);
		TSsOperator operator = this.tSsOperatorService.get(vo.getCreator());
		if(operator != null){
			vo.setCreatorName(operator.getName());
		}
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 查询审核日志列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ActionParam(key = "datatype", message = "数据类型", type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result checkList(String datatype, Integer pageNum, Integer pageSize, String sorts) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
		List<String> methodList = (List<String>) session.getAttribute("methodList");
		List<String> controllerList = new ArrayList<String>();
		for(String method : methodList){
			if(method.indexOf("check") > -1){
				controllerList.add(method.substring(0,method.indexOf(":")));
			}
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		if(RoleId.user.equals(operator.getRole())){
			if(RoleId.user.equals(operator.getRole())){inputParams.put("products", Utlity.getStringForSql(new ArrayList<String>(productMap.keySet())));}
			inputParams.put("datatypes", Utlity.getStringForSql(controllerList));
		}
		inputParams.put("datatype", datatype);
		inputParams.put("type", TSsLogType.CHECK);
		inputParams.put("status", TSsLogStatus.NORMAL);
		
		Integer count = tSsLogService.getCount(inputParams);
		List<TSsLog> list = tSsLogService.getListForPage(inputParams, pageNum, pageSize, sorts);
		List<TSsLogVO> voList = new ArrayList<TSsLogVO>();
		
		for(TSsLog t : list){
			TSsLogVO vo = new TSsLogVO(t);
			
			TSsOperator creator = this.tSsOperatorService.get(vo.getCreator());
			if(creator != null){
				vo.setCreatorName(creator.getName());
			}
			voList.add(vo);
		}
		
		return ResultManager.createDataResult(voList, count);
	}
}
