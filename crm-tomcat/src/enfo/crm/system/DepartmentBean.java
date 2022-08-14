 
package enfo.crm.system;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.DepartmentVO;

@Component(value="department")
public class DepartmentBean extends enfo.crm.dao.CrmBusiExBean implements DepartmentLocal {
	
	private static String listSql = "{call SP_QUERY_TDEPARTMENT(?,?,?)}";//查询部门信息表	
	private static String appendSql = "{?=call SP_ADD_TDEPARTMENT(?,?,?,?,?,?,?)}";//添加部门信息表
	private static String modiSql = "{?=call SP_MODI_TDEPARTMENT(?,?,?,?,?,?)}";//修改部门信息表
	private static String deletSql = "{?=call SP_DEL_TDEPARTMENT(?,?)}";//删除部门信息表
	private static String listTreeSql = "{call SP_QUERY_TDEPARTMENT_TREE(?)}";//树型结构查询部门信息
	
	protected sun.jdbc.rowset.CachedRowSet rowset;
	/* (non-Javadoc)
	 * @see enfo.crm.system.DepartmentLocal#listProcAll(enfo.crm.vo.DepartmentVO)
	 */
	@Override
	public List listProcAll(DepartmentVO dept)throws Exception{
		List lisAll = null;
		Object[]params = new Object[3];
		params[0] = Utility.parseInt(dept.getDepart_id(),new Integer(0));
		params[1] = Utility.parseInt(dept.getParent_id(),new Integer(0));
		params[2] = Utility.parseInt(dept.getBottom_flag(),new Integer(1));
		lisAll = super.listProcAll(listSql,params);
		return lisAll;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DepartmentLocal#listAll(java.lang.Integer)
	 */
	@Override
	public List listAll(Integer depart_id)throws BusiException{
		List list = null;
		Object[]params = new Object[3];
		params[0] = depart_id;
		params[1] = null;
		params[2] = null;
		list = super.listBySql(listSql,params);
		return list;	
	}
	
	//树形结构查询信息
	/* (non-Javadoc)
	 * @see enfo.crm.system.DepartmentLocal#listBySql(java.lang.Integer)
	 */
	@Override
	public List listBySql(Integer depart_id)throws BusiException{
		List list = null;
		Object[]params = new Object[1];
		params[0] = depart_id;
		list = super.listBySql(listTreeSql,params);
		return list;	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DepartmentLocal#append(enfo.crm.vo.DepartmentVO, java.lang.Integer)
	 */
	@Override
	public void append(DepartmentVO dept,Integer input_operatorCode)throws Exception{
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(dept.getDepart_id(),new Integer(0));
		params[1] = Utility.trimNull(dept.getDepart_name());
		params[2] = Utility.trimNull(dept.getDepart_jc());
		params[3] = Utility.parseInt(dept.getParent_id(),new Integer(0));	
		params[4] = Utility.trimNull(dept.getManager_man());
		params[5] = Utility.trimNull(dept.getLeader_man());
		params[6] = Utility.parseInt(input_operatorCode,new Integer(0));
		super.cudProc(appendSql,params);
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DepartmentLocal#modi(enfo.crm.vo.DepartmentVO, java.lang.Integer)
	 */
	@Override
	public void modi(DepartmentVO dept,Integer input_man)throws Exception{
		Object[] param = new Object[6];
		param[0] = Utility.parseInt(dept.getDepart_id(),new Integer(0));
		param[1] = Utility.trimNull(dept.getDepart_name());
		param[2] = Utility.trimNull(dept.getDepart_jc());
		param[3] = Utility.trimNull(dept.getManager_man());
		param[4] = Utility.trimNull(dept.getLeader_man());
		param[5] = Utility.parseInt(input_man,new Integer(0));
		super.cudProc(modiSql,param);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.DepartmentLocal#delet(enfo.crm.vo.DepartmentVO, java.lang.Integer)
	 */
	@Override
	public void delet(DepartmentVO dept ,Integer input_man)throws Exception{
		Object[]param = new Object[2];
		param[0] = Utility.parseInt(dept.getDepart_id(),new Integer(0));
		param[1] = Utility.parseInt(input_man,new Integer(0));
		super.cudProc(deletSql,param);
	}
}