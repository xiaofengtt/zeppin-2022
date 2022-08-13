package cn.zeppin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.ITeacherTrainingAssignedDao;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.TeacherTrainingAssigned;
import cn.zeppin.service.ITeacherTrainingAssginedService;

@SuppressWarnings("rawtypes")
public class TeacherTrainingAssignedServiceImpl extends
		BaseServiceImpl<TeacherTrainingAssigned, Integer> implements
		ITeacherTrainingAssginedService {
	
	private ITeacherTrainingAssignedDao teacherTrainingAssignedDao;
	
	

	public ITeacherTrainingAssignedDao getTeacherTrainingAssignedDao() {
		return teacherTrainingAssignedDao;
	}

	public void setTeacherTrainingAssignedDao(
			ITeacherTrainingAssignedDao teacherTrainingAssignedDao) {
		this.teacherTrainingAssignedDao = teacherTrainingAssignedDao;
	}

	@Override
	public List<String> getProjectYearList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getProjectNameByParams(HashMap<String, String> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountByParams(HashMap<String, String> searchMap) {
		// TODO Auto-generated method stub
		return this.teacherTrainingAssignedDao.getCountByParams(searchMap);
	}

	@Override
	public List getListByParams(
			HashMap<String, String> searchMap, Map<String, String> sortParams,
			Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return this.teacherTrainingAssignedDao.getListByParams(searchMap, sortParams, start, limit);
	}

	@Override
	public void addOtherTrainingRecords(String year, ProjectType projectType,
			String projectName, String shortName, Integer rowCount,
			List<HashMap<String, Object>> infomationList) {
		// TODO Auto-generated method stub

	}

	@Override
	public TeacherTrainingAssigned add(TeacherTrainingAssigned t) {
		// TODO Auto-generated method stub
		return this.teacherTrainingAssignedDao.add(t);
	}

	@Override
	public TeacherTrainingAssigned update(TeacherTrainingAssigned t) {
		// TODO Auto-generated method stub
		return this.teacherTrainingAssignedDao.update(t);
	}

	@Override
	public void delete(TeacherTrainingAssigned t) {
		// TODO Auto-generated method stub
		this.teacherTrainingAssignedDao.delete(t);
	}

	@Override
	public TeacherTrainingAssigned load(Integer id) {
		// TODO Auto-generated method stub
		return this.teacherTrainingAssignedDao.load(id);
	}

	@Override
	public TeacherTrainingAssigned get(Integer id) {
		// TODO Auto-generated method stub
		return this.teacherTrainingAssignedDao.get(id);
	}

	@Override
	public List<TeacherTrainingAssigned> findAll() {
		// TODO Auto-generated method stub
		return this.teacherTrainingAssignedDao.findAll();
	}

	
	
}
