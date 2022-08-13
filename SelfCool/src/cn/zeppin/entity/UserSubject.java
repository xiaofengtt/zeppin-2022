package cn.zeppin.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * UserSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_subject")
public class UserSubject implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4858478635120042630L;
	private Integer id;
	private Subject subject;
	private SsoUser ssoUser;
	private Short status;
	private Double progress;
	private Integer brushItemCount;
	private Integer nextTestdayCount;
	private Double correctRate;
	private Double rankingRate;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public UserSubject() {
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
	@JoinColumn(name = "SUBJECT", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SSO_USER", nullable = false)
	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}


	@Column(name = "STATUS", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	
	@Column(name = "PROGRESS", nullable = false)
	public Double getProgress() {
		return this.progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}
	
	@Column(name = "BRUSH_ITEM_COUNT", nullable = false)
	public Integer getBrushItemCount() {
		return brushItemCount;
	}

	public void setBrushItemCount(Integer brushItemCount) {
		this.brushItemCount = brushItemCount;
	}

	@Column(name = "NEXT_TESTDAY_COUNT", nullable = false)
	public Integer getNextTestdayCount() {
		return nextTestdayCount;
	}

	public void setNextTestdayCount(Integer nextTestdayCount) {
		this.nextTestdayCount = nextTestdayCount;
	}

	@Column(name = "CORRECT_RATE", nullable = false)
	public Double getCorrectRate() {
		return correctRate;
	}

	public void setCorrectRate(Double correctRate) {
		this.correctRate = correctRate;
	}

	@Column(name = "RANKING_RATE", nullable = false)
	public Double getRankingRate() {
		return rankingRate;
	}

	public void setRankingRate(Double rankingRate) {
		this.rankingRate = rankingRate;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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