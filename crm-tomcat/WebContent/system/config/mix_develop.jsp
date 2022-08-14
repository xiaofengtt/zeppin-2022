<%@ page contentType="text/html; charset=GBK" import="enfo.crm.project.*,enfo.crm.vo.*, enfo.crm.system.*,enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,java.util.*,enfo.crm.system.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
	String regionCode = Utility.trimNull(request.getParameter("RegionCode"));
	String showCount = Utility.trimNull(request.getParameter("ShowCount"));
	String addCount = Utility.trimNull(request.getParameter("AddCount"));
	String redCount = Utility.trimNull(request.getParameter("RedCount"));
	String addRowCount = Utility.trimNull(request.getParameter("AddRowCount"));
	String redRowCount = Utility.trimNull(request.getParameter("RedRowCount"));	
	String actionFlag = Utility.trimNull(request.getParameter("ActionFlag"));
	//System.out.println("addCount:"+addCount+"<br>");
	//System.out.println("redCount:"+redCount+"<br>");
	if("".equals(actionFlag)){
		if("".equals(addCount)){
		 	addCount="0";
		}else if(Integer.parseInt(addCount)>0){
			if("".equals(redCount)){
				redCount="0";
			}else if(Integer.parseInt(redCount)>0){
				addCount =String.valueOf(Integer.parseInt(addCount)-1);
			}
			if("0".equals(redCount)){
				addCount =String.valueOf(Integer.parseInt(addCount)+1);
			}
		}
	}
	if("".equals(actionFlag)){
		if("".equals(addRowCount)){
		 	addRowCount="0";
		}else if(Integer.parseInt(addRowCount)>0){
			if("".equals(redRowCount)){
				redRowCount="0";
			}else if(Integer.parseInt(redRowCount)>0){
				addRowCount =String.valueOf(Integer.parseInt(addRowCount)-1);
			}
			if("0".equals(redRowCount)){
				addRowCount =String.valueOf(Integer.parseInt(addRowCount)+1);
			}
		}
	}
	if("".equals(showCount)) showCount="5";
	if("".equals(regionCode)) regionCode="";
	//System.out.println("addCount1:"+addCount+"<br>");
	//System.out.println("redCount1:"+redCount+"<br>");	
	ConfigLocal configLocal = EJBFactory.getConfig();
	if("delete".equals(actionFlag)){
		if(request.getMethod().equals("POST")){//ɾ����Ϣ
			String[] s = request.getParameterValues("regionID");
			if(s.length>0){
				ConfigUtil.delInfo(configLocal,s,"INTERFACE_FIELD"," INTERFACEFIELD_ID");
			}
		}	
	}

	String fieldName="",fieldID="",interfaceCode="",interfaceName="",fieldWidth="",orderNo="";
	Map fieldMap=null;
	String interfaceType_code = regionCode;
	String table_code = ConfigUtil.getPropertyNameById("interface_catalog","table_code","interfacetype_code",interfaceType_code);
	
	List tableFieldList = configLocal.tableFieldShowList(interfaceType_code);//��ѯ��Ҫ��ʾ��Ч��ҳ�����ֶ�
	String[] fieldStrs = new String[tableFieldList.size()];//��ʾ�ֶα���
	String[] fieldNameStrs = new String[tableFieldList.size()];//��ʾ�ֶ�����
	ConfigUtil.pageValidColShowBlock(tableFieldList,fieldStrs,fieldNameStrs);//��ȡ�ֶκ��ֶ����Ƽ���
	List fieldHideList = configLocal.tableNoShowList(interfaceType_code);//��ѯ��Ҫ��ʾ��Ч��ҳ�����ֶ�
	
	List queryFieldList = configLocal.queryShowList(interfaceType_code);
	String[] queryFieldColStr =new String[queryFieldList.size()];//�ֶμ�
	String[] queryFieldNameStr =new String[queryFieldList.size()];//�ֶ����Ƽ�
	String[] queryValueStr = new String[queryFieldList.size()];//��ѯ�ֶ�ҳ��ֵ
	String[] queryConditionTypeStr = new String[queryFieldList.size()];//��ѯ���ͼ�
	String[] queryConditionValueStr = new String[queryFieldList.size()];//������ѯ�����ֵ
	String[] queryInputTypeStr = new String[queryFieldList.size()];//Ԫ�ؿ����ͼ�
	String[] queryValueTypeStr = new String[queryFieldList.size()];//����������Դ���ͼ�
	String[] queryValueContentStr =new String[queryFieldList.size()];//���ݲ�ѯ��伯
	ConfigUtil.queryConditionBlock(request,queryFieldList,queryFieldColStr,queryFieldNameStr,queryInputTypeStr,queryValueTypeStr,queryValueContentStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);
	//��������������Ӧ�Ĵ���ֵ
	List selectValuelist=ConfigUtil.getSelectValue(tableFieldList,"QueryAllSelect");

	Integer identity_code = new Integer(0);
	List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,new Integer(0),"2");//��ȡҳ��������ʾ����ЧԪ�ؼ���
	List fieldHideInfo = ConfigUtil.getFieldShowList(interfaceType_code,new Integer(0),"3");//��ȡҳ���������ص���ЧԪ�ؼ���
	List modShowDataList =new ArrayList();

		
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>	
<LINK href="/system/configstyle.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
</HEAD>
<BODY class="BODY">
<form name="theform" method="POST" action="">
<input type="hidden" name="AddCount" value="<%=addCount%>" />
<input type="hidden" name="RedCount" value="<%=redCount%>" />
<input type="hidden" name="AddRowCount" value="<%=addRowCount%>" />
<input type="hidden" name="RedRowCount" value="<%=redRowCount%>" />
<input type="hidden" name="ActionFlag" value="<%=actionFlag%>" />
<input type="hidden" name="regionID" value="" />
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<TD vAlign=top align=left>	
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
		<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						���ô��룺<input type="text" size="20" name="RegionCode" value="<%=regionCode%>" onclick="javascript:queryInfo();">&nbsp;&nbsp;&nbsp; 
						���ø�����<input type="text" size="3" name="ShowCount" value="<%=showCount%>" onclick="javascript:configInfo();">&nbsp;&nbsp;&nbsp; 
						<button type="button"  class="xpbutton5" accessKey=n id="btnNew" name="btnNew" title="����һ��" onclick="javascript:newInfo();">����һ��(<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton5" accessKey=l id="btnDel" name="btnDel" title="ɾ��һ��" onclick="javascript:deleteInfo();">ɾ��һ��(<u>L</u>)</button>
						&nbsp;&nbsp;&nbsp; 
						<button type="button"  class="xpbutton5" accessKey=r id="btnNew" name="btnNew" title="����һ��" onclick="javascript:newRowInfo();">����һ��(<u>R</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton5" accessKey=t id="btnDel" name="btnDel" title="ɾ��һ��" onclick="javascript:deleteRowInfo();">ɾ��һ��(<u>T</u>)</button>
						&nbsp;&nbsp;&nbsp; 	
						<button type="button"  class="xpbutton5" accessKey=t id="btnDel" name="btnDel" title="ɾ����ӦField" onclick="javascript:delInfo();">ɾ����ӦField(<u>T</u>)</button>
						&nbsp;&nbsp;&nbsp;	
					</td>	
				</tr>	
			</table>
			     	  
<br><b>List������ʾ�ֶ�����</b>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
				<%if(tableFieldList.size()>0){%>
					<tr class="trh">
					<%for(int i=0;i<fieldNameStrs.length;i++){
							fieldMap = (Map)tableFieldList.get(i);
							fieldName = Utility.trimNull(fieldMap.get("INTERFACEFIELD_NAME"));
							fieldID = Utility.trimNull(fieldMap.get("INTERFACEFIELD_ID"));
							orderNo = Utility.trimNull(fieldMap.get("ORDER_NO"));
							interfaceCode = Utility.trimNull(fieldMap.get("INTERFACETYPE_CODE"));
							interfaceName = Utility.trimNull(fieldMap.get("INTERFACETYPE_NAME"));
					%>
						<td class="tdh" align="center" onclick="javascript:showList('<%=i%>','<%=fieldID%>','<%=interfaceCode%>','<%=interfaceName%>');"><%=fieldName+"��"+orderNo+"��"%></td>
					<%}%>
					<%for(int i=1;i<Integer.parseInt(addCount);i++){%>
						 <td class="tdh" align="center" onclick="javascript:showList('<%=i%>','0','<%=interfaceCode%>','<%=interfaceName%>');"><font style="font-weight: bold">field<%=(i+49)%></font></TD> 
			        <%}%> 	
					</tr>
					<%for(int k=1;k<6;k++){%>
					<tr class="tr0">
						<%for(int j=0;j<fieldStrs.length;j++){
							fieldMap = (Map)tableFieldList.get(j);
							fieldWidth = Utility.trimNull(fieldMap.get("TABLEFIELDWIDTH"));
						%>
							<td align="center" width="<%=fieldWidth%>%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<%}%>
			        <%for(int i=1;i<Integer.parseInt(addCount);i++){%>
						 <td class="tdh" align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;</font></TD> 
			        <%}%> 						
					</tr>
					<%}%> 
				<%}%>	
				<%if(tableFieldList.size()==0){%>
					<tr class="trh">
	               <%for(int i=0;i<Integer.parseInt(showCount);i++){%>
					    <td class="tdh" align="center" onclick="javascript:showList('<%=i%>','0');"><font style="font-weight: bold">field<%=i+1%></font></TD> 
		           <%}%>
			        <%for(int i=1;i<Integer.parseInt(addCount);i++){%>
						<td class="tdh" align="center" onclick="javascript:showList('<%=i%>','0');"><font style="font-weight: bold">field<%=(i+49)%></font></TD> 
			        <%}%> 	
		           </tr>
		    	 <%for(int j=1;j<6;j++){%>
		           <TR class="tr0">  
		           	<%for(int i=0;i<Integer.parseInt(showCount);i++){%>
		           		<td class="tdh" align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;</font></TD> 
		           	<%}%>	
			        <%for(int i=1;i<Integer.parseInt(addCount);i++){%>
						<td class="tdh" align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;</font></TD> 
			        <%}%>
		           </tr>
		        <%}%>
		       <%}%>
			</table>
			<%if(fieldHideList.size()>0){%>
			<br><b>List���������ֶ�����</b>
			<%}%>
			<table class="tablelinecolor" id="table4" border="0" width="100%" align="center" cellspacing="1" cellpadding="2">	 
				<%for(int i=0;i<fieldHideList.size();i++){//�����ֶμ�
				Map  hideMap = (Map)fieldHideList.get(i);
					fieldID = Utility.trimNull(hideMap.get("INTERFACEFIELD_ID"));
					fieldName = Utility.trimNull(hideMap.get("INTERFACEFIELD_NAME"));
					orderNo = Utility.trimNull(hideMap.get("ORDER_NO"));
			%>
				<tr class="trh">
						<td class="tdh" width="20%" align="left" onclick="javascript:showList('<%=i%>','<%=fieldID%>');"><%=fieldName+"��"+orderNo+"��"%></td>
						<td width="80%" align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></TD> 
				</tr>
			<%}%> 
			</table>	
			
	<%if(queryFieldList.size()>0){%>
		<br><b>List�����ѯ�ֶ�����</b>
	<%}%>
<table class="tablelinecolor" id="table5" border="0" width="100%" align="center" cellspacing="1" cellpadding="2">
		<%for(int i=0;i<queryFieldColStr.length;i++){%>
		<tr class="tr0">
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
			if(inputType.equals("selectbox")){//������
			if(valueType.equals("1")){
		%>
				<SELECT size="1" name="<%=queryFieldColStr[i]%>" style="width: 150px;" onkeydown="javascript:nextKeyPress(this)">
					<%=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"SelectBox")%>
				</SELECT>
		<%}else{%>
				<SELECT size="1" name="<%=queryFieldColStr[i]%>" style="width: 150px;" onkeydown="javascript:nextKeyPress(this)">
					<%=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"SelectBox")%>
				</SELECT>
		<%}}else if(inputType.equals("radio")){
			if(valueType.equals("1")){
		%>
				<%=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"CheckRadio")%>
		<%}else{ %>
				<%=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"CheckRadio")%>
		<%}}else if(inputType.equals("checkbox")){
			if(valueType.equals("1")){
		 %>
				<%=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"CheckBox")%>
		<%}else{ %>
				<%=ConfigUtil.getSelectContent(selectValuelist,queryFieldColStr[i],queryValueStr[i],"CheckBox")%>
		<%}}else{//��������%>
			<input type="text" name="<%=queryFieldColStr[i]%>" style="width: 150px; size="15" value="<%=queryValueStr[i]%>" onkeydown="javascript:nextKeyPress(this)">
		<%}%>
		<div id="divbet<%=i%>" style="display:none;">
			<%if(queryConditionTypeStr[i].indexOf("7")!=7){%>
			<input type="text" name="<%=queryFieldColStr[i]%>one<%=i%>"  style="width: 75px;" size="15" value="" onkeydown="javascript:nextKeyPress(this)">&nbsp;&nbsp;��
			<input type="text" name="<%=queryFieldColStr[i]%>two<%=i%>"  style="width: 75px;" size="15" value="" onkeydown="javascript:nextKeyPress(this)">
			<%}%>
		</div>
		</td>
		</tr>
		<%}%>
	</table>	
	

			<br><b>Info������ʾ�ֶ�����</b>
<table class="tablelinecolor" id="table3" border="0" width="100%" align="center" cellspacing="1" cellpadding="2">
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
	String subfieldID = "";//����ȫ����
	String suborderNo = "";//����ȫ����
	
	int totalNum =fieldShowList.size();
	boolean subarea_flag = false;
	%>
	<tr class="tr0">
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
				fieldID = Utility.trimNull(rowMapTd.get("INTERFACEFIELD_ID"));
				String interfaceField_code = Utility.trimNull(rowMapTd.get("INTERFACEFIELD_CODE"));
				String interfaceField_name = Utility.trimNull(rowMapTd.get("INTERFACEFIELD_NAME"));
				orderNo = Utility.trimNull(rowMapTd.get("ORDER_NO"));
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
			<td align="right" height="30" <%=tdStyle1%> onclick="javascript:showInfo('<%=i%>','<%=fieldID%>');"><%=interfaceField_name+"��"+interfaceField_code+"-"+orderNo+"��"%>:</td>
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
            <%for(int iCount=1;iCount<Integer.parseInt(addCount);iCount++){%>
				<td class="tdh" align="center" onclick="javascript:showInfo('<%=iCount%>','0');"><font style="font-weight: bold">field<%=(iCount+9)%></font></TD> 
				<td class="tdh" align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></TD> 
	        <%}%>			
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
					subfieldID = Utility.trimNull(subareaMap.get("INTERFACEFIELD_ID"));
					suborderNo = Utility.trimNull(subareaMap.get("ORDER_NO"));					
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
			 <td colspan="6" onclick="javascript:showInfo('<%=i%>','<%=subfieldID%>');">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="<%=subareaColor%>" style="font-size:<%=subareaSize%>;font-family:<%=subareaFamily%>;font-weight:<%=subareaWeight%>";><%=subareaTitle+"��"+suborderNo+"��"%></font>&nbsp;<input class="edlinenew" disabled="disabled" size="<%=subareaAllSize%>">
			</td>
		  <% }else if(subareaAlign.equals("2")){%>
		  	<td colspan="3" align="right">
				<input class="edlinenew" disabled="disabled" size="<%=subareaLeftSize%>">
			</td>
			<td colspan="3" onclick="javascript:showInfo('<%=i%>','<%=subfieldID%>');">
				&nbsp;<font color="<%=subareaColor%>" style="font-size:<%=subareaSize%>;font-family:<%=subareaFamily%>;font-weight:<%=subareaWeight%>";><%=subareaTitle+"��"+suborderNo+"��"%></font>&nbsp;<input class="edlinenew" disabled="disabled" size="<%=subareaRightSize%>">
			</td>
		  <%}else if(subareaAlign.equals("3")){%>
		  	<td colspan="6" onclick="javascript:showInfo('<%=i%>','<%=subfieldID%>');">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="edlinenew" disabled="disabled" size="<%=subareaAllSize%>">&nbsp;<font color="<%=subareaColor%>" style="font-size:<%=subareaSize%>;font-family:<%=subareaFamily%>;font-weight:<%=subareaWeight%>";><%=subareaTitle+"��"+suborderNo+"��"%></font>&nbsp;
			</td>
		  <%}%>
	 </tr>
		<%}	
		}
		%>
		<%if(fieldShowList.size()==0){%>
             <%for(int jCount=1;jCount<6;jCount++){%>      
               <TR class="tr0" >  
	               <%for(int iCount=0;iCount<Integer.parseInt(showCount);iCount++){%>
					 <td class="tdh" align="center" onclick="javascript:showInfo('<%=iCount%>','0');"><font style="font-weight: bold">field<%=5*(jCount-1)+(iCount+1)%></font></TD> 
					 <td class="tdh" align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></TD> 
		           <%}%>
	               <%for(int iCount=1;iCount<Integer.parseInt(addCount);iCount++){%>
					 <td class="tdh" align="center" onclick="javascript:showInfo('<%=iCount%>','0');"><font style="font-weight: bold">field<%=(iCount+9)%></font></TD> 
					 <td class="tdh" align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></TD> 
		           <%}%>	          
	          </TR>	
	        <%}%>	
	    <%}%>    
			<%for(int iCount=1;iCount<Integer.parseInt(addRowCount);iCount++){%>
				<tr class="tr0">
					<td class="tdh" align="center" onclick="javascript:showInfo('<%=iCount%>','0');"><font style="font-weight: bold">field<%=(iCount+9)%></font></TD> 
					<td class="tdh" colspan=10 align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></TD> 
			    </tr>
		    <%}%>		        
</table>	     
<%if(fieldHideInfo.size()>0){%>
<br><b>Info���������ֶ�����</b>
<%}%>
<table class="tablelinecolor" id="table4" border="0" width="100%" align="center" cellspacing="1" cellpadding="2">	 
	<%for(int i=0;i<fieldHideInfo.size();i++){//�����ֶμ�
	Map  hideMap = (Map)fieldHideInfo.get(i);
	fieldID = Utility.trimNull(hideMap.get("INTERFACEFIELD_ID"));
	String fieldCode = Utility.trimNull(hideMap.get("INTERFACEFIELD_CODE"));
	fieldName = Utility.trimNull(hideMap.get("INTERFACEFIELD_NAME"));
	orderNo = Utility.trimNull(hideMap.get("ORDER_NO"));
%>
	<tr class="tr0">
			<td align="left" height="30" onclick="javascript:showInfo('<%=i%>','<%=fieldID%>');"><%=fieldName+"��"+fieldCode+"-"+orderNo+"��"%>:</td>
			<td class="tdh" align="center"><font style="font-weight: bold">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></TD> 
	</tr>
<%}%> 
</table>	 				     	  
			     	         
	    </TD>
	    </TR>			
	</TABLE>
	</TD>
	</TR>
</TABLE>
<script language=javascript>
	//----------------------����list---------------//
	function showList(orderNo,fieldID){
		var regionCode=trim(document.theform.RegionCode.value);
		if(showModalDialog('/system/config/interface_content_info.jsp?interfacetype_code_type=020&identity_code='+fieldID+'&table_code=INTERFACE_FIELD&thisinterfacetype_code='+regionCode, '', 'dialogWidth:1050px;dialogHeight:660px;status:0;help:0') != null)
		{
			sl_update_ok();
			document.theform.ActionFlag.value="Save";
			document.theform.submit();
		}
		document.theform.regionID.value=fieldID;
	}
	//----------------------����info---------------//
	function showInfo(orderNo,fieldID){
		var regionCode=trim(document.theform.RegionCode.value);
		if(showModalDialog('/system/config/interface_content_info.jsp?interfacetype_code_type=020&identity_code='+fieldID+'&table_code=INTERFACE_FIELD&thisinterfacetype_code='+regionCode, '', 'dialogWidth:1050px;dialogHeight:660px;status:0;help:0') != null)
		{
			sl_update_ok();
			document.theform.ActionFlag.value="Save";
			document.theform.submit();
		}
		document.theform.regionID.value=fieldID;
	}
	//----------------------��ѯ---------------//
	function queryInfo(){
		var regionCode=trim(document.theform.RegionCode.value);
		if(typeof(regionCode)!="undefined" && regionCode!="")
		{
			if(confirm("ȷ��Ҫִ�в�����")){
				document.theform.submit();
			}
		}
	}
	//----------------------����---------------//
	function configInfo(){
		var showCount=trim(document.theform.ShowCount.value);
		if(typeof(showCount)!="undefined" && showCount!="")
		{	
			if(confirm("ȷ��Ҫִ�в�����")){
				document.theform.submit();
			}
		}
	}
	//----------------------����һ��---------------//
	function newInfo(){
		if("<%=addCount%>"==0){
			document.theform.AddCount.value="1";
			document.theform.RedCount.value="0";
			document.theform.ActionFlag.value="";
		}else{
			document.theform.AddCount.value="<%=addCount%>";
			document.theform.RedCount.value="0";
			document.theform.ActionFlag.value="";
		}
		document.theform.submit();
	}
	//----------------------ɾ��һ��---------------//
	function deleteInfo(){
		document.theform.RedCount.value="1";
		document.theform.ActionFlag.value="";
		document.theform.submit();
	}
	//----------------------����һ��---------------//
	function newRowInfo(){
		if("<%=addRowCount%>"==0){
			document.theform.AddRowCount.value="1";
			document.theform.RedRowCount.value="0";
			document.theform.ActionFlag.value="";
		}else{
			document.theform.AddRowCount.value="<%=addRowCount%>";
			document.theform.RedRowCount.value="0";
			document.theform.ActionFlag.value="";
		}
		document.theform.submit();
	}
	//----------------------ɾ��һ��---------------//
	function deleteRowInfo(){
		document.theform.RedRowCount.value="1";
		document.theform.ActionFlag.value="";
		document.theform.submit();
	}	
	//----------------------ɾ��---------------//
	function delInfo(){
		var RegionID=trim(document.theform.regionID.value);
		if(typeof(RegionID)=="undefined" || RegionID==""){
			alert("��ѡ���Ӧ��field��");
			return;	
		}
		document.theform.regionID.value=RegionID;
		document.theform.ActionFlag.value="delete";
		if(confirm("ȷ��Ҫִ��ɾ����")){
			document.theform.submit();
		}
	}			
</script>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>


