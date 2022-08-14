<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0)); 
Integer showFlag = Utility.parseInt(request.getParameter("showFlag"),new Integer(1)); 

ProductInfoReposLocal prod_info = EJBFactory.getProductInfoRepos();
ProductVO vo = new ProductVO();

Map p_map = new HashMap();
Map i_map = new HashMap();
if(preproduct_id.intValue()>0 || product_id.intValue()>0){
	vo.setProduct_id(product_id);
	vo.setPreproduct_id(preproduct_id);
	vo.setInput_man(input_operatorCode);
	List list = prod_info.listBySql(vo);
	if(list.size()>0)
		p_map = (Map)list.get(0); 

	list = prod_info.queryIntroMaterial(vo);
	if (list.size()>0) 
		i_map = (Map)list.get(0);

	String s = Utility.trimNull(i_map.get("STUDY_VOICE"));
	if (! s.equals(""))
		i_map.put("STUDY_VOICE_ORIGIN_NAME", s.substring(s.lastIndexOf("//")+2, s.lastIndexOf('~')));
}
%>
<HTML>
<head>
<title>产品推介资料</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
</HEAD>
<BODY class="BODY">
<table border="0" width="97%" cellspacing="0" cellpadding="0" height="100%">
	<tr>
		<td colspan="6" align="center"><font size="4"><b><%=Utility.trimNull(p_map.get("PRODUCT_NAME"))%></b></font></td>
	</tr>

	<tr><td>
<div align="center" style="margin-left:10px;margin-right:10px;">
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="right" width="30%">售前培训会录音:</td>
			<td align="left" width="*">
		<% if (! Utility.trimNull(i_map.get("STUDY_VOICE")).equals("")) { %>			
				<%-- %>embed src="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(i_map.get("STUDY_VOICE")),"\\","/")%>&name=<%=Utility.trimNull(i_map.get("STUDY_VOICE_ORIGIN_NAME"))%>"
					autostart="false" loop="false" EnableContextMenu="false"/--%>
				<%=Utility.trimNull(i_map.get("STUDY_VOICE_ORIGIN_NAME"))%><br/>
				<object classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95">
					<param name="FileName" value="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(i_map.get("STUDY_VOICE")),"\\","/")%>&name=<%=Utility.trimNull(i_map.get("STUDY_VOICE_ORIGIN_NAME"))%>" />
					<param name="autoStart" value="false">	 
					<param name="EnableContextMenu" value="false">
					<param name="ShowStatusBar" value="true">
				</object>
		<% }  %>
			</td>
		</tr>
	</table>
</div>
<div align="center" style="margin-right:5px;" >
	<table border="0" width="100%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="right"><button type="button"  class="xpbutton3" accesskey=c  onclick="javascript:window.close();">关闭(<u>C</u>)</button></td>
		</tr>
	</table>	
</div>
	</td></tr>
</table>
</BODY>
</HTML>