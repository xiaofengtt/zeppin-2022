 
package enfo.crm.customer;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.CustGroupMemberVO;

@Component(value="custGroupMember")
public class CustGroupMemberBean extends enfo.crm.dao.CrmBusiExBean implements CustGroupMemberLocal {

	/**
	 * 添加客户群组成员
	 */
	private static final String appendSql = "{?=call SP_ADD_TCUSTGROUPMEMBERS(?,?,?)}";

	/**
	 * 删除客户群组成员
	 */
	private static final String delSql = "{?=call SP_DEL_TCUSTGROUPMEMBERS(?,?,?)}";

	/**
	 * 查询客户群组成员
	 */
	private static final String querySql = "{call SP_QUERY_TCUSTGROUPMEMBERS(?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustGroupMemberLocal#appendCustGroupMember(enfo.crm.vo.CustGroupMemberVO)
	 */
	@Override
	public void appendCustGroupMember(CustGroupMemberVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = vo.getGroup_id();
		params[1] = vo.getCust_id();
		params[2] = vo.getInsertMan();
	
		super.cudProc(appendSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustGroupMemberLocal#delCustGroupMember(enfo.crm.vo.CustGroupMemberVO)
	 */
	@Override
	public void delCustGroupMember(CustGroupMemberVO vo) throws BusiException
	{
		Object[] params = new Object[3];
		params[0] = vo.getGroup_id();
		params[1] = vo.getCust_id();
		params[2] = vo.getInsertMan();
		
		super.cudProc(delSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustGroupMemberLocal#queryAll(enfo.crm.vo.CustGroupMemberVO, int, int)
	 */
	@Override
	public IPageList queryAll(CustGroupMemberVO vo, int pageIndex, int pageSize) throws BusiException
	{
		IPageList rslist = null;
		Object[] params = new Object[4];
		params[0] = vo.getSerial_no();
		params[1] = vo.getGroup_id();
		params[2] = vo.getCust_id();
		params[3] = vo.getInsertMan();
		
		rslist = super.listProcPage(querySql, params, pageIndex, pageSize);
		return rslist;
	}
}