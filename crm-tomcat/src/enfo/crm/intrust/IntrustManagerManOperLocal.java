package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.IntrustManagerManOpVO;

public interface IntrustManagerManOperLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ���������˳�Ա��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void addManagerOp(IntrustManagerManOpVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ɾ�������˳�Ա��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delManagerManOp(IntrustManagerManOpVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸������˳�Ա��Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiManagerOp(IntrustManagerManOpVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ�����˳�Ա��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList queryManagerOp(IntrustManagerManOpVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ�����˳�Ա��Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryManagerOp(IntrustManagerManOpVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ø�Ա��������������MANAGER_ID
	 * @param op_code
	 * @return
	 * @throws Exception
	 */
	List queryManagerOp(Integer op_code) throws Exception;

}