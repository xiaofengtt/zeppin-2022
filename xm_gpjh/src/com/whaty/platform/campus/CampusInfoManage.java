package com.whaty.platform.campus;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.CampusNews;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class CampusInfoManage {

	/**
	 * ���ID��ѯ������Ϣ
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract CampusNews getCampusNews(String id)
			throws PlatformException;

	/**
	 * �������Լ���ʦ��Ϣ
	 * @param title
	 * @param shortTitle
	 * @param reporter
	 * @param reportDate
	 * @param submitManagerId
	 * @param submitManagerName
	 * @param body
	 * @param isPop
	 * @param isActive
	 * @param isTop
	 * @param topSequence
	 * @param picLink
	 * @param docentId
	 * @param docentName
	 * @param detail
	 * @param photo_link
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addCampusNews(String title, String shortTitle,
			String reporter, String reportDate, String submitManagerId,
			String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence, String picLink,
			String docentId, String docentName, String detail, String photo_link,String courseLink)
			throws PlatformException;

	/**
	 * �������Լ���ʦ��Ϣ
	 * @param title
	 * @param color
	 * @param shortTitle
	 * @param short_color
	 * @param reporter
	 * @param reportDate
	 * @param submitManagerId
	 * @param submitManagerName
	 * @param body
	 * @param isPop
	 * @param isActive
	 * @param isTop
	 * @param topSequence
	 * @param picLink
	 * @param docentId
	 * @param docentName
	 * @param detail
	 * @param photo_link
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addCampusNews(String title, String color,
			String shortTitle, String short_color, String reporter,
			String reportDate, String submitManagerId,
			String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence, String picLink,
			String docentId, String docentName, String detail, String photo_link,String courseLink)
			throws PlatformException;

	/**
	 * ���½�����Ϣ
	 * @param id
	 * @param shortTitle
	 * @param title
	 * @param reporter
	 * @param reportDate
	 * @param submitManagerId
	 * @param submitManagerName
	 * @param body
	 * @param isPop
	 * @param isActive
	 * @param isTop
	 * @param topSequence
	 * @param picLink
	 * @param docentId
	 * @param docentName
	 * @param detail
	 * @param photo_link
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateCampusNews(String id, String shortTitle,
			String title, String reporter, String reportDate,
			String submitManagerId, String submitManagerName, String body,
			String isPop, String isActive, String isTop, String topSequence,
			String picLink, String docentId, String docentName, String detail,
			String photo_link,String courseLink) throws PlatformException;

	/**
	 * ���½�����Ϣ
	 * @param id
	 * @param title
	 * @param color
	 * @param shortTitle
	 * @param short_color
	 * @param reporter
	 * @param reportDate
	 * @param submitManagerId
	 * @param submitManagerName
	 * @param body
	 * @param isPop
	 * @param isActive
	 * @param isTop
	 * @param topSequence
	 * @param picLink
	 * @param docentId
	 * @param docentName
	 * @param detail
	 * @param photo_link
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateCampusNews(String id, String title, String color,
			String shortTitle, String short_color, String reporter,
			String reportDate, String submitManagerId,
			String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence, String picLink,
			String docentId, String docentName, String detail, String photo_link,String courseLink)
			throws PlatformException;

	/**
	 * ���IDɾ������Ϣ
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteCampusNews(String id) throws PlatformException;

	/**
	 * ��ɾ������Ϣ
	 * @param ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteCampusNews(List ids) throws PlatformException;

	/**
	 * ���ID��������Ϣ�ö�
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void putTop(String id) throws PlatformException;

	/**
	 * ����������Ϣ�ö�
	 * @param ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract int putTop(List ids) throws PlatformException;

	/**
	 * �ö�˳��
	 * @param id
	 * @param topSequence
	 * @throws PlatformException
	 */
	public abstract void putTop(String id, String topSequence)
			throws PlatformException;

	/**
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void cancelTop(String id) throws PlatformException;

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract int cancelTop(List ids) throws PlatformException;

	/**
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void active(String id) throws PlatformException;

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract int active(List ids) throws PlatformException;

	/**
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void unActive(String id) throws PlatformException;

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract int unActive(List ids) throws PlatformException;

	/**
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void putPop(String id) throws PlatformException;

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract int putPop(List ids) throws PlatformException;

	/**
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void cancelPop(String id) throws PlatformException;

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws PlatformException
	 */
	public abstract int cancelPop(List ids) throws PlatformException;

	/**
	 * ����һ������Ϊ���״̬, confirmFlag Ϊ��˱�־�� true Ϊ����ˣ� false Ϊδ���
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int confirmNews(String managerId, String managerName,
			List newsIds, boolean confirmFlag) throws PlatformException;

	 

	/**
	 * ���courseware��������Ҫ�õ�WhatyEditor�����ò���
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
//	public abstract WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException;

	public abstract int addNewsReadCount(String newsId)
			throws PlatformException;

	//****************************DATA*******QUERY **********************************

 

	/**
	 * 
	 * @param page
	 * @param isactive
	 * @param rep_date_box
	 * @param title_box
	 * @param isTop
	 * @param isconfirm
	 * @param isPop
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getNewsList(Page page, String isactive,
			String rep_date_box, String title_box, String isTop,
			String isconfirm, String isPop) throws PlatformException;
	
	public abstract int getNewsListNum(String isactive, String rep_date_box,
			String title_box, String isTop, String isconfirm, String isPop)
			throws PlatformException;

}
