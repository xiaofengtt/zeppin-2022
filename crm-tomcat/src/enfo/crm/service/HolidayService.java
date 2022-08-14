/*
 * 创建日期 2012-1-16
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.service;

import java.util.List;
import java.util.Map;

import enfo.crm.dao.CrmDBManager;

/**
 * @author carlos
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class HolidayService {

	/**
	 * 查询某个时间段内节假日情况
	 * @author carlos
	 *
	 * TODO 要更改此生成的类型注释的模板，请转至
	 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
	 */
	public List queryHolidays(Map dataMap) throws Exception{
		String querySql = "SELECT * FROM V_HOLIDAYS WHERE DC_DATEINT BETWEEN ? AND ?";
		Object[] params = new Object[2];
		params[0] = dataMap.get("startParam");
		params[1] = dataMap.get("endParam");
		return CrmDBManager.listBySql(querySql,params);
	}
	/**
	 * 修改例外日期
	 * @author carlos
	 *
	 * TODO 要更改此生成的类型注释的模板，请转至
	 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
	 */
	public void modiHoliday_date(Map dataMap) throws Exception{
		String modiSql = "{? = CALL SP_MODI_THOLIDAYDATE(?, ?, ?, ?, ?)}";
		Object[] params = new Object[5];
		params[0] = dataMap.get("serial_no");
		params[1] = dataMap.get("holiday_id");
		params[2] = dataMap.get("year");
		params[3] = dataMap.get("mmdd");
		params[4] = dataMap.get("input_man");
		CrmDBManager.cudProc(modiSql, params);
	}
	/**
	 * delete
	 * @author carlos
	 *
	 * TODO 要更改此生成的类型注释的模板，请转至
	 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
	 */
	public void delHolidayDate(Map dataMap) throws Exception{
		String delSql = "{? = CALL SP_DEL_THOLIDAYDATE(?, ?)}";
		Object[] params = new Object[2];
		params[0] = dataMap.get("serial_no");
		params[1] = dataMap.get("input_man");
		CrmDBManager.cudProc(delSql, params);
	}
	
	public void test(){
		System.out.println("tester");
	}
	
}
