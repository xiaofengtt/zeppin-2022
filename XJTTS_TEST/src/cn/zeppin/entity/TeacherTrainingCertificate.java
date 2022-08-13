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
@Table(name = "teacher_training_certificate")
public class TeacherTrainingCertificate implements java.io.Serializable
{

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private TeacherTrainingRecords teacherTrainingRecords;
    private String imageurl;

    // Constructors

    /** default constructor */
    public TeacherTrainingCertificate()
    {
    }

    /** full constructor */
	public TeacherTrainingCertificate(Integer id,
			TeacherTrainingRecords teacherTrainingRecords, String imageurl) {
		super();
		this.id = id;
		this.teacherTrainingRecords = teacherTrainingRecords;
		this.imageurl = imageurl;
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
	@JoinColumn(name = "TEACHER_TRAINING_RECORDS", nullable = false)
	public TeacherTrainingRecords getTeacherTrainingRecords() {
		return teacherTrainingRecords;
	}

	public void setTeacherTrainingRecords(
			TeacherTrainingRecords teacherTrainingRecords) {
		this.teacherTrainingRecords = teacherTrainingRecords;
	}

	@Column(name = "IMAGE_URL")
	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

    

}