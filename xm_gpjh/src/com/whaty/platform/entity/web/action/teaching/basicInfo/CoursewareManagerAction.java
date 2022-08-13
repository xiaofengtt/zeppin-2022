package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.File;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeTchCourseware;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.basicInfo.PeTchCourseService;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class CoursewareManagerAction extends MyBaseAction<PeTchCourseware> {
	private File upload;
	private PeTchCourseService peTchCourseService;
	

	/**
	 * 批量添加课件
	 * @return
	 */
	public String coursewareBatch(){
		this.setTogo("back");
		int count = 0;
		try {
			count = this.getPeTchCourseService().save_uploadCourseware(this.getUpload());
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "msg";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		return "msg";
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("课件列表"));

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课件名"), "name");
		this.getGridConfig().addColumn(this.getText("课件编号"), "code", true, true, true, "regex:new RegExp(/^\\d{3}?$/),regexText:'课件号输入格式：3位整数',");
		this.getGridConfig().addColumn(this.getText("所属课程"), "peTchCourse.name");
		this.getGridConfig().addColumn(this.getText("作者"), "author", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("出版商"), "publisher", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("是否在使用"), "enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("备注"), "note", true, true, true, "TextArea", true, 2000);
		this.getGridConfig().addColumn(this.getText("课件地址"), "link", true, true, false, "", false, 500);
		this.getGridConfig().addRenderFunction(this.getText("点击查看"), "<a href=\"${value}\" target=\"_blank\">${value}</a> ", "link");

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeTchCourseware.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/courseware";
	}

	public void setBean(PeTchCourseware instance) {
		super.superSetBean(instance);
	}

	public PeTchCourseware getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTchCourseware.class);
		dc.createCriteria("peTchCourse", "peTchCourse");
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		return dc;
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		DetachedCriteria dcPeTchCourseware = DetachedCriteria
				.forClass(PeTchCourseware.class);
		dcPeTchCourseware.add(Restrictions.eq("code", this.getBean().getCode()));
		List bookList = this.getGeneralService().getList(dcPeTchCourseware);
		if (bookList.size() > 0) {
			throw new EntityException("课件号已经存在，请重新填写！");
		}
		dcPeTchCourseware = DetachedCriteria.forClass(PeTchCourseware.class);
		bookList = null;
		dcPeTchCourseware.add(Restrictions.eq("name", this.getBean().getName()));
		bookList = this.getGeneralService().getList(dcPeTchCourseware);
		if (bookList.size() > 0) {
			throw new EntityException("课件名已存在，请重新填写！");
		}
	}


	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	public PeTchCourseService getPeTchCourseService() {
		return peTchCourseService;
	}

	public void setPeTchCourseService(PeTchCourseService peTchCourseService) {
		this.peTchCourseService = peTchCourseService;
	}
}
