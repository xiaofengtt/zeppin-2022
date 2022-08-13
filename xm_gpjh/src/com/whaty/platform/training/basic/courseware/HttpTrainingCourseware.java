/**
 * 
 */
package com.whaty.platform.training.basic.courseware;

import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.training.basic.TrainingCourseware;

/**
 * @author chenjian
 *
 */
public class HttpTrainingCourseware extends TrainingCourseware {

	private String httpLink;
	
	public HttpTrainingCourseware() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HttpTrainingCourseware(String id) {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHttpLink() {
		return httpLink;
	}

	public void setHttpLink(String httpLink) {
		this.httpLink = httpLink;
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
