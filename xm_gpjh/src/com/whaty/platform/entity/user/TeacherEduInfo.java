/**
 * 
 */
package com.whaty.platform.entity.user;

/**
 * @author chenjian
 * 
 */
public class TeacherEduInfo {

	private String gh = null;// 教师工号

	private String teach_level = null;// 教师类型

	private String teach_time = null;// 工作时间

	private String dep_name = null;// 所属学院名称

	private String dep_id = null;// 所属学院编号

	private String status = null;// 教师状态

	private String work_kind = null;// 工作性质

	private String researchDirection = null;// 研究方向

	private String photo_link = null; // 在平台中的照片位置

	private String type ="0";      //0 为普通教师；1为指导教师
	
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
