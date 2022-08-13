package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.AssignTeacherCheck;

public interface IAssignTeacherCheckDao extends IBaseDao<AssignTeacherCheck, Integer> {
	
	public List<AssignTeacherCheck> getAssignTeacherChecksByTeacherRecordId(int teacherRecordId);
	
}
