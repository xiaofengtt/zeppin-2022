 
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ContractUnrealVO;
import enfo.crm.vo.ContractVO;

@Component(value="contract")
public class ContractBean extends enfo.crm.dao.IntrustBusiExBean implements ContractLocal {
	
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryPurchanseContract(enfo.crm.vo.ContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryPurchanseContract(			
		ContractVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[16];
		String sql = "{call SP_QUERY_TCONTRACT_COMM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getCust_name();
		params[4] = vo.getCard_id();
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[6] = Utility.parseInt(vo.getPre_flag(), new Integer(0));
		params[7] = vo.getMin_rg_money();
		params[8] = vo.getMax_rg_money();
		params[9] = vo.getProduct_name();
		params[10] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[11] = vo.getContract_sub_bh();
		params[12] = vo.getContract_zjflag();
		params[13] = vo.getCust_type();
		params[14] = vo.getMax_rg_money();
		params[15] = vo.getMin_rg_money();

		rsList =
			super.listProcPage(sql, params, totalColumn, pageIndex, pageSize);
		return rsList;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryPurchanseContract_crm(enfo.crm.vo.ContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList queryPurchanseContract_crm(
		ContractVO vo,
		String[] totalColumn,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[23];
		String sql = "{call SP_QUERY_TCONTRACT_COMM_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getCust_name();
		params[4] = vo.getCard_id();
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[6] = Utility.parseInt(vo.getPre_flag(), new Integer(0));
		params[7] = vo.getMin_rg_money();
		params[8] = vo.getMax_rg_money();
		params[9] = vo.getProduct_name();
		params[10] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[11] = vo.getContract_sub_bh();
		params[12] = vo.getContract_zjflag();
		params[13] = vo.getCust_type();
		params[14] = vo.getMax_rg_money2();
		params[15] = vo.getMin_rg_money2();
		params[16] = vo.getCust_group_id();
		params[17] = vo.getClassdetail_id();	
		params[18] = vo.getChannel_id();
		params[19] = vo.getManagerID();
		params[20] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		params[21] = Utility.parseInt(vo.getTeam_id(), new Integer(0));
		params[22] = vo.getJk_type();
		rsList =
			CrmDBManager.listProcPage(sql, params, totalColumn, pageIndex, pageSize);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryPurchanseContract(enfo.crm.vo.ContractVO)
	 */
	@Override
	public List queryPurchanseContract(ContractVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[16];
		String sql = "{call SP_QUERY_TCONTRACT_COMM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getCust_name();
		params[4] = vo.getCard_id();
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[6] = Utility.parseInt(vo.getPre_flag(), new Integer(0));
		params[7] = vo.getMin_rg_money();
		params[8] = vo.getMax_rg_money();
		params[9] = vo.getProduct_name();
		params[10] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[11] = vo.getContract_sub_bh();
		params[12] = vo.getContract_zjflag();
		params[13] = vo.getCust_type();
		params[14] = vo.getMax_rg_money();
		params[15] = vo.getMin_rg_money();

		rsList = super.listBySql(sql, params);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryPurchanseContract_crm(enfo.crm.vo.ContractVO)
	 */
	@Override
	public List queryPurchanseContract_crm(ContractVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[22];
		String sql = "{call SP_QUERY_TCONTRACT_COMM_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getCust_name();
		params[4] = vo.getCard_id();
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[6] = Utility.parseInt(vo.getPre_flag(), new Integer(0));
		params[7] = vo.getMin_rg_money();
		params[8] = vo.getMax_rg_money();
		params[9] = vo.getProduct_name();
		params[10] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[11] = vo.getContract_sub_bh();
		params[12] = vo.getContract_zjflag();
		params[13] = vo.getCust_type();
		params[14] = vo.getMax_rg_money();
		params[15] = vo.getMin_rg_money();
		params[16] = vo.getCust_group_id();
		params[17] = vo.getClassdetail_id();
		params[18] = vo.getChannel_id();
		params[19] = vo.getManagerID();
		params[20] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
		params[21] = Utility.parseInt(vo.getTeam_id(), new Integer(0));

		rsList = CrmDBManager.listBySql(sql, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#isExistSameContractBH(enfo.crm.vo.ContractVO)
	 */
	@Override
	public Integer isExistSameContractBH(ContractVO vo) throws Exception {
		Object[] oparams = new Object[4];
		String sql = "{?=call SP_REGEDIT_CONTRACT_BH(?,?,?,?,?)}";
		int outParamPos = 9;
		int outParamType = 4;

		oparams[0] = vo.getBook_code();
		oparams[1] = vo.getContract_type();
		oparams[2] = vo.getProduct_id();
		oparams[3] = vo.getContract_sub_bh();

		try {
			Integer exist =
				(Integer) super.cudProc(
					sql,
					oparams,
					6,
					java.sql.Types.INTEGER);
			return exist;
		} catch (Exception e) {
			throw new BusiException("合同编号读取失败:" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#getSameBHContractInfo(enfo.crm.vo.ContractVO)
	 */
	@Override
	public String getSameBHContractInfo(ContractVO vo) throws Exception {
		Object[] oparams = new Object[3];
		String sql = "{call SP_GET_CONTRACT_BH_INFO(?,?,?)}";

		oparams[0] = vo.getContract_type();
		oparams[1] = vo.getProduct_id();
		oparams[2] = vo.getContract_sub_bh();
		try {
			List list =CrmDBManager.listBySql(sql,oparams);
			if(list!=null&&list.size()>0){
				Map map = (Map)list.get(0);
				return Utility.trimNull(map.get("INFO"));
			}
			return "";
		} catch (Exception e) {
			throw new BusiException("合同信息读取失败:" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#appendNoPre(enfo.crm.vo.ContractVO)
	 */
	@Override
	public Object[] appendNoPre(ContractVO vo) throws BusiException {
		String sql =
			"{?=call SP_ADD_TCONTRACT_NOPRE_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[63];
		Object[] result = null;
		int[] outParamPos = new int[] { 23, 24 };
		int[] outParamType = new int[] { Types.INTEGER, Types.VARCHAR };

		params[0] = vo.getBook_code();
		params[1] = vo.getCust_id();
		params[2] = vo.getLink_man();
		params[3] = vo.getProduct_id();
		params[4] = vo.getRg_money();
		params[5] = vo.getJk_type();
		params[6] = vo.getBank_id();
		params[7] = vo.getBank_acct();
		params[8] = vo.getValid_period();
		params[9] = vo.getService_man();

		params[10] = vo.getSummary();
		params[11] = vo.getInput_man();
		params[12] = vo.getContract_bh();
		params[13] = vo.getQs_date();
		params[14] = vo.getJk_date();
		params[15] = vo.getBank_sub_name();
		params[16] = vo.getEntity_price();
		params[17] = vo.getEntity_name();
		params[18] = vo.getEntity_type();
		params[19] = vo.getContract_sub_bh();

		params[20] = vo.getCity_serialno();
		params[21] = new Integer(vo.getSelf_ben_flag());
		params[22] = vo.getTouch_type();
		params[23] = vo.getTouch_type_name();
		params[24] = vo.getGain_acct();
		params[25] = new Integer(vo.getFee_jk_type());
		params[26] = vo.getBank_acct_type();
		params[27] = vo.getStart_date();
		params[28] = vo.getEnd_date();
		params[29] = vo.getBonus_flag();
		params[30] = vo.getSub_product_id();
		params[31] = vo.getWith_bank_flag();
		params[32] = vo.getHt_bank_id();
		params[33] = vo.getHt_bank_sub_name();
		params[34] = vo.getChannel_id();
		params[35] = vo.getWith_secuity_flag();
		params[36] = vo.getWith_private_flag();
		params[37] = vo.getBonus_rate();
		params[38] = vo.getProv_flag();
		params[39] = vo.getProv_level();
		params[40] = vo.getChannel_type();
		params[41] = vo.getChannel_memo();
		params[42] = vo.getChannel_cooperation();
		params[43] = Utility.parseInt(vo.getBzj_flag(), new Integer(2));
		params[44] = Utility.parseBigDecimal(vo.getMarket_trench_money(), new BigDecimal(0));
		
		params[45] = Utility.parseInt(vo.getRecommend_man(),new Integer(0));
		params[46] = Utility.trimNull(vo.getBank_province());
		params[47] = Utility.trimNull(vo.getBank_city());
		params[48] = Utility.parseBigDecimal(vo.getExpect_ror_lower(),new BigDecimal(0.00));
		params[49] = Utility.parseBigDecimal(vo.getExpect_ror_upper(),new BigDecimal(0.00));
		params[50] = Utility.trimNull(vo.getRecommend_man_name());
		params[51] = Utility.trimNull(vo.getIs_ykgl());
		params[52] = Utility.trimNull(vo.getXthtyjsyl());		
		params[53] = Utility.trimNull(vo.getContact_id());
		params[54] = vo.getPeriod_unit();
		params[55] = vo.getHt_cust_name();
		params[56] = vo.getHt_cust_address();
		params[57] = vo.getHt_cust_tel();
		params[58] = vo.getSpot_deal();
		params[59] = vo.getMoney_origin();
		params[60] = vo.getSub_money_origin();
		params[61] = vo.getProperty_source();
		params[62] = vo.getOther_explain();
		
		result = CrmDBManager.cudProc(sql, params, outParamPos, outParamType);

		return result;
	}

	

	 /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryMoenyImport(java.lang.String, java.lang.String[], int, int)
	 */
    @Override
	public IPageList queryMoenyImport(String cust_name ,String[] totalColumn,int pageIndex,int pageSize) throws Exception {
    	
		String sql = "SELECT * FROM TMONEYDATA_IMPORT   WHERE  CUST_NAME  LIKE  "+"'%"+cust_name+"%'";
		return CrmDBManager.listSqlPage(sql,pageIndex,pageSize);   
    }
	 

	
	

	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#append(enfo.crm.vo.ContractVO)
	 */
	@Override
	public Object[] append(ContractVO vo) throws BusiException {
		String sql =
			"{?=call SP_ADD_TCONTRACT_CRM (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[56];
		int[] outParamPos = new int[] { 18, 19 };
		int[] outParamType = new int[] { Types.INTEGER, Types.VARCHAR };

		params[0] = vo.getProduct_id();
		params[1] = vo.getPre_code();
		params[2] = vo.getRg_money();
		params[3] = vo.getJk_type();
		params[4] = vo.getBank_id();
		params[5] = vo.getBank_acct();
		params[6] = vo.getValid_period();
		params[7] = vo.getService_man();
		params[8] = vo.getSummary();
		params[9] = vo.getInput_man();
		params[10] = vo.getCheck_man();
		params[11] = vo.getContract_bh();
		params[12] = vo.getQs_date();
		params[13] = vo.getJk_date();
		params[14] = vo.getBank_sub_name();
		params[15] = vo.getCity_serialno();
		params[16] = new Integer(vo.getSelf_ben_flag());
		params[17] = vo.getTouch_type();
		params[18] = vo.getTouch_type_name();
		params[19] = vo.getGain_acct();
		params[20] = new Integer(vo.getFee_jk_type());
		params[21] = vo.getContract_sub_bh();
		params[22] = vo.getBank_acct_type();
		params[23] = vo.getBonus_flag();
		params[24] = vo.getSub_product_id();
		params[25] = vo.getWith_bank_flag();
		params[26] = vo.getHt_bank_id();
		params[27] = vo.getHt_bank_sub_name();
		params[28] = vo.getChannel_id();
		params[29] = vo.getWith_secuity_flag();
		params[30] = vo.getWith_private_flag();
		params[31] = new BigDecimal(0.00);
		params[32] = vo.getProv_flag();
		params[33] = vo.getProv_level();
		params[34] = vo.getChannel_type();
		params[35] = vo.getChannel_memo();
		params[36] = vo.getChannel_cooperation();
		params[37] = Utility.parseInt(vo.getBzj_flag(), new Integer(2));
		params[38] = Utility.parseBigDecimal(vo.getMarket_trench_money(), new BigDecimal(0));
		
		params[39] = Utility.parseInt(vo.getRecommend_man(),new Integer(0));
		params[40] = Utility.trimNull(vo.getBank_province());
		params[41] = Utility.trimNull(vo.getBank_city());
		params[42] = Utility.parseBigDecimal(vo.getExpect_ror_lower(),new BigDecimal(0.00));
		params[43] = Utility.parseBigDecimal(vo.getExpect_ror_upper(),new BigDecimal(0.00));
		params[44] = null;	
		params[45] = Utility.trimNull(vo.getIs_ykgl());
		params[46] = Utility.trimNull(vo.getXthtyjsyl());
		params[47] = Utility.parseInt(vo.getContact_id(),null);
		params[48] = vo.getPeriod_unit();
		params[49] = vo.getHt_cust_name();
		params[50] = vo.getHt_cust_address();
		params[51] = vo.getHt_cust_tel();
		params[52] = vo.getSpot_deal();
		params[53] = vo.getMoney_origin();
		params[54] = vo.getSub_money_origin();
		params[55] = vo.getCrm_pre_no();
		Object[] result = CrmDBManager.cudProc(sql, params, outParamPos, outParamType);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#save(enfo.crm.vo.ContractVO)
	 */
	@Override
	public void save(ContractVO vo) throws BusiException {
		String sql =
			"{?=call SP_MODI_TCONTRACT_CRM_NOCHECK (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[62];

		params[0] = vo.getSerial_no();
		params[1] = vo.getRg_money();
		params[2] = vo.getJk_type();
		params[3] = vo.getBank_id();
		params[4] = vo.getBank_acct();
		params[5] = vo.getValid_period();
		params[6] = Utility.parseInt(vo.getService_man(), new Integer(0));
		params[7] = vo.getSummary();
		params[8] = vo.getInput_man();
		params[9] = vo.getCheck_man();
		params[10] = vo.getQs_date();
		params[11] = vo.getJk_date();
		params[12] = Utility.parseInt(vo.getLink_man(), new Integer(0));
		params[13] = vo.getProduct_id();
		params[14] = vo.getBank_sub_name();
		params[15] = vo.getContract_bh();
		params[16] = vo.getEntity_price();
		params[17] = vo.getEntity_name();
		params[18] = vo.getEntity_type();
		params[19] = vo.getContract_sub_bh();
		params[20] = Utility.parseInt(vo.getCity_serialno(), new Integer(0));
		params[21] = vo.getTouch_type();
		params[22] = vo.getTouch_type_name();
		params[23] = vo.getGain_acct();
		params[24] = new Integer(vo.getFee_jk_type()); //费用缴款方式：1从本金扣，2另外交,0不交
		params[25] = vo.getBank_acct_type();
		params[26] = vo.getStart_date();
		params[27] = vo.getEnd_date();
		params[28] = vo.getBonus_flag();
		params[29] = vo.getWith_bank_flag();
		params[30] = vo.getHt_bank_id();
		params[31] = vo.getHt_bank_sub_name();
		params[32] = vo.getChannel_id();
		params[33] = vo.getWith_secuity_flag();
		params[34] = vo.getWith_private_flag();
		params[35] = new BigDecimal(0.00);
		params[36] = vo.getProv_flag();
		params[37] = vo.getProv_level();
		params[38] = vo.getChannel_type();
		params[39] = vo.getChannel_memo();
		params[40] = vo.getChannel_cooperation();
		params[41] = Utility.parseInt(vo.getBzj_flag(), new Integer(2));
		params[42] = Utility.parseBigDecimal(vo.getMarket_trench_money(), new BigDecimal(0));
		
		params[43] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		params[44] = Utility.parseInt(vo.getRecommend_man(),new Integer(0));
		params[45] = Utility.trimNull(vo.getBank_province());
		params[46] = Utility.trimNull(vo.getBank_city());
		System.out.println("--vo.getExpect_ror_lower():"+vo.getExpect_ror_lower());		
		params[47] = Utility.parseBigDecimal(vo.getExpect_ror_lower(),new BigDecimal(0.00));
		params[48] = Utility.parseBigDecimal(vo.getExpect_ror_upper(),new BigDecimal(0.00));
		params[49] = Utility.trimNull(vo.getRecommend_man_name());
		params[50] = vo.getIs_ykgl();
		params[51] = vo.getXthtyjsyl();
		params[52] = vo.getContact_id();
		params[53] = vo.getPeriod_unit();
		params[54] = vo.getMoney_origin();
		params[55] = vo.getSub_money_origin();
		params[56] = vo.getHt_cust_name();
		params[57] = vo.getHt_cust_address();
		params[58] = vo.getHt_cust_tel();
		params[59] = vo.getSpot_deal();
		params[60] = vo.getProperty_source();
		params[61] = vo.getOther_explain();
		CrmDBManager.cudProc(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#save_CRM(enfo.crm.vo.ContractVO)
	 */
	@Override
	public void save_CRM(ContractVO vo) throws BusiException {
		String sql =
			"{?=call SP_MODI_TCONTRACT_CRM (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[45];

		params[0] = vo.getSerial_no();
		params[1] = vo.getRg_money();
		params[2] = vo.getJk_type();
		params[3] = vo.getBank_id();
		params[4] = vo.getBank_acct();
		params[5] = vo.getValid_period();
		params[6] = Utility.parseInt(vo.getService_man(), new Integer(0));
		params[7] = vo.getSummary();
		params[8] = vo.getInput_man();
		params[9] = vo.getCheck_man();
		params[10] = vo.getQs_date();
		params[11] = vo.getJk_date();
		params[12] = Utility.parseInt(vo.getLink_man(), new Integer(0));
		params[13] = vo.getSync_benifitor();
		params[14] = vo.getBank_sub_name();
		params[15] = vo.getContract_bh();
		params[16] = vo.getEntity_price();
		params[17] = vo.getEntity_name();
		params[18] = vo.getEntity_type();
		params[19] = vo.getContract_sub_bh();
		params[20] = Utility.parseInt(vo.getCity_serialno(), new Integer(0));
		params[21] = vo.getTouch_type();
		params[22] = vo.getTouch_type_name();
		params[23] = vo.getGain_acct();
		params[24] = new Integer(vo.getFee_jk_type()); //费用缴款方式：1从本金扣，2另外交,0不交
		params[25] = vo.getBank_acct_type();
		params[26] = vo.getStart_date();
		params[27] = vo.getEnd_date();
		params[28] = vo.getBonus_flag();
		params[29] = vo.getWith_bank_flag();
		params[30] = vo.getHt_bank_id();
		params[31] = vo.getHt_bank_sub_name();
		params[32] = vo.getChannel_id();
		params[33] = vo.getWith_secuity_flag();
		params[34] = vo.getWith_private_flag();
		params[35] = new BigDecimal(0.00);
		params[36] = vo.getProv_flag();
		params[37] = vo.getProv_level();
		params[38] = vo.getChannel_type();
		params[39] = vo.getChannel_memo();
		params[40] = vo.getChannel_cooperation();
		params[41] = Utility.parseInt(vo.getBzj_flag(), new Integer(2));
		params[42] = Utility.parseBigDecimal(vo.getMarket_trench_money(), new BigDecimal(0));
		params[43] = vo.getIs_ykgl();
		params[44] = vo.getXthtyjsyl();
//		params[45] = vo.getCust_ids();
		
		CrmDBManager.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#load(enfo.crm.vo.ContractVO)
	 */
	@Override
	public List load(ContractVO vo) throws BusiException {
		String sql = "{call SP_QUERY_TCONTRACT_SERIALNO(?)}";
		List rsList = null;
		Object[] params = new Object[1];
		params[0] = vo.getSerial_no();
		rsList = CrmDBManager.listProcAll(sql, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#delete(enfo.crm.vo.ContractVO)
	 */
	@Override
	public void delete(ContractVO vo) throws BusiException {
		String sql = "{?=call SP_DEL_TCONTRACT (?,?)}";
		Object[] params = new Object[2];

		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();

		super.cudProc(sql, params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#getPrecontract_premoney(enfo.crm.vo.ContractVO)
	 */
	@Override
	public BigDecimal getPrecontract_premoney(ContractVO vo) throws Exception {
		String sql = "{?=call SP_QUERY_TPRECONTRACT_MONEY(?,?,?,?)}";
		BigDecimal premoney = null;
		Object[] oparams = new Object[3];
		int outParamPos = 5;
		int outParamType = Types.DECIMAL;

		oparams[0] = vo.getBook_code();
		oparams[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		oparams[2] = vo.getPre_code();

		premoney =
			(BigDecimal) super.cudProc(sql, oparams, outParamPos, outParamType);
		return premoney;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryContract(enfo.crm.vo.ContractVO)
	 */
	@Override
	public List queryContract(ContractVO vo) throws Exception {
		String sql = "{call SP_QUERY_TCONTRACT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		List rsList = null;
		Object[] params = new Object[14];

		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getProduct_code();
		params[3] = vo.getContract_bh();
		params[4] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[6] = Utility.trimNull(vo.getContract_sub_bh());

		params[7] = Utility.parseInt(vo.getContract_zjflag(), new Integer(0));
        params[8] = null;
        params[9] = null;
        params[10] = null;
        params[11] = null;
        params[12] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));;
        params[13] = new Integer(0);
		
		rsList = super.listProcAll(sql, params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listAll(enfo.crm.vo.ContractVO)
	 */
	@Override
	public List listAll(ContractVO vo) throws Exception {   
		Object[] params = new Object[23];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = vo.getContract_bh();
		params[3] = vo.getCust_name();
		params[4] = vo.getCard_id();
		params[5] = vo.getInput_man();
		params[6] = vo.getOnly_thisproduct();
		params[7] = vo.getCust_type();
		params[8] = vo.getProv_level();
		params[9] = vo.getCust_no();
		params[10] = vo.getMin_rg_money();
		params[11] = vo.getMax_rg_money();
		params[12] = vo.getProduct_name();
		params[13] = vo.getCity_name();
		params[14] = Utility.parseInt(vo.getService_man(), new Integer(0));
		params[15] = vo.getContract_sub_bh();
        
        params[16] = Utility.parseInt(vo.getCell_flag(), new Integer(1));
        params[17] = Utility.parseInt(vo.getCell_id(), new Integer(0));
        params[18] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
        params[19] = Utility.parseInt(vo.getSq_date_start(), new Integer(0));
        params[20] = Utility.parseInt(vo.getSq_date_end(), new Integer(0));
        params[21] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        params[22] = vo.getAdmin_manager();
		return
			super.listBySql(
                    "{call  SP_QUERY_TCONTRACT_ALL (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				params);
	}
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listAll(enfo.crm.vo.ContractVO, int, int)
	 */
    @Override
	public IPageList listAll(ContractVO vo, int page, int pagesize) throws Exception {
        Object[] params = new Object[23];
        params[0] =Utility.parseInt(vo.getBook_code(), new Integer(0));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getContract_bh();
        params[3] = vo.getCust_name();
        params[4] = vo.getCard_id();
        params[5] =Utility.parseInt(vo.getInput_man(),new Integer(0));
        params[6] =Utility.parseInt(vo.getOnly_thisproduct(),new Integer(0));
        params[7] =Utility.parseInt(vo.getCust_type(),new Integer(0));
        params[8] = vo.getProv_level();
        params[9] = vo.getCust_no();
        params[10] = vo.getMin_rg_money();
        params[11] = vo.getMax_rg_money();
        params[12] = vo.getProduct_name();
        params[13] = vo.getCity_name();
        params[14] = Utility.parseInt(vo.getService_man(), new Integer(0));
        params[15] = vo.getContract_sub_bh();
        params[16] = Utility.parseInt(vo.getCell_flag(), new Integer(1));
        params[17] = Utility.parseInt(vo.getCell_id(), new Integer(0));
        params[18] = Utility.parseInt(vo.getDepart_id(), new Integer(0));
        params[19] = Utility.parseInt(vo.getSq_date_start(), new Integer(0));
        params[20] = Utility.parseInt(vo.getSq_date_end(), new Integer(0));
        params[21] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        params[22] = vo.getAdmin_manager();
        return super.listProcPage(
                "{call  SP_QUERY_TCONTRACT_ALL (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                params, page, pagesize);
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listAll(enfo.crm.vo.ContractVO, java.lang.String[], int, int)
	 */	
	@Override
	public IPageList listAll(ContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws BusiException{
		IPageList pageList = null;
		Object[] params = new Object[10];
		String sql = "{call SP_QUERY_TCONTRACT_ENTITY(?,?,?,?,?,?,?,?,?,?)}";
		
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = vo.getProduct_code();
        params[3] = vo.getContract_bh();
        params[4] = Utility.parseInt(vo.getCheck_flag(), new Integer(0));
        params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        params[6] = vo.getEntity_type();
        params[7] = vo.getCust_name();
        params[8] = Utility.parseInt(vo.getPz_flag(), new Integer(0));
        params[9] = vo.getContract_sub_bh();
		
		pageList = super.listProcPage(sql,params,totalColumn,pageIndex,pageSize);		
		return pageList;
	}

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listHistory(java.lang.Integer)
	 */
    @Override
	public List listHistory(Integer serial_no) throws Exception {
        Object[] params = new Object[1];
        params[0] = serial_no;
        // 合同状态变动表
        return super.listBySql("{call  SP_QUERY_TCONTRACTLIST (?)}", params);
    }
   
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryContractByCustID(enfo.crm.vo.ContractVO)
	 */
	@Override
	public List queryContractByCustID(ContractVO vo) throws Exception {
		List list = new ArrayList();
		Object[] params = new Object[3];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
		params[2] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		list =
			super.listBySql(
				"{call SP_QUERY_TCONTRACT_BYCUSTID(?,?,?)}",
				params);
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#getProductNameByCustId(java.lang.Integer)
	 */
	@Override
	public String getProductNameByCustId(Integer cust_id) throws Exception {
		String product_name = "";
		String printSql =
			"SELECT DISTINCT B.* FROM TBENIFITOR A,TCONTRACT B WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.CONTRACT_BH = B.CONTRACT_BH AND A.CUST_ID ="
				+ cust_id;
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery(printSql);
		try {
			while (rslist.next()) {
				product_name = rslist.getObject("PRODUCT_NAME").toString();
			}
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
			return product_name;
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#getCheckContract(enfo.crm.vo.ContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList getCheckContract(ContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws Exception{
		
		Object[] params = new Object[15];
		params[0] = vo.getBook_code();
		params[1] = vo.getProduct_id();
		params[2] = vo.getProduct_code();
		params[3] = vo.getContract_bh();
		params[4] = vo.getCheck_flag();
		params[5] = vo.getInput_man();
		params[6] = vo.getContract_sub_bh();
		params[7] = vo.getZjFlag();
		params[8] = vo.getProduct_name();
		params[9] = vo.getCust_type();
		params[10] = vo.getMax_rg_money();
		params[11] = vo.getMin_rg_money();
		params[12] = vo.getSub_product_id();
		params[13] = vo.getIntrustFlag1();
		params[14] = vo.getCust_name();
		
		try {
			return CrmDBManager.listProcPage("{call SP_QUERY_TCONTRACT_CRM (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",params,totalColumn,pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询认购合同失败" +e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#contractCheck(enfo.crm.vo.ContractVO)
	 */
	@Override
	public void contractCheck(ContractVO vo) throws Exception
	{

		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();

		try 
		{
			super.cudProc("{?=call SP_CHECK_TCONTRACT (?,?)}",params);
		} 
		catch (Exception e) 
		{
			throw new BusiException("认购合同审核失败！ " + e.getMessage());
		}

	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#contractCheck_aftercheck(enfo.crm.vo.ContractVO)
	 */
	@Override
	public void contractCheck_aftercheck(ContractVO vo) throws Exception
	{

		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();

		try 
		{
			CrmDBManager.cudProc("{?=call SP_CHECK_TCONTRACT_AFTERCHECK_CRM (?,?)}",params);
		} 
		catch (Exception e) 
		{
			throw new BusiException("认购合同审核失败！ " + e.getMessage());
		}

	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#getRecheckContract(enfo.crm.vo.ContractVO, java.lang.String[], int, int)
	 */
	@Override
	public IPageList getRecheckContract(ContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws Exception {

		Object[] params = new Object[6];
		params[0] = vo.getBook_code();
		params[1] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[2] = "";
		params[3] = vo.getContract_bh();
		params[4] = vo.getInput_man();
		params[5] = vo.getContract_sub_bh();
	
		try {
			return super.listProcPage("{call SP_QUERY_TCONTRACT_BACK(?,?,?,?,?,?)}",params,totalColumn,pageIndex,pageSize);
		} catch (BusiException e) {
			throw new BusiException("查询可恢复认购合同失败："+e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#recheckContract(enfo.crm.vo.ContractVO)
	 */
	@Override
	public void recheckContract(ContractVO vo) throws Exception
	{

		Object[] params = new Object[2];
		params[0] = vo.getSerial_no();
		params[1] = vo.getInput_man();

		try 
		{
			super.cudProc("{?=call SP_CHECK_TCONTRACT_BACK(?,?)}",params);
		} 
		catch (Exception e) 
		{
			throw new BusiException("认购合同审核恢复失败！: " + e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#getToBankDate(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public Integer getToBankDate(Integer cust_id,Integer product_id,String contract_bh) throws BusiException{
		Integer toBankDate = new Integer(0);
		String listSql =  
				"SELECT ISNULL(TO_BANK_DATE,0) FROM TMONEYDETAIL  WHERE CUST_ID = "+ cust_id+" AND PRODUCT_ID =" + product_id + "AND CONTRACT_BH = '" + contract_bh+"'";
		
		List rsList = IntrustDBManager.listBySql(listSql);
		
		if(rsList!=null&&rsList.size()>0){
			HashMap rsMap = (HashMap) rsList.get(0);
			
			if(rsMap!=null){
				toBankDate = Utility.parseInt(Utility.trimNull(rsMap.get("TO_BANK_DATE")), new Integer(0));
			}
		}
		
		return toBankDate;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryOldTemp(enfo.crm.vo.ContractVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList queryOldTemp(ContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws Exception {
        Object[] params = new Object[3];
        params[0] = vo.getProduct_name();
        params[1] = vo.getContract_bh();
        params[2] = vo.getCust_name();

        return CrmDBManager.listProcPage("{call SP_QUERY_OLD(?,?,?)}", params,totalColumn,pageIndex,pageSize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryOldBenifitor(enfo.crm.vo.ContractVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList queryOldBenifitor(ContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws Exception {
        Object[] params = new Object[3];
        params[0] = vo.getProduct_name();
        params[1] = vo.getContract_bh();
        params[2] = vo.getCust_name();
        return CrmDBManager.listProcPage("{call SP_QUERY_OLD_BENIFITOR(?,?,?)}", params,totalColumn,pageIndex,pageSize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryZhejinTemp(enfo.crm.vo.ContractVO, java.lang.String[], int, int)
	 */
    @Override
	public IPageList queryZhejinTemp(ContractVO vo,String[] totalColumn,int pageIndex,int pageSize) throws Exception {
        Object[] params = new Object[2];
        params[0] = vo.getProduct_name();
        params[1] = vo.getCust_name();
        return CrmDBManager.listProcPage("{call SP_QUERY_TCONTRACTIMPORT(?,?)}", params,totalColumn,pageIndex,pageSize);
    }
    
    //hesl 2011.4.29
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#checkEntity(enfo.crm.vo.ContractVO)
	 */
    @Override
	public void checkEntity(ContractVO vo) throws BusiException {

        Object[] params = new Object[2];
        params[0] = vo.getSerial_no();
        params[1] = vo.getInput_man();

        try {
            super.update("{?=call SP_CHECK_TCONTRACT_ENTIRY (?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("审核失败！: " + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#appendContractUnreal(enfo.crm.vo.ContractUnrealVO)
	 */
    @Override
	public void appendContractUnreal(ContractUnrealVO vo) throws BusiException{        
        Object[] params = new Object[20];
        params[0] = vo.getPreproductId();
        params[1] = vo.getCustId();
        params[2] = vo.getContractSubBh();
        params[3] = vo.getProvFlag();
        params[4] = vo.getProvLevel();
        params[5] = vo.getQsDate();
        params[6] = vo.getRgMoney();
        params[7] = vo.getJkType();
        params[8] = vo.getJkDate();
        params[9] = vo.getBankId();
        params[10] = vo.getBankSubName();
        params[11] = vo.getBankAcct();
        params[12] = vo.getBankAcctType();
        params[13] = vo.getGainAcct();
        params[14] = vo.getServiceMan();
        params[15] = vo.getExpectRorLower();
        params[16] = vo.getExpectRorUpper();
        params[17] = vo.getInputMan();
        params[18] = Utility.trimNull(vo.getSummary()).trim();
        params[19] = null;
        CrmDBManager.cudProc("{?=call SP_ADD_TCONTRACTUNREAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);    
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#updateContractUnreal(enfo.crm.vo.ContractUnrealVO)
	 */
    @Override
	public void updateContractUnreal(ContractUnrealVO vo) throws BusiException{        
        Object[] params = new Object[20];
        params[0] = vo.getSerialNo();
        params[1] = vo.getPreproductId();
        params[2] = vo.getCustId();
        params[3] = vo.getContractSubBh();
        params[4] = vo.getProvFlag();
        params[5] = vo.getProvLevel();
        params[6] = vo.getQsDate();
        params[7] = vo.getRgMoney();
        params[8] = vo.getJkType();
        params[9] = vo.getJkDate();
        params[10] = vo.getBankId();
        params[11] = vo.getBankSubName();
        params[12] = vo.getBankAcct();
        params[13] = vo.getBankAcctType();
        params[14] = vo.getGainAcct();
        params[15] = vo.getServiceMan();
        params[16] = vo.getExpectRorLower();
        params[17] = vo.getExpectRorUpper();
        params[18] = vo.getInputMan();
        params[19] = Utility.trimNull(vo.getSummary()).trim();
        CrmDBManager.cudProc("{?=call SP_MODI_TCONTRACTUNREAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);    
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#deleteContractUnreal(enfo.crm.vo.ContractUnrealVO)
	 */
    @Override
	public void deleteContractUnreal(ContractUnrealVO vo) throws BusiException{        
        Object[] params = new Object[2];
        params[0] = vo.getSerialNo();
        params[1] = vo.getInputMan();
        CrmDBManager.cudProc("{?=call SP_DEL_TCONTRACTUNREAL(?,?)}", params);    
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#convertContractUnreal(enfo.crm.vo.ContractUnrealVO)
	 */
    @Override
	public void convertContractUnreal(ContractUnrealVO vo) throws BusiException{        
        Object[] params = new Object[2];
        params[0] = vo.getPreproductId();
        params[1] = vo.getInputMan();
        CrmDBManager.cudProc("{?=call SP_CONVERT_TCONTRACTUNREAL(?,?)}", params);    
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryContractUnreal(enfo.crm.vo.ContractUnrealVO, int, int)
	 */
    @Override
	public IPageList queryContractUnreal(ContractUnrealVO vo, int pageNo, int pageSize) throws BusiException{        
        Object[] params = new Object[10];
        params[0] = vo.getSerialNo();
        params[1] = vo.getPreproductId();
        params[2] = vo.getPreproductName();
        params[3] = vo.getCustId();
        params[4] = vo.getCustName();
        params[5] = vo.getContractSubBh();
        params[6] = vo.getQsDate1();
        params[7] = vo.getQsDate2();
        params[8] = vo.getStatus();
        params[9] = vo.getInputMan();        
        return CrmDBManager.listProcPage("{call SP_QUERY_TCONTRACTUNREAL(?,?,?,?,?,?,?,?,?,?)}", params, pageNo, pageSize);    
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#loadContractUnreal(enfo.crm.vo.ContractUnrealVO)
	 */
    @Override
	public Map loadContractUnreal(ContractUnrealVO vo) throws BusiException{        
        Object[] params = new Object[10];
        params[0] = vo.getSerialNo();
        params[1] = vo.getPreproductId();
        params[2] = vo.getPreproductName();
        params[3] = vo.getCustId();
        params[4] = vo.getCustName();
        params[5] = vo.getContractSubBh();
        params[6] = vo.getQsDate1();
        params[7] = vo.getQsDate2();
        params[8] = vo.getStatus();
        params[9] = vo.getInputMan();        
        List list = CrmDBManager.listBySql("{call SP_QUERY_TCONTRACTUNREAL(?,?,?,?,?,?,?,?,?,?)}", params);    
        if (list.size()>0) {
            return (Map)list.get(0);
        } else {
        	return null;
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryOldTemp(java.lang.String, java.lang.String, java.lang.String, int, int, int)
	 */
    @Override
	public IPageList queryOldTemp(String product_name, String contract_bh,
            String cust_name, int dealFlag, int page, int pagesize) throws Exception {

        Object[] params = new Object[3];
        params[0] = product_name;
        params[1] = contract_bh;
        params[2] = cust_name;

        String querySQL = "{call SP_QUERY_OLD(?,?,?)}";
        if (dealFlag == 2) {
            querySQL = "{call SP_QUERY_OLD_CHANGE(?,?,?)}";
        }
        return super.listProcPage(querySQL, params, page, pagesize);
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryAllContractMessage(enfo.crm.vo.ContractVO, java.lang.Integer)
	 */
	@Override
	public List queryAllContractMessage(ContractVO vo, Integer flag) throws Exception {
		List list = new ArrayList();
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[1] = Utility.parseInt(vo.getStart_date(),new Integer(0));
		params[2] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		params[3] = Utility.parseInt(vo.getProduct_id(),new Integer(0));
		params[4] = Utility.parseInt(flag,1);
		list =
			super.listBySql(
				"{call SP_IMPORT_CONTRACT_MESSAGE(?,?,?,?,?)}",
				params);
		return list;
	}
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#statManagerSales(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
    @Override
	public List statManagerSales(Integer productId, Integer startDate, Integer endDate,
            Integer servMan, Integer inputMan) throws BusiException {
        Object[] params = new Object[5];
        params[0] = productId;
        params[1] = startDate;
        params[2] = endDate;
        params[3] = servMan;
        params[4] = inputMan;
        return CrmDBManager.listBySql("{call SP_STAT_SERVICEMANSELLS(?,?,?,?,?)}",
                    params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#statCustContract(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
    @Override
	public List statCustContract(Integer custId, Integer flag, Integer productId, Integer servMan) 
        throws BusiException {
        Object[] params = new Object[4];
        params[0] = custId;
        params[1] = flag;
        params[2] = productId;
        params[3] = servMan;
        return CrmDBManager.listBySql("{call SP_QUERY_TCONTRACT_CUST(?,?,?,?)}",
                    params);
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#statLess300Contract(java.lang.Integer)
	 */
    @Override
	public List statLess300Contract(Integer productId)  throws BusiException {
	    String sql = "SELECT COUNT(0) RG_STAT FROM TCONTRACT A WHERE RG_MONEY<3000000 AND EXISTS (SELECT 1 FROM TCUSTOMERINFO WHERE CUST_ID=A.CUST_ID AND CUST_TYPE=1) AND PRODUCT_ID=? OR (0=?)";
    	Object[] params = new Object[2];
	    params[0] = productId;
	    params[1] = productId;
	    return super.listBySql(sql,params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#queryEndContract(enfo.crm.vo.ContractVO, int, int)
	 */
    @Override
	public IPageList queryEndContract(ContractVO vo, int page, int pagesize) 
        throws Exception {
        Object[] params = new Object[8];
        params[0] = vo.getBook_code();
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = "";
        params[3] = Utility.parseInt(vo.getStart_date(), new Integer(0));
        params[4] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
        params[5] = vo.getInput_man();
        params[6] = vo.getContract_sub_bh();
        params[7] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        return super.listProcPage("{call SP_QUERY_TCONTRACT_END (?,?,?,?,?,?,?,?)}", 
                params, page, pagesize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listContractList(enfo.crm.vo.ContractVO, int, int)
	 */
    @Override
	public IPageList listContractList(ContractVO vo, int page, int pagesize) 
            throws Exception {
        Object[] params = new Object[6];
        params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[1] = vo.getContract_bh();
        params[2] = vo.getHt_status();
        params[3] = Utility.parseInt(vo.getList_id(), new Integer(0));
        params[4] = vo.getContract_sub_bh();
        params[5] = vo.getSub_product_id();
        return super.listProcPage("{call SP_QUERY_TCONTRACTLIST_DETAIL(?,?,?,?,?,?)}",
                        params, page, pagesize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listContractList(enfo.crm.vo.ContractVO)
	 */
    @Override
	public List listContractList(ContractVO vo) 
            throws Exception {
        Object[] params = new Object[6];
        params[0] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[1] = vo.getContract_bh();
        params[2] = vo.getHt_status();
        params[3] = Utility.parseInt(vo.getList_id(), new Integer(0));
        params[4] = vo.getContract_sub_bh();
        params[5] = vo.getSub_product_id();
        return super.listBySql("{call SP_QUERY_TCONTRACTLIST_DETAIL(?,?,?,?,?,?)}",
                        params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listContractFor2ndEdit(enfo.crm.vo.ContractVO, int, int)
	 */
    @Override
	public IPageList listContractFor2ndEdit(ContractVO vo, int page, int pagesize) 
            throws Exception {
        Object[] params = new Object[9];
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
        params[1] = Utility.parseInt(vo.getContract_type(), new Integer(0));
        params[2] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[3] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[4] = vo.getProduct_code();
        params[5] = vo.getProduct_name();
        params[6] = vo.getContract_sub_bh();
        params[7] = vo.getCust_name();
        params[8] = vo.getInput_man();
        return CrmDBManager.listProcPage("{call SP_QUERY_CONTRACT_FOR_2ND_EDIT(?,?,?,?,?,?,?,?,?)}",
                        params, page, pagesize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listContractFor2ndEdit(enfo.crm.vo.ContractVO)
	 */
    @Override
	public List listContractFor2ndEdit(ContractVO vo) 
            throws Exception {
        Object[] params = new Object[9];
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(1));
        params[1] = Utility.parseInt(vo.getContract_type(), new Integer(0));
        params[2] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[3] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[4] = vo.getProduct_code();
        params[5] = vo.getProduct_name();
        params[6] = vo.getContract_sub_bh();
        params[7] = vo.getCust_name();
        params[8] = vo.getInput_man();
        return CrmDBManager.listBySql("{call SP_QUERY_CONTRACT_FOR_2ND_EDIT(?,?,?,?,?,?,?,?,?)}", params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#modiContractFor2ndEdit(enfo.crm.vo.ContractVO)
	 */
    @Override
	public void modiContractFor2ndEdit(ContractVO vo) 
            throws Exception {
        Object[] params = new Object[15];
        params[0] = Utility.parseInt(vo.getContract_type(), new Integer(0));
        params[1] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[2] = Utility.parseInt(vo.getProv_flag(), new Integer(0));
        params[3] = Utility.trimNull(vo.getProv_level()).trim();
        params[4] = Utility.trimNull(vo.getChannel_cooperation()).trim();
        params[5] = Utility.parseInt(vo.getWith_bank_flag(), new Integer(0));
        params[6] = Utility.trimNull(vo.getHt_bank_id()).trim();
        params[7] = Utility.trimNull(vo.getHt_bank_sub_name()).trim();
        params[8] = Utility.parseInt(vo.getWith_secuity_flag(), new Integer(0));
        params[9] = Utility.parseInt(vo.getWith_private_flag(), new Integer(0));
        params[10] = vo.getCity_serialno();
        params[11] = Utility.trimNull(vo.getJg_wtrlx2()).trim();        
        params[12] = vo.getValid_period();
        params[13] = vo.getPeriod_unit();
        params[14] = vo.getInput_man();
        CrmDBManager.cudProc("{ ? = call SP_MODI_CONTRACT_FOR_2ND_EDIT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#updateOld(java.lang.String)
	 */
    @Override
	public void updateOld(String sql) 
            throws Exception {
        super.executeSql(sql);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#loadContractModi(enfo.crm.vo.ContractVO)
	 */
	@Override
	public List loadContractModi(ContractVO vo) throws BusiException {
		Object[] params = new Object[1];
		params[0] = vo.getSerial_no();
		return CrmDBManager.listProcAll("{call SP_QUERY_TCONTRACTMODI_LOAD(?)}", params);
	}
	
	 /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#checkContractModi(enfo.crm.vo.ContractVO)
	 */
	@Override
	public void checkContractModi(ContractVO vo) throws BusiException {
		Object[] params = new Object[4];
		params[0] = vo.getSerial_no();
		params[1] = vo.getCheck_flag();
		params[2] = vo.getSummary();
		params[3] = vo.getInput_man();
		CrmDBManager.cudProc("{ ? = call SP_CHECK_TCONTRACT_AFTERCHECK_CRM(?,?,?,?)}", params);
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#modiContractModi(enfo.crm.vo.ContractVO)
	 */
    @Override
	public void modiContractModi(ContractVO vo) 
            throws Exception {
        Object[] params = new Object[22];
        params[0] = vo.getSerial_no();
        params[1] = vo.getContract_sub_bh();
        params[2] = vo.getQs_date();
        params[3] = vo.getJk_date();
        params[4] = vo.getJk_type();
        params[5] = vo.getBank_id();
        params[6] = vo.getBank_sub_name();
        params[7] = vo.getBank_acct();
        params[8] = vo.getGain_acct();
        params[9] = vo.getBonus_flag();
        params[10] = vo.getBonus_rate();
        params[11] = vo.getProv_flag();   
        params[12] = vo.getProv_level();
        params[13] = vo.getLink_man();
        params[14] = vo.getChannel_id();
        params[15] = vo.getChannel_type();
        params[16] = vo.getChannel_memo();
        params[17] = vo.getChannel_cooperation();
        params[18] = vo.getMarket_trench_money();
		params[19] = vo.getSummary();
        params[20] = vo.getInput_man();
        params[21] = vo.getCust_ids();
        CrmDBManager.cudProc("{ ? = call SP_MODI_TCONTRACTMODI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);
    }
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ContractLocal#listContractForPurchaseImportChange(enfo.crm.vo.ContractVO)
	 */
	@Override
	public List listContractForPurchaseImportChange(ContractVO vo) throws BusiException {
		Object[] params = new Object[3];
		params[0] = vo.getProduct_id();
		params[1] = vo.getContract_sub_bh();
		params[2] = vo.getInput_man();
		return CrmDBManager.listProcAll("{call SP_QUERY_TCONTRACT_EXPORT(?,?,?)}", params);
	}
}