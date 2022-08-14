package enfo.crm.intrust;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TaskInfoVO;

public interface TaskInfoLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 */
	IPageList queryOpinfo(TaskInfoVO vo, int pageIndex, int pageSize) throws BusiException;

}