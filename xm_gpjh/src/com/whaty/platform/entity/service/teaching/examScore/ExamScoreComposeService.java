package com.whaty.platform.entity.service.teaching.examScore;

import com.whaty.platform.entity.exception.EntityException;

public interface ExamScoreComposeService {

	public String saveCompose() throws EntityException;
	
	public String saveComposeSingle(String peCourseName,String scoreExamRate,String scoreHomeworkRate,String scoreUsualRate,String peEdutypeName,String peGradeName,String peMajorName) throws EntityException;
}
