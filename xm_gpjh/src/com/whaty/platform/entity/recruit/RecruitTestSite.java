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
	 * 统计本考点本考试批次总考试人数
	 * 
	 * @param testbatch
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStudentsNum(RecruitTestBatch testbatch)
			throws PlatformException;

	/**
	 * 统计本考点考试本考试批次所需的剩余座位数
	 * 
	 * @param testbatch
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getScarcityStudentsNum(RecruitTestBatch testbatch)
			throws PlatformException;

	/**
	 * 为该考点添加教学站
	 * 
	 * @return 0为失败；大于0则成功
	 */
	public abstract int addSubSites(List subSiteList);

	/**
	 * 从该考点删除教学站
	 * 
	 * @return 0为失败；大于0则成功
	 */
	public abstract int deleteSubSites(List subSiteList);

	/**
	 * 该考点是否以及分配考场
	 * 
	 * @return 0为没有；大于0则有
	 */
	public abstract int isHasTestRooms();

	/**
	 * 该考点是否包含教学站
	 * 
	 * @return 0为没有；大于0则有
	 */
	public abstract int isHasSubSites();

}
