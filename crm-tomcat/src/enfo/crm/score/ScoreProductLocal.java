package enfo.crm.score;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface ScoreProductLocal extends IBusiExLocal {

	IPageList queryScoreProduct(ScoreProductVo vo, int pageIndex, int pageSize) throws BusiException;

	void addScoreProduct(ScoreProductVo vo) throws Exception;

	void modiScoreProduct(ScoreProductVo vo) throws Exception;

	void delScoreProduct(ScoreProductVo vo) throws Exception;

}