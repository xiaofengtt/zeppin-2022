<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script language="javascript">
   function onSub(){
     var type = document.getElementById("type");
     var lore_id = "<s:property value='lore_id'/>";
     var fatherDir_id = "<s:property value='fatherDir_id'/>";
     var course_id = "<s:property value='course_id'/>";
	 var link = "/entity/function/lore/lore_store_question_batch_add.jsp?lore_id="+lore_id+"&fatherDir_id="+fatherDir_id+"&course_id=" + course_id;
     if(type!="YUEDU")
       window.location.href= link + ", 'newwindow', ' top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes'";
       //window.open (link, 'newwindow', ' top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes');
       else
       	alert("不能导入");
   }

   function onDown1(){
	  var link="";
      if(type=="DANXUAN")
    	link = "lore_list_excel1.jsp?lore_id=<s:property value='lore_id'/>";
      if(type=="DUOXUAN")
      	link = "lore_list_excel2.jsp?lore_id=<s:property value='lore_id'/>";
      if(type=="WENDA")
      	link = "lore_list_excel4.jsp?lore_id=<s:property value='lore_id'/>";
       if(type=="PANDUAN")
      	link = "lore_list_excel3.jsp?lore_id=<s:property value='lore_id'/>";
       if(type=="TIANKONG")
      	link = "lore_list_excel5.jsp?lore_id=<s:property value='lore_id'/>";
       if(type!="YUEDU")		
	       window.open (link);
	   else
	   	   alert("不能导出");
   }

 function onDown(){
     var type = document.getElementById("type");
          
          var link = "download.jsp?lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>" ;
          if(type!="YUEDU")
       window.open (link, 'newwindow', ' top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=yes');
       else
       　	alert("不能导出");
   }
   
   function listSelect(listId) {
  
	var form = document.forms[listId];
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'idaaa')) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = form.listSelectAll.checked;
			}
		}
	}
	return true;
}
 
</script>
<script type="text/javascript">
function deleteaa() {
	var x=document.getElementsByName("idaaa");
	var issel=false;
	for(var i=0;i<x.length;i++){
		if(x[i].checked){
			issel=true;

		} 
	}
 	if(issel==false){
 		alert("请选择要删除的题目！");
 		return false;
 	}	
	document.user.submit();	
}

function checkTitle()
{
	if(document.search.searchVal.value!="" && document.search.searchVal.value.indexOf("'")!="-1")
	{
		alert("对不起，搜索内容不能包含英文单引号！");
		document.search.searchVal.focus();
		return false;
	}
}
</script>
<s:if test="msg != null and msg !=''">
	<script>
		alert("<s:property value='msg' escape='false'/>");
	</script>
</s:if>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="/entity/function/images/top_01.gif">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="/entity/function/lore/images/tk.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="left" valign="top">
                      	<form name="search" action="/entity/studyZone/courseResources_enterLore.action" method="post" onsubmit="return checkTitle();">
                      	<input type="hidden" name="lore_id" value="<s:property value='lore_id'/>" />
                      	<input type="hidden" name="fatherDir_id" value="<s:property value='fatherDir_id'/>" />
                      	<input type="hidden" name="course_id" value="<s:property value='course_id'/>" />
                        <table width="90%" border="0" cellpadding="0" cellspacing="0">
                          <tr>                            
                            <td align="center"><img src="/entity/function/images/xb.gif" width="48" height="32"></td>
                            <td align="center" width="30%" class="mc1">按名称搜索：</td>
                            <td align="center"><input name="searchVal" type="text" size="20" maxlength="50" value="<s:property value='searchVal'/>"></td>
                            <td align="center"><input type="image" src="/entity/function/images/search.gif" width="99" height="19"></td>
                            <td align="right" width="50%" class="mc1">
								<a href="/entity/studyZone/courseResources_loreList.action?course_id=<s:property value='course_id'/>&loreDir_id=<s:property value='fatherDir_id'/>">[返回知识点]</a>
								<a href="/entity/studyZone/courseResources_toQuestionAdd.action?lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value='course_id'/>&type=DANXUAN">[添加题目]</a>
								<div id='link'><a href="/entity/function/lore/lore_store_question_batch_add.jsp?lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value='course_id'/>">[批量导入]</a>
								<!-- 
								<a href="#" onclick="javascript:onDown()">[批量导出]</a>
								 -->
								 </div>
								 <div></div>
								 </td>
							
                          </tr>
                        </table>
                        </form>
                      </td>
                      <td height="46">&nbsp;</td>
                    </tr>
                </table>
                </td>
              </tr>
              <form name="user" action="/entity/studyZone/courseResources_batchDeleteQuestion.action" method="post">
              	<input type="hidden" name="lore_id" value="<s:property value='lore_id'/>" />
               	<input type="hidden" name="fatherDir_id" value="<s:property value='fatherDir_id'/>" />
               	<input type="hidden" name="course_id" value="<s:property value='course_id'/>" />
              <tr>
                <td align="center">
                
			<table width="812" border="0" cellspacing="0" cellpadding="0">
              <tr>
                      
                <td height="26" background="/entity/function/images/tabletop.gif">
	              <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width='0' class='select' align='center'><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('user')"></td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="24%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">发布时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="20%" align="center" class="title">题目类型</td>
                      <td width="1%" align="center" valign="bottom"><img src="/entity/function/images/topxb.gif"></td>
                      <td width="15%" align="center" class="title">操作</td>
                    </tr>                    
                  </table>
                </td>
                </tr>
               <s:set name="urlList" value="urlList"/>
               <s:set name="typeStrList" value="typeStrList"/>
              <s:iterator value="questionInfoList" id="info" status="stas">
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr> 
                           <td width="5%" align="center" valign="middle" class="td1"><img src="/entity/function/images/xb2.gif" width="8" height="8"></td>
                          <td class='select' style='text-align: center; '> <input type='checkbox' class='checkbox' name='idaaa' value="<s:property value='#info.id'/>" id=''> </td>
                            <input type="hidden" id= "type" name="type" value=<s:property value='#info.type'/> />
                            <input type="hidden" id= "title" name="title" value=<s:property value='#info.title'/> />
                            <td width="24%" align="center" class="td1"><a href="<s:property value='#urlList[#stas.index]'/>?question_id=<s:property value='#info.id'/>" target="_blank"><s:property value='#info.title'/></a></td>
                            <td width="18%" align="center" class="td1"><s:property value='#info.creatuser'/></td>
                            <td width="17%" align="center" class="td1"><s:date name='#info.creatdate' format='yyyy-MM-dd'/></td>
                            <td width="21%" align="center" class="td1"><s:property value='#typeStrList[#stas.index]'/></td>
                            <td width="15%" align="center" class="td1"><a href="/entity/studyZone/courseResources_editQuestion.action?course_id=<s:property value='course_id'/>&question_id=<s:property value='#info.id'/>&lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&type=<s:property value='#info.type'/>&curPage=<s:property value='curPage'/>">编辑</a>&nbsp;
                            <a href="javascript:if(confirm('确定删除吗？')) window.location.href='/entity/studyZone/courseResources_deleteQuestion.action?course_id=<s:property value='course_id'/>&question_id=<s:property value='#info.id'/>&lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&curPage=<s:property value='curPage'/>'">删除</a></td>
                          </tr>
                         
                        </table>
                      </td>
                    </tr>
              </s:iterator>
                    <tr>
                      <td align="center" background="/entity/function/images/tablebian.gif"><a href="#" onclick="javascript:deleteaa();">批量删除</a></td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
                  </form>
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
			            		<a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value="lore_id"/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value="course_id"/>&curPage=1&searchVal=<s:property value="searchVal"/>">首页</a>
			            		<a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value="lore_id"/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value="course_id"/>&curPage=<s:property value="%{curPage-1}"/>&searchVal=<s:property value="searchVal"/>">上一页</a>
			            	</s:else>
			            	<s:if test="curPage == totalPage">
			            		<font color="gray">下一页 尾页</font>
			            	</s:if>
			            	<s:else>
			            		<a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value="lore_id"/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value="course_id"/>&&curPage=<s:property value="%{curPage+1}"/>&searchVal=<s:property value="searchVal"/>">下一页</a>
			            		<a href="/entity/studyZone/courseResources_enterLore.action?lore_id=<s:property value="lore_id"/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value="course_id"/>&&curPage=<s:property value="totalPage"/>&searchVal=<s:property value="searchVal"/>">尾页</a>
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
<script>
    function   setdiv(divname)   {   
       var   obj   =   document.getElementById(divname);
       if(document.getElementById('type') != null) {
	       var  type = document.getElementById('type').value;
	       if(type!='YUEDU'){
	       	obj.style.display='block';
	       }
       }
  	}
  	   
   setdiv('link');
</script>
</body>
</html>
