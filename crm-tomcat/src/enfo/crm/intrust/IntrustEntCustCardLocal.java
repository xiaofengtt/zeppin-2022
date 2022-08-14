package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiFullLocal;
import enfo.crm.vo.EntCustCardVO;

public interface IntrustEntCustCardLocal extends IBusiFullLocal {

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ������ҵ�ͻ�֤����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void addEntCustCard(EntCustCardVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * �޸���ҵ�ͻ�֤����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modiEntCustCard(EntCustCardVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ɾ����ҵ�ͻ�֤����Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delEntCustCard(EntCustCardVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��ҵ�ͻ�֤����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List queryEntCustCard(EntCustCardVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * ��ѯ��ҵ�ͻ�֤����Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	String queryEntCustCardStr(EntCustCardVO vo) throws BusiException;

}