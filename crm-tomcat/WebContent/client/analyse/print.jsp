<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%int Print_flag = Utility.parseInt(request.getParameter("Print_flag"),1);

StringBuffer list = new StringBuffer(200);  //每页打印个数
Argument.appendOptions(list, 1, "每张打印1条", Print_flag);
Argument.appendOptions(list, 2, "每张打印2条", Print_flag);
Argument.appendOptions(list, 3, "每张打印3条", Print_flag);
Argument.appendOptions(list, 4, "每张打印4条", Print_flag);
%>
<html>
<head>
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
			<td width="100%" align="center">									
			<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:if(confirm('确认要打印吗？'))	{	document.all.WebBrowser.ExecWB(6,6);}">直接打印(<u>P</u>)</button>
			&nbsp;&nbsp;&nbsp;			
			<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:window.returnValue=null;window.close();">返回(<u>B</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<select size="1" name="Print_flag" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:document.theform.submit();" style="WIDTH: 111px; height:20px">
					<%=list.toString()%>
			</select>
			</td>
		</tr>
</table>
<%
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
Integer strID = null;
Integer print_post_info = new Integer(0);
String[] s = request.getParameterValues("cust_id");	
int count = 0;	
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
				vo.setInput_man(input_operatorCode);
				vo.setCust_id(strID);
				List cust_list = local.listProcAll(vo);
				Iterator cust_it = cust_list.iterator();
				Map cust_map = new HashMap();
				//根据客户ID查询该客户所购信托产品的名称
				ContractLocal contract = EJBFactory.getContract();
				String product_name = "";
				while (cust_it.hasNext())
				{		
					cust_map = (Map)cust_it.next();			
					product_name = Utility.trimNull(contract.getProductNameByCustId(Utility.parseInt(Utility.trimNull(cust_map.get("CUST_ID")), new Integer(0))))+","+product_name;
					print_post_info = Utility.parseInt(Utility.trimNull(cust_map.get("PRINT_POST_INFO")),new Integer(0));//从客户信息中获取时候时候打印ems,披露信息所用
				}	
					if(Print_flag==2&&n%2!=0) 	//一张打印2条时的判断
					{
						for(int m = 0;m < 26; m++) 
							out.print("<br>");
					}
					else if(Print_flag==4&&n>0&&n%2==0&&n%4!=0)	//一张打印4条时的判断,2/1分页
					{
						for(int m = 0;m < 5; m++) 
						out.print("<br>");					
					}
					else if(Print_flag==4&&n>0&&n%2!=0) //一张打印4条时的判断,4/1分页	
					{
						for(int m = 0;m < 2; m++) 
							out.print("<br>");
					}
					if(print_post_info.intValue() ==1)	{
						count++;
					
							
%>
<table <%if(n>0&&(n%Print_flag==0)) out.print("style='page-break-before:always'");%> border="0" width="100%" cellspacing="0" cellpadding="4" height="20%">
	<%if(n%Print_flag==0)
		out.print("<br>");
	%>
	<tr>
		<td width="6%" align="right"  style="height:20mm"></td>
		<td width="94%" colspan="3" align="left">&nbsp;</td>
	</tr>	
	<tr>		
			<td width="6%" align="right" style="height:8mm;"></td>
			<td width="40%" align="left"><font size="3" face="宋体">邮政编码:<b><%=Utility.trimNull(cust_map.get("POST_CODE"))%></b></font></td>
			<td width="15%"  align="left" >&nbsp;</td>
			<td width="45%"  align="left"></td>
		</tr>
		<tr>		
			<td width="6%" align="right" style="height:8mm;"></td>
			<td width="40%" align="left"><font size="3" face="宋体"><%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></font></td>
			<td width="15%"  align="left" height="3%">&nbsp;</td>
			<td width="49%" align="left" height="3%" rowspan="3">
			<FONT size="1"></FONT></td>
		</tr>
		<tr >		
			<td width="6%" align="right" style="height:8mm;"></td>
			<td width="40%" align="left"><font size="3" face="宋体"><b><%=Utility.trimNull(cust_map.get("CUST_NAME"))%></b>&nbsp; 
			<!--如果是个人-->
			<%if(Utility.parseInt(Utility.trimNull(cust_map.get("CUST_TYPE")),new Integer(0)).equals(new Integer(1)))
			{	
				if(Utility.parseInt(Utility.trimNull(cust_map.get("SEX")),new Integer(0)).equals(new Integer(1))) out.print("先生"); else out.print("女士");
			}%> &nbsp;&nbsp;&nbsp;</font></td>
			<td width="15%"  align="left">&nbsp;</td>
		</tr>
	<tr>
		<td width="6%" align="right" height="3%">&nbsp;</td>
		<td width="40%" align="left" height="3%">&nbsp;</td>
		<td width="15%"  align="left" height="3%">&nbsp;</td>
	</tr>
	</table>
<%				}		
			}
		}						
	}
}
local.remove();
%>
<span class="Noprint">打印总数:<%=count%></span>
</form>
</body>
</html>

