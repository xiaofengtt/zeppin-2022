package cn.zeppin.dao.impl;


import cn.zeppin.dao.ITeacherMobileCodeDao;
import cn.zeppin.entity.TeacherMobileCode;

public class TeacherMobileCodeDaoImpl extends BaseDaoImpl<TeacherMobileCode, Integer> implements ITeacherMobileCodeDao {

	@Override
	public TeacherMobileCode getRecordByUuid(String uuid) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		
		sb.append("from TeacherMobileCode t where t.uuid='");
		sb.append(uuid+"'");
		
		TeacherMobileCode tmc = (TeacherMobileCode)this.getObjectByHql(sb.toString(), null);
		return tmc;
	}

	

}
