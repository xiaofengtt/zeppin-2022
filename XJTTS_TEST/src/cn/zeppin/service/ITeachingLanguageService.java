package cn.zeppin.service;

import cn.zeppin.entity.TeachingLanguage;

public interface ITeachingLanguageService extends
	IBaseService<TeachingLanguage, Integer>
{
	public void deleteTeachingLanguage(int teacher);
}
