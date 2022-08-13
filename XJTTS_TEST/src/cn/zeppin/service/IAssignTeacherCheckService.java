package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.AssignTeacherCheck;

public interface IAssignTeacherCheckService extends IBaseService<AssignTeacherCheck, Integer> {
	
	public List<AssignTeacherCheck> getAssignTeacherChecksByTeacherRecordId(int teacherRecordId);
	
}
