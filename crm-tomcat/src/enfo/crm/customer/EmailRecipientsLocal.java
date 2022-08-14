package enfo.crm.customer;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.EmailRecipientsVO;

public interface EmailRecipientsLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����ռ�����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void appendReci(EmailRecipientsVO vo) throws BusiException;

}