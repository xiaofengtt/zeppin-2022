package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Component;
import com.cmos.china.mobile.media.core.bean.Entity.GerneralStatusType;
import com.cmos.china.mobile.media.core.service.IComponentService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.ComponentVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class ComponentServiceImpl extends BaseServiceImpl implements IComponentService {

	/**
	 * 分类列表
	 */
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		String status = inputObject.getValue("status");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String sort = inputObject.getValue("sort");
		if(!Utlity.checkOrderBy(sort)){
			throw new Exception("参数异常");
		}
		
		Integer start = (pagenum - 1) * pagesize;
		
		if(sort == null || "".equals(sort)){
			sort = "createtime";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("name", name);
		paramMap.put("status", status);
		paramMap.put("start", start+"");
		paramMap.put("limit", pagesize+"");
		paramMap.put("sort", sort);
		
		Integer count = this.getBaseDao().getTotalCount("component_getCountByParams", paramMap);
		Integer pageCount = (int) Math.ceil((double) count / pagesize);
		List<ComponentVO> list = this.getBaseDao().queryForList("component_getListByParams", paramMap, ComponentVO.class);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		resultMap.put("totalResultCount", count);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	/**
	 * 加载分类详细信息
	 */
	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		Component component = this.getBaseDao().queryForObject("component_get", id, Component.class);
		if(component==null){
			throw new Exception("组件不存在");
		}else{
			outputObject.convertBean2Map(component);
		}
	}

	/**
	 * 添加分类
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws Exception {
		String name = inputObject.getValue("name");
		String creator = inputObject.getValue("creator");
		String status = inputObject.getValue("status");
		
		if(name==null||name.equals("")){
			throw new Exception("名称不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new Exception("状态值不正确");
		}
		
		Component component = new Component();
		String id = UUID.randomUUID().toString();
		component.setId(id);
		component.setName(name);
		component.setStatus(status);
		
		component.setCreatetime(new Timestamp((new Date()).getTime()));
		component.setCreator(creator);
		this.getBaseDao().insert("component_add", component);
	}

	/**
	 * 编辑分类
	 */
	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		String status = inputObject.getValue("status");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		if(name==null||name.equals("")){
			throw new Exception("名称不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new Exception("状态值不正确");
		}
		
		Component component = this.getBaseDao().queryForObject("component_get", id, Component.class);
		if(component == null){
			throw new Exception("组件不存在");
		}else{
			component.setName(name);
			component.setStatus(status);
			this.getBaseDao().update("component_update", component);
		}
	}

	/**
	 * 删除分类
	 */
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		Component component = this.getBaseDao().queryForObject("component_get", id, Component.class);
		if(component==null){
			throw new Exception("组件不存在");
		}else{
			this.getBaseDao().update("component_delete", component);
		}
	}
}
