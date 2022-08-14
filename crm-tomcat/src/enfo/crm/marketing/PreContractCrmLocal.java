package enfo.crm.marketing;

import java.math.BigDecimal;
import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PreContractCrmVO;

public interface PreContractCrmLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 预约查询
	 * @throws BusiException
	 */
	IPageList queryPreContractCrm(PreContractCrmVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;
	
	/**
	 * @ejb.interface-method view-type = "local" 预约查询
	 * @throws BusiException
	 */
	IPageList queryPreContractCrm_m(PreContractCrmVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param PreContractCrmVO 
	 * 					查询预约的到账情况
	 * @throws BusiException
	 */
	List queryMoneyData(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 *            预约查询 移植到CRM 增加客户评级和客户群组的联动查询
	 * @throws BusiException
	 */
	List listPreContractCrm(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 增加预登记 SP_ADD_TPRECONTRACT
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
	 * @IN_EXP_REG_DATE INT = NULL, --预计认购日期
	 * @IN_CUST_SOURCE NVARCHAR(10) = NULL, --客户来源(1110)
	 * @OUT_PRE_CODE VARCHAR(16) OUTPUT,
	 * @OUT_SERIAL_NO INT OUTPUT
	 */

	Object[] append(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 预约
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void save(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 删除 预约
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void delete(PreContractCrmVO vo) throws BusiException;

	/**
	 * 查询在售产品预约情况
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getProductPreList(PreContractCrmVO vo) throws BusiException;

	/**
	 * 查询在售产品预约情况
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	IPageList getProductPreList(PreContractCrmVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * 查询预约统计数,包括小额预约份数(300W以下)
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getProductPreInfo(PreContractCrmVO vo) throws BusiException;

	/**
	 * 查询预约统计数,包括小额预约份数(300W以下)
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getProductPreInfoByproid(PreContractCrmVO vo) throws BusiException;

	/**
	 * 查询各个团度预约统计信息
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getProductPreTotal(PreContractCrmVO vo) throws BusiException;

	/**
	 * 查询预约缴款信息
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @param PreContractCrmVO
	 * @return
	 * @throws Exception
	 */
	List getPreMoneyDetialList(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 增加预约缴款信息
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void addPreMoneyDetial(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 修改预约缴款信息
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void modiPreMoneyDetial(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 修改预约的合同签约金额。（过渡方法，以后将此方法合并到修改合同中去）
	 * @param vo
	 * @throws BusiException
	 * @IN_HT_MONEY            DECIMAL(16,3),    --合同签约金额
	                          @IN_CUST_ID             INT,              --客户ID
	                          @IN_PRODUCT_ID          INT,              --产品ID
	                          @IN_SUB_PRODUCT_ID      INT,              --子产品ID
	                          @IN_INPUT_MAN           INT
	 */
	void modiPreContractHT(BigDecimal ht_money, Integer cust_id, Integer product_id, Integer sub_product_id,
			Integer inputman, Integer crmPreNo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 缴款信息退款处理
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void refundInfo(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 删除缴款信息
	 * @param PreContractCrmVO
	 * @throws Exception
	 */
	void deletePreMoneyDetial(PreContractCrmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 预约信息同步
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