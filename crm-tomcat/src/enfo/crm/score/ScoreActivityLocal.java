package enfo.crm.score;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface ScoreActivityLocal extends IBusiExLocal {

	IPageList queryScoreActivity(ScoreActivityVo vo, int pageIndex, int pageSize) throws BusiException;

	void addScoreActivity(ScoreActivityVo vo) throws Exception;

	void modiScoreActivity(ScoreActivityVo vo) throws Exception;

}