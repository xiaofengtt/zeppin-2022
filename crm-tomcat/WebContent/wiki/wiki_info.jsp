<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
String q_faq_keywords = Utility.trimNull(request.getParameter("q_faq_keywords"));
String sort_field = Utility.trimNull(request.getParameter("sort_field"));

String faq_title = null;
String faq_content = null;
String faq_keywords = null;
String input_time = null;
String popularity_rating  = null;

FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();
vo.setSerial_no(serial_no);
vo.setInput_man(input_operatorCode);
List list = faqs.listBySql(vo);
sUrl = sUrl;

//附件相关变量
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(56));
attachmentVO.setInput_man(input_operatorCode);
List attachmentList=null;
Map attachmentMap=null;
String attachmentId="";
String origin_name="";
String save_name="";
attachmentList=attachmentLocal.load(attachmentVO);
%>
<HTML>
<HEAD>
<TITLE>知识库查看</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<STYLE type=text/css>
<!--
body,td{font-family:arial}
TD{FONT-SIZE:9pt;LINE-HEIGHT:25px}
.cred{color:#FF0000}
-->
</STYLE>

<script language="javascript">
function editInfo(serial_no){
	  location = 'wiki_info_update.jsp?serial_no='+serial_no+'&faq_class_no=<%=faq_class_no%>&q_faq_keywords=<%=q_faq_keywords%>';
}
function deleteInfo(){
	if(sl_confirm("删除该条记录")){
		document.theform.action = "wiki_remove.jsp?faq_class_no=<%=faq_class_no%>";
		document.theform.submit();
	}
}
function supportInfo(support_flag){
	var support_content  = document.getElementById('unsupport_id').innerHTML;
	if(support_flag ==1 )
		support_content = document.getElementById('support_id').innerHTML;
	if(sl_confirm("提交 '"+support_content+"' 的意见")){
		document.theform.support_flag.value = support_flag;
		document.theform.action = "wiki_support.jsp?serial_no=<%=serial_no%>&faq_class_no=<%=faq_class_no%>";
		document.theform.submit();
	}
}

/*查看附件方法*/
function DownloadAttachment(attachmentId){
	var url = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;		
	window.location.href = url;	
}
</script>
</HEAD>
<BODY class="BODY body-nox" leftmargin="0" topmargin="10" marginwidth="0" marginheight="0">
<form name="theform" method="post" action="">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="support_flag" value="">
<table align="center" width="98%" >
	<tr>	
		<td>
		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td class="page-title">			
					<font color="#215dc6"><b><%=menu_info%></b></font>
				</td>
			</tr>
		</table>
		<br/>
		<%if(list.size()>0){
		    Map map = (Map)list.get(0);
			faq_title = Utility.trimNull(map.get("FAQ_TITLE"));
			faq_content = Utility.trimNull(map.get("FAQ_CONTENT"));
			faq_keywords = Utility.trimNull(map.get("FAQ_KEYWORDS"));
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			input_time =  Utility.trimNull(map.get("INPUT_TIME"));
			popularity_rating  =  Utility.trimNull(map.get("POPULARITY_RATING"));
			Integer support_times = Utility.parseInt(Utility.trimNull(map.get("SUPPORT_TIMES")),new Integer(0));
			Integer oppose_times = Utility.parseInt(Utility.trimNull(map.get("OPPOSE_TIMES")),new Integer(0));
			Integer input_man = Utility.parseInt(Utility.trimNull(map.get("INPUT_MAN")),new Integer(0));
			if(input_time.length() > 19){
					input_time = input_time.substring(0,19);
				}
			 %>
			<table align="center" width="100%" cellspacing="0" cellpadding="2" border="0" class="product-list">
				<tr>
				    <td align="center" colspan="4"><font size ="4"><%=faq_title%></font></td>
				</tr>
				<tr>
					<td align="left" colspan="2" width="39%">&nbsp;</td>	
				    <td align="center"><%=input_time%></td>
					<td align="right">
						<%if(input_man.intValue() == input_operatorCode.intValue()){ %>
							<%if(input_operator.hasFunc(menu_id, 100)){%>
						<a href="#" onclick="javascript:editInfo('<%=serial_no%>');">编辑</a>&nbsp;&nbsp;&nbsp;
						<a href="#" onclick="javascript:deleteInfo('<%=serial_no%>');">删除</a>
						<%}} %>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
				    <td align="left" colspan="4"><%=faq_content%></td>
				</tr>
				<tr>
			</table>
		<table align="center" width="100%" cellspacing="0" cellpadding="2" border="0" class="product-list">
		<br>
<%
	for(int i=0;i<attachmentList.size();i++){
		attachmentMap=(Map)attachmentList.get(i);
		attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
		origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
	   	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
%>		<tr>	
			<td  align="left" width="7%">附件<%=i+1%>：</td>
			<td colspan="3" width="*">
				<a href="#" onclick="javascript:DownloadAttachment(<%=attachmentId%>)"><%= origin_name%></a>
			</td>
		</tr>
<%	}
 %>
		
	</tr>
				<tr>
					<td align="left" colspan="3">&nbsp;</td>	
					<td align="right">
						<a href="#" onclick="javascript:supportInfo(1);" id ="support_id">这篇文章有用</a>&nbsp;(<%=support_times %>)&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
						<a href="#" onclick="javascript:supportInfo(2);" id ="unsupport_id">这篇文章没用</a>&nbsp;(<%=oppose_times %>)&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>	
			</table>
		<%} %>
			<hr size="1" width="100%" color="#0066CC">
			<table align="center" width="98%" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td align="right" style="LINE-HEIGHT:10px">
						<button type="button"  class="xpbutton3" accessKey="b" onclick="javascript:location='wiki_list.jsp?menu_id=<%=faq_class_no %>'">返回(<u>B</u>)</button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</BODY>
</HTML>