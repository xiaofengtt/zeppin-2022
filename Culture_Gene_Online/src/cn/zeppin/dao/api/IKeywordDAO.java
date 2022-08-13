package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.Keyword;

public interface IKeywordDAO extends IBaseDAO<Keyword, Integer> {

	public List<Keyword> getKeyWordList(Integer number);
	
}
