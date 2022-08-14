package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ExamInformation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exam_information")
public class ExamInformation implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 3726802598639091476L;
	/**
	 * 
	 */
	private Integer id;
	private String name;
	private Timestamp examStarttime;
	private Timestamp examEndtime;
	private Integer integral;
	private String information;
	private String invigilationContract;
	private Short status;
	private Integer creator;
	private Timestamp createtime;

	private Timestamp applyendtime;
	private Timestamp checkendtime;

	//申报注意事项
	private String applyNotice;
	//监考注意事项
	private String invigilationNotice;

	// Constructors

	/** default constructor */
	public ExamInformation() {
	}

	/** minimal constructor */
	public ExamInformation(Integer id, String name, Timestamp examStarttime, Timestamp examEndtime, Integer integral,
			String information, String invigilationContract, Short status, Integer creator, Timestamp createtime,
			Timestamp applyendtime, Timestamp checkendtime, String applyNotice, String invigilationNotice) {
		super();
		this.id = id;
		this.name = name;
		this.examStarttime = examStarttime;
		this.examEndtime = examEndtime;
		this.integral = integral;
		this.information = information;
		this.invigilationContract = invigilationContract;
		this.status = status;
		this.creator = creator;
		this.createtime = createtime;
		this.applyendtime = applyendtime;
		this.checkendtime = checkendtime;
		this.applyNotice = applyNotice;
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

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "EXAM_STARTTIME", nullable = false, length = 19)
	public Timestamp getExamStarttime() {
		return examStarttime;
	}

	public void setExamStarttime(Timestamp examStarttime) {
		this.examStarttime = examStarttime;
	}

	@Column(name = "EXAM_ENDTIME", nullable = false, length = 19)
	public Timestamp getExamEndtime() {
		return examEndtime;
	}

	public void setExamEndtime(Timestamp examEndtime) {
		this.examEndtime = examEndtime;
	}

	@Column(name = "INTEGRAL", nullable = false, length = 6)
	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	@Column(name = "INFORMATION", nullable = false, length = 1000)
	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Column(name = "INVIGILATION_CONTRACT", nullable = false, length = 1000)
	public String getInvigilationContract() {
		return invigilationContract;
	}

	public void setInvigilationContract(String invigilationContract) {
		this.invigilationContract = invigilationContract;
	}

	@Column(name = "CREATOR", nullable = false, length = 11)
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "APPLY_ENDTIME", nullable = false, length = 19)
	public Timestamp getApplyendtime() {
		return applyendtime;
	}

	public void setApplyendtime(Timestamp applyendtime) {
		this.applyendtime = applyendtime;
	}

	@Column(name = "CHECK_ENDTIME", nullable = false, length = 19)
	public Timestamp getCheckendtime() {
		return checkendtime;
	}

	public void setCheckendtime(Timestamp checkendtime) {
		this.checkendtime = checkendtime;
	}

	@Column(name = "APPLY_NOTICE", length = 1000)
	public String getApplyNotice() {
		return applyNotice;
	}

	public void setApplyNotice(String applyNotice) {
		this.applyNotice = applyNotice;
	}

	@Column(name = "INVIGILATION_NOTICE",length = 1000)
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