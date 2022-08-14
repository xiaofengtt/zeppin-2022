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
			throw new BusiException("文件上传失败!" + e.getMessage());
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
			throw new BusiException("文件大小不能超过10M！");
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
			throw new BusiException("文件上传失败: " + e.getMessage());
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
			throw new BusiException("文件大小不能超过10M！");

		try {
			smartUpload.save(toFolder,
					com.jspsmart.upload.SmartUpload.SAVE_PHYSICAL);
			Utility.debugln("success upload:"
					+ smartUpload.getFiles().getFile(0).getFileName() + " to "
					+ toFolder);
			return true;
		} catch (Exception e) {
			throw new BusiException("文件上传失败: " + e.getMessage());
		}
	}

	// 保存文档附件到数据库
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
			throw new BusiException("文件保存失败: " + e.getMessage());
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
			throw new BusiException("文件不存在！有可能该文件已被删除。");
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
			throw new Exception("传入的文件" + file.getName() + "不存在！");
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
			throw new BusiException("文件保存失败: " + e.getMessage());
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
			throw new BusiException("文件不存在！有可能该文件已被删除。");
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
			throw new BusiException("打开文件失败，请确认是否有权限阅读!");
		} finally {
			os.close();
			dis.close();
			out.close();
		}
	}

	/**
	 * ADD BY TSG 2009-03-13 获取服务对应的文件格式
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
			throw new BusiException("读取文件格式信息错误:" + e.getMessage());
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
	 * ADD BY TSG 2009-03-11 获取用户ID
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
			throw new BusiException("读取银行简码信息错误:" + e.getMessage());
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
	 * ADD BY TSG 2009-03-11 更新跟踪表的文件状态
	 */

	public void updateFileStatus(Integer trace_id) throws Exception {
		Connection conn = CrmDBManager.getConnection();
		Statement stmt = conn.createStatement();
		try {
			stmt
					.executeUpdate("UPDATE BC_SERVICE_TRACE SET FILES_STATE = 2 WHERE TRACE_ID = "
							+ trace_id);
		} catch (Exception e) {
			throw new BusiException("读取银行简码信息错误:" + e.getMessage());
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * 通过IO流生成文件
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
					case 2: // BigDecimal数值类型的
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
					case 4: // 取item_balance1的值
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
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		}
	}

	/**
	 * 获得打印客户信息的参数
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
				.getParameter("card_type"));// 证件类型
		String card_id = Utility.trimNull(pageContext.getRequest()
				.getParameter("card_id"));// 证件号码
		String vip_card_id = Utility.trimNull(pageContext.getRequest()
				.getParameter("vip_card_id"));// VIP卡编号
		String hgtzr_bh = Utility.trimNull(pageContext.getRequest()
				.getParameter("hgtzr_bh"));// 合格投资人编号
		Integer birthday = Utility.parseInt(pageContext.getRequest()
				.getParameter("birthday"), new Integer(0));// 出生日期
		Integer start_rg_times = Utility.parseInt(pageContext.getRequest()
				.getParameter("start_rg_times"), new Integer(0));// 开始购买次数
		Integer end_rg_times = Utility.parseInt(pageContext.getRequest()
				.getParameter("end_rg_times"), new Integer(0));// 结束购买次数
		BigDecimal min_total_money = Utility.stringToDouble(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"min_total_money")));// 最低购买金额
		BigDecimal max_total_money = Utility.stringToDouble(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"max_total_money")));// 最高购买金额
		BigDecimal ben_amount_min = Utility.stringToDouble(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"ben_amount_min")));// 最低受益金额
		BigDecimal ben_amount_max = Utility.stringToDouble(Utility
				.trimNull(pageContext.getRequest().getParameter(
						"ben_amount_max")));// 最高受益金额
		String touch_type = Utility.trimNull(pageContext.getRequest()
				.getParameter("touch_type"));// 联系方式
		String cust_tel = Utility.trimNull(pageContext.getRequest()
				.getParameter("cust_tel"));// 联系电话
		String post_address = Utility.trimNull(pageContext.getRequest()
				.getParameter("post_address"));// 联系地址
		Integer product_id = Utility.parseInt(pageContext.getRequest()
				.getParameter("product_id"), new Integer(0));// 产品ID
		Integer sub_product_id = Utility.parseInt(pageContext.getRequest()
				.getParameter("sub_product_id"), new Integer(0));// 子产品ID
		Integer is_link = Utility.parseInt(pageContext.getRequest()
				.getParameter("is_link"), new Integer(0));// 是否按关联方查询
		Integer onlyemail = Utility.parseInt(pageContext.getRequest()
				.getParameter("onlyemail"), new Integer(0));// 是否存在邮寄
		String family_name = Utility.trimNull(pageContext.getRequest()
				.getParameter("family_name"));// 家庭名称
		String sort_name = Utility.trimNull(pageContext.getRequest()
				.getParameter("sort_name"));// 排序字段
		String prov_level = Utility.trimNull(pageContext.getRequest()
				.getParameter("prov_level"));// 受益级别
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
			mac="未找到地址！";
		}
		// 参数设置
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
	 * 导出代理销售更新导入用的模板excel
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
			WritableSheet ws = wwb.createSheet("第一页", 0);
			
			// DATE: 对应20130901这样的INT型的数据
			String[] fields = {"项目名称","PRODUCT_NAME", "STR"	,"合同编号","CONTRACT_SUB_BH","STR"
				,"合同签订日期","QS_DATE","DATE", "缴款日期","JK_DATE","DATE"
				,"合同截止日期","END_DATE","DATE", "合同期限","VALID_PERIOD","INT"
				/*,"期限单位","PERIOD_UNIT","INT"*/, "合同金额","TO_MONEY","DEC"
				,"委托人","CUST_NAME","STR", "委托人类型","CUST_TYPE_NAME","STR"				
				,"委托人证件名称","CARD_TYPE_NAME","STR", "委托人证件编号","CARD_ID","STR"
				,"委托人地址","POST_ADDRESS","STR", "委托人邮编","POST_CODE","STR"
				,"委托人法人代表","LEGAL_MAN","STR", "委托人联系方式","TOUCH_TYPE_NAME","STR"
				,"委托人固定电话","CUST_TEL","STR"	,"委托人手机","MOBILE","STR"
				,"委托人电子邮件","E_MAIL","STR"
				,"受益人","BENIFITOR_NAME","STR", "受益人类型","BENIFITOR_TYPE_NAME","STR"
				,"受益人证件名称","BENIFITOR_CARD_TYPE_NAME","STR", "受益人证件编号","BENIFITOR_CARD_ID","STR"
				,"受益人地址","BENIFITOR_POST_ADDRESS","STR", "受益人邮编","BENIFITOR_POST_CODE","STR"
				,"受益人联系方式","BENIFITOR_TOUCH_TYPE_NAME","STR", "受益人固定电话","BENIFITOR_CUST_TEL","STR"
				,"受益人手机","BENIFITOR_MOBILE","STR", "受益人电子邮件","BENIFITOR_E_MAIL","STR"
				,"受益人法人代表","BENIFITOR_LEGAL_MAN","STR"
				,"开户银行名称","BANK_NAME","STR", "支行名称","BANK_SUB_NAME","STR"
				,"银行账号","BANK_ACCT","STR", "开户户名","GAIN_ACCT","STR"
				,"财产类别","ENTITY_TYPE_NAME","STR", "财产名称","ENTITY_NAME","STR"
				,"缴款方式","JK_TYPE_NAME","STR", "认购费扣款方式","FEE_JK_TYPE","STR"
				,"受益优先级别","PROV_FLAG_NAME","STR"};

			exportExcel(ws, fileName, fields, list);
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("下载文件失败:" + e.getMessage());
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
					} else if ("DEC".equals(type)) { // BigDecimal数值类型的(保留两位)
						Label lableValue = new Label(i, line, Format
								.formatMoney(Utility.stringToDouble(Utility
										.trimNull(map.get(name)))));
						ws.addCell(lableValue);
					} else if ("DATE".equals(type)) { // Integer转日期
						Integer value = Utility.parseInt((Integer)map.get(name), new Integer(0));
						String tempValue = Format.formatDateLine(value);
						Label lableValue = new jxl.write.Label(i, line, tempValue);
						ws.addCell(lableValue);
					}		
				}

				line ++;
			}

		} catch (Exception e) {
			throw new BusiException("导出文件失败:" + e.getMessage());
		}
	}
	
	/**
	 * 导出客户信息
	 * 
	 * @author dingyj
	 * @since 2009-12-15
	 * @param objPath
	 *            文件名
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
		
		
		//获取结果集
		if (cust_ids != null) {
			for (int i = 0; i < cust_ids.length; i++) {
				
				if (cust_ids[i] != null && cust_ids[i].length() > 0) {
					custList.add(Utility.parseInt(Utility.trimNull(cust_ids[i]),
							new Integer(0)));
					cust_id = Utility.parseInt(cust_ids[i], new Integer(0));
				}
				//获得对象
				CustomerLocal locals = EJBFactory.getCustomer();
				CustomerVO cust_vo = new CustomerVO();
				cust_vo.setCust_id(cust_id);
				cust_vo.setInput_man(input_man);
				cust_vo.setIp(ip);
				cust_vo.setMac(mac);
				if (i==0)
					cust_vo.setExport_flag(new Integer(1)); // 导出客户信息
				else 
					cust_vo.setExport_flag(new Integer(10)); // 一批次导出只记一条log
				
				pageLists = locals.listProcAllExt(cust_vo, tPage,
						tPagesize);
				list=pageLists.getRsList();
				
//				获取选择的选项
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
			WritableSheet ws = wwb.createSheet("第一页", 0);
			/*String[] titleName = { "编号", "客户名", "客户类型", "出生日期", "证件类型", "证件号码",
					"认购金额", "转让金额", "托付金额", "受益金额", "最近购买的时间", "客户等级", "固定电话",
					"手机", "邮箱", "地址", "备注" };
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
			throw new BusiException("下载文件失败:" + e.getMessage());
		}
	}
	
	/**
	 * 导出受益人信息
	 * 
	 * @author huangsl
	 * @since 2013-03-05
	 * @param objPath
	 *            文件名
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
			WritableSheet ws = wwb.createSheet("第一页", 0);
			String[] titleName = { "受益人姓名", "证件号", "产品名称", "合同编号", "付款银行名称", "付款银行账号","银行账户名称"};
			String[] fieldName = { "CUST_NAME", "CARD_ID","PRODUCT_NAME", "CONTRACT_SUB_BH", "BANK_NAME", "BANK_ACCT","CUST_ACCT_NAME"};
			String[] fieldType = { "1", "1", "1", "1", "1", "1", "1"};

			clientExcel(ws, fileName, titleName, fieldName, fieldType,
					arrayList);
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		}catch(Exception e){
			throw new BusiException("下载文件失败:" + e.getMessage());
		}
	}

	/**
	 * 设置导出客户信息
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
			BigDecimal total_money = new BigDecimal(0.000); // 已购金额合计
			BigDecimal ben_amount = new BigDecimal(0.000); // 受益金额合计
			BigDecimal bxchange_amount = new BigDecimal(0.000); // 转让金额合计
			BigDecimal back_amount = new BigDecimal(0.000); // 兑付金额合计
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
					int sfieldType = Utility.parseInt(fieldType[k], 0); // 参数类型
					String sfieldName = fieldName[k];
					switch (sfieldType) {
					case 0: {
						Label lableValue = new Label(k, j + 2, Utility
								.trimNull(map.get(sfieldName)));
						ws.addCell(lableValue);
						break;
					}
					case 1: // String数值类型的
					{
						String value = Utility.trimNull(map.get(sfieldName));
						Label lableValue = new jxl.write.Label(k, j + 2, value);
						ws.addCell(lableValue);
						break;
					}
					case 2: // BigDecimal数值类型的(保留两位)
					{
						Label lableValue = new Label(k, j + 2, Format
								.formatMoney(Utility.stringToDouble(Utility
										.trimNull(map.get(sfieldName)))));
						ws.addCell(lableValue);
						break;
					}
					case 6: // Integer转日期
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
				Label labelValue = new Label(0, list.size() + 2, "合计");
				ws.addCell(labelValue);
				if (titleName[p] == "认购金额") {
					Label labelValue1 = new Label(index, list.size() + 2,
							Format.formatMoney(total_money).toString());
					ws.addCell(labelValue1);
				}
				if (titleName[p] == "转让金额") {
					Label labelValue1 = new Label(index, list.size() + 2,
							Format.formatMoney(bxchange_amount).toString());
					ws.addCell(labelValue1);
				}
				if (titleName[p] == "托付金额") {
					Label labelValue1 = new Label(index, list.size() + 2,
							Format.formatMoney(back_amount).toString());
					ws.addCell(labelValue1);
				}
				if (titleName[p] == "受益金额") {
					Label labelValue1 = new Label(index, list.size() + 2,
							Format.formatMoney(ben_amount).toString());
					ws.addCell(labelValue1);
				}
				index++;
			}

		} catch (Exception e) {
			throw new BusiException("导出文件失败:" + e.getMessage());
		}
	}

	/**
	 * 导出客户通讯录信息
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
	 * 获得客户通讯录信息
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
		String[] titleName = { "姓名", "手机1", "手机2", "邮箱", "地址", "项目名称", "公司电话",
				"家庭电话", "传真", "邮编" };
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
	 * 创建客户通讯录的Excel信息
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
			WritableSheet ws = wwb.createSheet("第一页", 0);
			WritableFont font = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD);
			WritableCellFormat format = new WritableCellFormat(font);
			format.setAlignment(Alignment.CENTRE);
			ws.mergeCells(0, 0, (fieldName.length) - 1, 0);
			Label labelC = new Label(0, 0, "客户通讯录", format);
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
			throw new BusiException("导出文件失败：" + ex.getMessage());
		}
	}

	/**
	 * ***************************************CRM 信托部分导出功能 BEGIN
	 * ************************************************************************
	 */
	// 预登记信息导出
	public void downloadExcel_refinfo() throws Exception {
		String filename = "D:\\预登记信息.xls";
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
				2, "10000");// 最低登记额度
		BigDecimal max_reg_money = Utility.parseDecimal(pageContext
				.getRequest().getParameter("max_reg_money"), new BigDecimal(0),
				2, "10000");// 最高登记额度
		String q_customer_cust_source = Utility.trimNull(pageContext
				.getRequest().getParameter("q_customer_cust_source"));// 客户来源
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
			throw new Exception("下载Excel文件失败！" + e.getMessage());
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

			String titleName[] = { "客户编号", "客户姓名", "客户类别", "家庭电话", "办公电话",
					"手机", "手机2", "登记金额", "登记日期", "客户来源", "预计投向" };

			String fieldName[] = { "CUST_NO", "CUST_NAME", "CUST_TYPE_NAME",
					"CUST_TEL", "O_TEL", "MOBILE", "BP", "REG_MONEY",
					"REG_DATE", "CUST_SOURCE_NAME", "INVEST_TYPE_NAME" };

			String fieldType[] = { "1", "1", "1", "1", "1", "1", "1", "2", "3",
					"1", "1" };
			// 1为字符串型,2为BigDecimal 3 Integer

			vfile = reginfoExcel(objpath, "客户预登记信息维护", titleName, fieldName,
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
		BigDecimal reg_money_total = new BigDecimal(0);// 登记金额合计

		try {
			f = new java.io.File(filename);
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(f,wbs);
			WritableSheet ws = wwb.createSheet("第一页", 0);

			// 设置列宽
			for (int y = 0; y < titleName.length; y++) {
				ws.setColumnView(y, 25);
			}

			// 1、标题的格式
			// 制定子字串格式
			WritableFont font = new WritableFont(WritableFont
					.createFont("华文楷体"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// 指定单元格的各种属性
			WritableCellFormat format = new WritableCellFormat(font);
			// 指定水平对齐的方式居中
			format.setAlignment(Alignment.CENTRE);
			// 制定垂直对齐的方式居中
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 合并单元格
			ws.mergeCells(0, 0, (titleName.length) - 1, 0);
			// 设置行高
			ws.setRowView(0, 500);
			// 设置边框
			format.setBorder(Border.ALL, BorderLineStyle.THIN);

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,
					format);
			ws.addCell(labelC0);
			int i = 0;

			// 2、表头的格式
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.BOLD, false,
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

			// 3、内容的格式
			// 居左
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 剧中
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 居右
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
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
					case 0: // 空
					{
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								"");
						ws.addCell(labelValue0);
						break;
					}
					case 1: // 字符串-居左
					{
						String fieldValue = Utility.trimNull(map
								.get(sfieldName));
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								fieldValue, formatLeft);
						ws.addCell(labelValue0);
						break;
					}
					case 2: // BigDecimal数值类型，保留2位小数-居右
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
					case 3: // Integer-居右
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
					case 4: // Integer to Date(yyyy-mm-dd)-居中
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
					case 5: // timesamp数值类型的-居中
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
			// Label labelValue = new Label(0, rslist.size() + 2, "合计");
			// ws.addCell(labelValue);
			// if (titleName[p].equals("登记额度")){
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
							Utility.trimNull("合计" + rslist.size() + "项"),
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
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		} finally {
			return f;
		}
	}

	// 预约
	public void downloadExcel_presell() throws Exception {
		String filename = "D:\\预约信息.xls";
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
				2, "10000");// 最低登记额度
		BigDecimal max_reg_money = Utility.parseDecimal(pageContext
				.getRequest().getParameter("max_reg_money"), new BigDecimal(0),
				2, "10000");// 最高登记额度
		String q_customer_cust_source = Utility.trimNull(pageContext
				.getRequest().getParameter("q_customer_cust_source"));// 客户来源
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
			throw new Exception("下载Excel文件失败！" + e.getMessage());
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
			String titleName[] = { "客户姓名", "客户类别", "预约号", "预约份数", "已转认购份数",
					"金额", "预期收益率下限", "预期收益率上限", "联系人", "联系电话", "客户来源", "日期",
					"有效期限", "状态", "备注" };
			String fieldName[] = { "CUST_NAME", "CUST_TYPE_NAME", "PRE_CODE",
					"PRE_NUM", "RG_NUM", "PRE_MONEY", "EXP_RATE1", "EXP_RATE2",
					"LINK_MAN", "MOBILE", "CUST_SOURCE_NAME", "PRE_DATE",
					"VALID_DAYS", "PRE_STATUS_NAME", "SUMMARY" };
			String[] fieldType = { "1", "1", "1", "3", "3", "2", "2", "2", "1",
					"1", "1", "3", "3", "1", "1" };
			// 1为字符串型,2为BigDecimal 3 Integer

			vfile = presellExcel(objpath, "客户预约信息", titleName, fieldName,
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
		Integer rg_num_total = new Integer(0); //已认购份数
		Integer pre_num_total = new Integer(0);//预约份数
		BigDecimal pre_money_total = new BigDecimal(0);//预约金额

		try {
			f = new java.io.File(filename);
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(f,wbs);
			WritableSheet ws = wwb.createSheet("第一页", 0);

			// 设置列宽
			for (int y = 0; y < titleName.length; y++) {
				ws.setColumnView(y, 25);
			}

			// 1、标题的格式
			// 制定子字串格式
			WritableFont font = new WritableFont(WritableFont
					.createFont("华文楷体"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// 指定单元格的各种属性
			WritableCellFormat format = new WritableCellFormat(font);
			// 指定水平对齐的方式居中
			format.setAlignment(Alignment.CENTRE);
			// 制定垂直对齐的方式居中
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 合并单元格
			ws.mergeCells(0, 0, (titleName.length) - 1, 0);
			// 设置行高
			ws.setRowView(0, 500);
			// 设置边框
			format.setBorder(Border.ALL, BorderLineStyle.THIN);

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,
					format);
			ws.addCell(labelC0);
			int i = 0;

			// 2、表头的格式
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.BOLD, false,
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

			// 3、内容的格式
			// 居左
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 剧中
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 居右
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
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
					case 2: // BigDecimal数值类型，保留2位小数-居右
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
					case 3: // Integer-居右
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
					case 4: // Integer to Date(yyyy-mm-dd)-居中
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
						// timesamp数值类型的-居中
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
							Utility.trimNull("合计" + rslist.size() + "项"),
							formatLeft);
					ws.addCell(labeSum0);
				} else {
					if (titleName[y].equals("金额")) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format
										.formatMoney(pre_money_total)),
								formatRight);
						ws.addCell(labeSum3);
					} else if (titleName[y].equals("已转认购份数")) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format
										.formatMoney(rg_num_total)),
								formatRight);
						ws.addCell(labeSum3);
					} else if (titleName[y].equals("预约份数")) {
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
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		} finally {
			return f;
		}
	}

	// 认购信息导出
	public void downloadExcel_subscribe() throws Exception {
		String filename = "D:\\认购信息.xls";
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
				.getParameter("min_rg_money"), new BigDecimal(0), 2, "10000");// 最低登记额度
		BigDecimal max_rg_money = Utility.parseDecimal(pageContext.getRequest()
				.getParameter("max_rg_money"), new BigDecimal(0), 2, "10000");// 最高登记额度
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
			throw new Exception("下载Excel文件失败！" + e.getMessage());
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
		String titleName[] = { "合同编号", "产品名称", "客户编号", "客户名称", "认购金额", "签署日期",
				"状态", "认购方式" };

		String fieldName[] = { "CONTRACT_SUB_BH", "PRODUCT_NAME", "CUST_NO",
				"CUST_NAME", "RG_MONEY", "QS_DATE", "HT_STATUS_NAME",
				"PRE_FLAG" };
		String[] fieldType = { "1", "1", "1", "1", "2", "3", "1", "1" };
		// 1为字符串型,2为BigDecimal 3 Integer

		vfile = subscribeExcel(objpath, "客户认购信息", titleName, fieldName,
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
			WritableSheet ws = wwb.createSheet("第一页", 0);

			// 设置列宽
			for (int y = 0; y < titleName.length; y++) {
				ws.setColumnView(y, 25);
			}

			// 1、标题的格式
			// 制定子字串格式
			WritableFont font = new WritableFont(WritableFont
					.createFont("华文楷体"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// 指定单元格的各种属性
			WritableCellFormat format = new WritableCellFormat(font);
			// 指定水平对齐的方式居中
			format.setAlignment(Alignment.CENTRE);
			// 制定垂直对齐的方式居中
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 合并单元格
			ws.mergeCells(0, 0, (titleName.length) - 1, 0);
			// 设置行高
			ws.setRowView(0, 500);
			// 设置边框
			format.setBorder(Border.ALL, BorderLineStyle.THIN);

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,
					format);
			ws.addCell(labelC0);

			int i = 0;

			// 2、表头的格式
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.BOLD, false,
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

			// 3、内容的格式
			// 居左
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 剧中
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 居右
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
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
					case 0: // 空
					{
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								"");
						ws.addCell(labelValue0);
						break;
					}
					case 1: // 字符串-居左
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
					case 2: // BigDecimal数值类型，保留2位小数-居右
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
					case 3: // Integer-居右
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
					case 4: { // Integer to Date(yyyy-mm-dd)-居中
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
					case 5: {// timesamp数值类型的-居中
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
							Utility.trimNull("合计" + rslist.size() + "项"),
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
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		} finally {
			return f;
		}
	}

	/**
	 * ***************************************CRM 信托部分导出功能
	 * END************************************************************************
	 */

	/**
	 * 打印客户手机
	 * 
	 * @author dingyj
	 * @since 2010-1-11
	 */
	public void downloadMobile(String objpath) throws Exception {
		String filename = objpath;
		String mobile = "";
		// 获得对象
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
	 * 导出客户手机，可勾选导出
	 * 
	 * @author guifeng
	 * @since 2011-04-01
	 */
	public void downloadMobileList(String objpath) throws Exception {
		String filename = objpath;
		String mobile = "";
		Integer cust_id = null;
		// 获得对象
		java.io.File f = new java.io.File(filename);
		PrintWriter out = new PrintWriter(new FileWriter(f));
		String[] cust_ids = pageContext.getRequest().getParameterValues(
				"cust_id");
		Integer input_man = Utility.parseInt(pageContext.getRequest().getParameter("input_man"), new Integer(0));
		String ip = Utility.trimNull(pageContext.getRequest().getRemoteAddr());
		String mac = Utility.getClientMacAddr(ip);
			

		if(mac==""||mac==null){
			mac="未找到地址！";
		}
		
		if (cust_ids != null) {
			for (int i = 0; i < cust_ids.length; i++) {
				cust_id = Utility.parseInt(cust_ids[i], new Integer(0));
				//获得对象
				CustomerLocal local = EJBFactory.getCustomer();
				CustomerVO cust_vo = new CustomerVO();
				cust_vo.setCust_id(cust_id);
				cust_vo.setInput_man(input_man);
				cust_vo.setIp(ip);
				cust_vo.setMac(mac);
				if (i==0)
					cust_vo.setExport_flag(new Integer(2)); // 导出客户手机
				else 
					cust_vo.setExport_flag(new Integer(10)); // 一批次导出只记一条log
				
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
	 * ***************************************CRM 客户信息导入功能
	 * END************************************************************************
	 */

	// 读Excel
	public int readExcel(PageContext in_pageContext, String objpath,
			int isystemFalg, Integer inputOperator) throws Exception {
		// smartUpload 初始化
		pageContext = in_pageContext;
		smartUpload.initialize(pageContext);
		String filename = objpath;

		if (!hasParse) {
			parseRequest();
		}

		// 导出路径
		String sfile_name = smartUpload.getRequest().getParameter("file_name");
		sfile_name = sfile_name.substring(sfile_name.lastIndexOf("\\") + 1);
		filename = objpath + "\\" + sfile_name;
		File f = new File(filename);

		HashMap customerMap = this.getCustomerTableMap();
		ProxyCustomer proxy_cust = new ProxyCustomer();
		CustomerVO vo = null;
		int count = 0;
		// 读取Excel 并SAVE入数据库

		try {
			CustomerLocal customerLocal = EJBFactory.getCustomer();
			InputStream is = new FileInputStream(filename);
			jxl.Workbook book = Workbook.getWorkbook(is);

			if (book == null) {
				throw new Exception("文件不存在!");
			}

			// 获得第一个工作表对象
			Sheet[] shets = book.getSheets();
			int iSheet = book.getNumberOfSheets();

			// 只读Sheet1
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

					if (!colTitle.equals("年龄") && !colTitle.equals("投资潜力")) {
						value[0] = cel.getContents();
					} else if (colTitle.equals("年龄")) {
						value[0] = Utility.parseInt(cel.getContents(),
								new Integer(0));
					} else if (colTitle.equals("投资潜力")) {
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
			throw new Exception("导数据失败,错误信息:" + e.getMessage());
		}

		return count;
	}

	/**
	 * 得到标题字段映射MAP
	 * 
	 * @return
	 */
	private HashMap getCustomerTableMap() {
		HashMap map = new HashMap();

		map.put("客户名称", "setCust_name");
		map.put("客户类型", "setCust_type_name");
		map.put("有效证件名称", "setCard_type_name");
		map.put("有效证件号码", "setCard_id");
		map.put("年龄", "setAge");
		map.put("投资潜力", "setPotenital_money");
		map.put("地区", "setArea");
		map.put("联系地址", "setPost_address");
		map.put("邮政编码", "setPost_code");
		map.put("手机号码", "setMobile");
		map.put("E-Mail地址", "setE_mail");
		map.put("联系人", "setContact_man");
		map.put("固话", "setCust_tel");
		map.put("客户经理", "setService_man_name");
		map.put("是否委托人", "setIsClient");
		map.put("联系方式", "setTouch_type_name");
		map.put("客户来源", "setCust_source_name");

		return map;
	}

	// 内部类 代理Set方法
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
		// 清空输出流
		response.reset();
		// 设置响应头和下载保存的文件名
		response.setHeader("content-disposition",
				Encode("attachment; filename=" + fileName));
		// 定义输出类型
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
					case 1: {// String类型
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
					case 2: {// BigDecimal数值类型的
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
					case 3: // 日期型
					{
						Integer fieldvalue = (Integer) rowMap.get(sfieldName[0]
								.toUpperCase());
						jxl.write.Label labelValue0 = new jxl.write.Label(k, j,
								Format.formatDateCn(fieldvalue), cellformat);
						ws.addCell(labelValue0);
						break;
					}
					case 4: // 整型的
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
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
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
	 * if(!file.exists()){ new Exception("文件不存在"); }else{ InputStream is = new
	 * FileInputStream(file); jxl.Workbook book = Workbook.getWorkbook(is);
	 * Sheet sheet = null; int rowCount = 0; int columCount = 0;
	 * 
	 * if(book==null){ new Exception("book异常"); }else{
	 * if(book.getNumberOfSheets()>0){ sheet = book.getSheet(0); rowCount =
	 * sheet.getRows(); columCount = sheet.getColumns(); }else{ new
	 * Exception("sheet异常"); } }
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
			mac="未找到地址！";
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
					vo.setExport_flag(new Integer(3)); // 导出通讯录
				else
					vo.setExport_flag(new Integer(10)); // 一批次导出只记一条log
				
				cust_list = local.listProcAll(vo);
				if (cust_list != null && cust_list.size() > 0) {
					list.add((Map) cust_list.get(0));
				}
			}
		}

		exportExcel(fileName, "客户通讯录", titleName, fieldName, fieldType,
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
				case 1:// String类型数据
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
				case 4:// Integer类型
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

	/** *****************************************代理销售功能************************************************************* */
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
			// 1只有serial_no没有book_code
		
			
			if (serialflag == 1) {
				iserial_no = getSequenceValue(table, isystemFalg);
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "( SERIAL_NO," + sFields.toString() + ") VALUES ("
						+ iserial_no + "," + sVlues.toString() + ")";
			}
			// 2只有serial_no没有book_code=1
			if (serialflag == 2) {
				iserial_no = getSequenceValue(table, isystemFalg);
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "( SERIAL_NO,BOOK_CODE," + sFields.toString()
						+ ") VALUES (" + iserial_no + ",1," + sVlues.toString()
						+ ")";
			}
			// 3只有serial_no没有book_code=2
			if (serialflag == 3) {
				iserial_no = getSequenceValue(table, isystemFalg);
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "( SERIAL_NO,BOOK_CODE," + sFields.toString()
						+ ") VALUES (" + iserial_no + ",2," + sVlues.toString()
						+ ")";
			}
			// 4没有serial_no没有book_code=1
			if (serialflag == 4) {
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "(BOOK_CODE," + sFields.toString() + ") VALUES ("
						+ "1," + sVlues.toString() + ")";
			}
			// 5没有serial_no没有book_code=2
			if (serialflag == 5) {
				insertSql = "INSERT  INTO " + table.toUpperCase()
						+ "(BOOK_CODE," + sFields.toString() + ") VALUES ("
						+ "2," + sVlues.toString() + ")";
			}
			stmt.executeUpdate(insertSql);

		} catch (Exception e) {
			throw new BusiException("插入数据失败：" + e.getMessage());
			
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
				throw new Exception("文件不存在!");
			// 获得第一个工作表对象
			Sheet[] shets = book.getSheets();
			int iSheet = book.getNumberOfSheets();
			for (int i = 0; i < iSheet; i++) {
				Sheet sheet = book.getSheet(i);

				// WritableSheet ws2 =wwb.getSheet(0);
				int iRows = sheet.getRows();
				int iColums = sheet.getColumns();
				// Excel页名的第一个字符存放的是自增量字段serial_no标志,1为表有这个字段,0为没有.
				String table = sheet.getName();
				Utility.debugln("table：" + table);
				int flag = Utility.parseInt(sheet.getName().substring(0, 1), 0);
				// 取得是否有自增量字段serial_no标志
				if (flag == 1)
					table = table.substring(1, table.length());
				String[] fields = new String[iColums];
				String[] sfieldtypes = new String[iColums];
				for (int r = 0; r < iColums; r++) {
					Cell cel = sheet.getCell(r, 0);
					fields[r] = cel.getContents();
					// Utility.debugln("第"+r+"列名："+fields[r]);
				}
				for (int j = 1; j < iRows; j++) {
					String[] values = new String[iColums];

					for (int k = 0; k < iColums; k++) {
						Cell cel = sheet.getCell(k, j);
						values[k] = "N'" + cel.getContents() + "'";
						//Utility.debugln("第"+k+"列值："+values[k]);
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
			throw new Exception("导数据失败,错误信息:" + e.getMessage());
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
				throw new Exception("文件不存在!");
			// 获得第一个工作表对象
			Sheet[] shets = book.getSheets();
			int iSheet = book.getNumberOfSheets();
			for (int i = 0; i < iSheet; i++) {
				Sheet sheet = book.getSheet(i);

				// WritableSheet ws2 =wwb.getSheet(0);
				int iRows = sheet.getRows();
				int iColums = sheet.getColumns();
				// Excel页名的第一个字符存放的是自增量字段serial_no标志,1为表有这个字段,0为没有.
				String table = sheet.getName();
				Utility.debugln("table：" + table);

				String[] fields = new String[iColums];
				String[] sfieldtypes = new String[iColums];
				for (int r = 0; r < iColums; r++) {
					Cell cel = sheet.getCell(r, 0);
					fields[r] = cel.getContents();
					// Utility.debugln("第"+r+"列名："+fields[r]);
				}
				for (int j = 1; j < iRows; j++) {
					String[] values = new String[iColums];

					for (int k = 0; k < iColums; k++) {
						Cell cel = sheet.getCell(k, j);
						values[k] = "N" + Utility.escapeSqlStr(cel.getContents().trim());
						//Utility.debugln("第"+k+"列值："+values[k]);
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
			throw new Exception("导数据失败,错误信息:" + e.getMessage());
		}
		return true;
	}
	
	
	
    // 读上传的金汇集合信托产品代销数据(xls电子表格格式)到临时的EFCRM..TCONTRACTIMPORT表中
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
                throw new Exception("文件不存在!");

            Map map = new HashMap();
			/*
			 * CREATE TABLE TCONTRACTIMPORT( BUSIN_FLAG NVARCHAR(3) NOT NULL,
			 *       --业务标志，020：认购022：申购024：赎回043：分红050: 清盘 
             * entrust_date INT NOT NULL,  --交易日期 
             * product_code NVARCHAR(20) NULL, --信托产品代码 
             * product_name NVARCHAR(30) NOT NULL, --信托产品名称 
             * entrust_balance NUMERIC(18,2) NOT NULL, --委托金额 
             * entrust_share INT NULL, --委托份额 
             * client_name NVARCHAR(30) NOT NULL, --客户名称 
             * id_kind NVARCHAR(6) NOT NULL,  --客户证件类型，和开放式基金接口定义相同 
             * id_no NVARCHAR(20) NOT NULL, --证件号码 )
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
                            content = content.replaceAll("'", "''"); // 对字符串中的'进行转义，适用于sql server
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
            throw new Exception("导数据失败,错误信息:" + e.getMessage());
        }
        return true;
    }

    // 清空保存着金汇集合信托产品代销数据的临时的EFCRM..TCONTRACTIMPORT表
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
            throw new BusiException("删除历史表信息失败: " + e.getMessage());
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
			throw new Exception("导数据失败,错误信息:" + e.getMessage());
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
			throw new Exception("导数据失败,错误信息:" + e.getMessage());
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
				throw new Exception("文件不存在!");

			//获得第一个工作表对象

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
					throw new Exception("到账日期[" + dzDateStr
							+ "]不合法,请修改为yyyy-MM-dd类型的日期");
				}
				//到账金额
				cell = sheet.getCell(1, i);
				String dzDateMoneyStr = Utility.trimNull(cell.getContents());
				BigDecimal dzDateMoney = null;
				try {
					dzDateMoney = new BigDecimal(dzDateMoneyStr.trim());
				} catch (NumberFormatException e) {
					throw new Exception("到账金额[" + dzDateMoneyStr + "]不合法");
				}
				//到账份额
				cell = sheet.getCell(2, i);
				String dzAmountStr = Utility.trimNull(cell.getContents());
				BigDecimal dzAmount = null;
				try {
					dzAmount = new BigDecimal(dzAmountStr.trim());
				} catch (NumberFormatException e) {
					throw new Exception("到账份额[" + dzAmount + "]不合法");
				}
				//客户姓名
				cell = sheet.getCell(3, i);
				String custName = Utility.trimNull(cell.getContents()).trim();
				if ("".equals(custName)) {
					throw new Exception("客户姓名不能为空");
				}
				
				//客户类别
				cell = sheet.getCell(4, i);
				String custType = Utility.trimNull(cell.getContents()).trim();
				if ("".equals(custType)) {
					throw new Exception("客户类型不能为空");
				}
				if (!"机构".equals(custType) && !"个人".equals(custType)) {
					throw new Exception("客户类型，只能是机构或个人");
				}
				int custTypeInt = "机构".equals(custType) ? 2 : 1;
				//推荐地名称
				cell = sheet.getCell(5, i);
				String cityName = Utility.trimNull(cell.getContents()).trim();
				
				//受益人银行
				cell = sheet.getCell(6, i);
				String bankName = Utility.trimNull(cell.getContents()).trim();
				
				String bankId = Argument.getTdictparamValueByContent(1103,bankName);
				
				//受益人银行支行
				cell = sheet.getCell(7, i);
				String bankSubName = Utility.trimNull(cell.getContents()).trim();
				
				//受益人账号
				cell = sheet.getCell(8, i);
				String bankAcct = Utility.trimNull(cell.getContents()).trim();
				//资金来源
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
			//删除已经导入数据

		} catch (Exception e) {
			throw new Exception("导数据失败,错误信息:" + e.getMessage());
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
			throw new Exception("删除据失败,错误信息:" + e.getMessage());
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
				throw new Exception("数据确认成功,错误信息:" + e.getMessage());
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
			throw new Exception("导数据失败,错误信息:" + e.getMessage());
		} finally {
			stmt.close();
			conn.close();
		}
		return true;
	}
	
	// 删除历史表信息
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
			throw new BusiException("删除历史表信息失败: " + e.getMessage());
		} finally {
			conn.close();
		}

	}

	//	 删除 受益人信息的中间导入表的所有数据
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
			throw new BusiException("删除历史表信息失败: " + e.getMessage());
		} finally {
			conn.close();
		}

	}
	
	// 纠正历史信息
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
			throw new BusiException("纠正导入数据信息失败: " + e.getMessage());
		} finally {
			conn.close();
		}

	}

	/**
	 * 导出受益人查询信息
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
				//				获取选择的选项
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

			publicExport(fileName, "受益人信息", viewStr, arrayList, menu_id,
					input_man, flag);
		} catch (Exception e) {
			throw new Exception("查询失败:" + e.getMessage());
		}
	}

    /**
     * 导出缴款查询信息
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
            publicExport(fileName, "缴款信息", viewStr, list, menu_id, input_man,
                    flag);
        } catch (Exception e) {
            throw new Exception("查询失败:" + e.getMessage());
        }
    }
    
    /**
     * 导出受益账户修改查询信息
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
            publicExport(fileName, "受益账户修改信息", viewStr, list, menu_id,
                    input_man, flag);
        } catch (Exception e) {
            throw new Exception("查询失败:" + e.getMessage());
        }
    }
    
    /**
     * 导出合同变更明细查询信息
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
            publicExport(fileName, "合同变更明细信息", viewStr, list, menu_id,
                    input_man, flag);
        } catch (Exception e) {
            throw new Exception("查询失败:" + e.getMessage());
        }
    }
    
    /**
     * 导出受益权转让查询信息
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
            publicExport(fileName, "受益权转让信息", viewStr, list, menu_id,
                    input_man, flag);
        } catch (Exception e) {
            throw new Exception("查询失败:" + e.getMessage());
        }
    }

    /**
     * 导出收益分配汇总查询信息
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
            publicExport(fileName, "收益分配汇总信息", viewStr, list, menu_id,
                    input_man, flag);
        } catch (Exception e) {
            throw new Exception("查询失败:" + e.getMessage());
        }
    }
    
    /**
     * 导出认购查询信息
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
            publicExport(fileName, "客户认购信息", viewStr, list, menu_id, input_man,
                    flag);

        } catch (Exception e) {
            throw new Exception("查询失败:" + e.getMessage());
        }
    }
    
	/**
	 * 填充Excel数据
	 * 
	 * @param fileName
	 *            文件名
	 * @param excelTitle
	 *            标题
	 * @param viewStri
	 *            拥有的字段
	 * @param list
	 *            数据集
	 * @throws Exception
	 */
	public void publicExport(String fileName, String excelTitle,
			String viewStr, List list, String menu_id, Integer input_man,
			int flag) throws Exception {
		Map map = new HashMap();
		String fee_jk_type = "";

		// 获得该员工已拥有的菜单
		String tempView = Argument.getMyMenuViewStr(menu_id, input_man);
		// 如果该员工没有设置菜单，则为默认菜单
		if (tempView != null && !tempView.equals("")) {
			viewStr = tempView;
		}
		// 将拥有的菜单与设置的所有菜单进行匹配
		Map fieldsMap = Argument.getMenuViewMap(menu_id, viewStr);
		if (fieldsMap == null || fieldsMap.isEmpty()) {
			fieldsMap = new HashMap();
			viewStr = "";
		}
		// 获得列字段的数组
		String[] fieldsArr = Utility.splitString(viewStr, "$");
		// 创建文件
		OutputStream outStr = getResponseStream(fileName + ".xls");
		WorkbookSettings wbs = new WorkbookSettings();
		wbs.setGCDisabled(true);
		WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
		WritableSheet ws = wwb.createSheet("第一页", 0);

		// 1、标题的格式
		// 制定子字串格式
		WritableFont font = new WritableFont(WritableFont.createFont("华文楷体"),
				20, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		// 指定单元格的各种属性
		WritableCellFormat format = new WritableCellFormat(font);
		// 指定水平对齐的方式居中
		format.setAlignment(Alignment.CENTRE);
		// 制定垂直对齐的方式居中
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		// 合并单元格
		ws.mergeCells(0, 0, (fieldsArr.length) - 1, 0);
		// 设置行高
		ws.setRowView(0, 500);
		// 设置边框
		format.setBorder(Border.ALL, BorderLineStyle.THIN);
		// 添加标题
		jxl.write.Label labelC = new jxl.write.Label(0, 0, excelTitle, format);
		ws.addCell(labelC);

		// 2、表头的格式
		WritableFont fontTop = new WritableFont(WritableFont.createFont("宋体"),
				14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat formatTop = new WritableCellFormat(fontTop);
		formatTop.setAlignment(Alignment.CENTRE);
		ws.setRowView(1, 400);
		formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);

		// 3、内容的格式
		// 居左
		WritableFont fontLeft = new WritableFont(WritableFont.createFont("宋体"),
				12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
		formatLeft.setAlignment(Alignment.LEFT);
		formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
		formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
		// 剧中
		WritableFont fontCenter = new WritableFont(WritableFont
				.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
		formatCenter.setAlignment(Alignment.CENTRE);
		formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
		formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
		// 居右
		WritableFont fontRight = new WritableFont(
				WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat formatRight = new WritableCellFormat(fontRight);
		formatRight.setAlignment(Alignment.RIGHT);
		formatRight.setVerticalAlignment(VerticalAlignment.CENTRE);
		formatRight.setBorder(Border.ALL, BorderLineStyle.THIN);

		try {
			// 设置表头
			for (int i = 0; i < fieldsArr.length; i++) {
				ws.setColumnView(i, 20);
				Label lableTitle = new jxl.write.Label(i, 1, ((Map) fieldsMap
						.get(fieldsArr[i])).get("FIELD_NAME").toString(),
						formatTop);
				ws.addCell(lableTitle);
			}

//           设置内容
            Integer serial_no = null;
            for (int j = 0; j < list.size(); j++) {
                map = (Map) list.get(j);
                serial_no = Utility.parseInt(Utility.trimNull(map
                        .get("SERIAL_NO")), new Integer(0));
                for (int k = 0; k < fieldsArr.length; k++) {
                    Label content = null;
                    ws.setRowView((k + 2), 400);
                    // 该字段的类型
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
                        if(temp_date.intValue() != 0) temp_date_str = Utility.getDatePart(temp_date.intValue(),1)+"年"+Utility.getDatePart(temp_date.intValue(),2)+"月";                       
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
                        if(temp_flag!=null&&temp_flag.booleanValue()){temp_str = "是";}else{temp_str = "否";}
                        content = new Label(k, (j + 2),temp_str, formatCenter);
                        break;                  
                    }
                    case 6: {
                        // 受益账户修改查询附件显示
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
                                str += aMap.get("ORIGIN_NAME") + "  "; // 循环获得附件
                            }
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(str), formatLeft);
                        }
                        // 受益权转让查询获得产品名称
                        if (flag == 5) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getProductName(Utility
                                            .parseInt(Utility.trimNull(map
                                                    .get(fieldsArr[k])),
                                                    new Integer(0)))),
                                    formatLeft);
                        }
                        // 收益分配汇总查询 获得产品编号
                        if (flag == 6) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getProductCode(Utility
                                            .parseInt(Utility.trimNull(map
                                                    .get("PRODUCT_ID")),
                                                    new Integer(0)))),
                                    formatLeft);
                        }
                        // 融资合同信息获得货币名
                        if (flag == 7) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getCurrencyName(Utility
                                            .trimNull(map.get(fieldsArr[k])))),
                                    formatLeft);
                        }
                        // 贷款类合同信息获得货币名
                        if (flag == 8) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getCurrencyName(Utility
                                            .trimNull(map.get(fieldsArr[k])))),
                                    formatLeft);
                        }
                        // 股权投资>>合同信息查询与打印获得货币名 或项目查询
                        if (flag == 11 || flag == 12) {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getCurrencyName(Utility
                                            .trimNull(map.get(fieldsArr[k])))),
                                    formatLeft);
                        }
                        break;
                    }
                    case 7: // 获得操作员名称
                    {
                        content = new Label(k, (j + 2), Utility
                                .trimNull(Argument.getIntrustOpName(Utility
                                        .parseInt(Utility.trimNull(map
                                                .get(fieldsArr[k])),
                                                new Integer(0)))), formatLeft);
                        break;
                    }
                    case 8: {
                        if (flag == 3) // 受益账户修改查询 获得银行名称+原支行名称
                        {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getBankName(Utility
                                            .trimNull(map.get(fieldsArr[k]))))
                                    + Utility.trimNull(map
                                            .get("OLD_BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        if (flag == 5) // 受益权转让查询表示受让人银行-银行支行名称
                        {
                            content = new Label(k, (j + 2),
                                    Utility.trimNull(map.get(fieldsArr[k]))
                                            + Utility.trimNull(map
                                                    .get("BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        if (flag == 6) // 收益分配汇总查询 获得产品名称
                        {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getProductName(Utility
                                            .parseInt(Utility.trimNull(map
                                                    .get("PRODUCT_ID")),
                                                    new Integer(0)))),
                                    formatLeft);
                        }
                        if (flag == 10) // 风险管理>>企业监控>>基本信息
                        {
                            content = new Label(k, (j + 2),
                                    Utility.trimNull(map.get(fieldsArr[k]))
                                            + Utility.trimNull(map
                                                    .get("BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        if (flag == 13 || flag == 15) // 信息查询>>项目信息
                        {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(map.get(fieldsArr[k]))
                                    + Utility.trimNull(map
                                            .get("TG_BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        break;
                    }
                    case 9: // 受益账户修改查询 表示合同编号 受益序号
                    {
                        content = new Label(k, (j + 2), Utility.trimNull(map
                                .get(fieldsArr[k]))
                                + Utility.trimNull(map.get("LIST_ID")),
                                formatLeft);
                        break;
                    }
                    case 10: {
                        if (flag == 3) // 受益账户修改查询 获得原银行名称+原支行名称
                        {
                            content = new Label(k, (j + 2), Utility
                                    .trimNull(Argument.getBankName(Utility
                                            .trimNull(map.get(fieldsArr[k]))))
                                    + Utility.trimNull(map
                                            .get("NEW_BANK_SUB_NAME")),
                                    formatLeft);
                        }
                        // 信息查询>>项目查询
                        if (flag == 12) {
                            String str = Utility.trimNull(
                                    map.get("BP_PERIOD_UNIT")).equals("1") ? "天"
                                    : "".equals("2") ? "月" : "年";
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
                        // 信息查询>>产品查询
                        if (flag == 13 || flag == 15) {
                            String str = "无期限";
                            if (Utility.trimNull(map.get("PERIOD_UNIT"))
                                    .equals("1"))
                                str = "天";
                            if (Utility.trimNull(map.get("PERIOD_UNIT"))
                                    .equals("2"))
                                str = "月";
                            if (Utility.trimNull(map.get("PERIOD_UNIT"))
                                    .equals("3"))
                                str = "年";
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
                            // 获得企业客户名称
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
			throw new Exception("文件下失败: " + e.getMessage());
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

			String titleName[] = { "产品名称", "客户名称", "客户经理","销售团队", "预约级别","到账日期", "到账金额", "到账方式",
					"退款日期", "退款金额" };

			OutputStream outStr = getResponseStream(java.net.URLEncoder.encode(
					"到账明细.xls", "utf-8"));
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
			WritableSheet ws = wwb.createSheet("第一页", 0);
			//设置列宽
			for (int y = 0; y < titleName.length; y++) {
				if(y == 0)
					ws.setColumnView(y, 30);
				else
					ws.setColumnView(y, 20);
			}
			// 1、标题的格式
			// 制定子字串格式
			WritableFont font = new WritableFont(WritableFont
					.createFont("华文楷体"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// 指定单元格的各种属性
			WritableCellFormat format = new WritableCellFormat(font);
			// 指定水平对齐的方式居中
			format.setAlignment(Alignment.CENTRE);
			// 制定垂直对齐的方式居中
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 合并单元格
			ws.mergeCells(0, 0, titleName.length - 1, 0);
			// 设置行高
			ws.setRowView(0, 500);
			// 设置边框
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 添加标题
			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, "到账明细", format);
			ws.addCell(labelC0);

			//2、表头的格式
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.BOLD, false,
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
			
			//3、内容的格式
			// 居左
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 剧中
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 居右
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
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
			//合计项
			ws.setRowView((list.size()+2), 350);
			jxl.write.Label labelValue0 = new jxl.write.Label(0, (list.size()+2), "合计"+list.size()+"项", formatTop);
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
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
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
			preVo.setStatus(1);//只查询正常的数据

			List list = local.querySalesResultForStatisticMore(preVo);

			String titleName[] = { "产品名称", "客户类型", "客户编号", "客户名称", "客户经理","销售团队", "预约级别","到账日期", "到账金额", "到账方式",
					"产品分类", "备注", "退款日期", "退款金额" };

			OutputStream outStr = getResponseStream(java.net.URLEncoder.encode(
					"转销量结果统计.xls", "utf-8"));
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
			WritableSheet ws = wwb.createSheet("第一页", 0);
			//设置列宽
			for (int y = 0; y < titleName.length; y++) {
				if(y == 0)
					ws.setColumnView(y, 30);
				else
					ws.setColumnView(y, 20);
			}
			// 1、标题的格式
			// 制定子字串格式
			WritableFont font = new WritableFont(WritableFont
					.createFont("华文楷体"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// 指定单元格的各种属性
			WritableCellFormat format = new WritableCellFormat(font);
			// 指定水平对齐的方式居中
			format.setAlignment(Alignment.CENTRE);
			// 制定垂直对齐的方式居中
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 合并单元格
			ws.mergeCells(0, 0, titleName.length - 1, 0);
			// 设置行高
			ws.setRowView(0, 500);
			// 设置边框
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 添加标题
			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, "到账明细", format);
			ws.addCell(labelC0);

			//2、表头的格式
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.BOLD, false,
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
			
			//3、内容的格式
			// 居左
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
			formatLeft.setAlignment(Alignment.LEFT);
			formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 剧中
			WritableFont fontCenter = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
			formatCenter.setAlignment(Alignment.CENTRE);
			formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 居右
			WritableFont fontRight = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
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
			//合计项
			ws.setRowView((list.size()+2), 350);
			jxl.write.Label labelValue0 = new jxl.write.Label(0, (list.size()+2), "合计"+list.size()+"项", formatTop);
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
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
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
                throw new Exception("文件不存在!");
            //获得第一个工作表对象
            Sheet[] shets = book.getSheets();

            int iSheet = book.getNumberOfSheets();

            for (int i = 0; i < iSheet; i++) {
                Sheet sheet = book.getSheet(i);
                //WritableSheet ws2 =wwb.getSheet(0);
                int iRows = sheet.getRows();
                int iColums = sheet.getColumns();
                //Excel页名的第一个字符存放的是自增量字段serial_no标志,1为表有这个字段,0为没有.
                String table = sheet.getName();

                int flag = Utility.parseInt(sheet.getName().substring(0, 1), 0);
                //取得是否有自增量字段serial_no标志
                if (flag == 1)
                    table = table.substring(1, table.length());
                String[] fields = new String[iColums];
                String[] sfieldtypes = new String[iColums];
                for (int r = 0; r < iColums; r++) {
                    Cell cel = sheet.getCell(r, 0);
                    fields[r] = cel.getContents();
                    //Utility.debugln("第"+r+"列名："+fields[r]);
                }
                for (int j = 1; j < iRows; j++) {
                    String[] values = new String[iColums];

                    for (int k = 0; k < iColums; k++) {
                        Cell cel = sheet.getCell(k, j);
                        if(cel.getContents() != null && !"".equals(cel.getContents().trim())){ //20110117 dingyj 添加判断空行不插入
                            values[k] = "N'" + cel.getContents() + "'";
                        }   
                        //Utility.debugln("第"+k+"列值："+values[k]);
                    }
                    //Utility.debugln("------------------------------------------");
                    if (!Utility.trimNull(values[0]).equals("''")) {
                        insertRecord(fields,values,tableName,flag,isystemFalg,stmt);
                    } else
                        continue;
                }
            }
            if("OLDREDEEM".equals(tableName)){//导入预处理，错误验证
    
                CallableStatement ctmt = conn.prepareCall("{?=call SP_IMPORT_TSQREDEEM_PRETREAT}");
                ctmt.registerOutParameter(1, Types.INTEGER);
                ctmt.executeUpdate();

                int iret = ctmt.getInt(1);
                
                if(ctmt != null)
                    ctmt.close();

                if (iret < 0)
                    throw new Exception("导入预处理，错误验证失败,错误信息:" + iret );
            }
            
        } catch (Exception e) {
            throw new Exception("导数据失败,错误信息:" + e.getMessage());
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
     * 根据次数将代理销售数据分别从不同表导入到相应的表中
     * @param input_man
     * @param book_code
     * @param dealFlag 处理标志。1表示代理销售，2代表代理销售更新
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
            if(dealFlag==2){ // 2代表代理销售更新
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
            throw new Exception("导数据失败,错误信息:" + e.getMessage());
        }finally{
            if(stmt!=null)
                stmt.close();
            if(conn!=null)
                conn.close();
        }
        
        return true;
    }
    
    // dealFlag 处理标志。1表示代理销售，2代表代理销售更新
    public boolean delOldData(int dealFlag) throws Exception {
        int inputflag = Utility.parseInt(smartUpload.getRequest().getParameter("inputflag"),0);

        if (inputflag != 3)
            return false;
        Connection conn = IntrustDBManager.getConnection();
        try {
            String dealSQL = "DELETE FROM OLD";
            if(dealFlag==2){ // 2代表代理销售更新
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
            throw new BusiException("删除历史表信息失败: " + e.getMessage());
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
            throw new BusiException("纠正导入数据信息失败: " + e.getMessage());
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
                Utility.debugln("进入3");
                file =
                    ProductBondCountExcel(
                        filename,
                        product_id,
                        "",
                        new Integer(0),
                        prov_level);

            }*/ else if (outporttype == 4) {
                Utility.debugln("进入4");
                String[] paramvalues =
                    pageContext.getRequest().getParameterValues("serial_no");
                file = DeployOutputTotalExcel(filename, book_code, paramvalues);
            }
            System.out.println(file.toString());
            downloadJsp(file);
        } catch (Exception e) {
            throw new Exception("下载Excel文件失败！" + e.getMessage());
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
                    "合同编号",
                    "收益人姓名",
                    "身份证号",
                    "本金",
                    "收益类型",
                    "收益金额",
                    "扣税",
                    "银行帐号",
                    "付款银行",
                    "收益分配时间" };
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

            //1为字符串型,2为BigDecimal
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
            WritableSheet ws = wwb.createSheet("第一页", 0);

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
                        case 2 : //BigDecimal数值类型的
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
                        case 4 : //BigDecimal数值类型的
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
            throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
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
                    "合同编号",
                    "收益人姓名",
                    "身份证号",
                    "本金",
                    "收益类型",
                    "收益金额",
                    "扣税",
                    "银行帐号",
                    "付款银行",
                    "银行户名",
                    "收益分配时间",
                    "备注"};
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
            //1为字符串型,2为BigDecimal
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
                WritableSheet ws = wwb.createSheet("第一页", 0);
                //设置列宽
                for (int y = 0; y < titleName.length; y++) {
                    ws.setColumnView(y, 25);
                }
//               1、标题的格式
                // 制定子字串格式
                WritableFont font = new WritableFont(WritableFont
                        .createFont("华文楷体"), 20, WritableFont.BOLD, false,
                        UnderlineStyle.NO_UNDERLINE);
                // 指定单元格的各种属性
                WritableCellFormat format = new WritableCellFormat(font);
                // 指定水平对齐的方式居中
                format.setAlignment(Alignment.CENTRE);
                // 制定垂直对齐的方式居中
                format.setVerticalAlignment(VerticalAlignment.CENTRE);
                // 合并单元格
                ws.mergeCells(0, 0, (titleName.length) - 1, 0);
                // 设置行高
                ws.setRowView(0, 500);
                // 设置边框
                format.setBorder(Border.ALL, BorderLineStyle.THIN);
                // 添加标题
                jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,format);
                ws.addCell(labelC0);
                int i = 0;
//               2、表头的格式
                WritableFont fontTop = new WritableFont(WritableFont
                        .createFont("宋体"), 12, WritableFont.BOLD, false,
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
//               3、内容的格式
                // 居左
                WritableFont fontLeft = new WritableFont(WritableFont
                        .createFont("宋体"), 12, WritableFont.NO_BOLD, false,
                        UnderlineStyle.NO_UNDERLINE);
                WritableCellFormat formatLeft = new WritableCellFormat(fontLeft);
                formatLeft.setAlignment(Alignment.LEFT);
                formatLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
                formatLeft.setBorder(Border.ALL, BorderLineStyle.THIN);
                // 剧中
                WritableFont fontCenter = new WritableFont(WritableFont
                        .createFont("宋体"), 12, WritableFont.NO_BOLD, false,
                        UnderlineStyle.NO_UNDERLINE);
                WritableCellFormat formatCenter = new WritableCellFormat(fontCenter);
                formatCenter.setAlignment(Alignment.CENTRE);
                formatCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
                formatCenter.setBorder(Border.ALL, BorderLineStyle.THIN);
                // 居右
                WritableFont fontRight = new WritableFont(WritableFont
                        .createFont("宋体"), 12, WritableFont.NO_BOLD, false,
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
                            case 2 : //BigDecimal数值类型的
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
                            case 4 : //BigDecimal数值类型的
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
                throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
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
			throw new BusiException("文件不存在！有可能该文件已被删除。");
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
			throw new BusiException("打开文件失败，请确认是否有权限阅读!");
		} finally {
			os.close();
			dis.close();
			out.close();
		}
	}
    //导出任务明细信息
	public void exportService(Integer input_operatorCode) throws Exception {
		try {
			String serial_no = Utility.trimNull(pageContext.getRequest().getParameter("serial_no"));
			Integer edit_flag = Utility.parseInt(pageContext.getRequest().getParameter("edit_flag"), new Integer(0));
			Integer q_serviceType = Utility.parseInt(pageContext.getRequest().getParameter("q_serviceType"),new Integer(0));
			String q_serviceWay = Utility.trimNull(pageContext.getRequest().getParameter("q_serviceWay"));

			//获得对象
			ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
			ServiceTaskVO vo = new ServiceTaskVO();
			
			List list = new ArrayList();
			if (serial_no.equals("")) {
				//获得明细列表
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
			
			String titleName[] = { "客户编号", "客户名称", "客户经理","任务名称","联系电话", "邮寄地址1","邮编1", "邮寄地址2", "邮编2","处理时间","处理结果描述" };

			OutputStream outStr = getResponseStream(java.net.URLEncoder.encode(
					"任务明细导出.xls", "utf-8"));
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
			WritableSheet ws = wwb.createSheet("第一页", 0);
			//设置列宽
			for (int y = 0; y < titleName.length; y++) {
				if(y == 0)
					ws.setColumnView(y, 30);
				else
					ws.setColumnView(y, 20);
			}
			// 1、标题的格式
			// 制定子字串格式
			WritableFont font = new WritableFont(WritableFont
					.createFont("华文楷体"), 20, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			// 指定单元格的各种属性
			WritableCellFormat format = new WritableCellFormat(font);
			// 指定水平对齐的方式居中
			format.setAlignment(Alignment.CENTRE);
			// 制定垂直对齐的方式居中
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 合并单元格
			ws.mergeCells(0, 0, titleName.length - 1, 0);
			// 设置行高
			ws.setRowView(0, 500);
			// 设置边框
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 添加标题
			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, "任务明细", format);
			ws.addCell(labelC0);

			//2、表头的格式
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.BOLD, false,
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
			
			//3、内容的格式
			// 居左
			WritableFont fontLeft = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
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
			//合计项
			ws.setRowView((list.size()+2), 350);
			jxl.write.Label labelValue0 = new jxl.write.Label(0, (list.size()+2), "合计"+list.size()+"项", formatTop);
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
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		}
	}
	
}