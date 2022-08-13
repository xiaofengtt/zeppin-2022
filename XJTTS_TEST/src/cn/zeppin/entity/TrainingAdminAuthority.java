package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "training_admin_authority")
public class TrainingAdminAuthority implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private TrainingAdmin trainingAdmin;
    private Project project;
    private TrainingSubject trainingSubject;
    private Integer classes;

    // Constructors

    /** default constructor */
    public TrainingAdminAuthority()
    {
    }

    /** full constructor */
    public TrainingAdminAuthority(TrainingAdmin trainingAdmin, Project project , TrainingSubject trainingSubject , Integer classes)
    {
	this.trainingAdmin = trainingAdmin;
	this.project = project;
	this.trainingSubject = trainingSubject;
	this.classes = classes;
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
    @JoinColumn(name = "TRAINING_ADMIN", nullable = false)
    public TrainingAdmin getTrainingAdmin()
    {
	return this.trainingAdmin;
    }

    public void setTrainingAdmin(TrainingAdmin trainingAdmin)
    {
	this.trainingAdmin = trainingAdmin;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT", nullable = false)
    public Project getProject()
    {
	return this.project;
    }

    public void setProject(Project project)
    {
	this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAINING_SUBJECT", nullable = false)
    public TrainingSubject getTrainingSubject()
    {
	return this.trainingSubject;
    }

    public void setTrainingSubject(TrainingSubject trainingSubject)
    {
	this.trainingSubject = trainingSubject;
    }
    
    @Column(name = "CLASSES")
    public Integer getClasses()
    {
	return this.classes;
    }

    public void setClasses(Integer classes)
    {
	this.classes = classes;
    }
}