package enfo.crm.marketing;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.NonProductVO;

public interface NonProductLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @IN_NONPRODUCT_NAME
	 * @IN_INVESTMENT_DIRECTION
	 * @IN_VALID_PRIOD_UNIT
	 * @IN_VALID_PRIOD
	 * @IN_START_DATE
	 * @IN_END_DATE
	 * @IN_EXPECT_MONEY
	 * @IN_EXPECT_RATE1
	 * @IN_EXPECT_RATE2
	 * @IN_INVESTMENT_MANAGER
	 * @IN_PARTNER_CUST_ID
	 * @IN_INPUT_MAN
	 * @OUT_NONPRODUCT_ID
	 * @throws BusiException
	 */
	void append(NonProductVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @IN_NONPRODUCT_ID
	 * @IN_INPUT_MAN
	 * @throws BusiException
	 */
	void delete(NonProductVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @IN_NONPRODUCT_ID
	 * @IN_NONPRODUCT_NAME
	 * @IN_INVESTMENT_DIRECTION
	 * @IN_VALID_PRIOD_UNIT
	 * @IN_VALID_PRIOD
	 * @IN_START_DATE
	 * @IN_END_DATE
	 * @IN_EXPECT_MONEY
	 * @IN_EXPECT_RATE1
	 * @IN_EXPECT_RATE2
	 * @IN_INVESTMENT_MANAGER
	 * @IN_PARTNER_CUST_ID
	 * @IN_INPUT_MAN
	 * @throws BusiException
	 */
	void modi(NonProductVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @IN_NONPRODUCT_ID
	 * @IN_NONPRODUCT_NAME
	 * @IN_INVESTMENT_MANAGER
	 * @IN_PARTNER_CUST_ID
	 * @IN_STATUS
	 * @IN_CHECK_FLAG
	 * @IN_INPUT_MAN
	 * @throws BusiException
	 */
	IPageList query(NonProductVO vo, String[] totalColumn, Integer input_operatorCode, int pageIndex, int pageSize)
			throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @IN_NONPRODUCT_ID
	 * @IN_NONPRODUCT_NAME
	 * @IN_INVESTMENT_MANAGER
	 * @IN_PARTNER_CUST_ID
	 * @IN_STATUS
	 * @IN_CHECK_FLAG
	 * @IN_INPUT_MAN
	 * @throws BusiException
	 */
	List load(NonProductVO vo, Integer input_operatorCode) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 
	 * ÉóºË
	 * SP_CHECK_TNONPRODUCT	IN_NONPRODUCT_ID	INT	ÐòºÅ
	 * 					    IN_CHECK_FLAG	INT
	 * 						IN_INPUT_MAN	INT	²Ù×÷Ô±
	 * @throws BusiException
	 */
	void check(NonProductVO vo) throws BusiException;

}