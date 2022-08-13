package cn.zeppin.service.impl;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherDao;
import cn.zeppin.dao.impl.TeacherDaoImpl;
import cn.zeppin.entity.Teacher;
import cn.zeppin.service.ITeacherService;

public class TeacherServiceImpl extends BaseServiceImpl<Teacher, Integer> implements ITeacherService {

	private ITeacherDao teacherDao;

	public ITeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	@Override
	public Teacher add(Teacher t) {
		// TODO Auto-generated method stub
		return teacherDao.add(t);
	}

	@Override
	public Teacher update(Teacher t) {
		// TODO Auto-generated method stub
		return teacherDao.update(t);
	}

	@Override
	public void delete(Teacher t) {
		// TODO Auto-generated method stub
		teacherDao.delete(t);
	}

	@Override
	public Teacher load(Integer id) {
		// TODO Auto-generated method stub
		return teacherDao.load(id);
	}

	@Override
	public Teacher get(Integer id) {
		// TODO Auto-generated method stub
		return teacherDao.get(id);
	}

	@Override
	public List<Teacher> loadAll() {
		// TODO Auto-generated method stub
		return teacherDao.loadAll();
	}

	@Override
	public List<Teacher> findAll() {
		// TODO Auto-generated method stub
		return teacherDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return teacherDao.findByHSQL(querySql);
	}

	@Override
	public List<Teacher> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return teacherDao.getListForPage(hql, offset, length);
	}

	@Override
	public List<Teacher> getListForPage(String hql, int offset, int length, Object[] objects) {
		// TODO Auto-generated method stub
		return teacherDao.getListForPage(hql, offset, length, objects);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		teacherDao.executeHSQL(hql);
	}

	@Override
	public List<Teacher> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return teacherDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return teacherDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		teacherDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return teacherDao.getListPage(sql, offset, length, objParas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.ITeacherService#existIdCard(java.lang.String)
	 */
	@Override
	public boolean existIdCard(String idCard) {
		// TODO Auto-generated method stub

		String hqlString = "from  Teacher where idcard='" + idCard + "'";
		int count = teacherDao.getListByHSQL(hqlString).size();
		return count > 0 ? true : false;

	}

	@Override
	public Teacher getTeacherByIdCard(String idCard) {
		String hqlString = "from  Teacher where idcard='" + idCard + "'";
		List<Teacher> lit = teacherDao.getListByHSQL(hqlString);
		if (lit != null && lit.size() > 0) {
			return lit.get(0);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.ITeacherService#existMobile(java.lang.String)
	 */
	@Override
	public boolean existMobile(String mobile) {
		// TODO Auto-generated method stub
		String hqlString = "from  Teacher where mobile='" + mobile + "'";
		int count = teacherDao.getListByHSQL(hqlString).size();
		return count > 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.ITeacherService#existEmail(java.lang.String)
	 */
	@Override
	public boolean existEmail(String email) {
		// TODO Auto-generated method stub
		String hqlString = "from  Teacher where email='" + email + "'";
		int count = teacherDao.getListByHSQL(hqlString).size();
		return count > 0 ? true : false;
	}

	public int getTotalPage(int pageLength) {
		int totalPage = 1;
		String sqlString = "select count(*) from teacher";
		return totalPage;

	}

	/**
	 * @author Clark 2014.05.27
	 * 
	 *         通过搜索条件取得教师信息
	 * @param paramsMap
	 * @param offset
	 * @param length
	 * @return List<Teacher> 符合条件的教师信息列表
	 */
	@Override
	public List<Teacher> getTeachers(Map<String, Object> paramsMap, int offset, int length) {
		// TODO Auto-generated method stub
		List<Teacher> result = this.getTeacherDao().getTeachers(paramsMap, offset, length);
		return result;
	}

	/**
	 * @author Clark 2014.05.27
	 * 
	 *         通过搜索条件取得教师信息数量
	 * @param paramsMap
	 * @return int 符合条件的教师信息数量
	 */
	@Override
	public int getTeacherCount(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		int result = this.getTeacherDao().getTeacherCount(paramsMap);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.ITeacherService#getCount(java.lang.String)
	 */
	@Override
	public double getCount(String string) {
		// TODO Auto-generated method stub
		double count = teacherDao.getCount(string);
		return count;
	}

	@Override
	public List<Teacher> getTeacherByEmail(String email) {
		// TODO Auto-generated method stub
		return this.teacherDao.getTeacherByEmail(email);
	}

	@Override
	public List getTeacherForLogin(Object[] pars) {
		// TODO Auto-generated method stub
		return this.teacherDao.getTeacherForLogin(pars);
	}

}
