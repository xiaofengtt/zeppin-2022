package cn.zeppin.service.impl;

import java.util.List;
import cn.zeppin.dao.ISysUserRightDao;
import cn.zeppin.entity.SysUserRight;
import cn.zeppin.service.ISysUserRightService;

public class SysUserRightServiceImpl extends BaseServiceImpl<SysUserRight, Integer> implements ISysUserRightService {
	
	private ISysUserRightDao sysUserRightDao;

	public ISysUserRightDao getSysUserRightDao() {
		return sysUserRightDao;
	}

	public void setSysUserRightDao(ISysUserRightDao sysUserRightDao) {
		this.sysUserRightDao = sysUserRightDao;
	}

	@Override
	public SysUserRight add(SysUserRight t) {
		// TODO Auto-generated method stub
		return sysUserRightDao.add(t);
	}

	@Override
	public SysUserRight update(SysUserRight t) {
		// TODO Auto-generated method stub
		return sysUserRightDao.update(t);
	}

	@Override
	public void delete(SysUserRight t) {
		// TODO Auto-generated method stub
		sysUserRightDao.delete(t);
	}

	@Override
	public SysUserRight load(Integer id) {
		// TODO Auto-generated method stub
		return sysUserRightDao.load(id);
	}

	@Override
	public SysUserRight get(Integer id) {
		// TODO Auto-generated method stub
		return sysUserRightDao.get(id);
	}

	@Override
	public List<SysUserRight> loadAll() {
		// TODO Auto-generated method stub
		return sysUserRightDao.loadAll();
	}

	@Override
	public List<SysUserRight> findAll() {
		// TODO Auto-generated method stub
		return sysUserRightDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return sysUserRightDao.findByHSQL(querySql);
	}

	@Override
	public List<SysUserRight> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return sysUserRightDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		sysUserRightDao.executeHSQL(hql);
	}

	@Override
	public List<SysUserRight> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return sysUserRightDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return sysUserRightDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		sysUserRightDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return sysUserRightDao.getListPage(sql, offset, length, objParas);
	}

	

}
