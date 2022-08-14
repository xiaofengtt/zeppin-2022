<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
	//初始化参数
	ConfigLocal configLocal = EJBFactory.getConfig();
	List catalogList = configLocal.listBySql("");
	Integer identity_code = Utility.parseInt(request.getParameter("identityCode"),new Integer("0"));
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>

function StartCon(){
	var catalogCode = document.theform.catalog_code.value;
	if(catalogCode == ''){
		alert('请选择要素类别！');
	}else{
		if(showModalDialog('/system/config/interface_apply_info.jsp?identityCode=<%=identity_code%>&catalog_code='+catalogCode, '', 'dialogWidth:960px;dialogHeight:660px;status:0;help:0') == 1)
		{
			window.close();
			window.returnValue = 1;
		}
	}
	
}

</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="">
<table border="0" width="100%" align="center" cellspacing="0" cellpadding="0">
<tr>
	<td>
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="0">  				
		<tr>
			<td>请选择要素类别：</td>
		</tr>
	</table> 
	</td>
</tr>
	<!-- 要加入的查询内容 -->
<tr>
	<td>
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
	
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td align="left">要素类别:</td>
			<td>
				<SELECT size="1" name="catalog_code" style="width: 120px" onkeydown="javascript:nextKeyPress(this)">
					<%
					if(catalogList != null)
					{
					%>
						<option value="" >请选择</option>
						<%for(int i=0;i < catalogList.size();i++)
						{
							Map  rowMap = (Map)catalogList.get(i);
							String catalogName = Utility.trimNull(rowMap.get("CATALOG_NAME"));
							String catalogCode = Utility.trimNull(rowMap.get("CATALOG_CODE"));
						%>
							<option  value="<%=catalogCode%>" ><%=catalogName%></option>
					<%	}
					}%>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td height="25"></td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartCon();">确定(<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">返回(<u>C</u>)</button>
			</td>
		</tr>			
	</table>
	</td>
</tr>
</table>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%configLocal.remove();%>

