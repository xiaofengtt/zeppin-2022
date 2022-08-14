package enfo.crm.system;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;

public interface PortalLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * �޸� Protal �û�״̬��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updatePortalUserState(String state, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local" 
	 * �޸� Portal��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updatePortalAdd(String portalCodes, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * �޸�Portal CLOSE��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updatePortalClose(String portalCode, Integer opCode, String visiable) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * �޸�Portal Collapse��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updatePortalCollapse(String portalCode, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * �޸�Portal Collapse��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	void updateProtalExpand(String portalCode, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ��ѯPortal��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryMyPortal(Integer bookCode, Integer opCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * ��ѯ��ҳportal�ڹ��ܲ���
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryPortalMenu3(Integer op_code, String language) throws BusiException;

}