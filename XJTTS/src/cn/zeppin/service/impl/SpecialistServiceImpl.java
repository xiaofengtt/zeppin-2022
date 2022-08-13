package cn.zeppin.service.impl;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.dao.ISpecialistDao;
import cn.zeppin.entity.Specialist;
import cn.zeppin.service.ISpecialistService;

public class SpecialistServiceImpl extends BaseServiceImpl<Specialist, Integer> implements ISpecialistService {
	
	private ISpecialistDao specialistDao;
	
	public ISpecialistDao getSpecialistDao() {
		return specialistDao;
	}

	public void setSpecialistDao(ISpecialistDao specialistDao) {
		this.specialistDao = specialistDao;
	}

	public Integer getSpecialistCount(HashMap<String,String> searchMap){
		return specialistDao.getSpecialistCount(searchMap);
	}
	
	public List<Specialist> getSpecialistList(HashMap<String,String> searchMap , String sortname,  String sorttype, int offset, int length){
		return specialistDao.getSpecialistList(searchMap, sortname, sorttype, offset, length);
	}

	public int checkUserInfo(Object[] pars) {
		return specialistDao.checkUserInfo(pars);
	}
	
	@Override
	public Specialist add(Specialist t) {
		return specialistDao.add(t);
	}


	@Override
	public Specialist update(Specialist t) {
		return specialistDao.update(t);
	}


	@Override
	public void delete(Specialist t) {
		specialistDao.delete(t);
	}


	@Override
	public Specialist load(Integer id) {
		return specialistDao.load(id);
	}


	@Override
	public Specialist get(Integer id) {
		return specialistDao.get(id);
	}


	@Override
	public List<Specialist> loadAll() {
		return specialistDao.loadAll();
	}


	@Override
	public List<Specialist> findAll() {
		return specialistDao.findAll();
	}
}
