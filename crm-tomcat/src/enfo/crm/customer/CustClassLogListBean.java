package enfo.crm.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustClassLogListVO;

@Component(value="custClassLogList")
public class CustClassLogListBean extends enfo.crm.dao.CrmBusiExBean implements CustClassLogListLocal {

	/**
	 * 查询语句
	 */
	private static final String list_ccll = "{call SP_QUERY_TCUSTCLASSLOG(?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassLogListLocal#list_fenye(enfo.crm.vo.CustClassLogListVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList list_fenye(CustClassLogListVO vo, String[] totalColumn,
			int pageIndex, int pageSize) throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_no(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_name(), new String(""));
		try {
			rsList = super.listProcPage(list_ccll, params, totalColumn,
					pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassLogListLocal#list_leibiao(enfo.crm.vo.CustClassLogListVO)
	 */
	@Override
	public List list_leibiao(CustClassLogListVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_no(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_name(), new String(""));
		try {
			list = super.listBySql(list_ccll, params);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
		return list;
	}

}

