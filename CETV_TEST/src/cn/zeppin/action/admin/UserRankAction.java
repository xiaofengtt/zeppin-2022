/** 
 * Project Name:CETV_TEST 
 * File Name:SubjectAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.action.base.IStandardAction;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.UserRank;
import cn.zeppin.service.api.IUserRankService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: SubjectAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年7月20日 下午5:23:35 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserRankAction extends BaseAction implements IStandardAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1381225742757194727L;

	private IUserRankService userRankService;

	public IUserRankService getUserRankService() {
		return userRankService;
	}

	public void setUserRankService(IUserRankService userRankService) {
		this.userRankService = userRankService;
	}
	/**
	 * 添加用户等级
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午5:26:18 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "score", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {

		// ***********************************
		//
		// 1.验证参数的合法性
		// 2.按名称判断名称是否存在
		//
		// ***********************************

		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		String score = request.getParameter("score");
		float fscore =Float.valueOf(score);
		Integer iscore = (int)fscore;
		UserRank tmpUserRank = this.getUserRankService().getUserRankByName(name);
		if (tmpUserRank != null) {
			result.init(FAIL_STATUS, "已经存在 “" + name + "”！", null);
		} else {
			UserRank userRank=new UserRank();
			userRank.setName(name);
			userRank.setScore(iscore);
			this.getUserRankService().addUserRank(userRank);			
			result.init(SUCCESS_STATUS, null, null);
		}
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 编辑用户等级
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:08:49 <br/>
	 */
	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "score", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update() {

		// ***********************************
		//
		// 1.验证参数的合法性
		// 2.按名称判断名称是否存在
		//
		// ***********************************

		String dataType = request.getParameter("datatype");

		ActionResult result = new ActionResult();

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String score = request.getParameter("score");
		
		float fscore =Float.valueOf(score);
		Integer iscore = (int)fscore;
		
		int userRankId = Integer.valueOf(id);
		UserRank userRank = this.getUserRankService().getUserRankById(userRankId);

		if (userRank != null) {
			userRank.setName(name);
			userRank.setScore(iscore);
			this.getUserRankService().updateUserRank(userRank);
			result.init(SUCCESS_STATUS, "修改成功", null);
		} else {

			result.init(FAIL_STATUS, "当前用户等级不存在！", null);

		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * load
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:29:41 <br/>
	 */
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {

		// ***********************************
		//
		// 1.验证参数的合法性
		//
		// ***********************************

		String dataType = request.getParameter("datatype");

		String id = request.getParameter("id");
		ActionResult result = new ActionResult();
		String split = request.getParameter("split");
		split = split == null ? "." : split;

		UserRank userRank = this.getUserRankService().getUserRankById(Integer.valueOf(id));
		if (userRank != null) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			result.put("id", userRank.getId());
			result.put("name", userRank.getName());
			result.put("score", userRank.getScore());
			result.init(SUCCESS_STATUS, null, map);

		} else {
			result.init(FAIL_STATUS, "学科不存在!", null);
		}

		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除
	 * 
	 * @author Administrator
	 * @date: 2014年7月22日 下午2:29:47 <br/>
	 */

	@AuthorityParas(userGroupName = "MANAGER_ADD_EDIT")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {

		// ***********************************
		//
		// 1.验证参数的合法性
		//
		// ***********************************

		String dataType = request.getParameter("datatype");
		String id = request.getParameter("id");

		ActionResult result = new ActionResult();

		int userRankId = Integer.valueOf(id);
		UserRank userRank = this.getUserRankService().getUserRankById(userRankId);

		if (userRank != null) {
			this.getUserRankService().delete(userRank);
			result.init(SUCCESS_STATUS, "删除成功", null);
		} else {
			result.init(FAIL_STATUS, "当前用户等级不存在！", null);
		}

		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 获取用户等级 搜索(后台管理接口)
	 * 
	 * @author Administrator
	 * @date: 2014年7月20日 下午7:10:43 <br/>
	 */
	@ActionParam(key = "pageunm", type = ValueType.NUMBER, nullable = true, emptyable = true)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER, nullable = true, emptyable = true)
	public void List() {

		// ***********************************
		//
		// 1.验证参数的合法性
		//
		// ***********************************

		String dataType = request.getParameter("datatype");
		ActionResult result = new ActionResult();

		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;
		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		
		HashMap<String, Object> ht = new HashMap<>();
		ht.put("sorts", sorts);
		
		int records = this.getUserRankService().getUserRankCount();
		List<UserRank> liUR = this.getUserRankService().getUserRank(ht,offset, pagesize);

		List<Map<String, Object>> liM = new ArrayList<>();
		for (UserRank userRank : liUR) {
			Map<String, Object> URM = new LinkedHashMap<String, Object>();
			URM.put("id", userRank.getId());
			URM.put("name", userRank.getName());
			URM.put("score", userRank.getScore());
			liM.add(URM);
		}
		result.init(SUCCESS_STATUS, null, liM);
		result.setTotalCount(records);
		Utlity.ResponseWrite(result, dataType, response);
	}

	@Override
	public void Search() {
		// TODO Auto-generated method stub
		
	}

}
