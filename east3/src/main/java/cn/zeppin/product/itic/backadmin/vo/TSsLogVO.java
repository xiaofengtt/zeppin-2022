package cn.zeppin.product.itic.backadmin.vo;

import java.sql.Timestamp;
import java.util.Map;

import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

public class TSsLogVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String type;
	private String dataid;
	private String datatype;
	private String dataproduct;
	private Map<String, Object> olddata;
	private Map<String, Object> newdata;
	private String status;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	
	public TSsLogVO(TSsLog t) {
		this.id = t.getId();
		this.type = t.getType();
		this.dataid = t.getDataid();
		this.datatype = t.getDatatype();
		this.dataproduct = t.getDataproduct();
		this.olddata = JSONUtils.json2map(Utlity.ClobToString(t.getOlddata()));
		this.newdata = JSONUtils.json2map(Utlity.ClobToString(t.getNewdata()));
		this.status = t.getStatus();
		this.creator = t.getCreator();
		this.createtime = t.getCreatetime();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDataproduct() {
		return dataproduct;
	}

	public void setDataproduct(String dataproduct) {
		this.dataproduct = dataproduct;
	}

	public Map<String, Object> getOlddata() {
		return olddata;
	}

	public void setOlddata(Map<String, Object> olddata) {
		this.olddata = olddata;
	}

	public Map<String, Object> getNewdata() {
		return newdata;
	}

	public void setNewdata(Map<String, Object> newdata) {
		this.newdata = newdata;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}
