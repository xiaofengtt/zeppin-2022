package cn.zeppin.product.ntb.web.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.InvestorInformation;
import cn.zeppin.product.ntb.core.entity.InvestorInformation.InvestorInformationStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class InvestorInformationVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String infoTitle;
	private String infoText;
	private Timestamp createtime;
	private String createtimeCN;
	private Boolean flagRead;
	private String status;
	
	
 	public InvestorInformationVO(){
		
	}
	
	public InvestorInformationVO(InvestorInformation ii){
		this.uuid = ii.getUuid();
		this.infoTitle = ii.getInfoTitle();
		this.infoText = ii.getInfoText();
		this.createtime = ii.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToChinaStringLess(ii.getCreatetime());
		this.flagRead = false;
		if(!InvestorInformationStatus.UNREAD.equals(ii.getStatus())){
			this.flagRead = true;
		}
		this.status = ii.getStatus();
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public String getInfoTitle() {
		return infoTitle;
	}
	

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	

	public String getInfoText() {
		return infoText;
	}
	

	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}
	

	public Timestamp getCreatetime() {
		return createtime;
	}
	

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	

	public String getCreatetimeCN() {
		return createtimeCN;
	}
	

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
	

	public Boolean getFlagRead() {
		return flagRead;
	}
	

	public void setFlagRead(Boolean flagRead) {
		this.flagRead = flagRead;
	}
	

	public String getStatus() {
		return status;
	}
	

	public void setStatus(String status) {
		this.status = status;
	}
}
