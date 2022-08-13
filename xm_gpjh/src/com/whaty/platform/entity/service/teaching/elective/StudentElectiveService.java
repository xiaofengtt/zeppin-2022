package com.whaty.platform.entity.service.teaching.elective;

import com.whaty.platform.entity.bean.PeSemester;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.exception.EntityException;

public interface StudentElectiveService {

	public String saveElective(PeStudent student, PeSemester semester, String[] courseid) throws EntityException;
	
}
