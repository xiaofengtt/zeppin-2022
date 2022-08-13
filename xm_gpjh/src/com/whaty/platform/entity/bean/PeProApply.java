package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeProApply entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeProApply extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private EnumConst enumConstByFkCheckFinal;
	private EnumConst enumConstByFkCheckFirst;
	private PeProApplyno peProApplyno;
	private PeSubject peSubject;
	private PeUnit peUnit;
	private EnumConst enumConstByFkCheckResultProvince;
	private EnumConst enumConstByFkCheckNational;
	private String declaration;
	private String noteFirst;
	private String noteFinal;
	private Date declareDate;
	private String scheme;
	private String classIdentifier;
	private Set prProExperts = new HashSet(0);
	private Set peProImplemts = new HashSet(0);
	private Set peCoursePlans = new HashSet(0);
	private Set peBriefReports = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeProApply() {
	}

	/** full constructor */
	public PeProApply(EnumConst enumConstByFkCheckFinal,
			EnumConst enumConstByFkCheckFirst, PeProApplyno peProApplyno,
			PeSubject peSubject, PeUnit peUnit,
			EnumConst enumConstByFkCheckResultProvince,
			EnumConst enumConstByFkCheckNational, String declaration,
			String noteFirst, String noteFinal, Date declareDate,
			String scheme, String classIdentifier, Set prProExperts,
			Set peProImplemts, Set peCoursePlans, Set peBriefReports) {
		this.enumConstByFkCheckFinal = enumConstByFkCheckFinal;
		this.enumConstByFkCheckFirst = enumConstByFkCheckFirst;
		this.peProApplyno = peProApplyno;
		this.peSubject = peSubject;
		this.peUnit = peUnit;
		this.enumConstByFkCheckResultProvince = enumConstByFkCheckResultProvince;
		this.enumConstByFkCheckNational = enumConstByFkCheckNational;
		this.declaration = declaration;
		this.noteFirst = noteFirst;
		this.noteFinal = noteFinal;
		this.declareDate = declareDate;
		this.scheme = scheme;
		this.classIdentifier = classIdentifier;
		this.prProExperts = prProExperts;
		this.peProImplemts = peProImplemts;
		this.peCoursePlans = peCoursePlans;
		this.peBriefReports = peBriefReports;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumConst getEnumConstByFkCheckFinal() {
		return this.enumConstByFkCheckFinal;
	}

	public void setEnumConstByFkCheckFinal(EnumConst enumConstByFkCheckFinal) {
		this.enumConstByFkCheckFinal = enumConstByFkCheckFinal;
	}

	public EnumConst getEnumConstByFkCheckFirst() {
		return this.enumConstByFkCheckFirst;
	}

	public void setEnumConstByFkCheckFirst(EnumConst enumConstByFkCheckFirst) {
		this.enumConstByFkCheckFirst = enumConstByFkCheckFirst;
	}

	public PeProApplyno getPeProApplyno() {
		return this.peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}

	public PeSubject getPeSubject() {
		return this.peSubject;
	}

	public void setPeSubject(PeSubject peSubject) {
		this.peSubject = peSubject;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public EnumConst getEnumConstByFkCheckResultProvince() {
		return this.enumConstByFkCheckResultProvince;
	}

	public void setEnumConstByFkCheckResultProvince(
			EnumConst enumConstByFkCheckResultProvince) {
		this.enumConstByFkCheckResultProvince = enumConstByFkCheckResultProvince;
	}

	public EnumConst getEnumConstByFkCheckNational() {
		return this.enumConstByFkCheckNational;
	}

	public void setEnumConstByFkCheckNational(
			EnumConst enumConstByFkCheckNational) {
		this.enumConstByFkCheckNational = enumConstByFkCheckNational;
	}

	public String getDeclaration() {
		return this.declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public String getNoteFirst() {
		return this.noteFirst;
	}

	public void setNoteFirst(String noteFirst) {
		this.noteFirst = noteFirst;
	}

	public String getNoteFinal() {
		return this.noteFinal;
	}

	public void setNoteFinal(String noteFinal) {
		this.noteFinal = noteFinal;
	}

	public Date getDeclareDate() {
		return this.declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public String getScheme() {
		return this.scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getClassIdentifier() {
		return this.classIdentifier;
	}

	public void setClassIdentifier(String classIdentifier) {
		this.classIdentifier = classIdentifier;
	}

	public Set getPrProExperts() {
		return this.prProExperts;
	}

	public void setPrProExperts(Set prProExperts) {
		this.prProExperts = prProExperts;
	}

	public Set getPeProImplemts() {
		return this.peProImplemts;
	}

	public void setPeProImplemts(Set peProImplemts) {
		this.peProImplemts = peProImplemts;
	}

	public Set getPeCoursePlans() {
		return this.peCoursePlans;
	}

	public void setPeCoursePlans(Set peCoursePlans) {
		this.peCoursePlans = peCoursePlans;
	}

	public Set getPeBriefReports() {
		return this.peBriefReports;
	}

	public void setPeBriefReports(Set peBriefReports) {
		this.peBriefReports = peBriefReports;
	}

}