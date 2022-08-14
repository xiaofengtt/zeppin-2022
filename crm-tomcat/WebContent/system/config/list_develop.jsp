<%@ page contentType="text/html; charset=GBK" import="enfo.crm.project.*,enfo.crm.vo.*, enfo.crm.system.*,enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,java.util.*,enfo.crm.system.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
	String regionCode = Utility.trimNull(request.getParameter("RegionCode"));
	String showCount = Utility.trimNull(request.getParameter("ShowCount"));
	String addCount = Utility.trimNull(request.getParameter("AddCount"));
	String redCount = Utility.trimNull(request.getParameter("RedCount"));
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
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
</HEAD>
<BODY class="BODY">
<form name="theform" method="POST" action="">
<input type="hidden" name="AddCount" value="<%=addCount%>" />
<input type="hidden" name="RedCount" value="<%=redCount%>" />
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
						<button type="button"  class="xpbutton5" accessKey=n id="btnNew" name="btnNew" title="����һ��" onclick="javascript:newInfo();">����һ��<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton5" accessKey=l id="btnDel" name="btnDel" title="ɾ��һ��" onclick="javascript:deleteInfo();">ɾ��һ��(<u>L</u>)</button>
						&nbsp;&nbsp;&nbsp; 
						<button type="button"  class="xpbutton5" accessKey=t id="btnDel" name="btnDel" title="ɾ����ӦField" onclick="javascript:delInfo();">ɾ����ӦField(<u>T</u>)</button>
						&nbsp;&nbsp;&nbsp;
					</td>	
				</tr>	
			</table>
			<br><b>������ʾ�ֶ�����</b>
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
			<br><b>���������ֶ�����</b>
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
		<br><b>�����ѯ�ֶ�����</b>
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
					
	    </TD>
	    </TR>			
	</TABLE>
	</TD>
	</TR>
</TABLE>
<script language=javascript>
	//----------------------����list---------------//
	function showList(orderNo,fieldID,interfaceCode,interfaceName){
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
	//----------------------����---------------//
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
	//----------------------ɾ��---------------//
	function deleteInfo(){
		document.theform.RedCount.value="1";
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


