package com.whaty.platform.standard.aicc.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.file.AUData;
import com.whaty.platform.standard.aicc.file.CRSData;
import com.whaty.platform.standard.aicc.file.CSTData;
import com.whaty.platform.standard.aicc.file.DESData;
import com.whaty.platform.standard.aicc.file.ORTData;
import com.whaty.platform.standard.aicc.util.AiccLog;



/**
 * ������4����Aicc�������ļ������Aicc˵���ĵ�
 * ��(��
 *     Mandatory��.AU,.CRS,.CST,.DES
 *     Optional��.ORT,.PRE,.CMP
 * @author chenjian
 *
 */
public abstract class AiccFileManage {
	
	private String course_id;
	private File uploadFile;
	private int auFileNumber=0;
	private int desFileNumber=0;
	private int cstFileNumber=0;
	private int crsFileNumber=0;
	private int ortFileNumber=0;
	private int preFileNumber=0;
	private int cmpFileNumber=0;
	private BufferedReader bufferedReader= null;
	
	public File getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(File uploadfile) {
		this.uploadFile = uploadfile;
	}
	
	
	public void parseAiccFile()throws AiccException
	{
		CRSData crsData=null;
		List desList=null;
		List ortList=null;
		List cstList=null;
		List auList=null;
		List preList=null;
		List cmpList=null;
		try
		{
			  
			  ZipFile archive = new ZipFile(getUploadFile());
	          for ( Enumeration e=archive.entries(); e.hasMoreElements(); )
	          {
	        	    ZipEntry entry = (ZipEntry) e.nextElement();
	                if(entry.isDirectory())
	                {
	                	//can't include directories
	                	throw new AiccException("Error! package has directory!");
	                }
	                else
	                {
	                	String fileName=entry.getName();
	                	AiccLog.setDebug("filename"+fileName);
	                	if(fileName.length()>4 && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equalsIgnoreCase("CRS"))
	                	{
	                		//this is CRS file 
	                		InputStream in = archive.getInputStream(entry);
	                		AiccLog.setDebug("parse .crs file!");
	                		crsData=parseCRSFile(in);
	                		this.crsFileNumber++;
	                	}
	                	else if(fileName.length()>4 && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equalsIgnoreCase("DES"))
	                	{
	                		//this is DES file 
	                		InputStream in = archive.getInputStream(entry);
	                		AiccLog.setDebug("parse .des file!");
	                		desList=parseDESFile(in);
	                		this.desFileNumber++;
	                	}
	                	else if(fileName.length()>3 && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equalsIgnoreCase("AU"))
	                	{
	                		//this is AU file 
	                		InputStream in = archive.getInputStream(entry);
	                		AiccLog.setDebug("parse .au file!");
	                		auList=parseAUFile(in);
	                		this.auFileNumber++;
	                	}
	                	else if(fileName.length()>4 && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equalsIgnoreCase("CST"))
	                	{
	                		//this is CST file 
	                		InputStream in = archive.getInputStream(entry);
	                		AiccLog.setDebug("parse .cst file!");
	                		cstList=parseCSTFile(in);
	                		this.cstFileNumber++;
	                	}
	                	else if(fileName.length()>4 && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equalsIgnoreCase("ORT"))
	                	{
	                		//this is ORT file 
	                		InputStream in = archive.getInputStream(entry);
	                		AiccLog.setDebug("parse .ort file!");
	                		ortList=parseORTFile(in);
	                		this.ortFileNumber++;	                		
	                	}
	                	else if(fileName.length()>4 && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equalsIgnoreCase("PRE"))
	                	{
	                		//this is PRE file 
	                		InputStream in = archive.getInputStream(entry);
	                		AiccLog.setDebug("parse .pre file!");
	                		preList=parsePREFile(in);
	                		this.preFileNumber++;	      	                		
	                	}
	                	else if(fileName.length()>4 && fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equalsIgnoreCase("CMP"))
	                	{
	                		//this is CMP file 
	                		InputStream in = archive.getInputStream(entry);
	                		AiccLog.setDebug("parse .cmp file!");
	                		cmpList=parseCMPFile(in);
	                		this.cmpFileNumber++;	      	                		
	                	}
	                	else
	                	{
	                		throw new AiccException("inclue file type not permitted by Aicc");
	                	}
	                }
	          }
	          AiccLog.setDebug("au file number"+this.auFileNumber);
	          AiccLog.setDebug("des file number"+this.desFileNumber);
	          AiccLog.setDebug("cst file number"+this.cstFileNumber);
	          AiccLog.setDebug("crs file number"+this.crsFileNumber);
	          AiccLog.setDebug("ort file number"+this.ortFileNumber);
	          AiccLog.setDebug("cmp file number"+this.cmpFileNumber);
	          AiccLog.setDebug("pre file number"+this.preFileNumber);
	          if(this.auFileNumber<1 || this.desFileNumber<1 || this.cstFileNumber<1 || this.crsFileNumber<1)
	          {
	        	  throw new AiccException("aicc file is not enough. (.au,..crs,.cst,.des)");
	          }
	          else
	          {
	        	 if(checkAiccData(crsData,desList,auList,cstList,ortList,preList,cmpList))
	        	 {
	        		 putAiccFileToDB(crsData,desList,auList,cstList,ortList,preList,cmpList);
	        	 }
	        	  
	          }
		}
		catch(IOException e)
		{
			throw new AiccException("aicc file IO error!");
		}
	}
	
	public List parseAUFile(InputStream inputStream) throws AiccException
	{
		
		bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
		List aulist=new ArrayList();
		try
		{
			if(bufferedReader.ready())
			{
				String[] fields=bufferedReader.readLine().trim().split(",");
				int fields_length=fields.length;
				String lineStr=null;
				while((lineStr=bufferedReader.readLine())!=null)
				{
					if(lineStr.length()<1) continue;
					String[] values=lineStr.split(",");
					Map map=new HashMap();
					for(int i=0;i<fields_length;i++)
					{
						map.put(this.deleteQuotationMark(fields[i]).toLowerCase(),this.deleteQuotationMark(values[i]));
					}
					AUData auData=new AUData(map);
					AiccLog.setDebug(auData.toStrData());
					aulist.add(auData);
				}
				return aulist;
			}
			else
			{
				throw new AiccException("error in read .AU file");
			}
		}
		catch(IOException e)
		{
			throw new AiccException("error in read .AU file");
		}
		catch(NullPointerException e)
		{
			throw new AiccException("nullPointer error in read .AU file");
		}
	}
	public CRSData parseCRSFile(InputStream inputStream) throws AiccException
	{
		bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
		CRSData crsData = new CRSData();
		try
		{
			if(bufferedReader.ready())
			{
				String lineStr=null;
				Map valueMap=null;
				String oldelement=null;
				String element=null;
				while((lineStr=bufferedReader.readLine())!=null)
				{
					lineStr=lineStr.trim();
					if(lineStr.length()<1)
					{
						continue;
					}
					if(lineStr.startsWith("["))
					{
						oldelement=element;
						element=lineStr.substring(1,lineStr.length()-1);
						if(valueMap!=null)
						{
							if(oldelement!=null && oldelement.equalsIgnoreCase("course"))
							{
								crsData.getCourse().setData(valueMap);
							}
							else if(oldelement!=null && oldelement.equalsIgnoreCase("course_behavior"))
							{
								crsData.getCourseBehavior().setData(valueMap);
							}
							else if(oldelement!=null && oldelement.equalsIgnoreCase("course_description"))
							{
								crsData.getCourseDes().setData(valueMap);
							}
						}
						valueMap=new HashMap();
					}
					else
					{
						String[] values=lineStr.split("=");
						valueMap.put(values[0].trim().toLowerCase(),values[1].trim().toLowerCase());
					}
					oldelement=element;
					if(oldelement!=null && oldelement.equalsIgnoreCase("course"))
					{
						crsData.getCourse().setData(valueMap);
					}
					else if(oldelement!=null && oldelement.equalsIgnoreCase("course_behavior"))
					{
						crsData.getCourseBehavior().setData(valueMap);
					}
					else if(oldelement!=null && oldelement.equalsIgnoreCase("course_description"))
					{
						crsData.getCourseDes().setData(valueMap);
					}
				}
				AiccLog.setDebug(crsData.toStrData());
				return crsData;
			}
			else
			{
				throw new AiccException("error in read .CRS file");
			}
		}
		catch(IOException e)
		{
			throw new AiccException("error in read .CRS file");
		}
		catch(NullPointerException e)
		{
			throw new AiccException("nullPointer error in read .CRS file");
		}
	}
	public List parseDESFile(InputStream inputStream) throws AiccException
	{
		bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
		List deslist=new ArrayList();
		try
		{
			if(bufferedReader.ready())
			{
				String[] fields=bufferedReader.readLine().split(",");
				int fields_length=fields.length;
				String lineStr=null;
				while((lineStr=bufferedReader.readLine())!=null)
				{
					if(lineStr.length()<1) continue;
					String[] values=lineStr.split(",");
					Map map=new HashMap();
					for(int i=0;i<fields_length;i++)
					{
						map.put(this.deleteQuotationMark(fields[i]).toLowerCase(),this.deleteQuotationMark(values[i]));
					}
					DESData desData=new DESData(map);
					AiccLog.setDebug(desData.toStrData());
					deslist.add(desData);
					
				}
				return deslist;
				
			}
			else
			{
				throw new AiccException("error in read .DES file");
			}
		}
		catch(IOException e)
		{
			throw new AiccException("error in read .DES file");
		}
		catch(NullPointerException e)
		{
			throw new AiccException("nullPointer error in read .DES file");
		}
	}
	public List parseCSTFile(InputStream inputStream) throws AiccException
	{
		bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
		List cstlist=new ArrayList();
		try
		{
			if(bufferedReader.ready())
			{
				bufferedReader.readLine();
				String lineStr=null;
				while((lineStr=bufferedReader.readLine())!=null)
				{
					if(lineStr.length()<1) continue;
					String[] values=lineStr.split(",");
					List valuelist=new ArrayList();
					for(int i=0;i<values.length;i++)
						valuelist.add(this.deleteQuotationMark(values[i]));
					Map map=new HashMap();
					map.put("block",valuelist.get(0));
					map.put("members",valuelist.subList(1,valuelist.size()));
					CSTData cstData=new CSTData(map);
					AiccLog.setDebug(cstData.toStrData());
					cstlist.add(cstData);
					
				}
				return cstlist;
			}
			else
			{
				throw new AiccException("error in read .CST file");
			}
		}
		catch(IOException e)
		{
			throw new AiccException("error in read .CST file");
		}
	}
	public List parseORTFile(InputStream inputStream) throws AiccException
	{
		bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
		List ortlist=new ArrayList();
		try
		{
			if(bufferedReader.ready())
			{
				bufferedReader.readLine();
				String lineStr=null;
				while((lineStr=bufferedReader.readLine())!=null)
				{
					if(lineStr.length()<1) continue;
					String[] values=lineStr.split(",");
					List valuelist=new ArrayList();
					for(int i=0;i<values.length;i++)
						valuelist.add(this.deleteQuotationMark(values[i]));
					Map map=new HashMap();
					map.put("course_element",valuelist.get(0));
					map.put("members",valuelist.subList(1,valuelist.size()));
					ORTData ortData=new ORTData(map);
					AiccLog.setDebug(ortData.toStrData());
					ortlist.add(ortData);
				}
				return ortlist;
			}
			else
			{
				throw new AiccException("error in read .ORT file");
			}
		}
		catch(IOException e)
		{
			throw new AiccException("error in read .ORT file");
		}
		catch(NullPointerException e)
		{
			throw new AiccException("nullPointer error in read .ORT file");
		}
	}
	public List parsePREFile(InputStream inputStream) throws AiccException
	{
		return null;
		
	}
	public List parseCMPFile(InputStream inputStream) throws AiccException
	{
		return null;
		
	}
	
	
	public abstract void putAiccFileToDB(CRSData crsData, List desList, List auList, List cstList, List ortList, List preList, List cmpList) throws AiccException;
	
	public abstract boolean courseIsExist();
	
	private boolean checkAiccData(CRSData crsData, List desList, List auList, List cstList, List ortList, List preList, List cmpList) throws AiccException{
		int totalAus=Integer.parseInt(crsData.getCourse().getTotalAus());
		int totalBlocks=Integer.parseInt(crsData.getCourse().getTotalBlocks());
		int totalObjectives=Integer.parseInt(crsData.getCourse().getTotalObjectives());
		int totalComplexObjectives=Integer.parseInt(crsData.getCourse().getTotalComplexObjectives());
		AiccLog.setDebug("auList number="+auList.size());
		AiccLog.setDebug("cstList number="+cstList.size());
		if(totalAus!=auList.size())
		{
			throw new AiccException("check upload aicc Data error");
		}
		else
		{
			return true;
		}
	}
	private String deleteQuotationMark(String str)
	{
		str=str.replaceAll("\"","").trim();
		return str;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

}
