package enfo.crm.intrust;  

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustBusiExBean;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustomerInfoVO;

@Component(value="customerInfo")
public class CustomerInfoBean extends IntrustBusiExBean implements CustomerInfoLocal {

	/**
	 * 查询客户信息(多参数)
	 */
	private static final String listSqlExt = "{call SP_QUERY_TCUSTOMERINFO2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	private static final String listRelationSql = "{call SP_QUERY_TCUSTFAMILYINFO(?,?,?)}";
	
	private static final String listCustBankSql = "{call SP_QUERY_TCUSTBANKACCT(?,?,?,?,?,?)}";
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#load(enfo.crm.vo.CustomerInfoVO)
	 */
	@Override
	public List load(CustomerInfoVO vo) throws BusiException {
		List list = null;
		Object[] params = new Object[2];
		params[0] = Utility.parseInt(vo.getCust_id(), 0);
		params[1] = Utility.parseInt(vo.getInput_man(), null);

		try {
			list = super.listBySql("{call SP_QUERY_TCUSTOMERINFO_LOAD(?,?)}",
					params);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#listProcAllExt(enfo.crm.vo.CustomerInfoVO, int, int)
	 */
	@Override
	public IPageList listProcAllExt(CustomerInfoVO vo, int pageIndex,
			int pageSize) throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[35];
		params[0] = Utility.parseInt(vo.getBook_code(), null);
		params[1] = Utility.parseInt(vo.getCust_id(), 0);
		params[2] = Utility.trimNull(vo.getCust_no());
		params[3] = Utility.trimNull(vo.getCust_name());
		params[4] = Utility.trimNull(vo.getCust_source());
		params[5] = Utility.trimNull(vo.getCard_type());
		params[6] = Utility.trimNull(vo.getCard_id());
		params[7] = Utility.trimNull(vo.getTouch_type());
		params[8] = Utility.parseInt(vo.getMin_times(), 0);
		params[9] = Utility.parseInt(vo.getMax_times(), 0);
		params[10] = Utility.parseInt(vo.getInput_man(), null);
		params[11] = Utility.trimNull(vo.getH_tel());
		params[12] = Utility.trimNull(vo.getPost_address());
		params[13] = Utility.parseInt(vo.getCust_type(), 0);
		params[14] = Utility.parseInt(vo.getProduct_id(), 0);
		params[15] = Utility.trimNull(vo.getFamily_name());
		params[16] = Utility.parseInt(vo.getOnlyemail(), 0);
		params[17] = Utility.trimNull(vo.getCust_level());
		params[18] = Utility.parseBigDecimal(vo.getMin_total_money(),
				new BigDecimal(0));
		params[19] = Utility.parseBigDecimal(vo.getMax_total_money(),
				new BigDecimal(0));
		params[20] = Utility.parseInt(vo.getOnly_thisproduct(), 0);
		params[21] = Utility.trimNull(vo.getOrderby());
		params[22] = Utility.parseInt(vo.getBirthday(), new Integer(0));
		params[23] = Utility.parseInt(vo.getPrint_deploy_bill(), 0);
		params[24] = Utility.parseInt(vo.getIs_link(), 0);
		params[25] = Utility.trimNull(vo.getProv_level());
		params[26] = Utility.parseBigDecimal(vo.getBen_amount_min(),
				new BigDecimal(0));
		params[27] = Utility.parseBigDecimal(vo.getBen_amount_max(),
				new BigDecimal(0));
		params[28] = Utility.trimNull(vo.getVip_card_id());
		params[29] = Utility.trimNull(vo.getHgtzr_bh());
		params[30] = Utility.parseInt(vo.getWt_flag(), new Integer(0));
		params[31] = Utility.parseInt(vo.getBirthday_end(), new Integer(0));
		params[32] = Utility.parseInt(vo.getBirthday_month_begin(), 0);
		params[33] = Utility.parseInt(vo.getBirthday_month_end(), 0);
		params[34] = Utility.parseInt(vo.getComplete_flag(), new Integer(0));

		try {
			rsList = super
					.listProcPage(listSqlExt, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#listRelation(enfo.crm.vo.CustomerInfoVO, int, int)
	 */
	@Override
	public IPageList listRelation(CustomerInfoVO vo, int pageIndex,
			int pageSize) throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(vo.getFamily_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getFamily_name());
		params[2] = vo.getInput_man();
		try {
			rsList = super
					.listProcPage(listRelationSql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
		return rsList;
	}
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#appendRela(enfo.crm.vo.CustomerInfoVO)
	 */
    @Override
	public Integer appendRela(CustomerInfoVO vo) throws Exception {
    	Integer family_id = null;
    	Object[] oparams = new Object[4];
        oparams[0] = vo.getCust_id();
        oparams[1] = Utility.parseInt(vo.getFamily_id(), new Integer(0));
        oparams[2] = vo.getFamily_name();
        oparams[3] = vo.getInput_man();
        try {
           family_id = (Integer) super.cudProc(
                    "{?=call SP_ADD_TCUSTFAMILYINFO (?,?,?,?,?)}", oparams, 6,
                    java.sql.Types.INTEGER);
        } catch (Exception e) {
            throw new BusiException("失败:" + e.getMessage());
        }
        return family_id;
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#deleteRela(enfo.crm.vo.CustomerInfoVO)
	 */
    @Override
	public void deleteRela(CustomerInfoVO vo) throws Exception {

        Object[] params = new Object[3];
        params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
        params[1] = Utility.parseInt(vo.getFamily_id(), new Integer(0));
        params[2] = vo.getInput_man();

        try {
            super.cudProc("{?=call SP_DEL_TCUSTFAMILYINFO (?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("家庭信息删除失败: " + e.getMessage());
        }
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#queryByCustNo(enfo.crm.vo.CustomerInfoVO)
	 */
    @Override
	public List queryByCustNo(CustomerInfoVO vo) throws Exception {
    	List list = null;	
        Object[] params = new Object[8];
        params[0] = vo.getBook_code();
        params[1] = vo.getCust_no();
        params[2] = vo.getCard_id();
        params[3] = vo.getVip_card_id();
        params[4] = vo.getHgtzr_bh();
        params[5] = vo.getCust_name();
        params[6] = vo.getIs_link();
        params[7] = vo.getInput_man();
        try {
			list = super.listBySql("{call SP_QUERY_TCUSTOMERINFO_NO (?,?,?,?,?,?,?,?)}",
					params);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
		return list;
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#listRelation1(enfo.crm.vo.CustomerInfoVO)
	 */
    @Override
	public List listRelation1(CustomerInfoVO vo) throws Exception {
    	List list = null;
        Object[] params = new Object[3];
        params[0] = Utility.parseInt(vo.getFamily_id(), new Integer(0));
        params[1] = vo.getFamily_name();
        params[2] = vo.getInput_man();
        try {
			list = super.listBySql("{call SP_QUERY_TCUSTFAMILYINFO1(?,?,?)}",
					params);
		} catch (Exception e) {
			throw new BusiException("失败: " + e.getMessage());
		}
		return list;
    }
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#listCustBank(enfo.crm.vo.CustomerInfoVO, int, int)
	 */
	@Override
	public IPageList listCustBank(CustomerInfoVO vo, int pageIndex,
			int pageSize) throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[7];
		params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
		params[1] = Utility.trimNull(vo.getBank_id());
		params[2] = Utility.trimNull(vo.getCard_id());
		params[3] = Utility.trimNull(vo.getBank_acct());
		params[4] = Utility.trimNull(vo.getBank_name());
		params[5] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[6] = Utility.trimNull("");
		
		try {
			String sql = "{call SP_QUERY_TCUSTBANKACCT(?,?,?,?,?,?,?)}";
			rsList = super.listProcPage(sql, params, pageIndex, pageSize);
		} catch (Exception e) {
			throw new BusiException("查询客户账户信息失败: " + e.getMessage());
		}
		return rsList;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#loadCustBank(enfo.crm.vo.CustomerInfoVO)
	 */
	@Override
	public List loadCustBank(CustomerInfoVO vo) throws BusiException {
		List rsList = null;
		try {
			rsList = super.listBySql("SELECT * FROM TCUSTBANKACCT  WHERE SERIAL_NO = "+ vo.getSerial_no());
		} catch (Exception e) {
			throw new BusiException("查询客户账户信息失败: " + e.getMessage());
		}
		return rsList;
	}
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#deleteCustBank(enfo.crm.vo.CustomerInfoVO)
	 */
    @Override
	public void deleteCustBank(CustomerInfoVO vo) throws Exception {

        Object[] params = new Object[2];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = vo.getInput_man();
        try {
            super.cudProc("{?=call SP_DEL_TCUSTBANKACCT (?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("客户银行账户信息删除失败: " + e.getMessage());
        }
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#addCustBank(enfo.crm.vo.CustomerInfoVO)
	 */
    @Override
	public void addCustBank(CustomerInfoVO vo) throws Exception {

        Object[] params = new Object[8];
        params[0] = Utility.parseInt(vo.getCust_id(), new Integer(0));
        params[1] = Utility.trimNull(vo.getBank_id());
        params[2] = Utility.trimNull(vo.getBank_name());	
        params[3] = Utility.trimNull(vo.getBank_acct());	
        params[4] = Utility.trimNull(vo.getAcct_name());	
        params[5] = Utility.trimNull(vo.getCard_type());	
        params[6] = Utility.trimNull(vo.getCard_id());	
        params[7] = Utility.parseInt(vo.getInput_man(), new Integer(0));

        try {
            super.cudProc("{?=call SP_ADD_TCUSTBANKACCT (?,?,?,?,?,?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("客户银行账户信息新建失败: " + e.getMessage());
        }
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#modiCustBank(enfo.crm.vo.CustomerInfoVO)
	 */
    @Override
	public void modiCustBank(CustomerInfoVO vo) throws Exception {

        Object[] params = new Object[9];
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
        params[2] = Utility.trimNull(vo.getBank_id());
        params[3] = Utility.trimNull(vo.getBank_name());	
        params[4] = Utility.trimNull(vo.getBank_acct());	
        params[5] = Utility.trimNull(vo.getAcct_name());	
        params[6] = Utility.trimNull(vo.getCard_type());	
        params[7] = Utility.trimNull(vo.getCard_id());	
        params[8] = Utility.parseInt(vo.getInput_man(), new Integer(0));	

        try {
            super.cudProc("{?=call SP_MODI_TCUSTBANKACCT (?,?,?,?,?,?,?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("客户银行账户信息修改失败: " + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#listEntCust(enfo.crm.vo.CustomerInfoVO)
	 */
    @Override
	public List listEntCust(CustomerInfoVO vo) throws Exception {
        Object[] params = new Object[10];
        params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
        params[1] = Utility.parseInt(vo.getCust_id(), new Integer(0));
        params[2] = Utility.parseInt(vo.getCust_type(), new Integer(0));
        ;
        params[3] = vo.getCust_name();
        params[4] = vo.getCust_code();
        params[5] = Utility.parseInt(vo.getIs_link(), new Integer(0));
        params[6] = Utility.parseInt(vo.getJt_flag(), new Integer(0));
        params[7] = vo.getCard_code();
        params[8] = vo.getComplete_flag();
        params[9] = vo.getCard_id();
        return super.listBySql("{call SP_QUERY_TENTCUSTINFO (?,?,?,?,?,?,?,?,?,?)}",
                params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.CustomerInfoLocal#loadEntCust(java.lang.Integer)
	 */
    @Override
	public List loadEntCust(Integer cust_id) throws Exception {
        if (cust_id != null && cust_id.intValue() > 0)  {
            CustomerInfoVO vo = new CustomerInfoVO();
            vo.setCust_id(cust_id);
            return this.listEntCust(vo);
        } else {
            return new ArrayList();
        }
    }
    
}