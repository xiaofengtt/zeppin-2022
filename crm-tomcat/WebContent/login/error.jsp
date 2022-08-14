<%@ page contentType="text/html; charset=GBK" isErrorPage="true" import="enfo.crm.tools.*,enfo.crm.dao.BusiException,enfo.crm.system.*"%>

<% 
//本地化信息
Integer languageFlag = enfo.crm.tools.Utility.parseInt(enfo.crm.tools.Utility.trimNull(session.getAttribute("languageFlag")),Integer.valueOf("0"));
String languageType = "auto";
java.util.Locale clientLocale = null;
//设置语言环境
if(languageFlag.intValue()==0){
	clientLocale = request.getLocale();//得到本地化信息
	languageType = clientLocale.getLanguage()+"_"+clientLocale.getCountry();
}
else if(languageFlag.intValue()==1){
	clientLocale = new java.util.Locale("zh","CN");
	languageType = "zh_CN";
}
else if(languageFlag.intValue()==2){
	clientLocale = new java.util.Locale("en","US");
	languageType = "en_US";
}
if(languageType.equals("zh_CN")){
	languageFlag = Integer.valueOf("1");
}
else{
	languageFlag = Integer.valueOf("2");
}
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<link href="/includes/default.css" type=text/css rel=stylesheet>
<script src="/includes/default.js"></script>
<title><%=LocalUtilis.language("message.note",clientLocale)%> </title><!--系统提示-->
</head>

<body>
  <table border="0" width="400" cellpadding="2" align="center">
    <tr>
      <td valign="top"><b><font color="#215dc6"><img border="0" src="<%=request.getContextPath()%>/images/close.gif" align="absmiddle"></font></b>
        <b><font color="#215dc6"><%=LocalUtilis.language("message.note",clientLocale)%></font></b><!--系统提示-->  
        <p align="center"><%  int sflag = exception.getMessage().indexOf("操作员尚未登录");
        					  int sflag2 = exception.getMessage().indexOf("Operator Login Error");
        					  String sMessage=exception.getMessage();
        					  if(exception instanceof BusiException){
        						  BusiException busiException = (BusiException)exception;
        						  int errorCode = busiException.getErrorCode();
        						  if(errorCode!=0){
        							  sMessage = BusiException.getErrorMsg(errorCode,languageFlag.intValue());
        						  }        						         						
        					  }
        					  if(exception != null) out.println(sMessage);%></p>  
        <p style="display:none">For Debug: <%if(exception!=null) out.println(exception);%></p>  
      </td>  
    </tr>  
    <tr>  
      <td>  
        <p align="center"> 
        <%
        if(sflag==-1||sflag2==-1)
        {
  		%> 
  		<!--返回-->
        <button type="button"  class="xpbutton3" accessKey=r name="btnReturn" onclick="javascript:history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>R</u>)</button>&nbsp;&nbsp; 
        <%} else {%>
        <!--重新登录-->
        <button type="button"  class="xpbutton5" accessKey=r name="btnReturn" onclick="javascript:top.location='/';"><%=LocalUtilis.language("message.loginAgain",clientLocale)%> (<u>R</u>)</button>&nbsp;&nbsp;  
        <%}%>
        <script language="javascript">
          //关闭
          if (this.parent==this)
        	document.writeln("<button type="button"  class='xpbutton3' accessKey=c name='B3' onclick='javascript:window.close();'><%=enfo.crm.tools.LocalUtilis.language("message.close", clientLocale)%>(<u>C</u>)</button></p>");  
        </script>
      </td>  
    </tr>  
  </table>  
</body>  
</html>