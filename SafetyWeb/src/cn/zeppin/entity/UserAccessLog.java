package cn.zeppin.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserAccessLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_access_log", catalog = "cetv")
public class UserAccessLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -62558287825736774L;
	private Long id;
	private User user;
	private Integer accessType;
	private String accessOs;
	private Timestamp accessTime;
	private String accessIp;
	private String accessBrowser;
	private String accessTablename;
	private Integer accessResourseId;
	private String accessEquipment;
	private int accessClient;

	// Constructors

	/** default constructor */
	public UserAccessLog() {
	}

	/** minimal constructor */
	public UserAccessLog(User user, Integer accessType, String accessOs, Timestamp accessTime, String accessIp) {
		this.user = user;
		this.accessType = accessType;
		this.accessOs = accessOs;
		this.accessTime = accessTime;
		this.accessIp = accessIp;
	}

	/** full constructor */
	public UserAccessLog(User user, Integer accessType, String accessOs, Timestamp accessTime, String accessIp, String accessBrowser, String accessTablename, Integer accessResourseId, String accessEquipment) {
		this.user = user;
		this.accessType = accessType;
		this.accessOs = accessOs;
		this.accessTime = accessTime;
		this.accessIp = accessIp;
		this.accessBrowser = accessBrowser;
		this.accessTablename = accessTablename;
		this.accessResourseId = accessResourseId;
		this.accessEquipment = accessEquipment;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "ACCESS_TYPE", nullable = false)
	public Integer getAccessType() {
		return this.accessType;
	}

	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}

	@Column(name = "ACCESS_OS", nullable = false)
	public String getAccessOs() {
		return this.accessOs;
	}

	public void setAccessOs(String accessOs) {
		this.accessOs = accessOs;
	}

	@Column(name = "ACCESS_TIME", nullable = false, length = 19)
	public Timestamp getAccessTime() {
		return this.accessTime;
	}

	public void setAccessTime(Timestamp accessTime) {
		this.accessTime = accessTime;
	}

	@Column(name = "ACCESS_IP", nullable = false, length = 50)
	public String getAccessIp() {
		return this.accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	@Column(name = "ACCESS_BROWSER", length = 100)
	public String getAccessBrowser() {
		return this.accessBrowser;
	}

	public void setAccessBrowser(String accessBrowser) {
		this.accessBrowser = accessBrowser;
	}

	@Column(name = "ACCESS_TABLENAME", length = 100)
	public String getAccessTablename() {
		return this.accessTablename;
	}

	public void setAccessTablename(String accessTablename) {
		this.accessTablename = accessTablename;
	}

	@Column(name = "ACCESS_RESOURSE_ID")
	public Integer getAccessResourseId() {
		return this.accessResourseId;
	}

	public void setAccessResourseId(Integer accessResourseId) {
		this.accessResourseId = accessResourseId;
	}

	@Column(name = "ACCESS_EQUIPMENT", length = 100)
	public String getAccessEquipment() {
		return this.accessEquipment;
	}

	public void setAccessEquipment(String accessEquipment) {
		this.accessEquipment = accessEquipment;
	}

	@Column(name = "ACCESS_CLIENT")
	public int getAccessClient() {
		return accessClient;
	}

	public void setAccessClient(int accessClient) {
		this.accessClient = accessClient;
	}

}