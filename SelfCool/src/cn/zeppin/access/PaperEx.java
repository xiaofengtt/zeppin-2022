package cn.zeppin.access;

import java.util.LinkedList;
import java.util.List;

import cn.zeppin.access.PaperSectionEx;

public class PaperEx {

	private String gradeName;
	private int gradeId;
	private String areaName;
	private int areaId;
	private String subjectName;
	private int subjectId;
	private String sourceType;
	
	private Integer id;
	private String year;
	private Short type;
	private String name;
	private String source;
	private Integer answerTime;
	private Short totalScore;
	private String cover;
	private Short status;
	
	private String isFree;
	private Integer price;
	
	private List<PaperSectionEx> lstSections = new LinkedList<PaperSectionEx>();

	public PaperEx() {
	}
	
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Integer answerTime) {
		this.answerTime = answerTime;
	}

	public Short getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Short totalScore) {
		this.totalScore = totalScore;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public List<PaperSectionEx> getLstSections() {
		return lstSections;
	}

	public void setLstSections(List<PaperSectionEx> lstSections) {
		this.lstSections = lstSections;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}
}
