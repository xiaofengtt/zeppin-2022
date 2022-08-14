package enfo.crm.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 *  
 * @author Felix
 * @since 2008-5-20
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */

public interface IPageList {
	
	/**
	 * ������.
	 * @param rs �����������JDBC��ResultSet��������һ�λ�ȡ���н������ֱ��ȡ���ܼ�¼��
	 */
	public void populate(ResultSet rs) throws SQLException;	
    
	/**
	 * 
	 * ���ͳ�ƵĽ��
	 * 
	 * @param columnName �ֶ�����
	 */
	public BigDecimal getTotalValue(String columnName) throws BusiException;
    
    
	//��ҳ����
	public String getPageLink(String strPageName);
	//��ҳ���� ���ʻ�
	public String getPageLink(String strPageName,Locale locale);

	/**
	 * @return ���� pageIndex��
	 */
	public int getPageIndex();

	/**
	 * @param pageIndex ���� pageIndex��
	 */
	public void setPageIndex(int pageIndex);

	/**
	 * @return ���� pageSize��
	 */
	public int getPageSize();

	/**
	 * @param pageSize ���� pageSize��
	 */
	public void setPageSize(int pageSize);

	/**
	 * @return ���� rsList��
	 */
	public List getRsList();

	/**
	 * @param rsList ���� rsList��
	 */
	public void setRsList(List rsList);

	/**
	 * @return ���� totalPage��
	 */
	public int getTotalPage();

	/**
	 * @param totalPage ���� totalPage��
	 */
	public void setTotalPage(int totalPage);

	/**
	 * @return ���� totalSize��
	 */
	public int getTotalSize();

	/**
	 * @param totalSize ���� totalSize��
	 */
	public void setTotalSize(int totalSize);
    
	/**
	 * �����Ҫ�����еĴ�С
	 * 
	 * @return
	 */
	public int getBlankSize(); 
	
	/**
	 * 
	 * 
	 * @param strings
	 */
	public void setTotalColumn(String[] strings);

}
