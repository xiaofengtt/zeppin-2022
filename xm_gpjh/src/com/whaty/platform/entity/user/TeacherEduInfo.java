/**
 * 
 */
package com.whaty.platform.entity.user;

/**
 * @author chenjian
 * 
 */
public class TeacherEduInfo {

	private String gh = null;// ��ʦ����

	private String teach_level = null;// ��ʦ����

	private String teach_time = null;// ����ʱ��

	private String dep_name = null;// ����ѧԺ����

	private String dep_id = null;// ����ѧԺ���

	private String status = null;// ��ʦ״̬

	private String work_kind = null;// ��������

	private String researchDirection = null;// �о�����

	private String photo_link = null; // ��ƽ̨�е���Ƭλ��

	private String type ="0";      //0 Ϊ��ͨ��ʦ��1Ϊָ����ʦ
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResearchDirection() {
		return researchDirection;
	}

	public void setResearchDirection(String researchDirection) {
		this.researchDirection = researchDirection;
	}

	public String getWork_kind() {
		return work_kind;
	}

	public void setWork_kind(String work_kind) {
		this.work_kind = work_kind;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getTeach_level() {
		return teach_level;
	}

	public void setTeach_level(String teach_level) {
		this.teach_level = teach_level;
	}

	public String getTeach_time() {
		return teach_time;
	}

	public void setTeach_time(String teach_time) {
		this.teach_time = teach_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getDep_id() {
		return dep_id;
	}

	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}

	public String getPhoto_link() {
		return photo_link;
	}

	public void setPhoto_link(String photo_link) {
		this.photo_link = photo_link;
	}
}
