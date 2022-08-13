<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>添加题目</title>
 		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script>
		
		
		var bSubmit=false;
		var bLoaded=false;
		function pageGuarding()
		{

		//var oEditor = FCKeditorAPI.GetInstance('body') ;
      // var acontent=oEditor.GetXHTML();
      var acontent = document.getElementById('body').value;
       	if(acontent == null || acontent==""){
       		alert("内容为空，您还是多写几句吧！");
					return;
       	}
       	if(acontent.length > 500)
			{
				alert("内容不得多于500个字，请重新填写!");
				return false;
			}
			var itemNum;
			for(k=0;k<40;k++)
			{
				if(document.getElementById("tr"+k).style.display=="none")
				{
					itemNum=k;
					break;
				}
			}
			if(itemNum<1)
			{
				alert("没有选项!");
				return false;
			}
			
			for(k=0;k<40;k++)
			{
				if(document.getElementById("tr"+k).style.display !="none")
				{
					var t= document.getElementById("item"+(k+1)).value;
					if(t==null ||t==""){
						alert("选项："+(k+1)+"不能为空！");
						return false;
					}
					else if(t.length>25)
					{
						alert("选项："+(k+1)+"长度不能大于25！");
						return false;
					}
				}else{
					break;
				}
			}
			
			document.paperinfo.itemNum.value=itemNum;
			document.paperinfo.submit();
		}
</script>
  </head>
  
  <body  leftmargin="0" topmargin="0" class="scllbar">
  		<form name='paperinfo' action='/entity/information/prVoteQuestion_addQuestion.action?bean.peVotePaper.id=<s:property value="peVotePaper.id"/>' method='post'
			class='nomargin' onsubmit="">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><div class="content_title" id="zlb_title_start">
												添加调查问卷题目
											    <input type="hidden" name="peVotePaper_id" value="<s:property value="peVotePaper_id"/>"/></div id="zlb_title_end"></td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder_zlb">

						<div class="cntent_k" id="zlb_content_start">
							<table width="96%" border="0" cellpadding="0" cellspacing="0">
								<input type=hidden name="paper_id" value="<s:property value="peVotePaper.id"/>">
										<input type=hidden name="itemNum" value="4">
										
								

								 <tr valign="bottom" class="postFormBox"> 
						              <td valign="top" nowrap="nowrap"><span class="name">内容：</span></td>
						              <td> 
						              <textarea class="smallarea"  name="bean.questionNote" cols="70" rows="8" id="body"></textarea>
						
									  <!--img src="../images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="top.showHelpInfo('该新闻类型的介绍')" onmouseout="top.showHelpInfo()"-->
						              </td>
            					</tr>
            					
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" nowrap="nowrap">
										<span class="name">题目类型：</span>
									</td>
									<td>
										<select name="question" id="question_type" class="input6303">
										<s:iterator value="questionTypeList">
											<option value="<s:property value="code"/>"><s:property value="name"/>
											</option>
										</s:iterator>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<%for(int i=0;i<40;i++)
								{ 
								%>
								<tr id="tr<%=i %>" valign="bottom" class="postFormBox" <%if(i>=4) out.print("style='display:none'"); %>>
									<td valign="bottom" nowrap="nowrap">
										<span class="name">选项<%=i+1 %>:</span>
									</td>
									<td>
										<input type=text id="item<%=i+1 %>" name="bean.item<%=i+1 %>" class=selfScale size="50" maxlength="25"> 
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
											<td align=center>
											<span class="norm"
												style="width:100px;height:15px;padding-top:3px"
												onmouseover="className='over'" onmouseout="className='norm'"
												onmousedown="className='push'" onmouseup="className='over'"
												onclick="javascript:deleteNewSelect();"><span class="text">[删除选项]</span>
											</span>
											</td>
											<td  align=center>
											<span class="norm"
												style="width:100px;height:15px;padding-top:3px"
												onmouseover="className='over'" onmouseout="className='norm'"
												onmousedown="className='push'" onmouseup="className='over'"
												onclick="javascript:addNewSelect();"><span class="text">[添加新选项]</span>
											</span>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<script>
									function addNewSelect()
									{
										for(k=0;k<40;k++)
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
										if(confirm("你确认要删除最后一个选项吗?"))
										{
											
											for(k=39;k>=0;k--)
											{
												if(document.getElementById("tr"+k).style.display!="none")
												{
													document.getElementById("tr"+k).style.display="none";
													document.getElementById("item"+(k+1)).value="";
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
   
</html>
