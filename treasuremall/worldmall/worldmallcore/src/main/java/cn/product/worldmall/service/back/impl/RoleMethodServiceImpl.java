package cn.product.worldmall.service.back.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.MethodDao;
import cn.product.worldmall.dao.RoleDao;
import cn.product.worldmall.dao.RoleMethodDao;
import cn.product.worldmall.entity.Method;
import cn.product.worldmall.entity.Role;
import cn.product.worldmall.entity.RoleMethod;
import cn.product.worldmall.service.back.RoleMethodService;
import cn.product.worldmall.vo.back.MethodVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("roleMethodService")
public class RoleMethodServiceImpl implements RoleMethodService {
	
	@Autowired
    private RoleMethodDao roleMethodDao;

	@Autowired
	private MethodDao methodDao;
	
	@Autowired
	private RoleDao roleDao;
	@Override
	public void all(InputParams params, DataResult<Object> result) {
		//查询符合条件的功能列表
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("sort", "level asc");
		List<Method> controllerlist = methodDao.getListByParams(search);
		List<MethodVO> dataList = new ArrayList<>();
		//将功能转成VO插入结果列表
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
    	String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
    	
		Role operatorRole = roleDao.get(role);
		
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("role", role);
			//查询符合条件的角色功能信息列表
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

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
    	String[] method = (String[]) paramsMap.get("method");
    	
		//获取修改的角色
		Role operatorRole = roleDao.get(role);
		
		//判断角色是否存在
		if(operatorRole!=null && role.equals(operatorRole.getUuid())){
			List<Method> methodList = new ArrayList<>();
			
			//判断是否为空权限
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
    	String role = paramsMap.get("role") == null ? "" : paramsMap.get("role").toString();
    	
    	List<Method> list = this.roleMethodDao.getMethodListByRole(role);
    	
    	result.setData(list);
		result.setTotalResultCount(list.size());
		result.setStatus(ResultStatusType.SUCCESS);
		
	}
	
	//多级分类添加
	static Boolean addMethod(List<MethodVO> voList, MethodVO vo){
		Boolean flag = false;
		for(MethodVO parent : voList){
			if(vo.getUrl().startsWith(parent.getUrl())){
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
