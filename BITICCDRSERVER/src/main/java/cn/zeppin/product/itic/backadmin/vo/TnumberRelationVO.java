/**
 * 
 */
package cn.zeppin.product.itic.backadmin.vo;

import java.sql.Timestamp;
import java.util.List;

import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationStatus;
import cn.zeppin.product.itic.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author L
 */
public class TnumberRelationVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5233988491797513339L;
	
	private Integer id;
	
	private String status;
	private String statusCN;
	
	private Timestamp createtime;
	private String createtimeCN;
	
	private Timestamp expiryDate;
	private String expiryDateCN;
	
	private Integer custId;
	private String custName;
	
	private Integer opCode;
	private String opName;
	
	private String toMobile;
	private String toTel;
	private String tcPhone;
	private String tcTel;
	
	private Integer processStatus;
	
	private Integer maxduration;
	private Integer isrecord;
	private String isrecordCN;
	private String waybillnum;
	
	private List<String> listNumber;
	
	public TnumberRelationVO(TnumberRelation tr) {
		this.id = tr.getId();
		this.status = tr.getStatus();
		if(TnumberRelationStatus.NORMAL.equals(tr.getStatus())) {
			this.statusCN = "正常";
		} else if(TnumberRelationStatus.UNABLE.equals(tr.getStatus())) {
			this.statusCN = "失效";
		} else if(TnumberRelationStatus.DELETED.equals(tr.getStatus())) {
			this.statusCN = "已删除";
		} else if(TnumberRelationStatus.ERROR.equals(tr.getStatus())) {
			this.statusCN = "异常";
		}
		
		this.createtime = tr.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(tr.getCreatetime());
		
		this.expiryDate = tr.getExpiryDate();
		if(tr.getExpiryDate() != null) {
			this.expiryDateCN = Utlity.timeSpanToString(tr.getExpiryDate());
		} else {
			this.expiryDateCN = "--";
		}
		
		this.custId = tr.getFkTcustomers();
		this.custName = "";
		this.opCode = tr.getFkToperator();
		this.opName = "";
		
		this.toMobile = tr.getToMobile() == null ? "" : tr.getToMobile();
		this.toTel = tr.getToTel() == null ? "" : tr.getToTel();
		this.tcPhone = tr.getTcPhone() == null ? "" : tr.getTcPhone();
		this.tcTel = tr.getTcTel() == null ? "" : tr.getTcTel();
		
		this.processStatus = tr.getProcessStatus();
		
		this.maxduration = tr.getMaxduration() == null ? 0 : tr.getMaxduration();
		this.isrecord = tr.getIsrecord() == null ? 0 : tr.getIsrecord();
		if(tr.getIsrecord() == 1) {
			this.isrecordCN = "是";
		} else if (tr.getIsrecord() == 0) {
			this.isrecordCN = "否";
		} else {
			this.isrecordCN = "未设置";
		}
		this.waybillnum = tr.getWaybillnum() == null ? "" : tr.getWaybillnum();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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


	public Timestamp getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}


	public String getExpiryDateCN() {
		return expiryDateCN;
	}


	public void setExpiryDateCN(String expiryDateCN) {
		this.expiryDateCN = expiryDateCN;
	}


	public Integer getCustId() {
		return custId;
	}


	public void setCustId(Integer custId) {
		this.custId = custId;
	}


	public String getCustName() {
		return custName;
	}


	public void setCustName(String custName) {
		this.custName = custName;
	}


	public Integer getOpCode() {
		return opCode;
	}


	public void setOpCode(Integer opCode) {
		this.opCode = opCode;
	}


	public String getOpName() {
		return opName;
	}


	public void setOpName(String opName) {
		this.opName = opName;
	}


	public String getToMobile() {
		return toMobile;
	}


	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}


	public String getToTel() {
		return toTel;
	}


	public void setToTel(String toTel) {
		this.toTel = toTel;
	}


	public String getTcPhone() {
		return tcPhone;
	}


	public void setTcPhone(String tcPhone) {
		this.tcPhone = tcPhone;
	}


	public String getTcTel() {
		return tcTel;
	}


	public void setTcTel(String tcTel) {
		this.tcTel = tcTel;
	}


	public Integer getProcessStatus() {
		return processStatus;
	}


	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}


	public List<String> getListNumber() {
		return listNumber;
	}


	public void setListNumber(List<String> listNumber) {
		this.listNumber = listNumber;
	}


	public Integer getMaxduration() {
		return maxduration;
	}


	public void setMaxduration(Integer maxduration) {
		this.maxduration = maxduration;
	}


	public Integer getIsrecord() {
		return isrecord;
	}


	public void setIsrecord(Integer isrecord) {
		this.isrecord = isrecord;
	}


	public String getIsrecordCN() {
		return isrecordCN;
	}


	public void setIsrecordCN(String isrecordCN) {
		this.isrecordCN = isrecordCN;
	}


	public String getWaybillnum() {
		return waybillnum;
	}


	public void setWaybillnum(String waybillnum) {
		this.waybillnum = waybillnum;
	}
}
