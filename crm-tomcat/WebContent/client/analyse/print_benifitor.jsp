<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.intrust.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
StringBuffer sb = new StringBuffer(200);  //每页打印个数
%>
<html>
<head>
<TITLE>打印客户受益分配明细</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<!--media=print 这个属性可以在打印时有效-->
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<script language="javascript">

</script>
</head>
<body topmargin="8" leftmargin="8" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform"  method="post">
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
<table border="0" width="100%" class="Noprint" id="btnprint">
		<tr>
			<td width="100%" align="right">									
			<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:if(confirm('确认要打印吗？'))	{	document.all.WebBrowser.ExecWB(6,6);}">直接打印(<u>P</u>)</button>
			&nbsp;&nbsp;&nbsp;			
			<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:window.returnValue=null;window.close();">返回(<u>B</u>)</button>
			&nbsp;&nbsp;&nbsp;
		
			</td>
		</tr>
</table>
<%
BenifitorLocal local = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();
Integer strID = null;
Map map = new HashMap();

String [] s = request.getParameterValues("cust_id");	

if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{  	
		String[] paras = Utility.splitString(s[i], ",");
		for(int n = 0;n < paras.length;n++)
		{			
			strID = new Integer(paras[n].trim());		
			if(!strID.equals(""))
			{		
				vo.setBook_code(new Integer(1));
				vo.setCust_id(strID);
				vo.setInput_man(input_operatorCode);
				vo.setProduct_id(new Integer(0));
				vo.setContract_bh(null);
				vo.setList_id(new Integer(0));
				vo.setCust_name(null);
				List list = local.queryDetail(vo);
				if(list != null && list.size() > 0)
					map = (Map)list.get(0);
%>							
				
<table <%if(n>0) out.print("style='page-break-before:always'");%> border="1" width="100%" cellspacing="0" cellpadding="4" height="20%">

	<tr>		
		<td align="left" height="8%" width="13%">姓名:</td>
		<td align="left" height="3%" width="17%"><font size="3" face="宋体"><b><%=Utility.trimNull(map.get("SY_CUST_NAME"))%>&nbsp;</b></font>
		<td align="left" height="8%" width="13%">地址:</td>
		<td align="left" height="3%" width="17%" colspan="4"><font size="3" face="宋体"><b><%=Utility.trimNull(map.get("SY_ADDRESS"))%>&nbsp;</b></font>
	</tr>
	<tr>
		<td  align="center" height="25">合同编号</td>
		<td  align="center" height="25">项目名称</td>
		<td  align="center" height="25" width="124">信托期限</td>
		<td  align="center" height="25" width="106">分配周期</td>
		<td  align="center" height="25" width="118">预期收益率(%)</td>
		<td  align="center" height="25" width="156">开户银行</td>
		<td  align="center" height="25" width="117">帐号</td>
	</tr>
	<%
	if(list != null && list.size() > 0){					
	%>
	<tr>
		<td align="left" height="25"><%=Utility.trimNull(map.get("CONTRACT_BH"))%>&nbsp;</td>
		<td align="left" height="25"><%=Utility.trimNull(map.get("PRODUCT_NAME"))%>&nbsp;</td>
		<td align="right" height="25" width="124"><%=Utility.trimNull(map.get("PRODUCT_QX"))%>&nbsp;</td>
        <td align="left" height="25" width="106"><%=Utility.trimNull(map.get("PRODUCT_PERIOD"))%>&nbsp;</td>
        <td align="left" height="25" width="118"><%=Utility.trimNull(map.get("PRODUCT_EXP_RATE"))%>&nbsp;</td>
        <td align="left" height="25" width="156"><%=Utility.trimNull(map.get("BANK_NAME"))%>&nbsp;</td>
        <td align="left" height="25" width="117"><%=Utility.trimNull(map.get("BANK_ACCT"))%>&nbsp;</td>
	</tr>
	<%			
	 }%>
	</table> 
	<br>
<%
			}
		}						
	}
}
local.remove();
%>
</form>
</body>
</html>

