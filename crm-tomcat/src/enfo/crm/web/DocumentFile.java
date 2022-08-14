package enfo.crm.web;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import enfo.crm.affair.ServiceTaskLocal;
import enfo.crm.customer.CustomerLocal;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.intrust.AttachmentLocal;
import enfo.crm.intrust.BenChangeLocal;
import enfo.crm.intrust.BenifitorLocal;
import enfo.crm.intrust.ContractLocal;
import enfo.crm.intrust.DeployLocal;
import enfo.crm.intrust.MoneyDetailLocal;
import enfo.crm.intrust.PreContractLocal;
import enfo.crm.intrust.ProductLocal;
import enfo.crm.marketing.BenifiterQueryLocal;
import enfo.crm.marketing.PreMoneyDetailLocal;
import enfo.crm.marketing.SalesResultForStatisticLocal;
import enfo.crm.tools.Argument;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Format;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AttachmentVO;
import enfo.crm.vo.BenChangeVO;
import enfo.crm.vo.BenifitorVO;
import enfo.crm.vo.ContractVO;
import enfo.crm.vo.CustomerVO;
import enfo.crm.vo.DeployVO;
import enfo.crm.vo.GainTotalVO;
import enfo.crm.vo.MoneyDetailVO;
import enfo.crm.vo.PreContractVO;
import enfo.crm.vo.ServiceTaskVO;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DocumentFile {
	// private final String PROP_FILE = "c:\\config.ini";
	private final String SEPARATOR = java.io.File.separator;

	private final long MAXFILESIZE = 50000000;

	public com.jspsmart.upload.SmartUpload smartUpload = new com.jspsmart.upload.SmartUpload();

	private PageContext pageContext;

	private String strFolder = "C:\\Temp";

	private String file_name = "";

	private String toFolder;

	private com.jspsmart.upload.Request request;

	private boolean hasParse = false;

	public DocumentFile(PageContext in_pageContext) {
		// InputStream in;
		try {
			// in = new FileInputStream(PROP_FILE);
			// Properties properties = new Properties();
			// properties.load(in);
			// strFolder = properties.getProperty("document.Folder", "c:\\");
			pageContext = in_pageContext;
			smartUpload.initialize(pageContext);
			smartUpload.setTotalMaxFileSize(MAXFILESIZE);
		} catch (Exception e) {
			smartUpload = null;
			pageContext = null;
		}
	}

	public DocumentFile() {
	}

	/*
	 * public DocumentFile() { //InputStream in; try { //in = new
	 * FileInputStream(PROP_FILE); //Properties properties = new Properties();
	 * //properties.load(in); //strFolder =
	 * properties.getProperty("document.Folder", "c:\\"); pageContext =
	 * in_pageContext; smartUpload.initialize(pageContext);
	 * smartUpload.setTotalMaxFileSize(MAXFILESIZE); } catch (Exception e) {
	 * smartUpload = null; pageContext = null; } }
	 */

	public void parseRequest() throws Exception {
		if (hasParse)
			return;
		try {
			smartUpload.upload();
			request = smartUpload.getRequest();
			hasParse = true;
		} catch (Exception e) {
			request = null;
			throw new BusiException("�ļ��ϴ�ʧ��!" + e.getMessage());
		}
	}

	public boolean uploadFile(Integer file_id) throws Exception {
		if (!hasParse)
			parseRequest();

		if (file_id == null)
			return false;

		if (!insureFolder(strFolder))
			return false;
		toFolder = strFolder; // + SEPARATOR + file_id;

		if (!insureFolder(toFolder))
			return false;
		if (smartUpload.getFiles().getCount() == 0)
			return false;
		if (smartUpload.getFiles().getFile(0).getSize() == 0)
			return false;
		if (smartUpload.getFiles().getFile(0).getSize() > 10 * 1024 * 1024)
			throw new BusiException("�ļ���С���ܳ���10M��");
		// if (!deleteFolder(toFolder))
		// return false;

		try {
			smartUpload.save(toFolder,
					com.jspsmart.upload.SmartUpload.SAVE_PHYSICAL);

			file_name = smartUpload.getFiles().getFile(0).getFileName();
			Utility.debugln("success upload:"
					+ smartUpload.getFiles().getFile(0).getFileName());
			return true;
		} catch (Exception e) {
			throw new BusiException("�ļ��ϴ�ʧ��: " + e.getMessage());
		}
	}

	public boolean uploadFile(String strFolder) throws Exception {
		if (!hasParse)
			parseRequest();

		if (!insureFolder(strFolder))
			return false;
		toFolder = strFolder; // + SEPARATOR + file_id;

		if (!insureFolder(toFolder))
			return false;
		if (smartUpload.getFiles().getCount() == 0)
			return false;
		if (smartUpload.getFiles().getFile(0).getSize() == 0)
			return false;
		if (smartUpload.getFiles().getFile(0).getSize() > 10 * 1024 * 1024)
			throw new BusiException("�ļ���С���ܳ���10M��");

		try {
			smartUpload.save(toFolder,
					com.jspsmart.upload.SmartUpload.SAVE_PHYSICAL);
			Utility.debugln("success upload:"
					+ smartUpload.getFiles().getFile(0).getFileName() + " to "
					+ toFolder);
			return true;
		} catch (Exception e) {
			throw new BusiException("�ļ��ϴ�ʧ��: " + e.getMessage());
		}
	}

	// �����ĵ����������ݿ�
	public boolean updateToDB(Integer file_id) throws Exception {
		if (file_id == null)
			return false;

		Connection conn = CrmDBManager.getConnection();
		try {
			PreparedStatement stmt = conn
					.prepareStatement("UPDATE TDOCUMENT SET FILE_INFO=? WHERE FILE_ID="
							+ file_id);
			File file = new File(toFolder + SEPARATOR
					+ smartUpload.getFiles().getFile(0).getFileName());
			InputStream inputStream = new FileInputStream(file);
			stmt.setBinaryStream(1, inputStream, (int) (file.length()));
			stmt.executeUpdate();
			inputStream.close();
			file.delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusiException("�ļ�����ʧ��: " + e.getMessage());
		} finally {
			conn.close();
		}
	}

	public boolean deleteFile(Integer file_id) {
		if (file_id == null)
			return false;
		if (!insureFolder(strFolder))
			return false;
		String toFolder = strFolder + SEPARATOR + file_id;
		if (!insureFolder(toFolder))
			return false;
		return deleteFolder(toFolder);
	}

	public String getParameter(String name) {
		if (request == null)
			return null;
		return request.getParameter(name);
	}

	public Enumeration getParameterNames() {
		if (request == null)
			return null;
		return request.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		if (request == null)
			return null;
		return request.getParameterValues(name);
	}

	private boolean insureFolder(String strFolder) {
		java.io.File dir = new java.io.File(strFolder);
		if (!dir.isDirectory())
			dir.mkdirs();
		return dir.isDirectory();
	}

	private boolean deleteFolder(String strFolder) {
		if (!insureFolder(strFolder))
			return false;

		java.io.File dir = new java.io.File(strFolder);
		java.io.File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			java.io.File f = files[i];
			f.delete();
		}
		return true;
	}

	private String Encode(String in) {
		try {
			return new String(in.getBytes("GBK"), "ISO8859_1");
		} catch (Exception e) {
			return in;
		}
	}

	private void download(String strFile) throws Exception {

		java.io.File file = new java.io.File(strFile);
		String filen = "";
		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) (pageContext
				.getResponse());
		if (!file.exists())
			throw new BusiException("�ļ������ڣ��п��ܸ��ļ��ѱ�ɾ����");
		try {

			response.setContentType("application/octet-stream");

			response.addHeader("Content-disposition", Encode("inline;filename="
					+ file.getName()));
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			OutputStream os = response.getOutputStream();
			byte[] buf = new byte[1024];
			int left = (int) file.length();
			int read = 0;
			while (left > 0) {
				read = dis.read(buf);
				left -= read;
				os.write(buf, 0, read);
			}
			dis.close();
			os.close();
			out.close();
		} finally {

			file.delete();
		}
	}

	private void downloadJsp(java.io.File file) throws Exception {
		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) (pageContext
				.getResponse());
		if (!file.exists()) {
			throw new Exception("������ļ�" + file.getName() + "�����ڣ�");
		}
		out.clear();
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition",
				Encode("attachment; filename=" + file.getName()));
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		OutputStream os = response.getOutputStream();
		byte[] buf = new byte[1024];
		int read = 0;
		read = dis.read(buf);
		while (read != -1) {
			os.write(buf, 0, read);
			read = dis.read(buf);
		}
		dis.close();
		os.close();
		out.close();

	}

	public boolean downloadFile(Integer file_id) throws Exception {
		if (file_id == null)
			return false;
		Connection conn = CrmDBManager.getConnection();
		try {
			PreparedStatement stmt = conn
					.prepareStatement("SELECT FILE_FULL_NAME, FILE_INFO FROM TDOCUMENT WHERE FILE_ID="
							+ file_id);
			ResultSet r = stmt.executeQuery();
			r.next();
			String filename = strFolder + SEPARATOR
					+ r.getString("FILE_FULL_NAME");
			// Blob blob = r.getBlob("FILE_INFO");

			InputStream inputStream = r.getBinaryStream("FILE_INFO");

			// InputStream inputStream = blob.getBinaryStream();
			FileOutputStream fo = new FileOutputStream(filename);

			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer)) != -1)
				fo.write(buffer, 0, bytesRead);
			fo.flush();
			fo.close();
			stmt.close();
			download(filename);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusiException("�ļ�����ʧ��: " + e.getMessage());
		} finally {
			conn.close();
		}
	}

	public static boolean renamefile(String filepath, String destname) {
		boolean result = false;
		File f = new File(filepath);
		String fileParent = f.getParent();
		String filename = f.getName();
		File rf = new File(fileParent + "//" + destname);
		if (f.renameTo(rf)) {
			result = true;
		}
		f = null;
		rf = null;
		return result;
	}

	public void downloadProblemFile(String strFile) throws Exception {
		java.io.File file = new java.io.File(strFile);
		String filen = "";
		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) (pageContext
				.getResponse());
		if (!file.exists())
			throw new BusiException("�ļ������ڣ��п��ܸ��ļ��ѱ�ɾ����");
		DataInputStream dis = null;
		OutputStream os = null;
		try {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-disposition", // inline

					Encode("attachment;filename=" + file.getName()));
			dis = new DataInputStream(new FileInputStream(file));
			os = response.getOutputStream();
			byte[] buf = new byte[1024];
			int left = (int) file.length();
			int read = 0;
			while (left > 0) {
				read = dis.read(buf);
				left -= read;
				os.write(buf, 0, read);
			}

		} catch (Exception e) {
			throw new BusiException("���ļ�ʧ�ܣ���ȷ���Ƿ���Ȩ���Ķ�!");
		} finally {
			os.close();
			dis.close();
			out.close();
		}
	}

	/**
	 * ADD BY TSG 2009-03-13 ��ȡ�����Ӧ���ļ���ʽ
	 */

	public String getFileNameByServiceId(Integer service_id) throws Exception {
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		String files_name = "";
		String files_format_name = "";
		try {
			rs = stmt
					.executeQuery("SELECT FILES_NAME FROM BC_FILE_DEFINE WHERE FILES_ID IN(SELECT FILES_ID FROM BC_SERVICE WHERE SERVICE_ID = "
							+ service_id + ")");

			while (rs.next()) {
				files_name = Utility.trimNull(rs.getString("FILES_NAME"));
				break;
			}
		} catch (Exception e) {
			throw new BusiException("��ȡ�ļ���ʽ��Ϣ����:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		int len = files_name.length();
		int start_index = files_name.indexOf(".");
		if (start_index != -1) {
			files_format_name = files_name.substring(start_index, len);
		}
		return files_format_name;
	}

	/**
	 * ADD BY TSG 2009-03-11 ��ȡ�û�ID
	 */

	public int getUserId() throws Exception {
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		int user_id = 0;
		try {
			rs = stmt.executeQuery("SELECT * FROM TSYSTEMINFO");

			while (rs.next()) {
				user_id = rs.getInt("USER_ID");

			}
		} catch (Exception e) {
			throw new BusiException("��ȡ���м�����Ϣ����:" + e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return user_id;
	}

	/**
	 * ADD BY TSG 2009-03-11 ���¸��ٱ���ļ�״̬
	 */

	public void updateFileStatus(Integer trace_id) throws Exception {
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		try {
			stmt
					.executeUpdate("UPDATE BC_SERVICE_TRACE SET FILES_STATE = 2 WHERE TRACE_ID = "
							+ trace_id);
		} catch (Exception e) {
			throw new BusiException("��ȡ���м�����Ϣ����:" + e.getMessage());
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * ͨ��IO�������ļ�
	 * 
	 * @param file
	 * @throws Exception
	 */
	public OutputStream getResponseStream(String filename) throws Exception {
		Utility.debugln("filename is " + filename.toUpperCase());
		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) (pageContext
				.getResponse());
		out.clear();
		out = pageContext.pushBody();
		response.reset();
		response.setHeader("Content-disposition",
				Encode("attachment; filename=" + filename));
		response.setContentType("APPLICATION/msexcel");
		OutputStream outputstream = response.getOutputStream();
		return outputstream;
	}

	public void custExcel(Integer input_bookCode, Integer invest_cust_id,
			Integer trade_date, Integer item_type, WritableSheet ws,
			String exceltitle, String[] titleName, String[] fieldType,
			List leftList, int right) throws Exception {
		try {
			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, exceltitle);
			ws.addCell(labelC0);
			int p = 0, q = 0;
			if (right == 0) {
				p = 0;
				q = titleName.length;
			}
			if (right == 1) {
				p = titleName.length;
				q = p + titleName.length;
			}
			for (int i = p; i < q; i++) {
				String sName = "";
				if (right == 0) {
					sName = titleName[i];
				}
				if (right == 1) {
					sName = titleName[i - titleName.length];
				}
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName);
				ws.addCell(labelC1);
			}
			int m = 0, n = 0;
			for (int j = 0; j < leftList.size(); j++) {
				if (right == 0) {
					m = 0;
					n = fieldType.length;
				}
				if (right == 1) {
					m = fieldType.length;
					n = m + fieldType.length;
				}
				for (int k = m; k < n; k++) {
					Map rowMap1 = (Map) leftList.get(j);
					Integer item_id1 = Utility.parseInt(Utility
							.trimNull(rowMap1.get("ITEM_ID")), null);
					String item_name1 = Utility.trimNull(rowMap1
							.get("ITEM_NAME"));

					Integer line_id1 = Utility.parseInt(Utility
							.trimNull(rowMap1.get("LINE_ID")), null);
					BigDecimal item_balance1 = Argument.getCust_balance(
							input_bookCode, invest_cust_id, trade_date,
							item_type, item_id1, 1);

					BigDecimal item_balance2 = Argument.getCust_balance(
							input_bookCode, invest_cust_id, trade_date,
							item_type, item_id1, 2);
					int iType = 0;
					if (right == 0) {
						iType = Utility.parseInt(fieldType[k], 0);
					}
					if (right == 1) {
						iType = Utility.parseInt(fieldType[k - 4], 0);
					}
					switch (iType) {
					case 0: {
						break;
					}
					case 1: {
						String fieldValue = item_name1;
						jxl.write.Label labelValue0 = new jxl.write.Label(k,
								j + 2, Utility.trimNull(fieldValue));
						ws.addCell(labelValue0);

						break;
					}
					case 2: // BigDecimal��ֵ���͵�
					{
						BigDecimal fiedvalue = item_balance2;

						double tempmoney = (fiedvalue == null ? 0 : fiedvalue
								.doubleValue());
						jxl.write.NumberFormat nf = new jxl.write.NumberFormat(
								"#.##");
						jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(
								nf);
						jxl.write.Number labe4NF = new jxl.write.Number(k,
								j + 2, tempmoney, wcfN);

						ws.addCell(labe4NF);
						break;
					}
					case 3: {
						Integer fieldValue = line_id1;
						String tmepvalue = "";
						if (fieldValue != null)
							tmepvalue = fieldValue.toString();

						jxl.write.Label labelValue0 = new jxl.write.Label(k,
								j + 2, Utility.trimNull(tmepvalue));

						ws.addCell(labelValue0);
						break;
					}
					case 4: // ȡitem_balance1��ֵ
					{
						BigDecimal fiedvalue = item_balance1;

						double tempmoney = (fiedvalue == null ? 0 : fiedvalue
								.doubleValue());
						jxl.write.NumberFormat nf = new jxl.write.NumberFormat(
								"#.##");
						jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(
								nf);
						jxl.write.Number labe4NF = new jxl.write.Number(k,
								j + 2, tempmoney, wcfN);

						ws.addCell(labe4NF);
						break;
					}
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		}
	}

	/**
	 * ��ô�ӡ�ͻ���Ϣ�Ĳ���
	 * 
	 * @return
	 */
	public CustomerVO getPringCustParams() {
		CustomerVO vo = new CustomerVO();
		String cust_no = Utility.trimNull(pageContext.getRequest()
				.getParameter("cust_no"));
		String cust_name = Utility.trimNull(pageContext.getRequest()
				.getParameter("cust_name"));
		Integer cust_type = Utility.parseInt(pageContext.getRequest()
				.getParameter("cust_type"), new Integer(0));
		String cust_level = Utility.trimNull(pageContext.getRequest()
				.getParameter("cust_level"));
		String cust_source = Utility.trimNull(pageContext.getRequest()
				.getParameter("cust_source"));
		String card_type = Utility.trimNull(pageContext.getRequest()
				.getParameter("card_type"));// ֤������
		String card_id = Utility.trimNull(pageContext.getRequest()
				.getParameter("card_id"));// ֤������
		String vip_card_id = Utility.trimNull(pageContext.getRequest()
				.getParameter("vip_card_id"));// VIP�����
		String hgtzr_bh = Utility.trimNull(pageContext.getRequest()
				.getParameter("hgtzr_bh"));// �ϸ�Ͷ���˱��
		Integer birthday = Utility.parseInt(pageContext.getRequest()
				.getParameter("birthday"), new Integer(0));// ��������
		Integer start_rg_times = Utility.parseInt(pageContext.getRequest()
				.getParameter("start_rg_times"), new Integer(0));// ��ʼ�������
		Integer end_rg_times = Utility.parseInt(pageContext.getRequest()
				.getParameter("end_rg_times"), new Integer(0));// �����������
		BigDecimal min_total_money = Utility.stringToDouble(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"min_total_money")));// ��͹�����
		BigDecimal max_total_money = Utility.stringToDouble(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"max_total_money")));// ��߹�����
		BigDecimal ben_amount_min = Utility.stringToDouble(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"ben_amount_min")));// ���������
		BigDecimal ben_amount_max = Utility.stringToDouble(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"ben_amount_max")));// ���������
		String touch_type = Utility.trimNull(pageContext.getRequest()
				.getParameter("touch_type"));// ��ϵ��ʽ
		String cust_tel = Utility.trimNull(pageContext.getRequest()
				.getParameter("cust_tel"));// ��ϵ�绰
		String post_address = Utility.trimNull(pageContext.getRequest()
				.getParameter("post_address"));// ��ϵ��ַ
		Integer product_id = Utility.parseInt(pageContext.getRequest()
				.getParameter("product_id"), new Integer(0));// ��ƷID
		Integer sub_product_id = Utility.parseInt(pageContext.getRequest()
				.getParameter("sub_product_id"), new Integer(0));// �Ӳ�ƷID
		Integer is_link = Utility.parseInt(pageContext.getRequest()
				.getParameter("is_link"), new Integer(0));// �Ƿ񰴹�������ѯ
		Integer onlyemail = Utility.parseInt(pageContext.getRequest()
				.getParameter("onlyemail"), new Integer(0));// �Ƿ�����ʼ�
		String family_name = Utility.trimNull(pageContext.getRequest()
				.getParameter("family_name"));// ��ͥ����
		String sort_name = Utility.trimNull(pageContext.getRequest()
				.getParameter("sort_name"));// �����ֶ�
		String prov_level = Utility.trimNull(pageContext.getRequest()
				.getParameter("prov_level"));// ���漶��
		Integer input_operatorCode = Utility.parseInt(Utility
				.trimNull(pageContext.getRequest().getParameter("input_man")),
				new Integer(0));

		Integer is_deal = Utility.parseInt(pageContext.getRequest()
				.getParameter("is_deal"), new Integer(0));
		Integer group_id = Utility.parseInt(pageContext.getRequest()
				.getParameter("group_id"), new Integer(0));
		Integer export_flag = Utility.parseInt(pageContext.getRequest()
				.getParameter("export_flag"), new Integer(0));
		
		String ip = Utility.trimNull(pageContext.getRequest().getRemoteAddr());
		String mac = Utility.getClientMacAddr(ip);

		if(mac==""||mac==null){
			mac="δ�ҵ���ַ��";
		}
		// ��������
		vo.setCust_no(cust_no);
		vo.setCust_name(cust_name);
		vo.setCust_type(cust_type);
		vo.setCust_level(cust_level);
		vo.setCust_source(cust_source);
		vo.setInput_man(input_operatorCode);
		vo.setCard_type(card_type);
		vo.setCard_id(card_id);
		vo.setVip_card_id(vip_card_id);
		vo.setHgtzr_bh(hgtzr_bh);
		vo.setBirthday(birthday);
		vo.setMin_times(start_rg_times);
		vo.setMax_times(end_rg_times);
		vo.setMin_total_money(min_total_money);
		vo.setMax_total_money(max_total_money);
		vo.setBen_amount_min(ben_amount_min);
		vo.setBen_amount_max(ben_amount_max);
		vo.setTouch_type(touch_type);
		vo.setCust_tel(cust_tel);
		vo.setPost_address(post_address);
		vo.setProduct_id(product_id);
		vo.setSub_product_id(sub_product_id);
		vo.setIs_link(is_link);
		vo.setOnlyemail(onlyemail);
		vo.setFamily_name(family_name);
		vo.setOrderby(sort_name);
		vo.setProv_level(prov_level);
		vo.setInput_man(input_operatorCode);
		vo.setIs_deal(is_deal);
		vo.setGroupID(group_id);
		vo.setExport_flag(export_flag);
		vo.setIp(ip);
		vo.setMac(mac);
		return vo;
	}

	/**
	 * �����������۸��µ����õ�ģ��excel
	 */
	public void exportPurchaseImportChange(String objPath) throws Exception {
		String fileName = objPath;		

		Integer product_id =  Utility.parseInt(pageContext.getRequest().getParameter("product_id"), new Integer(0));
		Integer op_code =  Utility.parseInt(pageContext.getRequest().getParameter("op_code"), new Integer(0));
		ContractVO vo = new ContractVO();
		vo.setProduct_id(product_id);
		vo.setContract_sub_bh("");
		vo.setInput_man(op_code);		

		try {
			ContractLocal local = EJBFactory.getContract();
			List list = local.listContractForPurchaseImportChange(vo);
			
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			
			// DATE: ��Ӧ20130901������INT�͵�����
			String[] fields = {"��Ŀ����","PRODUCT_NAME", "STR"	,"��ͬ���","CONTRACT_SUB_BH","STR"
				,"��ͬǩ������","QS_DATE","DATE", "�ɿ�����","JK_DATE","DATE"
				,"��ͬ��ֹ����","END_DATE","DATE", "��ͬ����","VALID_PERIOD","INT"
				/*,"���޵�λ","PERIOD_UNIT","INT"*/, "��ͬ���","TO_MONEY","DEC"
				,"ί����","CUST_NAME","STR", "ί��������","CUST_TYPE_NAME","STR"				
				,"ί����֤������","CARD_TYPE_NAME","STR", "ί����֤�����","CARD_ID","STR"
				,"ί���˵�ַ","POST_ADDRESS","STR", "ί�����ʱ�","POST_CODE","STR"
				,"ί���˷��˴���","LEGAL_MAN","STR", "ί������ϵ��ʽ","TOUCH_TYPE_NAME","STR"
				,"ί���˹̶��绰","CUST_TEL","STR"	,"ί�����ֻ�","MOBILE","STR"
				,"ί���˵����ʼ�","E_MAIL","STR"
				,"������","BENIFITOR_NAME","STR", "����������","BENIFITOR_TYPE_NAME","STR"
				,"������֤������","BENIFITOR_CARD_TYPE_NAME","STR", "������֤�����","BENIFITOR_CARD_ID","STR"
				,"�����˵�ַ","BENIFITOR_POST_ADDRESS","STR", "�������ʱ�","BENIFITOR_POST_CODE","STR"
				,"��������ϵ��ʽ","BENIFITOR_TOUCH_TYPE_NAME","STR", "�����˹̶��绰","BENIFITOR_CUST_TEL","STR"
				,"�������ֻ�","BENIFITOR_MOBILE","STR", "�����˵����ʼ�","BENIFITOR_E_MAIL","STR"
				,"�����˷��˴���","BENIFITOR_LEGAL_MAN","STR"
				,"������������","BANK_NAME","STR", "֧������","BANK_SUB_NAME","STR"
				,"�����˺�","BANK_ACCT","STR", "��������","GAIN_ACCT","STR"
				,"�Ʋ����","ENTITY_TYPE_NAME","STR", "�Ʋ�����","ENTITY_NAME","STR"
				,"�ɿʽ","JK_TYPE_NAME","STR", "�Ϲ��ѿۿʽ","FEE_JK_TYPE","STR"
				,"�������ȼ���","PROV_FLAG_NAME","STR"};

			exportExcel(ws, fileName, fields, list);
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("�����ļ�ʧ��:" + e.getMessage());
		}
	}
	
	private void exportExcel(WritableSheet ws, String excelTitle, String[] fields, List list) throws Exception {
		try {
			WritableFont font = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setAlignment(Alignment.CENTRE);
			ws.mergeCells(0, 0, (fields.length/3) - 1, 0);
			jxl.write.Label labelC = new jxl.write.Label(0, 0, excelTitle,
					format);
			ws.addCell(labelC);

			for (int i = 0; i < fields.length/3; i++) {
				Label lableTitle = new jxl.write.Label(i, 1, fields[i*3]);
				ws.addCell(lableTitle);
			}
			
			Iterator iter = list.iterator();
			int line = 2;
			while (iter.hasNext()) {
				Map map = (Map) iter.next();

				for (int i = 0; i < fields.length/3; i++) {					
					String name = fields[i*3+1];
					String type = fields[i*3+2];					
					
					if ("STR".equals(type) || "INT".equals(type)) {
						Label lableValue = new Label(i, line, Utility.trimNull(map.get(name)));
						ws.addCell(lableValue);
					} else if ("DEC".equals(type)) { // BigDecimal��ֵ���͵�(������λ)
						Label lableValue = new Label(i, line, Format
								.formatMoney(Utility.stringToDouble(Utility
										.trimNull(map.get(name)))));
						ws.addCell(lableValue);
					} else if ("DATE".equals(type)) { // Integerת����
						Integer value = Utility.parseInt((Integer)map.get(name), new Integer(0));
						String tempValue = Format.formatDateLine(value);
						Label lableValue = new jxl.write.Label(i, line, tempValue);
						ws.addCell(lableValue);
					}		
				}

				line ++;
			}

		} catch (Exception e) {
			throw new BusiException("�����ļ�ʧ��:" + e.getMessage());
		}
	}
	
	/**
	 * �����ͻ���Ϣ
	 * 
	 * @author dingyj
	 * @since 2009-12-15
	 * @param objPath
	 *            �ļ���
	 */
	public void exportExcelClientInfo(String objPath,String fields[][]) throws Exception {
		String fileName = objPath;
		CustomerLocal local = EJBFactory.getCustomer();
		Integer cust_id =new Integer(0);
		IPageList pageLists=null;
		List list = null;
		List arrayList = new ArrayList();
		List custList = new ArrayList();
		Map map =new HashMap();
		
		String[] cust_ids = pageContext.getRequest().getParameterValues(
				"cust_id");
		int tPage = Utility.parseInt(Utility.trimNull(pageContext.getRequest()
				.getParameter("page")), 1);
		int tPagesize = Utility.parseInt(Utility.trimNull(pageContext
				.getRequest().getParameter("pagesize")), 8);
		Integer input_man = Utility.parseInt(pageContext.getRequest().getParameter("input_man"), new Integer(0));
		String ip = Utility.trimNull(pageContext.getRequest().getRemoteAddr());
		String mac = Utility.getClientMacAddr(ip);
		
		
		//��ȡ�����
		if (cust_ids != null) {
			for (int i = 0; i < cust_ids.length; i++) {
				
				if (cust_ids[i] != null && cust_ids[i].length() > 0) {
					custList.add(Utility.parseInt(Utility.trimNull(cust_ids[i]),
							new Integer(0)));
					cust_id = Utility.parseInt(cust_ids[i], new Integer(0));
				}
				//��ö���
				CustomerLocal locals = EJBFactory.getCustomer();
				CustomerVO cust_vo = new CustomerVO();
				cust_vo.setCust_id(cust_id);
				cust_vo.setInput_man(input_man);
				cust_vo.setIp(ip);
				cust_vo.setMac(mac);
				if (i==0)
					cust_vo.setExport_flag(new Integer(1)); // �����ͻ���Ϣ
				else 
					cust_vo.setExport_flag(new Integer(10)); // һ���ε���ֻ��һ��log
				
				pageLists = locals.listProcAllExt(cust_vo, tPage,
						tPagesize);
				list=pageLists.getRsList();
				
//				��ȡѡ���ѡ��
				if(list!=null && list.size()>0){
					map = (Map) list.get(0);
					for (int j = 0; j < custList.size(); j++) {
						if (custList.get(j).equals(
								Utility.parseInt(Utility.trimNull(map
										.get("CUST_ID")), new Integer(0)))) {
							arrayList.add(map);
							custList.remove(j);
						}
					}
				}
				
			}
		}

		try {
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			/*String[] titleName = { "���", "�ͻ���", "�ͻ�����", "��������", "֤������", "֤������",
					"�Ϲ����", "ת�ý��", "�и����", "������", "��������ʱ��", "�ͻ��ȼ�", "�̶��绰",
					"�ֻ�", "����", "��ַ", "��ע" };
			String[] fieldName = { "CUST_NO", "CUST_NAME", "CUST_TYPE_NAME",
					"BIRTHDAY", "CARD_TYPE_NAME", "CARD_ID", "TOTAL_MONEY",
					"EXCHANGE_AMOUNT", "BACK_AMOUNT", "BEN_AMOUNT",
					"LAST_RG_DATE", "CUST_LEVEL_NAME", "CUST_TEL", "MOBILE",
					"E_MAIL", "POST_ADDRESS", "SUMMARY" };
			String[] fieldType = { "1", "1", "1", "6", "1", "1", "2", "2", "2",
					"2", "6", "1", "1", "1", "1", "1", "1" };
					*/
			String[] titleName=fields[1];
			String[] fieldName=fields[0];
			String[] fieldType=fields[2];
			clientExcel(ws, fileName, titleName, fieldName, fieldType,
					arrayList);
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("�����ļ�ʧ��:" + e.getMessage());
		}
	}
	
	/**
	 * ������������Ϣ
	 * 
	 * @author huangsl
	 * @since 2013-03-05
	 * @param objPath
	 *            �ļ���
	 */
	public void exportExcelBeneficInfo(String objPath) throws Exception {
		String fileName = objPath;
		BenifitorLocal benifitor = EJBFactory.getBenifitor();
		BenifitorVO vo_ben = new BenifitorVO();
		
		String q_contract_sub_bh = Utility.trimNull(pageContext.getRequest().getParameter("q_contract_sub_bh"));
		String q_cust_no = Utility.trimNull(pageContext.getRequest().getParameter("q_cust_no"));
		String q_cust_name = Utility.trimNull(pageContext.getRequest().getParameter("q_cust_name"));
		String q_productCode = Utility.trimNull(pageContext.getRequest().getParameter("q_productCode"));
		String q_product_name=Utility.trimNull(pageContext.getRequest().getParameter("q_product_name"));
		Integer q_productId = Utility.parseInt(pageContext.getRequest().getParameter("q_productId"),new Integer(0));
		Integer begin_date = Utility.parseInt(pageContext.getRequest().getParameter("begin_date"),new Integer(0));
		Integer end_date = Utility.parseInt(pageContext.getRequest().getParameter("end_date"),new Integer(0));
		Integer input_operatorCode = Utility.parseInt(pageContext.getRequest().getParameter("input_operatorCode"),new Integer(0));
		vo_ben.setBook_code(new Integer(1));
		vo_ben.setContract_sub_bh(q_contract_sub_bh);
		vo_ben.setCust_no(q_cust_no);
		vo_ben.setSerial_no(new Integer(0));
		vo_ben.setFunction_id(new Integer(100));
		vo_ben.setInput_man(input_operatorCode);
		vo_ben.setProduct_id(q_productId);
		vo_ben.setProduct_name(q_product_name);
		vo_ben.setCust_name(q_cust_name);

		vo_ben.setStart_date(begin_date);
		vo_ben.setEnd_date(end_date); 
		
		List list = benifitor.queryModi1(vo_ben);
		List arrayList = new ArrayList();
		Iterator iterator = list.iterator();

		while(iterator.hasNext()){
			Map hashmap = new HashMap();
			Map map = (Map)iterator.next();

			String cust_name = Utility.trimNull(map.get("CUST_NAME"));
			String card_id = Utility.trimNull(map.get("CARD_ID"));
			String product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
			String contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
			String list_id = Utility.trimNull(map.get("LIST_ID"));
			
			String bank_name = Utility.trimNull(map.get("BANK_NAME"));
			String bank_sub_name =  Utility.trimNull(map.get("BANK_SUB_NAME"));
			String bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
			String cust_acct_name = Utility.trimNull(map.get("CUST_ACCT_NAME"));
			
			hashmap.put("CUST_NAME",cust_name);
			hashmap.put("CARD_ID",card_id);
			hashmap.put("PRODUCT_NAME",product_name);
			hashmap.put("CONTRACT_SUB_BH",contract_sub_bh+" - "+list_id);
			hashmap.put("BANK_NAME",bank_name+bank_sub_name);
			hashmap.put("BANK_ACCT",bank_acct);
			hashmap.put("CUST_ACCT_NAME",cust_acct_name);
			
			arrayList.add(hashmap);			
		}
		try{
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			String[] titleName = { "����������", "֤����", "��Ʒ����", "��ͬ���", "������������", "���������˺�","�����˻�����"};
			String[] fieldName = { "CUST_NAME", "CARD_ID","PRODUCT_NAME", "CONTRACT_SUB_BH", "BANK_NAME", "BANK_ACCT","CUST_ACCT_NAME"};
			String[] fieldType = { "1", "1", "1", "1", "1", "1", "1"};

			clientExcel(ws, fileName, titleName, fieldName, fieldType,
					arrayList);
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		}catch(Exception e){
			throw new BusiException("�����ļ�ʧ��:" + e.getMessage());
		}
	}

	/**
	 * ���õ����ͻ���Ϣ
	 * 
	 * @author dingyj
	 * @since 2009-12-15
	 * @param ws
	 * @param excelTitle
	 * @param titleName
	 * @param fieldType
	 * @param list
	 */
	private void clientExcel(WritableSheet ws, String excelTitle,
			String[] titleName, String[] fieldName, String[] fieldType,
			List list) throws Exception {
		try {
			BigDecimal total_money = new BigDecimal(0.000); // �ѹ����ϼ�
			BigDecimal ben_amount = new BigDecimal(0.000); // ������ϼ�
			BigDecimal bxchange_amount = new BigDecimal(0.000); // ת�ý��ϼ�
			BigDecimal back_amount = new BigDecimal(0.000); // �Ҹ����ϼ�
			WritableFont font = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setAlignment(Alignment.CENTRE);
			ws.mergeCells(0, 0, (fieldName.length) - 1, 0);
			jxl.write.Label labelC = new jxl.write.Label(0, 0, excelTitle,
					format);
			ws.addCell(labelC);

			for (int i = 0; i < titleName.length; i++) {
				Label lableTitle = new jxl.write.Label(i, 1, titleName[i]);
				ws.addCell(lableTitle);
			}
			for (int j = 0; j < list.size(); j++) {
				Map map = (Map) list.get(j);
				if (map.get("TOTAL_MONEY") != null)
					total_money = total_money.add(new BigDecimal(Utility
							.trimNull(map.get("TOTAL_MONEY"))));
				if (map.get("BEN_AMOUNT") != null)
					ben_amount = ben_amount.add(new BigDecimal(Utility
							.trimNull(map.get("BEN_AMOUNT"))));
				if (map.get("EXCHANGE_AMOUNT") != null)
					bxchange_amount = bxchange_amount.add(new BigDecimal(
							Utility.trimNull(map.get("EXCHANGE_AMOUNT"))));
				if (map.get("BACK_AMOUNT") != null)
					back_amount = back_amount.add(new BigDecimal(Utility
							.trimNull(map.get("BACK_AMOUNT"))));
				for (int k = 0; k < fieldName.length; k++) {
					int sfieldType = Utility.parseInt(fieldType[k], 0); // ��������
					String sfieldName = fieldName[k];
					switch (sfieldType) {
					case 0: {
						Label lableValue = new Label(k, j + 2, Utility
								.trimNull(map.get(sfieldName)));
						ws.addCell(lableValue);
						break;
					}
					case 1: // String��ֵ���͵�
					{
						String value = Utility.trimNull(map.get(sfieldName));
						Label lableValue = new jxl.write.Label(k, j + 2, value);
						ws.addCell(lableValue);
						break;
					}
					case 2: // BigDecimal��ֵ���͵�(������λ)
					{
						Label lableValue = new Label(k, j + 2, Format
								.formatMoney(Utility.stringToDouble(Utility
										.trimNull(map.get(sfieldName)))));
						ws.addCell(lableValue);
						break;
					}
					case 6: // Integerת����
					{
						Integer value = Utility.parseInt(Utility.trimNull(map
								.get(sfieldName)), new Integer(0));
						String tempValue = Format.formatDateLine(value);
						Label lableValue = new jxl.write.Label(k, j + 2,
								tempValue);
						ws.addCell(lableValue);
					}
					default:
						break;
					}
				}

			}
			int index = 0;

			for (int p = 0; p < titleName.length; p++) {
				Label labelValue = new Label(0, list.size() + 2, "�ϼ�");
				ws.addCell(labelValue);
				if (titleName[p] == "�Ϲ����") {
					Label labelValue1 = new Label(index, list.size() + 2,
							Format.formatMoney(total_money).toString());
					ws.addCell(labelValue1);
				}
				if (titleName[p] == "ת�ý��") {
					Label labelValue1 = new Label(index, list.size() + 2,
							Format.formatMoney(bxchange_amount).toString());
					ws.addCell(labelValue1);
				}
				if (titleName[p] == "�и����") {
					Label labelValue1 = new Label(index, list.size() + 2,
							Format.formatMoney(back_amount).toString());
					ws.addCell(labelValue1);
				}
				if (titleName[p] == "������") {
					Label labelValue1 = new Label(index, list.size() + 2,
							Format.formatMoney(ben_amount).toString());
					ws.addCell(labelValue1);
				}
				index++;
			}

		} catch (Exception e) {
			throw new BusiException("�����ļ�ʧ��:" + e.getMessage());
		}
	}

	/**
	 * �����ͻ�ͨѶ¼��Ϣ
	 * 
	 * @author dingyj
	 * @since 2009-12-28
	 * @param objPath
	 */
	public void exportClientAddressList(String objPath) throws Exception {
		String fileName = objPath;
		String[] cust_ids = pageContext.getRequest().getParameterValues(
				"cust_id");
		Integer input_operatorCode = Utility.parseInt(Utility
				.trimNull(pageContext.getRequest().getParameter("input_man")),
				new Integer(0));
		creatClientAddressList(fileName, cust_ids, input_operatorCode);
	}

	/**
	 * ��ÿͻ�ͨѶ¼��Ϣ
	 * 
	 * @author dingyj
	 * @since 2009-12-28
	 * @param fileName
	 * @param cust_ids
	 * @param input_man
	 * @throws Exception
	 */
	public void creatClientAddressList(String fileName, String[] cust_ids,
			Integer input_operatorCode) throws Exception {
		String[] titleName = { "����", "�ֻ�1", "�ֻ�2", "����", "��ַ", "��Ŀ����", "��˾�绰",
				"��ͥ�绰", "����", "�ʱ�" };
		String[] fieldType = { "1", "1", "1", "1", "1", "1", "1", "1", "1", "1" };
		String[] fieldName = { "CUST_NAME", "MOBILE", "CUST_TEL", "E_MAIL",
				"POST_ADDRESS", "PRODUCT_NAME", "O_TEL", "H_TEL", "FAX",
				"POST_CODE" };
		List list = new ArrayList();
		Integer cust_id = new Integer(0);
		CustomerLocal local = EJBFactory.getCustomer();
		CustomerVO vo = new CustomerVO();
		for (int i = 0; i < cust_ids.length; i++) {
			if (cust_ids[i] != null && cust_ids[i].length() > 0) {
				cust_id = Utility.parseInt(Utility.trimNull(cust_ids[i]),
						new Integer(0));
			}
			if ((!cust_id.equals(new Integer(0)))) {
				vo.setCust_id(cust_id);
				vo.setInput_man(input_operatorCode);
				List cust_list = local.listProcAll(vo);
				if (cust_list != null && cust_list.size() > 0) {
					list.add((Map) cust_list.get(0));
				}
			}
		}
		OutputStream outStr = getResponseStream(fileName);
		creatExcelClientAddList(outStr, titleName, fieldType, fieldName, list);
	}

	/**
	 * �����ͻ�ͨѶ¼��Excel��Ϣ
	 * 
	 * @author dingyj
	 * @since 2009-12-28
	 * @param outStr
	 * @param titleName
	 * @param fieldType
	 * @param list
	 * @throws Exception
	 */
	public void creatExcelClientAddList(OutputStream outStr,
			String[] titleName, String[] fieldType, String[] fieldName,
			List list) throws Exception {
		Map map = new HashMap();
		try {
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			WritableFont font = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setAlignment(Alignment.CENTRE);
			ws.mergeCells(0, 0, (fieldName.length) - 1, 0);
			Label labelC = new Label(0, 0, "�ͻ�ͨѶ¼", format);
			ws.addCell(labelC);
			for (int i = 0; i < titleName.length; i++) {
				Label title = new Label(i, 1, titleName[i]);
				ws.addCell(title);
			}
			for (int j = 0; j < list.size(); j++) {
				map = (Map) list.get(j);
				String product_name = Argument.getProductNameByCustId(Utility
						.parseInt(Utility.trimNull(map.get("CUST_ID")),
								new Integer(0)));
				for (int k = 0; k < fieldName.length; k++) {
					int type = Utility.parseInt(fieldType[k], 0);
					switch (type) {
					case 1: {
						Label value = null;
						if ("PRODUCT_NAME".equals(fieldName[k]))
							value = new Label(k, j + 2, Utility
									.trimNull(product_name));
						else
							value = new Label(k, j + 2, Utility.trimNull(map
									.get(fieldName[k])));
						ws.addCell(value);
						break;
					}
					default:
						break;
					}
				}
			}
			wwb.write();
			wwb.close();
		} catch (Exception ex) {
			throw new BusiException("�����ļ�ʧ�ܣ�" + ex.getMessage());
		}
	}

	/**
	 * ***************************************CRM ���в��ֵ������� BEGIN
	 * ************************************************************************
	 */
	// Ԥ�Ǽ���Ϣ����
	public void downloadExcel_refinfo() throws Exception {
		String filename = "D:\\Ԥ�Ǽ���Ϣ.xls";
		String q_cust_name = pageContext.getRequest().getParameter(
				"q_cust_name");
		Integer book_code = Utility.parseInt(pageContext.getRequest()
				.getParameter("book_code"), null);
		Integer input_man = Utility.parseInt(pageContext.getRequest()
				.getParameter("input_man"), new Integer(0));
		Integer int_flag = Utility.parseInt(pageContext.getRequest()
				.getParameter("int_flag"), new Integer(0));
		Integer q_cust_type = Utility.parseInt(Utility.trimNull(pageContext
				.getRequest().getParameter("q_cust_type")), new Integer(0));
		String q_invest_type_name = Utility.trimNull(pageContext.getRequest()
				.getParameter("q_invest_type_name"));
		String q_invest_type = Utility.trimNull(pageContext.getRequest()
				.getParameter("q_invest_type"));
		BigDecimal min_reg_money = Utility.parseDecimal(pageContext
				.getRequest().getParameter("min_reg_money"), new BigDecimal(0),
				2, "10000");// ��͵ǼǶ��
		BigDecimal max_reg_money = Utility.parseDecimal(pageContext
				.getRequest().getParameter("max_reg_money"), new BigDecimal(0),
				2, "10000");// ��ߵǼǶ��
		String q_customer_cust_source = Utility.trimNull(pageContext
				.getRequest().getParameter("q_customer_cust_source"));// �ͻ���Դ
		Integer q_group_id = Utility.parseInt(Utility.trimNull(pageContext
				.getRequest().getParameter("q_group_id")), new Integer(0));
		Integer q_class_detail_id = Utility.parseInt(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"q_class_detail_id")), new Integer(0));

		PreContractVO vo = new PreContractVO();

		vo.setBook_code(book_code);
		vo.setInput_man(input_man);
		vo.setCust_name(q_cust_name);
		vo.setInt_flag(int_flag);
		vo.setCust_type(q_cust_type);
		vo.setCust_source(q_customer_cust_source);
		vo.setInvest_type(q_invest_type);
		vo.setMax_reg_money(max_reg_money);
		vo.setMin_reg_money(min_reg_money);
		vo.setClassdetail_id(q_class_detail_id);
		vo.setCust_group_id(q_group_id);

		try {
			java.io.File file = null;
			file = DeployOutListExcel_reginfo(filename, vo);

			downloadJsp(file);

		} catch (Exception e) {
			throw new Exception("����Excel�ļ�ʧ�ܣ�" + e.getMessage());
		}
	}

	public java.io.File DeployOutListExcel_reginfo(String objpath,
			PreContractVO vo) throws Exception {
		String filename = objpath;
		java.io.File vfile = new java.io.File(filename);
		if (!vfile.exists()) {
			vfile.createNewFile();
		}
		PreContractLocal preContract = EJBFactory.getPreContract();

		try {

			List rslist = preContract.query_reginfo_crm(vo);

			String titleName[] = { "�ͻ����", "�ͻ�����", "�ͻ����", "��ͥ�绰", "�칫�绰",
					"�ֻ�", "�ֻ�2", "�Ǽǽ��", "�Ǽ�����", "�ͻ���Դ", "Ԥ��Ͷ��" };

			String fieldName[] = { "CUST_NO", "CUST_NAME", "CUST_TYPE_NAME",
					"CUST_TEL", "O_TEL", "MOBILE", "BP", "REG_MONEY",
					"REG_DATE", "CUST_SOURCE_NAME", "INVEST_TYPE_NAME" };

			String fieldType[] = { "1", "1", "1", "1", "1", "1", "1", "2", "3",
					"1", "1" };
			// 1Ϊ�ַ�����,2ΪBigDecimal 3 Integer

			vfile = reginfoExcel(objpath, "�ͻ�Ԥ�Ǽ���Ϣά��", titleName, fieldName,
					fieldType, rslist);
		} finally {
			return vfile;
		}
	}

	public java.io.File reginfoExcel(String objpath, String excelTitle,
			String[] titleName, String[] fieldName, String[] fieldType,
			List rslist) throws Exception {
		String filename = objpath;
		java.io.File f = null;
		BigDecimal reg_money_total = new BigDecimal(0);// �Ǽǽ��ϼ�

		try {
			f = new java.io.File(filename);
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(f,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);

			// �����п�
			for (int y = 0; y < titleName.length; y++) {
				ws.setColumnView(y, 25);
			}

			// 1������ĸ�ʽ
			// �ƶ����ִ���ʽ
			WritableFont font = new WritableFont(WritableFont
					.createFont("���Ŀ���"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// ָ����Ԫ��ĸ�������
			WritableCellFormat format = new WritableCellFormat(font);
			// ָ��ˮƽ����ķ�ʽ����
			format.setAlignment(Alignment.CENTRE);
			// �ƶ���ֱ����ķ�ʽ����
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// �ϲ���Ԫ��
			ws.mergeCells(0, 0, (titleName.length) - 1, 0);
			// �����и�
			ws.setRowView(0, 500);
			// ���ñ߿�
			format.setBorder(Border.ALL, BorderLineStyle.THIN);

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,
					format);
			ws.addCell(labelC0);
			int i = 0;

			// 2����ͷ�ĸ�ʽ
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatTop = new WritableCellFormat(fontTop);
			formatTop.setAlignment(Alignment.CENTRE);
			formatTop.setVerticalAlignment(VerticalAlignment.CENTRE);
			ws.setRowView(1, 400);
			formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);

			for (; i < titleName.length; i++) {
				String sName = titleName[i];
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,
						formatTop);
				ws.addCell(labelC1);
			}

			// 3�����ݵĸ�ʽ
			// ����
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatRight = new WritableCellFormat(fontRight);
			formatRight.setAlignment(Alignment.RIGHT);
			formatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

			int j = 2;
			Map map = null;
			Iterator it = rslist.listIterator();

			while (it.hasNext()) {
				map = (Map) it.next();
				int k = 0;

				if (map.get("REG_MONEY") != null)
					reg_money_total = reg_money_total.add(new BigDecimal(
							Utility.trimNull(map.get("REG_MONEY"))));

				for (; k < fieldType.length; k++) {
					String sfieldName = fieldName[k];

					int iType = Utility.parseInt(fieldType[k], 0);
					switch (iType) {
					case 0: // ��
					{
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								"");
						ws.addCell(labelValue0);
						break;
					}
					case 1: // �ַ���-����
					{
						String fieldValue = Utility.trimNull(map
								.get(sfieldName));
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								fieldValue, formatLeft);
						ws.addCell(labelValue0);
						break;
					}
					case 2: // BigDecimal��ֵ���ͣ�����2λС��-����
					{
						BigDecimal fiedvalue = Utility.parseDecimal(Utility
								.trimNull(map.get(sfieldName)), new BigDecimal(
								"0"));

						// if(!"".equals(Utility.trimNull(map.get(sfieldName))))
						// total_money =
						// total_money.add(Utility.stringToDouble(Utility.trimNull(map.get(sfieldName))));
						jxl.write.Label labe4NF = new jxl.write.Label(
								k,
								j,
								Utility.trimNull(Format.formatMoney(fiedvalue)),
								formatRight);
						ws.addCell(labe4NF);
						break;
					}
					case 3: // Integer-����
					{
						Integer fieldValue = Utility.parseInt(Utility
								.trimNull(map.get(sfieldName)), new Integer(0));
						String finalValue = "";
						if (fieldValue != null)
							finalValue = fieldValue.toString();

						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatRight);

						ws.addCell(labelValue0);
						break;
					}
					case 4: // Integer to Date(yyyy-mm-dd)-����
					{
						Integer fieldValue = Utility.parseInt(Utility
								.trimNull(map.get(sfieldName)), new Integer(0));
						String finalValue = "";
						if (fieldValue != null) {
							finalValue = Format.formatDateLine(fieldValue);
						}
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatCenter);

						ws.addCell(labelValue0);
						break;
					}
					case 5: // timesamp��ֵ���͵�-����
					{
						Timestamp fieldValue = Utility.parseTimestamp(Utility
								.trimNull(map.get(sfieldName)));
						String finalValue = Format.formatDatetimeCn(fieldValue);
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatCenter);

						ws.addCell(labelValue0);
						break;
					}
					default:
						break;
					}
				}
				j++;
			}

			int index = 0;
			// for (int p = 0; p < titleName.length; p++) {
			// Label labelValue = new Label(0, rslist.size() + 2, "�ϼ�");
			// ws.addCell(labelValue);
			// if (titleName[p].equals("�ǼǶ��")){
			// Label labelValue1 = new Label( index,
			// rslist.size() + 2,
			// Format.formatMoney(reg_money_total).toString());
			// ws.addCell(labelValue1);
			// }
			// index++;
			// }
			for (int y = 0; y < fieldType.length; y++) {
				if (y == 0) {
					jxl.write.Label labeSum0 = new jxl.write.Label(y, j,
							Utility.trimNull("�ϼ�" + rslist.size() + "��"),
							formatLeft);
					ws.addCell(labeSum0);
				} else {
					if ("2".equals(fieldType[y])) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format
										.formatMoney(reg_money_total)),
								formatRight);
						ws.addCell(labeSum3);
					} else {
						jxl.write.Label labeSum1 = new jxl.write.Label(y, j,
								Utility.trimNull(""), formatLeft);
						ws.addCell(labeSum1);
					}
				}
			}

			wwb.write();
			wwb.close();
		} catch (Exception e) {
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		} finally {
			return f;
		}
	}

	// ԤԼ
	public void downloadExcel_presell() throws Exception {
		String filename = "D:\\ԤԼ��Ϣ.xls";
		Integer book_code = Utility.parseInt(pageContext.getRequest()
				.getParameter("book_code"), new Integer(1));
		Integer input_man = Utility.parseInt(pageContext.getRequest()
				.getParameter("input_man"), new Integer(0));
		
		Integer q_productId = new Integer(0);
		Integer preproductId = new Integer(0);
		String sProductId = Utility.trimNull(request.getParameter("q_productId"));
		String[] temp = sProductId.split("-");
		if (temp.length>0) {
			preproductId = Utility.parseInt(temp[0], new Integer(0));
		}
		if (temp.length>1) {
			q_productId = Utility.parseInt(temp[1], new Integer(0));
		}
		
		String q_productCode = Utility.trimNull(pageContext.getRequest()
				.getParameter("q_productCode"));
		String q_cust_name = Utility.trimNull(pageContext.getRequest()
				.getParameter("q_cust_name"));
		String q_pre_code = Utility.trimNull(pageContext.getRequest()
				.getParameter("q_pre_code"));
		Integer q_cust_type = Utility.parseInt(Utility.trimNull(pageContext
				.getRequest().getParameter("q_cust_type")), new Integer(0));
		BigDecimal min_reg_money = Utility.parseDecimal(pageContext
				.getRequest().getParameter("min_reg_money"), new BigDecimal(0),
				2, "10000");// ��͵ǼǶ��
		BigDecimal max_reg_money = Utility.parseDecimal(pageContext
				.getRequest().getParameter("max_reg_money"), new BigDecimal(0),
				2, "10000");// ��ߵǼǶ��
		String q_customer_cust_source = Utility.trimNull(pageContext
				.getRequest().getParameter("q_customer_cust_source"));// �ͻ���Դ
		Integer q_group_id = Utility.parseInt(Utility.trimNull(pageContext
				.getRequest().getParameter("q_group_id")), new Integer(0));
		Integer q_class_detail_id = Utility.parseInt(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"q_class_detail_id")), new Integer(0));

		PreContractVO vo = new PreContractVO();

		vo.setProduct_id(q_productId);
		vo.setCust_name(q_cust_name);
		vo.setPre_code(q_pre_code);
		vo.setBook_code(book_code);
		vo.setInput_man(input_man);
		vo.setCust_type(q_cust_type);
		vo.setMax_reg_money(max_reg_money);
		vo.setMin_reg_money(min_reg_money);
		vo.setCust_source(q_customer_cust_source);
		vo.setClassdetail_id(q_class_detail_id);
		vo.setCust_group_id(q_group_id);
		vo.setPre_product_id(preproductId);
		
		try {
			java.io.File file = null;
			file = OutExcel_presell(filename, vo);
			downloadJsp(file);
		} catch (Exception e) {
			throw new Exception("����Excel�ļ�ʧ�ܣ�" + e.getMessage());
		}
	}

	public java.io.File OutExcel_presell(String objpath, PreContractVO vo)
			throws Exception {
		String filename = objpath;
		java.io.File vfile = new java.io.File(filename);
		if (!vfile.exists()) {
			vfile.createNewFile();
		}
		PreContractLocal preContract = EJBFactory.getPreContract();
		String[] totalColumn = new String[0];

		try {
			IPageList pageList = preContract.preContract_page_query_crm(vo,
					totalColumn, 1, -1);
			List rslist = pageList.getRsList();
			String titleName[] = { "�ͻ�����", "�ͻ����", "ԤԼ��", "ԤԼ����", "��ת�Ϲ�����",
					"���", "Ԥ������������", "Ԥ������������", "��ϵ��", "��ϵ�绰", "�ͻ���Դ", "����",
					"��Ч����", "״̬", "��ע" };
			String fieldName[] = { "CUST_NAME", "CUST_TYPE_NAME", "PRE_CODE",
					"PRE_NUM", "RG_NUM", "PRE_MONEY", "EXP_RATE1", "EXP_RATE2",
					"LINK_MAN", "MOBILE", "CUST_SOURCE_NAME", "PRE_DATE",
					"VALID_DAYS", "PRE_STATUS_NAME", "SUMMARY" };
			String[] fieldType = { "1", "1", "1", "3", "3", "2", "2", "2", "1",
					"1", "1", "3", "3", "1", "1" };
			// 1Ϊ�ַ�����,2ΪBigDecimal 3 Integer

			vfile = presellExcel(objpath, "�ͻ�ԤԼ��Ϣ", titleName, fieldName,
					fieldType, rslist);
		} finally {
			return vfile;
		}
	}

	public java.io.File presellExcel(String objpath, String excelTitle,
			String[] titleName, String[] fieldName, String[] fieldType,
			List rslist) throws Exception {
		String filename = objpath;
		java.io.File f = null;
		Integer rg_num_total = new Integer(0); //���Ϲ�����
		Integer pre_num_total = new Integer(0);//ԤԼ����
		BigDecimal pre_money_total = new BigDecimal(0);//ԤԼ���

		try {
			f = new java.io.File(filename);
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(f,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);

			// �����п�
			for (int y = 0; y < titleName.length; y++) {
				ws.setColumnView(y, 25);
			}

			// 1������ĸ�ʽ
			// �ƶ����ִ���ʽ
			WritableFont font = new WritableFont(WritableFont
					.createFont("���Ŀ���"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// ָ����Ԫ��ĸ�������
			WritableCellFormat format = new WritableCellFormat(font);
			// ָ��ˮƽ����ķ�ʽ����
			format.setAlignment(Alignment.CENTRE);
			// �ƶ���ֱ����ķ�ʽ����
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// �ϲ���Ԫ��
			ws.mergeCells(0, 0, (titleName.length) - 1, 0);
			// �����и�
			ws.setRowView(0, 500);
			// ���ñ߿�
			format.setBorder(Border.ALL, BorderLineStyle.THIN);

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,
					format);
			ws.addCell(labelC0);
			int i = 0;

			// 2����ͷ�ĸ�ʽ
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatTop = new WritableCellFormat(fontTop);
			formatTop.setAlignment(Alignment.CENTRE);
			formatTop.setVerticalAlignment(VerticalAlignment.CENTRE);
			ws.setRowView(1, 400);
			formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);

			for (; i < titleName.length; i++) {
				String sName = titleName[i];
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,
						formatTop);
				ws.addCell(labelC1);
			}

			// 3�����ݵĸ�ʽ
			// ����
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatRight = new WritableCellFormat(fontRight);
			formatRight.setAlignment(Alignment.RIGHT);
			formatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

			int j = 2;
			Map map = null;
			Iterator it = rslist.listIterator();

			while (it.hasNext()) {
				int k = 0;
				map = (Map) it.next();

				if (map.get("RG_NUM") != null) {
					rg_num_total = new Integer(rg_num_total.intValue()
							+ Utility.parseInt(Utility.trimNull(map
									.get("RG_NUM")), 0));
				}

				if (map.get("PRE_NUM") != null) {
					pre_num_total = new Integer(pre_num_total.intValue()
							+ Utility.parseInt(Utility.trimNull(map
									.get("PRE_NUM")), 0));
				}

				if (map.get("PRE_MONEY") != null) {
					pre_money_total = pre_money_total.add(new BigDecimal(
							Utility.trimNull(map.get("PRE_MONEY"))));
				}

				for (; k < fieldType.length; k++) {
					String sfieldName = fieldName[k];
					int iType = Utility.parseInt(fieldType[k], 0);

					switch (iType) {
					case 1: {
						if (sfieldName.equals("LINK_MAN")) {
							jxl.write.Label labelValue0 = new jxl.write.Label(
									k,
									j,
									Utility
											.trimNull(Argument
													.getOpName(new Integer(
															Utility
																	.trimNull(map
																			.get("LINK_MAN"))))),
									formatLeft);
							ws.addCell(labelValue0);
						} else {
							String fieldValue = Utility.trimNull(map
									.get(sfieldName));
							jxl.write.Label labelValue0 = new jxl.write.Label(
									k, j, Utility.trimNull(fieldValue),
									formatLeft);
							ws.addCell(labelValue0);
						}

						break;
					}
					case 2: // BigDecimal��ֵ���ͣ�����2λС��-����
					{
						BigDecimal fiedvalue = Utility.parseDecimal(Utility
								.trimNull(map.get(sfieldName)), new BigDecimal(
								"0"));

						// if(!"".equals(Utility.trimNull(map.get(sfieldName))))
						// total_money =
						// total_money.add(Utility.stringToDouble(Utility.trimNull(map.get(sfieldName))));
						jxl.write.Label labe4NF = new jxl.write.Label(
								k,
								j,
								Utility.trimNull(Format.formatMoney(fiedvalue)),
								formatRight);
						ws.addCell(labe4NF);
						break;
					}
					case 3: // Integer-����
					{
						Integer fieldValue = Utility.parseInt(Utility
								.trimNull(map.get(sfieldName)), new Integer(0));
						String finalValue = "";
						if (fieldValue != null)
							finalValue = fieldValue.toString();

						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatRight);

						ws.addCell(labelValue0);
						break;
					}
					case 4: // Integer to Date(yyyy-mm-dd)-����
					{
						Integer fieldValue = Utility.parseInt(Utility
								.trimNull(map.get(sfieldName)), new Integer(0));
						String finalValue = "";
						if (fieldValue != null) {
							finalValue = Format.formatDateLine(fieldValue);
						}
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatCenter);

						ws.addCell(labelValue0);
						break;
					}
					case 5: {
						// timesamp��ֵ���͵�-����
						Timestamp fieldValue = Utility.parseTimestamp(Utility
								.trimNull(map.get(sfieldName)));
						String finalValue = Format.formatDatetimeCn(fieldValue);
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatCenter);

						ws.addCell(labelValue0);
						break;
					}
					default:
						break;
					}
				}
				j++;
			}

			int index = 0;
			for (int y = 0; y < fieldType.length; y++) {
				if (y == 0) {
					jxl.write.Label labeSum0 = new jxl.write.Label(y, j,
							Utility.trimNull("�ϼ�" + rslist.size() + "��"),
							formatLeft);
					ws.addCell(labeSum0);
				} else {
					if (titleName[y].equals("���")) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format
										.formatMoney(pre_money_total)),
								formatRight);
						ws.addCell(labeSum3);
					} else if (titleName[y].equals("��ת�Ϲ�����")) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format
										.formatMoney(rg_num_total)),
								formatRight);
						ws.addCell(labeSum3);
					} else if (titleName[y].equals("ԤԼ����")) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format
										.formatMoney(pre_num_total)),
								formatRight);
						ws.addCell(labeSum3);
					} else {
						jxl.write.Label labeSum1 = new jxl.write.Label(y, j,
								Utility.trimNull(""), formatLeft);
						ws.addCell(labeSum1);
					}
				}
			}
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		} finally {
			return f;
		}
	}

	// �Ϲ���Ϣ����
	public void downloadExcel_subscribe() throws Exception {
		String filename = "D:\\�Ϲ���Ϣ.xls";
		String q_productCode = Utility.trimNull(pageContext.getRequest()
				.getParameter("q_productCode"));
		Integer q_pre_flag = Utility.parseInt(pageContext.getRequest()
				.getParameter("q_pre_flag"), new Integer(0));
		Integer q_productId = Utility.parseInt(pageContext.getRequest()
				.getParameter("q_productId"), new Integer(0));
		Integer q_check_flag = Utility.parseInt(pageContext.getRequest()
				.getParameter("q_check_flag"), new Integer(0));
		String q_cust_name = Utility.trimNull(pageContext.getRequest()
				.getParameter("q_cust_name"));
		String q_card_id = Utility.trimNull(pageContext.getRequest()
				.getParameter("q_card_id"));
		String query_contract_bh = Utility.trimNull(pageContext.getRequest()
				.getParameter("query_contract_bh"));
		BigDecimal min_rg_money = Utility.parseDecimal(pageContext.getRequest()
				.getParameter("min_rg_money"), new BigDecimal(0), 2, "10000");// ��͵ǼǶ��
		BigDecimal max_rg_money = Utility.parseDecimal(pageContext.getRequest()
				.getParameter("max_rg_money"), new BigDecimal(0), 2, "10000");// ��ߵǼǶ��
		Integer q_cust_type = Utility.parseInt(Utility.trimNull(pageContext
				.getRequest().getParameter("q_cust_type")), new Integer(0));
		Integer book_code = Utility.parseInt(pageContext.getRequest()
				.getParameter("book_code"), new Integer(1));
		Integer input_man = Utility.parseInt(pageContext.getRequest()
				.getParameter("input_man"), new Integer(0));
		Integer q_group_id = Utility.parseInt(Utility.trimNull(pageContext
				.getRequest().getParameter("q_group_id")), new Integer(0));
		Integer q_class_detail_id = Utility.parseInt(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"q_class_detail_id")), new Integer(0));

		ContractVO vo = new ContractVO();

		vo.setBook_code(book_code);
		vo.setInput_man(input_man);
		vo.setProduct_code(q_productCode);
		vo.setPre_flag(q_pre_flag);
		vo.setProduct_id(q_productId);
		vo.setCheck_flag(q_check_flag);
		vo.setCust_name(q_cust_name);
		vo.setCard_id(q_card_id);
		vo.setContract_sub_bh(query_contract_bh);
		vo.setMax_rg_money(max_rg_money);
		vo.setMin_rg_money(min_rg_money);
		vo.setCust_type(q_cust_type);
		vo.setClassdetail_id(q_class_detail_id);
		vo.setCust_group_id(q_group_id);

		try {
			java.io.File file = null;
			file = OutExcel_subscribe(filename, vo);
			downloadJsp(file);
		} catch (Exception e) {
			throw new Exception("����Excel�ļ�ʧ�ܣ�" + e.getMessage());
		}
	}

	public java.io.File OutExcel_subscribe(String objpath, ContractVO vo)
			throws Exception {
		String filename = objpath;
		java.io.File vfile = new java.io.File(filename);
		if (!vfile.exists()) {
			vfile.createNewFile();
		}
		ContractLocal contract = EJBFactory.getContract();

		List rslist = contract.queryPurchanseContract_crm(vo);
		String titleName[] = { "��ͬ���", "��Ʒ����", "�ͻ����", "�ͻ�����", "�Ϲ����", "ǩ������",
				"״̬", "�Ϲ���ʽ" };

		String fieldName[] = { "CONTRACT_SUB_BH", "PRODUCT_NAME", "CUST_NO",
				"CUST_NAME", "RG_MONEY", "QS_DATE", "HT_STATUS_NAME",
				"PRE_FLAG" };
		String[] fieldType = { "1", "1", "1", "1", "2", "3", "1", "1" };
		// 1Ϊ�ַ�����,2ΪBigDecimal 3 Integer

		vfile = subscribeExcel(objpath, "�ͻ��Ϲ���Ϣ", titleName, fieldName,
				fieldType, rslist);

		return vfile;
	}

	public java.io.File subscribeExcel(String objpath, String excelTitle,
			String[] titleName, String[] fieldName, String[] fieldType,
			List rslist) throws Exception {
		String filename = objpath;
		java.io.File f = null;
		BigDecimal rg_money_total = new BigDecimal(0);

		try {
			f = new java.io.File(filename);
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(f,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);

			// �����п�
			for (int y = 0; y < titleName.length; y++) {
				ws.setColumnView(y, 25);
			}

			// 1������ĸ�ʽ
			// �ƶ����ִ���ʽ
			WritableFont font = new WritableFont(WritableFont
					.createFont("���Ŀ���"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// ָ����Ԫ��ĸ�������
			WritableCellFormat format = new WritableCellFormat(font);
			// ָ��ˮƽ����ķ�ʽ����
			format.setAlignment(Alignment.CENTRE);
			// �ƶ���ֱ����ķ�ʽ����
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// �ϲ���Ԫ��
			ws.mergeCells(0, 0, (titleName.length) - 1, 0);
			// �����и�
			ws.setRowView(0, 500);
			// ���ñ߿�
			format.setBorder(Border.ALL, BorderLineStyle.THIN);

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,
					format);
			ws.addCell(labelC0);

			int i = 0;

			// 2����ͷ�ĸ�ʽ
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatTop = new WritableCellFormat(fontTop);
			formatTop.setAlignment(Alignment.CENTRE);
			formatTop.setVerticalAlignment(VerticalAlignment.CENTRE);
			ws.setRowView(1, 400);
			formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);

			for (; i < titleName.length; i++) {
				String sName = titleName[i];
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,
						formatTop);
				ws.addCell(labelC1);
			}

			// 3�����ݵĸ�ʽ
			// ����
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatRight = new WritableCellFormat(fontRight);
			formatRight.setAlignment(Alignment.RIGHT);
			formatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

			int j = 2;
			Map map = null;
			Iterator it = rslist.listIterator();

			while (it.hasNext()) {
				int k = 0;
				map = (Map) it.next();

				if (map.get("RG_MONEY") != null)
					rg_money_total = rg_money_total.add(new BigDecimal(Utility
							.trimNull(map.get("RG_MONEY"))));

				for (; k < fieldType.length; k++) {
					String sfieldName = fieldName[k];
					int iType = Utility.parseInt(fieldType[k], 0);

					switch (iType) {
					case 0: // ��
					{
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								"");
						ws.addCell(labelValue0);
						break;
					}
					case 1: // �ַ���-����
					{
						/*
						 * String fieldValue = Utility.trimNull(map
						 * .get(sfieldName)); jxl.write.Label labelValue0 = new
						 * jxl.write.Label(k, j, fieldValue, formatLeft);
						 * ws.addCell(labelValue0); break;
						 */

						if (sfieldName.equals("PRE_FLAG")) {
							jxl.write.Label labelValue0 = new jxl.write.Label(
									k, j, Utility.trimNull(Argument
											.getPreflag(Utility.parseInt(
													Utility.trimNull(map
															.get("PRE_FLAG")),
													new Integer(0)))),
									formatLeft);
							ws.addCell(labelValue0);
						} else {
							String fieldValue = Utility.trimNull(map
									.get(sfieldName));
							jxl.write.Label labelValue0 = new jxl.write.Label(
									k, j, fieldValue, formatLeft);
							ws.addCell(labelValue0);
						}

						break;
					}
					case 2: // BigDecimal��ֵ���ͣ�����2λС��-����
					{
						BigDecimal fiedvalue = Utility.parseDecimal(Utility
								.trimNull(map.get(sfieldName)), new BigDecimal(
								"0"));

						jxl.write.Label labe4NF = new jxl.write.Label(
								k,
								j,
								Utility.trimNull(Format.formatMoney(fiedvalue)),
								formatRight);
						ws.addCell(labe4NF);
						break;
					}
					case 3: // Integer-����
					{
						Integer fieldValue = Utility.parseInt(Utility
								.trimNull(map.get(sfieldName)), new Integer(0));
						String finalValue = "";
						if (fieldValue != null)
							finalValue = fieldValue.toString();

						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatRight);

						ws.addCell(labelValue0);
						break;
					}
					case 4: { // Integer to Date(yyyy-mm-dd)-����
						Integer fieldValue = Utility.parseInt(Utility
								.trimNull(map.get(sfieldName)), new Integer(0));
						String finalValue = "";

						if (fieldValue != null) {
							finalValue = Format.formatDateLine(fieldValue);
						}

						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatCenter);
						ws.addCell(labelValue0);
						break;
					}
					case 5: {// timesamp��ֵ���͵�-����
						Timestamp fieldValue = Utility.parseTimestamp(Utility
								.trimNull(map.get(sfieldName)));
						String finalValue = Format.formatDatetimeCn(fieldValue);
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								finalValue, formatCenter);

						ws.addCell(labelValue0);
						break;
					}
					default:
						break;
					}
				}
				j++;
			}

			int index = 0;

			for (int y = 0; y < fieldType.length; y++) {
				if (y == 0) {
					jxl.write.Label labeSum0 = new jxl.write.Label(y, j,
							Utility.trimNull("�ϼ�" + rslist.size() + "��"),
							formatLeft);
					ws.addCell(labeSum0);
				} else {
					if ("2".equals(fieldType[y])) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format
										.formatMoney(rg_money_total)),
								formatRight);
						ws.addCell(labeSum3);
					} else {
						jxl.write.Label labeSum1 = new jxl.write.Label(y, j,
								Utility.trimNull(""), formatLeft);
						ws.addCell(labeSum1);
					}
				}
			}

			wwb.write();
			wwb.close();
		} catch (Exception e) {
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		} finally {
			return f;
		}
	}

	/**
	 * ***************************************CRM ���в��ֵ�������
	 * END************************************************************************
	 */

	/**
	 * ��ӡ�ͻ��ֻ�
	 * 
	 * @author dingyj
	 * @since 2010-1-11
	 */
	public void downloadMobile(String objpath) throws Exception {
		String filename = objpath;
		String mobile = "";
		// ��ö���
		CustomerLocal local = EJBFactory.getCustomer();
		List list = local.listProcAllExt(getPringCustParams());
		Iterator it = list.iterator();
		Map map = new HashMap();

		java.io.File f = new java.io.File(filename);
		PrintWriter out = new PrintWriter(new FileWriter(f));

		while (it.hasNext()) {
			map = (Map) it.next();
			if (Utility.trimNull(map.get("MOBILE")) != ""
					&& Utility.trimNull(map.get("MOBILE")).length() >= 11)
				mobile = Utility.trimNull(map.get("MOBILE")) + "," + mobile;
			if (Utility.trimNull(map.get("BP")) != ""
					&& Utility.trimNull(map.get("BP")).length() >= 11)
				mobile = Utility.trimNull(map.get("BP")) + "," + mobile;
		}
		out.print(mobile);
		out.close();
		downloadJsp(f);
		if (f.exists())
			f.delete();
	}

	/**
	 * �����ͻ��ֻ����ɹ�ѡ����
	 * 
	 * @author guifeng
	 * @since 2011-04-01
	 */
	public void downloadMobileList(String objpath) throws Exception {
		String filename = objpath;
		String mobile = "";
		Integer cust_id = null;
		// ��ö���
		java.io.File f = new java.io.File(filename);
		PrintWriter out = new PrintWriter(new FileWriter(f));
		String[] cust_ids = pageContext.getRequest().getParameterValues(
				"cust_id");
		Integer input_man = Utility.parseInt(pageContext.getRequest().getParameter("input_man"), new Integer(0));
		String ip = Utility.trimNull(pageContext.getRequest().getRemoteAddr());
		String mac = Utility.getClientMacAddr(ip);
			

		if(mac==""||mac==null){
			mac="δ�ҵ���ַ��";
		}
		
		if (cust_ids != null) {
			for (int i = 0; i < cust_ids.length; i++) {
				cust_id = Utility.parseInt(cust_ids[i], new Integer(0));
				//��ö���
				CustomerLocal local = EJBFactory.getCustomer();
				CustomerVO cust_vo = new CustomerVO();
				cust_vo.setCust_id(cust_id);
				cust_vo.setInput_man(input_man);
				cust_vo.setIp(ip);
				cust_vo.setMac(mac);
				if (i==0)
					cust_vo.setExport_flag(new Integer(2)); // �����ͻ��ֻ�
				else 
					cust_vo.setExport_flag(new Integer(10)); // һ���ε���ֻ��һ��log
				
				List cust_list = local.listCustomerLoad(cust_vo);

				Map map = new HashMap();
				if (cust_list != null && cust_list.size() > 0) {
					map = (Map) cust_list.get(0);
					if (Utility.trimNull(map.get("MOBILE")) != ""
							&& Utility.trimNull(map.get("MOBILE")).length() >= 11)
						mobile = Utility.trimNull(map.get("MOBILE")) + "\r\n"
								+ mobile;
					if (Utility.trimNull(map.get("BP")) != ""
							&& Utility.trimNull(map.get("BP")).length() >= 11)
						mobile = Utility.trimNull(map.get("BP")) + "\r\n"
								+ mobile;
				}
			}
		}

		out.print(mobile);
		out.close();
		downloadJsp(f);
		if (f.exists())
			f.delete();
	}

	/**
	 * ***************************************CRM �ͻ���Ϣ���빦��
	 * END************************************************************************
	 */

	// ��Excel
	public int readExcel(PageContext in_pageContext, String objpath,
			int isystemFalg, Integer inputOperator) throws Exception {
		// smartUpload ��ʼ��
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);
		String filename = objpath;

		if (!hasParse) {
			parseRequest();
		}

		// ����·��
		String sfile_name = smartUpload.getRequest().getParameter("file_name");
		sfile_name = sfile_name.substring(sfile_name.lastIndexOf("\\") + 1);
		filename = objpath + "\\" + sfile_name;
		File f = new File(filename);

		HashMap customerMap = this.getCustomerTableMap();
		ProxyCustomer proxy_cust = new ProxyCustomer();
		CustomerVO vo = null;
		int count = 0;
		// ��ȡExcel ��SAVE�����ݿ�

		try {
			CustomerLocal customerLocal = EJBFactory.getCustomer();
			InputStream is = new FileInputStream(filename);
			jxl.Workbook book = Workbook.getWorkbook(is);

			if (book == null) {
				throw new Exception("�ļ�������!");
			}

			// ��õ�һ�����������
			Sheet[] shets = book.getSheets();
			int iSheet = book.getNumberOfSheets();

			// ֻ��Sheet1
			Sheet sheet = book.getSheet(0);

			int iRows = sheet.getRows();
			int iColums = sheet.getColumns();
			String table = sheet.getName();

			String[] fields = new String[iColums];
			String[] sfieldtypes = new String[iColums];

			for (int r = 0; r < iColums; r++) {
				Cell cel = sheet.getCell(r, 0);
				fields[r] = cel.getContents();
			}
			
			for (int j = 1; j < iRows; j++) {
				// String[] values = new String[iColums];
				vo = new CustomerVO();	
				Object[] value = null;
				for (int k = 0; k < iColums; k++) {
					value = new Object[1];
					String colTitle = sheet.getCell(k, 0).getContents();
					String methodName = (String) customerMap.get(colTitle);
					Cell cel = sheet.getCell(k, j);

					if (!colTitle.equals("����") && !colTitle.equals("Ͷ��Ǳ��")) {
						value[0] = cel.getContents();
					} else if (colTitle.equals("����")) {
						value[0] = Utility.parseInt(cel.getContents(),
								new Integer(0));
					} else if (colTitle.equals("Ͷ��Ǳ��")) {
						value[0] = Utility.parseDecimal(cel.getContents(),
								new BigDecimal(0.00));
					}

					if (methodName != null) {
						proxy_cust.invokeSetMethod(vo, methodName, value);
					}

				}

				if (vo.getCust_name() != null && vo.getCust_name() != "") {
					vo.setInput_man(inputOperator);
					customerLocal.importCustomer(vo);
					count++;
				}
			}
			customerLocal.remove();
			book.close();
		} catch (Exception e) {
			throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
		}

		return count;
	}

	/**
	 * �õ������ֶ�ӳ��MAP
	 * 
	 * @return
	 */
	private HashMap getCustomerTableMap() {
		HashMap map = new HashMap();

		map.put("�ͻ�����", "setCust_name");
		map.put("�ͻ�����", "setCust_type_name");
		map.put("��Ч֤������", "setCard_type_name");
		map.put("��Ч֤������", "setCard_id");
		map.put("����", "setAge");
		map.put("Ͷ��Ǳ��", "setPotenital_money");
		map.put("����", "setArea");
		map.put("��ϵ��ַ", "setPost_address");
		map.put("��������", "setPost_code");
		map.put("�ֻ�����", "setMobile");
		map.put("E-Mail��ַ", "setE_mail");
		map.put("��ϵ��", "setContact_man");
		map.put("�̻�", "setCust_tel");
		map.put("�ͻ�����", "setService_man_name");
		map.put("�Ƿ�ί����", "setIsClient");
		map.put("��ϵ��ʽ", "setTouch_type_name");
		map.put("�ͻ���Դ", "setCust_source_name");

		return map;
	}

	// �ڲ��� ����Set����
	private class ProxyCustomer {

		public void invokeSetMethod(CustomerVO vo, String methodName,
				Object[] args) throws Exception {
			Class custClass = Class.forName(CustomerVO.class.getName());
			Class[] argsClass = new Class[args.length];

			for (int i = 0; i < args.length; i++) {
				argsClass[i] = args[i].getClass();
			}

			Method method = custClass.getMethod(methodName, argsClass);
			method.invoke(vo, args);
		}
	}

	private void exportExcel(String fileName, String excelTitle,
			String[][] titleName, String[][] fieldName, String[] fieldType,
			int startRow, List list) throws Exception {

		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) pageContext
				.getResponse();
		out.clear();
		out = pageContext.pushBody();
		OutputStream outputstream = response.getOutputStream();
		// ��������
		response.reset();
		// ������Ӧͷ�����ر�����ļ���
		response.setHeader("content-disposition",
				Encode("attachment; filename=" + fileName));
		// �����������
		response.setContentType("APPLICATION/msexcel");

		WorkbookSettings wbs = new WorkbookSettings();
		wbs.setGCDisabled(true);
		WritableWorkbook wwb = Workbook.createWorkbook(outputstream,wbs);
		WritableSheet ws = wwb.createSheet(excelTitle, 0);

		try {
			WritableCellFormat cellformat = new WritableCellFormat();
			WritableCellFormat cellformat1 = new WritableCellFormat();
			cellformat.setAlignment(jxl.format.Alignment.CENTRE);
			cellformat1.setAlignment(jxl.format.Alignment.RIGHT);
			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,
					cellformat);
			ws.addCell(labelC0);
			if (fieldType.length > 1)
				ws.mergeCells(0, 0, fieldType.length - 1, 0);
			jxl.write.Label labelC1 = null;
			for (int i = 0; i < titleName.length; i++) {
				labelC1 = new jxl.write.Label(new Integer(titleName[i][1])
						.intValue(), new Integer(titleName[i][2]).intValue(),
						titleName[i][0], cellformat);
				ws.addCell(labelC1);
				ws.mergeCells(new Integer(titleName[i][1]).intValue(),
						new Integer(titleName[i][2]).intValue(), new Integer(
								titleName[i][3]).intValue(), new Integer(
								titleName[i][4]).intValue());
			}
			int j = startRow;

			Iterator it = list.iterator();
			Map rowMap = null;
			int iType = 0;
			while (it.hasNext()) {
				rowMap = (Map) it.next();

				for (int k = 0; k < fieldType.length; k++) {
					String[] sfieldName = fieldName[k];
					iType = Utility.parseInt(fieldType[k], 0);

					switch (iType) {
					case 1: {// String����
						String fieldValue = "";
						if (sfieldName.length > 1) {
							for (int h = 0; h < sfieldName.length - 1; h++) {
								if (rowMap.get(sfieldName[h].toUpperCase()) != null) {
									fieldValue = fieldValue
											+ Utility.trimNull(rowMap
													.get(sfieldName[h]
															.toUpperCase()))
											+ sfieldName[sfieldName.length - 1];
								}
							}
							if (fieldValue != "") {
								fieldValue = fieldValue.substring(0, fieldValue
										.length() - 1);
							}
						} else {
							fieldValue = Utility.trimNull(rowMap
									.get(sfieldName[0].toUpperCase()));
						}
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								Utility.trimNull(fieldValue), cellformat);
						ws.addCell(labelValue0);
						break;
					}
					case 2: {// BigDecimal��ֵ���͵�
						BigDecimal fiedvalue = (BigDecimal) rowMap
								.get(sfieldName[0].toUpperCase());
						double tempmoney = (fiedvalue == null ? 0 : fiedvalue
								.doubleValue());
						jxl.write.NumberFormat nf = new jxl.write.NumberFormat(
								"#.##");
						jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(
								nf);
						wcfN.setAlignment(jxl.format.Alignment.RIGHT);
						jxl.write.Number labe4NF = new jxl.write.Number(k, j,
								tempmoney, wcfN);
						ws.addCell(labe4NF);
						break;
					}
					case 3: // ������
					{
						Integer fieldvalue = (Integer) rowMap.get(sfieldName[0]
								.toUpperCase());
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								Format.formatDateCn(fieldvalue), cellformat);
						ws.addCell(labelValue0);
						break;
					}
					case 4: // ���͵�
					{
						Integer fiedvalue = (Integer) rowMap.get(sfieldName[0]
								.toUpperCase());
						int tempmoney = (fiedvalue == null ? 0 : fiedvalue
								.intValue());
						jxl.write.Number labe4NF = new jxl.write.Number(k, j,
								tempmoney, cellformat1);
						ws.addCell(labe4NF);
						break;
					}
					default:
						break;
					}
				}
				j++;
			}

		} catch (Exception e) {
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		} finally {
			wwb.write();
			wwb.close();
			outputstream.flush();
			outputstream.close();
			response.flushBuffer();
		}
	}

	/*
	 * public void importExcel(String fullPath,Map fieldMap)throws Exception{
	 * 
	 * File file = new File(fullPath);
	 * 
	 * if(!file.exists()){ new Exception("�ļ�������"); }else{ InputStream is = new
	 * FileInputStream(file); jxl.Workbook book = Workbook.getWorkbook(is);
	 * Sheet sheet = null; int rowCount = 0; int columCount = 0;
	 * 
	 * if(book==null){ new Exception("book�쳣"); }else{
	 * if(book.getNumberOfSheets()>0){ sheet = book.getSheet(0); rowCount =
	 * sheet.getRows(); columCount = sheet.getColumns(); }else{ new
	 * Exception("sheet�쳣"); } }
	 * 
	 * Set set = fieldMap.keySet(); Iterator it = set.iterator();
	 * 
	 * file.delete(); } }
	 */

	private class ProxySetMethod {

		public void invokeSetMethod(Object obj, String methodName, Object[] args)
				throws Exception {

			Class objClass = Class.forName(obj.getClass().getName());
			Class[] argsClass = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argsClass[i] = args[i].getClass();
			}
			Method method = objClass.getMethod(methodName, argsClass);
			method.invoke(obj, args);
		}
	}

	public void exportCustAddressBook(String fileName) throws Exception {

		String[] cust_ids = pageContext.getRequest().getParameterValues(
				"cust_id");
		Integer input_operatorCode = Utility.parseInt(Utility
				.trimNull(pageContext.getRequest().getParameter("input_man")),
				new Integer(0));
		String ip = Utility.trimNull(pageContext.getRequest().getRemoteAddr());
		String mac = Utility.getClientMacAddr(ip);
		

		if(mac==""||mac==null){
			mac="δ�ҵ���ַ��";
		}
		
		String titleNames = pageContext.getRequest().getParameter("titleNames");
		String fieldNames = pageContext.getRequest().getParameter("fieldNames");
		String fieldTypes = pageContext.getRequest().getParameter("fieldTypes");

		String[][] titleName = Utility.splitString(titleNames, "#", "$");
		String[][] fieldName = Utility.splitString(fieldNames, "#", "$");
		String[] fieldType = Utility.splitString(fieldTypes, "#");
		int startRow = 2;

		List list = new ArrayList();
		List cust_list = null;
		Integer cust_id = new Integer(0);
		CustomerLocal local = EJBFactory.getCustomer();
		CustomerVO vo = new CustomerVO();

		for (int i = 0; i < cust_ids.length; i++) {

			if (cust_ids[i] != null && cust_ids[i].length() > 0) {
				cust_id = Utility.parseInt(Utility.trimNull(cust_ids[i]),
						new Integer(0));
			}
			if (cust_id.intValue() > 0) {
				vo.setCust_id(cust_id);
				vo.setInput_man(input_operatorCode);
				vo.setIp(ip);
				vo.setMac(mac);
				if (i==0)
					vo.setExport_flag(new Integer(3)); // ����ͨѶ¼
				else
					vo.setExport_flag(new Integer(10)); // һ���ε���ֻ��һ��log
				
				cust_list = local.listProcAll(vo);
				if (cust_list != null && cust_list.size() > 0) {
					list.add((Map) cust_list.get(0));
				}
			}
		}

		exportExcel(fileName, "�ͻ�ͨѶ¼", titleName, fieldName, fieldType,
				startRow, list);
	}

	public void importCustInfo(String custInfo, String fieldMethod,
			String fieldType, Integer inputOperator) throws Exception {

		String[][] custInfoArr = Utility.splitString(custInfo, "$", "#");
		String[] fieldMethodArr = Utility.splitString(fieldMethod, "$");
		String[] fieldTypeArr = Utility.splitString(fieldType, "$");
		int c = 0;

		CustomerVO vo = new CustomerVO();
		CustomerLocal customerLocal = EJBFactory.getCustomer();
		ProxySetMethod setMethod = new ProxySetMethod();
		Object[] value = new Object[1];
		for (int i = 0; i < custInfoArr.length; i++) {
			for (int j = 0; j < custInfoArr[i].length; j++) {
				c = Utility.parseInt(fieldTypeArr[j], 0);
				switch (c) {
				case 1:// String��������
					value[0] = Utility.trimNull(custInfoArr[i][j]);
					if ("0".equals(value[0]))
						value[0] = "";
					setMethod.invokeSetMethod(vo, fieldMethodArr[j], value);
					break;
				case 2:// Decimal
					value[0] = Utility.parseDecimal(custInfoArr[i][j],
							new BigDecimal("0"));
					setMethod.invokeSetMethod(vo, fieldMethodArr[j], value);
					break;
				case 4:// Integer����
					value[0] = Utility.parseInt(custInfoArr[i][j], new Integer(
							0));
					setMethod.invokeSetMethod(vo, fieldMethodArr[j], value);
					break;
				default:
					break;
				}
			}
			vo.setInput_man(inputOperator);
			customerLocal.importCustomer(vo);
		}
		customerLocal.remove();
	}

	/** *****************************************�������۹���************************************************************* */
	public synchronized static Integer getSequenceValue(String table,
			int sysytemFlag) throws Exception {
		Connection conn = IntrustDBManager.getConnection();
		CallableStatement stmt = conn.prepareCall("{call SP_GET_TMAXID(?,?)}");
		try {
			stmt.setString(1, table.toUpperCase());
			stmt.registerOutParameter(2, Types.INTEGER);
			stmt.execute();
			return new Integer(stmt.getInt(2));
		} finally {
			stmt.close();
			conn.close();
		}
	}

	
	


	
	public void insertRecord(String[] fields, String[] values, String table,
			int serialflag, int isystemFalg, Statement stmt) throws Exception {
		try {
			Integer iserial_no = new Integer(0);
			StringBuffer sFields = new StringBuffer("");
			StringBuffer sVlues = new StringBuffer("");

			for (int i = 0; i < fields.length; i++) {
				if (!Utility.trimNull(fields[i]).equals("")) {
					if (i == 0) {
						sFields.append("[" + fields[i]);
						sVlues.append(values[i]);
					} else if (i > 0 && i < fields.length - 1) {
						sFields.append("],[" + fields[i]);
						sVlues.append("," + values[i]);
					} else if (i == fields.length - 1) {
						sFields.append("],[" + fields[i] + "]");
						sVlues.append("," + values[i]);
					}
				} else {
					sFields.append("]");
					break;
				}
			}
			
			

			String insertSql = "";
			insertSql = "INSERT INTO " + table.toUpperCase() + "("
					+ sFields.toString() + ") VALUES(" +sVlues.toString().replaceAll("//??","&#8226")
					+ ")";
			// 1ֻ��serial_noû��book_code
		
			
			if (serialflag == 1) {
				iserial_no = getSequenceValue(table, isystemFalg);
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "( SERIAL_NO," + sFields.toString() + ") VALUES ("
						+ iserial_no + "," + sVlues.toString() + ")";
			}
			// 2ֻ��serial_noû��book_code=1
			if (serialflag == 2) {
				iserial_no = getSequenceValue(table, isystemFalg);
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "( SERIAL_NO,BOOK_CODE," + sFields.toString()
						+ ") VALUES (" + iserial_no + ",1," + sVlues.toString()
						+ ")";
			}
			// 3ֻ��serial_noû��book_code=2
			if (serialflag == 3) {
				iserial_no = getSequenceValue(table, isystemFalg);
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "( SERIAL_NO,BOOK_CODE," + sFields.toString()
						+ ") VALUES (" + iserial_no + ",2," + sVlues.toString()
						+ ")";
			}
			// 4û��serial_noû��book_code=1
			if (serialflag == 4) {
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "(BOOK_CODE," + sFields.toString() + ") VALUES ("
						+ "1," + sVlues.toString() + ")";
			}
			// 5û��serial_noû��book_code=2
			if (serialflag == 5) {
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "(BOOK_CODE," + sFields.toString() + ") VALUES ("
						+ "2," + sVlues.toString() + ")";
			}
			stmt.executeUpdate(insertSql);

		} catch (Exception e) {
			throw new BusiException("��������ʧ�ܣ�" + e.getMessage());
			
		}
	}

	public boolean readExcel2(PageContext in_pageContext, String objpath,
			int isystemFalg) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);
		String filename = objpath;
		if (!hasParse)
			parseRequest();

		String sfile_name = smartUpload.getRequest().getParameter("file_name");
		sfile_name = sfile_name.substring(sfile_name.lastIndexOf("\\") + 1);
		filename = objpath + "\\" + sfile_name;
		File f = new File(filename);
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);
		if (inputflag != 2)
			return false;
		try {
			//Connection conn = CrmDBManager.getConnection();

			Connection conn = IntrustDBManager.getConnection();
			Statement stmt = conn.createStatement();
			InputStream is = new FileInputStream(filename);
			
			//WorkbookSettings workbooksetting = new WorkbookSettings();
			//workbooksetting.setCellValidationDisabled(true);


			jxl.Workbook book = Workbook.getWorkbook(is);
			if (book == null)
				throw new Exception("�ļ�������!");
			// ��õ�һ�����������
			Sheet[] shets = book.getSheets();
			int iSheet = book.getNumberOfSheets();
			for (int i = 0; i < iSheet; i++) {
				Sheet sheet = book.getSheet(i);

				// WritableSheet ws2 =wwb.getSheet(0);
				int iRows = sheet.getRows();
				int iColums = sheet.getColumns();
				// Excelҳ���ĵ�һ���ַ���ŵ����������ֶ�serial_no��־,1Ϊ��������ֶ�,0Ϊû��.
				String table = sheet.getName();
				Utility.debugln("table��" + table);
				int flag = Utility.parseInt(sheet.getName().substring(0, 1), 0);
				// ȡ���Ƿ����������ֶ�serial_no��־
				if (flag == 1)
					table = table.substring(1, table.length());
				String[] fields = new String[iColums];
				String[] sfieldtypes = new String[iColums];
				for (int r = 0; r < iColums; r++) {
					Cell cel = sheet.getCell(r, 0);
					fields[r] = cel.getContents();
					// Utility.debugln("��"+r+"������"+fields[r]);
				}
				for (int j = 1; j < iRows; j++) {
					String[] values = new String[iColums];

					for (int k = 0; k < iColums; k++) {
						Cell cel = sheet.getCell(k, j);
						values[k] = "N'" + cel.getContents() + "'";
						//Utility.debugln("��"+k+"��ֵ��"+values[k]);
					}
					//Utility.debugln("------------------------------------------");
					if (Utility.trimNull(values[0]).equals("''") || Utility.trimNull(values[0]).equals("N''")) {
						continue;
					} else{
						insertRecord(fields, values, "INTRUST..OLD", flag,
								isystemFalg, stmt);
					}
				}
			}
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();

			book.close();
		} catch (Exception e) {
			throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
		}
		return true;
	}

	public boolean readExcelBenifitor(PageContext in_pageContext, String objpath,
			int isystemFalg) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);
		String filename = objpath;
		if (!hasParse)	parseRequest();

		String sfile_name = smartUpload.getRequest().getParameter("file_name");
		sfile_name = sfile_name.substring(sfile_name.lastIndexOf("\\") + 1);
		filename = objpath + "\\" + sfile_name;
		File f = new File(filename);
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);
		if (inputflag != 2) return false;
		
		try {
			Connection conn = CrmDBManager.getConnection();
			Statement stmt = conn.createStatement();
			InputStream is = new FileInputStream(filename);
			
			//WorkbookSettings workbooksetting = new WorkbookSettings();
			//workbooksetting.setCellValidationDisabled(true);


			jxl.Workbook book = Workbook.getWorkbook(is);
			if (book == null)
				throw new Exception("�ļ�������!");
			// ��õ�һ�����������
			Sheet[] shets = book.getSheets();
			int iSheet = book.getNumberOfSheets();
			for (int i = 0; i < iSheet; i++) {
				Sheet sheet = book.getSheet(i);

				// WritableSheet ws2 =wwb.getSheet(0);
				int iRows = sheet.getRows();
				int iColums = sheet.getColumns();
				// Excelҳ���ĵ�һ���ַ���ŵ����������ֶ�serial_no��־,1Ϊ��������ֶ�,0Ϊû��.
				String table = sheet.getName();
				Utility.debugln("table��" + table);

				String[] fields = new String[iColums];
				String[] sfieldtypes = new String[iColums];
				for (int r = 0; r < iColums; r++) {
					Cell cel = sheet.getCell(r, 0);
					fields[r] = cel.getContents();
					// Utility.debugln("��"+r+"������"+fields[r]);
				}
				for (int j = 1; j < iRows; j++) {
					String[] values = new String[iColums];

					for (int k = 0; k < iColums; k++) {
						Cell cel = sheet.getCell(k, j);
						values[k] = "N" + Utility.escapeSqlStr(cel.getContents().trim());
						//Utility.debugln("��"+k+"��ֵ��"+values[k]);
					}
					//Utility.debugln("------------------------------------------");
					if (Utility.trimNull(values[0]).equals("''") || Utility.trimNull(values[0]).equals("N''")) {
						continue;
					} else{
						insertRecord(fields, values, "OLD_BENIFITOR", 0,
								isystemFalg, stmt);
					}
				}
			}
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();

			book.close();
		} catch (Exception e) {
			throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
		}
		return true;
	}
	
	
	
    // ���ϴ��Ľ�㼯�����в�Ʒ��������(xls���ӱ���ʽ)����ʱ��EFCRM..TCONTRACTIMPORT����
    public boolean readZhejinTrustExcel(PageContext in_pageContext, String objpath,
            int isystemFalg) throws Exception {
        pageContext = in_pageContext;
        smartUpload.initialize(pageContext);
        String filename = objpath;
        if (!hasParse)
            parseRequest();

        String sfile_name = smartUpload.getRequest().getParameter("file_name");
        sfile_name = sfile_name.substring(sfile_name.lastIndexOf("\\") + 1);
        filename = objpath + "\\" + sfile_name;
        File f = new File(filename);
        int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
                .getRequest().getParameter("inputflag"), 0);
        if (inputflag != 2) return false;
        
        try {
            Connection conn = CrmDBManager.getConnection();

            Statement stmt = conn.createStatement();
            InputStream is = new FileInputStream(filename);

            jxl.Workbook book = Workbook.getWorkbook(is);
            if (book == null)
                throw new Exception("�ļ�������!");

            Map map = new HashMap();
			/*
			 * CREATE TABLE TCONTRACTIMPORT( BUSIN_FLAG NVARCHAR(3) NOT NULL,
			 *       --ҵ���־��020���Ϲ�022���깺024�����043���ֺ�050: ���� 
             * entrust_date INT NOT NULL,  --�������� 
             * product_code NVARCHAR(20) NULL, --���в�Ʒ���� 
             * product_name NVARCHAR(30) NOT NULL, --���в�Ʒ���� 
             * entrust_balance NUMERIC(18,2) NOT NULL, --ί�н�� 
             * entrust_share INT NULL, --ί�зݶ� 
             * client_name NVARCHAR(30) NOT NULL, --�ͻ����� 
             * id_kind NVARCHAR(6) NOT NULL,  --�ͻ�֤�����ͣ��Ϳ���ʽ����ӿڶ�����ͬ 
             * id_no NVARCHAR(20) NOT NULL, --֤������ )
			 */
			map.put("BUSIN_FLAG", "STRING");
			map.put("ENTRUST_DATE", "INT");
			map.put("PRODUCT_CODE", "STRING");
			map.put("PRODUCT_NAME", "STRING");
			map.put("ENTRUST_BALANCE", "DECIMAL");
			map.put("ENTRUST_SHARE", "INT");
			map.put("CLIENT_NAME", "STRING");
			map.put("ID_KIND", "STRING");
			map.put("ID_NO", "STRING");

            Sheet[] sheets = book.getSheets();
			for (int i = 0; i < sheets.length; i++) {
				int iRows = sheets[i].getRows();
                if (iRows==0) 
                    continue;
                
				int iCols = sheets[i].getColumns();
                
				Map map2 = new HashMap();
				for (int j=0; j<iCols; j++) {
					Cell cell = sheets[i].getCell(j, 0); // 1st row
					String content = cell.getContents().trim();
					if ("businflag".equalsIgnoreCase(content))
						content = "BUSIN_FLAG";
					else if ("prod_code".equalsIgnoreCase(content))
						content = "PRODUCT_CODE";
					else if ("prod_name".equalsIgnoreCase(content))
						content = "PRODUCT_NAME";
					else
						content = content.toUpperCase();

					if (map.containsKey(content)) {
                        map2.put(content, new Integer(j));
					}
				}

                if (! map2.containsKey("BUSIN_FLAG")) 
                    continue; // 
                
                String[] fields = new String[map2.size()];
                fields = (String[])map2.keySet().toArray(fields);            
                 
				for (int j=1; j<iRows; j++) {
                    String[] values = new String[fields.length]; 
                    
                    int k;
					for (k=0; k<fields.length; k++) {
						Cell cell = sheets[i].getCell(((Integer)map2.get(fields[k])).intValue(), j);
                        String content = cell.getContents().trim(); //
                        if ("BUSIN_FLAG".equals(fields[k]) && "".equals(content))
                            break;
                        
						String type = (String) map.get(fields[k]);
						if ("STRING".equals(type)) {    
                            content = content.replaceAll("'", "''"); // ���ַ����е�'����ת�壬������sql server
							values[k] = "N'" + content + "'";
                        } else if ("INT".equals(type)) {
                            if (! content.matches("\\d+(.0+)?")) content = "0"; // 
                            values[k] = content;
                        } else if ("DECIMAL".equals(type)) {
                            if (! content.matches("\\d+(.\\d+)?")) content = "0.0"; // 
                            values[k] = content;
                        }
					}                    
                    
                    if (k<fields.length)
                        continue;
                                 
                    insertRecord(fields, values, "EFCRM..TCONTRACTIMPORT", 0, isystemFalg, stmt);
                }
            }
            if (conn != null)
                conn.close();
            if (stmt != null)
                stmt.close();

            book.close();
        } catch (Exception e) {
            throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
        }
        return true;
    }

    // ��ձ����Ž�㼯�����в�Ʒ�������ݵ���ʱ��EFCRM..TCONTRACTIMPORT��
    public boolean delZhejinData(PageContext in_pageContext) throws Exception {
        pageContext = in_pageContext;
        smartUpload.initialize(pageContext);

        if (!hasParse)
            parseRequest();
        int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
                .getRequest().getParameter("inputflag"), 0);

        if (inputflag != 3)
            return false;
        Connection conn = CrmDBManager.getConnection();
        try {
            PreparedStatement stmt = conn
                    .prepareStatement("DELETE FROM EFCRM..TCONTRACTIMPORT");

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusiException("ɾ����ʷ����Ϣʧ��: " + e.getMessage());
        } finally {
            conn.close();
        }

    }
    
	public boolean insertContractRecord(PageContext in_pageContext,
			Integer input_man, Integer dt_intrust) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);
		Connection conn = null;
		CallableStatement stmt = null;

		if (!hasParse)
			parseRequest();
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);

		if (inputflag != 1)
			return false;

		try {
			String insertSql = null;
			if (dt_intrust.intValue() == 1) {
				insertSql = "{?=call SP_IMPORT_ZY_HISTORY_CRM1(?,?)}";
			} else {
				insertSql = "{?=call SP_IMPORT_ZY_HISTORY_CRM2(?,?)}";
			}

			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(insertSql);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.registerOutParameter(2, Types.VARCHAR);
			if (input_man != null)
				stmt.setInt(3, input_man.intValue());
			else
				stmt.setInt(3, 0);

			stmt.executeUpdate();

			int iret = stmt.getInt(1);
			String strmesg = stmt.getString(2);

			if (iret < 0)
				throw new Exception(strmesg);
		} catch (Exception e) {
			throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
		} finally {
			stmt.close();
			conn.close();
		}
		return true;
	}

	public boolean insertContractRecord(PageContext in_pageContext,
			Integer input_man) throws Exception {
 		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);
		Connection conn = null;
		CallableStatement stmt = null;

		if (!hasParse)
			parseRequest();
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);

		if (inputflag != 1)
			return false;

		try {
			String insertSql = null;
			insertSql = "{?=call SP_IMPORT_ZY_HISTORY_NEW(?,?)}";

			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(insertSql);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.registerOutParameter(2, Types.VARCHAR);
			if (input_man != null)
				stmt.setInt(3, input_man.intValue());
			else
				stmt.setInt(3, 0);

			stmt.executeUpdate();

			int iret = stmt.getInt(1);
			String strmesg = stmt.getString(2);

			if (iret < 0)
				throw new Exception(strmesg);
		} catch (Exception e) {
			throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
			//e.printStackTrace();
		} finally {
			stmt.close();
			conn.close();
		}
		return true;
	}

	
	public boolean readExcel4(PageContext in_pageContext, String objpath,
			Integer input_operatorCode) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);
		String filename = objpath;
		if (!hasParse)
			parseRequest();
		String sfile_name = smartUpload.getRequest().getParameter("file_name");
		sfile_name = sfile_name.substring(sfile_name.lastIndexOf("\\") + 1);
		filename = objpath + "\\" + sfile_name;
		File f = new File(filename);
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);
		if (inputflag != 2)
			return false;
		jxl.Workbook book = null;
		try {
			InputStream is = new FileInputStream(filename);
			book = Workbook.getWorkbook(is);
			if (book == null)
				throw new Exception("�ļ�������!");

			//��õ�һ�����������

			Sheet[] shets = book.getSheets();

			int iSheet = book.getNumberOfSheets();

			Sheet sheet = book.getSheet(0);
			int iRows = sheet.getRows();

			for (int i = 1; i < iRows; i++) {
				Cell cell = sheet.getCell(0, i);
				String dzDateStr = Utility.trimNull(cell.getContents());
				if ("".equals(dzDateStr)) continue;
				int dzDateInt = 0;
				try {
					Date dzDate = new SimpleDateFormat("yyyy-MM-dd")
							.parse(dzDateStr.trim());
					
					dzDateInt = Utility.getDateInt(dzDate);
				} catch (ParseException e) {
					throw new Exception("��������[" + dzDateStr
							+ "]���Ϸ�,���޸�Ϊyyyy-MM-dd���͵�����");
				}
				//���˽��
				cell = sheet.getCell(1, i);
				String dzDateMoneyStr = Utility.trimNull(cell.getContents());
				BigDecimal dzDateMoney = null;
				try {
					dzDateMoney = new BigDecimal(dzDateMoneyStr.trim());
				} catch (NumberFormatException e) {
					throw new Exception("���˽��[" + dzDateMoneyStr + "]���Ϸ�");
				}
				//���˷ݶ�
				cell = sheet.getCell(2, i);
				String dzAmountStr = Utility.trimNull(cell.getContents());
				BigDecimal dzAmount = null;
				try {
					dzAmount = new BigDecimal(dzAmountStr.trim());
				} catch (NumberFormatException e) {
					throw new Exception("���˷ݶ�[" + dzAmount + "]���Ϸ�");
				}
				//�ͻ�����
				cell = sheet.getCell(3, i);
				String custName = Utility.trimNull(cell.getContents()).trim();
				if ("".equals(custName)) {
					throw new Exception("�ͻ���������Ϊ��");
				}
				
				//�ͻ����
				cell = sheet.getCell(4, i);
				String custType = Utility.trimNull(cell.getContents()).trim();
				if ("".equals(custType)) {
					throw new Exception("�ͻ����Ͳ���Ϊ��");
				}
				if (!"����".equals(custType) && !"����".equals(custType)) {
					throw new Exception("�ͻ����ͣ�ֻ���ǻ��������");
				}
				int custTypeInt = "����".equals(custType) ? 2 : 1;
				//�Ƽ�������
				cell = sheet.getCell(5, i);
				String cityName = Utility.trimNull(cell.getContents()).trim();
				
				//����������
				cell = sheet.getCell(6, i);
				String bankName = Utility.trimNull(cell.getContents()).trim();
				
				String bankId = Argument.getTdictparamValueByContent(1103,bankName);
				
				//����������֧��
				cell = sheet.getCell(7, i);
				String bankSubName = Utility.trimNull(cell.getContents()).trim();
				
				//�������˺�
				cell = sheet.getCell(8, i);
				String bankAcct = Utility.trimNull(cell.getContents()).trim();
				//�ʽ���Դ
				cell = sheet.getCell(9, i);
				String moneyORIGIN = Utility.trimNull(cell.getContents()).trim();
				cell = sheet.getCell(10, i);
				String subMoneyORIGIN = Utility.trimNull(cell.getContents()).trim();
				
				String sql = "INSERT TMONEYDATA_IMPORT(DZ_DATE,TO_MONEY,TO_AMOUNT,CUST_TYPE,CUST_NAME,CITY_NAME,INPUT_MAN,BANK_ID,BANK_NAME,BANK_SUB_NAME,BANK_ACCT,MONEY_ORIGIN,SUB_MONEY_ORIGIN)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				Object[] params = new Object[13];
				params[0] = new Integer(dzDateInt);
				params[1] = dzDateMoney;
				params[2] = dzAmount;
				params[3] = new Integer(custTypeInt);
				params[4] = custName;
				params[5] = cityName;
				params[6] = input_operatorCode;
				params[7] = bankId;
				params[8] = bankName;
				params[9] = bankSubName;
				params[10] = bankAcct;
				params[11] = moneyORIGIN;
				params[12] = subMoneyORIGIN;
				CrmDBManager.executeSql(sql, params);

			}
			//ɾ���Ѿ���������

		} catch (Exception e) {
			throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
		} finally {
			if (book != null)
				book.close();
		}
		return true;
	}
	
	public boolean deleteRead4(PageContext in_pageContext,
			Integer input_operatorCode) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);

		if (!hasParse)
			parseRequest();
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);
		if (inputflag != 3)
			return false;
		try {
			String[] s = smartUpload.getRequest().getParameterValues(
					"serial_no");
			if (s != null) {
				for (int i = 0; i < s.length; i++) {
					int serial_no = Utility.parseInt(s[i], 0);
					if (serial_no != 0) {
						String sql = "DELETE TMONEYDATA_IMPORT WHERE SERIAL_NO = ? ";
						CrmDBManager.executeSql(sql, new Object[] { new Integer(serial_no) });
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("ɾ����ʧ��,������Ϣ:" + e.getMessage());
		} finally {

		}
		return true;

	}
	
	public boolean saveRead4(PageContext in_pageContext,
			Integer input_operatorCode) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);

		if (!hasParse)
			parseRequest();
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);
		int product_id = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("product_id"), 0);
		int book_code = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("book_code"), 1);
		int sub_product_id = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("sub_product_id"), 0);
		int sbf_serial_no = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("sbf_serial_no"), 0);
		if (inputflag != 1)
			return false;
		ContractLocal  local   = EJBFactory.getContract();
		if(product_id != 0){
			try {
				
				String[] s = smartUpload.getRequest().getParameterValues("serial_no");
				StringBuffer buffer = new StringBuffer();
				boolean b = false;
				if (s != null) {
					for (int i = 0; i < s.length; i++) {
						int serial_no = Utility.parseInt(s[i], 0);
						if (serial_no != 0) {
							buffer.append(serial_no);
							buffer.append(",");
							b = true;
						}
					}
				}
				if(b){
					buffer.deleteCharAt(buffer.lastIndexOf(","));
				}
				Object []params =  new Object[6];
				params[0] = new Integer(book_code);
				params[1] = new Integer(product_id);
				params[2] = new Integer(sub_product_id);
				params[3] = buffer.toString();
				params[4] = input_operatorCode;
				params[5] = new Integer(sbf_serial_no);
				String udpateSQL = "{call SP_CHECK_TMONEYDATA_IMPORT(?,?,?,?,?,?)}";
				CrmDBManager.executeSql(udpateSQL,params);
				
			} catch (Exception e) {
				throw new Exception("����ȷ�ϳɹ�,������Ϣ:" + e.getMessage());
			} finally {
	
			}
		}
		return true;

	}
	
	public boolean insertBenifitorRecord(PageContext in_pageContext,
			Integer input_man) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);
		Connection conn = null;
		CallableStatement stmt = null;

		if (!hasParse)
			parseRequest();
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);

		if (inputflag != 1)
			return false;

		try {
			String insertSql = null;
			insertSql = "{?=call SP_IMPORT_TY_BENIFITOR(?,?)}";

			conn = CrmDBManager.getConnection();
			stmt = conn.prepareCall(insertSql);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.registerOutParameter(2, Types.VARCHAR);
			if (input_man != null)
				stmt.setInt(3, input_man.intValue());
			else
				stmt.setInt(3, 0);

			stmt.executeUpdate();

			int iret = stmt.getInt(1);
			String strmesg = stmt.getString(2);

			if (! Utility.trimNull(strmesg).equals(""))
				throw new Exception(strmesg);
			//if (iret < 0)
			//	throw new Exception(strmesg);
		} catch (Exception e) {
			throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
		} finally {
			stmt.close();
			conn.close();
		}
		return true;
	}
	
	// ɾ����ʷ����Ϣ
	public boolean delOldData(PageContext in_pageContext) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);

		if (!hasParse)
			parseRequest();
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);

		if (inputflag != 3)
			return false;
		Connection conn = CrmDBManager.getConnection();
		try {
			PreparedStatement stmt = conn
					.prepareStatement("DELETE FROM INTRUST.dbo.OLD");

			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusiException("ɾ����ʷ����Ϣʧ��: " + e.getMessage());
		} finally {
			conn.close();
		}

	}

	//	 ɾ�� ��������Ϣ���м䵼������������
	public boolean delOldBenifitorData(PageContext in_pageContext) throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);

		if (!hasParse)	parseRequest();
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);

		if (inputflag != 3) return false;
		
		Connection conn = CrmDBManager.getConnection();
		try {
			PreparedStatement stmt = conn
					.prepareStatement("DELETE FROM OLD_BENIFITOR");

			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusiException("ɾ����ʷ����Ϣʧ��: " + e.getMessage());
		} finally {
			conn.close();
		}

	}
	
	// ������ʷ��Ϣ
	public boolean correctOldData(PageContext in_pageContext, Integer input_man)
			throws Exception {
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);

		if (!hasParse)
			parseRequest();
		int inputflag = enfo.crm.tools.Utility.parseInt(smartUpload
				.getRequest().getParameter("inputflag"), 0);

		if (inputflag != 4)
			return false;
		Connection conn = CrmDBManager.getConnection();
		try {
			String insertSql = "{?=call SP_COMPARE_IMPORT_DATA(?,?,?)}";

			CallableStatement stmt = conn.prepareCall(insertSql);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.registerOutParameter(2, Types.VARCHAR);

			stmt.setString(3, "");
			if (input_man != null)
				stmt.setInt(4, input_man.intValue());
			else
				stmt.setInt(4, 0);

			stmt.executeUpdate();
			int iret = stmt.getInt(1);
			String strmesg = stmt.getString(1);
			if (iret < 0)
				throw new Exception(strmesg);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusiException("��������������Ϣʧ��: " + e.getMessage());
		} finally {
			conn.close();
		}

	}

	/**
	 * ���������˲�ѯ��Ϣ
	 * 
	 * @author
	 * @throws Exception
	 */
	public void exportBenifiter(Integer book_code, Integer input_man,
			String menu_id, String viewStr, String sQuery, String fileName,
			int flag) throws Exception {

		String[] serial_nos = pageContext.getRequest().getParameterValues(
				"serial_no");

		List arrayList = new ArrayList();
		List serialist = new ArrayList();
		Map map = null;

		for (int i = 0; i < serial_nos.length; i++) {
			if (serial_nos[i] != null && serial_nos[i].length() > 0) {
				serialist.add(Utility.parseInt(Utility.trimNull(serial_nos[i]),
						new Integer(0)));
			}
		}

		BenifiterQueryLocal benifitor = EJBFactory.getBenifiterQuery();
		BenifitorVO vo = new BenifitorVO();
		try {
			List list = new ArrayList();

			if (!sQuery.equals("")) {
				String[] paras = Utility.splitString(sQuery + " ", "$");

				vo.setContract_sub_bh(paras[0].trim());
				vo.setCust_name(paras[1].trim());
				vo.setCust_no(paras[2].trim());
				vo.setSy_cust_no(paras[3].trim());
				vo.setProv_level(paras[4].trim());
				vo.setBen_status(paras[5].trim());
				vo.setCust_type(Utility.parseInt(paras[6].trim(),
						new Integer(0)));
				vo.setProduct_id(Utility.parseInt(paras[7].trim(), new Integer(
						0)));
				vo.setInput_man(input_man);
				vo.setBook_code(book_code);
				vo.setExport_flag(new Integer(1));
				vo.setQuery_flag(new Integer(1));
				list = benifitor.listbySqlAll(vo);
				//				��ȡѡ���ѡ��
				for (int j = 0; j < list.size(); j++) {
					map = (Map) list.get(j);

					for (int i = 0; i < serialist.size(); i++) {
						if (serialist.get(i).equals(
								Utility.parseInt(Utility.trimNull(map
										.get("SERIAL_NO")), new Integer(0)))) {
							arrayList.add(map);
							serialist.remove(i);
						}
					}
				}

			}

			publicExport(fileName, "��������Ϣ", viewStr, arrayList, menu_id,
					input_man, flag);
		} catch (Exception e) {
			throw new Exception("��ѯʧ��:" + e.getMessage());
		}
	}

    /**
     * �����ɿ��ѯ��Ϣ
     * 
     * @author dingyj
     */
    public void exportMoneyDetail(Integer book_code, Integer input_man,
            String menu_id, String viewStr, String sQuery, String fileName,
            int flag) throws Exception {
        try {
            List list = new ArrayList();
            if (!sQuery.equals("")) {
                String[] paras = Utility.splitString(sQuery + " ", "$");
                MoneyDetailLocal detail = EJBFactory.getMoneyDetail();
                MoneyDetailVO vo = new MoneyDetailVO();                
                
                vo.setProduct_id(Utility.parseInt(paras[0].trim(), new Integer(0)));
                vo.setContract_sub_bh(paras[1].trim());
                
                vo.setBook_code(book_code);
                vo.setInput_man(input_man);
                vo.setCust_name(paras[2].trim());
                vo.setCust_no(paras[3].trim());                
                vo.setSub_product_id(Utility.parseInt(paras[4].trim(),new Integer(0)));
                
                list = detail.listBySql(vo);
            }
            publicExport(fileName, "�ɿ���Ϣ", viewStr, list, menu_id, input_man,
                    flag);
        } catch (Exception e) {
            throw new Exception("��ѯʧ��:" + e.getMessage());
        }
    }
    
    /**
     * ���������˻��޸Ĳ�ѯ��Ϣ
     * 
     * @author dingyj
     */
    public void exportBenifiterModi(Integer book_code, Integer input_man,
            String menu_id, String viewStr, String sQuery, String fileName,
            int flag) throws Exception {
        try {
            List list = new ArrayList();
            if (!"".equals(sQuery)) {
                String[] paras = Utility.splitString(sQuery + " ", "$");
                BenifitorLocal benifitor = EJBFactory.getBenifitor();
                BenifitorVO vo = new BenifitorVO();
                vo.setBook_code(book_code);
                vo.setContract_sub_bh(paras[3].trim());
                vo.setCust_name(paras[1].trim()); 
                vo.setCust_no(paras[2].trim());
                vo.setProduct_id(Utility.parseInt(paras[0].trim(),
                        new Integer(0)));
                vo.setInput_man(input_man); 
                vo.setSub_product_id(Utility.parseInt(paras[4].trim(),
                        new Integer(0)));                 
                list = benifitor.queryModiAcctDetail(vo);
            }
            publicExport(fileName, "�����˻��޸���Ϣ", viewStr, list, menu_id,
                    input_man, flag);
        } catch (Exception e) {
            throw new Exception("��ѯʧ��:" + e.getMessage());
        }
    }
    
    /**
     * ������ͬ�����ϸ��ѯ��Ϣ
     * 
     * @author dingyj
     */
   public void exportContractList(Integer book_code, Integer input_man,
            String menu_id, String viewStr, String sQuery, String fileName,
            int flag) throws Exception {
        try {
            List list = new ArrayList();
            if (!"".equals(sQuery)) {
                String[] paras = Utility.splitString(sQuery + " ", "$");
                ContractLocal contract = EJBFactory.getContract();
                ContractVO vo = new ContractVO();
                vo.setBook_code(book_code);
                vo.setInput_man(input_man);
                vo.setProduct_id(Utility.parseInt(paras[0].trim(),
                        new Integer(0)));
                vo.setContract_sub_bh(paras[1].trim());
                vo.setHt_status(paras[2].trim());
                vo.setSub_product_id(Utility.parseInt(paras[3].trim(),
                        new Integer(0)));                
                list =  contract.listContractList(vo);
            }
            publicExport(fileName, "��ͬ�����ϸ��Ϣ", viewStr, list, menu_id,
                    input_man, flag);
        } catch (Exception e) {
            throw new Exception("��ѯʧ��:" + e.getMessage());
        }
    }
    
    /**
     * ��������Ȩת�ò�ѯ��Ϣ
     * 
     * @author dingyj
     */
    public void exportContractExchange(Integer book_code, Integer input_man,
            String menu_id, String viewStr, String sQuery, String fileName,
            int flag) throws Exception {
        try {
            List list = new ArrayList();
            if (!"".equals(sQuery)) {
                String[] paras = Utility.splitString(sQuery + " ", "$");
                BenChangeLocal query = EJBFactory.getBenChange();
                BenChangeVO vo = new BenChangeVO();
                vo.setBook_code(book_code);
                vo.setInput_man(input_man);
                vo.setTrans_type(paras[0].trim());
                vo.setFrom_cust_name(paras[1].trim());
                vo.setTrans_date_begin(Utility.parseInt(paras[2].trim(),
                        new Integer(0)));
                vo.setTrans_date_end(Utility.parseInt(paras[3].trim(),
                        new Integer(0)));
                vo.setContract_sub_bh(paras[4].trim());
                vo.setProduct_id(Utility.parseInt(paras[5].trim(),
                        new Integer(0)));
                vo.setSub_product_id(Utility.parseInt(paras[6].trim(),
                        new Integer(0)));
                list = query.list(vo);
            }
            publicExport(fileName, "����Ȩת����Ϣ", viewStr, list, menu_id,
                    input_man, flag);
        } catch (Exception e) {
            throw new Exception("��ѯʧ��:" + e.getMessage());
        }
    }

    /**
     * �������������ܲ�ѯ��Ϣ
     * 
     * @author dingyj
     */
    public void exportSquare(Integer book_code, Integer input_man,
            String menu_id, String viewStr, String sQuery, String fileName,
            int flag) throws Exception {
        try {
            List list = new ArrayList();
            if (!"".equals(sQuery)) {
                String[] paras = Utility.splitString(sQuery + " ", "$");
                ProductLocal local = EJBFactory.getProduct();
                GainTotalVO vo = new GainTotalVO();
                vo.setBook_code(book_code);
                vo.setProduct_id(Utility.parseInt(paras[0].trim(),
                        new Integer(0)));
                vo.setSy_type(paras[1].trim());
                vo.setSy_date(Utility.parseInt(paras[2].trim(), new Integer(
                        0)));
                vo.setInput_man(input_man);
                vo.setSub_product_id(Utility.parseInt(paras[3].trim(), new Integer(
                        0)));
                list = local.listGainTotal(vo, 1, -1).getRsList();
                local.remove();
            }
            publicExport(fileName, "������������Ϣ", viewStr, list, menu_id,
                    input_man, flag);
        } catch (Exception e) {
            throw new Exception("��ѯʧ��:" + e.getMessage());
        }
    }
    
    /**
     * �����Ϲ���ѯ��Ϣ
     * 
     * @author dingyj
     */
    public void exportPurchase(Integer book_code, Integer input_man,
            String menu_id, String viewStr, String sQuery, String fileName,
            int flag) throws Exception {
        try {
            List list = new ArrayList();
            String[] paras = Utility.splitString(sQuery + " ", "$");
            if (paras.length != 1) {
                ContractLocal contract = EJBFactory.getContract();
                ContractVO vo = new ContractVO();
                vo.setBook_code(book_code);
                vo.setInput_man(input_man);
                vo.setProduct_id(Utility.parseInt(paras[13].trim(),
                        new Integer(0)));
                vo.setContract_sub_bh(Utility.trimNull(paras[1].trim()));
                vo.setCust_name(Utility.trimNull(paras[3].trim()));
                vo.setCust_no(Utility.trimNull(paras[6].trim()));
                vo.setCard_id(Utility.trimNull(paras[4].trim()));
                vo.setOnly_thisproduct(new Integer(0));
                vo.setCust_type(Utility.parseInt(paras[2].trim(),
                        new Integer(0)));
                vo.setMin_rg_money(Utility.parseDecimal(paras[8].trim(),
                        null));
                vo.setMax_rg_money(Utility.parseDecimal(paras[9].trim(),
                        null));
                vo.setProduct_name(Utility.trimNull(paras[14].trim()));
                vo.setService_man(Utility.parseInt(paras[10].trim(),
                        new Integer(0)));
                vo.setCity_name(Utility.trimNull(paras[11].trim()));
                vo.setSub_product_id(Utility.parseInt(paras[20].trim(),new Integer(0)));

                list = contract.listAll(vo);
            }
            publicExport(fileName, "�ͻ��Ϲ���Ϣ", viewStr, list, menu_id, input_man,
                    flag);

        } catch (Exception e) {
            throw new Exception("��ѯʧ��:" + e.getMessage());
        }
    }
    
	/**
	 * ���Excel����
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param excelTitle
	 *            ����
	 * @param viewStri
	 *            ӵ�е��ֶ�
	 * @param list
	 *            ���ݼ�
	 * @throws Exception
	 */
	public void publicExport(String fileName, String excelTitle,
			String viewStr, List list, String menu_id, Integer input_man,
			int flag) throws Exception {
		Map map = new HashMap();
		String fee_jk_type = "";

		// ��ø�Ա����ӵ�еĲ˵�
		String tempView = Argument.getMyMenuViewStr(menu_id, input_man);
		// �����Ա��û�����ò˵�����ΪĬ�ϲ˵�
		if (tempView != null && !tempView.equals("")) {
			viewStr = tempView;
		}
		// ��ӵ�еĲ˵������õ����в˵�����ƥ��
		Map fieldsMap = Argument.getMenuViewMap(menu_id, viewStr);
		if (fieldsMap == null || fieldsMap.isEmpty()) {
			fieldsMap = new HashMap();
			viewStr = "";
		}
		// ������ֶε�����
		String[] fieldsArr = Utility.splitString(viewStr, "$");
		// �����ļ�
		OutputStream outStr = getResponseStream(fileName + ".xls");
		WorkbookSettings wbs = new WorkbookSettings();
		wbs.setGCDisabled(true);
		WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
		WritableSheet ws = wwb.createSheet("��һҳ", 0);

		// 1������ĸ�ʽ
		// �ƶ����ִ���ʽ
		WritableFont font = new WritableFont(WritableFont.createFont("���Ŀ���"),
				20, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		// ָ����Ԫ��ĸ�������
		WritableCellFormat format = new WritableCellFormat(font);
		// ָ��ˮƽ����ķ�ʽ����
		format.setAlignment(Alignment.CENTRE);
		// �ƶ���ֱ����ķ�ʽ����
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		// �ϲ���Ԫ��
		ws.mergeCells(0, 0, (fieldsArr.length) - 1, 0);
		// �����и�
		ws.setRowView(0, 500);
		// ���ñ߿�
		format.setBorder(Border.ALL, BorderLineStyle.THIN);
		// ��ӱ���
		jxl.write.Label labelC = new jxl.write.Label(0, 0, excelTitle, format);
		ws.addCell(labelC);

		// 2����ͷ�ĸ�ʽ
		WritableFont fontTop = new WritableFont(WritableFont.createFont("����"),
				14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat formatTop = new WritableCellFormat(fontTop);
		formatTop.setAlignment(Alignment.CENTRE);
		ws.setRowView(1, 400);
		formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);

		// 3�����ݵĸ�ʽ
		// ����
		WritableFont fontLeft = new WritableFont(WritableFont.createFont("����"),
				12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
		formatLeft.setAlignment(Alignment.LEFT);
		formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
		formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
		// ����
		WritableFont fontCenter = new WritableFont(WritableFont
				.createFont("����"), 12, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
		formatCenter.setAlignment(Alignment.CENTRE);
		formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
		formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
		// ����
		WritableFont fontRight = new WritableFont(
				WritableFont.createFont("����"), 12, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat formatRight = new WritableCellFormat(fontRight);
		formatRight.setAlignment(Alignment.RIGHT);
		formatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
		formatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

		try {
			// ���ñ�ͷ
			for (int i = 0; i < fieldsArr.length; i++) {
				ws.setColumnView(i, 20);
				Label lableTitle = new jxl.write.Label(i, 1, ((Map) fieldsMap
						.get(fieldsArr[i])).get("FIELD_NAME").toString(),
						formatTop);
				ws.addCell(lableTitle);
			}

//           ��������
            Integer serial_no = null;
            for (int j = 0; j < list.size(); j++) {
                map = (Map) list.get(j);
                serial_no = Utility.parseInt(Utility.trimNull(map
                        .get("SERIAL_NO")), new Integer(0));
                for (int k = 0; k < fieldsArr.length; k++) {
                    Label content = null;
                    ws.setRowView((k + 2), 400);
                    // ���ֶε�����
                    int iType = ((Integer) ((Map) fieldsMap.get(fieldsArr[k]))
                            .get("FIELD_TYPE")).intValue();
                    switch (iType) {
                    case 1: {
                        String temp = fieldsArr[k].equals("PRODUCT_NAME") ? Utility.trimNull(map.get("LIST_NAME")).equals("") ? Utility.trimNull(map.get(fieldsArr[k])) : Utility.trimNull(map.get(fieldsArr[k])) + "(" + Utility.trimNull(map.get("LIST_NAME")) + ")" : Utility.trimNull(map.get(fieldsArr[k]));
                        content = new Label(k, (j + 2), temp, formatLeft);
                        break;
                    }
                    case 2: {
                        content = new Label(k, (j + 2), Utility.trimNull(Format
                                .formatMoney(Utility.stringToDouble(Utility
                                        .trimNull(map.get(fieldsArr[k]))))),
                                formatRight);
                        break;
                    }
                    case 3: {
                        content = new Label(k, (j + 2), Utility.trimNull(Format
                                .formatDateLine(Utility.parseInt(Utility
                                        .trimNull(map.get(fieldsArr[k])),
                                        new Integer(0)))), formatCenter);
                        break;
                    }
                    case 31: {
                        Integer temp_date = Utility.parseInt(Utility.trimNull(map.get(fieldsArr[k])), new Integer(0));
                        String temp_date_str = "";                      
                        if(temp_date.intValue() != 0) temp_date_str = Utility.getDatePart(temp_date.intValue(),1)+"��"+Utility.getDatePart(temp_date.intValue(),2)+"��";                       
                        content = new Label(k, (j + 2),temp_date_str, formatCenter);
                        break;
                    }
                    case 4: {
                        content = new Label(k, (j + 2), Utility
                                .trimNull(Utility.parseInt(Utility.trimNull(map
                                        .get(fieldsArr[k])), null)),
                                formatRight);
                        break;
                    }
                    case 5: {
                        content = new Label(
                                k,
                                (j + 2),
                                Utility
                                        .trimNull(Argument
                                                .getTintegerparamValue(
                                                        Utility
                                                                .parseInt(
                                                                        Utility
                                                                                .trimNull((((Map) fieldsMap
                                                                                        .get(fieldsArr[k]))
                                                                                        .get("PARAM_TYPE_ID"))),
                                                                        new Integer(
                                                                                0)),
                                                        Utility
                                                                .parseInt(
                                                                        Utility
                                                                                .trimNull(map
                                                                                        .get(fieldsArr[k])),
                                                                        new Integer(
                                                                                0)))),
                                formatLeft);
                        break;
                    }
                    case 51 : {
                        Boolean temp_flag = (Boolean)map.get(fieldsArr[k]);
                        String temp_str = "";
                        if(temp_flag!=null&&temp_flag.booleanValue()){temp_str = "��";}else{temp_str = "��";}
                        content = new Label(k, (j + 2),temp_str, formatCenter);
                        break;                  
                    }
                    case 6: {
                        // �����˻��޸Ĳ�ѯ������ʾ
                        if (flag == 3 && !(serial_no.equals(new Integer(0)))) {
                            String str = "";
                            AttachmentLocal attachment = EJBFactory
                                    .getAttachment();
                            AttachmentVO vo = new AttachmentVO();
                            vo.setDf_talbe_id(new Integer(8));
                            vo.setDf_serial_no(serial_no);
                            List aList = attachment.load(vo);
                            for (int i=0; i<aList.size(); i++) {
                                Map aMap = (Map)aList.get(i);
                                str += aMap.get("ORIGIN_NAME") + "  "; // ѭ����ø���
                            }
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(str), formatLeft);
                        }
                        // ����Ȩת�ò�ѯ��ò�Ʒ����
                        if (flag == 5) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getProductName(Utility
                                            .parseInt(Utility.trimNull(map
                                                    .get(fieldsArr[k])),
                                                    new Integer(0)))),
                                    formatLeft);
                        }
                        // ���������ܲ�ѯ ��ò�Ʒ���
                        if (flag == 6) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getProductCode(Utility
                                            .parseInt(Utility.trimNull(map
                                                    .get("PRODUCT_ID")),
                                                    new Integer(0)))),
                                    formatLeft);
                        }
                        // ���ʺ�ͬ��Ϣ��û�����
                        if (flag == 7) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getCurrencyName(Utility
                                            .trimNull(map.get(fieldsArr[k])))),
                                    formatLeft);
                        }
                        // �������ͬ��Ϣ��û�����
                        if (flag == 8) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getCurrencyName(Utility
                                            .trimNull(map.get(fieldsArr[k])))),
                                    formatLeft);
                        }
                        // ��ȨͶ��>>��ͬ��Ϣ��ѯ���ӡ��û����� ����Ŀ��ѯ
                        if (flag == 11 || flag == 12) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getCurrencyName(Utility
                                            .trimNull(map.get(fieldsArr[k])))),
                                    formatLeft);
                        }
                        break;
                    }
                    case 7: // ��ò���Ա����
                    {
                        content = new Label(k, (j + 2), Utility
                                .trimNull(Argument.getIntrustOpName(Utility
                                        .parseInt(Utility.trimNull(map
                                                .get(fieldsArr[k])),
                                                new Integer(0)))), formatLeft);
                        break;
                    }
                    case 8: {
                        if (flag == 3) // �����˻��޸Ĳ�ѯ �����������+ԭ֧������
                        {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getBankName(Utility
                                            .trimNull(map.get(fieldsArr[k]))))
                                    + Utility.trimNull(map
                                            .get("OLD_BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        if (flag == 5) // ����Ȩת�ò�ѯ��ʾ����������-����֧������
                        {
                            content = new Label(k, (j + 2),
                                    Utility.trimNull(map.get(fieldsArr[k]))
                                            + Utility.trimNull(map
                                                    .get("BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        if (flag == 6) // ���������ܲ�ѯ ��ò�Ʒ����
                        {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getProductName(Utility
                                            .parseInt(Utility.trimNull(map
                                                    .get("PRODUCT_ID")),
                                                    new Integer(0)))),
                                    formatLeft);
                        }
                        if (flag == 10) // ���չ���>>��ҵ���>>������Ϣ
                        {
                            content = new Label(k, (j + 2),
                                    Utility.trimNull(map.get(fieldsArr[k]))
                                            + Utility.trimNull(map
                                                    .get("BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        if (flag == 13 || flag == 15) // ��Ϣ��ѯ>>��Ŀ��Ϣ
                        {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(map.get(fieldsArr[k]))
                                    + Utility.trimNull(map
                                            .get("TG_BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        break;
                    }
                    case 9: // �����˻��޸Ĳ�ѯ ��ʾ��ͬ��� �������
                    {
                        content = new Label(k, (j + 2), Utility.trimNull(map
                                .get(fieldsArr[k]))
                                + Utility.trimNull(map.get("LIST_ID")),
                                formatLeft);
                        break;
                    }
                    case 10: {
                        if (flag == 3) // �����˻��޸Ĳ�ѯ ���ԭ��������+ԭ֧������
                        {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getBankName(Utility
                                            .trimNull(map.get(fieldsArr[k]))))
                                    + Utility.trimNull(map
                                            .get("NEW_BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        // ��Ϣ��ѯ>>��Ŀ��ѯ
                        if (flag == 12) {
                            String str = Utility.trimNull(
                                    map.get("BP_PERIOD_UNIT")).equals("1") ? "��"
                                    : "".equals("2") ? "��" : "��";
                            if (!"".equals(Utility.trimNull(map
                                    .get(fieldsArr[k])))
                                    && !Utility.trimNull(map.get(fieldsArr[k]))
                                            .equals("0"))
                                content = new Label(k, (j + 2), Utility
                                        .trimNull(map.get(fieldsArr[k]))
                                        + str, formatCenter);
                            else
                                content = new Label(k, (j + 2), "",
                                        formatCenter);
                        }
                        // ��Ϣ��ѯ>>��Ʒ��ѯ
                        if (flag == 13 || flag == 15) {
                            String str = "������";
                            if (Utility.trimNull(map.get("PERIOD_UNIT"))
                                    .equals("1"))
                                str = "��";
                            if (Utility.trimNull(map.get("PERIOD_UNIT"))
                                    .equals("2"))
                                str = "��";
                            if (Utility.trimNull(map.get("PERIOD_UNIT"))
                                    .equals("3"))
                                str = "��";
                            if (!"".equals(Utility.trimNull(map
                                    .get(fieldsArr[k])))
                                    && !Utility.trimNull(map.get(fieldsArr[k]))
                                            .equals("0"))
                                content = new Label(k, (j + 2), Utility
                                        .trimNull(map.get(fieldsArr[k]))
                                        + str, formatCenter);
                            else
                                content = new Label(k, (j + 2), "",
                                        formatCenter);
                        }
                        break;
                    }
                    case 11: {
                        if (flag == 12) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(map.get(fieldsArr[k]))
                                    + Utility.trimNull(map
                                            .get("GOV_REGIONAL_NAME")),
                                    formatLeft);
                        }else{
                            content = new Label(k, (j + 2),Utility
                                    .trimNull(map.get(fieldsArr[k])),formatLeft);
                        }
                        break;
                    }
                    case 20: {
                        if (flag == 12) {
                            // �����ҵ�ͻ�����
                            content = new Label(
                                    k,
                                    (j + 2),
                                    Utility
                                            .trimNull(Argument
                                                    .getEntCustomerName(Utility
                                                            .parseInt(
                                                                    Utility
                                                                            .trimNull(map
                                                                                    .get(fieldsArr[k])),
                                                                    new Integer(
                                                                            0)))),
                                    formatLeft);
                        }
                        break;
                    }
                    default:
                        content = new Label(k, (j + 2), "", formatLeft);
                        break;
                    }
                    ws.addCell(content);
                }
            }
		} catch (Exception e) {
			throw new Exception("�ļ���ʧ��: " + e.getMessage());
		} finally {
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		}
	}

	/** ************************************************************************************************************* */

	public void exportPreMoney(Integer input_operatorCode) throws Exception {
		try {
			Integer q_pre_product_id = Utility.parseInt(Utility
					.trimNull(pageContext.getRequest().getParameter(
							"q_pre_product_id")), new Integer(0));
			String q_cust_name = Utility.trimNull(pageContext.getRequest()
					.getParameter("q_cust_name"));
			Integer start_date = Utility.parseInt(Utility.trimNull(pageContext
					.getRequest().getParameter("start_date")), new Integer(0));
			Integer end_date = Utility.parseInt(Utility.trimNull(pageContext
					.getRequest().getParameter("end_date")), new Integer(0));
			String pre_status = Utility.trimNull(pageContext.getRequest()
					.getParameter("pre_status"), "110202");
			String q_pre_level = Utility.trimNull(pageContext.getRequest()
					.getParameter("q_pre_level"));
			Integer team_id = Utility.parseInt(Utility.trimNull(pageContext
					.getRequest().getParameter("team_id")), new Integer(0));

			PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
			enfo.crm.vo.PreMoneyDetailVO preVo = new enfo.crm.vo.PreMoneyDetailVO();
			preVo.setPre_product_id(q_pre_product_id);
			preVo.setStart_date(start_date);
			preVo.setEnd_date(end_date);
			preVo.setPre_status(pre_status);
			preVo.setCust_name(q_cust_name);
			preVo.setInput_man(input_operatorCode);
			preVo.setPre_level(q_pre_level);
			preVo.setTeam_id(team_id);

			List list = local.queryPreMoneyDetail(preVo);

			String titleName[] = { "��Ʒ����", "�ͻ�����", "�ͻ�����","�����Ŷ�", "ԤԼ����","��������", "���˽��", "���˷�ʽ",
					"�˿�����", "�˿���" };

			OutputStream outStr = getResponseStream(java.net.URLEncoder.encode(
					"������ϸ.xls", "utf-8"));
			problemExcelByPreMoneyInfo(titleName, outStr, list);
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void problemExcelByPreMoneyInfo(String[] titleName,
			OutputStream outStr, List list) throws Exception {
		try {
			Utility.debugln("outputstream is " + outStr.toString());
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			//�����п�
			for (int y = 0; y < titleName.length; y++) {
				if(y == 0)
					ws.setColumnView(y, 30);
				else
					ws.setColumnView(y, 20);
			}
			// 1������ĸ�ʽ
			// �ƶ����ִ���ʽ
			WritableFont font = new WritableFont(WritableFont
					.createFont("���Ŀ���"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// ָ����Ԫ��ĸ�������
			WritableCellFormat format = new WritableCellFormat(font);
			// ָ��ˮƽ����ķ�ʽ����
			format.setAlignment(Alignment.CENTRE);
			// �ƶ���ֱ����ķ�ʽ����
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// �ϲ���Ԫ��
			ws.mergeCells(0, 0, titleName.length - 1, 0);
			// �����и�
			ws.setRowView(0, 500);
			// ���ñ߿�
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ��ӱ���
			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, "������ϸ", format);
			ws.addCell(labelC0);

			//2����ͷ�ĸ�ʽ
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatTop = new WritableCellFormat(fontTop);
			formatTop.setAlignment(Alignment.CENTRE);
			formatTop.setVerticalAlignment(VerticalAlignment.CENTRE);
			ws.setRowView(1, 400);
			formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);
			int i = 0;
			for (; i < titleName.length; i++) {
				String sName = titleName[i];
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,
						formatTop);
				ws.addCell(labelC1);
			}
			
			//3�����ݵĸ�ʽ
			// ����
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatRight = new WritableCellFormat(fontRight);
			formatRight.setAlignment(Alignment.RIGHT);
			formatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatRight.setBorder(Border.ALL, BorderLineStyle.THIN);
			Map map = new HashMap();
			BigDecimal dz_money = new BigDecimal(0);
			BigDecimal refund_money = new BigDecimal(0);
			for (int u = 0; u < list.size(); u++) {
				map = (Map) list.get(u);
				dz_money = dz_money.add(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1"));
				refund_money = refund_money.add(Utility.parseDecimal(Utility.trimNull(map.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1"));
				
				ws.setRowView((u+2), 350);
				
				jxl.write.Label labelValue0 = new jxl.write.Label(0, (u+2),
						Utility.trimNull(map.get("PREPRODUCT_NAME")), formatLeft);
				ws.addCell(labelValue0);
				
				jxl.write.Label labelValue1 = new jxl.write.Label(1, (u+2),
						Utility.trimNull(map.get("CUST_NAME")), formatLeft);
				ws.addCell(labelValue1);
				
				jxl.write.Label labelValue7 = new jxl.write.Label(2, (u+2),
						Utility.trimNull(Argument.getOpNameByOpCode(Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0)))), formatLeft);
				ws.addCell(labelValue7);
				jxl.write.Label labelValue9 = new jxl.write.Label(3, (u+2),Utility.trimNull(map.get("TEAM_NAME")), formatLeft);
				ws.addCell(labelValue9);
				
				jxl.write.Label labelValue8 = new jxl.write.Label(4, (u+2),Utility.trimNull(map.get("PRE_LEVEL_NAME")), formatLeft);
				ws.addCell(labelValue8);
				
				jxl.write.Label labelValue2 = new jxl.write.Label(5, (u+2),
						Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0)))), formatCenter);
				ws.addCell(labelValue2);
				
				jxl.write.Label labelValue3 = new jxl.write.Label(6, (u+2),
						Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1"))), formatRight);
				ws.addCell(labelValue3);
				
				jxl.write.Label labelValue4 = new jxl.write.Label(7, (u+2),
						Utility.trimNull(map.get("JK_TYPE_NAME")), formatLeft);
				ws.addCell(labelValue4);
				
				jxl.write.Label labelValue5 = new jxl.write.Label(8, (u+2),
						Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("REFUND_DATE")),new Integer(0)))), formatCenter);
				ws.addCell(labelValue5);
				
				jxl.write.Label labelValue6 = new jxl.write.Label(9, (u+2),
						Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1"))), formatRight);
				ws.addCell(labelValue6);
			}
			//�ϼ���
			ws.setRowView((list.size()+2), 350);
			jxl.write.Label labelValue0 = new jxl.write.Label(0, (list.size()+2), "�ϼ�"+list.size()+"��", formatTop);
			ws.addCell(labelValue0);
			
			jxl.write.Label labelValue1 = new jxl.write.Label(1, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue1);
			
			jxl.write.Label labelValue2 = new jxl.write.Label(2, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue2);
			
			jxl.write.Label labelValue7 = new jxl.write.Label(3, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue7);
			
			jxl.write.Label labelValue9 = new jxl.write.Label(4, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue9);
			
			jxl.write.Label labelValue8 = new jxl.write.Label(5, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue8);
			
			jxl.write.Label labelValue3 = new jxl.write.Label(6, (list.size()+2),
					Utility.trimNull(Format.formatMoney(dz_money)), formatRight);
			ws.addCell(labelValue3);
			
			jxl.write.Label labelValue4 = new jxl.write.Label(7, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue4);
			
			jxl.write.Label labelValue5 = new jxl.write.Label(8, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue5);
			
			jxl.write.Label labelValue6 = new jxl.write.Label(9, (list.size()+2),
					Utility.trimNull(Format.formatMoney(refund_money)), formatRight);
			ws.addCell(labelValue6);
			
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		}
	}
	
	
	public void exportSalesChangesResult(Integer input_operatorCode) throws Exception {
		try {
			Integer q_pre_product_id = Utility.parseInt(Utility
					.trimNull(pageContext.getRequest().getParameter(
							"q_pre_product_id")), new Integer(0));
			String q_cust_name = Utility.trimNull(pageContext.getRequest()
					.getParameter("q_cust_name"));
			Integer start_date = Utility.parseInt(Utility.trimNull(pageContext
					.getRequest().getParameter("start_date")), new Integer(0));
			Integer end_date = Utility.parseInt(Utility.trimNull(pageContext
					.getRequest().getParameter("end_date")), new Integer(0));
			String pre_status = Utility.trimNull(pageContext.getRequest()
					.getParameter("pre_status"), "110202");
			String q_pre_level = Utility.trimNull(pageContext.getRequest()
					.getParameter("q_pre_level"));
			Integer team_id = Utility.parseInt(Utility.trimNull(pageContext
					.getRequest().getParameter("team_id")), new Integer(0));
			Integer pre_product_type = Utility.parseInt(Utility.trimNull(pageContext
					.getRequest().getParameter("pre_product_type")), new Integer(0));
			SalesResultForStatisticLocal local = EJBFactory.getSalesResultForStatistic();
			enfo.crm.vo.SalesResultForStatisticVO preVo = new enfo.crm.vo.SalesResultForStatisticVO();
			preVo.setPre_product_id(q_pre_product_id);
			preVo.setStart_date(start_date);
			preVo.setEnd_date(end_date);
			preVo.setPre_status(pre_status);
			preVo.setCust_name(q_cust_name);
			preVo.setInput_man(input_operatorCode);
			preVo.setPre_level(q_pre_level);
			preVo.setTeam_id(team_id);
			preVo.setPre_product_type(pre_product_type);
			preVo.setStatus(1);//ֻ��ѯ����������

			List list = local.querySalesResultForStatisticMore(preVo);

			String titleName[] = { "��Ʒ����", "�ͻ�����", "�ͻ����", "�ͻ�����", "�ͻ�����","�����Ŷ�", "ԤԼ����","��������", "���˽��", "���˷�ʽ",
					"��Ʒ����", "��ע", "�˿�����", "�˿���" };

			OutputStream outStr = getResponseStream(java.net.URLEncoder.encode(
					"ת�������ͳ��.xls", "utf-8"));
			problemExcelBySalesChangesResultInfo(titleName, outStr, list);
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void problemExcelBySalesChangesResultInfo(String[] titleName,
			OutputStream outStr, List list) throws Exception {
		try {
			Utility.debugln("outputstream is " + outStr.toString());
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			//�����п�
			for (int y = 0; y < titleName.length; y++) {
				if(y == 0)
					ws.setColumnView(y, 30);
				else
					ws.setColumnView(y, 20);
			}
			// 1������ĸ�ʽ
			// �ƶ����ִ���ʽ
			WritableFont font = new WritableFont(WritableFont
					.createFont("���Ŀ���"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// ָ����Ԫ��ĸ�������
			WritableCellFormat format = new WritableCellFormat(font);
			// ָ��ˮƽ����ķ�ʽ����
			format.setAlignment(Alignment.CENTRE);
			// �ƶ���ֱ����ķ�ʽ����
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// �ϲ���Ԫ��
			ws.mergeCells(0, 0, titleName.length - 1, 0);
			// �����и�
			ws.setRowView(0, 500);
			// ���ñ߿�
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ��ӱ���
			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, "������ϸ", format);
			ws.addCell(labelC0);

			//2����ͷ�ĸ�ʽ
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatTop = new WritableCellFormat(fontTop);
			formatTop.setAlignment(Alignment.CENTRE);
			formatTop.setVerticalAlignment(VerticalAlignment.CENTRE);
			ws.setRowView(1, 400);
			formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);
			int i = 0;
			for (; i < titleName.length; i++) {
				String sName = titleName[i];
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,
						formatTop);
				ws.addCell(labelC1);
			}
			
			//3�����ݵĸ�ʽ
			// ����
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ����
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatRight = new WritableCellFormat(fontRight);
			formatRight.setAlignment(Alignment.RIGHT);
			formatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatRight.setBorder(Border.ALL, BorderLineStyle.THIN);
			Map map = new HashMap();
			BigDecimal dz_money = new BigDecimal(0);
			BigDecimal refund_money = new BigDecimal(0);
			for (int u = 0; u < list.size(); u++) {
				map = (Map) list.get(u);
				dz_money = dz_money.add(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1"));
				refund_money = refund_money.add(Utility.parseDecimal(Utility.trimNull(map.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1"));
				Integer record_count = Utility.parseInt(Utility.trimNull(map.get("RECORDS_COUNT")), 1);
				
				ws.setRowView((u+2), 350);
				Integer preProductType = Utility.parseInt(Utility.trimNull(map.get("PRE_PRODUCT_TYPE")), 1);
				
				String product_name = Utility.trimNull(map.get("PREPRODUCT_NAME"));
				String cust_name = Utility.trimNull(map.get("CUST_NAME"));
				String cust_type_name = Utility.trimNull(map.get("CUST_TYPE_NAME"));
				if(preProductType > 1){
					product_name = Utility.trimNull(map.get("RG_PRODUCT_NAME"));
					cust_name = Utility.trimNull(map.get("RG_CUST_NAME"));
					cust_type_name = Utility.trimNull(map.get("RG_CUST_TYPE_NAME"));
				}
				
				jxl.write.Label labelValue0 = new jxl.write.Label(0, (u+2),
						Utility.trimNull(product_name), formatLeft);
				ws.addCell(labelValue0);
				
				jxl.write.Label labelValue11 = new jxl.write.Label(1, (u+2),
						Utility.trimNull(cust_type_name), formatLeft);
				ws.addCell(labelValue11);
				
				jxl.write.Label labelValue12 = new jxl.write.Label(2, (u+2),
						Utility.trimNull(map.get("CUST_ID")), formatLeft);
				ws.addCell(labelValue12); 
				if(record_count == 1) {
					jxl.write.Label labelValue1 = new jxl.write.Label(3, (u+2),
							Utility.trimNull(cust_name), formatLeft);
					ws.addCell(labelValue1);
					
					jxl.write.Label labelValue7 = new jxl.write.Label(4, (u+2),
							Utility.trimNull(Argument.getOpNameByOpCode(Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0)))), formatLeft);
					ws.addCell(labelValue7);
					jxl.write.Label labelValue9 = new jxl.write.Label(5, (u+2),Utility.trimNull(map.get("TEAM_NAME")), formatLeft);
					ws.addCell(labelValue9);
				} else if(record_count == -1) {
					jxl.write.Label labelValue1 = new jxl.write.Label(3, (u+2),
							Utility.trimNull(cust_name), formatLeft);
					ws.addCell(labelValue1); 
					
					jxl.write.Label labelValue7 = new jxl.write.Label(4, (u+2),
							Utility.trimNull(map.get("RG_SERVICE_MAN_NAME")), formatLeft);
					ws.addCell(labelValue7);
					jxl.write.Label labelValue9 = new jxl.write.Label(5, (u+2),Utility.trimNull(map.get("TT_TEAM_NAME")), formatLeft);
					ws.addCell(labelValue9);
				} else {
					jxl.write.Label labelValue1 = new jxl.write.Label(3, (u+2),
							Utility.trimNull(""), formatLeft);
					ws.addCell(labelValue1); 
					
					jxl.write.Label labelValue7 = new jxl.write.Label(4, (u+2),
							Utility.trimNull(map.get("TO_SERVICE_MAN_NAME")), formatLeft);
					ws.addCell(labelValue7);
					jxl.write.Label labelValue9 = new jxl.write.Label(5, (u+2),Utility.trimNull(map.get("TT_TEAM_NAME")), formatLeft);
					ws.addCell(labelValue9);
				}

				
				jxl.write.Label labelValue8 = new jxl.write.Label(6, (u+2),Utility.trimNull(map.get("PRE_LEVEL_NAME")), formatLeft);
				ws.addCell(labelValue8);
				
				jxl.write.Label labelValue2 = new jxl.write.Label(7, (u+2),
						Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0)))), formatCenter);
				ws.addCell(labelValue2);
				
				jxl.write.Label labelValue3 = new jxl.write.Label(8, (u+2),
						Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1"))), formatRight);
				ws.addCell(labelValue3);
				
				jxl.write.Label labelValue4 = new jxl.write.Label(9, (u+2),
						Utility.trimNull(map.get("JK_TYPE_NAME")), formatLeft);
				ws.addCell(labelValue4);
				
				jxl.write.Label labelValue5 = new jxl.write.Label(10, (u+2),
						Utility.trimNull(map.get("PRE_PRODUCT_TYPE_NAME")), formatLeft);
				ws.addCell(labelValue5);
				
				jxl.write.Label labelValue6 = new jxl.write.Label(11, (u+2),
						Utility.trimNull(map.get("REMARK")), formatLeft);
				ws.addCell(labelValue6);
				
				jxl.write.Label labelValue13 = new jxl.write.Label(12, (u+2),
						Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("REFUND_DATE")),new Integer(0)))), formatCenter);
				ws.addCell(labelValue13);
				
				jxl.write.Label labelValue14 = new jxl.write.Label(13, (u+2),
						Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1"))), formatRight);
				ws.addCell(labelValue14);
			}
			//�ϼ���
			ws.setRowView((list.size()+2), 350);
			jxl.write.Label labelValue0 = new jxl.write.Label(0, (list.size()+2), "�ϼ�"+list.size()+"��", formatTop);
			ws.addCell(labelValue0);
			
			jxl.write.Label labelValue1 = new jxl.write.Label(1, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue1);
			
			jxl.write.Label labelValue11 = new jxl.write.Label(2, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue11);
			
			jxl.write.Label labelValue12 = new jxl.write.Label(3, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue12);
			
			jxl.write.Label labelValue2 = new jxl.write.Label(4, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue2);
			
			jxl.write.Label labelValue7 = new jxl.write.Label(5, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue7);
			
			jxl.write.Label labelValue9 = new jxl.write.Label(6, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue9);
			
			jxl.write.Label labelValue8 = new jxl.write.Label(7, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue8);
			
			jxl.write.Label labelValue3 = new jxl.write.Label(8, (list.size()+2),
					Utility.trimNull(Format.formatMoney(dz_money)), formatRight);
			ws.addCell(labelValue3);
			
			jxl.write.Label labelValue4 = new jxl.write.Label(9, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue4);
			
			jxl.write.Label labelValue5 = new jxl.write.Label(10, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue5);
			
			jxl.write.Label labelValue6 = new jxl.write.Label(11, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue6);
			
			jxl.write.Label labelValue13 = new jxl.write.Label(12, (list.size()+2),
					Utility.trimNull(""), formatCenter);
			ws.addCell(labelValue13);
			
			jxl.write.Label labelValue14 = new jxl.write.Label(13, (list.size()+2),
					Utility.trimNull(Format.formatMoney(refund_money)), formatRight);
			ws.addCell(labelValue14);
			
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		}
	}
    
    public boolean readExcel(
            String objpath,
            int isystemFalg,
            String tableName)
            throws Exception {
        String filename = objpath;
        //String sfile_name = uploadFile.getFileName();
        String sfile_name = smartUpload.getRequest().getParameter("file_name");
        sfile_name = sfile_name.substring(sfile_name.lastIndexOf("\\") + 1);
        filename = objpath + "\\" + sfile_name;
        File f = new File(filename);
        
        int inputflag = Utility.parseInt(smartUpload.getRequest().getParameter("inputflag"),0);
        if (inputflag != 2)
            return false;
        Connection conn  = null;
        Statement stmt = null;
        jxl.Workbook book = null;
        try{
            conn = IntrustDBManager.getConnection();
            stmt = conn.createStatement();
            InputStream is = new FileInputStream(filename);
            book = Workbook.getWorkbook(is);
            if (book == null)
                throw new Exception("�ļ�������!");
            //��õ�һ�����������
            Sheet[] shets = book.getSheets();

            int iSheet = book.getNumberOfSheets();

            for (int i = 0; i < iSheet; i++) {
                Sheet sheet = book.getSheet(i);
                //WritableSheet ws2 =wwb.getSheet(0);
                int iRows = sheet.getRows();
                int iColums = sheet.getColumns();
                //Excelҳ���ĵ�һ���ַ���ŵ����������ֶ�serial_no��־,1Ϊ��������ֶ�,0Ϊû��.
                String table = sheet.getName();

                int flag = Utility.parseInt(sheet.getName().substring(0, 1), 0);
                //ȡ���Ƿ����������ֶ�serial_no��־
                if (flag == 1)
                    table = table.substring(1, table.length());
                String[] fields = new String[iColums];
                String[] sfieldtypes = new String[iColums];
                for (int r = 0; r < iColums; r++) {
                    Cell cel = sheet.getCell(r, 0);
                    fields[r] = cel.getContents();
                    //Utility.debugln("��"+r+"������"+fields[r]);
                }
                for (int j = 1; j < iRows; j++) {
                    String[] values = new String[iColums];

                    for (int k = 0; k < iColums; k++) {
                        Cell cel = sheet.getCell(k, j);
                        if(cel.getContents() != null && !"".equals(cel.getContents().trim())){ //20110117 dingyj ����жϿ��в�����
                            values[k] = "N'" + cel.getContents() + "'";
                        }   
                        //Utility.debugln("��"+k+"��ֵ��"+values[k]);
                    }
                    //Utility.debugln("------------------------------------------");
                    if (!Utility.trimNull(values[0]).equals("''")) {
                        insertRecord(fields,values,tableName,flag,isystemFalg,stmt);
                    } else
                        continue;
                }
            }
            if("OLDREDEEM".equals(tableName)){//����Ԥ����������֤
    
                CallableStatement ctmt = conn.prepareCall("{?=call SP_IMPORT_TSQREDEEM_PRETREAT}");
                ctmt.registerOutParameter(1, Types.INTEGER);
                ctmt.executeUpdate();

                int iret = ctmt.getInt(1);
                
                if(ctmt != null)
                    ctmt.close();

                if (iret < 0)
                    throw new Exception("����Ԥ����������֤ʧ��,������Ϣ:" + iret );
            }
            
        } catch (Exception e) {
            throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
        }finally{
            if(book!=null)
                book.close();
            if(stmt!=null)
                stmt.close();
            if(conn!=null)
                conn.close();
        }
        return true;
    }
    
    /**
     * ���ݴ����������������ݷֱ�Ӳ�ͬ���뵽��Ӧ�ı���
     * @param input_man
     * @param book_code
     * @param dealFlag �����־��1��ʾ�������ۣ�2����������۸���
     * @return
     * @throws Exception
     */
    public boolean insertContractRecord(
        Integer input_man,
        Integer book_code,
        int dealFlag)
        throws Exception {
        int inputflag = Utility.parseInt(smartUpload.getRequest().getParameter("inputflag"),0);

        if (inputflag != 1)
            return false;
        Connection conn = null;
        CallableStatement stmt = null ;
        try {
            String insertSql = "{?=call SP_IMPORT_ZY_HISTORY(?,?,?)}";
            if(dealFlag==2){ // 2����������۸���
                insertSql = "{?=call SP_IMPORT_ZY_HISTORY2(?,?,?)}";
            }
            
            conn = IntrustDBManager.getConnection();
            stmt = conn.prepareCall(insertSql);
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.registerOutParameter(2, Types.VARCHAR);
            if (input_man != null)
                stmt.setInt(3, input_man.intValue());
            else
                stmt.setInt(3, 0);
            if(book_code!=null)
                stmt.setInt(4, book_code.intValue());
            else
                stmt.setInt(4, 0);
            stmt.executeUpdate();

            int iret = stmt.getInt(1);
            String strmesg = stmt.getString(2);

            if (iret < 0)
                throw new Exception(strmesg);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            throw new Exception("������ʧ��,������Ϣ:" + e.getMessage());
        }finally{
            if(stmt!=null)
                stmt.close();
            if(conn!=null)
                conn.close();
        }
        
        return true;
    }
    
    // dealFlag �����־��1��ʾ�������ۣ�2����������۸���
    public boolean delOldData(int dealFlag) throws Exception {
        int inputflag = Utility.parseInt(smartUpload.getRequest().getParameter("inputflag"),0);

        if (inputflag != 3)
            return false;
        Connection conn = IntrustDBManager.getConnection();
        try {
            String dealSQL = "DELETE FROM OLD";
            if(dealFlag==2){ // 2����������۸���
                dealSQL = "DELETE FROM OLD_CHANGE";
            }
            if(dealFlag==3){
                dealSQL = "DELETE FROM OLDREDEEM" ;
            }
            PreparedStatement stmt = conn.prepareStatement(dealSQL);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusiException("ɾ����ʷ����Ϣʧ��: " + e.getMessage());
        } finally {
            conn.close();
        }
    }
    
    public boolean correctOldData(
            Integer input_man,
            Integer book_code,
            int dealFlag)
            throws Exception {
        int inputflag = Utility.parseInt(smartUpload.getRequest().getParameter("inputflag"),0);

        if (inputflag != 4)
            return false;
        Connection conn = IntrustDBManager.getConnection();
        try {
            String insertSql = "{?=call SP_COMPARE_IMPORT_DATA(?,?,?,?)}";
            
            if(dealFlag==2){
                insertSql = "{?=call SP_COMPARE_IMPORT_DATA(?,?,?,?)}";
            }
            CallableStatement stmt = conn.prepareCall(insertSql);
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.registerOutParameter(2, Types.VARCHAR);
            
            stmt.setString(3,"");
            if (input_man != null)
                stmt.setInt(4, input_man.intValue());
            else
                stmt.setInt(4, 0);
            if(book_code!=null)
                stmt.setInt(5, book_code.intValue());
            else
                stmt.setInt(5, 0);          
            stmt.executeUpdate();
            int iret = stmt.getInt(1);
            String strmesg = stmt.getString(1);
            if (iret < 0)
                throw new Exception(strmesg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusiException("��������������Ϣʧ��: " + e.getMessage());
        } finally {
            conn.close();
        }
    }
    
    public void downloadExcel() throws Exception {
        String filename = "C:\\JTWT.xls";

        Integer product_id =
            Utility.parseInt(
                pageContext.getRequest().getParameter("product_id"),
                new Integer(0));

        String[] serial_no =
            pageContext.getRequest().getParameterValues("serial_no");

        String bank_id = pageContext.getRequest().getParameter("bank_id");
        String sy_type = pageContext.getRequest().getParameter("sy_type");
        String jk_type = pageContext.getRequest().getParameter("jk_type");
        Integer sy_date =
            Utility.parseInt(
                pageContext.getRequest().getParameter("sy_date"),
                new Integer(0));
        String prov_level = pageContext.getRequest().getParameter("prov_level");

        int outporttype =
            Utility.parseInt(
                pageContext.getRequest().getParameter("outporttype"),
                1);

        Integer book_code =
            Utility.parseInt(
                pageContext.getRequest().getParameter("book_code"),
                new Integer(0));

        Integer sub_product_id =
            Utility.parseInt(
                pageContext.getRequest().getParameter("sub_product_id"),
                new Integer(0));

        Integer input_man =
            Utility.parseInt(
                pageContext.getRequest().getParameter("input_man"),
                new Integer(0));
        
        Integer link_man = Utility.parseInt(pageContext.getRequest().getParameter("link_man"),new Integer(0));
        try {
            java.io.File file = null;
            if (outporttype == 1) {
                file =
                    DeployOutListExcel(
                        filename,
                        book_code,
                        bank_id,
                        product_id,
                        sy_type,
                        jk_type,
                        sy_date,
                        prov_level,
                        sub_product_id,
                        link_man,
                        input_man);

            }/* else if (outporttype == 2) {
                String sy_type1 =
                    pageContext.getRequest().getParameter("sytype1");
                String sy_type2 =
                    pageContext.getRequest().getParameter("sytype2");
                String sy_type3 =
                    pageContext.getRequest().getParameter("sytype3");
                String sy_type4 =
                    pageContext.getRequest().getParameter("sytype4");
                String sy_type5 =
                    pageContext.getRequest().getParameter("sytype5");
                String sy_type_all =
                    sy_type1
                        + " $"
                        + sy_type2
                        + " $"
                        + sy_type3
                        + " $"
                        + sy_type4
                        + " $"
                        + sy_type5
                        + " $";
                Utility.debugln(sy_type_all);
                file =
                    DeployOutputAllExcel(
                        filename,
                        book_code,
                        bank_id,
                        product_id,
                        sy_type_all);
            } else if (outporttype == 3) {
                Utility.debugln("����3");
                file =
                    ProductBondCountExcel(
                        filename,
                        product_id,
                        "",
                        new Integer(0),
                        prov_level);

            }*/ else if (outporttype == 4) {
                Utility.debugln("����4");
                String[] paramvalues =
                    pageContext.getRequest().getParameterValues("serial_no");
                file = DeployOutputTotalExcel(filename, book_code, paramvalues);
            }
            System.out.println(file.toString());
            downloadJsp(file);
        } catch (Exception e) {
            throw new Exception("����Excel�ļ�ʧ�ܣ�" + e.getMessage());
        }
    }
    
    public java.io.File DeployOutputTotalExcel(
            String objpath,
            Integer book_code,
            String[] paramvalues)
            throws Exception {
        String filename = objpath;

        java.io.File vfile = new java.io.File(filename);
        try {
            DeployLocal deploy = EJBFactory.getDeploy();

            LinkedList objlist = new LinkedList();

            String titleName[] =
                {
                    "��ͬ���",
                    "����������",
                    "���֤��",
                    "����",
                    "��������",
                    "������",
                    "��˰",
                    "�����ʺ�",
                    "��������",
                    "�������ʱ��" };
            String fieldName[] =
                {
                    "CONTRACT_SUB_BH",
                    "CUST_NAME",
                    "CARD_ID",
                    "SY_AMOUNT",
                    "SY_TYPE_NAME",
                    "SY_MONEY",
                    "BOND_TAX",
                    "BANK_ACCT",
                    "BANK_NAME",
                    "SY_DATE" };
            String[] fieldType =
                { "1", "1", "1", "2", "1", "2", "2", "1", "1", "3" };
            
            DeployVO vo = new DeployVO();
            for (int i = 0; i < paramvalues.length; i++) {
                String paramvalue = paramvalues[i];
                String[] arraparam = Utility.splitString(paramvalue, "$");                
                vo.setBook_code(book_code);
                vo.setProv_level(arraparam[4].trim());
                vo.setProduct_id(Utility.parseInt(arraparam[1], new Integer(0)/*null*/));
                vo.setSy_type(arraparam[2].trim());
                vo.setSy_date(Utility.parseInt(arraparam[3], new Integer(0)/*null*/));
                vo.setSub_product_id(Utility.parseInt(arraparam[5], new Integer(0)/*null*/));
                sun.jdbc.rowset.CachedRowSet rslist =
                    deploy.getOutputListResult(vo);

                while (rslist.next()) {
                    LinkedList fieldvaluelist = new LinkedList();

                    String contracts = rslist.getString("CONTRACT_SUB_BH");
                    fieldvaluelist.add(contracts);

                    fieldvaluelist.add(rslist.getString("CUST_NAME"));
                    fieldvaluelist.add(rslist.getString("CARD_ID"));
                    fieldvaluelist.add(rslist.getBigDecimal("SY_AMOUNT"));
                    fieldvaluelist.add(rslist.getString("SY_TYPE_NAME"));

                    fieldvaluelist.add(rslist.getBigDecimal("SY_MONEY"));
                    fieldvaluelist.add(rslist.getBigDecimal("BOND_TAX"));

                    fieldvaluelist.add(rslist.getString("BANK_ACCT"));
                    fieldvaluelist.add(rslist.getString("BANK_NAME"));
                    fieldvaluelist.add(new Integer(rslist.getInt("SY_DATE")));

                    objlist.add(fieldvaluelist);

                }
            }

            //1Ϊ�ַ�����,2ΪBigDecimal
            vfile =
                productTotoalExcel(
                    objpath,
                    Argument.getProductName(vo.getProduct_id()),
                    titleName,
                    fieldType,
                    objlist);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            return vfile;
        }
    }
    
    public java.io.File productTotoalExcel(
            String objpath,
            String excelTitle,
            String[] titleName,
            String[] fieldType,
            LinkedList rslist)
            throws Exception {
        String filename = objpath;
        java.io.File f = null;

        try {
            f = new java.io.File(filename);
            WorkbookSettings wbs=new WorkbookSettings();
            wbs.setGCDisabled(true);
            WritableWorkbook wwb = Workbook.createWorkbook(f,wbs);
            WritableSheet ws = wwb.createSheet("��һҳ", 0);

            jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle);
            ws.addCell(labelC0);
            int i = 0;

            for (; i < titleName.length; i++) {
                String sName = titleName[i];
                jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName);
                ws.addCell(labelC1);
            }

            int j = 2;
            LinkedList fieldvaluelist = new LinkedList();
            for (int r = 0; r < rslist.size(); r++) {
                fieldvaluelist = (LinkedList) rslist.get(r);
                int k = 0;

                for (; k < fieldType.length; k++) {
                    int iType = Utility.parseInt(fieldType[k], 0);
                    switch (iType) {
                        case 1 :
                            {
                                String fieldValue =
                                    (String) fieldvaluelist.get(k);

                                jxl.write.Label labelValue0 =
                                    new jxl.write.Label(
                                        k,
                                        j,
                                        Utility.trimNull(fieldValue));
                                ws.addCell(labelValue0);
                                break;
                            }
                        case 2 : //BigDecimal��ֵ���͵�
                            {
                                BigDecimal fiedvalue =
                                    (BigDecimal) fieldvaluelist.get(k);

                                double tempmoney =
                                    (fiedvalue == null
                                        ? 0
                                        : fiedvalue.doubleValue());
                                jxl.write.NumberFormat nf =
                                    new jxl.write.NumberFormat("#.##");
                                jxl.write.WritableCellFormat wcfN =
                                    new jxl.write.WritableCellFormat(nf);
                                jxl.write.Number labe4NF =
                                    new jxl.write.Number(k, j, tempmoney, wcfN);

                                ws.addCell(labe4NF);
                                break;
                            }
                        case 3 :
                            {
                                Integer fieldValue =
                                    (Integer) fieldvaluelist.get(k);
                                String tmepvalue = "";
                                if (fieldValue != null)
                                    tmepvalue = fieldValue.toString();

                                jxl.write.Label labelValue0 =
                                    new jxl.write.Label(
                                        k,
                                        j,
                                        Utility.trimNull(tmepvalue));

                                ws.addCell(labelValue0);
                                break;
                            }
                        case 4 : //BigDecimal��ֵ���͵�
                            {
                                BigDecimal fiedvalue =
                                    (BigDecimal) fieldvaluelist.get(k);

                                double tempmoney =
                                    (fiedvalue == null
                                        ? 0
                                        : fiedvalue.doubleValue());
                                jxl.write.NumberFormat nf =
                                    new NumberFormat("#.#####");

                                jxl.write.WritableCellFormat wcfN =
                                    new jxl.write.WritableCellFormat(nf);
                                jxl.write.Number labe4NF =
                                    new jxl.write.Number(k, j, tempmoney, wcfN);

                                ws.addCell(labe4NF);
                                break;
                            }
                        default :
                            break;
                    }
                }
                j++;
            }
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
        } finally {
            return f;
        }
    }
    
    public java.io.File DeployOutListExcel(
            String objpath,
            Integer book_code,
            String bank_id,
            Integer product_id,
            String sy_type,
            String jk_type,
            Integer sy_date,
            String prov_flag,
            Integer sub_product_id,
            Integer link_man,
            Integer input_man)
            throws Exception {
        String filename = objpath;

        java.io.File vfile = new java.io.File(filename);

        try {
            DeployLocal deploy = EJBFactory.getDeploy();
            DeployVO vo = new DeployVO();
            vo.setBook_code(book_code);
            vo.setBank_id(bank_id);
            vo.setProduct_id(product_id);
            vo.setSy_type(sy_type);
            vo.setJk_type(jk_type);
            vo.setSy_date(sy_date);
            vo.setProv_level(prov_flag);
            vo.setSub_product_id(sub_product_id);
            vo.setLink_man(link_man);
            vo.setInput_man(input_man);
            sun.jdbc.rowset.CachedRowSet rslist = deploy.getOutputListResult(vo);

            String titleName[] =
                {
                    "��ͬ���",
                    "����������",
                    "���֤��",
                    "����",
                    "��������",
                    "������",
                    "��˰",
                    "�����ʺ�",
                    "��������",
                    "���л���",
                    "�������ʱ��",
                    "��ע"};
            String fieldName[] =
                {
                    "CONTRACT_SUB_BH",
                    "CUST_NAME",
                    "CARD_ID",
                    "SY_AMOUNT",
                    "SY_TYPE_NAME",
                    "SY_MONEY",
                    "BOND_TAX",
                    "BANK_ACCT",
                    "BANK_NAME",
                    "CUST_ACCT_NAME",
                    "SY_DATE",
                    "SUMMARY"};

            String[] fieldType =
                { "1", "1", "1", "2", "1", "2", "2", "1", "1", "1", "1","1" };
            //1Ϊ�ַ�����,2ΪBigDecimal
            vfile =
                productExcel(
                    objpath,
                    Argument.getProductName(product_id),
                    titleName,
                    fieldName,
                    fieldType,
                    rslist);
        }catch(Exception e){
            e.printStackTrace();
        } 
        return vfile;
        
    }
    
    public java.io.File productExcel(
            String objpath,
            String excelTitle,
            String[] titleName,
            String[] fieldName,
            String[] fieldType,
            sun.jdbc.rowset.CachedRowSet rslist)
            throws Exception {
            String filename = objpath;
            java.io.File f = null;

            try {
                f = new java.io.File(filename);
                WorkbookSettings wbs=new WorkbookSettings();
                wbs.setGCDisabled(true);
                WritableWorkbook wwb = Workbook.createWorkbook(f,wbs);
                WritableSheet ws = wwb.createSheet("��һҳ", 0);
                //�����п�
                for (int y = 0; y < titleName.length; y++) {
                    ws.setColumnView(y, 25);
                }
//               1������ĸ�ʽ
                // �ƶ����ִ���ʽ
                WritableFont font = new WritableFont(WritableFont
                        .createFont("���Ŀ���"), 20, WritableFont.BOLD, false,
                        UnderlineStyle.NO_UNDERLINE);
                // ָ����Ԫ��ĸ�������
                WritableCellFormat format = new WritableCellFormat(font);
                // ָ��ˮƽ����ķ�ʽ����
                format.setAlignment(Alignment.CENTRE);
                // �ƶ���ֱ����ķ�ʽ����
                format.setVerticalAlignment(VerticalAlignment.CENTRE);
                // �ϲ���Ԫ��
                ws.mergeCells(0, 0, (titleName.length) - 1, 0);
                // �����и�
                ws.setRowView(0, 500);
                // ���ñ߿�
                format.setBorder(Border.ALL, BorderLineStyle.THIN);
                // ��ӱ���
                jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,format);
                ws.addCell(labelC0);
                int i = 0;
//               2����ͷ�ĸ�ʽ
                WritableFont fontTop = new WritableFont(WritableFont
                        .createFont("����"), 12, WritableFont.BOLD, false,
                        UnderlineStyle.NO_UNDERLINE);
                WritableCellFormat formatTop = new WritableCellFormat(fontTop);
                formatTop.setAlignment(Alignment.CENTRE);
                formatTop.setVerticalAlignment(VerticalAlignment.CENTRE);
                ws.setRowView(1, 400);
                formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);
                for (; i < titleName.length; i++) {
                    String sName = titleName[i];
                    jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,formatTop);
                    ws.addCell(labelC1);
                }
//               3�����ݵĸ�ʽ
                // ����
                WritableFont fontLeft = new WritableFont(WritableFont
                        .createFont("����"), 12, WritableFont.NO_BOLD, false,
                        UnderlineStyle.NO_UNDERLINE);
                WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
                formatLeft.setAlignment(Alignment.LEFT);
                formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
                formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
                // ����
                WritableFont fontCenter = new WritableFont(WritableFont
                        .createFont("����"), 12, WritableFont.NO_BOLD, false,
                        UnderlineStyle.NO_UNDERLINE);
                WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
                formatCenter.setAlignment(Alignment.CENTRE);
                formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
                formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
                // ����
                WritableFont fontRight = new WritableFont(WritableFont
                        .createFont("����"), 12, WritableFont.NO_BOLD, false,
                        UnderlineStyle.NO_UNDERLINE);
                WritableCellFormat formatRight = new WritableCellFormat(fontRight);
                formatRight.setAlignment(Alignment.RIGHT);
                formatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
                formatRight.setBorder(Border.ALL, BorderLineStyle.THIN);
                int j = 2;
                while (rslist.next()) {
                    int k = 0;

                    for (; k < fieldType.length; k++) {
                        String sfieldName = fieldName[k];
                        int iType = Utility.parseInt(fieldType[k], 0);
                        switch (iType) {
                            case 1 :
                                {
                                    String fieldValue =
                                        rslist.getString(sfieldName);

                                    jxl.write.Label labelValue0 =
                                        new jxl.write.Label(
                                            k,
                                            j,
                                            Utility.trimNull(fieldValue),formatLeft);
                                    ws.addCell(labelValue0);
                                    break;
                                }
                            case 2 : //BigDecimal��ֵ���͵�
                                {
                                    BigDecimal fiedvalue =
                                        rslist.getBigDecimal(sfieldName);

                                    double tempmoney =
                                        (fiedvalue == null
                                            ? 0
                                            : fiedvalue.doubleValue());
                                    jxl.write.NumberFormat nf =
                                        new jxl.write.NumberFormat("#.##");
                                    jxl.write.WritableCellFormat wcfN =
                                        new jxl.write.WritableCellFormat(nf);
                                    jxl.write.Number labe4NF =
                                        new jxl.write.Number(k, j, tempmoney, wcfN);

                                    ws.addCell(labe4NF);
                                    break;
                                }
                            case 3 :
                                {
                                    Integer fieldValue =
                                        new Integer(rslist.getInt(sfieldName));
                                    String tmepvalue = "";
                                    if (fieldValue != null)
                                        tmepvalue = fieldValue.toString();

                                    jxl.write.Label labelValue0 =
                                        new jxl.write.Label(
                                            k,
                                            j,
                                            Utility.trimNull(tmepvalue));

                                    ws.addCell(labelValue0);
                                    break;
                                }
                            case 4 : //BigDecimal��ֵ���͵�
                                {
                                    BigDecimal fiedvalue =
                                        rslist.getBigDecimal(sfieldName);

                                    double tempmoney =
                                        (fiedvalue == null
                                            ? 0
                                            : fiedvalue.doubleValue());
                                    jxl.write.NumberFormat nf =
                                        new NumberFormat("#.#####");

                                    jxl.write.WritableCellFormat wcfN =
                                        new jxl.write.WritableCellFormat(nf);
                                    jxl.write.Number labe4NF =
                                        new jxl.write.Number(k, j, tempmoney, wcfN);

                                    ws.addCell(labe4NF);
                                    break;
                                }
                            default :
                                break;
                        }
                    }
                    j++;
                }
                wwb.write();
                wwb.close();
            } catch (Exception e) {
                throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
            } finally {
                return f;
            }
        }


    
    public void downloadProblemFile2(String strFile, String name)
		throws Exception {
		strFile = Utility.replaceAll(strFile, "\\", "/");
		java.io.File file = new java.io.File(strFile);
		String filen = "";
		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) (pageContext
				.getResponse());
		if (!file.exists())
			throw new BusiException("�ļ������ڣ��п��ܸ��ļ��ѱ�ɾ����");
		DataInputStream dis = null;
		OutputStream os = null;
		try {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-disposition", //inline
		
					Encode("attachment;filename=" + name));
			dis = new DataInputStream(new FileInputStream(file));
			os = response.getOutputStream();
			byte[] buf = new byte[1024];
			int left = (int) file.length();
			int read = 0;
			while (left > 0) {
				read = dis.read(buf);
				left -= read;
				os.write(buf, 0, read);
			}
		
		} catch (Exception e) {
			throw new BusiException("���ļ�ʧ�ܣ���ȷ���Ƿ���Ȩ���Ķ�!");
		} finally {
			os.close();
			dis.close();
			out.close();
		}
	}
    //����������ϸ��Ϣ
	public void exportService(Integer input_operatorCode) throws Exception {
		try {
			String serial_no = Utility.trimNull(pageContext.getRequest().getParameter("serial_no"));
			Integer edit_flag = Utility.parseInt(pageContext.getRequest().getParameter("edit_flag"), new Integer(0));
			Integer q_serviceType = Utility.parseInt(pageContext.getRequest().getParameter("q_serviceType"),new Integer(0));
			String q_serviceWay = Utility.trimNull(pageContext.getRequest().getParameter("q_serviceWay"));

			//��ö���
			ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
			ServiceTaskVO vo = new ServiceTaskVO();
			
			List list = new ArrayList();
			if (serial_no.equals("")) {
				//�����ϸ�б�
				vo.setServiceType(q_serviceType);
				vo.setServiceWay(q_serviceWay);
				vo.setTaskSerialNO(null);
				vo.setNeedFeedBack(new Integer(1));
				vo.setStatus(new Integer(3));
				vo.setInputMan(input_operatorCode);
	
				list = serviceTask.query_details(vo);
			} else {
				String[] arr = serial_no.split(",");
				
				if (arr!=null)
					for (int i=0; i<arr.length; i++) {
						vo.setSerial_no(Utility.parseInt(arr[i], new Integer(0)));
						vo.setInputMan(input_operatorCode);
						List l = serviceTask.query_details(vo);
						
						if (l.size()>0)
							list.add(l.get(0));
					}
			}
			
			String titleName[] = { "�ͻ����", "�ͻ�����", "�ͻ�����","��������","��ϵ�绰", "�ʼĵ�ַ1","�ʱ�1", "�ʼĵ�ַ2", "�ʱ�2","����ʱ��","����������" };

			OutputStream outStr = getResponseStream(java.net.URLEncoder.encode(
					"������ϸ����.xls", "utf-8"));
			exportExcelService(titleName, outStr, list);
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportExcelService(String[] titleName,
			OutputStream outStr, List list) throws Exception {
		try {
			Utility.debugln("outputstream is " + outStr.toString());
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			//�����п�
			for (int y = 0; y < titleName.length; y++) {
				if(y == 0)
					ws.setColumnView(y, 30);
				else
					ws.setColumnView(y, 20);
			}
			// 1������ĸ�ʽ
			// �ƶ����ִ���ʽ
			WritableFont font = new WritableFont(WritableFont
					.createFont("���Ŀ���"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// ָ����Ԫ��ĸ�������
			WritableCellFormat format = new WritableCellFormat(font);
			// ָ��ˮƽ����ķ�ʽ����
			format.setAlignment(Alignment.CENTRE);
			// �ƶ���ֱ����ķ�ʽ����
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// �ϲ���Ԫ��
			ws.mergeCells(0, 0, titleName.length - 1, 0);
			// �����и�
			ws.setRowView(0, 500);
			// ���ñ߿�
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			// ��ӱ���
			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, "������ϸ", format);
			ws.addCell(labelC0);

			//2����ͷ�ĸ�ʽ
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatTop = new WritableCellFormat(fontTop);
			formatTop.setAlignment(Alignment.CENTRE);
			formatTop.setVerticalAlignment(VerticalAlignment.CENTRE);
			ws.setRowView(1, 400);
			formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);
			int i = 0;
			for (; i < titleName.length; i++) {
				String sName = titleName[i];
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,
						formatTop);
				ws.addCell(labelC1);
			}
			
			//3�����ݵĸ�ʽ
			// ����
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("����"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
		
			Map map = new HashMap();
			BigDecimal dz_money = new BigDecimal(0);
			BigDecimal refund_money = new BigDecimal(0);
			for (int u = 0; u < list.size(); u++) {
				map = (Map) list.get(u);

				ws.setRowView((u+2), 350);
				
				jxl.write.Label labelValue0 = new jxl.write.Label(0, (u+2),
						Utility.trimNull(map.get("CUST_NO")), formatLeft);
				ws.addCell(labelValue0);
				
				jxl.write.Label labelValue1 = new jxl.write.Label(1, (u+2),
						Utility.trimNull(map.get("CUST_NAME")), formatLeft);
				ws.addCell(labelValue1);
				
				jxl.write.Label labelValue2 = new jxl.write.Label(2, (u+2),
						Utility.trimNull(Argument.getOpNameByOpCode(Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0)))), formatLeft);
				ws.addCell(labelValue2);
				
				jxl.write.Label labelValue3 = new jxl.write.Label(3, (u+2),
						Utility.trimNull(map.get("ServiceTitle")), formatLeft);
				ws.addCell(labelValue3);

				String h_tel = Utility.trimNull(map.get("H_TEL"));
				String o_tel = Utility.trimNull(map.get("O_TEL"));
				String mobile = Utility.trimNull(map.get("MOBILE"));
				String bp = Utility.trimNull(map.get("BP"));

				jxl.write.Label labelValue4 = new jxl.write.Label(4, (u+2),Utility.trimNull(Argument.getALLTEL(h_tel,o_tel,mobile,bp)), formatLeft);
				ws.addCell(labelValue4);
				
				jxl.write.Label labelValue5 = new jxl.write.Label(5, (u+2),Utility.trimNull(map.get("POST_ADDRESS")), formatLeft);
				ws.addCell(labelValue5);
				
				jxl.write.Label labelValue6 = new jxl.write.Label(6, (u+2),Utility.trimNull(map.get("POST_CODE")), formatLeft);
				ws.addCell(labelValue6);
				
				jxl.write.Label labelValue7 = new jxl.write.Label(7, (u+2),Utility.trimNull(map.get("POST_ADDRESS2")), formatLeft);
				ws.addCell(labelValue7);
				
				jxl.write.Label labelValue8 = new jxl.write.Label(8, (u+2),Utility.trimNull(map.get("POST_CODE2")), formatLeft);
				ws.addCell(labelValue8);
				
				jxl.write.Label labelValue9 = new jxl.write.Label(9, (u+2),Utility.trimNull(map.get("ExecuteTime")), formatLeft);
				ws.addCell(labelValue9);
				
				jxl.write.Label labelValue10 = new jxl.write.Label(10, (u+2),Utility.trimNull(map.get("Result")), formatLeft);
				ws.addCell(labelValue10);
			}
			//�ϼ���
			ws.setRowView((list.size()+2), 350);
			jxl.write.Label labelValue0 = new jxl.write.Label(0, (list.size()+2), "�ϼ�"+list.size()+"��", formatTop);
			ws.addCell(labelValue0);
			
			jxl.write.Label labelValue1 = new jxl.write.Label(1, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue1);
			
			jxl.write.Label labelValue2 = new jxl.write.Label(2, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue2);
			
			jxl.write.Label labelValue7 = new jxl.write.Label(3, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue7);
			
			jxl.write.Label labelValue9 = new jxl.write.Label(4, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue9);
			
			jxl.write.Label labelValue8 = new jxl.write.Label(5, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue8);
			
			jxl.write.Label labelValue3 = new jxl.write.Label(6, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue3);
			
			jxl.write.Label labelValue4 = new jxl.write.Label(7, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue4);
			
			jxl.write.Label labelValue5 = new jxl.write.Label(8, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue5);
			
			jxl.write.Label labelValue6 = new jxl.write.Label(9, (list.size()+2),
					Utility.trimNull(""), formatLeft);
			ws.addCell(labelValue6);
			
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		}
	}
	
}