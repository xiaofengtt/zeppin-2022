 
package enfo.crm.intrust;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.RedeemVO;

@Component(value="redeem")
public class RedeemBean extends enfo.crm.dao.IntrustBusiExBean implements RedeemLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.RedeemLocal#listAll(enfo.crm.vo.RedeemVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listAll(RedeemVO vo, String[] totalColumn, int pageIndex,
			int pageSize) throws BusiException {
		IPageList pageList = null;
		Object[] params = new Object[13];
		String sql = "{call SP_QUERY_TSQREDEEM(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseInt(vo.getProduct_id(), null);
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getList_id(), null);
		params[4] = Utility.parseInt(vo.getSq_date(), null);
		params[5] = Utility.parseInt(vo.getCheck_flag(), new Integer(1));
		params[6] = Utility.parseInt(vo.getFlag(), new Integer(0));
		params[7] = Utility.parseInt(vo.getInput_man(), null);
		params[8] = null;
		params[9] = null;
		params[10] = null;
		params[11] = null;
		params[12] = vo.getCust_name();
		pageList = super.listProcPage(sql, params, totalColumn, pageIndex,
				pageSize);
		return pageList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.RedeemLocal#listBySql(enfo.crm.vo.RedeemVO)
	 */
	@Override
	public List listBySql(RedeemVO vo) throws BusiException {
		String sql = "{call SP_QUERY_TSQREDEEM(?,?,?,?,?,?,?,?)}";
		List list = null;
		Object[] params = new Object[8];

		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseInt(vo.getProduct_id(), null);
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getList_id(), null);
		params[4] = Utility.parseInt(vo.getSq_date(), null);
		params[5] = Utility.parseInt(vo.getCheck_flag(), null);
		params[6] = Utility.parseInt(vo.getFlag(), new Integer(0));
		params[7] = Utility.parseInt(vo.getInput_man(), null);

//		list = CrmDBManager.listBySql(sql, params);
		list = super.listBySql(sql, params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.RedeemLocal#append(enfo.crm.vo.RedeemVO)
	 */
	@Override
	public void append(RedeemVO vo) throws BusiException {
		String sql = "{?= call SP_ADD_TSQREDEEM_CRM(?,?,?,?,null,0,null,0,0,?,?,?)}";
		Object[] params = new Object[7];

		params[0] = Utility.parseInt(vo.getBen_serail_no(), null);
		params[1] = Utility.parseBigDecimal(vo.getRedeem_amout(), null);
		params[2] = Utility.parseInt(vo.getSq_date(), null);
		params[3] = Utility.parseInt(vo.getInput_man(), null);
		params[4] = vo.getTransfer_product_id();
		params[5] = vo.getTransfer_sub_product_id();
		params[6] = vo.getTransfer_money();
		CrmDBManager.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.RedeemLocal#modi(enfo.crm.vo.RedeemVO)
	 */
	@Override
	public void modi(RedeemVO vo) throws BusiException {
		String sql = "{?= call SP_MODI_TSQREDEEM_CRM(?,?,?,?,0,null,0,0,0,?,?,?)}";
		Object[] params = new Object[7];

		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseBigDecimal(vo.getRedeem_amout(), null);
		params[2] = Utility.parseInt(vo.getSq_date(), null);
		params[3] = Utility.parseInt(vo.getInput_man(), null);
		params[4] = vo.getTransfer_product_id();
		params[5] = vo.getTransfer_sub_product_id();
		params[6] = vo.getTransfer_money();
		CrmDBManager.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.RedeemLocal#delete(enfo.crm.vo.RedeemVO)
	 */
	@Override
	public void delete(RedeemVO vo) throws BusiException {
		String sql = "{?= call SP_DEL_TSQREDEEM(?,?)}";
		Object[] params = new Object[2];

		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseInt(vo.getInput_man(), null);
		super.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.RedeemLocal#check(enfo.crm.vo.RedeemVO)
	 */
	@Override
	public void check(RedeemVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseInt(vo.getCheck_flag(), null);
		params[2] = Utility.parseInt(vo.getInput_man(), null);
		CrmDBManager.cudProc("{?= call SP_CHECK_TSQREDEEM_CRM(?,?,?)}", params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.RedeemLocal#recheck(enfo.crm.vo.RedeemVO)
	 */
	@Override
	public void recheck(RedeemVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(), null);
		params[1] = Utility.parseInt(vo.getInput_man(), null);
		super.cudProc("{?= call SP_CHECK_TSQREDEEM_BACK(?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.RedeemLocal#listSqredeemLarge(enfo.crm.vo.RedeemVO, int, int)
	 */
	@Override
	public IPageList listSqredeemLarge(RedeemVO vo, int pageIndex, int pageSize)
			throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));

		try {
			rsList = super.listProcPage("{call SP_QUERY_TSQREDEEM_LARGE(?)}", params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("æﬁ∂Ó Íªÿ≤È—Ø≤È—Ø ß∞‹: " + e.getMessage());
		}
		return rsList;
	}
}