package com.whaty.platform.entity.web.action.workspaceStudent;

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
import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrTchPaperContent;
import com.whaty.platform.entity.bean.PrTchStuPaper;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class StudentDraftAAction extends MyBaseAction {

	private List paperContentList;
	private PrTchStuPaper paper;
	private File upload;
	private String uploadFileName;
	private String uploadFileContentType;
	private String savePath;
	private String operateresult;
	private List stuMsgList;
	private String paperId;
	private String note;
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public List getPaperContentList() {
		return paperContentList;
	}

	public void setPaperContentList(List paperContentList) {
		this.paperContentList = paperContentList;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public List getStuMsgList() {
		return stuMsgList;
	}

	public void setStuMsgList(List stuMsgList) {
		this.stuMsgList = stuMsgList;
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
	
	public PrTchStuPaper getPaper() {
		return paper;
	}

	public void setPaper(PrTchStuPaper paper) {
		this.paper = paper;
	}

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrTchStuPaper.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/studentDraftA";
	}
	
	public void setBean(PrTchStuPaper instance) {
		super.superSetBean(instance);
	}
	
	public PrTchStuPaper getBean(){
		return (PrTchStuPaper) super.superGetBean();
	}
	
	public String toUpload() {
		
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.add(Restrictions.eq("ssoUser", ssoUser));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeStudent peStudent = (PeStudent) peStudentList.get(0);
		
		DetachedCriteria paperDC = DetachedCriteria.forClass(PrTchStuPaper.class);
		paperDC.createCriteria("prTchPaperTitle", "prTchPaperTitle").createAlias("peSemester", "peSemester");
		paperDC.add(Restrictions.eq("peStudent", peStudent));
		paperDC.add(Restrictions.eq("peSemester.flagActive", "1"));
		List paperList = new ArrayList();
		try {
			paperList = this.getGeneralService().getList(paperDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setPaper((PrTchStuPaper) paperList.get(0));
		
		Date today = new Date();
		DetachedCriteria peSemesterDC = DetachedCriteria.forClass(PeSemester.class);
		peSemesterDC.add(Restrictions.eq("flagActive", "1"));
		try {
			PeSemester peSemester =  (PeSemester) this.getGeneralService().getList(peSemesterDC).get(0);
			if (today.getTime() < peSemester.getPaperStartDate().getTime() || today.getTime() > peSemester.getPaperFinalEndDate().getTime()) {
				this.setOperateresult("对不起，现在不是论文写作时间。");
				return "operateResult";
			}
			if (this.getPaper().getFinalScore() != null) {
				this.setOperateresult("论文成绩已经评出，不能操作。");
				return "operateResult";
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		
		//教师留言
		DetachedCriteria paperContentDC = DetachedCriteria.forClass(PrTchPaperContent.class);
		paperContentDC.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		paperContentDC.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		paperContentDC.add(Restrictions.eq("prTchStuPaper", this.getPaper()));
		paperContentDC.add(Restrictions.eq("enumConstByFlagPaperSection.code", "1"));
		paperContentDC.add(Restrictions.eq("enumConstByFlagActionUser.code", "1"));
		paperContentDC.addOrder(Order.asc("actionDate"));
		try {
			this.setPaperContentList(this.getGeneralService().getList(paperContentDC));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		//学生留言
		DetachedCriteria stuMsgDC = DetachedCriteria.forClass(PrTchPaperContent.class);
		stuMsgDC.createCriteria("enumConstByFlagPaperSection", "enumConstByFlagPaperSection");
		stuMsgDC.createCriteria("enumConstByFlagActionUser", "enumConstByFlagActionUser");
		stuMsgDC.add(Restrictions.eq("prTchStuPaper", this.getPaper()));
		stuMsgDC.add(Restrictions.eq("enumConstByFlagPaperSection.code", "1"));
		stuMsgDC.add(Restrictions.eq("enumConstByFlagActionUser.code", "0"));
		stuMsgDC.addOrder(Order.asc("actionDate"));
		try {
			this.setStuMsgList(this.getGeneralService().getList(stuMsgDC));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toUpload";
	}
	
	public String upload() {
		
		PrTchPaperContent pc = new PrTchPaperContent();
		PrTchStuPaper thePaper = new PrTchStuPaper();
		try {
			thePaper = (PrTchStuPaper) this.getGeneralService().getById(this.getPaperId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		thePaper.setEnumConstByFlagDraftALastUpdate(this.getMyListService().getEnumConstByNamespaceCode("FlagDraftALastUpdate", "1"));
		
		pc.setActionDate(new Date());
		pc.setEnumConstByFlagActionUser(this.getMyListService().getEnumConstByNamespaceCode("FlagActionUser", "0"));
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
			this.setOperateresult("初稿操作成功。");
			this.setTogo("/entity/workspaceStudent/student_toPaperStepSelect.action");
		} catch (EntityException e) {
			e.printStackTrace();
			new File(ServletActionContext.getServletContext().getRealPath(this.getSavePath() + "/" + tempFileName)).delete();
			this.setOperateresult("初稿操作失败。");
		}
		
		return "operateResult";
	}
	
	private String saveUpload() {
		
		int index = 0;
		String tempName = this.getUploadFileName();
		tempName = tempName.substring(0, tempName.lastIndexOf(".")) + "_学生" + tempName.substring(tempName.lastIndexOf("."));
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

}
