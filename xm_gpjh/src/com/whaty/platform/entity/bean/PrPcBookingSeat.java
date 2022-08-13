package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PrPcBookingSeat entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcBookingSeat extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrPcOpencourse prPcOpencourse;
	private Date courseDatetime;
	private Date bookingBeginDatetime;
	private Date bookingEndDatetime;
	private Long limitNum;
	private Set prPcStuBookings = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrPcBookingSeat() {
	}

	/** full constructor */
	public PrPcBookingSeat(PrPcOpencourse prPcOpencourse, Date courseDatetime,
			Date bookingBeginDatetime, Date bookingEndDatetime, Long limitNum,
			Set prPcStuBookings) {
		this.prPcOpencourse = prPcOpencourse;
		this.courseDatetime = courseDatetime;
		this.bookingBeginDatetime = bookingBeginDatetime;
		this.bookingEndDatetime = bookingEndDatetime;
		this.limitNum = limitNum;
		this.prPcStuBookings = prPcStuBookings;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrPcOpencourse getPrPcOpencourse() {
		return this.prPcOpencourse;
	}

	public void setPrPcOpencourse(PrPcOpencourse prPcOpencourse) {
		this.prPcOpencourse = prPcOpencourse;
	}

	public Date getCourseDatetime() {
		return this.courseDatetime;
	}

	public void setCourseDatetime(Date courseDatetime) {
		this.courseDatetime = courseDatetime;
	}

	public Date getBookingBeginDatetime() {
		return this.bookingBeginDatetime;
	}

	public void setBookingBeginDatetime(Date bookingBeginDatetime) {
		this.bookingBeginDatetime = bookingBeginDatetime;
	}

	public Date getBookingEndDatetime() {
		return this.bookingEndDatetime;
	}

	public void setBookingEndDatetime(Date bookingEndDatetime) {
		this.bookingEndDatetime = bookingEndDatetime;
	}

	public Long getLimitNum() {
		return this.limitNum;
	}

	public void setLimitNum(Long limitNum) {
		this.limitNum = limitNum;
	}

	public Set getPrPcStuBookings() {
		return this.prPcStuBookings;
	}

	public void setPrPcStuBookings(Set prPcStuBookings) {
		this.prPcStuBookings = prPcStuBookings;
	}

}