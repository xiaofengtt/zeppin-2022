package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.QuotientAffirmVO;

public interface QuotientAffirmLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * IN_SUBSCRIBE_ID	INT	非信托产品合同ID
	 * IN_QUOTIENT_AMOUNT	DEC(16,3)	份额
	 * IN_CHANGE_DATE	INT	日期
	 * IN_INPUT_MAN	INT	操作员
	 * @throws BusiException
	 */
	void append(QuotientAffirmVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @IN_SERIAL_NO
	 * @IN_INPUT_MAN
	 * @throws BusiException
	 */
	void delete(QuotientAffirmVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * IN_SERIAL_NO	INT	序号
	 * IN_CHANGE_AMOUNT	DEC(16,3)	份额
	 * IN_CHANGE_DATE	INT	日期
	 * @IN_INPUT_MAN	INT
	 * @throws BusiException
	 */
	void modi(QuotientAffirmVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 *@IN_SERIAL_NO	INT
	 *@IN_NONPRODUCT_ID	INT
	 *@IN_NONPRODUCT_NAME	NVARCHAR(60)
	 *@IN_CUST_NAME	NVARCHAR(100)
	 *@IN_CHECK_FLAG	INT
	 *@IN_SUBSCRIBE_BH NVARCHAR(60)
	 *@IN_INPUT_MAN	INT
	 * @throws BusiException
	 */
	IPageList query(QuotientAffirmVO vo, String[] totalColumn, Integer input_operatorCode, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 *@IN_SERIAL_NO	INT
	 *@IN_NONPRODUCT_ID	INT
	 *@IN_NONPRODUCT_NAME	NVARCHAR(60)
	 *@IN_CUST_NAME	NVARCHAR(100)
	 *@IN_CHECK_FLAG	INT
	 *@IN_SUBSCRIBE_BH NVARCHAR(60)
	 *@IN_INPUT_MAN	INT
	 * @throws BusiException
	 */
	List load(QuotientAffirmVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * 产品份额确认
	 * SP_CHECK_TQUOTIENTCHANGE	IN_SERIAL_NO INT
	 * 					    IN_CHECK_FLAG	INT
	 * 						IN_INPUT_MAN	INT	操作员
	 * @throws BusiException
	 */
	void affirm(QuotientAffirmVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 *@IN_SERIAL_NO	INT
	 *@IN_NONPRODUCT_ID	INT
	 *@IN_NONPRODUCT_NAME	NVARCHAR(60)
	 *@IN_CUST_NAME	NVARCHAR(100)
	 *@IN_STATUS
	 *@IN_SUBSCRIBE_BH NVARCHAR(60)
	 *@IN_INPUT_MAN	INT
	 * @throws BusiException
	 */
	IPageList queryQuotient(QuotientAffirmVO vo, String[] totalColumn, Integer input_operatorCode, int pageIndex,
			int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 *@IN_SERIAL_NO	INT
	 *@IN_NONPRODUCT_ID	INT
	 *@IN_NONPRODUCT_NAME	NVARCHAR(60)
	 *@IN_CUST_NAME	NVARCHAR(100)
	 *@IN_STATUS
	 *@IN_SUBSCRIBE_BH NVARCHAR(60)
	 *@IN_INPUT_MAN	INT
	 * @throws BusiException
	 */
	List loadQuotient(QuotientAffirmVO vo, Integer input_operatorCode) throws BusiException;

}