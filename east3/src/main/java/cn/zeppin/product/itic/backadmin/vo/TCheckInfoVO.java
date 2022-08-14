package cn.zeppin.product.itic.backadmin.vo;

import java.sql.Timestamp;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

public class TCheckInfoVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Timestamp updatetime;
	private String status;
	private String type;
	private String dataid;
	private String datatype;
	private String dataproduct;
	private Map<String, Object> newdata;
	private String jrxkzh;
	private String cjrq;
	private String creator;
	private String creatorName;
	
	public TCheckInfoVO(TCheckInfo t) {
		this.id = t.getId();
		this.type = t.getType();
		this.dataid = t.getDataid();
		this.datatype = t.getDatatype();
		this.dataproduct = t.getDataproduct();
		this.newdata = JSONUtils.json2map(Utlity.ClobToString(t.getNewdata()));
		this.updatetime = t.getUpdatetime();
		this.creator = t.getCreator();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getDataproduct() {
		return dataproduct;
	}

	public void setDataproduct(String dataproduct) {
		this.dataproduct = dataproduct;
	}

	public Map<String, Object> getNewdata() {
		return newdata;
	}

	public void setNewdata(Map<String, Object> newdata) {
		this.newdata = newdata;
	}

	public String getJrxkzh() {
		return jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getCjrq() {
		return cjrq;
	}

	public void setCjrq(String cjrq) {
		this.cjrq = cjrq;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
