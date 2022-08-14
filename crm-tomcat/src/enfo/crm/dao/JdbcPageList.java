package enfo.crm.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import enfo.crm.tools.Argument;
import enfo.crm.tools.LocalUtilis;

/**
 * 
 * @author Felix
 * @since 2008-5-20
 * @version 1.0 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

public class JdbcPageList implements IPageList {

	private int totalPage = 0; // 总的页面数
	private int pageIndex = 1; // 当前的页码
	private int pageSize = 8; // 一页显示的记录数，如果为0则对应全部结果
	private int totalSize = 0; // 总记录数
	private int blankSize = 0;// 补空行的大小

	private List rsList = null; // 缓存的页面集结果集

	private String[] totalColumn; // 需要统计合计的金额字段数组(全部合计)
	private Map totalResult = null; // 金额字段的统计映射

	/**
	 * 缓存结果.
	 * 
	 * @param rs
	 *            结果集可以是JDBC的ResultSet型由于是一次获取所有结果所有直接取得总记录数
	 */
	public void populate(ResultSet rs) throws SQLException {
		rsList = new ArrayList();
		int iRow = 0;
		int rowStart = (pageIndex - 1) * pageSize; // 开始行数
		int rowEnd = rowStart + pageSize; // 结束行数

		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		BigDecimal[] totalValues = null; // 统计金额数组
		if (totalColumn != null && totalColumn.length > 0) // 存在需要统计的金额字段
		{
			totalResult = new HashMap(totalColumn.length); // 初始化统计结果存放的map
			totalValues = new BigDecimal[totalColumn.length]; // 初始化统计金额数组
			for (int j = 0; j < totalColumn.length; j++) {
				totalValues[j] = new BigDecimal(0);
			}

		}
		
		while (rs.next()) // 根据分页参数重新生成一个页面大小的List结果集
		{
			iRow++;
			
						
			if ((iRow > rowStart && iRow <= rowEnd) || pageSize <= 0) // 取当前页面大小结果||pageSize小于等于0获取全部结果
			{
				Map rowMap = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					switch (rsmd.getColumnType(i)) {
					case java.sql.Types.TIMESTAMP:
						rowMap.put(rsmd.getColumnName(i), rs.getTimestamp(i));
						break;
					case java.sql.Types.CLOB:
						rowMap.put(rsmd.getColumnName(i), rs.getString(i));
						break;
					default:
						rowMap.put(rsmd.getColumnName(i), rs.getObject(i));
					}
				}
				
				rsList.add(rowMap);
			}
			if (totalColumn != null && totalColumn.length > 0) // 存在需要统计的金额字段
			{
				for (int j = 0; j < totalColumn.length; j++) {
					if (rs.getBigDecimal(totalColumn[j]) != null)
						totalValues[j] = totalValues[j].add(rs
								.getBigDecimal(totalColumn[j]));
				}

			}
		}
		// 将统计好的金额存放到结果映射中去
		if (totalColumn != null && totalColumn.length > 0) // 存在需要统计的金额字段
		{
			for (int j = 0; j < totalColumn.length; j++) {
				totalResult.put(totalColumn[j], totalValues[j]);
			}
		}

		totalSize = iRow;
		countPages(); // 根据总结果集大小计算总的页面数
	}

	/**
	 * 缓存结果.
	 * 
	 * @param rs
	 *            结果集可以是JDBC的List<Map>型由于是一次获取所有结果所有直接取得总记录数
	 */
	private void populate(List rs) {
		totalSize = rs.size();
		countPages(); // 根据总结果集大小计算总的页面数
		rsList = new ArrayList();
		int iRow = 0;
		int rowStart = (pageIndex - 1) * pageSize; // 开始行数
		int rowEnd = rowStart + pageSize; // 结束行数
		for (int i = 0; i < rs.size(); i++) // 根据分页参数重新生成一个页面大小的List结果集
		{
			iRow++;
			if ((iRow > rowStart && iRow <= rowEnd) || pageSize <= 0) // 取当前页面大小结果||pageSize小于等于0获取全部结果
			{
				rsList.add(rs.get(i));
			}
		}
	}

	/**
	 * 
	 * 获得统计的金额
	 * 
	 * @param columnName
	 *            字段名称
	 */
	public BigDecimal getTotalValue(String columnName) throws BusiException {
		if (totalResult == null)
			throw new BusiException("尚未初始化统计配置信息");
		return (BigDecimal) totalResult.get(columnName);
	}

	/**
	 * 根据总的结果集数量计算页面数.
	 */
	private void countPages() {
		// 如果显示全部
		if (pageSize <= 0) {
			totalPage = 1;
		} else// 计算总的页面数
		{
			blankSize = pageSize - rsList.size();// 计算补空行的大小

			this.totalPage = this.totalSize / this.pageSize;
			if ((this.totalSize % this.pageSize) != 0)
				this.totalPage++;
		}
		// 如果输入的页面超过最大页面则跳转到最后一页
		if (pageIndex > totalPage) {
			pageIndex = totalPage;
		}
	}

	// 分页导航
	public String getPageLink(String strPageName) {
		 boolean b=true;


		 
		 if(strPageName.indexOf("ifhavaAll=1")>=0){
		 	 b=false;
		 }
		 	
		
		StringBuffer bfPageName = new StringBuffer(1000); // 返回的字符串
		StringBuffer sTemp = new StringBuffer(1000); // 返回的字符串
		int nextPage = pageIndex + 1;
		int prePage = pageIndex - 1;
		bfPageName.append(strPageName);

		// 只而当前页不为首页时，才能出现首页和上一面的按钮
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (pageIndex > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >上一页</a>&nbsp;");
		}
		// 只而当前页不末页，才能出现下一页和尾页的安钮。
		if (this.pageIndex < totalPage) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">下一页</a>&nbsp;");
		}

		if (totalPage > 0) {
			sTemp.append("共");
			sTemp.append(totalSize);
			sTemp.append("项内容, 显示第");
			sTemp
					.append("<select size='1' name='page_list' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.totalPage; i++)
				Argument
						.appendOptions(sTemp, i, Integer.toString(i), pageIndex);
			sTemp.append("</select> / ");
			sTemp.append(this.totalPage);
			sTemp.append("页, ");
		}
		sTemp.append("显示");
		sTemp
				.append("<select size='1' name='pagesize' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");

		StringBuffer sb = new StringBuffer(200);
		Argument.appendOptions(sb, 8, "8", pageSize);
		Argument.appendOptions(sb, 10, "10", pageSize);
		Argument.appendOptions(sb, 15, "15", pageSize);
		Argument.appendOptions(sb, 20, "20", pageSize);
		Argument.appendOptions(sb, 50, "50", pageSize);


		if(b==true){ 
			Argument.appendOptions(sb, -1, "ALL", pageSize);
		}
		

		sTemp.append(sb.toString());
		sTemp.append("</select>项 / 页");
		return sTemp.toString();
	}
	
	// 分页导航 国际化
	public String getPageLink(String strPageName,Locale locale) {
		
	       boolean b=true;

	     if(strPageName.indexOf("ifhavaAll=1")>=0){
		 	 b=false;
		 }
		 	
	        
		StringBuffer bfPageName = new StringBuffer(1000); // 返回的字符串
		StringBuffer sTemp = new StringBuffer(1000); // 返回的字符串
		int nextPage = pageIndex + 1;
		int prePage = pageIndex - 1;
		bfPageName.append(strPageName);

		// 只而当前页不为首页时，才能出现首页和上一面的按钮
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (pageIndex > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >"+LocalUtilis.language("message.pageUp", locale)+"</a>&nbsp;");//上一页
		}
		// 只而当前页不末页，才能出现下一页和尾页的安钮。
		if (this.pageIndex < totalPage) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">"+LocalUtilis.language("message.pageDown", locale)+"</a>&nbsp;");//下一页
		}

		if (totalPage > 0) {
			sTemp.append(LocalUtilis.language("message.total2", locale));//共
			sTemp.append("&nbsp;"+totalSize+"&nbsp;");
			sTemp.append(LocalUtilis.language("message.items2", locale)+","+LocalUtilis.language("message.show", locale));//项内容, 显示第
			sTemp
					.append("&nbsp;<select size='1' name='page_list' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.totalPage; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i), pageIndex);
			sTemp.append("</select>&nbsp; / ");
			sTemp.append(this.totalPage);
			sTemp.append(""+LocalUtilis.language("message.page", locale)+", ");//页
		}else{
			int idx = strPageName.indexOf("pagesize=");
			if (idx>=0) {
				int i = idx + "pagesize=".length();
				int idx2 = strPageName.indexOf("&",i);
				int j = idx2>=0? idx2: strPageName.length();
				if (j<=i) pageSize=0;
				else pageSize = Integer.parseInt(strPageName.substring(i, j));
			}				
		}
		
		sTemp.append(LocalUtilis.language("message.show2", locale));//显示
		sTemp.append("&nbsp;<select size='1' name='pagesize' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");

		StringBuffer sb = new StringBuffer(200);
		Argument.appendOptions(sb, 8, "8", pageSize);
		Argument.appendOptions(sb, 10, "10", pageSize);
		Argument.appendOptions(sb, 15, "15", pageSize);
		Argument.appendOptions(sb, 20, "20", pageSize);
		Argument.appendOptions(sb, 50, "50", pageSize);

		if(b==true){ 
			Argument.appendOptions(sb, -1, "ALL", pageSize);
		}
		sTemp.append(sb.toString());
		sTemp.append("</select>&nbsp;"+LocalUtilis.language("message.entries", locale)+" / "+LocalUtilis.language("message.page", locale));//项 / 页
		return sTemp.toString();
	}

	/**
	 * @return 返回 pageIndex。
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex
	 *            设置 pageIndex。
	 */
	public void setPageIndex(int pageIndex) {
		if (pageIndex > 0)
			this.pageIndex = pageIndex;
	}

	/**
	 * @return 返回 pageSize。
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            设置 pageSize。
	 */
	public void setPageSize(int pageSize) {
		if (pageSize != 0) // 如果是0则用默认值
			this.pageSize = pageSize;
	}

	/**
	 * @return 返回 rsList。
	 */
	public List getRsList() {
		return rsList;
	}

	/**
	 * @param rsList
	 *            设置 rsList。
	 */
	public void setRsList(List rsList) {
		populate(rsList);
	}

	/**
	 * @return 返回 totalPage。
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            设置 totalPage。
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return 返回 totalSize。
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize
	 *            设置 totalSize。
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * 获得需要补空行的大小
	 * 
	 * @return
	 */
	public int getBlankSize() {
		return blankSize;
	}

	/**
	 * 
	 * 
	 * @param strings
	 */
	public void setTotalColumn(String[] strings) {
		totalColumn = strings;
	}
}
