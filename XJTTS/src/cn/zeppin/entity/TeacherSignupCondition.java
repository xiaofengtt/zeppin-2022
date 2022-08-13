/**
 * 
 */
package cn.zeppin.entity;

import java.util.List;
import java.util.Map;


/**
 * 教师培训报名条件限制信息
 * @author Administrator
 *
 */
public class TeacherSignupCondition implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 270136284529284334L;
    private List<String> teachingGrade;
    private List<String> teachingSubject;
    private List<String> teachingLanguage;
    private String multiLanguage;
    private List<String> jobTitle;
    private List<String> ethnic;
    private Map<String, Object> teachingAge;// startTimeAge:0 ~ endTimeAge:100
    private Map<String, Object> teacherAge;//  startAge:0 ~ endAge:100
    private List<String> police;
	
    public TeacherSignupCondition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherSignupCondition(List<String> teachingGrade, List<String> teachingSubject,
			List<String> teachingLanguage, String multiLanguage,
			List<String> jobTitle, List<String> ethnic,
			Map<String, Object> teachingAge, Map<String, Object> teacherAge,
			List<String> police) {
		super();
		this.teachingGrade = teachingGrade;
		this.teachingSubject = teachingSubject;
		this.teachingLanguage = teachingLanguage;
		this.multiLanguage = multiLanguage;
		this.jobTitle = jobTitle;
		this.ethnic = ethnic;
		this.teachingAge = teachingAge;
		this.teacherAge = teacherAge;
		this.police = police;
	}


	public List<String> getTeachingGrade() {
		return teachingGrade;
	}

	public void setTeachingGrade(List<String> teachingGrade) {
		this.teachingGrade = teachingGrade;
	}

	public List<String> getTeachingSubject() {
		return teachingSubject;
	}

	public void setTeachingSubject(List<String> teachingSubject) {
		this.teachingSubject = teachingSubject;
	}

	public List<String> getTeachingLanguage() {
		return teachingLanguage;
	}

	public void setTeachingLanguage(List<String> teachingLanguage) {
		this.teachingLanguage = teachingLanguage;
	}

	public String getMultiLanguage() {
		return multiLanguage;
	}

	public void setMultiLanguage(String multiLanguage) {
		this.multiLanguage = multiLanguage;
	}

	public List<String> getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(List<String> jobTitle) {
		this.jobTitle = jobTitle;
	}

	public List<String> getEthnic() {
		return ethnic;
	}

	public void setEthnic(List<String> ethnic) {
		this.ethnic = ethnic;
	}

	public Map<String, Object> getTeachingAge() {
		return teachingAge;
	}

	public void setTeachingAge(Map<String, Object> teachingAge) {
		this.teachingAge = teachingAge;
	}

	public Map<String, Object> getTeacherAge() {
		return teacherAge;
	}

	public void setTeacherAge(Map<String, Object> teacherAge) {
		this.teacherAge = teacherAge;
	}

	public List<String> getPolice() {
		return police;
	}

	public void setPolice(List<String> police) {
		this.police = police;
	}
    
    
    
}
