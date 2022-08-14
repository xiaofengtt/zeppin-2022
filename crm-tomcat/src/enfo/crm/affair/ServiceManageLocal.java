package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.ServiceManageVO;

public interface ServiceManageLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_TServiceDefine(ServiceManageVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸ķ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void edit_TServiceDefine(ServiceManageVO vo) throws BusiException;

}