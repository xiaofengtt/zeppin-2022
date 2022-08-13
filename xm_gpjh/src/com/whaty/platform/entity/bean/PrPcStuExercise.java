package com.whaty.platform.entity.bean;

/**
 * PrPcStuExercise entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPcStuExercise extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrPcElective prPcElective;
	private PePcExercise pePcExercise;
	private String note;

	// Constructors

	/** default constructor */
	public PrPcStuExercise() {
	}

	/** full constructor */
	public PrPcStuExercise(PrPcElective prPcElective,
			PePcExercise pePcExercise, String note) {
		this.prPcElective = prPcElective;
		this.pePcExercise = pePcExercise;
		this.note = note;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrPcElective getPrPcElective() {
		return this.prPcElective;
	}

	public void setPrPcElective(PrPcElective prPcElective) {
		this.prPcElective = prPcElective;
	}

	public PePcExercise getPePcExercise() {
		return this.pePcExercise;
	}

	public void setPePcExercise(PePcExercise pePcExercise) {
		this.pePcExercise = pePcExercise;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}