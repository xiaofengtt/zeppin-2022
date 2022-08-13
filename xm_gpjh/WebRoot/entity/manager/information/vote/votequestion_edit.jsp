<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>修改题目</title>
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
					else if(t.length>50)
					{
						alert("选项："+(k+1)+"长度不能大于50！");
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
  		<form name='paperinfo' action='/entity/information/prVoteQuestion_editQuestion.action?bean.id=<s:property value="bean.id"/>' method='post'
			class='nomargin' onsubmit="">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><div class="content_title" id="zlb_title_start">
												修改调查问卷题目
											   </div id="zlb_title_end"></td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder_zlb">

						<div class="cntent_k" id="zlb_content_start">
							<table width="96%" border="0" cellpadding="0" cellspacing="0">
								<input type=hidden name="paper_id" value="<s:property value="peVotePaper.id"/>">
										<input type=hidden name="itemNum" value="<s:property value="peVotePaper.itemNum"/>">
								

								 <tr valign="bottom" class="postFormBox"> 
						              <td valign="top" nowrap="nowrap"><span class="name">内容：</span></td>
						              <td> 
						              <textarea class="smallarea"  name="bean.questionNote" cols="70" rows="8" id="body"><s:property value="bean.questionNote"/></textarea>
						
									  <!--img src="../images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="top.showHelpInfo('该新闻类型的介绍')" onmouseout="top.showHelpInfo()"-->
						              </td>
            					</tr>
            					
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" nowrap="nowrap">
										<span class="name">题目类型：</span>
									</td>
									<td>
										<select name="question" id="question_type" class="input6303">
										 <s:set name="typecode" value="bean.enumConstByFlagQuestionType.code"/> 
										<s:iterator value="questionTypeList">
										 <s:set name="type" value="code"/>
											<option value="<s:property value="code"/>" <s:if test="#typecode == #type"> selected="selected"</s:if> ><s:property value="name"/>
											</option>
										</s:iterator>										
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								
								<tr id="tr0" valign="bottom" class="postFormBox"  <s:if test="bean.item1 == null">style='display:none'</s:if> >	
									<td valign="bottom" width="20%">
										<span class="name">选项1:</span>
									</td>
									<td>
										<input type=text id="item1" name="bean.item1" class=selfScale value="<s:property value="bean.item1"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							
								<tr id="tr1" valign="bottom" class="postFormBox"  <s:if test="bean.item2 == null">style='display:none'</s:if> >								
								
									<td valign="bottom" width="20%">
										<span class="name">选项2:</span>
									</td>
									<td>
										<input type=text id="item2" name="bean.item2" class=selfScale value="<s:property value="bean.item2"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>																
								
								<tr id="tr2" valign="bottom" class="postFormBox"  <s:if test="bean.item3 == null">style='display:none'</s:if> >	
									<td valign="bottom" width="20%">
										<span class="name">选项3:</span>
									</td>
									<td>
										<input type=text id="item3" name="bean.item3" class=selfScale value="<s:property value="bean.item3"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								
								<tr id="tr3" valign="bottom" class="postFormBox"  <s:if test="bean.item4 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项4:</span>
									</td>
									<td>
										<input type=text id="item4" name="bean.item4" class=selfScale value="<s:property value="bean.item4"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>		
								
								<tr id="tr4" valign="bottom" class="postFormBox"  <s:if test="bean.item5 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项5:</span>
									</td>
									<td>
										<input type=text id="item5" name="bean.item5" class=selfScale value="<s:property value="bean.item5"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								
								<tr id="tr5" valign="bottom" class="postFormBox"  <s:if test="bean.item6 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项6:</span>
									</td>
									<td>
										<input type=text id="item6" name="bean.item6" class=selfScale value="<s:property value="bean.item6"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>																
								
								<tr id="tr6" valign="bottom" class="postFormBox"  <s:if test="bean.item7 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项7:</span>
									</td>
									<td>
										<input type=text id="item7" name="bean.item7" class=selfScale value="<s:property value="bean.item7"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								
								<tr id="tr7" valign="bottom" class="postFormBox"  <s:if test="bean.item8 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项8:</span>
									</td>
									<td>
										<input type=text id="item8" name="bean.item8" class=selfScale value="<s:property value="bean.item8"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
							 
								<tr id="tr8" valign="bottom" class="postFormBox"  <s:if test="bean.item9 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项9:</span>
									</td>
									<td>
										<input type=text id="item9" name="bean.item9" class=selfScale value="<s:property value="bean.item9"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								
								<tr id="tr9" valign="bottom" class="postFormBox"  <s:if test="bean.item10 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项10:</span>
									</td>
									<td>
										<input type=text id="item10" name="bean.item10" class=selfScale value="<s:property value="bean.item10"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>																
								
								<tr id="tr10" valign="bottom" class="postFormBox"  <s:if test="bean.item11 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项11:</span>
									</td>
									<td>
										<input type=text id="item11" name="bean.item11" class=selfScale value="<s:property value="bean.item11"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								
								<tr id="tr11" valign="bottom" class="postFormBox"  <s:if test="bean.item12 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项12:</span>
									</td>
									<td>
										<input type=text id="item12" name="bean.item12" class=selfScale value="<s:property value="bean.item12"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>		
								
								<tr id="tr12" valign="bottom" class="postFormBox"  <s:if test="bean.item13 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项13:</span>
									</td>
									<td>
										<input type=text id="item13" name="bean.item13" class=selfScale value="<s:property value="bean.item13"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							
								<tr id="tr13" valign="bottom" class="postFormBox"  <s:if test="bean.item14 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项14:</span>
									</td>
									<td>
										<input type=text id="item14" name="bean.item14" class=selfScale value="<s:property value="bean.item14"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>																
								
								<tr id="tr14" valign="bottom" class="postFormBox"  <s:if test="bean.item15 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项15:</span>
									</td>
									<td>
										<input type=text id="item15" name="bean.item15" class=selfScale value="<s:property value="bean.item15"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
									
								<tr id="tr15" valign="bottom" class="postFormBox"  <s:if test="bean.item16 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项16:</span>
									</td>
									<td>
										<input type=text id="item16" name="bean.item16" class=selfScale value="<s:property value="bean.item16"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr16" valign="bottom" class="postFormBox"  <s:if test="bean.item17 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项17:</span>
									</td>
									<td>
										<input type=text id="item17" name="bean.item17" class=selfScale value="<s:property value="bean.item17"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr17" valign="bottom" class="postFormBox"  <s:if test="bean.item18 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项18:</span>
									</td>
									<td>
										<input type=text id="item18" name="bean.item18" class=selfScale value="<s:property value="bean.item18"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr18" valign="bottom" class="postFormBox"  <s:if test="bean.item19 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项19:</span>
									</td>
									<td>
										<input type=text id="item19" name="bean.item19" class=selfScale value="<s:property value="bean.item19"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr19" valign="bottom" class="postFormBox"  <s:if test="bean.item20 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项20:</span>
									</td>
									<td>
										<input type=text id="item20" name="bean.item20" class=selfScale value="<s:property value="bean.item20"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr20" valign="bottom" class="postFormBox"  <s:if test="bean.item21 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项21:</span>
									</td>
									<td>
										<input type=text id="item21" name="bean.item21" class=selfScale value="<s:property value="bean.item21"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr21" valign="bottom" class="postFormBox"  <s:if test="bean.item22 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项22:</span>
									</td>
									<td>
										<input type=text id="item22" name="bean.item22" class=selfScale value="<s:property value="bean.item22"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr22" valign="bottom" class="postFormBox"  <s:if test="bean.item23 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项23:</span>
									</td>
									<td>
										<input type=text id="item23" name="bean.item23" class=selfScale value="<s:property value="bean.item23"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr23" valign="bottom" class="postFormBox"  <s:if test="bean.item24 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项24:</span>
									</td>
									<td>
										<input type=text id="item24" name="bean.item24" class=selfScale value="<s:property value="bean.item24"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr24" valign="bottom" class="postFormBox"  <s:if test="bean.item25 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项25:</span>
									</td>
									<td>
										<input type=text id="item25" name="bean.item25" class=selfScale value="<s:property value="bean.item25"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr25" valign="bottom" class="postFormBox"  <s:if test="bean.item26 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项26:</span>
									</td>
									<td>
										<input type=text id="item26" name="bean.item26" class=selfScale value="<s:property value="bean.item26"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr26" valign="bottom" class="postFormBox"  <s:if test="bean.item27 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项27:</span>
									</td>
									<td>
										<input type=text id="item27" name="bean.item27" class=selfScale value="<s:property value="bean.item27"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr27" valign="bottom" class="postFormBox"  <s:if test="bean.item28 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项28:</span>
									</td>
									<td>
										<input type=text id="item28" name="bean.item28" class=selfScale value="<s:property value="bean.item28"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr28" valign="bottom" class="postFormBox"  <s:if test="bean.item29 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项29:</span>
									</td>
									<td>
										<input type=text id="item29" name="bean.item29" class=selfScale value="<s:property value="bean.item29"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr29" valign="bottom" class="postFormBox"  <s:if test="bean.item30 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项30:</span>
									</td>
									<td>
										<input type=text id="item30" name="bean.item30" class=selfScale value="<s:property value="bean.item30"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr30" valign="bottom" class="postFormBox"  <s:if test="bean.item31 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项31:</span>
									</td>
									<td>
										<input type=text id="item31" name="bean.item31" class=selfScale value="<s:property value="bean.item31"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr31" valign="bottom" class="postFormBox"  <s:if test="bean.item32 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项32:</span>
									</td>
									<td>
										<input type=text id="item32" name="bean.item32" class=selfScale value="<s:property value="bean.item32"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr id="tr32" valign="bottom" class="postFormBox"  <s:if test="bean.item33 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项33:</span>
									</td>
									<td>
										<input type=text id="item33" name="bean.item33" class=selfScale value="<s:property value="bean.item33"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr33" valign="bottom" class="postFormBox"  <s:if test="bean.item34 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项34:</span>
									</td>
									<td>
										<input type=text id="item34" name="bean.item34" class=selfScale value="<s:property value="bean.item34"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr34" valign="bottom" class="postFormBox"  <s:if test="bean.item35 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项35:</span>
									</td>
									<td>
										<input type=text id="item35" name="bean.item35" class=selfScale value="<s:property value="bean.item35"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr35" valign="bottom" class="postFormBox"  <s:if test="bean.item36 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项36:</span>
									</td>
									<td>
										<input type=text id="item36" name="bean.item36" class=selfScale value="<s:property value="bean.item36"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr36" valign="bottom" class="postFormBox"  <s:if test="bean.item37 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项37:</span>
									</td>
									<td>
										<input type=text id="item37" name="bean.item37" class=selfScale value="<s:property value="bean.item37"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr37" valign="bottom" class="postFormBox"  <s:if test="bean.item38 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项38:</span>
									</td>
									<td>
										<input type=text id="item38" name="bean.item38" class=selfScale value="<s:property value="bean.item38"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr38" valign="bottom" class="postFormBox"  <s:if test="bean.item39 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项39:</span>
									</td>
									<td>
										<input type=text id="item39" name="bean.item39" class=selfScale value="<s:property value="bean.item39"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
								<tr id="tr39" valign="bottom" class="postFormBox"  <s:if test="bean.item40 == null">style='display:none'</s:if> >
									<td valign="bottom" width="20%">
										<span class="name">选项40:</span>
									</td>
									<td>
										<input type=text id="item40" name="bean.item40" class=selfScale value="<s:property value="bean.item40"/>" size="50" maxlength="25"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>	
																		
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
								<td align="center" valign="middle">
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
		<script>bLoaded=true;</script>    
  </body>
 
</html>
