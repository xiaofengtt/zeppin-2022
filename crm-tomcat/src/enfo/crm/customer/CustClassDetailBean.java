 
package enfo.crm.customer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.vo.CustClassDetailVO;

@Component(value="custClassDetail")
public class CustClassDetailBean extends enfo.crm.dao.CrmBusiExBean implements CustClassDetailLocal {
	/**
	 * 添加客户分级定义之明细
	 */
	private static final String appendSql =
		"{? = call SP_ADD_TCustClassDetail(?,?,?,?,?,?)}";

	/**
	 * 修改客户分级定义之明细
	 */
	private static final String modiSql =
		"{? = call SP_MODI_TCustClassDetail(?,?,?,?,?)}";

	/**
	 * 删除客户分级定义之明细
	 */
	private static final String delSql =
		"{? = call SP_DEL_TCustClassDetail(?,?)}";

	/**
	 * 查询客户分级定义之明细
	 */
	private static final String querySql =
		"{call SP_QUERY_TCustClassDetail(?,?,?)}";

	/**
	 * 查询客户分类定义之明细
	 */
	private static final String querySql2 =
		"{call SP_QUERY_TCustClassDetail_2(?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDetailLocal#append(enfo.crm.vo.CustClassDetailVO)
	 */
	@Override
	public void append(CustClassDetailVO vo) throws BusiException {
		Object[] params = new Object[6];
		params[0] = vo.getClass_define_id();
		params[1] = vo.getClass_detail_id();
		params[2] = vo.getClass_detail_name();
		params[3] = vo.getClass_minvalue();
		params[4] = vo.getClass_maxvalue();
		params[5] = vo.getInput_man();

		super.cudProc(appendSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDetailLocal#modify(enfo.crm.vo.CustClassDetailVO)
	 */
	@Override
	public void modify(CustClassDetailVO vo) throws BusiException {
		Object[] params = new Object[5];
		params[0] = vo.getClass_detail_id();
		params[1] = vo.getClass_detail_name();
		params[2] = vo.getClass_minvalue();
		params[3] = vo.getClass_maxvalue();
		params[4] = vo.getInput_man();
		
		super.cudProc(modiSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDetailLocal#delete(enfo.crm.vo.CustClassDetailVO)
	 */
	@Override
	public void delete(CustClassDetailVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = vo.getClass_detail_id();
		params[1] = vo.getInput_man();
		
		super.cudProc(delSql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDetailLocal#query(enfo.crm.vo.CustClassDetailVO)
	 */
	@Override
	public List query(CustClassDetailVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[3];
		params[0] = vo.getClass_define_id();
		params[1] = vo.getClass_detail_id();
		params[2] = vo.getInput_man();

		list = super.listBySql(querySql, params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDetailLocal#query2(enfo.crm.vo.CustClassDetailVO)
	 */
	@Override
	public List query2(CustClassDetailVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[3];
		params[0] = vo.getClass_define_id();
		params[1] = vo.getClass_detail_id();
		params[2] = vo.getInput_man();

		list = super.listBySql(querySql2, params);
		return list;
	}
}