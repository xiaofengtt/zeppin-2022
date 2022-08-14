/*
 * �������� 2003-10-9
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.web;

/**
 * @author db2admin
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */


import java.io.*;
import java.util.Dictionary;
import java.util.Hashtable;
import com.jspsmart.upload.*;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Argument;
import enfo.crm.tools.EJBFactory;
import enfo.crm.tools.Format;
import enfo.crm.tools.Utility;

import javax.servlet.ServletInputStream;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
public class UploadFile
{
	public String[] sourcefile = new String[255]; //Դ�ļ��� 
	public String objectpath = "c:/"; //Ŀ���ļ�Ŀ¼ 
	public String[] suffix = new String[255]; //�ļ���׺�� 
	public String[] objectfilename = new String[255]; //Ŀ���ļ��� 
	public ServletInputStream sis = null; //������ 
	public String[] description = new String[255]; //����״̬ 
	public long size = 10000 * 1024; //���ƴ�С 
	private int count = 0; //�Ѵ����ļ���Ŀ 
	private byte[] b = new byte[4096]; //�ֽ���������� 
	private boolean successful = true;
	private String sourcefilename = ""; //��ȡ��Դ�ļ����ơ�
	private Hashtable fields = new Hashtable(); //fields��ű����Ԫ��ֵ�����ƵĶ�Ӧ
	private String hq_date = ""; //�����������
	private String depart_code = ""; //Ӫҵ�����ʼ������ļ����

	private final String SEPARATOR = java.io.File.separator;
	private SmartUpload mySmartUpload = new SmartUpload();
	private Request m_formRequest;
	private boolean hasParse = false;
	private String strFolder;
	private PageContext pageContext;
	private final long MAXFILESIZE = 50000000;

	//private Integer input_man;
	//private PageContext pageContext;
	//fields = new 
	//�ϴ��ļ�
	public void setInitalUpload(PageContext in_pageContext, String configFile)
	{
		InputStream in;
		try
		{
			in = new FileInputStream(configFile);
			Properties properties = new Properties();
			properties.load(in);
			strFolder = properties.getProperty("document.Folder", "c:\\");
			pageContext = in_pageContext;
			mySmartUpload.initialize(pageContext);
			mySmartUpload.setTotalMaxFileSize(MAXFILESIZE);

		}
		catch (Exception e)
		{
			mySmartUpload = null;
			pageContext = null;
			strFolder = "c:\\";
		}
	}

	public String[] getParameterValues(String name)
	{
		if (m_formRequest == null)
			return null;
		return m_formRequest.getParameterValues(name);
	}
	public String getParameter(String name)
	{
		if (m_formRequest == null)
			return null;
		return m_formRequest.getParameter(name);
	}

	public Enumeration getParameterNames()
	{
		if (m_formRequest == null)
			return null;
		return m_formRequest.getParameterNames();
	}
	private boolean insureFolder(String strFolder)
	{
		java.io.File dir = new java.io.File(strFolder);
		if (!dir.isDirectory())
		{
			dir.mkdirs();
		}
		return dir.isDirectory();
	}
	private boolean deleteFolder(String strFolder)
	{
		if (!insureFolder(strFolder))
			return false;

		java.io.File dir = new java.io.File(strFolder);
		java.io.File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			java.io.File f = files[i];
			f.delete();
		}
		return true;
	}
	public boolean parseRequest() throws Exception
	{
		if (hasParse)
			return true;
		hasParse = true;
		if (mySmartUpload == null)
			return false;
		try
		{
			Utility.debug("upload...");
			mySmartUpload.upload();
			Utility.debug("upload...");
			m_formRequest = mySmartUpload.getRequest();
			return true;
		}
		catch (Exception e)
		{
			m_formRequest = null;
			throw new Exception(e.getMessage());
		}
	}

	

	public void doUploadFile(HttpServletRequest request, int flag) throws Exception
	{
		sis = request.getInputStream();

		int a = 0;
		int k = 0;
		String s = "";
		try
		{
			a = sis.readLine(b, 0, b.length);
			while (a != -1)
			{
				int boundaryLength = a - 2;
				s = new String(b, 0, a);
				if (s.startsWith("Content-Disposition: form-data; name=\""))
				{
					//�����������ļ���
					Utility.debug(s);
					if ((k = s.indexOf("filename=")) != -1)
					{
						int pos = s.indexOf("name=\"");
						String strtemp = "";
						if (pos != -1)
							strtemp = s.substring(pos + 6);
						int posend = strtemp.indexOf("\"");
						String fieldName = "";

						if (posend != -1)
							fieldName = strtemp.substring(0, posend);

						//��ȡ����ϴ��ļ������Ƶı�Ԫ������file_name1,file_name2

						s = s.substring(k + 10);
						k = s.indexOf("\"");
						s = s.substring(0, k);
						sourcefile[count] = s;

						fields.put(fieldName.trim(), s.trim());
						sourcefilename = Utility.getFileNameFromPath(s);
						//sourcefile[count] = sourcefilename;
						k = s.lastIndexOf(".");

						suffix[count] = s.substring(k + 1);
						if (canTransfer(count))
							transferfile(count, flag, fieldName);
						count++;

					} //������Ƿ��ļ����������һЩԪ�ؼ�ֵ������hashtable��
					else
					{
						int pos = s.indexOf("name=\"");
						String fieldName = "";
						fieldName = s.substring(pos + 6, s.length() - 3);
						a = sis.readLine(b, 0, b.length);
						a = sis.readLine(b, 0, b.length);
						if (a != -1)
							s = new String(b, 0, a);

						fields.put(fieldName, s);
					}
				}
				if (!successful)
					break;
				a = sis.readLine(b, 0, b.length);
			}
		}
		catch (Exception e)
		{
			successful = false;
			throw new Exception(e.getMessage());
		}
	}

	private String trimDbf(String dbfname) throws Exception
	{
		int istart = -1;
		//dbfname = dbfname.toLowerCase();
		if (dbfname == "")
			return dbfname;
		Utility.debug("dbfname:" + dbfname);

		istart = dbfname.indexOf(".dbf");
		if (istart == -1)
			istart = dbfname.indexOf(".DBF");

		if (istart != -1)
			dbfname = dbfname.substring(0, istart);
		else
			throw new Exception("�ļ���ʽ��·�����ԣ�");

		return dbfname;
	}



	public int getCount()
	{
		return count;
	}

	public String[] getSourcefile()
	{
		return sourcefile;
	}

	public void setObjectpath(String objectpath)
	{
		this.objectpath = objectpath;
	}

	public String getObjectpath()
	{
		return objectpath;
	}

	private boolean canTransfer(int i)
	{
		suffix[i] = suffix[i].toLowerCase();
		//�������������ͼƬ�ģ���λ���԰Ѻ�׺���ĵ����߲�Ҫ������� 
		if (sourcefile[i].equals("")
			|| (!suffix[i].equals("dbf")
				&& !suffix[i].equals("jpg")
				&& !suffix[i].equals("jpeg")
				&& !suffix[i].equals("txt")))
		{
			description[i] = "ERR suffix is wrong";
			return false;
		}
		else
			return true;
	}

	private void transferfile(int i, int flag, String fieldName) throws Exception
	{
		String x = Long.toString(new java.util.Date().getTime());

		try
		{
			String strcode = "";

			if (!fields.isEmpty())
				depart_code = (String) fields.get("depart_code");

			Utility.debug("fieldName:" + fieldName);

			//depart_code=fieldName.substring(1,fieldName.length()-9);
			//fieldName=fieldName.substring(fieldName.length()-10,fieldName.length());

			if (flag == 1)
				objectfilename[i] = sourcefilename; //x + "." + suffix[i];�ϴ����ʽ����ļ�
			else if (flag == 2)
			{
				if (fieldName.trim().equals("file_name1") || fieldName.trim() == "file_name1")
					objectfilename[i] = depart_code.trim() + "_jgdbf.dbf";
				else if (fieldName.trim().equals("file_name2") || fieldName.trim() == "file_name2")
					objectfilename[i] = depart_code.trim() + "_dzdbf.dbf";
			}

			java.io.File file2 = new java.io.File(objectpath + objectfilename[i]);
			if (file2.exists())
				file2.delete();

			Utility.debug("objectfilename:" + objectfilename[i]);

			FileOutputStream out = new FileOutputStream(objectpath + objectfilename[i]);

			int a = 0;
			int k = 0;
			long hastransfered = 0; //��ʾ�Ѿ�������ֽ��� 
			String s = "";
			while ((a = sis.readLine(b, 0, b.length)) != -1)
			{
				s = new String(b, 0, a);
				if ((k = s.indexOf("Content-Type:")) != -1)
					break;
			}

			sis.readLine(b, 0, b.length);
			while ((a = sis.readLine(b, 0, b.length)) != -1)
			{
				s = new String(b, 0, a);
				if ((b[0] == 45) && (b[1] == 45) && (b[2] == 45) && (b[3] == 45) && (b[4] == 45))
					break;
				out.write(b, 0, a);

				hastransfered += a;
				if (hastransfered >= size)
				{
					description[count] =
						"ERR The file "
							+ sourcefile[count]
							+ " is too large to transfer. The whole process is interrupted.";
					successful = false;
					break;
				}
			}
			if (successful)
				description[count] = "Right The file " + sourcefile[count] + " has been transfered successfully.";
			//++count;
			out.close();
		}
		catch (IOException ioe)
		{
			successful = false;
			throw new Exception(ioe.getMessage());
		}
	}

	

	
	private void downloadJsp(java.io.File file) throws Exception
	{
		JspWriter out = pageContext.getOut();
		Utility.debug("downloadJsping...");
		HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());
		if (!file.exists())
		{
			throw new Exception("������ļ�" + file.getName() + "�����ڣ�");
		}
		out.clear();
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", Encode("attachment; filename=" + file.getName()));
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		OutputStream os = response.getOutputStream();
		byte[] buf = new byte[1024];

		int read = 0;
		read = dis.read(buf);
		while (read != -1)
		{
			os.write(buf, 0, read);
			read = dis.read(buf);
		}
		dis.close();
		os.close();
		out.close();
		Utility.debug("downloadJsped...");
	}

	private void downloadFunction(java.io.File file) throws Exception
	{
		//java.io.File file = new java.io.File(strFile);
		//JspWriter out = pageContext.getOut();
		HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());
		if (!file.exists())
			throw new BusiException("�ļ������ڣ�");
		Utility.debug("downloadFunctioning...");
		try
		{
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", Encode("attachment; filename=" + file.getName()));
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			OutputStream os = response.getOutputStream();
			byte[] buf = new byte[1024];
			int left = (int) file.length();
			int read = 0;
			while (left > 0)
			{
				read = dis.read(buf);
				left -= read;
				os.write(buf, 0, read);
			}
			dis.close();
			os.close();
			//out.close();
			Utility.debug("downloadFunctioned...");
		}
		finally
		{
			file.delete();
		}
	}


	public UploadFile()
	{
		//�����ڹ��������湹���������ϴ�Ŀ¼��Ҳ������javabean���õ�ʱ���Լ����� 
		//setObjectpath("/home/www/jspvhost4/web/popeyelin/images/");

	}
	/**
	 * @return
	 */
	public String getSourcefilename()
	{
		return sourcefilename;
	}

	/**
	 * @param string
	 */
	public void setSourcefilename(String string)
	{
		sourcefilename = string;
	}
	
	private String Encode(String in)
	{
		try
		{
			return new String(in.getBytes("GBK"), "ISO8859_1");
		}
		catch (Exception e)
		{
			return in;
		}
	}

}
