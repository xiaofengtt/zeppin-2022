<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//主键值
Integer identity_code = Utility.parseInt(request.getParameter("identity_code"),new Integer("0"));
String table_code = Utility.trimNull(request.getParameter("table_code"));
String interfaceType_code = Utility.trimNull(request.getParameter("interfacetype_code"));
ConfigLocal configLocal = EJBFactory.getConfigCatalog();

//通过页面编码查找该表对应的主键字段
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
boolean bSuccess = false;

List modShowDataList = new ArrayList();//查看页面显示元素数据
List fieldTdList = ConfigUtil.getFieldViewList(configLocal,interfaceType_code);//查看页面显示的有效元素集合

if(identity_code != 0){
	modShowDataList = configLocal.modPageShowData(table_code,identityCode,String.valueOf(identity_code));
}
%>
<html>
<head>
<title>新增要素应用</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<style type="text/css">
.edlinenew
{
    BACKGROUND-COLOR: transparent;
    BORDER-BOTTOM: #808080 1px solid;
    BORDER-LEFT: medium none;
    BORDER-RIGHT: medium none;
    BORDER-TOP: medium none;
}
</style>
</head>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>


<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="preitemtest_info.jsp" >
<input type="hidden" name="interfacetype_code" value="<%=interfaceType_code%>" />
<input type="hidden" name="identity_code" value="<%=identity_code%>">
<input type="hidden" name="table_code" value="<%=table_code%>"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<img border="0" src="/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>新增要素类别</b></font>
			</td>
		</tr>
		<tr>
			<td >
			<hr noshade color="#808080" size="1">
			</td>
		</tr>
</table>
<table border="0" width="100%" align="center" cellspacing="3">
	<%for(int i=0;i<fieldTdList.size();i++){
	Map  rowMapTr = (Map)fieldTdList.get(i);
	boolean subarea_flag = false;//是否分区
	%>
	<tr>
		<%  int behind_row = Utility.parseInt(rowMapTr.get("BEHIND_ROW").toString(),1);
			for(int j=i;j<behind_row+i;j++){
				Map  rowMapTd = (Map)fieldTdList.get(j);
				String interfaceField_code = Utility.trimNull(rowMapTd.get("INTERFACEFIELD_CODE"));
				String interfaceField_name = Utility.trimNull(rowMapTd.get("INTERFACEFIELD_NAME"));
				String show_type =Utility.trimNull(rowMapTd.get("SHOW_TYPE"));
				String edit_type =Utility.trimNull(rowMapTd.get("EDIT_TYPE"));
				String value_content = Utility.trimNull(rowMapTd.get("VALUE_CONTENT"));
				String destride_row = Utility.trimNull(rowMapTd.get("BESTRIDE_ROW"));
				String show_unit = Utility.trimNull(rowMapTd.get("SHOW_UNIT"));
				String value_type = Utility.trimNull(rowMapTd.get("VALUE_TYPE"));
				
				String tableValue ="";//页面数据值
				String tableValueName ="";//如果为select、radio、checkbox时对应的数据值名称
				String inputType = "text";
				
				inputType = ConfigUtil.getInputType(show_type,edit_type);//元素框类型
				//获取元素值
				tableValue = ConfigUtil.getTableValue(modShowDataList,inputType,interfaceField_code);
				if(inputType.equals("selectbox")||inputType.equals("radio")||inputType.equals("checkbox")){//只读是获取select、radio、checkbox对应的名称值
					tableValueName =ConfigUtil.getTableValueName(inputType,value_type,tableValue,value_content);//获取元素值名称
				}
		%>
			<td align="right" height="30"><%=interfaceField_name%>:</td>
			<td align="left" colspan='<%=destride_row%>' >
				<%
					if(inputType.equals("textarea")){
				%>
					<input type="textarea" name="<%=interfaceField_code%>" readonly="readonly" style="width: 180px" value="<%=tableValue%>" onkeydown="javascript:nextKeyPress(this)">
				<% }else if(inputType.equals("selectbox")||inputType.equals("radio")||inputType.equals("checkbox")){%>
					<input type="text" name="<%=interfaceField_code%>" readonly="readonly" style="width: 180px" class="edline" value="<%=tableValueName%>" onkeydown="javascript:nextKeyPress(this)">
				<% }else{%>
					<input type="text" name="<%=interfaceField_code%>" readonly="readonly" style="width: 180px" class="edline" value="<%=tableValue%>" onkeydown="javascript:nextKeyPress(this)">
				<%}%>
				<%if(!(show_unit.length()>20)){//弹出框只读时去掉后面的弹出按钮
				%>
					<%=show_unit%>
				<%}%>
			</td>
		<%
		if(j==behind_row+i-1){//td中最后一个时判断下面是否加上分区横线
				String subareaFlag = Utility.trimNull(rowMapTd.get("SUBAREA_FLAG"));//是否分区
				if(subareaFlag.equals("1")){
					subarea_flag = true;
				}
			}
		}
		if(behind_row>1){
			i= i + (Integer.valueOf(behind_row).intValue()-1);
		}
		%>
	</tr>
	<%if(subarea_flag){//分区横线%>
		<tr>
			<td colspan="6">
				<hr>
			</td>
		</tr>
	<%}}%>
</table>
<br>
<br>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">返回(<u>C</u>)</button>
		&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>
