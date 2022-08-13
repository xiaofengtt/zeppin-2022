package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.Site;

public abstract class RecruitTestSite implements Items {
	private Site site;

	private String id = "";

	private String name = "";

	private String note = "";

	private List subSiteList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public List getSubSiteList() {
		return subSiteList;
	}

	public void setSubSiteList(List subSiteList) {
		this.subSiteList = subSiteList;
	}

	/**
	 * ͳ�Ʊ����㱾���������ܿ�������
	 * 
	 * @param testbatch
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStudentsNum(RecruitTestBatch testbatch)
			throws PlatformException;

	/**
	 * ͳ�Ʊ����㿼�Ա��������������ʣ����λ��
	 * 
	 * @param testbatch
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getScarcityStudentsNum(RecruitTestBatch testbatch)
			throws PlatformException;

	/**
	 * Ϊ�ÿ�����ӽ�ѧվ
	 * 
	 * @return 0Ϊʧ�ܣ�����0��ɹ�
	 */
	public abstract int addSubSites(List subSiteList);

	/**
	 * �Ӹÿ���ɾ����ѧվ
	 * 
	 * @return 0Ϊʧ�ܣ�����0��ɹ�
	 */
	public abstract int deleteSubSites(List subSiteList);

	/**
	 * �ÿ����Ƿ��Լ����俼��
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasTestRooms();

	/**
	 * �ÿ����Ƿ������ѧվ
	 * 
	 * @return 0Ϊû�У�����0����
	 */
	public abstract int isHasSubSites();

}
