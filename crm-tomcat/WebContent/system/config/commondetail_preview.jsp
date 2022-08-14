<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//主键值
String catalog_id = Utility.trimNull(request.getParameter("catalog_id"));
String preview_code = Utility.trimNull(request.getParameter("preview_code"));
Integer identity_code = new Integer(0);
if(preview_code.equals("3")){//修改页面，默认值设为1作为修改状态
	identity_code = new Integer(1);
}

String interfaceType_code = ConfigUtil.getPropertyNameById("interface_catalog","interfacetype_code","interfacetype_id",catalog_id);
List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,new Integer(0),"2");//获取页面所有显示的有效元素集合
List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,new Integer(0),"3");//获取页面所有隐藏的有效元素集合
List modShowDataList =new ArrayList();
//获得所有下拉框对应的代码值
List selectValuelist=ConfigUtil.getSelectValue(fieldShowList,"QueryAllSelect");

%>
<html>
<head>
<title>新增要素应用</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/system/configstyle.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>

<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="" >
<%for(int i=0;i<fieldHideList.size();i++){//隐藏字段集
	Map  hideMap = (Map)fieldHideList.get(i);
	String interfaceField_code = Utility.trimNull(hideMap.get("INTERFACEFIELD_CODE"));
	String default_flag = Utility.trimNull(hideMap.get("DEFAULT_FLAG"));
	String default_value = Utility.trimNull(hideMap.get("DEFAULT_VALUE"));
	String show_type =Utility.trimNull(hideMap.get("SHOW_TYPE"));
	String edit_type =Utility.trimNull(hideMap.get("EDIT_TYPE"));
	String inputType = ConfigUtil.getInputType(show_type,edit_type);//文本框类型
	String fieldValue = ConfigUtil.getPageElementValue(identity_code,default_flag,default_value,inputType,interfaceField_code,modShowDataList);
	
%>
<input type="hidden" name="<%=interfaceField_code%>" value="<%=fieldValue%>"/>
<%}%>

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
	<%for(int i=0;i<fieldShowList.size();i++){
	Map  rowMapTr = (Map)fieldShowList.get(i);	
	String subareaTitle = "";//分区标题
	String subareaAlign = "";//分区对齐方式
	String subareaSize = "";//分区标题字体大小
	String subareaFamily = "";//分区标题字体类型
	String subareaWeight = "";//分区标题字体粗细
	String subareaColor = "";//分区标题字体颜色
	String subareaLeftSize = "";//分区左长度
	String subareaRightSize = "";//分区右长度
	String subareaAllSize = "";//分区全长度
	
	int totalNum =fieldShowList.size();
	boolean subarea_flag = false;
	%>
	<tr>
		<%  int behind_row = Utility.parseInt(rowMapTr.get("BEHIND_ROW").toString(),1);
			String elementValue = "";//元素框值
			String elementName = "";//元素框名称
			int num = 0;//每行中存在td的个数
			for(int j=i;j<behind_row+i;j++){
			if(j<totalNum){//循环序号不能大于总的个数
				Map  rowMapTd = (Map)fieldShowList.get(j);
				String tdBehind_row = Utility.trimNull(rowMapTd.get("BEHIND_ROW"));
				if(j==i || Integer.valueOf(tdBehind_row).intValue()==1){
				num++;
				String interfaceField_code = Utility.trimNull(rowMapTd.get("INTERFACEFIELD_CODE"));
				String interfaceField_name = Utility.trimNull(rowMapTd.get("INTERFACEFIELD_NAME"));
				String align_type = Utility.trimNull(rowMapTd.get("ALIGN_TYPE"));
				String show_type =Utility.trimNull(rowMapTd.get("SHOW_TYPE"));
				String edit_type =Utility.trimNull(rowMapTd.get("EDIT_TYPE"));
				String value_content = Utility.trimNull(rowMapTd.get("VALUE_CONTENT"));
				String destride_row = Utility.trimNull(rowMapTd.get("BESTRIDE_ROW"));
				String show_unit = Utility.trimNull(rowMapTd.get("SHOW_UNIT"));
				String value_type = Utility.trimNull(rowMapTd.get("VALUE_TYPE"));
				String required_flag = Utility.trimNull(rowMapTd.get("REQUIRED_FLAG"));
				String visibled_flag = Utility.trimNull(rowMapTd.get("VISIBLED_FLAG"));
				String show_width = Utility.trimNull(rowMapTd.get("SHOW_WIDTH"));
				String show_height = Utility.trimNull(rowMapTd.get("SHOW_HEIGHT"));
				String readonly_flag = Utility.trimNull(rowMapTd.get("READONLY_FLAG"));
				String default_flag = Utility.trimNull(rowMapTd.get("DEFAULT_FLAG"));
				String default_value = Utility.trimNull(rowMapTd.get("DEFAULT_VALUE"));
				
				boolean readonlyFlag = false;//只读标识
				String inputType = "text";
				String tdStyle1="";
				String tdStyle2="";
				String tdRequiredStyle1="";
				String tdVisibledStyle1="";
				String tdVisibledStyle2="";
				String inputStyle ="";//input框的高度、宽度设置
				
				readonlyFlag = ConfigUtil.getReadonlyFlag(identity_code,readonly_flag);//只读类型
				inputType = ConfigUtil.getInputType(show_type,edit_type);//文本框类型
				if(default_flag.equals("1")){
					elementValue =default_value;
					if(inputType.equals("money")){//货币默认值进行转化
						if(!"".equals(elementValue)){
							elementValue = ConfigUtil.MoneyTransform(elementValue);//货币转化
						}
					}
				}
				if(readonlyFlag){//只读是获取select、radio、checkbox对应的名称值
					//elementName =ConfigUtil.getTableValueName(inputType,value_type,elementValue,value_content);//获取元素值名称
					elementName =ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,elementValue,"SelectName");//获取元素值名称
				}
				align_type = ConfigUtil.getAlignType(align_type);//对齐方式
				tdRequiredStyle1 =ConfigUtil.getRequiredFlag(required_flag);//必输判断
				tdVisibledStyle1 = ConfigUtil.getVisibledFlag(identity_code,visibled_flag);//标签名称是否可见
				tdVisibledStyle2 = ConfigUtil.getVisibledFlag(identity_code,visibled_flag);//标签框是否可见
				String[] tdStr1 ={tdRequiredStyle1,tdVisibledStyle1};
				String[] tdStr2 ={tdVisibledStyle2};
				tdStyle1 =ConfigUtil.getCombinationStyle(tdStr1);//样式组合
				tdStyle2 =ConfigUtil.getCombinationStyle(tdStr2);
				inputStyle = ConfigUtil.getInputStyle(show_width,show_height);//元素框宽度高度样式
				
		%>
			<td align="right" height="30" <%=tdStyle1%>><%=interfaceField_name%>:</td>
			<td align="left" colspan='<%=destride_row%>' <%=tdStyle2%>>
			<%if(!readonlyFlag){%>
				<%if(inputType.equals("text")){%>
					<input type="text" name="<%=interfaceField_code%>" <%=align_type%> <%=inputStyle%> value="<%=elementValue%>" onkeydown="javascript:nextKeyPress(this)">
				<%}else if(inputType.equals("number")){%>
					<input type="text" onblur="checkAllNumber('<%=interfaceField_code%>')" name="<%=interfaceField_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=elementValue%>">
				<%}else if(inputType.equals("integer")){%>
					<input type="text" onblur="checkInteger('<%=interfaceField_code%>')" name="<%=interfaceField_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=elementValue%>">
				<%}else if(inputType.equals("textarea")){%>
					<textarea cols="100" rows="5" name="<%=interfaceField_code%>" <%=align_type%> <%=inputStyle%>><%=elementValue%></textarea>
				<%}else if(inputType.equals("date")){%>
					<INPUT TYPE="text" NAME="<%=interfaceField_code%>" <%=inputStyle%> value="<%=elementValue%>" class=selecttext>
					<%if(!readonly_flag.equals("1")){%>
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.<%=interfaceField_code%>,theform.<%=interfaceField_code%>.value,this);"> 
					<%}%>
				<%}else if(inputType.equals("checkbox")){
					if(value_type.equals("2")){
				%>
						<%//=ConfigUtil.getParamCheckboxSql(value_content,elementValue,interfaceField_code)%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,elementValue,"CheckBox")%>
				<%}else{%>
						<%//=ConfigUtil.getDictParamCheckBoxs(value_content,elementValue,interfaceField_code,"")%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,elementValue,"CheckBox")%>
				<%}
				}else if(inputType.equals("selectbox")){
					if(value_type.equals("2")){
				%>
					<SELECT size="1" name="<%=interfaceField_code%>" <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)">
						<%//=ConfigUtil.getParamSelectSql(value_content,elementValue)%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,elementValue,"SelectBox")%>
					</SELECT>
				<%}else{ %>
					<SELECT size="1" name="<%=interfaceField_code%>" <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)">
						<%//=ConfigUtil.getDictParamConfig(value_content,elementValue)%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,elementValue,"SelectBox")%>
					</SELECT>
				<%}
				}else if(inputType.equals("radio")){
					if(value_type.equals("2")){
				%>
						<%//=ConfigUtil.getParamRadioSql(value_content,elementValue,interfaceField_code,"10010001")%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,elementValue,"CheckRadio")%>
				<% }else{%>
						<%//=ConfigUtil.getDictParamRadios(value_content,elementValue,interfaceField_code,"1")%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,elementValue,"CheckRadio")%>
				<%}
				}else if(inputType.equals("money")){
				%>
					<input type="text" name="<%=interfaceField_code%>" onblur="moneyChange('<%=interfaceField_code%>')" <%=align_type%> <%=inputStyle%> value="<%=elementValue%>" onkeydown="javascript:nextKeyPress(this)">					
				<%}%>
				<%}else{//只读时操作
					if(inputType.equals("textarea")){	
				%>
					<input type="textarea" name="<%=interfaceField_code%>" readonly="readonly" <%=align_type%> <%=inputStyle%> value="<%=elementValue%>" onkeydown="javascript:nextKeyPress(this)">
				<% }else if(inputType.equals("selectbox")||inputType.equals("radio")||inputType.equals("checkbox")){%>
					<input type="text" name="<%=interfaceField_code%>" readonly="readonly" class="edline" <%=align_type%> <%=inputStyle%> value="<%=elementName%>" onkeydown="javascript:nextKeyPress(this)">
				<% }else{%>
					<input type="text" name="<%=interfaceField_code%>" readonly="readonly" class="edline" <%=align_type%> <%=inputStyle%>  value="<%=elementValue%>" onkeydown="javascript:nextKeyPress(this)">
				<%}}%>
				<%if(!(readonly_flag.equals("1")&&show_unit.length()>20)){//弹出框只读时去掉后面的弹出按钮
				%>
					<%=show_unit%>
				<%}%>
			</td>
		<%
			//如果这行中的最后一个元素为分区进行判断
			String subareaFlag = Utility.trimNull(rowMapTd.get("SUBAREA_FLAG"));//是否分区
			if(subareaFlag.equals("1")){
				Map  subareaMap = (Map)fieldShowList.get(j+1);
				String subareaEditType =Utility.trimNull(subareaMap.get("EDIT_TYPE"));
				String subareaShowType =Utility.trimNull(subareaMap.get("SHOW_TYPE"));
				String subareaInputType = ConfigUtil.getInputType(subareaShowType,subareaEditType);
				Map	 subareaNextMap = (Map)fieldShowList.get(j+2);
				String nextBehindRow = Utility.trimNull(subareaNextMap.get("BEHIND_ROW"),"1");
				//每行的最后元素并且下个为分区或不是每行最后的元素并且下个元素为分区下下个元素合并列不为1
				if((j==behind_row+i-1 && subareaInputType.equals("subarea")) || (j!=behind_row+i-1 && subareaInputType.equals("subarea") && !nextBehindRow.equals("1"))){
					subarea_flag = true;
					subareaTitle = Utility.trimNull(subareaMap.get("INTERFACEFIELD_NAME"));
					subareaAlign = Utility.trimNull(subareaMap.get("ALIGN_TYPE"));
					subareaSize = ConfigUtil.getFontStyle("FONT_SIZE",subareaMap);
					subareaFamily = ConfigUtil.getFontStyle("FONT_FAMILY",subareaMap);
					subareaWeight = ConfigUtil.getFontStyle("OVERSTRIKING_FLAG",subareaMap);
					subareaColor = ConfigUtil.getFontStyle("FONT_COLOR",subareaMap);
					String subareaShowStyle = Utility.trimNull(subareaMap.get("SHOW_STYLE"));
					if(!subareaShowStyle.equals("")){
						String subareaStrs[] = subareaShowStyle.split(",");
						if(subareaStrs.length==3){
							subareaLeftSize =subareaStrs[0];
							subareaRightSize = subareaStrs[1];
							subareaAllSize = subareaStrs[2];
						}
					}
					num++;
				}
			}
			}
			}
			}
			if(behind_row>1){
				i= i + num-1;
			}
		%>
	</tr>
	<%if(subarea_flag){//分区横线%>
	<tr>
		 <%if(subareaAlign.equals("1")){%> 
			 <td colspan="6">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="<%=subareaColor%>" style="font-size:<%=subareaSize%>;font-family:<%=subareaFamily%>;font-weight:<%=subareaWeight%>";><%=subareaTitle%></font>&nbsp;<input class="edlinenew" disabled="disabled" size="<%=subareaAllSize%>">
			</td>
		  <% }else if(subareaAlign.equals("2")){%>
		  	<td colspan="3" align="right">
				<input class="edlinenew" disabled="disabled" size="<%=subareaLeftSize%>">
			</td>
			<td colspan="3">
				&nbsp;<font color="<%=subareaColor%>" style="font-size:<%=subareaSize%>;font-family:<%=subareaFamily%>;font-weight:<%=subareaWeight%>";><%=subareaTitle%></font>&nbsp;<input class="edlinenew" disabled="disabled" size="<%=subareaRightSize%>">
			</td>
		  <%}else if(subareaAlign.equals("3")){%>
		  	<td colspan="6">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="edlinenew" disabled="disabled" size="<%=subareaAllSize%>">&nbsp;<font color="<%=subareaColor%>" style="font-size:<%=subareaSize%>;font-family:<%=subareaFamily%>;font-weight:<%=subareaWeight%>";><%=subareaTitle%></font>&nbsp;
			</td>
		  <%}%>
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
