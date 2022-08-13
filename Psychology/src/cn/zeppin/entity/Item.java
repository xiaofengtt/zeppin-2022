package cn.zeppin.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.zeppin.entity.base.BaseEntity;

/**
 * Item entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "item", catalog = "cetv")
public class Item extends BaseEntity {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7074333549412359175L;
	private Integer id;
	private SysUser sysUser;
	private Grade grade;
	private Subject subject;
	private ItemType itemType;
	private Knowledge knowledge;
	private Material material;
	private TextbookCapter textbookCapter;
	private String content;
	private String contentBackup;
	private String analysis;
	private Boolean isGroup;
	private Short diffcultyLevel;
	private Integer defaultScore;
	private Short sourceType;
	private String source;
	private Short status;
	private int level;
	private Item parent;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public Item() {
	}

	/** minimal constructor */
	public Item(SysUser sysUser, ItemType itemType, Boolean isGroup, Short diffcultyLevel, Integer defaultScore, Short sourceType, Short status, Timestamp createtime) {
		this.sysUser = sysUser;
		this.itemType = itemType;
		this.isGroup = isGroup;
		this.diffcultyLevel = diffcultyLevel;
		this.defaultScore = defaultScore;
		this.sourceType = sourceType;
		this.status = status;
		this.createtime = createtime;
	}

	/** full constructor */
	public Item(SysUser sysUser, Grade grade, Subject subject, ItemType itemType, Knowledge knowladge, Material material, TextbookCapter textbookCapter, String content, String contentBackup, Boolean isGroup, Short diffcultyLevel, Integer defaultScore, Short sourceType, String source, Short status,
			Timestamp createtime) {
		this.sysUser = sysUser;
		this.grade = grade;
		this.subject = subject;
		this.itemType = itemType;
		this.knowledge = knowladge;
		this.material = material;
		this.textbookCapter = textbookCapter;
		this.content = content;
		this.setContentBackup(contentBackup);
		this.isGroup = isGroup;
		this.diffcultyLevel = diffcultyLevel;
		this.defaultScore = defaultScore;
		this.sourceType = sourceType;
		this.source = source;
		this.status = status;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATER", nullable = false)
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GRADE")
	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TYPE", nullable = false)
	public ItemType getItemType() {
		return this.itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "KNOWLADGE")
	public Knowledge getKnowledge() {
		return this.knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MATERIAL")
	public Material getMaterial() {
		return this.material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEXTBOOK_CAPTER")
	public TextbookCapter getTextbookCapter() {
		return this.textbookCapter;
	}

	public void setTextbookCapter(TextbookCapter textbookCapter) {
		this.textbookCapter = textbookCapter;
	}

	@Column(name = "CONTENT", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CONTENT_BACKUP", length = 65535)
	public String getContentBackup() {
		return contentBackup;
	}

	@Column(name = "ANALYSIS", length = 65535)
	public String getAnalysis() {

		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public void setContentBackup(String contentBackup) {
		this.contentBackup = contentBackup;
	}

	@Column(name = "IS_GROUP", nullable = false)
	public Boolean getIsGroup() {
		return this.isGroup;
	}

	public void setIsGroup(Boolean isGroup) {
		this.isGroup = isGroup;
	}

	@Column(name = "DIFFCULTY_LEVEL", nullable = false)
	public Short getDiffcultyLevel() {
		return this.diffcultyLevel;
	}

	public void setDiffcultyLevel(Short diffcultyLevel) {
		this.diffcultyLevel = diffcultyLevel;
	}

	@Column(name = "DEFAULT_SCORE")
	public Integer getDefaultScore() {
		return this.defaultScore;
	}

	public void setDefaultScore(Integer defaultScore) {
		this.defaultScore = defaultScore;
	}

	@Column(name = "SOURCE_TYPE", nullable = false)
	public Short getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(Short sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "SOURCE", length = 200)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent")
	public Item getParent() {
		return parent;
	}

	public void setParent(Item parent) {
		this.parent = parent;
	}

	/**
	 * cacheKey
	 * 
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}