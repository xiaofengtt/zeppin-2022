package enfo.crm.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import enfo.crm.vo.CustIntegralVO;
import enfo.crm.vo.IntegralCalVO;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.marketing.CustIntegralLocal;
import enfo.crm.marketing.IntegralCalLocal;
import enfo.crm.tools.Utility;
import enfo.crm.tools.EJBFactory;

public class IntegralService {
	/**
	 * 获取认购合同数量
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	public int getContractCount(IntegralCalVO vo) throws BusiException{
		String listSql = "SELECT * FROM TCONTRACT " +
						"WHERE HT_STATUS = '120101' " +
						"AND BOOK_CODE="+ vo.getBookCode() +
						"AND CUST_ID=" + vo.getCust_id() +
						"AND QS_DATE<=" + vo.getQsDateUpper() +
						"AND QS_DATE>=" + vo.getQsDateLower();
		return IntrustDBManager.listSqlCount(listSql);
	}
	/**
	 * 获取认购合同数量
	 * @param cust_id
	 * @param bookCode
	 * @param ruleID
	 * @return
	 * @throws BusiException
	 */
	public String[] getContractInfo(Integer serial_no ,Integer ruleID) throws BusiException{		
		String listSql = "SELECT * FROM IM_RULE WHERE RULE_ID = " + ruleID;
		String listSql2 = "SELECT * FROM TCONTRACT WHERE SERIAL_NO = " + serial_no;
		List rsList = CrmDBManager.listBySql(listSql);
		List rsList2 = IntrustDBManager.listBySql(listSql2);
		
		Integer cust_id = new Integer(0);
		Integer bookCode = new Integer(0);
		Integer endDate = new Integer(0);
		Integer begDate = new Integer(0);
		Integer product_id = new Integer(0);
		BigDecimal amount = new BigDecimal(0);
		int ret = 0;
		int flag = 0;
		String[] reslut = new String[4];
		
		if(rsList!=null&&rsList.size()>0){			
			HashMap map = (HashMap) rsList.get(0);			
			endDate = Utility.parseInt(Utility.trimNull(map.get("END_DATE")), new Integer(0));
			begDate = Utility.parseInt(Utility.trimNull(map.get("BEG_DATE")), new Integer(0));
			flag++;
		}
		
		if(rsList2!=null&&rsList2.size()>0){
			HashMap map2 = (HashMap) rsList2.get(0);
			bookCode = Utility.parseInt(Utility.trimNull(map2.get("BOOK_CODE")), new Integer(0));
			cust_id =  Utility.parseInt(Utility.trimNull(map2.get("CUST_ID")), new Integer(0));
			amount = Utility.parseDecimal(Utility.trimNull(map2.get("RG_MONEY")), new BigDecimal(0));
			product_id =  Utility.parseInt(Utility.trimNull(map2.get("PRODUCT_ID")), new Integer(0));
			
			reslut[0] = amount.toString();
			reslut[2] = cust_id.toString();
			reslut[3] = product_id.toString();
			flag++;
		}
		
		if(flag==2){
			IntegralCalVO vo = new IntegralCalVO();
			
			vo.setBookCode(bookCode);
			vo.setCust_id(cust_id);
			vo.setQsDateUpper(endDate);
			vo.setQsDateLower(begDate);
			
			ret = this.getContractCount(vo);
			reslut[1] = Utility.trimNull(new Integer(ret));
		}
		return reslut;
	}
	
	/**
	 * 计算
	 * @param vo
	 * @throws Exception
	 */
	public void integralCal(IntegralCalVO vo,CustIntegralVO cust_vo,int flag) throws Exception{
		IntegralCalLocal local = EJBFactory.getIntegralCal();
		Object[] ret = local.integralCal(vo);
		Integer rdtl_id = new Integer(0);//明细规则
		Integer ramount_id = new Integer(0);//额度
		BigDecimal integral = new BigDecimal(0);//积分
		
		if(ret!=null){
			rdtl_id = Utility.parseInt(Utility.trimNull(ret[0]), new Integer(0));
			ramount_id = Utility.parseInt(Utility.trimNull(ret[1]), new Integer(0));
			integral = Utility.parseDecimal(Utility.trimNull(ret[2]), new BigDecimal(0)).multiply(new BigDecimal(flag));
			
			cust_vo.setAd_integral(new Integer(integral.intValue()));
			cust_vo.setRdtl_id(rdtl_id);
			cust_vo.setRamount_id(ramount_id);
			
			CustIntegralLocal local2 = EJBFactory.getCustIntegral();
			local2.addCustIntegral(cust_vo);
		}
		

	}
	/**
	 * 修改客户积分
	 * @param vo
	 * @throws Exception
	 */
	public void addCustIntegral(CustIntegralVO vo) throws Exception{
		CustIntegralLocal local = EJBFactory.getCustIntegral();
		local.addCustIntegral(vo);
	}
}
