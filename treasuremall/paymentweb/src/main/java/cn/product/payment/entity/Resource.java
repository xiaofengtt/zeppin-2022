package cn.product.payment.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class Resource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3224568297504152366L;
	
	private String uuid;
	private String type;
	private String name;
	private String url;
	private BigInteger size;
	private Timestamp createtime;
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
