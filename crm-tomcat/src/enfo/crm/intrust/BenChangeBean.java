
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.BenChangeVO;

@Component(value="benChange")
public class BenChangeBean extends CrmBusiExBean implements BenChangeLocal {

	/**
	 * 查询受益权转让——分页显示
	 */
	private static final String listSql =
		"{call SP_QUERY_TBENCHANGES(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 查询受益权转让——单条记录
	 */
	private static final String listByChangeSql =
		"{call SP_QUERY_TBENCHANGES_LOAD (?)}";

    /**
     * 修改受益权转让备注
     */
    private static final String modiSummarySql = "{?=call SP_MODI_TBENCHANGES_SUMMARY(?,?,?)}";
    
	/**
	 * 删除受益权转让
	 */
	private static final String delSql = "{?=call SP_DEL_TBENCHANGES(?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#listAll(enfo.crm.vo.BenChangeVO, int, int)
	 */
	@Override
	public IPageList listAll(BenChangeVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[13];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[4] = Utility.parseInt(vo.getTrans_date_begin(), new Integer(0));
		params[5] = Utility.parseInt(vo.getTrans_date_end(), new Integer(0));
		params[6] = vo.getFrom_cust_name();
		params[7] = vo.getTo_cust_name();
		params[8] = vo.getTrans_type();
		params[9] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[10] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[11] = vo.getContract_sub_bh();
		params[12] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		return super.listProcPage(listSql, params, pageIndex, pageSize);
	}
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#listAll(enfo.crm.vo.BenChangeVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList listAll(BenChangeVO vo, String[] totalColumn,int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[13];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[4] = Utility.parseInt(vo.getTrans_date_begin(), new Integer(0));
		params[5] = Utility.parseInt(vo.getTrans_date_end(), new Integer(0));
		params[6] = vo.getFrom_cust_name();
		params[7] = vo.getTo_cust_name();
		params[8] = vo.getTrans_type();
		params[9] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[10] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[11] = vo.getContract_sub_bh();
		params[12] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		return super.listProcPage(listSql, params,totalColumn, pageIndex, pageSize);
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#list(enfo.crm.vo.BenChangeVO)
	 */
    @Override
	public List list(BenChangeVO vo)
        throws BusiException {
        Object[] params = new Object[13];
        params[0] = vo.getBook_code();
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getContract_bh();
        params[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        params[4] = Utility.parseInt(vo.getTrans_date_begin(), new Integer(0));
        params[5] = Utility.parseInt(vo.getTrans_date_end(), new Integer(0));
        params[6] = vo.getFrom_cust_name();
        params[7] = vo.getTo_cust_name();
        params[8] = vo.getTrans_type();
        params[9] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
        params[10] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[11] = vo.getContract_sub_bh();
        params[12] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        return super.listBySql(listSql, params);
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#listByChange(enfo.crm.vo.BenChangeVO)
	 */
	@Override
	public List listByChange(BenChangeVO vo) throws BusiException {
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		return super.listBySql(listByChangeSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#append(enfo.crm.vo.BenChangeVO)
	 */
	@Override
	public Integer append(BenChangeVO vo) throws BusiException {
		Object[] params = new Object[30];
		params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[1] = vo.getContract_bh();
		params[2] = Utility.parseInt(vo.getFrom_cust_id(), new Integer(0));
		params[3] = vo.getJk_type();
		params[4] = vo.getTrans_flag();
		params[5] = vo.getTo_amount();
		params[6] = vo.getSx_fee();
		params[7] = Utility.parseInt(vo.getTo_cust_id(), new Integer(0));
		params[8] = vo.getSummary();
		params[9] = vo.getBank_id();
		params[10] = vo.getBank_acct();
		params[11] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[12] = Utility.parseInt(vo.getCheck_man(), new Integer(0));
		params[13] = Utility.parseInt(vo.getChange_date(), new Integer(0));
		params[14] = vo.getBank_sub_name();
		params[15] = Utility.parseInt(vo.getForm_list_id(), new Integer(0));
		params[16] = Utility.parseInt(vo.getFx_change_flag(), new Integer(0));
		params[17] = Utility.parseInt(vo.getSy_change_flag(), new Integer(0));
		params[18] = vo.getTrans_type();
		params[19] = Utility.parseInt(vo.getChange_qs_date(), new Integer(0));
		params[20] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[21] = vo.getZqr_name();
		params[22] = Utility.parseInt(vo.getChange_end_date(), new Integer(0));
		params[23] =
			Utility.parseBigDecimal(vo.getSx_fee1(), new BigDecimal(0));
		params[24] =
			Utility.parseBigDecimal(vo.getSx_fee2(), new BigDecimal(0));
		params[25] =
			Utility.parseBigDecimal(vo.getSx_fee3(), new BigDecimal(0));
		params[26] = Utility.parseInt(vo.getRp_flag(), new Integer(0));
		params[27] =
			Utility.parseBigDecimal(vo.getRp_money(), new BigDecimal(0));
		params[28] = Utility.parseInt(vo.getProjectid(), new Integer(0));
		params[29] = Utility.trimNull(vo.getBank_acct_name());
//		params[30] = Utility.parseInt(vo.getSrf_money_type(), new Integer(0));
//		params[31] = Utility.trimNull(vo.getMoney_origin());
//		params[32] = Utility.trimNull(vo.getSub_money_origin());
		
		try {
			Integer problem_id = (Integer) super.cudProc("{?=call SP_ADD_TBENCHANGES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"
													, params, 31, java.sql.Types.INTEGER);
			return problem_id;
			
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#modi(enfo.crm.vo.BenChangeVO)
	 */
	@Override
	public void modi(BenChangeVO vo) throws BusiException {
		Object[] params = new Object[24];
		params[0] = vo.getSerial_no();
		params[1] = vo.getTrans_flag();
		params[2] = vo.getTo_amount();
		params[3] = vo.getSx_fee();
		params[4] = vo.getTo_cust_id();
		params[5] = vo.getSummary();
		params[6] = vo.getBank_id();
		params[7] = vo.getBank_acct();
		params[8] = vo.getInput_man();
		params[9] = vo.getCheck_man();
		params[10] = vo.getChange_date();
		params[11] = vo.getBank_sub_name();
		params[12] = vo.getFx_change_flag();
		params[13] = vo.getSy_change_flag();
		params[14] = vo.getTrans_type();
		params[15] = Utility.parseInt(vo.getChange_qs_date(), new Integer(0));
		params[16] = vo.getZqr_name();
		params[17] = Utility.parseInt(vo.getChange_end_date(), new Integer(0));
		params[18] =
			Utility.parseBigDecimal(vo.getSx_fee1(), new BigDecimal(0));
		params[19] =
			Utility.parseBigDecimal(vo.getSx_fee2(), new BigDecimal(0));
		params[20] =
			Utility.parseBigDecimal(vo.getSx_fee3(), new BigDecimal(0));
		params[21] =
			Utility.parseInt(vo.getRp_flag(), new Integer(1)); //
		params[22] =
			Utility.parseBigDecimal(vo.getRp_money(), new BigDecimal(0));
		params[23] = Utility.trimNull(vo.getBank_acct_name());
//		params[24] = Utility.parseInt(vo.getSrf_money_type(), new Integer(0)); 
//		params[25] = Utility.trimNull(vo.getMoney_origin());
//		params[26] = Utility.trimNull(vo.getSub_money_origin());
		super.cudProc("{?=call SP_MODI_TBENCHANGES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);
	}

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#modiSummary(enfo.crm.vo.BenChangeVO)
	 */
    @Override
	public void modiSummary(BenChangeVO vo) throws BusiException {
        Object[] params = new Object[3];
        params[0] = vo.getSerial_no();
        params[1] = vo.getSummary();
        params[2] = vo.getInput_man();
        super.cudProc(modiSummarySql, params);
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#delete(enfo.crm.vo.BenChangeVO)
	 */
	@Override
	public void delete(BenChangeVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();
		try {
			super.cudProc(delSql, params);
		} catch (Exception e) {
			throw new BusiException("受益权转让信息删除失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#checkPZ(enfo.crm.vo.BenChangeVO)
	 */        
	@Override
	public void checkPZ(BenChangeVO vo) throws BusiException{
		String sqlStr = "{?=call SP_CHECK_TBENCHANGES_PZ(?,?,?)}";
		Object[] params = new Object[3];
		params[0] = vo.getSerial_no();
		params[1] = vo.getChange_date();
		params[2] = vo.getInput_man();
		
		try {
			super.cudProc(sqlStr, params);
		} catch (BusiException e) {
			throw new BusiException("受益权转让审核失败!" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.BenChangeLocal#check(enfo.crm.vo.BenChangeVO)
	 */
	@Override
	public void check(BenChangeVO vo) throws BusiException{
		Object[] params = new Object[4];
		params[0] = vo.getSerial_no();
		params[1] = vo.getCheck_flag();
		params[2] = vo.getCheck_summary();
		params[3] = vo.getInput_man();
		try {
			super.cudProc("{?=call SP_CHECK_TBENCHANGES(?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("受益权转让审核失败!" + e.getMessage());
		}		
	}
}