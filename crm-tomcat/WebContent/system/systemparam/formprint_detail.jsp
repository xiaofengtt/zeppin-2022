<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String interfaceType_code = "FormPrint020";
ConfigLocal configLocal = EJBFactory.getConfig();
boolean bSuccess=false;

//ͨ��ҳ�������Ҹñ��Ӧ�������ֶ�
String otherFormCode = Utility.trimNull(request.getParameter("form_code"),"0");
String form_name = Utility.trimNull(request.getParameter("form_name"),"");
//ͨ��ҳ�������Ҹñ��Ӧ�������ֶκͱ���

String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
List catalogList =ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = (String)catalogList.get(0);
String grid_name =(String)catalogList.get(1);
String freeform_name =(String)catalogList.get(2);
	
String delStr=identityCode+"DEL";
if(request.getMethod().equals("POST")){//ɾ����Ϣ
	String[] s = request.getParameterValues(delStr);
	if(s.length>0){
		ConfigUtil.delInfo(configLocal,s,table_code,identityCode);
		bSuccess = true;
	}
}

//��ѯʱ�õ������ݼ���
List queryFieldList = configLocal.queryShowList(interfaceType_code);
String[] queryFieldColStr =new String[queryFieldList.size()];//�ֶμ�
String[] queryFieldNameStr =new String[queryFieldList.size()];//�ֶ����Ƽ�
String[] queryValueStr = new String[queryFieldList.size()];//��ѯ�ֶ�ҳ��ֵ
String[] queryConditionTypeStr = new String[queryFieldList.size()];//��ѯ���ͼ�
String[] queryConditionValueStr = new String[queryFieldList.size()];//������ѯ�����ֵ
String[] queryInputTypeStr = new String[queryFieldList.size()];//Ԫ�ؿ����ͼ�
String[] queryValueTypeStr = new String[queryFieldList.size()];//����������Դ���ͼ�
String[] queryValueContentStr =new String[queryFieldList.size()];//���ݲ�ѯ��伯
//��ѯ�����
ConfigUtil.queryConditionBlock(request,queryFieldList,queryFieldColStr,queryFieldNameStr,queryInputTypeStr,queryValueTypeStr,queryValueContentStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);
List tableFieldList = configLocal.tableFieldShowList(interfaceType_code);//��ѯ��Ҫ��ʾ��Ч��ҳ�����ֶ�
String[] fieldStrs = new String[tableFieldList.size()];//��ʾ�ֶα���
String[] fieldNameStrs = new String[tableFieldList.size()];//��ʾ�ֶ�����
ConfigUtil.pageValidColShowBlock(tableFieldList,fieldStrs,fieldNameStrs);//��ȡ�ֶκ��ֶ����Ƽ���

//������ѯ�ֶ�
List replaceColValue = new ArrayList();//��Ҫ�滻��ֵ
replaceColValue.add(String.valueOf(input_operatorCode));
String sqlContent = ConfigUtil.getOtherQuerySql(interfaceType_code,replaceColValue);

IPageList pageList = new JdbcPageList();
if(!otherFormCode.equals("0")){//�Լ�ƴ�ӵ�������ѯ�������
		sqlContent =sqlContent + " and form_code='"+otherFormCode+"'";
		pageList= configLocal.showPageDataOther(interfaceType_code,table_code,queryFieldColStr,queryValueStr,queryConditionValueStr,
											sqlContent,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,-1));
}else{
		pageList= configLocal.showPageDataOther(interfaceType_code,table_code,queryFieldColStr,queryValueStr,queryConditionValueStr,
											sqlContent,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
}
//��ö�Ӧ���ֵ���滻
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
//-----------------�༭-------------------
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
//-----------------����---------------------
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
//----------����-----------
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
request.setAttribute("queryFieldColStr",queryFieldColStr);//��ѯ�ֶμ���
request.setAttribute("queryFieldNameStr",queryFieldNameStr);//��ѯ�ֶ����Ƽ�;
request.setAttribute("queryConditionTypeStr",queryConditionTypeStr);//��ѯ�ֶβ�ѯ���ͼ�
request.setAttribute("queryConditionValueStr",queryConditionValueStr);//��ѯ�ֶ�ѡ��Ĳ�ѯ����
request.setAttribute("queryInputTypeStr",queryInputTypeStr);//Ԫ�ؿ����ͼ�
request.setAttribute("queryValueTypeStr",queryValueTypeStr);//����������Դ���ͼ�
request.setAttribute("queryValueContentStr",queryValueContentStr);//���ݲ�ѯ���
request.setAttribute("queryValueStr",queryValueStr);//��ѯ�ֶ�ҳ��ֵ
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
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">��ѯ(<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp; 
							<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="�½���¼" onclick="javascript:newInfo();">����(<u>N</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=l id="btnDel" name="btnDel" title="ɾ��" onclick="javascript: remove('<%=delStr%>')">ɾ��(<u>L</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<%if(!("0".equals(otherFormCode))){%> 
							<button type="button"  class="xpbutton3" accessKey=b id="btnDel" name="backDel" title="����" onclick="javascript: doBack()">����(<u>B</u>)</button>
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
