package com.whaty.platform.campus.user;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 * 
 */
public abstract class CampusInfoManagerPriv {

	private String managerId;

	private String newsTypeId;

	private List newsTypesGet = new ArrayList();

	private List newsTypesAdd = new ArrayList();

	private List newsTypesUpdate = new ArrayList();

	private List newsTypesDelete = new ArrayList();

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewsTypeId(String newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

	public int getNews = 0;

	public int addNews = 0;

	public int updateNews = 0;

	public int deleteNews = 0;

	public int lockNews = 0;

	public int topNews = 0;

	public int addNewsType = 0;

	public int updateNewsType = 0;

	public int deleteNewsType = 0;

	public int appointNewsTypeManager = 0;
	
	public int getNewsType = 1;
	
	public int popNews = 0;
	
	public int confirmNews = 0;
	
	public int copyNews = 0;
	
	/***************************************************************************
	 * ���Ź���Ȩ�� *
	 **************************************************************************/
	public int sendSms = 1;

	public int getSms = 1;

	public int updateSms = 1;

	public int deleteSms = 1;

	public int checkSms = 1;

	public int addSms = 1;

	public int batchImportMobiles = 1; // �Ƿ�������������ƶ�����

	public int rejectSms = 1;// ���ض���

	public int getSmsStatistic = 1;// ��������ͳ��

	/***************************************************************************
	 * ��ȡ�������ݡ�ѧ����Ϣ����Ȩ�� *
	 **************************************************************************/
	public int getEduType = 1;

	public int getGrade = 1;

	public int getMajor = 1;

	public int getSite = 1;
	
	public int getStudent = 1;
	
	public int getRecruitBatch = 1;
	
	public int getPassRecruitStudent = 1;
	
	public int getTeacher = 1;
	
	public List getNewsTypesAdd() {
		return newsTypesAdd;
	}

	public void setNewsTypesAdd(List newsTypesAdd) {
		this.newsTypesAdd = newsTypesAdd;
	}

	public List getNewsTypesDelete() {
		return newsTypesDelete;
	}

	public void setNewsTypesDelete(List newsTypesDelete) {
		this.newsTypesDelete = newsTypesDelete;
	}

	public List getNewsTypesGet() {
		return newsTypesGet;
	}

	public void setNewsTypesGet(List newsTypesGet) {
		this.newsTypesGet = newsTypesGet;
	}

	public List getNewsTypesUpdate() {
		return newsTypesUpdate;
	}

	public void setNewsTypesUpdate(List newsTypesUpdate) {
		this.newsTypesUpdate = newsTypesUpdate;
	}

	public String toInSql(List list) {
		if (list == null && list.size() == 0)
			return null;
		else {
			String in = " ";
			Iterator it = list.iterator();
			while (it.hasNext()) {
				in += "'" + (String) it.next() + "',";
			}
			in = in.substring(0, in.length() - 1) + " ";
			return in;
		}
	}

	public abstract int putInfomanagerPriv(String userId, String siteId)
			throws PlatformException;

}
