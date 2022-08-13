package com.whaty.platform.courseware.basic;

import java.util.List;

import com.whaty.platform.courseware.config.CoursewareConfig;
import com.whaty.platform.courseware.exception.CoursewareException;

public abstract class CoursewareActivity {
	
	public abstract void changeCoursewareType(Courseware courseware,String coursewareType) throws CoursewareException;
	
	/**第一次创建课件相关页面
	 * @param config
	 * @param coursewareId
	 * @param templateId
	 * @throws CoursewareException
	 */
	public abstract void buildOnlineCourseware(CoursewareConfig config,String coursewareId,String templateId) throws CoursewareException;

	/**为课件添加相关信息页面
	 * @param config
	 * @param coursewareId
	 * @param infoId
	 * @param infoTitle
	 */
	public abstract void addOnlineCoursewareInfo(CoursewareConfig config, String coursewareId, String infoId, String infoTitle) throws CoursewareException;

	/**为课件删除相关信息页面
	 * @param config
	 * @param coursewareId
	 * @param infoId
	 * @throws CoursewareException
	 */
	public abstract void deleteOnlineCoursewareInfo(CoursewareConfig config, String coursewareId, String infoUrl,String infoTitle) throws CoursewareException;

	/**得到课件相关信息页面列表
	 * @param config
	 * @param coursewareId
	 * @return
	 * @throws CoursewareException
	 */
	public abstract List getOnlineCoursewareInfos(CoursewareConfig config, String coursewareId) throws CoursewareException;

	/**添加在线课件章节页面
	 * @param config
	 * @param coursewareId
	 * @param mySection
	 * @return
	 * @throws CoursewareException
	 */
	public abstract void addChapterPage(CoursewareConfig config, String coursewareId, String remark) throws CoursewareException;

	/**得到课件
	 * @param coursewareId
	 * @return
	 * @throws CoursewareException
	 */
	public abstract Courseware getCourseware(String coursewareId) throws CoursewareException;

	/**得到课件入口的url
	 * @param config
	 * @param coursewareId
	 * @throws CoursewareException
	 */
	public abstract String getCoursewareEnterUrl(CoursewareConfig config, String coursewareId) throws CoursewareException;

	/**批量激活课件
	 * @param coursewareId
	 * @param flag
	 * @throws CoursewareException
	 */
	public abstract void activeCoursewares(List coursewareIdList,boolean flag) throws CoursewareException;
}
