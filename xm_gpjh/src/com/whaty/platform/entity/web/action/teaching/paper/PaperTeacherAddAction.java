package com.whaty.platform.entity.web.action.teaching.paper;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.service.MyListService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PaperTeacherAddAction extends MyBaseAction<PeTeacher> {
	
	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("添加论文教师"));
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("教师姓名"), "trueName");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("身份证号"), "idCard", true, true, true,  "TextField", false, 25);
		this.getGridConfig().addColumn(this.getText("所属专业"),"peMajor.name",true,true,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("最高学历"), "enumConstByFlagMaxXueli.name");
		this.getGridConfig().addColumn(this.getText("最高学位"), "enumConstByFlagMaxXuewei.name");
		this.getGridConfig().addColumn(this.getText("职称"), "enumConstByFlagZhicheng.name");
		this.getGridConfig().addColumn(this.getText("毕业院校"), "graduateSchool", true, true, false,  "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("毕业专业"), "graduateMajor", true, true, false,  "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("单位电话"), "phoneOffice",  false, true, true, Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilephone", false, true, true, Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false, true, true, Const.email_for_extjs);
		this.getGridConfig().addColumn(this.getText("工作单位"), "workplace", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("简介"), "note", true, true, false, "TextArea", true, 500);
		this.getGridConfig().addMenuFunction("添加为论文教师", "enumConstByFlagPaper.id");
		this.getGridConfig().addMenuScript("返回", "{history.back();}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeTeacher.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/paperTeacherAdd";
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
		dc.createCriteria("enumConstByFlagMaxXueli","enumConstByFlagMaxXueli");
		dc.createCriteria("enumConstByFlagMaxXuewei","enumConstByFlagMaxXuewei");
		dc.createCriteria("enumConstByFlagZhicheng","enumConstByFlagZhicheng");
		dc.createCriteria("enumConstByFlagActive","enumConstByFlagActive").add(Restrictions.eq("name", "是"));
		dc.createCriteria("enumConstByFlagPaper","enumConstByFlagPaper").add(Restrictions.eq("name", "否"));
		dc.createCriteria("enumConstByGender","enumConstByGender");
		dc.createCriteria("peMajor", "peMajor",DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	public Map updateColumn() {
		if(this.getColumn().equals("enumConstByFlagPaper.id")){
			EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagPaper","1");
			this.setValue(enumConst.getId());
			return super.updateColumn();
		}else{
			Map map = new HashMap();
			return map;
		}
	}
	
}
