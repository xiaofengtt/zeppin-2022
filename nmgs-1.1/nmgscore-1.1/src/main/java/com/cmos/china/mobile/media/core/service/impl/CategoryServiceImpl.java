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
import com.cmos.china.mobile.media.core.bean.Province;
import com.cmos.china.mobile.media.core.service.ICategoryService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.CategoryVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class CategoryServiceImpl extends BaseServiceImpl implements ICategoryService {
 
	/**
	 * 分类列表
	 */
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		String province = inputObject.getValue("province");
		String component = inputObject.getValue("component");
		String level = inputObject.getValue("level");
		String parent = inputObject.getValue("parent");
		String status = inputObject.getValue("status");
		String scode = inputObject.getValue("scode");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String sort = inputObject.getValue("sort");

		if(province==null){
			throw new ExceptionUtil("地区不能为空");
		}
		
		if(!Utlity.checkOrderBy(sort)){
			throw new ExceptionUtil("参数异常");
		}
		
		Integer start = (pagenum - 1) * pagesize;
		
		if(sort == null || "".equals(sort)){
			sort = "createtime";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("name", name);
		paramMap.put("province", province);
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
		
		Map<String, Object> resultMap = new HashMap<>();
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
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Category category = this.getBaseDao().queryForObject("category_get", id, Category.class);
		if(category==null){
			throw new ExceptionUtil("栏目不存在");
		}else{
			outputObject.convertBean2Map(category);
		}
	}

	/**
	 * 添加分类
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String name = inputObject.getValue("name");
		String parent = inputObject.getValue("parent");
		String province = inputObject.getValue("province");
		String component = inputObject.getValue("component");
		String creator = inputObject.getValue("creator");
		String status = inputObject.getValue("status");

		if(name==null || "".equals(name)){
			throw new ExceptionUtil("名称不能为空");
		}
		if(province==null || "".equals(province)){
			throw new ExceptionUtil("地区不能为空");
		}
		if(component==null || "".equals(province)){
			throw new ExceptionUtil("组件不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new ExceptionUtil("状态值不正确");
		}
		
		if(parent != null && !"".equals(parent)){
			if(status.equals(GerneralStatusType.NORMAL)){
			Map<String, String> param = new HashMap<>();
			param.put("id", parent);
			param.put("status", "stopped");
			Integer count = this.getBaseDao().getTotalCount("category_getCountByParams", param);
			if(count > 0){
				throw new ExceptionUtil("父级栏目停用，子级不允许启用");
				}
			}
		}

		Category category = new Category();
		String id = UUID.randomUUID().toString();
		category.setId(id);
		
		Province prov = this.getBaseDao().queryForObject("province_get", province, Province.class);
		if(prov!=null && !"".equals(prov)){
			category.setProvince(province);
		}else{
			throw new ExceptionUtil("地区不存在");
		}
		
		Component comp = this.getBaseDao().queryForObject("component_get", component, Component.class);
		if(comp!=null && !"".equals(comp)){
			category.setComponent(component);
		}else{
			throw new ExceptionUtil("组件不存在");
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
				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("parent", parent);
				int count = this.getBaseDao().getTotalCount("category_getChildCount", paramMap);
				count++;
				scode = parentCategory.getScode() + df.format(count);
			}
		}
		category.setLevel(level);
		
		if(level == 1){
			Map<String, String> paramMap = new HashMap<>();
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
	public void edit(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		String status = inputObject.getValue("status");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		if(name==null||"".equals(name)){
			throw new ExceptionUtil("名称不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new ExceptionUtil("状态值不正确");
		}
		Category category = this.getBaseDao().queryForObject("category_get", id, Category.class);
		if(category == null || "".equals(category)){
			throw new ExceptionUtil("栏目不存在");
		}
		
		if(GerneralStatusType.STOPPED.equals(status)){
			if(category.getParent() != null && !"".equals(category.getParent())){
				category.setName(name);
				category.setStatus(status);
				this.getBaseDao().update("category_update", category);
			}else{
				Map<String, String> param = new HashMap<>();
				param.put("level", "2");
				param.put("status", "normal");
				param.put("parent", id);
				Integer count = this.getBaseDao().getTotalCount("category_getCountByParams", param);
				if(count > 0){
				throw new ExceptionUtil("请先停用其子栏目");
				}
				category.setName(name);
				category.setStatus(status);
				this.getBaseDao().update("category_update", category);
			}
		}else{
			if(category.getParent() == null || "".equals(category.getParent())){
				category.setName(name);
				category.setStatus(status);
				this.getBaseDao().update("category_update", category);
			}else{
				Map<String, String> param = new HashMap<>();
				param.put("id", category.getParent());
				param.put("status", "stopped");
				Integer count = this.getBaseDao().getTotalCount("category_getCountByParams", param);
				if(count > 0){
				throw new ExceptionUtil("请先启用一级栏目");
				}
				category.setName(name);
				category.setStatus(status);
				this.getBaseDao().update("category_update", category);
			}
			
		}
	}

	/**
	 * 删除分类
	 */
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Category category = this.getBaseDao().queryForObject("category_get", id, Category.class);
		if(category==null){
			throw new ExceptionUtil("栏目不存在");
		}else{
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("parent", id);
			Integer count = this.getBaseDao().getTotalCount("category_getCountByParams", paramMap);
			if(count==0){
				Map<String, String> param = new HashMap<>();
				param.put("category", category.getId());
				param.put("statusNot", "deleted");
				Integer countVideo = this.getBaseDao().getTotalCount("videoPublish_getCountByParams", param);
				if(!(countVideo>0)){
				this.getBaseDao().update("category_delete", category.getScode());
				}else{
					throw new ExceptionUtil("请先删除该栏目所有视频");
				}	
			}else{
				throw new ExceptionUtil("请先删除所有子栏目");
			}
		}
	}

	/**
	 * 加载父级列表
	 */
	@Override
	public void loadNav(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String parent = inputObject.getValue("parent");
		if(parent==null || "".equals(parent)){
			throw new ExceptionUtil("父ID不能为空");
		}else{
			Category category = this.getBaseDao().queryForObject("category_get", parent, Category.class);
			if(category==null){
				throw new ExceptionUtil("父栏目不存在");
			}else{
				LinkedList<Category> categorylist = new LinkedList<>();
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
