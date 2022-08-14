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
 * �޸ļ�¼�� 2007-5-11�������˷�װ������ɾ�Ĳ鷽��
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
	
	private static String version = "1.1"; // �汾��ʶ

	public final static int PAGE_SIZE = 10;

	protected Integer operator; // ҵ��������Ա���

	protected Integer book; // ���ױ��

	private int pageSize = PAGE_SIZE; // һҳ��ʾ�ļ�¼��

	private int pageCount = 0; // �ܵ�ҳ����

	private int currentPage = 1; //��ǰҳ

	private int pageNum = 15; // һ����ʾ�Ŀ����Ӹ���

	private int rowCount; // �ܹ���¼������

	protected int updateFlag = 0; // ��������ˢ�±�־������beginUpdate()��endUpdate()

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

	// ҳ����ת
	public void gotoPage(int page) throws Exception {
		this.countPages();
		int iRow;
		if (page < 0)
			currentPage = 1;
		else if (page > pageCount)
			currentPage = pageCount;
		else
			currentPage = page;

		// ����¼ָ�붨λ������ʾҳ�ĵ�һ����¼ǰ��
		iRow = (currentPage - 1) * pageSize + 1;
		if ((iRow <= rowCount) && (iRow > 0)) {
			rowset.absolute(iRow);
			rowset.previous();
		}
	}

	// ҳ����ת
	public void gotoPage(String page) throws Exception {
		try {
			gotoPage(Integer.parseInt(page));
		} catch (Exception e) {
			gotoPage(1);
		}
	}

	// ҳ����ת
	public void gotoPage(String page, String pagesize) throws Exception {
		this.setPageSize(Utility.parseInt(pagesize, this.pageSize));
		this.gotoPage(page);
	}

	// �����¼��
	protected void countRows() throws Exception {
		rowset.last();
		rowCount = rowset.getRow();
		rowset.beforeFirst();
	}

	//	�����¼��
	public int getRows() throws Exception {
		rowset.last();
		rowCount = rowset.getRow();
		rowset.beforeFirst();
		return rowCount;
	}

	// ����ҳ����
	protected void countPages() throws Exception {
		if (pageSize == 0) {
			pageCount = 0;
			return;
		}
		this.pageCount = this.rowCount / this.pageSize;
		if ((this.rowCount % this.pageSize) != 0)
			this.pageCount++;
	}

	//����ĳһ�ֶεĺϼ�
	public String getTotalUp(String columnName) throws Exception {
		java.math.BigDecimal sum = new java.math.BigDecimal(0.000);
		rowset.beforeFirst(); //�ƶ�����һ�е�ǰ��
		while (rowset.next()) {
			if (rowset.getBigDecimal(columnName) != null) {
				sum = sum.add(rowset.getBigDecimal(columnName));
			}
		}
		return Format.formatMoney(sum);
	}

	// ����ʽ��ҳ�ŵ���
	public String getPageLink(String strPageName) {
		if (this.rowCount <= PAGE_SIZE) //������������С�ڵ�ǰҳ���С������ʾ��ҳ����
			return "<input type='hidden' name='pagesize' value='" + PAGE_SIZE
					+ "'>";

		StringBuffer bfPageName = new StringBuffer(1000); // ���ص��ַ���
		StringBuffer sTemp = new StringBuffer(1000); // ���ص��ַ���
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		//ֻ����ǰҳ��Ϊ��ҳʱ�����ܳ�����ҳ����һ��İ�ť
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (currentPage > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >��һҳ</a>&nbsp;");
		}
		//ֻ����ǰҳ��ĩҳ�����ܳ�����һҳ��βҳ�İ�ť��
		if (this.currentPage < pageCount) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">��һҳ</a>&nbsp;");
		}

		if (pageCount > 0) {
			sTemp.append("��");
			sTemp.append(this.rowCount);
			sTemp.append("������, ��ʾ��");
			sTemp
					.append("<select size='1' name='page' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.pageCount; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i),
						currentPage);
			sTemp.append("</select> / ");
			sTemp.append(this.pageCount);
			sTemp.append("ҳ, ");
		}
		sTemp.append("��ʾ");
		sTemp
				.append("<select size='1' name='pagesize' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");
		sTemp.append(Argument.getPageSizeOptions(this.getPageSize()));
		sTemp.append("</select>�� / ҳ");
		return sTemp.toString();
	}
	
	//����ʽ��ҳ�ŵ���-Ӣ��
	public String getPageLinkEn(String strPageName) {
		if (this.rowCount <= PAGE_SIZE) //������������С�ڵ�ǰҳ���С������ʾ��ҳ����
			return "<input type='hidden' name='pagesize' value='" + PAGE_SIZE
					+ "'>";

		StringBuffer bfPageName = new StringBuffer(1000); // ���ص��ַ���
		StringBuffer sTemp = new StringBuffer(1000); // ���ص��ַ���
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		//ֻ����ǰҳ��Ϊ��ҳʱ�����ܳ�����ҳ����һ��İ�ť
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (currentPage > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >Previous</a>&nbsp;");
		}
		//ֻ����ǰҳ��ĩҳ�����ܳ�����һҳ��βҳ�İ�ť��
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
	 * ��ҳ���������Ը��ݲ�ͬ��������ʾ��ͬ��ҳЧ��
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
	 * author ���Ƽ
	 * ��ͬһҳ������Ҫͬʱ���ֶ����ҳ��ǩʱ���������
	 * strPageName  ָ����Ҫ��ת��ҳ��·���Լ���Ҫͨ�����󴫵ݵĲ�����url��ʽ��
	 * pageArgu     ָ���Ƿ�ҳ�еڼ�ҳ�Ĳ�������
	 * pagesizeArgu ָ���Ƿ�ҳ��ÿҳ���Ĳ�������
	 **/
	public String getSecondPageLink(String strPageName,String pageArgu,String pagesizeArgu) {
		if (this.rowCount <= PAGE_SIZE) //������������С�ڵ�ǰҳ���С������ʾ��ҳ����
			return "<input type='hidden' name='"+pagesizeArgu+"' value='" + PAGE_SIZE
					+ "'>";

		StringBuffer bfPageName = new StringBuffer(1000); // ���ص��ַ���
		StringBuffer sTemp = new StringBuffer(1000); // ���ص��ַ���
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		//ֻ����ǰҳ��Ϊ��ҳʱ�����ܳ�����ҳ����һ��İ�ť
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&"+pageArgu+"=");
		else
			bfPageName.append("?"+pageArgu+"=");

		strPageName = bfPageName.toString();

		if (currentPage > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >��һҳ</a>&nbsp;");
		}
		//ֻ����ǰҳ��ĩҳ�����ܳ�����һҳ��βҳ�İ�ť��
		if (this.currentPage < pageCount) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">��һҳ</a>&nbsp;");
		}

		if (pageCount > 0) {
			sTemp.append("��");
			sTemp.append(this.rowCount);
			sTemp.append("������, ��ʾ��");
			sTemp
					.append("<select size='1' name='"+pageArgu+"' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.pageCount; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i),
						currentPage);
			sTemp.append("</select> / ");
			sTemp.append(this.pageCount);
			sTemp.append("ҳ, ");
		}
		sTemp.append("��ʾ");
		sTemp.append("<select size='1' name='"+pagesizeArgu+"' onchange='javascript:refreshPage2("+pageArgu+","+pagesizeArgu+");' style='font-family: Tahoma; font-size: 7pt'>");
		
		
		sTemp.append(Argument.getPageSizeOptions(this.getPageSize()));
		sTemp.append("</select>�� / ҳ");
		return sTemp.toString();
	}

	//��ʽ��ҳ�ŵ���
	public String getPageLinkModal(String strPageName) {
		StringBuffer bfPageName = new StringBuffer(1000); // ���ص��ַ���
		StringBuffer sTemp = new StringBuffer(1000); // ���ص��ַ���
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		//ֻ����ǰҳ��Ϊ��ҳʱ�����ܳ�����ҳ����һ��İ�ť
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		//ֻ����ǰҳ��ĩҳ�����ܳ�����һҳ��βҳ�İ�ť��

		if (pageCount > 0) {
			sTemp.append("��");
			sTemp.append(this.rowCount);
			sTemp.append("������, ��ʾ��");
			sTemp
					.append("<select size='1' name='page' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.pageCount; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i),
						currentPage);
			sTemp.append("</select> / ");
			sTemp.append(this.pageCount);
			sTemp.append("ҳ, ");
		}
		sTemp.append("��ʾ");
		sTemp
				.append("<select size='1' name='pagesize' onchange='javascript:refreshPage();' style='font-family: Tahoma; font-size: 7pt'>");
		sTemp.append(Argument.getPageSizeOptions(this.getPageSize()));
		sTemp.append("</select>�� / ҳ");
		return sTemp.toString();
	}

	/**
	 * ����վ��ҳ��ʽ
	 * 
	 * @param strPageName
	 * @return
	 * @author caiyuan
	 * @date 2006-12-13
	 */
	public String getPageLinkEx(String strPageName) {
		if (this.rowCount <= PAGE_SIZE) //������������С�ڵ�ǰҳ���С������ʾ��ҳ����
			return "";

		StringBuffer bfPageName = new StringBuffer(1000); // ���ص��ַ���
		StringBuffer sTemp = new StringBuffer(1000); // ���ص��ַ���
		int nextPage = currentPage + 1;
		int prePage = currentPage - 1;
		bfPageName.append(strPageName);

		sTemp
				.append("<table class=\"pagetable\" width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
		sTemp.append("<tr><td align=\"center\" valign=\"middle\">");

		//ֻ����ǰҳ��Ϊ��ҳʱ�����ܳ�����ҳ����һ��İ�ť
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
							+ "\" ><img src=\"/portal/images/page/firstPage.gif\"  style=\"border:0\"  title=\"��һҳ\"  alt=\"��һҳ\" /></a>&nbsp;");
			sTemp
					.append("<a href=\""
							+ strPageName
							+ prePage
							+ "\" ><img src=\"/portal/images/page/prevPage.gif\"  style=\"border:0\"  title=\"��һҳ\"  alt=\"��һҳ\" /></a>&nbsp;");
		} else {
			sTemp
					.append("<img src=\"/portal/images/page/firstPageDisabled.gif\"  style=\"border:0\"  title=\"��һҳ\"  alt=\"��һҳ\" />&nbsp;");
			sTemp
					.append("<img src=\"/portal/images/page/prevPageDisabled.gif\"  style=\"border:0\"  title=\"��һҳ\"  alt=\"��һҳ\" />&nbsp;");
		}

		//����5����ҳ����
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

		//ֻ����ǰҳ��ĩҳ�����ܳ�����һҳ��βҳ�İ�ť��
		if (this.currentPage < pageCount) {
			sTemp
					.append("<a href=\""
							+ strPageName
							+ nextPage
							+ "\" ><img src=\"/portal/images/page/nextPage.gif\"  style=\"border:0\"  title=\"��һҳ\"  alt=\"��һҳ\" /></a>&nbsp;");
			sTemp
					.append("<a href=\""
							+ strPageName
							+ endPage
							+ "\" ><img src=\"/portal/images/page/lastPage.gif\"  style=\"border:0\"  title=\"���ҳ\"  alt=\"���ҳ\" /></a>&nbsp;");
		} else {
			sTemp
					.append("<img src=\"/portal/images/page/nextPageDisabled.gif\"  style=\"border:0\"  title=\"��һҳ\"  alt=\"��һҳ\" />&nbsp;");
			sTemp
					.append("<img src=\"/portal/images/page/lastPageDisabled.gif\"  style=\"border:0\"  title=\"���ҳ\"  alt=\"���ҳ\" />&nbsp;");
		}

		sTemp.append("��");
		sTemp.append(this.rowCount);
		sTemp.append("������");

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
			throw new BusiException("�����־ʧ��: " + e.getMessage());
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

	//�Ż�����
	/**
	 * ��ɾ�ĵķ�װ
	 * 
	 * @param cudSQl
	 *            �洢����
	 * @param params
	 *            ��������
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
					//NULLֵ�������͵ģ�������Object������Ч�����԰�����ΪString����
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
		if (errorCode < 0) //ֻ���д����ʱ��Ų���
			IntrustDBManager.handleResultCode(errorCode);
	}

	/**
	 * �����ķ�װ
	 * 
	 * @param appendSql
	 *            �洢����
	 * @param params
	 *            ��������
	 * @throws Exception
	 */
	public void append(String appendSql, Object[] params) throws Exception {
		this.cud(appendSql, params);
	}

	/**
	 * ���µķ�װ
	 * 
	 * @param updateSql
	 *            �洢����
	 * @param params
	 *            ��������
	 * @throws Exception
	 */
	public void update(String updateSql, Object[] params) throws Exception {
		this.cud(updateSql, params);
	}

	/**
	 * ɾ���ķ�װ
	 * 
	 * @param deleteSql
	 *            �洢����
	 * @param params
	 *            ��������
	 * @throws Exception
	 */
	public void delete(String deleteSql, Object[] params) throws Exception {
		this.cud(deleteSql, params);
	}

	/**
	 * ��ɾ�ĵķ�װ,��һ�������������
	 * 
	 * @param cudSql
	 *            �洢����
	 * @param params
	 *            ��������
	 * @param outParamPos
	 *            ���������λ��,�����˵�һλ�Ĵ�������������
	 * @param outParamType
	 *            �������������
	 * @return Object �������
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
				if (i >= tempOutParamPos) //������һ������������ڣ�����ʵ�ʵ����������λ�ý�����1
					trueIndex++;

				if (params[i] == null)
					//NULLֵ�������͵ģ�������Object������Ч�����԰�����ΪString����
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
		if (errorCode < 0) //ֻ���д����ʱ��Ų���
			IntrustDBManager.handleResultCode(errorCode);
		return ret;
	}

	/**
	 * ��ɾ�ĵķ�װ,�ж�������������
	 * 
	 * @param cudSql
	 *            �洢����
	 * @param params
	 *            ��������
	 * @param ourParamPos
	 *            ���������λ��,�����˵�һλ�Ĵ�������������
	 * @param outParamType
	 *            �������������
	 * @return Object �������
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
				for (int j = 0; j < outParamPos.length; j++) //�����ж������������ڣ�����ʵ�ʵ����������λ�ý�����N
				{
					int tempOutParamPos = outParamPos[j] - 2;
					if (i >= tempOutParamPos)
						trueIndex++;
				}

				stmt.setObject(trueIndex, params[i]);
			}
			//ע���������
			for (int j = 0; j < outParamPos.length; j++) {
				stmt.registerOutParameter(outParamPos[j], outParamType[j]);
			}

			stmt.executeUpdate();

			errorCode = stmt.getInt(1);
			//����������
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
		if (errorCode < 0) //ֻ���д����ʱ��Ų���
			IntrustDBManager.handleResultCode(errorCode);
		return ret;
	}

	/**
	 * ��ѯ�ķ�װ
	 * 
	 * @param listSql
	 *            �洢����
	 * @param params
	 *            ��������
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
					//NULLֵ�������͵ģ�������Object������Ч�����԰�����ΪString����
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
	 * ���ROWSET�����
	 * 
	 * @return rowset
	 */
	public sun.jdbc.rowset.CachedRowSet getRowset() {
		return rowset;
	}

	/**
	 * ��ҳ��ѯ�Ĳ���
	 * @param procSql �洢����
	 * @param params ����
	 */
   public IPageList listProcPage(String procSql,Object[] params,int pageIndex,int pageSize) throws BusiException
	{		
		return IntrustDBManager.listProcPage(procSql,params,pageIndex,pageSize);
	}

	@Override
	public void remove() {
		
	}
   
}