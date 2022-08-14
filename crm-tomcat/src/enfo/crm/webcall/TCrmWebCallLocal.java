package enfo.crm.webcall;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.TCrmWebCallVO;

public interface TCrmWebCallLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��Ӳ���Ա��UUID��Ӧ
	 * @param vo
	 * @throws BusiException
	 */
	void appendOperatorUUID(TCrmWebCallVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���Ҳ���Ա��UUID��Ӧ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listOperatorUUID(TCrmWebCallVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ӿͻ�ID��webcall��Ӧ
	 * @param vo
	 * @throws BusiException
	 */
	void appendCrmWebcall(TCrmWebCallVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���Ҳ���Ա��UUID��Ӧ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listCrmWebcall(TCrmWebCallVO vo) throws BusiException;

}