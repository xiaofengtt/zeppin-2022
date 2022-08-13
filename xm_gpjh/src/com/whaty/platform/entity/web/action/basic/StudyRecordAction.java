package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.bean.PrTchTraineeCourse;
import com.whaty.platform.entity.bean.ScormCourseInfo;
import com.whaty.platform.entity.bean.ScormStuCourse;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.offline.OffLineRecordManage;
import com.whaty.platform.util.JsonUtil;

public class StudyRecordAction extends MyBaseAction {

	private String course_id;
	private String loginId;
	private String course_name;
	private List scormStuList;//学习记录列表
	private List jsonList;//学习记录详细信息json字符串列表
	private List courseWareNameList;//课件名称列表
	
	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText("学习记录跟踪"));
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("ID2"), "peTchCourse.id", false);
		this.getGridConfig().addColumn(this.getText("学员姓名"), "peTrainee.trueName");
		this.getGridConfig().addColumn(this.getText("性别"),	"enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("学员账号"), "peTrainee.loginId");
		this.getGridConfig().addColumn(this.getText("身份证号"), "peTrainee.cardNo");
		this.getGridConfig().addColumn(this.getText("登录次数"),"ssoUser.loginNum");
		this.getGridConfig().addColumn(this.getText("最近登录日期"),"ssoUser.lastLoginDate");
		this.getGridConfig().addColumn(this.getText("在线时长"),"onlineTime");
		this.getGridConfig().addColumn(this.getText("所属班级"),"peTrainingClass.name");
		this.getGridConfig().addColumn(this.getText("课程名称"),"peTchCourse.name");
		this.getGridConfig().addRenderFunction(this.getText("SCORM学习记录"), "<a href='studyRecord_viewStudyRecord.action?course_id=\"+record.data['peTchCourse.id']+\"&loginId=\"+record.data['peTrainee.loginId'] + \"' target='_blank'>查看</a>");
		
	}
	
	/**
	 * 查看学员课程的学习记录
	 * 
	 * @return
	 */
	public String viewStudyRecord() {
		
		this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourse.class);
		try {
			PeTchCourse c = (PeTchCourse)this.getGeneralService().getById(this.course_id);
			if(c != null) {
				this.setCourse_name(c.getName());
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		this.getGeneralService().getGeneralDao().setEntityClass(SsoUser.class);
		DetachedCriteria dcp=DetachedCriteria.forClass(SsoUser.class);
		dcp.add(Restrictions.eq("loginId", this.loginId));
		SsoUser ssoUser = null;
		
		try {
			List ll = this.getGeneralService().getList(dcp);
			if(ll != null && ll.size() > 0) {
				ssoUser = (SsoUser)ll.get(0);
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		if(ssoUser != null) {
			this.getGeneralService().getGeneralDao().setEntityClass(PeTchCourseware.class);
			DetachedCriteria dc=DetachedCriteria.forClass(PeTchCourseware.class);
			dc.createCriteria("peTchCourse", "peTchCourse");
			dc.add(Restrictions.eq("peTchCourse.id", this.course_id));
			List idList = new ArrayList();
			try {
				List list = this.getGeneralService().getList(dc);
				if(list != null && list.size() > 0) {
					List codeList = new ArrayList();
					for(int i = 0; i < list.size(); i++) {
						codeList.add(((PeTchCourseware)list.get(i)).getCode());
					}
					
					this.getGeneralService().getGeneralDao().setEntityClass(ScormCourseInfo.class);
					DetachedCriteria dcs=DetachedCriteria.forClass(ScormCourseInfo.class);
					dcs.add(Restrictions.in("courseCode", codeList));
					List scormList = this.getGeneralService().getList(dcs);
					
					if(scormList != null && scormList.size() > 0) {
						List courseWareNameList = new ArrayList();
						for(int j = 0; j < scormList.size(); j++) {
							idList.add(((ScormCourseInfo)scormList.get(j)).getId());
							courseWareNameList.add(((ScormCourseInfo)scormList.get(j)).getTitle());
						}
						this.setCourseWareNameList(courseWareNameList);
						
						this.getGeneralService().getGeneralDao().setEntityClass(ScormStuCourse.class);
						DetachedCriteria dcssc=DetachedCriteria.forClass(ScormStuCourse.class);
						dcssc.createCriteria("scormCourseInfo", "scormCourseInfo");
						dcssc.add(Restrictions.in("scormCourseInfo.id", idList));
						dcssc.add(Restrictions.eq("studentId", ssoUser.getId()));
						List l = this.getGeneralService().getList(dcssc);
						if(l != null && l.size() > 0) {
							List scormStuList = new ArrayList();
							for(Object o : l) {
								ScormStuCourse ssc = (ScormStuCourse)o;
								scormStuList.add(ssc);
							}
							this.setScormStuList(scormStuList);
						}
					}
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
			
			try {
				if(idList != null && idList.size() > 0) {
					List jsonList = new ArrayList();
					for(Object o : idList) {
						String id = (String)o;
						OffLineRecordManage recordManage = new OffLineRecordManage();
						recordManage.getPlatformData(ssoUser.getId(), id);
						String returnJson = JsonUtil.toJSONString(recordManage.getOfflineData().get(0));
						jsonList.add(returnJson);
					}
					
					this.setJsonList(jsonList);
				}
			} catch (PlatformException e) {
				e.printStackTrace();
			} catch (ScormException e) {
				e.printStackTrace();
			}
		}
		
		return "view_study_record";
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchTraineeCourse.class);
		dc.createCriteria("peTrainee", "peTrainee",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peTrainee.ssoUser", "ssoUser");
		dc.createCriteria("peTrainee.enumConstByGender", "enumConstByGender",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peTrainee.peTrainingClass", "peTrainingClass",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peTchCourse", "peTchCourse",
				DetachedCriteria.LEFT_JOIN);
		
		return dc;
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchTraineeCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/studyRecord";
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public List getScormStuList() {
		return scormStuList;
	}

	public void setScormStuList(List scormStuList) {
		this.scormStuList = scormStuList;
	}

	public List getJsonList() {
		return jsonList;
	}

	public void setJsonList(List jsonList) {
		this.jsonList = jsonList;
	}

	public List getCourseWareNameList() {
		return courseWareNameList;
	}

	public void setCourseWareNameList(List courseWareNameList) {
		this.courseWareNameList = courseWareNameList;
	}
	
	
	
}
