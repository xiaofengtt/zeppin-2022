package cn.zeppin.service.api;

import java.util.List;

import cn.zeppin.entity.Keyword;

public interface IKeywordService {
	
	/**
	 * 添加
	 */
	public Keyword addKeyword(Keyword keyword);
	
	public List<Keyword> getKeyWordList(Integer number);
}
