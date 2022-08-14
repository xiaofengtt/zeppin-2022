 
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ApplyreachVO;

@Component(value="applyreach")
public class ApplyreachBean extends enfo.crm.dao.IntrustBusiExBean implements ApplyreachLocal {
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ApplyreachLocal#listAll(enfo.crm.vo.ApplyreachVO, java.lang.String[], int, int)
	 */	
	@Override
	public IPageList listAll(ApplyreachVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList pageList = null;
		Object[] params = new Object[12];
		String sql = "{call SP_QUERY_TCONTRACTSG(?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		params[0] = Utility.parseInt(vo.getBook_code(),null);
		params[1] = Utility.parseInt(vo.getSerial_no(),null);
		params[2] = Utility.parseInt(vo.getProduct_id(),null);
		params[3] = vo.getProduct_code();
		params[4] = vo.getCust_name();
		params[5] = vo.getContract_bh();		
		params[6] = Utility.parseInt(vo.getCheck_flag(),null);
		params[7] = Utility.parseInt(vo.getInput_man(),null);
		params[8] = vo.getProduct_name();
		params[9] = Utility.parseInt(vo.getFlag1(),null);
		params[10] = new Integer(0);
		params[11] = Utility.parseInt(vo.getSub_product_id(),null);
		
		pageList = super.listProcPage(sql,params,totalColumn,pageIndex,pageSize);		
		return pageList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ApplyreachLocal#delete(enfo.crm.vo.ApplyreachVO)
	 */
	@Override
	public void delete(ApplyreachVO vo)throws BusiException{
		String sql = "{?= call SP_DEL_TCONTRACTSG(?,?)}";
		Object[] params = new Object[2];
		
		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		super.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ApplyreachLocal#append(enfo.crm.vo.ApplyreachVO)
	 */
	@Override
	public Object[] append(ApplyreachVO vo) throws BusiException {
		//String sql = "{?= call SP_ADD_TCONTRACTSG_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		String sql = "{?= call SP_ADD_TCONTRACTSG_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	    
		Object[] params = new Object[58];
		params[0] = Utility.parseInt(vo.getBook_code(),null);
		params[1] = Utility.parseInt(vo.getCust_id(),null);
		params[2] =	Utility.parseInt(vo.getProduct_id(),null);
		params[3] =	vo.getContract_bh();
		params[4] =	vo.getContract_sub_bh();
		params[5] =	Utility.parseBigDecimal(vo.getSg_money(),null);
		params[6] =	Utility.parseBigDecimal(vo.getSg_price(),null);
		params[7] =	vo.getJk_type();
		params[8] =	vo.getBank_id();
		params[9] =	vo.getBank_acct();
		params[10] = vo.getBank_sub_name();
		params[11] = vo.getGain_acct();
		params[12] = Utility.parseInt(vo.getSq_date(),null);
		params[13] = Utility.parseInt(vo.getJk_date(),null);
		params[14] = Utility.parseInt(vo.getStart_date(),null);
		params[15] = Utility.parseInt(vo.getValid_period(),null);
		params[16] = Utility.parseInt(vo.getLink_man(),null);
		params[17] = Utility.parseInt(vo.getService_man(),null);
		params[18] = Utility.parseInt(vo.getCity_serial_no(),null);
		params[19] = vo.getTouch_type();
		params[20] = vo.getTocuh_type_name();
		params[21] = Utility.parseInt(vo.getFee_jk_type(),null);
		params[22] = vo.getSummary();
		params[23] = Utility.parseInt(vo.getInput_man(),null);
		params[24] = vo.getBank_acct_type();	
		params[25] = vo.getBonus_flag();
		params[26] = vo.getSub_product_id();
		params[27] = Utility.parseInt(Utility.trimNull(vo.getWith_bank_flag()),new Integer(0));
		params[28] = Utility.trimNull(vo.getHt_bank_id());
		params[29] = Utility.trimNull(vo.getHt_bank_sub_name());
		params[30] = Utility.parseInt(Utility.trimNull(vo.getWith_security_flag()),new Integer(0));
		params[31] = Utility.parseInt(Utility.trimNull(vo.getWith_private_flag()),new Integer(0));
		params[32] = Utility.parseBigDecimal(vo.getBonus_rate(), new BigDecimal(0.00));
		params[33] = Utility.parseInt(vo.getProv_flag(),new Integer(0));
		params[34] = Utility.trimNull(vo.getProv_level());
		params[35] = vo.getChannel_id();
		params[36] = vo.getChannel_type();
		params[37] = vo.getChannel_memo();
		params[38] = Utility.parseInt(vo.getBzj_flag(),new Integer(0));
		params[39] = vo.getChannel_cooperation();
		params[40] = Utility.parseBigDecimal(vo.getMarket_trench_money(), new BigDecimal(0));
		
		params[41] = Utility.parseInt(vo.getRecommend_man(),new Integer(0));
		params[42] = Utility.trimNull(vo.getBank_province());
		params[43] = Utility.trimNull(vo.getBank_city());
		params[44] = Utility.parseBigDecimal(vo.getExpect_ror_lower(), new BigDecimal(0));
		params[45] = Utility.parseBigDecimal(vo.getExpect_ror_upper(), new BigDecimal(0));
		params[46] = Utility.trimNull(vo.getRecommend_man_name());
		params[47] = Utility.parseInt(vo.getContact_id(),null);
		params[48] = vo.getPeriod_unit();
		params[49] = Utility.trimNull(vo.getMoney_origin());
		params[50] = Utility.trimNull(vo.getSub_money_origin());
		params[51] = Utility.trimNull(vo.getHt_cust_name());
		params[52] = Utility.trimNull(vo.getHt_cust_address());
		params[53] = Utility.trimNull(vo.getHt_cust_tel());
		params[54] = vo.getSpot_deal();
		params[55] = vo.getProperty_souce();
		params[56] = vo.getOther_explain();
		params[57] = vo.getContract_type();
	    
		int[] outParamPos = new int[]{40,41};
		int[] outParamType = new int[]{Types.INTEGER,Types.VARCHAR};
		
		return CrmDBManager.cudProc(sql,params,outParamPos,outParamType);		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ApplyreachLocal#append2(enfo.crm.vo.ApplyreachVO)
	 */
	@Override
	public Integer append2(ApplyreachVO vo) throws BusiException {
		//String sql = "{?= call SP_ADD_TCONTRACTSG_SIMP_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		String sql = "{?= call SP_ADD_TCONTRACTSG_SIMP_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	    
		Object[] params = new Object[46];
		params[0] = Utility.parseInt(vo.getCntr_serial_no(), null);
		params[1] = Utility.parseBigDecimal(vo.getSg_money(), null);
		params[2] = Utility.parseBigDecimal(vo.getSg_price(), null);
		params[3] = vo.getJk_type();
		params[4] = vo.getBank_id();
		params[5] = vo.getBank_acct();
		params[6] = vo.getBank_sub_name();
		params[7] = vo.getGain_acct();
		params[8] = Utility.parseInt(vo.getSq_date(), null);
		params[9] = Utility.parseInt(vo.getJk_date(), null);
		params[10] = Utility.parseInt(vo.getStart_date(), null);
		params[11] = Utility.parseInt(vo.getValid_period(), null);
		params[12] = Utility.parseInt(vo.getFee_jk_type(), null);
		params[13] = vo.getSummary();
		params[14] = Utility.parseInt(vo.getInput_man(), null);
		params[15] = vo.getBank_acct_type();
		params[16] = vo.getBonus_flag();
		params[17] = Utility.parseInt(vo.getWith_bank_flag(),new Integer(0));
		params[18] = Utility.trimNull(vo.getHt_bank_id());
		params[19] = Utility.trimNull(vo.getHt_bank_sub_name());
		params[20] = Utility.parseInt(Utility.trimNull(vo.getWith_security_flag()),new Integer(0));
		params[21] = Utility.parseInt(Utility.trimNull(vo.getWith_private_flag()),new Integer(0));
		params[22] = Utility.parseBigDecimal(vo.getBonus_rate(), new BigDecimal(0.00));
		params[23] = vo.getChannel_id();
		params[24] = vo.getChannel_type();
		params[25] = vo.getChannel_memo();
		params[26] = Utility.parseInt(vo.getBzj_flag(),new Integer(0));
		params[27] = vo.getChannel_cooperation();
		params[28] = Utility.parseBigDecimal(vo.getMarket_trench_money(), new BigDecimal(0));
		
		params[29] = Utility.parseInt(vo.getRecommend_man(),new Integer(0));
		params[30] = Utility.trimNull(vo.getBank_province());
		params[31] = Utility.trimNull(vo.getBank_city());
		params[32] = Utility.parseBigDecimal(vo.getExpect_ror_lower(), new BigDecimal(0));
		params[33] = Utility.parseBigDecimal(vo.getExpect_ror_upper(), new BigDecimal(0));
		params[34] = Utility.trimNull(vo.getRecommend_man_name());
		params[35] = Utility.parseInt(vo.getContact_id(),null);
		params[36] = vo.getPeriod_unit();
		params[37] = Utility.trimNull(vo.getMoney_origin());
		params[38] =Utility.trimNull(vo.getSub_money_origin());
		params[39] = Utility.trimNull(vo.getHt_cust_name());
		params[40] = Utility.trimNull(vo.getHt_cust_address());
		params[41] = Utility.trimNull(vo.getHt_cust_tel());
		params[42] = vo.getSpot_deal();
		params[43] = vo.getProperty_souce();
		params[44] = vo.getOther_explain();
		params[45] = vo.getContract_type();
		return (Integer) CrmDBManager.cudProc(sql,params, 28, Types.INTEGER);		
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ApplyreachLocal#modi(enfo.crm.vo.ApplyreachVO)
	 */
	@Override
	public void modi(ApplyreachVO vo) throws BusiException{
		//String sql = "{?= call SP_MODI_TCONTRACTSG_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	    String sql = "{?= call SP_MODI_TCONTRACTSG_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		Object[] params = new Object[56];
		
		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] =	vo.getContract_bh();
		params[2] =	vo.getContract_sub_bh();
		params[3] =	Utility.parseBigDecimal(vo.getSg_money(),null);
		params[4] =	Utility.parseBigDecimal(vo.getSg_price(),null);
		params[5] =	vo.getJk_type();
		params[6] =	vo.getBank_id();
		params[7] =	vo.getBank_acct();
		params[8] = vo.getBank_sub_name();
		params[9] = vo.getGain_acct();
		params[10] = Utility.parseInt(vo.getSq_date(),null);
		params[11] = Utility.parseInt(vo.getJk_date(),null);
		params[12] = Utility.parseInt(vo.getStart_date(),null);
		params[13] = Utility.parseInt(vo.getValid_period(),null);
		params[14] = Utility.parseInt(vo.getLink_man(),null);
		params[15] = Utility.parseInt(vo.getService_man(),null);
		params[16] = Utility.parseInt(vo.getCity_serial_no(),null);
		params[17] = vo.getTouch_type();
		params[18] = vo.getTocuh_type_name();
		params[19] = Utility.parseInt(vo.getFee_jk_type(),null);
		params[20] = vo.getSummary();
		params[21] = Utility.parseInt(vo.getInput_man(),null);
		params[22] = vo.getBonus_flag();	
		params[23] = vo.getBank_acct_type();
		
		params[24] = Utility.parseInt(Utility.trimNull(vo.getWith_bank_flag()),new Integer(0));
		params[25] = Utility.trimNull(vo.getHt_bank_id());
		params[26] = Utility.trimNull(vo.getHt_bank_sub_name());
		params[27] = Utility.parseInt(Utility.trimNull(vo.getWith_security_flag()),new Integer(0));
		params[28] = Utility.parseInt(Utility.trimNull(vo.getWith_private_flag()),new Integer(0));
		params[29] = Utility.parseBigDecimal(vo.getBonus_rate(), new BigDecimal(0));
		params[30] = Utility.parseInt(Utility.trimNull(vo.getProv_flag()),new Integer(1));
		params[31] = vo.getProv_level();
		params[32] = vo.getChannel_id();
		params[33] = vo.getChannel_type();
		params[34] = vo.getChannel_memo();
		params[35] = Utility.parseInt(vo.getBzj_flag(),new Integer(0));
		params[36] = vo.getChannel_cooperation();
		params[37] = Utility.parseBigDecimal(vo.getMarket_trench_money(), new BigDecimal(0));
		
		params[38] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		params[39] = Utility.parseInt(vo.getRecommend_man(),new Integer(0));
		params[40] = Utility.trimNull(vo.getBank_province());
		params[41] = Utility.trimNull(vo.getBank_city());
		params[42] = Utility.parseBigDecimal(vo.getExpect_ror_lower(), new BigDecimal(0));
		params[43] = Utility.parseBigDecimal(vo.getExpect_ror_upper(), new BigDecimal(0));
		params[44] = Utility.trimNull(vo.getRecommend_man_name());
		params[45] = Utility.parseInt(vo.getContact_id(),null);
		params[46] = vo.getPeriod_unit();
		params[47] = vo.getMoney_origin();
		params[48] = vo.getSub_money_origin();
		params[49] = Utility.trimNull(vo.getHt_cust_name());
		params[50] = Utility.trimNull(vo.getHt_cust_address());
		params[51] = Utility.trimNull(vo.getHt_cust_tel());
		params[52] = vo.getSpot_deal();
		params[53] = vo.getProperty_souce();
		params[54] = vo.getOther_explain();
		params[55] = vo.getContract_type();
		CrmDBManager.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ApplyreachLocal#listBySql(enfo.crm.vo.ApplyreachVO)
	 */
	@Override
	public List listBySql(ApplyreachVO vo) throws BusiException{
		List list = null;
		String sql = "{call SP_QUERY_TCONTRACTSG(?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[10];
		
		params[0] = Utility.parseInt(vo.getBook_code(),null);
		params[1] = Utility.parseInt(vo.getSerial_no(),null);
		params[2] = Utility.parseInt(vo.getProduct_id(),null);
		params[3] = vo.getProduct_code();
		params[4] = vo.getCust_name();
		params[5] = vo.getContract_bh();
		params[6] = Utility.parseInt(vo.getCheck_flag(),null);
		params[7] = Utility.parseInt(vo.getInput_man(),null);
		params[8] = vo.getProduct_name();
		params[9] = Utility.parseInt(vo.getFlag1(),null);

		list = super.listProcAll(sql,params);
		return list;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ApplyreachLocal#checkApplyreachContract(enfo.crm.vo.ApplyreachVO)
	 */
	@Override
	public void checkApplyreachContract(ApplyreachVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		super.cudProc("{?= call SP_CHECK_TCONTRACTSG(?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ApplyreachLocal#recheckApplyreachContract(enfo.crm.vo.ApplyreachVO)
	 */
	@Override
	public void recheckApplyreachContract(ApplyreachVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(),null);
		params[1] = Utility.parseInt(vo.getInput_man(),null);
		super.cudProc("{?= call SP_CHECK_TCONTRACTSG_BACK(?,?)}",params);
	}	
}