<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ include file="/entity/function/pub/priv.jsp"%>

<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset=utf-8">
<title>生殖健康咨询师培训网 </title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script language="javascript" type="text/javascript" src="/js/datePicker/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script language=javascript>

//是否为空校验
function isEmpty(s) {
	var lll=trim(s);
	if( lll == null || lll.length == 0 )
		return true;
	else
		return false;
}

//删除字符串左边的空格
function ltrim(str) { 
	if(str.length==0)
		return(str);
	else {
		var idx=0;
		while(str.charAt(idx).search(/\s/)==0)
			idx++;
		return(str.substr(idx));
	}
}

//删除字符串右边的空格
function rtrim(str) { 
	if(str.length==0)
		return(str);
	else {
		var idx=str.length-1;
		while(str.charAt(idx).search(/\s/)==0)
			idx--;
		return(str.substring(0,idx+1));
	}
}

//删除字符串左右两边的空格
function trim(str) { 
	return(rtrim(ltrim(str)));
}
	function chkSubmit()
	{
		if(isEmpty(frmAnnounce.title.value))
		{
			alert("请填写标题");
			return false;
		}
		
		if(frmAnnounce.startDate.value.length == 0) {
			alert("请填写开始时间");
			return false;
		}
		if(frmAnnounce.endDate.value.length == 0) {
			alert("请填写结束时间");
			return false;
		}
		
		var oEditor = FCKeditorAPI.GetInstance('note') ;
       	var acontent=oEditor.GetXHTML();
       	if(isEmpty(acontent)){
			alert("对不起，题目内容不能为空！");
			
			return false;
		}
	}
</script>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="/entity/workspaceTeacher/interactionHomework_addExe.action" method="POST" name="frmAnnounce" onsubmit="return chkSubmit();">
        <input name="groupId" type="hidden" value="<%=teachclass_id %>">

        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>添加作业</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td>
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            <tr>
                            	<td class="neirong" width=20% nowrap="nowrap">
									<font size=2>题目：</font>
								</td>
								<td class="neirong" valign=top>
									<input type=text name="title" class=input1 size=60 value="">
								</td>
						  	</tr>
						  	<tr>
                            	<td class="neirong" width=20% nowrap="nowrap">
									<font size=2>开始时间：</font>
								</td>
								<td>
									<input class="Wdate" type="text" name="startDate" id="hts" onfocus="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true)" maxdate="#F{$('hte').value}" onPicked="$('hte').onfocus()"/>
								</td>		                 
						  	</tr>
						  	<tr>
                            	<td class="neirong" width=20% nowrap="nowrap">
									<font size=2>结束时间：</font>
								</td>
								<td>
									<input class="Wdate" type="text" name="endDate" id="hte" onfocus="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true)" mindate="#F{$('hts').value}" />
								</td>
						  	</tr>
                          	<tr>
                          		<td class="neirong" valign=top width=20%>
									<font size=2>内容：</font>
								</td>
								<td class="neirong" valign=top>
									<textarea class="smallarea" cols="80" name="note" rows="12"></textarea>
								</td>
						  	</tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=submit value="提交" id="sub">&nbsp;&nbsp;<input type=button value="返回" onclick="window.history.back()"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      </form>
</body>

<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 

var oFCKeditor = new FCKeditor( 'note' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
</html>

