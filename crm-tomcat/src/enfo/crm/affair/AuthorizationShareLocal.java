package enfo.crm.affair;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.AuthorizationShareVO;

public interface AuthorizationShareLocal extends IBusiExLocal{

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ��Ȩ��Ϣ������¼  
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	List load(Integer serial_no) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ������Ȩ�������¼
	 * @param vo
	 */
	Integer append(AuthorizationShareVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ��Ȩ����Ȩ��¼��Ϣ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query_authorize(AuthorizationShareVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ�ͻ���Ϣ�����¼
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query_share(AuthorizationShareVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ���ͻ���Ȩ��
	 * @param vo
	*/
	void delete(AuthorizationShareVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸���Ȩ��¼����״̬�����á����ã�
	 * @param vo
	*/
	void modiStatus(AuthorizationShareVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �޸Ŀͻ���Ȩ����Ȩ��¼
	 * @param vo
	 * 
	 */
	void modi(AuthorizationShareVO vo) throws BusiException;

}