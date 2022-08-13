<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目信息管理</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<style>
.btn-lg, .btn-group-lg > .btn{padding:0;}
.btn-default:hover, .btn-default:focus, .btn-default:active, .btn-default.active, .open .dropdown-toggle.btn-default{}
.btn:active, .btn.active{box-shadow:none;}
.companylocation{-webkit-box-shadow:inset 0 1px 1px rgba(0, 0, 0, .075);box-shadow:inset 0 1px 1px rgba(0, 0, 0, .075);background:#fff url("../img/city_select_arrow.png") no-repeat 98% 50%}
.companylocation:focus{border-color:#66afe9;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);box-shadow:inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);}
.list-item{position:relative;}
</style>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">项目信息管理 &nbsp;&nbsp;&nbsp;<a href="../admin/projectBase_initPage.action?pageType=easy">简明页面</a></div>
		<div class="iconDiv">
			<a class="btn btn-lg btnMyself btn-screen">
				<span><img src="../img/sousuo.png" alt="icon">
				<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选项目</b>
				</p>
			</a>
			<a class="btn btn-lg btnMyself" href="../admin/projectBaseOpt_initPage.action" data-fancybox-type="iframe">
				<span><img src="../img/kaishexiangmu.png" alt="icon">
					<b>开设</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>开设项目</b>
				</p>
			</a>
			<s:if test="isAdd == 1">
				<a class="btn btn-lg btnMyself btn-create" href="../admin/projectBase_addStageProjectInit.action" data-fancybox-type="iframe">
					<span><img src="../img/tianjiaduojieduanxiangmu.png" alt="icon">
						<b>多阶段</b>
					</span>
					<p>
						<img src="../img/lanse.png" alt="蓝色三角">
						<b>添加多阶段项目</b>
					</p>
				</a>
			</s:if>
		</div>
		<div class="cui-menu2" style="position:relative">
			<div>
				<form id="reprtTask" class="form-horizontal" role="form" action="../admin/projectBase_output.action" style="position:relative;display:none;">
					<div class="row clearfix">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-2 control-label" for="">年份</label>
								<div class="col-sm-9">
									<select id="year" class="form-control" name="year">
										<option value="0">全部</option>
										<s:iterator value="searchYear" id="yt">
											<option value='<s:property value="#yt" />' <s:if test="#yt==selectYear"> selected </s:if> ><s:property
													value="#yt" /></option>
										</s:iterator>
									</select>
								</div>
							</div>
							
						</div>
						
<!-- 						<div class="col-md-6"> -->
<!-- 							<label class="col-sm-2 control-label" for="">项目类型</label> -->
<!-- 								<div class="col-sm-9"> -->
<%-- 									<select id="projectType" class="form-control" name="projectType" id="projectType"> --%>
<!-- 										<option value="0">全部</option> -->
<%-- 										<s:iterator value="searchProjectType" id="rt"> --%>
<%-- 											<option value='<s:property value="#rt.getId()" />' <s:if test="#rt.getId()==selectProjectType"> selected </s:if> ><s:property --%>
<%-- 													value="#rt.getName()" /></option> --%>
<%-- 										</s:iterator> --%>
<%-- 									</select> --%>
<!-- 								</div> -->
<!-- 						</div> -->
						<div class="col-md-6 control-group" id="categoryDiv">
							<label class="col-sm-2 control-label" for="">项目类型</label>
							<div class="controls" style="margin-left:115px">
								<div class="companylocation" style="z-index: 1;width:87%;height:30px;padding:0;border-radius:4px;" >
									<span class="clId clid" style="height: 30px;border-radius:4px;line-height:30px;padding-left:8px;" id="calId"
										onclick="changeToggle()" name="0">全部</span>
									<div id="calListBox" class="listCate" style="margin-top:30px">
										<div id="calList" class="list_sub sm_icon">
											<div id="cabido"></div>
											<div id="menuTree" class="menuTree">
												<div onclick="getName(this)">
													<span>
														<a name="0">全部</a>
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<input type="hidden" id="category" name="category" value="">
							</div>
						</div>
						
						
						<div style="clear:both"></div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-2 control-label" for="">项目名称</label>
								<div class="col-sm-9">
									<input type="hidden" id="type_title" name="stype" value="name" checked="checked">
									<input class="form-control" type="text" name="q" id="projectName">
								</div>
							</div>
						</div>
					</div>
					<div style="clear:both"></div>
					<div class="row actionbar">
						<div class="text-center" style="padding-bottom:10px">
							<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
							<button class="btn btn-default btn-myself" id="" type="submit">导出</button>
						</div>
					</div>
				</form>
			</div>
			<div class="stateDiv">
				<label>状态 ：</label>
				<a class="" value="0">全部<span id="span1">(0)</span></a>
				<a class="" value="1">未发布<span id="span2">(0)</span></a>
				<a class="" value="2">已发布<span id="span3">(0)</span></a>
				<a class="" value="4">已关闭<span id="span4">(0)</span></a>
			</div>
		</div>

		<div class="sorting clearfix">
			<div class="checkall-btn">
				<input onchange="toCheckAll(this)" type="checkbox" name="checkall">
			</div>
			<div class="order-option">排序 :</div>

			<ul class="sorting-btns">
				<li id="sorting-year"><a data-name="year"><span>年份</span></a></li>
				<li id="sorting-name" class=""><a data-name="name"><span>名称</span></a></li>
				<li id="sorting-status" class=""><a data-name="status"><span>状态</span></a></li>
				<li id="sorting-funds" class=""><a data-name="funds"><span>经费标准</span></a></li>
				<li id="sorting-creator" class=""><a data-name="creator"><span>创建人</span></a></li>
				<li id="sorting-creattime" class=""><a data-name="creattime"><span>创建时间</span></a></li>

			</ul>

		</div>


		<div id="list-content" class="list-content clearfix">


			<s:iterator value="projectHash" id="pros">
				<div id="<s:property value="key" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="150px"><input class="listcheck"
										onchange="toCheck(this)" type="checkbox" name="traininfo"
										value=""> <span class="text-primary">ID：</span><s:property value="key" /></td>
									<td width="auto"><span class="text-primary list-title">名称：</span><span><s:property
												value="value[0]" /><s:if test="value[21] != 0"><font color="green">(第<s:property value='value[21]'/>期)</font></s:if><s:if test="value[18]==2" ><font color=red>(自主报名)</font></s:if></span></td>
									
									<td align="center" width="280px">&nbsp;&nbsp; 
										<s:if test="isCheck == 1"><a class="releasebtn" data-acttype='release' data-id ="<s:property value="key" />" href="javascript:void(0)">发布</a>&nbsp;&nbsp; </s:if>
										<a class="" onclick="editButton('<s:property value="key" />')" href="javascript:void(0)">编辑</a>&nbsp;&nbsp; 

										<div href="javascript:void(0)" class="clickMore">更多<span class="caret"></span>
											<div>
												<img src="../img/artboard_03.png" alt="三角形">
												<ul class="More">
													<li><a class="bigifrmbox"  href="../admin/projectBaseOpt_authorityPage.action?id=<s:property value="key" />" data-fancybox-type="iframe">查看权限</a></li>
													<li><s:if test="value[21] != 0">
														<a class="ifrmbox"  href="../admin/projectBase_updateStageProjectInit.action?id=<s:property value="key" />" data-fancybox-type="iframe">继承</a>&nbsp;&nbsp; 
														</s:if></li>
													<li><a class="releasebtn" data-acttype='close' data-id ="<s:property value="key" />" href="javascript:void(0)">关闭</a></li>
													<li><a href="javascript:void(0)" class="delbtn" data-url="../admin/projectBase_deleteProject.action" data-id ="<s:property value="key" />">删除</a></li>
												</ul>
											</div>
										</div>	
									</td>
										
								</tr>
							</tbody>
						</table>
					</div>
					<s:if test="isCheck == 1">
					<div class="list-item-bd clearfix">
						<div class="list-item-col list-5-2">
							<ul>
								<li><span class="text-primary">开设年份：</span> <s:property
										value="value[2]" /></li>
								<li><span class="text-primary">项目类型：</span> 
								<div onmouseout="this.className='longtxt2'" onmouseover="this.className='longtxt2 longtxt2_hover'" class="longtxt2">
								<s:property
										value="value[3]" /></div></li>
								<li><span class="text-primary">所属地区：</span> <s:property
										value="value[5]" /></li>
								<li><span class="text-primary">项目级别：</span> <s:property
										value="value[13]" /></li>										

							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">申报截止日期：</span> <s:property
										value="value[6]" /></li>
								<li><span class="text-primary">经费标准：</span> <s:property
										value="value[7]" /> <s:property
										value="value[20]" /></li>
								<li><span class="text-primary">计划培训人数：</span> <s:property
										value="value[8]" /></li>
								<li><span class="text-primary">最大申报学科：</span> <s:property
										value="value[9]" /></li>

							</ul>
						</div>
						<div class="list-item-col list-5-05">
							<ul>
								<li><span class="text-primary">招标类型：</span> <s:property
										value="value[10]" /></li>
								<li><span class="text-primary">学科范围：</span> <s:property
										value="value[11]" /></li>
								<li><span class="text-primary">培训方式：</span> <s:property
										value="value[12]" /></li>
								<li><span class="text-primary">评审指标：</span> <s:property
										value="value[15]" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">红头文件：</span> <s:property
										value="value[19]" /></li>
								<li><span class="text-primary">申报模板：</span> <s:property
										value="value[14]" /></li>
								<li><span class="text-primary">&nbsp;&nbsp;&nbsp;
										创建人：</span> <s:property value="value[16]" /></li>
								<li><span class="text-primary">创建时间：</span> <s:property
										value="value[17]" /></li>

							</ul>
						</div>
						
					</div>
					</s:if>
					<s:else>
						<div class="list-item-bd clearfix">
							<div class="list-item-col list-5-2">
								<ul>
									<li><span class="text-primary">开设年份：</span> <s:property
											value="value[2]" /></li>
									<li><span class="text-primary">项目类型：</span> 
									<div onmouseout="this.className='longtxt2'" onmouseover="this.className='longtxt2 longtxt2_hover'" class="longtxt2">
									<s:property
											value="value[3]" /></div></li>
									<li><span class="text-primary">所属地区：</span> <s:property
											value="value[5]" /></li>
									<li><span class="text-primary">项目级别：</span> <s:property
											value="value[13]" /></li>										
	
								</ul>
							</div>
							<div class="list-item-col list-5-08">
								<ul>
									<li><span class="text-primary">申报截止日期：</span> <s:property
											value="value[6]" /></li>
<%-- 									<li><span class="text-primary">经费标准：</span> <s:property --%>
<%-- 											value="value[7]" /> <s:property --%>
<%-- 											value="value[20]" /></li> --%>
									<li><span class="text-primary">计划培训人数：</span> <s:property
											value="value[8]" /></li>
<%-- 									<li><span class="text-primary">最大申报学科：</span> <s:property --%>
<%-- 											value="value[9]" /></li> --%>
									<li><span class="text-primary">培训方式：</span> <s:property
											value="value[12]" /></li>
									<li><span class="text-primary">评审指标：</span> <s:property
											value="value[15]" /></li>
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
<%-- 									<li><span class="text-primary">招标类型：</span> <s:property --%>
<%-- 											value="value[10]" /></li> --%>
<%-- 									<li><span class="text-primary">学科范围：</span> <s:property --%>
<%-- 											value="value[11]" /></li> --%>
									<li><span class="text-primary">&nbsp;&nbsp;&nbsp;
											创建人：</span> <s:property value="value[16]" /></li>
									<li><span class="text-primary">创建时间：</span> <s:property
											value="value[17]" /></li>
								</ul>
							</div>
							<div class="list-item-col list-5-1">
								<ul>
<%-- 									<li><span class="text-primary">红头文件：</span> <s:property --%>
<%-- 											value="value[19]" /></li> --%>
<%-- 									<li><span class="text-primary">申报模板：</span> <s:property --%>
<%-- 											value="value[14]" /></li> --%>
									
	
								</ul>
							</div>
	
						</div>
					</s:else>
					<div  class="base<s:property value="value[1]" /> statusDiv"></div>
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>


	</div>

</div>
<script>
function changeToggle(){
	$("#menuTree").toggle();
}

var str = "<div onclick='getname(this)'><span><a name='0'>全部</a></span></div>";
function getTree(){
	$.getJSON('../admin/projectBase_getProjectTypeTreeList.action',function(r){
		forTree(r.records);
	})
}
function getname(a,e){
	window.event? window.event.cancelBubble = true : e.stopPropagation();
	$("#calId").html($(a).find("a").html());
	$("#calId").attr("name",$(a).find("a").attr("name"));
}
function forTree(o,l){
		
		for(var i=0;i<o.length;i++){		 
			var urlstr = "";
			
				try {
					if (typeof o[i]["id"] == "undefined") {
						urlstr = "<div onclick='getname(this)'><span>" + o[i]["name"]
								+ "</span><ul>";
					} else {
						urlstr = "<div onclick='getname(this)'><span class='span"+l+"'><a name="+ o[i]["id"] +">"
								+ o[i]["name"]
								+ "</a></span><ul>";
						for(var j=0;j<o.length;j++){
							if(o[j]["id"]==projectType){
								$("#calId").html(o[j]["name"]);	
								break;
							}else{
								$("#calId").html("全部");
								
							}
						}
					}
					str += urlstr;
					if (o[i]["records"] != null) {
						forTree(o[i]["records"],"2");
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
	//搜索
	$('#findRecord')
			.click(
					function(e) {
						var year = $('[name="year"]').val();
						var projectType = $('#calId').attr("name");
						var status = $('.stateDiv a.light').attr("value");
						var stype = $('#type_title').val();
						var name = $('#projectName').val();
						window.location.href = "../admin/projectBase_initPage.action?year="
								+ year
								+ "&projectType="
								+ projectType
								+ "&status=" + status
								+ "&q=" + name
								+ "&stype=" + stype;
					});

	$(function() {//添加按钮
		getTree();
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "860px",
			height : "600px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
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
	})
	$(function() {//添加按钮
		$(document).on("click", function(e) {
			if (!$(e.target).parents().is('#categoryDiv'))
				$('#menuTree').hide();
		});
		$(".bigifrmbox").colorbox({
			iframe : true,
			width : "90%",
			height : "95%",
			maxWidth : '1600px',
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
	})
	var page = (url('?page') != null) ? url('?page') : '';	
	var year = (url('?year') != null) ? url('?year') : '0';
	var projectStatus = (url('?status')!= null) ? url('?status') : '0';
	var projectType = (url('?projectType')!= null) ? url('?projectType') : '0';
	var stype = (url('?stype') != null) ? url('?stype') : '';	
	var name = (url('?q') != null) ? url('?q') : '';
	$.getJSON('../admin/projectBase_getPageJson.action?', {
		page : page,
		year : year,
		projectType : projectType,
		status : projectStatus,
		stype : stype,
		q : name
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
	$.getJSON('../admin/projectBase_getStatusCount.action?', {
		page : page,
		year : year,
		projectType : projectType,
		status : projectStatus,
		stype : stype,
		q : name
	}, function(r) {
		$("#span1").html("(" + r.totalCount + ")");
		$("#span2").html("(" +r.noCheck + ")");
		$("#span3").html("(" +r.checkPass + ")");
		$("#span4").html("(" +r.checkNoPass + ")");
	})

	$(function() {
		var sort = (url('?sort')) ? url('?sort') : 'year-asc';
		var _ = sort.split('-');
		$('#sorting-' + _[0]).addClass('crt');
		$('#sorting-' + _[0]).find('span').addClass(_[1]);

	})

	
	function sortasc(name) {
		var page = (url('?page') != null) ? url('?page') : '';	
		var year = (url('?year') != null) ? url('?year') : '0';
		var status = (url('?status')!= null) ? url('?status') : '0';
		var projectType = (url('?projectType')!= null) ? url('?projectType') : '0';
		var qqq = (url('?sort') != null)? url('?sort'):'year-asc';
		var order = (qqq.split('-')[1] == 'asc') ? 'desc' : 'asc';
		var stype = (url('?stype') != null) ? url('?stype') : '0';	
		var name1 = (url('?q') != null) ? url('?q') : '';
		window.location = url('protocol') + '://' + url('hostname')
				+ url('path') + '?year='+ year+ "&projectType="+ projectType+ "&status=" + status+'&sort=' + name + '-' + order + '&stype='+stype + "&q="+name1;
	}
	$(function() {
		$('.sorting-btns a').click(function() {

			var name = $(this).attr('data-name');
			sortasc(name);
			return false;
		})

// 		var page = (url('?page') != null) ? url('?page') : '0';	
// 		var year = (url('?year') != null) ? url('?year') : '0';
// 		var status = (url('?status')!= null) ? url('?status') : '0';
// 		var projectType = (url('?projectType')!= null) ? url('?projectType') : '0';
// 		$("#sorting-year a").attr("href","../admin/projectBase_initPage.action?sort=year-asc&year="+ year+ "&projectType="+ projectType+ "&status=" + status);
// 		$("#sorting-status a").attr("href","../admin/projectBase_initPage.action?sort=status-asc&year="+ year+ "&projectType="+ projectType+ "&status=" + status);
// 		$("#sorting-name a").attr("href","../admin/projectBase_initPage.action?sort=name-asc&year="+ year+ "&projectType="+ projectType+ "&status=" + status);
// 		$("#sorting-funds a").attr("href","../admin/projectBase_initPage.action?sort=funds-asc&year="+ year+ "&projectType="+ projectType+ "&status=" + status);
// 		$("#sorting-creator a").attr("href","../admin/projectBase_initPage.action?sort=creator-asc&year="+ year+ "&projectType="+ projectType+ "&status=" + status);
// 		$("#sorting-creattime a").attr("href","../admin/projectBase_initPage.action?sort=creattime-asc&year="+ year+ "&projectType="+ projectType+ "&status=" + status);
		$(".stateDiv a").each(function(index, val) {
			if ($(this).attr("value") == projectStatus) {
				$(this).addClass("light").siblings().removeClass("light");
			}
		})
		$(".stateDiv a")
				.click(
						function() {
							$(this).addClass("light").siblings().removeClass(
									"light");
							var year = $('[name="year"]').val();
							var projectType = $('#calId').attr("name");
							var status = $('.stateDiv a.light').attr("value");
							var stype = $('#type_title').val();
							var name = (url('?q') != null) ? url('?q') : '';
							window.location.href = "../admin/projectBase_initPage.action?year="
									+ year
									+ "&projectType="
									+ projectType
									+ "&status=" + status
									+ "&q=" + name
									+ "&stype=" + stype;
						})
	})
	function editButton(id){
		var page = (url('?page') != null) ? url('?page') : '';	
		var year = (url('?year') != null) ? url('?year') : '0';
		var status = (url('?status')!= null) ? url('?status') : '0';
		var projectType = (url('?projectType')!= null) ? url('?projectType') : '0';
		var qqq = (url('?sort') != null)? url('?sort'):'year-asc';		
		var stype = (url('?stype') != null) ? url('?stype') : '0';	
		var name1 = (url('?q') != null) ? url('?q') : '';
		window.location.href='../admin/projectBaseOpt_initPage.action?id='+id+'&year='
				+ year+ "&projectType="+ projectType+ "&status=" + status+'&sort=' + qqq + '&stype='+stype + "&q="+name1+"&page="+page;
	}
</script>


<jsp:include page="foot.jsp"></jsp:include>