 
package enfo.crm.intrust;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.DeployVO;

@Component(value="deploy")
public class DeployBean extends enfo.crm.dao.IntrustBusiExBean implements DeployLocal {

	/**
	 * 查询客户收益账户明细
	 */
	private static final String listSql =
		"{call SP_QUERY_TDEPLOY_CUSTID(?,?,?)}";

	/**
	 * 查询客户收益账户明细
	 */
	private static final String listSqlByCustId =
		"{call SP_QUERY_TDEPLOY_BY_CUST_ID(?,?,?)}";

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.DeployLocal#querycust(enfo.crm.vo.DeployVO)
	 */
	@Override
	public List querycust(DeployVO vo) throws BusiException {
		List list = new ArrayList();
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStartdate(), new Integer(0));
		params[2] = Utility.parseInt(vo.getEnddate(), new Integer(0));
		list = super.listBySql(listSql, params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.DeployLocal#queryForCust(enfo.crm.vo.DeployVO)
	 */
	@Override
	public List queryForCust(DeployVO vo) throws BusiException {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		params[3] = Utility.trimNull(vo.getContract_sub_bh());
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		return CrmDBManager.listBySql("{call SP_QUERY_TDEPLOY_DETAIL4CUST(?,?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.DeployLocal#queryDeployByCustId(enfo.crm.vo.DeployVO)
	 */
	@Override
	public List queryDeployByCustId(DeployVO vo) throws BusiException {
		List list = new ArrayList();
		String listSql="{call SP_QUERY_TDEPLOY_CRMBY_CUST_ID(?,?,?)}"; //从CRM的过程中调用
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		list = CrmDBManager.listBySql(listSql, params);
		return list;
	}
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.DeployLocal#queryOutputList(enfo.crm.vo.DeployVO, int, int)
	 */
    @Override
	public IPageList queryOutputList(DeployVO vo, int page, int pagesize) 
            throws Exception {
        Object[] params = new Object[10];
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getBank_id();
        params[3] = vo.getSy_type();
        params[4] = vo.getJk_type();
        params[5] = Utility.parseInt(vo.getSy_date(), new Integer(0));
        params[6] = vo.getProv_level();
        params[7] = vo.getInput_man();
        params[8] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        params[9] = vo.getLink_man();
        return super.listProcPage("{call SP_QUERY_TDEPLOY_OUTPUT(?,?,?,?,?,?,?,?,?,?)}", 
                    params, page, pagesize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.DeployLocal#queryDetail(enfo.crm.vo.DeployVO, int, int)
	 */
    @Override
	public IPageList queryDetail(DeployVO vo, int page, int pagesize) throws Exception {
        String[] paras = Utility.splitString(vo.getSy_type(), "$");
        Object[] params = new Object[20];
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getContract_bh();
        params[3] = vo.getCust_no();
        params[4] = vo.getCust_name();
        params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        params[6] = paras[0].trim();
        params[7] = paras[1].trim();
        params[8] = paras[2].trim();
        params[9] = paras[3].trim();
        params[10] = paras[4].trim();
        params[11] = vo.getProv_level();
        params[12] = Utility.parseInt(vo.getBegin_date(), new Integer(0));
        params[13] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
        params[14] = vo.getProduct_name();
        params[15] = Utility.parseInt(vo.getPz_flag(), new Integer(0));
        params[16] = Utility.parseInt(vo.getFp_flag(), new Integer(0));
        params[17] = vo.getContract_sub_bh();
        params[18] = vo.getSub_product_id();
        params[19] = vo.getLink_man();
        return super
                .listProcPage(
                        "{call SP_QUERY_TDEPLOY_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                        params, page, pagesize);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.DeployLocal#load(java.lang.Integer)
	 */
    @Override
	public List load(Integer serial_no) throws Exception {
        Object[] param = new Object[1];
        param[0] = serial_no;
        return super.listBySql("{call SP_QUERY_TDEPLOY_LOAD(?)}", param);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.DeployLocal#getOutputListResult(enfo.crm.vo.DeployVO)
	 */
    @Override
	public sun.jdbc.rowset.CachedRowSet getOutputListResult(DeployVO vo) throws Exception {
        sun.jdbc.rowset.CachedRowSet rowset = new sun.jdbc.rowset.CachedRowSet();
        
        //super.list();
        //begin 2005-7-26 添加了一个参数
        String outSql = "{call SP_QUERY_TDEPLOY_OUTPUT(?,?,?,?,?,?,?,?,?,?)}";
        //end
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rslist = null;
        try {
            conn = IntrustDBManager.getConnection();
            stmt = conn.prepareCall(outSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            if (vo.getBook_code() != null)
                stmt.setInt(1, vo.getBook_code().intValue());
            else
                stmt.setInt(1, 1);
            if (vo.getProduct_id() != null)
                stmt.setInt(2, vo.getProduct_id().intValue());
            else
                stmt.setInt(2, 0);
            stmt.setString(3, vo.getBank_id());
            stmt.setString(4, vo.getSy_type());
            stmt.setString(5, vo.getJk_type());
            if (vo.getSy_date() != null)
                stmt.setInt(6, vo.getSy_date().intValue());
            else
                stmt.setInt(6, 0);
            stmt.setString(7, vo.getProv_level());
            //begin 2005-7-26 添加了一个参数
            if (vo.getInput_man() == null)
                stmt.setInt(8, 0);
            else
                stmt.setInt(8, vo.getInput_man().intValue());
            stmt.setInt(9, vo.getSub_product_id().intValue());
            if (vo.getLink_man() == null)
                stmt.setInt(10, 0);
            else
                stmt.setInt(10, vo.getLink_man().intValue());
            rslist = stmt.executeQuery();

            rowset.close();
            rowset.populate(rslist);

        } finally {
            if (rslist != null)
                rslist.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
        return rowset;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.DeployLocal#getDeployOutputResult(enfo.crm.vo.DeployVO, java.lang.String)
	 */
    @Override
	public sun.jdbc.rowset.CachedRowSet getDeployOutputResult(DeployVO vo, String date)
    throws Exception {
        sun.jdbc.rowset.CachedRowSet rowset = new sun.jdbc.rowset.CachedRowSet();
        ResultSet rslist = null;
        //super.list();
        String[] paras = Utility.splitString(vo.getSy_type(), "$");
        String[] paras2 = Utility.splitString(date, "|");
        if (paras != null) {
        
            Connection conn = null;
            CallableStatement stmt = null;
        
            try {
                conn = IntrustDBManager.getConnection();
                        stmt = conn
                        .prepareCall(
                                "{call SP_QUERY_TDEPLOY_OUTPUT_ALL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                stmt.setInt(1, vo.getBook_code().intValue());
                if (vo.getProduct_id() != null)
                    stmt.setInt(2, vo.getProduct_id().intValue());
                else
                    stmt.setInt(2, 0);
                stmt.setString(3, vo.getBank_id());
                stmt.setString(4, vo.getJk_type());
                if (vo.getList_id() != null)
                    stmt.setInt(5, vo.getList_id().intValue());
                else
                    stmt.setInt(5, 0);
        
                stmt.setString(6, vo.getContract_bh());
        
                stmt.setInt(7, vo.getProv_flag().intValue());
        
                stmt.setString(8, vo.getProv_level());
                
                if (vo.getInput_man() != null)
                    stmt.setInt(9, vo.getInput_man().intValue());
                else
                    stmt.setInt(9, 0);
                
                stmt.setString(10, paras[0].trim());
                stmt.setString(11, paras[1].trim());
                stmt.setString(12, paras[2].trim());
                stmt.setString(13, paras[3].trim());
                stmt.setString(14, paras[4].trim());
                stmt.setString(15, paras[5].trim());
                stmt.setString(16, paras[6].trim());
                
                stmt.setString(17, paras2[0].trim());
                stmt.setString(18, paras2[1].trim());
                stmt.setString(19, paras2[2].trim());
                stmt.setString(20, paras2[3].trim());
                stmt.setString(21, paras2[4].trim());
                
                stmt.setString(22, vo.getCust_name());
                
                if (vo.getFp_flag() != null)
                    stmt.setInt(23, vo.getFp_flag().intValue());
                else
                    stmt.setInt(23, 0);
                stmt.setString(24, vo.getContract_sub_bh());
                stmt.setInt(25, vo.getSub_product_id().intValue());
                stmt.setInt(26, vo.getBonus_flag().intValue());
                stmt.setInt(27, vo.getLink_man().intValue());
                rslist = stmt.executeQuery();
        
                rowset.close();
                rowset.populate(rslist);
            } finally {
                if (rslist != null)
                    rslist.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
        }
        return rowset;
    }
  
}