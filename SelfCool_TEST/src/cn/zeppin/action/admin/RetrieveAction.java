/** 
 * Project Name:Self_Cool  
 * File Name:ManagerAction.java 
 * Package Name:cn.zeppin.action.admin 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.entity.Retrieve;
import cn.zeppin.entity.RetrieveType;
import cn.zeppin.entity.base.SerializeEntity;
import cn.zeppin.service.api.IRetrieveService;
import cn.zeppin.service.api.IRetrieveTypeService;
import cn.zeppin.utility.Dictionary;
import cn.zeppin.utility.Utlity;

/**
 * ClassName: ManagerAction <br/>
 * date: 2014年7月20日 下午7:05:24 <br/>
 * 
 * @author Clark
 * @version
 * @since JDK 1.7
 */
public class RetrieveAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5718810690800239372L;

	private IRetrieveService retrieveService;
	private IRetrieveTypeService retrieveTypeService;

	public IRetrieveService getRetrieveService() {
		return retrieveService;
	}

	public void setRetrieveService(IRetrieveService retrieveService) {
		this.retrieveService = retrieveService;
	}

	public IRetrieveTypeService getRetrieveTypeService() {
		return retrieveTypeService;
	}

	public void setRetrieveTypeService(IRetrieveTypeService retrieveTypeService) {
		this.retrieveTypeService = retrieveTypeService;
	}

	/**
	 * 添加分类科目检索范围
	 * 
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "retrieveType.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Add() {
		ActionResult result = new ActionResult();

		Integer retrieveTypeID = Integer.valueOf(request.getParameter("retrieveType.id"));
		String name = request.getParameter("name");
		short status = Short.valueOf(this.request.getParameter("status"));

		Retrieve retrieve = new Retrieve();
		retrieve.setRetrieveType(this.retrieveTypeService.getRetrieveTypeById(retrieveTypeID));
		retrieve.setName(name);
		retrieve.setStatus(status);

		this.getRetrieveService().addRetrieve(retrieve);
		Map<String, Object> data = SerializeEntity.retrieve2Map(retrieve);
		result.init(SUCCESS_STATUS, "添加分类科目检索范围成功！", data);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除分类科目检索范围
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Delete() {
		
		ActionResult result = new ActionResult();
		Integer retrieveID = Integer.valueOf(request.getParameter("id"));
		Retrieve retrieve = this.getRetrieveService().getRetrieveById(retrieveID);
		
		if (retrieve != null) {
			
			this.getRetrieveService().deleteRetrieve(retrieve);
			
			Map<String, Object> data = SerializeEntity.retrieve2Map(retrieve);
			result.init(SUCCESS_STATUS, "删除分类科目检索范围成功！", data);
			
		} else {
			result.init(FAIL_STATUS, "无效的分类科目检索范围ID信息！", null);
		}
		
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载分类科目检索范围，一般用于修改的时候load
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Load() {
		ActionResult result = new ActionResult();
		Integer retrieveID = Integer.valueOf(request.getParameter("id"));
		Retrieve retrieve = this.getRetrieveService().getRetrieveById(retrieveID);
		if (retrieve != null) {
			Map<String, Object> data = SerializeEntity.retrieve2Map(retrieve);
			result.init(SUCCESS_STATUS, "加载分类科目检索范围信息成功！", data);
		} else {
			result.init(FAIL_STATUS, "无效的分类科目检索范围ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 修改分类科目检索范围
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午6:41:58 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "retrieveType.id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void Update() {
		ActionResult result = new ActionResult();
		Integer retrieveID = Integer.valueOf(request.getParameter("id"));
		Integer retrieveTypeID = Integer.valueOf(request.getParameter("retrieveType.id"));
		String name = request.getParameter("name");
		short status = Short.valueOf(this.request.getParameter("status"));

		Retrieve retrieve = this.getRetrieveService().getRetrieveById(retrieveID);
		if (retrieve != null) {
			
			retrieve.setRetrieveType(this.retrieveTypeService.getRetrieveTypeById(retrieveTypeID));
			retrieve.setName(name);
			retrieve.setStatus(status);
			
			this.getRetrieveService().updateRetrieve(retrieve);
			Map<String, Object> data = SerializeEntity.retrieve2Map(retrieve);
			result.init(SUCCESS_STATUS, "修改分类科目检索范围信息成功！", data);
			
		} else {
			result.init(FAIL_STATUS, "无效的分类科目检索范围ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 分类科目检索范围列表
	 */
//	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "retrieveType.id", type = ValueType.NUMBER)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void List() {

		ActionResult result = new ActionResult();

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("status", request.getParameter("status"));
		searchMap.put("retrieveType.id", request.getParameter("retrieveType.id"));

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.getRetrieveService().searchRetrieveCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Retrieve> retrieveList = getRetrieveService().searchRetrieves(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (retrieveList != null && retrieveList.size() > 0) {
			for (Retrieve retrieve : retrieveList) {
				Map<String, Object> data = SerializeEntity.retrieve2Map(retrieve);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 添加分类科目检索类别
	 * 
	 * @author Clark
	 * @date: 2014年6月24日 下午2:49:24 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void TypeAdd() {
		ActionResult result = new ActionResult();

		String name = request.getParameter("name");
		Short status = Short.valueOf(request.getParameter("status"));

		RetrieveType rtemp = this.getRetrieveTypeService().getRetrieveTypeByName(name);

		if (rtemp != null) {
			result.init(ERROR_STATUS, "已经存在“" + name + "”检索类别", null);
		} else {
			RetrieveType retrieveType = new RetrieveType();
			retrieveType.setName(name);
			retrieveType.setStatus(status);

			this.getRetrieveTypeService().addRetrieveType(retrieveType);
			Map<String, Object> data = SerializeEntity.retrieveType2Map(retrieveType);
			result.init(SUCCESS_STATUS, "添加分类科目检索类别成功！", data);

		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 删除分类科目检索类别
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 下午5:52:13 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void TypeDelete() {
		ActionResult result = new ActionResult();
		Integer retrieveTypeID = Integer.valueOf(request.getParameter("id"));
		RetrieveType retrieveType = this.getRetrieveTypeService().getRetrieveTypeById(retrieveTypeID);

		if (retrieveType != null) {
			this.getRetrieveTypeService().deleteRetrieveType(retrieveType);
			Map<String, Object> data = SerializeEntity.retrieveType2Map(retrieveType);
			result.init(SUCCESS_STATUS, "删除分类科目检索类别成功！", data);
		} else {
			result.init(FAIL_STATUS, "无效的分类科目检索类别ID信息！", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 加载分类科目检索类别，一般用于修改的时候load
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 上午11:36:48 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void TypeLoad() {

		ActionResult result = new ActionResult();
		Integer retrieveTypeID = Integer.valueOf(request.getParameter("id"));
		RetrieveType retrieveType = this.getRetrieveTypeService().getRetrieveTypeById(retrieveTypeID);

		if (retrieveType != null) {
			Map<String, Object> data = SerializeEntity.retrieveType2Map(retrieveType);
			result.init(SUCCESS_STATUS, "加载分类科目检索类别信息成功！", data);
		} else {
			result.init(FAIL_STATUS, "无效的分类科目检索类别ID信息！", null);
		}

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);

	}

	/**
	 * 修改分类科目检索类别
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午6:41:58 <br/>
	 */
	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER, nullable = false, emptyable = false)
	@ActionParam(key = "name", type = ValueType.STRING, nullable = false, emptyable = false)
	@ActionParam(key = "status", type = ValueType.NUMBER, nullable = false, emptyable = false)
	public void TypeUpdate() {
		ActionResult result = new ActionResult();
		Integer retrieveTypeID = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		Short status = Short.valueOf(request.getParameter("status"));

		RetrieveType retrieveType = this.getRetrieveTypeService().getRetrieveTypeById(retrieveTypeID);

		if (retrieveType != null) {

			if (!retrieveType.getName().equals(name)) {

				RetrieveType rtemp = this.getRetrieveTypeService().getRetrieveTypeByName(name);

				if (rtemp != null) {
					result.init(ERROR_STATUS, "已经存在“" + name + "”检索类别", null);
				} else {

					retrieveType.setName(name);
					retrieveType.setStatus(status);

					this.getRetrieveTypeService().updateRetrieveType(retrieveType);
					Map<String, Object> data = SerializeEntity.retrieveType2Map(retrieveType);
					result.init(SUCCESS_STATUS, "修改分类科目检索类别信息成功！", data);

				}
			} else {
				
				retrieveType.setName(name);
				retrieveType.setStatus(status);
				this.getRetrieveTypeService().updateRetrieveType(retrieveType);
				
				Map<String, Object> data = SerializeEntity.retrieveType2Map(retrieveType);
				result.init(SUCCESS_STATUS, "修改分类科目检索类别信息成功！", data);
			}

		} else {
			result.init(FAIL_STATUS, "无效的分类科目检索类别ID信息！", null);
		}
		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}

	/**
	 * 分类科目检索类别列表
	 */
//	@AuthorityParas(userGroupName = "SUPERADMIN_ONLY")
	@ActionParam(key = "id", type = ValueType.NUMBER)
	@ActionParam(key = "name", type = ValueType.STRING)
	@ActionParam(key = "status", type = ValueType.NUMBER)
	@ActionParam(key = "pagenum", type = ValueType.NUMBER)
	@ActionParam(key = "pagesize", type = ValueType.NUMBER)
	@ActionParam(key = "sorts", type = ValueType.STRING)
	public void TypeList() {

		ActionResult result = new ActionResult();

		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", request.getParameter("id"));
		searchMap.put("name", request.getParameter("name"));
		searchMap.put("status", request.getParameter("status"));

		String sorts = this.getStrValue(request.getParameter("sorts"), "").replaceAll("-", " ");
		int pagenum = this.getIntValue(request.getParameter("pagenum"), 1);
		int pagesize = this.getIntValue(request.getParameter("pagesize"), Dictionary.PAGESIZE_DEFAULT);
		int offset = (pagenum - 1) * pagesize;

		int recordCount = this.getRetrieveTypeService().searchRetrieveTypeCount(searchMap);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<RetrieveType> retrieveTypeList = getRetrieveTypeService().searchRetrieveTypes(searchMap, sorts, offset, pagesize);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (retrieveTypeList != null && retrieveTypeList.size() > 0) {
			for (RetrieveType retrieveType : retrieveTypeList) {
				Map<String, Object> data = SerializeEntity.retrieveType2Map(retrieveType);
				dataList.add(data);
			}
		}
		result.init(SUCCESS_STATUS, "搜索完成！", dataList);
		result.setPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalCount(recordCount);

		String dataType = request.getParameter("datatype");
		Utlity.ResponseWrite(result, dataType, response);
	}
}
