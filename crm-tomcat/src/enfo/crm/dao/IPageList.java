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
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

public interface IPageList {
	
	/**
	 * 缓存结果.
	 * @param rs 结果集可以是JDBC的ResultSet型由于是一次获取所有结果所有直接取得总记录数
	 */
	public void populate(ResultSet rs) throws SQLException;	
    
	/**
	 * 
	 * 获得统计的金额
	 * 
	 * @param columnName 字段名称
	 */
	public BigDecimal getTotalValue(String columnName) throws BusiException;
    
    
	//分页导航
	public String getPageLink(String strPageName);
	//分页导航 国际化
	public String getPageLink(String strPageName,Locale locale);

	/**
	 * @return 返回 pageIndex。
	 */
	public int getPageIndex();

	/**
	 * @param pageIndex 设置 pageIndex。
	 */
	public void setPageIndex(int pageIndex);

	/**
	 * @return 返回 pageSize。
	 */
	public int getPageSize();

	/**
	 * @param pageSize 设置 pageSize。
	 */
	public void setPageSize(int pageSize);

	/**
	 * @return 返回 rsList。
	 */
	public List getRsList();

	/**
	 * @param rsList 设置 rsList。
	 */
	public void setRsList(List rsList);

	/**
	 * @return 返回 totalPage。
	 */
	public int getTotalPage();

	/**
	 * @param totalPage 设置 totalPage。
	 */
	public void setTotalPage(int totalPage);

	/**
	 * @return 返回 totalSize。
	 */
	public int getTotalSize();

	/**
	 * @param totalSize 设置 totalSize。
	 */
	public void setTotalSize(int totalSize);
    
	/**
	 * 获得需要补空行的大小
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
