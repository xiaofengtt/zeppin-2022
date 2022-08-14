 
package enfo.crm.marketing;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.NonProductVO;

@Component(value="nonProduct")
public class NonProductBean extends enfo.crm.dao.CrmBusiExBean implements NonProductLocal {

	String sqladd = "{?= call SP_ADD_TNONPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	String sqlmodi = "{?= call SP_MODI_TNONPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	String sqldel = "{?= call SP_DEL_TNONPRODUCT(?,?)}";
	String sqlquery = "{call SP_QUERY_TNONPRODUCT(?,?,?,?,?,?,?)}";
	String sqlcheck = "{? = call SP_CHECK_TNONPRODUCT(?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.NonProductLocal#append(enfo.crm.vo.NonProductVO, java.lang.Integer)
	 */
	@Override
	public void append(NonProductVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[14];
		params[0] = Utility.trimNull(vo.getNonproduct_name());
		params[1] = Utility.trimNull(vo.getInvestment_direction());
		params[2] = Utility.parseInt(Utility.trimNull(vo.getValid_priod_unit()),
				new Integer(0));
		params[3] = Utility.parseInt(Utility.trimNull(vo.getValid_priod()),
				new Integer(0));
		params[4] = Utility.parseInt(Utility.trimNull(vo.getStart_date()),
				new Integer(0));
		params[5] = Utility.parseInt(Utility.trimNull(vo.getEnd_date()),
				new Integer(0));
		params[6] = Utility.parseDecimal(Utility.trimNull(vo.getExpect_money()),
				new BigDecimal(0), 2, "1");
		params[7] = Utility.parseDecimal(Utility.trimNull(vo.getExpect_rate1()),
				new BigDecimal(0), 2, "1");
		params[8] = Utility.parseDecimal(Utility.trimNull(vo.getExpect_rate2()),
				new BigDecimal(0), 2, "1");
		params[9] = Utility.trimNull(vo.getInvestment_manager());
		params[10] = Utility.parseInt(vo.getPartner_cust_id(),
				new Integer(0));
		params[11] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		params[12] = Utility.parseInt(vo.getOut_nonproduct_id(),
				new Integer(0));
		params[13] = Utility.trimNull(vo.getDescription());
 		super.cudProc(sqladd,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.NonProductLocal#delete(enfo.crm.vo.NonProductVO, java.lang.Integer)
	 */
	@Override
	public void delete(NonProductVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		params[1] = Utility.parseInt(input_operatorCode,
				new Integer(0));
 		super.cudProc(sqldel,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.NonProductLocal#modi(enfo.crm.vo.NonProductVO, java.lang.Integer)
	 */
	@Override
	public void modi(NonProductVO vo,Integer input_operatorCode) throws BusiException {
		Object[] params = new Object[14];
		params[0] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		params[1] = Utility.trimNull(vo.getNonproduct_name());
		params[2] = Utility.trimNull(vo.getInvestment_direction());
		params[3] = Utility.parseInt(Utility.trimNull(vo.getValid_priod_unit()),
				new Integer(0));
		params[4] = Utility.parseInt(Utility.trimNull(vo.getValid_priod()),
				new Integer(0));
		params[5] = Utility.parseInt(Utility.trimNull(vo.getStart_date()),
				new Integer(0));
		params[6] = Utility.parseInt(Utility.trimNull(vo.getEnd_date()),
				new Integer(0));
		params[7] = Utility.parseDecimal(Utility.trimNull(vo.getExpect_money()),
				new BigDecimal(0), 2, "1");
		params[8] = Utility.parseDecimal(Utility.trimNull(vo.getExpect_rate1()),
				new BigDecimal(0), 2, "1");
		params[9] = Utility.parseDecimal(Utility.trimNull(vo.getExpect_rate2()),
				new BigDecimal(0), 2, "1");
		params[10] = Utility.trimNull(vo.getInvestment_manager());
		params[11] = Utility.parseInt(vo.getPartner_cust_id(),
				new Integer(0));
		params[12] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		params[13] = Utility.trimNull(vo.getDescription());
 		super.cudProc(sqlmodi,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.NonProductLocal#query(enfo.crm.vo.NonProductVO, java.lang.String[], java.lang.Integer, int, int)
	 */
	@Override
	public IPageList query(NonProductVO vo, String[] totalColumn,Integer input_operatorCode,int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[7];
		IPageList rsList = null;
		param[0] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[1] = Utility.trimNull(vo.getNonproduct_name());
		param[2] = Utility.trimNull(vo.getInvestment_manager());
		param[3] = Utility.trimNull(vo.getPartner_cust_name());
		param[4] = Utility.trimNull(vo.getStatus());
		param[5] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		rsList = super.listProcPage(sqlquery,param,totalColumn,pageIndex,pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.marketing.NonProductLocal#load(enfo.crm.vo.NonProductVO, java.lang.Integer)
	 */
	@Override
	public List load(NonProductVO vo,Integer input_operatorCode) throws BusiException {
		Object[] param = new Object[7];
		List rsList = null;
		param[0] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		param[1] = Utility.trimNull(vo.getNonproduct_name());
		param[2] = Utility.trimNull(vo.getInvestment_manager());
		param[3] = Utility.trimNull(vo.getPartner_cust_name());
		param[4] = Utility.trimNull(vo.getStatus());
		param[5] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		param[6] = Utility.parseInt(input_operatorCode,
				new Integer(0));
		rsList = super.listBySql(sqlquery,param);
		return rsList;
	}
	

	/* (non-Javadoc)
	 * @see enfo.crm.marketing.NonProductLocal#check(enfo.crm.vo.NonProductVO)
	 */
	@Override
	public void check(NonProductVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getNonproduct_id(),
				new Integer(0));
		params[1] = Utility.parseInt(vo.getCheck_flag(),
				new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),
				new Integer(0));
 		super.cudProc(sqlcheck,params);
	}
	
}
