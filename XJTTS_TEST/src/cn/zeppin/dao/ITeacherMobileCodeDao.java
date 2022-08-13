package cn.zeppin.dao;

import cn.zeppin.entity.TeacherMobileCode;

public interface ITeacherMobileCodeDao extends IBaseDao<TeacherMobileCode, Integer> {
	
	public TeacherMobileCode getRecordByUuid(String uuid);

	
}
