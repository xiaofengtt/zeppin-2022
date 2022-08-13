package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.service.teaching.elective.PrBzzTchSelectCoursesSerivce;
import com.whaty.platform.entity.web.action.MyBaseAction;

import com.whaty.platform.util.JsonUtil;

public class PrBzzTchSelectCoursesAction extends MyBaseAction {

    private String id;
    private String message;
    private PrBzzTchSelectCoursesSerivce prBzzTchSelectCoursesSerivce;
    private String returnUrl;
    private String status;

    public PrBzzTchSelectCoursesSerivce getPrBzzTchSelectCoursesSerivce() {
	return prBzzTchSelectCoursesSerivce;
    }

    public void setPrBzzTchSelectCoursesSerivce(
	    PrBzzTchSelectCoursesSerivce prBzzTchSelectCoursesSerivce) {
	this.prBzzTchSelectCoursesSerivce = prBzzTchSelectCoursesSerivce;
    }

    public String SelectCourses() {
	try {
	    if(status!=null&&status.equals("returnurl")){
		message ="/entity/teaching/prBzzTchSelectCourses_SelectCourses.action";
		returnUrl ="ajaxsubmit";
		  return returnUrl;
	    }else if(status!=null&&status.equals("submit")){
	    // 得到当前批次下面的学生
	    int num=1;
		Map map = new HashMap();
	    DetachedCriteria opdcv = DetachedCriteria
		    .forClass(PeBzzStudent.class);
	    opdcv.add(Restrictions.eq("peBzzBatch.id", id));
	    List ssolist = this.getGeneralService().getList(opdcv);
	    Iterator<PeBzzStudent> pit = ssolist.iterator();

	    while (pit!=null &&pit.hasNext()) {
	    System.out.println("stunum:"+num);
	    num++;
		PeBzzStudent bzzStudent = pit.next();
		String sid = bzzStudent.getSsoUser().getId();
		DetachedCriteria TrainingCou = DetachedCriteria
			.forClass(TrainingCourseStudent.class);
		TrainingCou.add(Restrictions.eq("ssoUser.id", sid));
		TrainingCou.setProjection(Property
			.forName("prBzzTchOpencourse.id"));
		List slist = this.getGeneralService().getList(TrainingCou);

		//获取该学生没有选择的开课
		DetachedCriteria studentdc = DetachedCriteria
			.forClass(PrBzzTchOpencourse.class);
		studentdc.add(Restrictions.eq("peBzzBatch.id", id));
		studentdc.setProjection(Property.forName("id"));
		List zlist = this.getGeneralService().getList(studentdc);
		DetachedCriteria openCoursedc = DetachedCriteria
			.forClass(PrBzzTchOpencourse.class);
		openCoursedc.add(Restrictions.eq("peBzzBatch.id", id));
		if (zlist != null && zlist.size() > 0) {
		    if (slist != null && slist.size() > 0) {
			zlist.removeAll(slist);
		    }
		    if(zlist!=null && zlist.size() > 0)
		    {
		    	openCoursedc.add(Restrictions.in("id", zlist));
		    }
		}
		
		//如果存在没有选择的课程，开始选课
		if(zlist!=null && zlist.size()>0)
		{
			List<PrBzzTchOpencourse> courselist = this.getGeneralService()
				.getList(openCoursedc);
			if (courselist!=null && courselist.size() > 0) {
			    
			    for (int n = 0; n < courselist.size(); n++) {
				Date now = new Date();
				TrainingCourseStudent tr = new TrainingCourseStudent();
				tr.setPercent(Double.parseDouble("0"));
				tr.setSsoUser(bzzStudent.getSsoUser());
				tr.setPrBzzTchOpencourse(courselist.get(n));
				tr.setLearnStatus("INCOMPLETE");
				PrBzzTchStuElective elective = new PrBzzTchStuElective();
				elective.setPrBzzTchOpencourse(courselist.get(n));
				elective.setPeBzzStudent(bzzStudent);
				elective.setElectiveDate(now);
				tr.setPrBzzTchStuElective(elective);
				elective.setTrainingCourseStudent(tr);
				this.getGeneralService().save(elective);
			    }
			}
		}
	    }
	   map.put("success", "true");
	   map.put("msg", "开课成功");
	   this.setJsonString(JsonUtil.toJSONString(map));
	   return json();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    @Override
    public void initGrid() {
	// TODO Auto-generated method stub

    }

    @Override
    public void setEntityClass() {
	// TODO Auto-generated method stub

    }

    @Override
    public void setServletPath() {
	this.servletPath = "/entity/teaching/prBzzTchSelectCourses";
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getReturnUrl() {
	return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
	this.returnUrl = returnUrl;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }
}
