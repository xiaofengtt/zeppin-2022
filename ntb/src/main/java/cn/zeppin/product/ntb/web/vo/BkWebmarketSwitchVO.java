/**
 * 
 */
package cn.zeppin.product.ntb.web.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BkWebmarketSwitch;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe 2016年2月8日
 */

public class BkWebmarketSwitchVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private String webMarket;
	private String webMarketName;
	private Boolean flagSwitch;
	private String flagSwitchCN;
	private Timestamp createtime;
	private String createtimeCN;
	private String creator;
	private String creatorCN;
	private String version;
	private Integer versionNum;
	private String versionName;
	private String status;
	
	
	public BkWebmarketSwitchVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BkWebmarketSwitchVO(BkWebmarketSwitch bp) {
		this.uuid = bp.getUuid();
		this.webMarket = bp.getWebMarket();
		this.webMarketName = bp.getWebMarketName();
		this.flagSwitch = bp.getFlagSwitch();
		if(bp.getFlagSwitch()){
			this.flagSwitchCN = "开启";
		} else {
			this.flagSwitchCN = "关闭";
		}
		this.createtime = bp.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToDateString(bp.getCreatetime());
		this.creator = bp.getCreator();
		this.version = bp.getVersion();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Boolean getFlagSwitch() {
		return flagSwitch;
	}

	public void setFlagSwitch(Boolean flagSwitch) {
		this.flagSwitch = flagSwitch;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
		this.createtimeCN = Utlity.timeSpanToDateString(createtime);
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getCreatorCN() {
		return creatorCN;
	}

	public void setCreatorCN(String creatorCN) {
		this.creatorCN = creatorCN;
	}

	
	public String getWebMarket() {
		return webMarket;
	}
	

	public void setWebMarket(String webMarket) {
		this.webMarket = webMarket;
	}
	

	public String getWebMarketName() {
		return webMarketName;
	}
	

	public void setWebMarketName(String webMarketName) {
		this.webMarketName = webMarketName;
	}
	

	public String getFlagSwitchCN() {
		return flagSwitchCN;
	}
	

	public void setFlagSwitchCN(String flagSwitchCN) {
		this.flagSwitchCN = flagSwitchCN;
	}
	

	public String getVersion() {
		return version;
	}
	

	public void setVersion(String version) {
		this.version = version;
	}
	

	public Integer getVersionNum() {
		return versionNum;
	}
	

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
}
