<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>生殖健康咨询师培训课堂</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

a:link { text-decoration: none;color: #000000}
a:active { text-decoration:blink}
a:hover { text-decoration:underline;color: red} 
a:visited { text-decoration: none;color: #000000}

.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style>
</head>
<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0
				marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no
				allowTransparency="true"></IFRAME>
			<tr>
				<td height="13"></td>
			</tr>
		</table>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0">
		<!--
		
			<tr>
				<td width="237" valign="top">
					<iframe name="leftA" width=237 height=520 frameborder=0
						marginwidth=0 marginheight=0
						src="/entity/bzz-students/pub/left.jsp" scrolling=no
						allowtransparency="true"></iframe>
				</td>
				<td width="543" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr align="center" valign="top">
							<td height="17" align="left" class="twocentop">
								<img src="/entity/bzz-students/images/two/1.jpg" width="11"
									height="12" />
								当前位置：浏览公告
								&nbsp; marquee id="affiche" align="left" behavior="scroll" direction="left" hspace="5" vspace="5" loop="-1" scrollamount="20" scrolldelay="300" onMouseOut="this.start()" onMouseOver="this.stop()">
								 
							</td>
						</tr>
						  <tr>
        <td><img name="two2_r5_c5" src="/entity/bzz-students/images/two/two2_r5_c5.jpg" width="543" height="72" border="0" id="two2_r5_c5" alt="" /></td>
      </tr>  
						<tr>
							<td height="56">
								<table width="96%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									 tr>
										<td align="left">
											<img src="/entity/bzz-students/images/two/2.jpg" width="124"
												height="24" />&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="/CourseImports/stupx/index.htm" style="text-decoration: none;" target="_blank"><img border="0" src="/entity/bzz-students/images/two/2_1.jpg" width="124"
												height="24" /></a>
										</td>
									</tr 
									<tr>
										<td height="0" align="left">
											 table width="100%" height="78" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td>
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0" class="twocen">
															<tr>
																<td width="38" align="right">
																	<img src="/entity/bzz-students/images/two/10.jpg"
																		width="15" height="14" />
																</td>
																<td width="29" align="center">
																	<img src="/entity/bzz-students/images/two/11.jpg"
																		width="3" height="17" />
																</td>
																<td width="165">
																	正在学习培训课程数量：
																	<span class="STYLE2">
																	<s:property value="#session.jccomnum"/>
																					</span> 门
																</td>
																<td width="51" class="twocen1">
																	<a class="twocen1" href="/entity/workspaceStudent/stuPeBulletinView_DetailCourse.action?ctype=jcwc" onclick="window.alert('建设中...');return false;">[<font color="#1E90FF">浏览</font>]</a>
																</td>
															</tr>
														</table>
													</td>
													<td>
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0" class="twocen">
															<tr>
																<td width="38" align="right">
																	<img src="/entity/bzz-students/images/two/10.jpg"
																		width="15" height="14" />
																</td>
																<td width="29" align="center">
																	<img src="/entity/bzz-students/images/two/11.jpg"
																		width="3" height="17" />
																</td>
																<td width="165">
																	已经完成培训课程数量：
																	<span class="STYLE2">
																	<s:property value="#session.tscomnum"/>
																	</span> 门
																</td>
																<td width="51" class="twocen1">
																	<a href="/entity/workspaceStudent/stuPeBulletinView_DetailCourse.action?ctype=tswc" onclick="window.alert('建设中...');return false;">[<font color="#1E90FF">浏览</font>]</a>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table 
										</td>
									</tr>
									<tr>
										<td align="left">
											<img src="/entity/bzz-students/images/two/note.jpg"
												width="124" height="25" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr align="center">
							<td height="29"
								background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
								<table width="96%" border="0" cellpadding="0" cellspacing="0"
									class="twocencetop">
									<tr>
									<td width="3%" height="30" align="center">&nbsp;
											</td>
										<td width="57%" align="left">
											&nbsp;&nbsp;标题
										</td>
										<td width="20%" align="left">
											时间
										</td>
										<td width="20%" align="left">
											发布人
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr valign="top">
							<td height="252" >
							<s:if test="bulletinList.size() > 0">	
								<s:iterator value="bulletinList" status="bulletin">
									<s:if test="title==1">
									<table width="96%" border="0" cellpadding="0" cellspacing="0"
										class="twocen">
										<tr valign="bottom">
											<td width="3%" height="10" align="center">
												<img src="/entity/bzz-students/images/two/4.jpg" width="9"
													height="9" />
											</td>
											<td width="57%" align="left">
											</td>
											<td width="20%" align="left">
											</td>
											<td width="20%" align="left">
											</td>
										</tr>
										<tr valign="top">
											<td colspan="4">
												<img src="/entity/bzz-students/images/two/7.jpg" width="543"
													height="2" />
											</td>
										</tr>
										</table>
									</s:if><s:else>
									<table width="96%" border="0" cellpadding="0" cellspacing="0"
										class="twocen">
										<tr valign="bottom">
											<td width="3%" height="10" align="center">
												<img src="/entity/bzz-students/images/two/4.jpg" width="9"
													height="9" />
											</td>
											<td width="57%" align="left">
												<a href='/entity/workspaceStudent/stuPeBulletinView_toInfo.action?bean.id=<s:property value="id"/>' target="_blank"><s:property value="title" /></a>
											</td>
											<td width="20%" align="left">
												<s:date name="publishDate" format="yyyy-MM-dd" />
											</td>
											<td width="20%" align="left">
												<s:if test="peManager!=null">
													<s:property value="peManager.trueName" />
												</s:if>
											</td>
										</tr>
										<tr valign="top">
											<td colspan="4">
												<img src="/entity/bzz-students/images/two/7.jpg" width="543"
													height="2" />
											</td>
										</tr>
									</table>
									</s:else>
								</s:iterator>
								</s:if>
								<div class="font_bq" align="right" style="padding:0px 10px 0px 0px;"><a href="/entity/workspaceStudent/stuPeBulletinView_allPebulletins.action" target="_blank"><font color="#1E90FF">更多＞＞</font></a></div>
							</td>
						</tr>
					</table>
				</td>
				-->
				<td width="209" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<img name="two2_r4_c8"
									src="/entity/bzz-students/images/two/two2_r4_c8.jpg"
									width="209" height="52" border="0" id="two2_r4_c8" alt="" />
							</td>
						</tr>
						<tr>
							<td height="220" align="center" valign="middle"
								background="/entity/bzz-students/images/two/two2_r6_c8.jpg">
								<table width="90%" border="0" cellpadding="3" cellspacing="5"
									class="twocen">
									<tr valign="top">
										<td height="20" align="center">
											<font color="red"><s:property value="votePaper.title" />
											</font>
										</td>
									</tr>
									<tr valign="top">
										<td height="100" style="word-break: break-all" align="left">
											<s:property value="votePaper.note" escape="false" />
										</td>
									</tr>
									<tr valign="baseline">
										<td align="center">
											<a target="_blank"
												href="/entity/first/firstPeVotePaper_toVote.action?bean.id=<s:property value="votePaper.id" />" ><img
													border="0"
													src="/entity/bzz-students/images/two/wenjuan.jpg"
													width="51" height="22" />
											</a>
											<!--<a target="_blank"
												href="/entity/first/firstPeVotePaper_voteResult.action?bean.id=<s:property value="votePaper.id"/>" ><img
													border="0" src="/entity/bzz-students/images/two/9.jpg"
													width="72" height="22" />
											</a>-->
										</td>
									</tr>
								</table>
							</td>
				</tr>
						<tr>
							<td height="190" align="center" valign="top"
								background="/entity/bzz-students/images/two/two2_r14_c8.jpg">
								<table width="90%" border="0" cellspacing="3" cellpadding="4">
									<tr>
										<td align="left" class="twocentop">
											如果您对“问卷调查”有任
											<br />
											何意见或建议请发送邮件至
											<br />
											zhangqunfang@whaty.com
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<img name="two2_r22_c8"
									src="/entity/bzz-students/images/two/two2_r22_c8.jpg"
									width="209" height="13" border="0" id="two2_r22_c8" alt="" />
							</td>
						</tr>
						<tr>
              <td width="174" height="44" align="right" background="entity/images/two/two2_r11_c3.jpg">
			  <table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_getVoteList.action'">
             	<td align="center">调查问卷列表</td> 
        		</table>
        		</td></tr>
					</table>
				</td>
			</tr>
		</table>
		<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0
			marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no
			allowTransparency="true" align=center></IFRAME>
			</td>
  </tr>
</table>
	</body>
</html>