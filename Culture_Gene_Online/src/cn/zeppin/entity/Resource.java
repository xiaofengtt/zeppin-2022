package cn.zeppin.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.zeppin.entity.base.BaseEntity;

@Entity
@Table(name = "resource")
public class Resource extends BaseEntity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	private Integer id;
	private String title;
	private Integer level;
	private Resource parent;
	private Category category;
	private User owner;
	private Timestamp createtime;
	private String scode;
	private String comment;
	private National national;
	private String meaning;
	private String type;
	private Long size;
	private String ratio;
	private Integer source;
	private String url;
	private Integer status;
	private Integer eminent;
	private Integer isObject;
	private String sourcePath;
	private String sourceTime;
	private List<ResourceTag> tagList = new ArrayList<ResourceTag>();
	private List<ResourceCustomTag> customTagList = new ArrayList<ResourceCustomTag>();
	private Set<Resource> childResource = new HashSet<Resource>(0);	
	// Constructors
	
	/** default constructor */
	public Resource()
	{
	}
	
	/** full constructor */

	public Resource(String title, Integer level, Resource parent, Category category, User owner, Timestamp createtime, String scode,
			String comment, National national, String meaning, String type, Long size, String ratio,  Integer source, String url, 
			Integer status, Integer eminent, String sourcePath, String sourceTime, Integer isObject,
			List<ResourceTag> tagList, List<ResourceCustomTag> customTagList, Set<Resource> childResource)	{
		this.title = title;
		this.level = level;
		this.parent = parent;
		this.category = category;
		this.owner = owner;
		this.createtime = createtime;
		this.scode = scode;
		this.comment = comment;
		this.national = national;
		this.meaning = meaning;
		this.type = type;
		this.size = size;
		this.ratio = ratio;
		this.source = source;
		this.url = url;
		this.status = status;
		this.eminent = eminent;
		this.sourcePath = sourcePath;
		this.sourceTime = sourceTime;
		this.isObject = isObject;
		this.tagList = tagList;		
		this.customTagList = customTagList;	
		this.childResource = childResource;	
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
	
	@Column(name = "TITLE", nullable = false, length = 200)
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	@Column(name = "LEVEL", nullable = false)
	public Integer getLevel()
	{
		return this.level;
	}
	
	public void setLevel(Integer level)
	{
		this.level = level;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT")
	public Resource getParent()
	{
		return this.parent;
	}
	
	public void setParent(Resource parent)
	{
		this.parent = parent;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY")
	public Category getCategory()
	{
		return this.category;
	}
	
	public void setCategory(Category category)
	{
		this.category = category;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OWNER", nullable = false)
	public User getOwner()
	{
		return this.owner;
	}
	
	public void setOwner(User owner)
	{
		this.owner = owner;
	}
	
	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime()
	{
		return this.createtime;
	}
	
	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}
	
	@Column(name = "SCODE", nullable = false , length = 100)
	public String getScode()
	{
		return this.scode;
	}
	
	public void setScode(String scode)
	{
		this.scode = scode;
	}
	
	@Column(name = "COMMENT", nullable = false)
	public String getComment()
	{
		return this.comment;
	}
	
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NATIONAL")
	public National getNational()
	{
		return this.national;
	}
	
	public void setNational(National national)
	{
		this.national = national;
	}
	
	@Column(name = "MEANING", length = 1000)
	public String getMeaning()
	{
		return this.meaning;
	}
	
	public void setMeaning(String meaning)
	{
		this.meaning = meaning;
	}
	
	@Column(name = "TYPE", nullable = false ,length = 100)
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	@Column(name = "SIZE", nullable = false)
	public Long getSize()
	{
		return this.size;
	}
	
	public void setSize(Long size)
	{
		this.size = size;
	}
	
	@Column(name = "RATIO", nullable = false, length = 100)
	public String getRatio()
	{
		return this.ratio;
	}
	
	public void setRatio(String ratio)
	{
		this.ratio = ratio;
	}
	
	@Column(name = "SOURCE", nullable = false)
	public Integer getSource()
	{
		return this.source;
	}
	
	public void setSource(Integer source)
	{
		this.source = source;
	}

	@Column(name = "URL", nullable = false, length = 200)
	public String getUrl()
	{
		return this.url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	@Column(name = "STATUS", nullable = false)
	public Integer getStatus()
	{
		return this.status;
	}
	
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	
	@Column(name = "EMINENT", nullable = false)
	public Integer getEminent()
	{
		return this.eminent;
	}
	
	public void setEminent(Integer eminent)
	{
		this.eminent = eminent;
	}
	
	@Column(name = "SOURCE_PATH",length = 100)
	public String getSourcePath()
	{
		return this.sourcePath;
	}
	
	public void setSourcePath(String sourcePath)
	{
		this.sourcePath = sourcePath;
	}
	
	@Column(name = "SOURCE_TIME",length = 100)
	public String getSourceTime()
	{
		return this.sourceTime;
	}
	
	public void setSourceTime(String sourceTime)
	{
		this.sourceTime = sourceTime;
	}
	
	@Column(name = "IS_OBJECT", nullable = false)
	public Integer getIsObject()
	{
		return this.isObject;
	}
	
	public void setIsObject(Integer isObject)
	{
		this.isObject = isObject;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
    public List<ResourceTag> getTagList()
    {
	return this.tagList;
    }

    public void setTagList(List<ResourceTag> tagList)
    {
	this.tagList = tagList;
    }
	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
    public List<ResourceCustomTag> getCustomTagList()
    {
	return this.customTagList;
    }

    public void setCustomTagList(List<ResourceCustomTag> customTagList)
    {
	this.customTagList = customTagList;
    }
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	public Set<Resource> getChildResource() {
		return childResource;
	}

	public void setChildResource(Set<Resource> childResource) {
		this.childResource = childResource;
	}
    
	@Override
	public String toString() {
		return getClass().getName() + String.valueOf(id);
	}
	
}