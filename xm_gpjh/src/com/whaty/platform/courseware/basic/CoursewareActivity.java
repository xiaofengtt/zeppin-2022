package com.whaty.platform.courseware.basic;

import java.util.List;

import com.whaty.platform.courseware.config.CoursewareConfig;
import com.whaty.platform.courseware.exception.CoursewareException;

public abstract class CoursewareActivity {
	
	public abstract void changeCoursewareType(Courseware courseware,String coursewareType) throws CoursewareException;
	
	/**��һ�δ����μ����ҳ��
	 * @param config
	 * @param coursewareId
	 * @param templateId
	 * @throws CoursewareException
	 */
	public abstract void buildOnlineCourseware(CoursewareConfig config,String coursewareId,String templateId) throws CoursewareException;

	/**Ϊ�μ���������Ϣҳ��
	 * @param config
	 * @param coursewareId
	 * @param infoId
	 * @param infoTitle
	 */
	public abstract void addOnlineCoursewareInfo(CoursewareConfig config, String coursewareId, String infoId, String infoTitle) throws CoursewareException;

	/**Ϊ�μ�ɾ�������Ϣҳ��
	 * @param config
	 * @param coursewareId
	 * @param infoId
	 * @throws CoursewareException
	 */
	public abstract void deleteOnlineCoursewareInfo(CoursewareConfig config, String coursewareId, String infoUrl,String infoTitle) throws CoursewareException;

	/**�õ��μ������Ϣҳ���б�
	 * @param config
	 * @param coursewareId
	 * @return
	 * @throws CoursewareException
	 */
	public abstract List getOnlineCoursewareInfos(CoursewareConfig config, String coursewareId) throws CoursewareException;

	/**������߿μ��½�ҳ��
	 * @param config
	 * @param coursewareId
	 * @param mySection
	 * @return
	 * @throws CoursewareException
	 */
	public abstract void addChapterPage(CoursewareConfig config, String coursewareId, String remark) throws CoursewareException;

	/**�õ��μ�
	 * @param coursewareId
	 * @return
	 * @throws CoursewareException
	 */
	public abstract Courseware getCourseware(String coursewareId) throws CoursewareException;

	/**�õ��μ���ڵ�url
	 * @param config
	 * @param coursewareId
	 * @throws CoursewareException
	 */
	public abstract String getCoursewareEnterUrl(CoursewareConfig config, String coursewareId) throws CoursewareException;

	/**��������μ�
	 * @param coursewareId
	 * @param flag
	 * @throws CoursewareException
	 */
	public abstract void activeCoursewares(List coursewareIdList,boolean flag) throws CoursewareException;
}
