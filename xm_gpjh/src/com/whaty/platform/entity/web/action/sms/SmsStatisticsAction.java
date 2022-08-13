package com.whaty.platform.entity.web.action.sms;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.PeTrainingPlan;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.bean.SmsInfo;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.entity.bean.PrTraingCourse;
import com.whaty.platform.entity.bean.PeFeeDetail;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.util.Page;


public class SmsStatisticsAction extends MyBaseAction {
	
	

	private String startDate;
	private String endDate;

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc =DetachedCriteria.forClass(PeManager.class);
		return dc;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("短信数据统计"));
        this.getGridConfig().setCapability(false, false, false,true,false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("短信次数"),"sendnum");
		this.getGridConfig().addColumn(this.getText("短信人次"),"totalnum");
		this.getGridConfig().addColumn(this.getText("发送短信人数"),"sendobjnum");
		this.getGridConfig().addColumn(this.getText("短信条数"),"splititem");


//		this.getGridConfig().addMenuScript(this.getText("添加选中"), );
	}


	@Override
	public Page list(){
		
		StringBuffer strb=new StringBuffer();//Oracle 查询语句
		strb.append(" select '1' as id ,count(sms.id) as sendnum,");//--数据库条目数            
		strb.append("      nvl(sum(nvl(sendobjnum,0) * nvl(splititem,0)),0) as totalnum,       ");//--发送短信总条数 
		strb.append("      nvl(sum(nvl(sendobjnum,0)),0) as sendobjnum,       ");//--发送对象总数
		strb.append("      nvl(sum(nvl(splititem,0)),0) as splititem       ");//--分割短信总数
		strb.append(" from sms_info sms,enum_const enu            ");
		strb.append(" where sms.type=enu.id            ");
		strb.append("       and enu.code <>'2'       ");
		strb.append("       and sms.send_date >= to_date('"+this.getStartDate()+"','yyyy-MM-dd')      ");
		strb.append("       and sms.send_date <= to_date('"+this.getEndDate()+"','yyyy-MM-dd')      ");

		StringBuffer strb_mysql=new StringBuffer();//mysql 查询语句
		strb_mysql.append(" select '1' as id ,count(sms.id) as sendnum,");//--数据库条目数            
		strb_mysql.append("      IFNULL(sum(IFNULL(sendobjnum,0) * IFNULL(splititem,0)),0) as totalnum,       ");//--发送短信总条数 
		strb_mysql.append("      IFNULL(sum(IFNULL(sendobjnum,0)),0) as sendobjnum,       ");//--发送对象总数
		strb_mysql.append("      IFNULL(sum(IFNULL(splititem,0)),0) as splititem       ");//--分割短信总数
		strb_mysql.append(" from sms_info sms,enum_const enu            ");
		strb_mysql.append(" where sms.type=enu.id            ");
		strb_mysql.append("       and enu.code <>'2'       ");
		strb_mysql.append("       and sms.send_date >= to_date('"+this.getStartDate()+"','yyyy-MM-dd')      ");
		strb_mysql.append("       and sms.send_date <= to_date('"+this.getEndDate()+"','yyyy-MM-dd')      ");
		
		Page page=null;
		try {
			page=this.getGeneralService().getByPageSQL(strb.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/information/smsStatisticsAction";
		
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



}
