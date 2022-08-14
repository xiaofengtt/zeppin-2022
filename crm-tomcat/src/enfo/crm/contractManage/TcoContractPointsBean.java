 
package enfo.crm.contractManage;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.vo.TcoContractPointsVO;

@Component(value="tcoContractPoints")
public class TcoContractPointsBean extends enfo.crm.dao.CrmBusiExBean implements TcoContractPointsLocal {
	
	private static String addSql = "{?=call SP_ADD_TCOCONTRACTPOINTS(?,?,?,?,?)}";
	private static String querySql = "{call SP_QUERY_TCOCONTRACTPOINTS(?,?,?)}";

	private static String modiSql = "{?= call SP_MODI_TCOCONTRACTPOINTS(?,?,?,?,?)}";
	private static String deleteSql = "{?= call SP_DEL_TCOCONTRACTPOINTS(?,?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPointsLocal#append(enfo.crm.vo.TcoContractPointsVO)
	 */
	@Override
	public void append(TcoContractPointsVO vo) throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getCoContract_id();
		params[1] = vo.getPoint_summary();
		params[2] = vo.getCoProduct_id();
		params[3] = vo.getSub_ht_money();
		params[4] = vo.getInput_man();	
		
		super.cudProc(addSql,params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPointsLocal#queryByList(enfo.crm.vo.TcoContractPointsVO)
	 */
	@Override
	public List queryByList(TcoContractPointsVO vo)throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getSubcoContract_id();
		params[1] = vo.getCoContract_id();
		params[2] = vo.getInput_man();
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPointsLocal#modi(enfo.crm.vo.TcoContractPointsVO)
	 */
	@Override
	public void modi(TcoContractPointsVO vo)throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getSubcoContract_id();
		params[1] = vo.getPoint_summary();
		params[2] = vo.getCoProduct_id();
		params[3] = vo.getSub_ht_money();
		params[4] = vo.getInput_man();	
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractPointsLocal#delete(enfo.crm.vo.TcoContractPointsVO)
	 */
	@Override
	public void delete(TcoContractPointsVO vo)throws BusiException{     
		Object[] params = new Object[3];
		params[0] = vo.getSubcoContract_id();
		params[1] = vo.getInput_man();
		params[2] = vo.getCoContract_id();
		super.cudProc(deleteSql,params);	
	}
}