<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
int cell_id = 0;

boolean successFlag = false;
String cell_name = Utility.trimNull(request.getParameter("cell_name"));
String cell_code = Utility.trimNull(request.getParameter("q_cell_code"));
session.setAttribute("QUERY_CELL_NAME", cell_name); 
session.setAttribute("QUERY_CELL_CODE", cell_code);

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
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
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
	    
	    if(document.theform.cell_id.value=="0"||document.theform.cell_id.value=="/marketing/synthesis/report_tree.jsp#"){
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
function queryCode(value){
	if(event.keyCode == 13 && value != ""){
		query();	
	}	
}
</SCRIPT>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin=0>
<form name="theform" method="POST">
<input type="hidden" id="itemID" value="">
<input type="hidden" name="cell_id" value="">
<input type="hidden" name="parent_id" value="">
<input type="hidden" name="sub_display" value="">
<input type="hidden" name="leafflag" value="">
<input type="hidden" name="flag" value="">
<TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>
	<tr>
		<TD>
      		<TABLE  class=flyoutMenu ></table>
    	</TD>
    </tr>	
    <tr>
		<td colspan="3"><font color="#215dc6"><b><img border="0" src="/images/member.gif"  width="32">产品单元选择</b></font></td>
	</tr>
	<tr>
		<td align="right">产品单元编号:</td>
		<td><input type="text" name="q_cell_code" onkeydown="javascript:queryCode(this.value);" value="<%=Utility.trimNull(cell_code)%>"  size="10"></td>
	</tr><tr>
		<td align="right">产品单元名称:<td>
			<input type="text" name="cell_name" value="<%=Utility.trimNull(cell_name)%>"  size="40">
		</td>
		<td><button type="button"  class="xpbutton3" onclick="javascript:query();" accessKey=Q name="btnQuery">查询(<u>Q</u>)</button></td>
	</tr>
	<tr>
		<td align="right"><b>选择的产品单元:</b></td><td> <input type="text" name="cell_code" size="40" readonly >
		</td><td>
			<button type="button"  class="xpbutton3" onclick="javascript:pressok();" accessKey=o name="btnOk">&nbsp;&nbsp;确定(<u>O</u>)</button>
			&nbsp;
			<button type="button"  class="xpbutton3" accessKey=c name="btnCancel" title="取消" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
		</td>
	</tr> 
	<tr>
		<td colspan="3">
		<table border="0" width="100%" cellpadding="4">
		
			<tr>
				<td valign="top">
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
