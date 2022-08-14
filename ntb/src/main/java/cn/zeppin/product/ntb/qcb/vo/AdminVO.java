package cn.zeppin.product.ntb.qcb.vo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.QcbAdmin;
import cn.zeppin.product.ntb.core.entity.QcbAdmin.QcbAdminStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.DataTimeConvert;

public class AdminVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7010872682032384372L;
	
	private String uuid;
	private String mobile;
	private String name;
	private Integer passwordLevel;
	private String wechatNum;
	private Boolean wechatFlag;
	private String status;
	private Timestamp lastLoginTime;
	private String lastLoginTimeCN;
	private String lastLoginIp;
	
	private String statusCN;
	
	private String qcbCompany;
	private String qcbCompanyName;
	private String qcbCompanyStatus;
	
//	private List<QcbMenu> listMenu;
	private Map<String, QcbMenuLessVO> listMenu;
	
	private List<QcbCompanyAccountLessVO> listCompanyAccount;
	
	public AdminVO(){
		
	}
	
	public AdminVO(QcbAdmin operator){
		this.uuid = operator.getUuid();
		this.name = operator.getName();
		this.mobile = operator.getMobile();
		this.status = operator.getStatus();
		if(QcbAdminStatus.NORMAL.equals(operator.getStatus())){
			this.statusCN = "正常";
		}else if(QcbAdminStatus.DISABLE.equals(operator.getStatus())){
			this.statusCN = "停用";
		}else if(QcbAdminStatus.DELETED.equals(operator.getStatus())){
			this.statusCN = "已删除";
		}
		this.passwordLevel = operator.getPasswordLevel();
		this.wechatNum = operator.getWechatNum();
		this.wechatFlag = operator.getWechatFlag();
		this.lastLoginIp = operator.getLastLoginIp();
		this.lastLoginTime = operator.getLastLoginTime();
		if(lastLoginTime != null){
			this.lastLoginTimeCN = DataTimeConvert.timespanToString(lastLoginTime,"yyyy-MM-dd HH:mm:ss");
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getPasswordLevel() {
		return passwordLevel;
	}

	public void setPasswordLevel(Integer passwordLevel) {
		this.passwordLevel = passwordLevel;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCN() {
		return statusCN;
	}
	
	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}
	
	public String getWechatNum() {
		return wechatNum;
	}

	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}

	public Boolean getWechatFlag() {
		return wechatFlag;
	}

	public void setWechatFlag(Boolean wechatFlag) {
		this.wechatFlag = wechatFlag;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
		if(lastLoginTime != null){
			this.lastLoginTimeCN = DataTimeConvert.timespanToString(lastLoginTime,"yyyy-MM-dd HH:mm:ss");
		}
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginTimeCN() {
		return lastLoginTimeCN;
	}

	public void setLastLoginTimeCN(String lastLoginTimeCN) {
		this.lastLoginTimeCN = lastLoginTimeCN;
	}

	public String getQcbCompany() {
		return qcbCompany;
	}

	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}

	public String getQcbCompanyName() {
		return qcbCompanyName;
	}

	public void setQcbCompanyName(String qcbCompanyName) {
		this.qcbCompanyName = qcbCompanyName;
	}

	public Map<String, QcbMenuLessVO> getListMenu() {
		return listMenu;
	}

	public void setListMenu(Map<String, QcbMenuLessVO> listMenu) {
		this.listMenu = listMenu;
	}

	public List<QcbCompanyAccountLessVO> getListCompanyAccount() {
		return listCompanyAccount;
	}

	public void setListCompanyAccount(
			List<QcbCompanyAccountLessVO> listCompanyAccount) {
		this.listCompanyAccount = listCompanyAccount;
	}

	
	public String getQcbCompanyStatus() {
		return qcbCompanyStatus;
	}
	

	public void setQcbCompanyStatus(String qcbCompanyStatus) {
		this.qcbCompanyStatus = qcbCompanyStatus;
	}
}
