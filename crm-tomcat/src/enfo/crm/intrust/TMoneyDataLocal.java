package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TMoneyDataVO;

public interface TMoneyDataLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ �ʽ�¼�����
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listForPage(TMoneyDataVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ �ʽ�¼�����
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listForPagePZ(TMoneyDataVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ѯ �ʽ�¼�����
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listAll(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ��ʽ�¼�� ��� ʹ��CRM����
	 * @param vo
	 * @throws BusiException
	 */
	void append(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ��ʽ�¼�� �༭
	 * @param vo
	 * @throws BusiException
	 */
	void modify(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ��ʽ�¼�� ɾ��
	 * @param vo
	 * @throws BusiException
	 */
	void delete(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ��ʽ�¼�����
	 * @param vo
	 * @throws BusiException
	 */
	void input_check(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ��ʽ����
	 * @param vo
	 * @throws BusiException
	 */
	void check(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 
	 * @param vo
	 * @throws BusiException
	 */
	List getTmoneydata(String serial_no_list) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Զ����ɸ��ֺ�ͬ��Ϣ
	 * @return
	 * @throws Exception
	 */
	void combineContract(TMoneyDataVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 * @param cust_id
	 * @param cust_name
	 * @param product_id
	 * @throws Exception
	 */
	void modiSubproductid(Integer serial_no, Integer sub_product_id, Integer product_id) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������ȫ��ͬ ��ҳ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listContractMendForPage(TMoneyDataVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��������ȫ��ͬ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listContractMend(TMoneyDataVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @throws Exception
	 */
	void checkPurchanseContractMend(TMoneyDataVO vo) throws Exception;

	/** 
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ���ͬ���ɻָ���ѯ(��ҳ)
	 */
	IPageList queryContractRestore(TMoneyDataVO vo, int pageIndex, int pageSize) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ���ͬ���ɻָ�
	 */
	void combineMoneyDataRestore(TMoneyDataVO vo) throws Exception;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param serial_no
	 * @param cust_id
	 * @param cust_name
	 * @param product_id
	 * @throws Exception
	 */
	void modiWTR(Integer serial_no, Integer cust_id, String cust_name) throws Exception;

}