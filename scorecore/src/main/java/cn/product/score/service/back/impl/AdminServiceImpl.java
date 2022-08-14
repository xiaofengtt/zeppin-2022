package cn.product.score.service.back.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.score.api.base.BaseResult.ResultStatusType;
import cn.product.score.api.base.DataResult;
import cn.product.score.api.base.InputParams;
import cn.product.score.dao.AdminDao;
import cn.product.score.entity.Admin;
import cn.product.score.entity.Admin.AdminStatus;
import cn.product.score.service.back.AdminService;
import cn.product.score.util.PasswordHelper;

/**
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminDao adminDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		Admin admin = adminDao.get(uuid);
		if(admin == null || AdminStatus.DELETE.equals(admin.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("管理员不存在");
			return;
		}
		result.setData(admin);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		Map<String, Object> paramslis = new HashMap<String, Object>();
		paramslis.put("username", admin.getUsername());
		paramslis.put("status", admin.getStatus());
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramslis.put("offSet", (pageNum-1)*pageSize);
			paramslis.put("pageSize", pageSize);
		}
		paramslis.put("sort", sort);
		
		Integer totalCount = adminDao.getCountByParams(paramslis);
		List<Admin> adminList = adminDao.getListByParams(paramslis);
		
		result.setData(adminList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		admin.setUuid(UUID.randomUUID().toString());
    	admin.setCreatetime(new Timestamp(System.currentTimeMillis()));
    	PasswordHelper.encryptPassword(admin);
    	adminDao.insert(admin);
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void update(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		
		Admin a = adminDao.get(admin.getUuid());
		if(admin == null || AdminStatus.DELETE.equals(admin.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("管理员不存在");
			return;
		}
		a.setRealname(admin.getRealname());
		a.setRole(admin.getRole());
		a.setStatus(admin.getStatus());
		adminDao.update(a);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void password(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		
		Admin a = adminDao.get(admin.getUuid());
		if(admin == null || AdminStatus.DELETE.equals(admin.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("管理员不存在");
			return;
		}
		a.setPassword(admin.getPassword());
		PasswordHelper.encryptPassword(a);
		adminDao.update(a);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void getByUsername(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String username = paramsMap.get("username") == null ? "" : paramsMap.get("username").toString();
    	Admin admin = this.adminDao.getByUsername(username);
    	
    	result.setData(admin);
    	result.setStatus(ResultStatusType.SUCCESS);
	}
	
}
