 
package enfo.crm.customer;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
//import java.sql.Statement;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
//import enfo.crm.dao.IntrustDBManager;
import enfo.crm.fileupload.DestinationTableVo;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AmCustInfoVO;
import enfo.crm.vo.CustLevelVO;
import enfo.crm.vo.CustomerInfoVO;
import enfo.crm.vo.CustomerVO;

@Component(value="customer")
public class CustomerBean extends enfo.crm.dao.CrmBusiExBean implements CustomerLocal {

	/**
	 * 添加客户信息
	 */
	private static final String appendSql =
		"{?=call SP_ADD_TCustomers (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
	private static final String appendSql2 =
		"{?=call SP_ADD_TCustomers2 (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 客户信息合并处理
	 */
	private static final String uniteSql = "{call SP_HB_TCustomers(?,?,?,?)}";

	/**
	 * 删除客户信息
	 */
	private static final String delSql = "{call SP_DEL_TCustomers(?,?)}";

	/**
	 * 修改客户信息
	 */
	private static final String modiSql =
			"{?=call SP_MODI_TCustomers(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static final String modiSql_m =
			"{?=call SP_MODI_TCustomers_m(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String modiSql2_m =
			"{?=call SP_MODI_TCustomers2_m(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
	private static final String modiSql2 =
		"{?=call SP_MODI_TCustomers2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String modiSql3 =
		"{?=call SP_MODI_TCUSTOMER_CARDINFO(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String modiSql4 =
		"{?=call SP_MODI_TCUSTOMER_IMAGE(?,?,?,?,?,?)}";
	/**
	 * 根据证件号查询
	 */
	private static final String queryBuCustNoSql =
		"{call SP_QUERY_TCustomer_ByCustNO(?,?,?,?,?,?,?)}";

	/**
	 * 查询客户分配计划
	**/
	private static final String listSqlGain=
			"{call SP_QUERY_GAINPLAN_CUST(?,?,?,?,?,?)}";
	/**
	 * 查询客户基本信息
	 */
	private static final String listAll =
		"{call SP_QUERY_TCustomers(?,?,?,?,?,?,?,?,?,?,?)}";

	private static final String listAll_m =
			"{call SP_QUERY_TCustomers_m(?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * 查询新增客户信息 
	 */
	private static final String newCustSql = 
		"{call SP_STAT_NEWCUST_TOTAL(?,?,?,?,?,?,?)}";
	/**
	 * 查询证件快到期的客户信息  
	 */
	private static final String cardValidDateSql  = 
		"{call SP_QUERY_TCustomersCardValidDate(?,?)}";
	/**
	 * 查询客户信息(多参数)
	 */
	private static final String listSqlExt =
		"{call SP_QUERY_TCustomersExt(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	/**
	 * 查询客户信息(多参数)
	 */
	private static final String listSqlExt_C =
		"{call SP_QUERY_TCustomersExt_C(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	
	/**
	 * 查询礼物信息(多参数)
	 */
	private static final String listSqlGIFi ="{call SP_QUERY_TGIFIINFO(?,?)}";
	
	/**
	 * 查询礼物出库信息(多参数)
	 */
	private static final String listSqlGIFiMove ="{call SP_QUERY_TGIFITOCUST(?,?,?,?,?)}";
	/**
	 * 查询礼物出库信息(多参数)
	 */
	private static final String listSqlGIFiMove1 ="{call SP_QUERY_TGIFIMOVEOUT(?,?,?,?,?)}";
	
	/**
	 * 查询礼物发放信息(多参数)
	 */
	private static final String listSqlGIFitoCust ="{call SP_QUERY_TGIFITOCUST(?,?)}";
	
	
	/**
	 * 查询客户信息返回多项值  
	 */
	private static final String listSqlLoad = "{call SP_QUERY_TCustomers_LOAD (?,?,?,?)}";

	private static final String cusMRSql = "{call SP_QUERY_TCUSTSERVICELOG(?,?,?,?,?,?,?,?)}";
	
	private static final String custMRSql = "{call SP_QUERY_TCUSTSERVICELOG_BYCUST(?,?)}";
	
	private static final String queryCusSql = "{call SP_QUERY_TCustomers_LOAD_2(?,?,?,?,?,?,?,?,?)}";
	
	/**
	 * 客户投诉信息
	 */
	private static final String queryCompSql = "{call SP_QUERY_TCOMPLAINT(?,?,?,?,?,?,?)}";
	
	private static final String updateCompSql = "{?=call SP_MODI_TCOMPLAINT(?,?,?,?,?,?,?,?,?,?)}";
	
	private static final String addCompSql = "{?=call SP_ADD_TCOMPLAINT(?,?,?,?,?)}";
	
	/**
	 * 邮件群发
	 */
	private static final String addEmail = "{?=call SP_ADD_TDEPLOYEMAIL(?,?,?,?,?,?)}";
	private static final String queryEmail = "{call SP_QUERY_TDEPLOYEMAIL(?,?,?,?,?,?,?)}";
	private static final String modiEmail1 = "{?=call SP_MODI_TDEPLOYEMAIL(?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#append(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public Integer append(CustomerVO vo) throws BusiException {
		Integer new_cust_id = new Integer(0);
		Object[] oparams = new Object[103];
		
		oparams[0] = vo.getCust_name();
		oparams[1] = vo.getCust_tel();
		oparams[2] = vo.getPost_address();
		oparams[3] = vo.getPost_code();
		oparams[4] = vo.getCard_type();
		oparams[5] = vo.getCard_id();
		oparams[6] = Utility.parseInt(vo.getAge(), new Integer(0));
		oparams[7] = Utility.parseInt(vo.getSex(), new Integer(0));
		oparams[8] = vo.getO_tel();
		oparams[9] = vo.getH_tel();
		oparams[10] = vo.getMobile();
		oparams[11] = vo.getBp();
		oparams[12] = vo.getFax();
		oparams[13] = vo.getE_mail();
		oparams[14] = vo.getCust_type();
		oparams[15] = vo.getTouch_type();
		oparams[16] = vo.getCust_source();
		oparams[17] = vo.getSummary();
		oparams[18] = vo.getInput_man();
		oparams[19] = vo.getCust_no();
		oparams[20] = vo.getLegal_man();
		oparams[21] = vo.getLegal_address();
		oparams[22] = Utility.parseInt(vo.getBirthday(),new Integer(0));
		oparams[23] = vo.getPost_address2();
		oparams[24] = vo.getPost_code2();
		oparams[25] =
			Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
		oparams[26] = Utility.parseInt(vo.getPrint_post_info(), new Integer(0));
		oparams[27] = Utility.parseInt(vo.getIs_link(), new Integer(0));
		oparams[28] = Utility.parseInt(vo.getService_man(), new Integer(0));
		oparams[29] = vo.getVip_card_id();
		oparams[30] = Utility.parseInt(vo.getVip_date(), new Integer(0));
		oparams[31] = vo.getHgtzr_bh();
		oparams[32] = vo.getVoc_type();
		oparams[33] = Utility.parseInt(vo.getCard_valid_date(), null);
		oparams[34] = vo.getCountry();
		oparams[35] = vo.getJg_cust_type();
		oparams[36] = vo.getContact_man();
		oparams[37] = vo.getMoney_source();
		oparams[38] = vo.getMoney_source_name();
		oparams[39] = vo.getFact_controller();
		oparams[40] = vo.getPotenital_money();
		oparams[41] = vo.getGov_prov_regional();
		oparams[42] = vo.getGov_regional();
		oparams[43] = vo.getRecommended();
		
		oparams[44] = vo.getPersonal_income();
		oparams[45] = vo.getHousehold_income();
		oparams[46] = vo.getFeeding_num_people();
		oparams[47] = vo.getMain_source();
		oparams[48] = vo.getOther_source();
		oparams[49] = vo.getHouse_position();
		oparams[50] = vo.getHouse_property();
		oparams[51] = vo.getHouse_area();
		oparams[52] = vo.getPlat_envaluate();
		oparams[53] = vo.getMarket_appraisal();
		oparams[54] = vo.getVehicle_num();
		oparams[55] = vo.getVehicle_make();
		oparams[56] = vo.getVehicle_type();
		oparams[57] = vo.getCredit_type();//贷款种类
		oparams[58] = vo.getCredit_num();
		oparams[59] = vo.getCredit_start_date();
		oparams[60] = vo.getCredit_end_date();
		oparams[61] = vo.getOther_investment_status();
		oparams[62] = vo.getType_pref();
		oparams[63] = vo.getTime_limit_pref();
		oparams[64] = vo.getInvest_pref();
		oparams[65] = vo.getHobby_pref();
		oparams[66] = vo.getService_pref();
		oparams[67] = vo.getContact_pref();
		oparams[68] = vo.getHead_office_cust_id();
		oparams[69] = vo.getStature();
		oparams[70] = vo.getWeight();
		oparams[71] = vo.getSpouse_name();
		oparams[72] = vo.getSpouse_info();
		oparams[73] = vo.getChildren_name();
		oparams[74] = vo.getChildren_info();
		oparams[75] = vo.getNation();
		oparams[76] = vo.getMarital();
		oparams[77] = vo.getHealth();
		oparams[78] = vo.getEducation();
		oparams[79] = vo.getPost();
		oparams[80] = vo.getHolderofanoffice();
		oparams[81] = vo.getCompany_character();
		oparams[82] = vo.getCompany_staff();
		oparams[83] = vo.getBocom_staff();
	//机构
		oparams[84] = vo.getClient_quale();
		oparams[85] = vo.getRegistered_place();
		oparams[86] = vo.getRegistered_capital();
		oparams[87] = vo.getEmployee_num();//员工数
		oparams[88] = vo.getHolding();//控股相关信息
		oparams[89] = vo.getRisk_pref();//风险偏好
		oparams[90] = vo.getFree_cash_flow();
		oparams[91] = vo.getBurend_of_debt();//
		oparams[92] = vo.getComplete_flag();
		
		oparams[93] = vo.getCompany_unit();
		oparams[94] = vo.getCompany_depart();
		oparams[95] = vo.getCompany_position();
		
		oparams[96] = vo.getCust_aum();
		oparams[97] = vo.getCust_age_group();
		oparams[98] = vo.getInves_experinc();
		oparams[99] = vo.getCust_source_explain();
		oparams[100] = vo.getEast_jg_type();
		oparams[101] = vo.getJg_wtrlx2();
		oparams[102] = vo.getCust_flag();//vo.getTrue_flag();
		try {
			new_cust_id =
				(Integer) cudProcAdd(appendSql,
					oparams,
					31,
					java.sql.Types.INTEGER,
					vo.getInputStream());

		} catch (Exception e) {
			throw new BusiException("客户基本信息新建失败:" + e.getMessage());
		}
		return new_cust_id;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#append2(enfo.crm.vo.CustomerVO)
	 */
		@Override
		public Integer append2(CustomerVO vo) throws BusiException {
			Integer new_cust_id = new Integer(0);
			Object[] oparams = new Object[103];
		
			oparams[0] = vo.getCust_name();
			oparams[1] = vo.getCust_tel();
			oparams[2] = vo.getPost_address();
			oparams[3] = vo.getPost_code();
			oparams[4] = vo.getCard_type();
			oparams[5] = vo.getCard_id();
			oparams[6] = Utility.parseInt(vo.getAge(), new Integer(0));
			oparams[7] = Utility.parseInt(vo.getSex(), new Integer(0));
			oparams[8] = vo.getO_tel();
			oparams[9] = vo.getH_tel();
			oparams[10] = vo.getMobile();
			oparams[11] = vo.getBp();
			oparams[12] = vo.getFax();
			oparams[13] = vo.getE_mail();
			oparams[14] = vo.getCust_type();
			oparams[15] = vo.getTouch_type();
			oparams[16] = vo.getCust_source();
			oparams[17] = vo.getSummary();
			oparams[18] = vo.getInput_man();
			oparams[19] = vo.getCust_no();
			oparams[20] = vo.getLegal_man();
			oparams[21] = vo.getLegal_address();
			oparams[22] = Utility.parseInt(vo.getBirthday(), new Integer(0));
			oparams[23] = vo.getPost_address2();
			oparams[24] = vo.getPost_code2();
			oparams[25] =
				Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
			oparams[26] = Utility.parseInt(vo.getPrint_post_info(), new Integer(0));
			oparams[27] = Utility.parseInt(vo.getIs_link(), new Integer(0));
			oparams[28] = Utility.parseInt(vo.getService_man(), new Integer(0));
			oparams[29] = vo.getVip_card_id();
			oparams[30] = Utility.parseInt(vo.getVip_date(), new Integer(0));
			oparams[31] = vo.getHgtzr_bh();
			oparams[32] = vo.getVoc_type();
			oparams[33] = Utility.parseInt(vo.getCard_valid_date(), null);
			oparams[34] = vo.getCountry();
			oparams[35] = vo.getJg_cust_type();
			oparams[36] = vo.getContact_man();
			oparams[37] = vo.getMoney_source();
			oparams[38] = vo.getMoney_source_name();
			oparams[39] = vo.getFact_controller();
			oparams[40] = vo.getPotenital_money();
			oparams[41] = vo.getGov_prov_regional();
			oparams[42] = vo.getGov_regional();
			oparams[43] = vo.getRecommended();
			
			oparams[44] = vo.getPersonal_income();
			oparams[45] = vo.getHousehold_income();
			oparams[46] = vo.getFeeding_num_people();
			oparams[47] = vo.getMain_source();
			oparams[48] = vo.getOther_source();
			oparams[49] = vo.getHouse_position();
			oparams[50] = vo.getHouse_property();
			oparams[51] = vo.getHouse_area();
			oparams[52] = vo.getPlat_envaluate();
			oparams[53] = vo.getMarket_appraisal();
			oparams[54] = vo.getVehicle_num();
			oparams[55] = vo.getVehicle_make();
			oparams[56] = vo.getVehicle_type();
			oparams[57] = vo.getCredit_type();//贷款种类
			oparams[58] = vo.getCredit_num();
			oparams[59] = vo.getCredit_start_date();
			oparams[60] = vo.getCredit_end_date();
			oparams[61] = vo.getOther_investment_status();
			oparams[62] = vo.getType_pref();
			oparams[63] = vo.getTime_limit_pref();
			oparams[64] = vo.getInvest_pref();
			oparams[65] = vo.getHobby_pref();
			oparams[66] = vo.getService_pref();
			oparams[67] = vo.getContact_pref();
			oparams[68] = vo.getHead_office_cust_id();
			oparams[69] = vo.getStature();
			oparams[70] = vo.getWeight();
			oparams[71] = vo.getSpouse_name();
			oparams[72] = vo.getSpouse_info();
			oparams[73] = vo.getChildren_name();
			oparams[74] = vo.getChildren_info();
			oparams[75] = vo.getNation();
			oparams[76] = vo.getMarital();
			oparams[77] = vo.getHealth();
			oparams[78] = vo.getEducation();
			oparams[79] = vo.getPost();
			oparams[80] = vo.getHolderofanoffice();
			oparams[81] = vo.getCompany_character();
			oparams[82] = vo.getCompany_staff();
			oparams[83] = vo.getBocom_staff();
		//机构
			oparams[84] = vo.getClient_quale();
			oparams[85] = vo.getRegistered_place();
			oparams[86] = vo.getRegistered_capital();
			oparams[87] = vo.getEmployee_num();//员工数
			oparams[88] = vo.getHolding();//控股相关信息
			
			oparams[89] = vo.getRisk_pref();//风险偏好
			oparams[90] = vo.getFree_cash_flow();
			oparams[91] = vo.getBurend_of_debt();//
			oparams[92] = vo.getComplete_flag();
			
			oparams[93] = vo.getCompany_unit();
			oparams[94] = vo.getCompany_depart();
			oparams[95] = vo.getCompany_position();
			
			oparams[96] = vo.getCust_aum();
			oparams[97] = vo.getCust_age_group();
			oparams[98] = vo.getInves_experinc();
			oparams[99] = vo.getCust_source_explain();
			oparams[100] = vo.getEast_jg_type();
			oparams[101] = vo.getJg_wtrlx2();
			oparams[102] = vo.getCust_flag();//vo.getTrue_flag();
			try {
				new_cust_id =
					(Integer) cudProcAdd(appendSql2,
						oparams,
						31,
						java.sql.Types.INTEGER,
						vo.getInputStream());

			} catch (Exception e) {
				throw new BusiException("客户基本信息新建失败:" + e.getMessage());
			}
			return new_cust_id;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.customer.CustomerLocal#append_jh(enfo.crm.vo.CustomerVO)
		 */
		@Override
		public Integer append_jh(CustomerVO vo) throws BusiException {
			Object[] oparams = new Object[70];
	        oparams[0] = vo.getBook_code();
	        oparams[1] = vo.getCust_name();
	        oparams[2] = vo.getCust_tel();
	        oparams[3] = vo.getPost_address();
	        oparams[4] = vo.getPost_code();
	        oparams[5] = vo.getCard_type();
	        oparams[6] = vo.getCard_id();
	        oparams[7] = Utility.parseInt(vo.getAge(), new Integer(0));
	        oparams[8] = Utility.parseInt(vo.getSex(), new Integer(0));
	        oparams[9] = vo.getO_tel();
	        oparams[10] = vo.getH_tel();
	        oparams[11] = vo.getMobile();
	        oparams[12] = vo.getBp();
	        oparams[13] = vo.getFax();
	        oparams[14] = vo.getE_mail();
	        oparams[15] = vo.getCust_type();
	        oparams[16] = vo.getTouch_type();
	        oparams[17] = vo.getCust_source();
	        oparams[18] = vo.getSummary();
	        oparams[19] = vo.getInput_man();
	        oparams[20] = vo.getCust_no();
	        oparams[21] = vo.getLegal_man();
	        oparams[22] = vo.getLegal_address();
	        oparams[23] = Utility.parseInt(vo.getBirthday(), new Integer(0));
	        oparams[24] = vo.getPost_address2();
	        oparams[25] = vo.getPost_code2();
	        oparams[26] = Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
	        oparams[27] = Utility.parseInt(vo.getPrint_post_info(), new Integer(0));
	        oparams[28] = Utility.parseInt(vo.getIs_link(), new Integer(0));
	        oparams[29] = Utility.parseInt(vo.getService_man(), new Integer(0));
	        oparams[30] = vo.getVip_card_id();
	        oparams[31] = Utility.parseInt(vo.getVip_date(), new Integer(0));
	        oparams[32] = vo.getHgtzr_bh();
	        oparams[33] = vo.getVoc_type();
	        oparams[34] = Utility.parseInt(vo.getCard_valid_date(), null);
	        oparams[35] = vo.getCountry();
	        oparams[36] = vo.getJg_cust_type();
	        oparams[37] = vo.getContact_man();
	        oparams[38] = vo.getMoney_source();
	        
	        oparams[39] = vo.getFact_controller();
	        
	        oparams[40] = Utility.parseInt(vo.getLink_type(), new Integer(0));
	        oparams[41] = Utility.parseBigDecimal(vo.getLink_gd_money(), new BigDecimal(0));
	        oparams[42] = vo.getGrade_level();
	        oparams[43] = Utility.parseInt(vo.getComplete_flag(), new Integer(2));
	        oparams[44] = Utility.trimNull(vo.getGov_prov_regional());
	        oparams[45] = Utility.trimNull(vo.getGov_regional());
	        oparams[46] = Utility.trimNull(vo.getLegal_tel());
	        oparams[47] = Utility.trimNull(vo.getLegal_mobile());
	        oparams[48] = Utility.trimNull(vo.getIssued_org());
	        oparams[49] = Utility.trimNull(vo.getIndustry_post());
	        oparams[50] = Utility.trimNull(vo.getCust_industry());
	        oparams[51] = Utility.trimNull(vo.getCust_corp_nature());
	        oparams[52] = Utility.trimNull(vo.getCorp_branch());
	        oparams[53] = Utility.trimNull(vo.getService_man_tel());
	        oparams[54] = Utility.trimNull(vo.getGrade_score());
	        oparams[55] = Utility.trimNull(vo.getFc_card_type());
	        oparams[56] = Utility.trimNull(vo.getFc_card_id());
	        oparams[57] = Utility.trimNull(vo.getFc_card_valid_date());
	        oparams[58] = Utility.trimNull(vo.getSummary1());
	        oparams[59] = Utility.trimNull(vo.getSummary2());
	        oparams[60] = Utility.trimNull(vo.getLegal_post_code());
	        oparams[61] = Utility.trimNull(vo.getTrans_name());
	        oparams[62] = Utility.trimNull(vo.getTrans_tel());
	        oparams[63] = Utility.trimNull(vo.getTrans_mobile());
	        oparams[64] = Utility.trimNull(vo.getTrans_address());
	        oparams[65] = Utility.trimNull(vo.getTrans_post_code());
	        oparams[66] = Utility.trimNull(vo.getRegister_address());
	        oparams[67] = Utility.trimNull(vo.getRegister_post_code());
	        oparams[68] = Utility.trimNull(vo.getEast_jg_type());
	        oparams[69] = Utility.trimNull(vo.getJg_wtrlx2());
	        try {
	            return (Integer) super
	                    .cudProc(
	                            "{?=call SP_ADD_TCUSTOMERINFO (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            oparams, 32, java.sql.Types.INTEGER);
	        } catch (Exception e) {
	            throw new BusiException("客户基本信息新建失败:" + e.getMessage());
	        }	        
		}
	
		 /* (non-Javadoc)
		 * @see enfo.crm.customer.CustomerLocal#save_jh(enfo.crm.vo.CustomerVO)
		 */
	    @Override
		public void save_jh(CustomerVO vo) throws BusiException {
	        Object[] params = new Object[70];
	        params[0] = vo.getCust_id();
	        params[1] = vo.getCust_name();
	        params[2] = vo.getCust_tel();
	        params[3] = vo.getPost_address();
	        params[4] = vo.getPost_code();
	        params[5] = vo.getCard_type();
	        params[6] = vo.getCard_id();
	        params[7] = Utility.parseInt(vo.getAge(), new Integer(0));
	        params[8] = Utility.parseInt(vo.getSex(), new Integer(0));
	        params[9] = vo.getO_tel();
	        params[10] = vo.getH_tel();
	        params[11] = vo.getMobile();
	        params[12] = vo.getBp();
	        params[13] = vo.getFax();
	        params[14] = vo.getE_mail();
	        params[15] = Utility.parseInt(vo.getCust_type(), new Integer(0));
	        params[16] = vo.getTouch_type();
	        params[17] = vo.getCust_source();
	        params[18] = vo.getSummary();
	        params[19] = vo.getInput_man();
	        params[20] = vo.getCust_no();
	        params[21] = vo.getLegal_man();
	        params[22] = vo.getLegal_address();
	        params[23] = Utility.parseInt(vo.getBirthday(), new Integer(0));
	        params[24] = vo.getPost_address2();
	        params[25] = vo.getPost_code2();
	        params[26] = Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
	        params[27] = Utility.parseInt(vo.getPrint_post_info(), new Integer(0));
	        params[28] = Utility.parseInt(vo.getIs_link(), new Integer(0));
	        params[29] = Utility.parseInt(vo.getService_man(), vo.getInput_man());
	        params[30] = vo.getVip_card_id();
	        params[31] = Utility.parseInt(vo.getVip_date(), vo.getInput_date());
	        params[32] = vo.getHgtzr_bh();
	        params[33] = vo.getVoc_type();
	        params[34] = Utility.parseInt(vo.getCard_valid_date(), null);
	        params[35] = vo.getCountry();
	        params[36] = vo.getJg_cust_type();
	        params[37] = vo.getContact_man();
	        params[38] = vo.getMoney_source();
	        params[39] = vo.getFact_controller();
	        params[40] = Utility.parseInt(vo.getLink_type(), new Integer(0));
	        params[41] = Utility.parseBigDecimal(vo.getLink_gd_money(), new BigDecimal(0));
	        params[42] = vo.getGrade_level();
	        params[43] = Utility.parseInt(vo.getComplete_flag(), new Integer(2));
	        params[44] = Utility.trimNull(vo.getGov_prov_regional());
	        params[45] = Utility.trimNull(vo.getGov_regional());
	        params[46] = Utility.trimNull(vo.getLegal_tel());
	        params[47] = Utility.trimNull(vo.getLegal_mobile());
	        params[48] = Utility.trimNull(vo.getIssued_org());
	        params[49] = Utility.trimNull(vo.getIndustry_post());
	        params[50] = Utility.trimNull(vo.getCust_industry());
	        params[51] = Utility.trimNull(vo.getCust_corp_nature());
	        params[52] = Utility.trimNull(vo.getCorp_branch());
	        params[53] = Utility.trimNull(vo.getService_man_tel());
	        params[54] = Utility.trimNull(vo.getGrade_score());
	        params[55] = Utility.trimNull(vo.getFc_card_type());
	        params[56] = Utility.trimNull(vo.getFc_card_id());
	        params[57] = Utility.trimNull(vo.getFc_card_valid_date());
	        params[58] = Utility.trimNull(vo.getSummary1());
	        params[59] = Utility.trimNull(vo.getSummary2());
	        params[60] = Utility.trimNull(vo.getLegal_post_code());
	        params[61] = Utility.trimNull(vo.getTrans_name());
	        params[62] = Utility.trimNull(vo.getTrans_tel());
	        params[63] = Utility.trimNull(vo.getTrans_mobile());
	        params[64] = Utility.trimNull(vo.getTrans_address());
	        params[65] = Utility.trimNull(vo.getTrans_post_code());
	        params[66] = Utility.trimNull(vo.getRegister_address());
	        params[67] = Utility.trimNull(vo.getRegister_post_code());
	        params[68] = Utility.trimNull(vo.getEast_jg_type());
	        params[69] = Utility.trimNull(vo.getJg_wtrlx2());
	        
	        try {
	            super.cudProc("{?=call SP_MODI_TCUSTOMERINFO (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            params);
	        } catch (Exception e) {
	            throw new BusiException("客户基本信息保存失败: " + e.getMessage());
	        }
	    }
	    
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#cudProcAdd(java.lang.String, java.lang.Object[], int, int, java.io.InputStream)
	 */
	@Override
	public Object cudProcAdd(
		String procSql,
		Object[] params,
		int outParamPos,
		int outParamType,
		InputStream inputStream)
		throws BusiException {
		int errorCode = 0;
		Object ret = null;
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(procSql);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			int trueIndex = 0;
			int tempOutParamPos = outParamPos - 2;
			for (int i = 0; i < params.length; i++) {
				trueIndex = i + 2;

				if (i >= tempOutParamPos) //由于有一个输出参数存在，后面实际的输入参数的位置将向后调1
					trueIndex++;
				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(trueIndex, null);
				else
					stmt.setObject(trueIndex, params[i]);
			}
			//判读是否上传图片
			
			if (inputStream!=null&&inputStream.available() != 0) {
				stmt.setBinaryStream(
					(params.length + 3),
					inputStream,
					inputStream.available());
			} else {
				//stmt.setObject((params.length + 3), null);
				stmt.setBinaryStream((params.length + 3), null,0);
			}
			stmt.registerOutParameter(outParamPos, outParamType);
			stmt.executeUpdate();
			errorCode = stmt.getInt(1);
			ret = stmt.getObject(outParamPos);
			stmt.close();
			conn.close();
			CrmDBManager.handleResultCode(errorCode);
		} catch (Exception e) {
			throw new BusiException("数据库操作异常！", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
		}
		return ret;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#cudProcEdit(java.lang.String, java.lang.Object[], java.io.InputStream)
	 */
	@Override
	public Object cudProcEdit(
		String procSql,
		Object[] params,
		InputStream inputStream)
		throws BusiException {
		int errorCode = 0;
		Object ret = null;
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(procSql);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			int trueIndex = 0;
			for (int i = 0; i < params.length; i++) {
				trueIndex = i + 2;
				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(trueIndex, null);
				else
					stmt.setObject(trueIndex, params[i]);
			}
			
			//判读是否上传图片
			if (inputStream!=null&&inputStream.available()!= 0) {
				stmt.setBinaryStream((params.length + 2),inputStream,inputStream.available());
			} else {
				stmt.setBinaryStream((params.length + 2), null,0);
			}
			stmt.executeUpdate();
			errorCode = stmt.getInt(1);
			stmt.close();
			conn.close();
			CrmDBManager.handleResultCode(errorCode);
		} catch (Exception e) {
			throw new BusiException("数据库操作异常！", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
		}
		return ret;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#cudProcEdit1(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public Object cudProcEdit1(CustomerVO vo)
		throws BusiException {
		//先判断卡ID和卡类型，然后更新该卡的证件照片
		Object[] params = new Object[4];
		params[0] = vo.getCust_id();
		params[1] = vo.getCard_type();
		params[2] = vo.getCard_id();
		params[3] = vo.getInput_man();
		
		int errorCode = 0;
		Object ret = null;
		Connection conn = null;
		CallableStatement stmt = null;
		byte [] bx = new byte[0];
		java.io.InputStream ins = new java.io.ByteArrayInputStream(bx);
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(modiSql4);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			int trueIndex = 0;
			for (int i = 0; i < params.length; i++) {
				trueIndex = i + 2;
				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(trueIndex, null);
				else
					stmt.setObject(trueIndex, params[i]);
			}
			/*判读是否上传图片*/
			/*判读是否上传图片*/
			if (vo.getInputStream()!=null&&vo.getInputStream().available()!= 0) {
				stmt.setBinaryStream((params.length + 2),vo.getInputStream(),vo.getInputStream().available());
			} else {
				stmt.setBinaryStream((params.length + 2), null,0);
			}
			if (vo.getInputStream1()!=null&&vo.getInputStream1().available()!= 0) {
				stmt.setBinaryStream((params.length + 3),vo.getInputStream1(),vo.getInputStream1().available());
			} else {
				stmt.setBinaryStream((params.length + 3),null,0);
			}	
			//stmt.setBinaryStream((params.length + 3),vo.getInputStream1(),vo.getInputStream1().available());
			stmt.executeUpdate();
			errorCode = stmt.getInt(1);
			stmt.close();
			conn.close();
			CrmDBManager.handleResultCode(errorCode);
		} catch (Exception e) {
			throw new BusiException("数据库操作异常！", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
		}
		return ret;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#unite(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void unite(CustomerVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = vo.getFrom_cust_id();
		params[1] = vo.getTo_cust_id();
		params[2] = vo.getInput_man();
		params[3] = new Integer(1); //@IN_HB_RGMEONY 1时合并认购次数，金额，存量金额等字段值
		try {
			super.cudProcNoRet(uniteSql, params);
		} catch (Exception e) {
			throw new BusiException("合并失败！: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryAllCustomer(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List queryAllCustomer(CustomerVO vo)throws BusiException{
		Object[] params = new Object[9];
		params[0] = Utility.parseInt(vo.getCust_id(),new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.trimNull(vo.getVip_card_id());
		params[4] = Utility.trimNull(vo.getCard_id());
		params[5] = Utility.trimNull(vo.getHgtzr_bh());
		params[6] = Utility.parseInt(vo.getIs_deal(), new Integer(0));
		params[7] = Utility.parseInt(vo.getIs_link(), new Integer(0));
		params[8] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		return super.listBySql(queryCusSql, params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryCardValidDate(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList queryCardValidDate(CustomerVO vo, int pageIndex, int pageSize) throws BusiException{
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCard_valid_date(),new Integer(0));
		params[1] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		try {
			return  super.listProcPage(cardValidDateSql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#delete(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void delete(CustomerVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = vo.getCust_id();
		params[1] = vo.getInput_man();
		try {
//			super.cudProcNoRet(delSql, params);
			CrmDBManager.cudProcNoRetSingle(delSql, params);
		} catch (Exception e) {
			throw new BusiException("客户基本信息删除失败！: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modify(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void modify(CustomerVO vo) throws BusiException {
		Object[] params = new Object[104];
		params[0] = vo.getCust_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getCust_tel();
		params[3] = vo.getPost_address();
		params[4] = vo.getPost_code();
		params[5] = vo.getCard_type();
		params[6] = vo.getCard_id();
		params[7] = Utility.parseInt(vo.getAge(), new Integer(0));
		params[8] = Utility.parseInt(vo.getSex(), new Integer(0));
		params[9] = vo.getO_tel();
		params[10] = vo.getH_tel();
		params[11] = vo.getMobile();
		params[12] = vo.getBp();
		params[13] = vo.getFax();
		params[14] = vo.getE_mail();
		params[15] = Utility.parseInt(vo.getCust_type(), new Integer(0));
		params[16] = vo.getTouch_type();
		params[17] = vo.getCust_source();
		params[18] = vo.getSummary();
		params[19] = vo.getInput_man();
		params[20] = vo.getCust_no();
		params[21] = vo.getLegal_man();
		params[22] = vo.getLegal_address();
		params[23] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[24] = vo.getPost_address2();
		params[25] = vo.getPost_code2();
		params[26] =
			Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
		params[27] = Utility.parseInt(vo.getPrint_post_info(), new Integer(0));
		params[28] = Utility.parseInt(vo.getIs_link(), new Integer(0));
		params[29] = Utility.parseInt(vo.getService_man(), vo.getInput_man());
		params[30] = vo.getVip_card_id();
		params[31] = Utility.parseInt(vo.getVip_date(), vo.getInput_man());
		params[32] = vo.getHgtzr_bh();
		params[33] = vo.getVoc_type();
		params[34] = Utility.parseInt(vo.getCard_valid_date(), null);
		params[35] = vo.getCountry();
		params[36] = vo.getJg_cust_type();
		params[37] = vo.getContact_man();
		params[38] = vo.getMoney_source();
		params[39] = vo.getMoney_source_name();
		params[40] = vo.getFact_controller();
		params[41] = vo.getPotenital_money();
		params[42] = vo.getGov_prov_regional();
		params[43] = vo.getGov_regional();
		params[44] = vo.getRecommended();
		
		params[45] = vo.getPersonal_income();
		params[46] = vo.getHousehold_income();
		params[47] = vo.getFeeding_num_people();
		params[48] = vo.getMain_source();
		params[49] = vo.getOther_source();
		params[50] = vo.getHouse_position();
		params[51] = vo.getHouse_property();
		params[52] = vo.getHouse_area();
		params[53] = vo.getPlat_envaluate();
		params[54] = vo.getMarket_appraisal();
		params[55] = vo.getVehicle_num();
		params[56] = vo.getVehicle_make();
		params[57] = vo.getVehicle_type();
		params[58] = vo.getCredit_type();//贷款种类
		params[59] = vo.getCredit_num();
		params[60] = vo.getCredit_start_date();
		params[61] = vo.getCredit_end_date();
		params[62] = vo.getOther_investment_status();
		params[63] = vo.getType_pref();
		params[64] = vo.getTime_limit_pref();
		params[65] = vo.getInvest_pref();
		params[66] = vo.getHobby_pref();
		params[67] = vo.getService_pref();
		params[68] = vo.getContact_pref();
		params[69] = vo.getHead_office_cust_id();
		params[70] = vo.getStature();
		params[71] = vo.getWeight();
		params[72] = vo.getSpouse_name();
		params[73] = vo.getSpouse_info();
		params[74] = vo.getChildren_name();
		params[75] = vo.getChildren_info();
		params[76] = vo.getNation();
		params[77] = vo.getMarital();
		params[78] = vo.getHealth();
		params[79] = vo.getEducation();
		params[80] = vo.getPost();
		params[81] = vo.getHolderofanoffice();
		params[82] = vo.getCompany_character();
		params[83] = vo.getCompany_staff();
		params[84] = vo.getBocom_staff();
	//机构
		params[85] = vo.getClient_quale();
		params[86] = vo.getRegistered_place();
		params[87] = vo.getRegistered_capital();
		params[88] = vo.getEmployee_num();//员工数
		params[89] = vo.getHolding();//控股相关信息
		params[90] = vo.getRisk_pref();//风险偏好		
		params[91] = vo.getFree_cash_flow();//
		params[92] = vo.getBurend_of_debt();//
		params[93] = vo.getComplete_flag();
		
		params[94] = vo.getCompany_unit();
		params[95] = vo.getCompany_depart();
		params[96] = vo.getCompany_position();
		
		params[97] = vo.getCust_aum();
		params[98] = vo.getCust_age_group();
		params[99] = vo.getInves_experinc();
		params[100] = vo.getCust_source_explain();
		params[101] = vo.getEast_jg_type();
		params[102] = vo.getJg_wtrlx2();
		params[103] = vo.getCust_flag();
		try {
			cudProcEdit(modiSql, params, vo.getInputStream());
	
		} catch (Exception e) {
			throw new BusiException("客户基本信息保存失败: " + e.getMessage());
		}
	}
	
	@Override
	public void modify_m(CustomerVO vo) throws BusiException {
		Object[] params = new Object[104];
		params[0] = vo.getCust_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getCust_tel();
		params[3] = vo.getPost_address();
		params[4] = vo.getPost_code();
		params[5] = vo.getCard_type();
		params[6] = vo.getCard_id();
		params[7] = Utility.parseInt(vo.getAge(), new Integer(0));
		params[8] = Utility.parseInt(vo.getSex(), new Integer(0));
		params[9] = vo.getO_tel();
		params[10] = vo.getH_tel();
		params[11] = vo.getMobile();
		params[12] = vo.getBp();
		params[13] = vo.getFax();
		params[14] = vo.getE_mail();
		params[15] = Utility.parseInt(vo.getCust_type(), new Integer(0));
		params[16] = vo.getTouch_type();
		params[17] = vo.getCust_source();
		params[18] = vo.getSummary();
		params[19] = vo.getInput_man();
		params[20] = vo.getCust_no();
		params[21] = vo.getLegal_man();
		params[22] = vo.getLegal_address();
		params[23] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[24] = vo.getPost_address2();
		params[25] = vo.getPost_code2();
		params[26] =
			Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
		params[27] = Utility.parseInt(vo.getPrint_post_info(), new Integer(0));
		params[28] = Utility.parseInt(vo.getIs_link(), new Integer(0));
		params[29] = Utility.parseInt(vo.getService_man(), vo.getInput_man());
		params[30] = vo.getVip_card_id();
		params[31] = Utility.parseInt(vo.getVip_date(), vo.getInput_man());
		params[32] = vo.getHgtzr_bh();
		params[33] = vo.getVoc_type();
		params[34] = Utility.parseInt(vo.getCard_valid_date(), null);
		params[35] = vo.getCountry();
		params[36] = vo.getJg_cust_type();
		params[37] = vo.getContact_man();
		params[38] = vo.getMoney_source();
		params[39] = vo.getMoney_source_name();
		params[40] = vo.getFact_controller();
		params[41] = vo.getPotenital_money();
		params[42] = vo.getGov_prov_regional();
		params[43] = vo.getGov_regional();
		params[44] = vo.getRecommended();
		
		params[45] = vo.getPersonal_income();
		params[46] = vo.getHousehold_income();
		params[47] = vo.getFeeding_num_people();
		params[48] = vo.getMain_source();
		params[49] = vo.getOther_source();
		params[50] = vo.getHouse_position();
		params[51] = vo.getHouse_property();
		params[52] = vo.getHouse_area();
		params[53] = vo.getPlat_envaluate();
		params[54] = vo.getMarket_appraisal();
		params[55] = vo.getVehicle_num();
		params[56] = vo.getVehicle_make();
		params[57] = vo.getVehicle_type();
		params[58] = vo.getCredit_type();//贷款种类
		params[59] = vo.getCredit_num();
		params[60] = vo.getCredit_start_date();
		params[61] = vo.getCredit_end_date();
		params[62] = vo.getOther_investment_status();
		params[63] = vo.getType_pref();
		params[64] = vo.getTime_limit_pref();
		params[65] = vo.getInvest_pref();
		params[66] = vo.getHobby_pref();
		params[67] = vo.getService_pref();
		params[68] = vo.getContact_pref();
		params[69] = vo.getHead_office_cust_id();
		params[70] = vo.getStature();
		params[71] = vo.getWeight();
		params[72] = vo.getSpouse_name();
		params[73] = vo.getSpouse_info();
		params[74] = vo.getChildren_name();
		params[75] = vo.getChildren_info();
		params[76] = vo.getNation();
		params[77] = vo.getMarital();
		params[78] = vo.getHealth();
		params[79] = vo.getEducation();
		params[80] = vo.getPost();
		params[81] = vo.getHolderofanoffice();
		params[82] = vo.getCompany_character();
		params[83] = vo.getCompany_staff();
		params[84] = vo.getBocom_staff();
	//机构
		params[85] = vo.getClient_quale();
		params[86] = vo.getRegistered_place();
		params[87] = vo.getRegistered_capital();
		params[88] = vo.getEmployee_num();//员工数
		params[89] = vo.getHolding();//控股相关信息
		params[90] = vo.getRisk_pref();//风险偏好		
		params[91] = vo.getFree_cash_flow();//
		params[92] = vo.getBurend_of_debt();//
		params[93] = vo.getComplete_flag();
		
		params[94] = vo.getCompany_unit();
		params[95] = vo.getCompany_depart();
		params[96] = vo.getCompany_position();
		
		params[97] = vo.getCust_aum();
		params[98] = vo.getCust_age_group();
		params[99] = vo.getInves_experinc();
		params[100] = vo.getCust_source_explain();
		params[101] = vo.getEast_jg_type();
		params[102] = vo.getJg_wtrlx2();
		params[103] = vo.getCust_flag();
		try {
			cudProcEdit(modiSql_m, params, vo.getInputStream());
	
		} catch (Exception e) {
			throw new BusiException("客户基本信息保存失败: " + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modify2(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void modify2(CustomerVO vo) throws BusiException {
		Object[] params = new Object[104];
		params[0] = vo.getCust_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getCust_tel();
		params[3] = vo.getPost_address();
		params[4] = vo.getPost_code();
		params[5] = vo.getCard_type();
		params[6] = vo.getCard_id();
		params[7] = Utility.parseInt(vo.getAge(), new Integer(0));
		params[8] = Utility.parseInt(vo.getSex(), new Integer(0));
		params[9] = vo.getO_tel();
		params[10] = vo.getH_tel();
		params[11] = vo.getMobile();
		params[12] = vo.getBp();
		params[13] = vo.getFax();
		params[14] = vo.getE_mail();
		params[15] = Utility.parseInt(vo.getCust_type(), new Integer(0));
		params[16] = vo.getTouch_type();
		params[17] = vo.getCust_source();
		params[18] = vo.getSummary();
		params[19] = vo.getInput_man();
		params[20] = vo.getCust_no();
		params[21] = vo.getLegal_man();
		params[22] = vo.getLegal_address();
		params[23] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[24] = vo.getPost_address2();
		params[25] = vo.getPost_code();
		params[26] =
			Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
		params[27] = Utility.parseInt(vo.getPrint_post_info(), new Integer(0));
		params[28] = Utility.parseInt(vo.getIs_link(), new Integer(0));
		params[29] = Utility.parseInt(vo.getService_man(), vo.getInput_man());
		params[30] = vo.getVip_card_id();
		params[31] = Utility.parseInt(vo.getVip_date(), vo.getInput_man());
		params[32] = vo.getHgtzr_bh();
		params[33] = vo.getVoc_type();
		params[34] = Utility.parseInt(vo.getCard_valid_date(), null);
		params[35] = vo.getCountry();
		params[36] = vo.getJg_cust_type();
		params[37] = vo.getContact_man();
		params[38] = vo.getMoney_source();
		params[39] = vo.getMoney_source_name();
		params[40] = vo.getFact_controller();
		params[41] = vo.getPotenital_money();
		params[42] = vo.getGov_prov_regional();
		params[43] = vo.getGov_regional();
		params[44] = vo.getRecommended();
		
		params[45] = vo.getPersonal_income();
		params[46] = vo.getHousehold_income();
		params[47] = vo.getFeeding_num_people();
		params[48] = vo.getMain_source();
		params[49] = vo.getOther_source();
		params[50] = vo.getHouse_position();
		params[51] = vo.getHouse_property();
		params[52] = vo.getHouse_area();
		params[53] = vo.getPlat_envaluate();
		params[54] = vo.getMarket_appraisal();
		params[55] = vo.getVehicle_num();
		params[56] = vo.getVehicle_make();
		params[57] = vo.getVehicle_type();
		params[58] = vo.getCredit_type();//贷款种类
		params[59] = vo.getCredit_num();
		params[60] = vo.getCredit_start_date();
		params[61] = vo.getCredit_end_date();
		params[62] = vo.getOther_investment_status();
		params[63] = vo.getType_pref();
		params[64] = vo.getTime_limit_pref();
		params[65] = vo.getInvest_pref();
		params[66] = vo.getHobby_pref();
		params[67] = vo.getService_pref();
		params[68] = vo.getContact_pref();
		params[69] = vo.getHead_office_cust_id();
		params[70] = vo.getStature();
		params[71] = vo.getWeight();
		params[72] = vo.getSpouse_name();
		params[73] = vo.getSpouse_info();
		params[74] = vo.getChildren_name();
		params[75] = vo.getChildren_info();
		params[76] = vo.getNation();
		params[77] = vo.getMarital();
		params[78] = vo.getHealth();
		params[79] = vo.getEducation();
		params[80] = vo.getPost();
		params[81] = vo.getHolderofanoffice();
		params[82] = vo.getCompany_character();
		params[83] = vo.getCompany_staff();
		params[84] = vo.getBocom_staff();
	//机构
		params[85] = vo.getClient_quale();
		params[86] = vo.getRegistered_place();
		params[87] = vo.getRegistered_capital();
		params[88] = vo.getEmployee_num();//员工数
		params[89] = vo.getHolding();//控股相关信息
		params[90] = vo.getRisk_pref();//风险偏好		
		params[91] = vo.getFree_cash_flow();//
		params[92] = vo.getBurend_of_debt();//
		params[93] = vo.getComplete_flag();
		
		params[94] = vo.getCompany_unit();
		params[95] = vo.getCompany_depart();
		params[96] = vo.getCompany_position();
		params[97] = vo.getCust_aum();
		params[98] = vo.getCust_age_group();
		params[99] = vo.getInves_experinc();
		params[100] = vo.getCust_source_explain();
		params[101] = vo.getEast_jg_type();
		params[102] = vo.getJg_wtrlx2();		
		params[103] = vo.getCust_flag();//vo.getTrue_flag();
		try {
			cudProcEdit(modiSql2, params, vo.getInputStream());

		} catch (Exception e) {
			throw new BusiException("客户基本信息保存失败: " + e.getMessage());
		}
	}

	@Override
	public void modify2_m(CustomerVO vo) throws BusiException {
		Object[] params = new Object[104];
		params[0] = vo.getCust_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getCust_tel();
		params[3] = vo.getPost_address();
		params[4] = vo.getPost_code();
		params[5] = vo.getCard_type();
		params[6] = vo.getCard_id();
		params[7] = Utility.parseInt(vo.getAge(), new Integer(0));
		params[8] = Utility.parseInt(vo.getSex(), new Integer(0));
		params[9] = vo.getO_tel();
		params[10] = vo.getH_tel();
		params[11] = vo.getMobile();
		params[12] = vo.getBp();
		params[13] = vo.getFax();
		params[14] = vo.getE_mail();
		params[15] = Utility.parseInt(vo.getCust_type(), new Integer(0));
		params[16] = vo.getTouch_type();
		params[17] = vo.getCust_source();
		params[18] = vo.getSummary();
		params[19] = vo.getInput_man();
		params[20] = vo.getCust_no();
		params[21] = vo.getLegal_man();
		params[22] = vo.getLegal_address();
		params[23] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[24] = vo.getPost_address2();
		params[25] = vo.getPost_code();
		params[26] =
			Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
		params[27] = Utility.parseInt(vo.getPrint_post_info(), new Integer(0));
		params[28] = Utility.parseInt(vo.getIs_link(), new Integer(0));
		params[29] = Utility.parseInt(vo.getService_man(), vo.getInput_man());
		params[30] = vo.getVip_card_id();
		params[31] = Utility.parseInt(vo.getVip_date(), vo.getInput_man());
		params[32] = vo.getHgtzr_bh();
		params[33] = vo.getVoc_type();
		params[34] = Utility.parseInt(vo.getCard_valid_date(), null);
		params[35] = vo.getCountry();
		params[36] = vo.getJg_cust_type();
		params[37] = vo.getContact_man();
		params[38] = vo.getMoney_source();
		params[39] = vo.getMoney_source_name();
		params[40] = vo.getFact_controller();
		params[41] = vo.getPotenital_money();
		params[42] = vo.getGov_prov_regional();
		params[43] = vo.getGov_regional();
		params[44] = vo.getRecommended();
		
		params[45] = vo.getPersonal_income();
		params[46] = vo.getHousehold_income();
		params[47] = vo.getFeeding_num_people();
		params[48] = vo.getMain_source();
		params[49] = vo.getOther_source();
		params[50] = vo.getHouse_position();
		params[51] = vo.getHouse_property();
		params[52] = vo.getHouse_area();
		params[53] = vo.getPlat_envaluate();
		params[54] = vo.getMarket_appraisal();
		params[55] = vo.getVehicle_num();
		params[56] = vo.getVehicle_make();
		params[57] = vo.getVehicle_type();
		params[58] = vo.getCredit_type();//贷款种类
		params[59] = vo.getCredit_num();
		params[60] = vo.getCredit_start_date();
		params[61] = vo.getCredit_end_date();
		params[62] = vo.getOther_investment_status();
		params[63] = vo.getType_pref();
		params[64] = vo.getTime_limit_pref();
		params[65] = vo.getInvest_pref();
		params[66] = vo.getHobby_pref();
		params[67] = vo.getService_pref();
		params[68] = vo.getContact_pref();
		params[69] = vo.getHead_office_cust_id();
		params[70] = vo.getStature();
		params[71] = vo.getWeight();
		params[72] = vo.getSpouse_name();
		params[73] = vo.getSpouse_info();
		params[74] = vo.getChildren_name();
		params[75] = vo.getChildren_info();
		params[76] = vo.getNation();
		params[77] = vo.getMarital();
		params[78] = vo.getHealth();
		params[79] = vo.getEducation();
		params[80] = vo.getPost();
		params[81] = vo.getHolderofanoffice();
		params[82] = vo.getCompany_character();
		params[83] = vo.getCompany_staff();
		params[84] = vo.getBocom_staff();
	//机构
		params[85] = vo.getClient_quale();
		params[86] = vo.getRegistered_place();
		params[87] = vo.getRegistered_capital();
		params[88] = vo.getEmployee_num();//员工数
		params[89] = vo.getHolding();//控股相关信息
		params[90] = vo.getRisk_pref();//风险偏好		
		params[91] = vo.getFree_cash_flow();//
		params[92] = vo.getBurend_of_debt();//
		params[93] = vo.getComplete_flag();
		
		params[94] = vo.getCompany_unit();
		params[95] = vo.getCompany_depart();
		params[96] = vo.getCompany_position();
		params[97] = vo.getCust_aum();
		params[98] = vo.getCust_age_group();
		params[99] = vo.getInves_experinc();
		params[100] = vo.getCust_source_explain();
		params[101] = vo.getEast_jg_type();
		params[102] = vo.getJg_wtrlx2();		
		params[103] = vo.getCust_flag();//vo.getTrue_flag();
		try {
			cudProcEdit(modiSql2_m, params, vo.getInputStream());

		} catch (Exception e) {
			throw new BusiException("客户基本信息保存失败: " + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryByCustNo(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List queryByCustNo(CustomerVO vo) throws BusiException {
		Object[] params = new Object[7];
		params[0] = vo.getCust_no();
		params[1] = vo.getCard_id();
		params[2] = vo.getVip_card_id();
		params[3] = vo.getHgtzr_bh();
		params[4] = vo.getCust_name();
		params[5] = vo.getIs_link();
		params[6] = vo.getInput_man();
		try {
			return super.listBySql(queryBuCustNoSql, params);
		} catch (Exception e) {
			throw new BusiException("根据证件号码、客户编号搜索查询失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listProcAll(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList listProcAll(CustomerVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[11];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[5] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[6] = vo.getProv_level();
		params[7] = vo.getCust_type();
		params[8] = vo.getIs_deal();
		params[9] = Utility.trimNull(vo.getCust_tel());
		params[10] = vo.getExport_flag();
		
		try {
			return  super.listProcPage(listAll, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listNewCustAll(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList listNewCustAll(CustomerVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getBegin_date(), new Integer(0));
		params[1] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		params[2] = Utility.parseInt(vo.getCust_type(),new Integer(0));
		params[3] = Utility.parseInt(vo.getQuery_type(),new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[5] = Utility.trimNull(vo.getCustomer_messager());
		params[6] = Utility.trimNull(vo.getTeam_name());
		try {
			return  super.listProcPage(newCustSql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listProcAll(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List listProcAll(CustomerVO vo) throws BusiException {
		Object[] params = new Object[11];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[5] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[6] = vo.getProv_level();
		params[7] = vo.getCust_type();
		params[8] = vo.getIs_deal();
		params[9] = Utility.trimNull(vo.getCust_tel());
		params[10] = vo.getExport_flag();
		
		try {
			return super.listBySql(listAll, params);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
	}

	@Override
	public List listProcAll_m(CustomerVO vo) throws BusiException {
		Object[] params = new Object[11];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[5] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[6] = vo.getProv_level();
		params[7] = vo.getCust_type();
		params[8] = vo.getIs_deal();
		params[9] = Utility.trimNull(vo.getCust_tel());
		params[10] = vo.getExport_flag();
		
		try {
			return super.listBySql(listAll_m, params);
		} catch (Exception e) {
			throw new BusiException("查询客户失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#getInputStream(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public InputStream getInputStream(CustomerVO vo) throws BusiException {
		Object[] params = new Object[9];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[5] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[6] = vo.getProv_level();
		params[7] = vo.getCust_type();
		params[8] = vo.getIs_deal();		
		
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		java.io.InputStream inputStream = null;
		try {
			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall("{call SP_QUERY_TCustomers(?,?,?,?,?,?,?,?,?)}");
			int trueIndex = 0;
			for (int i = 0; i < params.length; i++) {
				trueIndex = i + 1;
				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(trueIndex, null);
				else
					stmt.setObject(trueIndex, params[i]);
			}
			
			rs = stmt.executeQuery();
			if (rs.next()) {
				inputStream = rs.getBinaryStream("ImageIdentification");
			}
			//stmt.close();
			//conn.close();
		} catch (Exception e) {
			throw new BusiException("数据库操作异常！", e);
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}				
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
			if (conn != null) {
				try {
					if (!conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					throw new BusiException("关闭数据库连接异常！");
				}
			}
		}
		return inputStream;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listProcAllExt(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList listProcAllExt(CustomerVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params =  getParmas(vo);

		try {
			return	super.listProcPage(listSqlExt, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listProcAllExt(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList listProcAllExt_C(CustomerVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[58];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.trimNull(vo.getCust_source());
		params[4] = Utility.trimNull(vo.getCard_type());
		params[5] = Utility.trimNull(vo.getCard_id());
		params[6] = Utility.trimNull(vo.getTouch_type());
		params[7] = Utility.parseInt(vo.getMin_times(), new Integer(0));
		params[8] = Utility.parseInt(vo.getMax_times(), new Integer(0));
		params[9] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[10] = Utility.trimNull(vo.getH_tel());
		params[11] = Utility.trimNull(vo.getPost_address());
		params[12] = Utility.parseInt(vo.getCust_type(), new Integer(0));
		params[13] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[14] = Utility.trimNull(vo.getFamily_name());
		params[15] = Utility.parseInt(vo.getOnlyemail(), new Integer(0));
		params[16] = Utility.trimNull(vo.getCust_level());
		params[17] =
			Utility.parseBigDecimal(
				vo.getMin_total_money(),
				new BigDecimal(0.00));
		params[18] =
			Utility.parseBigDecimal(
				vo.getMax_total_money(),
				new BigDecimal(0.00));
		params[19] = Utility.parseInt(vo.getOnly_thisproduct(), new Integer(0));
		params[20] = Utility.trimNull(vo.getOrderby());
		Integer birInt = new Integer(0); 
		birInt = Utility.parseInt(vo.getBirthday(), new Integer(0));
		if(birInt.intValue() != 0);
			vo.setBirthday(new Integer(birInt.intValue()+2010*10000));
		params[21] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[22] =
			Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
		params[23] = Utility.parseInt(vo.getIs_link(), new Integer(0));
		params[24] = Utility.trimNull(vo.getProv_level());
		params[25] =
			Utility.parseBigDecimal(
				vo.getBen_amount_min(),
				new BigDecimal(0.00));
		params[26] =
			Utility.parseBigDecimal(
				vo.getBen_amount_max(),
				new BigDecimal(0.00));
		params[27] = Utility.trimNull(vo.getVip_card_id());
		params[28] = Utility.trimNull(vo.getHgtzr_bh());
		params[29] = Utility.parseInt(vo.getWt_flag(), new Integer(0));
		params[30] = Utility.trimNull(vo.getClassEs());
		params[31] =  Utility.trimNull(vo.getIs_deal());
		params[32] = Utility.parseInt(vo.getGroupID(), new Integer(0));
		params[33] = vo.getMoney_source_name();
		params[34] = vo.getCountry();
		params[35] = vo.getService_man();
		params[36] = vo.getRecommended();
		
		params[37] = Utility.parseInt(vo.getProductTo(),null);
		params[38] = vo.getStart_age();
		params[39] = vo.getEnd_age();
		params[40] = Utility.parseInt(vo.getCell_id(), new Integer(0));
		if(Utility.parseInt(vo.getBirthday_end(), new Integer(0)).intValue() != 0)
			vo.setBirthday_end(new Integer(vo.getBirthday_end().intValue()+2010*10000));
		params[41] = vo.getBirthday_end();
		params[42] = vo.getRg_begin_date();
		params[43] = vo.getRg_end_date();
		
		params[44] = vo.getStart_current_score();
		params[45] = vo.getEnd_current_score();
		params[46] = vo.getRegion();
		params[47] = vo.getChannel();
		params[48] = vo.getCity_name();
		params[49] = Utility.parseInt(vo.getSub_product_id(),0);
		params[50] = vo.getInvest_field();
		//params[51] = vo.getMobile();
		//params[52] = vo.getPost_address();
		params[51] = vo.getTrue_flag();
		params[52] = vo.getExport_flag();
		params[57] = Utility.trimNull(vo.getSl_status(),"-1"); 

		try {
			return	super.listProcPage(listSqlExt_C, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	
	/**
	 * @ejb.interface-method view-type = "local"	 * 查询礼物资料的基本信息——分页显示(多参数)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	public IPageList listGifiAll(CustomerVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[2];
		params[0] = vo.getGift_id();
		params[1] = vo.getGift_name();
		try {
			return	super.listProcPage(listSqlGIFi, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户资料的基本信息——列表显示(多参数)
	 * @param vo
	 * @return list
	 * @throws BusiException
	 */
	public List listGifiAll(CustomerVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = vo.getGift_id();
		params[1] = vo.getGift_name();
		try {
			return super.listBySql(listSqlGIFi, params);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询礼物资料的基本信息—
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	public List listGifimoveoutAll(CustomerVO vo)
		throws BusiException {
		Object[] params = new Object[5];
		params[0] = vo.getMove_out_id();
		params[1] = vo.getGift_name();
		params[2] =Utility.parseInt(vo.getCheck_flag1(),new Integer(0));
		params[3] =Utility.parseInt(vo.getCheck_flag2(),new Integer(0));
		params[4] = vo.getGift_id();
		try {
			return super.listBySql(listSqlGIFiMove1, params);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询礼品分发给客户的明细
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	public List listGifiToCust(CustomerVO vo) throws BusiException {
		Object[] params = new Object[1];
		params[0] = vo.getMove_out_id();
		String proc="{call SP_QUERY_TGIFTTOCUST(?)}";
		try {
			return super.listBySql(proc, params);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void checkGiftMoveout(CustomerVO vo) throws Exception {
		Object[] params = new Object[5];
		params[0] = vo.getMove_out_id();
		params[1] = vo.getGift_modi_flag();
		params[2] = vo.getCheck_flag();
		params[3] = vo.getCheck_man1_explain();
		params[4] = vo.getInput_man();
		try {
			super.cudProc("{?=call SP_CHECK_TGIFTMOVEOUT(?,?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("礼物审核失败：" + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询礼物资料的基本信息——分页显示(多参数)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	public IPageList listGifimoveoutAll(CustomerVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[5];
		params[0] = vo.getMove_out_id();
		params[1] = vo.getGift_name();
		params[2] =Utility.parseInt(vo.getCheck_flag1(),new Integer(0));
		params[3] =Utility.parseInt(vo.getCheck_flag2(),new Integer(0));
		params[4] = vo.getGift_id();
		try {
			return	super.listProcPage(listSqlGIFiMove1, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询礼物入库明细
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	public IPageList listGifiPutin(CustomerVO vo, int pageIndex, int pageSize) throws BusiException {
		Object[] params = new Object[2];
		params[0] = vo.getGift_id();
		params[1] = vo.getGift_name();
		String proc="{call SP_QUERY_TGIFTPUTIN(?,?)}";
		try {
			return super.listProcPage(proc,params,pageIndex,pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalValue
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	public IPageList listProcAllExt(CustomerVO vo,String[] totalValue, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params =  getParmas(vo);
		try {
			return	super.listProcPage(listSqlExt,params,totalValue,pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listProcAllExt(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List listProcAllExt(CustomerVO vo) throws BusiException {
		Object[] params = getParmas(vo);

		try {
			return super.listBySql(listSqlExt, params);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#getParmas(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public Object[] getParmas(CustomerVO vo) {
		Object[] params = new Object[53];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getCust_no());
		params[2] = Utility.trimNull(vo.getCust_name());
		params[3] = Utility.trimNull(vo.getCust_source());
		params[4] = Utility.trimNull(vo.getCard_type());
		params[5] = Utility.trimNull(vo.getCard_id());
		params[6] = Utility.trimNull(vo.getTouch_type());
		params[7] = Utility.parseInt(vo.getMin_times(), new Integer(0));
		params[8] = Utility.parseInt(vo.getMax_times(), new Integer(0));
		params[9] = Utility.parseInt(vo.getInput_man(), new Integer(888));
		params[10] = Utility.trimNull(vo.getH_tel());
		params[11] = Utility.trimNull(vo.getPost_address());
		params[12] = Utility.parseInt(vo.getCust_type(), new Integer(0));
		params[13] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[14] = Utility.trimNull(vo.getFamily_name());
		params[15] = Utility.parseInt(vo.getOnlyemail(), new Integer(0));
		params[16] = Utility.trimNull(vo.getCust_level());
		params[17] =
			Utility.parseBigDecimal(
				vo.getMin_total_money(),
				new BigDecimal(0.00));
		params[18] =
			Utility.parseBigDecimal(
				vo.getMax_total_money(),
				new BigDecimal(0.00));
		params[19] = Utility.parseInt(vo.getOnly_thisproduct(), new Integer(0));
		params[20] = Utility.trimNull(vo.getOrderby());
		Integer birInt = new Integer(0); 
		birInt = Utility.parseInt(vo.getBirthday(), new Integer(0));
		if(birInt.intValue() != 0);
			vo.setBirthday(new Integer(birInt.intValue()+2010*10000));
		params[21] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[22] =
			Utility.parseInt(vo.getPrint_deploy_bill(), new Integer(0));
		params[23] = Utility.parseInt(vo.getIs_link(), new Integer(0));
		params[24] = Utility.trimNull(vo.getProv_level());
		params[25] =
			Utility.parseBigDecimal(
				vo.getBen_amount_min(),
				new BigDecimal(0.00));
		params[26] =
			Utility.parseBigDecimal(
				vo.getBen_amount_max(),
				new BigDecimal(0.00));
		params[27] = Utility.trimNull(vo.getVip_card_id());
		params[28] = Utility.trimNull(vo.getHgtzr_bh());
		params[29] = Utility.parseInt(vo.getWt_flag(), new Integer(0));
		params[30] = Utility.trimNull(vo.getClassEs());
		params[31] =  Utility.trimNull(vo.getIs_deal());
		params[32] = Utility.parseInt(vo.getGroupID(), new Integer(0));
		params[33] = vo.getMoney_source_name();
		params[34] = vo.getCountry();
		params[35] = vo.getService_man();
		params[36] = vo.getRecommended();
		
		params[37] = Utility.parseInt(vo.getProductTo(),null);
		params[38] = vo.getStart_age();
		params[39] = vo.getEnd_age();
		params[40] = Utility.parseInt(vo.getCell_id(), new Integer(0));
		if(Utility.parseInt(vo.getBirthday_end(), new Integer(0)).intValue() != 0)
			vo.setBirthday_end(new Integer(vo.getBirthday_end().intValue()+2010*10000));
		params[41] = vo.getBirthday_end();
		params[42] = vo.getRg_begin_date();
		params[43] = vo.getRg_end_date();
		
		params[44] = vo.getStart_current_score();
		params[45] = vo.getEnd_current_score();
		params[46] = vo.getRegion();
		params[47] = vo.getChannel();
		params[48] = vo.getCity_name();
		params[49] = Utility.parseInt(vo.getSub_product_id(),0);
		params[50] = vo.getInvest_field();
		//params[51] = vo.getMobile();
		//params[52] = vo.getPost_address();
		params[51] = vo.getTrue_flag();
		params[52] = vo.getExport_flag();
		return params;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listByControl(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List listByControl(CustomerVO vo) throws BusiException {
		return listProcAll(vo);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listCustomerLoad(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List listCustomerLoad(CustomerVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = vo.getCust_id();
		params[1] = vo.getInput_man();
		params[2] = Utility.parseInt(vo.getQuery_flag(),new Integer(0));
		params[3] = vo.getExport_flag();
		return super.listBySql(listSqlLoad, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryRepeatCustomers(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList queryRepeatCustomers(
		CustomerVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		Object[] params = new Object[6];
		params[0] = vo.getRg_times();
		params[1] = vo.getRepaeat_flag();
		params[2] = vo.getInput_man();
		params[3] = vo.getMust_contain();
		params[4] = vo.getLoosely_match();
		params[5] = vo.getMax_diff();
		try {
			return super.listProcPage("{call SP_QUERY_TCustomers_REPEAT_CARD(?,?,?,?,?,?)}", params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void addCustlaundering(CustomerVO vo) throws Exception {
		Object[] params = new Object[2];
		
		params[0] = vo.getCust_id();
		params[1] = vo.getLaundering_para();
		try {
			super.cudProc("{?=call SP_ADD_TCUSTLAUNDERING(?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("新建客户反洗钱信息失败：" + e.getMessage());
		}
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void addCustlaunderinggrade(CustomerVO vo) throws Exception {
		Object[] params = new Object[2];
		
		params[0] = vo.getCust_id();
		params[1] = vo.getModi_man();
		try {
			super.cudProc("{?=call SP_ADD_TCUSTLAUNDERINGGRADE(?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("新建客户反洗钱信息失败：" + e.getMessage());
		}
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void addCustLevel(CustLevelVO vo) throws Exception {
		Object[] params = new Object[6];
		params[0] = vo.getProduct_id();
		params[1] = vo.getLevel_id();
		params[2] = vo.getLevel_value_name();
		params[3] = vo.getMin_value();
		params[4] = vo.getMax_value();
		params[5] = vo.getInput_man();

		try {
			super.cudProc("{?=call SP_ADD_TCUSTLEVEL(?,?,?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("新建级别失败：" + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void addGifi(CustomerVO vo) throws Exception {
		Object[] params = new Object[6];
			params[0] = vo.getGift_name();
			params[1] = vo.getGift_price();
			params[2] = vo.getGift_number();
			params[3] = vo.getGift_summary();
			params[4] = vo.getDetail_info();
			params[5] = vo.getInput_man();
				
		try {
			super.cudProc("{?=call SP_ADD_TGIFTPUTIN(?,?,?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("礼物入库失败：" + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public Integer addGifiMove(CustomerVO vo) throws Exception {
		Integer moveId = new Integer(0);
		Object[] params = new Object[4];
			params[0] = vo.getGift_id();
			params[1] = vo.getGift_number();
			params[2] = vo.getGift_summary();
			params[3] = vo.getInput_man();
		try {
			moveId=(Integer) super.cudProc(
                      "{?=call SP_ADD_TGIFTMOVEOUT(?,?,?,?,?)}",
                      params, 6, java.sql.Types.INTEGER);
		} catch (BusiException e) {
			throw new BusiException("礼物领用失败：" + e.getMessage());
		}
		return moveId;
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void addGifitoCust(CustomerVO vo) throws Exception {
		Object[] params = new Object[8];
			params[0] = vo.getMove_out_id();
			params[1] = vo.getCust_id();
			params[2] = Utility.parseInt(vo.getGift_number(),new Integer(0));
			params[3] = vo.getProvide(); 
			params[4] = vo.getPost_address();
			params[5] = vo.getInput_man();
			params[6] = Utility.parseInt(vo.getProvide_dade(),new Integer(0));
			params[7] = vo.getCheck_flag();
		try {
			super.cudProc("{?=call SP_ADD_TGIFTTOCUST(?,?,?,?,?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("礼物出库失败：" + e.getMessage());
		}
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void modiCustLevel(CustLevelVO vo) throws Exception {
		Object[] params = new Object[5];
		params[0] = vo.getSerial_no();
		params[1] = vo.getLevel_value_name();
		params[2] = vo.getMin_value();
		params[3] = vo.getMax_value();
		params[4] = vo.getInput_man();

		try {
			super.cudProc("{?=call SP_MODI_TCUSTLEVEL(?,?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("修改级别失败：" + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void updateGifi(CustomerVO vo) throws Exception {
		Object[] params = new Object[7];
		params[0] =  Utility.parseInt(vo.getGift_id(),new Integer(0));
		params[1] = vo.getGift_name();
		params[2] = vo.getGift_price();
		params[3] = Utility.parseInt(vo.getGift_number(),new Integer(1));
		params[4] = vo.getGift_summary();
		params[5] = vo.getDetail_info();
		params[6] =Utility.parseInt(vo.getInput_man(),new Integer(0));
		try {
			super.cudProc("{?=call SP_MODI_TGIFTPUTIN(?,?,?,?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("修改礼物入库信息失败：" + e.getMessage());
		}
	}
	
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void modiGiftMove(CustomerVO vo) throws Exception {
		Object[] params = new Object[9];
		params[0] =  Utility.parseInt(vo.getSerial_no(),new Integer(0));
		params[1] =  Utility.parseInt(vo.getMove_out_id(),new Integer(0));
		params[2] = Utility.parseInt(vo.getGift_number(),new Integer(0));
		params[3] = vo.getGift_summary();
		params[4] = Utility.parseInt(vo.getCheck_flag1(),new Integer(0));
		params[5] = Utility.parseInt(vo.getCheck_man1(),new Integer(0));
		params[6] = vo.getCheck_man1_explain();
		params[7] =  Utility.parseInt(vo.getGift_modi_flag(),new Integer(0));		
		params[8] =Utility.parseInt(vo.getInput_man(),new Integer(0));
		try {
			super.cudProc("{?=call SP_MODI_TGIFTMOVEOUT(?,?,?,?,?,?,?,?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("礼物审核信息失败：" + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void delCustLevel(CustLevelVO vo) throws Exception {
		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();

		try {
			super.cudProc("{?=call SP_DEL_TCUSTLEVEL(?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("删除级别失败：" + e.getMessage());
		}
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void deleteGifi(CustomerVO vo) throws Exception {
		Object[] params = new Object[2];
		params[0] = vo.getGift_id();
		params[1] = vo.getInput_man();
		try {
			super.cudProc("{?=call SP_DEL_TGIFTPUTIN(?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("删除级别失败：" + e.getMessage());
		}
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @throws Exception
	 */
	public void deleteGifimove(CustomerVO vo) throws Exception {
		Object[] params = new Object[2];
		params[0] = vo.getMove_out_id();
		params[1] = vo.getInput_man();
		try {
			super.cudProc("{?=call SP_DEL_TGIFTMOVEOUT(?,?)}", params);
		} catch (BusiException e) {
			throw new BusiException("删除级别失败：" + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local" 
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	public IPageList listCustLevel(CustLevelVO vo, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[5];
		params[0] = vo.getSerial_no();
		params[1] = vo.getProduct_id();
		params[2] = vo.getLevel_id();
		params[3] = vo.getLevel_value_id();
		params[4] = vo.getInput_man();
		try {
			return super.listProcPage("{call SP_QUERY_TCUSTLEVEL(?,?,?,?,?)}", params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询级别失败: " + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#importCustomer(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void importCustomer(CustomerVO vo) throws BusiException{
		Object[] params= new Object[39];
		String sql = "{?=call SP_IMPORT_CUSTOMERS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
		params[0] = vo.getCust_name();
		params[1] = vo.getCust_type_name();
		params[2] = vo.getCard_type_name();
		params[3] = vo.getCard_id();
		params[4] = vo.getPost_address();
		params[5] = vo.getPost_code();
		params[6] = vo.getMobile();
		params[7] = vo.getCust_tel();
		params[8] = vo.getE_mail();
		params[9] = vo.getContact_man();
		params[10] = vo.getService_man_name();
		params[11] = vo.getIsClient();
		params[12] = vo.getTouch_type_name();
		params[13] = vo.getCust_source_name();
		params[14] = vo.getAge();
		params[15] = vo.getPotenital_money();
		params[16] = vo.getInput_man();
		params[17] = vo.getCust_id();
		params[18] = vo.getBirthday();
		params[19] = vo.getSex_name();
		params[20] = vo.getVoc_type_name();
		params[21] = vo.getH_tel();
		params[22] = vo.getO_tel();
		params[23] = vo.getMobile2();
		params[24] = vo.getFax();
		params[25] = vo.getPost_address2();
		params[26] = vo.getPost_code2();
		params[27] = vo.getLegal_man();
		
		params[28] = vo.getCompany_unit();
		params[29] = vo.getCompany_depart();
		params[30] = vo.getCompany_position();
		params[31] = vo.getCust_aum();
		params[32] = vo.getInves_experinc();
		params[33] = vo.getGov_prov_regional_name();
		params[34] = vo.getGov_regional_name();
		params[35] = vo.getRisk_pref();
		params[36] = vo.getHobby_pref();
		params[37] = vo.getCust_source_explain();
		params[38] = vo.getCust_age_group();
		
		try {
			super.cudProc(sql,params);	
		} catch (Exception e) {
			throw new BusiException("导入客户信息失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#getCustomerMaintenanceRecord(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList getCustomerMaintenanceRecord(CustomerVO vo,int pageIndex,int pageSize)throws BusiException{
		Object[]params = new Object[8];
		params[0] = vo.getSerial_no();
		params[1] = vo.getCust_no();
		params[2] = vo.getCust_name();
		params[3] = vo.getService_info();
		params[4] = vo.getService_man();
		params[5] = vo.getExecutor();
		params[6] = vo.getData_flag();
		params[7] = vo.getInput_man();
		return super.listProcPage(cusMRSql, params, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#getCustomerMaintenanceRecord(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List getCustomerMaintenanceRecord(CustomerVO vo)throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getCust_id();
		params[1] = vo.getInput_man();
		return super.listBySql(custMRSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modi2(enfo.crm.vo.AmCustInfoVO, enfo.crm.vo.CustomerInfoVO)
	 */
	@Override
	public void modi2(AmCustInfoVO amvo, CustomerInfoVO vo) throws Exception {
		Object[] params = new Object[12];
		params[0] = Utility.parseInt(Utility.trimNull(amvo.getCust_id()),
				new Integer(0));
		params[1] = Utility.trimNull(vo.getPost_address());
		params[2] = Utility.trimNull(vo.getCust_tel());
		params[3] = Utility.trimNull(vo.getPost_address2());
		params[4] = Utility.trimNull(amvo.getCard_type());
		params[5] = Utility.trimNull(amvo.getCard_id());
		params[6] = Utility.trimNull(amvo.getVoc_type());
		params[7] = Utility.parseInt(Utility
				.trimNull(amvo.getCard_valid_date()), new Integer(0));
		params[8] = Utility.trimNull(amvo.getCountry());
		params[9] = Utility.trimNull(amvo.getJg_cust_type());
		params[10] = Utility.parseInt(amvo.getInput_man(), new Integer(0));
        params[11] = Utility.trimNull(vo.getFcName());
		try {
			super.cudProc("{?=call SP_AML_MODI_CUST(?,?,?,?,?,?,?,?,?,?,?,?)}",
					params);
		} catch (Exception e) {
			throw new BusiException("修改客户附加信息失败: " + e.getMessage());
		}
	}
	
	//按年龄来统计 1.购买金额
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryChart(java.lang.Integer, java.lang.String, int, java.lang.String)
	 */
	@Override
	public List queryChart(Integer product_id,String cust_id,int flag,String arrayField)throws Exception{
		List list = new ArrayList();
		String[] x_interval_field = new String[2];
		String sqlChart_1 = "";
		String sqlChart_2 = "";
		String printSql = "";
		String[] valueAll  = Utility.splitString(arrayField,"β");
		if(valueAll.length>0){
			for(int i=0;i<valueAll.length;i++){
				List list2 = null;
				x_interval_field = Utility.splitString(valueAll[i],"γ");
				if(flag==1){//y轴 1为认购金额
					printSql = 
						"SELECT '"+x_interval_field[0]+" 至 "+x_interval_field[1]+"' AS INTERVAL_FIELD, COUNT(A.CUST_ID) AS CUST_NUM,ISNULL(SUM(C.RG_MONEY),0) AS TOTAL_MONEY FROM TCUSTOMERS A LEFT JOIN (SELECT B.CUST_ID,SUM(B.RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT B GROUP BY B.CUST_ID)C ON A.CUST_ID = C.CUST_ID WHERE A.CUST_TYPE =1 " +
						" AND A.CUST_ID IN("+cust_id+")"+
						"AND ISNULL(A.AGE,0) BETWEEN "+x_interval_field[0]+" AND "+x_interval_field[1];			
					list2 = super.listBySql(printSql);
					Map map = (Map)list2.get(0);				
					list.add(map);
				}else if(flag==2){//y轴 2为购买金额
					printSql = 
						"SELECT '"+x_interval_field[0]+" 至 "+x_interval_field[1]+"' AS INTERVAL_FIELD, COUNT(A.CUST_ID) AS CUST_NUM,ISNULL(SUM(C.BEN_AMOUNT),0) AS TOTAL_MONEY FROM TCUSTOMERS A LEFT JOIN (SELECT B.CUST_ID,SUM(B.BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR B GROUP BY B.CUST_ID)C ON A.CUST_ID = C.CUST_ID WHERE A.CUST_TYPE = 1  " +
						" AND A.CUST_ID IN("+cust_id+")"+
						"AND ISNULL(A.AGE,0) BETWEEN "+x_interval_field[0]+" AND "+x_interval_field[1];			
					list2 = super.listBySql(printSql);
					Map map = (Map)list2.get(0);				
					list.add(map);
				}else if(flag==3){ //累计等分
					
				}
			}
		}
		//机构的全部归为其他
		printSql = "SELECT '其他' AS INTERVAL_FIELD, COUNT(A.CUST_ID) AS CUST_NUM,ISNULL(SUM(C.BEN_AMOUNT),0) AS TOTAL_MONEY FROM TCUSTOMERS A LEFT JOIN (SELECT B.CUST_ID,SUM(B.BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR B GROUP BY B.CUST_ID)C ON A.CUST_ID = C.CUST_ID WHERE A.CUST_TYPE = 2  " +
					" AND A.CUST_ID IN("+cust_id+")";
		List list3 = super.listBySql(printSql);
		Map map = (Map)list3.get(0);				
		list.add(map);
		return list;
	}
	
	//由系统来自定规则
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryChart2(java.lang.Integer, java.lang.String, int, int, int, java.lang.Integer)
	 */
	@Override
	public List queryChart2(Integer product_id,String cust_id,int x_flag,int y_flag,int cell_flag,Integer cell_id)throws BusiException{
		List list = new ArrayList();
		String sql = "";
		if(x_flag==1){//x轴上为客户类别
			if(y_flag==1){//y轴上为购买金额
				sql = "SELECT CASE WHEN A.CUST_TYPE=1 THEN '个人'ELSE '机构' END AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,SUM(C.RG_MONEY) AS TOTAL_MONEY " +
				"  FROM TCUSTOMERS A " +
				" LEFT JOIN (SELECT B.CUST_ID,SUM(B.RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT B WHERE (B.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) " +
				" GROUP BY B.CUST_ID)C ON A.CUST_ID = C.CUST_ID " +
				" WHERE A.CUST_ID IN ("+cust_id+") GROUP BY A.CUST_TYPE";
			}else if(y_flag==2){//y轴上为存量金额
				sql = "SELECT CASE WHEN A.CUST_TYPE=1 THEN '个人'ELSE '机构' END AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,SUM(C.BEN_AMOUNT) AS TOTAL_MONEY FROM TCUSTOMERS A " +
				" LEFT JOIN (SELECT B.CUST_ID,SUM(B.BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR B WHERE (B.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) " +
				" GROUP BY B.CUST_ID)C ON A.CUST_ID = C.CUST_ID " +
				" WHERE A.CUST_ID IN ("+cust_id+") GROUP BY A.CUST_TYPE";
			}else if(y_flag==3){//y轴上为累计得分---待定
				sql = "SELECT CASE WHEN A.CUST_TYPE=1 THEN '个人'ELSE '机构' END AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,AVG(C.CURRENT_SOURCE) AS TOTAL_MONEY FROM TCUSTOMERS A " +
				" LEFT JOIN TCustScore C ON A.CUST_ID = C.CUST_ID AND C.END_DATE = 21001231" +
				" WHERE A.CUST_ID IN ("+cust_id+") GROUP BY A.CUST_TYPE";
			}	
		}else if(x_flag==3){//x轴上为风险等级
			if(y_flag==1){//y轴上为购买金额
				sql="SELECT C.RISK_PREF_NAME AS INTERVAL_FIELD,COUNT(C.CUST_ID) AS CUST_NUM,SUM(B.RG_MONEY) AS TOTAL_MONEY FROM TCUSTOMERS C " +
					" LEFT JOIN (SELECT A.CUST_ID,SUM(A.RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT A WHERE (A.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) " +
					" GROUP BY A.CUST_ID)B ON C.CUST_ID = B.CUST_ID " +
					" WHERE C.CUST_ID IN("+cust_id+") GROUP BY C.RISK_PREF,C.RISK_PREF_NAME";
			}if(y_flag==2){//y轴上为存量金额
				sql="SELECT C.RISK_PREF_NAME AS INTERVAL_FIELD,COUNT(C.CUST_ID) AS CUST_NUM,SUM(B.BEN_AMOUNT) AS TOTAL_MONEY FROM TCUSTOMERS C " +
					" LEFT JOIN (SELECT A.CUST_ID,SUM(A.BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR A WHERE (A.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) " +
					" GROUP BY A.CUST_ID)B ON C.CUST_ID = B.CUST_ID " +
					" WHERE C.CUST_ID IN("+cust_id+") GROUP BY C.RISK_PREF,C.RISK_PREF_NAME";
			}
			if(y_flag==3){//y轴上为累计得分---待定
				sql="SELECT C.RISK_PREF_NAME AS INTERVAL_FIELD,COUNT(C.CUST_ID) AS CUST_NUM,AVG(B.CURRENT_SOURCE) AS TOTAL_MONEY FROM TCUSTOMERS C " +
					" LEFT JOIN TCustScore B ON C.CUST_ID = B.CUST_ID  AND B.END_DATE = 21001231" +
					" WHERE C.CUST_ID IN("+cust_id+") GROUP BY C.RISK_PREF,C.RISK_PREF_NAME";
			}
		}else if(x_flag==4){//x轴上为客户性别
			if(y_flag==1){//y轴上为购买金额
				sql="SELECT CASE WHEN ISNULL(A.SEX,0)=0 THEN '未知' WHEN ISNULL(A.SEX,0)=1 THEN '男' WHEN ISNULL(A.SEX,0)=2 THEN '女' " +
					" ELSE '其他' END AS INTERVAL_FIELD ,COUNT(A.CUST_ID) AS CUST_NUM,SUM(C.RG_MONEY) AS TOTAL_MONEY  " +
					" FROM TCUSTOMERS A LEFT JOIN (SELECT B.CUST_ID,SUM(B.RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT B WHERE (B.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) " +
					" GROUP BY B.CUST_ID) C ON A.CUST_ID = C.CUST_ID WHERE  A.CUST_ID IN ("+cust_id+") GROUP BY ISNULL(A.SEX,0)";
			}if(y_flag==2){//y轴上为存量金额
				sql="SELECT CASE WHEN ISNULL(A.SEX,0)=0 THEN '未知' WHEN ISNULL(A.SEX,0)=1 THEN '男'  WHEN ISNULL(A.SEX,0)=2 THEN '女' " +
				" ELSE '其他' END AS INTERVAL_FIELD ,COUNT(A.CUST_ID) AS CUST_NUM,SUM(C.BEN_AMOUNT) AS TOTAL_MONEY  " +
				" FROM TCUSTOMERS A LEFT JOIN (SELECT B.CUST_ID,SUM(B.BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR B WHERE (B.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) " +
				" GROUP BY B.CUST_ID) C ON A.CUST_ID = C.CUST_ID WHERE A.CUST_TYPE = 1 AND A.CUST_ID IN ("+cust_id+") GROUP BY ISNULL(A.SEX,0)";
			}
			if(y_flag==3){//y轴上为累计得分---待定
				sql="SELECT CASE WHEN ISNULL(A.SEX,0)=0 THEN '未知' WHEN ISNULL(A.SEX,0)=1 THEN '男' " +
				" ELSE '女' END AS INTERVAL_FIELD ,COUNT(A.CUST_ID) AS CUST_NUM,AVG(B.CURRENT_SOURCE) AS TOTAL_MONEY  " +
				" FROM TCUSTOMERS A LEFT JOIN TCustScore B ON A.CUST_ID = B.CUST_ID  AND B.END_DATE = 21001231 WHERE A.CUST_TYPE = 1 AND A.CUST_ID IN ("+cust_id+") GROUP BY ISNULL(A.SEX,0)";
			}
		}else if(x_flag==5){//x轴上 --区域
			if(y_flag==1){//y轴上为购买金额
				sql="SELECT ISNULL(A.GOV_PROV_REGIONAL_NAME,'其他') AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,SUM(C.RG_MONEY) AS TOTAL_MONEY " +
					" FROM TCUSTOMERS A LEFT JOIN (SELECT B.CUST_ID,SUM(B.RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT B WHERE (B.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) GROUP BY B.CUST_ID)C " +
					" ON A.CUST_ID = C.CUST_ID WHERE A.CUST_ID IN ("+cust_id+") GROUP BY ISNULL(A.GOV_PROV_REGIONAL,''),GOV_PROV_REGIONAL_NAME";
			}else if(y_flag==2){//y轴上为存量金额
				sql="SELECT ISNULL(A.GOV_PROV_REGIONAL_NAME,'其他') AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,SUM(C.BEN_AMOUNT) AS TOTAL_MONEY " +
				" FROM TCUSTOMERS A LEFT JOIN (SELECT B.CUST_ID,SUM(B.BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR B WHERE (B.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) GROUP BY B.CUST_ID)C " +
				" ON A.CUST_ID = C.CUST_ID WHERE A.CUST_ID IN ("+cust_id+") GROUP BY ISNULL(A.GOV_PROV_REGIONAL,''),GOV_PROV_REGIONAL_NAME";;
			}else if(y_flag==3){//y轴上为累计得分---待定
				sql="SELECT ISNULL(A.GOV_PROV_REGIONAL_NAME,'其他') AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,AVG(B.CURRENT_SOURCE) AS TOTAL_MONEY " +
				" FROM TCUSTOMERS A LEFT JOIN TCustScore B ON A.CUST_ID = B.CUST_ID  AND B.END_DATE = 21001231 WHERE A.CUST_ID IN ("+cust_id+") GROUP BY ISNULL(A.GOV_PROV_REGIONAL,''),GOV_PROV_REGIONAL_NAME";
			}		
		}else if(x_flag==5){//x轴上 --购买金额
				sql="SELECT ISNULL(A.GOV_PROV_REGIONAL_NAME,'其他') AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,SUM(C.RG_MONEY) AS TOTAL_MONEY " +
					" FROM TCUSTOMERS A LEFT JOIN (SELECT B.CUST_ID,SUM(B.RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT B WHERE (B.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) GROUP BY B.CUST_ID)C " +
					" ON A.CUST_ID = C.CUST_ID WHERE A.CUST_ID IN ("+cust_id+") GROUP BY ISNULL(A.GOV_PROV_REGIONAL,''),GOV_PROV_REGIONAL_NAME";
					
		}else if(x_flag==8){ //x轴上 --产品
			if(cell_flag==2){//按产品单元来统计
				if(y_flag==1){//y轴上为购买金额
					sql="SELECT F.PRODUCT_NAME AS INTERVAL_FIELD,COUNT(B.CUST_ID) AS CUST_NUM,ISNULL(SUM(B.RG_MONEY),0.00) AS TOTAL_MONEY"+
						" FROM INTRUST..TBENIFITOR A LEFT JOIN INTRUST..TCONTRACT B ON A.CUST_ID = B.CUST_ID AND A.PRODUCT_ID = B.PRODUCT_ID AND B.CUST_ID IN("+cust_id+"),"+
						" (SELECT C.PRODUCT_ID,C.PRODUCT_NAME FROM INTRUST..TPRODUCT C WHERE EXISTS(SELECT PRODUCT_ID FROM INTRUST.dbo.GETCELLPRODUCTS("+cell_id+") E WHERE C.PRODUCT_ID = E.PRODUCT_ID)) F"+
						" WHERE A.PRODUCT_ID = F.PRODUCT_ID"+						
						" GROUP BY A.PRODUCT_ID,F.PRODUCT_NAME";
				}else if(y_flag==2){//y轴上为存量金额
					sql="SELECT F.PRODUCT_NAME AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,ISNULL(SUM(A.BEN_AMOUNT),0.00) AS TOTAL_MONEY"+
						" FROM INTRUST..TBENIFITOR A,"+
						" (SELECT C.PRODUCT_ID,C.PRODUCT_NAME FROM INTRUST..TPRODUCT C WHERE EXISTS(SELECT PRODUCT_ID FROM INTRUST.dbo.GETCELLPRODUCTS(61) E WHERE C.PRODUCT_ID = E.PRODUCT_ID)) F"+
						" WHERE A.PRODUCT_ID = F.PRODUCT_ID"+
						" AND A.CUST_ID IN("+cust_id+")"+
						" GROUP BY A.PRODUCT_ID,F.PRODUCT_NAME";
				}else if(y_flag==3){//y轴上为累计得分---待定
					sql="SELECT F.PRODUCT_NAME AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,AVG(E.CURRENT_SOURCE) AS TOTAL_MONEY"+
					" FROM INTRUST..TBENIFITOR A LEFT JOIN TCustScore E ON A.CUST_ID = E.CUST_ID  AND E.END_DATE = 21001231 ,"+
					" (SELECT C.PRODUCT_ID,C.PRODUCT_NAME FROM INTRUST..TPRODUCT C WHERE EXISTS(SELECT PRODUCT_ID FROM INTRUST.dbo.GETCELLPRODUCTS(61) E WHERE C.PRODUCT_ID = E.PRODUCT_ID)) F"+
					" WHERE A.PRODUCT_ID = F.PRODUCT_ID"+
					" AND A.CUST_ID IN("+cust_id+")"+
					" GROUP BY A.PRODUCT_ID,F.PRODUCT_NAME";
				}
				
			}else{	
				if(y_flag==1){//y轴上为购买金额
					sql="SELECT B.PRODUCT_NAME AS INTERVAL_FIELD,COUNT(B.CUST_ID) AS CUST_NUM,SUM(B.RG_MONEY) AS TOTAL_MONEY FROM(" +
						" SELECT A.PRODUCT_ID,A.PRODUCT_NAME,A.CUST_ID,SUM(A.RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT A " +
						" WHERE (A.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) AND A.CUST_ID IN("+cust_id+") GROUP BY A.PRODUCT_ID,A.PRODUCT_NAME,A.CUST_ID)B GROUP BY B.PRODUCT_ID,B.PRODUCT_NAME";
				}else if(y_flag==2){//y轴上为存量金额
					sql="SELECT B.PRODUCT_NAME AS INTERVAL_FIELD,COUNT(B.CUST_ID) AS CUST_NUM,SUM(B.BEN_AMOUNT) AS TOTAL_MONEY FROM(" +
					" SELECT A.PRODUCT_ID,A.PRODUCT_NAME,A.CUST_ID,SUM(A.BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR A " +
					" WHERE (A.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) AND A.CUST_ID IN("+cust_id+") GROUP BY A.PRODUCT_ID,A.PRODUCT_NAME,A.CUST_ID)B GROUP BY B.PRODUCT_ID,B.PRODUCT_NAME";
				}else if(y_flag==3){//y轴上为累计得分---待定
					sql="SELECT B.PRODUCT_NAME AS INTERVAL_FIELD,COUNT(B.CUST_ID) AS CUST_NUM,AVG(CURRENT_SOURCE) AS TOTAL_MONEY FROM(" +
					" SELECT A.PRODUCT_ID,A.PRODUCT_NAME,A.CUST_ID,SUM(A.RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT A " +
					" WHERE (A.PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) AND A.CUST_ID IN("+cust_id+") GROUP BY A.PRODUCT_ID,A.PRODUCT_NAME,A.CUST_ID)B"+
					" LEFT JOIN TCustScore E ON B.CUST_ID = E.CUST_ID  AND E.END_DATE = 21001231 GROUP BY B.PRODUCT_ID,B.PRODUCT_NAME";
				}
			}
		}			
		list = super.listBySql(sql);
		return list;
	}
	
	//按购买金额来统计 个数
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryChart3(java.lang.Integer, java.lang.String, int, java.lang.String)
	 */
	@Override
	public List queryChart3(Integer product_id,String cust_id,int flag,String arrayField)throws Exception{
		List list = new ArrayList();
		String[] x_interval_field = new String[2];
		String sqlChart_1 = "";
		String sqlChart_2 = "";
		String printSql = "";
		String[] valueAll  = Utility.splitString(arrayField,"β");
		if(valueAll.length>0){
			for(int i=0;i<valueAll.length;i++){
				List list2 = null;
				x_interval_field = Utility.splitString(valueAll[i],"γ");
				if(flag==6){//x轴 1为认购金额
					printSql = 
						"SELECT '"+x_interval_field[0]+" 至 "+x_interval_field[1]+"' AS INTERVAL_FIELD, COUNT(*) AS CUST_NUM,COUNT(*) AS TOTAL_MONEY FROM INTRUST..TCONTRACT " +
						" WHERE (PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) AND (CUST_ID IN("+cust_id+"))"+
						" AND ISNULL(RG_MONEY,0) BETWEEN "+x_interval_field[0]+" AND "+x_interval_field[1];			
					list2 = super.listBySql(printSql);
					Map map = (Map)list2.get(0);				
					list.add(map);
				}else if(flag==7){//x轴 2为购买金额
					printSql = 
						"SELECT '"+x_interval_field[0]+" 至 "+x_interval_field[1]+"' AS INTERVAL_FIELD, COUNT(*) AS CUST_NUM,COUNT(*) AS TOTAL_MONEY FROM INTRUST..TBENIFITOR " +
						" WHERE (PRODUCT_ID = "+product_id+" OR ISNULL("+product_id+",0)=0) AND (CUST_ID IN("+cust_id+"))"+
						" AND ISNULL(BEN_AMOUNT,0) BETWEEN "+x_interval_field[0]+" AND "+x_interval_field[1];			
					list2 = super.listBySql(printSql);
					Map map = (Map)list2.get(0);				
					list.add(map);
				}else if(flag==9){ //累计等分
					printSql = "SELECT '"+x_interval_field[0]+" 至 "+x_interval_field[1]+"' AS INTERVAL_FIELD,COUNT(A.CUST_ID) AS CUST_NUM,COUNT(*) AS TOTAL_MONEY FROM TCustScore A LEFT JOIN "+
								" (SELECT CUST_ID,SUM(RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT GROUP BY CUST_ID) B ON A.CUST_ID = B.CUST_ID"+
								" WHERE A.CURRENT_SOURCE BETWEEN "+x_interval_field[0]+" AND "+x_interval_field[1]+" AND A.END_DATE = 21001231"+
								" AND A.CUST_ID IN("+cust_id+")";
					list2 = super.listBySql(printSql);
					Map map = (Map)list2.get(0);				
					list.add(map);
				}	 
			}
		}				
		return list;
	}
	
	//由系统来自定规则--曲线
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryChart4(java.lang.Integer, java.lang.String, int, int, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List queryChart4(Integer product_id,String cust_id,int query_flag,int table_flag,Integer start_date,Integer end_date)throws BusiException{
		Object[] params = new Object[6];
		params[0] = cust_id;
		params[1] = start_date;
		params[2] = end_date;
		params[3] = new Integer(query_flag);
		params[4] = product_id;
		params[5] =  new Integer(table_flag);
		return super.listBySql("{call SP_QUERY_TQUARTER_CHART(?,?,?,?,?,?)}", params);
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryImportData(enfo.crm.fileupload.DestinationTableVo, int, int)
	 */
	@Override
	public IPageList queryImportData(DestinationTableVo vo, int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[4];
		params[0] = vo.getSerial_no();
		params[1] = vo.getModule_id();
		params[2] = vo.getStatus();
		params[3] = vo.getInput_man();
		return super.listProcPage("{CALL SP_QUERY_IMPORT_DATA(?, ?, ?, ?)}", params, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#prepareImportData(enfo.crm.fileupload.DestinationTableVo)
	 */
	@Override
	public void prepareImportData(DestinationTableVo vo) throws BusiException{
		Object[] params = new Object[1];
		params[0] = vo.getInput_man();
		super.cudProc("{? = CALL SP_PREPARE_IMPORT_DATA(?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#confirmImportData(enfo.crm.fileupload.DestinationTableVo)
	 */
	@Override
	public void confirmImportData(DestinationTableVo vo) throws BusiException{
		Object[] params = new Object[1];
		params[0] = vo.getInput_man();
		super.cudProc("{? = CALL SP_CONFIRM_IMPORT_DATA(?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#deleteImportData()
	 */
	@Override
	public void deleteImportData() throws BusiException{
		String deleteSql = "delete from import_data where status = 1 or status = 2 or status = -2";
		super.executeSql(deleteSql);
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modiManager(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void modiManager(CustomerVO vo)throws BusiException{
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.parseInt(vo.getService_man(), new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));		              
		super.cudProc("{? = CALL SP_MODI_TCUSTOMERS_SERVICE_MAN(?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#quickSearch(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList quickSearch(CustomerVO vo,int pageIndex,int pageSize)throws BusiException{
		Object[] params = new Object[8];
		params[0] = Utility.trimNull(vo.getCust_name());
		params[1] = Utility.parseInt(vo.getCust_type(),new Integer(0));
		params[2] = Utility.trimNull(vo.getCard_type());
		params[3] = Utility.trimNull(vo.getCard_id());
		params[4] = Utility.trimNull(vo.getCust_tel());
		params[5] = Utility.trimNull(vo.getE_mail());
		params[6] = Utility.trimNull(vo.getProduct_name());
		params[7] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		return super.listProcPage("{call SP_QUERY_QUICKSEARCH_TCUSTOMERS(?,?,?,?,?,?,?,?)}",params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#quickSearchByAll(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List quickSearchByAll(CustomerVO vo)throws BusiException{
		Object[] params = new Object[8];
		params[0] = Utility.trimNull(vo.getCust_name());
		params[1] = Utility.parseInt(vo.getCust_type(),new Integer(0));
		params[2] = Utility.trimNull(vo.getCard_type());
		params[3] = Utility.trimNull(vo.getCard_id());
		params[4] = Utility.trimNull(vo.getCust_tel());
		params[5] = Utility.trimNull(vo.getE_mail());
		params[6] = Utility.trimNull(vo.getProduct_name());
		params[7] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		return super.listBySql("{call SP_QUERY_QUICKSEARCH_TCUSTOMERS(?,?,?,?,?,?,?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listCustUpdateProcPage(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList listCustUpdateProcPage(CustomerVO vo,int pageIndex,int pageSize)throws BusiException{
		Object[] params = new Object[11];
		params[0] = vo.getCust_id();
		params[1] = vo.getCust_no();
		params[2] = vo.getCust_name();
		params[3] = vo.getCard_id();
		params[4] = vo.getCard_type();
		params[5] = vo.getCust_type();
		params[6] = Utility.parseInt(vo.getService_man(),new Integer(0));
		params[7] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		params[8] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		params[9] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[10] = Utility.parseInt(vo.getChange_check_flag(),new Integer(0));
		return super.listProcPage("{call SP_QUERY_TCustomerCHANGE(?,?,?,?,?,?,?,?,?,?,?)}",params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listCustUpdateProcPage(enfo.crm.vo.CustomerVO, java.lang.Integer, int, int)
	 */
	@Override
	public IPageList listCustUpdateProcPage(CustomerVO vo,Integer check_flag,int pageIndex,int pageSize)throws BusiException{
		Object[] params = new Object[11];
		params[0] = vo.getCust_id();
		params[1] = vo.getCust_no();
		params[2] = vo.getCust_name();
		params[3] = vo.getCard_id();
		params[4] = vo.getCard_type();
		params[5] = vo.getCust_type();
		params[6] = Utility.parseInt(vo.getService_man(),new Integer(0));
		params[7] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		params[8] = Utility.parseInt(vo.getEnd_date(),new Integer(0));
		params[9] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		params[10] = Utility.parseInt(check_flag,new Integer(0));
		return super.listProcPage("{call SP_QUERY_TCustomerCHANGE(?,?,?,?,?,?,?,?,?,?,?)}",params,pageIndex,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listByAll(int)
	 */
	@Override
	public String listByAll(int cust_type)throws BusiException{
		String sql = "SELECT CUST_ID,CUST_NAME FROM TCUSTOMERS WHERE CUST_TYPE="+cust_type;
		List list =  super.listBySql(sql);
		String returnValue = "";
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			returnValue += Utility.trimNull(map.get("CUST_NAME"))
							+"_"+Utility.trimNull(map.get("CUST_ID"))+",";
		}
		return returnValue;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listSameNameCustomers(java.lang.String)
	 */
	@Override
	public List listSameNameCustomers(String custName) throws BusiException{
		String sql = "SELECT CUST_ID,CUST_NAME,CARD_TYPE_NAME,CARD_ID FROM TCUSTOMERS WHERE CUST_NAME="+Utility.escapeSqlStr(custName);
		return super.listBySql(sql);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listCustCardInfo(java.lang.Integer)
	 */
	@Override
	public List listCustCardInfo(Integer cust_id) throws BusiException{
		String sql = "SELECT SERIAL_NO,CUST_ID,CARD_TYPE_NAME,VERIFY_DATE,VERIFY_STATE,VERIFY_SUMMARY FROM TCUSTCARDINFO WHERE CUST_ID=?";
		Object[] params = new Object[1];
		params[0] = cust_id;
		return super.listBySql(sql,params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modify3(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void modify3(CustomerVO vo) throws BusiException {
		//根据客户ID更新客户证件信息
		Object[] params = new Object[12];
		params[0] = vo.getCust_id();
		params[1] = vo.getCust_name();
		params[2] = vo.getCard_type();
		params[3] = vo.getCard_id();
		params[4] = vo.getCard_valid_date();
		params[5] = vo.getCard_address();
		params[6] = vo.getBirthday();
		params[7] = Utility.parseInt(vo.getSex(), new Integer(1));
		params[8] = vo.getNation_name();
		params[9] = vo.getIssued_org();
		params[10] = vo.getIssued_date();
		params[11] = vo.getInput_man();
	
		try {
			super.cudProc(modiSql3,params) ;//证件信息保存
			cudProcEdit1(vo);//证件图像保存
            
		} catch (Exception e) {
			throw new BusiException("证件信息保存失败: " + e.getMessage());
		}
		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#updateCheckStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateCheckStatus(String name,String id,String state,String summary)throws BusiException{
		String sql = "UPDATE TCUSTCARDINFO"
						+" SET VERIFY_DATE=dbo.GETDATEINT(GETDATE()),VERIFY_STATE="+state+",VERIFY_SUMMARY="+Utility.escapeSqlStr(summary)
						+" WHERE CARD_CUST_NAME="+Utility.escapeSqlStr(name)+" AND CARD_ID="+Utility.escapeSqlStr(id);
		super.executeSql(sql);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#getCustomerComplaint(java.lang.Object[], int, int)
	 */
	@Override
	public IPageList getCustomerComplaint(Object[] params,int pageIndex,int pageSize)throws BusiException{
		return super.listProcPage(queryCompSql, params, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#updateCustomerComplaint(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void updateCustomerComplaint(CustomerVO vo) throws BusiException{		
		Object[] params = new Object[10];
		params[0] = vo.getSerial_no();
		params[1] = vo.getContent();
		params[2] = vo.getCheck_content();
		params[3] = vo.getReply_type();
		params[4] = vo.getReply_date();
		params[5] = vo.getDo_status();
		params[6] = vo.getForward_type();
		params[7] = vo.getDo_result();
		params[8] = vo.getRelpy_man();
		params[9] = vo.getInput_man();		
		try {
			super.cudProc(updateCompSql,params);//投诉信息保存
		} catch (BusiException e) {
			throw new BusiException("投诉信息保存失败: " + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#addCustomerComplaint(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void addCustomerComplaint(CustomerVO vo) throws BusiException{		
		Object[] params = new Object[5];
		params[0] = vo.getCust_name();
		params[1] = vo.getInput_date();
		params[2] = vo.getComp_type();
		params[3] = vo.getContent();
		params[4] = vo.getInput_man();		
		super.cudProc(addCompSql,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#addEmail(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public void addEmail(Integer cust_id,String Email,String title,String content,Integer input_man)throws BusiException{		
		Object[] params = new Object[6];
		params[0] = cust_id;
		params[1] = new Integer(0);
		params[2] = Email;
		params[3] = title;
		params[4] = content;
		params[5] = input_man;		
		super.cudProc(addEmail,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryEmail(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List queryEmail(Integer cust_id,Integer send_flag)throws BusiException{		
		Object[] params = new Object[7];
		params[0] = cust_id;
		params[1] = new Integer(0);
		params[2] = new Integer(0);
		params[3] = "";
		params[4] = "";
		params[5] = send_flag;
		params[6] = new Integer(0);		
		return super.listBySql(queryEmail,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modiEmail(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void modiEmail(Integer cust_id,Integer input_man)throws BusiException{		
		Object[] params = new Object[2];
		params[0] = cust_id;
		params[1] = input_man;
		super.cudProc(modiEmail1,params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#checkCustMsg(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void checkCustMsg(Integer cust_id,Integer check_flag,Integer input_man)throws BusiException{
		Object[] params = new Object[3];
		params[0] = cust_id;
		params[1] = check_flag;
		params[2] = input_man;
		super.cudProc("{?=call SP_CHECK_TCustomerChanges(?,?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#getDelCustomer(java.lang.Object[], int, int)
	 */
	@Override
	public IPageList getDelCustomer(Object[] params,int pageIndex,int pageSize)throws BusiException{
		return super.listProcPage("{call SP_QUERY_TCustomersDel (?,?,?)}", params, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modiDelCustomer(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean modiDelCustomer(Integer cust_id,Integer input_man)throws BusiException{		
		Object[] params = new Object[2];
		params[0] = cust_id;
		params[1] = input_man;
		super.cudProc("{?=call SP_MODI_TCustomersDel(?,?)}",params);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#queryCustAllInfo(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List queryCustAllInfo(Integer cust_id, Integer flag, Integer input_man)throws BusiException{
		Object[] params = new Object[3];
		params[0] = cust_id;
		params[1] = flag;
		params[2] = input_man;
		return super.listBySql("{call SP_QUERY_TCustomers_AllInfo(?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#merge(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void merge(Integer from_cust_id, Integer to_cust_id, Integer input_man)
		throws BusiException{
		Object[] params = new Object[3];
		params[0] = from_cust_id;
		params[1] = to_cust_id;
		params[2] = input_man;
		super.cudProc("{?=call SP_ADD_TCUSTMERGE(?,?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#checkMerge(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void checkMerge(Integer serial_no, Integer check_flag, Integer input_man)
		throws BusiException{
		Object[] params = new Object[3];
		params[0] = serial_no;
		params[1] = check_flag;
		params[2] = input_man;
		super.cudProc("{?=call SP_CHECK_TCUSTMERGE(?,?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#recoverMerge(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void recoverMerge(Integer serial_no, Integer input_man)
		throws BusiException{
		Object[] params = new Object[2];
		params[0] = serial_no;
		params[1] = input_man;
		super.cudProc("{?=call SP_RECOVER_TCUSTMERGE(?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#listMerge(enfo.crm.vo.CustomerVO, int, int)
	 */
	@Override
	public IPageList listMerge(CustomerVO vo, int pageIndex,int pageSize)throws BusiException{
		Object[] params = new Object[4];
		params[0] = vo.getSerial_no();
		params[1] = vo.getCheck_flag();
		params[2] = vo.getCust_name();
		params[3] = vo.getInput_man();
		return super.listProcPage("{call SP_QUERY_TCUSTMERGE (?,?,?,?)}", params, pageIndex, pageSize);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#updateEntCustInfo(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void updateEntCustInfo(CustomerVO vo)
		throws BusiException{
		Object[] params = new Object[17];
		params[0] = vo.getCust_id();
		params[1] = vo.getBusiness_code();		
		params[2] = vo.getOrgan_code();
		params[3] = vo.getTax_reg_code();		
		params[4] = vo.getLegal_name();
		params[5] = vo.getLegal_card_id();
		params[6] = vo.getAgent_name();
		params[7] = vo.getAgent_card_id();
		params[8] = vo.getPartner_card_id();
		params[9] = vo.getInput_man();		
		params[10] = vo.getManaging_scope();		
		params[11] = vo.getLegal_card_type();
		params[12] = vo.getLegal_card_valid_date();		
		params[13] = vo.getLegal_tel();
		params[14] = vo.getAgent_card_type();
		params[15] = vo.getAgent_tel();
		params[16] = vo.getAgent_card_valid_date();
		super.cudProc("{?=call SP_MODI_TCUSTINFO_ORGAN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params);
	}
	@Override
	public void updateEntCustInfo_m(CustomerVO vo)
		throws BusiException{
		Object[] params = new Object[17];
		params[0] = vo.getCust_id();
		params[1] = vo.getBusiness_code();		
		params[2] = vo.getOrgan_code();
		params[3] = vo.getTax_reg_code();		
		params[4] = vo.getLegal_name();
		params[5] = vo.getLegal_card_id();
		params[6] = vo.getAgent_name();
		params[7] = vo.getAgent_card_id();
		params[8] = vo.getPartner_card_id();
		params[9] = vo.getInput_man();		
		params[10] = vo.getManaging_scope();		
		params[11] = vo.getLegal_card_type();
		params[12] = vo.getLegal_card_valid_date();		
		params[13] = vo.getLegal_tel();
		params[14] = vo.getAgent_card_type();
		params[15] = vo.getAgent_tel();
		params[16] = vo.getAgent_card_valid_date();
		super.cudProc("{?=call SP_MODI_TCUSTINFO_ORGAN_m(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#loadEntCustInfo(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List loadEntCustInfo(CustomerVO vo)
		throws BusiException{
		Object[] params = new Object[2];
		params[0] = vo.getCust_id();;
		params[1] = vo.getInput_man();
		return super.listBySql("{call SP_QUERY_TCUSTINFO_ORGAN_LOAD(?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modiCustTrueFlag(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void modiCustTrueFlag(CustomerVO vo)
		throws BusiException{
		Object[] params = new Object[3];
		params[0] = vo.getCust_id();
		params[1] = vo.getTrue_flag();
		params[2] = vo.getInput_man();		
		super.cudProc("{?=call SP_MODI_CUST_TRUEFLAG(?,?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#batchModiCustInfo(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public void batchModiCustInfo(CustomerVO vo)
		throws BusiException{
		Object[] params = new Object[11];
		params[0] = vo.getCust_id();
		params[1] = vo.getCard_type();
		params[2] = vo.getCard_id();
		params[3] = vo.getE_mail();
		params[4] = vo.getFax();
		params[5] = vo.getMobile();
		params[6] = vo.getO_tel();
		params[7] = vo.getPost_address();
		params[8] = vo.getPost_code();
		params[9] = vo.getService_man();
		params[10] = vo.getInput_man();		
		super.cudProc("{?=call SP_MODI_TCustomers_BAT(?,?,?,?,?,?,?,?,?,?,?)}",params);
	}
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#maintainCustContactInfo(enfo.crm.vo.CustomerVO)
	 */ 
	@Override
	public void maintainCustContactInfo(CustomerVO vo)
		throws BusiException{
		Object[] params = new Object[15];
		params[0] = vo.getCust_id();
		params[1] = vo.getList_id();
		params[2] = vo.getProvince();
		params[3] = vo.getCity();
		params[4] = vo.getDistrict();
		params[5] = vo.getTown_detail();
		params[6] = vo.getPost_code();
		params[7] = vo.getContact_man();
		params[8] = vo.getTel();
		params[9] = vo.getFax();
		params[10] = vo.getTouch_type();
		params[11] = vo.getO_tel();
		params[12] = vo.getH_tel();
		params[13] = vo.getE_mail();
		params[14] = vo.getInput_man();		
		super.cudProc("{?=call SP_MODI_TCUSTADDRESS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#getCustContactInfo(enfo.crm.vo.CustomerVO)
	 */
	@Override
	public List getCustContactInfo(CustomerVO vo) throws BusiException {
		Object[] params = new Object[2];
		params[0] = vo.getCust_id();;
		params[1] = vo.getInput_man();
		return super.listBySql("{call SP_QUERY_TCUSTADDRESS(?,?)}",params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#modiPropertySurvey(enfo.crm.vo.PropertySurveyVO)
	 */
	@Override
	public void modiPropertySurvey(enfo.crm.vo.PropertySurveyVO vo) throws BusiException {
		Object[] params = new Object[24];
		params[0] = vo.getCust_id();
		params[1] = vo.getSurvey_date();
		params[2] = vo.getLink_address();
		params[3] = vo.getLink_phone();
		params[4] = vo.getGr_nation();
		params[5] = vo.getProfession();
		params[6] = vo.getBusiness();
		params[7] = vo.getCapital();
		params[8] = vo.getSwdjhm();
		params[9] = vo.getFr_name();
		params[10] = vo.getFz_card_type();
		params[11] = vo.getFz_card_id();
		params[12] = vo.getFz_card_yxq();
		params[13] = vo.getGd_name();
		params[14] = vo.getGd_card_type();
		params[15] = vo.getGd_card_id();
		params[16] = vo.getGd_card_yxq();
		params[17] = vo.getBl_name();
		params[18] = vo.getBl_card_type();
		params[19] = vo.getBl_card_id();
		params[20] = vo.getBl_card_yxq();
		params[21] = vo.getProperty_source();
		params[22] = vo.getKhfxdj();
		params[23] = vo.getInput_man();
		super.cudProc("{?=call SP_MODI_TPROPERTY_SURVER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.CustomerLocal#querySurveyByCustId(java.lang.Integer)
	 */
	@Override
	public List querySurveyByCustId(Integer cust_id) throws BusiException {
		Object[] params = new Object[1];
		params[0] = cust_id;
		try {
			return super.listBySql("{call SP_QUERY_TPROPERTY_SURVER(0,?)}", params);
		} catch (Exception e) {
			throw new BusiException("根据客户id查询失败: " + e.getMessage());
		}
	}

	/**
	 * 修改隐藏号码
	 * @param vo
	 * @throws BusiException
	 */
	@Override
	public void modiCustYc_Mobile(Integer cust_id) throws BusiException {
		
		Map cust_map = super.mapBySql("select * from TCUSTOMERS where cust_id = ?", new Object[]{
				cust_id
		});
		Integer custmanager_id = Utility.parseInt(Utility.trimNull(cust_map.get("SERVICE_MAN")), new Integer(0));
		
		Map number_map = super.mapBySql("SELECT TOP 1 A.CNO_NUMBER FROM TMANAGERZSHM A,TMANAGERZSHMEXT B WHERE A.SERIAL_NO = B.ZSHM_SERIAL_NO AND B.ManagerID = ?", new Object[]{
				custmanager_id
		});
		
		Integer serial_no = Utility.parseInt(Utility.trimNull(cust_map.get("SERIAL_NO")), new Integer(0));
		String yc_mobile = Utility.trimNull(number_map.get("CNO_NUMBER"));
		
		String updateSql = "update TCUSTOMERS set YC_MOBILE = ? WHERE CUST_ID = ? ";
		
		super.executeSql(updateSql, new Object[]{
				yc_mobile,
				cust_id
		});
		
		super.executeSql("update TMANAGERZSHM set IS_USE = 1 where SERIAL_NO = ?", new Object[]{serial_no});
	}

	/**
	 * 查询客户信息
	 * @param cust_id
	 * @return
	 * @throws BusiException
	 */
	@Override
	public Map queryCustomersByCustId(Integer cust_id) throws BusiException {
		Map map = super.mapBySql("select * from TCUSTOMERS where cust_id = ?", new Object[]{
				cust_id
		});
		return map;
	}

	/**
	 * 变更手机
	 * @param vo
	 * @throws BusiException
	 */
	@Override
	public void modiCustomerMobile(CustomerVO vo) throws BusiException {
		String updateSql = "update TCUSTOMERS set MOBILE = ?,MODI_MAN = ?,MODI_TIME=convert(varchar(10),getdate(),112) WHERE CUST_ID = ?";
		super.executeSql(updateSql, new Object[]{
				Utility.trimNull(vo.getMobile()),
				Utility.parseInt(vo.getInput_man(), new Integer(0)),
				Utility.parseInt(vo.getCust_id(), new Integer(0))
		});
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询客户资料的基本信息——分页显示(多参数)
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @param sQuery
	 * @throws BusiException
	 * @return rsList
	 */
	public IPageList SearchByCustomers(CustomerVO vo,int pageIndex,int pageSize) throws BusiException{
		Object[] params = new Object[3];
		params[0] = Utility.trimNull(vo.getKey_word());
		params[1] = Utility.parseInt(vo.getFlag(),new Integer(0));
		params[2] = Utility.parseInt(vo.getInput_man(),new Integer(0));
		return super.listProcPage("{call SP_QUERY_TCustomers_Search(?,?,?)}",params,pageIndex,pageSize);

	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * @param vo
	 * @param totalValue
	 * @param pageIndex
	 * @param pageSize 
	 * @return
	 * @throws BusiException
	 */
	public IPageList getlistGainCust(CustomerVO vo,String[] totalValue, int pageIndex, int pageSize)
		throws BusiException {
		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[1] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		params[2] = Utility.parseInt(vo.getTask_type(),new Integer(0));
		params[3] = Utility.parseInt(vo.getDate_begin(),new Integer(0));
		params[4] = Utility.parseInt(vo.getDate_end(),new Integer(0));
		params[5] = Utility.parseInt(vo.getInput_man(),new Integer(0)); 
		try {
			return	super.listProcPage(listSqlGain,params,totalValue,pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
	}
	/**
	 * @ejb.interface-method view-type = "local"
	 * 根据手机号码查询
	 * @param vo
	 * @throws BusiException
	 * @return list
	 */
	public List queryCustByMobile(CustomerVO vo) throws BusiException {
		Object[] params = new Object[1];
		params[0] = vo.getMobile();
		try {
			return super.listBySql("{call SP_QUERY_TCustomers_Mobile(?)}", params);
		} catch (Exception e) {
			throw new BusiException("根据电话查询客户失败: " + e.getMessage());
		}
	}
	
}
