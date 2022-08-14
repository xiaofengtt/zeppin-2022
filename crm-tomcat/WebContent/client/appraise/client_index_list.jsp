<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer grade_id =Utility.parseInt(request.getParameter("grade_id"), new Integer(0));

GradeIndexLocal gi_local = EJBFactory.getGradIndex();
GradeIndexVO gi_vo = new GradeIndexVO();

gi_vo.setGrade_id(grade_id);

IPageList pageList = gi_local.queryGradeIndex(gi_vo, Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();
Map map = new HashMap();
String tempUrl = "";

tempUrl = "&grade_id=" + grade_id;
sUrl = sUrl + tempUrl;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
//�༭���ֱ�׼����
function showInfo(grade_id,index_id)
{   
	if(showModalDialog('client_index_update.jsp?newflag=0&grade_id=' + grade_id+'&index_id='+index_id, '', 'dialogWidth:420px;dialogHeight:635px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}
//������ֱ�׼����
function newInfo()
{
	if(showModalDialog('client_index_update.jsp?newflag=1', '', 'dialogWidth:440px;dialogHeight:630px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}
//��ѯ���ֱ�׼����
function refreshPage()
{	
	disableAllBtn(true);
	waitting.style.visibility='visible';
	location = 'client_index_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&grade_id=' + document.theform.grade_id.value;
}

function StartQuery()
{
	refreshPage();
}
//�������
window.onload = function(){
initQueryCondition()
};

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="client_index_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
<div id="queryCondition" class="qcMain" style="display:none;width:250px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">		
	<tr>
		<td align="right" noWrap><%=LocalUtilis.language("class.crmGrade",clientLocale)%> :</td><!--������ϵ-->
		<td><select size="1" name="grade_id"
					onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
					<%=Argument.getCrmGradeIDOptions(grade_id)%>
		</select></td>
	</tr>
	<tr>
		<td align="center" colspan="4"><button type="button"  class="xpbutton3" accessKey=s name="btnQuery"
									onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;<!--ȷ��-->
			</td>
		</tr>						
	</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>

	<TR>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=left border=0>
		<TR>
			<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" class="page-title">						
						<font color="#215dc6"><b><%=menu_info%></b></font>
					</td>
				</tr>
				<tr>
					<td align="right">
					<div class="btn-wrapper">
					<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
						<%if (input_operator.hasFunc(menu_id, 100)) {%>
                    <!-- �½���¼ --><!-- �½� -->
					<button type="button"  class="xpbutton3" accessKey=n  id="btnNew" name="btnNew" title='<%=LocalUtilis.language("message.newRecord",clientLocale)%>' onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
					&nbsp;&nbsp;&nbsp; <%}%>  <%if (input_operator.hasFunc(menu_id, 101)) {%>		
                    <!-- ɾ����ѡ��¼ --><!-- ɾ�� -->
					<button type="button"  class="xpbutton3" accessKey=d  id="btnCancel" name="btnCancel" title='<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%>' onclick="javascript:if(confirmRemove(document.theform.index_id)) {  document.theform.submit();}"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
					<%}%>
					</div>
					</td>
				</tr>
			</table>
			<br/>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trh">
					<!--���-->
                    <td><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.index_id);"> <%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td>
					<td><%=LocalUtilis.language("class.typeName",clientLocale)%> </td><!--�������-->
					<td><%=LocalUtilis.language("class.indexName",clientLocale)%> </td><!-- ϸ������ -->
					<td><%=LocalUtilis.language("class.flagName1",clientLocale)%> </td><!--ֵ��Դ-->		
					<td><%=LocalUtilis.language("class.flagName2",clientLocale)%> </td><!--��Ч��־-->
					<td><%=LocalUtilis.language("class.stValue",clientLocale)%> </td>	<!--��׼ֵ-->
					<td><%=LocalUtilis.language("class.dfgs",clientLocale)%> </td><!--�÷ֹ�ʽ-->
					<td><%=LocalUtilis.language("class.inputMan2",clientLocale)%> </td><!--������Ա-->
					<td><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
				</tr>
				<%
				int iCount = 0;
				int iCurrent = 0;
				while (it.hasNext())
				{  
					map = (Map)it.next();
					Integer value_flag = new Integer(map.get("VALUE_FLAG").toString());
					String valid_flag = map.get("VALID_FLAG").toString();
					String flag_name1="";
					String flag_name2=enfo.crm.tools.LocalUtilis.language("message.invalid",clientLocale);//��Ч
					if(value_flag.equals(new Integer(1))){
						flag_name1=enfo.crm.tools.LocalUtilis.language("message.manual Entry",clientLocale);//�ֹ�¼��
					}
					if(value_flag.equals(new Integer(2))){
						flag_name1=enfo.crm.tools.LocalUtilis.language("message.calculate",clientLocale);//����
					}
					if(value_flag.equals(new Integer(3))){
						flag_name1=enfo.crm.tools.LocalUtilis.language("message.boolean",clientLocale);//������
					}
					if(valid_flag == "true"){
						flag_name2=enfo.crm.tools.LocalUtilis.language("message.valid",clientLocale);//��Ч
					}
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td width="10%"> 
					<table border="0" width="20%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%" ><input type="checkbox" name="index_id" value="<%=map.get("INDEX_ID")+"#"+map.get("GRADE_ID")%>" class="flatcheckbox"></td>
							<td width="90%" align="left"><%=map.get("XH")%></td>							
						</tr>
					</table>
					</td>
					<td align="left"><%=Utility.trimNull(map.get("INDEX_TYPE_NAME"))%></td>
					<td align="left"><%=Utility.trimNull(map.get("INDEX_NAME"))%></td>
					<td align="left"><%=Utility.trimNull(flag_name1)%></td>
					<td align="left"><%=Utility.trimNull(flag_name2)%></td>		
					<td align="left"><%=Format.formatMoney0(new BigDecimal(String.valueOf(map.get("ST_VALUE"))))%></td>		
					<td align="left"><%=Utility.trimNull(map.get("DF_GS"))%></td>	
					<td align="left"><%=Argument.getOpName(new Integer(String.valueOf(map.get("INPUT_MAN"))))%></td>	
					<td align="center">
					<a href="#" onclick="javascript:showInfo(<%=map.get("GRADE_ID")%>,<%=map.get("INDEX_ID")%>);">
						<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" alt='<%=LocalUtilis.language("message.edit",clientLocale)%> '/><!--�༭-->
					</a>
					</td>					
				</tr>
				<%
				iCurrent++;
				iCount++;
				}
				for (; iCurrent < pageList.getPageSize(); iCurrent++)
				{
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
				</tr>
				<%}
				%>
				<tr class="trbottom">
					<!--�ϼ�--><!--��-->
                    <td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
				</tr>
			</table>

			<br>

			<table border="0" width="100%" class="page-link">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>					
				</tr>
			</table>

			</TD>
		</TR>
	</TABLE>
	</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%gi_local.remove();%>
