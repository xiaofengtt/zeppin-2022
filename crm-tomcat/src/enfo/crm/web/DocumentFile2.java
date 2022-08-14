package enfo.crm.web;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.intrust.AttachmentLocal;
import enfo.crm.intrust.ContractLocal;
import enfo.crm.intrust.GainLevelLocal;
import enfo.crm.intrust.PreContractLocal;
import enfo.crm.intrust.ProductLocal;
import enfo.crm.system.DictparamLocal;
import enfo.crm.system.ProductInfoReposLocal;
import enfo.crm.tools.Argument;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Format;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AttachmentVO;
import enfo.crm.vo.ContractVO;
import enfo.crm.vo.DictparamVO;
import enfo.crm.vo.PreContractVO;
import enfo.crm.vo.ProductVO;

public class DocumentFile2 {
	private String file_name = "";
	private boolean hasParse = false;
	private final long MAXFILESIZE = 500000000;
	private PageContext pageContext;
	private com.jspsmart.upload.Request request;
	private final String SEPARATOR = java.io.File.separator;
	public com.jspsmart.upload.SmartUpload smartUpload = new com.jspsmart.upload.SmartUpload();
	private String strFolder = "C:\\Temp";
	private String toFolder;
	
	public DocumentFile2() {
	}

	public DocumentFile2(PageContext in_pageContext) {
		try {
			pageContext = in_pageContext;
			smartUpload.initialize(pageContext);
			smartUpload.setTotalMaxFileSize(MAXFILESIZE);
		} catch (Exception e) {
			smartUpload = null;
			pageContext = null;
		}
	}
	
	public void parseRequest() throws Exception {
		if (hasParse) return;
		try {
			smartUpload.upload();
			request = smartUpload.getRequest();
			hasParse = true;
		} catch (Throwable e) {
			request = null;
			throw new BusiException("文件上传失败:"+e.toString());
		}
	}
	
	private boolean insureFolder(String strFolder) {
		java.io.File dir = new java.io.File(strFolder);
		if (!dir.isDirectory())
			dir.mkdirs();
		return dir.isDirectory();
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
	
	public String getFile_name() {
		return file_name;
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 上传附件
	 */
	public boolean uploadAttchment(Integer df_table_id, Integer df_serial_no,
			String description, String df_temp_id, String file_name[],
			Integer input_man) throws Exception {
		if (!hasParse)
			parseRequest();
		
		String strFolder = Utility.replaceAll(Argument.getDictContentIntrust("800101")," ",""); 
		if("".equals(strFolder)) 
			strFolder = "c:\\uploadfiles\\";
		
		strFolder = Utility.replaceAll(strFolder,"\\","//");		
		
		if (!insureFolder(strFolder))
			return false;

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
			int fileCount = smartUpload.getFiles().getCount();
			for(int i= 0;i <  fileCount;i++){
				int iFileSize = smartUpload.getFiles().getFile(i).getSize();
		
				if (iFileSize == 0)
					return false;
				if (iFileSize > 500* 1024 * 1024)
					throw new BusiException("文件大小不能超过500M！");
			}
			

			File f = new File(strFolder);
			

			
			if (!f.exists())
				f.mkdir();
			
			int numInde = f.list().length;
				
			smartUpload.save(strFolder,com.jspsmart.upload.SmartUpload.SAVE_PHYSICAL);
		
			
			for(int i= 0;i < fileCount;i++){	
				numInde++;

				String newFileName = df.format(new Date())+ "-" + numInde+ "."
						+ smartUpload.getFiles().getFile(i).getFileExt();
				String oldFileName = smartUpload.getFiles().getFile(i).getFileName();
			
				//重命名
				renamefile(strFolder + oldFileName, newFileName);
				newFileName = strFolder + newFileName;

				int iFileSize = smartUpload.getFiles().getFile(i).getSize();
				//保存记录
				AttachmentLocal attachment = EJBFactory.getAttachment();
				AttachmentVO vo = new AttachmentVO();
						
				
				if (true) {
					if(file_name!=null && file_name[i] !=null && !"".equals(file_name[i])){
						//oldFileName = file_name[i];//+ oldFileName.substring(oldFileName.lastIndexOf("."));
						description = file_name[i];
					}
	
					vo.setDf_talbe_id(df_table_id);
					vo.setDf_serial_no(df_serial_no);
					vo.setSave_name(newFileName);
					vo.setOrigin_name(oldFileName);
					vo.setFile_size(new Integer(iFileSize));
					vo.setDescription(description);
					vo.setTemp_df_id(df_temp_id);
					vo.setInput_man(input_man);
					attachment.append(vo);
				}
				attachment.remove();
			}
			return true;
		} catch (Exception e) {
			throw new BusiException("文件上传失败: " + e.getMessage());
		}
	}
	
	/**
	 * ADD BY TAOCHEN 20100819 得到操作员签名图片
	 * 
	 * @param realPath
	 * @param op_code
	 * @return
	 * @throws Exception
	 */
	public static String getOpImgUrl(String realPath, Integer cust_id)
			throws Exception {
		String imageUrl = "";
		if (cust_id == null) {
			return null;
		} else {
			String strSql = "select ImageIdentification from TCustoemrs where cust_id ="+cust_id;
			String folderPath = realPath + "\\images\\temp\\";
			String fileName = "Cust_" + cust_id + ".jpg";
			// 新建文件夹 用于保存图片
			java.io.File myFilePath = new java.io.File(folderPath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
			// 输出流
			java.io.InputStream in = null;
			java.io.FileOutputStream fileOutStream = new java.io.FileOutputStream(folderPath + fileName);
			java.io.DataOutputStream dataOutStream = new java.io.DataOutputStream(new java.io.BufferedOutputStream(fileOutStream));

			java.sql.Connection conn = CrmDBManager.getConnection();
			java.sql.Statement stmt = conn.createStatement();
			java.sql.ResultSet rs = null;

			try {
				rs = stmt.executeQuery(strSql);
				while (rs.next()) {
					in = rs.getBinaryStream("ImageIdentification");
					break;
				}
				if (in != null) {
					byte b[] = new byte[0x7a120];
					int len = 0;
					while ((len = in.read(b)) != -1) {
						dataOutStream.write(b, 0, len);
					}
					dataOutStream.close();
					in.close();
					imageUrl = "/images/temp/" + fileName;
				}
			} catch (Exception e) {
				throw new BusiException("读取客户身份证图片失败:" + e.getMessage());
			} finally {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			}
		}
		return imageUrl;
	}
	
	private String Encode(String in) {
		try {
			return new String(in.getBytes("GBK"), "ISO8859_1");
		} catch (Exception e) {
			return in;
		}
	}
	
	public void downloadProblemFile2(String strFile,String name) throws Exception {
		strFile = Utility.replaceAll(strFile,"\\","/");
		java.io.File file = new java.io.File(strFile);
		String filen = "";
		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());
		if (!file.exists())
			throw new BusiException("文件不存在！有可能该文件已被删除。");
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-disposition", //inline
					Encode("attachment;filename=\"" + name +"\""));
			fis = new FileInputStream(file); 
			os = response.getOutputStream();
			byte[] buf = new byte[1024];
			int read = 0;
			while ( (read=fis.read(buf)) > -1) {
				os.write(buf, 0, read);				
			}
			
			os.flush();  
			response.flushBuffer(); 
			
		} catch (Exception e) {
			throw new BusiException("打开文件失败，请确认是否有权限阅读!");
		} finally {			
			if (fis!=null) fis.close();			
			if (os!=null) os.close();
		}
	}
	/**
	 * 预登记信息导出
	 * @param fileName
	 * @throws Exception
	 */
	public void downloadExcel_reginfo(String fileName) throws Exception{
		PreContractVO vo = new PreContractVO();
		PreContractLocal preContract = EJBFactory.getPreContract();
		
		String q_cust_name = pageContext.getRequest().getParameter("q_cust_name");
		Integer book_code = Utility.parseInt(pageContext.getRequest().getParameter("book_code"), null);
		Integer input_man = Utility.parseInt(pageContext.getRequest().getParameter("input_man"), new Integer(0));
		Integer int_flag = Utility.parseInt(pageContext.getRequest().getParameter("int_flag"), new Integer(0));
		Integer q_cust_type = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_cust_type")), new Integer(0));
		String q_invest_type_name = Utility.trimNull(pageContext.getRequest().getParameter("q_invest_type_name"));
		String q_invest_type = Utility.trimNull(pageContext.getRequest().getParameter("q_invest_type"));
		BigDecimal min_reg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("min_reg_money"), new BigDecimal(0),2, "10000");// 最低登记额度
		BigDecimal max_reg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("max_reg_money"), new BigDecimal(0),2, "10000");// 最高登记额度
		String q_customer_cust_source = Utility.trimNull(pageContext.getRequest().getParameter("q_customer_cust_source"));// 客户来源
		Integer q_group_id = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_group_id")),new Integer(0));
		Integer q_class_detail_id = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_class_detail_id")),new Integer(0));
		
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
		
		List rslist = preContract.query_reginfo_crm(vo);
		
		String titleName[] = { "客户编号", "客户姓名", "客户类别", "家庭电话", "办公电话",
				"手机", "手机2", "登记金额", "登记日期", "客户来源", "预计投向" };

		String fieldName[] = { "CUST_NO", "CUST_NAME", "CUST_TYPE_NAME",
				"CUST_TEL", "O_TEL", "MOBILE", "BP", "REG_MONEY",
				"REG_DATE", "CUST_SOURCE_NAME", "INVEST_TYPE_NAME" };

		String fieldType[] = { "1", "1", "1", "1", "1", "1", "1", "2", "3",
				"1", "1" };
		
		//导出功能
		try{
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("第一页", 0);
			
			exportExcel_reginfo(ws, "客户预登记信息", titleName, fieldName, fieldType, rslist);
			
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("下载文件失败:" + e.getMessage());
		}
	}
	
	public void exportExcel_reginfo(WritableSheet ws, String excelTitle,String[] titleName, String[] fieldName, String[] fieldType,List rslist) throws Exception{
		BigDecimal reg_money_total = new BigDecimal(0);// 登记金额合计
		try{
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
		} 
		catch (Exception e) {
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		} 
	}
	/**
	 * 预约信息导出
	 * @param fileName
	 * @throws Exception 
	 */
	public void downloadExcel_presell(String fileName) throws Exception{
		PreContractVO vo = new PreContractVO();
		PreContractLocal preContract = EJBFactory.getPreContract();
		
		Integer book_code = Utility.parseInt(pageContext.getRequest().getParameter("book_code"), new Integer(1));
		Integer input_man = Utility.parseInt(pageContext.getRequest().getParameter("input_man"), new Integer(0));
		Integer q_productId = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_productId")), new Integer(0));
		String q_productCode = Utility.trimNull(pageContext.getRequest().getParameter("q_productCode"));
		String q_cust_name = Utility.trimNull(pageContext.getRequest().getParameter("q_cust_name"));
		String q_pre_code = Utility.trimNull(pageContext.getRequest().getParameter("q_pre_code"));
		Integer q_cust_type = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_cust_type")), new Integer(0));
		BigDecimal min_reg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("min_reg_money"), new BigDecimal(0), 2, "10000");// 最低登记额度
		BigDecimal max_reg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("max_reg_money"), new BigDecimal(0), 2, "10000");// 最高登记额度
		String q_customer_cust_source = Utility.trimNull(pageContext.getRequest().getParameter("q_customer_cust_source"));// 客户来源
		Integer q_group_id = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_group_id")),new Integer(0));
		Integer q_class_detail_id = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_class_detail_id")),new Integer(0));

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
		
		List rslist = preContract.query_crm(vo);
		
		String titleName[] = { "客户姓名", "客户类别", "预约号", "预约份数", "已转认购份数",
				"金额", "预期收益率下限", "预期收益率上限", "联系人", "联系电话", "客户来源", "预约日期",
				"预计认购日期","客户群组","有效期限", "状态", "备注" };
		String fieldName[] = { "CUST_NAME", "CUST_TYPE_NAME", "PRE_CODE",
				"PRE_NUM", "RG_NUM", "PRE_MONEY", "EXP_RATE1", "EXP_RATE2",
				"LINK_MAN", "MOBILE", "CUST_SOURCE_NAME", "PRE_DATE",
				"EXP_REG_DATE","CUST_ID","VALID_DAYS", "PRE_STATUS_NAME", "SUMMARY" };
		String[] fieldType = { "1", "1", "1", 
							"3","3","2","2","2",
							"1","1","1","4",
							"4","1","3","1","1" };
		
		//导出功能
		try{
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("第一页", 0);
			
			exportExcel_presell(ws, "客户预约信息", titleName, fieldName, fieldType, rslist);
			
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("下载文件失败:" + e.getMessage());
		}
	}
	
	public void exportExcel_presell(WritableSheet ws, String excelTitle,String[] titleName, String[] fieldName, String[] fieldType,List rslist) throws Exception{
		Integer rg_num_total = new Integer(0); //已认购份数
		Integer pre_num_total = new Integer(0);//预约份数
		BigDecimal pre_money_total = new BigDecimal(0);//预约金额
		
		try{
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

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,format);
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
									Utility.trimNull(Argument.getOpName(new Integer(
															Utility.trimNull(map.get("LINK_MAN"))))),formatLeft);
							ws.addCell(labelValue0);
						} 
						else if(sfieldName.equals("CUST_ID")){
							Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")), new Integer(0));
							String custGroup = Argument.getCustGroups(cust_id);
							jxl.write.Label labelValue0 = new jxl.write.Label(
									k,
									j,
									Utility.trimNull(custGroup),formatLeft);
							ws.addCell(labelValue0);								
						}
						else {
							String fieldValue = Utility.trimNull(map.get(sfieldName));
							jxl.write.Label labelValue0 = new jxl.write.Label(k, j, Utility.trimNull(fieldValue),formatLeft);
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
					default:break;
					}
				}
				j++;
			}

			for (int y = 0; y < fieldType.length; y++) {
				if (y == 0) {
					jxl.write.Label labeSum0 = new jxl.write.Label(y, j,
							Utility.trimNull("合计" + rslist.size() + "项"),
							formatLeft);
					ws.addCell(labeSum0);
				} else {
					if (titleName[y].equals("金额")) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format.formatMoney(pre_money_total)),formatRight);
						ws.addCell(labeSum3);
					} 
					else if(titleName[y].equals("已转认购份数")){
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format.formatMoney(rg_num_total)),formatRight);
						ws.addCell(labeSum3);
					}
					else if(titleName[y].equals("预约份数")){
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format.formatMoney(pre_num_total)),formatRight);
						ws.addCell(labeSum3);
					}
					else {
						jxl.write.Label labeSum1 = new jxl.write.Label(y, j,
								Utility.trimNull(""), formatLeft);
						ws.addCell(labeSum1);
					}
				}
			}
		} 
		catch (Exception e) {
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		} 
	}
	/**
	 * 认购导出
	 * @throws Exception
	 */
	public void downloadExcel_subscribe(String fileName) throws Exception{
		ContractVO vo = new ContractVO();
		ContractLocal contract = EJBFactory.getContract();
		
		String q_productCode = Utility.trimNull(pageContext.getRequest().getParameter("q_productCode"));
		Integer q_pre_flag = Utility.parseInt(pageContext.getRequest().getParameter("q_pre_flag"), new Integer(0));
		Integer q_productId = Utility.parseInt(pageContext.getRequest().getParameter("q_productId"), new Integer(0));
		Integer q_check_flag = Utility.parseInt(pageContext.getRequest().getParameter("q_check_flag"), new Integer(0));
		String q_cust_name = Utility.trimNull(pageContext.getRequest().getParameter("q_cust_name"));
		String q_card_id = Utility.trimNull(pageContext.getRequest().getParameter("q_card_id"));
		String query_contract_bh = Utility.trimNull(pageContext.getRequest().getParameter("query_contract_bh"));
		BigDecimal min_rg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("min_rg_money"), new BigDecimal(0), 2, "10000");// 最低登记额度
		BigDecimal max_rg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("max_rg_money"), new BigDecimal(0), 2, "10000");// 最高登记额度
		Integer q_cust_type = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_cust_type")), new Integer(0));
		Integer book_code = Utility.parseInt(pageContext.getRequest().getParameter("book_code"), new Integer(1));
		Integer input_man = Utility.parseInt(pageContext.getRequest().getParameter("input_man"), new Integer(0));
		Integer q_group_id = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_group_id")),new Integer(0));
		Integer q_class_detail_id = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_class_detail_id")),new Integer(0));
		Integer q_channel_id = Utility.parseInt(Utility.trimNull(pageContext.getRequest().getParameter("q_channel_id")),new Integer(0));

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
		vo.setChannel_id(q_channel_id);
		
		List rslist = contract.queryPurchanseContract_crm(vo);
		
		String titleName[] = { "合同编号", "产品名称", "客户编号", "客户名称","客户群组","渠道名称", "认购金额",
				"签署日期", "状态", "认购方式","客户经理"};

		String fieldName[] = { "CONTRACT_SUB_BH", "PRODUCT_NAME", "CUST_NO",
				"CUST_NAME","CUST_ID","CHANNEL_NAME","RG_MONEY", "QS_DATE","HT_STATUS_NAME", "PRE_FLAG" ,"SERVICE_MAN_NAME"
		};
		String[] fieldType = {"1","1","1","1","1","1","2","4","1","1","1"};
		// 1为字符串型,2为BigDecimal 3 Integer
		//导出功能
		try{
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("第一页", 0);
			
			exportExcel_subscribe(ws, "客户认购信息", titleName, fieldName, fieldType, rslist);
			
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("下载文件失败:" + e.getMessage());
		}
		
	}
	
	public void exportExcel_subscribe(WritableSheet ws, String excelTitle,String[] titleName, String[] fieldName, String[] fieldType,List rslist)throws Exception{
		BigDecimal rg_money_total = new BigDecimal(0);	

		try {			
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

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,format);
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
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,formatTop);
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
					rg_money_total = rg_money_total.add(new BigDecimal(
							Utility.trimNull(map.get("RG_MONEY"))));

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
							if (sfieldName.equals("PRE_FLAG")) {
								jxl.write.Label labelValue0 = new jxl.write.Label(
										k,
										j,
										Utility.trimNull(Argument.getPreflag(Utility.parseInt(Utility.trimNull(map.get("PRE_FLAG")),new Integer(0)))),formatLeft);
								ws.addCell(labelValue0);
							} 
							else if(sfieldName.equals("CUST_ID")){
								Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")), new Integer(0));
								String custGroup = Argument.getCustGroups(cust_id);
								jxl.write.Label labelValue0 = new jxl.write.Label(
										k,
										j,
										Utility.trimNull(custGroup),formatLeft);
								ws.addCell(labelValue0);								
							}
							else {
								String fieldValue = Utility.trimNull(map.get(sfieldName));
								jxl.write.Label labelValue0 = new jxl.write.Label(k, j,fieldValue, formatLeft);
								ws.addCell(labelValue0);
							}
							
							break;
						}
						case 2: // BigDecimal数值类型，保留2位小数-居右
						{
							BigDecimal fiedvalue = Utility.parseDecimal(Utility.trimNull(map.get(sfieldName)), new BigDecimal("0"));

							jxl.write.Label labe4NF = new jxl.write.Label(k,j,
									Utility.trimNull(Format.formatMoney(fiedvalue)),
									formatRight);
							ws.addCell(labe4NF);
							break;
						}
						case 3: // Integer-居右
						{
							Integer fieldValue = Utility.parseInt(Utility.trimNull(map.get(sfieldName)), new Integer(0));
							String finalValue = "";
							if (fieldValue != null)
								finalValue = fieldValue.toString();
	
							jxl.write.Label labelValue0 = new jxl.write.Label(k, j,finalValue, formatRight);
	
							ws.addCell(labelValue0);
							break;
						}
						case 4:{ // Integer to Date(yyyy-mm-dd)-居中
							Integer fieldValue = Utility.parseInt(Utility.trimNull(map.get(sfieldName)), new Integer(0));
							String finalValue = "";
							
							if (fieldValue != null) {
								finalValue = Format.formatDateLine(fieldValue);
							}
							
							jxl.write.Label labelValue0 = new jxl.write.Label(k, j,finalValue, formatCenter);	
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
						default:break;
					}
				}				
				j++;
			}
			
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
			
		} catch (Exception e) {
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		} 
	}
	
	
	/**
	 * 产品代理销售模板下载
	 * @throws Exception
	 */
	public void exportProductTemp(Integer input_bookCode, Integer input_operatorCode) throws Exception{
		String titleName[] = {"项目名称","产品期数","合同编号","合同签订日期","缴款日期","合同金额","认购费扣缴方式",
					"委托人","委托人类型","委托人邮编","委托人地址","委托人备用邮编","委托人备用地址","委托人证件名称",
					"委托人证件编号","委托人法人代表","委托人联系方式","委托人固定电话","委托人手机","委托人电子邮件",
					"开户银行名称","支行名称","银行账号","缴款方式","受益人","受益人类型","受益优先级别","收益级别","受益人联系方式",
					"受益人证件名称","受益人证件编号","受益人地址","受益人邮编","受益人法人代表","受益人固定电话","受益人手机",
					"受益人电子邮件","新合同编号","渠道类别","渠道名称","渠道备注","省","市","客户经理","渠道合作方式"};
		String [] items = pageContext.getRequest().getParameterValues("product_idv");

		try {
			OutputStream outStr = getResponseStream("代理销售导入模板.xls");
			exportProductTempInfo(outStr, titleName, items, input_bookCode, input_operatorCode);
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("下载文件失败:" + e.getMessage());
		}
	}
	
	public void exportProductTempInfo(OutputStream outStr, String [] titleName, String [] value, Integer input_bookCode, Integer input_operatorCode)throws Exception{
		try {
			Utility.debugln("outputstream is " + outStr.toString());
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("第一页", 0);
			WritableCellFeatures wcf = null;
			//设置列宽
			for (int y = 0; y < titleName.length; y++) {
				if(y == 0)
					ws.setColumnView(y, 70);
				else
					ws.setColumnView(y, 25);
			}

			// 2、表头的格式
			WritableFont fontTop = new WritableFont(WritableFont
					.createFont("宋体"), 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat formatTop = new WritableCellFormat(fontTop);
			formatTop.setAlignment(Alignment.CENTRE);
			formatTop.setVerticalAlignment(VerticalAlignment.CENTRE);
			formatTop.setBackground(jxl.format.Colour.AQUA);
			ws.setRowView(0, 700);
			formatTop.setBorder(Border.ALL, BorderLineStyle.THIN);
			int i = 0;
			for (; i < titleName.length; i++) {
				String sName = titleName[i];
				jxl.write.Label labelC1 = new jxl.write.Label(i, 0, sName,
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
			
			ProductLocal local = EJBFactory.getProduct();
			ProductVO vo = new ProductVO();
			vo.setBook_code(input_bookCode);
			vo.setInput_man(input_operatorCode);
			Integer product_id = new Integer(0);
			List markList = null;
			Map markMap = null;
			List subList = null;
			Map subMap = null;
			List channelTypeList = new ArrayList();
			List channelNameList = new ArrayList();
			List subProductList = new ArrayList();
			
			List ChannelCoopList = new ArrayList();
			List gainProvList = new ArrayList();
			List gainLeveList = new ArrayList();
			GainLevelLocal gainLeveLocal = EJBFactory.getGainLevel();
			GainLevelLocal gainProvLocal = EJBFactory.getGainLevel();
			DictparamLocal dictLocal = EJBFactory.getDictparam();
			DictparamVO dictVo = new DictparamVO();
			dictVo.setType_id(new Integer(5502));//渠道合作方式
			List dictList = dictLocal.listDictparamAll(dictVo);
			//渠道合作方式
			ChannelCoopList.clear();
			ChannelCoopList.add("请选择");
			if(ChannelCoopList!=null)
			for(int k=0;k<dictList.size();k++) {
				Map map = (Map)dictList.get(k);				
				ChannelCoopList.add(Utility.trimNull(map.get("TYPE_CONTENT")));
			}
			
			for (int j = 0; j < value.length; j++) {
				product_id = Utility.parseInt(value[j], new Integer(0));
				if(product_id.intValue() != 0){
					vo.setProduct_id(product_id);
					List list = local.listCrmProduct(vo);
					Map map = null;
					if(list != null && list.size() != 0){
						map = (Map)list.get(0);
						ws.setRowView((j+1), 350); //行高
						//产品销售渠道信息
						channelTypeList.clear();
						channelNameList.clear();
						channelTypeList.add("请选择");
						channelNameList.add("请选择");
						markList = local.queryMarketTrench(vo);
						for (int k = 0; k < markList.size(); k++) {
							markMap = (Map)markList.get(k);
							if(!"".equals(Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME"))))
								channelTypeList.add(Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME")));
							if(!"".equals(Utility.trimNull(markMap.get("CHANNEL_NAME"))))
								channelNameList.add(Utility.trimNull(markMap.get("CHANNEL_NAME")));
						}
						//查询子产品
						subProductList.clear();
						subProductList.add("请选择");
						subList = local.listSubProduct(vo);
						if(subList != null && subList.size() != 0){
							for(int t=0; t<subList.size(); t++){
								subMap = (Map)subList.get(t);
								subProductList.add(Utility.parseInt(Utility.trimNull(subMap.get("LIST_ID")),new Integer(0)));
							}
						}
						jxl.write.Label labelValue0 = new jxl.write.Label(0, (j+1), Utility.trimNull(map.get("PRODUCT_NAME")), formatLeft); 	ws.addCell(labelValue0);
						jxl.write.Label labelValue1 = new jxl.write.Label(1, (j+1), "请选择", formatLeft);
						wcf = new WritableCellFeatures();
						wcf.setDataValidationList(subProductList);
						labelValue1.setCellFeatures(wcf);
						ws.addCell(labelValue1);
						jxl.write.Label labelValue2 = new jxl.write.Label(2, (j+1), "", formatLeft);    ws.addCell(labelValue2);
						jxl.write.Label labelValue3 = new jxl.write.Label(3, (j+1), "", formatLeft);    ws.addCell(labelValue3);
						jxl.write.Label labelValue4 = new jxl.write.Label(4, (j+1), "", formatLeft);    ws.addCell(labelValue4);
						jxl.write.Label labelValue5 = new jxl.write.Label(5, (j+1), "", formatLeft);    ws.addCell(labelValue5);
						jxl.write.Label labelValue6 = new jxl.write.Label(6, (j+1), "", formatLeft);    ws.addCell(labelValue6);
						jxl.write.Label labelValue7 = new jxl.write.Label(7, (j+1), "", formatLeft);    ws.addCell(labelValue7);
						jxl.write.Label labelValue8 = new jxl.write.Label(8, (j+1), "", formatLeft);    ws.addCell(labelValue8);
						jxl.write.Label labelValue9 = new jxl.write.Label(9, (j+1), "", formatLeft);    ws.addCell(labelValue9);
						jxl.write.Label labelValue10 = new jxl.write.Label(10, (j+1), "", formatLeft);    ws.addCell(labelValue10);
						jxl.write.Label labelValue11 = new jxl.write.Label(11, (j+1), "", formatLeft);    ws.addCell(labelValue11);
						jxl.write.Label labelValue12 = new jxl.write.Label(12, (j+1), "", formatLeft);    ws.addCell(labelValue12);
						jxl.write.Label labelValue13 = new jxl.write.Label(13, (j+1), "", formatLeft);    ws.addCell(labelValue13);
						jxl.write.Label labelValue14 = new jxl.write.Label(14, (j+1), "", formatLeft);    ws.addCell(labelValue14);
						jxl.write.Label labelValue15 = new jxl.write.Label(15, (j+1), "", formatLeft);    ws.addCell(labelValue15);
						jxl.write.Label labelValue16 = new jxl.write.Label(16, (j+1), "", formatLeft);    ws.addCell(labelValue16);
						jxl.write.Label labelValue17 = new jxl.write.Label(17, (j+1), "", formatLeft);    ws.addCell(labelValue17);
						jxl.write.Label labelValue18 = new jxl.write.Label(18, (j+1), "", formatLeft);    ws.addCell(labelValue18);
						jxl.write.Label labelValue19 = new jxl.write.Label(19, (j+1), "", formatLeft);    ws.addCell(labelValue19);
						jxl.write.Label labelValue20 = new jxl.write.Label(20, (j+1), "", formatLeft);    ws.addCell(labelValue20);
						jxl.write.Label labelValue21 = new jxl.write.Label(21, (j+1), "", formatLeft);    ws.addCell(labelValue21);
						jxl.write.Label labelValue22 = new jxl.write.Label(22, (j+1), "", formatLeft);    ws.addCell(labelValue22);
						jxl.write.Label labelValue23 = new jxl.write.Label(23, (j+1), "", formatLeft);    ws.addCell(labelValue23);
						jxl.write.Label labelValue24 = new jxl.write.Label(24, (j+1), "", formatLeft);    ws.addCell(labelValue24);
						jxl.write.Label labelValue25 = new jxl.write.Label(25, (j+1), "", formatLeft);    ws.addCell(labelValue25);
						
						gainProvLocal.setProduct_id(product_id);
						gainProvLocal.queryLevelFlag();
						gainProvList.clear();
						gainProvList.add("请选择");
						if(gainProvList!=null && gainProvLocal.getRows()!=0) {
							while(gainProvLocal.getNextProvFlag()) {
								gainProvList.add(gainProvLocal.getProv_flag_name());
							}
						}
						//受益优先级
						jxl.write.Label labelValue26 = new jxl.write.Label(26, (j+1), "请选择", formatLeft);  
						wcf = new WritableCellFeatures();
						wcf.setDataValidationList(gainProvList);
						labelValue26.setCellFeatures(wcf);
						ws.addCell(labelValue26);
						
						
						//收益级别
						gainLeveLocal.setProduct_id(product_id);
						gainLeveLocal.query();
						gainLeveList.clear();
						gainLeveList.add("请选择");
						while(gainLeveLocal.getNextLevel()) {
							gainLeveList.add(gainLeveLocal.getProv_level_name());
						}
						jxl.write.Label labelValue27 = new jxl.write.Label(27, (j+1), "请选择", formatLeft);    
						wcf = new WritableCellFeatures();
						wcf.setDataValidationList(gainLeveList);
						labelValue27.setCellFeatures(wcf);
						ws.addCell(labelValue27);
						
						jxl.write.Label labelValue28 = new jxl.write.Label(28, (j+1), "", formatLeft);    ws.addCell(labelValue28);
						jxl.write.Label labelValue29 = new jxl.write.Label(29, (j+1), "", formatLeft);    ws.addCell(labelValue29);
						jxl.write.Label labelValue30 = new jxl.write.Label(30, (j+1), "", formatLeft);    ws.addCell(labelValue30);
						jxl.write.Label labelValue31 = new jxl.write.Label(31, (j+1), "", formatLeft);    ws.addCell(labelValue31);
						jxl.write.Label labelValue32 = new jxl.write.Label(32, (j+1), "", formatLeft);    ws.addCell(labelValue32);
						jxl.write.Label labelValue33 = new jxl.write.Label(33, (j+1), "", formatLeft);    ws.addCell(labelValue33);
						jxl.write.Label labelValue34 = new jxl.write.Label(34, (j+1), "", formatLeft);    ws.addCell(labelValue34);
						jxl.write.Label labelValue35 = new jxl.write.Label(35, (j+1), "", formatLeft);    ws.addCell(labelValue35);
						jxl.write.Label labelValue36 = new jxl.write.Label(36, (j+1), "", formatLeft);    ws.addCell(labelValue36);
						jxl.write.Label labelValue37 = new jxl.write.Label(37, (j+1), "", formatLeft);    ws.addCell(labelValue37);
						
						jxl.write.Label labelValue38 = new jxl.write.Label(38, (j+1), "请选择", formatLeft);
						wcf = new WritableCellFeatures();
						wcf.setDataValidationList(channelTypeList);
						labelValue38.setCellFeatures(wcf);
						ws.addCell(labelValue38);
						
						jxl.write.Label labelValue39 = new jxl.write.Label(39, (j+1), "请选择", formatLeft);
						wcf = new WritableCellFeatures();
						wcf.setDataValidationList(channelNameList);
						labelValue39.setCellFeatures(wcf);
						ws.addCell(labelValue39);
						
						jxl.write.Label labelValue40 = new jxl.write.Label(40, (j+1), "", formatLeft);    ws.addCell(labelValue40);
						jxl.write.Label labelValue41 = new jxl.write.Label(41, (j+1), "", formatLeft);    ws.addCell(labelValue41);
						jxl.write.Label labelValue42 = new jxl.write.Label(42, (j+1), "", formatLeft);    ws.addCell(labelValue42);
						jxl.write.Label labelValue43 = new jxl.write.Label(43, (j+1), "", formatLeft);    ws.addCell(labelValue43);
						
						jxl.write.Label labelValue44 = new jxl.write.Label(44, (j+1), "请选择", formatLeft);   
						wcf = new WritableCellFeatures();
						wcf.setDataValidationList(ChannelCoopList);
						labelValue44.setCellFeatures(wcf);
						ws.addCell(labelValue44);				
					}
				}
			}
			
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			Utility.debugln("生成Excel文件失败,错误代码:" + e.getMessage());
			throw new Exception("生成Excel文件失败,错误代码:" + e.getMessage());
		}
	}
	
	public boolean readExcelPreCustContract(String objpath,
			Integer pre_date,Integer input_man) throws Exception {

		String filename = objpath;
		String tempValue = "";
		String sfile_name = "AppointInfo"+pre_date+".dat";
		filename = objpath + "\\" + sfile_name;
		System.out.println("--filename:"+filename);
		File f = new File(filename);
		if (!f.exists())
			throw new BusiException("文件不存在！有可能该文件已被删除。");
		int j= 0;
		try {
			Connection conn = CrmDBManager.getConnection();
			Statement stmt = conn.createStatement();
			PreContractLocal local = EJBFactory.getPreContract();
			BufferedReader read = null;
			read = new BufferedReader(new FileReader(f));
			while((tempValue = read.readLine())!=null){
				j++;
				
				String[] v = Utility.splitString_01(tempValue,"#");
				PreContractVO vo = new PreContractVO();
				
				if(v.length==11){
					vo.setCustomer_id(Utility.trimNull(v[0]));
					vo.setCust_name(Utility.trimNull(v[1]));
					vo.setSex_name(Utility.trimNull(v[2]));
					vo.setMobile(Utility.trimNull(v[3]));
					vo.setPre_money(Utility.parseDecimal(v[4],new BigDecimal(0)));
					vo.setPre_date(Utility.parseInt(v[5],pre_date));
					vo.setProduct_name(Utility.trimNull(v[6]));
					vo.setProduct_code(Utility.trimNull(v[7]));
					vo.setAddress(Utility.trimNull(v[8]));
					vo.setEmail(Utility.trimNull(v[9]));
				}
				vo.setInput_man(input_man);
				vo.setFile_name(sfile_name);
				local.importPreCustCon(vo);//导入每一笔预约数据
			}
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();

		} catch (Exception e) {
			throw new Exception("导数据失败,错误信息:" + e.getMessage());
		}
		return true;
	}
	
	
	/**
	 * 万向-认购合同\申购合同\赎回\账户变动\受益情况变动\受益转让\ 数据导出
	 * @throws Exception
	 */
	public void downloadExcel_WX(String fileName) throws Exception{
		ContractVO vo = new ContractVO();
		ContractLocal contract = EJBFactory.getContract();
		
		List rslist1 = contract.queryAllContractMessage(vo,new Integer(1));
		List rslist2 = contract.queryAllContractMessage(vo,new Integer(2));
		List rslist3 = contract.queryAllContractMessage(vo,new Integer(3));
		List rslist4 = contract.queryAllContractMessage(vo,new Integer(4));
		List rslist5 = contract.queryAllContractMessage(vo,new Integer(5));
		
		//认购
		String titleName[] = { "合同编号", "客户名称", "客户类型","证件类型","证件号码", "机构EAST类型",
				"自然人EAST类型", "联系电话", "联系地址","手机号码","到账金额","到账日期","受益级别",
				"汇款银行","汇款银行支行","汇款账号","汇款账户户名","收益分配方式","转入其它产品代码",
				"缴款方式","缴款金额","客户经理"};

		String fieldName[] = { "CONTRACT_SUB_BH", "CUST_NAME", "CUST_TYPE_NAME","CARD_TYPE_NAME","CARD_ID","机构EAST类型",
				"自然人EAST类型", "CUST_TEL","POST_ADDRESS", "MOBILE" ,"RG_MONEY","JK_DATE",
				"PROV_FLAG_NAME","BANK_NAME","BANK_SUB_NAME","BANK_ACCT","GAIN_ACCT","BONUS_FLAG_NAME","转入其它产品代码",
				"JK_TYPE_NAME","TO_AMOUNT","SERVICE_MAN"
		};
		// 1为字符串型,2为BigDecimal 3 Integer
		String[] fieldType = {"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"};

		//申购 
		String titleName2[] = { "产品编号", "客户编号", "申购金额","申购份额", "开放日","缴款日期",
				"合同编号", "受益开户行", "支行","账号","账户名","申购费用"};

		String fieldName2[] = { "PRODUCT_CODE", "CUST_NO", "SG_MONEY","TO_AMOUNT","SQ_DATE","JK_DATE",
				"CONTRACT_SUB_BH", "BANK_NAME","BANK_SUB_NAME", "BANK_ACCT" ,"CUST_ACCT_NAME","MARKET_MONEY"
		};
		String[] fieldType2 = {"1","1","2","2","4","4","1","1","1","1","1","1"};
		
		//赎回
		String titleName3[] = { "产品编号","合同编号","客户编号","赎回份额",
				"赎回金额","开放日","购回申请日","赎回费用","巨额赎回"};

		String fieldName3[] = { "PRODUCT_CODE","CONTRACT_SUB_BH", "CUST_NO", "REDEEM_AMOUNT",
				"REDEEM_MONEY","TRANS_DATE","SQ_DATE", "FEE","巨额赎回"};
		String[] fieldType3 = {"1","1","1","2","2","4","4","4","1"};
		
		//导出功能
		try{
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("认购合同", 0);			
			exportExcel_subscribe(ws, "认购合同信息导出", titleName, fieldName, fieldType, rslist1);
			
			WritableWorkbook wwb2 = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws2 = wwb.createSheet("申购合同", 1);			
			exportExcel_subscribe(ws2, "申购合同信息导出", titleName2, fieldName2, fieldType2, rslist2);
			
			WritableWorkbook wwb3 = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws3 = wwb.createSheet("赎回", 2);			
			exportExcel_subscribe(ws3, "赎回信息导出", titleName3, fieldName3, fieldType3, rslist3);
			
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("下载文件失败:" + e.getMessage());
		}
		
	}
	
	/**
	 * 上传产品推介材料文件
	 */
	public boolean uploadProductIntroMaterial(Integer	product_id, Integer preproduct_id, Integer input_man) throws Exception {
		if (!hasParse)
			parseRequest();
		
		String strFolder = Utility.replaceAll(Argument.getDictContent("8003")," ",""); 
		if("".equals(strFolder)) 
			strFolder = "c:\\uploadfiles\\";
		
		strFolder = Utility.replaceAll(strFolder,"\\","//");		
		
		if (!insureFolder(strFolder))
			return false;

		if (!insureFolder(strFolder+"feasstudy//"))
			return false;
		
		if (!insureFolder(strFolder+"feasstudy_easy//"))
			return false;
		
		if (!insureFolder(strFolder+"study_voice//"))
			return false;
		
		try {			
			int fileCount = smartUpload.getFiles().getCount();
			/*for(int i= 0;i <  fileCount;i++){
				int iFileSize = smartUpload.getFiles().getFile(i).getSize();		
				if (iFileSize > 20* 1024 * 1024)
					throw new BusiException("文件大小不能超过20M！");
			}*/
							
			smartUpload.save(strFolder,com.jspsmart.upload.SmartUpload.SAVE_PHYSICAL);		
					
			String feasstudy = null;
			String feasstudy_easy = null;
			String study_voice = null;
			
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
			for (int i= 0;i < fileCount;i++){	
				com.jspsmart.upload.File file = smartUpload.getFiles().getFile(i);
						
				int iFileSize = file.getSize();
				if (iFileSize>0) {					
					String oldFileName = file.getFileName();
										
					if ("feasstudy".equals(file.getFieldName())) {	
						File f = new File(strFolder+"feasstudy//");		
						if (!f.exists())
							if (! f.mkdirs()) return false;	
							
						int numInde = f.list().length + 1;
						String newFileName = oldFileName + "~"+ df.format(new Date())+ "-" + numInde+ "." + file.getFileExt();
						// 重命名
						
						renamefile(strFolder + oldFileName, "feasstudy//"+newFileName);
						newFileName = strFolder + "feasstudy//"+newFileName;
							
						feasstudy = newFileName;
						
					} else if ("feasstudy_easy".equals(file.getFieldName())) {
						File f = new File(strFolder+"feasstudy_easy//");		
						if (!f.exists())
							if (! f.mkdirs()) return false;	
						int numInde = f.list().length + 1;
						String newFileName = oldFileName + "~"+ df.format(new Date())+ "-" + numInde+ "." + file.getFileExt();	
						
						renamefile(strFolder + oldFileName, "feasstudy_easy//"+newFileName);
						newFileName = strFolder + "feasstudy_easy//"+newFileName;
						
						feasstudy_easy = newFileName;
						
					} else if ("study_voice".equals(file.getFieldName())) {
						File f = new File(strFolder+"study_voice//");		
						if (!f.exists())
							if (! f.mkdirs()) return false;	
						int numInde = f.list().length + 1;
						String newFileName = oldFileName + "~"+ df.format(new Date())+ "-" + numInde+ "." + file.getFileExt();	
						
						renamefile(strFolder + oldFileName, "study_voice//"+newFileName);
						newFileName = strFolder + "study_voice//"+newFileName;
						
						study_voice = newFileName;					
					}
				}
			}
			
			ProductInfoReposLocal prod_info = EJBFactory.getProductInfoRepos();
			ProductVO vo = new ProductVO();
			vo.setProduct_id(product_id);
			vo.setPreproduct_id(preproduct_id);
			vo.setFeasstudy(feasstudy);
			vo.setFeasstudy_easy(feasstudy_easy);
			vo.setStudy_voice(study_voice);
			vo.setInput_man(input_man);
			prod_info.editIntroMaterial(vo);
			prod_info.remove();
			return true;
			
		} catch (Exception e) {
			throw new BusiException("文件上传失败: " + e.getMessage());
		}
	}
}
