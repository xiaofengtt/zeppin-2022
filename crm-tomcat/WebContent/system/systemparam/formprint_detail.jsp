<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String interfaceType_code = "FormPrint020";
ConfigLocal configLocal = EJBFactory.getConfig();
boolean bSuccess=false;

//通过页面编码查找该表对应的主键字段
String otherFormCode = Utility.trimNull(request.getParameter("form_code"),"0");
String form_name = Utility.trimNull(request.getParameter("form_name"),"");
//通过页面编码查找该表对应的主键字段和表名

String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
List catalogList =ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = (String)catalogList.get(0);
String grid_name =(String)catalogList.get(1);
String freeform_name =(String)catalogList.get(2);
	
String delStr=identityCode+"DEL";
if(request.getMethod().equals("POST")){//删除信息
	String[] s = request.getParameterValues(delStr);
	if(s.length>0){
		ConfigUtil.delInfo(configLocal,s,table_code,identityCode);
		bSuccess = true;
	}
}

//查询时用到的数据集合
List queryFieldList = configLocal.queryShowList(interfaceType_code);
String[] queryFieldColStr =new String[queryFieldList.size()];//字段集
String[] queryFieldNameStr =new String[queryFieldList.size()];//字段名称集
String[] queryValueStr = new String[queryFieldList.size()];//查询字段页面值
String[] queryConditionTypeStr = new String[queryFieldList.size()];//查询类型集
String[] queryConditionValueStr = new String[queryFieldList.size()];//真正查询的类别值
String[] queryInputTypeStr = new String[queryFieldList.size()];//元素框类型集
String[] queryValueTypeStr = new String[queryFieldList.size()];//下拉框数据源类型集
String[] queryValueContentStr =new String[queryFieldList.size()];//数据查询语句集
//查询处理块
ConfigUtil.queryConditionBlock(request,queryFieldList,queryFieldColStr,queryFieldNameStr,queryInputTypeStr,queryValueTypeStr,queryValueContentStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);
List tableFieldList = configLocal.tableFieldShowList(interfaceType_code);//查询需要显示有效的页面表格字段
String[] fieldStrs = new String[tableFieldList.size()];//显示字段编码
String[] fieldNameStrs = new String[tableFieldList.size()];//显示字段名称
ConfigUtil.pageValidColShowBlock(tableFieldList,fieldStrs,fieldNameStrs);//获取字段和字段名称集合

//其他查询字段
List replaceColValue = new ArrayList();//需要替换的值
replaceColValue.add(String.valueOf(input_operatorCode));
String sqlContent = ConfigUtil.getOtherQuerySql(interfaceType_code,replaceColValue);

IPageList pageList = new JdbcPageList();
if(!otherFormCode.equals("0")){//自己拼接的其他查询条件语句
		sqlContent =sqlContent + " and form_code='"+otherFormCode+"'";
		pageList= configLocal.showPageDataOther(interfaceType_code,table_code,queryFieldColStr,queryValueStr,queryConditionValueStr,
											sqlContent,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,-1));
}else{
		pageList= configLocal.showPageDataOther(interfaceType_code,table_code,queryFieldColStr,queryValueStr,queryConditionValueStr,
											sqlContent,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
}
//获得对应结果值并替换
List listRS = pageList.getRsList();
listRS=ConfigCheck.replaceFieldValue(listRS,"SHOW_NAME","&nbsp;","nbsp;");
	
sUrl = ConfigUtil.getSUrl(sUrl,queryFieldColStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);
sPage="page="+sPage+"&form_code="+otherFormCode;
sUrl = sUrl +"&form_code="+otherFormCode;
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
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>

<script language=javascript>
//-----------------编辑-------------------
function showInfo(identity_code)
{
	var tableCode = document.theform.table_code.value;
	var interfaceTypeCode = document.theform.interfacetype_code.value;
	if(showModalDialog('<%=freeform_name%>?interfacetype_code='+interfaceTypeCode+'&identity_code='+identity_code+'&table_code='+tableCode, '', 'dialogWidth:600px;dialogHeight:510px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}
//-----------------新增---------------------
function newInfo()
{
	var tableCode = document.theform.table_code.value;
	var interfaceTypeCode = document.theform.interfacetype_code.value;
	
	var  form_code= document.theform.form_code.value;
	var form_name=document.theform.form_name.value;
	if(showModalDialog('<%=freeform_name%>?interfacetype_code='+interfaceTypeCode+'&identity_code=0&table_code='+tableCode+'&form_code='+form_code+'&form_name='+form_name, '', 'dialogWidth:600px;dialogHeight:510px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}
//----------返回-----------
function doBack(){
	location ='/system/systemparam/formprint_catalog.jsp';
}

</script>

<BODY class="BODY" >
<form name="theform" method="POST" action="<%=grid_name%>">
<input name="interfacetype_code" type="hidden" value="<%=interfaceType_code %>" />
<input name="table_code" type="hidden" value="<%=table_code%>" />
<input name="form_code" type="hidden" value="<%=otherFormCode%>">
<input name="form_name" type="hidden" value="<%=form_name%>">

<%
request.setAttribute("queryFieldColStr",queryFieldColStr);//查询字段集集
request.setAttribute("queryFieldNameStr",queryFieldNameStr);//查询字段名称集;
request.setAttribute("queryConditionTypeStr",queryConditionTypeStr);//查询字段查询类型集
request.setAttribute("queryConditionValueStr",queryConditionValueStr);//查询字段选择的查询类型
request.setAttribute("queryInputTypeStr",queryInputTypeStr);//元素框类型集
request.setAttribute("queryValueTypeStr",queryValueTypeStr);//下拉框数据源类型集
request.setAttribute("queryValueContentStr",queryValueContentStr);//数据查询语句
request.setAttribute("queryValueStr",queryValueStr);//查询字段页面值
 %>
<jsp:include page="/system/config/basiclist_query.jsp" flush="true"></jsp:include>
<%
	request.setAttribute("bSuccess",new Boolean(bSuccess));
	request.setAttribute("queryFieldColStr",queryFieldColStr);
	request.setAttribute("queryConditionTypeStr",queryConditionTypeStr);
	request.setAttribute("queryConditionValueStr",queryConditionValueStr);
	request.setAttribute("queryValueStr",queryValueStr);
%>
<jsp:include page="/system/config/basiclist_js.jsp" flush="true">
	<jsp:param name="grid_name" value="<%=grid_name%>"/>
	<jsp:param name="sPage" value="<%=sPage%>"/>
</jsp:include>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<IMG src="/images/member.gif" align="absBottom" border="0" width="32" height="28"><b><%=menu_info%></b>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp; 
							<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="新建记录" onclick="javascript:newInfo();">新增(<u>N</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=l id="btnDel" name="btnDel" title="删除" onclick="javascript: remove('<%=delStr%>')">删除(<u>L</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<%if(!("0".equals(otherFormCode))){%> 
							<button type="button"  class="xpbutton3" accessKey=b id="btnDel" name="backDel" title="返回" onclick="javascript: doBack()">返回(<u>B</u>)</button>
							&nbsp;&nbsp;&nbsp; 
							<%}%>
						</td>
					</tr>
					<tr><td colspan="2"><hr noshade color="#808080" size="1"></td></tr>
				</table>
		<%
	 	  request.setAttribute("fieldNameStrs",fieldNameStrs); 
	 	  request.setAttribute("pageList",pageList);
	 	  request.setAttribute("fieldStrs",fieldStrs);
	 	  request.setAttribute("tableFieldList",tableFieldList);
	 	%>
		<jsp:include page="/system/config/basiclist_table.jsp" flush="true">
			<jsp:param name="sUrl" value="<%=sUrl%>"/>
		</jsp:include>
	</td>
	</tr>	
</table>	
</TD>
</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>

</HTML>
<%configLocal.remove();%>
