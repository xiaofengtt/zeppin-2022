/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITSsRoleService;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TSsRole;

/**
 * 角色管理
 */

@Controller
@RequestMapping(value = "/backadmin/role")
public class TSsRoleController extends BaseController {

	@Autowired
	private ITSsRoleService tSsRoleService;
	
	/**
	 * 查询所有角色信息 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list() {
		List<TSsRole> list = tSsRoleService.getList();
		return ResultManager.createDataResult(list, list.size());
	}
	
	/**
	 * 获取信息
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "角色ID", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String id) {
		TSsRole role = tSsRoleService.get(id);
		if(role == null){
			return ResultManager.createFailResult("数据不存在!");
		}
		return ResultManager.createDataResult(role);
	}
	
}
