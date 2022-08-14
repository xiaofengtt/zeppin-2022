package enfo.crm.score;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface ScoreExchangeLocal extends IBusiExLocal {

	void addScoreExchange(ScoreExchangeVo vo) throws Exception;

	IPageList queryScoreExchange(ScoreExchangeVo vo, int pageIndex, int pageSize) throws BusiException;

	void modiscoreExchange(ScoreExchangeVo vo) throws Exception;

}