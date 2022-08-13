<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ include file="/entity/function/pub/priv.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>华南师范大学远程教育管理平台</title>
		<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
		<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此作业吗？")) {
		delform.homeworkInfoId.value=link;
		document.delform.submit();
	}
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" background="/entity/function/images/bg2.gif">
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" class="bg3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td valign="top" background="/entity/function/images/top_01.gif">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<!-- tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/zxzy.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr-->
									<tr>
										<td align="right" valign="top">
											<table width="60%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td align="center">
														<a href="/entity/workspaceTeacher/interactionHomework_toAdd.action" class="tj">[添加新作业]</a>&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
						
							<td align="center">
								<table width="95%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="26" background="/entity/function/images/tabletop.gif"
											style="background-repeat: no-repeat;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='/entity/function/images/tabletop.gif');">
											<table width="100%" border="0" align="center" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="22%" align="center" class="title">
														名&nbsp;&nbsp;&nbsp;&nbsp;称
													</td>
													<td width="1%" align="center" valign="bottom">
														<img src="/entity/function/images/topxb.gif">
													</td>
													<td width="6%" align="center" class="title">
														发布人
													</td>
													<td width="1%" align="center" valign="bottom">
														<img src="/entity/function/images/topxb.gif">
													</td>
													<td width="8%" align="center" class="title">
														发布时间
													</td>
													<td width="1%" align="center" valign="bottom">
														<img src="/entity/function/images/topxb.gif">
													</td>
													<td width="22%" align="center" class="title">
														作业时间
													</td>
													<td width="1%" align="center" valign="bottom">
														<img src="/entity/function/images/topxb.gif">
													</td>
													<td width="5%" align="center" class="title">
														状态
													</td>
													<td width="1%" align="center" valign="bottom">
														<img src="/entity/function/images/topxb.gif">
													</td>
													<td width="30%" align="center" class="title">
														操作
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
									<form name="delform" action="/entity/workspaceTeacher/interactionHomework_delHomeworkInfo.action">
										<input type="hidden" name="homeworkInfoId">
										
									<s:iterator value="homeworkList" status="stuts">
										<tr>
											<td align="center" background="/entity/function/images/tablebian.gif">
												<table width="99%" border="0" cellspacing="0"
													cellpadding="0" class="mc1">
													<s:if test="#stuts.odd == true">
														<tr bgcolor="#E8F9FF">
													</s:if>
													<s:else>
														<tr bgcolor="#ffffff">
													</s:else>
													<td width="21%" class="td1">
														<a href="/entity/workspaceTeacher/interactionHomework_viewDetail.action?homeworkInfoId=<s:property value='id'/>"
															target=_blank><s:property value="title"/> </a>
													</td>
													<td width="6%" align="center" class="td1">
														<s:property value="peTeacher.trueName"/>
													</td>
													<td width="10%" align="center" class="td1">
														<s:date name="creatdate" format="yyyy-MM-dd"/>
													</td>
													<td width="23%" align="center" class="td1">
														<s:date name="startdate" format="yyyy-MM-dd hh:mm:ss"/>
														<br>
														至
														<br>
														<s:date name="enddate" format="yyyy-MM-dd hh:mm:ss"/>
													</td>
													<td width="5%" align="center"  class="td1">
														<a href="/entity/workspaceTeacher/interactionHomework_changeValid.action?homeworkInfoId=<s:property value="id"/>"><s:if test="enumConstByFlagIsvalid.code==1">有效</s:if><s:else>无效</s:else></a>
													</td>
													<td width="30%" align="center"  class="td1">
														<a href="/entity/workspaceTeacher/interactionHomework_toHomeworkHistoryList.action?homeworkInfoId=<s:property value="id"/>">已交作业列表</a>
                            							<a href="/entity/workspaceTeacher/interactionHomework_toModifyHomeworkInfo.action?homeworkInfoId=<s:property value="id"/>">编辑基本信息</a>
							                            <a href="javascript:cfmdel('<s:property value="id"/>');" class="button">删除</a>
													</td>
													</tr>
												</table>
											</td>
										</tr>
									</s:iterator>
									</form>
						<tr>
                      <td><img src="/entity/function/images/tablebottom.gif" width="100%" height="4"></td>
                    </tr>
                  </table>
					<br>
				</td>
			</tr>
			<tr>
				<td align="center">
					<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/entity/function/images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/bottom02.gif">
                      <%@ include file="/entity/function/pub/struts_dividepage.jsp"%>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="/entity/function/images/bottom03.gif" width="806" height="7"></td>
                    </tr>
                  </table>
				</td>
			</tr>
		</table>
		</td>

		</tr>
		</table>
	</body>
</html>

