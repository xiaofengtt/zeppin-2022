package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.zeppin.entity.base.BaseEntity;

/**
 * TextbookCapter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "textbook_capter", catalog = "cetv")
public class TextbookCapter extends BaseEntity
{
	
	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3118544842346868335L;
	private Integer id;
	private Textbook textbook;
	private TextbookCapter textbookCapter;
	private String name;
	private Short level;
	private String number;
	private String scode;
	
	// Constructors
	
	/** default constructor */
	public TextbookCapter()
	{
	}
	
	/** minimal constructor */
	public TextbookCapter(Integer id, Textbook textbook, String name, Short level, String number, String scode)
	{
		this.id = id;
		this.textbook = textbook;
		this.name = name;
		this.level = level;
		this.number = number;
		this.scode = scode;
	}
	
	/** full constructor */
	public TextbookCapter(Integer id, Textbook textbook, TextbookCapter textbookCapter, String name, Short level, String number, String scode)
	{
		this.id = id;
		this.textbook = textbook;
		this.textbookCapter = textbookCapter;
		this.name = name;
		this.level = level;
		this.number = number;
		this.scode = scode;
	}
	
	// Property accessors
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEXTBOOK", nullable = false)
	public Textbook getTextbook()
	{
		return this.textbook;
	}
	
	public void setTextbook(Textbook textbook)
	{
		this.textbook = textbook;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT")
	public TextbookCapter getTextbookCapter()
	{
		return this.textbookCapter;
	}
	
	public void setTextbookCapter(TextbookCapter textbookCapter)
	{
		this.textbookCapter = textbookCapter;
	}
	
	@Column(name = "NAME", nullable = false, length = 100)
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Column(name = "LEVEL", nullable = false)
	public Short getLevel()
	{
		return this.level;
	}
	
	public void setLevel(Short level)
	{
		this.level = level;
	}
	
	@Column(name = "NUMBER", nullable = false, length = 20)
	public String getNumber()
	{
		return number;
	}
	
	public void setNumber(String number)
	{
		this.number = number;
	}
	
	/**
	 * @return the scode
	 */
	@Column(name = "SCODE", nullable = true, length = 100)
	public String getScode()
	{
		return this.scode;
	}
	
	/**
	 * @param scode
	 *            the scode to set
	 */
	public void setScode(String scode)
	{
		this.scode = scode;
	}
	
	/**
	 * cacheKey
	 * 
	 * @author Clark
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + String.valueOf(id);
	}
}