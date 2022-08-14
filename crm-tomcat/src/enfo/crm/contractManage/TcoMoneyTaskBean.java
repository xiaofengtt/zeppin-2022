 
package enfo.crm.contractManage;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoMoneyTaskVO;

@Component(value="tcoMoneyTask")
public class TcoMoneyTaskBean extends CrmBusiExBean implements TcoMoneyTaskLocal {
	
	
	private static String querySql = "{call SP_QUERY_TcoMoneyTask(?)}";
	private static String createSql="{?=call SP_CREATE_MONEYTASKREMIND(?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoMoneyTaskLocal#queryByPageList(enfo.crm.vo.TcoMoneyTaskVO, int, int)
	 */
	@Override
	public IPageList queryByPageList(TcoMoneyTaskVO vo, int pageIndex, int pageSize)
		throws BusiException{
		Object[] params = new Object[1];
		params[0] = vo.getInput_man();
		return super.listProcPage(querySql,params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoMoneyTaskLocal#queryByList(enfo.crm.vo.TcoMoneyTaskVO)
	 */
	@Override
	public List queryByList(TcoMoneyTaskVO vo)throws BusiException{
		Object[] params = new Object[1];
		params[0] = vo.getInput_man();
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoMoneyTaskLocal#createMoneyTask(enfo.crm.vo.TcoMoneyTaskVO)
	 */
	@Override
	public void createMoneyTask(TcoMoneyTaskVO vo)throws BusiException{
		Object[] params = new Object[1];
		params[0] = vo.getInput_man();
		super.cudProc(createSql,params);
	}                    
}