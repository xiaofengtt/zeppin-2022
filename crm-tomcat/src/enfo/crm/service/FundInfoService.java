package enfo.crm.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enfo.crm.dao.IPageList;
import enfo.crm.intrust.ProductLocal;
import enfo.crm.intrust.RedeemLocal;
import enfo.crm.tools.Argument;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Format;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ProductVO;
import enfo.crm.vo.RedeemVO;

public class FundInfoService {

	/**
	 * 获得产品详细信息
	 * 
	 * @param input_operatorCode
	 * @param input_bookCode
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	public List getProductInfo(Integer input_operatorCode, Integer input_bookCode, Integer product_id) throws Exception {
		List list = null;
		List listValue = new ArrayList();
		Map map = null;
		Map mapValue = new HashMap();
		//产品信息
		ProductLocal product = EJBFactory.getProduct();
		ProductVO vo = new ProductVO();
		vo.setProduct_id(product_id);
		list = product.load(vo);
		for(int i = 0; i < list.size(); i++){
			map = (Map)list.get(i);
			mapValue.put("product_name", Utility.trimNull(map.get("PRODUCT_NAME")));
			mapValue.put("total_amount", Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_AMOUNT")), new BigDecimal(0)).doubleValue()));
			mapValue.put("pre_start_date", Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")), null)));
			mapValue.put("pre_end_date", Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")), null)));
			mapValue.put("open_flag", Utility.parseInt(Utility.trimNull(map.get("OPEN_FLAG")),new Integer(0)).intValue() == 1 ? "开放式" : "封闭式");
			mapValue.put("start_date", Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("START_DATE")), null)));
			mapValue.put("end_date", Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("END_DATE")), null)));
			mapValue.put("manager_man", Utility.trimNull(Argument.getOpName(Utility.parseInt(Utility.trimNull(map.get("MANAGER_MAN")), null))));
			mapValue.put("currency_id", Utility.trimNull(Argument.getCurrencyName(Utility.trimNull(map.get("CURRENCY_ID")))));
			mapValue.put("product_jc",Utility.trimNull(map.get("PRODUCT_JC")));
			mapValue.put("pre_num",Utility.trimNull(map.get("PRE_NUM")));
			mapValue.put("pre_money", Format.formatMoney0(Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0)).multiply(new BigDecimal(0.0001)).setScale(3,1)));
		}
		
		//产品销售信息
		list = product.queryProductLimit(vo);
		for(int i = 0; i < list.size(); i++){
			map = (Map)list.get(i);
			mapValue.put("jg_min_subamount",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("JG_MIN_SUBAMOUNT")), new BigDecimal(0)).doubleValue()));
			mapValue.put("gr_min_subamount",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("GR_MIN_SUBAMOUNT")), new BigDecimal(0)).doubleValue()));
			mapValue.put("jg_min_bidsamount",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("JG_MIN_BIDSAMOUNT")), new BigDecimal(0)).doubleValue()));
			mapValue.put("gr_min_bidsamount",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("GR_MIN_BIDSAMOUNT")), new BigDecimal(0)).doubleValue()));
			mapValue.put("jg_min_appbidsamount",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("JG_MIN_APPBIDSAMOUNT")), new BigDecimal(0)).doubleValue()));
			mapValue.put("gr_min_appbidsamount",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("GR_MIN_APPBIDSAMOUNT")), new BigDecimal(0)).doubleValue()));
			mapValue.put("min_redeem_vol",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("MIN_REDEEM_VOL")), new BigDecimal(0)).doubleValue()));
			mapValue.put("min_redeem_vol2",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("MIN_REDEEM_VOL2")), new BigDecimal(0)).doubleValue()));
			mapValue.put("large_redeem_flag",Utility.trimNull(map.get("LARGE_REDEEM_FLAG")).equals("1") ? "是" : "否");
			mapValue.put("large_redeem_condition",Utility.trimNull(map.get("LARGE_REDEEM_CONDITION")).equals("1") ? "按净赎回份额判断" : "按总赎回份额判断");
			mapValue.put("large_redeem_percent",Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("LARGE_REDEEM_PERCENT")), new BigDecimal(0)).doubleValue(), 4));
			mapValue.put("large_redeem_deal",Utility.trimNull(map.get("LARGE_REDEEM_DEAL")).equals("1") ? "顺延" : "根据客户申请数据处理");
			mapValue.put("coerce_redeem_flag",Utility.trimNull(map.get("COERCE_REDEEM_FLAG")).equals("1") ? "是" : "否");
		}
		product.remove();
		listValue.add(mapValue);
		return listValue;
	}

	/**
	 * 获取产品赎回信息
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	public List getSqredeemLarge(Integer product_id) throws Exception {
		List listValue = new ArrayList();
		Map mapValue = new HashMap();
		Map map = null;
		RedeemLocal local = EJBFactory.getRedeem();
		RedeemVO vo = new RedeemVO();
		vo.setProduct_id(product_id);
		IPageList pageList = local.listSqredeemLarge(vo, 1, -1);
		List list = pageList.getRsList();
		for(int i = 0; i < list.size(); i++){
			map = (Map)list.get(i);
			mapValue.put("OPEN_DATE", Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("OPEN_DATE")), new Integer(0)))));
			mapValue.put("OPEN_DATE_NO", Utility.trimNull(map.get("OPEN_DATE")));
			mapValue.put("NAV", Utility.trimNull(Format.formatMoney(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map.get("NAV"))), null).doubleValue(), 4)));
			mapValue.put("PURCHASE_NUM", Utility.trimNull(map.get("PURCHASE_NUM")));
			mapValue.put("PURCHASE_MONEY", Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("PURCHASE_MONEY"))))));
			mapValue.put("PURCHASE_AMOUNT", Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("PURCHASE_AMOUNT"))))));
			mapValue.put("REDEEM_NUM", Utility.trimNull(map.get("REDEEM_NUM")));
			mapValue.put("REDEEM_AMOUNT", Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("REDEEM_AMOUNT"))))));
			mapValue.put("NETREDEEM_AMOUNT", Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("NETREDEEM_AMOUNT"))))));
			mapValue.put("NETREDEEM_PERCENT", Utility.trimNull(Format.formatMoney(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map.get("NETREDEEM_PERCENT"))).multiply(new BigDecimal(100)), null).doubleValue(), 2))+"%");
			mapValue.put("REDEEM_PERCENT", Utility.trimNull(map.get("LARGE_REDEEM_CONDITION")).equals("1") ? "" : Utility.trimNull(Format.formatMoney(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map.get("REDEEM_PERCENT"))).multiply(new BigDecimal(100)), null).doubleValue(), 2))+"%");
			mapValue.put("LAST_TOTAL_AMOUNT", Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("LAST_TOTAL_AMOUNT"))))));
			mapValue.put("LARGE_REDEEM_PERCENT", Utility.trimNull(Format.formatMoney(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map.get("LARGE_REDEEM_PERCENT"))).multiply(new BigDecimal(100)), null).doubleValue(), 2))+"%");
			mapValue.put("LARGE_REDEEM_CONDITION", Utility.trimNull(map.get("LARGE_REDEEM_CONDITION")).equals("1") ? "按净赎回份额判断" : "按总赎回份额判断");
			mapValue.put("LARGE_REDEEM_CONDITION_NO", Utility.trimNull(map.get("LARGE_REDEEM_CONDITION")));
			mapValue.put("LARGE_REDEEM_FLAG", Utility.trimNull(map.get("LARGE_REDEEM_FLAG")).equals("1") ? "<font color='red'>本期已构成巨额赎回</font>" : "本期未构成巨额赎回");
			mapValue.put("LARGE_REDEEM_FLAG_NO", Utility.trimNull(map.get("LARGE_REDEEM_FLAG")));
			mapValue.put("LARGE_DEAL_FLAG", Utility.trimNull(map.get("LARGE_DEAL_FLAG")));
			mapValue.put("LARGE_REDEEM_RATE", Utility.trimNull(Format.formatMoney(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map.get("LARGE_REDEEM_RATE"))).multiply(new BigDecimal(100)), null).doubleValue(), 2))+"%");
			listValue.add(mapValue);
		}
		local.remove();
		return listValue;
	}
}