/**
 * 
 */
package cn.zeppin.product.itic.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.itic.core.entity.base.BaseEntity;

/**
 */

@Entity
@Table(name = "T_SS_ROLE")
public class TSsRole extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String description;
	private Short sort;
	
	public class RoleId{
		public final static String superAdmin = "0127fcbe-f57f-11e6-ac06-cacda7da5000";
		public final static String leader = "d9f5a6f4-ead3-35ca-64ba-a066c0ac4ac1";
		public final static String user = "49f5c6f5-f8d2-45c2-94bb-4036be1c4bb6";
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Short getSort() {
		return sort;
	}
	
	
	public void setSort(Short sort) {
		this.sort = sort;
	}
}
