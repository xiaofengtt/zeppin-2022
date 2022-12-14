<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.project.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//初始化参数信息
String interfaceType_code = "FlowTask020";
String action_flag = Utility.trimNull(request.getParameter("action_flag"));
ConfigLocal configLocal = EJBFactory.getConfigCatalog();
BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
boolean bSuccess = false;

//通过页面编码查找该表对应的主键字段和表名
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
List<String> catalogList =ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = catalogList.get(0);
String grid_name ="/project/flow/allflowtask_audit.jsp";
String freeform_name =catalogList.get(2);
String delStr=identityCode+"DEL";//列表中checkbox的name值

if(request.getMethod().equals("POST")){//删除信息
	String returnValue= Utility.trimNull(request.getParameter("identityCode"));
	String[] s=returnValue.split("@@");
	if(returnValue != null && action_flag.equals("delete")){ 
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
tableFieldList=ConfigCheck.setFieldValue(tableFieldList,"900","SHOW_TYPE","DelRow");
tableFieldList=ConfigCheck.setFieldValue(tableFieldList,"910","SHOW_TYPE","DelRow");
tableFieldList=ConfigCheck.setFieldValue(tableFieldList,"920","SHOW_TYPE","DelRow");
tableFieldList=ConfigCheck.setFieldValue(tableFieldList,"930","SHOW_TYPE","DelRow");
String[] fieldStrs = new String[tableFieldList.size()];//显示字段编码
String[] fieldNameStrs = new String[tableFieldList.size()];//显示字段名称
ConfigUtil.pageValidColShowBlock(tableFieldList,fieldStrs,fieldNameStrs);//获取字段和字段名称集合
//其他查询字段
List<String> replaceColValue = new ArrayList<String>();//需要替换的值
String sqlContent = ConfigUtil.getOtherQuerySql(interfaceType_code,replaceColValue);
//不同入口判断不同的查询条件

IPageList pageList = configLocal.showPageDataOther(interfaceType_code,table_code,queryFieldColStr,queryValueStr,queryConditionValueStr,
										sqlContent,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = ConfigUtil.getSUrl(sUrl,queryFieldColStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);
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

//---------编辑---------
function showInfo(identity_code,object_no,object_type,flow_no,node_no)
{
	var returnValue=showModalDialog('/project/flow/flowobject_tab.jsp?task_id='+identity_code+'&object_type='+object_type+'&flow_no='+flow_no+'&node_no='+node_no+'&show_flag=FlowTask&edit_right=no&object_id='+object_no, '', 'dialogWidth:1300px;dialogHeight:860px;status:0;help:0');
	if(returnValue=="success") location.reload();
}
</script>

<BODY class="BODY" >
<form name="theform" method="POST" action="<%=grid_name%>">
<input name="interfacetype_code" type="hidden" value="<%=interfaceType_code %>" />
<input name="table_code" type="hidden" value="<%=table_code%>" />
<input name="action_flag" type="hidden" value="<%=action_flag%>" />
<input name="identityCode" type="hidden" value="<%=identityCode%>" />

<%
request.setAttribute("queryFieldColStr",queryFieldColStr);//查询字段集集
request.setAttribute("queryFieldNameStr",queryFieldNameStr);//查询字段名称集;
request.setAttribute("queryConditionTypeStr",queryConditionTypeStr);//查询字段查询类型集
request.setAttribute("queryConditionValueStr",queryConditionValueStr);//查询字段选择的查询类型
request.setAttribute("queryInputTypeStr",queryInputTypeStr);//元素框类型集
request.setAttribute("queryValueTypeStr",queryValueTypeStr);//下拉框数据源类型集
request.setAttribute("queryValueContentStr",queryValueContentStr);//数据查询语句
request.setAttribute("queryValueStr",queryValueStr);//查询字段页面值
request.setAttribute("queryInputTypeStr",queryInputTypeStr);//元素框类型值
 %>
<jsp:include page="/system/config/basiclist_query.jsp" flush="true"></jsp:include>

<%
	request.setAttribute("bSuccess",bSuccess);
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
						<td><IMG src="/images/member.gif" align="absBottom" border="0" width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align="right">
							<button class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;						
						</td>
					</tr>
					<tr>
						<td colspan="2"><hr noshade color="#808080" size="1"></td>
					</tr>
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
	</td>
	</tr>	
</table>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>

</HTML>
<%configLocal.remove();%>