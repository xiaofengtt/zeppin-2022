package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ServiceTaskVO;

public interface ServiceTaskLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ���������� By InputMan
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_dealTask_page(ServiceTaskVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ��������
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page(ServiceTaskVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����������Ϣ ����SerialNo
	 * @param vo
	 * @throws BusiException
	 */
	Object append(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸�������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void modi(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ��������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * һ���ѯ������Ϣ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ӷ���������ϸ ���ӿͻ�
	 * @param vo
	 * @throws BusiException
	 */
	void append_details(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ����ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void delete_details(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��ϸ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_details(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ��ϸ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	IPageList query_detailsa(ServiceTaskVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������
	 * @param vo
	 * @throws BusiException
	 */
	void allocate(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������
	 * @param vo
	 * @throws BusiException
	 */
	void treat(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ����������ϸ
	 * @param vo
	 * @throws BusiException
	 */
	void treat_details(ServiceTaskVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������
	 * @param vo
	 * @throws BusiException
	 */
	void feedback(ServiceTaskVO vo) throws BusiException;

}