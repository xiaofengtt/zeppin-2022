package com.whaty.platform.entity.web.action.teaching.elective;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzAssess;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PrBzzTchOpenCourseService;
import com.whaty.platform.entity.service.teaching.elective.SemesterOpenCourseService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;

import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PrBzzTchOpenCourseDetailAction extends MyBaseAction {

	private PrBzzTchOpenCourseService prBzzTchOpenCourseService;

	private String id;
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, true, false, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"),
				"peBzzTchCourse.name");
		this.getGridConfig().addColumn(this.getText("课程代码"),
				"peBzzTchCourse.code");
		this.getGridConfig().addColumn(this.getText("学时"),
				"peBzzTchCourse.time", false, true, true, null);
		this.getGridConfig().addColumn(this.getText("所属学期"), "peBzzBatch.name");
		this.getGridConfig().addColumn(this.getText("课程性质"), "enumConstByFlagCourseType.name");
		this.getGridConfig().addColumn(this.getText("授课教师"),
				"peBzzTchCourse.teacher", false, true, true, "");
		this.getGridConfig().addColumn(this.getText("教师简介"),
				"peBzzTchCourse.teacherNote", false, true, true, "");
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us.getRoleId().equals("3")) {
			this
					.getGridConfig()
					.addRenderFunction(
							this.getText("查看课程评估"),
							"<a href=\"/entity/manager/statistics/chart_assess.jsp?id=${value}\">查看评估结果</a>",
							"id");
			this
					.getGridConfig()
					.addRenderFunction(
							this.getText("查看评估详细情况"),
							"<a href=\"/entity/teaching/pebzzAssessRusts.action?courseid=${value}&type=2\">管理员</a>|<a href=\"/entity/teaching/pebzzAssessRusts.action?courseid=${value}&type=1\">学生</a>",
							"id");
		}
		if (!us.getRoleId().equals("3"))
		{
			this
					.getGridConfig()
					.addRenderFunction(
							this.getText("开始课程评估"),
							"<a href=\"/entity/teaching/pebzzManagerAssess_toAssess.action?courseid=${value}\">开始课程评估</a>",
							"id");
			this.getGridConfig().addRenderFunction(this.getText("查看评估结果"), "<a href=\"/entity/teaching/pebzzAssessRusts_toReviwes.action?ssoid="+us.getId()+"&type=2&courseid=${value}\">查看</a>","id");
		}
		this
				.getGridConfig()
				.addMenuScript(this.getText("返回"),
						"{window.location='/entity/teaching/prBzzTchOpenCourse.action'}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrBzzTchOpencourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prBzzTchOpenCourseDetail";

	}

	public PrBzzTchOpencourse getBean() {
		return (PrBzzTchOpencourse) super.superGetBean();
	}

	public void setBean(PrBzzTchOpencourse opencourse) {
		super.superSetBean(opencourse);
	}
	
	private int getDaysOfTowDiffDate(Date sdate,Date edate){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdate);
		Long begin =calendar.getTimeInMillis();
		calendar.setTime(edate);
		Long end =calendar.getTimeInMillis();
		System.out.println("begin:"+begin);
		System.out.println("end:"+end);
	   int betweenDays = (int) ( ( end- begin ) / ( 1000 * 60 * 60  ) );
	   return betweenDays;
	}

	public String IsCanEval(){
		DetachedCriteria criteria = DetachedCriteria.forClass(PeBzzBatch.class);
		criteria.add(Restrictions.eq("id", id));
		try {
			PeBzzBatch tempbatch =  prBzzTchOpenCourseService.getPeBzzBatch(criteria);
			
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(now);
			Date currentTime= formatter.parse(dateString);
			java.sql.Date currentTime_1 = new java.sql.Date(currentTime.getTime()); 
			Date start  = tempbatch.getEvalStartDate();
			Date end = tempbatch.getEvalEndDate();
			
			String startstring=formatter.format(start);
			Date startTime= formatter.parse(startstring);
			java.sql.Date startTime_1 = new java.sql.Date(startTime.getTime()); 
			 
			String endstring=formatter.format(end);
			Date endTime= formatter.parse(endstring);
			java.sql.Date endTime_1 = new java.sql.Date(endTime.getTime()); 
			//int temp1 = this.getDaysOfTowDiffDate(start, now);
			//int temp2 = this.getDaysOfTowDiffDate(now, end);
			//System.out.println("temp1:"+temp1);
			//System.out.println("temp2:"+temp2);
			if(startTime_1.compareTo(currentTime_1)<=0 && endTime_1.compareTo(currentTime_1)>=0){
				this.setFlag(true);
			}else{
				this.setFlag(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultinfo";
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria prdc = DetachedCriteria
				.forClass(PrBzzTchOpencourse.class);
		prdc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
		prdc.createCriteria("enumConstByFlagCourseType", "enumConstByFlagCourseType");
		prdc.createCriteria("peBzzBatch", "peBzzBatch");
		prdc.add(Restrictions.eq("peBzzBatch.id", this.getId()));
		return prdc;
	}

	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			String strId="";
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
					strId+="'"+ids[i]+"',";
				}
				strId=strId.substring(0,strId.length()-1);
				
				//先判断所删除课程是否已经有学员开始学习。如果没有才可以进行删除
				DetachedCriteria dc1 = DetachedCriteria
				.forClass(TrainingCourseStudent.class);
				dc1.createCriteria("prBzzTchOpencourse",
						"prBzzTchOpencourse");
				dc1.add(Restrictions.gt("percent", Double.parseDouble("0")));

				dc1.add(Restrictions.in("prBzzTchOpencourse.id", ids));
				
				try{
			
					List<TrainingCourseStudent> tlist1 = this.getGeneralService().getList(dc1);
					if(tlist1.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "该课程有学员开始学习,无法删除!");
						return map;
					}else{
					    String sql = "delete from stuttime where fk_open_course_id in ("+strId+")";
						int rett=this.getGeneralService().executeBySQL(sql);
					}
				}catch(EntityException e){
					e.printStackTrace();
				}
				
				//判断是否已经有课程评估
				DetachedCriteria dc2 = DetachedCriteria
				.forClass(PeBzzAssess.class);
				dc2.createCriteria("prBzzTchOpencourse",
						"prBzzTchOpencourse");
				dc2.add(Restrictions.in("prBzzTchOpencourse.id", ids));
				
				try{
			
					List<TrainingCourseStudent> tlist2 = this.getGeneralService().getList(dc2);
					if(tlist2.size()>0){
						map.clear();
						map.put("success", "false");
						map.put("info", "该课程已有课程评估内容,无法删除!");
						return map;
					}
				}catch(EntityException e){
					e.printStackTrace();
				}
				
				try {

					DetachedCriteria dc = DetachedCriteria
							.forClass(TrainingCourseStudent.class);
					dc.createCriteria("prBzzTchOpencourse",
							"prBzzTchOpencourse");

					dc.add(Restrictions.gt("percent", Double.parseDouble("0")));

					dc.add(Restrictions.in("prBzzTchOpencourse.id", ids));
					List<TrainingCourseStudent> tlist = this
							.getGeneralService().getList(dc);
					if (tlist.size() > 0) {
						for (int k = 0; k < tlist.size(); k++) {
							this.getGeneralService().delete(tlist.get(k));
						}
					}
					this.prBzzTchOpenCourseService.deleteByIds(idList);
					map.put("success", "true");
					map.put("info", "删除成功");
				} catch (RuntimeException e) {
					return super.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	public PrBzzTchOpenCourseService getPrBzzTchOpenCourseService() {
		return prBzzTchOpenCourseService;
	}

	public void setPrBzzTchOpenCourseService(
			PrBzzTchOpenCourseService prBzzTchOpenCourseService) {
		this.prBzzTchOpenCourseService = prBzzTchOpenCourseService;
	}
}
