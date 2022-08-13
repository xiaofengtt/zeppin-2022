<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>发送邮件</title>
 		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script>
		
	//	var bSubmit=false;
	//	var bLoaded=false;
		function pageGuarding()
		{
		var zhuti= document.getElementById("theme").value;
		if(zhuti == null || zhuti==''){
			alert("主题为空！");
			return;
		}
		
		var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       	if(acontent == null || acontent==""){
       		alert("内容为空，您还是多写几句吧！");
					return;
       	}
       	if(acontent.length > 2000)
			{
				alert("内容不得多于2000个字，请重新填写!");
				return false;
			}
			var itemNum=0;
			for(k=0;k<5;k++)
			{
				if(document.getElementById("tr"+k).style.display!="none")
				{
					itemNum++;
				}
			}
			
			for(k=0;k<5;k++)
			{
				if(document.getElementById("tr"+k).style.display !="none")
				{
					var t= document.getElementById("upload"+(k+1)).value;
					if(t==null ||t==""){
						alert("附件"+(k+1)+"：不能为空！");
						return false;
					}
				}else{
					break;
				}
			}
			//alert(itemNum);
			document.paperinfo.itemNum.value=itemNum;
			document.paperinfo.submit();
		}
</script>
  </head>
  
  <body  leftmargin="0" topmargin="0" class="scllbar">
  		<form name='paperinfo' action='<s:property value="getServletPath()"/>_sendEmail.action' method='post'enctype="multipart/form-data"
			class='nomargin' onsubmit="">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><div class="content_title" id="zlb_title_start">
												发送邮件
			 <input type="hidden" name="targertList" value="<s:property value="targertList"/>"/>
			  <input type="hidden" name="itemNum" id="itemNum"/>
			 </div id="zlb_title_end"></td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder_zlb">

						<div class="cntent_k" id="zlb_content_start">
							<table width="96%" border="0" cellpadding="0" cellspacing="0">
								
								 <tr valign="bottom" class="postFormBox"> 
						              <td valign="top" nowrap="nowrap"><span class="name">主题：</span></td>
						              <td> 
						              <input class=selfScale name="theme"  id="theme" width="100" />
						
									  <!--img src="../images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="top.showHelpInfo('该新闻类型的介绍')" onmouseout="top.showHelpInfo()"-->
						              </td>
            					</tr>	
								

								 <tr valign="bottom" class="postFormBox"> 
						              <td valign="top" nowrap="nowrap"><span class="name">内容：</span></td>
						              <td> 
						              <textarea class="smallarea"  name="content" cols="70" rows="12" id="body"></textarea>
						
									  <!--img src="../images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="top.showHelpInfo('该新闻类型的介绍')" onmouseout="top.showHelpInfo()"-->
						              </td>
            					</tr>
            					
								
								<%for(int i=0;i<5;i++)
								{ 
								%>
								<tr id="tr<%=i %>" valign="bottom" class="postFormBox" <%if(i>=0) out.print("style='display:none'"); %>>
									<td valign="bottom" nowrap="nowrap">
										<span class="name">附件<%=i+1 %>:</span>
									</td>
									<td>
										<input type="file" id="upload<%=i+1 %>" name="upload<%=i+1 %>" class=selfScale size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								
								<%
								} 
								%>
								<tr>
									<td colspan=2 align=center>
									<table>
										<tr>
											<td>
											<span class="norm"
												style="width:100px;height:15px;padding-top:3px"
												onmouseover="className='over'" onmouseout="className='norm'"
												onmousedown="className='push'" onmouseup="className='over'"
												onclick="javascript:deleteNewSelect();"><span class="text">[删除附件]</span>
											</span>
											</td>
											<td  align=center>
											<span class="norm"
												style="width:100px;height:15px;padding-top:3px"
												onmouseover="className='over'" onmouseout="className='norm'"
												onmousedown="className='push'" onmouseup="className='over'"
												onclick="javascript:addNewSelect();"><span class="text">[添加新附件]</span>
											</span>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<script>
									function addNewSelect()
									{
										for(k=0;k<5;k++)
										{
											if(document.getElementById("tr"+k).style.display=="none")
											{
												document.getElementById("tr"+k).style.display="block";
												break;
											}
										}
										
									}
									function deleteNewSelect()
									{
										if(confirm("你确认要删除最后一个附件吗?"))
										{
											
											for(k=4;k>=0;k--)
											{
												if(document.getElementById("tr"+k).style.display!="none")
												{
													document.getElementById("tr"+k).style.display="none";
												//	document.getElementById("item"+(k+1)).value="";
													break;
												}
											}
										}
										
									}
								</script>
							</table>
						</div>
						
					</td>
				</tr>
				<tr>
					<td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0"
							cellspacing="0"><tr>
								<td align="center" valign="middle">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:pageGuarding();"><span class="text">提交</span>
									</span>
								</td>
								<td align="left" valign="middle">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:history.back();"><span class="text">返回</span>
									</span>
								</td>
							</tr>
						</table>
					</td>
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


oFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script>   
</html>
