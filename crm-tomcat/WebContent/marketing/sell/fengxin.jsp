<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,java.net.*" %>

<%
HttpURLConnection hConnect =null;
String  levelname="";
int resposecode =0;
String strUrl = Utility.trimNull(request.getParameter("url"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
URL newUrl = null;
try{

//String strUrl = "http://128.8.18.188:8080/rating/json/queryCustLevel/";
//String strUrl = "http://128.8.28.106:8080/rating/json/queryCustLevel/";
//String cust_name = "王飞";
cust_name = URLEncoder.encode(cust_name, "UTF-8");

//cust_name = URLDecoder.decode(cust_name,"GBK");
//cust_name=new String(cust_name.getBytes("ISO-8859-1")); 
//cust_name=new String(cust_name.getBytes("ISO-8859-1"), "UTF-8");

newUrl = new URL(strUrl+cust_name); 

}catch(Throwable  e){
 e.printStackTrace();
}

try{

hConnect = (HttpURLConnection) newUrl.openConnection(); 

}catch(Throwable  e){
	 e.printStackTrace();
	levelname = "<font color='red'>服务器连接失败！</font>";
}

try{
if(hConnect!=null){
	resposecode = hConnect.getResponseCode(); 
	//判断是否连接
	if(resposecode==HttpURLConnection.HTTP_OK){
		String level_code1 = hConnect.getHeaderField("leve_code");
		
		
		if(("1013000").equals(level_code1)){
			levelname = "禁止类";
		}else if(("1013001").equals(level_code1)){
			levelname = "高风险";
		}else if(("1013002").equals(level_code1)){
			levelname = "中风险";
		}else if(("1013003").equals(level_code1)){
			levelname = "低风险";
		}else{
			levelname = "风险未评估";
		}
	}else{
		levelname = "<font color='red'>请求失败！</font>";
	}
}else{
	levelname = "<font color='red'>服务器连接地址可能出错！</font>";
}


 %>

<%}catch(Throwable  e){
	 e.printStackTrace();
	levelname = "<font color='red'>服务器连接失败！</font>";
}finally{
    hConnect.disconnect();
}%>


<div>
	<%=levelname %>
</div>

