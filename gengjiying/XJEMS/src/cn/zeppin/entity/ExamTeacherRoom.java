package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ExamTeacherRoom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exam_teacher_room")
public class ExamTeacherRoom implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 3726802598639091476L;
	/**
	 * 
	 */
	private Integer id;
	// private ExamTeacherRecords records;
	private ExamInformation exam;
	private ExamRoom room;
	private InvigilationTeacher teacher;
	private Short isChief;
	private Short isMixed;
	private Short isConfirm;
	private Timestamp confirtime;

	private Integer operater;
	private Short isAuto;

	private Integer creator;
	private Timestamp createtime;

	private Short status;

	private Timestamp applytime;
	private Integer credit;
	private String reason;

	private Short isFirstApply;
	private String remark;
	// 禁用原因
	private String disableReason;
	//是否被取消二次确认
	private Short isConfirmCanceled;
	//可参加考试的开始时间
	private Timestamp startTime;
	//可参加考试的结束时间
	private Timestamp endTime;

	// Constructors

	/** default constructor */
	public ExamTeacherRoom() {
	}

	/** minimal constructor */
	public ExamTeacherRoom(Integer id, ExamInformation exam, ExamRoom room, InvigilationTeacher teacher, Short isChief,
			Short isMixed, Short isConfirm, Timestamp confirtime, Integer operater, Short isAuto, Integer creator,
			Timestamp createtime, Short status, Timestamp applytime, Integer credit, String reason, Short isFirstApply,
			String remark, String disableReason,Short isConfirmCanceled,Timestamp startTime,Timestamp endTime) {
		super();
		this.id = id;
		this.exam = exam;
		this.room = room;
		this.teacher = teacher;
		this.isChief = isChief;
		this.isMixed = isMixed;
		this.isConfirm = isConfirm;
		this.confirtime = confirtime;
		this.operater = operater;
		this.isAuto = isAuto;
		this.creator = creator;
		this.createtime = createtime;
		this.status = status;
		this.applytime = applytime;
		this.credit = credit;
		this.reason = reason;
		this.isFirstApply = isFirstApply;
		this.remark = remark;
		this.disableReason = disableReason;
		this.isConfirmCanceled = isConfirmCanceled;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "CREATOR", nullable = false, length = 11)
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EXAM", nullable = false)
	public ExamInformation getExam() {
		return exam;
	}

	public void setExam(ExamInformation exam) {
		this.exam = exam;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EXAM_ROOM")
	public ExamRoom getRoom() {
		return room;
	}

	public void setRoom(ExamRoom room) {
		this.room = room;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "TEACHER", nullable = false)
	public InvigilationTeacher getTeacher() {
		return teacher;
	}

	public void setTeacher(InvigilationTeacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "IS_CHIEF", length = 4)
	public Short getIsChief() {
		return isChief;
	}

	public void setIsChief(Short isChief) {
		this.isChief = isChief;
	}

	@Column(name = "IS_MIXED", length = 4)
	public Short getIsMixed() {
		return isMixed;
	}

	public void setIsMixed(Short isMixed) {
		this.isMixed = isMixed;
	}

	@Column(name = "IS_CONFIRM", nullable = false, length = 4)
	public Short getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Short isConfirm) {
		this.isConfirm = isConfirm;
	}

	@Column(name = "CONFIRM_TIME")
	public Timestamp getConfirtime() {
		return confirtime;
	}

	public void setConfirtime(Timestamp confirtime) {
		this.confirtime = confirtime;
	}

	@Column(name = "STATUS", nullable = false, length = 4)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "APPLY_TIME", length = 19)
	public Timestamp getApplytime() {
		return this.applytime;
	}

	public void setApplytime(Timestamp applytime) {
		this.applytime = applytime;
	}

	@Column(name = "CREDIT", length = 4)
	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	@Column(name = "REASON")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "IS_FIRST_APPLY", nullable = false, length = 4)
	public Short getIsFirstApply() {
		return isFirstApply;
	}

	public void setIsFirstApply(Short isFirstApply) {
		this.isFirstApply = isFirstApply;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "OPERATER", length = 11)
	public Integer getOperater() {
		return operater;
	}

	public void setOperater(Integer operater) {
		this.operater = operater;
	}

	@Column(name = "IS_AUTO", nullable = false, length = 4)
	public Short getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(Short isAuto) {
		this.isAuto = isAuto;
	}

	@Column(name = "DISABLEREASON")
	public String getDisableReason() {
		return disableReason;
	}

	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}

	
	@Column(name = "IS_CONFIRM_CANCELED", nullable = false, length = 4)
	public Short getIsConfirmCanceled() {
		return isConfirmCanceled;
	}

	public void setIsConfirmCanceled(Short isConfirmCanceled) {
		this.isConfirmCanceled = isConfirmCanceled;
	}

	@Column(name = "START_TIME")
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	@Column(name = "END_TIME")
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	/**
	 * cacheKey
	 * 
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}

}