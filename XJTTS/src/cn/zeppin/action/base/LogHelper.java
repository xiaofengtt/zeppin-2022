package cn.zeppin.action.base;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.aop.AfterReturningAdvice;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.AssignTeacherTask;
import cn.zeppin.entity.Document;
import cn.zeppin.entity.Grade;
import cn.zeppin.entity.JobDuty;
import cn.zeppin.entity.JobTitle;
import cn.zeppin.entity.Log;
import cn.zeppin.entity.MailInformation;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.OtherTrainingRecords;
import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectAdmin;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectApplyEvaluate;
import cn.zeppin.entity.ProjectApplyExpert;
import cn.zeppin.entity.ProjectExpert;
import cn.zeppin.entity.ProjectType;
import cn.zeppin.entity.Psq;
import cn.zeppin.entity.Subject;
import cn.zeppin.entity.Teacher;
import cn.zeppin.entity.TeacherEduAdvance;
import cn.zeppin.entity.TeacherTrainingCertificate;
import cn.zeppin.entity.TeacherTrainingRecords;
import cn.zeppin.entity.TeacherTrainingReplace;
import cn.zeppin.entity.TeacherTrainingReversal;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.entity.TrainingCollege;
import cn.zeppin.entity.TrainingSubject;
import cn.zeppin.service.ILogService;
import cn.zeppin.utility.DictionyMap;
  
public class LogHelper implements AfterReturningAdvice{   
	private ILogService iLogService;
	@SuppressWarnings("serial")
	private List<String> logList = new ArrayList<String>() {
	{
	    add("class cn.zeppin.entity.AssignTeacherTask");
	    add("class cn.zeppin.entity.Document");
	    add("class cn.zeppin.entity.Grade");
	    add("class cn.zeppin.entity.JobDuty");
	    add("class cn.zeppin.entity.JobTitle");
	    add("class cn.zeppin.entity.Organization");
	    add("class cn.zeppin.entity.Project");
	    add("class cn.zeppin.entity.ProjectAdmin");
	    add("class cn.zeppin.entity.ProjectApply");
	    add("class cn.zeppin.entity.ProjectApplyExpert");
	    add("class cn.zeppin.entity.ProjectApplyEvaluate");
	    add("class cn.zeppin.entity.ProjectExpert");
	    add("class cn.zeppin.entity.ProjectType");
	    add("class cn.zeppin.entity.Psq");
	    add("class cn.zeppin.entity.Subject");
	    add("class cn.zeppin.entity.Teacher");
	    add("class cn.zeppin.entity.TeacherTrainingRecords");
	    add("class cn.zeppin.entity.TeacherTrainingReplace");
	    add("class cn.zeppin.entity.TeacherTrainingReversal");
	    add("class cn.zeppin.entity.TrainingAdmin");
	    add("class cn.zeppin.entity.TrainingCollege");
	    add("class cn.zeppin.entity.TrainingSubject");
	    add("class cn.zeppin.entity.OtherTrainingRecords");
	    add("class cn.zeppin.entity.TeacherEduAdvance");
	    add("class cn.zeppin.entity.TeacherTrainingCertificate");
	    add("class cn.zeppin.entity.MailInformation");
	}};
	
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] params, Object target) throws Throwable {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(params!=null && params.length >0 && (session.getAttribute("usersession")!=null || session.getAttribute("teachersession")!=null)){
			if(logList.contains(params[0].getClass().toString())){
		    	if (method.getName().equals("add")){
		    		Log log = getLogInfomation(returnValue);
		    		log.setType(DictionyMap.LOG_TYPE_ADD);
		    		this.iLogService.add(log);
		        }else if (method.getName().equals("update")){
		        	Log log = getLogInfomation(returnValue);
		    		log.setType(DictionyMap.LOG_TYPE_UPDATE);
		    		this.iLogService.add(log);
		        }else if (method.getName().equals("delete")){
		        	Log log = getLogInfomation(params[0]);
		    		log.setType(DictionyMap.LOG_TYPE_DELETE);
		    		this.iLogService.add(log);
		        }
			}
		}
	}
	
	Log getLogInfomation(Object obj){
		HttpServletRequest request = ServletActionContext.getRequest();
    	HttpSession session = request.getSession();
    	UserSession us = new UserSession();
    	
    	Log log = new Log();
    	if(session.getAttribute("usersession")!=null){
    		us = (UserSession) session.getAttribute("usersession");
    		log.setUserRole(us.getRole());
    	}else{
    		us = (UserSession) session.getAttribute("teachersession");
    		log.setUserRole((short)3);
    	}
		
    	String table = obj.getClass().toString();
    	String tableName = table.substring(table.lastIndexOf(".")+1);
    	
    	log.setUserId(us.getId());
    	log.setTime(new Timestamp(new Date().getTime()));
    	log.setTableName(tableName);
    	log = setObjectInfo(log , obj);
		return log;
	}
	
	Log setObjectInfo(Log log ,Object obj){
		if(obj.getClass().equals(new AssignTeacherTask().getClass())){
			AssignTeacherTask object = (AssignTeacherTask) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("project", object.getProject().getName());
			dataMap.put("college", object.getTrainingCollege().getName());
			dataMap.put("subject", object.getTrainingSubject().getName());
			dataMap.put("level", object.getLevel().toString());
			dataMap.put("organization", object.getOrganizationByGOrganization().getName());
			if(object.getOrganizationByPOrganization()!=null){
				dataMap.put("parent", object.getOrganizationByPOrganization().getName());
			}else{
				dataMap.put("parent", "");
			}
			dataMap.put("teacherNumber", object.getTeacherNumber().toString());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new Document().getClass())){
			Document object = (Document) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("title", object.getTitle());
			dataMap.put("size", object.getSize().toString());
			dataMap.put("resourceType", object.getResourceType().toString());
			dataMap.put("type", object.getType().toString());
			dataMap.put("path", object.getResourcePath());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new Grade().getClass())){
			Grade object = (Grade) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("isSchool", object.getIsSchool().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new JobDuty().getClass())){
			JobDuty object = (JobDuty) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new JobTitle().getClass())){
			JobTitle object = (JobTitle) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new Organization().getClass())){
			Organization object = (Organization) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("level", object.getOrganizationLevel().getName());
			if(object.getOrganization()!=null){
				dataMap.put("parent", object.getOrganization().getName());
			}else{
				dataMap.put("parent", "");
			}
			dataMap.put("phone", object.getPhone());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new Project().getClass())){
			Project object = (Project) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("year", object.getYear());
			dataMap.put("projectType", object.getProjectType().getName());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new ProjectAdmin().getClass())){
			ProjectAdmin object = (ProjectAdmin) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("organization", object.getOrganization().getName());
			dataMap.put("phone", object.getMobile());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new ProjectApply().getClass())){
			ProjectApply object = (ProjectApply) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("project", object.getProject().getName());
			dataMap.put("subject", object.getTrainingSubject().getName());
			dataMap.put("college", object.getTrainingCollege().getName());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new ProjectApplyExpert().getClass())){
			ProjectApplyExpert object = (ProjectApplyExpert) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("project", object.getProjectApply().getProject().getName());
			dataMap.put("subject", object.getProjectApply().getTrainingSubject().getName());
			dataMap.put("college", object.getProjectApply().getTrainingCollege().getName());
			dataMap.put("expert", object.getProjectExpert().getName());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new ProjectApplyEvaluate().getClass())){
			ProjectApplyEvaluate object = (ProjectApplyEvaluate) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("project", object.getProjectApply().getProject().getName());
			dataMap.put("subject", object.getProjectApply().getTrainingSubject().getName());
			dataMap.put("college", object.getProjectApply().getTrainingCollege().getName());
			dataMap.put("expert", object.getProjectExpert().getName());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new ProjectExpert().getClass())){
			ProjectExpert object = (ProjectExpert) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("organization", object.getOrganization());
			dataMap.put("phone", object.getMobile());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new ProjectType().getClass())){
			ProjectType object = (ProjectType) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("level", object.getProjectLevel().getName().toString());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new Psq().getClass())){
			Psq object = (Psq) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("title", object.getTitle());
			dataMap.put("questions", object.getQuestions().toString());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new Subject().getClass())){
			Subject object = (Subject) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new Teacher().getClass())){
			Teacher object = (Teacher) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("organization", object.getOrganization().getName());
			dataMap.put("phone", object.getMobile());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new TeacherTrainingRecords().getClass())){
			TeacherTrainingRecords object = (TeacherTrainingRecords) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("project", object.getProject().getName());
			dataMap.put("teacher", object.getTeacher().getName());
			dataMap.put("subject", object.getTrainingSubject().getName());
			dataMap.put("college", object.getTrainingCollege().getName());
			dataMap.put("organization", object.getOrganization().getName());
			dataMap.put("finalStatus", object.getFinalStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new TeacherTrainingReplace().getClass())){
			TeacherTrainingReplace object = (TeacherTrainingReplace) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("changeTeacher", object.getTeacher().getName());
			dataMap.put("beChangedTeacher", object.getBereplacedttr().getTeacher().getName());
			dataMap.put("status", object.getReplaceStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new TeacherTrainingReversal().getClass())){
			TeacherTrainingReversal object = (TeacherTrainingReversal) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new TrainingAdmin().getClass())){
			TrainingAdmin object = (TrainingAdmin) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("trainingCollege", object.getTrainingCollege().getName());
			dataMap.put("phone", object.getMobile());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new TrainingCollege().getClass())){
			TrainingCollege object = (TrainingCollege) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new TrainingSubject().getClass())){
			TrainingSubject object = (TrainingSubject) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("name", object.getName());
			dataMap.put("status", object.getStatus().toString());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new OtherTrainingRecords().getClass())){
			OtherTrainingRecords object = (OtherTrainingRecords) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("teacher", object.getTeacher().getName());
			dataMap.put("project", object.getProjectName());
			dataMap.put("trainingSubject", object.getTrainingSubject().getName());
			dataMap.put("trainingCollege", object.getTrainingCollege().getName());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new TeacherEduAdvance().getClass())){
			TeacherEduAdvance object = (TeacherEduAdvance) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("teacher", object.getTeacher().getName());
			dataMap.put("graduation", object.getGraduation());
			dataMap.put("major", object.getMajor());
			dataMap.put("certificate", object.getCertificate());
			dataMap.put("educationBackground", object.getEducationBackground().getName());
			dataMap.put("oldEducationBack", object.getOldEducationBack());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new TeacherTrainingCertificate().getClass())){
			TeacherTrainingCertificate object = (TeacherTrainingCertificate) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("teacher", object.getTeacherTrainingRecords().getTeacher().getName());
			dataMap.put("certificate", object.getTeacherTrainingRecords().getCertificate());
			dataMap.put("urlImage", object.getImageurl());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else if(obj.getClass().equals(new MailInformation().getClass())){
			MailInformation object = (MailInformation) obj;
			log.setTableId(object.getId().toString());
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("id", object.getId().toString());
			dataMap.put("title", object.getTitle());
			dataMap.put("text", object.getText());
			dataMap.put("inscription", object.getInscription());
			JSONObject jsonObject = JSONObject.fromObject(dataMap);
			log.setRemark(jsonObject.toString());
		}else{
			log.setTableId("");
			log.setRemark("");
		}
		return log;
	}
	
	public ILogService getiLogService() {
		return iLogService;
	}

	public void setiLogService(ILogService iLogService) {
		this.iLogService = iLogService;
	}

} 