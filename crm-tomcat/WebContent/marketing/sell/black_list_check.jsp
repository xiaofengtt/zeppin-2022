<%@page contentType="text/html; charset=GBK" import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面参数
String cust_id = Utility.trimNull(request.getParameter("cust_id"));//客户ID
String full_name_c = Utility.trimNull(request.getParameter("full_name_c"));//中文全称
String country = Utility.trimNull(request.getParameter("country"));//
//String full_name_e = Utility.trimNull(request.getParameter("full_name_e"));//英文全称
String card_type = Utility.trimNull(request.getParameter("card_type"));//
String card_no = Utility.trimNull(request.getParameter("card_no"));//
String category_no = Utility.trimNull(request.getParameter("category_no"),"");//类别编号

boolean bSuccess = false;
//获得对象
IntrustHmdLocal hmd = EJBFactory.getIntrustHmdLocal();
HmdVO vo = new HmdVO();
Map map        = null;
Map map_bm     = null;
List list      = null;
List list_bm   = null;
Iterator it_bm = null;

//设置变量
vo.setCard_no(card_no);
//vo.setCountry(country);
vo.setFull_name_c(full_name_c);
vo.setCard_type(card_type);
vo.setCard_no(card_no);
//vo.setCategory_no(category_no);

list = hmd.queryAll(vo);
%>
<HTML>
<HEAD>
<TITLE>匹配的黑名单信息</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>

//响应查看详细信息动作
function setiteminfor(tr10,tablePro,flagdisplay,imagex)
{
    i= flagdisplay.value;
   
    if(i==0)
    {
      tablePro.style.display="";
      tr10.style.display="";
      flagdisplay.value=1;
      imagex.src='/images/up_enabled.gif';
    }
    else if(i==1)
    {
       tablePro.style.display="none";
        tr10.style.display="none";
      flagdisplay.value=0;
      imagex.src='/images/down_enabled.gif';
    }
}
//确认黑名单
function func_hmdqr(id){

	document.theform.submit();
	window.returnValue = id;
	//window.close();
}

<%if (bSuccess){%>
	sl_update_ok();
	window.close();
<%}%>

</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="black_list_check_modi.jsp">
<input type="hidden" name="cust_id" value="<%=cust_id%>">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
    
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
					<%
					Iterator it = list.iterator();
					if (it.hasNext()){
					
					%>
					<table border="0" width="100%">
						<tr>
							<td align="right">
							<button type="button"  class="xpbutton3" accessKey=s id="hmdqr" name="hmdqr" onclick="javascript:func_hmdqr(1);">确定(<u>S</u>)</button>&nbsp;
							<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
							</td>
						</tr>
					</table>
					<table id="table3" border="0" cellspacing="1" cellpadding="2"class="tablelinecolor" width="100%" sort="multi">
						<tr class="trh">
							<td>中文全称</td>
							<td>英文全称</td>
							<td>证件类型</td>
							<td>证件编号</td>
							<td>所属国家</td>
							<td>查看化名</td>
						</tr>
						<%
						int iCount = 0;
						int iCurrent = 0;
						String main_id = "";
						
						
						while (it.hasNext()) {
							map      = (Map) it.next();
							main_id  = Utility.trimNull(map.get("SERIAL_NO"));
						%>
						<tr class="tr<%=(iCurrent % 2)%>">
							<td class="tdh"><%=Utility.trimNull(map.get("FULL_NAME_C"))%></td>
							<td align="left"><%=map.get("FULL_NAME_E")%></td>
							<td align="center"><%=Argument.getCardTypeName(Utility.trimNull(map.get("CARD_TYPE")))%></td>
							<td align="left"><%=Utility.trimNull(map.get("CARD_NO"))%></td>
							<td align="center"><%=Utility.trimNull(map.get("COUNTRY"))%></td>
							<td align="center">
							<button type="button"  class="xpbutton2" name="btnsetinital" onclick="javascript:setiteminfor(tr<%=main_id%>,tablePro<%=main_id%>,document.theform.flag<%=main_id%>,document.theform.image<%=main_id%>);">
							<IMG id="image<%=main_id%>" src="/images/down_enabled.gif" width="7" height="9"></button>
							<input type="hidden" name="flag<%=main_id%>" value="0">
							</td>
						</tr>
						<tr id="tr<%=main_id%>" style="display: none">
							<td align="center" bgcolor="#FFFFFF" colspan="10" >
								<table id="tablePro<%=main_id%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
									<tr>
										<td bgcolor="#FFFFFF" align="center">中文化名(曾用名)</td>
										<td bgcolor="#FFFFFF" align="center">中文化名简称</td>
										<td bgcolor="#FFFFFF" align="center">英文化名</td>
										<td bgcolor="#FFFFFF" align="center">英文化名简称</td>
									</tr>
									<%
									IntrustHmdLocal alias = EJBFactory.getIntrustHmdLocal();
									HmdVO vo2 = new HmdVO();
									vo2.setBirth_name_id(new Integer(main_id));
									list_bm = alias.query_bm(vo2);
									it_bm = list_bm.iterator();
									
									if(list_bm != null && list_bm.size() > 0){
										//int i = 0;
										while(it_bm.hasNext()){
											//i++;
											map_bm = (Map)it_bm.next();
									%>
									<tr>
										<td bgcolor="#FFFFFF" align="center"><%=Utility.trimNull(map_bm.get("FULL_NAME_C"))%></td>
										<td bgcolor="#FFFFFF" align="center"><%=Utility.trimNull(map_bm.get("FOR_SHORT_C"))%></td>
										<td bgcolor="#FFFFFF" align="center"><%=Utility.trimNull(map_bm.get("FULL_NAME_E"))%></td>
										<td bgcolor="#FFFFFF" align="center"><%=Utility.trimNull(map_bm.get("FOR_SHORT_E"))%></td>
									</tr>
									<%}
									}
									alias.remove();
									%>
								</table>
							</td>
						</tr>
						<%iCurrent++;}%>

					</table>
					<%}else{%>
					<table width="100%" align=center>
						<tr align="center"><td><b>此客户不在黑名单之列</b></td></tr>
						<tr>
							<td align="right">
							<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:window.returnValue=null;window.close();">确定(<u>S</u>)</button>
							</td>
						</tr>
					</table>
					<%}%>
	  			</TD>
			</TR>
		</TABLE>
	  
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%hmd.remove();%>
