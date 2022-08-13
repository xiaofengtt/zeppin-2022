package cn.zeppin.service.imp;

import java.util.List;

import cn.zeppin.dao.api.IKeywordDAO;
import cn.zeppin.entity.Keyword;
import cn.zeppin.service.api.IKeywordService;

public class KeywordService implements IKeywordService {

	
	private IKeywordDAO keywordDAO;
	
	public IKeywordDAO getKeywordDAO() {
		return keywordDAO;
	}

	public void setKeywordDAO(IKeywordDAO keywordDAO) {
		this.keywordDAO = keywordDAO;
	}
	
	/**
	 * 添加
	 */
	public Keyword addKeyword(Keyword keyword) {
		return getKeywordDAO().save(keyword);
	}
	
	public List<Keyword> getKeyWordList(Integer number){
		return getKeywordDAO().getKeyWordList(number);
	}
}
