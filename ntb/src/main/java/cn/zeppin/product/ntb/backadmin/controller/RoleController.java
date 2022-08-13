/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBkOperatorRoleService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.entity.BkOperatorRole;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * @author hehe
 *
 * 菜单管理
 */

@Controller
@RequestMapping(value = "/backadmin/role")
public class RoleController extends BaseController {

	@Autowired
	private IBkOperatorRoleService bkOperatorRoleService;
	
	/**
	 * 根据条件查询菜单信息 
	 * @param scode
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result all() {
		//查询全部列表
		List<Entity> list = bkOperatorRoleService.getList(BkOperatorRole.class);
		
		return ResultManager.createDataResult(list);
	}
	
	/**
	 * 获取一条角色信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//获取角色信息
		BkOperatorRole role = bkOperatorRoleService.get(uuid);
		if (role == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		return ResultManager.createDataResult(role);
	}
}
