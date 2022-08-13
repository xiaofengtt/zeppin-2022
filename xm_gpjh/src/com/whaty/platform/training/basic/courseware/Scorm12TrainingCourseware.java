/**
 * 
 */
package com.whaty.platform.training.basic.courseware;

import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.operation.ScormCourse;
import com.whaty.platform.standard.scorm.operation.ScormFactory;
import com.whaty.platform.standard.scorm.operation.ScormStudentManage;
import com.whaty.platform.standard.scorm.operation.ScormStudentPriv;
import com.whaty.platform.training.basic.TrainingCourseware;
import com.whaty.platform.training.basic.TrainingCoursewareType;

/**
 * @author chenjian
 *
 */
public class Scorm12TrainingCourseware extends TrainingCourseware{

	private ScormCourse course;
	
	public Scorm12TrainingCourseware() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Scorm12TrainingCourseware(String coursewareId) throws PlatformException {
		ScormFactory factory=ScormFactory.getInstance();
		ScormStudentPriv userPriv=factory.creatScormStudentPriv();
		userPriv.getCourse=1;
		
		ScormStudentManage userManage=factory.creatScormStudentManage(userPriv);
		this.setId(coursewareId);
		try {
			this.setCourse(userManage.getCourse(coursewareId));
			this.setNavigate(this.getCourse().getNavigate());
		} catch (ScormException e) {
			throw new PlatformException(e.toString());
		}
		this.setCoursewareType(TrainingCoursewareType.SCORM12);
	}

	public ScormCourse getCourse() {
		return course;
	}

	public void setCourse(ScormCourse course) {
		this.course = course;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.basic.TrainingCourseware#getEntranceURL(java.util.Map)
	 */
	
	public String getEntranceURL(Map params) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.basic.TrainingCourseware#getStudyStatisticsURL(java.util.Map)
	 */
	
	public String getStudyStatisticsURL(Map params) throws PlatformException {
		// TODO Auto-generated method stub
		return null;
	}
}
