<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.project.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//��ʼ��������Ϣ
String interfaceType_code = "staffreimb030";
Integer object_id = Utility.parseInt(request.getParameter("object_id"), new Integer(0));
String edit_right = Utility.trimNull(request.getParameter("edit_right"));
Integer object_type = Utility.parseInt(request.getParameter("object_type"), new Integer(0));
ConfigLocal configLocal = EJBFactory.getConfig();
BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
String showFlag = Utility.trimNull(request.getParameter("ShowFlag"));
String modiTypeFlag = request.getParameter("modiTypeFlag");
boolean bSuccess = false;
//ͨ��ҳ�������Ҹñ��Ӧ�������ֶκͱ���
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
List catalogList =ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = (String)catalogList.get(0);
String grid_name = (String)catalogList.get(1);
String freeform_name = (String)catalogList.get(2);
String delStr=identityCode+"DEL";//�б���checkbox��nameֵ
if(request.getMethod().equals("POST")){//ɾ����Ϣ
	String[] s = request.getParameterValues(delStr);
	if(s!=null && s.length>0){
		if("submitAll".equals(modiTypeFlag)){
			
			for(int i = 0;i < s.length; i++){
				CRMBusinessCheck.referCheck(s[i],object_id+"");//�޸�Ҫ���ܵı�־λ
			}
			
		}else{
			ConfigUtil.delInfo(configLocal,s,table_code,identityCode);
		}
		bSuccess = true;
	}
	
	CRMBusinessCheck.getReimbSum(String.valueOf(object_id));
}

String totalInfo=CRMBusinessCheck.getRelationsInfo("StaffReimbSum"+"@@"+object_id);


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
String sqlContent = ConfigUtil.getOtherQuerySql(interfaceType_code,replaceColValue);
String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));

sqlContent= sqlContent + " and ( exists (select 1 from TOPROLE where ROLE_ID ='102' and OP_CODE=" + input_operatorCode +" and INPUT_DEPT="+depart_id+")";
sqlContent= sqlContent + " or INPUT_MAN=" + input_operatorCode +" )";
sqlContent =sqlContent + " and isNull( APPLY_ID,'')=''";
IPageList pageList = configLocal.showPageDataOther(interfaceType_code,table_code,queryFieldColStr,queryValueStr,queryConditionValueStr,
										sqlContent,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = ConfigUtil.getSUrl(sUrl,queryFieldColStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);
sPage="page="+sPage+"&object_id="+object_id+"&edit_right="+edit_right+"&object_type="+object_type;
sUrl=sUrl+"&object_id="+object_id+"&edit_right="+edit_right+"&object_type="+object_type;
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
<BASE TARGET="_self">
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>

<script language=javascript>
<%if(bSuccess && "submitAll".equals(modiTypeFlag)){
	bSuccess = false;
%>
	alert("���ܳɹ�");
	window.returnValue=1;
	window.close();
<%}%>
//---------�༭---------
function showInfo(identity_code)
{
	var tableCode = document.theform.table_code.value;
	var interfaceTypeCode = document.theform.interfacetype_code.value;
	if(showModalDialog('<%=freeform_name%>?interfacetype_code='+interfaceTypeCode+'&edit_right=<%=edit_right%>&object_type=<%=object_type%>&object_id=<%=object_id%>&identity_code='+identity_code+'&table_code='+tableCode, '', 'dialogWidth:800px;dialogHeight:400px;status:0;help:0') != null)
	{
		sl_update_ok();
		document.theform.submit();
	}
}
//---------����------------
function referAll(AllId){
	var ids = document.getElementsByName(AllId);
	if(ids.length > 0){
		var num = 0;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				num ++;
			}
		}
		if(num <= 0){
			alert('��ѡ����Ҫ���ܵ���Ϣ��');
			return false;
		}
	}else{
		alert('û�пɻ�����Ϣ��');
		return false;
	}
	if(confirm("ȷ��Ҫ������Ϣ��")){
		disableAllBtn(true);
		document.getElementById('modiTypeFlag').value='submitAll';
		document.theform.submit();
	}
}
//----------����-----------
function newInfo()
{	
	var tableCode = document.theform.table_code.value;
	var interfaceTypeCode = document.theform.interfacetype_code.value;
	if(showModalDialog('<%=freeform_name%>?interfacetype_code='+interfaceTypeCode+'&object_type=<%=object_type%>&object_id=<%=object_id%>&edit_right=yes&identity_code=0&table_code='+tableCode, '', 'dialogWidth:800px;dialogHeight:500px;status:0;help:0') != null)
	{
		sl_update_ok();
		document.theform.submit();
				
	}
	
}
//--------------�ύ��������-----------------//
function referflow(identity_code)
{   
   var object_id=identity_code;
   //var checkFlag=showModalDialog('/fund/item/checkerror_info.jsp?check_flag=DiscInfoCheck&object_type=Disc&object_id=' + object_id, '', 'dialogWidth:560px;dialogHeight:450px;status:0;help:0');
   if("1"=="1")
   {
	   if(confirm("ȷ��Ҫ�ύ��"))
		{
			disableAllBtn(true);
			var returnValue=actionFlow("FlowUtil","initFlow",object_id+"@@"+"<%=object_type%>"+"@@"+"TentFlow010"+"@@"+"<%=input_operatorCode%>");
			if(returnValue=="success"){
				alert("�����������ɳɹ���")
				location.reload();
			}
		}
	}
}

</script>

<BODY class="BODY" >
<form name="theform" method="POST" action="<%=grid_name%>">
<input name="interfacetype_code" type="hidden" value="<%=interfaceType_code %>" />
<input name="table_code" type="hidden" value="<%=table_code%>" />
<input name="edit_right" type="hidden" value="<%=edit_right%>" />
<input name="object_type" type="hidden" value="<%=object_type%>" />
<input name="object_id" type="hidden" value="<%=object_id%>" />
<input name="modiTypeFlag" id="modiTypeFlag" type="hidden" value=""/>
<%
request.setAttribute("queryFieldColStr",queryFieldColStr);//��ѯ�ֶμ���
request.setAttribute("queryFieldNameStr",queryFieldNameStr);//��ѯ�ֶ����Ƽ�;
request.setAttribute("queryConditionTypeStr",queryConditionTypeStr);//��ѯ�ֶβ�ѯ���ͼ�
request.setAttribute("queryConditionValueStr",queryConditionValueStr);//��ѯ�ֶ�ѡ��Ĳ�ѯ����
request.setAttribute("queryInputTypeStr",queryInputTypeStr);//Ԫ�ؿ����ͼ�
request.setAttribute("queryValueTypeStr",queryValueTypeStr);//����������Դ���ͼ�
request.setAttribute("queryValueContentStr",queryValueContentStr);//���ݲ�ѯ���
request.setAttribute("queryValueStr",queryValueStr);//��ѯ�ֶ�ҳ��ֵ
request.setAttribute("queryInputTypeStr",queryInputTypeStr);//Ԫ�ؿ�����ֵ
 %>
<jsp:include page="/system/config/basiclist_query_m.jsp" flush="true"></jsp:include>

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
						<td class="page-title"><b>Ա��������ϸ</b></td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
							<button class="xpbutton3" accessKey=f id="queryButton" name="queryButton">��ѯ(<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<button class="xpbutton3" accessKey=m id="btnAll" name="btnAll" title="����" onclick="javascript:referAll('<%=delStr%>');">��ӻ���(<u>M</u>)</button>
							&nbsp;&nbsp;&nbsp;
							 </div>
							 <br/>
						</td>
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