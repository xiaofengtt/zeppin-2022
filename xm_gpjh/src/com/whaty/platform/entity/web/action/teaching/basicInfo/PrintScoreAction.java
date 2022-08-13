package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrExamBooking;
import com.whaty.platform.entity.bean.PrTchStuElective;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PrintScoreAction extends MyBaseAction<PeStudent> {

	private List scoreReportList;
	public List getScoreReportList() {
		return scoreReportList;
	}
	public void setScoreReportList(List scoreReportList) {
		this.scoreReportList = scoreReportList;
	}


	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
		this.getGridConfig().setTitle(this.getText("学生成绩单"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("scoreReport.ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
//		this.getGridConfig().addRenderFunction(this.getText("scorePracticeView.takenclass"), "<a href=\"/entity/exam/scoreReport_getScoreByRegNo.action?regNo=${value}\" target=\"_blank\">已修课程</a>", "regNo");
//		this.getGridConfig().addMenuScript(this.getText("test.back"), "{window.history.back();}");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/teaching/printScore_printScoreReports.action")){
		this.getGridConfig().addMenuScript(this.getText("打印成绩单"), 
				"{var m = grid.getSelections();  "+
					"if(m.length > 0){	         "+
					"	var jsonData = '';       "+
					"	for(var i = 0, len = m.length; i < len; i++){"+
					"		var ss =  m[i].get('id');"+
					"		if(i==0)	jsonData = jsonData + ss ;"+
					"		else	jsonData = jsonData + ',' + ss;"+
					"	}                        "+
					"	document.getElementById('user-defined-content').style.display='none'; "+
					"	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='/entity/teaching/printScore_printScoreReports.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ></form>\";"+
					"	document.formx1.submit();"+
					"	document.getElementById('user-defined-content').innerHTML=\"\";"+
					"} else {                    "+
//					"	Ext.MessageBox.alert('<s:text name=\"test.error\"/>','<s:text name=\"test.pleaseSelectAtLeastOneItem\"/>');"+
					"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
		"}}                         ");
		}
	}
	
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createCriteria("peGrade","peGrade");
		dc.createCriteria("peEdutype","peEdutype");
		dc.createCriteria("peMajor","peMajor");
		dc.createCriteria("peSite","peSite");
		return dc;
	}


	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		super.entityClass = PeStudent.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		servletPath = "/entity/teaching/printScore";
	}
	public String printScoreReports(){
		List<scoreReports> tempList = new ArrayList();
		scoreReports instance = null;
		
		
		String[] ids = super.getIds().split(",");
		for(int i = 0; i < ids.length; i++){
			instance = new scoreReports();
			String stuId = ids[i];
			
			//String tmp_totalCredit = queryTotalCredit(stuId);
			
//			double totalCredit = 0.0;
//			try {
//				totalCredit = Double.parseDouble(tmp_totalCredit);
//			} catch (Exception e) {
//			}
//			
//			instance.setTotalCredit(tmp_totalCredit);
			instance.setElectiveList(queryCourseList(stuId));
			try {
				instance.setPeStudent(this.getGeneralService().getById(stuId));
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//instance.setAvgScore(queryAvgScore(stuId, totalCredit));
			
			tempList.add(instance);
		}
		this.setScoreReportList(tempList);
		
		Date currentDate = new Date(System.currentTimeMillis());
//		this.setCurrentDate(currentDate);
		
		return "printscorebatch";
	}
	
	private List queryCourseList(String stuId){
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuElective.class);
		dc.add(Restrictions.ge("scoreTotal", Double.parseDouble(this.getMyListService().getSysValueByName("creditMustScore"))));
		dc.createCriteria("prTchOpencourse", "prTchOpencourse").createCriteria("peTchCourse","peTchCourse");
		dc.createCriteria("peStudent", "peStudent").add(Restrictions.eq("id", stuId));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 取得学期
	 * @param id
	 * @return
	 */
	public String getPeSemesterName(String id){
		DetachedCriteria dcBooking = DetachedCriteria.forClass(PrExamBooking.class);
		dcBooking.createCriteria("prTchStuElective","prTchStuElective").add(Restrictions.eq("id", id));
		dcBooking.createAlias("peSemester", "peSemester");
		List<PrExamBooking> books = null;
		try {
			books = this.getGeneralService().getList(dcBooking);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String name = "";
		if(books!=null&&books.size()>0){
			long a = -1;
			for (PrExamBooking prExamBooking : books) {
				if(prExamBooking.getPeSemester().getSerialNumber().longValue()>a){
					a = prExamBooking.getPeSemester().getSerialNumber().longValue();
					name = prExamBooking.getPeSemester().getName();
				}
			}
		}else {
			PrTchStuElective elective = (PrTchStuElective)this.getMyListService().getById(PrTchStuElective.class, id);
			name = elective.getPrTchOpencourse().getPeSemester().getName();
		}
		return name;
	}
	
	public String getSco(double number){
		Double a = number;
		return new DecimalFormat("0.0").format(number);
	}
	/**
	 * 用于批量打印成绩单
	 */
	private class scoreReports{
		private PeStudent peStudent;
		private List electiveList;
		private double avgScore;
		private String totalCredit;
		public double getAvgScore() {
			return avgScore;
		}
		public void setAvgScore(double avgScore) {
			this.avgScore = avgScore;
		}
		public String getTotalCredit() {
			return totalCredit;
		}
		public void setTotalCredit(String totalCredit) {
			this.totalCredit = totalCredit;
		}
		public PeStudent getPeStudent() {
			return peStudent;
		}
		public void setPeStudent(PeStudent peStudent) {
			this.peStudent = peStudent;
		}
		public List getElectiveList() {
			return electiveList;
		}
		public void setElectiveList(List electiveList) {
			this.electiveList = electiveList;
		}
		
	}

}
