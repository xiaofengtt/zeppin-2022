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
			throw new Exception("�ļ��ϴ�ʧ��!"+ e.getMessage());
		}
	}

	public boolean uploadFile() throws Exception 
	{
		if (!hasParse)
			parseRequest();
				
		//toFolder = path + strFolder + getFolderName(); 
		toFolder = strFolder;  //ͳһ�����һ���ļ�����

		if (!insureFolder(toFolder))
			return false;
		if (existFile())	//û��ɾ���ɹ��ͷ���
			return false;
		if (smartUpload.getFiles().getCount() == 0)
			return false;
		if (smartUpload.getFiles().getFile(0).getSize() == 0)
			return false;
		if (smartUpload.getFiles().getFile(0).getSize() > 10 * 1024 * 1024)
			throw new Exception("�ļ���С���ܳ���10M��");

		try 
		{			
			smartUpload.getFiles().getFile(0).saveAs((toFolder + getRptId(getFileName())+".CLL"),com.jspsmart.upload.SmartUpload.SAVE_VIRTUAL);	//���ڿ����ϴ����ļ��в����ڣ�����ֻ���þ���·��			

			return true;
		} 
		catch (Exception e) 
		{
			throw new Exception("�ļ��ϴ�ʧ��: " + e.getMessage());
		}
	}
	
	//�ж��Ƿ����Ŀ���ļ��У�û�еĻ�������һ��
	private boolean insureFolder(String strFolder) 
	{
		java.io.File dir = new java.io.File(strFolder);					
		if (!dir.isDirectory())				
			dir.mkdirs();					
		return dir.isDirectory();
	}
	
	//�ж��Ƿ����Ŀ���ļ����еĻ���ɾ��
	private boolean existFile() 
	{
		java.io.File tofile = new java.io.File(strFolder + this.getRptId(this.getFileName())+".CLL");					
		if (tofile.isFile())				
			tofile.delete();			
		return tofile.isFile();
	}
	//�õ��ļ�ȫ·����---����ҳ���ϵ�ȱ����Ҫ��Ŀ¼���д���
	public String getFilePathName()
	{			
		return smartUpload.getFiles().getFile(0).getFilePathName().replace('\\','/');
	}
	//�õ��ļ���
	public String getFileName()
	{
		return smartUpload.getFiles().getFile(0).getFileName();
	}
	
	//�õ�CELL�ı��---���ǵ����������ļ����Բ�����ʽ���룬����ͬ
	public int getRptId(String filename)
	{		
		int pos = filename.indexOf("@");		
		return CellHelper.parseInt(filename.substring(0,pos),0);
	}
	
	//�õ�CELL�ı�������
	public String getRptTitle(String filename)
	{	
		int pos = filename.indexOf("@");
		filename = filename.substring(pos+1);
		pos = filename.indexOf(".");
		filename = filename.substring(0,pos);	
				
		return filename;
	}
	
	//����ȫ�ļ����õ��ļ����ڵ��ϲ�Ŀ¼
	public String getFolderName()
	{
		String filepath = smartUpload.getFiles().getFile(0).getFilePathName();
		String temp = filepath;
		int pos1,pos2;
		pos1 = temp.lastIndexOf("\\");		
		temp = temp.substring(0,pos1);	//�ص��ļ���
		pos2 = temp.lastIndexOf("\\");			
		filepath = filepath.substring(pos2+1,pos1) + "/" ;
		
		return filepath;				
	}
	
	//����ȫ�ļ����õ��ļ����ڵ��ϲ�Ŀ¼
	public String getMenuFolderName()
	{		
		String filepath = getFilePathName();
		String temp = filepath;
		int pos1,pos2;
		pos1 = temp.lastIndexOf("/");		
		temp = temp.substring(0,pos1);	//�ص��ļ���
		pos2 = temp.lastIndexOf("/");	
		filepath = filepath.substring(pos2+1,pos1) ;
		
		return filepath;				
	}	

}
