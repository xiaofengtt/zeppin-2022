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

/**
 * ProjectApplyWorkReport entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher_edu_evidence")
public class TeacherEduEvidence implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private TeacherEduAdvance teacherEduAdvance;
	private Document document;
	
	public TeacherEduEvidence() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherEduEvidence(Integer id, TeacherEduAdvance teacherEduAdvance,
			Document document) {
		super();
		this.id = id;
		this.teacherEduAdvance = teacherEduAdvance;
		this.document = document;
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
	@JoinColumn(name = "TEACHER_EDU_ADVANCE", nullable = false)
	public TeacherEduAdvance getTeacherEduAdvance() {
		return teacherEduAdvance;
	}

	public void setTeacherEduAdvance(TeacherEduAdvance teacherEduAdvance) {
		this.teacherEduAdvance = teacherEduAdvance;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DOCUMENT", nullable = false)
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}