<%@ page language="java" contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,java.util.*" %>
<%
	Integer identity_code =Utility.parseInt(request.getParameter("identity_code"),new Integer(0));
	List fieldShowList =(List)request.getAttribute("fieldShowList");
	List modShowDataList = (List)request.getAttribute("modShowDataList");
	//��������������Ӧ�Ĵ���ֵ
	List selectValuelist=ConfigUtil.getSelectValue(fieldShowList,"QueryAllSelect");
 %>
<table border="0" width="100%" cellspacing="3" class="product-list">
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
			int num = 0;//ÿ���д���td�ĸ���
			for(int j=i;j<behind_row+i;j++){
			if(j<totalNum){//ѭ����Ų��ܴ����ܵĸ���
				Map  rowMapTd = (Map)fieldShowList.get(j);
				String tdBehind_row = Utility.trimNull(rowMapTd.get("BEHIND_ROW"));
				String tdEdit_type = Utility.trimNull(rowMapTd.get("EDIT_TYPE"));
				String tdShow_type = Utility.trimNull(rowMapTd.get("SHOW_TYPE"));
				String tdInputType =ConfigUtil.getInputType(tdShow_type,tdEdit_type);
				//ÿ�еĵ�һ����ϲ�����Ϊ1�����Ƿ������ͣ���Ԫ���½�td
				if((j==i || Integer.valueOf(tdBehind_row).intValue()==1) && !tdInputType.equals("subarea")){
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
				String show_style = Utility.trimNull(rowMapTd.get("SHOW_STYLE"));//��ʱ����select������onchang�¼�
				String overStriking_flag = ConfigUtil.getFontStyle("OVERSTRIKING_FLAG",rowMapTd);
				String font_color = ConfigUtil.getFontStyle("FONT_COLOR",rowMapTd);
				String font_size = ConfigUtil.getFontStyle("FONT_SIZE",rowMapTd);
				String font_family = ConfigUtil.getFontStyle("FONT_FAMILY",rowMapTd);
				
				boolean readonlyFlag = false;//ֻ����ʶ
				String tableValue ="";//ҳ������ֵ
				String tableValueName ="";//���Ϊselect��radio��checkboxʱ��Ӧ������ֵ����
				String inputType = "text";
				String tdVisibledStyle1="";
				String tdVisibledStyle2="";
				String inputStyle ="";//input��ĸ߶ȡ��������
				String showRequired="";
				
				readonlyFlag = ConfigUtil.getReadonlyFlag(identity_code,readonly_flag);//ֻ������
				inputType = ConfigUtil.getInputType(show_type,edit_type);//�ı�������
				//�����޸�ʱ��ȡԪ��ֵ
				tableValue = ConfigUtil.getPageElementValue(identity_code,default_flag,default_value,inputType,interfaceField_code,modShowDataList);
				//ֻ���ǻ�ȡselect��radio��checkbox��Ӧ������ֵ
				if(readonlyFlag){
					//tableValueName =ConfigUtil.getTableValueName(inputType,value_type,tableValue,value_content);//��ȡԪ��ֵ����
					tableValueName =ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,tableValue,"SelectName");//��ȡԪ��ֵ����
				}
				align_type = ConfigUtil.getAlignType(align_type);//���뷽ʽ
				tdVisibledStyle1 = ConfigUtil.getVisibledFlag(identity_code,visibled_flag);//��ǩ�����Ƿ�ɼ�
				tdVisibledStyle2 = ConfigUtil.getVisibledFlag(identity_code,visibled_flag);//��ǩ���Ƿ�ɼ�
				inputStyle = ConfigUtil.getInputStyle(show_width,show_height);//Ԫ�ؿ��ȸ߶���ʽ
				
				if(required_flag.equals("1")) showRequired="*";
				font_color = ConfigUtil.getFontColor(font_color,required_flag);
		%>
			<%if(!inputType.equals("subarea")){%>
			<td align="right" height="30" <%=tdVisibledStyle1%>><font color="<%=font_color%>" style="font-family:<%=font_family%>;font-size:<%=font_size%>;font-weight:<%=overStriking_flag%>"><%=showRequired+interfaceField_name%>:</font></td>
			<%}%>
			<td align="left" colspan='<%=destride_row%>' <%=tdVisibledStyle2%>>
			<%if(!readonlyFlag){%>
				<%if(inputType.equals("text")){%>
					<input type="text" name="<%=interfaceField_code%>" <%=align_type%> <%=inputStyle%> value="<%=tableValue%>" onkeydown="javascript:nextKeyPress(this)">
				<%}else if(inputType.equals("number")){%>
					<input type="text" onblur="checkAllNumber('<%=interfaceField_code%>')" name="<%=interfaceField_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValue%>">
				<%}else if(inputType.equals("integer")){%>
					<input type="text" onblur="checkInteger('<%=interfaceField_code%>')" name="<%=interfaceField_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValue%>">
				<%}else if(inputType.equals("textarea")){%>
					<textarea cols="100" rows="5" name="<%=interfaceField_code%>" <%=align_type%> <%=inputStyle%>><%=tableValue%></textarea>
				<%}else if(inputType.equals("date")){%>
					<INPUT TYPE="text" onblur="checkVDate('<%=interfaceField_code%>')" NAME="<%=interfaceField_code%>" <%=inputStyle%> value="<%=tableValue%>" class=selecttext>
					<%if(!readonly_flag.equals("1")){%>
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.<%=interfaceField_code%>,theform.<%=interfaceField_code%>.value,this);"> 
					<%}%>
				<%}else if(inputType.equals("checkbox")){
					if(value_type.equals("2")){
				%>
						<%//=ConfigUtil.getParamCheckboxSql(value_content,tableValue,interfaceField_code)%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,tableValue,"CheckBox")%>
				<%}else{%>
						<%//=ConfigUtil.getDictParamCheckBoxs(value_content,tableValue,interfaceField_code,"")%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,tableValue,"CheckBox")%>
				<%}
				}else if(inputType.equals("selectbox")){
					if(value_type.equals("2")){
				%>
					<SELECT size="1" name="<%=interfaceField_code%>" <%=inputStyle%> onchange="<%=show_style%>" onkeydown="javascript:nextKeyPress(this)">
						<%//=ConfigUtil.getParamSelectSql(value_content,tableValue)%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,tableValue,"SelectBox")%>
					</SELECT>
				<%}else{ %>
					<SELECT size="1" name="<%=interfaceField_code%>" <%=inputStyle%> onchange="<%=show_style%>"  onkeydown="javascript:nextKeyPress(this)">
						<%//=ConfigUtil.getDictParamConfig(value_content,tableValue)%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,tableValue,"SelectBox")%>
					</SELECT>
				<%}
				}else if(inputType.equals("radio")){
					if(value_type.equals("2")){
				%>
						<%//=ConfigUtil.getParamRadioSql(value_content,tableValue,interfaceField_code,"")%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,tableValue,"CheckRadio")%>
				<% }else{%>
						<%//=ConfigUtil.getDictParamRadios(value_content,tableValue,interfaceField_code,"")%>
						<%=ConfigUtil.getSelectContent(selectValuelist,interfaceField_code,tableValue,"CheckRadio")%>
				<%}
				}else if(inputType.equals("money")){
				%>
					<input type="text" name="<%=interfaceField_code%>" onblur="moneyChange('<%=interfaceField_code%>')" <%=align_type%> <%=inputStyle%> value="<%=tableValue%>" onkeydown="javascript:nextKeyPress(this)">					
				<%}%>
			<%}else{//ֻ��ʱ����
					if(inputType.equals("textarea")){	
				%>
					<textarea cols="100" rows="5" readonly="readonly" name="<%=interfaceField_code%>" <%=align_type%> <%=inputStyle%>><%=tableValue%></textarea>
				<% }else if(inputType.equals("selectbox")||inputType.equals("radio")||inputType.equals("checkbox")){%>
					<input type="text" name="<%=interfaceField_code%>" readonly="readonly" class="edline" <%=align_type%> <%=inputStyle%> value="<%=tableValueName%>" onkeydown="javascript:nextKeyPress(this)">
				<% }else{%>
					<input type="text" name="<%=interfaceField_code%>" readonly="readonly" class="edline" <%=align_type%> <%=inputStyle%>  value="<%=tableValue%>" onkeydown="javascript:nextKeyPress(this)">
				<%}
			}%>
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