 
package enfo.crm.contractManage;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.tools.Utility;
import enfo.crm.vo.TcoMaintainDetailVO;

@Component(value="tcoMaintainDetail")
public class TcoMaintainDetailBean extends CrmBusiExBean implements TcoMaintainDetailLocal {
	
	private static String addSql = "{?=call SP_ADD_TCOMAINTAINDETAIL(?,?,?,?,?,?,?,?)}";
	private static String querySql = "{call SP_QUERY_TCOMAINTAINDETAIL(?,?,?,?,?)}";
	private static String modiSql = "{?= call SP_MODI_TCOMAINTAINDETAIL(?,?,?,?,?,?,?,?)}";
	private static String deleteSql = "{?= call SP_DEL_TCOMAINTAINDETAIL(?,?,?)}";
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * 合同管理-增加合同信息
	 * @param vo
	 * @throws BusiException
	 * 
 * 
	 */
	public void append(TcoMaintainDetailVO vo) throws BusiException{
		Object[] params = new Object[8];
		params[0] = vo.getMaintain_id();
		params[1] = vo.getCoContract_id();
		params[2] = vo.getCoProduct_id();
		params[3] = vo.getCoProduct_name();
		params[4] = vo.getXm_ht_money();
		params[5] = vo.getMain_rate();
		params[6] = vo.getMain_money();
		params[7] = vo.getInput_man();		
		super.cudProc(addSql,params);
	}
	
	
	/**
	 * 查询---无分页（到账明细列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	public List queryByList(TcoMaintainDetailVO vo)throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getMaintainDetail_id();
		params[1] = vo.getMaintain_id();
		params[2] = vo.getInput_man();
		params[3] = Utility.parseInt(vo.getCoContract_id(), new Integer(0));
		params[4] = Utility.parseInt(vo.getCoProduct_id(), new Integer(0));
		return super.listBySql(querySql,params);
	}
	
	/**
	 * 修改--到账明细
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */      
	public void modi(TcoMaintainDetailVO vo)throws BusiException{
		Object[] params = new Object[8];
		params[0] = vo.getMaintainDetail_id();
		params[1] = vo.getCoContract_id();
		params[2] = vo.getCoProduct_id();
		params[3] = vo.getCoProduct_name();
		params[4] = vo.getXm_ht_money();
		params[5] = vo.getMain_rate();
		params[6] = vo.getMain_money();
		params[7] = vo.getInput_man();	
		super.cudProc(modiSql,params);
	}
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--到账明细
	 * @param vo
	 * @throws BusiException
	 */
	public void delete(TcoMaintainDetailVO vo)throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getMaintainDetail_id();
		params[1] = vo.getInput_man();
		params[2] = Utility.parseInt(vo.getMaintain_id(), new Integer(0));
		super.cudProc(deleteSql,params);	
	}
}