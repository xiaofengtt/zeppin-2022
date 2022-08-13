package com.whaty.platform.entity.activity.score;

public class ScoreModifier {

	private String id;//成绩修改者ID
	
	private String name;//成绩修改者姓名
	
	private String type;//成绩修改者类型
	
	private String login_id;//如果修改者为管理员，为管理员登陆用户名
	
	private String gh;//如果修改者为教师，此为修改者工号

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
