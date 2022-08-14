package enfo.crm.marketing;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PreContractCrmVO;

public interface PreContractCrmLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" ԤԼ��ѯ
	 * @throws BusiException
	 */
	IPageList queryPreContractCrm(PreContractCrmVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local" ԤԼ��ѯ
	 * @throws BusiException
	 */
	IPageList queryPreContractCrm_m(PreContractCrmVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param PreContractCrmVO 
	 * 					��ѯԤԼ�ĵ������
	 * @throws BusiException
	 */
	List queryMoneyData(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 *            ԤԼ��ѯ ��ֲ��CRM ���ӿͻ������Ϳͻ�Ⱥ���������ѯ
	 * @throws BusiException
	 */
	List listPreContractCrm(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ����Ԥ�Ǽ� SP_ADD_TPRECONTRACT
	 * @IN_PRODUCT_ID INT,
	 * @IN_CUST_ID INT,
	 * @IN_PRE_MONEY DECIMAL (16, 3),
	 * @IN_LINK_MAN INT,
	 * @IN_VALID_DAYS INT,
	 * @IN_PRE_TYPE VARCHAR(10),
	 * @IN_SUMMARY VARCHAR(200),
	 * @IN_PRE_NUM INT,
	 * @IN_INPUT_MAN INT,
	 * @IN_PRE_DATE INT,
	 * @IN_EXP_REG_DATE INT = NULL, --Ԥ���Ϲ�����
	 * @IN_CUST_SOURCE NVARCHAR(10) = NULL, --�ͻ���Դ(1110)
	 * @OUT_PRE_CODE VARCHAR(16) OUTPUT,
	 * @OUT_SERIAL_NO INT OUTPUT
	 */

	Object[] append(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ԤԼ
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void save(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ɾ�� ԤԼ
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void delete(PreContractCrmVO vo) throws BusiException;

	/**
	 * ��ѯ���۲�ƷԤԼ���
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getProductPreList(PreContractCrmVO vo) throws BusiException;

	/**
	 * ��ѯ���۲�ƷԤԼ���
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	IPageList getProductPreList(PreContractCrmVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * ��ѯԤԼͳ����,����С��ԤԼ����(300W����)
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getProductPreInfo(PreContractCrmVO vo) throws BusiException;

	/**
	 * ��ѯԤԼͳ����,����С��ԤԼ����(300W����)
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getProductPreInfoByproid(PreContractCrmVO vo) throws BusiException;

	/**
	 * ��ѯ�����Ŷ�ԤԼͳ����Ϣ
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getProductPreTotal(PreContractCrmVO vo) throws BusiException;

	/**
	 * ��ѯԤԼ�ɿ���Ϣ
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getPreMoneyDetialList(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ����ԤԼ�ɿ���Ϣ
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void addPreMoneyDetial(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �޸�ԤԼ�ɿ���Ϣ
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void modiPreMoneyDetial(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �޸�ԤԼ�ĺ�ͬǩԼ�������ɷ������Ժ󽫴˷����ϲ����޸ĺ�ͬ��ȥ��
	 * @param vo
	 * @throws BusiException
	 * @IN_HT_MONEY            DECIMAL(16,3),    --��ͬǩԼ���
	                          @IN_CUST_ID             INT,              --�ͻ�ID
	                          @IN_PRODUCT_ID          INT,              --��ƷID
	                          @IN_SUB_PRODUCT_ID      INT,              --�Ӳ�ƷID
	                          @IN_INPUT_MAN           INT
	 */
	void modiPreContractHT(BigDecimal ht_money, Integer cust_id, Integer product_id, Integer sub_product_id,
			Integer inputman, Integer crmPreNo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" �ɿ���Ϣ�˿��
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void refundInfo(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" ɾ���ɿ���Ϣ
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void deletePreMoneyDetial(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * ԤԼ��Ϣͬ��
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	Integer syncPreContract(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws BusiException
	 */
	void setContractNo(Integer serial_no, String contract_no) throws BusiException;

}