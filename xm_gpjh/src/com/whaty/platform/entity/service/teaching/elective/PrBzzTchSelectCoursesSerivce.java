package com.whaty.platform.entity.service.teaching.elective;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.TrainingCourseStudent;

public interface PrBzzTchSelectCoursesSerivce {

	public List<PeBzzStudent> PeBzzStudentList(DetachedCriteria opdc);

	public List<PrBzzTchOpencourse> PrBzzTchOpencourseList(
			DetachedCriteria newdc);

	public void save(PrBzzTchStuElective elective);

	public void updateelective(TrainingCourseStudent trainingCourseStudent);

	
}
