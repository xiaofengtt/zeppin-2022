<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//����ֵ
String catalog_id = Utility.trimNull(request.getParameter("catalog_id"));
String preview_code = Utility.trimNull(request.getParameter("preview_code"));
Integer identity_code = new Integer(0);
if(preview_code.equals("3")){//�޸�ҳ�棬Ĭ��ֵ��Ϊ1��Ϊ�޸�״̬
	identity_code = new Integer(1);
}

String interfaceType_code = ConfigUtil.getPropertyNameById("interface_catalog","interfacetype_code","interfacetype_id",catalog_id);
List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,new Integer(0),"2");//��ȡҳ��������ʾ����ЧԪ�ؼ���
List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,new Integer(0),"3");//��ȡҳ���������ص���ЧԪ�ؼ���
List modShowDataList =new ArrayList();
//��������������Ӧ�Ĵ���ֵ
List selectValuelist=ConfigUtil.getSelectValue(fieldShowList,"QueryAllSelect");

%>
<html>
<head>
<title>����Ҫ��Ӧ��</title>
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
<%for(int i=0;i<fieldHideList.size();i++){//�����ֶμ�
	Map  hideMap = (Map)fieldHideList.get(i);
	String interfaceField_code = Utility.trimNull(hideMap.get("INTERFACEFIELD_CODE"));
	String default_flag = Utility.trimNull(hideMap.get("DEFAULT_FLAG"));
	String default_value = Utility.trimNull(hideMap.get("DEFAULT_VALUE"));
	String show_type =Utility.trimNull(hideMap.get("SHOW_TYPE"));
	String edit_type =Utility.trimNull(hideMap.get("EDIT_TYPE"));
	String inputType = ConfigUtil.getInputType(show_type,edit_type);//�ı�������
	String fieldValue = ConfigUtil.getPageElementValue(identity_code,default_flag,default_value,inputType,interfaceField_code,modShowDataList);
	
%>
<input type="hidden" name="<%=interfaceField_code%>" value="<%=fieldValue%>"/>
<%}%>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<img border="0" src="/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>����Ҫ�����</b></font>
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
	String subareaTitle = "";//��������
	String subareaAlign = "";//�������뷽ʽ
	String subareaSize = "";//�������������С
	String subareaFamily = "";//����������������
	String subareaWeight = "";//�������������ϸ
	String subareaColor = "";//��������������ɫ
	String subareaLeftSize = "";//�����󳤶�
	String subareaRightSize = "";//�����ҳ���
	String subareaAllSize = "";//����ȫ����
	
	int totalNum =fieldShowList.size();
	boolean subarea_flag = false;
	%>
	<tr>
		<%  int behind_row = Utility.parseInt(rowMapTr.get("BEHIND_ROW").toString(),1);
			String elementValue = "";//Ԫ�ؿ�ֵ
			String elementName = "";//Ԫ�ؿ�����
			int num = 0;//ÿ���д���td�ĸ���
			for(int j=i;j<behind_row+i;j++){
			if(j<totalNum){//ѭ����Ų��ܴ����ܵĸ���
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
				
				boolean readonlyFlag = false;//ֻ����ʶ
				String inputType = "text";
				String tdStyle1="";
				String tdStyle2="";
				String tdRequiredStyle1="";
				String tdVisibledStyle1="";
				String tdVisibledStyle2="";
				String inputStyle ="";//input��ĸ߶ȡ��������
				
				readonlyFlag = ConfigUtil.getReadonlyFlag(identity_code,readonly_flag);//ֻ������
				inputType = ConfigUtil.getInputType(show_type,edit_type);//�ı�������
				if(default_flag.equals("1")){
					elementValue =default_value;
					if(inputType.equals("money")){//����Ĭ��ֵ����ת��
						if(!"".equals(elementValue)){
							elementValue = ConfigUtil.MoneyTransform(elementValue);//����ת��
						}
					}
				}
				if(readonlyFlag){//ֻ���ǻ�ȡselect��radio��checkbox��Ӧ������ֵ
					//elementName =ConfigUtil.getTableValueName(inputType,value_type,elementValue,value_content);//��ȡԪ��ֵ����
					elementName =ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,elementValue,"SelectName");//��ȡԪ��ֵ����
				}
				align_type = ConfigUtil.getAlignType(align_type);//���뷽ʽ
				tdRequiredStyle1 =ConfigUtil.getRequiredFlag(required_flag);//�����ж�
				tdVisibledStyle1 = ConfigUtil.getVisibledFlag(identity_code,visibled_flag);//��ǩ�����Ƿ�ɼ�
				tdVisibledStyle2 = ConfigUtil.getVisibledFlag(identity_code,visibled_flag);//��ǩ���Ƿ�ɼ�
				String[] tdStr1 ={tdRequiredStyle1,tdVisibledStyle1};
				String[] tdStr2 ={tdVisibledStyle2};
				tdStyle1 =ConfigUtil.getCombinationStyle(tdStr1);//��ʽ���
				tdStyle2 =ConfigUtil.getCombinationStyle(tdStr2);
				inputStyle = ConfigUtil.getInputStyle(show_width,show_height);//Ԫ�ؿ��ȸ߶���ʽ
				
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
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.<%=interfaceField_code%>,theform.<%=interfaceField_code%>.value,this);"> 
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
				<%}else{//ֻ��ʱ����
					if(inputType.equals("textarea")){	
				%>
					<input type="textarea" name="<%=interfaceField_code%>" readonly="readonly" <%=align_type%> <%=inputStyle%> value="<%=elementValue%>" onkeydown="javascript:nextKeyPress(this)">
				<% }else if(inputType.equals("selectbox")||inputType.equals("radio")||inputType.equals("checkbox")){%>
					<input type="text" name="<%=interfaceField_code%>" readonly="readonly" class="edline" <%=align_type%> <%=inputStyle%> value="<%=elementName%>" onkeydown="javascript:nextKeyPress(this)">
				<% }else{%>
					<input type="text" name="<%=interfaceField_code%>" readonly="readonly" class="edline" <%=align_type%> <%=inputStyle%>  value="<%=elementValue%>" onkeydown="javascript:nextKeyPress(this)">
				<%}}%>
				<%if(!(readonly_flag.equals("1")&&show_unit.length()>20)){//������ֻ��ʱȥ������ĵ�����ť
				%>
					<%=show_unit%>
				<%}%>
			</td>
		<%
			//��������е����һ��Ԫ��Ϊ���������ж�
			String subareaFlag = Utility.trimNull(rowMapTd.get("SUBAREA_FLAG"));//�Ƿ����
			if(subareaFlag.equals("1")){
				Map  subareaMap = (Map)fieldShowList.get(j+1);
				String subareaEditType =Utility.trimNull(subareaMap.get("EDIT_TYPE"));
				String subareaShowType =Utility.trimNull(subareaMap.get("SHOW_TYPE"));
				String subareaInputType = ConfigUtil.getInputType(subareaShowType,subareaEditType);
				Map	 subareaNextMap = (Map)fieldShowList.get(j+2);
				String nextBehindRow = Utility.trimNull(subareaNextMap.get("BEHIND_ROW"),"1");
				//ÿ�е����Ԫ�ز����¸�Ϊ��������ÿ������Ԫ�ز����¸�Ԫ��Ϊ�������¸�Ԫ�غϲ��в�Ϊ1
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
	<%if(subarea_flag){//��������%>
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
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">����(<u>C</u>)</button>
		&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>
