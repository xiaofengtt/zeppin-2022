package cn.zeppin.dao;

import cn.zeppin.entity.TeachingLanguage;

public interface ITeachingLanguageDao extends IBaseDao<TeachingLanguage, Integer> {

	public void deleteTeachingLanguage(int teacher);
}
