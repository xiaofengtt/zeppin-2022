<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.project.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//��ʼ��������Ϣ
String interfaceType_code = "FlowTask010";
String action_flag = Utility.trimNull(request.getParameter("action_flag"));
ConfigLocal configLocal = EJBFactory.getConfig();
//ConfigLocal configLocal = EJBFactory.getConfigCatalog();
BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
boolean bSuccess = false;

//ͨ��ҳ�������Ҹñ��Ӧ�������ֶκͱ���
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
List catalogList =ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = (String)catalogList.get(0);
String grid_name =(String)catalogList.get(1);
String freeform_name =(String)catalogList.get(2);
String delStr=identityCode+"DEL";//�б���checkbox��nameֵ
String object_no="",object_type="",flow_no="",node_no="";

if(request.getMethod().equals("POST")){//ɾ����Ϣ
	object_no= Utility.trimNull(request.getParameter("object_no"));
	object_type= Utility.trimNull(request.getParameter("object_type"));
	flow_no= Utility.trimNull(request.getParameter("flow_no"));
	node_no= Utility.trimNull(request.getParameter("node_no"));	
	if(object_no != null && action_flag.equals("delete")){ 
		FlowUtil.deleteFlow(object_no,object_type,flow_no,node_no);
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
String sqlContent = ConfigUtil.getOtherQuerySql(interfaceType_code,replaceColValue);
//��ͬ����жϲ�ͬ�Ĳ�ѯ����
if(UNQUERY.intValue()==1){
	sqlContent =sqlContent + " and user_id='"+input_operatorCode+"'";
}else if(UNQUERY.intValue()==0){
	sqlContent =sqlContent + " and user_id='"+input_operatorCode+"'" + work_condition;
}
IPageList pageList = configLocal.showPageDataOther(interfaceType_code,table_code,queryFieldColStr,queryValueStr,queryConditionValueStr,
										sqlContent,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = ConfigUtil.getSUrl(sUrl,queryFieldColStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);
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

//---------�༭---------
function showInfo(identity_code,object_no,object_type,flow_no,node_no)
{
	var edit_right="yes";
	if(object_type=="BusinessContract")	edit_right="spec";
	var returnValue=showModalDialog('/project/flow/flowobject_tab.jsp?task_id='+identity_code+'&object_type='+object_type+'&flow_no='+flow_no+'&node_no='+node_no+'&show_flag=FlowTask&edit_right='+edit_right+'&object_id='+object_no, '', 'dialogWidth:1000px;dialogHeight:760px;status:0;help:0');
	if(returnValue=="success") location.reload();
}
//----------����-----------
function newInfo()
{	
	var tableCode = document.theform.table_code.value;
	var interfaceTypeCode = document.theform.interfacetype_code.value;
	if(showModalDialog('/project/flow/flowtask_new.jsp?identity_code=0&table_code='+tableCode, '', 'dialogWidth:1000px;dialogHeight:500px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}

}

//---------ɾ��---------
function deleteInfo(identity_code,object_no,object_type,flow_no,node_no)
{
	if(node_no=="010" || node_no=="300"){
		if(confirm("ȷ��Ҫɾ����")){
			disableAllBtn(true);
			document.theform.identityCode.value=identity_code;
			document.theform.object_no.value=object_no;
			document.theform.object_type.value=object_type;
			document.theform.flow_no.value=flow_no;
			document.theform.node_no.value=node_no;			
			document.theform.action_flag.value="delete";
			document.theform.submit();
		}
	}else{
		sl_alert("��ǰ����������ɾ�����벹�����������ύ��");
		return;
	}
}

//--------------�ύ��������-----------------//
function referflow(identity_code,object_no,object_type,flow_no,node_no,node_opinion,drive_type,next_node)
{   
	//��ʼ������
	if(typeof(object_no)=="undefined" || object_no=="") object_no=identity_code;
	if(typeof(object_type)=="undefined" || object_type=="") object_type="autoFlow";
	if(typeof(node_opinion)=="undefined" || node_opinion==""){
		sl_alert("��ѡ��ǰ�׶α�������Ȼ�����ύ��");
		return;
	}
	if(flow_no=="WorkLogFlow" ){
		
	//��ʼ������
		var checkFlag=showModalDialog('/project/flow/checkerror_info.jsp?check_flag=DiscInfoCheck&object_type=WorkLogFlow&object_id=' + object_no, '', 'dialogWidth:560px;dialogHeight:450px;status:0;help:0');
   		if(checkFlag=="1"){
	 		if(confirm("ȷ��Ҫ�ύ��"))
			{
			disableAllBtn(true);
			//�ֹ��ڵ�ѡ����һ�ڵ������Ա
			var selectUser="";
			if(drive_type=="hand"){
				var url="/project/flow/operateobject_query.jsp";
				var param='query_type=NodeUser&flow_no='+flow_no+'&node_no='+next_node;
				selectUser=showModalDialog(url+'?'+param, '', 'dialogWidth:400px;dialogHeight:450px;status:0;help:0');
				if(typeof(selectUser)=='undefined' || selectUser==''){
					sl_alert("��ѡ����һ�ڵ������Ա��");
					return;
				}
			}else{
				selectUser="0";
			}
			var returnValue=actionFlow("FlowUtil","actionFlow",identity_code+'@@'+object_no+'@@'+object_type+'@@'+flow_no+'@@'+node_no+'@@'+node_opinion+'@@'+selectUser);
			if(returnValue=="success"){
				alert("�����ύ�ɹ���")
				location.reload();
			}
		}
	}
	}
	else{
	if(confirm("ȷ��Ҫ�ύ��"))
	{
		disableAllBtn(true);
		//�ֹ��ڵ�ѡ����һ�ڵ������Ա
		var selectUser="";
		if(drive_type=="hand"){
			var url="/project/flow/operateobject_query.jsp";
			var param='query_type=NodeUser&flow_no='+flow_no+'&node_no='+next_node;
			selectUser=showModalDialog(url+'?'+param, '', 'dialogWidth:400px;dialogHeight:450px;status:0;help:0');
			if(typeof(selectUser)=='undefined' || selectUser==''){
				sl_alert("��ѡ����һ�ڵ������Ա��");
				return;
			}
		}else{
			selectUser="0";
		}
		var returnValue=actionFlow("FlowUtil","actionFlow",identity_code+'@@'+object_no+'@@'+object_type+'@@'+flow_no+'@@'+node_no+'@@'+node_opinion+'@@'+selectUser);
		if(returnValue=="success"){
			alert("�����ύ�ɹ���")
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
<input name="action_flag" type="hidden" value="<%=action_flag%>" />
<input name="identityCode" type="hidden" value="<%=identityCode%>" />
<input name="object_no" type="hidden" value="<%=object_no%>" />
<input name="object_type" type="hidden" value="<%=object_type%>" />
<input name="flow_no" type="hidden" value="<%=flow_no%>" />
<input name="node_no" type="hidden" value="<%=node_no%>" />

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
						<td class="page-title" ><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
							<button class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="�½�����" onclick="javascript:newInfo();">�½�����(<u>N</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<button class="xpbutton3" accessKey=f id="queryButton" name="queryButton">��ѯ(<u>F</u>)</button>
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