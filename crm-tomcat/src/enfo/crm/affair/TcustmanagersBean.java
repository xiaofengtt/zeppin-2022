 
package enfo.crm.affair;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.OperatorVO;
import enfo.crm.vo.TcustmanagersVO;
import enfo.crm.vo.TcustmanagertreeVO;

@Component(value="tcustmanagers")
public class TcustmanagersBean extends enfo.crm.dao.CrmBusiExBean implements TcustmanagersLocal {
	
	private static final String sql_add_tcustmanagers = "{?=call SP_ADD_TCUSTMANAGERS(?,?,?,?,?,?)}";

	private static final String sql_modi_tcustmanagers = "{?=call SP_MODI_TCUSTMANAGERS(?,?,?,?,?,?)}";

	private static final String sql_del_tcustmanagers = "{?=call SP_DEL_TCUSTMANAGERS(?,?)}";

	private static final String sql_get_managerlist_bynode = "{call SP_QUERY_TCUSTMANAGERS(?,?,?,?,?,?)}";
	
	private static final String sql_get_operator_query = "{call SP_QUERY_TOPERATOR(?)}";
	
	private static final String listTreeSql = "{call SP_GET_MANAGERREE_CHILDREN(?)}";
	
	private static final String tree_appendSql = "{?=call SP_ADD_TCUSTMANAGERTREE(?,?,?,?,?)}";
	
	private static final String tree_modiSql = "{?=call SP_MODI_TCUSTMANAGERTREE(?,?,?,?,?)}";
	
	private static final String tree_removeSql = "{?=call SP_DEL_TCUSTMANAGERTREE(?,?)}";
	
	private static final String sql_getManagerList_nextLevel = "{call SP_GET_ManagerList_NextLevel(?)}";
	private static final String sql_getManagerList_sameLevel = "{call SP_GET_ManagerList_SameLevel(?)}";
	private static final String sql_getManagerList = "{call SP_GET_ManagerList(?)}";
	
	private static final String sql_get_managerlist_forTree = "{call SP_QUERY_TCUSTMANAGERS_FORTREE()}";
	
	private static final String sql_getManagerList_auth = "{call SP_QUERY_TCUSTMANAGERS_AUTH(?,?)}";
	
	private static final String appendOperatorSql2 =	"{?=call SP_ADD_ITEM_ROLE(?,?,?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#append(enfo.crm.vo.TcustmanagersVO)
	 */
	@Override
	public void append(TcustmanagersVO vo) throws BusiException{
		Object[] params = new Object[6];
		
		params[0] = Utility.parseInt(vo.getManagerid(),new Integer(0));
		params[1] = Utility.trimNull(vo.getExtension());
		params[2] = Utility.trimNull(vo.getRecordextension());
		params[3] = Utility.trimNull(vo.getDutyname());
		params[4] = Utility.parseInt(vo.getProvideservices(),new Integer(0));
		params[5] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		
		super.cudProc(sql_add_tcustmanagers,params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#modi(enfo.crm.vo.TcustmanagersVO)
	 */
	@Override
	public void modi(TcustmanagersVO vo) throws BusiException{
		Object[] params = new Object[6];
		
		params[0] = Utility.parseInt(vo.getManagerid(),new Integer(0));
		params[1] = Utility.trimNull(vo.getExtension());
		params[2] = Utility.trimNull(vo.getRecordextension());
		params[3] = Utility.trimNull(vo.getDutyname());
		params[4] = Utility.parseInt(vo.getProvideservices(),new Integer(0));
		params[5] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		
		super.cudProc(sql_modi_tcustmanagers,params);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#delete(enfo.crm.vo.TcustmanagersVO)
	 */
	@Override
	public void delete(TcustmanagersVO vo) throws BusiException{
		Object[] params = new Object[2];
		
		params[0] = Utility.parseInt(vo.getManagerid(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		
		super.cudProc(sql_del_tcustmanagers,params);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#pagelist_query(enfo.crm.vo.TcustmanagersVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(TcustmanagersVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[6];
		
		params[0] = Utility.parseInt(vo.getManagerid(),new Integer(0));
		params[1] = Utility.trimNull(vo.getManagername());
		params[2] = Utility.trimNull(vo.getExtension());
		params[3] = Utility.trimNull(vo.getRecordextension());
		params[4] = Utility.trimNull(vo.getDutyname());
		params[5] = Utility.parseInt(vo.getProvideservices(),new Integer(0));

		rsList = super.listProcPage(sql_get_managerlist_bynode,params,totalColumn,pageIndex,pageSize);		
		return rsList;		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#list_query(enfo.crm.vo.TcustmanagersVO)
	 */
	@Override
	public List list_query(TcustmanagersVO vo) throws BusiException{
		List list = null;		
		Object[] params = new Object[6];
		
		params[0] = Utility.parseInt(vo.getManagerid(),new Integer(0));
		params[1] = Utility.trimNull(vo.getManagername());
		params[2] = Utility.trimNull(vo.getExtension());
		params[3] = Utility.trimNull(vo.getRecordextension());
		params[4] = Utility.trimNull(vo.getDutyname());
		params[5] = Utility.parseInt(vo.getProvideservices(),new Integer(0));
		
		list = super.listProcAll(sql_get_managerlist_bynode,params);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#operator_query(enfo.crm.vo.TcustmanagersVO)
	 */
	@Override
	public List operator_query(TcustmanagersVO vo) throws BusiException{
		List list = null;
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(vo.getManagerid(),new Integer(0));
		list = super.listProcAll(sql_get_operator_query,params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#listBySql(java.lang.Integer)
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
	 * @see enfo.crm.affair.TcustmanagersLocal#tree_append(enfo.crm.vo.TcustmanagertreeVO, java.lang.Integer)
	 */
	@Override
	public void tree_append(TcustmanagertreeVO vo,Integer input_operatorCode)throws BusiException{
		Object[] params = new Object[5];
		
		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getManagerid(),new Integer(0));	
		params[2] = Utility.parseInt(input_operatorCode,new Integer(0));
		params[3] = Utility.trimNull(vo.getLevel_no());
		params[4] = Utility.trimNull(vo.getLevel_name());
		super.cudProc(tree_appendSql,params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#tree_modi(enfo.crm.vo.TcustmanagertreeVO, java.lang.Integer)
	 */
	@Override
	public void tree_modi(TcustmanagertreeVO vo,Integer input_man)throws BusiException{
		Object[] params = new Object[5];
		
		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(vo.getManagerid(),new Integer(0));	
		params[2] = Utility.parseInt(input_man,new Integer(0));
		params[3] = Utility.trimNull(vo.getLevel_no());
		params[4] = Utility.trimNull(vo.getLevel_name());
		
		super.cudProc(tree_modiSql,params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#tree_delet(enfo.crm.vo.TcustmanagertreeVO, java.lang.Integer)
	 */
	@Override
	public void tree_delet(TcustmanagertreeVO vo ,Integer input_man)throws BusiException{
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.parseInt(input_man,new Integer(0));
		super.cudProc(tree_removeSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#getManagerList_nextLevel(java.lang.Integer)
	 */
	@Override
	public List getManagerList_nextLevel(Integer input_man) throws BusiException{
		List list = null;		
		Object[] params = new Object[1];
		params[0] =input_man;
		list = super.listProcAll(sql_getManagerList_nextLevel,params);
		return list;
	}	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#getManagerList_sameLevel(java.lang.Integer)
	 */
	@Override
	public List getManagerList_sameLevel(Integer input_man) throws BusiException{
		List list = null;		
		Object[] params = new Object[1];
		params[0] =input_man;
		list = super.listProcAll(sql_getManagerList_sameLevel,params);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#getManagerList_sortByAuth(java.lang.Integer)
	 */
	@Override
	public List getManagerList_sortByAuth(Integer input_man) throws BusiException{
		List list = null;		
		Object[] params = new Object[1];
		params[0] =input_man;
		list = super.listProcAll(sql_getManagerList,params);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#getManagerList_forTree()
	 */
	@Override
	public List getManagerList_forTree() throws BusiException{
		List list = null;		
		list = super.listBySql(sql_get_managerlist_forTree);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#pagelist_query_forTree(enfo.crm.vo.TcustmanagersVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query_forTree(TcustmanagersVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList rsList = null;		

		rsList = super.listSqlPage(sql_get_managerlist_forTree,pageIndex,pageSize);		
		return rsList;		
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#getManagerListAuth(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List getManagerListAuth(Integer input_man,Integer all_flag) throws BusiException{
		List list = null;		
		Object[] params = new Object[2];
		params[0] =input_man;
		params[1] =all_flag;
		list = super.listProcAll(sql_getManagerList_auth,params);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.TcustmanagersLocal#addItemManagerId(enfo.crm.vo.TcustmanagersVO)
	 */
	@Override
	public void addItemManagerId(TcustmanagersVO vo)
		throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(Utility.trimNull(vo.getItem_id()), null);
		params[1] = Utility.parseInt(Utility.trimNull(vo.getManagerid()), null);
		params[2] = Utility.parseInt(Utility.trimNull(vo.getFlag()), null);
		params[3] = Utility.parseInt(Utility.trimNull(vo.getInput_man()), null);
		super.cudProc(appendOperatorSql2, params);
	}
	
	/**
	 * ÐÂÔöÒþ²ØºÅÂë
	 * @param vo
	 * @throws BusiException
	 */
	@Override
	public void addManagerYchm(TcustmanagersVO vo) throws BusiException{
		Object[] params = new Object[5];
		
		Map dict = super.mapBySql("select * from TDICTPARAM where TYPE_VALUE = ?", new Object[]{
			Utility.trimNull(vo.getGroup_id())
		});
		
		String group_name = Utility.trimNull(dict.get("TYPE_CONTENT"));
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getGroup_id()), new Integer(0));
		params[1] = Utility.trimNull(group_name);
		params[2] = Utility.trimNull(vo.getCno_number());
		params[3] = Utility.trimNull(vo.getSummary());
		params[4] = Utility.parseInt(Utility.trimNull(vo.getInput_man()), null);
		
		String updateSql = "insert into TMANAGERZSHM(GROUP_ID,GROUP_NAME,CNO_NUMBER,SUMMARY,INPUT_MAN)values(?,?,?,?,?)";
		
		super.executeSql(updateSql, params);
	}
	
	/**
	 * ²éÑ¯Òþ²ØºÅÂë
	 * @param vo
	 * @param totalColumn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	public IPageList pageManagerYchmList_query(TcustmanagersVO vo, String[] totalColumn, int pageIndex, int pageSize)
			throws BusiException{
		IPageList rsList = null;		
		Object[] params = new Object[4];
		
		params[0] = Utility.parseInt(vo.getGroup_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getCno_number());
		params[2] = Utility.trimNull(vo.getManagername());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));

		rsList = super.listProcPage("{call SP_QUERY_TMANAGERSYCHM(?,?,?,?)}",params,totalColumn,pageIndex,pageSize);		
		return rsList;	
	}

	/**
	 * ÐÞ¸ÄÒþ²ØºÅÂë
	 * @param vo
	 * @throws BusiException
	 */
	@Override
	public void modiManagerYchm(TcustmanagersVO vo) throws BusiException {
		Object[] params = new Object[5];
		
		Map dict = super.mapBySql("select * from TDICTPARAM where TYPE_VALUE = ?", new Object[]{
			Utility.trimNull(vo.getGroup_id())
		});
		
		String group_name = Utility.trimNull(dict.get("TYPE_CONTENT"));
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getGroup_id()), new Integer(0));
		params[1] = Utility.trimNull(group_name);
		params[2] = Utility.trimNull(vo.getCno_number());
		params[3] = Utility.trimNull(vo.getSummary());
		params[4] = Utility.parseInt(Utility.trimNull(vo.getSerial_no()), null);
		
		String updateSql = "update TMANAGERZSHM set GROUP_ID=?,GROUP_NAME=?,CNO_NUMBER=?,SUMMARY=? where SERIAL_NO =?";
		
		super.executeSql(updateSql, params);
	}

	/**
	 * ²éÑ¯Òþ²ØºÅÂë
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@Override
	public Map queryManagerYchm(TcustmanagersVO vo) throws BusiException {
		Map map = super.mapBySql("select * from TMANAGERZSHM where SERIAL_NO = ?", new Object[]{
				Utility.trimNull(vo.getSerial_no())
		});
		return map;
	}

	/**
	 * É¾³ýÒþ²ØºÅÂë
	 * @param vo
	 * @throws BusiException
	 */
	@Override
	public void deleteychm(TcustmanagersVO vo) throws BusiException {
		super.executeSql("delete from TMANAGERZSHMEXT where ZSHM_SERIAL_NO =?", new Object[]{
				Utility.parseInt(Utility.trimNull(vo.getSerial_no()), new Integer(0))
		});
		super.executeSql("delete from TMANAGERZSHM where SERIAL_NO =?", new Object[]{
			Utility.parseInt(Utility.trimNull(vo.getSerial_no()), new Integer(0))
		});
	}
	
	/**
	 * 
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	@Override
	public List queryTmanagerZshmOper(TcustmanagersVO vo) throws BusiException {
		List list = null;
		
		String listSql = "SELECT B.OP_CODE,B.OP_NAME FROM TMANAGERZSHMEXT A,TOPERATOR B WHERE A.MANAGERID = B.OP_CODE AND ZSHM_SERIAL_NO =?";
		
		list = super.listBySql(listSql, new Object[]{
				Utility.parseInt(vo.getSerial_no(), new Integer(0))
		});
		return list;
	}

	/**
	 * È¨ÏÞ¹ØÁªÏî
	 * @param vo
	 * @throws BusiException
	 */
	@Override
	public void addTmanagerZshmext(TcustmanagersVO vo) throws BusiException {
		String updateSql = "insert into TMANAGERZSHMEXT(ZSHM_SERIAL_NO,MANAGERID)VALUES(?,?)";
		super.executeSql(updateSql, new Object[]{
				vo.getSerial_no(),
				vo.getManagerid()
		});
	}
	
	/**
	 * É¾³ý¹ØÁªÐÅÏ¢
	 * @param vo
	 * @throws BusiException
	 */
	@Override
	public void deleteychmext(TcustmanagersVO vo) throws BusiException {
		super.executeSql("delete from TMANAGERZSHMEXT where ZSHM_SERIAL_NO =?", new Object[]{
				Utility.parseInt(Utility.trimNull(vo.getSerial_no()), new Integer(0))
		});
	}	
}