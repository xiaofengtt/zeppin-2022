package cn.zeppin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.dao.IOtherTrainingRecordsDao;
import cn.zeppin.entity.OtherTrainingRecords;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.IOtherTrainingRecordsService;

public class OtherTrainingRecordsServiceImpl extends BaseServiceImpl<OtherTrainingRecords, Integer> implements
	IOtherTrainingRecordsService
{

    private IOtherTrainingRecordsDao otherTrainingRecordsDao;

    public IOtherTrainingRecordsDao getOtherTrainingRecordsDao()
    {
    	return otherTrainingRecordsDao;
    }

    public void setOtherTrainingRecordsDao(IOtherTrainingRecordsDao otherTrainingRecordsDao)
    {
    	this.otherTrainingRecordsDao = otherTrainingRecordsDao;
    }

    public OtherTrainingRecordsServiceImpl() throws NullPointerException
    {
    }

    public List<String> getProjectYearList(){
    	return this.otherTrainingRecordsDao.getProjectYearList();
    }
    
	public List<String> getProjectNameByParams(HashMap<String, String> searchMap){
		return this.otherTrainingRecordsDao.getProjectNameByParams(searchMap);
	}
    
	public Integer getCountByParams(HashMap<String,String> searchMap){
		return this.otherTrainingRecordsDao.getCountByParams(searchMap);
	}
	
	public List<OtherTrainingRecords> getListByParams(HashMap<String,String> searchMap, Map<String, String> sortParams, Integer start, Integer limit){
		return this.otherTrainingRecordsDao.getListByParams(searchMap,sortParams,start,limit);
	}
	
	public void addOtherTrainingRecords(String year , ProjectType projectType , String projectName , String shortName ,Integer rowCount, List<HashMap<String,Object>> infomationList){
		HttpSession session = ServletActionContext.getRequest().getSession();
		for(int i=0;i<infomationList.size();i++){
			HashMap<String,Object> info = infomationList.get(i);
			Teacher teacher = (Teacher)info.get("teacher");
			TrainingSubject trainingSubject = (TrainingSubject)info.get("trainingSubject");
			TrainingCollege trainingCollege = (TrainingCollege)info.get("trainingCollege");
			HashMap<String,String> searchMap = new HashMap<String,String>();
			searchMap.put("year", year);
			searchMap.put("projectType", projectType.getId().toString());
			searchMap.put("projectName", projectName);
			searchMap.put("teacher", teacher.getId().toString());
			searchMap.put("trainingSubject", trainingSubject.getId().toString());
			searchMap.put("trainingCollege", trainingCollege.getId().toString());
			if(this.getCountByParams(searchMap) == 0){
				OtherTrainingRecords otr = new OtherTrainingRecords();
				otr.setYear(year);
				otr.setProjectType(projectType);
				otr.setShortName(shortName);
				otr.setProjectName(projectName);
				otr.setTeacher(teacher);
				otr.setTrainingSubject(trainingSubject);
				otr.setTrainingCollege(trainingCollege);
				if(info.get("trainingHour") != null && !info.get("trainingHour").equals("")){
					otr.setTrainingHour(Integer.valueOf((info.get("trainingHour").toString())));
				}else{
					otr.setTrainingHour(0);
				}
				if(info.get("trainingOnlineHour") != null && !info.get("trainingOnlineHour").equals("")){
					otr.setTrainingOnlineHour(Integer.valueOf((info.get("trainingOnlineHour").toString())));
				}else{
					otr.setTrainingOnlineHour(0);
				}
				otr.setStartTime(info.get("startTime").toString());
				otr.setEndTime(info.get("endTime").toString());
				this.add(otr);
			}
			int percent = (int) Math.ceil(((rowCount + infomationList.indexOf(info)) * 100) / (rowCount * 2));
			session.setAttribute("percent", percent);
		}
	}
	
    @Override
    public OtherTrainingRecords add(OtherTrainingRecords t) {
    	return otherTrainingRecordsDao.add(t);
    }

    @Override
    public OtherTrainingRecords update(OtherTrainingRecords t) {
    	return otherTrainingRecordsDao.update(t);
    }

    @Override
    public void delete(OtherTrainingRecords t){
    	otherTrainingRecordsDao.delete(t);
    }

    @Override
    public OtherTrainingRecords load(Integer id){
    	return otherTrainingRecordsDao.load(id);
    }

    @Override
    public OtherTrainingRecords get(Integer id){
    	return otherTrainingRecordsDao.get(id);
    }
}
