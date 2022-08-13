package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestLoreInfoId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestLoreInfoId extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date creatdate;
	private String content;
	private String loredir;
	private String createrid;
	private String active;

	// Constructors

	/** default constructor */
	public TestLoreInfoId() {
	}

	/** minimal constructor */
	public TestLoreInfoId(String id, String active) {
		this.id = id;
		this.active = active;
	}

	/** full constructor */
	public TestLoreInfoId(String id, String name, Date creatdate,
			String content, String loredir, String createrid, String active) {
		this.id = id;
		this.name = name;
		this.creatdate = creatdate;
		this.content = content;
		this.loredir = loredir;
		this.createrid = createrid;
		this.active = active;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatdate() {
		return this.creatdate;
	}

	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLoredir() {
		return this.loredir;
	}

	public void setLoredir(String loredir) {
		this.loredir = loredir;
	}

	public String getCreaterid() {
		return this.createrid;
	}

	public void setCreaterid(String createrid) {
		this.createrid = createrid;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TestLoreInfoId))
			return false;
		TestLoreInfoId castOther = (TestLoreInfoId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getCreatdate() == castOther.getCreatdate()) || (this
						.getCreatdate() != null
						&& castOther.getCreatdate() != null && this
						.getCreatdate().equals(castOther.getCreatdate())))
				&& ((this.getContent() == castOther.getContent()) || (this
						.getContent() != null
						&& castOther.getContent() != null && this.getContent()
						.equals(castOther.getContent())))
				&& ((this.getLoredir() == castOther.getLoredir()) || (this
						.getLoredir() != null
						&& castOther.getLoredir() != null && this.getLoredir()
						.equals(castOther.getLoredir())))
				&& ((this.getCreaterid() == castOther.getCreaterid()) || (this
						.getCreaterid() != null
						&& castOther.getCreaterid() != null && this
						.getCreaterid().equals(castOther.getCreaterid())))
				&& ((this.getActive() == castOther.getActive()) || (this
						.getActive() != null
						&& castOther.getActive() != null && this.getActive()
						.equals(castOther.getActive())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getCreatdate() == null ? 0 : this.getCreatdate().hashCode());
		result = 37 * result
				+ (getContent() == null ? 0 : this.getContent().hashCode());
		result = 37 * result
				+ (getLoredir() == null ? 0 : this.getLoredir().hashCode());
		result = 37 * result
				+ (getCreaterid() == null ? 0 : this.getCreaterid().hashCode());
		result = 37 * result
				+ (getActive() == null ? 0 : this.getActive().hashCode());
		return result;
	}

}