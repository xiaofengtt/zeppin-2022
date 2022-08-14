 
package enfo.crm.contractManage;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.vo.TcoContractMoneyDetailVO;

@Component(value="tcoContractMoneyDetail")
public class TcoContractMoneyDetailBean extends CrmBusiExBean implements TcoContractMoneyDetailLocal {
	
	private static String addSql = "{?=call SP_ADD_TCOCONTRACTMONEYMX(?,?,?,?,?,?)}";
	private static String querySql = "{call SP_QUERY_TCOCONTRACTMONEYMX(?,?,?)}";
	private static String modiSql = "{?= call SP_MODI_TCOCONTRACTMONEYMX(?,?,?,?,?)}";
	private static String deleteSql = "{?= call SP_DEL_TCOCONTRACTMONEYMX(?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMoneyDetailLocal#append(enfo.crm.vo.TcoContractMoneyDetailVO)
	 */
	@Override
	public void append(TcoContractMoneyDetailVO vo) throws BusiException{
		Object[] params = new Object[6];
		params[0] = vo.getPayPlan_id();
		params[1] = vo.getArrive_time();
		params[2] = vo.getArrive_money();
		params[3] = vo.getArrive_summary();
		params[4] = vo.getInput_man();	
		params[5] = vo.getIsAllArriveFlag();	
		super.cudProc(addSql,params);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMoneyDetailLocal#queryByList(enfo.crm.vo.TcoContractMoneyDetailVO)
	 */
	@Override
	public List queryByList(TcoContractMoneyDetailVO vo)throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getPayPlan_id();
		params[1] = vo.getMoneymx_id();
		params[2] = vo.getInput_man();
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMoneyDetailLocal#modi(enfo.crm.vo.TcoContractMoneyDetailVO)
	 */      
	@Override
	public void modi(TcoContractMoneyDetailVO vo)throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getMoneymx_id();
		params[1] = vo.getArrive_time();
		params[2] = vo.getArrive_money();
		params[3] = vo.getArrive_summary();
		params[4] = vo.getInput_man();		
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoContractMoneyDetailLocal#delete(enfo.crm.vo.TcoContractMoneyDetailVO)
	 */
	@Override
	public void delete(TcoContractMoneyDetailVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getMoneymx_id();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
}