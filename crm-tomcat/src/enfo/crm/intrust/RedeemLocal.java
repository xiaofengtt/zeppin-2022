package enfo.crm.intrust;

import java.util.List;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.RedeemVO;

public interface RedeemLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local" 赎回信息----查询全部
	 * @SP_QUERY_TSQREDEEM
	 * @IN_SERIAL_NO INT
	 * @IN_PRODUCT_ID INT
	 * @IN_CONTRACT_BH VARCHAR(16)
	 * @IN_LIST_ID INT 受益人ID
	 * @IN_SQ_DATE INT
	 * @IN_CHECK_FLAG INT 1查询未审核记录 2查询已普通审核记录 3查询已财务审核记录
	 * @IN_FALG INT, --1查询已经做过份额计算的记录0查询全部
	 * @IN_INPUT_MAN INT
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listAll(RedeemVO vo, String[] totalColumn, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 赎回信息----查询单一信息
	 * @SP_QUERY_TSQREDEEM
	 * @IN_SERIAL_NO INT
	 * @IN_PRODUCT_ID INT
	 * @IN_CONTRACT_BH VARCHAR(16)
	 * @IN_LIST_ID INT 受益人ID
	 * @IN_SQ_DATE INT
	 * @IN_CHECK_FLAG INT 1查询未审核记录 2查询已普通审核记录 3查询已财务审核记录
	 * @IN_FALG INT, --1查询已经做过份额计算的记录0查询全部
	 * @IN_INPUT_MAN INT
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	List listBySql(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 赎回信息----新增 SP_ADD_TSQREDEEM_CRM @ IN_BEN_SERIAL_NO
	 *                       INT
	 * @IN_REDEEM_AMOUNT DECIMAL(16,3)
	 * @IN_SQ_DATE INT
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	void append(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 赎回信息----修改 SP_MODI_TSQREDEEM_CRM @ IN_SERIAL_NO
	 *                       INT
	 * @IN_REDEEM_AMOUNT DECIMAL(16,3)
	 * @IN_SQ_DATE INT
	 * @IN_INPUT_MAN INT
	 * @throws BusiException
	 */
	void modi(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 赎回信息----删除 SP_DEL_TSQREDEEM
	 * @IN_SERIAL_NO INT 序号
	 * @IN_INPUT_MAN INT 录入操作员
	 * @throws BusiException
	 */
	void delete(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 赎回信息----复核/审核
	 *                       SP_CHECK_TSQREDEEM_CRM
	 * @IN_SERIAL_NO INT 序号
	 * @IN_CHECK_FLAG INT 2数据复核通过 3财务审核，4已兑现
	 * @IN_INPUT_MAN INT 录入操作员
	 * @throws BusiException
	 */
	void check(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local" 赎回审核恢复 SP_CHECK_TSQREDEEM_BACK
	 * @IN_SERIAL_NO INT, --SERIAL_NO
	 * @IN_INPUT_MAN INT --操作员
	 * @throws BusiException
	 */
	void recheck(RedeemVO vo) throws BusiException;

	/**
	 * @ejb.interface-method view-type="local"
	 * 
	 * 巨额赎回查询
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList listSqredeemLarge(RedeemVO vo, int pageIndex, int pageSize) throws BusiException;

}