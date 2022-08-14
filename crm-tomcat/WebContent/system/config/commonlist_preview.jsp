<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String catalog_id = Utility.trimNull(request.getParameter("catalog_id"));
ConfigLocal configLocal = EJBFactory.getConfig();
String interfaceType_code = ConfigUtil.getPropertyNameById("interface_catalog","interfacetype_code","interfacetype_id",catalog_id);
String table_code = ConfigUtil.getPropertyNameById("interface_catalog","table_code","interfacetype_code",interfaceType_code);

List tableFieldList = configLocal.tableFieldShowList(interfaceType_code);//查询需要显示有效的页面表格字段
String[] fieldStrs = new String[tableFieldList.size()];//显示字段编码
String[] fieldNameStrs = new String[tableFieldList.size()];//显示字段名称
ConfigUtil.pageValidColShowBlock(tableFieldList,fieldStrs,fieldNameStrs);//获取字段和字段名称集合

IPageList pageList = configLocal.previewPageData(table_code,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
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
<script type="text/javascript">
function refreshPage()
{
	location = "commonlist_preview.jsp?page=<%=sPage%>&pagesize=" + document.theform.pagesize.value;
}
</script>

<BODY class="BODY" >
<form name="theform" method="POST" action="">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td><IMG src="/images/member.gif" align=absBottom border=0
							width="32" height="28"><b><%=menu_info%></b></td>
						<td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=c id="btnClose" name="btnClose" title="关闭" onclick="javascript:window.close();">关闭(<u>C</u>)</button>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class="trh">
						<%for(int i=0;i<fieldNameStrs.length;i++){ 
							if(i < 0){
						%>
						<td width="10%">
							唯一流水号
						</td>
						<%}else{%>
						<td align="center"><%=fieldNameStrs[i]%></td>
						<%}}%>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<%for(int j=0;j<fieldStrs.length;j++){
							String showValue = Utility.trimNull(map.get(fieldStrs[j].toUpperCase()));//字段值
							Map fieldMap = (Map)tableFieldList.get(j);
							String valueType = Utility.trimNull(fieldMap.get("VALUE_TYPE"));
							String showType = Utility.trimNull(fieldMap.get("SHOW_TYPE"));//显示类型
							String tableFieldWidth = Utility.trimNull(fieldMap.get("TABLEFIELDWIDTH"));//显示类型
							
							if(!"".equals(valueType)){//如果为下拉框获取下拉框值
								String value_content = Utility.trimNull(fieldMap.get("VALUE_CONTENT"));
								showValue = ConfigUtil.getParamName(valueType,showValue,value_content);
							}
							if(j==0){//第一列加checkbox						
						%>
							<td class="tdh" align="center" width="<%=tableFieldWidth%>%">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td align="center"><%=showValue %></td>												
								</tr>
							</table>
							</td>
						<%}else if(showType.equals("6")){//图标按钮处理
							String iconfunc = Utility.trimNull(fieldMap.get("ICONFUNC"));//图标函数
							String iconurl = Utility.trimNull(fieldMap.get("ICONURL"));//图标地址
							String interfaceField_name = Utility.trimNull(fieldMap.get("INTERFACEFIELD_NAME"));
						%>
						<td align="center" width="<%=tableFieldWidth%>%">
							<img src="<%=iconurl%>" width="16" height="16" title="<%=interfaceField_name%>" />
						</td>
						<%}else{%>	
							<td align="center" width="<%=tableFieldWidth%>%"><%=showValue%></td>
						<%}}%>
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
						<td class="tdh" align="center"></td>
						<%for(int j=0;j<fieldNameStrs.length-1;j++){ %>
							<td></td>
						<% }%>
					</tr>
<%}
%>
					<tr class="tr1">
						<td class="tdh" align="center"><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<%for(int j=0;j<fieldNameStrs.length-1;j++){ %>
							<td align="center" class="tdh"></td>
						<% }%>
					</tr>
				</table>
				
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
