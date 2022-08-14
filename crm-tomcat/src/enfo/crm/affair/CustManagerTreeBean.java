 
package enfo.crm.affair;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustManagerTreeMembersVO;
import enfo.crm.vo.TcustmanagertreeVO;

@Component(value="custManagerTree")
public class CustManagerTreeBean extends enfo.crm.dao.CrmBusiExBean implements CustManagerTreeLocal {
	private static final String tree_appendSql = "{?=call SP_ADD_TCUSTMANAGERTREE(?,?,?,?,?)}";
	
	private static final String tree_modiSql = "{?=call SP_MODI_TCUSTMANAGERTREE(?,?,?)}";
	
	private static final String tree_removeSql = "{?=call SP_DEL_TCUSTMANAGERTREE(?,?)}";
	

	private static final String leve_tree_appendSql = "{?=call SP_ADD_TCUSTMANAGERLEVEL(?,?,?,?,?)}";
	
	/**
	 * 添加客户经理级别成员
	 */
	private static final String appendSql = "{?=call SP_ADD_TCustManagerTreeMembers(?,?,?,?)}";

	/**
	 * 删除客户经理级别成员
	 */
	private static final String delSql = "{?=call SP_DEL_TCustManagerTreeMembers(?,?,?)}";

	/**
	 * 查询某级别客户经理列表
	 */
	private static final String querySql = "{call SP_QUERY_TCustManagerLevelMembers(?)}";
	
	/**
	 * 修改经理级别
	 */
	private static final String modiSql = "{?=call SP_MODI_TCustManagerTree(?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerTreeLocal#appendCustManagerTreeMember(enfo.crm.vo.CustManagerTreeMembersVO)
	 */
	@Override
	public void appendCustManagerTreeMember(CustManagerTreeMembersVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = vo.getTree_id();
		params[1] = vo.getManagerid();
		params[2] = vo.getManagername();
		params[3] = vo.getInput_man();
		super.cudProc(appendSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerTreeLocal#delCustManagerTreeMember(enfo.crm.vo.CustManagerTreeMembersVO)
	 */
	@Override
	public void delCustManagerTreeMember(CustManagerTreeMembersVO vo) throws BusiException
	{
		Object[] params = new Object[3];
		params[0] = vo.getTree_id();
		params[1] = vo.getManagerid();
		params[2] = vo.getInput_man();
		
		super.cudProc(delSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerTreeLocal#queryAll(enfo.crm.vo.CustManagerTreeMembersVO, int, int)
	 */
	@Override
	public IPageList queryAll(CustManagerTreeMembersVO vo, int pageIndex, int pageSize) throws BusiException
	{
		IPageList rslist = null;
		Object[] params = new Object[1];
		params[0] = vo.getTree_id();
		rslist = super.listProcPage(querySql, params, pageIndex, pageSize);
		return rslist;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerTreeLocal#level_tree_append(enfo.crm.vo.TcustmanagertreeVO)
	 */
	@Override
	public void level_tree_append(TcustmanagertreeVO vo)throws BusiException{
		Object[] params = new Object[5];
		
		params[0] = Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] = Utility.trimNull(vo.getLevel_no());
		params[2] = Utility.trimNull(vo.getLevel_name());
		params[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[4] = Utility.parseInt(vo.getManagerid(),new Integer(0));
		
		super.cudProc(leve_tree_appendSql,params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.affair.CustManagerTreeLocal#modiCustGroup(enfo.crm.vo.TcustmanagertreeVO)
	 */
	@Override
	public void modiCustGroup(TcustmanagertreeVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = vo.getSerial_no();
		params[1] = vo.getLevel_no();
		params[2] = vo.getLevel_name();
		params[3] = vo.getInput_man();
		
		super.cudProc(modiSql, params);
	}
}