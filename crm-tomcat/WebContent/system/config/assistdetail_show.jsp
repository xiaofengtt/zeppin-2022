<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%
List elementTdShowList = (List)request.getAttribute("elementTdShowList");
List dataShowList = (List)request.getAttribute("dataShowList");
String catalog_code = Utility.trimNull(request.getParameter("catalog_code"));
Integer record_id = Utility.parseInt(request.getParameter("record_id"),0);
%>
<table border="0" width="100%" align="center" cellspacing="3">
	<%for(int i=0;i<elementTdShowList.size();i++){
	Map  rowMapTr = new HashMap();
	String subareaTitle = "";//��������
	String subareaAlign = "";//�������뷽ʽ
	String subareaSize = "";//�������������С
	String subareaFamily = "";//����������������
	String subareaWeight = "";//�������������ϸ
	String subareaColor = "";//��������������ɫ
	String subareaLeftSize = "";//�����󳤶�
	String subareaRightSize = "";//�����ҳ���
	String subareaAllSize = "";//�����ܳ���
	boolean subarea_flag = false;
	int totalNum =elementTdShowList.size();
	if(i==0){
		rowMapTr = (Map)elementTdShowList.get(i);
		String firstEditType= Utility.trimNull(rowMapTr.get("EDIT_TYPE"));
		if(firstEditType.equals("8")){
			subareaTitle = Utility.trimNull(rowMapTr.get("ELEMENT_NAME"));
			subareaAlign = Utility.trimNull(rowMapTr.get("ALIGN_TYPE"));
			subareaSize = ConfigUtil.getFontStyle("FONT_SIZE",rowMapTr);
			subareaFamily = ConfigUtil.getFontStyle("FONT_FAMILY",rowMapTr);
			subareaWeight = ConfigUtil.getFontStyle("OVERSTRIKING_FLAG",rowMapTr);
			subareaColor = ConfigUtil.getFontStyle("FONT_COLOR",rowMapTr);
			String subareaShowStyle = Utility.trimNull(rowMapTr.get("SHOW_STYLE"));
			if(!subareaShowStyle.equals("")){
				String subareaStrs[] = subareaShowStyle.split(",");
				if(subareaStrs.length==3){
					subareaLeftSize =subareaStrs[0];
					subareaRightSize = subareaStrs[1];
					subareaAllSize = subareaStrs[2];
				}
			}
	%>
	<tr>
		 <%if(subareaAlign.equals("1")){%> 
			 <td colspan="6">
				<font color="<%=subareaColor%>" style="font-size:<%=subareaSize%>;font-family:<%=subareaFamily%>;font-weight:<%=subareaWeight%>";><%=subareaTitle%></font>&nbsp;<input class="edlinenew" disabled="disabled" size="<%=subareaAllSize%>">
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
	<%i++;}}%>
	<tr>
		<%
			rowMapTr = (Map)elementTdShowList.get(i);
			int behind_row = Utility.parseInt(rowMapTr.get("BEHIND_ROW").toString(),1);
			int num = 0;//ÿ���д���td�ĸ���
			for(int j=i;j<behind_row+i;j++){
			if(j<totalNum){//ѭ����Ų��ܴ����ܵĸ���
				Map  rowMapTd = (Map)elementTdShowList.get(j);
				String tableValue ="";//ҳ������ֵ
				String tableValueName ="";
				String tdEdit_type =Utility.trimNull(rowMapTd.get("EDIT_TYPE"));
				String tdShow_type =Utility.trimNull(rowMapTd.get("SHOW_TYPE"));
				String tdInputType =ConfigUtil.getInputType(tdShow_type,tdEdit_type);
				String tdBehind_row = Utility.trimNull(rowMapTd.get("BEHIND_ROW"));
				//ÿ�еĵ�һ����ϲ�����Ϊ1�����Ƿ������ͣ���Ԫ���½�td
				if((j==i || Integer.valueOf(tdBehind_row)==1)&&!tdInputType.equals("subarea")){
				num++;
				if(elementTdShowList.size() == dataShowList.size()){
					Map dataMap = (Map)dataShowList.get(j);
					tableValue = Utility.trimNull(dataMap.get("RESULT_VALUE"));
					tableValueName = Utility.trimNull(dataMap.get("VALUE_NAME"));
				}
				catalog_code = Utility.trimNull(rowMapTd.get("CATALOG_CODE"));
				String element_code = Utility.trimNull(rowMapTd.get("ELEMENT_CODE"));
				String element_name = "";
				if(record_id == 0){
					element_name = Utility.trimNull(rowMapTd.get("ELEMENT_NAME"));
				}else{//�޸�ʱȡconfig_data���е�element_name
					element_name = Utility.trimNull(rowMapTd.get("OLDELEMENT_NAME"));
				}
				String order_no = Utility.trimNull(rowMapTd.get("ORDER_NO"));
				String align_type = Utility.trimNull(rowMapTd.get("ALIGN_TYPE"));
				String show_type =Utility.trimNull(rowMapTd.get("SHOW_TYPE"));
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
				String show_style = Utility.trimNull(rowMapTd.get("SHOW_STYLE"));
				String overStriking_flag = ConfigUtil.getFontStyle("OVERSTRIKING_FLAG",rowMapTd);
				String font_color = ConfigUtil.getFontStyle("FONT_COLOR",rowMapTd);
				String font_size = ConfigUtil.getFontStyle("FONT_SIZE",rowMapTd);
				String font_family = ConfigUtil.getFontStyle("FONT_FAMILY",rowMapTd);
				
				String inputType = "text";
				String tdVisibledStyle1="";
				String tdVisibledStyle2="";
				String showRequired="";
				String inputStyle = "";//input��ĸ߶ȡ��������
				boolean readonlyFlag = false;
				
				if(record_id==0){
					if(default_flag.equals("1")){//��Ĭ��ֵʱ�Ĵ���
						tableValue= default_value;
					}
				}
				readonlyFlag = ConfigUtil.getReadonlyFlag(record_id,readonly_flag);//ֻ������
				inputType =ConfigUtil.getInputType(show_type,tdEdit_type);
				if(inputType.equals("money")){
					if(!"".equals(tableValue))tableValue = ConfigUtil.MoneyTransform(tableValue);//����ת��
				}
				align_type = ConfigUtil.getAlignType(align_type);//���뷽ʽ
				
				tdVisibledStyle1 = ConfigUtil.getVisibledFlag(record_id,visibled_flag);//��ǩ�����Ƿ�ɼ�
				tdVisibledStyle2 = ConfigUtil.getVisibledFlag(record_id,visibled_flag);//��ǩ���Ƿ�ɼ�
				inputStyle = ConfigUtil.getInputStyle(show_width,show_height);//Ԫ�ؿ��ȸ߶���ʽ
				
				if(required_flag.equals("1")) showRequired="*";
				font_color = ConfigUtil.getFontColor(font_color,required_flag);
				
		%>
			<td align="right" height="30" <%=tdVisibledStyle1%>><font color="<%=font_color%>" style="font-family:<%=font_family%>;font-size:<%=font_size%>;font-weight:<%=overStriking_flag%>"><%=showRequired+element_name%>: </font></td>
			<td align="left" colspan='<%=destride_row%>' <%=tdVisibledStyle2%>>
			<%if(!readonlyFlag){%>
				<%if(inputType.equals("text")){%>
					<input type="text" name="<%=element_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValue%>">
				<%}else if(inputType.equals("number")){%>
					<input type="text" onblur="checkAllNumber('<%=element_code%>')" name="<%=element_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValue%>">
				<%}else if(inputType.equals("integer")){%>
					<input type="text" onblur="checkInteger('<%=element_code%>')" name="<%=element_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValue%>">
				<%}else if(inputType.equals("textarea")){%>
					<textarea cols="100" rows="5" <%=align_type%> <%=inputStyle%> name="<%=element_code%>"><%=tableValue%></textarea>
				<%}else if(inputType.equals("date")){%>
					<INPUT TYPE="text" NAME="<%=element_code%>" <%=inputStyle%> value="<%=tableValue%>" class=selecttext>
					<%if(!readonly_flag.equals("1")){ %>
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.<%=element_code%>,theform.<%=element_code%>.value,this);"> 
					<%}%>
				<%}else if(inputType.equals("checkbox"))
				{
					if(value_type.equals("2")){
				%>
						<%=ConfigUtil.getParamCheckboxSql(value_content,tableValue,element_code)%>
				<%}else{%>
						<%=ConfigUtil.getDictParamCheckBoxs(value_content,tableValue,element_code,"")%>
				<%}
				}else if(inputType.equals("selectbox")){
					if(value_type.equals("2")){
				%>
					<SELECT size="1" name="<%=element_code%>" <%=inputStyle%> onchange="<%=show_style%>" onkeydown="javascript:nextKeyPress(this)">
						<%=ConfigUtil.getParamSelectSql(value_content,tableValue)%>
					</SELECT>
				<%}else{ %>
					<SELECT size="1" name="<%=element_code%>" <%=inputStyle%> onchange="<%=show_style%>" onkeydown="javascript:nextKeyPress(this)">
						<%=ConfigUtil.getDictParamConfig(value_content,tableValue)%>
					</SELECT>
				<%}
					}else if(inputType.equals("radio")){
					if(value_type.equals("2")){
				%>
						<%=ConfigUtil.getParamRadioSql(value_content,tableValue,element_code,"10010001")%>
				<% 
					}else{
				%>
						<%=ConfigUtil.getDictParamRadios(value_content,tableValue,element_code,"1")%>
				<%	
					}
				}else if(inputType.equals("money")){
				%>
					<input type="text" onblur="moneyChange('<%=element_code%>')" name="<%=element_code%>" <%=align_type%> <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValue%>">					
				<%}%>
				<%}else{//ֻ��ʱ����
					if(inputType.equals("textarea")){	
				%>
					<input type="textarea" readonly="readonly" name="<%=element_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValue%>">
				<% }else if(inputType.equals("selectbox")||inputType.equals("radio")||inputType.equals("checkbox")){%>
					<input type="text" readonly="readonly" class="edline" name="<%=element_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValueName%>">
				<% }else{%>
					<input type="text" readonly="readonly" class="edline" name="<%=element_code%>"  <%=align_type%>  <%=inputStyle%> onkeydown="javascript:nextKeyPress(this)" value="<%=tableValue%>">
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
				Map  subareaMap = (Map)elementTdShowList.get(j+1);
				String subareaEditType =Utility.trimNull(subareaMap.get("EDIT_TYPE"));
				String subareaShowType =Utility.trimNull(subareaMap.get("SHOW_TYPE"));
				String subareaInputType = ConfigUtil.getInputType(subareaShowType,subareaEditType);
				Map	 subareaNextMap = (Map)elementTdShowList.get(j+2);
				String nextBehindRow = Utility.trimNull(subareaNextMap.get("BEHIND_ROW"),"1");
				//ÿ�е����Ԫ�ز����¸�Ϊ��������ÿ������Ԫ�ز����¸�Ԫ��Ϊ�������¸�Ԫ�غϲ��в�Ϊ1
				if((j==behind_row+i-1 && subareaInputType.equals("subarea")) || (j!=behind_row+i-1 && subareaInputType.equals("subarea") && !nextBehindRow.equals("1"))){
					subarea_flag = true;
					subareaTitle = Utility.trimNull(subareaMap.get("ELEMENT_NAME"));
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
				<font color="<%=subareaColor%>" style="font-size:<%=subareaSize%>;font-family:<%=subareaFamily%>;font-weight:<%=subareaWeight%>";><%=subareaTitle%></font>&nbsp;<input class="edlinenew" disabled="disabled" size="<%=subareaAllSize%>">
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