package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class Category implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7383086998865460664L;
	
	@Id
	private String uuid;
	private String name;
	private String shortname;
	private Integer level;
	private String parent;
	private String scode;
	private String icon;
	private Boolean istag;
	private Boolean flagGet;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	private String interfaceid;
	
	public class CategoryStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public class CategoryMainUuid{
		public final static String FOOTBALL = "0ae36f97-90b4-11e8-95fb-fcaa14314cbe";
		public final static String UCL = "5f61cb0b-8d40-4449-9d25-cbcddde89a57";
		public final static String PREMIERLEAGUE = "9bd4e736-e57f-46d2-ab25-b91a4c36b061";
		public final static String LALIGA = "dad45ea2-5c9d-4102-8445-b9720267f93d";
		public final static String SERIEA = "adf1fb28-306d-4870-96e9-875402d044b7";
		public final static String BUNDESLIGA = "42fee8ba-677f-4152-b10c-69d3befc6467";
		public final static String CSL = "f4e00df4-6f3f-4de3-b64e-0b2b6910e07e";
		public final static String CHINA = "fb64c611-25b3-42ef-98c7-96b1a2db38a5";
		public final static String AFCCL = "5c3a7159-70e5-490e-b242-328c2f5c3cc1";
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getIstag() {
		return istag;
	}

	public void setIstag(Boolean istag) {
		this.istag = istag;
	}

	public Boolean getFlagGet() {
		return flagGet;
	}

	public void setFlagGet(Boolean flagGet) {
		this.flagGet = flagGet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}


	public String getInterfaceid() {
		return interfaceid;
	}
	

	public void setInterfaceid(String interfaceid) {
		this.interfaceid = interfaceid;
	}
}