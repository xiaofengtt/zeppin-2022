/**
 * 
 */
package com.whaty.platform.training.skill;

/**����������֤����Ϣ
 * @author Administrator
 *
 */
public class SkillCertificate {
	
	//֤����������
	private Skill skill;
	
	//֤������
	private String type;
	
	//֤������
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Skill getSkill() {
		return skill;
	}
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
