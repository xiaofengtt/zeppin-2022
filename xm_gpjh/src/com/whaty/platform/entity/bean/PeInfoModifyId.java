package com.whaty.platform.entity.bean;

/**
 * PeInfoModifyId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeInfoModifyId extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String compuPhoto;
	private String copyright;
	private String logoPhoto;
	private String pingtaiName;
	private String schoolName;

	// Constructors

	/** default constructor */
	public PeInfoModifyId() {
	}

	/** full constructor */
	public PeInfoModifyId(String id, String compuPhoto, String copyright,
			String logoPhoto, String pingtaiName, String schoolName) {
		this.id = id;
		this.compuPhoto = compuPhoto;
		this.copyright = copyright;
		this.logoPhoto = logoPhoto;
		this.pingtaiName = pingtaiName;
		this.schoolName = schoolName;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompuPhoto() {
		return this.compuPhoto;
	}

	public void setCompuPhoto(String compuPhoto) {
		this.compuPhoto = compuPhoto;
	}

	public String getCopyright() {
		return this.copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getLogoPhoto() {
		return this.logoPhoto;
	}

	public void setLogoPhoto(String logoPhoto) {
		this.logoPhoto = logoPhoto;
	}

	public String getPingtaiName() {
		return this.pingtaiName;
	}

	public void setPingtaiName(String pingtaiName) {
		this.pingtaiName = pingtaiName;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PeInfoModifyId))
			return false;
		PeInfoModifyId castOther = (PeInfoModifyId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getCompuPhoto() == castOther.getCompuPhoto()) || (this
						.getCompuPhoto() != null
						&& castOther.getCompuPhoto() != null && this
						.getCompuPhoto().equals(castOther.getCompuPhoto())))
				&& ((this.getCopyright() == castOther.getCopyright()) || (this
						.getCopyright() != null
						&& castOther.getCopyright() != null && this
						.getCopyright().equals(castOther.getCopyright())))
				&& ((this.getLogoPhoto() == castOther.getLogoPhoto()) || (this
						.getLogoPhoto() != null
						&& castOther.getLogoPhoto() != null && this
						.getLogoPhoto().equals(castOther.getLogoPhoto())))
				&& ((this.getPingtaiName() == castOther.getPingtaiName()) || (this
						.getPingtaiName() != null
						&& castOther.getPingtaiName() != null && this
						.getPingtaiName().equals(castOther.getPingtaiName())))
				&& ((this.getSchoolName() == castOther.getSchoolName()) || (this
						.getSchoolName() != null
						&& castOther.getSchoolName() != null && this
						.getSchoolName().equals(castOther.getSchoolName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37
				* result
				+ (getCompuPhoto() == null ? 0 : this.getCompuPhoto()
						.hashCode());
		result = 37 * result
				+ (getCopyright() == null ? 0 : this.getCopyright().hashCode());
		result = 37 * result
				+ (getLogoPhoto() == null ? 0 : this.getLogoPhoto().hashCode());
		result = 37
				* result
				+ (getPingtaiName() == null ? 0 : this.getPingtaiName()
						.hashCode());
		result = 37
				* result
				+ (getSchoolName() == null ? 0 : this.getSchoolName()
						.hashCode());
		return result;
	}

}