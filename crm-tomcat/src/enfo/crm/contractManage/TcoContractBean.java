 
package enfo.crm.contractManage;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractVO;

@Component(value="tcoContract")
public class TcoContractBean extends CrmBusiExBean implements TcoContractLocal {
	
	private static String addSql = "{?=call SP_ADD_TCOCONTRACT(?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String querySql = "{call SP_QUERY_TCOCONTRACT(?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String modiSql = "{?= call SP_MODI_TCOCONTRACT(?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String deleteSql = "{?= call SP_DEL_TCOCONTRACT(?,?)}";
	
	private static String checkSql="{?=call SP_MODI_TCOCONTRACT_CHECK(?,?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractLocal#append(enfo.crm.vo.TcoContractVO)
	 */
	@Override
	public Integer append(TcoContractVO vo) throws BusiException{
		Object[] params = new Object[11];
		
		params[0] = vo.getCust_id();
		params[1] = vo.getCocontract_sub_bh();
		params[2] = vo.getSign_date();
		params[3] = vo.getStart_date();
		params[4] = vo.getEnd_date();
		params[5] = vo.getMain_end_date();
		params[6] = vo.getHt_money();
		params[7] = vo.getPayment_type();
		params[8] = vo.getCocontract_type();
		params[9] = vo.getInput_man();
		params[10] = vo.getComment();
		return (Integer)super.cudProc(addSql, params, 12, Types.INTEGER);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractLocal#queryByPageList(enfo.crm.vo.TcoContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryByPageList(TcoContractVO vo, String[] totalColumn, int pageIndex, int pageSize)
		throws BusiException{
		Object[] params = new Object[11];
		params[0] = vo.getCocontract_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getCocontract_sub_bh();
		params[3] = vo.getStart_date_begin();
		params[4] = vo.getStart_date_end();
		params[5] = vo.getEnd_date_begin();
		params[6] = vo.getEnd_date_end();
		params[7] = vo.getMain_end_date_begin();
		params[8] = vo.getMain_end_date_end();
		params[9] = vo.getInput_man();
		params[10]= vo.getCheck_flag();
		return super.listProcPage(querySql,params,totalColumn,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractLocal#queryByList(enfo.crm.vo.TcoContractVO)
	 */
	@Override
	public List queryByList(TcoContractVO vo)throws BusiException{
		Object[] params = new Object[11];
		params[0] = vo.getCocontract_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getCocontract_sub_bh();
		params[3] = vo.getStart_date_begin();
		params[4] = vo.getStart_date_end();
		params[5] = vo.getEnd_date_begin();
		params[6] = vo.getEnd_date_end();
		params[7] = vo.getMain_end_date_begin();
		params[8] = vo.getMain_end_date_end();
		params[9] = vo.getInput_man();
		params[10]= vo.getCheck_flag();
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractLocal#modi(enfo.crm.vo.TcoContractVO)
	 */      
	@Override
	public void modi(TcoContractVO vo)throws BusiException{
		Object[] params = new Object[12];
		params[0] = vo.getCocontract_id();
		params[1] = vo.getCocontract_sub_bh();
		params[2] = vo.getSign_date();
		params[3] = vo.getStart_date();
		params[4] = vo.getEnd_date();
		params[5] = vo.getMain_end_date();
		params[6] = vo.getHt_money();
		params[7] = vo.getPayment_type();
		params[8] = vo.getCocontract_type();
		params[9] = vo.getInput_man();
		params[10] = vo.getComment();
		params[11] = vo.getCust_id();
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractLocal#delete(enfo.crm.vo.TcoContractVO)
	 */
	@Override
	public void delete(TcoContractVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getCocontract_id();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractLocal#check(enfo.crm.vo.TcoContractVO)
	 */      
	@Override
	public void check(TcoContractVO vo)throws BusiException{
		Object[] params = new Object[4];
		params[0] = vo.getCocontract_id();
		params[1] = vo.getCheck_flag();
		params[2] = vo.getCheck_man();
		params[3] = vo.getCheck_date();	
		super.cudProc(checkSql,params);
	}
}