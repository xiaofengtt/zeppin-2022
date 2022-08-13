/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.score.ScoreDef;
import com.whaty.platform.entity.user.Student;

/**���������˺�ѧ��ֱ�ӹҹ��ĳɼ��������ҵ���ĳɼ����ļ�Ӣ��ɼ���ѧλӢ��ɼ��ȣ�
 * �ɼ�������StudentScoreType�ж���
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
