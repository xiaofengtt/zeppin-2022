 
package enfo.crm.customer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustomerClassVO;

@Component(value="customerClass")
public class CustomerClassBean extends enfo.crm.dao.CrmBusiExBean implements CustomerClassLocal {

	/**
	 * 修改客户信息
	 */
	private static final String modi_cc =
		"{call SP_MODI_CustomerClass(?,?,?,?)}";

	/**
	 * 查询客户基本信息
	 */
	private static final String list_cc = "{call SP_QUERY_CustomerClass2(?,?)}";

	/**
	 * 查询客户基本信息
	 */
	private static final String list_cc1 =
		"{call SP_QUERY_CustomerClass1(?,?)}";

	/**
	 * 查询客户未审核的评级
	 */
	private static final String queryNotCheckSql =
		"{call SP_QUERY_CustomerClass(?,?,?,?,?,?)}";

	/**
	 * 审核客户评级
	 */
	private static final String checkSql =
		"{?=call SP_CHECK_CustomerClass(?,?,?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerClassLocal#modi(enfo.crm.vo.CustomerClassVO)
	 */
	@Override
	public void modi(CustomerClassVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = vo.getCust_id();
		params[1] = vo.getClass_difine_id();
		params[2] = vo.getClass_detail_id();
		params[3] = vo.getInput_man();
		try {
			super.cudProcNoRet(modi_cc, params);
		} catch (Exception e) {
			throw new BusiException("客户基本信息保存失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerClassLocal#list_fenye(enfo.crm.vo.CustomerClassVO, int, int)
	 */
	@Override
	public IPageList list_fenye(
		CustomerClassVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		try {
			rsList = super.listProcPage(list_cc, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerClassLocal#list_leibiao(enfo.crm.vo.CustomerClassVO)
	 */
	@Override
	public List list_leibiao(CustomerClassVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		try {
			list = super.listBySql(list_cc, params);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerClassLocal#queryNotCheckClass(enfo.crm.vo.CustomerClassVO, int, int)
	 */
	@Override
	public IPageList queryNotCheckClass(
		CustomerClassVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		List list = new ArrayList();
		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.trimNull(vo.getClass_define_name());
		params[4] = Utility.trimNull(vo.getClass_detail_name());
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		rsList = super.listProcPage(queryNotCheckSql, params, pageIndex, pageSize);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerClassLocal#checkCustomerClass(enfo.crm.vo.CustomerClassVO)
	 */
	@Override
	public void checkCustomerClass(CustomerClassVO vo) throws BusiException {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getClass_difine_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getClass_detail_id(), new Integer(0));
		params[3] = Utility.parseInt(vo.getCheck_flag(), new Integer(1));//默认未审核
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(888));

		super.cudProc(checkSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerClassLocal#list_leibiao1(enfo.crm.vo.CustomerClassVO)
	 */
	@Override
	public List list_leibiao1(CustomerClassVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		try {
			list = super.listBySql(list_cc1, params);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
		return list;
	}
}