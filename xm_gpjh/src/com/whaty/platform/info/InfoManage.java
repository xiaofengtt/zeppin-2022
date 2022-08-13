/**
 * 
 */
package com.whaty.platform.info;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class InfoManage {

	private String newsTypeId;

	public String[] NewsTagsToCreateSiteType = { "��վ֪ͨ", "��վ���¹���", "��վ������Ϣ",
			"��վ��ѧ����", "��վ���԰���" };

	public String getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewsTypeId(String newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

	/**
	 * �õ�ĳ�����ŵ���ϸ��Ϣ
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract News getNews(String id) throws PlatformException;

	/**
	 * �õ�ĳ�������µ�ȫ�������б�,
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getNewsList(String newsTypeId)
			throws PlatformException;

	/**
	 * �õ�ĳ�������µ�ȫ��������Ŀ
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getNewsListNum(String newsTypeId)
			throws PlatformException;

	/**
	 * ��ҳ�õ�ĳ�������µ������б�
	 * 
	 * @param page
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getNewsList(Page page, String newsTypeId)
			throws PlatformException;

	/**
	 * ����ĳ�������µ������б?����ģ���ѯ��beginDate��endDateΪʱ����
	 * 
	 * @param page
	 * @param title
	 * @param begainDate
	 * @param endDate
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchNewsList(Page page, String title,
			String beginDate, String endDate, String newsTypeId)
			throws PlatformException;

	public abstract int addNews(String title, String shortTitle,
			String reporter, String reportDate, String newsTypeId,
			String submitManagerId,String submitManagerName, String body, String isPop, String isActive,
			String isTop, String topSequence, String propertyString,
			String picLink) throws PlatformException;

	public abstract int addNews(String title, String shortTitle,
			String reporter, String reportDate, String[] newsTypeIds,
			String submitManagerId,String submitManagerName, String body, String isPop, String isActive,
			String isTop, String topSequence, String propertyString,
			String picLink) throws PlatformException;

	public abstract int addNews(String title, String color, String short_title,
			String short_color, String reporter, String reportDate,
			String[] types, String submitManagerId,String submitManagerName, String body, String isPop,
			String isActive, String isTop, String topSequence, String[] person,
			String[] site_id, String[] grade_id, String[] major_id,
			String[] level_id, String picLink) throws PlatformException;

	public abstract int updateNews(String id, String shortTitle, String title,
			String reporter, String reportDate, String newsTypeId,
			String submitManagerId,String submitManagerName, String body, String isPop, String isActive,
			String isTop, String topSequence, String propertyString,
			String picLink) throws PlatformException;

	public abstract int deleteNews(String id) throws PlatformException;

	public abstract int deleteNews(List ids) throws PlatformException;

	public abstract void putTop(String id) throws PlatformException;

	public abstract int putTop(List ids) throws PlatformException;

	public abstract void putTop(String id, String topSequence)
			throws PlatformException;

	public abstract void cancelTop(String id) throws PlatformException;

	public abstract int cancelTop(List ids) throws PlatformException;

	public abstract void active(String id) throws PlatformException;

	public abstract int active(List ids) throws PlatformException;

	public abstract void unActive(String id) throws PlatformException;

	public abstract int unActive(List ids) throws PlatformException;

	public abstract void putPop(String id) throws PlatformException;

	public abstract int putPop(List ids) throws PlatformException;

	public abstract void cancelPop(String id) throws PlatformException;

	public abstract int cancelPop(List ids) throws PlatformException;

	public abstract NewsType getNewsType(String id) throws PlatformException;

	public abstract List getChildNewsType() throws PlatformException;

	public abstract int addNewsType(String name, String parent_id, String note,
			String status) throws PlatformException;

	public abstract int updateNewsType(String id, String name, String note,
			String status, String parent_id) throws PlatformException;

	public abstract int deleteNewsType(String id) throws PlatformException;

	public abstract int deleteNewsType(List ids) throws PlatformException;

	public abstract void moveNewsType(String id, String otherParentNewsTypeId)
			throws PlatformException;

	public abstract void setTypeManager(InfoManagerPriv managerpriv,
			String newsTypeId) throws PlatformException;

	public abstract List getNewsTypes(Page page, String parent_id)
			throws PlatformException;

	public abstract List getRightNewsTypes(Page page) throws PlatformException;

	/**
	 * Ϊƽ̨���Ź���Ա�Լ�ѧԺ���Ź���Ա����д�����ͣ� ����(��ڵ�.
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getInfoManagerNewsTypes(Page page,
			List searchproperty, List orderporperty) throws PlatformException;

	public abstract List getRightNewsTypes(Page page, List searchproperty,
			List orderporperty) throws PlatformException;

	public abstract List getNewsTypes(Page page, List searchproperty,
			List orderporperty) throws PlatformException;

	public abstract int getNewsTypesNum(String parent_id)
			throws PlatformException;

	public abstract int getNewsTypesNum(List searchproperty)
			throws PlatformException;

	/**
	 * ��ҳ�õ�ĳ�������µ������б�
	 * 
	 * @param page
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getNewsList(Page page, List searchproperty,
			List orderporperty) throws PlatformException;

	public abstract List getNewsList(Page page, String isactive,
			String news_type_id, String rep_date_box, String title_box,
			String isTop, String isconfirm, String isPop)
			throws PlatformException;

	/**
	 * �õ�ĳ�������µ�ȫ��������Ŀ
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getNewsListNum(List searchproperty)
			throws PlatformException;

	/**
	 * �õ�ĳ�������µ�ȫ��������Ŀ
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getNewsListNum(String isactive, String news_type_id,
			String rep_date_box, String title_box, String isTop,
			String isconfirm, String isPop)

	throws PlatformException;

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
	 * ��ȡ���ŵķ�����Χ,����ֵ��ÿһ��key-value��Ӧ��һ���������ֺ�һ������ֵ��List.
	 * 
	 * @param newsId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Map getNewsScope(News news) throws PlatformException;

	/**
	 * ����������ͱ�ǩ��ȡ������������Ŀ
	 * 
	 * @param tag
	 * @return
	 */
	public abstract int getNewsNum(String tag) throws PlatformException;

	/**
	 * ����������ͱ�ǩ��ȡ����������
	 * 
	 * @param page
	 * @param tag
	 * @return
	 */
	public abstract List getNews(Page page, String tag)
			throws PlatformException;

	/**
	 * ����������±�ǩ��ȡ���µģ�ָ����Ŀ����������
	 * 
	 * @param tag
	 * @param num
	 * @return
	 */
	public abstract List getNews(String tag, int num) throws PlatformException;

	/**
	 * ����������±�ǩ��ȡ���µģ�ָ����Ŀ����������
	 * 
	 * @param tag
	 * @param num
	 * @return
	 */
	public abstract Map getNews(Map tagAndNumber) throws PlatformException;

	/**
	 * ����������ͱ�ǩ
	 * 
	 * @param newsTypeId
	 * @param tag
	 */
	public abstract int updateTag(String newsTypeId, String tag)
			throws PlatformException;

	/**
	 * ���courseware��������Ҫ�õ�WhatyEditor�����ò���
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
//	public abstract WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException;

	public abstract int copyNews(String news_id, String newsType_id)
			throws PlatformException;

	public abstract int addInfoManager(String login_id, String name,
			String password, String nickName, String email, String status)
			throws PlatformException;

	/**
	 * ��վ֪ͨ��ǩ����
	 * 
	 * @param siteId
	 * @return
	 */
	public final String createSiteNoticeTag(String siteId) {
		if (siteId == null || siteId.equals("") || siteId.equals("null"))
			return null;
		return siteId + "��վ֪ͨ:" + siteId;
	}

	/**
	 * Ϊû�з�վ֪ͨ�������͵�վ�㴴��֪ͨ���������Լ���ǩ ��ǩ����"��վ֪ͨ:" + siteId";
	 * 
	 * ��վ֪ͨ�ĸ�����Ϊһ�� tag="��վ֪ͨ"����������
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int createNewsTypeForSiteNotice() throws PlatformException;

	public abstract int createNewsTypeForSiteNews() throws PlatformException;

	public abstract int putInfomanagerPriv(String userId, String siteId)
			throws PlatformException;

	public abstract int deleteSiteInfomanagerPriv(String userId, String siteId)
			throws PlatformException;

	public abstract List getRightNewsTypeIds(String userId)
			throws PlatformException;

	public abstract int updateInfoRight(String userId,
			String[] pageNewsTypeIds, String[] newsTypeIds)
			throws PlatformException;

	public abstract int addNewsReadCount(String newsId)
			throws PlatformException;
	
	public abstract SmsManagerPriv getSmsManagerPriv(String managerId)
	throws PlatformException;
	
	public abstract SmsManage getSmsManage(String managerId)
	throws PlatformException;
}
