package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ProductVO;
import enfo.crm.vo.RatingVO;

public interface RatingLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ��÷���ϸ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_tcustscoredetail(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ��÷�
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_tcustscore(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ�������
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pageList_tcustrating(RatingVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ�ͻ��÷���ϸ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List list_tcustscoredetail(RatingVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ���ӿͻ�����
	 * @param vo
	 * @throws BusiException
	 */
	void custRating(RatingVO vo) throws BusiException;

	/**
	 * ��Ŀ���յȼ� ����ֵ �޸�
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void modiLevelScore(ProductVO vo) throws BusiException;

	/**
	 * ��ѯ��Ŀ���յȼ� ����ֵ
	 * @ejb.interface-method view-type = "local"
	 * @param product_id
	 * @return
	 * @throws BusiException
	 */
	List queryLevelScore(Integer product_id) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ͻ������� ��ѯ ѡ�������ͻ�������
	 * @param vo
	 * @param cust_id
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	List queryChangeCustomerScore(RatingVO vo, String cust_id, int pageIndex, int pageSize) throws BusiException;

}