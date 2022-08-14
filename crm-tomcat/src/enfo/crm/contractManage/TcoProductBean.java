 
package enfo.crm.contractManage;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoProductVO;

@Component(value="tcoProduct")
public class TcoProductBean extends CrmBusiExBean implements TcoProductLocal {
	
	private static String querySql = "{call SP_QUERY_TCOPRODUCT(?,?)}";
	
	private static String addSql = "{?=call SP_ADD_TCOPRODUCT(?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String modiSql = "{?= call SP_MODI_TCOPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static String deleteSql = "{?= call SP_DEL_TCOPRODUCT(?,?)}";
	
	private static String checkSql="{?=call SP_CHECK_TCOPRODUCT(?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoProductLocal#listProcAllExt(enfo.crm.vo.TcoProductVO)
	 */
	@Override
	public List listProcAllExt(TcoProductVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[2];
		params[0]=vo.getCoProduct_id();
		params[1]=vo.getCoProduct_name(); 
		try {
			list = super.listBySql(querySql, params);
		} catch (Exception e) {
			throw new BusiException("Ê§°Ü: " + e.getMessage());
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoProductLocal#append(enfo.crm.vo.TcoProductVO)
	 */
	@Override
	public void append(TcoProductVO vo) throws BusiException{
		Object[] params = new Object[11];
		
		params[0] = vo.getCoProduct_name();
		params[1] = vo.getTeam_id();
		params[2] = vo.getCoProduct_manager();
		params[3] = vo.getPublish_date();
		params[4] = vo.getCoProduct_type();
		params[5] = vo.getCoProduct_type_name();
		params[6] = vo.getSelfMade_type();
		params[7] = vo.getSelfMade_type_name();
		params[8] = vo.getCoProduct_summary();
		params[9] = vo.getCoProduct_price();
		params[10] = vo.getInput_man();		
		super.cudProc(addSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoProductLocal#queryByPageList(enfo.crm.vo.TcoProductVO, int, int)
	 */
	@Override
	public IPageList queryByPageList(TcoProductVO vo, int pageIndex, int pageSize)
		throws BusiException{
		Object[] params = new Object[2];
		params[0]=vo.getCoProduct_id();
		params[1]=vo.getCoProduct_name(); 
		return super.listProcPage(querySql,params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoProductLocal#queryByList(enfo.crm.vo.TcoProductVO)
	 */
	@Override
	public List queryByList(TcoProductVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0]=vo.getCoProduct_id();
		params[1]=vo.getCoProduct_name(); 
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoProductLocal#modi(enfo.crm.vo.TcoProductVO)
	 */      
	@Override
	public void modi(TcoProductVO vo)throws BusiException{
		Object[] params = new Object[12];
		params[0] = vo.getCoProduct_id();
		params[1] = vo.getCoProduct_name();
		params[2] = vo.getTeam_id();
		params[3] = vo.getCoProduct_manager();
		params[4] = vo.getPublish_date();
		params[5] = vo.getCoProduct_type();
		params[6] = vo.getCoProduct_type_name();
		params[7] = vo.getSelfMade_type();
		params[8] = vo.getSelfMade_type_name();
		params[9] = vo.getCoProduct_summary();
		params[10] = vo.getCoProduct_price();
		params[11] = vo.getInput_man();		
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoProductLocal#delete(enfo.crm.vo.TcoProductVO)
	 */
	@Override
	public void delete(TcoProductVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getCoProduct_id();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.contractManage.TcoProductLocal#check(enfo.crm.vo.TcoProductVO)
	 */      
	@Override
	public void check(TcoProductVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getCoProduct_id();
		params[1] = vo.getInput_man();	
		super.cudProc(checkSql,params);
	}
}