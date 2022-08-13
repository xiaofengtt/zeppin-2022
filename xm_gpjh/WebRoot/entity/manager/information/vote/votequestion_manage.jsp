<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>调查问卷</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/css.css" rel="stylesheet" type="text/css">
		<script>
		function cfmdel(que){
			var id= document.getElementById("peVotePaper").value;
			if(confirm("您确定要删除本题目吗？")) {
				window.navigate('/entity/information/prVoteQuestion_delQuestion.action?bean.id='+que+'&bean.peVotePaper.id='+id);
			}
		}
		</script>	
	</head>
  
  <body topmargin="0" class="scllbar">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<div class="content_title" id="zlb_title_start">调查问卷题目管理  
					<input type="hidden" value="<s:property value="peVotePaper.id"/>" id="peVotePaper"/>
				</div>
			</td>
		</tr>
		<tr>
			<td valign="top" class="pageBodyBorder_zlb">
				<div class="cntent_k" id="zlb_content_start">
					<table width="96%" border="0" cellpadding="0" cellspacing="0">
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">调查问卷名称：</span>
							</td>
							<td>
								<s:property value="peVotePaper.title"/>
							</td>
						</tr>
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">培训项目：</span>
							</td>
							<td>
								<s:property value="peVotePaper.peProApplyno.name"/>
							</td>
						</tr>
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">调查问卷图片名称：</span>
							</td>
							<td>
								<s:property value="peVotePaper.pictitle"/>
							</td>
						</tr>
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">调查问卷时间范围：</span>
							</td>
							<td>从<s:date name="peVotePaper.startDate" format="yyyy-MM-dd" />
								到<s:date name="peVotePaper.endDate" format="yyyy-MM-dd" />
							</td>
						</tr>
					<!-- 	<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">调查问卷类型：</span>
							</td>
							<td>
								<s:property value="peVotePaper.enumConstByFlagType.name"/>
							</td>
						</tr>	 -->						
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">是否发布：</span>
							</td>
							<td>
								<s:property value="peVotePaper.enumConstByFlagIsvalid.name"/>
							</td>
						</tr>
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">是否允许添加建议：</span>
							</td>
							<td>
								<s:property value="peVotePaper.enumConstByFlagCanSuggest.name"/>
							</td>
						</tr>
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">是否允许查看建议：</span>
							</td>
							<td>
								<s:property value="peVotePaper.enumConstByFlagViewSuggest.name"/>
							</td>
						</tr>
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">是否要求同一IP不能重复投票：</span>
							</td>
							<td>
								<s:property value="peVotePaper.enumConstByFlagLimitDiffip.name"/>
							</td>
						</tr>
						<tr valign="bottom" class="postFormBox">
							<td valign="bottom">
								<span class="name">是否要求同一会话不能重复投票：</span>
							</td>
							<td>
								<s:property value="peVotePaper.enumConstByFlagLimitDiffsession.name"/>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td>
			<!-- 列出题目 -->
				<table>
					<tr>
						<td>题目列表:&nbsp;&nbsp;
							<a href="/entity/information/prVoteQuestion_toAddQuestion.action?bean.peVotePaper.id=<s:property value="peVotePaper.id"/>">[添加题目]</a>
						</td>
					</tr>
					<tr>
						<td>
							<table width=100%>
							  <s:iterator value="questionList" status="num">
								<tr>
									<td>
										<font color="blue"><s:property value="#num.index+1"/>、</font>&nbsp;&nbsp;
										（<s:property value="enumConstByFlagQuestionType.name"/>） <s:property value="questionNote" escape="false"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="/entity/information/prVoteQuestion_toEditQuestion.action?bean.id=<s:property value="id"/>">[修改]</a>
										<a href="javascript:cfmdel('<s:property value="id"/>')">[删除]</a>
									</td>
									<td rowspan='2'>
										
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
											<s:if test="item1 != null">
											<tr>
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（1）</font>&nbsp;
												<s:property value="item1"/>&nbsp;
											</td>
											</tr>
										</s:if>
										<s:if test="item2 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（2）</font>&nbsp;
												<s:property value="item2"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item3 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（3）</font>&nbsp;
												<s:property value="item3"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item4 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（4）</font>&nbsp;
												<s:property value="item4"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item5 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（5）</font>&nbsp;
												<s:property value="item5"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item6 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（6）</font>&nbsp;
												<s:property value="item6"/>&nbsp;
											</td></tr>
										</s:if>		
										<s:if test="item7 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（7）</font>&nbsp;
												<s:property value="item7"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item8 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（8）</font>&nbsp;
												<s:property value="item8"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item9 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（9）</font>&nbsp;
												<s:property value="item9"/>&nbsp;
											</td> </tr>
										</s:if>	
										<s:if test="item10 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（10）</font>&nbsp;
												<s:property value="item10"/>&nbsp;
											</td> </tr>
										</s:if>
										<s:if test="item11 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（11）</font>&nbsp;
												<s:property value="item11"/>&nbsp;
											</td> </tr>
										</s:if>
										<s:if test="item12 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（12）</font>&nbsp;
												<s:property value="item12"/>&nbsp;
											</td> </tr>
										</s:if>		
										<s:if test="item13 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（13）</font>&nbsp;
												<s:property value="item13"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item14 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（14）</font>&nbsp;
												<s:property value="item14"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item15 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（15）</font>&nbsp;
												<s:property value="item15"/>&nbsp;
											</td></tr>
										</s:if>																																																																		
										<s:if test="item16 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（16）</font>&nbsp;
												<s:property value="item16"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item17 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（17）</font>&nbsp;
												<s:property value="item17"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item18 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（18）</font>&nbsp;
												<s:property value="item18"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item19 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（19）</font>&nbsp;
												<s:property value="item19"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item20 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（20）</font>&nbsp;
												<s:property value="item20"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item21 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（21）</font>&nbsp;
												<s:property value="item21"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item22 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（22）</font>&nbsp;
												<s:property value="item22"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item23 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（23）</font>&nbsp;
												<s:property value="item23"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item24 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（24）</font>&nbsp;
												<s:property value="item24"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item25 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（25）</font>&nbsp;
												<s:property value="item25"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item26 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（26）</font>&nbsp;
												<s:property value="item26"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item27 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（27）</font>&nbsp;
												<s:property value="item27"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item28 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（28）</font>&nbsp;
												<s:property value="item28"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item29 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（29）</font>&nbsp;
												<s:property value="item29"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item30 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（30）</font>&nbsp;
												<s:property value="item30"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item31 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（31）</font>&nbsp;
												<s:property value="item31"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item32 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（32）</font>&nbsp;
												<s:property value="item32"/>&nbsp;
											</td></tr>
										</s:if>
										<s:if test="item33 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（33）</font>&nbsp;
												<s:property value="item33"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item34 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（34）</font>&nbsp;
												<s:property value="item34"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item35 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（35）</font>&nbsp;
												<s:property value="item35"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item36 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（36）</font>&nbsp;
												<s:property value="item36"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item37 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（37）</font>&nbsp;
												<s:property value="item37"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item38 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（38）</font>&nbsp;
												<s:property value="item38"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item39 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（39）</font>&nbsp;
												<s:property value="item39"/>&nbsp;
											</td></tr>
										</s:if>	
										<s:if test="item40 != null">
										<tr>	<td>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:blue;">（40）</font>&nbsp;
												<s:property value="item40"/>&nbsp;
											</td></tr>
										</s:if>					
										</table>
									</td>
								</tr>
							</s:iterator>
							</table>
						</td>
					</tr>
				</table>
				</td>
				<!-- ------ -->
			</tr>
			
			<tr>
				<td height="10" align="center" class="pageBottomBorder">
					<table width="98%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="center" valign="middle">
								<form id="questionManager" action="/entity/information/peVotePaper.action">
									<input type="hidden" name="search1_peProApplyno.id" value="<%=session.getAttribute("applyNo") %>" />
									<input type="hidden" name="peUnit" value="<%=session.getAttribute("peUnit") %>" />
									<input type="hidden" name="peSubject" value="<%=session.getAttribute("peSubject") %>" />
								</form>
								<span class="norm" style="width:100px;height:15px;padding-top:3px"
									onmouseover="className='over'" onmouseout="className='norm'"
									onmousedown="className='push'" onmouseup="className='over'"
									onclick="document.forms['questionManager'].submit()">返回调查问卷列表</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>  
  </body>
</html>