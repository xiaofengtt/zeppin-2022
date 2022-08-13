package cn.zeppin.action.sso;

import java.sql.Timestamp;
import java.util.List;

import cn.zeppin.entity.ProjectType;
public class UserSession {
	private Integer id;
	private String idcard;
	private String mobile;
	private String email;
	private String password;
	private Short role;
	private String name;
	private int organization;
	private int organizationLevel;
	private String organizationScode;
	private int createuser;
	private int level;
	private Short status;
	private Integer creator;
	private Timestamp creattime;
	private boolean isSchool;
	private String[] projectTypeScode;
	private List<ProjectType> lstProjectType;

	public UserSession() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getRole() {
		return role;
	}

	public void setRole(Short role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrganization() {
		return organization;
	}

	public void setOrganization(int organization) {
		this.organization = organization;
	}

	public int getCreateuser() {
		return createuser;
	}

	public void setCreateuser(int createuser) {
		this.createuser = createuser;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Timestamp getCreattime() {
		return creattime;
	}

	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}

	public int getOrganizationLevel() {
		return organizationLevel;
	}

	public void setOrganizationLevel(int organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	public boolean isSchool() {
		return isSchool;
	}

	public void setSchool(boolean isSchool) {
		this.isSchool = isSchool;
	}

	public String getOrganizationScode() {
		return organizationScode;
	}

	public void setOrganizationScode(String organizationScode) {
		this.organizationScode = organizationScode;
	}

	public String[] getProjectTypeScode() {
		return projectTypeScode;
	}

	public void setProjectTypeScode(String[] projectTypeScode) {
		this.projectTypeScode = projectTypeScode;
	}

	public List<ProjectType> getLstProjectType() {
		return lstProjectType;
	}

	public void setLstProjectType(List<ProjectType> lstProjectType) {
		this.lstProjectType = lstProjectType;
	}
	
	

}
