package com.whaty.platform.courseware.basic;

import java.util.List;

import com.whaty.platform.util.Page;

public interface CoursewareList {

	public List searchWhatyCoursewareTemplate(Page page, List searchProperty,
			List orderProperty);

	public List searchCourseware(Page page, List searchProperty,
			List orderProperty);

	public int searchCoursewareNum(List searchProperty);

	public int addTeachClassCware(String teachClassId, String[] coursewareIds,
			String[] pageCoursewareIds, String active);

}
