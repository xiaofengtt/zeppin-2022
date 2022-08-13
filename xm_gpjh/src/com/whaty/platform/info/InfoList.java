/*
 * NewsList.java
 *
 * Created on 2005��4��10��, ����8:31
 */

package com.whaty.platform.info;

import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.util.Page;

/**
 * ��Ҫ֪ͨ����������б�ӿ�
 * 
 * @author Ligang
 */
public interface InfoList {

	/**
	 * ���ȫ��ģ����
	 * 
	 * @return ��ѧվ��
	 */
	public int getTemplatesNum(List searchproperty) throws PlatformException;

	/**
	 * ���ȫ��ģ���б�
	 * 
	 * @param page
	 * @return ģ���б�
	 */
	public List getTemplates(Page page, List searchproperty, List orderproperty)
			throws PlatformException;

	/**
	 * ���ȫ������������
	 * 
	 * @return ����������
	 */
	public int getNewsTypesNum(List searchproperty) throws PlatformException;

	/**
	 * ���ȫ�����������б�
	 * 
	 * @param page
	 * @return ���������б�
	 */
	public List getNewsTypes(Page page, List searchproperty, List orderporperty)
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

	/**
	 * �õ�ĳ�������µ�ȫ��������Ŀ
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getNewsListNum(List searchproperty)
			throws PlatformException;

	public abstract List getNewsListForTeacher(Page page, Teacher teacher)
			throws PlatformException;

	public abstract int getNewsListNumForTeacher(Teacher teacher)
			throws PlatformException;

	public abstract List getNewsListForStudent(Page page, Student student)
			throws PlatformException;

	public abstract int getNewsListNumForStudent(Student student)
			throws PlatformException;

	public abstract int getNewsByTagNum(String tag) throws PlatformException;

	public abstract List getNewsByTag(Page page, String tag)
			throws PlatformException;

	public abstract List getNewsByTag(String tag, int num)
			throws PlatformException;

	public abstract Map getNewsByTags(Map tagAndNumber)
			throws PlatformException;

	/**
	 * Ϊû�з�վ֪ͨ�������͵�վ�㴴��֪ͨ���������Լ���ǩ ��ǩ����site_id + "��վ֪ͨ";
	 * 
	 * ��վ֪ͨ�ĸ�����Ϊһ�� tag="��վ֪ͨ"����������
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public int createNewsTypeForSiteNotice() throws PlatformException;

	public List getRightNewsTypes(String userId) throws PlatformException;

	
	/*
	public List getNews(Page page, String tag) throws PlatformException;

	public List getNews(String tag, int num) throws PlatformException;

	public int getNewsNum(String tag) throws PlatformException;

	public Map getNews(Map tagAndNumber) throws PlatformException;
	*/
}
