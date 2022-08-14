/*
 * �������� 2012-1-16
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.service;

import java.util.List;
import java.util.Map;

import enfo.crm.dao.CrmDBManager;

/**
 * @author carlos
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class HolidayService {

	/**
	 * ��ѯĳ��ʱ����ڽڼ������
	 * @author carlos
	 *
	 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
	 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
	 */
	public List queryHolidays(Map dataMap) throws Exception{
		String querySql = "SELECT * FROM V_HOLIDAYS WHERE DC_DATEINT BETWEEN ? AND ?";
		Object[] params = new Object[2];
		params[0] = dataMap.get("startParam");
		params[1] = dataMap.get("endParam");
		return CrmDBManager.listBySql(querySql,params);
	}
	/**
	 * �޸���������
	 * @author carlos
	 *
	 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
	 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
	 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
	 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
