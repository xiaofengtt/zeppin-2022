/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.score.ScoreDef;
import com.whaty.platform.entity.user.Student;

/**本类描述了和学生直接挂钩的成绩，比如毕业论文成绩，四级英语成绩，学位英语成绩等，
 * 成绩类型在StudentScoreType中定义
 * @author chenjian
 *
 */
public abstract class StudentScore {
	
	private Student student;
	
	private List scoreList;

	public StudentScore() {
		
	}

	public List getScoreList() {
		return scoreList;
	}

	public void setScoreList(List scoreList) {
		this.scoreList = scoreList;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	public abstract ScoreDef getScore(String scoreType) throws PlatformException;

}
