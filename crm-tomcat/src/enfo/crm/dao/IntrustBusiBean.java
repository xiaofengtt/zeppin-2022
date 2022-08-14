package enfo.crm.dao;

/**
 * <p>
 * Title: Business Bean
 * </p>
 * <p>
 * Description: Session Bean base class for Business object
 * </p>
 * <p>
 * Copyright: Copyright (c) Singlee Software 2003
 * </p>
 * <p>
 * Company: Singlee Software
 * </p>
 * 
 * @author <a href="mailto:zhou_bin@singlee.com">Zhou bin </a>
 * @version 1.0
 * 
 * 修改记录： 2007-5-11：增加了封装过的增删改查方法
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import enfo.crm.dao.supper.RemoveAware;
import enfo.crm.tools.Argument;
import enfo.crm.tools.Format;
import enfo.crm.tools.Utility;


public class IntrustBusiBean extends RemoveAware {
	
	private static String version = "1.1"; // 版本标识

	public final static int PAGE_SIZE = 10;

	protected Integer operator; // 业务对象操作员编号

	protected Integer book; // 账套编号

	private int pageSize = PAGE_SIZE; // 一页显示的记录数

	private int pageCount = 0; // 总的页面数

	private int currentPage = 1; //当前页

	private int pageNum = 15; // 一面显示的可链接个数

	private int rowCount; // 总共记录的行数

	protected int updateFlag = 0; // 批量更新刷新标志，用于beginUpdate()和endUpdate()

	protected sun.jdbc.rowset.CachedRowSet rowset;
	
	public IntrustBusiBean(){
		try {
			rowset = new sun.jdbc.rowset.CachedRowSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void validate() throws BusiException {
	}

	public void list() throws Exception {
	}

	public void load() throws Exception {
	}

	public void append() throws Exception {
		validate();
	}

	public void save() throws Exception {
		validate();
	}

	public void delete() throws Exception {
	}

	public void deleteAll() throws Exception {
	}

	public void listBySql(String sql) throws Exception {
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		ResultSet rslist = stmt.executeQuery();
		try {
			rowset.close();
			rowset.populate(rslist);
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		countRows();
		countPages();
	}

	public void listByCommonSql(String sql) throws Exception {
		Connection conn = IntrustDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rslist = stmt.executeQuery(sql);
		try {
			rowset.close();
			rowset.populate(rslist);
		} finally {
			rslist.close();
			stmt.close();
			conn.close();
		}
		countRows();
		countPages();
	}

	public boolean getNext() throws Exception{
		return rowset.next();
	}

	/*
	protected void beginTran() throws Exception {
		if (ut == null)
			ut = getSessionContext().getUserTransaction();

		ut.begin();
	}
	
	protected void commitTran() throws Exception {
		ut.commit();
	}

	protected void rollTran() throws Exception {
		try {
			ut.rollback();
		} catch (Exception e) {
		}
	}
	*/

	// 页面跳转
	public void gotoPage(int page) throws Exception {
		this.countPages();
		int iRow;
		if (page < 0)
			currentPage = 1;
		else if (page > pageCount)
			currentPage = pageCount;
		else
			currentPage = page;

		// 将记录指针定位到待显示页的第一条记录前面
		iRow = (currentPage - 1) * pageSize + 1;
		if ((iRow <= rowCount) && (iRow > 0)) {
			rowset.absolute(iRow);
			rowset.previous();
		}
	}

	// 页面跳转
	public void gotoPage(String page) throws Exception {
		try {
			gotoPage(Integer.parseInt(page));
		} catch (Exception e) {
			gotoPage(1);
		}
	}

	// 页面跳转
	public void gotoPage(String page, String pagesize) throws Exception {
		this.setPageSize(Utility.parseInt(pagesize, this.pageSize));
		this.gotoPage(page);
	}

	// 计算记录数
	protected void countRows() throws Exception {
		rowset.last();
		rowCount = rowset.getRow();
		rowset.beforeFirst();
	}

	//	计算记录数
	public int getRows() throws Exception {
		rowset.last();
		rowCount = rowset.getRow();
		rowset.beforeFirst();
		return rowCount;
	}

	// 计算页面数
	protected void countPages() throws Exception {
		if (pageSize == 0) {
			pageCount = 0;
			return;
		}
		this.pageCount = this.rowCount / this.pageSize;
		if ((this.rowCount % this.pageSize) != 0)
			this.pageCount++;
	}

	//计算某一字段的合计
	public String getTotalUp(String columnName) throws Exception {
		java.math.BigDecimal sum = new java.math.BigDecimal(0.000);
		rowset.beforeFirst(); //移动倒第一行的前面
		while (rowset.next()) {
			if (rowset.getBigDecimal(columnName) != null) {
				sum = sum.add(rowset.getBigDecimal(columnName));
			}
		}
		return Format.formatMoney(sum);
	}

	// 新样式的页号导航
	public String getPageLink(String strPageName) {
		if (this.rowCount <= PAGE_SIZE) //如果结果集数量小于当前页面大小，不显示分页导航
			return "<input type='hidden' name='pagesize' value='" + PAGE_SIZE
					+ "'>";

		StringBuffer bfPageName = new StringBuffer(1000); // 返回的字符串
		StringBuffer sTemp = new StringBuffer(1000); // 返回的字符串
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		//只而当前页不为首页时，才能出现首页和上一面的按钮
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (currentPage > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >上一页</a>&nbsp;");
		}
		//只而当前页不末页，才能出现下一页和尾页的安钮。
		if (this.currentPage < pageCount) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">下一页</a>&nbsp;");
		}

		if (pageCount > 0) {
			sTemp.append("共");
			sTemp.append(this.rowCount);
			sTemp.append("项内容, 显示第");
			sTemp
					.append("<select size='1' name='page' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.pageCount; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i),
						currentPage);
			sTemp.append("</select> / ");
			sTemp.append(this.pageCount);
			sTemp.append("页, ");
		}
		sTemp.append("显示");
		sTemp
				.append("<select size='1' name='pagesize' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");
		sTemp.append(Argument.getPageSizeOptions(this.getPageSize()));
		sTemp.append("</select>项 / 页");
		return sTemp.toString();
	}
	
	//新样式的页号导航-英文
	public String getPageLinkEn(String strPageName) {
		if (this.rowCount <= PAGE_SIZE) //如果结果集数量小于当前页面大小，不显示分页导航
			return "<input type='hidden' name='pagesize' value='" + PAGE_SIZE
					+ "'>";

		StringBuffer bfPageName = new StringBuffer(1000); // 返回的字符串
		StringBuffer sTemp = new StringBuffer(1000); // 返回的字符串
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		//只而当前页不为首页时，才能出现首页和上一面的按钮
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (currentPage > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >Previous</a>&nbsp;");
		}
		//只而当前页不末页，才能出现下一页和尾页的安钮。
		if (this.currentPage < pageCount) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">Next</a>&nbsp;");
		}

		if (pageCount > 0) {
			sTemp.append("Total");
			sTemp.append(this.rowCount);
			sTemp.append("Items, Display");
			sTemp
					.append("<select size='1' name='page' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.pageCount; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i),
						currentPage);
			sTemp.append("</select> / ");
			sTemp.append(this.pageCount);
			sTemp.append("Page, ");
		}
		sTemp.append("Display");
		sTemp
				.append("<select size='1' name='pagesize' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");
		sTemp.append(Argument.getPageSizeOptions(this.getPageSize()));
		sTemp.append("</select>Items / Page");
		return sTemp.toString();
	}
	
	/**
	 * 分页导航，可以根据不同的语言显示不同分页效果
	 */
	public String getPageLink(String strPageName, String language){
		String temp = "";
		if(language.equals("en"))
			temp = getPageLinkEn(strPageName);
		else
			temp = getPageLink(strPageName);
		return temp;
	}

	/** 
	 * author 蒋娅萍
	 * 在同一页面上需要同时出现多个分页标签时用这个方法
	 * strPageName  指的是要跳转的页面路径以及需要通过请求传递的参数（url格式）
	 * pageArgu     指的是分页中第几页的参数名称
	 * pagesizeArgu 指的是分页中每页数的参数名称
	 **/
	public String getSecondPageLink(String strPageName,String pageArgu,String pagesizeArgu) {
		if (this.rowCount <= PAGE_SIZE) //如果结果集数量小于当前页面大小，不显示分页导航
			return "<input type='hidden' name='"+pagesizeArgu+"' value='" + PAGE_SIZE
					+ "'>";

		StringBuffer bfPageName = new StringBuffer(1000); // 返回的字符串
		StringBuffer sTemp = new StringBuffer(1000); // 返回的字符串
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		//只而当前页不为首页时，才能出现首页和上一面的按钮
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&"+pageArgu+"=");
		else
			bfPageName.append("?"+pageArgu+"=");

		strPageName = bfPageName.toString();

		if (currentPage > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >上一页</a>&nbsp;");
		}
		//只而当前页不末页，才能出现下一页和尾页的安钮。
		if (this.currentPage < pageCount) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">下一页</a>&nbsp;");
		}

		if (pageCount > 0) {
			sTemp.append("共");
			sTemp.append(this.rowCount);
			sTemp.append("项内容, 显示第");
			sTemp
					.append("<select size='1' name='"+pageArgu+"' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.pageCount; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i),
						currentPage);
			sTemp.append("</select> / ");
			sTemp.append(this.pageCount);
			sTemp.append("页, ");
		}
		sTemp.append("显示");
		sTemp.append("<select size='1' name='"+pagesizeArgu+"' onchange='javascript:refreshPage2("+pageArgu+","+pagesizeArgu+");' style='font-family: Tahoma; font-size: 7pt'>");
		
		
		sTemp.append(Argument.getPageSizeOptions(this.getPageSize()));
		sTemp.append("</select>项 / 页");
		return sTemp.toString();
	}

	//样式的页号导航
	public String getPageLinkModal(String strPageName) {
		StringBuffer bfPageName = new StringBuffer(1000); // 返回的字符串
		StringBuffer sTemp = new StringBuffer(1000); // 返回的字符串
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		//只而当前页不为首页时，才能出现首页和上一面的按钮
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		//只而当前页不末页，才能出现下一页和尾页的安钮。

		if (pageCount > 0) {
			sTemp.append("共");
			sTemp.append(this.rowCount);
			sTemp.append("项内容, 显示第");
			sTemp
					.append("<select size='1' name='page' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.pageCount; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i),
						currentPage);
			sTemp.append("</select> / ");
			sTemp.append(this.pageCount);
			sTemp.append("页, ");
		}
		sTemp.append("显示");
		sTemp
				.append("<select size='1' name='pagesize' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");
		sTemp.append(Argument.getPageSizeOptions(this.getPageSize()));
		sTemp.append("</select>项 / 页");
		return sTemp.toString();
	}

	/**
	 * 新网站分页样式
	 * 
	 * @param strPageName
	 * @return
	 * @author caiyuan
	 * @date 2006-12-13
	 */
	public String getPageLinkEx(String strPageName) {
		if (this.rowCount <= PAGE_SIZE) //如果结果集数量小于当前页面大小，不显示分页导航
			return "";

		StringBuffer bfPageName = new StringBuffer(1000); // 返回的字符串
		StringBuffer sTemp = new StringBuffer(1000); // 返回的字符串
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		sTemp
				.append("<table class=\"pagetable\" width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
		sTemp.append("<tr><td align=\"center\" valign=\"middle\">");

		//只而当前页不为首页时，才能出现首页和上一面的按钮
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (currentPage > 1) {
			sTemp
					.append("<a href=\""
							+ strPageName
							+ 1
							+ "\" ><img src=\"/portal/images/page/firstPage.gif\"  style=\"border:0\"  title=\"第一页\"  alt=\"第一页\" /></a>&nbsp;");
			sTemp
					.append("<a href=\""
							+ strPageName
							+ prePage
							+ "\" ><img src=\"/portal/images/page/prevPage.gif\"  style=\"border:0\"  title=\"上一页\"  alt=\"上一页\" /></a>&nbsp;");
		} else {
			sTemp
					.append("<img src=\"/portal/images/page/firstPageDisabled.gif\"  style=\"border:0\"  title=\"第一页\"  alt=\"第一页\" />&nbsp;");
			sTemp
					.append("<img src=\"/portal/images/page/prevPageDisabled.gif\"  style=\"border:0\"  title=\"上一页\"  alt=\"上一页\" />&nbsp;");
		}

		//当中5个分页链接
		int startPage = (currentPage - 2) > 1 ? (currentPage - 2) : 1;
		int endPage = pageCount;
		if (startPage == 1) {
			endPage = 5 > pageCount ? pageCount : 5;
		} else {
			if ((currentPage + 2) > pageCount) {
				endPage = pageCount;
				startPage = (pageCount - 5 + 1) > 1 ? (pageCount - 5 + 1) : 1;
			} else {
				endPage = currentPage + 2;
			}
		}

		for (int i = startPage; i <= endPage; i++) {
			if (i == startPage)
				sTemp.append("<span class=\"pageNum\">&lt;&nbsp;");
			sTemp.append("<a href=\"" + strPageName + i + "\" >");
			if (i == currentPage)
				sTemp.append("<font class=\"currentNum\">");
			sTemp.append(i);
			if (i == currentPage)
				sTemp.append("</font>");
			sTemp.append("</a>");
			if (i == endPage) {
				sTemp.append("&nbsp;&gt;</span>&nbsp;");
			} else {
				sTemp.append(",");
			}
		}

		//只而当前页不末页，才能出现下一页和尾页的安钮。
		if (this.currentPage < pageCount) {
			sTemp
					.append("<a href=\""
							+ strPageName
							+ nextPage
							+ "\" ><img src=\"/portal/images/page/nextPage.gif\"  style=\"border:0\"  title=\"下一页\"  alt=\"下一页\" /></a>&nbsp;");
			sTemp
					.append("<a href=\""
							+ strPageName
							+ endPage
							+ "\" ><img src=\"/portal/images/page/lastPage.gif\"  style=\"border:0\"  title=\"最后页\"  alt=\"最后页\" /></a>&nbsp;");
		} else {
			sTemp
					.append("<img src=\"/portal/images/page/nextPageDisabled.gif\"  style=\"border:0\"  title=\"下一页\"  alt=\"下一页\" />&nbsp;");
			sTemp
					.append("<img src=\"/portal/images/page/lastPageDisabled.gif\"  style=\"border:0\"  title=\"最后页\"  alt=\"最后页\" />&nbsp;");
		}

		sTemp.append("共");
		sTemp.append(this.rowCount);
		sTemp.append("项内容");

		sTemp.append("</td></tr></table>");
		return sTemp.toString();
	}

	public void addLog(Integer busi_flag, String busi_name, String summary)
			throws Exception {
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn
				.prepareCall("{?=call SP_ADD_TLOGLIST(?, ?, ?, ?)}");
		try {
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, busi_flag.intValue());
			stmt.setString(3, busi_name);
			stmt.setInt(4, operator.intValue());
			stmt.setString(5, summary);
			stmt.execute();
			IntrustDBManager.handleResultCode(stmt.getInt(1));
		} catch (Exception e) {
			throw new BusiException("添加日志失败: " + e.getMessage());
		} finally {
			stmt.close();
			conn.close();
		}
	}

	public void beginUpdate() {
		this.updateFlag++;
	}

	public void endUpdate() {
		this.updateFlag--;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public String getVersion() {
		return version;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize == 0)
			this.pageSize = this.rowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Integer getBook() {
		return book;
	}

	public void setBook(Integer book) {
		this.book = book;
	}

	public int getRowCount() {
		return rowCount;
	}

	//优化方案
	/**
	 * 增删改的封装
	 * 
	 * @param cudSQl
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @throws Exception
	 */
	public void cud(String cudSl, Object[] params) throws Exception {
		int errorCode = 0;
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.prepareCall(cudSl);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			for (int i = 0; i < params.length; i++) {
				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(i + 2, null);
				else
					stmt.setObject(i + 2, params[i]);
			}
			stmt.executeUpdate();

			errorCode = stmt.getInt(1);
			stmt.close();
			conn.close();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {

			}
			try {
				if (conn != null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {

			}
		}
		if (errorCode < 0) //只有有错误的时候才捕获
			IntrustDBManager.handleResultCode(errorCode);
	}

	/**
	 * 新增的封装
	 * 
	 * @param appendSql
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @throws Exception
	 */
	public void append(String appendSql, Object[] params) throws Exception {
		this.cud(appendSql, params);
	}

	/**
	 * 更新的封装
	 * 
	 * @param updateSql
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @throws Exception
	 */
	public void update(String updateSql, Object[] params) throws Exception {
		this.cud(updateSql, params);
	}

	/**
	 * 删除的封装
	 * 
	 * @param deleteSql
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @throws Exception
	 */
	public void delete(String deleteSql, Object[] params) throws Exception {
		this.cud(deleteSql, params);
	}

	/**
	 * 增删改的封装,有一个输出参数返回
	 * 
	 * @param cudSql
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @param outParamPos
	 *            输出参数的位置,包含了第一位的错误代码输出参数
	 * @param outParamType
	 *            输出参数的类型
	 * @return Object 输出参数
	 * @throws Exception
	 */
	public Object cudEx(String cudSql, Object[] params, int outParamPos,
			int outParamType) throws Exception {
		int errorCode = 0;
		Object ret = null;
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.prepareCall(cudSql);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			int trueIndex;
			int tempOutParamPos = outParamPos - 2;
			for (int i = 0; i < params.length; i++) {
				trueIndex = i + 2; //2 3 4 5 6......
				if (i >= tempOutParamPos) //由于有一个输出参数存在，后面实际的输入参数的位置将向后调1
					trueIndex++;

				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(trueIndex, null);
				else
					stmt.setObject(trueIndex, params[i]);
			}
			stmt.registerOutParameter(outParamPos, outParamType);

			stmt.executeUpdate();

			errorCode = stmt.getInt(1);

			ret = stmt.getObject(outParamPos);
			stmt.close();
			conn.close();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {

			}
			try {
				if (conn != null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {

			}
		}
		if (errorCode < 0) //只有有错误的时候才捕获
			IntrustDBManager.handleResultCode(errorCode);
		return ret;
	}

	/**
	 * 增删改的封装,有多个输出参数返回
	 * 
	 * @param cudSql
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @param ourParamPos
	 *            输出参数的位置,包含了第一位的错误代码输出参数
	 * @param outParamType
	 *            输出参数的类型
	 * @return Object 输出参数
	 * @throws Exception
	 */
	public Object[] cudEx(String cudSql, Object[] params, int[] outParamPos,
			int[] outParamType) throws Exception {
		int errorCode = 0;
		Object[] ret = new Object[outParamPos.length];
		Connection conn = null;
		CallableStatement stmt = null;
		try {
			conn = IntrustDBManager.getConnection();
			stmt = conn.prepareCall(cudSql);
			stmt.registerOutParameter(1, java.sql.Types.INTEGER);
			int trueIndex;

			for (int i = 0; i < params.length; i++) {
				trueIndex = i + 2;
				for (int j = 0; j < outParamPos.length; j++) //由于有多个输出参数存在，后面实际的输入参数的位置将向后调N
				{
					int tempOutParamPos = outParamPos[j] - 2;
					if (i >= tempOutParamPos)
						trueIndex++;
				}

				stmt.setObject(trueIndex, params[i]);
			}
			//注册输出参数
			for (int j = 0; j < outParamPos.length; j++) {
				stmt.registerOutParameter(outParamPos[j], outParamType[j]);
			}

			stmt.executeUpdate();

			errorCode = stmt.getInt(1);
			//获得输出参数
			for (int j = 0; j < outParamPos.length; j++) {
				ret[j] = stmt.getObject(outParamPos[j]);
			}
			stmt.close();
			conn.close();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {

			}
			try {
				if (conn != null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {

			}
		}
		if (errorCode < 0) //只有有错误的时候才捕获
			IntrustDBManager.handleResultCode(errorCode);
		return ret;
	}

	/**
	 * 查询的封装
	 * 
	 * @param listSql
	 *            存储过程
	 * @param params
	 *            参数数组
	 * @throws Exception
	 */

	public void query(String listSql, Object[] params) throws Exception {
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rslist = null;
		try {
			conn = IntrustDBManager.getConnection();

			stmt = conn.prepareCall(listSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < params.length; i++) {

				if (params[i] == null)
					//NULL值是无类型的，但是用Object传递无效，所以把它作为String参数
					stmt.setString(i + 1, null);
				else
					stmt.setObject(i + 1, params[i]);

			}
			rslist = stmt.executeQuery();

			rowset.close();
			rowset.populate(rslist);
			rslist.close();
			stmt.close();
			conn.close();
		} finally {
			try {
				if (rslist != null)
					rslist.close();
			} catch (SQLException e) {

			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {

			}
			try {
				if (conn != null && !conn.isClosed())
					conn.close();
			} catch (SQLException e) {

			}
		}
		countRows();
		countPages();
	}

	/**
	 * 获得ROWSET结果集
	 * 
	 * @return rowset
	 */
	public sun.jdbc.rowset.CachedRowSet getRowset() {
		return rowset;
	}

	/**
	 * 分页查询的操作
	 * @param procSql 存储过程
	 * @param params 参数
	 */
   public IPageList listProcPage(String procSql,Object[] params,int pageIndex,int pageSize) throws BusiException
	{		
		return IntrustDBManager.listProcPage(procSql,params,pageIndex,pageSize);
	}

	@Override
	public void remove() {
		
	}
   
}