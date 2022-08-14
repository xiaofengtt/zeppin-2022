/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author Clark.R 2016年9月19日
 * @description 【数据对象】应用市场开关
 */

@Entity
@Table(name = "bk_webmarket_switch")
public class BkWebmarketSwitch extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3358494726056506080L;

	private String uuid;
	private String webMarket;
	private String webMarketName;
	private Boolean flagSwitch;
	private Timestamp createtime;
	private String creator;
	private String version;
	private String status;
	
	public class BkWebmarketSwitchStatus{
		public final static String NORMAL = "normal";
		public final static String DELETED = "deleted";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "web_market", nullable = false)
	public String getWebMarket() {
		return webMarket;
	}

	public void setWebMarket(String webMarket) {
		this.webMarket = webMarket;
	}

	@Column(name = "web_market_name", nullable = false)
	public String getWebMarketName() {
		return webMarketName;
	}

	public void setWebMarketName(String webMarketName) {
		this.webMarketName = webMarketName;
	}

	@Column(name = "flag_switch", nullable = false)
	public Boolean getFlagSwitch() {
		return flagSwitch;
	}

	public void setFlagSwitch(Boolean flagSwitch) {
		this.flagSwitch = flagSwitch;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "creator", nullable = false)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "version", nullable = false)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
