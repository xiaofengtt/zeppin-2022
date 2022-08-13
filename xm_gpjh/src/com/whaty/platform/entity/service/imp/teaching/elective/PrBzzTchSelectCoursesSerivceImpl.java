package com.whaty.platform.entity.service.imp.teaching.elective;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.service.teaching.elective.PrBzzTchSelectCoursesSerivce;

public class PrBzzTchSelectCoursesSerivceImpl implements
		PrBzzTchSelectCoursesSerivce {

	private GeneralDao generalDao;
	
	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public List<PeBzzStudent> PeBzzStudentList(DetachedCriteria opdc) {
		
		return this.generalDao.getList(opdc);
	}
	
	public List<PrBzzTchOpencourse> PrBzzTchOpencourseList(
			DetachedCriteria newdc) {
		
		return this.generalDao.getList(newdc);
	}
	
	public void save(PrBzzTchStuElective elective) {
		this.generalDao.save(elective);
	}

	public void updateelective(TrainingCourseStudent trainingCourseStudent) {
		this.generalDao.updateelective(trainingCourseStudent);
	}

	
}
