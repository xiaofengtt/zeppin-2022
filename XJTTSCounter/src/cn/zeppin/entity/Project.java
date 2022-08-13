package cn.zeppin.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Project entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project")
public class Project implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Psq psqByProjectJudgePsq;
    private Document document;
    private Psq psqByEvaluationTeacherPsq;
    private Psq psqByEvaluationTrainingPsq;
    private Psq psqByProjectSummarizePsq;
    private ProjectType projectType;
    private String name;
    private String shortname;
    private String year;
    private Short traintype;
    private Short subjectMax;
    private Timestamp timeup;
    private Double funds;
    private Short fundsType;
    private Integer number;
    private Boolean restrictCollege;
    private Boolean restrictSubject;
    private Short status;
    private Integer creator;
    private Timestamp creattime;
    private String remark;
    private Short enrollType;
    private Boolean isTest;
    private Document redHeadDocument;
    
    private ProjectGroup projectGroup;
    private Short index;
    
    private Short isAdvance;
    
    private Timestamp endtime;
    
    private Set<ProjectSubjectRange> projectSubjectRanges = new HashSet<ProjectSubjectRange>(
	    0);
    private Set<AssignTeacherTask> assignTeacherTasks = new HashSet<AssignTeacherTask>(
	    0);
    private Set<ProjectCollegeRange> projectCollegeRanges = new HashSet<ProjectCollegeRange>(
	    0);
    private Set<Submit> submits = new HashSet<Submit>(0);
    private Set<ProjectApply> projectApplies = new HashSet<ProjectApply>(0);
    private Set<TeacherTrainingRecords> teacherTrainingRecordses = new HashSet<TeacherTrainingRecords>(
	    0);

    // Constructors

    /** default constructor */
    public Project()
    {
    }

    /** minimal constructor */
    public Project(ProjectType projectType, String name, String shortname,
	    String year, Short traintype, Short subjectMax, Timestamp timeup,
	    Double funds, Short fundsType, Integer number, Boolean restrictCollege,
	    Boolean restrictSubject, Short status, Integer creator,
	    Timestamp creattime)
    {
	this.projectType = projectType;
	this.name = name;
	this.shortname = shortname;
	this.year = year;
	this.traintype = traintype;
	this.subjectMax = subjectMax;
	this.timeup = timeup;
	this.funds = funds;
	this.fundsType = fundsType;
	this.number = number;
	this.restrictCollege = restrictCollege;
	this.restrictSubject = restrictSubject;
	this.status = status;
	this.creator = creator;
	this.creattime = creattime;
    }

    /** full constructor */
    public Project(Psq psqByProjectJudgePsq, Document document, Document redHeadDocument,
	    Psq psqByEvaluationTeacherPsq, Psq psqByEvaluationTrainingPsq,
	    ProjectType projectType, String name, String shortname,
	    String year, Short traintype, Short subjectMax, Timestamp timeup,
	    Double funds, Short fundsType, Integer number, Boolean restrictCollege,
	    Boolean restrictSubject, Short status, Integer creator,
	    Timestamp creattime, String remark,
	    Set<ProjectSubjectRange> projectSubjectRanges,
	    Set<AssignTeacherTask> assignTeacherTasks,
	    Set<ProjectCollegeRange> projectCollegeRanges, Set<Submit> submits,
	    Set<ProjectApply> projectApplies,
	    Set<TeacherTrainingRecords> teacherTrainingRecordses,
	    Short isAdvance,
	    Timestamp endtime)
    {
	this.psqByProjectJudgePsq = psqByProjectJudgePsq;
	this.document = document;
	this.redHeadDocument = redHeadDocument;
	this.psqByEvaluationTeacherPsq = psqByEvaluationTeacherPsq;
	this.psqByEvaluationTrainingPsq = psqByEvaluationTrainingPsq;
	this.projectType = projectType;
	this.name = name;
	this.shortname = shortname;
	this.year = year;
	this.traintype = traintype;
	this.subjectMax = subjectMax;
	this.timeup = timeup;
	this.funds = funds;
	this.fundsType = fundsType;
	this.number = number;
	this.restrictCollege = restrictCollege;
	this.restrictSubject = restrictSubject;
	this.status = status;
	this.creator = creator;
	this.creattime = creattime;
	this.remark = remark;
	this.projectSubjectRanges = projectSubjectRanges;
	this.assignTeacherTasks = assignTeacherTasks;
	this.projectCollegeRanges = projectCollegeRanges;
	this.submits = submits;
	this.projectApplies = projectApplies;
	this.teacherTrainingRecordses = teacherTrainingRecordses;
	this.isAdvance = isAdvance;
	this.endtime = endtime;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_JUDGE_PSQ")
    public Psq getPsqByProjectJudgePsq()
    {
	return this.psqByProjectJudgePsq;
    }

    public void setPsqByProjectJudgePsq(Psq psqByProjectJudgePsq)
    {
	this.psqByProjectJudgePsq = psqByProjectJudgePsq;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_SUMMARIZE_PSQ")
    public Psq getPsqByProjectSummarizePsq()
    {
	return this.psqByProjectSummarizePsq;
    }

    public void setPsqByProjectSummarizePsq(Psq psqByProjectSummarizePsq)
    {
	this.psqByProjectSummarizePsq = psqByProjectSummarizePsq;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_APPLY_TEMPLATE")
    public Document getDocument()
    {
	return this.document;
    }

    public void setDocument(Document document)
    {
	this.document = document;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RED_HEAD_DOCUMENT")
    public Document getRedHeadDocument()
    {
	return this.redHeadDocument;
    }

    public void setRedHeadDocument(Document redHeadDocument)
    {
	this.redHeadDocument = redHeadDocument;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVALUATION_TEACHER_PSQ")
    public Psq getPsqByEvaluationTeacherPsq()
    {
	return this.psqByEvaluationTeacherPsq;
    }

    public void setPsqByEvaluationTeacherPsq(Psq psqByEvaluationTeacherPsq)
    {
	this.psqByEvaluationTeacherPsq = psqByEvaluationTeacherPsq;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVALUATION_TRAINING_PSQ")
    public Psq getPsqByEvaluationTrainingPsq()
    {
	return this.psqByEvaluationTrainingPsq;
    }

    public void setPsqByEvaluationTrainingPsq(Psq psqByEvaluationTrainingPsq)
    {
	this.psqByEvaluationTrainingPsq = psqByEvaluationTrainingPsq;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE", nullable = false)
    public ProjectType getProjectType()
    {
	return this.projectType;
    }

    public void setProjectType(ProjectType projectType)
    {
	this.projectType = projectType;
    }

    @Column(name = "NAME", nullable = false, length = 200)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "SHORTNAME", nullable = false, length = 200)
    public String getShortname()
    {
	return this.shortname;
    }

    public void setShortname(String shortname)
    {
	this.shortname = shortname;
    }

    @Column(name = "YEAR", nullable = false, length = 4)
    public String getYear()
    {
	return this.year;
    }

    public void setYear(String year)
    {
	this.year = year;
    }

    @Column(name = "TRAINTYPE", nullable = false)
    public Short getTraintype()
    {
	return this.traintype;
    }

    public void setTraintype(Short traintype)
    {
	this.traintype = traintype;
    }

    @Column(name = "SUBJECT_MAX", nullable = false)
    public Short getSubjectMax()
    {
	return this.subjectMax;
    }

    public void setSubjectMax(Short subjectMax)
    {
	this.subjectMax = subjectMax;
    }

    @Column(name = "TIMEUP", nullable = false, length = 19)
    public Timestamp getTimeup()
    {
	return this.timeup;
    }

    public void setTimeup(Timestamp timeup)
    {
	this.timeup = timeup;
    }

    @Column(name = "FUNDS", nullable = false, precision = 10)
    public Double getFunds()
    {
	return this.funds;
    }

    public void setFunds(Double funds)
    {
	this.funds = funds;
    }

    @Column(name = "FUNDS_TYPE", nullable = false)
    public Short getFundsType()
    {
	return this.fundsType;
    }

    public void setFundsType(Short fundsType)
    {
	this.fundsType = fundsType;
    }
    
    @Column(name = "number", nullable = false)
    public Integer getNumber()
    {
	return this.number;
    }

    public void setNumber(Integer number)
    {
	this.number = number;
    }

    @Column(name = "RESTRICT_COLLEGE", nullable = false)
    public Boolean getRestrictCollege()
    {
	return this.restrictCollege;
    }

    public void setRestrictCollege(Boolean restrictCollege)
    {
	this.restrictCollege = restrictCollege;
    }

    @Column(name = "RESTRICT_SUBJECT", nullable = false)
    public Boolean getRestrictSubject()
    {
	return this.restrictSubject;
    }

    public void setRestrictSubject(Boolean restrictSubject)
    {
	this.restrictSubject = restrictSubject;
    }

    @Column(name = "STATUS", nullable = false)
    public Short getStatus()
    {
	return this.status;
    }

    public void setStatus(Short status)
    {
	this.status = status;
    }

    @Column(name = "CREATOR", nullable = false)
    public Integer getCreator()
    {
	return this.creator;
    }

    public void setCreator(Integer creator)
    {
	this.creator = creator;
    }

    @Column(name = "CREATTIME", nullable = false, length = 19)
    public Timestamp getCreattime()
    {
	return this.creattime;
    }

    public void setCreattime(Timestamp creattime)
    {
	this.creattime = creattime;
    }

    @Column(name = "REMARK", length = 200)
    public String getRemark()
    {
	return this.remark;
    }

    public void setRemark(String remark)
    {
	this.remark = remark;
    }

    @Column(name = "ENROLL_TYPE")
	public Short getEnrollType() {
		return enrollType;
	}

	public void setEnrollType(Short enrollType) {
		this.enrollType = enrollType;
	}

	@OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "project")
    public Set<ProjectSubjectRange> getProjectSubjectRanges()
    {
	return this.projectSubjectRanges;
    }

    public void setProjectSubjectRanges(
	    Set<ProjectSubjectRange> projectSubjectRanges)
    {
	this.projectSubjectRanges = projectSubjectRanges;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "project")
    public Set<AssignTeacherTask> getAssignTeacherTasks()
    {
	return this.assignTeacherTasks;
    }

    public void setAssignTeacherTasks(Set<AssignTeacherTask> assignTeacherTasks)
    {
	this.assignTeacherTasks = assignTeacherTasks;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "project")
    public Set<ProjectCollegeRange> getProjectCollegeRanges()
    {
	return this.projectCollegeRanges;
    }

    public void setProjectCollegeRanges(
	    Set<ProjectCollegeRange> projectCollegeRanges)
    {
	this.projectCollegeRanges = projectCollegeRanges;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "project")
    public Set<Submit> getSubmits()
    {
	return this.submits;
    }

    public void setSubmits(Set<Submit> submits)
    {
	this.submits = submits;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "project")
    public Set<ProjectApply> getProjectApplies()
    {
	return this.projectApplies;
    }

    public void setProjectApplies(Set<ProjectApply> projectApplies)
    {
	this.projectApplies = projectApplies;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "project")
    public Set<TeacherTrainingRecords> getTeacherTrainingRecordses()
    {
	return this.teacherTrainingRecordses;
    }

    public void setTeacherTrainingRecordses(
	    Set<TeacherTrainingRecords> teacherTrainingRecordses)
    {
	this.teacherTrainingRecordses = teacherTrainingRecordses;
    }

    @Column(name = "ISTEST")
	public Boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(Boolean isTest) {
		this.isTest = isTest;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_GROUP")
	public ProjectGroup getProjectGroup() {
		return projectGroup;
	}

	public void setProjectGroup(ProjectGroup projectGroup) {
		this.projectGroup = projectGroup;
	}
	
	@Column(name = "PROJECT_INDEX")
	public Short getIndex() {
		return index;
	}

	public void setIndex(Short index) {
		this.index = index;
	}

	
	@Column(name = "ISADVANCE", nullable = false)
	public Short getIsAdvance() {
		return isAdvance;
	}
	

	public void setIsAdvance(Short isAdvance) {
		this.isAdvance = isAdvance;
	}

	
	@Column(name = "END_TIME", length = 19)
	public Timestamp getEndtime() {
		return endtime;
	}
	

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	
}