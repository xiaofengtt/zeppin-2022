package enfo.crm.score;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface ScoreRuleLocal extends IBusiExLocal {

	IPageList queryScoreRule(ScoreRuleVo vo, int pageIndex, int pageSize) throws BusiException;

	void addScoreRule(ScoreRuleVo vo) throws Exception;

	void modiScoreRule(ScoreRuleVo vo) throws Exception;

	void delScoreRule(ScoreRuleVo vo) throws Exception;

}