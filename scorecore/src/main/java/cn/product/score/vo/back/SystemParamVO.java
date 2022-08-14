/**
 * 
 */
package cn.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.score.entity.SystemParam;

public class SystemParamVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8039864403351291309L;
	
	private String paramKey;
	private String paramValue;
	private String description;
	private String partitional;
	private String type;
	private Timestamp createtime;
	private String creator;
	private String creatorName;
	
	public SystemParamVO(SystemParam sp) {
		this.paramKey = sp.getParamKey();
		this.paramValue = sp.getParamValue();
		this.description = sp.getDescription();
		this.type = sp.getType();
		this.partitional = sp.getPartitional();
		this.createtime = sp.getCreatetime();
		this.creator = sp.getCreator();
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPartitional() {
		return partitional;
	}

	public void setPartitional(String partitional) {
		this.partitional = partitional;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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
