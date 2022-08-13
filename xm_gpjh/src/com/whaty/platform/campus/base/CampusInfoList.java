package com.whaty.platform.campus.base;



import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

/**
 * ְҵ��չ����б�ӿ�
 * 
 * @author Ligang
 */
public interface CampusInfoList {
	
	/**
	 *  ��������
	 * @param searchproperty
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getNewsListNum(List searchproperty)
			throws PlatformException;

	/**
	 * �����б�
	 * @param searchproperty
	 * @return
	 * @throws PlatformException
	 */

	public abstract List getNewsList(Page page,List searchproperty, List orderProperty)
			throws PlatformException;
	/**
	 * ��ǰ���ŵĺ�����Ϣ
	 * @param news_id
	 * @param nextNum
	 * @return
	 * @throws PlatformException
	 */
	public List getNextActiveNews(String news_id, int nextNum) throws PlatformException;
	/**
	 * ��ǰ���ŵ�ǰ������Ϣ
	 * @param news_id
	 * @param preNum
	 * @return
	 * @throws PlatformException
	 */
	public List getPreActiveNews(String news_id, int preNum) throws PlatformException;

}
