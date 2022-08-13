package com.whaty.platform.entity.web.action.teaching.paper;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeTeacher;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.MyListService;
import com.whaty.platform.entity.util.GridConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;

public class PaperTeacherAction extends MyBaseAction<PeTeacher> {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("论文教师管理(双击设置最大论文学生数)"));
		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("教师姓名"), "trueName",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("用户名"), "loginId",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("所属专业"),"peMajor.name",true,true,true,"");
		this.getGridConfig().addColumn(this.getText("单位电话"), "phoneOffice",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilephone",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("电子邮箱"), "email", false,false,true,"");
		this.getGridConfig().addColumn(this.getText("是否带论文"), "enumConstByFlagPaper.name",true,false,true,Const.number_for_extjs);
		this.getGridConfig().addColumn(this.getText("最大论文学生数"), "stuCountLimit",true,true,true,"regex:new RegExp(/^((0|[1-9]\\d{0,3}))$/),regexText:'输入格式：正整数(最多4位)或0',");
		if(this.getGridConfig().checkBeforeAddMenu("/entity/teaching/paperTeacherAdd.action")){
		this.getGridConfig().addMenuScript("添加论文教师", "{window.location='/entity/teaching/paperTeacherAdd.action';}");
		}
		this.getGridConfig().addMenuFunction("删除论文教师", "enumConstByFlagPaper.id","否");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeTeacher.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/paperTeacher";
	}
	
	public PeTeacher getBean(){
		return this.superGetBean();
	}
	
	public void setBean(PeTeacher peTeacher){
		this.superSetBean(peTeacher);
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeTeacher.class);
		dc.createCriteria("enumConstByFlagMaxXueli","enumConstByFlagMaxXueli");
		dc.createCriteria("enumConstByFlagMaxXuewei","enumConstByFlagMaxXuewei");
		dc.createCriteria("enumConstByFlagZhicheng","enumConstByFlagZhicheng");
		dc.createCriteria("enumConstByFlagActive","enumConstByFlagActive").add(Restrictions.eq("name", "是"));
		dc.createCriteria("enumConstByFlagPaper","enumConstByFlagPaper").add(Restrictions.eq("name", "是"));
		dc.createCriteria("enumConstByGender","enumConstByGender");
		dc.createCriteria("peMajor", "peMajor",DetachedCriteria.LEFT_JOIN);
		return dc;
	}

	@Override
	public Map updateColumn() {
		if(this.getColumn().equals("enumConstByFlagPaper.id")){
			EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagPaper","0");
			this.setValue(enumConst.getId());
			return super.updateColumn();
		}else{
			Map map = new HashMap();
			return map;
		}
		
	}
	
}
