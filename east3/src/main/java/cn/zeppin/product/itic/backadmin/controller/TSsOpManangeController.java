/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.security.SecurityRealm;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorService;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsRole.RoleId;

/**
 * 用户管理查询
 */

@Controller
@RequestMapping(value = "/backadmin/opManage")
public class TSsOpManangeController extends BaseController {

	@Autowired
	private ITSsOperatorService tSsOperatorService;
	
	/**
	 * 所有用户
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		Map<String, String> params = new HashMap<>();
		params.put("role", RoleId.user);
		List<TSsOperator> list =  this.tSsOperatorService.getListForPage(params,-1,-1,null);
		
		return ResultManager.createDataResult(list, list.size());
	}
	
	/**
	 * 查询用户列表 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message = "姓名", type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, Integer pageNum, Integer pageSize, String sorts) {
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("name", name);
		inputParams.put("role", RoleId.user);
		
		Integer count = this.tSsOperatorService.getCount(inputParams);
		List<TSsOperator> list = this.tSsOperatorService.getListForPage(inputParams, pageNum, pageSize, sorts);
		
		return ResultManager.createDataResult(list, count);
	}
	
	/**
	 * 获取用户数据
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result get(String id) {
		TSsOperator op = this.tSsOperatorService.get(id);
		if(op == null){
			return ResultManager.createFailResult("用户不存在!");
		}
		if(RoleId.superAdmin.equals(op.getRole())){
			return ResultManager.createFailResult("无法获取超级管理员详细信息!");
		}
		return ResultManager.createDataResult(op);
	}
	
	/**
	 * 重置密码
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result reset(String id) {
		TSsOperator operator = tSsOperatorService.get(id);
		if(operator != null && RoleId.user.equals(operator.getRole())){
			String encryptPassword = SecurityRealm.encrypt("666666", ByteSource.Util.bytes(operator.getId()));
			operator.setPassword(encryptPassword);
			
			operator = tSsOperatorService.update(operator);
			return ResultManager.createDataResult(operator,"重置密码成功");
		}else{
			return ResultManager.createFailResult("用户不存在!");
		}
	}
}
