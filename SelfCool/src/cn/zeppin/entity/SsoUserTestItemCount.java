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
@Table(name = "sso_user_test_item_count")


public class SsoUserTestItemCount implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7374643888371711491L;
	private Integer id;
	private SsoUser ssoUser;
	private Item item;
	private Integer blankInx;
	private Integer testItemCount;
	private Integer testItemCorrectCount;
	private Integer testItemWrongCount;
	private SsoUserTestItem lastTestItem;
	private Timestamp lastTestItemAnswerTime;
	private Short lastTestItemCompleteType;
	private Short isWrongbookItem;
	private Short isWrongbookItemTested;
	private Short isWrongbookItemMastered;
	private Timestamp wrongbookItemCreatetime;
	private Timestamp wrongbookItemTesttime;
	private Timestamp wrongbookItemMastertime;
	
	
	// Constructors

	/** default constructor */
	public SsoUserTestItemCount() {
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
	@JoinColumn(name = "userid", nullable = false)
	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "itemid", nullable = false)
	public Item getItem() {
		return item;
	}



	public void setItem(Item item) {
		this.item = item;
	}


	@Column(name = "blank_inx", nullable = false)
	public Integer getBlankInx() {
		return blankInx;
	}



	public void setBlankInx(Integer blankInx) {
		this.blankInx = blankInx;
	}


	@Column(name = "test_item_count", nullable = false)
	public Integer getTestItemCount() {
		return testItemCount;
	}



	public void setTestItemCount(Integer testItemCount) {
		this.testItemCount = testItemCount;
	}


	@Column(name = "test_item_correct_count", nullable = false)
	public Integer getTestItemCorrectCount() {
		return testItemCorrectCount;
	}



	public void setTestItemCorrectCount(Integer testItemCorrectCount) {
		this.testItemCorrectCount = testItemCorrectCount;
	}


	@Column(name = "test_item_wrong_count", nullable = false)
	public Integer getTestItemWrongCount() {
		return testItemWrongCount;
	}



	public void setTestItemWrongCount(Integer testItemWrongCount) {
		this.testItemWrongCount = testItemWrongCount;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "last_test_item_id")
	public SsoUserTestItem getLastTestItem() {
		return lastTestItem;
	}



	public void setLastTestItem(SsoUserTestItem lastTestItem) {
		this.lastTestItem = lastTestItem;
	}


	@Column(name = "last_test_item_answer_time")
	public Timestamp getLastTestItemAnswerTime() {
		return lastTestItemAnswerTime;
	}



	public void setLastTestItemAnswerTime(Timestamp lastTestItemAnswerTime) {
		this.lastTestItemAnswerTime = lastTestItemAnswerTime;
	}


	@Column(name = "last_test_item_complete_type")
	public Short getLastTestItemCompleteType() {
		return lastTestItemCompleteType;
	}



	public void setLastTestItemCompleteType(Short lastTestItemCompleteType) {
		this.lastTestItemCompleteType = lastTestItemCompleteType;
	}


	@Column(name = "is_wrongbook_item", nullable = false)
	public Short getIsWrongbookItem() {
		return isWrongbookItem;
	}

	public void setIsWrongbookItem(Short isWrongbookItem) {
		this.isWrongbookItem = isWrongbookItem;
	}

	@Column(name = "is_wrongbook_item_tested", nullable = false)
	public Short getIsWrongbookItemTested() {
		return isWrongbookItemTested;
	}

	public void setIsWrongbookItemTested(Short isWrongbookItemTested) {
		this.isWrongbookItemTested = isWrongbookItemTested;
	}

	@Column(name = "is_wrongbook_item_mastered", nullable = false)
	public Short getIsWrongbookItemMastered() {
		return isWrongbookItemMastered;
	}

	public void setIsWrongbookItemMastered(Short isWrongbookItemMastered) {
		this.isWrongbookItemMastered = isWrongbookItemMastered;
	}

	@Column(name = "wrongbook_item_createtime")
	public Timestamp getWrongbookItemCreatetime() {
		return wrongbookItemCreatetime;
	}

	public void setWrongbookItemCreatetime(Timestamp wrongbookItemCreatetime) {
		this.wrongbookItemCreatetime = wrongbookItemCreatetime;
	}

	@Column(name = "wrongbook_item_testtime")
	public Timestamp getWrongbookItemTesttime() {
		return wrongbookItemTesttime;
	}

	public void setWrongbookItemTesttime(Timestamp wrongbookItemTesttime) {
		this.wrongbookItemTesttime = wrongbookItemTesttime;
	}

	@Column(name = "wrongbook_item_mastertime")
	public Timestamp getWrongbookItemMastertime() {
		return wrongbookItemMastertime;
	}

	public void setWrongbookItemMastertime(Timestamp wrongbookItemMastertime) {
		this.wrongbookItemMastertime = wrongbookItemMastertime;
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