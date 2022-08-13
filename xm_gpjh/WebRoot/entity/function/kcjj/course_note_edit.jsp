<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ include file="../pub/priv.jsp"%>

<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
%>
<%
	String id = request.getParameter("id");
	String title1 = request.getParameter("title");
	String type = request.getParameter("type");
	String sql = "";
	dbpool db = new dbpool();
	MyResultSet rs =null;
	
	
	String pageInt = request.getParameter("pageInt");
   try{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		InteractionTeachClass  teachclass = interactionManage.getTeachClass(id);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/ht生殖健康咨询师培训网charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script language=javascript>
	var bLoaded=false; 
	function chkSubmit()
	{
		if(frmAnnounce.title.value.length ==0)
		{
			alert("请填写标题");
			return false;
		}/*
		if(currentflag==1)//处于普通编辑模式
		{
			frmAnnounce.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
		}        
		else 
		{
			if (currentflag==3)//处于预浏览先通编辑模式
			{
				frmAnnounce.body.value=oDiv.innerHTML;
			}
		}
		*/
		if(frmAnnounce.body.value.length <2 || frmAnnounce.body.value == "<P>&nbsp;</P>")
		{
			alert("内容为空，还是多写几句吧？");
			return false;
		}	
		document.getElementById("sub").disabled = true;
	}
</script>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="course_note_editexe.jsp" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type=hidden name=teachclass_id value=<%=teachclass_id %>>
<input type=hidden name=type value=<%=type %>>
<input type=hidden name=id value=<%=id %>>
<input type=hidden name=title1 value=<%=title1 %>>
<input type=hidden name=pageInt value=<%=pageInt %>>

        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="../images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="../images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="../images/xb3.gif" width="17" height="15"> 
                              <strong>课程简介</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="../images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td>
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            	<tr>
                            <td class="neirong" width=20% nowrap="nowrap">
							<font size=2>题目：</font>
							</td>
							<td class="neirong" valign=top>
							<input type=text name=title class=input1 size=60 value="<%=title1%>">
							</td>
						  </tr>
                          		<tr>
                          		<td class="neirong" valign=top width=20%>
							<font size=2>内容：</font>
							</td>
							<%
								
                            		sql = "select t.content  as note  from interaction_teachclass_info t where t.id = '"+id+"'";
                            		//out.println(sql);
                            		rs = db.executeQuery(sql);
	                                while(rs!=null && rs.next())
	                                {
		                               String  note = fixnull(rs.getString("note"));
		                               
		                               
		                             
                            	 
							%>
							<td class="neirong" valign=top>
							<textarea class="smallarea" cols="80" name="body" rows="12"><%=note%></textarea>
							<script language=JavaScript src="../../../WhatyEditor/config.jsp"></script><br>
							<script language=JavaScript src="../../../WhatyEditor/edit.js"></script>
							</td>
						  </tr>
                            	</table>
                            	
                            	
                            	 <%
                            	 }
                            	 db.close(rs);
                            	 %>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="../images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=submit value="提交" id="sub">&nbsp;&nbsp;<input type=button value="返回" onclick="window.location='coursenote_list.jsp?pageInt=<%=pageInt %>&title=<%=title1%>'"></td>
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

var oFCKeditor = new FCKeditor( 'body' ) ; 
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
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
