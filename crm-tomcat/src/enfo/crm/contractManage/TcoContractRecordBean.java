 
package enfo.crm.contractManage;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.TcoContractRecordVO;

@Component(value="tcoContractRecord")
public class TcoContractRecordBean extends CrmBusiExBean implements TcoContractRecordLocal {
	
	private static String addSql = "{?=call SP_ADD_TCOCONTRACTRECORD(?,?,?,?,?,?,?)}";
	
	private static String querySql = "{call SP_QUERY_TCOCONTRACTRECORD(?,?,?,?,?,?)}";
	
	private static String modiSql = "{?= call SP_MODI_TCOCONTRACTRECORD(?,?,?,?,?,?,?,?)}";
	
	private static String deleteSql = "{?= call SP_DEL_TCOCONTRACTRECORD(?,?)}";
	
	private static String checkSql="{?=call SP_MODI_TCOCONTRACTRECORD_CHECK(?,?,?,?)}";
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * 客户维护管理-增加客户维护信息
	 * @param vo
	 * @throws BusiException
	 * 
 */										
	public void append(TcoContractRecordVO vo) throws BusiException{
		Object[] params = new Object[7];
		
		params[0] = vo.getCust_id();
		params[1] = vo.getMain_pro_name();
		params[2] = vo.getMain_content();
		params[3] = vo.getTeam_id();
		params[4] = vo.getCoProduct_manager();
		params[5] = vo.getRecord_date();
		params[6] = vo.getInput_man();		
		super.cudProc(addSql,params);
	}
	
	/**
	 * 查询--分页（客户维护记录列表）
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	public IPageList queryByPageList(TcoContractRecordVO vo, int pageIndex, int pageSize)
		throws BusiException{
		Object[] params = new Object[6];
		params[0] = vo.getMainRecord_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getMain_pro_name();
		params[3] = vo.getRecord_date_begin();
		params[4] = vo.getRecord_date_end();
		params[5] = vo.getInput_man();
		return super.listProcPage(querySql,params,pageIndex,pageSize);
	}
	
	/**
	 * 查询---无分页（客户维护记录列表）
	 * @param vo
	 * @return
	 * @throws BusiException
	 * @ejb.interface-method view-type="local"
	 */
	public List queryByList(TcoContractRecordVO vo)throws BusiException{
		Object[] params = new Object[6];
		params[0] = vo.getMainRecord_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getMain_pro_name();
		params[3] = vo.getRecord_date_begin();
		params[4] = vo.getRecord_date_end();
		params[5] = vo.getInput_man();
		return super.listBySql(querySql,params);
	}
	
	/**
	 * 修改--客户维护记录
	 * @param vo
	 * @ejb.interface-method view-type="local"
	 */      
	public void modi(TcoContractRecordVO vo)throws BusiException{
		Object[] params = new Object[8];
		params[0] = vo.getMainRecord_id();
		params[1] = vo.getCust_id();
		params[2] = vo.getMain_pro_name();
		params[3] = vo.getMain_content();
		params[4] = vo.getTeam_id();
		params[5] = vo.getCoProduct_manager();
		params[6] = vo.getRecord_date();
		params[7] = vo.getInput_man();	
		super.cudProc(modiSql,params);
	}
	
	/**
	 * @ejb.interface-method view-type="local"
	 * 删除--客户维护记录
	 * @param vo
	 * @throws BusiException
	 */
	public void delete(TcoContractRecordVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getMainRecord_id();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
}