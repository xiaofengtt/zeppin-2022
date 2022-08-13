package com.whaty.platform.entity.web.action.workspaceStudent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrTchPaperTitle;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

public class PrTchStuPaperTitleSelectManageAction extends MyBaseAction {

	@Override
	public void initGrid() {

		this.getGridConfig().setTitle(this.getText("论文选题"));
		this.getGridConfig().setCapability(false, false, false);
		
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学期"), "peSemester.name");
		this.getGridConfig().addColumn(this.getText("教师"), "peTeacher.trueName");
		this.getGridConfig().addColumn(this.getText("题目"), "title");
		this.getGridConfig().addColumn(this.getText("题目备注"), "titleMemo");
		this.getGridConfig().addColumn(this.getText("已选此题目人数"), "titleAlreadySelect");
		this.getGridConfig().addColumn(this.getText("题目最大学生人数"),"stuCountLimit");
		this.getGridConfig().addColumn(this.getText("是否为当前选题"), "isCurrentTitle", false, false, true, "TextField", false, 30);

		this.getGridConfig().addMenuFunction("选为我的论文题目", "/entity/workspaceStudent/prTchStuPaperTitleSelect_select.action", true, true);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchPaperTitle.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/prTchStuPaperTitleSelect";
	}

	public String select() {
		Map map = new HashMap();
		Date today = new Date();
		DetachedCriteria semesterdc = DetachedCriteria.forClass(PeSemester.class);
		semesterdc.add(Restrictions.eq("flagActive", "1"));
		PeSemester semester=new PeSemester();
		try {
			List semesterList=this.getGeneralService().getList(semesterdc);
			if(semesterList!=null&&semesterList.size()!=0){
				semester=(PeSemester)semesterList.get(0);
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if(today.getTime() > (semester.getPaperTitleEndDate()).getTime()){
			map.put("success", "false");
			map.put("info", "已过修改选题时间");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		//选择的题目id
		String id = this.getIds().split(",")[0];
		PrTchPaperTitle prTchStuPaperTitle = null;
		try {
			prTchStuPaperTitle = (PrTchPaperTitle) this.getGeneralService().getById(id);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		//如果论文表中该题目人数已满则选题不成功
		Long stuCountLimit = prTchStuPaperTitle.getStuCountLimit();
		DetachedCriteria dcPaperTitleCount = DetachedCriteria.forClass(PrTchStuPaper.class);
		dcPaperTitleCount.add(Restrictions.eq("prTchPaperTitle", prTchStuPaperTitle));
		List paperTitleCountList = new ArrayList();
		try {
			paperTitleCountList = this.getGeneralService().getList(dcPaperTitleCount);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (paperTitleCountList.size() == stuCountLimit) {
			map.put("success", "false");
			map.put("info", "该题目选题人数已满。");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		
		//得到当前登录学生
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria dcPeStudent = DetachedCriteria.forClass(PeStudent.class);
		dcPeStudent.add(Restrictions.eq("ssoUser", ssoUser));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(dcPeStudent);
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeStudent peStudent = (PeStudent) peStudentList.get(0);
		
		
		DetachedCriteria dcPrTchStuPaper = DetachedCriteria.forClass(PrTchStuPaper.class);
		dcPrTchStuPaper.add(Restrictions.eq("peStudent", peStudent));
		List stuPaperList = new ArrayList();
		try {
			stuPaperList = this.getGeneralService().getList(dcPrTchStuPaper);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		//论文表中有这个学生的记录
		if (stuPaperList.size() > 0) {
			for (int i = 0; i < stuPaperList.size(); i++) {
				PrTchStuPaper prTchStuPaper = (PrTchStuPaper) stuPaperList.get(i);
			
				//本学期已选题
				if (prTchStuPaper.getPrTchPaperTitle().getPeSemester().getFlagActive().equals("1")) {
					prTchStuPaper.setPrTchPaperTitle(prTchStuPaperTitle);
					try {
						this.getGeneralService().save(prTchStuPaper);
						map.put("success", "true");
						map.put("info", "设置成功");
					} catch (EntityException e) {
						e.printStackTrace();
						map.put("success", "false");
						map.put("info", "操作失败");
					}
					this.setJsonString(JsonUtil.toJSONString(map));
					return json();
				}
			}
		}
		
		//重修论文的学生与没有选过题的学生都会向 paper表中插入一条数据
		PrTchStuPaper prTchStuPaper = new PrTchStuPaper();
		prTchStuPaper.setPeStudent(peStudent);
		prTchStuPaper.setPrTchPaperTitle(prTchStuPaperTitle);
		EnumConst enumConstByFlagTitleAdmission = this.getMyListService().getEnumConstByNamespaceCode("FlagTitleAdmission", "0");
		prTchStuPaper.setEnumConstByFlagTitleAdmission(enumConstByFlagTitleAdmission);
		EnumConst enumConstByFlagSyllabusLastUpdate = this.getMyListService().getEnumConstByNamespaceCode("FlagSyllabusLastUpdate", "0");
		prTchStuPaper.setEnumConstByFlagSyllabusLastUpdate(enumConstByFlagSyllabusLastUpdate);
		EnumConst enumConstByFlagDraftALastUpdate = this.getMyListService().getEnumConstByNamespaceCode("FlagDraftALastUpdate", "0");
		prTchStuPaper.setEnumConstByFlagDraftALastUpdate(enumConstByFlagDraftALastUpdate);
		EnumConst enumConstByFlagFinalLastUpdate = this.getMyListService().getEnumConstByNamespaceCode("FlagFinalLastUpdate", "0");
		prTchStuPaper.setEnumConstByFlagFinalLastUpdate(enumConstByFlagFinalLastUpdate);
		EnumConst enumConstByFlagPaperRejoin = this.getMyListService().getEnumConstByNamespaceCode("FlagPaperRejoin", "0");
		prTchStuPaper.setEnumConstByFlagPaperRejoin(enumConstByFlagPaperRejoin);
		try {
			this.getGeneralService().save(prTchStuPaper);
			map.put("success", "true");
			map.put("info", "设置成功");
		} catch (EntityException e) {
			e.printStackTrace();
			map.put("success", "false");
			map.put("info", "操作失败");
		}
		
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	@Override
	public Page list() {
		Page page = null;
		List prTchPaperTitleList = new ArrayList();
		
		//得到当前登录学生
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		
		//得到当前学生的专业
		DetachedCriteria dcStudent = DetachedCriteria.forClass(PeStudent.class);
		dcStudent.add(Restrictions.eq("ssoUser", ssoUser));
		List studentList = new ArrayList();
		try {
			studentList = this.getGeneralService().getList(dcStudent);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeStudent peStudent = (PeStudent) studentList.get(0);
		PeMajor peMajor = peStudent.getPeMajor();
		
		DetachedCriteria dcPrTchPaperTitle = DetachedCriteria.forClass(PrTchPaperTitle.class);
		dcPrTchPaperTitle.createCriteria("peTeacher", "peTeacher");
		
		dcPrTchPaperTitle.add(Restrictions.eq("peTeacher.peMajor", peMajor));
		dcPrTchPaperTitle.createCriteria("peSemester", "peSemester");
		dcPrTchPaperTitle.add(Restrictions.eq("peSemester.flagActive", "1"));
		try {
			page = this.getGeneralService().getByPage(dcPrTchPaperTitle,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		//学生选题的题目列表
		prTchPaperTitleList = page.getItems();
		
		//查询paper表有没有当前学生的记录
		DetachedCriteria dcPrTchStuPaper = DetachedCriteria.forClass(PrTchStuPaper.class);
		dcPrTchStuPaper.add(Restrictions.eq("peStudent", peStudent));
		dcPrTchStuPaper.add(Restrictions.in("prTchPaperTitle", prTchPaperTitleList));
		//paper表中的记录
		List prTchStuPaperList = new ArrayList();
		try {
			prTchStuPaperList = this.getGeneralService().getList(dcPrTchStuPaper);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PrTchStuPaper prTchStuPaper = null;
		if (prTchStuPaperList.size()>0) {
			//已选题
			prTchStuPaper = (PrTchStuPaper) prTchStuPaperList.get(0);
		}
		for (Iterator iter = prTchPaperTitleList.iterator(); iter.hasNext();) {
			PrTchPaperTitle stuPaperTitle = (PrTchPaperTitle) iter.next();
			DetachedCriteria dcAlreadySelectNum = DetachedCriteria.forClass(PrTchStuPaper.class);
			dcAlreadySelectNum.add(Restrictions.eq("prTchPaperTitle", stuPaperTitle));
			long alreadySelectNum = 0l;
			try {
				List alreadySelectList = this.getGeneralService().getList(dcAlreadySelectNum);
				alreadySelectNum = alreadySelectList.size();
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if (prTchStuPaper != null && prTchStuPaper.getPrTchPaperTitle().equals(stuPaperTitle)) {
				stuPaperTitle.setIsCurrentTitle("是");
			} else {
				stuPaperTitle.setIsCurrentTitle("否");
			}
			stuPaperTitle.setTitleAlreadySelect(alreadySelectNum);
		}
		
		return page;
	}
	
}
