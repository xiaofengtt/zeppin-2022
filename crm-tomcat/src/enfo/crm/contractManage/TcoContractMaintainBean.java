 
package enfo.crm.contractManage;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractMaintainVO;

@Component(value="tcoContractMaintain")
public class TcoContractMaintainBean extends CrmBusiExBean implements TcoContractMaintainLocal {
	
	private static String addSql = "{?=call SP_ADD_TCOCONTRACTMAINTAIN(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String querySql = "{call SP_QUERY_TCOCONTRACTMAINTAIN(?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String modiSql = "{?= call SP_MODI_TCOCONTRACTMAINTAIN(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String deleteSql = "{?= call SP_DEL_TCOCONTRACTMAINTAIN(?,?)}";
	
	private static String checkSql="{?=call SP_CHECK_TCOCONTRACTMAINTAIN(?,?)}";
	private static String queryUnReceive="{call SP_QUERY_TCOCONTRACTMAINTAIN_UNRECIEVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static String setInvoiceSql="{? = call SP_SET_TCOCONTRACTMAINTAIN_INVOICE(?,?,?,?,?,?,?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMaintainLocal#append(enfo.crm.vo.TcoContractMaintainVO)
	 */
	@Override
	public Integer append(TcoContractMaintainVO vo) throws BusiException{
		Object[] params = new Object[13];
		
		params[0] = vo.getCoContractMaintain_sub_bh();
		params[1] = vo.getCust_id();
		params[2] = vo.getMain_period();
		params[3] = vo.getMain_period_unit();
		params[4] = vo.getMain_pro_name();
		params[5] = vo.getCollect_time();
		params[6] = vo.getStart_date();
		params[7] = vo.getEnd_date();
		params[8] = vo.getHt_money();
		params[9] = vo.getWh_money();
		params[10] = vo.getMain_summary();
		params[11] = vo.getInput_man();
		params[12] = vo.getSub_bh();
		return (Integer)super.cudProc(addSql, params, 14, Types.INTEGER);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMaintainLocal#queryByPageList(enfo.crm.vo.TcoContractMaintainVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryByPageList(TcoContractMaintainVO vo, String[] totalColumn, int pageIndex, int pageSize)
		throws BusiException{
		Object[] params = new Object[11];
		params[0] = vo.getMaintain_id();
		params[1] = vo.getCoContractMaintain_sub_bh();
		params[2] = vo.getMain_pro_name();
		params[3] = vo.getCollect_time_begin();
		params[4] = vo.getCollect_time_end();
		params[5] = vo.getStart_date_begin();
		params[6] = vo.getStart_date_end();
		params[7] = vo.getEnd_date_begin();
		params[8] = vo.getEnd_date_end();
		params[9] = vo.getInput_man();
		params[10] = vo.getCust_name();
		return super.listProcPage(querySql, params, totalColumn, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMaintainLocal#queryByList(enfo.crm.vo.TcoContractMaintainVO)
	 */
	@Override
	public List queryByList(TcoContractMaintainVO vo)throws BusiException{
		Object[] params = new Object[11];
		params[0] = vo.getMaintain_id();
		params[1] = vo.getCoContractMaintain_sub_bh();
		params[2] = vo.getMain_pro_name();
		params[3] = vo.getCollect_time_begin();
		params[4] = vo.getCollect_time_end();
		params[5] = vo.getStart_date_begin();
		params[6] = vo.getStart_date_end();
		params[7] = vo.getEnd_date_begin();
		params[8] = vo.getEnd_date_end();
		params[9] = vo.getInput_man();	
		params[10] = vo.getCust_name();
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMaintainLocal#modi(enfo.crm.vo.TcoContractMaintainVO)
	 */      
	
	@Override
	public void modi(TcoContractMaintainVO vo)throws BusiException{
		Object[] params = new Object[14];
		params[0] = vo.getMaintain_id();
		params[1] = vo.getCoContractMaintain_sub_bh();
		params[2] = vo.getCust_id();
		params[3] = vo.getMain_period();
		params[4] = vo.getMain_period_unit();
		params[5] = vo.getMain_pro_name();
		params[6] = vo.getCollect_time();
		params[7] = vo.getStart_date();
		params[8] = vo.getEnd_date();
		params[9] = vo.getHt_money();
		params[10] = vo.getWh_money();
		params[11] = vo.getMain_summary();
		params[12] = vo.getInput_man();
		params[13] = vo.getSub_bh();
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMaintainLocal#delete(enfo.crm.vo.TcoContractMaintainVO)
	 */
	@Override
	public void delete(TcoContractMaintainVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getMaintain_id();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMaintainLocal#check(enfo.crm.vo.TcoContractMaintainVO)
	 */      
	@Override
	public void check(TcoContractMaintainVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getMaintain_id();
		params[1] = vo.getInput_man();
		super.cudProc(checkSql,params);
	}
     /* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMaintainLocal#queryUnReceive(enfo.crm.vo.TcoContractMaintainVO, java.lang.String[], int, int)
	 */
 	@Override
	public IPageList queryUnReceive(TcoContractMaintainVO vo, String[] totalNum, int pageIndex, int pageSize)throws BusiException{
 		Object[] params = new Object[14];
 		params[0] = vo.getMaintain_id();
 		params[1] = vo.getCust_name();
		params[2] = vo.getCoContractMaintain_sub_bh();
		params[3] = vo.getMain_pro_name();
		params[4] = vo.getCollect_time_begin();
		params[5] = vo.getCollect_time_end();
		params[6] = vo.getStart_date_begin();
		params[7] = vo.getStart_date_end();
		params[8] = vo.getEnd_date_begin();
		params[9] = vo.getEnd_date_end();
		params[10] = vo.getInvoice_time_begin();
		params[11] = vo.getInvoice_time_end();
		params[12] = vo.getArriveMoney_flag();
		params[13] = vo.getInput_man();	
 		return super.listProcPage(queryUnReceive,params,totalNum,pageIndex,pageSize);
 	}
 	
 	@Override
	public IPageList queryUnReceive(TcoContractMaintainVO vo, int pageIndex, int pageSize)throws BusiException{
 		return queryUnReceive(vo,new String[]{},pageIndex,pageSize);
 	}
 	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMaintainLocal#setInvoice(enfo.crm.vo.TcoContractMaintainVO)
	 */
	@Override
	public void setInvoice(TcoContractMaintainVO vo)throws BusiException{
		Object[] params = new Object[8];
		params[0] = vo.getMaintain_id();
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
}