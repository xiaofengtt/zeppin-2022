 
package enfo.crm.contractManage;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractPayPlanVO;

@Component(value="tcoContractPayPlan")
public class TcoContractPayPlanBean extends enfo.crm.dao.CrmBusiExBean implements TcoContractPayPlanLocal {
	
	private static String addSql = "{?=call SP_ADD_TCOCONTRACTPAYPLAN(?,?,?,?,?,?,?,?)}";
	private static String querySql = "{call SP_QUERY_TCOCONTRACTPAYPLAN(?,?,?,?)}";
	private static String queryUnReceive="{call SP_QUERY_TCOCONTRACTPAYPLAN_UNRECIEVE(?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String modiSql = "{?= call SP_MODI_TCOCONTRACTPAYPLAN(?,?,?,?,?,?,?,?)}";
	private static String setInvoiceSql="{? = call SP_SET_TCOCONTRACTPAYPLAN_INVOICE(?,?,?,?,?,?,?,?)}";
	private static String deleteSql = "{?= call SP_DEL_TCOCONTRACTPAYPLAN(?,?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPayPlanLocal#append(enfo.crm.vo.TcoContractPayPlanVO)
	 */
	@Override
	public void append(TcoContractPayPlanVO vo) throws BusiException{
		Object[] params = new Object[8];
		
		params[0] = vo.getCocontract_id();
		params[1] = vo.getPay_num();
		params[2] = vo.getPay_num_name();
		params[3] = vo.getExp_date();
		params[4] = vo.getPay_rate();
		params[5] = vo.getPay_money();
		params[6] = vo.getPay_summary();
		params[7] = vo.getInput_man();	
		
		super.cudProc(addSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPayPlanLocal#queryByList(enfo.crm.vo.TcoContractPayPlanVO)
	 */
	@Override
	public List queryByList(TcoContractPayPlanVO vo)throws BusiException{
		Object[] params = new Object[4];
		
		params[0] = vo.getPayPlan_id();
		params[1] = vo.getCocontract_id();
		params[2] = vo.getPay_summary();
		params[3] = vo.getInput_man();
		return super.listBySql(querySql,params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPayPlanLocal#queryUnReceive(enfo.crm.vo.TcoContractPayPlanVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryUnReceive(TcoContractPayPlanVO vo, String[] totalNumber, int pageIndex, int pageSize)throws BusiException{
		Object[] params = new Object[11];
		params[0] = vo.getPayPlan_id();
		params[1] = vo.getCoContract_sub_bh();
		params[2] = vo.getCust_name();
		params[3] = vo.getPay_num_name();
		params[4] = vo.getExp_date_begin();
		params[5] = vo.getExp_date_end();
		params[6] = vo.getAcct_name(); 
		params[7] = vo.getInvoice_time_begin();
		params[8] = vo.getInvoice_time_end(); 
		params[9] = vo.getArriveMoney_flag();
		params[10] = vo.getInput_man();
		return super.listProcPage(queryUnReceive,params,totalNumber,pageIndex,pageSize);
	}
	
	@Override
	public IPageList queryUnReceive(TcoContractPayPlanVO vo, int pageIndex, int pageSize) throws BusiException{
		return queryUnReceive(vo, new String[]{},pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPayPlanLocal#modi(enfo.crm.vo.TcoContractPayPlanVO)
	 */
	
	@Override
	public void modi(TcoContractPayPlanVO vo)throws BusiException{
		Object[] params = new Object[8];
		params[0] = vo.getPayPlan_id();
		params[1] = vo.getPay_num();
		params[2] = vo.getPay_num_name();
		params[3] = vo.getExp_date();
		params[4] = vo.getPay_rate();
		params[5] = vo.getPay_money();
		params[6] = vo.getPay_summary();
		params[7] = vo.getInput_man();
		super.cudProc(modiSql,params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPayPlanLocal#setInvoice(enfo.crm.vo.TcoContractPayPlanVO)
	 */
	@Override
	public void setInvoice(TcoContractPayPlanVO vo)throws BusiException{
		Object[] params = new Object[8];
		params[0] = vo.getPayPlan_id();
		params[1] = vo.getBank_id();
		params[2] = vo.getBank_acct();
		params[3] = vo.getAcct_name();
		params[4] = vo.getInvoice_time();
		params[5] = vo.getInvoice_money();
		params[6] = vo.getInput_man();
		params[7] = vo.getInvoice_summary();
		try {
			super.cudProc(setInvoiceSql,params);
		} catch (Exception e) {
			throw new BusiException("¿ªÆ±Ê§°Ü£º"+e.getMessage());
		}
		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPayPlanLocal#delete(enfo.crm.vo.TcoContractPayPlanVO)
	 */
	@Override
	public void delete(TcoContractPayPlanVO vo)throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getPayPlan_id();
		params[1] = vo.getInput_man();
		params[2] =vo.getCocontract_id();
		super.cudProc(deleteSql,params);	
	}
}