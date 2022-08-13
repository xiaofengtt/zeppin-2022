package com.whaty.platform.entity.bean;

/**
 * PrProExpert entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrProExpert extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeProApply peProApply;
	private PeValuaExpert peValuaExpert;
	private Double resultFirst;
	private String noteFirst;
	private Double resultFinal;
	private String noteFinla;

	// Constructors

	/** default constructor */
	public PrProExpert() {
	}

	/** minimal constructor */
	public PrProExpert(PeValuaExpert peValuaExpert) {
		this.peValuaExpert = peValuaExpert;
	}

	/** full constructor */
	public PrProExpert(PeProApply peProApply, PeValuaExpert peValuaExpert,
			Double resultFirst, String noteFirst, Double resultFinal,
			String noteFinla) {
		this.peProApply = peProApply;
		this.peValuaExpert = peValuaExpert;
		this.resultFirst = resultFirst;
		this.noteFirst = noteFirst;
		this.resultFinal = resultFinal;
		this.noteFinla = noteFinla;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeProApply getPeProApply() {
		return this.peProApply;
	}

	public void setPeProApply(PeProApply peProApply) {
		this.peProApply = peProApply;
	}

	public PeValuaExpert getPeValuaExpert() {
		return this.peValuaExpert;
	}

	public void setPeValuaExpert(PeValuaExpert peValuaExpert) {
		this.peValuaExpert = peValuaExpert;
	}

	public Double getResultFirst() {
		return this.resultFirst;
	}

	public void setResultFirst(Double resultFirst) {
		this.resultFirst = resultFirst;
	}

	public String getNoteFirst() {
		return this.noteFirst;
	}

	public void setNoteFirst(String noteFirst) {
		this.noteFirst = noteFirst;
	}

	public Double getResultFinal() {
		return this.resultFinal;
	}

	public void setResultFinal(Double resultFinal) {
		this.resultFinal = resultFinal;
	}

	public String getNoteFinla() {
		return this.noteFinla;
	}

	public void setNoteFinla(String noteFinla) {
		this.noteFinla = noteFinla;
	}

}