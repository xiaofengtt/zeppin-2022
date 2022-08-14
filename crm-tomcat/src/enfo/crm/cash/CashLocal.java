package enfo.crm.cash;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;

public interface CashLocal {

	/**
	 * ��ѯ10���ڵĲ�Ʒ������
	 * @param vo
	 * @return
	 */
	List ProductFundYield(CashVo vo) throws BusiException;

	/**
	 * ��ҳ��ͻ�
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryCust(CashVo vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ10���ڵĿͻ���Ʒ������         
	 * @param vo
	 * @return
	 */
	List CustFundYield(CashVo vo) throws BusiException;

	/**
	 * ��ѯ�ͻ���Ʒ�ʽ���ϸ--��λ��ֵ��¶
	 * @param vo
	 * @return
	 */
	List queryNetValueDisclosures(CashVo vo) throws BusiException;

	/**
	 * ��ҳ��ѯ��Ʒ��ؼ�¼
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList queryProductRedeemList(CashVo vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯ��ر�����Ϣ
	 * @param vo
	 * @return
	 */
	List queryRedeemReport(CashVo vo) throws BusiException;

	/**
	 * ��ѯ�ֽ�������Ʒ���깺��Ϣ  
	 * @param vo
	 * @return
	 * @IN_CUST_ID        INT,            --�ͻ�ID
	                     @IN_BEN_ACCOUNT    NVARCHAR(8),    --�ͻ������˺�
	                     @IN_PRODUCT_ID     INT,            --��ƷID
	                     @IN_SUB_PRODUCT_ID INT,            --�Ӳ�ƷID
	                     @IN_INPUT_MAN      INT             --����Ա
	 */
	List CustFundYieldsubscribe(CashVo vo) throws BusiException;

	/**
	 * ��ѯ�ֽ�������Ʒ�Ŀͻ��ʽ���ˮ
	 * @param vo
	 * @return
	 * @IN_CUST_ID        INT,            --�ͻ�ID
	                     @IN_BEN_ACCOUNT    NVARCHAR(8),    --�ͻ������˺�
	                     @IN_PRODUCT_ID     INT,            --��ƷID
	                     @IN_SUB_PRODUCT_ID INT,            --�Ӳ�ƷID
	                     @IN_INPUT_MAN      INT   
	 */
	List CustFundflow(CashVo vo) throws BusiException;

	/**
	 * ��ѯ�ͻ������˺�
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List getCustBank(CashVo vo) throws BusiException;

}