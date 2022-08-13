package cn.zeppin.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.dao.ITeacherAdjustDao;
import cn.zeppin.dao.ITeacherDao;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherAdjust;
import cn.zeppin.service.ITeacherAdjustService;

@SuppressWarnings("rawtypes")
public class TeacherAdjustServiceImpl extends
		BaseServiceImpl<TeacherAdjust, Integer> implements
		ITeacherAdjustService {
	
	private ITeacherAdjustDao iTeacherAdjustDao;
	private ITeacherDao teacherDao;

	public ITeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public ITeacherAdjustDao getiTeacherAdjustDao() {
		return iTeacherAdjustDao;
	}

	public void setiTeacherAdjustDao(ITeacherAdjustDao iTeacherAdjustDao) {
		this.iTeacherAdjustDao = iTeacherAdjustDao;
	}
	

	@Override
	public TeacherAdjust add(TeacherAdjust t) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.add(t);
	}

	@Override
	public TeacherAdjust update(TeacherAdjust t) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.update(t);
	}

	@Override
	public void delete(TeacherAdjust t) {
		// TODO Auto-generated method stub
		this.iTeacherAdjustDao.delete(t);
	}

	@Override
	public TeacherAdjust load(Integer id) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.load(id);
	}

	@Override
	public TeacherAdjust get(Integer id) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.get(id);
	}

	@Override
	public List<TeacherAdjust> loadAll() {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.loadAll();
	}

	@Override
	public List<TeacherAdjust> findAll() {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.findByHSQL(querySql);
	}

	@Override
	public List<TeacherAdjust> getListForPage(String hql, int offset,
			int length) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		this.iTeacherAdjustDao.executeHSQL(hql);
	}

	@Override
	public List<TeacherAdjust> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.getListByHSQL(hql);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		this.iTeacherAdjustDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length,
			Object[] objParas) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<TeacherAdjust> getListForPage(String hql, int start,
			int length, Object[] object) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.getListForPage(hql, start, length, object);
	}

	@Override
	public List getRecordsListByParams(
			Map<String, Object> params, Map<String, Object> sortMap,
			int offset, int length) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.getRecordsListByParams(params, sortMap, offset, length);
	}

	@Override
	public int getRecordsListByParams(Map<String, Object> params,
			Map<String, Object> sortMap) {
		// TODO Auto-generated method stub
		return this.iTeacherAdjustDao.getRecordsListByParams(params, sortMap);
	}

	@Override
	public void saveMergeSchool(Organization oorg, Organization norg,UserSession us) {
		// TODO Auto-generated method stub
		Set<Teacher> teacherSet = oorg.getTeachers();
		for(Teacher tea : teacherSet){
			
			if(tea.getStatus() == 3){
				continue;
			}
			TeacherAdjust ta = new TeacherAdjust();
			ta.setTeacher(tea);
			ta.setOorganization(tea.getOrganization());
			ta.setNorganization(norg);
			ta.setCreator(us.getId());
			ta.setCreatorType(us.getRole());
			ta.setStatus((short)0);
			ta.setCreatetime(new Timestamp(System.currentTimeMillis()));

			if(tea.getStatus() == 1){
				tea.setStatus((short)3);//更新状态
			}
			this.teacherDao.update(tea);
			this.iTeacherAdjustDao.add(ta);//入库
		}
	}

	@Override
	public void saveAdjuestTeacher(Teacher teacher, TeacherAdjust ta) {
		// TODO Auto-generated method stub
		this.teacherDao.update(teacher);
		this.iTeacherAdjustDao.add(ta);//入库
	}

	@Override
	public void updateAdjuestTeacher(Teacher teacher, TeacherAdjust ta) {
		// TODO Auto-generated method stub
		this.teacherDao.update(teacher);
		this.iTeacherAdjustDao.update(ta);
	}
	
	

}
