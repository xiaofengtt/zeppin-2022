package cn.product.payment.service.system.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.payment.controller.base.BaseResult.ResultStatusType;
import cn.product.payment.controller.base.DataResult;
import cn.product.payment.controller.base.InputParams;
import cn.product.payment.dao.AdminDao;
import cn.product.payment.dao.UserRechargeDao;
import cn.product.payment.dao.UserWithdrawDao;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Admin.AdminStatus;
import cn.product.payment.entity.UserRecharge;
import cn.product.payment.entity.UserRecharge.UserRechargeStatus;
import cn.product.payment.entity.UserWithdraw;
import cn.product.payment.service.system.SystemAdminService;
import cn.product.payment.util.PasswordHelper;

/**
 * 品台管理员
 */
@Service("systemAdminService")
public class SystemAdminServiceImpl implements SystemAdminService{
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private UserRechargeDao userRechargeDao;
	
	@Autowired
	private UserWithdrawDao userWithdrawDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid").toString();
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
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
	
	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		
		if(this.adminDao.getByUsername(admin.getUsername()) != null) {
			//用户名重复校验
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("管理员已存在");
			return;
		}
		
		admin.setUuid(UUID.randomUUID().toString());
    	admin.setCreatetime(new Timestamp(System.currentTimeMillis()));
    	//生成密码
    	PasswordHelper.encryptPassword(admin);
    	adminDao.insert(admin);
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}
    
	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		
		Admin a = adminDao.get(admin.getUuid());
		if(a == null || AdminStatus.DELETE.equals(a.getStatus())){
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
	
	/**
	 * 修改密码
	 */
	@Override
	public void password(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		String password = paramsMap.get("password").toString();
		String newPassword = paramsMap.get("newPassword").toString();
		
		Admin a = adminDao.get(admin.getUuid());
		if(a == null || AdminStatus.DELETE.equals(a.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("管理员不存在");
			return;
		}
		if(!a.getPassword().equals(PasswordHelper.getEncryptPassword(admin, password))){
			//校验原密码
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("原密码错误！");
			return;
		}
		
		a.setPassword(newPassword);
		//生成信息吗
		PasswordHelper.encryptPassword(a);
		adminDao.update(a);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
	/**
	 *	通过用户名获取
	 */
	@Override
	public void getByUsername(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String username = paramsMap.get("username") == null ? "" : paramsMap.get("username").toString();
    	Admin admin = this.adminDao.getByUsername(username);
    	
    	result.setData(admin);
    	result.setStatus(ResultStatusType.SUCCESS);
	}
	
	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		Admin a = adminDao.get(uuid);
		if(a == null || AdminStatus.DELETE.equals(a.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("管理员不存在");
			return;
		}
		//处理用户名 防止后续同名校验错误
		a.setUsername(a.getUsername() + "_#"+a.getUuid());
		a.setStatus(AdminStatus.DELETE);
		adminDao.update(a);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");	
	}

	@Override
	public void getNotice(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Admin admin = (Admin) paramsMap.get("admin");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(!"9f82edd6-98a1-4e0e-ad64-059346525d82".equals(admin.getRole()) && !"e71fd95e-adcd-4092-b230-21a457703a1d".equals(admin.getRole())){
			//如果不是管理员 或 财务账户
			result.setData(resultMap);
			result.setStatus(ResultStatusType.SUCCESS);
			return;
		}
		
		Map<String ,Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", UserRechargeStatus.CHECKING);
		searchMap.put("offSet", 1);
		searchMap.put("pageSize", 1);
		//待处理充值
		List<UserRecharge> rechargeList = this.userRechargeDao.getListByParams(searchMap);
		//待处理提现
		List<UserWithdraw> withdrawList = this.userWithdrawDao.getListByParams(searchMap);
		
		if(rechargeList.size() > 0){
			resultMap.put("recharge", rechargeList.get(0).getUuid());
		}
		if(withdrawList.size() > 0){
			resultMap.put("withdraw", withdrawList.get(0).getUuid());
		}
		result.setData(resultMap);
		result.setStatus(ResultStatusType.SUCCESS);
	}
}
