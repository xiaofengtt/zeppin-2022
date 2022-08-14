package enfo.crm.customer;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.EmailListVO;

public interface EmailListLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����ʼ���Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	Integer append(EmailListVO vo) throws BusiException;

}