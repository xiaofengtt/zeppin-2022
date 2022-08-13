package com.whaty.platform.entity.web.action.recruit.recExam;

import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeRecRoom;
import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.bean.PrPriManagerSite;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
/**
 * 考场设置
 * @author 李冰
 *
 */
public class PeRecRoomAction extends MyBaseAction {

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle("考场设置");
		this.getGridConfig().setCapability(true, true, true, true);
		
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("招生考试批次"), "peRecruitplan.name");
		this.getGridConfig().addColumn(this.getText("考场名称"), "name");
		this.getGridConfig().addColumn(this.getText("考场号"),"code");
		this.getGridConfig().addColumn(this.getText("教室"),"classroom");
		this.getGridConfig().addColumn(this.getText("学习中心"),"peSite.name");
		this.getGridConfig().addColumn(this.getText("监考人1")
				, "invigilatorA", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("监考人2")
				, "invigilatorB", true, true, true, "TextField", true, 25);
	}
	
	public void checkBeforeUpdate() throws EntityException {
		this.check();
	}
	
	public void checkBeforeAdd() throws EntityException {
		this.check();
	}
	
	public void checkBeforeDelete(List idList) throws EntityException{
		DetachedCriteria dcPeRecRoom = DetachedCriteria.forClass(PeRecRoom.class);
		dcPeRecRoom.createCriteria("peRecruitplan", "peRecruitplan");
		dcPeRecRoom.add(Restrictions.in("id", idList));
		List<PeRecRoom> list = this.getGeneralService().getList(dcPeRecRoom);
		String str = "";
//		for (PeRecRoom peRecRoom : list) {
//			if (peRecRoom.getPeRecruitplan().getFlagActive().equals("1")) {
//				str += "包含当前招生批次的考场。";
//				break;
//			}
//		}
//		if (str.length()>0) {
//			DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
//			dc.add(Restrictions.eq("flagActive", "1"));
//			List<PeRecruitplan> peRecruitplan = this.getGeneralService()
//					.getList(dc);
//			Date start = peRecruitplan.get(0).getStartDate();
//			Date end = peRecruitplan.get(0).getExamStartDate();
//			Date now = new Date();
//			if (!now.before(end)) {
//				str += "只能在考试开始之前删除！";
//				throw new EntityException(str);
//			}
//		}
		for (PeRecRoom peRecRoom : list) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeRecStudent.class);
			dc.add(Restrictions.eq("peRecRoom", peRecRoom));
			List listStu = this.getGeneralService().getList(dc);
			if(listStu!=null&&listStu.size()>0){
				str += "有学生被分配到了“"+peRecRoom.getName()+"”无法删除！<br/>";
			}
		}
		if(str.length()>0){
			throw new EntityException(str);
		}
	}
	
	private void check() throws EntityException {
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
			throw new EntityException("只能在考试开始之前操作！");
		}
		if (!this.getBean().getPeRecruitplan().getId().equals(peRecruitplan.get(0).getId())) {
			throw new EntityException("只能操作当前招生批次的考场！");
		}
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeRecRoom.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peRecRoom";
	}
	
	public void setBean(PeRecRoom instance) {
		super.superSetBean(instance);
		
	}
	
	public PeRecRoom getBean(){
		return (PeRecRoom) super.superGetBean();
	}
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecRoom.class);
		dc.createCriteria("peRecruitplan","peRecruitplan");
		DetachedCriteria dcPeSite = dc.createCriteria("peSite", "peSite",DetachedCriteria.LEFT_JOIN);
		return dc;
	}

}
