package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ExamRoom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exam_room")
public class ExamRoom implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 205095790441518293L;
	// Fields
	private Integer id;
	private String roomIndex;
	private String roomAddress;
	private String examnationTime;
	private String examnationInformation;
	private ExamInformation exam;

	private Short status;

	private String arrivaltime;

	private Integer creator;
	private Timestamp createtime;

	private String examRoomInfo;
	//考场类型
	private String roomType;
	//监考注意事项
	private String invigilationNotice;

	// Constructors

	/** default constructor */
	public ExamRoom() {
	}

	/** minimal constructor */
	public ExamRoom(Integer id, String roomIndex, String roomAddress, String examnationTime,
			String examnationInformation, Short status, ExamInformation exam, Integer creator, Timestamp createtime,
			String arrivaltime, String examRoomInfo,String roomType,String invigilationNotice) {
		super();
		this.id = id;
		this.roomIndex = roomIndex;
		this.roomAddress = roomAddress;
		this.examnationTime = examnationTime;
		this.examnationInformation = examnationInformation;
		this.exam = exam;
		this.status = status;
		this.creator = creator;
		this.createtime = createtime;
		this.arrivaltime = arrivaltime;
		this.examRoomInfo = examRoomInfo;
		this.roomType = roomType;
		this.invigilationNotice = invigilationNotice;
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

	@Column(name = "ROOM_INDEX", nullable = false)
	public String getRoomIndex() {
		return roomIndex;
	}

	public void setRoomIndex(String roomIndex) {
		this.roomIndex = roomIndex;
	}

	@Column(name = "ROOM_ADDRESS", nullable = false, length = 30)
	public String getRoomAddress() {
		return roomAddress;
	}

	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}

	@Column(name = "EXAMINATION_TIME", nullable = true, length = 30)
	public String getExamnationTime() {
		return examnationTime;
	}

	public void setExamnationTime(String examnationTime) {
		this.examnationTime = examnationTime;
	}

	@Column(name = "EXAMINATION_INFORMATION", nullable = true, length = 50)
	public String getExamnationInformation() {
		return examnationInformation;
	}

	public void setExamnationInformation(String examnationInformation) {
		this.examnationInformation = examnationInformation;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EXAM", nullable = false)
	public ExamInformation getExam() {
		return exam;
	}

	public void setExam(ExamInformation exam) {
		this.exam = exam;
	}

	@Column(name = "STATUS", nullable = false, length = 4)
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "ARRIVALTIME", nullable = true)
	public String getArrivaltime() {
		return arrivaltime;
	}

	public void setArrivaltime(String arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

	@Column(name = "EXAMROOMINFO")
	public String getExamRoomInfo() {
		return examRoomInfo;
	}

	public void setExamRoomInfo(String examRoomInfo) {
		this.examRoomInfo = examRoomInfo;
	}
	
    @Column(name="ROOM_TYPE",length = 50)
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	@Column(name = "INVIGILATION_NOTICE")
	public String getInvigilationNotice() {
		return invigilationNotice;
	}

	public void setInvigilationNotice(String invigilationNotice) {
		this.invigilationNotice = invigilationNotice;
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