package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Module;
import com.cmos.china.mobile.media.core.bean.Province;
import com.cmos.china.mobile.media.core.bean.ProvinceModule;
import com.cmos.china.mobile.media.core.bean.Template;
import com.cmos.china.mobile.media.core.bean.Entity.GerneralStatusType;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.service.IProvinceModuleService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.ProvinceModuleVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class ProvinceModuleServiceImpl extends BaseServiceImpl implements IProvinceModuleService {
 
	/**
	 * 省份模块列表
	 */
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		Map<String,Object> templateData = new HashMap<>();
		List<Map<String,Object>> moduleDatas = new ArrayList<>();
		String id = inputObject.getValue("province");
		
		Province province = this.getBaseDao().queryForObject("province_get", id, Province.class);
		if(province==null || "".equals(province.getId())){
			throw new ExceptionUtil("接入省份信息不存在");
		}
		Template template = this.getBaseDao().queryForObject("template_get", province.getTemplate(), Template.class);
		if(template==null || "".equals(template.getId())){
		throw new ExceptionUtil("接入省份未选择模板");
		}
				Map<String,String> moduleParamsMap = new HashMap<>();
				moduleParamsMap.put("template", template.getId());
				List<Module> moduleList = this.getBaseDao().queryForList("module_getListByParams", moduleParamsMap, Module.class);
				if(moduleList.isEmpty()){
					throw new ExceptionUtil("接入省份模板配置错误");
				}
					templateData.put("id", template.getId());
					templateData.put("name", template.getName());
					templateData.put("module", moduleDatas);
					for(Module module: moduleList){
						Map<String,Object> moduleMap = new HashMap<>();
						moduleMap.put("id", module.getId());
						moduleMap.put("name", module.getName());
						moduleMap.put("count", module.getCount());
						moduleMap.put("sequence", module.getSequence());
						moduleMap.put("province", province.getId());
						Map<String,String> paramsMap = new HashMap<>();
						paramsMap.put("province", id);
						paramsMap.put("module", module.getId());
						paramsMap.put("sort", "priority");
						List<ProvinceModuleVO> pmList = this.getBaseDao().queryForList("provinceModule_getListByParams", paramsMap, ProvinceModuleVO.class);
						List<Map<String,Object>> moduleDataList = new ArrayList<>();
						for(ProvinceModuleVO pm: pmList){		
							Map<String,Object> data = new HashMap<>();
							data.put("id", pm.getId());
							data.put("title", pm.getTitle());
							data.put("content", pm.getContent());
							data.put("url", pm.getUrl());
							data.put("image", pm.getImage());
							data.put("imageUrl", pm.getImageUrl());
							data.put("priority", pm.getPriority());
							data.put("status", "normal".equals(pm.getStatus())?"正常":"停用");
							moduleDataList.add(data);
						}
						moduleMap.put("datas", moduleDataList);
						moduleDatas.add(moduleMap);
					}
					outputObject.setBean(templateData);
	}

	/**
	 * 加载省份模块详细信息
	 */
	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		ProvinceModule provinceModule = this.getBaseDao().queryForObject("provinceModule_get", id, ProvinceModule.class);
		if(provinceModule==null || "".equals(provinceModule)){
			throw new ExceptionUtil("栏目不存在");
		}else{
			outputObject.convertBean2Map(provinceModule);
		}
	}

	/**
	 * 加载省份模块详细信息VO
	 */
	@Override
	public void loadVO(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("id", id);
		List<ProvinceModuleVO> list = this.getBaseDao().queryForList("provinceModule_getListByParams", paramMap, ProvinceModuleVO.class);
		
		if(!list.isEmpty()){
			outputObject.convertBean2Map(list.get(0));
		}else{
			throw new ExceptionUtil("内容不存在");
		}
	}
	
	/**
	 * 添加省份模块
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String province = inputObject.getValue("province");
		String module = inputObject.getValue("module");
		String title = inputObject.getValue("title");
		String content = inputObject.getValue("content");
		String url = inputObject.getValue("url");
		String image = inputObject.getValue("image");
		Integer priority = Utlity.getIntValue(inputObject.getValue("priority"), 100);
		String status = inputObject.getValue("status");
		String creator = inputObject.getValue("creator");
		
		if(province==null||"".equals(province)){
			throw new ExceptionUtil("省份不能为空");
		}
		if(module==null||"".equals(module)){
			throw new ExceptionUtil("模板不能为空");
		}
		if(title==null||"".equals(title)){
			throw new ExceptionUtil("标题不能为空");
		}
		if(url==null||"".equals(url)){
			throw new ExceptionUtil("URL不能为空");
		}
		if(image==null||"".equals(image)){
			throw new ExceptionUtil("图标不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new ExceptionUtil("状态值不正确");
		}
		
		ProvinceModule provinceModule = new ProvinceModule();
		String id = UUID.randomUUID().toString();
		provinceModule.setId(id);
		Province prov = this.getBaseDao().queryForObject("province_get", province, Province.class);
		if(prov != null && !"".equals(prov)){
			provinceModule.setProvince(province);
		}else{
			throw new ExceptionUtil("模板不存在");
		}
		Module mod = this.getBaseDao().queryForObject("module_get", module, Module.class);
		if(mod != null && !"".equals(mod)){
			provinceModule.setModule(module);
		}else{
			throw new ExceptionUtil("模板不存在");
		}
		Resource res = this.getBaseDao().queryForObject("resource_get", image, Resource.class);
		if(res != null && !"".equals(res)){
			provinceModule.setImage(image);
		}else{
			throw new ExceptionUtil("图标不存在");
		}
		provinceModule.setTitle(title);
		provinceModule.setUrl(url);
		provinceModule.setContent(content);
		provinceModule.setPriority(priority);
		provinceModule.setStatus(status);
		provinceModule.setCreator(creator);
		provinceModule.setCreatetime(new Timestamp((new Date()).getTime()));
		this.getBaseDao().insert("provinceModule_add", provinceModule);
	}

	/**
	 * 编辑省份模块
	 */
	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String module = inputObject.getValue("module");
		String title = inputObject.getValue("title");
		String content = inputObject.getValue("content");
		String url = inputObject.getValue("url");
		String image = inputObject.getValue("image");
		Integer priority = Utlity.getIntValue(inputObject.getValue("priority"), 100);
		String status = inputObject.getValue("status");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		if(module==null||"".equals(module)){
			throw new ExceptionUtil("模板不能为空");
		}
		if(title==null||"".equals(title)){
			throw new ExceptionUtil("标题不能为空");
		}
		if(url==null||"".equals(url)){
			throw new ExceptionUtil("URL不能为空");
		}
		if(image==null||"".equals(image)){
			throw new ExceptionUtil("图标不能为空");
		}
		if(!GerneralStatusType.NORMAL.equals(status) && !GerneralStatusType.STOPPED.equals(status)){
			throw new ExceptionUtil("状态值不正确");
		}
		
		ProvinceModule provinceModule = this.getBaseDao().queryForObject("provinceModule_get", id, ProvinceModule.class);
		if(provinceModule == null || "".equals(provinceModule)){
			throw new ExceptionUtil("内容不存在");
		}else{
			Module mod = this.getBaseDao().queryForObject("module_get", module, Module.class);
			if(mod != null && !"".equals(mod)){
				provinceModule.setModule(module);
			}else{
				throw new ExceptionUtil("模板不存在");
			}
			Resource res = this.getBaseDao().queryForObject("resource_get", image, Resource.class);
			if(res != null && !"".equals(res)){
				provinceModule.setImage(image);
			}else{
				throw new ExceptionUtil("图标不存在");
			}
			provinceModule.setTitle(title);
			provinceModule.setUrl(url);
			provinceModule.setContent(content);
			provinceModule.setPriority(priority);
			provinceModule.setStatus(status);
			this.getBaseDao().update("provinceModule_update", provinceModule);
		}
	}

	/**
	 * 删除省份模块
	 */
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		ProvinceModule provinceModule = this.getBaseDao().queryForObject("provinceModule_get", id, ProvinceModule.class);
		if(provinceModule==null && !"".equals(provinceModule)){
			throw new ExceptionUtil("省份模块不存在");
		}else{
			this.getBaseDao().update("provinceModule_delete", provinceModule);
		}
	}

	@Override
	public void searchOtherModules(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		ProvinceModule provinceModule = this.getBaseDao().queryForObject("provinceModule_get", id, ProvinceModule.class);
		Province province = this.getBaseDao().queryForObject("province_get", provinceModule.getProvince(), Province.class);
		Template template = this.getBaseDao().queryForObject("template_get", province.getTemplate(), Template.class);
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("template", template.getId());
		List<Module> moduleList = this.getBaseDao().queryForList("module_getListByParams", paramsMap, Module.class);
		
		outputObject.convertBeans2List(moduleList);
	}
	
}
