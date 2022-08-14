<%@ page language="java" contentType="text/html;charset=GBK" import="enfo.crm.tools.*,java.util.*"%>
<%
String[] queryFieldColStr =(String[])request.getAttribute("queryFieldColStr");
String[] queryFieldNameStr =(String[])request.getAttribute("queryFieldNameStr");
String[] queryConditionTypeStr =(String[])request.getAttribute("queryConditionTypeStr");
String[] queryConditionValueStr =(String[])request.getAttribute("queryConditionValueStr");
String[] queryInputTypeStr =(String[])request.getAttribute("queryInputTypeStr");
String[] queryValueTypeStr =(String[])request.getAttribute("queryValueTypeStr");
String[] queryValueContentStr=(String[])request.getAttribute("queryValueContentStr");
String[] queryValueStr = (String[])request.getAttribute("queryValueStr");
//List tableFieldList =(List)request.getAttribute("tableFieldList");
//获得所有下拉框对应的代码值
//List selectValuelist=ConfigUtil.getSelectValue(tableFieldList,"QueryAllSelect");
 %>

<div id="queryCondition" class="qcMain" style="display:none;width:320px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
		<tr>
			<td>查询条件：</td>
			<td align="right">
				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<%for(int i=0;i<queryFieldColStr.length;i++){%>
		<tr>
		<td align="right"><%=queryFieldNameStr[i]%>:</td>
		<td>
			<SELECT size="1" name="<%=queryFieldColStr[i]%>type" onchange="call conditionChange('<%=queryFieldColStr[i]%>',<%=i%>)" style="width: 72px;" onkeydown="javascript:nextKeyPress(this)">
				<%=ConfigUtil.getQuerySelectValue(queryConditionTypeStr[i],queryConditionValueStr[i])%>
			</SELECT>
		</td>
		<td>
		<%
			String inputType = queryInputTypeStr[i];
			String valueType = queryValueTypeStr[i];
			String valueContent = queryValueContentStr[i];
			if(inputType.equals("selectbox")){//下拉框
			if(valueType.equals("1")){
		%>
				<SELECT size="1" name="<%=queryFieldColStr[i]%>" style="width: 150px;" onkeydown="javascript:nextKeyPress(this)">
					<%=ConfigUtil.getDictParamConfig(valueContent,queryValueStr[i])%>
					<%//=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"SelectBox")%>
				</SELECT>
		<%}else{%>
				<SELECT size="1" name="<%=queryFieldColStr[i]%>" style="width: 150px;" onkeydown="javascript:nextKeyPress(this)">
					<%=ConfigUtil.getParamSelectSql(valueContent,queryValueStr[i])%>
					<%//=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"SelectBox")%>
				</SELECT>
		<%}}else if(inputType.equals("radio")){
			if(valueType.equals("1")){
		%>
				<%=ConfigUtil.getDictParamRadios(valueContent,queryValueStr[i],queryFieldColStr[i],"")%>
				<%//=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"CheckRadio")%>
		<%}else{ %>
				<%=ConfigUtil.getParamRadioSql(valueContent,queryValueStr[i],queryFieldColStr[i],"")%>
				<%//=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"CheckRadio")%>
		<%}}else if(inputType.equals("checkbox")){
			if(valueType.equals("1")){
		 %>
			<%=ConfigUtil.getDictParamCheckBoxs(valueContent,queryValueStr[i],queryFieldColStr[i],"")%>
			<%//=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"CheckBox")%>
		<%}else{ %>
			<%=ConfigUtil.getParamCheckboxSql(valueContent,queryValueStr[i],queryFieldColStr[i])%>
			<%//=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"CheckBox")%>
		<%}}else if("date".equals(inputType)){//日期类型%>
			<INPUT TYPE="text" onblur="checkVDate('<%=queryFieldColStr[i]%>')" NAME="<%=queryFieldColStr[i]%>" 
				style="width:128px;" size="15" value="<%=queryValueStr[i]%>" class=selecttext onkeydown="javascript:nextKeyPress(this)">
			<INPUT TYPE="button" id="<%=queryFieldColStr[i]%>_dateButton" value="▼" class=selectbtn 
				onclick="javascript:CalendarWebControl.show(theform.<%=queryFieldColStr[i]%>,theform.<%=queryFieldColStr[i]%>.value,this);"> 
		<%}else{//其他类型%>
			<input type="text" name="<%=queryFieldColStr[i]%>" style="width: 150px; size="15" value="<%=queryValueStr[i]%>" onkeydown="javascript:nextKeyPress(this)">
		<%}%>
		<div id="divbet<%=i%>" style="display:none;">
			<%if(queryConditionTypeStr[i].indexOf("7")!=7){%>
				<%if("date".equals(inputType)){//日期类型 %>
				<input type="text" onblur="checkVDate('<%=queryFieldColStr[i]%>one<%=i%>')" NAME="<%=queryFieldColStr[i]%>one<%=i%>" 
					style="width:75px;" size="15" value="" class=selecttext onkeydown="javascript:nextKeyPress(this)">
				<input type="button" id="<%=queryFieldColStr[i]%>one<%=i%>_dateButton" value="▼" class=selectbtn 
					onclick="javascript:CalendarWebControl.show(theform.<%=queryFieldColStr[i]+"one"+i%>,theform.<%=queryFieldColStr[i]+"one"+i%>.value,this);"> 
				<input type="text" onblur="checkVDate('<%=queryFieldColStr[i]%>two<%=i%>')" NAME="<%=queryFieldColStr[i]%>two<%=i%>" 
					style="width:75px;" size="15" value="" class=selecttext onkeydown="javascript:nextKeyPress(this)">
				<input type="button" id="<%=queryFieldColStr[i]%>two<%=i%>_dateButton" value="▼" class=selectbtn 
					onclick="javascript:CalendarWebControl.show(theform.<%=queryFieldColStr[i]+"two"+i%>,theform.<%=queryFieldColStr[i]+"two"+i%>.value,this);"> 
				<%}else{ %>
				<input type="text" name="<%=queryFieldColStr[i]%>one<%=i%>"  style="width: 60px;" size="15" value="" onkeydown="javascript:nextKeyPress(this)">&nbsp;&nbsp;至
				<input type="text" name="<%=queryFieldColStr[i]%>two<%=i%>"  style="width: 60px;" size="15" value="" onkeydown="javascript:nextKeyPress(this)">
				<%} %>
			<%}%>
		</div>

		</td>
		</tr>
		<%}%>
		<tr>
			<td align="center" colspan="2">
				<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:newQuery();">确定(<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>			
	</table>
</div>