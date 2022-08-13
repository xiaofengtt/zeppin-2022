package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Category;
import com.cmos.china.mobile.media.core.bean.Component;
import com.cmos.china.mobile.media.core.bean.Entity.GerneralStatusType;
import com.cmos.china.mobile.media.core.service.ICategoryService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.CategoryVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class CategoryServiceImpl extends BaseServiceImpl implements ICategoryService {

	/**
	 * 分类列表
	 */
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		String component = inputObject.getValue("component");
		String level = inputObject.getValue("level");
		String parent = inputObject.getValue("parent");
		String status = inputObject.getValue("status");
		String scode = inputObject.getValue("scode");
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
		paramMap.put("component", component);
		paramMap.put("level", level);
		paramMap.put("parent", parent);
		paramMap.put("status", status);
		paramMap.put("scode", scode);
		paramMap.put("start", start+"");
		paramMap.put("limit", pagesize+"");
		paramMap.put("sort", sort);
		
		Integer count = this.getBaseDao().getTotalCount("category_getCountByParams", paramMap);
		Integer pageCount = (int) Math.ceil((double) count / pagesize);
		List<CategoryVO> list = this.getBaseDao().queryForList("category_getListByParams", paramMap, CategoryVO.class);
		
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
		
		Category category = this.getBaseDao().queryForObject("category_get", id, Category.class);
		if(category==null){
			throw new Exception("栏目不存在");
		}else{
			outputObject.convertBean2Map(category);
		}
	}

	/**
	 * 添加分类
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws Exception {
		String name = inputObject.getValue("name");
		String parent = inputObject.getValue("parent");
		String component = inputObject.getValue("component");
		String creator = inputObject.getValue("creator");
		String status = inputObject.getValue("status");
		
		if(name==null||name.equals("")){
			throw new Exception("名称不能为空");
		}
		if(component==null||component.equals("")){
			throw new Exception("组件不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new Exception("状态值不正确");
		}
		
		Category category = new Category();
		String id = UUID.randomUUID().toString();
		category.setId(id);
		
		Component comp = this.getBaseDao().queryForObject("component_get", component, Component.class);
		if(comp!=null){
			category.setComponent(component);
		}else{
			throw new Exception("组件不存在");
		}
		
		category.setName(name);
		category.setStatus(status);
		
		category.setCreatetime(new Timestamp((new Date()).getTime()));
		category.setCreator(creator);
		
		String format = "0000";
		DecimalFormat df = new DecimalFormat(format);
		String scode = "";
		Integer level = 1;
		if(parent != null && !"".equals(parent)){
			Category parentCategory = this.getBaseDao().queryForObject("category_get", parent, Category.class);
			if(parentCategory != null){
				category.setParent(parentCategory.getId());
				level = parentCategory.getLevel() + 1;
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("parent", parent);
				int count = this.getBaseDao().getTotalCount("category_getChildCount", paramMap);
				count++;
				scode = parentCategory.getScode() + df.format(count);
			}
		}
		category.setLevel(level);
		
		if(level == 1){
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("level", level+"");
			int count = this.getBaseDao().getTotalCount("category_getTotalCountByParams", paramMap);
			count++;
			scode = df.format(count);
		}
		category.setScode(scode);
		
		this.getBaseDao().insert("category_add", category);
	}

	/**
	 * 编辑分类
	 */
	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		String component = inputObject.getValue("component");
		String status = inputObject.getValue("status");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		if(name==null||name.equals("")){
			throw new Exception("名称不能为空");
		}
		if(component==null||component.equals("")){
			throw new Exception("组件不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new Exception("状态值不正确");
		}
		
		Category category = this.getBaseDao().queryForObject("category_get", id, Category.class);
		if(category == null){
			throw new Exception("栏目不存在");
		}else{
			Component comp = this.getBaseDao().queryForObject("component_get", component, Component.class);
			if(comp!=null){
				category.setComponent(component);
			}else{
				throw new Exception("组件不存在");
			}
			category.setName(name);
			category.setComponent(component);
			category.setStatus(status);
			this.getBaseDao().update("category_update", category);
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
		
		Category category = this.getBaseDao().queryForObject("category_get", id, Category.class);
		if(category==null){
			throw new Exception("栏目不存在");
		}else{
			this.getBaseDao().update("category_delete", category);
		}
	}

	/**
	 * 加载父级列表
	 */
	@Override
	public void loadNav(InputObject inputObject, OutputObject outputObject) throws Exception {
		String parent = inputObject.getValue("parent");
		if(parent==null || parent.equals("")){
			throw new Exception("父ID不能为空");
		}else{
			Category category = this.getBaseDao().queryForObject("category_get", parent, Category.class);
			if(category==null){
				throw new Exception("父栏目不存在");
			}else{
				LinkedList<Category> categorylist = new LinkedList<Category>();
				categorylist.add(category);
				while (category.getParent() != null && !"".equals(category.getParent())) {
					category = this.getBaseDao().queryForObject("category_get", category.getParent(), Category.class);
					categorylist.add(category);
				}
				List<Category> dataList = new ArrayList<>();
				int i=categorylist.size()-1;
				for(;i>=0;i--){
					Category cate = categorylist.get(i);
					dataList.add(cate);
				}
				outputObject.convertBeans2List(dataList);
			}
		}
	}
}
