package com.whaty.platform.entity.service.imp.studentStatus;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.bean.PrStuChangeMajor;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.PeChangeMajorService;

public class PeChangeMajorServiceImp implements PeChangeMajorService {

	private GeneralDao generalDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public PrStuChangeMajor save(PrStuChangeMajor prStuChangeMajor)
			throws EntityException {
		try{
			if(prStuChangeMajor.getPeMajorByFkOldMajorId().getId().equals(prStuChangeMajor.getPeMajorByFkNewMajorId().getId())){
				throw new EntityException("学生的新专业选择和旧专业一样!");				
			}else{
				prStuChangeMajor = (PrStuChangeMajor)this.getGeneralDao().save(prStuChangeMajor);
				if(prStuChangeMajor!=null){
					DetachedCriteria dc0 = DetachedCriteria.forClass(PeStudent.class);
					dc0.add(Restrictions.eq("id", prStuChangeMajor.getPeStudent().getId()));					
					PeStudent student = (PeStudent)this.getGeneralDao().getList(dc0).remove(0);//prStuChangeMajor.getPeStudent();
					student.setPeMajor(prStuChangeMajor.getPeMajorByFkNewMajorId());
					DetachedCriteria dc = DetachedCriteria.forClass(PrEduMajorSiteFeeLevel.class);
					dc.createAlias("peMajor", "peMajor").add(Restrictions.eq("peMajor.id", student.getPeMajor().getId()))
						.createAlias("peSite", "peSite").add(Restrictions.eq("peSite.id", student.getPeSite().getId()))
						.createAlias("peEdutype", "peEdutype").add(Restrictions.eq("peEdutype.id", student.getPeEdutype().getId()));
					student.setPeFeeLevel(((PrEduMajorSiteFeeLevel)this.getGeneralDao().getList(dc).remove(0)).getPeFeeLevel());	
					student = (PeStudent)this.getGeneralDao().save(student);
					prStuChangeMajor.setPeStudent(student);
				}else{
					throw new EntityException("学生"+prStuChangeMajor.getPeStudent().getTrueName()+"("+prStuChangeMajor.getPeStudent().getRegNo()+")"+"添加异动失败！");					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage());
			else
				throw new EntityException("添加异动失败！");
		}
		return prStuChangeMajor;
	}
	
}
