/** 
 * Project Name:ItemDatabase 
 * File Name:NavigationAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import cn.zeppin.access.Navigation;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Funcation;
import cn.zeppin.entity.RoleFuncation;
import cn.zeppin.entity.SysUser;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IRoleFunctionService;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: NavigationAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月24日 下午1:21:36 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class NavigationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4968981079027964560L;

	private IRoleFunctionService roleFunctionService;

	public IRoleFunctionService getRoleFunctionService() {
		return roleFunctionService;
	}

	public void setRoleFunctionService(IRoleFunctionService roleFunctionService) {
		this.roleFunctionService = roleFunctionService;
	}

	/**
	 * 获取道航列表
	 * 
	 * @author Administrator
	 * @date: 2014年7月24日 下午3:35:00 <br/>
	 */
	@AuthorityParas(userGroupName = "ALL")
	public void LoadRoleFunction() {

		// =====================================
		// 1.先根据当前用户登陆信息获取roleid
		// 2.根据roleId获取功能列表
		// 3.分析功能
		// =====================================

		String dataType = request.getParameter("datatype");
		String name = request.getParameter("name");

		SysUser currentUser = (SysUser) session.getAttribute("usersession");
		ActionResult result = new ActionResult();

		int roleId = currentUser.getRole().getId();
		List<RoleFuncation> liT = this.getRoleFunctionService().getRoleFunctionByRoleid(roleId);

		LinkedHashMap<Funcation, List<Funcation>> hashMap = new LinkedHashMap<>();

		for (RoleFuncation rf : liT) {

			Funcation currentFuncation = rf.getFuncation();
			Funcation parentFuncation = currentFuncation.getFuncation();

			if (parentFuncation != null) {

				if (hashMap.containsKey(parentFuncation)) {

					List<Funcation> list = hashMap.get(parentFuncation);
					list.add(currentFuncation);

				} else {

					List<Funcation> list = new ArrayList<Funcation>();
					list.add(currentFuncation);

					hashMap.put(parentFuncation, list);

				}

			}
		}

		Iterator<Entry<Funcation, List<Funcation>>> iterator = hashMap.entrySet().iterator();

		List<Navigation> liN = new ArrayList<Navigation>();

		while (iterator.hasNext()) {

			Entry<Funcation, List<Funcation>> entry = iterator.next();
			Funcation key = entry.getKey();

			Navigation nv = SerializeEntity.funcation2Navigation(key);
			liN.add(nv);
			nv.getData().clear();

			List<Funcation> tmlist = hashMap.get(key);
			for (Funcation f : tmlist) {
				Navigation tmnv = SerializeEntity.funcation2Navigation(f);
				if (name != null && tmnv.getPath().indexOf(name) > 0) {
					tmnv.setIscurrent(true);
				}
				nv.getData().add(tmnv);
			}
		}

		result.init(SUCCESS_STATUS, "加载成功", liN);

		Utlity.ResponseWrite(result, dataType, response);

	}

}
