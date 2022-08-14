package enfo.crm.webreport;
/**
 * <p>Title: CellUpload</p> 
 * <p>CreateTime: 2005-4-15</p>
 * @author <a href="mailto:caiyuan@singlee.com.cn">Cai Yuan</a>
 * @version 1.0 
 * 
 */

import java.io.*;
import javax.servlet.jsp.*;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;

public class CellUpload 
{	
	private final long MAXFILESIZE = 50000000;

	public com.jspsmart.upload.SmartUpload smartUpload =
		new com.jspsmart.upload.SmartUpload();

	private PageContext pageContext;
	private String strFolder = "/webreport/Cells/";
	private String toFolder ;	
	private com.jspsmart.upload.Request request;
	private boolean hasParse = false;

	public CellUpload(PageContext in_pageContext) 
	{		
		try 
		{
			pageContext = in_pageContext;			
			smartUpload.initialize(pageContext);
			smartUpload.setTotalMaxFileSize(MAXFILESIZE);
		} 
		catch (Exception e) 
		{
			smartUpload = null;
			pageContext = null;
		}
	}
	
	public void parseRequest() throws Exception 
	{
		if (hasParse)
			return;
		try 
		{
			smartUpload.upload();
			request = smartUpload.getRequest();
			hasParse = true;
		} 
		catch (Exception e) 
		{
			request = null;
			throw new Exception("文件上传失败!"+ e.getMessage());
		}
	}

	public boolean uploadFile() throws Exception 
	{
		if (!hasParse)
			parseRequest();
				
		//toFolder = path + strFolder + getFolderName(); 
		toFolder = strFolder;  //统一存放在一个文件夹里

		if (!insureFolder(toFolder))
			return false;
		if (existFile())	//没有删除成功就返回
			return false;
		if (smartUpload.getFiles().getCount() == 0)
			return false;
		if (smartUpload.getFiles().getFile(0).getSize() == 0)
			return false;
		if (smartUpload.getFiles().getFile(0).getSize() > 10 * 1024 * 1024)
			throw new Exception("文件大小不能超过10M！");

		try 
		{			
			smartUpload.getFiles().getFile(0).saveAs((toFolder + getRptId(getFileName())+".CLL"),com.jspsmart.upload.SmartUpload.SAVE_VIRTUAL);	//由于可能上传的文件夹不存在，所以只能用绝对路径			

			return true;
		} 
		catch (Exception e) 
		{
			throw new Exception("文件上传失败: " + e.getMessage());
		}
	}
	
	//判断是否存在目标文件夹，没有的话就生成一个
	private boolean insureFolder(String strFolder) 
	{
		java.io.File dir = new java.io.File(strFolder);					
		if (!dir.isDirectory())				
			dir.mkdirs();					
		return dir.isDirectory();
	}
	
	//判断是否存在目标文件，有的话就删除
	private boolean existFile() 
	{
		java.io.File tofile = new java.io.File(strFolder + this.getRptId(this.getFileName())+".CLL");					
		if (tofile.isFile())				
			tofile.delete();			
		return tofile.isFile();
	}
	//得到文件全路径名---由于页面上的缺陷需要对目录进行处理！
	public String getFilePathName()
	{			
		return smartUpload.getFiles().getFile(0).getFilePathName().replace('\\','/');
	}
	//得到文件名
	public String getFileName()
	{
		return smartUpload.getFiles().getFile(0).getFileName();
	}
	
	//得到CELL的编号---考虑到性能所以文件名以参数方式传入，下面同
	public int getRptId(String filename)
	{		
		int pos = filename.indexOf("@");		
		return CellHelper.parseInt(filename.substring(0,pos),0);
	}
	
	//得到CELL的报表名称
	public String getRptTitle(String filename)
	{	
		int pos = filename.indexOf("@");
		filename = filename.substring(pos+1);
		pos = filename.indexOf(".");
		filename = filename.substring(0,pos);	
				
		return filename;
	}
	
	//根据全文件名得到文件所在的上层目录
	public String getFolderName()
	{
		String filepath = smartUpload.getFiles().getFile(0).getFilePathName();
		String temp = filepath;
		int pos1,pos2;
		pos1 = temp.lastIndexOf("\\");		
		temp = temp.substring(0,pos1);	//截掉文件名
		pos2 = temp.lastIndexOf("\\");			
		filepath = filepath.substring(pos2+1,pos1) + "/" ;
		
		return filepath;				
	}
	
	//根据全文件名得到文件所在的上层目录
	public String getMenuFolderName()
	{		
		String filepath = getFilePathName();
		String temp = filepath;
		int pos1,pos2;
		pos1 = temp.lastIndexOf("/");		
		temp = temp.substring(0,pos1);	//截掉文件名
		pos2 = temp.lastIndexOf("/");	
		filepath = filepath.substring(pos2+1,pos1) ;
		
		return filepath;				
	}	

}
