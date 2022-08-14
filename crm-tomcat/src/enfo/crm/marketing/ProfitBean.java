 
package enfo.crm.marketing;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.QuotientAffirmVO;

@Component(value="profit")
public class ProfitBean extends enfo.crm.dao.CrmBusiExBean implements ProfitLocal {

	String sqladd = "{?= call SP_ADD_TPROFIT(?,?,?,?)}";
	String sqlmodi = "{?= call SP_MODI_TPROFIT(?,?,?,?)}";
	String sqldel = "{?= call SP_DEL_TPROFIT(?,?)}";
	String sqlquery = "{call SP_QUERY_TPROFIT(?,?,?,?,?,?,?,?,?,?)}";			
	String sqlcal = "{?= call SP_CAL_TPROFIT(?,?,?,?)}";
	String sqlcheck = "{?= call SP_CHECK_TPROFIT(?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ProfitLocal#append(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public void append(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSubscribe_id()),
				new Integer(0));
		params[1] = Utility.parseDecimal(Utility.trimNull(vo.getProfit_money()),
				new BigDecimal(0), 2, "1");
		params[2] = Utility.parseInt(Utility.trimNull(vo.getSy_date()),
				new Integer(0));
		params[3] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqladd,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ProfitLocal#delete(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public void delete(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		params[1] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqldel,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ProfitLocal#modi(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public void modi(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),
				new Integer(0));
		params[1] = Utility.parseDecimal(Utility.trimNull(vo.getProfit_money()),
				new BigDecimal(0), 2, "1");
		params[2] = Utility.parseInt(Utility.trimNull(vo.getSy_date()),
				new Integer(0));
		params[3] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqlmodi,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ProfitLocal#query(enfo.crm.vo.QuotientAffirmVO, java.lang.String[], java.lang.Integer, int, int)
	 */
	@Override
	public IPageList query(QuotientAffirmVO vo, String[] totalColumn,Integer input_operatorCode,int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[10];
		IPageList rsList = null;
		param[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		param[1] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[2] = Utility.trimNull(vo.getNonproduct_name());
		param[3] = Utility.trimNull(vo.getCust_name());
		param[4] = Utility.trimNull(vo.getSubscribe_bh());
		param[5] = Utility.parseInt(vo.getSy_date(),
				new Integer(0));
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		param[7] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		param[8] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		param[9] = Utility.trimNull(vo.getStatus());
		rsList = super.listProcPage(sqlquery,param,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ProfitLocal#load(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public List load(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] param = new Object[10];
		List rsList = null;
		param[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		param[1] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[2] = Utility.trimNull(vo.getNonproduct_name());
		param[3] = Utility.trimNull(vo.getCust_name());
		param[4] = Utility.trimNull(vo.getSubscribe_bh());
		param[5] = Utility.parseInt(vo.getSy_date(),
				new Integer(0));
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		param[7] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		param[8] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		param[9] = Utility.trimNull(vo.getStatus());
		rsList = super.listBySql(sqlquery,param);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ProfitLocal#cal(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public void cal(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getSubscribe_id(),
				new Integer(0));
		params[1] = Utility.parseDecimal(Utility.trimNull(vo.getSy_rate()),
				new BigDecimal(0), 2, "1");
		params[2] = Utility.parseInt(vo.getSy_date(),
				new Integer(0));
		params[3] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqlcal,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.ProfitLocal#check(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public void check(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		params[1] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		params[2] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqlcheck,params);
	}
	
}
