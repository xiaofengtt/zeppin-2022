package com.whaty.platform.courseware;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.basic.Courseware;
import com.whaty.platform.courseware.basic.CoursewareDir;
import com.whaty.platform.courseware.basic.WhatyCoursewareTemplate;
import com.whaty.platform.courseware.basic.WhatyOnlineCourseware;
import com.whaty.platform.util.Page;

/**
 * @author Administrator
 * 
 */
public abstract class CoursewareManage {

	private CoursewareManagerPriv priv;

	public CoursewareManagerPriv getPriv() {
		return priv;
	}

	public void setPriv(CoursewareManagerPriv priv) {
		this.priv = priv;
	}

	/**
	 * �õ��μ�������
	 * 
	 * @param coursewareId
	 * @return
	 */
	public abstract String getCoursewareType(String coursewareId);

	/**
	 * �õ��μ�
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Courseware getCourseware(String coursewareId)
			throws PlatformException;

	/**
	 * �õ������������͵Ŀμ�
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract WhatyOnlineCourseware getWhatyOnlineCourseware(
			String coursewareId) throws PlatformException;

	/**
	 * ��ӿμ�
	 * 
	 * @param name
	 * @param active
	 * @param founderId
	 * @param foundDate
	 * @param author
	 * @param publisher
	 * @param note
	 * @throws PlatformException
	 */
	public abstract int addCourseware(String name, String active,
			String founderId, String foundDate, String author,
			String publisher, String note) throws PlatformException;

	/**
	 * ��ӿμ�
	 * 
	 * @param id
	 * @param name
	 * @param active
	 * @param founderId
	 * @param foundDate
	 * @param author
	 * @param publisher
	 * @param note
	 * @throws PlatformException
	 */
	public abstract int addCourseware(String id, String name, String active,
			String founderId, String foundDate, String author,
			String publisher, String note) throws PlatformException;
	public abstract int addCourseware(String id, String name, String active,
			String founderId, String foundDate, String author,
			String publisher, String note,String openCourseDepId) throws PlatformException;


	/**
	 * ���һ�ſμ����Ҹ�ָ���Ľ�ѧ��ι�j��
	 * 
	 * @param name
	 * @param active
	 * @param founderId
	 * @param foundDate
	 * @param author
	 * @param publisher
	 * @param note
	 * @param teachclass_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addCoursewareAndToTeachClass(String name,
			String active, String founderId, String foundDate, String author,
			String publisher, String note, String teachclass_id)
			throws PlatformException;

	/**
	 * Ϊ��ѧ����ӿμ�
	 * 
	 * @param teachClassId
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addTeachClassCware(String teachClassId,
			String coursewareId, String active) throws PlatformException;

	/**
	 * Ϊ��ѧ����ӿμ�
	 * 
	 * @param teachClassId
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addTeachClassCware(String teachClassId,
			String[] coursewareIds, String[] page_courseware_ids, String active)
			throws PlatformException;

	/**
	 * Ϊ��ѧ��ɾ��μ�
	 * 
	 * @param teachClassId
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteTeachClassCware(String teachClassId,
			String coursewareId) throws PlatformException;

	/**
	 * �޸Ŀμ�����
	 * 
	 * @param coursewareId
	 * @param name
	 * @param active
	 * @param founderId
	 * @param foundDate
	 * @param author
	 * @param publisher
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void updateCourseware(String coursewareId, String name,
			String active, String founderId, String foundDate, String author,
			String publisher, String note) throws PlatformException;
	public abstract void updateCourseware(String coursewareId, String name,
			String active, String founderId, String foundDate, String author,
			String publisher, String note,String openCourseDepId) throws PlatformException;


	/**
	 * ɾ��μ�
	 * 
	 * @param coursewareId
	 * @throws PlatformException
	 */
	public abstract int deleteCourseware(String coursewareId)
			throws PlatformException;

	/**
	 * ���ÿμ�����
	 * 
	 * @param coursewareId
	 * @param courewareType
	 */
	public abstract void setCoursewareType(String coursewareId,
			String courewareType);

	public abstract void creatNormalHttpCourseware(String coursewareId)
			throws PlatformException;

	public abstract void creatUploadHttpCourseware(HttpSession session,
			String coursewareId) throws PlatformException;

	public abstract void creatWhatyOnlineCourseware(String coursewareId,
			String TemplateId);

	/**
	 * ��ÿ��õĿμ�ģ���б�
	 * 
	 * @return WhatyCoursewareTemplate ���б�
	 * @throws PlatformException
	 */
	public abstract List getWhatyCoursewareTemplate() throws PlatformException;

	/**
	 * �õ����߿μ�����ģ��
	 * 
	 * @param templateId
	 * @return
	 */
	public abstract WhatyCoursewareTemplate getWhatyCoursewareTemplate(
			String templateId);

	/**
	 * ɾ�����߿μ�����ģ��
	 * 
	 * @param templateId
	 */
	public abstract void deleteWhatyCoursewareTemplate(String templateId);

	public abstract void changeTemplate(String coursewareId, String templateId);

	/**
	 * ��һ�δ������߿μ�ҳ��
	 * 
	 * @param session
	 *            ���Դ�application�л�����������Ϣ
	 * @param coursewareId
	 *            �μ�id
	 * @param templateId
	 *            ģ��id
	 * @throws PlatformException
	 */
	public abstract void buildOnlineCourseware(HttpSession session,
			String coursewareId, String templateId) throws PlatformException;

	/**
	 * Ϊ���߿μ���ӿγ������Ϣҳ��
	 * 
	 * @param session
	 *            ���Դ�application�л�����������Ϣ
	 * @param coursewareId
	 *            �μ�id
	 * @param infoId
	 *            ��Ϣid
	 * @param infoTitle
	 *            ��Ϣ���
	 * @throws PlatformException
	 */
	public abstract void addOnlineCoursewareInfo(HttpSession session,
			String coursewareId, String infoId, String infoTitle)
			throws PlatformException;

	/**
	 * Ϊ���߿μ�ɾ��γ������Ϣҳ��
	 * 
	 * @param session
	 * @param coursewareId
	 * @param infoId
	 * @throws PlatformException
	 */
	public abstract void deleteOnlineCoursewareInfo(HttpSession session,
			String coursewareId, String infoUrl, String infoTitle)
			throws PlatformException;

	/**
	 * ������߿μ��γ������Ϣҳ��
	 * 
	 * @param session
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getOnlineCoursewareInfos(HttpSession session,
			String coursewareId) throws PlatformException;

	/**
	 * ���߿γ�����½�ҳ��
	 * 
	 * @param session
	 * @param coursewareId
	 * @param remark
	 *            �½�����
	 * @throws PlatformException
	 */
	public abstract void addChapterPage(HttpSession session,
			String coursewareId, String remark) throws PlatformException;

	/**
	 * �õ����߿μ��ĵ�ǰĿ¼
	 * 
	 * @param session
	 * @param request
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract String getOnlineCwRootDir(HttpSession session,
			String coursewareId) throws PlatformException;

	/**
	 * �޸�t���Ϳμ��ĵ�ַ
	 * 
	 * @param coursewareId
	 * @param link
	 * @throws PlatformException
	 */
	public abstract void updateNormalHttpLink(String coursewareId, String link)
			throws PlatformException;

	/**
	 * �޸ı��������ļ�������ļ�path
	 * 
	 * @param coursewareId
	 * @param link
	 * @throws PlatformException
	 */
	public abstract void updateUploadEnterFile(String coursewareId, String link)
			throws PlatformException;

	/**
	 * �õ��μ��б�
	 * 
	 * @param page
	 * @param coursewareId
	 * @param coursewareName
	 * @param active
	 * @param founderId
	 * @param author
	 * @param publisher
	 * @param coursewareType
	 * @return
	 */
	public abstract List searchCoursewares(Page page, String coursewareId,
			String coursewareName, String active, String founderId,
			String author, String publisher, String coursewareType,
			String coursewareDir) throws PlatformException;

	/**
	 * �õ��μ���Ŀ��
	 * 
	 * @param coursewareId
	 * @param coursewareName
	 * @param active
	 * @param founderId
	 * @param author
	 * @param publisher
	 * @param coursewareType
	 * @param coursewareDir
	 * @return
	 */
	public abstract int searchCoursewaresNum(String coursewareId,
			String coursewareName, String active, String founderId,
			String author, String publisher, String coursewareType,
			String coursewareDir);

	/**
	 * �õ�ĳ��Ŀ¼�µĿμ��б�
	 * 
	 * @param coursewareDir
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCoursewares(String parentDir)
			throws PlatformException;
	
	/**
	 * �õ�ĳ��Ŀ¼�µĿμ��б��µĿμ�
	 * 
	 * @param coursewareDir
	 * @param coursewareName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCoursewares(String parentDir, String coursewareName)
			throws PlatformException;

	/**
	 * �õ�ĳ��Ŀ¼�µ���Ŀ¼�б�
	 * 
	 * @param parentDir
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCoursewareDirs(String parentDir)
			throws PlatformException;
	
	/**
	 * �õ�ĳ��Ŀ¼�µ���Ŀ¼
	 * 
	 * @param parentDir
	 * @param dirName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCoursewareDirs(String parentDir, String dirName)
			throws PlatformException;

	/**
	 * �õ��μ���������ʵ�ַ
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
	public abstract String getCoursewareEnterUrl(HttpSession session,
			String coursewareId) throws PlatformException;

	/**
	 * �õ�ĳ��μ�Ŀ¼
	 * 
	 * @param coursewareDirId
	 * @return
	 * @throws PlatformException
	 */
	public abstract CoursewareDir getCoursewareDir(String coursewareDirId)
			throws PlatformException;

	/**
	 * ������μ� flag=1 Ϊ����,flag=0 Ϊ��
	 * 
	 * @param coursewareIdList
	 * @throws PlatformException
	 */
	public abstract void activeCoursewares(List coursewareIdList, String flag)
			throws PlatformException;

	/**
	 * �õ�ģ����ʽͼƬ�ķ���·��
	 * 
	 * @param session
	 * @param templateId
	 * @return
	 * @throws PlatformException
	 */
	public abstract String getCwTemplateImageUrl(HttpSession session,
			String templateId, String imgFile) throws PlatformException;

	/**
	 * ���courseware��������Ҫ�õ�WhatyEditor�����ò���
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
//	public abstract WhatyEditorConfig getWhatyEditorConfig(HttpSession session,
//			String coursewareId) throws PlatformException;

}
