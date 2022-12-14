<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得页面传递变量
Integer subject_id = Utility.parseInt(Utility.trimNull(request.getParameter("subject_id")),new Integer(0));
String operand_no = Utility.trimNull(request.getParameter("operand_no"));
String operand_name = Utility.trimNull(request.getParameter("operand_name"));
Integer scoring = Utility.parseInt(request.getParameter("scoring"),new Integer(0));
Integer source = Utility.parseInt(request.getParameter("source"),new Integer(0));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));

//页面辅助参数
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
List custlist = null;
Map map = null;
Map custmap =null;

//获得对象及结果集
RatingVO vo = new RatingVO();
ScoreOperandLocal score_operand = EJBFactory.getScoreOperand();

vo.setOperand_id(new Integer(0));
vo.setSubject_id(subject_id);
vo.setOperand_no(operand_no);
vo.setOperand_name(operand_name);
vo.setScoring(scoring);
vo.setSource(source);
vo.setInput_man(input_man);
IPageList pageList = score_operand.pageList_tscoreoperand(vo, totalColumn, t_sPage, t_sPagesize);

sUrl = sUrl+ "&subject_id =" +subject_id+ "&operand_no =" +operand_no
		+ "&operand_name =" +operand_name+ "&scoring =" +scoring+ "&source =" +source;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<!--计分操作数设置-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
function showInfo(operand_id)
{
	var url = 'score_operand_action.jsp?operand_id=' + operand_id;
	var ret = showModalDialog(url,'', 'dialogWidth:700px;dialogHeight:600px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}

function newInfo()
{	
	var url = "score_operand_action.jsp";
	var ret = showModalDialog(url,'', 'dialogWidth:700px;dialogHeight:600px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}

function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	disableAllBtn(true);
	
	var scoring;
	var source;
	var radio1=document.getElementsByName("scoring");
	for(var i=0;i<radio1.length;i++)
	{
		if(radio1[i].checked==true)
		{
			scoring = radio1[i].value;
		}
	}

	var radio2=document.getElementsByName("source");
	for(var i=0;i<radio2.length;i++)
	{
		if(radio2[i].checked==true)
		{
			source = radio2[i].value;
		}
	}

	location = 'score_operand.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
							+ '&subject_id=' + document.theform.subject_id.value
							+ '&operand_no=' + document.theform.operand_no.value
							+ '&operand_name=' + document.theform.operand_name.value
							+ '&scoring=' + scoring
							+ '&source=' + source;
}
/*删除功能*/
function removeInfo()
{
	if(checkedCount(document.getElementsByName("operand_id")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	if(sl_check_remove()){			
		var url = 'score_operand_remove.jsp';
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	return false;
}


window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="score_operand.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   					<td align="right">
   						<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- 要加入的查询内容 -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
					<td align="right"><%=LocalUtilis.language("class.subjectsName",clientLocale)%>:</td><!--科目名称-->
					<td align="left">
						<select name="subject_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getScoreSubjectOptions(subject_id)%>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.operandNumber",clientLocale)%>:</td><!--操作数编号-->
					<td align="left">
						<input type="text" name="operand_no" value="<%=operand_no %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
					<td align="right"><%=LocalUtilis.language("class.operandName",clientLocale)%> : </td><!--操作数名称-->
					<td align="left" >
						<input type="text" name="operand_name" value='<%=operand_name%>' onkeydown="javascript:nextKeyPress(this)" size="20">
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.scoringPeople",clientLocale)%> :</td><!--打分人-->
					<td align="left">
						<input type = "radio" value = "1"  name = "scoring" 
						<%if(scoring.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("class.aritificial",clientLocale)%>人工<input type = "radio" value = "2"  name = "scoring" 
						<%if(scoring.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("class.system",clientLocale)%>
					</td>
					<td align="right"><%=LocalUtilis.language("class.atrificialOpposesSource",clientLocale)%>: </td><!--人工打分来源-->
					<td align="left" >
						<!-- 手工打分 --><input type = "radio" value = "1"  name = "source" 
						<%if(source.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("class.manualScoring",clientLocale)%><input type = "radio" value = "2"  name = "source" 
						<%if(source.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("class.calculationScoring",clientLocale)%><!-- 通过计算打分 -->
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--确认-->
				</tr>			
			</table>
		</div>


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
						<%if(input_operator.hasFunc(menu_id, 100)){%> 		
							<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--新建记录-->
							&nbsp;&nbsp;&nbsp;<!--新建-->
						<%}if(input_operator.hasFunc(menu_id, 101)){%> 
							<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:removeInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
							<!--删除所选记录--><!--删除-->
						<%}%>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td>
						<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.operand_id,this);">
						<%=LocalUtilis.language("class.subjectsName",clientLocale)%>
						</td><!--科目名称-->
						<td align="center"><%=LocalUtilis.language("class.operandNumber",clientLocale)%></td><!--操作数编号-->
						<td align="center"><%=LocalUtilis.language("class.operandName",clientLocale)%></td><!--操作数名称-->
						<td align="center"><%=LocalUtilis.language("class.scoringPeople",clientLocale)%></td><!--打分人-->
						<td align="center"><%=LocalUtilis.language("class.atrificialOpposesSource",clientLocale)%></td><!--人工打分来源-->
						<td align="center"><%=LocalUtilis.language("class.memberDescription",clientLocale)%></td><!--描述-->
						<td align="center" width="30px"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
						<td align="center" width="30px"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
					</tr>
<%
//声明参数
Integer operand_id = new Integer(0);
String scoringStr = "";
String sourceStr = "";

list = pageList.getRsList();

for(int i=0;i<list.size(); i++)
{
	//读数据
    map = (Map)list.get(i);
	if(map!=null){
	operand_id = Utility.parseInt(Utility.trimNull(map.get("OPERAND_ID")),new Integer(0));
	subject_id = Utility.parseInt(Utility.trimNull(map.get("SUBJECT_ID")),new Integer(0));

	if(Utility.parseInt(Utility.trimNull(map.get("SCORING")),new Integer(0)).intValue()==2)
		scoringStr = LocalUtilis.language("class.system",clientLocale);
	else if(Utility.parseInt(Utility.trimNull(map.get("SCORING")),new Integer(0)).intValue()==1)
		scoringStr = LocalUtilis.language("class.aritificial",clientLocale);
	else
		scoringStr = "";

	if(Utility.parseInt(Utility.trimNull(map.get("SOURCE")),new Integer(0)).intValue()==2)
		sourceStr = LocalUtilis.language("class.calculationScoring",clientLocale);
	else if(Utility.parseInt(Utility.trimNull(map.get("SOURCE")),new Integer(0)).intValue()==1)
		sourceStr = LocalUtilis.language("class.manualScoring",clientLocale);
	else
		sourceStr = "";
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="left">
						  <input type="checkbox" name="operand_id" value="<%=operand_id%>" class="flatcheckbox">
						  &nbsp;&nbsp;<%=Utility.trimNull(map.get("SUBJECT_NAME"))%>
						</td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("OPERAND_NO")) %></td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("OPERAND_NAME")) %></td>
						<td align="center"><%=scoringStr%></td>
						<td align="center"><%=sourceStr %></td>
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("SUMMARY")) %></td>
						<td align="center"> 
						<%if(input_operator.hasFunc(menu_id, 102)){%>
							<a href="#" onclick="javascript:showInfo(<%=operand_id%>)">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
							</a>
						<%}%>	
						</td>
						<td align="center">
							<%if(input_operator.hasFunc(menu_id, 101)){%>
							<a href="#" onclick="javascript:removeInfo()">
								<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
							</a>
							<%}%>
						</td>
					</tr>
<%
	iCurrent++;
	iCount++;
	}
}

for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(i % 2)%>">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
<%@ include file="/includes/foot.inc" %>	
</BODY>
</HTML>
<%
score_operand.remove();
%>

