<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer rating_id = Utility.parseInt(Utility.trimNull(request.getParameter("rating_id")),new Integer(0));
Integer subject_id = Utility.parseInt(Utility.trimNull(request.getParameter("subject_id")),new Integer(0));
String rating_no = Utility.trimNull(request.getParameter("rating_no"));
String rating_name = Utility.trimNull(request.getParameter("rating_name"));
Integer include_top = Utility.parseInt(request.getParameter("include_top"),new Integer(0));
Integer include_end = Utility.parseInt(request.getParameter("include_end"),new Integer(0));
String score_lower = Utility.trimNull(request.getParameter("score_lower"));
String score_upper = Utility.trimNull(request.getParameter("score_upper"));
String summary = Utility.trimNull(request.getParameter("summary"));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
String subject_name = "";

Map map = null;
List rsList = null;

//获得对象及结果集
RatingVO vo = new RatingVO();
SubjectScoreRatingLocal subject_score_rating = EJBFactory.getSubjectScoreRating();

boolean bSuccess = false;

//保存信息
if(request.getMethod().equals("POST")){

	vo.setSubject_id(subject_id);
	vo.setRating_no(rating_no);
	vo.setRating_name(rating_name);
	vo.setInclude_top(include_top);
	vo.setInclude_end(include_end);
	vo.setScore_lower(Utility.parseInt(score_lower,new Integer(0)));
	vo.setScore_upper(Utility.parseInt(score_upper,null));
	vo.setSummary(summary);
	vo.setInput_man(input_man);	

	if(rating_id.intValue()!= 0){
		vo.setRating_id(rating_id);
		subject_score_rating.modi_subjectScoreRating(vo);
	}else{
		subject_score_rating.append_subjectScoreRating(vo);
	}
	bSuccess = true;
}
else {
	//信息显示
	if(rating_id.intValue()!= 0){
		vo.setRating_id(rating_id);
		vo.setInput_man(input_man);
		List list = subject_score_rating.list_subjectScoreRating(vo);
		map = (Map)list.get(0); 
	}
	
	if(map != null){
		rating_id = Utility.parseInt(Utility.trimNull(map.get("RATING_ID")),new Integer(0));
		rating_no = Utility.trimNull(map.get("RATING_NO"));
		rating_name = Utility.trimNull(map.get("RATING_NAME"));
		subject_id = Utility.parseInt(Utility.trimNull(map.get("SUBJECT_ID")),new Integer(0));
		subject_name = Utility.trimNull(map.get("SUBJECT_NAME"));
		include_top = Utility.parseInt(Utility.trimNull(map.get("INCLUDE_TOP")),new Integer(0));
		include_end = Utility.parseInt(Utility.trimNull(map.get("INCLUDE_END")),new Integer(0));
		score_lower = Utility.trimNull(map.get("SCORE_LOWER"));
		score_upper = Utility.trimNull(map.get("SCORE_UPPER"));
		summary = Utility.trimNull(map.get("SUMMARY"));
	}
}
%>

<HTML>
<HEAD>
<TITLE></TITLE><!--新增评分科目-->
<base target="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<script language="javascript">	
/*保存*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

function validateForm(form){
	if(form.subject_id.value == 0){
			sl_alert("<%=LocalUtilis.language("message.select2",clientLocale)%><%=LocalUtilis.language("class.subjectNumber",clientLocale)%>！");//请选择科目编号
			return false;
	}
	if(form.rating_no.value == ""){
			sl_alert("<%=LocalUtilis.language("class.pleaseFillIn",clientLocale)%><%=LocalUtilis.language("class.ratingNumbers",clientLocale)%>！");//请填写评级编号
			return false;
	}
	if(form.rating_name.value == ""){
			sl_alert("<%=LocalUtilis.language("class.pleaseFillIn",clientLocale)%><%=LocalUtilis.language("class.ratingName",clientLocale)%>！");//请填写评级名称
			return false;
	}	
	if((form.score_lower.value!="")&&(!sl_checkNum(form.score_lower,'<%=LocalUtilis.language("class.scoreLimit",clientLocale)%>', 30, 1)))	return false;	//分值下限
	if((form.score_upper.value!="")&&(!sl_checkNum(form.score_upper,'<%=LocalUtilis.language("class.scoreUpper",clientLocale)%>', 30, 1)))	return false;	//分值上限

	return sl_check_update();
}

/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.close();
	}
}

/*取消*/
function CancelAction(){
	window.close();
}
</script>
</HEAD>

<BODY class="BODY" style="overflow-x: hidden">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="subject_score_rating_action.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="rating_id" value="<%=rating_id%>">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<div align="left"  style="margin-left:20px">
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="margin-top:5px;">
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.subjectsName",clientLocale)%>: </td><!--科目名称-->
		<td align="left" colspan="3">
			<select name="subject_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
			<%=Argument.getScoreSubjectOptions(subject_id)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.ratingNumbers",clientLocale)%>: </td><!--评级编号-->
		<td align="left" >
		<input type="text" name="rating_no" size="30" value="<%=rating_no %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
		<td align="right">*<%=LocalUtilis.language("class.ratingName",clientLocale)%>:</td><!--评级名称-->
		<td align="left" >
			<input type="text" name="rating_name" size="30" value="<%=rating_name %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.scoreLimit",clientLocale)%>: </td><!--分值下限-->
		<td align="left" >
		<input type="text" name="score_lower" size="30" value="<%=score_lower %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
		<td align="right"><%=LocalUtilis.language("class.scoreUpper",clientLocale)%>:</td><!--分值上限-->
		<td align="left" >
			<input type="text" name="score_upper" size="30" value="<%=score_upper %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.includeLimit",clientLocale)%>:</td><!--是否包含分值下限-->
		<td align="left">
			<input type = "radio" value = "1"  name = "include_top" 
			<%if(include_top.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("message.yes",clientLocale)%><input type = "radio" value = "2"  name = "include_top" 
			<%if(include_top.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("message.no3",clientLocale)%>
		</td>
		<td align="right"><%=LocalUtilis.language("class.includeUpper",clientLocale)%>: </td><!--是否包含分值上限-->
		<td align="left" >
			<input type = "radio" value = "1"  name = "include_end" 
			<%if(include_end.intValue() == 1) {out.print("checked");}%> /><%=LocalUtilis.language("message.yes",clientLocale)%><input type = "radio" value = "2"  name = "include_end" 
			<%if(include_end.intValue() == 2) {out.print("checked");}%> /><%=LocalUtilis.language("message.no3",clientLocale)%>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.memberDescription",clientLocale)%>: </td><!--描述-->
		<td align="left" colspan="3"><textarea  rows="3" name="summary" cols="80"><%=summary %></textarea></td>
	</tr>
</table>
</div>
<div align="right" style="margin-right:20px">
	<br>
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%>(<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
subject_score_rating.remove();
%>
