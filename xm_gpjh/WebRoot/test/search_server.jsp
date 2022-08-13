<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.HashMap,java.io.*,"%>
<%!
	public String getServerFromIp(HashMap hm,String ip,String path)
	{
		String result="";
		String type="";
		String tmp_ip="";
		ip="60.253.128.1";
		if(hm!=null && ip!=null && !ip.equals(""))
		{
			String[] ipnums=ip.split("\\.");
			if(ipnums.length==4)
			{
				tmp_ip=ipnums[0]+".0.0.0";
				type=(String)hm.get(tmp_ip);
				if(type==null || type.equals(""))
				{
					tmp_ip=ipnums[0]+"."+ipnums[1]+".0.0";
					type=(String)hm.get(tmp_ip);
				}
				if(type==null || type.equals(""))
				{
					tmp_ip=ipnums[0]+"."+ipnums[1]+"."+ipnums[2]+".0";
					type=(String)hm.get(tmp_ip);
				}
				if(type==null || type.equals(""))
				{
					tmp_ip=ipnums[0]+"."+ipnums[1]+"."+ipnums[2]+"."+ipnums[3];
					type=(String)hm.get(tmp_ip);
				}
			}
		}
		if(type==null || type.equals(""))
			type="other";
		result=getSeverFromType(type,path);
		return result;
	}
	public String getSeverFromType(String type,String path)
	{
		String result="";
		if(type==null || type.equals(""))
		{
			return result;
		}
		else
		{
			String filepath=path+type+".txt";
			System.out.println("filepath:"+filepath);
		   
		   int minValue=0;
		   try {
		   FileReader read = new FileReader(filepath);
		   BufferedReader br = new BufferedReader(read);
		   String row;
		   boolean bFirst=true;
		   while((row = br.readLine())!=null){
		   System.out.println("row:"+row);
			   if(row!=null && !row.equals(""))
			   {
				   String[] ipArray=row.split("\\ ");
				   System.out.println("ipArray.length:"+ipArray.length);
				   if(ipArray.length>1)
				   {
				   		int temp_num=Integer.parseInt(ipArray[1]);
				   		if(bFirst)
				   		{
				   			minValue=temp_num;
				   			result=ipArray[0];
				   			bFirst=false;
				   			System.out.println("1:"+ipArray[0]);
				   			System.out.println("2:"+ipArray[1]);
				   		}
				   		if(temp_num<minValue)
				   		{
				   			minValue=temp_num;
				   			result=ipArray[0];
				   			System.out.println("1:"+ipArray[0]);
				   			System.out.println("2:"+ipArray[1]);
				   		}
				   }
			   }
		   	}
		   }
		   catch (FileNotFoundException e) {
			   e.printStackTrace();
			  } catch (IOException e){
			   e.printStackTrace();
			  }
		   	if(result.equals(""))
		   		result="bzzpxcourse.webtrn.cn";
			return result;
		}
	}
%>
<% 
String filepath=request.getParameter("filepath");

String client_ip=request.getRemoteAddr();
System.out.println("client_ip:"+client_ip);
HashMap ipReferList=(HashMap)application.getAttribute("ipReferList");
String path=request.getRealPath("/");
path+="test\\";
System.out.println("path:"+path);
String courseSever=getServerFromIp(ipReferList,client_ip,path);
System.out.println("aaabbbb:"+courseSever);

System.out.println("filepath:"+filepath);
if(courseSever==null || courseSever.equals(""))
{
	courseSever="bzzpxcourse.webtrn.cn";
}
String url="mms://"+courseSever+"/whaty/";
url+=filepath;
System.out.println("url:"+url);
response.sendRedirect(url); 
%>