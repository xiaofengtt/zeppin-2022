/**
 * 
 */
package com.whaty.platform.entity.test;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.Site;


/**该类描述了考点，该类包装了平台的Site对象
 * @author chenjian
 *2006-4-25
 */
public abstract class TestSite {

	private Site site;
	
	private String testSiteId;
	
	private String testSiteTitle;
	
	private String testSiteNote;

	private String email;
	
	private String address;
	
	private String fax;
	
	private String linkMan;
	
	private String manager;
	
	private String phone;
	
	private String zipCode;
	

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
		this.setEmail(site.getEmail());
		this.setAddress(site.getAddress());
		this.setFax(site.getFax());
		this.setLinkMan(site.getLinkman());
		this.setManager(site.getManager());
		this.setPhone(site.getPhone());
		this.setZipCode(site.getZip_code());
	}

	public String getTestSiteId() {
		return testSiteId;
	}

	public void setTestSiteId(String testSiteId) {
		this.testSiteId = testSiteId;
	}

	public String getTestSiteNote() {
		return testSiteNote;
	}

	public void setTestSiteNote(String testSiteNote) {
		this.testSiteNote = testSiteNote;
	}

	public String getTestSiteTitle() {
		return testSiteTitle;
	}

	public void setTestSiteTitle(String testSiteTitle) {
		this.testSiteTitle = testSiteTitle;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
	/**该方法获得某个批次下在该考点的考试课程
	 * @param testBatch 批次
	 * @return 课程列表
	 * @throws PlatformException
	 */
	public abstract List getCourses(TestBatch testBatch) throws PlatformException;
	
	
}
