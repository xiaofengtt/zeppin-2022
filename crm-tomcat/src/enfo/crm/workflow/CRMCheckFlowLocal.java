package enfo.crm.workflow;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface CRMCheckFlowLocal extends IBusiExLocal {

	/**
	 * 更新业务状态
	 * @param object_id
	 * @param object_type
	 * @param action_flag
	 * @throws BusiException
	 * @ejb.interface-method view-type = "local"
	 */
	void actionFlow(String object_id, String object_type, String action_flag) throws BusiException;

}