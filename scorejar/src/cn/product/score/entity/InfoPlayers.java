package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;



public class InfoPlayers implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1202525223950814371L;
	
	
    private String uuid;
	private String name;
	private String cnname;
	private String number;
	private String country;
	private String type;
	private String age;
	private String matchplayed;
	private String goals;
	private String yellowcards;
	private String redcards;
	
    private String status;
    private Timestamp createtime;
    private String creator;
    private String interfaceid;
    
    public class InfoPlayersStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMatchplayed() {
		return matchplayed;
	}

	public void setMatchplayed(String matchplayed) {
		this.matchplayed = matchplayed;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public String getYellowcards() {
		return yellowcards;
	}

	public void setYellowcards(String yellowcards) {
		this.yellowcards = yellowcards;
	}

	public String getRedcards() {
		return redcards;
	}

	public void setRedcards(String redcards) {
		this.redcards = redcards;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getInterfaceid() {
		return interfaceid;
	}

	public void setInterfaceid(String interfaceid) {
		this.interfaceid = interfaceid;
	}
}