package cn.zeppin.dao.impl;

import cn.zeppin.dao.ITeachingLanguageDao;
import cn.zeppin.entity.TeachingLanguage;

public class TeachingLanguageDaoImpl extends BaseDaoImpl<TeachingLanguage, Integer> implements ITeachingLanguageDao{

	@Override
	public void deleteTeachingLanguage(int teacher) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("delete from TeachingLanguage tl where 1=1");
		sb.append(" and tl.teacher="+teacher);
		this.executeHSQL(sb.toString());
	}

}
