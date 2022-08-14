 
package enfo.crm.system;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ProductVO;
import enfo.crm.vo.WikiSearchVO;

@Component(value="productInfoRepos")
public class ProductInfoReposBean extends enfo.crm.dao.CrmBusiExBean implements ProductInfoReposLocal {
	
	private String listSql = "{call SP_QUERY_TPRODUCT_INFOREPOS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//查询信息库中产品信息
	
	private String setSql = "{?=call SP_SET_TPREPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; //增加编辑产品
	
	private String deleteSql = "{?=call SP_DEL_TPREPRODUCT(?,?)}"; //删除
	
	//private String productSql = "{call SP_QUERY_TPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?)}";//产品搜索
	
	private String searchAll = "{call SP_SEARCH_INFOREPOS(?,?,?,?,?,?,?,?,?,?,?,?,?)}"; //产品知识库搜索
	
	private String bindSql = "{?=call SP_BINDING_TPREPRODUCT(?,?,?,?)}";
	
	private String relatedSql =  "{call SP_QUERY_PRODUCT_RELATED(?,?,?)}";//查询相关产品
	
	private String productSql = "{call SP_QUERY_TPREPRODUCT(?,?,?,?,?)}";//产品搜索
	
	private String checkQuotaSql = "{?=call SP_CHECK_TTEAMQUOTA(?,?,?)}"; 
	/* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#productInforeposList(enfo.crm.vo.ProductVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList productInforeposList(ProductVO vo, String[] totalColumn,
			int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[18];
		param[0] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		param[2] = Utility.trimNull(vo.getProduct_code());
		param[3] = Utility.trimNull(vo.getProduct_name());
		param[4] = Utility.trimNull(vo.getClass_type1());
		param[5] = Utility.trimNull(vo.getSearchkey());
		param[6] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[7] = Utility.parseInt(vo.getProduct_status1(),new Integer(0));
		param[8] = Utility.parseInt(vo.getOpen_flag(),new Integer(0));
		param[9] = Utility.trimNull(vo.getSortfield());
		param[10] = new Integer(0);
		param[11] = new Integer(0);
		param[12] = new Integer(0);
		param[13] = new Integer(0);
		param[14] = new Integer(0);
		param[15] = new Integer(0);
		param[16] = new Integer(0);
		param[17] = new Integer(0);
		try {
			return super
					.listProcPage(listSql,param,totalColumn, pageIndex, pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询产品信息失败" + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#preProductList(enfo.crm.vo.ProductVO, int, int)
	 */
    @Override
	public IPageList preProductList(ProductVO vo,
			int pageIndex, int pageSize) throws BusiException {
		Object[] param = new Object[5];
		param[0] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));

		param[1] = Utility.trimNull(vo.getProduct_name());
		param[2] = Utility.trimNull(vo.getProduct_status());
		param[3] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[4] = Utility.parseInt(vo.getQuotacheck_flag(),new Integer(0));
		try {
			return super
					.listProcPage(productSql,param, pageIndex, pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询产品信息失败" + e.getMessage());
		}
	}
    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#searchAll(enfo.crm.vo.WikiSearchVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList searchAll(WikiSearchVO vo, String[] totalColumn,
			int pageIndex, int pageSize) throws BusiException {
        Object[] param = new Object[13];
        param[0] = Utility.trimNull(vo.getSearchkey());
        param[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
        param[2] = Utility.parseInt(vo.getInclude_product(),new Integer(0));
        param[3] = Utility.parseInt(vo.getInclude_wiki(),new Integer(0));
        param[4] = Utility.parseInt(vo.getProduct_status(),new Integer(0));
        param[5] = Utility.parseInt(vo.getOpenflag(),new Integer(0));
        param[6] = Utility.trimNull(vo.getClass_type1());
        param[7] = Utility.parseInt(vo.getPre_date1(),new Integer(0));
        param[8] = Utility.parseInt(vo.getPre_date2(),new Integer(0));
        param[9] = Utility.parseInt(vo.getPrestart_date1(),new Integer(0));
        param[10] = Utility.parseInt(vo.getPrestart_date2(),new Integer(0));
        param[11] = Utility.trimNull(vo.getWiki_class());
        param[12] = Utility.trimNull(vo.getSortfield());
        try {
			return super
					.listProcPage(searchAll,param,totalColumn, pageIndex, pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询信息失败" + e.getMessage());
		}
    }
    
   /* (non-Javadoc)
 * @see enfo.crm.system.ProductInfoReposLocal#productList(enfo.crm.vo.ProductVO)
 */
    @Override
	public List productList(ProductVO vo) throws BusiException {
        List list = null;
		Object[] param = new Object[12];
		param[0] = Utility.parseInt(vo.getBook_code(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		param[2] = Utility.trimNull(vo.getProduct_code());
		param[3] = Utility.trimNull(vo.getProduct_name());
		param[4] = Utility.trimNull(vo.getProduct_status());
		param[5] = Utility.parseInt(vo.getOpen_flag(),new Integer(0));
		param[6] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		param[7] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		param[8] = Utility.parseInt(vo.getCheck_flag(),new Integer(0));
		param[9] = Utility.parseInt(vo.getIntrust_flag1(),new Integer(0));
		param[10] = Utility.parseInt(vo.getSale_status(),new Integer(0));
		param[11] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		try {
			list = super.listBySql(listSql,param);
			return list;
		} catch (BusiException e) {
			throw new BusiException("搜索产品信息失败" + e.getMessage());
		}
	}
    
    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#listBySql(enfo.crm.vo.ProductVO)
	 */
    @Override
	public List listBySql(ProductVO vo) throws Exception{
        List list = null;
        Object[] param = new Object[18];
		param[0] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		param[2] = Utility.trimNull(vo.getProduct_code());
		param[3] = Utility.trimNull(vo.getProduct_name());
		param[4] = Utility.trimNull(vo.getClass_type1());
		param[5] = Utility.trimNull(vo.getSearchkey());
		param[6] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		param[7] = Utility.parseInt(vo.getProduct_status1(),new Integer(0));
		param[8] = Utility.parseInt(vo.getOpen_flag(),new Integer(0));
		param[9] = Utility.trimNull(vo.getSortfield());
		param[10] = new Integer(0);
		param[11] = new Integer(0);
		param[12] = new Integer(0);
		param[13] = new Integer(0);
		param[14] = new Integer(0);
		param[15] = new Integer(0);
		param[16] = new Integer(0);
		param[17] = new Integer(0);
        list = super.listBySql(listSql,param);
        return list;
    }
    
    
    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#setPreProduct(enfo.crm.vo.ProductVO)
	 */
    @Override
	public Integer setPreProduct(ProductVO vo) throws Exception{
        Integer serial_no = new Integer(0);
        int ret;
        try {
            Connection conn = CrmDBManager.getConnection();
            CallableStatement stmt = conn.prepareCall(
                    "{?=call SP_SET_TPREPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setInt(2, Utility.parseInt(vo.getProduct_id(),new Integer(0)).intValue());
            stmt.setString(3, Utility.trimNull(vo.getPreproduct_name()));
            stmt.setString(4, Utility.trimNull(vo.getPeriod()));
            stmt.setString(5, Utility.trimNull(vo.getExpre_start_time()));
            stmt.setBigDecimal(6,Utility.parseBigDecimal(vo.getPre_money(),new BigDecimal(0)));
            stmt.setString(7,Utility.trimNull(vo.getProfit_date()));
            stmt.setString(8,Utility.trimNull(vo.getPre_rate()));
            stmt.setString(9,Utility.trimNull(vo.getCash_usetype()));
            stmt.setString(10,Utility.trimNull(vo.getEnsure_method()));
            stmt.setInt(11,Utility.parseInt(vo.getPre_start_date(),new Integer(0)).intValue());
            stmt.setInt(12,Utility.parseInt(vo.getPre_end_date(),new Integer(0)).intValue());
            stmt.setString(13,Utility.trimNull(vo.getClass_type1()));
            stmt.setString(14,Utility.trimNull(vo.getAdmin_manager3()));
            stmt.setInt(15,Utility.parseInt(vo.getDirect_sale(),new Integer(0)).intValue());
            stmt.setString(16,Utility.trimNull(vo.getAnnounce_url()));
            stmt.setString(17,Utility.trimNull(vo.getSummary()));
            stmt.setString(18,Utility.trimNull(vo.getKfq()));
            stmt.setString(19,Utility.trimNull(vo.getShsqtjq()));
            stmt.setString(20,Utility.trimNull(vo.getSyzh()));
            stmt.setString(21,Utility.trimNull(vo.getDxjg()));
            stmt.setString(22,Utility.trimNull(vo.getTzgw()));
            stmt.setString(23,Utility.trimNull(vo.getJjjl()));
            stmt.setString(24,Utility.trimNull(vo.getKfr()));
            stmt.setString(25,Utility.trimNull(vo.getPre_status()));
            stmt.setInt(26,Utility.parseInt(vo.getInput_man(),new Integer(0)).intValue());
            stmt.setInt(27,Utility.parseInt(vo.getStart_date(),new Integer(0)).intValue());
            stmt.setInt(28,Utility.parseInt(vo.getPreproduct_id(),new Integer(0)).intValue());
            stmt.registerOutParameter(28, java.sql.Types.INTEGER);
            stmt.setString(29, vo.getExpre_end_time());
            stmt.setInt(30, Utility.parseInt(vo.getPre_valid_days(),new Integer(0)).intValue());
            stmt.executeUpdate();
            ret = stmt.getInt(1);
            serial_no = new Integer(stmt.getInt(28));
            stmt.close();
            conn.close();
            if (ret != 100) throw new BusiException(ret);
            
        } catch (Exception e) {
            throw new BusiException("编辑产品失败: " + e.getMessage());
        }
        CrmDBManager.handleResultCode(ret);
        return serial_no;
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#delete(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void delete(ProductVO vo) throws Exception {
        Object[]param = new Object[2];
        param[0] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
        param[1] = Utility.parseInt(vo.getInput_man(),new Integer(0));
        try {
            super.cudProc(deleteSql,param);
		} catch (BusiException e) {
			throw new BusiException("删除产品失败" + e.getMessage());
		}
		
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#bindProduct(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void bindProduct(ProductVO vo) throws Exception{
        Object[] param = new Object[4];
        param[0] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
        param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
        param[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
        param[3] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
        try {
            super.cudProc(bindSql,param);
		} catch (BusiException e) {
			throw new BusiException("绑定产品失败" + e.getMessage());
		}
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#getCloudKeyword()
	 */
	@Override
	public List getCloudKeyword() throws BusiException {
     	List list = null;
 		Object[] param = new Object[0];
 		try {
 			list = super.listBySql("{call SP_CLOUD_KEYWORDS()}",param);
 			return list;
 		} catch (BusiException e) {
 			throw new BusiException("取关键字云失败" + e.getMessage());
 		}
 	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#relatedList(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List relatedList(ProductVO vo) throws BusiException {
        List list = null;
		Object[] param = new Object[3];
		param[0] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
		param[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		param[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		try {
			list = super.listBySql(relatedSql,param);
			return list;
		} catch (BusiException e) {
			throw new BusiException("获取相关产品信息失败" + e.getMessage());
		}
	}
    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#checkQuota(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void checkQuota(ProductVO vo) throws Exception{
        Object[] param = new Object[3];
        param[0] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
        param[1] = Utility.parseInt(vo.getQuotacheck_flag(),new Integer(0));
        param[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
        try {
            super.cudProc(checkQuotaSql,param);
		} catch (BusiException e) {
			throw new BusiException("审核信息失败" + e.getMessage());
		}
    }

    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#editIntroMaterial(enfo.crm.vo.ProductVO)
	 */
    @Override
	public void editIntroMaterial(ProductVO vo) throws Exception{
        Object[] param = new Object[8];
        param[0] = vo.getProduct_id();
        param[1] = vo.getPreproduct_id();        
        param[2] = vo.getProduct_desc();
        param[3] = vo.getFeasstudy();
        param[4] = vo.getFeasstudy_easy();
        param[5] = vo.getNotices();
        param[6] = vo.getStudy_voice();
        param[7] = vo.getInput_man();
        try {
            super.cudProc("{?=call SP_MODI_TPRODUCTIntroduction(?,?,?,?,?,?,?,?)}",param);
		} catch (BusiException e) {
			throw new BusiException("录入产品推介资料失败" + e.getMessage());
		}
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.system.ProductInfoReposLocal#queryIntroMaterial(enfo.crm.vo.ProductVO)
	 */
	@Override
	public List queryIntroMaterial(ProductVO vo) throws BusiException {
		Object[] param = new Object[3];
		param[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		param[1] = Utility.parseInt(vo.getPreproduct_id(),new Integer(0));
		param[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		try {
			return super.listBySql("{call SP_QUERY_TPRODUCTIntroduction(?,?,?)}",param);
		} catch (BusiException e) {
			throw new BusiException("查询产品推介资料失败" + e.getMessage());
		}
	}
}