 
package enfo.crm.cont;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ContractManagementVO;

@Component(value="contractManagement")
public class ContractManagementBean extends enfo.crm.dao.CrmBusiExBean implements ContractManagementLocal {
	
	private static String querySql = "{call SP_QUERY_CONTMANAGEMENT(?,?,?,?,?)}";
	
	private static String addSql = "{?= call SP_ADD_CONTMANAGEMENT(?,?,?,?,?,?,?)}";
	
	private static String modiSql = "{?= call SP_MODI_CONTMANAGEMENT(?,?,?,?,?,?,?,?)}";
	
	private static String deleteSql = "{?= call SP_DEL_CONTMANAGEMENT(?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.cont.ContractManagementLocal#queryByPageList(enfo.crm.vo.ContractManagementVO, int, int)
	 */
	@Override
	public IPageList queryByPageList(ContractManagementVO vo, int pageIndex, int pageSize)
		throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getContract_bh();
		params[1] = vo.getCust_name();
		params[2] = vo.getPayment_type();
		params[3] = vo.getContract_id();
		params[4] = vo.getInput_man();
		return super.listProcPage(querySql,params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cont.ContractManagementLocal#queryByList(enfo.crm.vo.ContractManagementVO)
	 */
	@Override
	public List queryByList(ContractManagementVO vo)throws BusiException{
		Object[] params = new Object[5];
		params[0] = vo.getContract_bh();
		params[1] = vo.getCust_name();
		params[2] = vo.getPayment_type();
		params[3] = vo.getContract_id();
		params[4] = vo.getInput_man();
		return super.listBySql(querySql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cont.ContractManagementLocal#addContManagement(enfo.crm.vo.ContractManagementVO)
	 */
	@Override
	public Integer addContManagement(ContractManagementVO vo)throws BusiException{
		Object[] params = new Object[7];
		params[0] = vo.getContract_bh();
		params[1] = vo.getCust_id();
		params[2] = vo.getPayment_type();
		params[3] = vo.getSign_date();
		params[4] = vo.getEnd_date();
		params[5] = vo.getScribe_money();
		params[6] = vo.getInput_man();
		
		return (Integer)super.cudProc(addSql,params,8,Types.INTEGER);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cont.ContractManagementLocal#addContractStat(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public void addContractStat(Integer product_id,Integer service_man,Integer flag,Integer input_man,String summary,
			Integer serial_no)throws BusiException{
		String sql = "{?=call SP_ADD_TCONTRACTSTAT(?,?,?,?,?,?)}";
		Object[] params = new Object[6];
		params[0] = product_id;
		params[1] = service_man;
		params[2] = flag;
		params[3] = summary;
		params[4] = input_man;
		params[5] = serial_no;
		super.cudProc(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cont.ContractManagementLocal#queryContractStat(java.lang.Integer)
	 */
	@Override
	public List queryContractStat(Integer product_id) throws BusiException{
		String sql = "SELECT SERIAL_NO,SERVICE_MAN,FLAG,SUMMARY from TCONTRACTSTAT WHERE PRODUCT_ID=?";
		Object[] params = new Object[1];
		params[0] = product_id;
		return super.listBySql(sql,params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.cont.ContractManagementLocal#queryContractStatResult(java.lang.Integer)
	 */
	@Override
	public List queryContractStatResult(Integer service_man) throws BusiException{
		String sql = "{call SP_QUERY_TCONTRACTSTAT_TOSERVICEMAN(?)}";
		Object[] params = new Object[1];
		params[0] = service_man;
		return super.listBySql(sql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cont.ContractManagementLocal#modiContManagement(enfo.crm.vo.ContractManagementVO)
	 */
	@Override
	public void modiContManagement(ContractManagementVO vo)throws BusiException{
		Object[] params = new Object[8];
		params[0] = vo.getContract_id();
		params[1] = vo.getContract_bh();
		params[2] = vo.getCust_id();
		params[3] = vo.getPayment_type();
		params[4] = vo.getSign_date();
		params[5] = vo.getEnd_date();
		params[6] = vo.getScribe_money();
		params[7] = vo.getInput_man();
		super.cudProc(modiSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.cont.ContractManagementLocal#deleteContManagement(enfo.crm.vo.ContractManagementVO)
	 */
	@Override
	public void deleteContManagement(ContractManagementVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getContract_id();
		params[1] = vo.getInput_man();
		super.cudProc(deleteSql,params);	
	}
}