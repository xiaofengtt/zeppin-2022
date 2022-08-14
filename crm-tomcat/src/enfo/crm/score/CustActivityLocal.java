package enfo.crm.score;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;

public interface CustActivityLocal extends IBusiExLocal {

	IPageList queryCustActivity(CustActivityVo vo, int pageIndex, int pageSize) throws BusiException;

	void addCustActivity(CustActivityVo vo) throws Exception;

	void delCustActivity(CustActivityVo vo) throws Exception;

}