package cn.zeppin.project.chinamobile.media.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;

/**
 * Category entity. 
 * 
 * @author Clark.R 2016.03.29
 */

@Entity
@Table(name = "commodity_display360")
public class CommodityDisplay extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields
	
	private String id;
	private String commodity;
	private Integer displayIndex;
	private String displayImage;
	private String creator;
	private Timestamp createtime;
	
	public CommodityDisplay() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CommodityDisplay(String id, String commodity, Integer displayIndex,
			String displayImage, String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.commodity = commodity;
		this.displayIndex = displayIndex;
		this.displayImage = displayImage;
		this.creator = creator;
		this.createtime = createtime;
	}


	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator") 
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "commodity", nullable = false, length = 36)
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	
	@Column(name = "display_index", nullable = false, length = 11)
	public Integer getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(Integer displayIndex) {
		this.displayIndex = displayIndex;
	}
	
	@Column(name = "display_image", nullable = false, length = 36)
	public String getDisplayImage() {
		return displayImage;
	}
	public void setDisplayImage(String displayImage) {
		this.displayImage = displayImage;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	
	
	
}
