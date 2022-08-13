package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 入学考试考场分配结果查看及修改
 * 
 * @author 李冰
 * 
 */
public class ExamStuRoomAction extends MyBaseAction {

	@Override
	public void initGrid() {
		if(this.getMsg()!=null&&this.getMsg().equals("current")){
			this.getGridConfig().setTitle(this.getText("准考证打印列表(只打印有考试科目的学生，打印时请在页面设置中选择A5纸)"));
			this.getGridConfig().setCapability(false, false, false);
			//this.getGridConfig().addMenuFunction(this.getText("打印准考证"), "/entity/recruit/examStuRoom_print.action", false, false);
			if(this.getGridConfig().checkBeforeAddMenu("/entity/recruit/examStuRoom_print.action")){
			this.getGridConfig().addMenuScript(this.getText("打印准考证"), 
					"{var m = grid.getSelections();  "+
						"if(m.length > 0){	         "+
						"	var jsonData = '';       "+
						"	for(var i = 0, len = m.length; i < len; i++){"+
						"		var ss =  m[i].get('id');"+
						"		if(i==0)	jsonData = jsonData + ss ;"+
						"		else	jsonData = jsonData + ',' + ss;"+
						"	}                        "+
						"	document.getElementById('user-defined-content').style.display='none'; "+
						"	document.getElementById('user-defined-content').innerHTML=\"<form target='_blank' action='/entity/recruit/examStuRoom_print.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ></form>\";"+
						"	document.formx1.submit();"+
						"	document.getElementById('user-defined-content').innerHTML=\"\";"+
						"} else {                    "+
//						"	Ext.MessageBox.alert('<s:text name=\"test.error\"/>','<s:text name=\"test.pleaseSelectAtLeastOneItem\"/>');"+
						"Ext.MessageBox.alert('" + this.getText("test.error") + "', '" + this.getText("test.pleaseSelectAtLeastOneItem") + "');  "  +                    
			"}}                         ");
			}
		}else{
			this.getGridConfig().setTitle(this.getText("考场分配结果"));
			this.getGridConfig().setCapability(false, false, true);
		}
		this.getGridConfig().addColumn(this.getText("id"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"), "name", true, false,
				true, "");
		this.getGridConfig().addColumn(this.getText("性别"), "gender", true,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo", true,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("学习中心"),
				"prRecPlanMajorSite.peSite.name", true, false, true, "");
		this.getGridConfig().addColumn(this.getText("层次"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name",
				true, false, true, "");
		this.getGridConfig().addColumn(this.getText("专业"),
				"prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name", true,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("学生状态"),
				"enumConstByFlagRecStatus.name", false, false, true, "");
		this.getGridConfig().addColumn(this.getText("准考证号"), "examCardNum");
		ColumnConfig column1 = new ColumnConfig(this.getText("考场名称"), "peRecRoom.name");
		column1.setComboSQL(" select room.id,room.name   from pe_rec_room room, pe_recruitplan recplan   "+
				"  where room.fk_recruitplan_id = recplan.id   and recplan.flag_active = '1'  ");
		this.getGridConfig().addColumn(column1);
		this.getGridConfig().addColumn(this.getText("座位号"), "seatNum",true, true, true, Const.numberMessage);
	}

	public void checkBeforeUpdate() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> peRecruitplan = this.getGeneralService()
				.getList(dc);
		Date start = peRecruitplan.get(0).getStartDate();
		Date end = peRecruitplan.get(0).getExamStartDate();
		Date now = new Date();
		if (!start.before(now)) {
			throw new EntityException("招生考试批次还未开始，无法操作");
		}
		if (!now.before(end)) {
			throw new EntityException("考试已经开始，无法操作");
		}
//		if (!this.getBean().getPeRecRoom().getPeRecruitplan().getId().equals(peRecruitplan.get(0).getId()))  {
//			throw new EntityException("只能选择当前招生批次的考场！");
//		}
		int seat = 0;
		try {
		 seat = Integer.parseInt(this.getBean().getSeatNum());

		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("座位号错误！");
		}
		if(seat>30){
			throw new EntityException("座位号不能大于30！");
		}
	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
		DetachedCriteria dcMajorEdutype = dc.createCriteria(
				"prRecPlanMajorSite", "prRecPlanMajorSite").createAlias(
				"peSite", "peSite").createCriteria("prRecPlanMajorEdutype",
				"prRecPlanMajorEdutype");
		dcMajorEdutype.createAlias("peEdutype", "peEdutype").createAlias(
				"peMajor", "peMajor");
		dcMajorEdutype.createCriteria("peRecruitplan", "peRecruitplan").add(
				Restrictions.eq("flagActive", "1"));
		DetachedCriteria dcFlagRecStatus = dc.createCriteria(
				"enumConstByFlagRecStatus", "enumConstByFlagRecStatus");
		dcFlagRecStatus.add(Restrictions.eq("code", "3"));
		if(this.getMsg()!=null&&this.getMsg().equals("current")){
			dc.createCriteria("peRecRoom", "peRecRoom");
		}else{
			dc.createCriteria("peRecRoom", "peRecRoom", DetachedCriteria.LEFT_JOIN);
		}
		DetachedCriteria dcEnumConstByFlagNoexam = dc.createCriteria(
				"enumConstByFlagNoexam", "enumConstByFlagNoexam");
		dcEnumConstByFlagNoexam.add(Restrictions.eq("code", "0"));
		return dc;
	}

	public List getRecStuPrint(){
		try{
			String[] printIds = this.getIds().split(",");
			DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
			dc.add(Restrictions.in("id", printIds));
			return this.getGeneralService().getList(dc);
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}
	public List getStuCourse(String id){
		try{
			DetachedCriteria dc = DetachedCriteria.forClass(PrRecExamStuCourse.class);
			dc.add(Restrictions.eq("peRecStudent.id", id));
			return this.getGeneralService().getList(dc);
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
		
	}
	public String print(){
		return "print";
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeRecStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/examStuRoom";
	}

}
