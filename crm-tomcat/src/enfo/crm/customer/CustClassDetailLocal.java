package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustClassDetailVO;

public interface CustClassDetailLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ӿͻ��ּ�����֮��ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void append(CustClassDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ��ּ�����֮��ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void modify(CustClassDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ͻ��ּ�����֮��ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(CustClassDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ��ּ�����֮��ϸ
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query(CustClassDetailVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ��ֲ���
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query2(CustClassDetailVO vo) throws BusiException;

}