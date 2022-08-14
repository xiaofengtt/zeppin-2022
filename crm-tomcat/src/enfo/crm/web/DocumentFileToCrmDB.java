package enfo.crm.web;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.intrust.AttachmentToCrmLocal;
import enfo.crm.intrust.ContractLocal;
import enfo.crm.intrust.PreContractLocal;
import enfo.crm.tools.Argument;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Format;
import enfo.crm.tools.Utility;
import enfo.crm.vo.AttachmentVO;
import enfo.crm.vo.ContractVO;
import enfo.crm.vo.PreContractVO;
/**
 * @author enfo.hesl
 * ���ڲ���λ��EFCRM���е��ļ� 2011.4.18
 */
public class DocumentFileToCrmDB {
	private String file_name = "";
	private boolean hasParse = false;
	private final long MAXFILESIZE = 50000000;
	private PageContext pageContext;
	private com.jspsmart.upload.Request request;
	private final String SEPARATOR = java.io.File.separator;
	public com.jspsmart.upload.SmartUpload smartUpload = new com.jspsmart.upload.SmartUpload();
	private String strFolder = "C:\\Temp";
	private String toFolder;
	
	public DocumentFileToCrmDB() {
	}

	public DocumentFileToCrmDB(PageContext in_pageContext) {
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
		} catch (Exception e) {
			request = null;
			System.out.println("��parseRequese�г����쳣");
			throw new BusiException("�ļ��ϴ�ʧ��!");
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * �ϴ��������������Ϣ���浽���ݿ��� hesl 2011.4.19
	 */
	public boolean uploadAttchment(Integer df_table_id, String df_table_name,
			Integer df_serial_no,String description,Integer input_man) throws Exception {
		if (!hasParse)
			parseRequest();
		String strFolder = Utility.replaceAll(Argument.getDictContentIntrust("800101")," ",""); 
		if("".equals(strFolder)) 
			strFolder = "c:\\uploadfiles\\";
		
		strFolder = Utility.replaceAll(strFolder,"\\","//");
		
		if (!insureFolder(strFolder))
			return false;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
			int fileCount = smartUpload.getFiles().getCount();
			for(int i= 0;i <fileCount;i++){
				int iFileSize = smartUpload.getFiles().getFile(i).getSize();
				
				if (iFileSize == 0)
					return false;
				if (iFileSize > 10* 1024 * 1024)
					throw new BusiException("�ļ���С���ܳ���10M��");
			}
			File f = new File(strFolder);
			if (!f.exists())
				f.mkdir();
			int numInde = f.list().length;
				
			smartUpload.save(strFolder,com.jspsmart.upload.SmartUpload.SAVE_PHYSICAL);
			AttachmentToCrmLocal attachment = EJBFactory.getAttachmentToCrm();
			for(int i= 0;i < fileCount;i++){	
				numInde++;
				String newFileName = df.format(new Date())+ "-" + numInde+ "."
						+ smartUpload.getFiles().getFile(i).getFileExt();
				String oldFileName = smartUpload.getFiles().getFile(i).getFileName();
			
				//������
				renamefile(strFolder + oldFileName, newFileName);
				newFileName = strFolder + newFileName;
				int iFileSize = smartUpload.getFiles().getFile(i).getSize();
				//�����¼
				AttachmentVO vo = new AttachmentVO();
				if (true) {
					
					vo.setDf_talbe_id(df_table_id);
					vo.setDf_table_name(df_table_name);
					vo.setDf_serial_no(df_serial_no);
					vo.setSave_name(newFileName);
					vo.setOrigin_name(oldFileName);
					vo.setFile_size(new Integer(iFileSize));
					vo.setDescription(description);
					vo.setInput_man(input_man);
					attachment.append(vo);
				}
				attachment.remove();
			}
			return true;
		} catch (Exception e) {
			throw new BusiException("�ļ��ϴ�ʧ��: " + e.getMessage());
		}
	}
	
	/**
	 * �ϴ��������������Ϣ���浽���ݿ��� hesl 2011.4.19
	 */
	public boolean uploadAttchment_crm(Integer df_table_id, String df_table_name,
			Integer df_serial_no,String description,Integer input_man) throws Exception {
		if (!hasParse)
			parseRequest();
		String strFolder = Utility.replaceAll(Argument.getDictContent("800102")," ",""); 
		if("".equals(strFolder)) 
			strFolder = "c:\\uploadfiles\\";
		
		strFolder = Utility.replaceAll(strFolder,"\\","//");
		
		if (!insureFolder(strFolder))
			return false;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
			int fileCount = smartUpload.getFiles().getCount();
			for(int i= 0;i <fileCount;i++){
				int iFileSize = smartUpload.getFiles().getFile(i).getSize();
				
				if (iFileSize == 0)
					return false;
				if (iFileSize > 10* 1024 * 1024)
					throw new BusiException("�ļ���С���ܳ���10M��");
			}
			File f = new File(strFolder);
			if (!f.exists())
				f.mkdir();
			int numInde = f.list().length;
				
			smartUpload.save(strFolder,com.jspsmart.upload.SmartUpload.SAVE_PHYSICAL);
			AttachmentToCrmLocal attachment = EJBFactory.getAttachmentToCrm();
			for(int i= 0;i < fileCount;i++){	
				numInde++;
				String newFileName = df.format(new Date())+ "-" + numInde+ "."
						+ smartUpload.getFiles().getFile(i).getFileExt();
				String oldFileName = smartUpload.getFiles().getFile(i).getFileName();
			
				//������
				renamefile(strFolder + oldFileName, newFileName);
				newFileName = strFolder + newFileName;
				int iFileSize = smartUpload.getFiles().getFile(i).getSize();
				//�����¼
				AttachmentVO vo = new AttachmentVO();
				if (true) {
					
					vo.setDf_talbe_id(df_table_id);
					vo.setDf_table_name(df_table_name);
					vo.setDf_serial_no(df_serial_no);
					vo.setSave_name(newFileName);
					vo.setOrigin_name(oldFileName);
					vo.setFile_size(new Integer(iFileSize));
					vo.setDescription(description);
					vo.setInput_man(input_man);
					attachment.append(vo);
				}
				attachment.remove();
			}
			return true;
		} catch (Exception e) {
			throw new BusiException("�ļ��ϴ�ʧ��: " + e.getMessage());
		}
	}
	
	/**
	 * ADD BY TAOCHEN 20100819 �õ�����Աǩ��ͼƬ
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
			// �½��ļ��� ���ڱ���ͼƬ
			java.io.File myFilePath = new java.io.File(folderPath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
			// �����
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
				throw new BusiException("��ȡ�ͻ����֤ͼƬʧ��:" + e.getMessage());
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
//		String filen = "";
//		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());
		if (!file.exists())
			throw new BusiException("�ļ������ڣ��п��ܸ��ļ��ѱ�ɾ����");
		DataInputStream dis = null;
		OutputStream os = null;
		try {
			response.setContentType("application/octet-stream");
			response.addHeader("Content-disposition",Encode("attachment;filename=" + name ));
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
//			out.close();
		}
	}
	
	//�ļ�����
	public void downloadZipFile(PageContext pageContext,String filepath,String filename) throws Exception {
		String filepathname = Utility.trimNull(filepath);
		File zipFile = new File(filepathname);
		if(!zipFile.exists()){
			throw new Exception("�ļ������ڣ�����ʧ�ܣ�");
		}
		
		JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse)(pageContext.getResponse());
		out.clear();
		out = pageContext.pushBody();
		
		response.reset();
		response.setHeader("Content-disposition",Encode("attachment; filename=" + filename));
		response.setContentType("APPLICATION/octet-stream");
		
		OutputStream outputstream = response.getOutputStream();
		
		InputStream inStream=new FileInputStream(filepathname);//�ļ��Ĵ��·��
		byte[] b = new byte[100]; 
		int len; 
		while((len=inStream.read(b)) >0) 
			outputstream.write(b,0,len);  
		inStream.close();
	}

	
	/**
	 * Ԥ�Ǽ���Ϣ����
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
		BigDecimal min_reg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("min_reg_money"), new BigDecimal(0),2, "10000");// ��͵ǼǶ��
		BigDecimal max_reg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("max_reg_money"), new BigDecimal(0),2, "10000");// ��ߵǼǶ��
		String q_customer_cust_source = Utility.trimNull(pageContext.getRequest().getParameter("q_customer_cust_source"));// �ͻ���Դ
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
		
		String titleName[] = { "�ͻ����", "�ͻ�����", "�ͻ����", "��ͥ�绰", "�칫�绰",
				"�ֻ�", "�ֻ�2", "�Ǽǽ��", "�Ǽ�����", "�ͻ���Դ", "Ԥ��Ͷ��" };

		String fieldName[] = { "CUST_NO", "CUST_NAME", "CUST_TYPE_NAME",
				"CUST_TEL", "O_TEL", "MOBILE", "BP", "REG_MONEY",
				"REG_DATE", "CUST_SOURCE_NAME", "INVEST_TYPE_NAME" };

		String fieldType[] = { "1", "1", "1", "1", "1", "1", "1", "2", "3",
				"1", "1" };
		
		//��������
		try{
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			
			exportExcel_reginfo(ws, "�ͻ�Ԥ�Ǽ���Ϣ", titleName, fieldName, fieldType, rslist);
			
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("�����ļ�ʧ��:" + e.getMessage());
		}
	}
	
	public void exportExcel_reginfo(WritableSheet ws, String excelTitle,String[] titleName, String[] fieldName, String[] fieldType,List rslist) throws Exception{
		BigDecimal reg_money_total = new BigDecimal(0);// �Ǽǽ��ϼ�
		try{
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
		} 
		catch (Exception e) {
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		} 
	}
	/**
	 * ԤԼ��Ϣ����
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
		BigDecimal min_reg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("min_reg_money"), new BigDecimal(0), 2, "10000");// ��͵ǼǶ��
		BigDecimal max_reg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("max_reg_money"), new BigDecimal(0), 2, "10000");// ��ߵǼǶ��
		String q_customer_cust_source = Utility.trimNull(pageContext.getRequest().getParameter("q_customer_cust_source"));// �ͻ���Դ
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
		
		String titleName[] = { "�ͻ�����", "�ͻ����", "ԤԼ��", "ԤԼ����", "��ת�Ϲ�����",
				"���", "Ԥ������������", "Ԥ������������", "��ϵ��", "��ϵ�绰", "�ͻ���Դ", "ԤԼ����",
				"Ԥ���Ϲ�����","�ͻ�Ⱥ��","��Ч����", "״̬", "��ע" };
		String fieldName[] = { "CUST_NAME", "CUST_TYPE_NAME", "PRE_CODE",
				"PRE_NUM", "RG_NUM", "PRE_MONEY", "EXP_RATE1", "EXP_RATE2",
				"LINK_MAN", "MOBILE", "CUST_SOURCE_NAME", "PRE_DATE",
				"EXP_REG_DATE","CUST_ID","VALID_DAYS", "PRE_STATUS_NAME", "SUMMARY" };
		String[] fieldType = { "1", "1", "1", 
							"3","3","2","2","2",
							"1","1","1","4",
							"4","1","3","1","1" };
		
		//��������
		try{
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			
			exportExcel_presell(ws, "�ͻ�ԤԼ��Ϣ", titleName, fieldName, fieldType, rslist);
			
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("�����ļ�ʧ��:" + e.getMessage());
		}
	}
	
	public void exportExcel_presell(WritableSheet ws, String excelTitle,String[] titleName, String[] fieldName, String[] fieldType,List rslist) throws Exception{
		Integer rg_num_total = new Integer(0); //���Ϲ�����
		Integer pre_num_total = new Integer(0);//ԤԼ����
		BigDecimal pre_money_total = new BigDecimal(0);//ԤԼ���
		
		try{
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

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,format);
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
					default:break;
					}
				}
				j++;
			}

			for (int y = 0; y < fieldType.length; y++) {
				if (y == 0) {
					jxl.write.Label labeSum0 = new jxl.write.Label(y, j,
							Utility.trimNull("�ϼ�" + rslist.size() + "��"),
							formatLeft);
					ws.addCell(labeSum0);
				} else {
					if (titleName[y].equals("���")) {
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format.formatMoney(pre_money_total)),formatRight);
						ws.addCell(labeSum3);
					} 
					else if(titleName[y].equals("��ת�Ϲ�����")){
						jxl.write.Label labeSum3 = new jxl.write.Label(y, j,
								Utility.trimNull(Format.formatMoney(rg_num_total)),formatRight);
						ws.addCell(labeSum3);
					}
					else if(titleName[y].equals("ԤԼ����")){
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
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		} 
	}
	/**
	 * �Ϲ�����
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
		BigDecimal min_rg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("min_rg_money"), new BigDecimal(0), 2, "10000");// ��͵ǼǶ��
		BigDecimal max_rg_money = Utility.parseDecimal(pageContext.getRequest().getParameter("max_rg_money"), new BigDecimal(0), 2, "10000");// ��ߵǼǶ��
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
		
		String titleName[] = { "��ͬ���", "��Ʒ����", "�ͻ����", "�ͻ�����","�ͻ�Ⱥ��","��������", "�Ϲ����",
				"ǩ������", "״̬", "�Ϲ���ʽ"};

		String fieldName[] = { "CONTRACT_SUB_BH", "PRODUCT_NAME", "CUST_NO",
				"CUST_NAME","CUST_ID","CHANNEL_NAME","RG_MONEY", "QS_DATE","HT_STATUS_NAME", "PRE_FLAG" 
		};
		String[] fieldType = {"1","1","1","1","1","1","2","4","1","1"};
		// 1Ϊ�ַ�����,2ΪBigDecimal 3 Integer
		//��������
		try{
			OutputStream outStr = getResponseStream(fileName + ".xls");
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(outStr,wbs);
			WritableSheet ws = wwb.createSheet("��һҳ", 0);
			
			exportExcel_subscribe(ws, "�ͻ��Ϲ���Ϣ", titleName, fieldName, fieldType, rslist);
			
			wwb.write();
			wwb.close();
			outStr.flush();
			outStr.close();
		} catch (Exception e) {
			throw new BusiException("�����ļ�ʧ��:" + e.getMessage());
		}
		
	}
	
	public void exportExcel_subscribe(WritableSheet ws, String excelTitle,String[] titleName, String[] fieldName, String[] fieldType,List rslist)throws Exception{
		BigDecimal rg_money_total = new BigDecimal(0);	

		try {			
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

			jxl.write.Label labelC0 = new jxl.write.Label(0, 0, excelTitle,format);
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
				jxl.write.Label labelC1 = new jxl.write.Label(i, 1, sName,formatTop);
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
					rg_money_total = rg_money_total.add(new BigDecimal(
							Utility.trimNull(map.get("RG_MONEY"))));

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
						case 2: // BigDecimal��ֵ���ͣ�����2λС��-����
						{
							BigDecimal fiedvalue = Utility.parseDecimal(Utility.trimNull(map.get(sfieldName)), new BigDecimal("0"));

							jxl.write.Label labe4NF = new jxl.write.Label(k,j,
									Utility.trimNull(Format.formatMoney(fiedvalue)),
									formatRight);
							ws.addCell(labe4NF);
							break;
						}
						case 3: // Integer-����
						{
							Integer fieldValue = Utility.parseInt(Utility.trimNull(map.get(sfieldName)), new Integer(0));
							String finalValue = "";
							if (fieldValue != null)
								finalValue = fieldValue.toString();
	
							jxl.write.Label labelValue0 = new jxl.write.Label(k, j,finalValue, formatRight);
	
							ws.addCell(labelValue0);
							break;
						}
						case 4:{ // Integer to Date(yyyy-mm-dd)-����
							Integer fieldValue = Utility.parseInt(Utility.trimNull(map.get(sfieldName)), new Integer(0));
							String finalValue = "";
							
							if (fieldValue != null) {
								finalValue = Format.formatDateLine(fieldValue);
							}
							
							jxl.write.Label labelValue0 = new jxl.write.Label(k, j,finalValue, formatCenter);	
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
						default:break;
					}
				}				
				j++;
			}
			
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
			
		} catch (Exception e) {
			Utility.debugln("����Excel�ļ�ʧ��,�������:" + e.getMessage());
			throw new Exception("����Excel�ļ�ʧ��,�������:" + e.getMessage());
		} 
	}
}
