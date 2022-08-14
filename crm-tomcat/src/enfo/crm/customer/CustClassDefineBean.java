 
package enfo.crm.customer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.vo.CustClassDefineVO;
import enfo.crm.vo.CustClassifyVO;

@Component(value="custClassDefine")
public class CustClassDefineBean extends enfo.crm.dao.CrmBusiExBean implements CustClassDefineLocal {

	/**
	 * 添加客户分级定义
	 */
	private static final String appendSql =
		"{? = call SP_ADD_TCustClassDefine(?,?,?,?,?,?,?,?)}";

	/**
	 * 修改客户分级定义
	 */
	private static final String modiSql =
		"{? = call SP_MODI_TCustClassDefine(?,?,?,?,?,?)}";

	/**
	 * 删除客户分级定义
	 */
	private static final String delSql =
		"{? = call SP_DEL_TCustClassDefine(?,?)}";

	/**
	 * 查询客户分级定义
	 */
	private static final String querySql =
		"{call SP_QUERY_TCustClassDefine(?,?,?,?,?,?,?,?)}";
	/**
	 * 查询客户分类
	 */
	private static final String query_custClassDefine_Sql =
		"{call SP_QUERY_TCustClassDefine(?,?,?,?,?,?,?,?)}";

	/**
	 * 根据不同分类，查询不同类别客户所占比例
	 */
	private static final String custClassifySql =
		"{call SP_STAT_CUSTCLASSIFY(?,?,?)}";
	


	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDefineLocal#append(enfo.crm.vo.CustClassDefineVO)
	 */
	@Override
	public void append(CustClassDefineVO vo) throws BusiException {
		Object[] params = new Object[8];
		params[0] = vo.getClass_define_id();
		params[1] = vo.getClass_define_name();
		params[2] = vo.getDefault_value();
		params[3] = vo.getSummary();
		params[4] = vo.getParent_id();
		params[5] = vo.getParent_value();
		params[6] = vo.getInput_man();
		params[7] = vo.getCD_no();
		super.cudProc(appendSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDefineLocal#modify(enfo.crm.vo.CustClassDefineVO)
	 */
	@Override
	public void modify(CustClassDefineVO vo) throws BusiException {
		Object[] params = new Object[6];
		params[0] = vo.getClass_define_id();
		params[1] = vo.getClass_define_name();
		params[2] = vo.getDefault_value();
		params[3] = vo.getParent_value();
		params[4] = vo.getSummary();
		params[5] = vo.getInput_man();
		
		super.cudProc(modiSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDefineLocal#delete(enfo.crm.vo.CustClassDefineVO)
	 */
	@Override
	public void delete(CustClassDefineVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = vo.getClass_define_id();
		params[1] = vo.getInput_man();

		super.cudProc(delSql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDefineLocal#query(enfo.crm.vo.CustClassDefineVO)
	 */
	@Override
	public List query(CustClassDefineVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[8];
		params[0] = vo.getClass_define_id();
		params[1] = vo.getClass_define_name();
		params[2] = vo.getLevel_id();
		params[3] = vo.getParent_id();
		params[4] = vo.getParent_value();
		params[5] = vo.getCanmodi();
		params[6] = vo.getInput_man(); 
		params[7] = vo.getCD_no();
		list = super.listBySql(querySql, params);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDefineLocal#query_custClassDefine(enfo.crm.vo.CustClassDefineVO)
	 */
	@Override
	public List query_custClassDefine(CustClassDefineVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[8];
		params[0] = vo.getClass_define_id();
		params[1] = vo.getClass_define_name();
		params[2] = vo.getLevel_id();
		params[3] = vo.getParent_id();
		params[4] = vo.getParent_value();
		params[5] = vo.getCanmodi();
		params[6] = vo.getInput_man(); 
		params[7] = vo.getCD_no(); 
		
		list = super.listBySql(query_custClassDefine_Sql, params);
		return list;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustClassDefineLocal#query_custClassify(enfo.crm.vo.CustClassifyVO)
	 */
	@Override
	public List query_custClassify(CustClassifyVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[3];
		params[0] = vo.getClassDefineID();
		params[1] = vo.getBook_code();
		params[2] = vo.getInput_man(); 
		
		list = super.listBySql(custClassifySql, params);
		return list;
	}
	
}