/**
 * 
 */
package com.whaty.platform.campus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.base.CampusNews;
import com.whaty.platform.entity.BasicEntityManage;
import com.whaty.platform.util.Page;
import com.whaty.util.log.Log;

/**
 * @author chenjian
 * 
 */
public abstract class CampusNormalInfoManage {

	private static Map newsReadCount;

	/**
	 * �����Ķt��������ݿ�ļ�����
	 */
	private final static int READ_COUNT_STEP_TO_SAVE =10;

 
	/**
	 * ��ݵ�½�ʺŻ�ø��û����ֻ��
	 * 
	 * @param loginId
	 * @return
	 * @throws PlatformException
	 */
	public abstract String getMobilePhoneByLogin(String loginId)
			throws PlatformException;
    
	/**
	 * �õ�newsTypeId�����µĻ����ͨ�����б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getActiveNews(Page page)
			throws PlatformException;
	/**
	 * �õ�newsTypeId�����µĻ����ͨ������Ŀ
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getActiveNewsNum()	throws PlatformException;
	/**
	 * �õ�ĳ�����ŵ���ϸ��Ϣ
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract CampusNews getNews(String id) throws PlatformException;

	public abstract CampusNews getPreViewNews(String id) throws PlatformException;
 
	/**
	 * �õ����Ϊnews_id�����ŵ�ǰpreNum����ͬ���͵Ļ������
	 * 
	 * @param news_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPreActiveNews(String news_id, int preNum)
			throws PlatformException;

	/**
	 * �õ����Ϊnews_id�����ŵĺ�nextNum����ͬ���͵Ļ������
	 * 
	 * @param news_id
	 * @param nextNum
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getNextActiveNews(String news_id, int nextNum)
			throws PlatformException;

 

	/**
	 * �õ�newsTypeId�����µ����num�����ͨ�����б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecentActiveNews()
			throws PlatformException;
	public abstract List getRecentActiveNews(int num) throws PlatformException ;
	/**
	 * ���н�ѧվ�б�
	 * 
	 * @return ��ѧվ�б�
	 * @throws NoRightException
	 */
	public abstract List getAllSites() throws NoRightException;

	/**
	 * �����ҳ��ʾ��ѧվ�б�
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract List getShowSites() throws NoRightException;

	public abstract List getShowSitesByDiqu(String diquId)
			throws NoRightException;

	public abstract BasicEntityManage createBasicEntityManage()
			throws NoRightException,PlatformException;

	public abstract int addNewsReadCount(String newsId)
			throws PlatformException;

	public abstract int setNewsReadCount(String newsId, int count)
			throws PlatformException;
	
	public abstract List getTopPop() throws PlatformException;

	/**
	 * ��ȡ�����Ķt���ͬʱ���Ķt������1
	 * 
	 * @param newsId
	 * @return
	 * @throws PlatformException
	 */
	public int getNewsReadCount(String newsId, ServletRequest req)
			throws PlatformException {
		int count = 0;
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		if (newsReadCount == null)
			newsReadCount = new HashMap();
		if (newsReadCount.containsKey(newsId)) {
			count = ((Integer) newsReadCount.get(newsId)).intValue();
			// ��ʱ����ˢ��
			if (true || session.isNew()) {
				count++;
				newsReadCount.put(newsId, new Integer(count));
			}
		} else {
			CampusNews news = getNews(newsId);
			count = news.getReadCount();
			if (true || session.isNew()) {
				count++;
			}
			newsReadCount.put(newsId, new Integer(count));
		}
		if (count != 0 && count % READ_COUNT_STEP_TO_SAVE == 0) {
			if (true || session.isNew()) {
				Log.setDebug("getNewsReadCount:" + count);
				setNewsReadCount(newsId, count);
			}
		}
		return count;
	}
	
}
