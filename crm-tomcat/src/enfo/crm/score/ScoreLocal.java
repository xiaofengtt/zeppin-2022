package enfo.crm.score;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;

public interface ScoreLocal {

	IPageList queryCustScore(ScoreVo vo, int pageIndex, int pageSize) throws BusiException;

}