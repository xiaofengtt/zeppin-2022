package enfo.crm.callcenter;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.SeatVO;

public interface SeatLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ��¼����� 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_seatActivity(SeatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ��¼�����  ��ҳ
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList qyery_page_seatActivity(SeatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ�绰�¼�����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_seatCallDetail(SeatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ�绰�¼����� ��ҳ
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page_seatCallDetail(SeatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ�������屨��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_seatAbandon(SeatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ�������屨�� ��ҳ
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page_seatAbandon(SeatVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ����������
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_seatWorkload(SeatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ���������� ��ҳ
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page_seatWorkload(SeatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ����绰����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_seatCallin(SeatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ����绰���� ��ҳ
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page_seatCallin(SeatVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ�����绰����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List query_seatCallout(SeatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ϯ�����绰���� ��ҳ
	 * @param vo
	 * @param page
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query_page_seatCallout(SeatVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

}