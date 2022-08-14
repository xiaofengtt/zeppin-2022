/**
 * 
 */
package cn.zeppin.product.itic.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;

/**
 * @author L
 *
 * 
 * @description 【数据对象】TCUSTOMERS入库
 */
@Entity
@Table(name = "TCustomers")
public class Tcustomers extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141633696155630930L;

	private Integer custId;
	private String custNo;
	private String custName;
	
	private String custTel;
	private String oTel;
	private String hTel;
	private String mobile;
	
	private String pinYinSimple;//姓名拼音首字母 搜索用
	private String pinYinWhole;//姓名拼音全拼 搜索用
	
	private String cardType;
	private String cardTypeName;
	private String cardId;
	private Integer custType;
	private String custTypeName;
	private Integer wtFlag;
	private String wtFlagName;
	private String touchType;
	private String touchTypeName;
	private String custSource;
	private String custSourceName;
	private Integer inputMan;
	private Timestamp inputTime;
	private Integer checkFlag;
	private String status;
	private String statusName;
	private String gradeLevel;
	private String gradeLevelName;
	private Integer isDeal;
	private Integer companyFamily;
	
	private Integer modiFlag;
	private Integer trueFlag;
	
	private Integer serviceMan;
	
	
	@Id
	@Column(name = "CUST_ID", unique = true, nullable = false)
	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	@Column(name = "CUST_NO", nullable = false, length = 8)
	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	@Column(name = "CUST_NAME", nullable = false, length = 100)
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "PinYinSimple", length = 100)
	public String getPinYinSimple() {
		return pinYinSimple;
	}

	public void setPinYinSimple(String pinYinSimple) {
		this.pinYinSimple = pinYinSimple;
	}

	@Column(name = "CARD_TYPE", nullable = false, length = 10)
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Column(name = "CARD_TYPE_NAME", nullable = false, length = 30)
	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	@Column(name = "CARD_ID", nullable = false, length = 40)
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "CUST_TYPE", nullable = false)
	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	@Column(name = "CUST_TYPE_NAME", nullable = false, length = 10)
	public String getCustTypeName() {
		return custTypeName;
	}

	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}

	@Column(name = "WT_FLAG", nullable = false)
	public Integer getWtFlag() {
		return wtFlag;
	}

	public void setWtFlag(Integer wtFlag) {
		this.wtFlag = wtFlag;
	}

	@Column(name = "WT_FLAG_NAME", nullable = false, length = 10)
	public String getWtFlagName() {
		return wtFlagName;
	}

	public void setWtFlagName(String wtFlagName) {
		this.wtFlagName = wtFlagName;
	}

	@Column(name = "TOUCH_TYPE", nullable = false, length = 10)
	public String getTouchType() {
		return touchType;
	}

	public void setTouchType(String touchType) {
		this.touchType = touchType;
	}

	@Column(name = "TOUCH_TYPE_NAME", nullable = false, length = 30)
	public String getTouchTypeName() {
		return touchTypeName;
	}

	public void setTouchTypeName(String touchTypeName) {
		this.touchTypeName = touchTypeName;
	}

	@Column(name = "CUST_SOURCE", nullable = false, length = 10)
	public String getCustSource() {
		return custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

	@Column(name = "CUST_SOURCE_NAME", nullable = false, length = 30)
	public String getCustSourceName() {
		return custSourceName;
	}

	public void setCustSourceName(String custSourceName) {
		this.custSourceName = custSourceName;
	}

	@Column(name = "INPUT_MAN", nullable = false)
	public Integer getInputMan() {
		return inputMan;
	}

	public void setInputMan(Integer inputMan) {
		this.inputMan = inputMan;
	}

	@Column(name = "INPUT_TIME", nullable = false)
	public Timestamp getInputTime() {
		return inputTime;
	}

	public void setInputTime(Timestamp inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "CHECK_FLAG", nullable = false)
	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

	@Column(name = "STATUS", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "STATUS_NAME", nullable = false, length = 30)
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Column(name = "GRADE_LEVEL", nullable = false, length = 10)
	public String getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	@Column(name = "GRADE_LEVEL_NAME", nullable = false, length = 30)
	public String getGradeLevelName() {
		return gradeLevelName;
	}

	public void setGradeLevelName(String gradeLevelName) {
		this.gradeLevelName = gradeLevelName;
	}

	@Column(name = "IS_DEAL", nullable = false)
	public Integer getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}

	@Column(name = "COMPANY_FAMILY", nullable = false)
	public Integer getCompanyFamily() {
		return companyFamily;
	}

	public void setCompanyFamily(Integer companyFamily) {
		this.companyFamily = companyFamily;
	}

	@Column(name = "MODI_FLAG", nullable = false)
	public Integer getModiFlag() {
		return modiFlag;
	}

	public void setModiFlag(Integer modiFlag) {
		this.modiFlag = modiFlag;
	}

	@Column(name = "TRUE_FLAG", nullable = false)
	public Integer getTrueFlag() {
		return trueFlag;
	}

	public void setTrueFlag(Integer trueFlag) {
		this.trueFlag = trueFlag;
	}

	@Column(name = "CUST_TEL", length = 40)
	public String getCustTel() {
		return custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}

	@Column(name = "O_TEL", length = 40)
	public String getoTel() {
		return oTel;
	}

	public void setoTel(String oTel) {
		this.oTel = oTel;
	}

	@Column(name = "H_TEL", length = 40)
	public String gethTel() {
		return hTel;
	}

	public void sethTel(String hTel) {
		this.hTel = hTel;
	}

	@Column(name = "MOBILE", length = 100)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "PinYinWhole", length = 500)
	public String getPinYinWhole() {
		return pinYinWhole;
	}

	public void setPinYinWhole(String pinYinWhole) {
		this.pinYinWhole = pinYinWhole;
	}

	@Column(name = "SERVICE_MAN")
	public Integer getServiceMan() {
		return serviceMan;
	}

	public void setServiceMan(Integer serviceMan) {
		this.serviceMan = serviceMan;
	}
}
