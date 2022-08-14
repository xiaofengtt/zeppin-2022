<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,java.math.*,java.util.*" %>
<%@ page import="java.text.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//声明页面参数
input_bookCode = new Integer(1);//帐套暂时设置
//获取页面传递变量
Integer cust_id = Utility.parseInt(request.getParameter("custId"),new Integer(0));;
String cust_no = Utility.trimNull(request.getParameter("custNo"));

String serviceTime="";
String serviceInfo="";
String serviceSummary="";
String content = "";
String subject = "";
String step_flag = "";
String info_level = "";
Integer service_man2 = new Integer(0);
Integer executor = new Integer(0);
//获取对象

CustServiceLogLocal custServiceLogLocal=EJBFactory.getCustServiceLog();
CustServiceLogVO custServiceLogVO=new CustServiceLogVO();

//保存客户服务记录
serviceTime= request.getParameter("serviceTime");
serviceInfo= new String(Utility.trimNull(request.getParameter("serviceInfo")).getBytes("GBK"), "utf-8");
serviceSummary= new String(Utility.trimNull(request.getParameter("serviceSummary")).getBytes("GBK"), "utf-8");
content = new String(Utility.trimNull(request.getParameter("scontent")).getBytes("GBK"), "utf-8");
subject = new String(Utility.trimNull(request.getParameter("subject")).getBytes("GBK"), "utf-8");
step_flag = Utility.trimNull(request.getParameter("step_flag"),"0"); 
info_level = Utility.trimNull(request.getParameter("info_level"),"0"); 
executor = Utility.parseInt(Utility.trimNull(request.getParameter("executor")),new Integer(0));
service_man2 = Utility.parseInt(Utility.trimNull(request.getParameter("service_man2")),new Integer(0));

custServiceLogVO.setCustId(cust_id);
custServiceLogVO.setCustNo(cust_no);

SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
custServiceLogVO.setServiceTime(df.format(new Date()));
custServiceLogVO.setServiceInfo(serviceInfo);
custServiceLogVO.setServiceSummary(serviceSummary);
custServiceLogVO.setInputMan(input_operatorCode);
custServiceLogVO.setSubject(subject);
custServiceLogVO.setContent(content);
custServiceLogVO.setStep_flag(step_flag);
custServiceLogVO.setInfo_level(info_level);
custServiceLogVO.setExecutor(executor);
custServiceLogVO.setServiceMan(service_man2);
custServiceLogLocal.append(custServiceLogVO);

custServiceLogLocal.remove();
%>