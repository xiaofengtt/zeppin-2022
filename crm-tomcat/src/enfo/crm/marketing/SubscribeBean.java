 
package enfo.crm.marketing;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.QuotientAffirmVO;
import enfo.crm.vo.SubscribeVO;

@Component(value="subscribe")
public class SubscribeBean extends enfo.crm.dao.CrmBusiExBean implements SubscribeLocal {

	String sqladd = "{?= call SP_ADD_TSUBSCRIBE(?,?,?,?,?,?,?,?,?,?)}";
	String sqlmodi = "{?= call SP_MODI_TSUBSCRIBE(?,?,?,?,?,?,?,?,?,?,?)}";
	String sqldel = "{?= call SP_DEL_TSUBSCRIBE(?,?)}";
	String sqlquery = "{call SP_QUERY_TSUBSCRIBE(?,?,?,?,?,?,?,?)}";
	String sqlcheck = "{?= call SP_CHECK_TSUBSCRIBE(?,?,?,?)}";
	String sqlsettle = "{?= call SP_SETTLE_TSUBSCRIBE(?,?,?)}";
	String sqlSettleCheck = "{?= call SP_SETTLE_CHECK_TSUBSCRIBE(?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SubscribeLocal#append(enfo.crm.vo.SubscribeVO, java.lang.Integer)
	 */
	@Override
	public void append(SubscribeVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[10];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getNonproduct_id()),
				new Integer(0));
		params[1] = Utility.parseInt(Utility.trimNull(vo.getCust_id()),
				new Integer(0));
		params[2] = Utility.trimNull(vo.getSubscribe_bh());
		params[3] = Utility.parseInt(Utility.trimNull(vo.getSign_date()),
				new Integer(0));
		params[4] = Utility.parseInt(Utility.trimNull(vo.getPay_date()),
				new Integer(0));
		params[5] = Utility.parseDecimal(Utility.trimNull(vo.getSubscribe_money()),
				new BigDecimal(0), 2, "1");
		params[6] = Utility.trimNull(vo.getBank_id());
		params[7] = Utility.trimNull(vo.getBank_sub_name());
		params[8] = Utility.trimNull(vo.getBank_acct());
		params[9] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqladd,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SubscribeLocal#delete(enfo.crm.vo.SubscribeVO, java.lang.Integer)
	 */
	@Override
	public void delete(SubscribeVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSubscribe_id(),
				new Integer(0));
		params[1] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqldel,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SubscribeLocal#modi(enfo.crm.vo.SubscribeVO, java.lang.Integer)
	 */
	@Override
	public void modi(SubscribeVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[11];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSubscribe_id()),
				new Integer(0));
		params[1] = Utility.parseInt(Utility.trimNull(vo.getNonproduct_id()),
				new Integer(0));
		params[2] = Utility.parseInt(Utility.trimNull(vo.getCust_id()),
				new Integer(0));
		params[3] = Utility.trimNull(vo.getSubscribe_bh());
		params[4] = Utility.parseInt(Utility.trimNull(vo.getSign_date()),
				new Integer(0));
		params[5] = Utility.parseInt(Utility.trimNull(vo.getPay_date()),
				new Integer(0));
		params[6] = Utility.parseDecimal(Utility.trimNull(vo.getSubscribe_money()),
				new BigDecimal(0), 2, "1");
		params[7] = Utility.trimNull(vo.getBank_id());
		params[8] = Utility.trimNull(vo.getBank_sub_name());
		params[9] = Utility.trimNull(vo.getBank_acct());
		params[10] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqlmodi,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SubscribeLocal#query(enfo.crm.vo.SubscribeVO, java.lang.String[], java.lang.Integer, int, int)
	 */
	@Override
	public IPageList query(SubscribeVO vo, String[] totalColumn,Integer input_operatorCode,int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[8];
		IPageList rsList = null;
		param[0] = Utility.parseInt(vo.getSubscribe_id(),
				new Integer(0));
		param[1] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[2] = Utility.trimNull(vo.getNonproduct_name());
		param[3] = Utility.trimNull(vo.getCust_name());
		param[4] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		param[5] = Utility.trimNull(vo.getStatus());
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		param[7] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		rsList = super.listProcPage(sqlquery,param,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SubscribeLocal#load(enfo.crm.vo.SubscribeVO, java.lang.Integer)
	 */
	@Override
	public List load(SubscribeVO vo,Integer input_operatorCode) throws BusiException {
		Object[] param = new Object[8];
		List rsList = null;
		param[0] = Utility.parseInt(vo.getSubscribe_id(),
				new Integer(0));
		param[1] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[2] = Utility.trimNull(vo.getNonproduct_name());
		param[3] = Utility.trimNull(vo.getCust_name());
		param[4] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		param[5] = Utility.trimNull(vo.getStatus());
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		param[7] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		rsList = super.listBySql(sqlquery,param);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SubscribeLocal#check(enfo.crm.vo.SubscribeVO)
	 */
	@Override
	public void check(SubscribeVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSubscribe_id(),
				new Integer(0));
		params[1] = Utility.parseInt(vo.getCust_id(),
				new Integer(0));
		params[2] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		params[3] = Utility.parseInt(vo.getInput_man(),
				new Integer(0));
 		super.cudProc(sqlcheck,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SubscribeLocal#settle(enfo.crm.vo.QuotientAffirmVO)
	 */
	@Override
	public void settle(QuotientAffirmVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSubscribe_id(),
				new Integer(0));
		params[1] = Utility.parseInt(vo.getEnd_date(),
				new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),
				new Integer(0));
 		super.cudProc(sqlsettle,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.SubscribeLocal#settlecheck(enfo.crm.vo.SubscribeVO)
	 */
	@Override
	public void settlecheck(SubscribeVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSubscribe_id(),
				new Integer(0));
		params[1] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),
				new Integer(0));
 		super.cudProc(sqlSettleCheck,params);
	}
}
