package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustomerClassVO;

public interface CustomerClassLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi(CustomerClassVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ������ҳ��ʾ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	*/
	IPageList list_fenye(CustomerClassVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ�����б���ʾ
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List list_leibiao(CustomerClassVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ�δ��˵Ŀͻ�����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList queryNotCheckClass(CustomerClassVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��˿ͻ�����
	 * @param vo
	 * @throws BusiException
	 */
	void checkCustomerClass(CustomerClassVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ����ϵĻ�����Ϣ�����б���ʾ
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List list_leibiao1(CustomerClassVO vo) throws BusiException;

}