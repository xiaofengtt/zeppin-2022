package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import cn.zeppin.dao.IBaseDao;
import cn.zeppin.dao.ISysUserDao;
import cn.zeppin.dao.impl.SysUserDao;
import cn.zeppin.entity.SysUser;
import cn.zeppin.service.ISysUserService;

@SuppressWarnings("rawtypes")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer> implements ISysUserService {

	private ISysUserDao sysUserDao;

	public ISysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(ISysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@Override
	public List findByAll(Object[] pars,Integer role) {
		// TODO Auto-generated method stub
		return this.sysUserDao.findByAll(pars,role);
	}

	@Override
	public List findByIdCard(Object[] pars) {
		// TODO Auto-generated method stub
		return this.sysUserDao.findByIdCard(pars);
	}

	@Override
	public List findByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.sysUserDao.findByParams(params);
	}

}
