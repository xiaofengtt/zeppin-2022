package enfo.crm.customer;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.EmailAttachVO;

public interface EmailAttachLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 添加邮件附件
	 * @param vo
	 * @throws BusiException
	 */
	Integer appendAttach(EmailAttachVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no2
	 * @param attach_name2
	 * @param strFolder
	 * @return
	 * @throws Exception
	 * //保存邮件附件到数据库
	 */
	boolean updateToDB(Long serial_no2, String attach_name2, String strFolder) throws Exception;

}