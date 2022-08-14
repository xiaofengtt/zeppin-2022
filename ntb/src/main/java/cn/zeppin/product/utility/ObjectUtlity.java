package cn.zeppin.product.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyProductType;

public class ObjectUtlity {
	/**
	 * 根据属性名获取属性值
	 * */
	private static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			// log.error(e.getMessage(),e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取属性名数组
	 * */
	private static String[] getFiledName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length-1];
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i]);
			if(fields[i].toString().indexOf("serialVersionUID") > -1){
				continue;
			}
			System.out.println(fields[i].getType());
			fieldNames[j] = fields[i].getName();
			j++;
		}
		return fieldNames;
	}

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 * */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private List getFiledsInfo(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		List list = new ArrayList();
		Map infoMap = null;
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap();
			infoMap.put("type", fields[i].getType().toString());
			infoMap.put("name", fields[i].getName());
			infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
			list.add(infoMap);
		}
		return list;
	}

	/**
	 * 获取对象的所有属性值，返回一个对象数组
	 * */
	public static Object[] getFiledValues(Object o) {
		String[] fieldNames = getFiledName(o);
		Object[] value = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			if(fieldNames[i] != null){
				value[i] = getFieldValueByName(fieldNames[i], o);
			}
			
		}
		return value;
	}
	public static void main(String[] args) {
		InvestorAccountHistory iahr = new InvestorAccountHistory();
		iahr.setUuid(UUID.randomUUID().toString());
		iahr.setInvestor("ccd865ed-6abd-422a-af19-86b59add5419");
		iahr.setProduct("ccd865ed-6abd-422a-af19-86b59add5419");
		iahr.setProductType(InvestorProductBuyProductType.BANK_PRODUCT);
		iahr.setType(InvestorAccountHistoryType.DIVIDEND);
		iahr.setOrderType(InvestorAccountHistoryOrderType.PAY_TYPE_COMPANY);
		iahr.setOrderId(iahr.getUuid());
		iahr.setOrderNum(Utlity.getOrderNumStr(Utlity.BILL_DEVICE_COMPANY,Utlity.BILL_PAYTYPE_OHER,Utlity.BILL_PURPOSE_DIVIDEND));
		iahr.setAccountBalance(BigDecimal.ZERO);
		iahr.setIncome(BigDecimal.ZERO);
		iahr.setStatus(InvestorAccountHistoryStatus.SUCCESS);
		iahr.setCreatetime(new Timestamp(System.currentTimeMillis()));
		iahr.setPoundage(BigDecimal.ZERO);
		iahr.setPay(BigDecimal.ZERO);
		Object[] obj = getFiledValues(iahr);
		System.out.println(obj);
	}
}
