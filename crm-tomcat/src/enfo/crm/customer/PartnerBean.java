 
package enfo.crm.customer;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.PartnerVO;

@Component(value="partner")
public class PartnerBean extends enfo.crm.dao.CrmBusiExBean implements PartnerLocal {

	/* (non-Javadoc)
	 * @see enfo.crm.customer.PartnerLocal#append(enfo.crm.vo.PartnerVO)
	 */
	@Override
	public Integer append(PartnerVO vo) throws BusiException{
		Integer partn_id = new Integer(0);
		Object[] oparams = new Object[33];
		String strSql = "{?=call SP_ADD_TPartner(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		int outParamPos = 35;
		int outParamType = 4;		
		
		oparams[0] = vo.getPartn_no();
		oparams[1] = vo.getPartn_name();
		oparams[2] = vo.getPartn_tel();
		oparams[3] = vo.getPost_address();
		oparams[4] = vo.getPost_address2();
		oparams[5] = vo.getPost_code();
		oparams[6] = vo.getPost_code2();
		oparams[7] = vo.getCard_type();
		oparams[8] = vo.getCard_id();
		oparams[9] = vo.getCard_valid_date();
		oparams[10] = vo.getCountry();
		oparams[11] = vo.getBirthday();
		oparams[12] = vo.getAge();
		oparams[13] = vo.getSex();
		oparams[14] = vo.getO_tel();
		oparams[15] = vo.getH_tel();
		oparams[16] = vo.getMobile();
		oparams[17] = vo.getBp();
		oparams[18] = vo.getFax();
		oparams[19] = vo.getE_mail();
		oparams[20] = vo.getPartn_type();
		oparams[21] = vo.getJg_partn_type();
		oparams[22] = vo.getTouch_type();
		oparams[23] =	vo.getSummary();
		oparams[24] = vo.getLegal_man();
		oparams[25] = vo.getLegal_man_address();
		oparams[26] = vo.getContract_man();
		oparams[27] = vo.getService_man();
		oparams[28] = vo.getVoc_type();
		oparams[29] = vo.getReport_type();
		oparams[30] = vo.getPartn_type2_flag();
		oparams[31] = vo.getCust_id();
		oparams[32] = vo.getInput_man();
 		
		try {
			partn_id = (Integer) super.cudProc(strSql,oparams,outParamPos,outParamType);
		} catch (BusiException e) {
			throw new BusiException("合作伙伴新建失败:" + e.getMessage());
		}
	
		return partn_id;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.PartnerLocal#modi(enfo.crm.vo.PartnerVO)
	 */
	@Override
	public void modi(PartnerVO vo) throws BusiException{
		Object[] oparams = new Object[34];
		String strSql = "{?=call SP_MODI_TPartner(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
		oparams[0] = vo.getPartn_id();
		oparams[1] = vo.getPartn_no();
		oparams[2] = vo.getPartn_name();
		oparams[3] = vo.getPartn_tel();
		oparams[4] = vo.getPost_address();
		oparams[5] = vo.getPost_address2();
		oparams[6] = vo.getPost_code();
		oparams[7] = vo.getPost_code2();
		oparams[8] = vo.getCard_type();
		oparams[9] = vo.getCard_id();
		oparams[10] = vo.getCard_valid_date();
		oparams[11] = vo.getCountry();
		oparams[12] = vo.getBirthday();
		oparams[13] = vo.getAge();
		oparams[14] = vo.getSex();
		oparams[15] = vo.getO_tel();
		oparams[16] = vo.getH_tel();
		oparams[17] = vo.getMobile();
		oparams[18] = vo.getBp();
		oparams[19] = vo.getFax();
		oparams[20] = vo.getE_mail();
		oparams[21] = vo.getPartn_type();
		oparams[22] = vo.getJg_partn_type();
		oparams[23] = vo.getTouch_type();
		oparams[24] =	vo.getSummary();
		oparams[25] = vo.getLegal_man();
		oparams[26] = vo.getLegal_man_address();
		oparams[27] = vo.getContract_man();
		oparams[28] = vo.getService_man();
		oparams[29] = vo.getVoc_type();
		oparams[30] = vo.getReport_type();
		oparams[31] = vo.getPartn_type2_flag();
		oparams[32] = vo.getCust_id();
		oparams[33] = vo.getInput_man();
		
		try {
			super.cudProcNoRet(strSql,oparams);
		} catch (BusiException e) {
			throw new BusiException("合作伙伴编辑失败:" + e.getMessage());
		}		
}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.PartnerLocal#delete(enfo.crm.vo.PartnerVO)
	 */
	@Override
	public void delete(PartnerVO vo) throws BusiException {
		Object[] params = new Object[2];
		String sql ="{?=call SP_DEL_TPartner(?,?)}";
			
		params[0]= vo.getPartn_id();
		params[1]= vo.getInput_man();
			
		try {
			super.cudProc(sql,params);
		} catch (BusiException e) {
			throw new BusiException("合作伙伴删除失败:" + e.getMessage());
		}		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.PartnerLocal#query(enfo.crm.vo.PartnerVO)
	 */
	@Override
	public List query(PartnerVO vo) throws BusiException{
		String strSql = "{ call SP_QUERY_TPartner(?,?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] oparams = new Object[8];
		 
		oparams[0] = vo.getPartn_id();
		oparams[1] = vo.getPartn_no();
		oparams[2] = vo.getPartn_name();
		oparams[3] = vo.getCard_id();
		oparams[4] = vo.getPartn_type();
		oparams[5] = vo.getReport_type();
		oparams[6] = vo.getPartn_type2_flag();
		oparams[7] = vo.getInput_man();
		
		try {
			rsList = super.listProcAll(strSql,oparams);
		} catch (BusiException e) {
			throw new BusiException("合作伙伴信息查询失败:" + e.getMessage());
		}
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.PartnerLocal#pagelist_query(enfo.crm.vo.PartnerVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList pagelist_query(PartnerVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		String strSql = "{ call SP_QUERY_TPartner(?,?,?,?,?,?,?,?)}";
		IPageList rsList = null;		
		Object[] oparams = new Object[8];
		 
		oparams[0] = vo.getPartn_id();
		oparams[1] = vo.getPartn_no();
		oparams[2] = vo.getPartn_name();
		oparams[3] = vo.getCard_id();
		oparams[4] = vo.getPartn_type();
		oparams[5] = vo.getReport_type();
		oparams[6] = vo.getPartn_type2_flag();
		oparams[7] = vo.getInput_man();
	
		try {
			rsList = super.listProcPage(strSql,oparams,totalColumn,pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("合作伙伴信息查询失败:" + e.getMessage());
		}		
		return rsList;		
	}
}