package cn.zeppin.service;

import cn.zeppin.entity.TeacherMobileCode;

public interface ITeacherMobileCodeService extends IBaseService<TeacherMobileCode, Integer> {

	public TeacherMobileCode getRecordByUuid(String uuid);
}
