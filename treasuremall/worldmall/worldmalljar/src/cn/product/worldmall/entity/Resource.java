package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;



public class Resource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4229089826896417390L;

	
	private String uuid;
	private String type;
	private String filename;
	private String url;
	private String filetype;
	private BigInteger size;
	private String status;
	private Timestamp createtime;
	
	public class ResourceStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
	}
	
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
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
