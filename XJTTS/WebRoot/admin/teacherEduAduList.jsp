<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师学历提升申请管理</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<script src="../js/layer-v2.2/layer/layer.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/dialog-min.js"></script>
	
<div class="main">

	<div class="listwrap">
		<div class="list_bar">教师学历提升申请审核</div>
		<div id="searchbox" class="searchbox" style="top:15px;z-index:0;">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">姓名/身份证号</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title">按姓名或身份证号</label><input type="radio" id="type_title" name="search" value="name" checked="checked">
								</li>
							</ul>
							<span class="bl"></span>
							<span class="br"></span>
						</div>
					</div>
					<button type="submit" id="searchBtn" class="search-button">Search</button>
				</fieldset>
			</form>
			
		</div>
		<div class="list-content clearfix">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			

			<div class="clearfix">
				<div class="cui-menu2 clearfix" style="margin-left:9px">
					<div class="stateDiv" style="margin-top:0;">
						<label>状态：</label>
						<a class="light" value="-2">全部<span id="span1">(0)</span></a>
						<a class="" value="-1">未审核<span id="span3">(0)</span></a>
						<a class="" value="1">初审通过<span id="span2">(0)</span></a>
						<a class="" value="2">终审通过<span id="span4">(0)</span></a>
						<a class="" value="0">未通过<span id="span5">(0)</span></a>
					</div>
					<style>
						.pull-left {
							background: none !important;
						}
					</style>
					<div class="pull-left">

					</div>
				</div>

				<div id="list-content" class="list-content clearfix"></div>

				<script id="ttRecordAduTpl" type="text/x-jsrender">
					<div id="adu_{{:id}}" class="list-item" style="position:relative;">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td width="35%" class="text-primary"><input class="listcheck" onclick="toCheck(this)" type="checkbox" name="traininfo" value="{{:id}}"> &nbsp;<span>{{:organization}}—{{:name}}</span>
										</td>
										<td width="25%">身份证号：<span class="text-primary">{{:idcard}}</span></td>
										<td width="25%">申请学历由<span class="text-primary">"{{:odlEducationBackground}}"</span>提升至<span class="text-primary">"{{:educationBackground}}"</span></td>
										<td width="15%" align="center"><a class="btn-group" href="javascript:" onclick="audlist(this)"
											data-url="../admin/teacheredu_getAduRecords.action?id={{:id}}">查看审核记录</a>
											
											{{if isAdmin<2}}
											<a class="btn-group" href="javascript:" onclick="layerIfreme('../admin/teacheredu_initAduRecord.action?id={{:id}}&isAdmin={{:isAdmin}}')">审核</a>
											{{else}}{{/if}}
										</td>								

									</tr>
								</tbody>
							</table>
						</div>

						<div class="list-item-bd clearfix">
							<div class="list-item-col list-5-05" style="width:10%;text-align:center">
								<img src="../img/u{{:sex}}.png" width="60">
							</div>
		
							<div class="list-item-col list-5-2" style="padding-top:23px;width:40%;">
								<ul>
									<li>进修学校：<span class="text-primary">{{:graduation}}</span></li>
									<li>进修专业：<span class="text-primary">{{:major}}</span></li>
								</ul>
							</div>
							<div class="list-item-col list-5-2" style="padding-top:23px;width:40%;">
								<ul>
									<li>进修时间：<span class="text-primary">{{:starttime}}</span>至<span class="text-primary">{{:endtime}}</span></li>
									<li>结业证书编号：<span class="text-primary">{{:certificate}}</span></li>
								</ul>
							</div>
							<div  class="levelFours{{:status}} statusDiv"></div>
						</div>
					</div>
			
			
				</script>



				<div id="pagination" style="float:right;" class="pull-right"></div>

			</div>
		</div>

	</div>
</div>
</div>
<div class="recordlist left">
	<div class="arrow"></div>
	<div class="bd" id="recordlistcnt"></div>
</div>
<div class="hide">
	<input type="hide" name="jobTitle_Json" value=""> <input
		type="hide" name="jobDuty_Json" value=""> <input type="hide"
		name="age_Json" value=""> <input type="hide"
		name="teachAge_Json" value=""> <input type="hide"
		name="chineseLanguageLevel_Json" value="">
</div>

<script>
	var aPage = '';
	
	var eduStatus = (url('?status')!= null) ? url('?status') : '-2';
	var search = ($('#searchheader').val()!= null) ? $('#searchheader').val() : '';
	$(function() {
		$(".stateDiv a").each(function(index, val) {
			if ($(this).attr("value") == eduStatus) {
				$(this).addClass("light").siblings().removeClass("light");
			}
		})
		if(eduStatus == null){
			eduStatus = $(".stateDiv a").attr("value");
		}
		$(".stateDiv a").click(
				function(e) {
					$(this).addClass("light").siblings().removeClass("light");
					e.preventDefault();
					eduStatus = $('.stateDiv a.light').attr("value");
// 					window.location.href = "../admin/ttRecord_searchAduPage.action?status=" + status; 
					ttrecordAdu('#aduTaskTeacher');
				})

		
		$('#searchBtn').click(function (e) {
            e.preventDefault();
            search = $('#searchheader').val();
            ttrecordAdu('#aduTaskTeacher');
            getStatusCount();
			return false;
        });
	})
	
	function ttrecordAdu(o, pp) {
		var pp = (typeof (pp) == 'undefined') ? 1 : pp;
		var str = $(o).serialize() + '&jtStartIndex=' + pp+'&status='+eduStatus+'&search='+search;
		$('#list-content').html('').addClass('loading');
		$.getJSON('../admin/teacheredu_getList.action?' + str,
				function(data) {
					if (data.Result == 'OK') {
						if(data.Records.length > 0){
							var template = $.templates('#ttRecordAduTpl');
							var html = template.render(data.Records);
							$('#list-content').html(html).removeClass('loading');

								
							var totalPages = Math.ceil((data.Totalcount/10));
							var options = {
								currentPage : pp,
								totalPages : totalPages,
								shouldShowPage : function(type, page, current) {
									switch (type) {
									default:
										return true;
									}
								},
								onPageClicked : function(e, originalEvent, type,
										page) {
									var pp = page;
									aPage = page;
									ttrecordAdu('#aduTaskTeacher', pp);
								}

							}
							$('#pagination').bootstrapPaginator(options);
						}else{
							$('#list-content').html('<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">暂无记录</p>').removeClass('loading');
						}
						
					} else {
						//$('.alert-danger').html(data.message).show();
						$('#list-content').html(
								'<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">'
										+ data.message + '</p>').removeClass(
								'loading');
						$('#pagination').html('');
					}
				}).done(function(){
					$(".ifrmbox").colorbox({
						iframe : true,
						width : "860px",
						height : "780px",
						maxWidth : '1600px',
						opacity : '0.5',
						overlayClose : false,
						escKey : true
					});
					$(".longtxt").css("width",$("#list-content").width()*0.3-90+"px");
					$(".longtxt_hover").css("width",$("#list-content").width()*0.3+50+"px");
					$(window).resize(function(){
						$(".longtxt").css("width",$("#list-content").width()*0.3-90+"px");
						$(".longtxt_hover").css("width",$("#list-content").width()*0.3+50+"px");
					})
				});
	}
	
	
	$(function() {
		
		var pp = (typeof (pp) == 'undefined') ? 1 : pp;
		var str = $("#aduTaskTeacher").serialize() + '&jtStartIndex=' + pp+'&search='+search;;
		$('#list-content').html('').addClass('loading');
		$.getJSON('../admin/teacheredu_getStatusCount.action?' + str,
				function(r) {
					$("#span1").html("(" +r.totalCount+ ")");
					$("#span3").html("(" +r.noCheck+ ")");
					$("#span2").html("(" +r.checkPass+ ")");
					$("#span4").html("(" +r.checkFPass+ ")");
					$("#span5").html("(" +r.checkNoPass+ ")");
				});
		
// 		$(".stateDiv a").click(function(e){
// 			$(this).addClass("light").siblings().removeClass("light");
// 			e.preventDefault();
// 			ttrecordAdu('#aduTaskTeacher');
// 			return false;
// 		})
		$(document).on("click", function(e) {
			if (!$(e.target).parents().is('.recordlist'))
				$('.recordlist').hide();
		});

		ttrecordAdu('#aduTaskTeacher');

	});
	
	function getStatusCount(){
		var pp = (typeof (pp) == 'undefined') ? 1 : pp;
		var str = $("#aduTaskTeacher").serialize() + '&jtStartIndex=' + pp+'&search='+search;;
		$('#list-content').html('').addClass('loading');
		$.getJSON('../admin/teacheredu_getStatusCount.action?' + str,
				function(r) {
					$("#span1").html("(" +r.totalCount+ ")");
					$("#span3").html("(" +r.noCheck+ ")");
					$("#span2").html("(" +r.checkPass+ ")");
					$("#span5").html("(" +r.checkNoPass+ ")");
				});
	}

	//学历提升审核记录
	function audlist(t) {
		var obj = $(t);
		var cUrl = obj.attr('data-url');
		var tt_offset = obj.offset();
		var tt_top = tt_offset.top;
		var tt_left = tt_offset.left;

		$.getJSON(
			cUrl,
			function(ret){
				if (ret.Result == 'OK') {
					var html = '<table class="table table-striped"><thead><tr><th width="80">序号</th><th width="160">审核时间</th><th>详细</th></tr></thead><tbody>';
					if (ret.Records.length > 0) {
						$.each(ret.Records, function(i, c) {
							html += '<tr><td><b>' + c.id + '</b></td><td>'
									+ c.time + '</td><td>' + c.info
									+ '</td></tr>';
						})
					} else {
						html += '<tr><td colspan=3>暂无审核记录</td></tr>';
					}
					html += '</tbody></table>'
					$('#recordlistcnt').html(html);
					var new_div_left = tt_left
							- $('.recordlist').outerWidth() - 16;
					var new_div_top = tt_top
							- $('.recordlist').outerHeight() / 2 + 6;
					$('.recordlist').css({
						'top' : new_div_top + 'px',
						'left' : new_div_left + 'px'
					}).toggle();

				} else {
					alert('失败,' + ret.Message);
				}
			})

	}
	//审核弹出层
	function layerIfreme(obj){
		var index=layer.open({
			  type: 2,
			  title: false,
			  shadeClose: true,
			  shade: 0.6,
			  scrollbar: false,
			  area: ['70%', '80%'],
			  content: obj 
			}); 
			$(".layui-layer-setwin .layui-layer-close2").css("display", "block");
		   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
	}
</script>

<body>
</html>