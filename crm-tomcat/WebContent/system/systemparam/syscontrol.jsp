<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer is_modi = Utility.parseInt(request.getParameter("is_modi"),new Integer(1));
String type_name = Utility.trimNull(request.getParameter("type_name"));
String flag_type = Utility.trimNull(request.getParameter("flag_type"));
String summary = Utility.trimNull(request.getParameter("summary"));

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

vo.setFlag_type(flag_type);
vo.setIs_modi(is_modi);
vo.setType_name(type_name);
vo.setSummary(summary);
String[] totalColumn=new String[0];
IPageList pageList = dictparam.listByMul(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl +"&type_name=" + type_name+ "&flag_type=" + flag_type+ "&summary=" + summary +"&is_modi="+is_modi;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.sysControl",clientLocale)%> </TITLE>
<!--系统开关-->
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
<script language=javascript>
function showInfo(serial_no){
	if(showModalDialog('syscontrol_update.jsp?flag_type=' + serial_no, '', 'dialogWidth:420px;dialogHeight:360px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function refreshPage()
{
	disableAllBtn(true);
	ismodi=1;
	waitting.style.visibility='visible';
	if(document.theform.ismodi.checked){
		ismodi=0;
	}
		
    location='syscontrol.jsp?page=<%=sPage%>&pagesize='+document.theform.pagesize.value+'&is_modi='+ismodi+'&type_name='+document.theform.type_name.value+'&summary='+document.theform.summary.value+'&flag_type=' + document.theform.flag_type.value;
}

function StartQuery(){
	refreshPage();
}

window.onload = function(){
	initQueryCondition()
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" >

<input type="hidden" name="is_modi" value="<%=is_modi%>"  onkeydown="javascript:nextKeyPress(this)">
<div id="queryCondition" class="qcMain" style="display:none;width:300px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	
	<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isModi",clientLocale)%> :</td><td align="left"><!--不可修改-->
		<input size="1" type="checkbox" class="flatcheckbox" <%if(is_modi.intValue()==0) out.print("checked");%> name="ismodi" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>						
         <td align="right"><%=LocalUtilis.language("class.flagType2",clientLocale)%> :</td><td align="left"><!--控制参数-->
         <input type="text" name="flag_type" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=flag_type%>">
         </td>      
	</tr>
	<tr>						
         <td align="right"><%=LocalUtilis.language("message.paraName",clientLocale)%> :</td><td align="left"><!--参数名称-->
         <input type="text" name="type_name" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=type_name%>">
         </td>      
	</tr>
	<tr>						
         <td align="right"><%=LocalUtilis.language("class.typeContent",clientLocale)%> :</td><td align="left"><!--参数说明-->
         <input type="text" name="summary" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=summary%>">
         </td>      
	</tr>
	
	<tr>
		<td align="center" colspan="2"><button type="button"  class="xpbutton3" accessKey=s name="btnQuery" onclick="javascript:StartQuery();">
		    <%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
		</td><!--确定-->
	</tr>						
	</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
		<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2"><IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
				</tr>			
				<tr>	
					<td valign="bottom" align="right">
					<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>>
					    <%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)
					</button><!--查询-->
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr noshade color="#808080" size="1"></td>
				</tr>
			</table>
			
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trtagsort">
					<td w><%=LocalUtilis.language("class.flagType2",clientLocale)%> </td><!--控制参数-->
					<td><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
					<td><%=LocalUtilis.language("message.paraValue",clientLocale)%> </td><!--参数值-->
					<td><%=LocalUtilis.language("class.summary2",clientLocale)%> </td><!--说明-->
					<td width="8%"><%=LocalUtilis.language("class.isModi2",clientLocale)%> </td><!--可修改标志-->
					<td width="6%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
				</tr>
				<!--从结果集中显示记录-->
<%
int iCount = 0;
int iCurrent = 0;
List rsList = pageList.getRsList();
Map rowMap = null;
for (int i = 0; i<rsList.size();i++){
	rowMap = (Map)rsList.get(i);
	String flag_type2 = Utility.trimNull(rowMap.get("FLAG_TYPE"));
	String type_name2 = Utility.trimNull(rowMap.get("TYPE_NAME"));
    Integer value = Utility.parseInt(Utility.trimNull(rowMap.get("VALUE")),new Integer(0));
	String summary2 = Utility.trimNull(rowMap.get("SUMMARY"));
	Integer is_mody = Utility.parseInt(Utility.trimNull(rowMap.get("IS_MODY")),new Integer(0));
%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td align="left"><%=flag_type2%></td>
					<td align="left"><%=type_name2%></td>
					<td align="center"><%=value%></td>
					<td align="left"><%=summary2%></td>
					<td align="center"><IMG src="<%=request.getContextPath()%>/images/<%if(is_modi.intValue()==0) out.print("no.gif");else out.print("ok.gif");%>" 
					    align=absBottom border=0 width="16" height="16">
					</td>
					<td align="center">
					<%if ( input_operator.hasFunc(menu_id, 102)&&is_modi.intValue() == 1){%>
					<a href="javascript:#" onclick="javascript:showInfo('<%=flag_type2%>');return false;">
						<img src="<%=request.getContextPath()%>/styles/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> "/>
					</a><!--编辑-->
					<%}%></td>
				</tr>
				
<%
iCurrent++;
iCount++;
}
%>
	
<%
for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
				<tr class="tr<%=(i % 2)%>">
					<td class="tdh" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
				</tr>
				<%}
%>
				<tr class="trbottom">	
					<td class="tdh" ><b><%=LocalUtilis.language("message.total",clientLocale)%> <!--合计--> <%=
iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					<td  align="center" >-</td>
					<td  align="center" >-</td>
					<td  align="center" >-</td>
					<td  align="center" >-</td>
					<td align="center" >-</td>
				</tr>
			</table>

			<br>

			<table border="0" width="100%">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl)%></td>
					<td align="right">
					<input type="hidden" class="xpbutton3" accessKey=r id="btnRefresh" name="btnRefresh" 
					    title="<%=LocalUtilis.language("message.currentPageRefrash",clientLocale)%> " onclick="javascript:refreshPage();"/><!--刷新当前页面-->
					<!--刷新(<u>R</u>)</button>
					&nbsp;&nbsp;&nbsp;-->
					<input type="hidden"class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" 
					    title="<%=LocalUtilis.language("message.backLastPage",clientLocale)%> " onclick="javascript:history.back();"/><!--返回上一页-->
					<!--返回(<u>B</u>)</button>
					&nbsp;&nbsp;&nbsp;--></td>
				</tr>
			</table>

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

<%dictparam.remove();%>
