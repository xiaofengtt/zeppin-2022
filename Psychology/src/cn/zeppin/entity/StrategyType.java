package cn.zeppin.entity;

// Generated 2014-7-29 15:28:15 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;





import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.entity.base.BaseEntity;

/**
 * StrategyType generated by hbm2java
 */
@Entity
@Table(name = "strategy_type", catalog = "cetv", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class StrategyType extends BaseEntity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1091224650278351801L;
	private Integer id;
	private String name;
	
	public StrategyType()
	{
	}
	
	public StrategyType(String name)
	{
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId()
	{
		return this.id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * cacheKey
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
}