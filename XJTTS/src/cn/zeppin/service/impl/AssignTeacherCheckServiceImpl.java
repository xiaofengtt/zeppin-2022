package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IAssignTeacherCheckDao;
import cn.zeppin.entity.AssignTeacherCheck;
import cn.zeppin.service.IAssignTeacherCheckService;

public class AssignTeacherCheckServiceImpl extends BaseServiceImpl<AssignTeacherCheck, Integer> implements IAssignTeacherCheckService {

	private IAssignTeacherCheckDao assignTeacherCheckDao;
	
	public IAssignTeacherCheckDao getAssignTeacherCheckDao() {
		return assignTeacherCheckDao;
	}

	public void setAssignTeacherCheckDao(IAssignTeacherCheckDao assignTeacherCheckDao) {
		this.assignTeacherCheckDao = assignTeacherCheckDao;
	}

	@Override
	public AssignTeacherCheck add(AssignTeacherCheck t) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.add(t);
	}

	@Override
	public AssignTeacherCheck update(AssignTeacherCheck t) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.update(t);
	}

	@Override
	public void delete(AssignTeacherCheck t) {
		// TODO Auto-generated method stub
		assignTeacherCheckDao.delete(t);
	}

	@Override
	public AssignTeacherCheck load(Integer id) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.load(id);
	}

	@Override
	public AssignTeacherCheck get(Integer id) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.get(id);
	}

	@Override
	public List<AssignTeacherCheck> loadAll() {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.loadAll();
	}

	@Override
	public List<AssignTeacherCheck> findAll() {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.findByHSQL(querySql);
	}

	@Override
	public List<AssignTeacherCheck> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		assignTeacherCheckDao.executeHSQL(hql);
	}

	@Override
	public List<AssignTeacherCheck> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		assignTeacherCheckDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<AssignTeacherCheck> getListForPage(String hql, int start, int length, Object[] object) {
		// TODO Auto-generated method stub
		return assignTeacherCheckDao.getListForPage(hql, start, length, object);
	}

	@Override
	public List<AssignTeacherCheck> getAssignTeacherChecksByTeacherRecordId(int teacherRecordId) {
		// TODO Auto-generated method stub
		return this.assignTeacherCheckDao.getAssignTeacherChecksByTeacherRecordId(teacherRecordId);
	}
	
	

}
