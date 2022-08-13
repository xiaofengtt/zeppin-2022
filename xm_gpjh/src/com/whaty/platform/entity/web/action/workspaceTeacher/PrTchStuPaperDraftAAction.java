package com.whaty.platform.entity.web.action.workspaceTeacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
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

public class PrTchStuPaperDraftAAction extends MyBaseAction{

	private String id;
	private PrTchStuPaper paper;
	private List paperContentList;
	private File upload;
	private String uploadFileName;
	private String uploadFileContentType;
	private String note;
	private String paperId;
	private String savePath;
	private String operateresult;
	private List stuMsgList;
	private String backAction;
	
	public String getBackAction() {
		return backAction;
	}

	public void setBackAction(String backAction) {
		this.backAction = backAction;
	}

	public List getStuMsgList() {
		return stuMsgList;
	}

	public void setStuMsgList(List stuMsgList) {
		this.stuMsgList = stuMsgList;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("论文初稿批改"));
        this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("学生姓名"), "peStudent.trueName");
		this.getGridConfig().addColumn(this.getText("题目"), "prTchPaperTitle.title");
		this.getGridConfig().addColumn(this.getText("状态"), "enumConstByFlagDraftALastUpdate.name");
		
		this.getGridConfig().addRenderFunction(this.getText("操作"), "<a href=\"/entity/workspaceTeacher/prTchStuPaperDraftA_toPigai.action?id=${value}\">批改</a>", "id");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceTeacher/prTchStuPaperDraftA";
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
		dc.createCriteria("enumConstByFlagDraftALastUpdate", "enumConstByFlagDraftALastUpdate");
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
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperContent.class);
		dc.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		dc.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		dc.add(Restrictions.eq("enumConstByFlagPaperSection.code", "1"));
		dc.add(Restrictions.eq("enumConstByFlagActionUser.code", "1"));
		dc.add(Restrictions.eq("prTchStuPaper",this.getPaper()));
		dc.addOrder(Order.asc("actionDate"));
		try {
			this.setPaperContentList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		DetachedCriteria dc1 = DetachedCriteria.forClass(PrTchPaperContent.class);
		dc1.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		dc1.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		dc1.add(Restrictions.eq("enumConstByFlagPaperSection.code", "1"));
		dc1.add(Restrictions.eq("prTchStuPaper", this.getPaper()));
		dc1.add(Restrictions.eq("enumConstByFlagActionUser.code", "0"));
		dc1.addOrder(Order.asc("actionDate"));
		try {
			this.setStuMsgList(this.getGeneralService().getList(dc1));
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
		
		thePaper.setEnumConstByFlagDraftALastUpdate(this.getMyListService().getEnumConstByNamespaceCode("FlagDraftALastUpdate", "2"));
		
		pc.setActionDate(new Date());
		pc.setEnumConstByFlagActionUser(this.getMyListService().getEnumConstByNamespaceCode("FlagActionUser", "1"));
		pc.setEnumConstByFlagPaperSection(this.getMyListService().getEnumConstByNamespaceCode("FlagPaperSection", "1"));
		pc.setNote(this.getNote());
		pc.setPrTchStuPaper(thePaper);
		String tempFileName = "";
		if (this.getUpload() != null) {
			tempFileName = this.saveUpload();
			pc.setUrl(this.getSavePath() + "/" + tempFileName);
		}
		
		try {
			this.getGeneralService().save(pc);
			this.setOperateresult("初稿批改操作成功。");
		} catch (EntityException e) {
			e.printStackTrace();
			new File(ServletActionContext.getServletContext().getRealPath(this.getSavePath() + "/" + tempFileName)).delete();
			this.setOperateresult("批改操作失败。");
		}
		this.setBackAction("/entity/workspaceTeacher/prTchStuPaperDraftA.action");
		return "operateResult";
	}
	
	private String saveUpload() {
		
		int index = 0;
		String tempName = this.getUploadFileName();
		tempName = tempName.substring(0, tempName.lastIndexOf(".")) + "_教师" + tempName.substring(tempName.lastIndexOf("."));
		String temptempName = tempName;
		if (tempName == null) {
			return null;
		}
		FileOutputStream fos;
		FileInputStream fis;
		while (true) {
			if(new File(ServletActionContext.getServletContext().getRealPath(this.getSavePath() + "/" + temptempName)).isFile()) {
				int point = (tempName.lastIndexOf(".")>0 ? tempName.lastIndexOf("."):tempName.length());
				temptempName = tempName.substring(0, point)+"["+String.valueOf(index)+"]"+tempName.substring(point);
				index++;
				continue;
			}
			tempName = temptempName;
			break;
		}
		try {
			String file = ServletActionContext.getServletContext().getRealPath(this.getSavePath() + "/" + tempName);
			fos = new FileOutputStream(file);
			fis = new FileInputStream(this.getUpload());
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer,0,len);
			}
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tempName;
	
	}
	
	public String toDownloadTeacherPiGai() {
		PrTchStuPaper thePaper = new PrTchStuPaper();
		try {
			thePaper = (PrTchStuPaper) this.getGeneralService().getById(this.getPaperId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperContent.class);
		dc.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		dc.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		dc.add(Restrictions.eq("enumConstByFlagPaperSection.code", "1"));
		dc.add(Restrictions.eq("enumConstByFlagActionUser.code", "1"));
		dc.add(Restrictions.eq("prTchStuPaper",thePaper));
		dc.add(Restrictions.isNotNull("url"));
		dc.addOrder(Order.asc("actionDate"));
		try {
			this.setPaperContentList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toDownloadTeacherPiGai";
	}
	
	public String toDownloadDraftA() {
		PrTchStuPaper thePaper = new PrTchStuPaper();
		
		try {
			thePaper = (PrTchStuPaper) this.getGeneralService().getById(this.getPaperId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrTchPaperContent.class);
		dc.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		dc.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		dc.add(Restrictions.eq("enumConstByFlagPaperSection.code", "1"));
		dc.add(Restrictions.eq("enumConstByFlagActionUser.code", "0"));
		dc.add(Restrictions.eq("prTchStuPaper",thePaper));
		dc.add(Restrictions.isNotNull("url"));
		dc.addOrder(Order.asc("actionDate"));
		try {
			this.setPaperContentList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toDownloadDraftA";
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

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

}
