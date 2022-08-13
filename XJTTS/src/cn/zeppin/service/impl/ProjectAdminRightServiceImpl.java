package cn.zeppin.service.impl;

import java.util.List;



import cn.zeppin.dao.IProjectAdminRightDao;
import cn.zeppin.entity.ProjectAdminRight;
import cn.zeppin.service.IProjectAdminRightService;

public class ProjectAdminRightServiceImpl extends BaseServiceImpl<ProjectAdminRight, Integer> implements IProjectAdminRightService {

	private IProjectAdminRightDao ProjectAdminRightDao;

	public IProjectAdminRightDao getProjectAdminRightDao() {
		return ProjectAdminRightDao;
	}

	public void setProjectAdminRightDao(IProjectAdminRightDao ProjectAdminRightDao) {
		this.ProjectAdminRightDao = ProjectAdminRightDao;
	}

	@Override
	public ProjectAdminRight add(ProjectAdminRight t) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.add(t);
	}

	@Override
	public ProjectAdminRight update(ProjectAdminRight t) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.update(t);
	}

	@Override
	public void delete(ProjectAdminRight t) {
		// TODO Auto-generated method stub
		ProjectAdminRightDao.delete(t);
	}

	@Override
	public ProjectAdminRight load(Integer id) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.load(id);
	}

	@Override
	public ProjectAdminRight get(Integer id) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.get(id);
	}

	@Override
	public List<ProjectAdminRight> loadAll() {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.loadAll();
	}

	@Override
	public List<ProjectAdminRight> findAll() {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectAdminRight> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		ProjectAdminRightDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectAdminRight> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		ProjectAdminRightDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return ProjectAdminRightDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public int deleteByProjectAdmin(int id) {
		// TODO Auto-generated method stub
		try {
			String hql = "delete ProjectAdminRight t where t.projectAdmin=" + id;
			this.executeHSQL(hql);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

}
