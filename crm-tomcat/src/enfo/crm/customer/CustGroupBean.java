 
package enfo.crm.customer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.vo.CustGroupVO;

@Component(value="custGroup")
public class CustGroupBean extends enfo.crm.dao.CrmBusiExBean implements CustGroupLocal {

	/**
	 * 添加客户群组
	 */
	private static final String appendSql = "{?=call SP_ADD_TCUSTGROUPS(?,?,?)}";

	/**
	 * 修改客户群组
	 */
	private static final String modiSql = "{?=call SP_MODI_TCUSTGROUPS(?,?,?)}";

	/**
	 * 删除客户群组
	 */
	private static final String delSql = "{?=call SP_DEL_TCUSTGROUPS(?,?)}";
	
	/**
	 * 查询客户群组
	 */
	private static final String querySql = "{call SP_QUERY_TCUSTGROUPS(?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustGroupLocal#appendCustGroup(enfo.crm.vo.CustGroupVO)
	 */
	@Override
	public void appendCustGroup(CustGroupVO vo) throws BusiException {
		Object[] params = new Object[3];
		//如果是主根节点添加时默认为1
		params[0] = vo.getGroupId().equals(new Integer(0))?new Integer(1):vo.getGroupId();
		params[1] = vo.getGroupName();
		params[2] = vo.getInputMan();
		
		super.cudProc(appendSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustGroupLocal#modiCustGroup(enfo.crm.vo.CustGroupVO)
	 */
	@Override
	public void modiCustGroup(CustGroupVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = vo.getGroupId();
		params[1] = vo.getGroupName();
		params[2] = vo.getInputMan();
		
		super.cudProc(modiSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustGroupLocal#delCustGroup(enfo.crm.vo.CustGroupVO)
	 */
	@Override
	public void delCustGroup(CustGroupVO vo) throws BusiException
	{
		Object[] params = new Object[2];
		params[0] = vo.getGroupId();
		params[1] = vo.getInputMan();
		super.cudProc(delSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustGroupLocal#queryAll(enfo.crm.vo.CustGroupVO)
	 */
	@Override
	public List queryAll(CustGroupVO vo) throws BusiException
	{
		List list = new ArrayList();
		Object[] params = new Object[2];
		params[0] = vo.getGroupId();
		params[1] = vo.getInputMan();
		
		list = super.listBySql(querySql, params);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustGroupLocal#queryByCustId(java.lang.Integer)
	 */
	@Override
	public List queryByCustId(Integer cust_id) throws BusiException{
		List list = new ArrayList();
		String sqlStr = " select A.GroupName,A.GroupID " +
						" from TCustGroups A " +
						" where A.GroupID not in (select B.GroupID from TCustGroupMembers B where CUST_ID=?) AND A.GroupID!=1";
		Object[] params = new Object[1];
		params[0] = cust_id;
		list = super.listBySql(sqlStr, params);
		return list;
	}
}