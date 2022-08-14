package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PartnerVO;

public interface PartnerLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * �������������Ϣ ����partn_id
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer append(PartnerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void modi(PartnerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ɾ�����������Ϣ
	 * @param vo
	 * @throws BusiException
	 */
	void delete(PartnerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������ѯ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query(PartnerVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������ѯ ��ҳ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_query(PartnerVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

}