package com.whaty.platform.entity.web.action.teaching.paper;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PrTchPaperContent;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PaperProcessInfoAction extends MyBaseAction {
	private String section;
	private String type;
	private String user;


	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		String theTitle = "论文进展详情:";
		if(this.getSection()!=null&&this.getSection().equals("kaiti")){
			theTitle +="开题报告";
		}
		if (this.getSection()!=null&&this.getSection().equals("chugao")){
			theTitle +="论文初稿";
		}
		if (this.getSection()!=null&&this.getSection().equals("zhonggao")){
			theTitle +="论文终稿";
		}
		if(this.getType()!=null&&this.getType().equals("tijiao")){
			theTitle +="已提交";
		}
		if(this.getType()!=null&&this.getType().equals("liuyan")){
			theTitle +="已留言";
		}
		this.getGridConfig().setTitle(this.getText(theTitle));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "prTchStuPaper.prTchPaperTitle.peSemester.name");
		this.getGridConfig().addColumn(this.getText("指导教师"), "prTchStuPaper.prTchPaperTitle.peTeacher.trueName");
		this.getGridConfig().addColumn(this.getText("学号"), "prTchStuPaper.peStudent.regNo");
		this.getGridConfig().addColumn(this.getText("学生姓名"), "prTchStuPaper.peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("论文题目"), "prTchStuPaper.prTchPaperTitle.title");
		this.getGridConfig().addColumn(this.getText("所属论文阶段"), "enumConstByFlagPaperSection.name");
		this.getGridConfig().addColumn(this.getText("操作人"), "enumConstByFlagActionUser.name");
		this.getGridConfig().addColumn(this.getText("操作日期"), "actionDate");
		this.getGridConfig().addColumn(this.getText("留言"), "note");
		this.getGridConfig().addColumn(this.getText("附件地址"), "url",false,false,false,"");	
//		this.getGridConfig().addRenderFunction(this.getText("附件地址"),
//				"<a href=\"${value}\" target=\"_blank\">${value}</a> ", "url");
		this.getGridConfig().addRenderScript(this.getText("附件"), "{if(record.data['url']!='') return '<a href=' + record.data['url'] + ' target=_blank><u><font color=blue>查看附件</font></u></a>'; else return '无附件';}","");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchPaperContent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/teaching/paperProcessInfo";
	}

	public void setBean(PrTchPaperContent instance) {
		super.superSetBean(instance);
	}

	public PrTchPaperContent getBean() {
		return (PrTchPaperContent) super.superGetBean();
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperContent.class);
		DetachedCriteria dcPrTchStuPaper = dc.createCriteria("prTchStuPaper", "prTchStuPaper");
		DetachedCriteria dcFlagPaperSection = dc.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		DetachedCriteria dcFlagActionUser = dc.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		DetachedCriteria dcPeStudent = dcPrTchStuPaper.createCriteria("peStudent", "peStudent");
		DetachedCriteria dcPrTchPaperTitle = dcPrTchStuPaper.createCriteria("prTchPaperTitle", "prTchPaperTitle");
		DetachedCriteria dcPeTeacher = dcPrTchPaperTitle.createCriteria("peTeacher", "peTeacher");
		DetachedCriteria dcPeSemester = dcPrTchPaperTitle.createCriteria("peSemester", "peSemester");
		DetachedCriteria dcPeGrade = dcPeStudent.createCriteria("peGrade", "peGrade");
		DetachedCriteria dcPeMajor = dcPeStudent.createCriteria("peMajor", "peMajor");
		DetachedCriteria dcPeSite = dcPeStudent.createCriteria("peSite", "peSite");
		DetachedCriteria dcPeEdutype = dcPeStudent.createCriteria("peEdutype", "peEdutype");
		if(this.getUser()!=null&&this.getUser().equals("teacher")){
			dcFlagActionUser.add(Restrictions.eq("code", "1"));
		} else {
			dcFlagActionUser.add(Restrictions.eq("code", "0"));
		}
		if(this.getType()!=null&&this.getType().equals("liuyan")){
			dc.add(Restrictions.isNotNull("note"));
		}
		if(this.getType()!=null&&this.getType().equals("tijiao")){
			dc.add(Restrictions.isNotNull("url"));
		}
		if(this.getSection()!=null&&this.getSection().equals("kaiti")){
			dcFlagPaperSection.add(Restrictions.eq("code", "0"));
		}
		if(this.getSection()!=null&&this.getSection().equals("chugao")){
			dcFlagPaperSection.add(Restrictions.eq("code", "1"));
		}
		if(this.getSection()!=null&&this.getSection().equals("zhonggao")){
			dcFlagPaperSection.add(Restrictions.eq("code", "2"));
		}
		return dc;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
