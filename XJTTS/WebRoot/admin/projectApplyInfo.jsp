<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目申报结果监审</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<style>
.companylocation {
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
	background: #fff url("../img/city_select_arrow.png") no-repeat 98% 50%;
	border-radius: 4px;
}

.companylocation:focus {
	border-color: #66afe9;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px
		rgba(102, 175, 233, .6);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px
		rgba(102, 175, 233, .6);
}
.select2-container .select2-choice{width:auto;height:28px;line-height:28px;border:none;}
.select2-container{padding:0;}
.select2-container .select2-choice>.select2-chosen {
	line-height: 34px;
}
.list-item-bd .list-5-08{    width: 22%;}
</style>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">项目申报信息管理</div>
		<div class="iconDiv">
			<a class="btn btnMyself btn-screen"> <span><img
					src="../img/sousuo.png" alt="icon"> <b>筛选</b> </span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角"> <b>筛选项目申报结果</b>
				</p>
			</a> <a class="btn btnMyself btn-create"
				href="../admin/projectApplyOpt_initPage.action"
				data-fancybox-type="iframe"> <span><img
					src="../img/kaishexiangmu.png" alt="icon"> <b>补录</b> </span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角"> <b>补录项目中标结果</b>
				</p>
			</a>
			<%-- 			<s:if test="isAdd == 1"> --%>
			<!-- 			<a class="btn btnMyself btn-create" onclick="addProjectApplyall(this)" data-url="../admin/projectApplyOpt_initExpertAllPage.action?evaluateType=1"> -->
			<%-- 				<span><img src="../img/chushenzhuanjia.png" alt="icon"></span> --%>
			<!-- 				<p> -->
			<!-- 					<img src="../img/lanse.png" alt="蓝色三角"> -->
			<!-- 					<b>批量设置初审专家</b> -->
			<!-- 				</p> -->
			<!-- 			</a> -->
			<!-- 			<a class="btn btnMyself"  onclick="addProjectApplyall(this)" data-url="../admin/projectApplyOpt_initExpertAllPage.action?evaluateType=2"> -->
			<%-- 				<span><img src="../img/zhongshenzhuanjia.png" alt="icon"></span> --%>
			<!-- 				<p> -->
			<!-- 					<img src="../img/lanse.png" alt="蓝色三角"> -->
			<!-- 					<b>批量设置终审专家</b> -->
			<!-- 				</p> -->
			<!-- 			</a> -->
			<%-- 			</s:if> --%>
		</div>
		<div class="cui-menu2" style="position: relative">

			<form id="reprtTask" class="form-horizontal" role="form"
				action="../admin/projectApply_output.action"
				style="position: relative; display: none;">
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">年份</label>
							<div class="col-sm-9">
								<select class="form-control" name="year" id='year'
									onchange="changeYear(this)">
									<option value="0">全部</option>
									<s:iterator value="searchYear" id="yt">
										<option value='<s:property value="#yt" />'
											<s:if test="#yt==selectYear"> selected </s:if>><s:property
												value="#yt" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目名称</label>
							<div class="col-sm-9" id="projectNames">
								<select class="form-control" name="projectName" id="projectName"
									onchange="changeProject(this)">
									<option value="0" search='全部'>全部</option>
									<s:iterator value="searchReportTask" id="rt">
										<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />'
											<s:if test="#rt.getId()==selectProjectId"> selected </s:if>>
											<s:property value="#rt.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

					</div>
					<div class="col-md-6">
						<!-- 					<div class="form-group"> -->
						<!-- 						<label class="col-sm-2 control-label" for="">项目类型</label> -->
						<!-- 						<div class="col-sm-9"> -->
						<%-- 							<select class="form-control" name="projectType" id="projectType" onchange="changeProjectType(this)"> --%>
						<!-- 								<option value="0">全部</option> -->
						<%-- 								<s:iterator value="searchProjectType" id="ptt"> --%>
						<%-- 									<option value='<s:property value="#ptt.getId()" />' <s:if test="#ptt.getId()==selectProjectTypeId"> selected </s:if>> --%>
						<%-- 									<s:property value="#ptt.getName()" /></option> --%>
						<%-- 								</s:iterator> --%>
						<%-- 							</select> --%>
						<!-- 						</div> -->
						<!-- 					</div> -->
						<div class="control-group" id="categoryDiv"
							style="margin-left: -17px;">
							<label class="col-sm-2 control-label" for="">项目类型</label>
							<div class="controls" style="margin-left: 115px">
								<div class="companylocation"
									style="z-index: 1; width: 87%; height: 30px; padding: 0">
									<span class="clId clid"
										style="height: 30px; border-radius: 4px; line-height: 30px; padding-left: 8px;"
										id="calId" onclick="changeToggle()" name="0">全部</span>
									<div id="calListBox" class="listCate" style="margin-top: 30px">
										<div id="calList" class="list_sub sm_icon">
											<div id="cabido"></div>
											<div id="menuTree" class="menuTree">
												<div onclick="getName(this)">
													<span> <a name="0">全部</a>
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<input type="hidden" id="category" name="category" value="">
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label" for="">培训科目</label>
							<div class="col-sm-9">
								<select class="form-control" id="subjectName" name="subjectName">
									<option value="0">全部</option>
									<s:iterator value="searchSubject" id="e">
										<option value="<s:property value="#e.id" />"
											search="<s:property value="#e.searchString" />"
											<s:if test="%{#e.id==selectSubjectId}" >selected</s:if>>
											<s:property value="#e.name" />
										</option>
									</s:iterator>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row actionbar">
					<div class="text-center" style="padding-bottom: 10px">
						<button class="btn btn-primary btn-myself" id="findRecord"
							type="button">查询</button>
						<button class="btn btn-default btn-myself" id="" type="submit">导出</button>
					</div>
				</div>
			</form>

			<div class="stateDiv">
				<label>状态&nbsp;：</label> <a class="" value="-1">全部<span
					id="span1">(0)</span></a> <a class="" value="1">未审核<span id="span2">(0)</span></a>
				<a class="" value="2">已通过<span id="span3">(0)</span></a> <a class=""
					value="0">未通过<span id="span4">(0)</span></a>
			</div>
		</div>
		<div class="sorting clearfix"
			style="margin-top: 10px; position: relative;">
			<div class="checkall-btn">
				<input onchange="toCheckAll(this)" type="checkbox" name="checkall">
			</div>
			<div class="order-option">排序 :</div>

			<ul class="sorting-btns">
				<li id="sorting-year0"><a data-name="year0"><span>年份</span></a></li>
				<li id="sorting-name0" class=""><a data-name="name0"><span>名称</span></a></li>
				<li id="sorting-status" class=""><a data-name="status"><span>状态</span></a></li>
				<li id="sorting-creattime" class=""><a data-name="creattime"><span>创建时间</span></a></li>

			</ul>
			<div class="expor">
				<a class="Preliminaryexpert" onclick="addProjectApplyall(this)"
					data-url="../admin/projectApplyOpt_initExpertAllPage.action?evaluateType=1">批量设置初审专家</a>
				<a class="Finalexpert" onclick="addProjectApplyall(this)"
					data-url="../admin/projectApplyOpt_initExpertAllPage.action?evaluateType=2">批量设置终审专家</a>
			</div>

		</div>


		<div id="list-content" class="list-content clearfix">


			<s:iterator value="projectApplyHash" id="pros">
				<div id="<s:property value="key" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="12%"><input class="listcheck"
										onchange="toCheck(this)" type="checkbox" name="traininfo"
										value="<s:property value="key" />"> <span
										class="text-primary">ID：</span> <s:property value="key" /></td>
									<td width="33%"><span class="text-primary">名称：</span><span><s:property
												value="value[0]" /> <s:if test="value[21]==2">
												<font color=red>(自主报名)</font>
											</s:if></span></td>
									<td width="22%"><span class="text-primary">学科：</span> <s:property
											value="value[1]" /></td>
									<td width="18%"><span class="text-primary">申报单位：</span> <s:property
											value="value[2]" /></td>
									<td width="15%" align="center">
										<%-- 									<a href="../admin/projectApplyOpt_initExpertPage.action?id=<s:property value="key" />&evaluateType=1" >添加初审专家</a>&nbsp;&nbsp; --%>
										<%-- 									<a href="../admin/projectApplyOpt_initExpertPage.action?id=<s:property value="key" />&evaluateType=2" >添加终审专家</a> <br /> --%>
										<%-- 									<a  href="../admin/projectApplyOpt_initPage.action?id=<s:property value="key" />" data-fancybox-type="iframe"  class="ifrmbox">编辑</a>&nbsp;&nbsp; --%>
										<%-- 									<a class="ProjectApplyDelete"  href="javascript:void(0)" data-url="../admin/projectApplyOpt_delete.action?id=<s:property value="key" />">删除</a>&nbsp;&nbsp;  --%>
										<a
										href="../admin/projectApplyOpt_initPassPage.action?id=<s:property value="key" />"
										data-fancybox-type="iframe" class="ifrmbox">审核</a>&nbsp;&nbsp;
										<div href="javascript:void(0)" class="clickMore">
											更多<span class="caret"></span>
											<div>
												<img src="../img/artboard_03.png" alt="三角形">
												<ul class="More">
													<s:if test="isAdd == 1">
														<li><a
															href="../admin/projectApplyOpt_initExpertPage.action?id=<s:property value="key" />&evaluateType=1">添加初审专家</a></li>
														<li><a
															href="../admin/projectApplyOpt_initExpertPage.action?id=<s:property value="key" />&evaluateType=2">添加终审专家</a></li>
													</s:if>
													<li><a class="ProjectApplyDelete"
														href="javascript:void(0)"
														data-url="../admin/projectApplyOpt_delete.action?id=<s:property value="key" />">删除</a></li>
													<li><a
														href="../admin/projectApplyOpt_initPage.action?id=<s:property value="key" />"
														data-fancybox-type="iframe" class="ifrmbox">编辑</a></li>
												</ul>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix" style="position: relative">
						<div class="list-item-col list-5-2">
							<ul>
								<li><span class="text-primary">项目年份：</span> <s:property
										value="value[3]" /></li>
								<li><span class="text-primary">项目类型：</span>
									<div onmouseout="this.className='longtxt2'"
										onmouseover="this.className='longtxt2 longtxt2_hover'"
										class="longtxt2">
										<s:property value="value[4]" />
									</div></li>
								<li><span class="text-primary">所属地区：</span> <s:property
										value="value[5]" /></li>
								<li><span class="text-primary">培训时间：</span> <s:property
										value="value[7]" /> 至 <s:property value="value[8]" /></li>
								<%-- 								<li><span class="text-primary">培训结束时间： </span></li> --%>
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
<%-- 								<s:if test="%{value[24]==1 || value[24]==3}"> --%>
<%-- 									<li><span class="text-primary">集中培训课时：</span> <s:property --%>
<%-- 											value="value[9]" /></li> --%>
<%-- 								</s:if> --%>
<%-- 								<s:if test="%{value[24]==2 || value[24]==3}"> --%>
<%-- 									<li><span class="text-primary">远程培训课时：</span> <s:property --%>
<%-- 											value="value[23]" /></li> --%>
<%-- 								</s:if> --%>
								<li><span class="text-primary">培训人数：</span> <s:property
										value="value[10]" /></li>
								<li><span class="text-primary">培训学时类型：</span>
									<s:iterator value="studyhourMap[key]" id="studyhours" status="index">
										<s:if test="value>0">
											<span class="text-primary"><s:property value="nameCN" /></span>
											<s:if test="#index.index % 2 == 0">
												<br>
											</s:if>
										</s:if>
									</s:iterator>
								</li>
	
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">培训形式：</span> <s:property
										value="value[6]" /></li>
								<li><span class="text-primary">专家初审评分：</span> <s:property
										value="value[15]" /> <a href="javascript:void(0)"
									onclick="scorelist(this)"
									data-url="../admin/projectApply_getExpertViewList.action?paid=<s:property value="key" />">详细</a>
								</li>
								<%-- 								<li><span class="text-primary">申报状态：</span> <s:property --%>
								<%-- 										value="value[16]" /></li> --%>
								<li><span class="text-primary">审核人：</span> <s:property
										value="value[17]" /></li>
								<li><span class="text-primary">审核时间：</span>
									<div onmouseout="this.className='longtxt'"
										onmouseover="this.className='longtxt longtxt_hover'"
										class="longtxt">
										<s:property value="value[18]" />
									</div></li>
								
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">提交申报：</span> <s:property
										value="value[11]" /> <s:if test='%{value[20]!=null}'>
										<a href="..<s:property value="value[20]" />">申报书下载</a>
									</s:if></li>
								<%-- 								<li><span class="text-primary">联系人：</span> <s:property --%>
								<%-- 										value="value[12]" /></li> --%>
								<%-- 								<li><span class="text-primary">联系电话：</span> <s:property --%>
								<%-- 										value="value[13]" /></li> --%>
								<li><span class="text-primary">申报时间：</span>
									<div onmouseout="this.className='longtxt'"
										onmouseover="this.className='longtxt longtxt_hover'"
										class="longtxt">
										<s:property value="value[14]" />
									</div></li>
								<s:if test="value[21]==2">
									<li><span class="text-primary">报名截止日期：</span> <s:property
											value="value[22]" /></li>
								</s:if>
							</ul>
						</div>

						<div
							class="chuo<s:property
										value="value[16]" /> statusDiv"></div>
					</div>
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float: right;" class="pull-right"></div>

	</div>

</div>
<div class="scorelist left">
	<div class="arrow"></div>
	<div class="bd" id="scorelistcnt"></div>
</div>
<script>
	function changeToggle() {
		$("#menuTree").toggle();
	}

	var str = "<div onclick='getname(this)'><span><a name='0'>全部</a></span></div>";
	function getTree() {
		$.getJSON('../admin/projectBase_getProjectTypeTreeList.action',
				function(r) {
					forTree(r.records);
				})
	}
	function getname(a, e) {
		window.event ? window.event.cancelBubble = true : e.stopPropagation();
		$("#calId").html($(a).find("a").html());
		$("#calId").attr("name", $(a).find("a").attr("name"));
	}
	function forTree(o, l) {

		for (var i = 0; i < o.length; i++) {
			var urlstr = "";

			try {
				if (typeof o[i]["id"] == "undefined") {
					urlstr = "<div onclick='getname(this)'><span>"
							+ o[i]["name"] + "</span><ul>";
				} else {
					urlstr = "<div onclick='getname(this)'><span class='span"+l+"'><a name="+ o[i]["id"] +">"
							+ o[i]["name"] + "</a></span><ul>";
					for (var j = 0; j < o.length; j++) {
						if (o[j]["id"] == projectType) {
							$("#calId").html(o[j]["name"]);
							break;
						} else {
							$("#calId").html("全部");

						}
					}
				}
				str += urlstr;
				if (o[i]["records"] != null) {
					forTree(o[i]["records"], "2");
				}
				str += "</ul></div>";
			} catch (e) {
			}
		}
		$("#menuTree").html(str);
		var menuTree = function() {
			//给有子对象的元素加[+-]
			$("#menuTree ul").each(function(index, element) {
				var ulContent = $(element).html();
				var spanContent = $(element).siblings("span").html();
				if (ulContent) {
					$(element).siblings("span").html("[+] " + spanContent)
				}
			});
			$("#menuTree").find("div span").click(function() {
				var ul = $(this).siblings("ul");
				var spanStr = $(this).html();
				var spanContent = spanStr.substr(3, spanStr.length);
				if (ul.find("div").html() != null) {
					if (ul.css("display") == "none") {
						ul.show(300);
						$(this).html("[-] " + spanContent);
					} else {
						ul.hide(300);
						$(this).html("[+] " + spanContent);
					}
				}
			})
		}()
	}

	$(".btn-screen").click(function(e) {
		e.preventDefault();
		$('#reprtTask').toggle();
	})
	$('#subjectName').select2();
	$('#projectNames select').select2(); 
	$('#findRecord')
			.click(
					function(e) {
						var year = $('[name="year"]').val();
						var projectType = $('#calId').attr("name");
						var projectName = $('select[name="projectName"]').val();
						var subjectName = $('select[name="subjectName"]').val();
						var status = $('.stateDiv a.light').attr("value");
						window.location.href = "../admin/projectApply_initPage.action?year="
								+ year
								+ "&projectType="
								+ projectType
								+ "&projectName="
								+ projectName
								+ "&subjectName="
								+ subjectName
								+ "&status="
								+ status;
					});
	$(function() {
		$(".longtxt2").css("width",
				$(".list-item-bd .list-5-2 li").width() - 80 + "px");
		$(".longtxt2_hover").css("width",
				$(".list-item-bd .list-5-2 li").width() - 50 + "px");
		$(".longtxt").css("width",
				$(".list-item-bd .list-5-08 li").width() - 90 + "px");
		$(".longtxt_hover").css("width",
				$(".list-item-bd .list-5-08 li").width() - 50 + "px");
		$(window).resize(
				function() {
					$(".longtxt2")
							.css(
									"width",
									$(".list-item-bd .list-5-2 li").width()
											- 80 + "px");
					$(".longtxt2_hover")
							.css(
									"width",
									$(".list-item-bd .list-5-2 li").width()
											- 50 + "px");
					$(".longtxt").css(
							"width",
							$(".list-item-bd .list-5-08 li").width() - 90
									+ "px");
					$(".longtxt_hover").css(
							"width",
							$(".list-item-bd .list-5-08 li").width() - 50
									+ "px");
				})
		$(".stateDiv a").each(function(index, val) {
			if ($(this).attr("value") == projectStatus) {
				$(this).addClass("light").siblings().removeClass("light");
			}
			if ($('.stateDiv a.light').attr("value") == "1") {
				$(".expor a.Finalexpert").css("display", "none");
				$(".expor a.Preliminaryexpert").css("display", "block");
			} else if ($('.stateDiv a.light').attr("value") == "2") {
				$(".expor a.Finalexpert").css("display", "block");
				$(".expor a.Preliminaryexpert").css("display", "none");
			} else {
				$(".expor a.Finalexpert").css("display", "none");
				$(".expor a.Preliminaryexpert").css("display", "none");
			}
		})
		$(".stateDiv a")
				.click(
						function() {
							$(this).addClass("light").siblings().removeClass(
									"light");
							var year = $('[name="year"]').val();
							var projectType = $('#calId').attr("name");
							var projectName = $('select[name="projectName"]')
									.val();
							var subjectName = $('select[name="subjectName"]')
									.val();
							var status = $('.stateDiv a.light').attr("value");
							window.location.href = "../admin/projectApply_initPage.action?year="
									+ year
									+ "&projectType="
									+ projectType
									+ "&projectName="
									+ projectName
									+ "&subjectName="
									+ subjectName
									+ "&status=" + status;
						})
	})
	function changeYear(t) {
		var year = $(t).val();
		var projectType = $('#calId').attr("name");
		$.getJSON('../admin/projectApply_getAssignTaskProject.action?year='
				+ year + '&projectType=' + projectType, function(data) {
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '" search="'+c.name+'">' + c.name
							+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据</option>');
			}
			$('#subjectName').html('<option value="0">全部</option>');
		})
	}
	// 	function changeProjectType(t){
	// 		var projectType = $(t).val();
	// 		var year = $('#year').val();
	// 		$.getJSON('../admin/projectApply_getAssignTaskProject.action?projectType='+projectType+'&year='+year, function(data){
	// 			if (data.projects.length > 0) {
	// 				var cLis = '';
	// 				$.each(data.projects, function(i, c) {
	// 					cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
	// 				});
	// 				$('#projectName').html(cLis);
	// 			} else {
	// 				$('#projectName').html('<option value="0">暂无数据</option>');
	// 			}
	// 			$('#subjectName').html('<option value="0">全部</option>');
	// 		})
	// 	}

	function changeProject(t) {
		var vid = $(t).val();
		$
				.getJSON(
						'../admin/ttRecord_getAssignTaskSubject.action?projectId='
								+ vid,
						function(data) {

							if (data.subjects.length > 0) {
								var cLis = '';
								$
										.each(
												data.subjects,
												function(i, c) {
													cLis += '<option value="' +c.id + '" ' + 'search="'+ c.search +'">'
															+ c.name
															+ '</option>';
												});
								$('#subjectName').html(cLis);
							} else {
								$('#subjectName').html(
										'<option value="0">暂无数据</option>');
							}

// 							if (data.trainingUnits.length > 0) {
// 								var cLis = '';
// 								$.each(data.trainingUnits, function(i, c) {
// 									cLis += '<option value="' +c.id + '">'
// 											+ c.name + '</option>';
// 								});
// 								$('#trainingUnit').html(cLis);
// 							} else {
// 								$('#trainingUnit').html(
// 										'<option value="0">暂无数据</option>');
// 							}

						});
	}

	$(function() {//添加按钮
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "860px",
			height : "600px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true

		});
		$(document).on("click", function(e) {
			if (!$(e.target).parents().is('.scorelist'))
				$('.scorelist').hide();
			if (!$(e.target).parents().is('#categoryDiv'))
				$('#menuTree').hide();
		});
		getTree();

		$('.ProjectApplyDelete').click(function() {
			var url = $(this).attr('data-url');
			if (confirm('你确定要删除吗？')) {
				$.get(url, function(r) {
					if (r.Result == 'OK') {
						window.location.href = window.location.href;
					} else {
						alert(r.Message);
					}
				})
			}

		});

	})

	var page = url('?page');
	var year = url('?year');
	var projectName = url('?projectName');
	var projectType = url('?projectType');
	var subjectName = url('?subjectName');
	var projectStatus = (url('?status') != null) ? url('?status') : '-1';
	$.getJSON('../admin/projectApply_getPageJson.action?', {
		page : page,
		year : year,
		projectName : projectName,
		projectType : projectType,
		subjectName : subjectName,
		status : projectStatus
	}, function(r) {
		var options = {
			currentPage : r.currentPage,
			totalPages : r.totalPage,
			shouldShowPage : function(type, page, current) {
				switch (type) {
				default:
					return true;
				}
			},
			onPageClicked : function(e, originalEvent, type, page) {
				window.location = updateURLParameter(url(), 'page', page);
			}

		}
		$('#pagination').bootstrapPaginator(options);
	})
	$.getJSON('../admin/projectApply_getStatusCount.action?', {
		page : page,
		year : year,
		projectName : projectName,
		projectType : projectType,
		subjectName : subjectName,
		status : projectStatus
	}, function(r) {
		$("#span1").html("(" + r.totalCount + ")");
		$("#span2").html("(" + r.noCheck + ")");
		$("#span3").html("(" + r.checkPass + ")");
		$("#span4").html("(" + r.checkNoPass + ")");
	})
	$(function() {
		var sort = (url('?sort')) ? url('?sort') : 'year0-asc';
		var _ = sort.split('-');
		$('#sorting-' + _[0]).addClass('crt');
		$('#sorting-' + _[0]).find('span').addClass(_[1]);
		$('.sorting-btns a').click(function() {
			var name = $(this).attr('data-name');
			sortasc(name);
			return false;
		})
	})

	function sortasc(name) {
		var subjectName = (url('?subjectName') != null) ? url('?subjectName')
				: '0';
		var year = (url('?year') != null) ? url('?year') : '0';
		var status = (url('?status') != null) ? url('?status') : '-1';
		var projectType = (url('?projectType') != null) ? url('?projectType')
				: '0';
		var projectName = (url('?projectName') != null) ? url('?projectName')
				: '0';
		var qqq = (url('?sort') != null) ? url('?sort') : 'year-asc';
		var order = (qqq.split('-')[1] == 'asc') ? 'desc' : 'asc';
		window.location = url('protocol') + '://' + url('hostname')
				+ url('path') + '?subjectName=' + subjectName + '&year=' + year
				+ "&projectName=" + projectName + "&projectType=" + projectType
				+ "&status=" + status + '&sort=' + name + '-' + order;
	}
</script>


<jsp:include page="foot.jsp"></jsp:include>