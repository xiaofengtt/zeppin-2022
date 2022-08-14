/**
 * 
 */
package cn.product.payment.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.MethodDao;
import cn.product.payment.dao.RoleDao;
import cn.product.payment.dao.RoleMethodDao;
import cn.product.payment.entity.Method;
import cn.product.payment.entity.Role;
import cn.product.payment.entity.RoleMethod;
import cn.product.payment.service.system.SystemRoleMethodService;
import cn.product.payment.vo.system.MethodVO;

/**
 * 平台管理员方法权限
 */
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
		
		//循环数据 构建树状结构
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
			//角色存在
			
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("role", role);
			
			List<RoleMethod> list = roleMethodDao.getListByParams(searchMap);
			result.setData(list);
			result.setTotalResultCount(list.size());
			result.setStatus(ResultStatusType.SUCCESS);
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("角色不存在");
			return;
		}
	}
	
	/**
	 * 修改角色方法权限
	 */
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? null : paramsMap.get("role").toString();
    	String[] method = (String[]) paramsMap.get("method");
    	
		Role operatorRole = roleDao.get(role);
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			//角色存在
			
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
			//入库
			roleMethodDao.updateAll(operatorRole, methodList);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("权限更改成功");
			return;
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("角色不存在");
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
			//循环父级
			if(vo.getParent().equals(parent.getUuid())){
				//找到父级 加入父级子列表
				flag = true;
				addMethod(parent.getChildren(), vo);
				break;
			}
		}
		if(!flag){
			//没找到父级 加入父级列表
			voList.add(vo);
		}
		return flag;
	}
}
