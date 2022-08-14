 
package enfo.crm.marketing;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.BenifitorVO;

@Component(value="benifiterQuery")
public class BenifiterQueryBean extends enfo.crm.dao.CrmBusiExBean implements BenifiterQueryLocal {
	
	 /* (non-Javadoc)
	 * @see enfo.crm.marketing.BenifiterQueryLocal#listbySqlSYRAll(enfo.crm.vo.BenifitorVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList listbySqlSYRAll(BenifitorVO vo,String[] totalColumn,int pageIndex,int pageSize) throws Exception {	
        Object[] params = new Object[15]; 
        params[0] = vo.getBook_code();
        params[1] = vo.getSerial_no();
        params[2] = vo.getProduct_id();
        params[3] = vo.getContract_bh();
        params[4] = vo.getContract_sub_bh();
        params[5] = vo.getSy_cust_no();
        params[6] = vo.getCust_no();
        params[7] = vo.getSy_cust_name();
        params[8] = vo.getCust_type(); ;
        params[9] = vo.getProv_level();
        params[10] = vo.getBen_status();
        params[11] = vo.getInput_man();  
        params[12] = vo.getExport_flag();
        params[13] = vo.getExport_summary();
        params[14] = vo.getTeam_id();
        //params[14] = vo.getSub_product_id();
        //params[15] = Utility.parseInt(vo.getCxsy_flag() , new Integer(0));
        //params[16] = vo.getGov_prov_regional();
        //params[17] = vo.getGov_regional();
        return super.listProcPage("{call SP_QUERY_TBENIFITOR(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params, totalColumn, pageIndex, pageSize);
        //return super.listProcPage("{call SP_QUERY_TBENIFITOR(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params,totalColumn,pageIndex,pageSize);
    }
    
	 /* (non-Javadoc)
	 * @see enfo.crm.marketing.BenifiterQueryLocal#listbySqlAll(enfo.crm.vo.BenifitorVO)
	 */
    @Override
	public List listbySqlAll(BenifitorVO vo) throws Exception {
        Object[] params = new Object[13];
        params[0] = vo.getBook_code();
        params[1] = vo.getSerial_no();
        params[2] = vo.getProduct_id();
        params[3] = vo.getContract_bh();
        params[4] = vo.getContract_sub_bh();
        params[5] = vo.getSy_cust_no();
        params[6] = vo.getCust_no();
        params[7] = vo.getSy_cust_name();
        params[8] = vo.getCust_type(); ;
        params[9] = vo.getProv_level();
        params[10] = vo.getBen_status();
        params[11] = vo.getInput_man();
        params[12] = vo.getExport_flag();
		//params[13] = vo.getExport_summary();
        //params[14] = vo.getQuery_flag();
        try {
        	//return super.listBySql("{call SP_QUERY_TBENIFITOR(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params);
        	return super.listBySql("{call SP_QUERY_TBENIFITOR(?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);
        } catch (Exception e) {
			throw new BusiException("受益人信息查询失败: " + e.getMessage());
		}
    }
}