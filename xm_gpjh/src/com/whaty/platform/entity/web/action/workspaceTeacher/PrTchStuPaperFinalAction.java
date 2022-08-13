package com.whaty.platform.entity.web.action.workspaceTeacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.bean.PrTchPaperContent;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PrTchStuPaperFinalAction extends MyBaseAction {
	
	private String id;
	private PrTchStuPaper paper;
	private List paperContentList;
	private String note;
	private String paperId;
	private String operateresult;
	private List stuMsgList;
	private String paperFinalScore;
	private PrTchPaperContent prTchPaperContent;
	private String backAction;
	private List<PrTchStuPaper> listPaper; //历史论文记录
	
	public String getBackAction() {
		return backAction;
	}

	public void setBackAction(String backAction) {
		this.backAction = backAction;
	}

	public PrTchPaperContent getPrTchPaperContent() {
		return prTchPaperContent;
	}

	public void setPrTchPaperContent(PrTchPaperContent prTchPaperContent) {
		this.prTchPaperContent = prTchPaperContent;
	}

	public List getStuMsgList() {
		return stuMsgList;
	}

	public void setStuMsgList(List stuMsgList) {
		this.stuMsgList = stuMsgList;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public PrTchStuPaper getPaper() {
		return paper;
	}

	public void setPaper(PrTchStuPaper paper) {
		this.paper = paper;
	}

	public List getPaperContentList() {
		return paperContentList;
	}

	public void setPaperContentList(List paperContentList) {
		this.paperContentList = paperContentList;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getPaperFinalScore() {
		return paperFinalScore;
	}

	public void setPaperFinalScore(String paperFinalScore) {
		this.paperFinalScore = paperFinalScore;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("论文终稿批改"));
        this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学生姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("题目"), "prTchPaperTitle.title");
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFlagFinalLastUpdate.name");
		
		this.getGridConfig().addRenderFunction(this.getText("操作"), "<a href=\"/entity/workspaceTeacher/prTchStuPaperFinal_toPigai.action?id=${value}\">批改</a>", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceTeacher/prTchStuPaperFinal";
	}

	public void setBean(PrTchStuPaper instance) {
		super.superSetBean(instance);
	}
	
	public PrTchStuPaper getBean(){
		return (PrTchStuPaper) super.superGetBean();
	}
	
	@Override
	public DetachedCriteria initDetachedCriteria() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria peTeacherDC = DetachedCriteria.forClass(PeTeacher.class);
		peTeacherDC.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
		List teacherList = new ArrayList();
		try {
			teacherList = this.getGeneralService().getList(peTeacherDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeTeacher peTeacher = (PeTeacher) teacherList.get(0);
		
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchStuPaper.class);
		dc.createCriteria("peStudent","peStudent");
		dc.createCriteria("prTchPaperTitle", "prTchPaperTitle").createAlias("peSemester", "peSemester").createAlias("peTeacher", "peTeacher");
		dc.createCriteria("enumConstByFlagFinalLastUpdate", "enumConstByFlagFinalLastUpdate");
		dc.add(Restrictions.eq("peSemester.flagActive", "1"));
		dc.add(Restrictions.eq("prTchPaperTitle.peTeacher", peTeacher));
		return dc;
	}
	
	public String toPigai() {
		
		try {
			PrTchStuPaper prTchStuPaper = (PrTchStuPaper) this.getGeneralService().getById(this.getId());
			this.setPaper(prTchStuPaper);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		//拿到学生的留言
		DetachedCriteria dc1 = DetachedCriteria.forClass(PrTchPaperContent.class);
		dc1.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		dc1.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		dc1.add(Restrictions.eq("enumConstByFlagPaperSection.code", "2"));
		dc1.add(Restrictions.eq("prTchStuPaper", this.getPaper()));
		dc1.add(Restrictions.eq("enumConstByFlagActionUser.code", "0"));
		dc1.addOrder(Order.asc("actionDate"));
		try {
			this.setStuMsgList(this.getGeneralService().getList(dc1));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		//拿到教师的评语记录
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperContent.class);
		dc.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		dc.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		dc.add(Restrictions.eq("enumConstByFlagPaperSection.code", "2"));
		dc.add(Restrictions.eq("prTchStuPaper", this.getPaper()));
		dc.add(Restrictions.eq("enumConstByFlagActionUser.code", "1"));
		List pcList = new ArrayList();
		try {
			pcList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (pcList.size() > 0) {
			this.setPrTchPaperContent((PrTchPaperContent) pcList.get(0));
		}
		DetachedCriteria dca = DetachedCriteria.forClass(PrTchStuPaper.class);
		dca.add(Restrictions.eq("peStudent", this.getPaper().getPeStudent()));
		DetachedCriteria dcTitle = dca.createCriteria("prTchPaperTitle","prTchPaperTitle");
		dcTitle.createCriteria("peSemester", "peSemester").add(Restrictions.ne("flagActive", "1"));
		List<PrTchStuPaper> alist = null;
		try {
			alist = this.getGeneralService().getList(dca);
			this.setListPaper(alist);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toPigai";
	}

	public String piGai() {
		
		PrTchPaperContent pc = new PrTchPaperContent();
		PrTchStuPaper thePaper = new PrTchStuPaper();
		try {
			thePaper = (PrTchStuPaper) this.getGeneralService().getById(this.getPaperId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
//		如果多次评分，需要load出paperContent；
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperContent.class);
		dc.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		dc.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		dc.add(Restrictions.eq("enumConstByFlagPaperSection.code", "2"));
		dc.add(Restrictions.eq("enumConstByFlagActionUser.code", "1"));
		dc.add(Restrictions.eq("prTchStuPaper", thePaper));
		List pcList = new ArrayList();
		try {
			pcList = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		if (pcList.size() > 0) {
			pc = (PrTchPaperContent) pcList.get(0);
		}
		
		thePaper.setEnumConstByFlagFinalLastUpdate(this.getMyListService().getEnumConstByNamespaceCode("FlagFinalLastUpdate", "2"));
		thePaper.setFinalScore(this.getPaper().getFinalScore());
		pc.setActionDate(new Date());
		pc.setEnumConstByFlagActionUser(this.getMyListService().getEnumConstByNamespaceCode("FlagActionUser", "1"));
		pc.setEnumConstByFlagPaperSection(this.getMyListService().getEnumConstByNamespaceCode("FlagPaperSection", "2"));
		pc.setNote(this.getNote());
		pc.setPrTchStuPaper(thePaper);
		
		
		try {
			this.getGeneralService().save(pc);
			this.getGeneralService().save(thePaper);
			this.setOperateresult("终稿操作成功。");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setOperateresult("终稿操作失败。");
		}
		this.setBackAction("/entity/workspaceTeacher/prTchStuPaperFinal.action");
		
		return "operateResult";
	}
	
	public String toDownloadFinal() {
		PrTchStuPaper thePaper = new PrTchStuPaper();
		
		try {
			thePaper = (PrTchStuPaper) this.getGeneralService().getById(this.getPaperId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperContent.class);
		dc.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		dc.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		dc.add(Restrictions.eq("enumConstByFlagPaperSection.code", "2"));
		dc.add(Restrictions.eq("enumConstByFlagActionUser.code", "0"));
		dc.add(Restrictions.eq("prTchStuPaper",thePaper));
		dc.add(Restrictions.isNotNull("url"));
		dc.addOrder(Order.asc("actionDate"));
		try {
			this.setPaperContentList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toDownloadFinal";
	}

	public List<PrTchStuPaper> getListPaper() {
		return listPaper;
	}

	public void setListPaper(List<PrTchStuPaper> listPaper) {
		this.listPaper = listPaper;
	}

}
