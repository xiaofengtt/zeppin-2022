package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.TeachingLanguage;

public interface ITeachingLanguageService extends
		IBaseService<TeachingLanguage, Integer> {
	public void deleteTeachingLanguage(int teacher);

	/**
	 * 9月12日加：通过参数获取TeachingLanguage表里的记录
	 * 
	 * @param map
	 * @return
	 */
	public List<TeachingLanguage> getListByParams(Map<String, Object> map);
}
