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


@Entity
@Table(name = "teacher_training_replace")
public class TeacherTrainingReplace implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Teacher teacher;
    private TeacherTrainingRecords bereplacedttr;
    private Short replaceStatus;
    private Integer creator;
    private Timestamp creattime;
    private Integer project;
    private Short subject;
    private Integer trainCollege;
    private String replaceReason;
    private String nopassReason;
//    private Project project;
//    private TrainingSubject trainingSubject;
//    private Integer classes;

    // Constructors

    /** default constructor */
    public TeacherTrainingReplace()
    {
    }

    /** full constructor */
	public TeacherTrainingReplace(Integer id, Teacher teacher,
			TeacherTrainingRecords beReplacedTTR, Short replaceStatus,
			Integer creator, Timestamp creattime, Integer project,
			Short subject, Integer trainCollege) {
		super();
		this.id = id;
		this.teacher = teacher;
		this.bereplacedttr = beReplacedTTR;
		this.replaceStatus = replaceStatus;
		this.creator = creator;
		this.creattime = creattime;
		this.project = project;
		this.subject = subject;
		this.trainCollege = trainCollege;
	}
	
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPLACE_TTR", nullable = false)
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BE_REPLACED_TTR", nullable = false)
	public TeacherTrainingRecords getBereplacedttr() {
		return bereplacedttr;
	}

	public void setBereplacedttr(TeacherTrainingRecords bereplacedttr) {
		this.bereplacedttr = bereplacedttr;
	}

	@Column(name = "REPLACE_STATUS", nullable = false)
	public Short getReplaceStatus() {
		return replaceStatus;
	}

	public void setReplaceStatus(Short replaceStatus) {
		this.replaceStatus = replaceStatus;
	}

	@Column(name = "CREATOR", nullable = false)
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "CREAT_TIME", nullable = false)
	public Timestamp getCreattime() {
		return creattime;
	}

	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}

	@Column(name = "PROJECT", nullable = false)
	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	@Column(name = "SUBJECT", nullable = false)
	public Short getSubject() {
		return subject;
	}

	public void setSubject(Short subject) {
		this.subject = subject;
	}

	@Column(name = "TRAINING_COLLEGE", nullable = false)
	public Integer getTrainCollege() {
		return trainCollege;
	}

	public void setTrainCollege(Integer trainCollege) {
		this.trainCollege = trainCollege;
	}

	
	@Column(name = "REPLACE_REASON", nullable = false)
	public String getReplaceReason() {
		return replaceReason;
	}
	

	public void setReplaceReason(String replaceReason) {
		this.replaceReason = replaceReason;
	}
	
	@Column(name = "CHECKNOPASS_REASON")

	public String getNopassReason() {
		return nopassReason;
	}
	

	public void setNopassReason(String nopassReason) {
		this.nopassReason = nopassReason;
	}
    

}