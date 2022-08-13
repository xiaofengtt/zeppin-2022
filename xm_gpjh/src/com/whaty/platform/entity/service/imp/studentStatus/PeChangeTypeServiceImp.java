package com.whaty.platform.entity.service.imp.studentStatus;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.bean.PrEduMajorSiteFeeLevel;
import com.whaty.platform.entity.bean.PrStuChangeEdutype;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.studentStatas.PeChangeTypeService;

public class PeChangeTypeServiceImp implements PeChangeTypeService {
	
	private GeneralDao generalDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public PrStuChangeEdutype save(PrStuChangeEdutype prStuChangeEdutype)
			throws EntityException {
		try{
			if(prStuChangeEdutype.getPeEdutypeByFkOldEdutypeId().getId().equals(prStuChangeEdutype.getPeEdutypeByFkNewEdutypeId().getId())){
				throw new EntityException("学生的新层次选择和旧层次一样!");				
			}else{
				prStuChangeEdutype = (PrStuChangeEdutype)this.getGeneralDao().save(prStuChangeEdutype);
				if(prStuChangeEdutype!=null){
					DetachedCriteria dc0 = DetachedCriteria.forClass(PeStudent.class);
					dc0.add(Restrictions.eq("id", prStuChangeEdutype.getPeStudent().getId()));					
					PeStudent student = (PeStudent)this.getGeneralDao().getList(dc0).remove(0);//prStuChangeEdutype.getPeStudent();
					student.setPeEdutype(prStuChangeEdutype.getPeEdutypeByFkNewEdutypeId());
					DetachedCriteria dc = DetachedCriteria.forClass(PrEduMajorSiteFeeLevel.class);
					dc.createAlias("peMajor", "peMajor").add(Restrictions.eq("peMajor.id", student.getPeMajor().getId()))
						.createAlias("peSite", "peSite").add(Restrictions.eq("peSite.id", student.getPeSite().getId()))
						.createAlias("peEdutype", "peEdutype").add(Restrictions.eq("peEdutype.id", student.getPeEdutype().getId()));
					student.setPeFeeLevel(((PrEduMajorSiteFeeLevel)this.getGeneralDao().getList(dc).remove(0)).getPeFeeLevel());	
					student = (PeStudent)this.getGeneralDao().save(student);
					prStuChangeEdutype.setPeStudent(student);
				}else{
					throw new EntityException("学生"+prStuChangeEdutype.getPeStudent().getTrueName()+"("+prStuChangeEdutype.getPeStudent().getRegNo()+")"+"添加异动失败！");					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().indexOf("学生")>=0)
				throw new EntityException(e.getMessage());
			else
				throw new EntityException("添加异动失败！");
		}
		return prStuChangeEdutype;
	}
}
