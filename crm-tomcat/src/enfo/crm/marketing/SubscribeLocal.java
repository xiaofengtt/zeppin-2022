package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.QuotientAffirmVO;
import enfo.crm.vo.SubscribeVO;

public interface SubscribeLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @IN_NONPRODUCT_ID  
	 * @IN_CUST_ID        
	 * @IN_SUBSCRIBE_BH   
	 * @IN_SIGN_DATE      
	 * @IN_SUBSCRIBE_MONEY
	 * @IN_BANK_ID        
	 * @IN_BANK_SUB_NAME  
	 * @IN_BANK_ACCT      
	 * @IN_INPUT_MAN
	 * @throws BusiException
	 */
	void append(SubscribeVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @IN_NONPRODUCT_ID
	 * @IN_INPUT_MAN
	 * @throws BusiException
	 */
	void delete(SubscribeVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @IN_SUBSCRIBE_ID	INT
	 * @IN_NONPRODUCT_ID	INT
	 * @IN_CUST_ID	INT
	 * @IN_SUBSCRIBE_BH	NVARCHAR(60)
	 * @IN_SIGN_DATE	INT
	 * @IN_PAY_DATE
	 * @IN_SUBSCRIBE_MONEY	DEC(16,3)
	 * @IN_BANK_ID	NVARCHAR(10)
	 * @IN_BANK_SUB_NAME	NVARCHAR(60)
	 * @IN_BANK_ACCT	NVARCHAR(60)
	 * @IN_INPUT_MAN	INT
	 * @throws BusiException
	 */
	void modi(SubscribeVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 *@IN_SUBSCRIBE_ID	INT
	 *@IN_NONPRODUCT_ID	INT
	 *@IN_NONPRODUCT_NAME	NVARCHAR(60)
	 *@IN_CUST_NAME	NVARCHAR(100)
	 *@IN_CHECK_FLAG	INT
	 *@IN_INPUT_MAN	INT
	 * @throws BusiException
	 */
	IPageList query(SubscribeVO vo, String[] totalColumn, Integer input_operatorCode, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 *@IN_SUBSCRIBE_ID	INT
	 *@IN_NONPRODUCT_ID	INT
	 *@IN_NONPRODUCT_NAME	NVARCHAR(60)
	 *@IN_CUST_NAME	NVARCHAR(100)
	 *@IN_CHECK_FLAG	INT
	 *@IN_INPUT_MAN	INT
	 * @throws BusiException
	 */
	List load(SubscribeVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 审核
	 * SP_CHECK_TNONPRODUCT	IN_SUBSCRIBE_ID	INT	非信托产品合同ID
	 *                      IN_CUST_ID      INT 客户
	 * 					    IN_CHECK_FLAG	INT
	 * 						IN_INPUT_MAN	INT	操作员
	 * @throws BusiException
	 */
	void check(SubscribeVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 结清
	 * SP_CHECK_TNONPRODUCT	IN_SUBSCRIBE_ID	INT	非信托产品合同ID
	 *                      IN_END_DATE     INT 合同结清日期
	 * 						IN_INPUT_MAN	INT	操作员
	 * @throws BusiException
	 */
	void settle(QuotientAffirmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 合同结清审核
	 * SP_CHECK_TNONPRODUCT	IN_SUBSCRIBE_ID	INT	非信托产品合同ID
	 * 					    IN_CHECK_FLAG	INT
	 * 						IN_INPUT_MAN	INT	操作员
	 * @throws BusiException
	 */
	void settlecheck(SubscribeVO vo) throws BusiException;

}