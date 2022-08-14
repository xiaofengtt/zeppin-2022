package cn.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.score.entity.InfoPlayers;

public class InfoPlayersVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4724809716903698376L;
	
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
    
    public InfoPlayersVO(InfoPlayers ip){
    	this.uuid = ip.getUuid();
    	this.name = ip.getName();
    	this.cnname = ip.getCnname();
    	this.number = ip.getNumber();
    	this.country = ip.getCountry();
    	this.type = ip.getType();
    	this.age = ip.getAge();
    	this.matchplayed = ip.getMatchplayed();
    	this.goals = ip.getGoals();
    	this.yellowcards = ip.getYellowcards();
    	this.redcards = ip.getRedcards();
    	this.status = ip.getStatus();
    	this.createtime = ip.getCreatetime();
    	this.creator = ip.getCreator();
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

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
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
}