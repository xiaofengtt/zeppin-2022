package com.whaty.platform.entity.web.action.first;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * 用于招生模块是否允许对学生操作
 * 
 * @author libing
 * 
 */
public class PeRecStudentBaseAction extends MyBaseAction<PeRecStudent> { 

	@Override 
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}

	public void setBean(PeRecStudent instance) {
		super.superSetBean(instance);

	}

	public PeRecStudent getBean() {
		return (PeRecStudent) super.superGetBean();
	}

	/**
	 * 取得学生报名号： 时间+4位顺序号
	 * @return
	 */
	public String getRecSequence(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
		String time = dateFormat.format(date);
		String sql = "select lpad(ARCHIEVE_ID.nextval,4,'0') from dual";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if(list!=null&&list.size()>0){
				time += list.get(0);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
//		if(this.getBean().getPrRecPlanMajorSite()!=null){
//			if(this.getBean().getPrRecPlanMajorSite().getPeSite()!=null){
//				time = this.getBean().getPrRecPlanMajorSite().getPeSite().getCode() + time;
//			}
//		}
		return time;
	}
	/**
	 * 判断传入id是否是当前活动招生批次
	 * 
	 * @param id
	 *            PeRecStudent表的id值
	 * @return
	 */
	public boolean isActive(List ids) {
		String id = "";
		id = "('" + ids.get(0) + "'";
		for (int i = 1; i < ids.size(); i++) {
			id += ",'" + ids.get(i) + "'";
		}
		id += ")";
		String sql = "select student.name	"
				+ "from pe_rec_student            student,"
				+ "     pr_rec_plan_major_site    site,"
				+ "     pr_rec_plan_major_edutype edutype,"
				+ "     pe_recruitplan            recruitplan"
				+ " where student.fk_rec_major_site_id = site.id"
				+ "   and site.fk_rec_plan_major_edutype_id = edutype.id"
				+ "  and edutype.fk_recruitplan_id = recruitplan.id"
				+ "  and recruitplan.flag_active = '1' "
				+ "  and student.id in" + id;
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list.size() == ids.size())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断是否在审核操作日期范围内
	 * 
	 * @return
	 */
	public boolean isCheckDate() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> list;
		try {
			list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				Date start = list.get(0).getSiteCheckStartDate();
				Date end = list.get(0).getSiteCheckEndDate();
				Date date = new Date();
				if (Const.compareDate(start, date) && Const.compareDate(date, end))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 判断是否在免试生录取操作日期范围内 起止时间：招生考试批次开始——录取结束
	 * 
	 * @return
	 */
	public boolean checkNoExamDate() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> list;
		try {
			list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				Date start = list.get(0).getStartDate();
				Date end = list.get(0).getMatriculateEndDate();
				Date date = new Date();
				if (date.after(start) && Const.compareDate(date, end))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断是否在考试生录取操作日期范围内 起止时间：成绩录入开始——录取结束
	 * 
	 * @return
	 */
	public boolean checkExamDate() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		List<PeRecruitplan> list;
		try {
			list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				Date start = list.get(0).getScoreStartDate();
				Date end = list.get(0).getMatriculateEndDate();
				Date date = new Date();
				if (date.after(start) && Const.compareDate(date, end))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断当前是否在审核时间之内。
	 * 根据权限：总站是在招生批次开始之后，考试开始之前；
	 * 分站是在分站审核操作日期范围内。
	 */
	public void checkTime() throws EntityException{
		// 根据session判断审核时间范围
		PeSite peSite = new PeSite();
		UserSession us = (UserSession)ServletActionContext
				.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (!us.getUserLoginType().equals("3")) {
			if (!this.isCheckDate()) {
				throw new EntityException("当前时间不在分站审核操作日期范围内!");
			}
		} else {
			DetachedCriteria dc = DetachedCriteria.forClass(PeRecruitplan.class);
			dc.add(Restrictions.eq("flagActive", "1"));
			List<PeRecruitplan> list;
				list = this.getGeneralService().getList(dc);
				if (list.size() > 0) {
					Date start = list.get(0).getStartDate();
					Date end = list.get(0).getExamStartDate();
					Date date = new Date();
					if (!(Const.compareDate(start, date) && date.before(end)))
						throw new EntityException("审核时间应在招生批次开始之后，考试开始之前!");
				}
		}
	}
}
