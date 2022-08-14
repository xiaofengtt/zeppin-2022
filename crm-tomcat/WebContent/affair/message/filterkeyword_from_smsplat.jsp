<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.tools.*,java.util.*" %>
<% String msg=Utility.trimNull(request.getParameter("sms_content"));
	String mobiles=Utility.trimNull(request.getParameter("mobiles"));
String template_code=Utility.trimNull(request.getParameter("template_code"));
if (template_code==null || "".equals(template_code))
	template_code="0001";
String msg2=java.net.URLEncoder.encode(msg,"UTF-8");
String filtURI=enfo.crm.tools.Argument.getDictParamName(800211,"800214");
if (filtURI==null || "".equals(filtURI)){
out.print("错误：短信平台的关键字与黑名单未正确配置");
return;
}
String url=filtURI+"&message="+msg2+"&phoneNumber="+mobiles+"&templateCode="+template_code;
String res=SmsClient.doGet(url,"UTF-8");
if ("正常".equals(res)){
	out.print("0");
}else{
//String s=new String(res.getBytes("UTF-8"),"GBK");
out.print(res);
}%>