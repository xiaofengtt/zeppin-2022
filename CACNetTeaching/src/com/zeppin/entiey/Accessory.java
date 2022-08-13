package com.zeppin.entiey;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Accessory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "accessory", catalog = "cac")
public class Accessory implements java.io.Serializable
{

    // Fields

    private Integer id;
    private String name;
    private String oraName;
    private String fileType;
    private String filePath;
    private float fileSize;
    private Date createTime;
    private boolean isCourseWare;
    private Set<HomeworkAccessoryMap> homeworkAccessoryMaps = new HashSet<HomeworkAccessoryMap>(
	    0);
    private Set<CourseCoursewareMap> courseCoursewareMaps = new HashSet<CourseCoursewareMap>(
	    0);

    // Constructors

    /** default constructor */
    public Accessory()
    {
    }

    /** minimal constructor */
    public Accessory(String name, String oraName, String fileType,
	    String filePath, float fileSize, Date createTime,
	    boolean isCourseWare)
    {
	this.name = name;
	this.oraName = oraName;
	this.fileType = fileType;
	this.filePath = filePath;
	this.fileSize = fileSize;
	this.createTime = createTime;
	this.isCourseWare = isCourseWare;
    }

    /** full constructor */
    public Accessory(String name, String oraName, String fileType,
	    String filePath, float fileSize, Date createTime,
	    boolean isCourseWare,
	    Set<HomeworkAccessoryMap> homeworkAccessoryMaps,
	    Set<CourseCoursewareMap> courseCoursewareMaps)
    {
	this.name = name;
	this.oraName = oraName;
	this.fileType = fileType;
	this.filePath = filePath;
	this.fileSize = fileSize;
	this.createTime = createTime;
	this.isCourseWare = isCourseWare;
	this.homeworkAccessoryMaps = homeworkAccessoryMaps;
	this.courseCoursewareMaps = courseCoursewareMaps;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId()
    {
	return this.id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Column(name = "name", nullable = false, length = 38)
    public String getName()
    {
	return this.name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    @Column(name = "oraName", nullable = false, length = 100)
    public String getOraName()
    {
	return this.oraName;
    }

    public void setOraName(String oraName)
    {
	this.oraName = oraName;
    }

    @Column(name = "fileType", nullable = false, length = 20)
    public String getFileType()
    {
	return this.fileType;
    }

    public void setFileType(String fileType)
    {
	this.fileType = fileType;
    }

    @Column(name = "filePath", nullable = false)
    public String getFilePath()
    {
	return this.filePath;
    }

    public void setFilePath(String filePath)
    {
	this.filePath = filePath;
    }

    @Column(name = "fileSize", nullable = false, precision = 12, scale = 0)
    public float getFileSize()
    {
	return this.fileSize;
    }

    public void setFileSize(float fileSize)
    {
	this.fileSize = fileSize;
    }

    @Column(name = "createTime", nullable = false, length = 19)
    public Date getCreateTime()
    {
	return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
	this.createTime = createTime;
    }

    @Column(name = "isCourseWare", nullable = false)
    public boolean getIsCourseWare()
    {
	return this.isCourseWare;
    }

    public void setIsCourseWare(boolean isCourseWare)
    {
	this.isCourseWare = isCourseWare;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "accessory")
    public Set<HomeworkAccessoryMap> getHomeworkAccessoryMaps()
    {
	return this.homeworkAccessoryMaps;
    }

    public void setHomeworkAccessoryMaps(
	    Set<HomeworkAccessoryMap> homeworkAccessoryMaps)
    {
	this.homeworkAccessoryMaps = homeworkAccessoryMaps;
    }

    @OneToMany(
	    cascade = CascadeType.ALL,
	    fetch = FetchType.LAZY,
	    mappedBy = "accessory")
    public Set<CourseCoursewareMap> getCourseCoursewareMaps()
    {
	return this.courseCoursewareMaps;
    }

    public void setCourseCoursewareMaps(
	    Set<CourseCoursewareMap> courseCoursewareMaps)
    {
	this.courseCoursewareMaps = courseCoursewareMaps;
    }

}