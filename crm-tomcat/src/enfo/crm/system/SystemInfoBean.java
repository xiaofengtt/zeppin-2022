 
package enfo.crm.system;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.SystemInfoVO;

@Component(value="systemInfo")
public class SystemInfoBean extends enfo.crm.dao.CrmBusiExBean implements SystemInfoLocal {
	private static final String listSql = "{call SP_QUERY_TUSERINFO(?)}";//查询系统参数
	private static final String modiSql = "{?=call SP_MODI_TUSERINFO(?,?,?,?,?,?,?)}";//修改系统参数
	
	private static final String queryBackupSql ="{?=call SP_BACKUP_DATABASE(?,?,?,?)}"; // 备份数据库
	/* (non-Javadoc)
	 * @see enfo.crm.system.SystemInfoLocal#listBySig(java.lang.Integer)
	 */
	@Override
	public List listBySig(Integer user_id) throws BusiException
	{
		List rsList = null;
		Object[] params = new Object[1];		
		params[0] = Utility.trimNull(user_id);				
		rsList = super.listProcAll(listSql,params);				
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.SystemInfoLocal#listBySig1(java.lang.Integer)
	 */
	@Override
	public List listBySig1(Integer  system_id) throws BusiException
	{
		List rsList = null;
		Object[] params = new Object[1];
		params[0] = Utility.trimNull(system_id);;
		rsList = super.listProcAll("{call SP_QUERY_TSYSTEMINFO(0,?)}",params);		
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.SystemInfoLocal#modi(enfo.crm.vo.SystemInfoVO, java.lang.Integer)
	 */
	@Override
	public void modi(SystemInfoVO vo,Integer input_operatorCode) throws BusiException
	{
		Object[] params = new Object[7];	
		
		params[0] = Utility.parseInt(vo.getUser_id(),new Integer(0));		
		params[1] = Utility.trimNull(vo.getUser_name());
		params[2] = Utility.trimNull(vo.getAddress());
		params[3] = Utility.trimNull(vo.getPost());
		params[4] = Utility.trimNull(vo.getTel());
		params[5] = Utility.trimNull(vo.getFax());
		params[6] = Utility.parseInt(input_operatorCode,new Integer(0));
		
		super.cudProc(modiSql,params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.SystemInfoLocal#backupDataBase(int, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public void backupDataBase(int flag,String backup_file,String comment,Integer op_code) throws Exception 
	{
	
		String backupSql = "";
		Object[] param = new Object[4];
		param[0] = new Integer(flag);
		param[1] = backup_file;
		param[2] = comment;
		param[3] = op_code;
		super.cudProc(queryBackupSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.SystemInfoLocal#loadUserByUserId(java.lang.Integer)
	 */
	@Override
	public List loadUserByUserId(Integer user_id) throws BusiException
	{
		List rsList = null;
		Object[] params = new Object[1];
		params[0] = user_id;
		rsList = super.listProcAll("{call SP_QUERY_TUSERINFO(?)}",params);		
		return rsList;
	}
}