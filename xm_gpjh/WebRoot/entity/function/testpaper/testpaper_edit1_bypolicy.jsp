<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
		<SCRIPT>
function cfmdel(link)
{
	if(confirm("您确定要删除这道题目吗？"))
		window.navigate(link);
}

function DetailInfo(type,id,id1)
{
	if(type=='YUEDU')
		window.open('paper_question_comprehension_info1.jsp?id='+id +'&id1='+id1,'','dialogWidth=350px;dialogHeight=330px');
	else 
		window.open('paper_question_info.jsp?id='+id,'','dialogWidth=350px;dialogHeight=330px');
	
}
function addSubmit()
{
	document.add.action="/entity/studyZone/courseResources_batchUpdatePaperQuestion.action";
	document.add.submit();
}
function deleteSubmit()
{
	var flag = false;
	var form = document.forms['add'];
	
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox')) {
			if ( form.elements[i].checked == true) {
				flag = true;
				break;
			}
		}
	}
	if(flag == false){
		alert('请选择您要删除的题目。');
		return;
	}
	
	document.add.action="/entity/studyZone/courseResources_batchDeletePaperQuestion.action";
	document.add.submit();
}
function doCommit()
{
  	addSubmit();
}
function listSelect(listName) {
	var form = document.forms['add'];
	var check = document.all(listName);
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == listName+"Child")) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = check.checked;
			}
		}
	}
	return true;
}
</SCRIPT>
<s:if test="msg != null and msg != ''">
	<script>
		alert("<s:property value='msg' escape='false'/>");
	</script>
</s:if>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<table width="489" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td height="42" background="/entity/function/images/st_05.gif"
					style="padding-left:53px;padding-top:8px" class="text3">
					题型选择添加
				</td>
			</tr>
			<form name='add' method='post' class='nomargin'>
				<input type="hidden" name="paper_id" value=<s:property value='paper_id'/> >
				<input type="hidden" name="test_id" value=<s:property value='test_id'/> >
				<input type="hidden" name="course_id" value=<s:property value='course_id'/> >
			<tr>
				<td valign="top" background="/entity/function/images/st_06.gif">
					<table height="390" width="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td valign="top" class="bg2">
								<table width="100%" border="0" cellspacing="0" cellpadding="6">
									<tr>
										<td valign="top" style="padding-left:30px">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
											<s:iterator value='paperquestionList' id='list'>	
												<tr>
													<td align="center">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr valign="bottom" class="mc1">
																<td width="10%" valign="middle">
																	<input type='checkbox' class='checkbox'
																		name='<s:property value='#list[0]'/>' id='<s:property value='#list[0]'/>' value='true'
																		class='checkbox' onClick="listSelect(name)">
																</td>
																<td colspan="2" width="45%">
																	<s:property value='#list[1]'/>
																	：
																</td>
																<td colspan="2" width="45%">
																	<a href="/entity/studyZone/courseResources_paperQuetionItemToAdd.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&type=<s:property value='#list[0]'/>&paper_id=<s:property value='paper_id'/>"
																		><span unselectable="on">添加试题</span>
																	</a>
																</td>
															</tr>
															<s:iterator value='#list[2]' id='question' status='stas'>
															<tr valign="bottom" class="mc1">
																<td width="15%" valign="middle" align="right">
																	<input type='checkbox' class='checkbox'
																		name="<s:property value='%{#list[0] + "Child"}'/>" value="<s:property value='#question.id'/>">
																</td>
																<td width="5%" align="right" valign="bottom"
																	style="padding-right:8px">
																	<s:property value='#question.serial'/>
																	.
																</td>
																<td width="45%" valign="bottom">
																	<a href="<s:property value='#list[3]'/>?question_id=<s:property value='#question.id'/>" class="button"
																		target="_blank"><span unselectable="on"><s:if test="%{#question.title.length() > 14}"><s:property value="#question.title.substring(0,14)+'...'"/></s:if><s:else><s:property value='#question.title'/></s:else></span>
																	</a>
																</td>
																<td width="20%" valign="bottom">
																	分值：
																	<input type="text" name="<s:property value='%{#question.id + "score"}'/>" size="2" style="text-align:center" value=<s:if test="#question.score != null or #question.score != ''"><s:property value='#question.score'/></s:if><s:else><s:property value='#question.referencescore'/></s:else> > </td>
																
																<td valign="bottom" width="15%">
																	<!--
				                        <a href="javascript:cfmdel('testpaper_del1_bypolicy.jsp?field=<%--<%=field%>&i=<%=i%>&paperId=<%=paperId%>--%>')" title="提交" class="button"><span unselectable="on">删	除</span></a></span></td>
				                        -->						</td>
															</tr>
														</s:iterator>
														</table>
													</td>
												</tr>
											</s:iterator>
											</table>
									</tr>
									<tr>
										<td>
											<table width="98%" height="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td height="4">
														<img src="/entity/function/images/page_bottomSlip.gif" width="100%"
															height="2">
													</td>
												</tr>
												<tr>
													<td align="center" valign="middle">
														<img src="/entity/function/testpaper/images/Confirm.gif" style="cursor: pointer;" width="80" height="24"
															onclick="doCommit()">
														&nbsp;&nbsp;&nbsp;
														<img src="/entity/function/testpaper/images/Delete.gif" style="cursor: pointer;" width="80" height="24"
															onclick="deleteSubmit()">
														&nbsp;&nbsp;&nbsp;
														<img src="/entity/function/images/Return.gif" style="cursor: pointer;" width="80" height="24"
															onclick="window.location.href='/entity/studyZone/courseResources_enterTestpaper.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>'">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<img src="/entity/function/images/st_07.gif" width="489" height="15">
				</td>
			</tr>
			</form>
		</table>
	</body>
</html>
