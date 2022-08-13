package cn.zeppin.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.dao.ITeacherExamRecordsDao;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherExamRecords;
import cn.zeppin.service.ITeacherExamRecordsService;

public class TeacherExamRecordsServiceImpl extends BaseServiceImpl<TeacherExamRecords, Integer> implements
	ITeacherExamRecordsService
{

    private ITeacherExamRecordsDao teacherExamRecordsDao;

    public ITeacherExamRecordsDao getTeacherExamRecordsDao()
    {
    	return teacherExamRecordsDao;
    }

    public void setTeacherExamRecordsDao(ITeacherExamRecordsDao teacherExamRecordsDao)
    {
    	this.teacherExamRecordsDao = teacherExamRecordsDao;
    }

    public TeacherExamRecordsServiceImpl() throws NullPointerException
    {
    }

    public List<String> getYearList(){
    	return this.teacherExamRecordsDao.getYearList();
    }
    
	public List<String> getExamByParams(HashMap<String, String> searchMap){
		return this.teacherExamRecordsDao.getExamByParams(searchMap);
	}
    
	public Integer getCountByParams(HashMap<String,String> searchMap){
		return this.teacherExamRecordsDao.getCountByParams(searchMap);
	}
	
	public List<TeacherExamRecords> getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer start, Integer limit){
		return this.teacherExamRecordsDao.getListByParams(searchMap,sortParams,start,limit);
	}
	
	public void addTeacherExamRecords(String year, String exam ,Integer rowCount, List<HashMap<String,Object>> infomationList){
		HttpSession session = ServletActionContext.getRequest().getSession();
		for(int i=0;i<infomationList.size();i++){
			HashMap<String,Object> info = infomationList.get(i);
			Teacher teacher = (Teacher)info.get("teacher");
			HashMap<String,String> searchMap = new HashMap<String,String>();
			searchMap.put("year", year);
			searchMap.put("exam", exam);
			searchMap.put("teacher", teacher.getId().toString());
			if(this.getCountByParams(searchMap) == 0){
				TeacherExamRecords ter = new TeacherExamRecords();
				ter.setYear(year);
				ter.setExam(exam);
				ter.setTeacher(teacher);
				ter.setScore(Float.valueOf(info.get("score").toString()));
				ter.setCreattime(new Timestamp(System.currentTimeMillis()));
				this.add(ter);
			}
			int percent = (int) Math.ceil(((rowCount + infomationList.indexOf(info)) * 100) / (rowCount * 2));
			session.setAttribute("percent", percent);
		}
	}
	
    @Override
    public TeacherExamRecords add(TeacherExamRecords t) {
    	return teacherExamRecordsDao.add(t);
    }

    @Override
    public TeacherExamRecords update(TeacherExamRecords t) {
    	return teacherExamRecordsDao.update(t);
    }

    @Override
    public void delete(TeacherExamRecords t){
    	teacherExamRecordsDao.delete(t);
    }

    @Override
    public TeacherExamRecords load(Integer id){
    	return teacherExamRecordsDao.load(id);
    }

    @Override
    public TeacherExamRecords get(Integer id){
    	return teacherExamRecordsDao.get(id);
    }
}
