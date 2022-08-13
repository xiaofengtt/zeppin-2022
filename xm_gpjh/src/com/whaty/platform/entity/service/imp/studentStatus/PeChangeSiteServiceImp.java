package com.whaty.platform.entity.service.imp.studentStatus;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.bean.PrStuChangeSite;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.PeChangeSiteService;

public class PeChangeSiteServiceImp implements PeChangeSiteService {

	private GeneralDao generalDao;
	
	/* (non-Javadoc)
	 * @see com.whaty.platform.entity.service.studentStatas.PeChangeSiteService#save(com.whaty.platform.entity.bean.PrStuChangeSite)
	 */
	public PrStuChangeSite save(PrStuChangeSite prStuChangeSite)throws EntityException{
		try{
			if(prStuChangeSite.getPeSiteByFkOldSiteId().getId().equals(prStuChangeSite.getPeSiteByFkNewSiteId().getId())){
				throw new EntityException("学生的新学习中心选择和旧学习中心一样!");				
			}else{
				prStuChangeSite = (PrStuChangeSite)this.getGeneralDao().save(prStuChangeSite);
				if(prStuChangeSite!=null){
					DetachedCriteria dc0 = DetachedCriteria.forClass(PeStudent.class);
					dc0.add(Restrictions.eq("id", prStuChangeSite.getPeStudent().getId()));					
					PeStudent student = (PeStudent)this.getGeneralDao().getList(dc0).remove(0);//prStuChangeSite.getPeStudent();
					student.setPeSite(prStuChangeSite.getPeSiteByFkNewSiteId());
					DetachedCriteria dc = DetachedCriteria.forClass(PrEduMajorSiteFeeLevel.class);
					dc.createAlias("peMajor", "peMajor").add(Restrictions.eq("peMajor.id", student.getPeMajor().getId()))
						.createAlias("peSite", "peSite").add(Restrictions.eq("peSite.id", student.getPeSite().getId()))
						.createAlias("peEdutype", "peEdutype").add(Restrictions.eq("peEdutype.id", student.getPeEdutype().getId()));
					student.setPeFeeLevel(((PrEduMajorSiteFeeLevel)this.getGeneralDao().getList(dc).remove(0)).getPeFeeLevel());	
					student = (PeStudent)this.getGeneralDao().save(student);
					prStuChangeSite.setPeStudent(student);
				}else{
					throw new EntityException("学生"+prStuChangeSite.getPeStudent().getTrueName()+"("+prStuChangeSite.getPeStudent().getRegNo()+")"+"添加异动失败！");					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage());
			else
				throw new EntityException("添加异动失败！");
		}
		return prStuChangeSite;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}
	
}
