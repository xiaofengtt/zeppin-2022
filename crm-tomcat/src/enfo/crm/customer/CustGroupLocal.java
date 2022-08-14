package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustGroupVO;

public interface CustGroupLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ӿͻ�Ⱥ��
	 * @param vo
	 * @throws BusiException
	 */
	void appendCustGroup(CustGroupVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�Ⱥ��
	 * @param vo
	 * @throws BusiException
	 */
	void modiCustGroup(CustGroupVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ͻ�Ⱥ��
	 * @param vo
	 * @throws BusiException
	 */
	void delCustGroup(CustGroupVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���пͻ�Ⱥ�������νṹ��ʾ
	 * @param vo(GROUPID,INPUTMAN)
	 * @return list
	 * @throws BusiException
	 */
	List queryAll(CustGroupVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���пͻ�Ⱥ��
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	List queryByCustId(Integer cust_id) throws BusiException;

}