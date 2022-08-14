/**
 * 
 */
package com.product.worldpay.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.worldpay.controller.base.BaseResult.ResultStatusType;
import com.product.worldpay.controller.base.DataResult;
import com.product.worldpay.controller.base.InputParams;
import com.product.worldpay.dao.MethodDao;
import com.product.worldpay.dao.RoleDao;
import com.product.worldpay.dao.RoleMethodDao;
import com.product.worldpay.entity.Method;
import com.product.worldpay.entity.Role;
import com.product.worldpay.entity.RoleMethod;
import com.product.worldpay.service.system.SystemRoleMethodService;
import com.product.worldpay.vo.system.MethodVO;

@Service("systemRoleMethodService")
public class SystemRoleMethodController implements SystemRoleMethodService {

	@Autowired
    private RoleMethodDao roleMethodDao;

	@Autowired
	private MethodDao methodDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public void all(InputParams params, DataResult<Object> result) {
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("sort", "level asc");
		List<Method> controllerlist = methodDao.getListByParams(search);
		List<MethodVO> dataList = new ArrayList<>();
		
		for(Method method:controllerlist){
			MethodVO controllerVO = new MethodVO(method);
			addMethod(dataList, controllerVO);
		}
		
		result.setData(dataList);
		result.setTotalResultCount(dataList.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? null : paramsMap.get("role").toString();
    	
		Role operatorRole = roleDao.get(role);
		
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("role", role);
			
			List<RoleMethod> list = roleMethodDao.getListByParams(searchMap);
			result.setData(list);
			result.setTotalResultCount(list.size());
			result.setStatus(ResultStatusType.SUCCESS);
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("role not exist !");
			return;
		}
	}
	
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? null : paramsMap.get("role").toString();
    	String[] method = (String[]) paramsMap.get("method");
    	
		Role operatorRole = roleDao.get(role);
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			List<Method> methodList = new ArrayList<>();
			
			String[] methods = new String[0];
			if(method != null){
				methods = method;
			}
			
			//循环角色权限
			for(String m : methods){
				Method rm = methodDao.get(m);
				methodList.add(rm);
			}
			roleMethodDao.updateAll(operatorRole, methodList);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("operate successed !");
			return;
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("role not exist !");
			return;
		}
	}
	
	@Override
	public void getMethodListByRole(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? null : paramsMap.get("role").toString();
    	
    	List<Method> list = this.roleMethodDao.getMethodListByRole(role);
    	
    	result.setData(list);
		result.setTotalResultCount(list.size());
		result.setStatus(ResultStatusType.SUCCESS);
	}
	
	//多级分类添加
	static Boolean addMethod(List<MethodVO> voList, MethodVO vo){
		Boolean flag = false;
		for(MethodVO parent : voList){
			if(vo.getParent().equals(parent.getUuid())){
				flag = true;
				addMethod(parent.getChildren(), vo);
				break;
			}
		}
		if(!flag){
			voList.add(vo);
		}
		return flag;
	}
}
