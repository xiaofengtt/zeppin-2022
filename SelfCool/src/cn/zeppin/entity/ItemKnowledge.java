package cn.zeppin.entity ;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ItemKnowledge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "item_knowledge", uniqueConstraints = @UniqueConstraint(columnNames = { "item", "knowledge" }))
public class ItemKnowledge implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5605116971947115887L;
	private Integer id;
	private Item item;
	private Knowledge knowledge;

	// Constructors

	/** default constructor */
	public ItemKnowledge() {
	}

	/** full constructor */
	public ItemKnowledge(Item item, Knowledge knowledge) {
		this.item = item;
		this.knowledge = knowledge;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ITEM", nullable = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "KNOWLEDGE", nullable = false)
	public Knowledge getKnowledge() {
		return this.knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	
	/**
	 * cacheKey
	 * 
	 * @author Clark
	 */
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}

}