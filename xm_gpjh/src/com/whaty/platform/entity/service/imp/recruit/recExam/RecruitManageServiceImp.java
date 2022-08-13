package com.whaty.platform.entity.service.imp.recruit.recExam;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PrRecExamCourseTime;
import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.bean.PrRecPlanMajorEdutype;
import com.whaty.platform.entity.bean.PrRecPlanMajorSite;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.GeneralService;
import com.whaty.platform.entity.service.recruit.recExam.RecruitManageService;

public class RecruitManageServiceImp implements RecruitManageService {
	private GeneralDao generalDao;


	public void saveScoreLine(String courseId, String score)
			throws EntityException {
		String[] courses = courseId.split(",");
		String[] scores = score.split(",");
		try {
			for (int i = 0; i < courses.length; i++) {
				DetachedCriteria dcPrRecExamCourseTime = DetachedCriteria
						.forClass(PrRecExamCourseTime.class);
				DetachedCriteria dcPeRecruitplan = dcPrRecExamCourseTime
						.createCriteria("peRecruitplan", "peRecruitplan");
				DetachedCriteria dcPeRecExamcourse = dcPrRecExamCourseTime
						.createCriteria("peRecExamcourse", "peRecExamcourse");
				dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
				dcPeRecExamcourse.add(Restrictions.eq("id", courses[i].trim()));
				List<PrRecExamCourseTime> list = this.getGeneralDao().getList(
						dcPrRecExamCourseTime);
				if (list.size() > 0) {
					PrRecExamCourseTime prRecExamCourseTime = list.get(0);
					prRecExamCourseTime.setScoreLine(Long.parseLong(scores[i]
							.trim()));
					this.getGeneralDao().save(prRecExamCourseTime);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制招生计划
	 * @param plan1name 源招生计划
	 * @param plan2name 目标招生计划
	 * @throws EntityException
	 */
	public void saveCopyPlan(String plan1name, String plan2name)throws EntityException{
		DetachedCriteria dcPlan1 = DetachedCriteria.forClass(PeRecruitplan.class);
		dcPlan1.add(Restrictions.eq("name", plan1name));
		List<PeRecruitplan> list1 = this.getGeneralDao().getList(dcPlan1);
		if(list1==null||list1.isEmpty()){
			throw new EntityException("源招生计划不存在!");
		}
		PeRecruitplan recruitplan1 = list1.get(0);
		
		DetachedCriteria dcPlan2 = DetachedCriteria.forClass(PeRecruitplan.class);
		dcPlan2.add(Restrictions.eq("name", plan2name));
		List<PeRecruitplan> list2 = this.getGeneralDao().getList(dcPlan2);
		if(list2==null||list2.isEmpty()){
			throw new EntityException("目标招生计划不存在!");
		}
		PeRecruitplan recruitplan2 = list2.get(0);
		
		//查询源计划招生的层次专业。
		DetachedCriteria dcPlanMajorEdutype = DetachedCriteria.forClass(PrRecPlanMajorEdutype.class);
		dcPlanMajorEdutype.add(Restrictions.eq("peRecruitplan", recruitplan1));
		List<PrRecPlanMajorEdutype> majorEdutypeList = this.getGeneralDao().getList(dcPlanMajorEdutype);
		if(majorEdutypeList==null||majorEdutypeList.isEmpty()){
			throw new EntityException("源招生计划没有招生的层次专业!");
		}
		
		try {
		//查询目标计划招生的层次专业。
		DetachedCriteria dcPlanMajorEdutype2 = DetachedCriteria.forClass(PrRecPlanMajorEdutype.class);
		dcPlanMajorEdutype2.add(Restrictions.eq("peRecruitplan", recruitplan2));
		List<PrRecPlanMajorEdutype> majorEdutypeList2 = this.getGeneralDao().getList(dcPlanMajorEdutype2);
		if(majorEdutypeList2!=null&&majorEdutypeList2.size()>0){

				for (PrRecPlanMajorEdutype prRecPlanMajorEdutype2 : majorEdutypeList2) {
					DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria.forClass(PrRecPlanMajorSite.class);
					dcPrRecPlanMajorSite.add(Restrictions.eq("prRecPlanMajorEdutype", prRecPlanMajorEdutype2));
					List<PrRecPlanMajorSite> majorSiteList = this.getGeneralDao().getList(dcPrRecPlanMajorSite);
					if(majorSiteList!=null&&majorSiteList.size()>0){
						for (PrRecPlanMajorSite prRecPlanMajorSite : majorSiteList) {
							this.getGeneralDao().delete(prRecPlanMajorSite);
						}
					}
					this.getGeneralDao().delete(prRecPlanMajorEdutype2);
				}

		}
		
		for (PrRecPlanMajorEdutype prRecPlanMajorEdutype : majorEdutypeList) {
			PrRecPlanMajorEdutype prRecPlanMajorEdutype2 = new PrRecPlanMajorEdutype();
			prRecPlanMajorEdutype2.setPeEdutype(prRecPlanMajorEdutype.getPeEdutype());
			prRecPlanMajorEdutype2.setPeMajor(prRecPlanMajorEdutype.getPeMajor());
			prRecPlanMajorEdutype2.setPeRecruitplan(recruitplan2);
			prRecPlanMajorEdutype2 = (PrRecPlanMajorEdutype)this.getGeneralDao().save(prRecPlanMajorEdutype2);
			
			//查询源计划招生的层次专业学习中心
			DetachedCriteria dcPrRecPlanMajorSite = DetachedCriteria.forClass(PrRecPlanMajorSite.class);
			dcPrRecPlanMajorSite.add(Restrictions.eq("prRecPlanMajorEdutype", prRecPlanMajorEdutype));
			List<PrRecPlanMajorSite> majorSiteList = this.getGeneralDao().getList(dcPrRecPlanMajorSite);
			if(majorSiteList!=null&&majorSiteList.size()>0){
				for (PrRecPlanMajorSite prRecPlanMajorSite : majorSiteList) {
					PrRecPlanMajorSite prRecPlanMajorSite2 = new PrRecPlanMajorSite();
					prRecPlanMajorSite2.setPeSite(prRecPlanMajorSite.getPeSite());
					prRecPlanMajorSite2.setPrRecPlanMajorEdutype(prRecPlanMajorEdutype2);
					this.getGeneralDao().save(prRecPlanMajorSite2);
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EntityException("目标招生计划已经有学生报名，无法操作!");
		}
	}
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

}
