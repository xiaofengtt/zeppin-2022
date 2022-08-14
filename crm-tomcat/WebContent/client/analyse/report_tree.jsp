<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@page import="enfo.crm.intrust.ProductCellLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
int cell_id = 0;

boolean successFlag = false;
String cell_name = Utility.trimNull(request.getParameter("cell_name"));
session.setAttribute("QUERY_CELL_NAME", cell_name); 
int flag = Utility.parseInt(request.getParameter("flag"),0); 
if(request.getMethod().equals("POST") && flag==2)
{
	cell_id = Utility.parseInt(request.getParameter("cell_id"),0);
	session.setAttribute("SESSION_CELL_ID",new Integer(cell_id));	//将CELL_ID保存到SESSION中
	successFlag = true;
}	

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/mstree/deeptree.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<BASE TARGET="_self">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%if(successFlag) {%>
	window.returnValue = <%=cell_id%>;
	window.close();
<%}%>

var depart_id = "";
var zc_flag="";
var depart_name = "";


function pressok()
{

  if(document.theform.cell_id.value=="")
  {
     alert("没有选择产品单元！");
     return;
  }
    if(document.theform.cell_id.value=="0")
  {
     alert("该级别的产品单元不能选择！");
     return;
  }		 
  //window.returnValue = document.theform.cell_id.value;
  //window.close();
  document.theform.flag.value = 2;
  document.theform.submit();  
 
}
function getselhref()
{
	
	if(event.srcElement == null)         return;
	var obj = event.srcElement;
	var hrefs = obj.href;
	if (hrefs == null || hrefs == "")
		return false;
	
	cellid = hrefs.indexOf("#")+1;
	startcode = hrefs.indexOf("α")+1;
	endcode = hrefs.indexOf("ε");
	
	cell_id = hrefs.substring(cellid,startcode-1);
	cell_code = hrefs.substring(startcode,endcode);
	
    document.theform.cell_code.value = cell_code;
	document.theform.cell_id.value = cell_id;

	return true;	
}
function getDbclick()
{
	 if(getselhref()!=false)
	 {	
	    
	    if(document.theform.cell_id.value=="0"||document.theform.cell_id.value=="/financing/totalinfo/report_tree.jsp#"){
	      return;
	    }
	 	//window.returnValue = document.theform.cell_id.value; 
	 	//window.close();
	 	document.theform.flag.value = 2;
	 	document.theform.submit();	 	
  	 }
}
function refresh()
{
	document.theform.submit(); 
}

function query()
{
	document.theform.flag.value = 1;
	document.theform.submit(); 
}

function back()
{
	if(event.keyCode == 13)
	{	
		document.theform.flag.value = 1;
		document.theform.submit(); 
	}
}

</SCRIPT>
</HEAD>
<BODY class="body body-nox"leftMargin=0 topMargin=0 rightmargin=0>
<form name="theform" method="POST">
<input type="hidden" id="itemID" value="">
<input type="hidden" name="cell_id" value="">
<input type="hidden" name="parent_id" value="">
<input type="hidden" name="sub_display" value="">
<input type="hidden" name="leafflag" value="">
<input type="hidden" name="flag" value="">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<tr>
		<TD>
      		<TABLE  class=flyoutMenu ></table>
    	</TD>
    </tr>	
    <tr>
		<td colspan=4 class="page-title"> <font color="#215dc6"><b>产品单元选择</b></font></td>
	</tr>
	<tr><td><br/></td></tr>
	<tr class="product-list">
		<td colspan="4">&nbsp;&nbsp;&nbsp;产品单元名称:
			<input type="text" name="cell_name" value="<%=Utility.trimNull(cell_name)%>"  size="40" onkeydown="javascript:back()">
			<button type="button"  class="xpbutton3" onclick="javascript:query();" accessKey=Q name="btnQuery">查询(<u>Q</u>)</button>
		</td>
		
	</tr>
	<tr class="product-list">
		<td><br/></td>
	</tr>
	<tr class="product-list">
		<td colspan=4> 选择的产品单元:&nbsp;<input type="text" name="cell_code" size="40" readonly >
			<button type="button"  class="xpbutton3" onclick="javascript:pressok();" accessKey=o name="btnOk">&nbsp;&nbsp;确定(<u>O</u>)</button>
			&nbsp;
			<button type="button"  class="xpbutton3" accessKey=c name="btnCancel" title="取消" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
			&nbsp;
		</td>
	</tr> 
	<tr>
		<td>
		<table border="0" width="100%" cellpadding="4">
		
			<tr>
				<td valign="top" colspan=4>
				<div id="deeptree" class="deeptree" CfgXMLSrc="maptree.xml" style="width: 100%" onclick="javascript:getselhref();" onDBlclick="javascript:getDbclick();" ></div>
				</td>
			</tr>
			
		</table>
		</td>
	</tr>
</TABLE>
</form>
</BODY>
</HTML>
