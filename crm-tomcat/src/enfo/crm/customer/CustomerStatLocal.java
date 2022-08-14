package enfo.crm.customer;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustomerStatVO;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.TcustmanagersVO;

public interface CustomerStatLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ������ϸ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getStatCustContract(CustomerStatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ������ϸ ��Ǩ����CRM
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getStatCustContract2(CustomerStatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ������ϸ ��ҳ 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getStatCustContract_page(CustomerStatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �Ϲ������ϸ ��ҳ  ��Ǩ����CRM
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getStatCustContract_page2(CustomerStatVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * �ͻ��Ϲ���ȷ���(���ͻ�����������) ��ҳ 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList getStatCustContract_page(CustomerStatVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ʧ�ͻ���ϸ
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getStatCustomerLevel(CustomerStatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ʧ�ͻ���ϸ ��ҳ
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException 
	 */
	IPageList getStatCustomerLevel_page(CustomerStatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * �ͻ�������
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List getVolueStat(CustomerStatVO vo) throws BusiException;

	/**
	 * �ͻ�����ֲ���ʾͼ
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List getAgeDistribution(CustomerStatVO vo) throws BusiException;

	/**
	 * �ͻ���������ʾͼ
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List getCategoryStat(CustomerStatVO vo) throws BusiException;

	/**
	 * �ͻ���Դ������ʾͼ
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	List getSourceStat(CustomerStatVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ʡ�������ֲ����ͻ�����
	 * @return
	 * @throws BusiException
	 */
	List getProvinceStat(Integer begin_date, Integer end_date) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ��ѯ���ڲ�Ʒ�Ŷ��Ϲ����
	 * @param vo
		@IN_SERIAL_NO	int
		@IN_PRODUCT_ID  INT
		@IN_TEAM_ID	int
		@IN_LINK_MAN_NAME NVARCHAR(40)
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryTeamProduct(TcustmanagersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ��ҳ���ؿͻ�����ͳ�ƿͻ����
	 * @param vo
		@IN_TEAM_ID     INT
		@IN_MANAGERNAME	nvarchar
		@IN_LEADER_NAME	varchar
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList pagelist_TeamManager(TcustmanagersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * Ӫ���ɱ�����-�ͻ�
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	IPageList getSaleCostCust(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * Ӫ���ɱ�����-��Ʒ
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	IPageList getSaleCostProduct(CustomerVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ֱ���ͻ�ͼ��ͳ�Ʒ����������ʾ
	 * @param begin_date
	 * @param end_date
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List queryDircetCustomerStat(Integer begin_date, Integer end_date, Integer input_man) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ֱ���ͻ�ͼ��ͳ�Ʒ���--ͼ����ʾ������ͼ��
	 * @param begin_date
	 * @param end_date
	 * @param input_man
	 * @return
	 * @throws BusiException
	 */
	List querySellingStat(Integer begin_date, Integer end_date, Integer input_man) throws BusiException;

	/**
	 * �ͻ�������--��ҳ��ʾ
	 * @ejb.interface-method view-type = "local"
	 * @return
	 * @throws BusiException
	 */
	IPageList getPageVolueStat(CustomerStatVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

}