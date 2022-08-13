package com.whaty.platform.entity.bean;

/**
 * EntityCourseItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EntityCourseItem extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private EntityCourseItemId id;

	// Constructors

	/** default constructor */
	public EntityCourseItem() {
	}

	/** full constructor */
	public EntityCourseItem(EntityCourseItemId id) {
		this.id = id;
	}

	// Property accessors

	public EntityCourseItemId getId() {
		return this.id;
	}

	public void setId(EntityCourseItemId id) {
		this.id = id;
	}

}