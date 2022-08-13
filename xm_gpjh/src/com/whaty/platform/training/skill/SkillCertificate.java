/**
 * 
 */
package com.whaty.platform.training.skill;

/**该类描述了证书信息
 * @author Administrator
 *
 */
public class SkillCertificate {
	
	//证书所属技能
	private Skill skill;
	
	//证书类型
	private String type;
	
	//证书内容
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
