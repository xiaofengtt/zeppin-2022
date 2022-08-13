package com.whaty.platform.entity.web.action.exam;

import java.text.SimpleDateFormat;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeExamPatrol;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;
/**
 * 监巡考管理
 * @author zqf
 *
 */
public class PeExamPatrolAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(true, true, true, true, true);
		this.getGridConfig().setTitle(this.getText("监巡考人员管理"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("姓名"),"name");
		//所属学习中心只对总站管理员可见，在添加完用户角色之后，可根据用户角色来判断
		//分站添加的时候默认就是管理员所在的学习中心
		//todo
		this.getGridConfig().addColumn(this.getText("所属学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("上岗证号"),"code");
		this.getGridConfig().addColumn(this.getText("身份证号"),"cardNum");
		this.getGridConfig().addColumn(this.getText("出生日期"),"birthday",true,true,true,"Date",false,50);
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("籍贯"),"hometown");
		this.getGridConfig().addColumn(this.getText("工作单位"),"workplace");
		this.getGridConfig().addColumn(this.getText("职业"),"occupation");
		this.getGridConfig().addColumn(this.getText("广州手机"),"mobileGuangzhou",true,true,true,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("外出手机"),"mobileAway",true,true,true,Const.mobile_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"),"phoneOffice",true,true,true,Const.telephone_for_extjs);
		this.getGridConfig().addColumn(this.getText("家庭电话"),"phoneHome",true,true,true,Const.telephone_for_extjs);
		//缺上传照片的功能
		//todo
		
		
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		AttributeManage manage=new WhatyAttributeManage();
		String str ="";
		String date ="";
		try {
			str = manage.getDateFromIdcard(this.getBean().getCardNum());
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			 date = sdf.format(this.getBean().getBirthday());
		} catch (Exception e) {
			throw new EntityException("身份证号码输入错误！");
		}
		if(!str.equals(date)){
			throw new EntityException("身份证号码与出生日期不一致！");
		}
		EnumConst enumJiankao = this.getMyListService().getEnumConstByNamespaceCode("FlagIsJiankao", "0");
		EnumConst enumXunKao = this.getMyListService().getEnumConstByNamespaceCode("FlagIsXunkao", "0");
		this.getBean().setEnumConstByFlagIsJiankao(enumJiankao);
		this.getBean().setEnumConstByFlagIsXunkao(enumXunKao);
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		AttributeManage manage=new WhatyAttributeManage();
		String str ="";
		String date ="";
		try {
			str = manage.getDateFromIdcard(this.getBean().getCardNum());
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			 date = sdf.format(this.getBean().getBirthday());
		} catch (Exception e) {
			throw new EntityException("身份证号码输入错误！");
		}
		if(!str.equals(date)){
			throw new EntityException("身份证号码与出生日期不一致！");
		}
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeExamPatrol.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peExamPatrol";
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeExamPatrol.class);
		dc.createCriteria("peSite","peSite",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByGender", "enumConstByGender");
		return dc;
	}
	
	public void setBean(PeExamPatrol instance){
		this.superSetBean(instance);
	}
	
	public PeExamPatrol getBean(){
		return (PeExamPatrol)this.superGetBean();
	}
	
}
