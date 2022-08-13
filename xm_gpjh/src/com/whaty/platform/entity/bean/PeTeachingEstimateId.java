package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeTeachingEstimateId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTeachingEstimateId extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String courseid;
	private String qifa;
	private String shiyong;
	private String bangzhu;
	private String shijian;
	private String manyi;
	private String yijian;
	private String studentid;
	private Date publishtime;

	// Constructors

	/** default constructor */
	public PeTeachingEstimateId() {
	}

	/** full constructor */
	public PeTeachingEstimateId(String id, String courseid, String qifa,
			String shiyong, String bangzhu, String shijian, String manyi,
			String yijian, String studentid, Date publishtime) {
		this.id = id;
		this.courseid = courseid;
		this.qifa = qifa;
		this.shiyong = shiyong;
		this.bangzhu = bangzhu;
		this.shijian = shijian;
		this.manyi = manyi;
		this.yijian = yijian;
		this.studentid = studentid;
		this.publishtime = publishtime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseid() {
		return this.courseid;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getQifa() {
		return this.qifa;
	}

	public void setQifa(String qifa) {
		this.qifa = qifa;
	}

	public String getShiyong() {
		return this.shiyong;
	}

	public void setShiyong(String shiyong) {
		this.shiyong = shiyong;
	}

	public String getBangzhu() {
		return this.bangzhu;
	}

	public void setBangzhu(String bangzhu) {
		this.bangzhu = bangzhu;
	}

	public String getShijian() {
		return this.shijian;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public String getManyi() {
		return this.manyi;
	}

	public void setManyi(String manyi) {
		this.manyi = manyi;
	}

	public String getYijian() {
		return this.yijian;
	}

	public void setYijian(String yijian) {
		this.yijian = yijian;
	}

	public String getStudentid() {
		return this.studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public Date getPublishtime() {
		return this.publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PeTeachingEstimateId))
			return false;
		PeTeachingEstimateId castOther = (PeTeachingEstimateId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getCourseid() == castOther.getCourseid()) || (this
						.getCourseid() != null
						&& castOther.getCourseid() != null && this
						.getCourseid().equals(castOther.getCourseid())))
				&& ((this.getQifa() == castOther.getQifa()) || (this.getQifa() != null
						&& castOther.getQifa() != null && this.getQifa()
						.equals(castOther.getQifa())))
				&& ((this.getShiyong() == castOther.getShiyong()) || (this
						.getShiyong() != null
						&& castOther.getShiyong() != null && this.getShiyong()
						.equals(castOther.getShiyong())))
				&& ((this.getBangzhu() == castOther.getBangzhu()) || (this
						.getBangzhu() != null
						&& castOther.getBangzhu() != null && this.getBangzhu()
						.equals(castOther.getBangzhu())))
				&& ((this.getShijian() == castOther.getShijian()) || (this
						.getShijian() != null
						&& castOther.getShijian() != null && this.getShijian()
						.equals(castOther.getShijian())))
				&& ((this.getManyi() == castOther.getManyi()) || (this
						.getManyi() != null
						&& castOther.getManyi() != null && this.getManyi()
						.equals(castOther.getManyi())))
				&& ((this.getYijian() == castOther.getYijian()) || (this
						.getYijian() != null
						&& castOther.getYijian() != null && this.getYijian()
						.equals(castOther.getYijian())))
				&& ((this.getStudentid() == castOther.getStudentid()) || (this
						.getStudentid() != null
						&& castOther.getStudentid() != null && this
						.getStudentid().equals(castOther.getStudentid())))
				&& ((this.getPublishtime() == castOther.getPublishtime()) || (this
						.getPublishtime() != null
						&& castOther.getPublishtime() != null && this
						.getPublishtime().equals(castOther.getPublishtime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getCourseid() == null ? 0 : this.getCourseid().hashCode());
		result = 37 * result
				+ (getQifa() == null ? 0 : this.getQifa().hashCode());
		result = 37 * result
				+ (getShiyong() == null ? 0 : this.getShiyong().hashCode());
		result = 37 * result
				+ (getBangzhu() == null ? 0 : this.getBangzhu().hashCode());
		result = 37 * result
				+ (getShijian() == null ? 0 : this.getShijian().hashCode());
		result = 37 * result
				+ (getManyi() == null ? 0 : this.getManyi().hashCode());
		result = 37 * result
				+ (getYijian() == null ? 0 : this.getYijian().hashCode());
		result = 37 * result
				+ (getStudentid() == null ? 0 : this.getStudentid().hashCode());
		result = 37
				* result
				+ (getPublishtime() == null ? 0 : this.getPublishtime()
						.hashCode());
		return result;
	}

}