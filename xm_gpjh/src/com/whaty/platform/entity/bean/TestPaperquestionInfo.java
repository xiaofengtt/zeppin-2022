package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * TestPaperquestionInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestPaperquestionInfo extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String creatuser;
	private Date creatdate;
	private String diff;
	private String questioncore;
	private String title;
	private String serial;
	private String score;
	private String lore;
	private String cognizetype;
	private String purpose;
	private String referencescore;
	private String referencetime;
	private String studentnote;
	private String teachernote;
	private String testpaperId;
	private String type;
	private String userId;

	// Constructors

	/** default constructor */
	public TestPaperquestionInfo() {
	}

	/** minimal constructor */
	public TestPaperquestionInfo(String creatuser, Date creatdate, String diff,
			String questioncore, String title, String serial, String score,
			String lore, String cognizetype, String purpose,
			String referencescore, String referencetime, String testpaperId) {
		this.creatuser = creatuser;
		this.creatdate = creatdate;
		this.diff = diff;
		this.questioncore = questioncore;
		this.title = title;
		this.serial = serial;
		this.score = score;
		this.lore = lore;
		this.cognizetype = cognizetype;
		this.purpose = purpose;
		this.referencescore = referencescore;
		this.referencetime = referencetime;
		this.testpaperId = testpaperId;
	}

	/** full constructor */
	public TestPaperquestionInfo(String creatuser, Date creatdate, String diff,
			String questioncore, String title, String serial, String score,
			String lore, String cognizetype, String purpose,
			String referencescore, String referencetime, String studentnote,
			String teachernote, String testpaperId, String type, String userId) {
		this.creatuser = creatuser;
		this.creatdate = creatdate;
		this.diff = diff;
		this.questioncore = questioncore;
		this.title = title;
		this.serial = serial;
		this.score = score;
		this.lore = lore;
		this.cognizetype = cognizetype;
		this.purpose = purpose;
		this.referencescore = referencescore;
		this.referencetime = referencetime;
		this.studentnote = studentnote;
		this.teachernote = teachernote;
		this.testpaperId = testpaperId;
		this.type = type;
		this.userId = userId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatuser() {
		return this.creatuser;
	}

	public void setCreatuser(String creatuser) {
		this.creatuser = creatuser;
	}

	public Date getCreatdate() {
		return this.creatdate;
	}

	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}

	public String getDiff() {
		return this.diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getQuestioncore() {
		return this.questioncore;
	}

	public void setQuestioncore(String questioncore) {
		this.questioncore = questioncore;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getLore() {
		return this.lore;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

	public String getCognizetype() {
		return this.cognizetype;
	}

	public void setCognizetype(String cognizetype) {
		this.cognizetype = cognizetype;
	}

	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getReferencescore() {
		return this.referencescore;
	}

	public void setReferencescore(String referencescore) {
		this.referencescore = referencescore;
	}

	public String getReferencetime() {
		return this.referencetime;
	}

	public void setReferencetime(String referencetime) {
		this.referencetime = referencetime;
	}

	public String getStudentnote() {
		return this.studentnote;
	}

	public void setStudentnote(String studentnote) {
		this.studentnote = studentnote;
	}

	public String getTeachernote() {
		return this.teachernote;
	}

	public void setTeachernote(String teachernote) {
		this.teachernote = teachernote;
	}

	public String getTestpaperId() {
		return this.testpaperId;
	}

	public void setTestpaperId(String testpaperId) {
		this.testpaperId = testpaperId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}