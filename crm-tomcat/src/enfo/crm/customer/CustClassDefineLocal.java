package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.vo.CustClassDefineVO;
import enfo.crm.vo.CustClassifyVO;

public interface CustClassDefineLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ӿͻ��ּ�����
	 * @param vo
	 * @throws BusiException
	 */
	void append(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ��ּ�����
	 * @param vo
	 * @throws BusiException
	 */
	void modify(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ͻ��ּ�����
	 * @param vo
	 * @throws BusiException
	 */
	void delete(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ��ּ�����
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ�����
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query_custClassDefine(CustClassDefineVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ݲ�ͬ���࣬��ѯ��ͬ���ͻ���ռ����
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	List query_custClassify(CustClassifyVO vo) throws BusiException;

}