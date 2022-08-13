<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>问卷调查系统 问卷列表</title>
	<link href="assets/NewDefault.css" rel="stylesheet" type="text/css">
		<link id="linkBaseUrl" rel="stylesheet" type="text/css"
			href="assets/q_12px.css">
			<style>
.ufc {
	float: left;
	width: 87px;
	margin: 5px 10px 0 10px;
	background: url(img/uf.png) no-repeat;
	position: relative;
}

.ufc .usel {
	display: block;
	line-height: 22px;
	padding: 0 15px 0 5px;
	color: #f60;
	cursor: pointer;
	white-space: nowrap;
	height: 22px;
}

.ufc .uul {
	padding: 5px 0;
	border: 1px solid #bfbebe;
	margin-top: -1px !important;
	background: #fff;
	position: absolute;
	display: none;
	left: 0;
	top: 22px;
	zoom: 1;
	z-index: 99;
	min-width: 100px;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
	-webkit-box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
	float: none !important;
	margin-left: 0 !important;
}

.ufc .uul li {
	padding: 3px 15px !important;
	line-height: 20px;
	white-space: nowrap;
	float: none !important;
}

.ufc .uul li:hover {
	background: #eee;
}

.uclose {
	cursor: pointer;
	font: 14px "Comic Sans MS";
	color: #B4B4B4;
	padding: 0 3px;
}

.uclose:hover {
	color: #f60;
}

.projectCnt li {
	padding: 3px 5px !important;
}
</style>
			<link href="assets/newdesign.css" rel="stylesheet" type="text/css">
				<script type="text/javascript" src="js/vendor/jquery-1.9.1.min.js"></script>
				<script>
					$(function() {
						$('body').click(
								function(e) {
									var o = $(e.target);
									if (o.hasClass('usel')
											|| o.parent('.usel').length) {
										return;
									}
									if (!o.parent('.uul').length) {
										$('.uul').hide();
									}
								});
					})

					function change_status(id, s) {
						$.get('paper_status?id=' + id + '&s=' + s, function(r) {
							if (r == 'true')
								window.location = window.location.href;
						})
					}

					function showul(t) {
						$('.uul:visible').hide();//clear all show uul(fix bug)
						var uul = t.next('.uul');
						//console.log(uul.css("visibility"))
						if (uul.is(':visible'))
							uul.hide();
						else
							uul.show();
					}

					function getul(id) {
						$.get('paper_projects', function(r) {

							if (r != 'false') {

								$('#uul_' + id).html(r);
							} else {
								$('#uul_' + id).html('<li>所有项目已经指定问卷</li>');
							}

						})

						/* $.getJSON('paper_projects',function(ret){
							
							if( data.item.length > 0 ){
								var ufc = '';
								$.each(data.item, function(i, item){
									aClick = ' onclick="addP(\''+ item.pid +'\')"'
									ufc += '<li '+ aClick +' >'+ itme.name +'</li>';
									$("#uul_"+id).append(ufc);	
									
								})
							}else {
								$('#uul_'+id).html('');
							}
							
						}) */
					}

					function addP(pid, obj) {
						var id = $(obj).parent().attr('id');
						var _ = id.split('_');
						var html = $(obj).html();
						$.get('paper_updateproject', {
							'pid' : pid,
							'qid' : _[1],
							'action' : 'add'
						}, function(r) {
							if (r == 'true') {
								var closex = '<span onclick="delP(' + pid
										+ ', this)" class="uclose">X</span>'
								$('#projectCnt_' + _[1]).append(
										'<li>' + html + closex + '</li>');
								$(obj).parent().hide();
								$(obj).remove();

							}

						})

					}
					function delP(pid, obj) {
						var id = $(obj).parent().parent().attr('id');
						var _ = id.split('_');
						$.get('paper_updateproject', {
							'pid' : pid,
							'qid' : _[1],
							'action' : 'del'
						}, function(r) {
							if (r == 'true') {
								$(obj).parent().remove();
							} else {
								alert("在此培训项目有学员进行问卷调查不可删除！");
							}

						})
					}

					function doTongZhi(id) {
						$
								.get(
										'paper_doLookLoginKey?paperid=' + id,
										function(r) {
											if (r == "true") {
												window.alert("本项目有未生成登录码的学员，正在生成登录码，请稍后。");
												$.get('paper_doLoginKey?paperid='+ id,function(t) {
													window.location.href = 'paper_doTZ.action?paperid='
														+ id;
												});
											} else {
												if (r == "false") {
													window.location.href = 'paper_doTZ.action?paperid='
															+ id;
												} else {
													window.alert(r);
												}
											}
										});

					}

					function doTelLoginKey(id) {
						$
								.get(
										'paper_doLookLoginKey?paperid=' + id,
										function(r) {
											if (r == "true") {
												window.alert("开始生成学员登陆码");

												$("#dologinkey_" + id)
														.css(
																"background-image",
																"url(assets/loading.gif)");

												$.get('paper_doLoginKey?paperid='+ id,function(t) {
													window.alert(t);
													$("#dologinkey_"+ id).css("background-image","url(assets/telephone.gif)");
												});
											} else {
												if (r == "false") {
													window
															.alert("当前问卷已经生成所有人员的登录码！");
												} else {
													window.alert(r);
												}
											}
										});
					}
				</script>
</head>
<body class="queedit-page">


	<div class="que-header">问卷调查系统</div>
	<div
		style="width:870px; margin: 0 auto; text-align: left;border:4px solid #289fe5;">

		<div class="que-list-cnt">
			<div class="info" style="height:30px;">

				<div style="line-height:30px;">
					<div class="bluefont-16px" style="float:left;">我的问卷&nbsp;&nbsp;&nbsp;</div>
					<div class="buttons" style="float:left;">
						<a href="paper_add.action" target="_blank"
							style="padding:4px 6px; font-weight:800;" class="positive"
							title="设计新问卷"> <img alt=""
							src="http://image.sojump.com/images/Master_Images/WjxVip/application_form_add.gif">设计新问卷</a>
					</div>
					<div class="divclear"></div>
				</div>
			</div>

			<div id="stuff" style="font-size: 12px; color: #666; border: 0;">
				<div>
					<table cellspacing="0" border="0"
						id="ctl01_ContentPlaceHolder1_grvwActivity"
						style="width:98%;border-collapse:collapse;">
						<tbody>
							<s:iterator value="paperList" id="paper">
								<tr>
									<td align="left">
										<div
											style="border: 2px solid rgb(219, 218, 218); margin: 10px 0px 5px;">
											<div
												style="height: 33px;background-color:#dfeffb; line-height:33px;">
												<div style="width: 450px; float: left; padding-left:8px;">
													<a
														id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_hlViewQ"
														title="<s:property value="title"/>" class="link-b666"
														target="_blank" style="text-decoration: underline;"><s:if
															test="title.length()<25">
															<s:property value="title" />
														</s:if> <s:else>
															<s:property value="title.substring(0,25)" />...</s:else></a><span
														id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_lblId"
														style="font-weight:bold;">(ID:<s:property
															value="id" />)
													</span>
												</div>
												<s:if test="%{status != 2}">
													<div
														style="float:left;width:200px;padding:0 8px;font-weight:bold;">
														<span style="display:inline-block;float:left;">所属项目</span>
														<div class="ufc">
															<span
																onclick="getul('<s:property value="id"/>');showul($(this))"
																class="usel"><strong>请选择...</strong></span>
															<ul id='uul_<s:property value="id"/>' class="uul"
																style="display: none;overflow:auto;height:200px;"></ul>
														</div>
													</div>
												</s:if>
												<div style="float: right;margin-right:10px;">
													&nbsp;&nbsp;&nbsp;&nbsp; <span
														id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_lblStatus"
														title="此问卷正在运行中，可以正常接收答卷"
														style="color:#666666;font-weight:bold;"> <s:if
															test="%{status == 1}">
											草稿
											</s:if> <s:if test="%{status == 2}">
											运行中
											</s:if> <s:if test="%{status == 3}">
											停止
											</s:if>
													</span> &nbsp;

												</div>
												<div class="divclear"></div>
											</div>
											<div style="min-height: 38px;padding:6px;">
												<ul style="float:left;overflow:hidden;">
													<li><s:if test="%{editalbe == 1 and status != 2}">
															<a class="view" target="_blank" title="设计问卷"
																editversion="0" hasvalue=""
																value="<s:property value="id"/>"
																href="paper_edit?curid=<s:property value="id"/>"> <span
																class="spanLeftTxt">设计问卷</span></a>
														</s:if> <s:else>
															<a class="view" onclick="alert('试卷在运行中或者已经提交过不能进行编辑！')"
																title="试卷在运行中或者已经提交过不能进行编辑！" href="javascript:void(0)"><span
																class="spanLeftTxt">设计问卷</span></a>
														</s:else></li>

													<li><a
														href="report_summary.action?pid=<s:property value="id"/>"
														target="_blank"
														id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_hrefReport"
														class="report" title="答卷统计分析"
														style="height: 22px; width: 69px;"
														value="<s:property value="id"/>"> <span
															class="spanLeftTxt">分析&amp;下载</span></a></li>
												</ul>

												<ul class="projectCnt"
													id='projectCnt_<s:property value="id"/>'
													style="float:left;width:300px;overflow:hidden;margin-left:100px;">

													<s:iterator value="projects" id="p" status="pro">
														<li><s:property value="#p.name" /> <s:if
																test="%{#paper.status != 2}">
																<span
																	onclick="delP('<s:property value="#p.id"/>', this)"
																	class="uclose">X</span>
															</s:if></li>
													</s:iterator>
												</ul>
												<ul style="float:right; padding-right:8px;">
													<li></li>
													<li><s:if test="%{status == 1}">
															<a onclick="return confirm('状态设为“发布”后将不能修改试卷，是否继续？');"
																id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus"
																title="此问卷正在草稿状态，点击发布问卷" class="run link-444"
																href='javascript:change_status(<s:property value="id"/>,2)'>发布</a>
														</s:if> <s:if test="%{status == 2}">
															<a onclick="return confirm('状态设为“停止”后将不能投票，是否继续？');"
																id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus"
																title="此问卷正在草稿状态，点击发布问卷" class="pause link-444"
																href='javascript:change_status(<s:property value="id"/>,3)'>停止</a>
														</s:if> <s:if test="%{status == 3}">
															<a onclick="return confirm('状态设为“发布”后将不能修改试卷，是否继续？');"
																id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus"
																title="此问卷正在草稿状态，点击发布问卷" class="run link-444"
																href="javascript:change_status(<s:property value='id'/>,2)">发布</a>
														</s:if> <!-- <a onclick="return confirm('状态设为“停止”后将不能填写，是否继续？');" id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus" title="此问卷正在运行，点击停止答卷回收" class="pause link-444" href="javascript:__doPostBack('ctl01$ContentPlaceHolder1$grvwActivity$ctl02$btnChangeStatus','')">停止</a> -->
													</li>
													<li><a onclick="return confirm('试卷删除后后将不能恢复，是否继续？');"
														id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus"
														title="此试卷删除后将不可恢复！" class="delete link-444"
														href="javascript:change_status(<s:property value='id'/>,0)">删除</a>
													</li>
													<li><a
														style="background:url(assets/invite_ico.gif) no-repeat 6px 4px;"
														href="javascript:doTongZhi(<s:property value='id'/>)">通知</a>
													</li>
													<li><a id="dologinkey_<s:property value="id"/>"
														style="background:url(assets/telephone.gif) no-repeat 6px 4px;"
														href="javascript:doTelLoginKey(<s:property value='id'/>)">生成登录码</a>
													</li>
												</ul>
												<div class="divclear"></div>
											</div>

										</div>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>
	<div style="display:none;"></div>
</body>
</html>