package com.cmos.china.mobile.media.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Province;
import com.cmos.china.mobile.media.core.bean.Entity.GerneralStatusType;
import com.cmos.china.mobile.media.core.bean.Template;
import com.cmos.china.mobile.media.core.service.IProvinceService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.ProvinceVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class ProvinceServiceImpl extends BaseServiceImpl implements IProvinceService {
 
	/**
	 * 接入省份列表
	 */
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String template = inputObject.getValue("template");
		String status = inputObject.getValue("status");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String sort = inputObject.getValue("sort");
		if(!Utlity.checkOrderBy(sort)){
			throw new ExceptionUtil("参数异常");
		}
		
		Integer start = (pagenum - 1) * pagesize;
		
		if(sort == null || "".equals(sort)){
			sort = "id";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("template", template);
		paramMap.put("status", status);
		paramMap.put("start", start+"");
		paramMap.put("limit", pagesize+"");
		paramMap.put("sort", sort);
		
		Integer count = this.getBaseDao().getTotalCount("province_getCountByParams", paramMap);
		Integer pageCount = (int) Math.ceil((double) count / pagesize);
		List<ProvinceVO> list = this.getBaseDao().queryForList("province_getListByParams", paramMap, ProvinceVO.class);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		resultMap.put("totalResultCount", count);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	/**
	 * 加载接入省份详细信息
	 */
	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Province province = this.getBaseDao().queryForObject("province_get", id, Province.class);
		if(province==null){
			throw new ExceptionUtil("接入省份不存在");
		}else{
			outputObject.convertBean2Map(province);
		}
	}

	/**
	 * 添加接入省份
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String name = inputObject.getValue("name");
		String template = inputObject.getValue("template");
		String status = inputObject.getValue("status");
		
		if(name==null||"".equals(name)){
			throw new ExceptionUtil("名称不能为空");
		}
		if(template==null||"".equals(template)){
			throw new ExceptionUtil("模板不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new ExceptionUtil("状态值不正确");
		}
		
		Province province = new Province();
		String id = UUID.randomUUID().toString();
		province.setId(id);
		Template temp = this.getBaseDao().queryForObject("template_get", template, Template.class);
		if(temp != null){
			province.setTemplate(template);
		}else{
			throw new ExceptionUtil("模板不存在");
		}
		province.setName(name);
		province.setStatus(status);
		this.getBaseDao().insert("province_add", province);
	}

	/**
	 * 编辑接入省份
	 */
	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		String template = inputObject.getValue("template");
		String status = inputObject.getValue("status");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		if(name==null||"".equals(name)){
			throw new ExceptionUtil("名称不能为空");
		}
		if(template==null||"".equals(template)){
			throw new ExceptionUtil("模板不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new ExceptionUtil("状态值不正确");
		}
		
		Province province = this.getBaseDao().queryForObject("province_get", id, Province.class);
		if(province == null){
			throw new ExceptionUtil("接入省份不存在");
		}else{
			Template temp = this.getBaseDao().queryForObject("template_get", template, Template.class);
			if(temp != null){
				province.setTemplate(template);
			}else{
				throw new ExceptionUtil("模板不存在");
			}
			province.setName(name);
			province.setStatus(status);
			this.getBaseDao().update("province_update", province);
		}
	}

	/**
	 * 删除接入省份
	 */
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Province province = this.getBaseDao().queryForObject("province_get", id, Province.class);
		if(province==null){
			throw new ExceptionUtil("接入省份不存在");
		}else{
			this.getBaseDao().update("province_delete", province);
		}
	}

	@Override
	public void templateList(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		Map<String, String> paramMap = new HashMap<>();
		List<Template> templateList = this.getBaseDao().queryForList("template_getList", paramMap, Template.class);
		outputObject.convertBeans2List(templateList);
	}
}
