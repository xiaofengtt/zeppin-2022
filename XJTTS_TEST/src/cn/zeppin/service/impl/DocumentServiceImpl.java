package cn.zeppin.service.impl;

import java.util.List;

import cn.zeppin.dao.IDocumentDao;
import cn.zeppin.entity.Document;
import cn.zeppin.service.IDocumentService;

public class DocumentServiceImpl extends BaseServiceImpl<Document, Integer> implements IDocumentService {

	private IDocumentDao documentDao;

	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	@Override
	public Document add(Document t) {
		// TODO Auto-generated method stub
		return documentDao.add(t);
	}

	@Override
	public Document update(Document t) {
		// TODO Auto-generated method stub
		return documentDao.update(t);
	}

	@Override
	public void delete(Document t) {
		// TODO Auto-generated method stub
		documentDao.delete(t);
	}

	@Override
	public Document load(Integer id) {
		// TODO Auto-generated method stub
		return documentDao.load(id);
	}

	@Override
	public Document get(Integer id) {
		// TODO Auto-generated method stub
		return documentDao.get(id);
	}

	@Override
	public List<Document> loadAll() {
		// TODO Auto-generated method stub
		return documentDao.loadAll();
	}

	@Override
	public List<Document> findAll() {
		// TODO Auto-generated method stub
		return documentDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return documentDao.findByHSQL(querySql);
	}

	@Override
	public List<Document> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return documentDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		documentDao.executeHSQL(hql);
	}

	@Override
	public List<Document> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return documentDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return documentDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		documentDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return documentDao.getListPage(sql, offset, length, objParas);
	}

}
