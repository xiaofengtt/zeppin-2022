<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//ȡ��ҳ���ѯ����
String q_temp_title = Utility.trimNull(request.getParameter("q_temp_title"));
String q_temp_type = Utility.trimNull(request.getParameter("q_temp_type"));
//url����
String tempUrl = "";
tempUrl = tempUrl+"&q_temp_title="+q_temp_title;
tempUrl = tempUrl+"&q_temp_type="+q_temp_type;
sUrl = sUrl + tempUrl;
//ҳ���ø�������
int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
//��ö���
SmsTemplateLocal smsTemplocal = EJBFactory.getSmsTemplate();
SmsTemplateVO vo = new SmsTemplateVO();
vo.setTitle(q_temp_title);
vo.setTempType(q_temp_type);
IPageList pageList = smsTemplocal.listAll(vo,t_sPage,t_sPagesize);
//��ҳ��������
List list = pageList.getRsList();
Map map = null;
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.smdTemplateSelection",clientLocale)%> </title><!--����ģ��ѡ��-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*��ѯ����*/
function QueryAction(){
	var form = document.getElementsByName("theform")[0];
	form.action = "smsTemplate_choose.jsp";
	form.submit();	
}
//ˢ��
function refreshPage(){
	 QueryAction();
}
function chooseAction(tempID,title){
	window.returnValue = tempID+"_"+title;
	window.close();
}
</script>
</head>
<body class="body body-nox">
<form id="theform" name="theform" method="post">
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=LocalUtilis.language("menu.smdTemplateSelection",clientLocale)%> </b></font><!--����ģ��ѡ��-->
	</div>	
	<div align="right" class="btn-wrapper">
		<select name="q_temp_type" id="q_temp_type" onchange="javascript:QueryAction();" onkeydown="javascript:nextKeyPress(this)" style="width:180px">	
			<%= Argument.getDictParamOptions(4038,q_temp_type) %>
		</select>
	</div>
<br/>
	<div align="center">
		<table border="0" width="575px" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trtagsort">
				 <td width="30px" align="center"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--���-->
				 <td width="20%" align="center"><%=LocalUtilis.language("class.templateTitle",clientLocale)%> </td><!--ģ�����-->
		         <td width="20%" align="center"><%=LocalUtilis.language("class.tempTypeName",clientLocale)%> </td><!--ģ�����-->	         
		         <td width="*" align="center"><%=LocalUtilis.language("class.templateContent",clientLocale)%> </td><!--ģ������-->
		     </tr>
		</table>
		<span id="tableList" style="overflow-y:auto;height:250;">
			<table border="0" width="575px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
				<%
				//�����ֶ�
				Iterator iterator = list.iterator();
				Integer tempID = new Integer(0);
				String title = "";
				while(iterator.hasNext()){
					iCount++;
					map = (Map)iterator.next();
					tempID = Utility.parseInt(Utility.trimNull(map.get("TempID")),new Integer(0));
					title = Utility.trimNull(map.get("Title"));
				 %>
                <!--˫��ѡ�ж���ģ��-->
                <tr class="tr<%=iCount%2%>" style="cursor:hand;" title="<%=LocalUtilis.language("message.doubleClickSelectTemp",clientLocale)%> " onDBlclick="javascript:chooseAction('<%=tempID%>','<%=title%>');">
					<td width="30px" align="center"><%=Utility.parseInt(Utility.trimNull(map.get("TempID")),new Integer(0))%></td> 
					<td width="20%" align="center"><%= Utility.trimNull(map.get("Title"))%></td> 
					<td width="20%" align="center"><%= Utility.trimNull(map.get("TempTypeName"))%></td> 
					<td width="*" align="center"><%= Utility.trimNull(map.get("Content"))%></td> 
				</tr>
				<%}%>
				<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  
				 <tr class="tr0">
				        <td width="30px" align="center"></td>
						<td width="20%" align="center"></td>
				        <td width="20%" align="center"></td>
				        <td width="*" align="center"></td>                                 
				  </tr>           
				<%}%>
			</table>
		</span>	
		<table border="0" width="575px" class="page-link">
			<tr valign="top">
				<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
			</tr>
		</table>
	</div>
</form>
</body>
</html>