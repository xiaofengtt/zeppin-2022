<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>tableType_1</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	function listSelect(listId) {
	var form = document.forms[listId + '_form'];
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'idaaa')) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = form.listSelectAll.checked;
			}
		}
	}
	return true;
}
function cfmdel(link)
{
	if(confirm("您确定要删除本组卷策略吗？"))
		window.navigate(link);
}

function DetailInfo(type,id)
{
	if(type=='YUEDU')
		window.open('store_question_comprehension_info.jsp?id='+id,'','dialogWidth=350px;dialogHeight=330px');
	else 
		window.open('store_question_info.jsp?id='+id,'','dialogWidth=350px;dialogHeight=330px');
	
}
function checkSubmit() {
	var id = document.getElementsByName("idaaa");
	for(var i = 0; i < id.length; i++) {
		if(id[i].type=="checkbox" && id[i].checked) {
			document.del_form.submit();
			return;
		}
	}
	
	alert("请选择您要添加的题目！");
	return false;
	
}
</script>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<form action="/entity/studyZone/courseResources_addQuestionToPaper.action" method="post" name="del_form">
<input type="hidden" name="type" value=<s:property value='type'/> >
<input type="hidden" name="test_id" value=<s:property value='test_id'/> >
<input type="hidden" name="paper_id" value=<s:property value='paper_id'/> >
<input type="hidden" name="course_id" value=<s:property value='course_id'/> >
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="/entity/function/images/top_01.gif">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"></td>
                      <td height="46">&nbsp;</td>  
                    </tr>
                    <tr> 
                      <td align="left" valign="top"> 
                        <table width="70%" border="0" cellpadding="0" cellspacing="0">
                          <tr>                            
                            <td align="left"><img src="/entity/function/testpaper/images/xb.gif" width="48" height="32"></td>
                            <td align="center" class="mc1"></td>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td align="left" class="mc1"><img src="/entity/function/images/Return.gif" style="cursor: pointer;" width="80" height="24" onclick="javascript:history.go(-1)">&nbsp;<a onclick='checkSubmit();'><IMG style="cursor: pointer;" src="/entity/function/testpaper/images/OK.gif"/></a></td>
                          </tr>
                        </table>
                      </td>
                      <td height="46">&nbsp;</td>
                    </tr>
                </table>
                </td>
              </tr>
              <tr>
                <td align="center">
			<table width="812" border="0" cellspacing="0" cellpadding="0">
              <tr>
                      
                <td height="26" background="/entity/function/images/tabletop.gif">
	              <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="24%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="10%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布时间</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="21%" align="center" class="title">所属知识点</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="15%" align="center" class="title">题目类型</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="5%" align="letf" class="title"><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('del')"></td>
                    </tr>                    
                  </table>
                </td>
                </tr>
				<s:iterator value='questionItemList' id='item' status='stus'>
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr> 
                            <td width="5%" align="center" valign="middle" class="td1"><img src="/entity/function/images/xb2.gif" width="8" height="8"></td>
                            <td width="24%"  class="td1"><a href="<s:property value='url'/>?question_id=<s:property value='#item.id'/>&type=<s:property value='type'/>" target=_blank><s:property value='#item.title'/></a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="/entity/function/testpaper/images/new.gif"></td>
                            <td width="12%" align="center"  class="td1"><s:property value='#item.creatuser'/></td>
                            <td width="16%" align="center"  class="td1"><s:date name='#item.creatdate' format='yyyy-MM-dd'/></td>
                            <td width="21%" align="center"  class="td1"><s:property value='loreNameList[#stus.index]'/></td>
                            <td width="16%" align="center"  class="td1"><s:property value='typeStr'/></td>
                            <td width="5%" align="center"  class="td1"><input type='checkbox' class='checkbox' name='idaaa' value='<s:property value='#item.id'/>'></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </s:iterator>
                    <tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
				<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/entity/function/images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td align="center" style="font-size:12px" background="/entity/function/images/bottom02.gif">
                       	共 <s:property value="totalPage"/> 页 <s:property value="totalSize"/> 条记录 | 
			            	<s:if test="curPage == 1">
			            		<font color="gray">首页 上一页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_paperQuetionItemToAdd.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&type=<s:property value='type'/>&paper_id=<s:property value='paper_id'/>&curPage=1">首页</a>
			            		<a href="/entity/studyZone/courseResources_paperQuetionItemToAdd.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&type=<s:property value='type'/>&paper_id=<s:property value='paper_id'/>&curPage=<s:property value="%{curPage-1}"/>">上一页</a>
			            	</s:else>
			            	<s:if test="curPage == totalPage">
			            		<font color="gray">下一页 尾页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_paperQuetionItemToAdd.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&type=<s:property value='type'/>&paper_id=<s:property value='paper_id'/>&curPage=<s:property value="%{curPage+1}"/>">下一页</a>
			            		<a href="/entity/studyZone/courseResources_paperQuetionItemToAdd.action?course_id=<s:property value='course_id'/>&test_id=<s:property value='test_id'/>&type=<s:property value='type'/>&paper_id=<s:property value='paper_id'/>&curPage=<s:property value="totalPage"/>">尾页</a>
			            	</s:else>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/bottom03.gif" width="806" height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
    </form>
</body>
</html>
