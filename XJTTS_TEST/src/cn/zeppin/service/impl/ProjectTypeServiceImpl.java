package cn.zeppin.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import cn.zeppin.dao.IProjectTypeDao;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.service.IProjectTypeService;

public class ProjectTypeServiceImpl extends BaseServiceImpl<ProjectType, Integer> implements IProjectTypeService {

	private IProjectTypeDao projectTypeDao;

	public IProjectTypeDao getProjectTypeDao() {
		return projectTypeDao;
	}

	public void setProjectTypeDao(IProjectTypeDao projectTypeDao) {
		this.projectTypeDao = projectTypeDao;
	}

	@Override
	public ProjectType add(ProjectType t) {
		// TODO Auto-generated method stub
		return projectTypeDao.add(t);
	}

	@Override
	public ProjectType update(ProjectType t) {
		// TODO Auto-generated method stub
		return projectTypeDao.update(t);
	}

	@Override
	public void delete(ProjectType t) {
		// TODO Auto-generated method stub
		projectTypeDao.delete(t);
	}

	@Override
	public ProjectType load(Integer id) {
		// TODO Auto-generated method stub
		return projectTypeDao.load(id);
	}

	@Override
	public ProjectType get(Integer id) {
		// TODO Auto-generated method stub
		return projectTypeDao.get(id);
	}

	@Override
	public List<ProjectType> loadAll() {
		// TODO Auto-generated method stub
		return projectTypeDao.loadAll();
	}

	@Override
	public List<ProjectType> findAll() {
		// TODO Auto-generated method stub
		return projectTypeDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return projectTypeDao.findByHSQL(querySql);
	}

	@Override
	public List<ProjectType> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return projectTypeDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		projectTypeDao.executeHSQL(hql);
	}

	@Override
	public List<ProjectType> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return projectTypeDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectTypeDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		projectTypeDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return projectTypeDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<ProjectType> findByName(String name) {
		return this.projectTypeDao.findByName(name);
	}

	@Override
	// 插入一个项目类型
	public int instByParams(Object[] pars) {
		// TODO Auto-generated method stub

		return this.projectTypeDao.instByParams(pars);

	}

	@Override
	public int updateByParams(Object[] pars) {
		// TODO Auto-generated method stub
		return this.projectTypeDao.updateByParams(pars);
	}

	@Override
	public List<ProjectType> getListByPid(int id) {
		String hql = "from ProjectType t where 1=1 and t.status=1 ";
		if (id == 0) {
			hql += " and t.projectType is null";
		} else {
			hql += " and t.projectType =" + id;
		}
		return this.getListByHSQL(hql);
	}

	@Override
	public int getProjectHasChild(int id) {

		String hql = "select count(*) from ProjectType t where 1=1 ";
		if (id == 0) {
			hql += " and t.projectType is null";
		} else {
			hql += " and t.projectType =" + id;
		}

		List li = this.findByHSQL(hql);
		if (li != null && li.size() > 0) {
			int flag = Integer.parseInt(li.get(0).toString());
			return flag > 0 ? 1 : 0;
		} else {
			return 0;
		}
	}

	@Override
	public void updateProjectTypeStatus(ProjectType pt, short status) {

		Set<ProjectType> pts = pt.getProjectTypes();
		if (pts.size() == 0) {
			pt.setStatus(status);
			this.update(pt);
		} else {
			for (ProjectType ptc : pts) {
				this.updateProjectTypeStatus(ptc, status);
			}
			pt.setStatus(status);
			this.update(pt);
		}

	}

	@Override
	public ProjectType getRootProjectType(ProjectType projectType) {
		// TODO Auto-generated method stub
		return this.getProjectTypeDao().getRootProjectType(projectType);
	}

}
