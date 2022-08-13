<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师基本信息审核管理</s:param>
</s:action>
<div class="main">
<div class="iconDiv">
			<a class="btn btnMyself btn-screen  btn-search" href="../admin/teacherInfo_addInit.action?opt=search&first=true" data-fancybox-type="iframe">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>搜索</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>搜索教师</b>
				</p>
			</a>
		</div>
	<div class="listwrap">
		<div class="list_bar">教师基本信息列表</div>
		<div class="cui-menu2">
<!-- 				<a href="../admin/teacherInfo_addInit.action?opt=search&first=true" -->
<!-- 				data-fancybox-type="iframe" class="btn btn-primary btn-search"> -->
<!-- 				 <b>搜索教师</b> </a> -->
		</div>
	
		<div class="sorting clearfix">
			<div class="checkall-btn">
				<input onchange="toCheckAll(this)" type="checkbox" name="checkall">
			</div>
			<div class="order-option">排序 :</div>

			<ul class="sorting-btns">
				<li id="sorting-name"><a href="../admin/teacherInfo_initPage.action?sort=name-asc" data-name="name"><span>姓名</span></a></li>
				<li id="sorting-sex" class=""><a href="../admin/teacherInfo_initPage.action?sort=sex-asc" data-name="sex"><span>性别</span></a></li>
				<li id="sorting-ethnic" class=""><a href="../admin/teacherInfo_initPage.action?sort=ethnic-asc" data-name="ethnic"><span>民族</span></a></li>
				<li id="sorting-jobDuty" class=""><a href="../admin/teacherInfo_initPage.action?sort=jobDuty-asc" data-name="jobDuty"><span>职称</span></a></li>
				<li id="sorting-jobTitle" class=""><a href="../admin/teacherInfo_initPage.action?sort=jobTitle-asc" data-name="jobTitle"><span>职务</span></a></li>
				<li id="sorting-teachingAge" class=""><a href="../admin/teacherInfo_initPage.action?sort=teachingAge-asc" data-name="teachingAge"><span>教龄</span></a></li>
				<li id="sorting-eductionBackground" class=""><a href="../admin/teacherInfo_initPage.action?sort=eductionBackground-asc" data-name="eductionBackground"><span>学历</span></a></li>
				<li id="sorting-creattime" class=""><a href="../admin/teacherInfo_initPage.action?sort=creattime-asc" data-name="creattime"><span>创建时间</span></a></li>
				<li id="sorting-status" class=""><a href="../admin/teacherInfo_initPage.action?sort=status-asc" data-name="status"><span>审核状态</span></a></li>
			</ul>

		</div>


		<div id="list-content" class="list-content clearfix">


			<s:iterator value="lstTeacherExs" id="tex">
				<div id="<s:property value="#tex.teacher.id" />" class="list-item" style="position:relative;">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="240px"><input class="listcheck"
										onchange="toCheck(this)" type="checkbox" name="traininfo"
										value=""> <span class="text-primary">身份证：</span><s:property value="#tex.teacher.idcard" /></td>
									<td width="180px"><span class="text-primary">姓名：</span><s:property value="#tex.teacher.name" /></td>
									<td width="auto"><span class="text-primary">所属学校：</span><span><s:property value="#tex.teacher.organization.name" /></span></td>
									<td width="150px"><span class="text-primary">手机：</span><span><s:property value="#tex.teacher.mobile" /></span></td>
									
									<td width="60px">
										<div class="btn-group" style="margin-top:-5px;">
											<button type="button"
														style="font-size:13px;background:none;border:0;color:#4365be;padding:1px 5px 0"
														class="btn btn-xs btn-default dropdown-toggle"
														data-toggle="dropdown">审核 <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" role="menu" style="min-width:100px;">
												<li><a href="javascript:void(0);" onclick="review(this,1)" id="review" data-teacherid='<s:property value="#tex.teacher.id" />'>通过</a>
												</li>
							
												<li><a href="javascript:void(0)" onclick="showaduRecord(this)">不通过</a></li>
											</ul>
											<div class="popover bottom" style="width:160px;margin-left:-70px;top:20px;">
												<div class="arrow"></div>
												<div class="popover-content">
													<p><input type="text" name="reason" class="form-control" ></p>
													<div style="padding:10px 0 0;border-top:1px solid #e5e5e5">
														<button type="button" onclick="$(this).closest('.popover').hide()" class="btn btn-default">关闭</button>
														<button type="button"  data-teacherid='<s:property value="#tex.teacher.id" />' onclick="review(this,0)" class="btn btn-primary">不通过</button>
															
													</div>
												</div>
											</div>
										</div>
<%-- 										<a href="javascript:void(0);" onclick="review(this)" id="review" data-teacherid="<s:property value="#tex.teacher.id" />">审核</a> --%>
									</td>
									<td width="50px"><a class="ifrmbox" href="../admin/teacherInfo_addInit.action?editId=<s:property value="#tex.teacher.id" />"data-fancybox-type="iframe">编辑</a></td>
<!-- 									<td width="110px"> -->
<%-- 										<a href="javascript:void(0)" onclick="records(this)" data-teacherid='<s:property value="#tex.teacher.id" />'>报送记录</a> --%>
<!-- 									</td>		 -->
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">
						
						<div class="list-item-col list-5-05" style="width:100px;text-align:center">
							<s:if test='%{#tex.sexString=="女"}'><img src="../img/u2.png" width="80" ></s:if>
							<s:else><img src="../img/u1.png" width="80" ></s:else>
						</div>
						
						<div class="list-item-col list-5-05">
							<ul>
								<li><span class="text-primary">性别：</span><s:property value="#tex.sexString" /></li>
								<li><span class="text-primary">年龄：</span> <s:property value="#tex.ageString" /></li>
								<li><span class="text-primary">民族：</span> <s:property value="#tex.teacher.ethnic.name" /></li>
								<li><span class="text-primary">政治面貌：</span> <s:property value="#tex.teacher.politics.name" /></li>
								<li><span class="text-primary">状态：</span> <s:property value="#tex.statusString" /></li>

							</ul>
						</div>
						<div class="list-item-col list-5-05">
							<ul>
								<li><span class="text-primary">教龄：</span> <s:property value="#tex.teachingAge" /></li>
								<li><span class="text-primary">职称：</span> <s:property value="#tex.teacher. jobTitle.name" /></li>
								<li><span class="text-primary">行政职务：</span> <s:property value="#tex.teacher.jobDuty.name" /></li>
								<li><span class="text-primary">是否双语教学：</span> <s:property value="#tex.isMultiLanguage" /></li>
								<li><span class="text-primary">编制：</span> <s:property value="#tex.authorized" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">主要教学学段：</span> <s:property value="#tex.mainTeachingClass.grade.name" /></li>
								<li><span class="text-primary">主要教学学科：</span> <s:property value="#tex.mainTeachingCourse.subject.name" /></li>
								<li><span class="text-primary">主要教学语言：</span> <s:property value="#tex.mainTeachingLanguage.language.name" /></li>
								<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt"><s:property value="#tex.areaString" /></div></li>

							</ul>
						</div>
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">最高学历：</span> <s:property value="#tex.teacher.eductionBackground.name" /></li>
								<li><span class="text-primary">汉语言水平：</span> <s:property value="#tex.teacher.chineseLanguageLevel.name" /></li>
<%-- 								<li><span class="text-primary">填报人：</span> <s:if test="#tex.creator == null">本人注册</s:if><s:property value="#tex.creator "/></li> --%>
<%-- 								<li><span class="text-primary">注册时间：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt"> <s:date  name="#tex.teacher.creattime" format="yyyy-MM-dd HH:mm:ss" /></div></li> --%>
								<li><span class="text-primary">最后编辑人：</span> <s:property value="#tex.updater "/></li>
								<li><span class="text-primary">编辑时间：</span>
									<s:if test="#tex.teacher.updater != null">
										<div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt"> <s:date  name="#tex.teacher.creattime" format="yyyy-MM-dd HH:mm:ss" /></div>
									</s:if>
									<s:else>
										暂无
									</s:else>
								</li>
							</ul>
						</div>
						

					</div>
					<div  class="teacher<s:if test="#tex.teacher.status == -1">-1</s:if><s:elseif test="#tex.teacher.status == 0">0</s:elseif> statusDiv"></div>
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>


	</div>

</div>
<script>
	$(function() {//添加按钮
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "860px",
			height : "680px",
			maxWidth : '1600px',
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
		
		$(".btn-search").colorbox({
			iframe : true,
			width : "860px",
			height : "580px",
			maxWidth : '1600px',
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
	})

	var page = url('?page');
	
	$.getJSON('../admin/teacherInfo_getPageJson.action?', {
		page : page
	}, function(r) {
		var options = {
			currentPage : r.currentPage,
			totalPages : r.totalPage,
			shouldShowPage:function(type, page, current){
                switch(type)
                {
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

	$(function() {
		var sort = (url('?sort')) ? url('?sort') : 'name-asc';
		var _ = sort.split('-');
		$('#sorting-' + _[0]).addClass('crt');
		$('#sorting-' + _[0]).find('span').addClass(_[1]);
		$(".longtxt").css("width",$(".list-item-bd .list-5-1 li").width()-90+"px");
		$(".longtxt_hover").css("width",$(".list-item-bd .list-5-1 li").width()-50+"px");
		$(window).resize(function(){
			$(".longtxt").css("width",$(".list-item-bd .list-5-1 li").width()-90+"px");
			$(".longtxt_hover").css("width",$(".list-item-bd .list-5-1 li").width()-50+"px");
		})
	})

	$('.sorting-btns a').click(function() {
		var name = $(this).attr('data-name');
		sortasc(name);
		return false;
	});

// 	$('#review').click(function() {
		
// 	});
	function review(e,i){
		var status = i;
		var teacherId = e.dataset.teacherid;
		var popover=$(e).closest('.popover');
		var reason;
		if(popover != null){
			reason = popover.find('input[name="reason"]').val();
		}
		$.getJSON('../admin/teacherInfo_review.action',{teacherId : teacherId , status:status , reason:reason},function(r){
			if (r.Result == 'OK') {
				window.alert(r.Message);
				window.location.href ="../admin/teacherInfo_initPage.action";
			} else {
				window.alert(r.Message);
			}
		}).fail(function(){
			window.alert("审核失败");
		});
	}
	function showaduRecord(obj) {
		$(obj).closest('.btn-group').find('.popover').show();
	}
	
	// 审核记录
	function records(t) {
		var obj = $(t);
		var tt_offset = obj.offset();
		var tt_top = tt_offset.top;
		var tt_left = tt_offset.left;
		var teacherId = t.dataset.teacherid;
		$.getJSON(
			"../admin/teacherInfo_getTeacherReportInfomation.action?teacherId="+teacherId,
			function(ret) {
				if (ret.Result == 'OK') {
					var html = '<table class="table table-striped"><thead><tr><th width="80">序号</th><th width="160">报送时间</th><th>详细</th></tr></thead><tbody>';
					if (ret.rows.length > 0) {
						$.each(ret.rows, function(i, c) {
							html += '<tr><td><b>' + c.id + '</b></td><td>'
									+ c.time + '</td><td>' + c.info
									+ '</td></tr>';
						})
					} else {
						html += '<tr><td colspan=3>暂无报送记录</td></tr>';
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
	
</script>


<jsp:include page="foot.jsp"></jsp:include>