 
package enfo.crm.contractManage;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.vo.TcoMaintainMoneyDetailVO;

@Component(value="tcoMaintainMoneyDetail")
public class TcoMaintainMoneyDetailBean extends CrmBusiExBean implements TcoMaintainMoneyDetailLocal {
	
	private static String addSql = "{?=call SP_ADD_TCOMAINTAINMONEYMX(?,?,?,?,?,?)}";
	private static String querySql = "{call SP_QUERY_TCOMAINTAINMONEYMX(?,?,?)}";
	private static String modiSql = "{?= call SP_MODI_TCOMAINTAINMONEYMX(?,?,?,?,?)}";
	private static String deleteSql = "{?= call SP_DEL_TCOMAINTAINMONEYMX(?,?)}";
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * 合同管理-增加合同信息
	 * @param vo
	 * @throws BusiException
	 * 
	 */
	public void append(TcoMaintainMoneyDetailVO vo) throws BusiException{
		Object[] params = new Object[6];
		params[0] = vo.getMaintain_id();
		params[1] = vo.getArrive_time();
		params[2] = vo.getArrive_money();
		params[3] = vo.getArrive_summary();
		params[4] = vo.getInput_man();
		params[5] = vo.getIsAllArriveFlag();
		super.cudProc(addSql,params);
	}
	
	
	/**
	 * 查询---无分页（到账明细列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	public List queryByList(TcoMaintainMoneyDetailVO vo)throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getMaintain_id();
		params[1] = vo.getMoneymx_id();
		params[2] = vo.getInput_man();
		return super.listBySql(querySql,params);
	}
	
	/**
	 * 修改--到账明细
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */      
	public void modi(TcoMaintainMoneyDetailVO vo)throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getMoneymx_id();
		params[1] = vo.getArrive_time();
		params[2] = vo.getArrive_money();
		params[3] = vo.getArrive_summary();
		params[4] = vo.getInput_man();		
		super.cudProc(modiSql,params);
	}
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--到账明细
	 * @param vo
	 * @throws BusiException
	 */
	public void delete(TcoMaintainMoneyDetailVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getMoneymx_id();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
}