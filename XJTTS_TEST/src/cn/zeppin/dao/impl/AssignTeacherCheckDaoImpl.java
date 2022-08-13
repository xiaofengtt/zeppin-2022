package cn.zeppin.dao.impl;

import java.util.List;

import cn.zeppin.dao.IAssignTeacherCheckDao;
import cn.zeppin.entity.AssignTeacherCheck;

public class AssignTeacherCheckDaoImpl extends BaseDaoImpl<AssignTeacherCheck, Integer> implements IAssignTeacherCheckDao {

	@Override
	public List<AssignTeacherCheck> getAssignTeacherChecksByTeacherRecordId(int teacherRecordId) {
		
		String hql = "from AssignTeacherCheck t where t.teacherTrainingRecords=" + teacherRecordId+" order by t.checktime DESC";
		return this.getListByHSQL(hql);
		
	}
}
