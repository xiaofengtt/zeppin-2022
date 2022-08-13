/**
 * 
 */
package com.whaty.platform.training.basic.courseware;

import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.operation.AiccCourse;
import com.whaty.platform.standard.aicc.operation.AiccFactory;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManage;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManagePriv;
import com.whaty.platform.training.basic.TrainingCourseware;
import com.whaty.platform.training.basic.TrainingCoursewareType;

/**
 * @author chenjian
 *
 */
public class AiccTrainingCourseware extends TrainingCourseware {

	private AiccCourse aiccCourse;
	
	
	public AiccTrainingCourseware() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AiccTrainingCourseware(String coursewareId) throws AiccException {
		AiccFactory aiccFactory=AiccFactory.getInstance();
		AiccUserDataManagePriv userPriv=aiccFactory.creatAiccUserDataManagePriv();
		userPriv.getCourse=1;
		AiccUserDataManage aiccUserManage=aiccFactory.creatAiccUserDataManage(userPriv);
		this.setAiccCourse(aiccUserManage.getCourse(coursewareId));
		this.setId(coursewareId);
		this.setName(this.getAiccCourse().getCourse().getCourseTitle());
		this.setCoursewareType(TrainingCoursewareType.AICC);
		this.setNote(this.getAiccCourse().getCourseDes().getCourseDescription());
	}
	
	public AiccCourse getAiccCourse() {
		return aiccCourse;
	}

	public void setAiccCourse(AiccCourse aiccCourse) {
		this.aiccCourse = aiccCourse;
	}

	
	/* (non-Javadoc)
	 * @see com.whaty.platform.training.basic.TrainingCourseware#getEntranceURL(java.util.List)
	 */
	public String getEntranceURL(Map params) throws PlatformException {
		String studentId=(String)params.get("studentId");
		String url="coursestandard/aicc/courseTree.jsp?course_id="+this.getId()+"&user_id="+studentId;
		return url;
	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.training.basic.TrainingCourseware#getStudyStatisticsURL(java.util.Map)
	 */
	public String getStudyStatisticsURL(Map params) throws PlatformException {
		String studentId=(String)params.get("studentId");
		String url="coursestandard/aicc/courseTree.jsp?course_id="+this.getId()+"&user_id="+studentId;
		return url;
	}

}
