/**
 * 
 */
package Organization;

/**
 * @author Administrator
 *
 */
public class TeacherTrainingRecordsEx {
	
	private int id;
	private String projectName;
	private String subjectName;
	private String trainingCollege;
	private Boolean isRecords;
	
	public TeacherTrainingRecordsEx(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTrainingCollege() {
		return trainingCollege;
	}

	public void setTrainingCollege(String trainingCollege) {
		this.trainingCollege = trainingCollege;
	}

	public Boolean getIsRecords() {
		return isRecords;
	}

	public void setIsRecords(Boolean isRecords) {
		this.isRecords = isRecords;
	}

}
