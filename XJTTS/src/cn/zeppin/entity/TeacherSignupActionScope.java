/**
 * 
 */
package cn.zeppin.entity;

import java.util.List;
import java.util.Map;


/**
 * 教师培训报名作用域信息
 * @author Administrator
 *
 */
public class TeacherSignupActionScope implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3380260617225791010L;
	private Map<String, Object> years;//startyear:YYYY~endyear:YYYY
	private List<String> projectLevel;
	private List<String> projectType;
	private List<String> project;
	private List<String> subject;
	private Map<String, Object> hours;//startline ~ endline 0~9999
	
    
    public TeacherSignupActionScope() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TeacherSignupActionScope(Map<String, Object> years,
			List<String> projectLevel, List<String> projectType,
			List<String> project, List<String> subject) {
		super();
		this.years = years;
		this.projectLevel = projectLevel;
		this.projectType = projectType;
		this.project = project;
		this.subject = subject;
	}


	public Map<String, Object> getYears() {
		return years;
	}


	public void setYears(Map<String, Object> years) {
		this.years = years;
	}


	public List<String> getProjectLevel() {
		return projectLevel;
	}


	public void setProjectLevel(List<String> projectLevel) {
		this.projectLevel = projectLevel;
	}


	public List<String> getProjectType() {
		return projectType;
	}


	public void setProjectType(List<String> projectType) {
		this.projectType = projectType;
	}


	public List<String> getProject() {
		return project;
	}


	public void setProject(List<String> project) {
		this.project = project;
	}


	public List<String> getSubject() {
		return subject;
	}


	public void setSubject(List<String> subject) {
		this.subject = subject;
	}


	
	public Map<String, Object> getHours() {
		return hours;
	}
	


	public void setHours(Map<String, Object> hours) {
		this.hours = hours;
	}

	
}
