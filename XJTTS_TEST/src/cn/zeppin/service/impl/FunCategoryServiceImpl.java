package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IFunCategoryDao;
import cn.zeppin.entity.FunCategory;
import cn.zeppin.service.IFunCategoryService;

public class FunCategoryServiceImpl extends BaseServiceImpl<FunCategory, Integer> implements IFunCategoryService {

	private IFunCategoryDao funCategoryDao;
	
	public IFunCategoryDao getFunCategoryDao() {
		return funCategoryDao;
	}

	public void setFunCategoryDao(IFunCategoryDao funCategoryDao) {
		this.funCategoryDao = funCategoryDao;
	}

	@Override
	public FunCategory add(FunCategory t) {
		// TODO Auto-generated method stub
		return funCategoryDao.add(t);
	}

	@Override
	public FunCategory update(FunCategory t) {
		// TODO Auto-generated method stub
		return funCategoryDao.update(t);
	}

	@Override
	public void delete(FunCategory t) {
		// TODO Auto-generated method stub
		funCategoryDao.delete(t);
	}

	@Override
	public FunCategory load(Integer id) {
		// TODO Auto-generated method stub
		return funCategoryDao.load(id);
	}

	@Override
	public FunCategory get(Integer id) {
		// TODO Auto-generated method stub
		return funCategoryDao.get(id);
	}

	@Override
	public List<FunCategory> loadAll() {
		// TODO Auto-generated method stub
		return funCategoryDao.loadAll();
	}

	@Override
	public List<FunCategory> findAll() {
		// TODO Auto-generated method stub
		return funCategoryDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return funCategoryDao.findByHSQL(querySql);
	}

	@Override
	public List<FunCategory> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return funCategoryDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		funCategoryDao.executeHSQL(hql);
	}

	@Override
	public List<FunCategory> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return funCategoryDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return funCategoryDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		funCategoryDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return funCategoryDao.getListPage(sql, offset, length, objParas);
	}

	

}
