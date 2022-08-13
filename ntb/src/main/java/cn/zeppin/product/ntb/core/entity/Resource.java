package cn.zeppin.product.ntb.core.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;


/**
 * Resource entity. 
 */

@Entity
@Table(name = "resource")
public class Resource extends BaseEntity {

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
	
	public class ResourceStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
	}
	
	@Id
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator") 
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "type", nullable = false, length = 10)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "filename", length = 100)
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "url", length = 200)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "filetype", length = 20)
	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Column(name = "size")
	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
