<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
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
}
prod_info.remove();
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>
<script type="text/javascript">
window.onload = function(){
		show(<%=showFlag%>);
	};

function show(parm){
   for (var i=1; i<=2; i++) {
	     if(i!= parm){
	      	document.getElementById('d'+i).background = '<%=request.getContextPath()%>/images/headdark_00_01.gif';
	      	if (document.getElementById("r"+i)) document.getElementById('r'+i).style.display = 'none';
		 } else {
		   	document.getElementById('d'+i).background = '<%=request.getContextPath()%>/images/head_00_01.gif';
		    if (document.getElementById("r"+i)) document.getElementById('r'+i).style.display = '';
		 }
	}
	document.theform.showFlag.value = parm;
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform">
<input type="hidden" name="showFlag"/>
<table border="0" width="97%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="6"><br><br></td>
	</tr>
	<tr>
		<td colspan="6" align="center"><font size="4"><b><%=Utility.trimNull(p_map.get("PRODUCT_NAME"))%></b></font></td>
	</tr>

	<tr><td>
<div align="left"  style="margin-left:10px;margin-bottom:10px;">
	<TABLE cellSpacing=0 cellPadding=0 width="95%" border="0" class="edline">
		<TBODY>
			<TR>
				<TD vAlign=top>&nbsp;</TD>
				<TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
				    &nbsp;&nbsp;&nbsp;产品说明书 </TD>
                <TD id="d2" style="background-repeat: no-repeat;" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
					&nbsp;&nbsp;&nbsp;推介通知单 </TD>
			</TR>
		</TBODY>
	</TABLE>
</div>
<div id="r1" align="center" style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="center"><textarea name="product_desc" cols="120" rows="50" readonly class="ednone"><%=Utility.trimNull(i_map.get("PRODUCT_DESC"))%></textarea></td>
		</tr>
	</table>
</div>
<div id="r2" align="center" style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="center"><textarea name="notices" cols="120" rows="50" readonly class="ednone"><%=Utility.trimNull(i_map.get("NOTICES"))%></textarea></td>
		</tr>
	</table>
</div>
<div align="center" style="margin-right:5px;margin-top:5px;" >
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="right"><button type="button"  class="xpbutton3" accesskey=c  onclick="javascript:window.close();">关闭(<u>C</u>)</button></td>
		</tr>
	</table>	
</div>
	</td></tr>
</table>
</form>
</BODY>
</HTML>