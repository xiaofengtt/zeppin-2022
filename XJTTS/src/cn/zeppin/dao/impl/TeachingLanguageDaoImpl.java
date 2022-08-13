package cn.zeppin.dao.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public List<TeachingLanguage> getListByParams(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("from TeachingLanguage where 1=1");
		if (map.get("teacher") != null && !map.get("teacher").equals("")) {
			sb.append(" and teacher = '").append(map.get("teacher"))
					.append("'");
		}
		if (map.get("id") != null && !map.get("id").equals("")) {
			sb.append(" and id = '").append(map.get("id")).append("'");
		}
		return this.getListByHSQL(sb.toString());
	}

}
