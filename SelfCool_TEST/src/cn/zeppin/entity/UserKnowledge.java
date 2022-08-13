package cn.zeppin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_knowledge")
public class UserKnowledge implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4858478635120042630L;
	private Integer id;
	private Knowledge knowledge;
	private SsoUser ssoUser;
	private Integer brushItemCount;
	private Integer lastTestItemCorrectCount;
	private Double correctRate;

	// Constructors

	/** default constructor */
	public UserKnowledge() {
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
	@JoinColumn(name = "SSO_USER", nullable = false)
	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "KNOWLEDGE", nullable = false)
	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	@Column(name = "LAST_TEST_ITEM_CORRECT_COUNT", nullable = false)
	public Integer getLastTestItemCorrectCount() {
		return lastTestItemCorrectCount;
	}

	public void setLastTestItemCorrectCount(Integer lastTestItemCorrectCount) {
		this.lastTestItemCorrectCount = lastTestItemCorrectCount;
	}

	@Column(name = "BRUSH_ITEM_COUNT", nullable = false)
	public Integer getBrushItemCount() {
		return brushItemCount;
	}

	public void setBrushItemCount(Integer brushItemCount) {
		this.brushItemCount = brushItemCount;
	}


	@Column(name = "CORRECT_RATE", nullable = false)
	public Double getCorrectRate() {
		return correctRate;
	}

	public void setCorrectRate(Double correctRate) {
		this.correctRate = correctRate;
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