package enfo.crm.customer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.vo.CustomerChangesVO;

@Component(value="customerChanges")
public class CustomerChangesBean extends enfo.crm.dao.CrmBusiExBean implements CustomerChangesLocal {

	/**
	 * 根据客户ID获得客户修改明细信息
	 */
	private static final String listSql = "{call SP_QUERY_TCUSTOMERCHANGES(?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerChangesLocal#queryCustChangesByCustId(enfo.crm.vo.CustomerChangesVO)
	 */
	@Override
	public List queryCustChangesByCustId(CustomerChangesVO vo)
		throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[1];
		params[0] = vo.getCust_id();
		list = super.listBySql(listSql, params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerChangesLocal#importCustomerInfo(java.lang.String[])
	 */
	@Override
	public void importCustomerInfo(String[] sqls) throws Exception{
		//UserTransaction transaction = getTransaction();
		
		try{
		//	transaction.begin();
			
			super.batchUpdate(sqls);
			
		//	transaction.commit();
			
		}catch(Exception e){
		//	try{
		//		transaction.rollback();
		//	}catch (Exception ex) {
		//		throw new BusiException("导入回滚失败，请手工删除数据");
		//	}
			throw new BusiException("导入失败。");
		}
		
	}
}