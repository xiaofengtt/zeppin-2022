 
package enfo.crm.marketing;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.QuotientAffirmVO;

@Component(value="quotientAffirm")
public class QuotientAffirmBean extends enfo.crm.dao.CrmBusiExBean implements QuotientAffirmLocal {

	String sqladd = "{?= call SP_ADD_TQUOTIENTCHANGE(?,?,?,?)}";
	String sqlmodi = "{?= call SP_MODI_TQUOTIENTCHANGE(?,?,?,?)}";
	String sqldel = "{?= call SP_DEL_TQUOTIENTCHANGE(?,?)}";
	String sqlquery = "{call SP_QUERY_TQUOTIENTCHANGE(?,?,?,?,?,?,?)}";			//查询份额变动表
	String sqlaffirm = "{?= call SP_AFFIRM_TQUOTIENTCHANGE(?,?,?)}";
	String sqlqueryQuotient = "{call SP_QUERY_TQUOTIENT(?,?,?,?,?,?,?,?)}";		//查询份额表

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuotientAffirmLocal#append(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public void append(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSubscribe_id()),
				new Integer(0));
		params[1] = Utility.parseDecimal(Utility.trimNull(vo.getChange_amount()),
				new BigDecimal(0), 2, "1");
		params[2] = Utility.parseInt(Utility.trimNull(vo.getChange_date()),
				new Integer(0));
		params[3] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqladd,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuotientAffirmLocal#delete(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
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
	 * @see enfo.crm.marketing.QuotientAffirmLocal#modi(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public void modi(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()),
				new Integer(0));
		params[1] = Utility.parseDecimal(Utility.trimNull(vo.getChange_amount()),
				new BigDecimal(0), 2, "1");
		params[2] = Utility.parseInt(Utility.trimNull(vo.getChange_date()),
				new Integer(0));
		params[3] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqlmodi,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuotientAffirmLocal#query(enfo.crm.vo.QuotientAffirmVO, java.lang.String[], java.lang.Integer, int, int)
	 */
	@Override
	public IPageList query(QuotientAffirmVO vo, String[] totalColumn,Integer input_operatorCode,int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[7];
		IPageList rsList = null;
		param[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		param[1] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[2] = Utility.trimNull(vo.getNonproduct_name());
		param[3] = Utility.trimNull(vo.getCust_name());
		param[4] = Utility.trimNull(vo.getSubscribe_bh());
		param[5] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		rsList = super.listProcPage(sqlquery,param,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuotientAffirmLocal#load(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public List load(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] param = new Object[7];
		List rsList = null;
		param[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		param[1] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[2] = Utility.trimNull(vo.getNonproduct_name());
		param[3] = Utility.trimNull(vo.getCust_name());
		param[4] = Utility.trimNull(vo.getSubscribe_bh());
		param[5] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		rsList = super.listBySql(sqlquery,param);
		return rsList;
	}
	

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuotientAffirmLocal#affirm(enfo.crm.vo.QuotientAffirmVO)
	 */
	@Override
	public void affirm(QuotientAffirmVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		params[1] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),
				new Integer(0));
 		super.cudProc(sqlaffirm,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuotientAffirmLocal#queryQuotient(enfo.crm.vo.QuotientAffirmVO, java.lang.String[], java.lang.Integer, int, int)
	 */
	@Override
	public IPageList queryQuotient(QuotientAffirmVO vo, String[] totalColumn,Integer input_operatorCode,int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[8];
		IPageList rsList = null;
		param[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		param[1] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[2] = Utility.trimNull(vo.getNonproduct_name());
		param[3] = Utility.trimNull(vo.getCust_name());
		param[4] = Utility.trimNull(vo.getSubscribe_bh());
		param[5] = Utility.trimNull(vo.getStatus());
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		param[7] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		rsList = super.listProcPage(sqlqueryQuotient,param,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.QuotientAffirmLocal#loadQuotient(enfo.crm.vo.QuotientAffirmVO, java.lang.Integer)
	 */
	@Override
	public List loadQuotient(QuotientAffirmVO vo,Integer input_operatorCode) throws BusiException {
		Object[] param = new Object[8];
		List rsList = null;
		param[0] = Utility.parseInt(vo.getSerial_no(),
				new Integer(0));
		param[1] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[2] = Utility.trimNull(vo.getNonproduct_name());
		param[3] = Utility.trimNull(vo.getCust_name());
		param[4] = Utility.trimNull(vo.getSubscribe_bh());
		param[5] = Utility.trimNull(vo.getStatus());
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		param[7] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		rsList = super.listBySql(sqlqueryQuotient,param);
		return rsList;
	}
}
