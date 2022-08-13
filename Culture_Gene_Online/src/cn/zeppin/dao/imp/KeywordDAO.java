package cn.zeppin.dao.imp;


import java.util.List;

import cn.zeppin.dao.api.IKeywordDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Keyword;

public class KeywordDAO extends HibernateTemplateDAO<Keyword, Integer> implements IKeywordDAO{

	public List<Keyword> getKeyWordList(Integer number){
		StringBuilder sb = new StringBuilder();
		sb.append(" from Keyword GROUP BY word ORDER BY count(word) desc");
		return this.getByHQL(sb.toString(), 0, number);
	}

}