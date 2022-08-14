/*
 * 创建日期 2003-10-9
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.web;

/**
 * @author db2admin
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
	public String[] sourcefile = new String[255]; //源文件名 
	public String objectpath = "c:/"; //目标文件目录 
	public String[] suffix = new String[255]; //文件后缀名 
	public String[] objectfilename = new String[255]; //目标文件名 
	public ServletInputStream sis = null; //输入流 
	public String[] description = new String[255]; //描述状态 
	public long size = 10000 * 1024; //限制大小 
	private int count = 0; //已传输文件数目 
	private byte[] b = new byte[4096]; //字节流存放数组 
	private boolean successful = true;
	private String sourcefilename = ""; //读取的源文件名称。
	private Hashtable fields = new Hashtable(); //fields存放表单里的元素值与名称的对应
	private String hq_date = ""; //行情更新日期
	private String depart_code = ""; //营业部对帐及交割文件编号

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
	//上传文件
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
					//如果读入的是文件流
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

						//读取存放上传文件的名称的表单元素名称file_name1,file_name2

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

					} //读入的是非文件，将表单里的一些元素及值保存在hashtable中
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
			throw new Exception("文件格式或路径不对！");

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
		//这个是我用来传图片的，各位可以把后缀名改掉或者不要这个条件 
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
				objectfilename[i] = sourcefilename; //x + "." + suffix[i];上传对帐交割文件
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
			long hastransfered = 0; //标示已经传输的字节数 
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
			throw new Exception("传入的文件" + file.getName() + "不存在！");
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
			throw new BusiException("文件不存在！");
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
		//可以在构建器里面构建服务器上传目录，也可以在javabean调用的时候自己构建 
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
