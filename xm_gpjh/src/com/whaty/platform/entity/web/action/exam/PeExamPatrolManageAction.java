package com.whaty.platform.entity.web.action.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeExamPatrol;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.Const;
/**
 * 报名参加监巡考
 * @author zqf
 *
 */
public class PeExamPatrolManageAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle(this.getText("申请参加监巡考"));
		this.getGridConfig().addColumn(this.getText("id"),"id",false);
		this.getGridConfig().addColumn(this.getText("姓名"),"name");
		this.getGridConfig().addColumn(this.getText("是否申请巡考"),"enumConstByFlagIsXunkao.name");
		this.getGridConfig().addColumn(this.getText("是否申请监考"),"enumConstByFlagIsJiankao.name");
		this.getGridConfig().addColumn(this.getText("上岗证号"),"code");
		this.getGridConfig().addColumn(this.getText("身份证号"),"cardNum");
		this.getGridConfig().addColumn(this.getText("性别"), "enumConstByGender.name");
		this.getGridConfig().addColumn(this.getText("籍贯"),"hometown");
		this.getGridConfig().addColumn(this.getText("工作单位"),"workplace");
		this.getGridConfig().addColumn(this.getText("职业"),"occupation");
		EnumConst enum_xunkao_Y = this.getMyListService().getEnumConstByNamespaceCode("FlagIsXunkao", "1");
		EnumConst enum_xunkao_N = this.getMyListService().getEnumConstByNamespaceCode("FlagIsXunkao", "0");
		EnumConst enum_jianKao_Y = this.getMyListService().getEnumConstByNamespaceCode("FlagIsJiankao", "1");
		EnumConst enum_jianKao_N = this.getMyListService().getEnumConstByNamespaceCode("FlagIsJiankao", "0");
		
		String xunKao_Y = enum_xunkao_Y.getId();
		String xunKao_N = enum_xunkao_N.getId();
		String jianKao_Y = enum_jianKao_Y.getId();
		String jianKao_N = enum_jianKao_N.getId();
		this.getGridConfig().addMenuFunction(this.getText("申请参加巡考"), "enumConstByFlagIsXunkao.id", xunKao_Y, "enumConstByFlagIsJiankao.id", jianKao_N);
		this.getGridConfig().addMenuFunction(this.getText("申请参加监考"), "enumConstByFlagIsJiankao.id", jianKao_Y, "enumConstByFlagIsXunkao.id", xunKao_N);
		this.getGridConfig().addMenuFunction(this.getText("取消申请"), "enumConstByFlagIsJiankao.id", jianKao_N, "enumConstByFlagIsXunkao.id", xunKao_N);
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeExamPatrol.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/exam/peExamPatrolManage";
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeExamPatrol.class);
		dc.createAlias("enumConstByGender", "enumConstByGender");
		dc.createAlias("enumConstByFlagIsXunkao", "enumConstByFlagIsXunkao");
		dc.createAlias("enumConstByFlagIsJiankao", "enumConstByFlagIsJiankao");
		//todo   通过登陆管理员所属的学习中心来添加学习中心的条件
		dc.createAlias("peSite", "peSite");
//		dc.add(Restrictions.eq("peSite.name", ""));
		return dc;
	}
	
}
