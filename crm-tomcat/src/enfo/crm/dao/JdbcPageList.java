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
 * @version 1.0 ��������������ע�͵�ģ��Ϊ ���� > ��ѡ�� > Java > �������� > �����ע��
 */

public class JdbcPageList implements IPageList {

	private int totalPage = 0; // �ܵ�ҳ����
	private int pageIndex = 1; // ��ǰ��ҳ��
	private int pageSize = 8; // һҳ��ʾ�ļ�¼�������Ϊ0���Ӧȫ�����
	private int totalSize = 0; // �ܼ�¼��
	private int blankSize = 0;// �����еĴ�С

	private List rsList = null; // �����ҳ�漯�����

	private String[] totalColumn; // ��Ҫͳ�ƺϼƵĽ���ֶ�����(ȫ���ϼ�)
	private Map totalResult = null; // ����ֶε�ͳ��ӳ��

	/**
	 * ������.
	 * 
	 * @param rs
	 *            �����������JDBC��ResultSet��������һ�λ�ȡ���н������ֱ��ȡ���ܼ�¼��
	 */
	public void populate(ResultSet rs) throws SQLException {
		rsList = new ArrayList();
		int iRow = 0;
		int rowStart = (pageIndex - 1) * pageSize; // ��ʼ����
		int rowEnd = rowStart + pageSize; // ��������

		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		BigDecimal[] totalValues = null; // ͳ�ƽ������
		if (totalColumn != null && totalColumn.length > 0) // ������Ҫͳ�ƵĽ���ֶ�
		{
			totalResult = new HashMap(totalColumn.length); // ��ʼ��ͳ�ƽ����ŵ�map
			totalValues = new BigDecimal[totalColumn.length]; // ��ʼ��ͳ�ƽ������
			for (int j = 0; j < totalColumn.length; j++) {
				totalValues[j] = new BigDecimal(0);
			}

		}
		
		while (rs.next()) // ���ݷ�ҳ������������һ��ҳ���С��List�����
		{
			iRow++;
			
						
			if ((iRow > rowStart && iRow <= rowEnd) || pageSize <= 0) // ȡ��ǰҳ���С���||pageSizeС�ڵ���0��ȡȫ�����
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
			if (totalColumn != null && totalColumn.length > 0) // ������Ҫͳ�ƵĽ���ֶ�
			{
				for (int j = 0; j < totalColumn.length; j++) {
					if (rs.getBigDecimal(totalColumn[j]) != null)
						totalValues[j] = totalValues[j].add(rs
								.getBigDecimal(totalColumn[j]));
				}

			}
		}
		// ��ͳ�ƺõĽ���ŵ����ӳ����ȥ
		if (totalColumn != null && totalColumn.length > 0) // ������Ҫͳ�ƵĽ���ֶ�
		{
			for (int j = 0; j < totalColumn.length; j++) {
				totalResult.put(totalColumn[j], totalValues[j]);
			}
		}

		totalSize = iRow;
		countPages(); // �����ܽ������С�����ܵ�ҳ����
	}

	/**
	 * ������.
	 * 
	 * @param rs
	 *            �����������JDBC��List<Map>��������һ�λ�ȡ���н������ֱ��ȡ���ܼ�¼��
	 */
	private void populate(List rs) {
		totalSize = rs.size();
		countPages(); // �����ܽ������С�����ܵ�ҳ����
		rsList = new ArrayList();
		int iRow = 0;
		int rowStart = (pageIndex - 1) * pageSize; // ��ʼ����
		int rowEnd = rowStart + pageSize; // ��������
		for (int i = 0; i < rs.size(); i++) // ���ݷ�ҳ������������һ��ҳ���С��List�����
		{
			iRow++;
			if ((iRow > rowStart && iRow <= rowEnd) || pageSize <= 0) // ȡ��ǰҳ���С���||pageSizeС�ڵ���0��ȡȫ�����
			{
				rsList.add(rs.get(i));
			}
		}
	}

	/**
	 * 
	 * ���ͳ�ƵĽ��
	 * 
	 * @param columnName
	 *            �ֶ�����
	 */
	public BigDecimal getTotalValue(String columnName) throws BusiException {
		if (totalResult == null)
			throw new BusiException("��δ��ʼ��ͳ��������Ϣ");
		return (BigDecimal) totalResult.get(columnName);
	}

	/**
	 * �����ܵĽ������������ҳ����.
	 */
	private void countPages() {
		// �����ʾȫ��
		if (pageSize <= 0) {
			totalPage = 1;
		} else// �����ܵ�ҳ����
		{
			blankSize = pageSize - rsList.size();// ���㲹���еĴ�С

			this.totalPage = this.totalSize / this.pageSize;
			if ((this.totalSize % this.pageSize) != 0)
				this.totalPage++;
		}
		// ��������ҳ�泬�����ҳ������ת�����һҳ
		if (pageIndex > totalPage) {
			pageIndex = totalPage;
		}
	}

	// ��ҳ����
	public String getPageLink(String strPageName) {
		 boolean b=true;


		 
		 if(strPageName.indexOf("ifhavaAll=1")>=0){
		 	 b=false;
		 }
		 	
		
		StringBuffer bfPageName = new StringBuffer(1000); // ���ص��ַ���
		StringBuffer sTemp = new StringBuffer(1000); // ���ص��ַ���
		int nextPage = pageIndex + 1;
		int prePage = pageIndex - 1;
		bfPageName.append(strPageName);

		// ֻ����ǰҳ��Ϊ��ҳʱ�����ܳ�����ҳ����һ��İ�ť
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (pageIndex > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >��һҳ</a>&nbsp;");
		}
		// ֻ����ǰҳ��ĩҳ�����ܳ�����һҳ��βҳ�İ�ť��
		if (this.pageIndex < totalPage) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">��һҳ</a>&nbsp;");
		}

		if (totalPage > 0) {
			sTemp.append("��");
			sTemp.append(totalSize);
			sTemp.append("������, ��ʾ��");
			sTemp
					.append("<select size='1' name='page_list' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.totalPage; i++)
				Argument
						.appendOptions(sTemp, i, Integer.toString(i), pageIndex);
			sTemp.append("</select> / ");
			sTemp.append(this.totalPage);
			sTemp.append("ҳ, ");
		}
		sTemp.append("��ʾ");
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
		sTemp.append("</select>�� / ҳ");
		return sTemp.toString();
	}
	
	// ��ҳ���� ���ʻ�
	public String getPageLink(String strPageName,Locale locale) {
		
	       boolean b=true;

	     if(strPageName.indexOf("ifhavaAll=1")>=0){
		 	 b=false;
		 }
		 	
	        
		StringBuffer bfPageName = new StringBuffer(1000); // ���ص��ַ���
		StringBuffer sTemp = new StringBuffer(1000); // ���ص��ַ���
		int nextPage = pageIndex + 1;
		int prePage = pageIndex - 1;
		bfPageName.append(strPageName);

		// ֻ����ǰҳ��Ϊ��ҳʱ�����ܳ�����ҳ����һ��İ�ť
		if (strPageName.indexOf("?") != -1)
			bfPageName.append("&page=");
		else
			bfPageName.append("?page=");

		strPageName = bfPageName.toString();

		if (pageIndex > 1) {
			sTemp.append("<a href=\"" + strPageName + prePage
					+ "\" >"+LocalUtilis.language("message.pageUp", locale)+"</a>&nbsp;");//��һҳ
		}
		// ֻ����ǰҳ��ĩҳ�����ܳ�����һҳ��βҳ�İ�ť��
		if (this.pageIndex < totalPage) {
			sTemp.append("<a href=\"" + strPageName + nextPage
					+ "\">"+LocalUtilis.language("message.pageDown", locale)+"</a>&nbsp;");//��һҳ
		}

		if (totalPage > 0) {
			sTemp.append(LocalUtilis.language("message.total2", locale));//��
			sTemp.append("&nbsp;"+totalSize+"&nbsp;");
			sTemp.append(LocalUtilis.language("message.items2", locale)+","+LocalUtilis.language("message.show", locale));//������, ��ʾ��
			sTemp
					.append("&nbsp;<select size='1' name='page_list' onchange=\"javascript:location='"
							+ bfPageName
							+ "' + this.value;\" style='font-family: Tahoma; font-size: 7pt'>");
			for (int i = 1; i <= this.totalPage; i++)
				Argument.appendOptions(sTemp, i, Integer.toString(i), pageIndex);
			sTemp.append("</select>&nbsp; / ");
			sTemp.append(this.totalPage);
			sTemp.append(""+LocalUtilis.language("message.page", locale)+", ");//ҳ
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
		
		sTemp.append(LocalUtilis.language("message.show2", locale));//��ʾ
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
		sTemp.append("</select>&nbsp;"+LocalUtilis.language("message.entries", locale)+" / "+LocalUtilis.language("message.page", locale));//�� / ҳ
		return sTemp.toString();
	}

	/**
	 * @return ���� pageIndex��
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex
	 *            ���� pageIndex��
	 */
	public void setPageIndex(int pageIndex) {
		if (pageIndex > 0)
			this.pageIndex = pageIndex;
	}

	/**
	 * @return ���� pageSize��
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            ���� pageSize��
	 */
	public void setPageSize(int pageSize) {
		if (pageSize != 0) // �����0����Ĭ��ֵ
			this.pageSize = pageSize;
	}

	/**
	 * @return ���� rsList��
	 */
	public List getRsList() {
		return rsList;
	}

	/**
	 * @param rsList
	 *            ���� rsList��
	 */
	public void setRsList(List rsList) {
		populate(rsList);
	}

	/**
	 * @return ���� totalPage��
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            ���� totalPage��
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return ���� totalSize��
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize
	 *            ���� totalSize��
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * �����Ҫ�����еĴ�С
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
