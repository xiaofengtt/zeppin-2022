package com.whaty.platform.entity.web.action.studentStatus.graduate;

import java.io.File;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.service.studentStatas.PeStudentSerive;
import com.whaty.platform.entity.web.action.studentStatus.stuInfo.PeStudentInfoAction;

public class PeGraduatedStudentAction extends PeStudentInfoAction {
	private File upload;
	public String certificateNoBatch(){
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getPeStudentService().saveCertificateNo(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false,true);
		this.getGridConfig().setTitle(this.getText("已毕业学生列表"));
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("证件号码"), "prStudentInfo.cardNo");
		this.getGridConfig().addColumn(this.getText("移动电话"), "prStudentInfo.mobilephone");
		this.getGridConfig().addColumn(this.getText("学习中心"), "peSite.name");
		this.getGridConfig().addColumn(this.getText("年级"), "peGrade.name");
		this.getGridConfig().addColumn(this.getText("专业"), "peMajor.name");
		this.getGridConfig().addColumn(this.getText("层次"), "peEdutype.name");
		this.getGridConfig().addColumn(this.getText("入学时间"), "recruitDate");
		this.getGridConfig().addColumn(this.getText("毕业时间"), "graduationDate");
		this.getGridConfig().addColumn(this.getText("毕业证编号"), "graduationCertificateNo");
		this.getGridConfig().addColumn(this.getText("获得学位时间"), "degreeDate");
		this.getGridConfig().addColumn(this.getText("学位证编号"),"degreeCertificateNo");
		this
		.getGridConfig()
		.addMenuScript("导入毕业证学位证编号",
				"{window.open('/entity/manager/studentStatus/certificateNo_batch.jsp')}");
		
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeStudent.class);
		dc.createAlias("prStudentInfo", "prStudentInfo", DetachedCriteria.LEFT_JOIN)
			.createAlias("peSite", "peSite")
			.createAlias("peMajor", "peMajor")
			.createAlias("peEdutype","peEdutype")
			.createAlias("peGrade", "peGrade")
			.createAlias("enumConstByFlagStudentStatus", "enumConstByFlagStudentStatus")
			.add(Restrictions.eq("enumConstByFlagStudentStatus.code", "5"));
		return dc;
	}
	@Override
	public void setServletPath() {
		this.servletPath = "/entity/studentStatus/peGraduatedStudent";
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
}
