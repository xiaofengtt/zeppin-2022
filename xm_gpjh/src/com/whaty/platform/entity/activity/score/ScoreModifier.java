package com.whaty.platform.entity.activity.score;

public class ScoreModifier {

	private String id;//�ɼ��޸���ID
	
	private String name;//�ɼ��޸�������
	
	private String type;//�ɼ��޸�������
	
	private String login_id;//����޸���Ϊ����Ա��Ϊ����Ա��½�û���
	
	private String gh;//����޸���Ϊ��ʦ����Ϊ�޸��߹���

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
