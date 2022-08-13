<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	
<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学员基本信息管理</s:param>
</s:action>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">学员基本信息管理</div>
		<div class="iconDiv">
			<a class="btn btnMyself btn-create" href="../admin/teacherManage_addInit.action?opt=add" data-fancybox-type="iframe">
				<span><img src="../img/tianjiaxueyuan.png" alt="icon">
					<b>添加</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>添加学员</b>
				</p>
			</a>
			<a class="btn btnMyself btn-search" href="../admin/teacherManage_addInit.action?opt=search&first=true" data-fancybox-type="iframe">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>搜索</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>搜索学员</b>
				</p>
			</a>

		</div>
		<div class="cui-menu2">
<!-- 			<a href="../admin/teacherManage_addInit.action?opt=add" -->
<!-- 				data-fancybox-type="iframe" class="btn btn-primary btn-create"> -->
<!-- 				+ 添加学员 </a> -->
<!-- 				<a href="../admin/teacherManage_addInit.action?opt=search&first=true" -->
<!-- 				data-fancybox-type="iframe" class="btn btn-primary btn-search"> -->
<!-- 				 <b>搜索学员</b> </a> -->
		</div>
		<div class="stateDiv" style="margin-top:0;">
			<label>状态：</label>
			<a class="light" value="0">全部<span id="span1">(0)</span></a>
			<a class="" value="1">在职<span id="span2">(0)</span></a>
			<a class="" value="2">离职<span id="span3">(0)</span></a>
			<a class="" value="3">转出<span id="span4">(0)</span></a>
			<a class="" value="4">退休<span id="span5">(0)</span></a>
			<a class="" value="5">死亡<span id="span6">(0)</span></a>
		</div>
		<div class="sorting clearfix">
			<div class="checkall-btn">
				<input onchange="toCheckAll(this)" type="checkbox" name="checkall">
			</div>
			<div class="order-option">排序 :</div>

			<ul class="sorting-btns">
				<li id="sorting-name"><a href="../admin/teacherManage_initPage.action?sort=name-asc" data-name="name"><span>姓名</span></a></li>
				<li id="sorting-sex" class=""><a href="../admin/teacherManage_initPage.action?sort=sex-asc" data-name="sex"><span>性别</span></a></li>
				<li id="sorting-ethnic" class=""><a href="../admin/teacherManage_initPage.action?sort=ethnic-asc" data-name="ethnic"><span>民族</span></a></li>
				<li id="sorting-jobDuty" class=""><a href="../admin/teacherManage_initPage.action?sort=jobDuty-asc" data-name="jobDuty"><span>职称</span></a></li>
				<li id="sorting-jobTitle" class=""><a href="../admin/teacherManage_initPage.action?sort=jobTitle-asc" data-name="jobTitle"><span>职务</span></a></li>
				<li id="sorting-teachingAge" class=""><a href="../admin/teacherManage_initPage.action?sort=teachingAge-asc" data-name="teachingAge"><span>教龄</span></a></li>
				<li id="sorting-eductionBackground" class=""><a href="../admin/teacherManage_initPage.action?sort=eductionBackground-asc" data-name="eductionBackground"><span>学历</span></a></li>
			</ul>

		</div>


		<div id="list-content" class="list-content clearfix">


			<s:iterator value="lstTeacherExs" id="tex">
				<div id="<s:property value="#tex.teacher.id" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="240px"><input class="listcheck"
										onchange="toCheck(this)" type="checkbox" name="traininfo"
										value=""> <span class="text-primary">身份证：<s:property value="#tex.teacher.idcard" /></span></td>
									<td width="200px"><span class="text-primary">姓名：</span><s:property value="#tex.teacher.name" /></td>
									<td width="auto"><span class="text-primary">所属学校：</span><span><s:property value="#tex.teacher.organization.name" /></span></td>
									<td width="150px"><span class="text-primary">是否为教学点教师：</span><span><s:if test="#tex.teacher.isTeachingSchool == 1">是</s:if><s:else>否</s:else></span></td>
									<td width="150px"><span class="text-primary">手机：</span><span><s:property value="#tex.teacher.mobile" /></span></td>
									<td width="80px" align="center"><a class="ifrmbox" href="teacherManage_addInit.action?editId=<s:property value="#tex.teacher.id" />"data-fancybox-type="iframe">编辑</a></td>
									<td width="80px" align="center"><a href="personalFile_init.action?Id=<s:property value="#tex.teacher.id" />">档案</a></td>
									<td width="80px" align="center"><a class="ifrmbox2" href="teacherManage_adjustInit.action?editId=<s:property value="#tex.teacher.id" />"data-fancybox-type="iframe">调出</a></td>
									<s:if test="#tex.teacherCertificate != null">
									<td width="150px" align="center"><a class="ifrmbox2" href="teacherManage_addCertificateInit.action?id=<s:property value="#tex.teacherCertificate.id" />&teacherId=<s:property value="#tex.teacher.id" />"data-fancybox-type="iframe">教师资格证信息</a></td>
									</s:if>
									<s:else>
									<td width="150px" align="center"><a class="ifrmbox2" href="teacherManage_addCertificateInit.action?id=&teacherId=<s:property value="#tex.teacher.id" />"data-fancybox-type="iframe">教师资格证信息</a></td>
									</s:else>
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
								<li><span class="text-primary">编制：</span> <s:property value="#tex.authorized" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-05">
							<ul>
								<li><span class="text-primary">教龄：</span> <s:property value="#tex.teachingAge" /></li>
								<li><span class="text-primary">职称：</span> <s:property value="#tex.teacher.jobTitle.name" /></li>
								<li><span class="text-primary">行政职务：</span> <s:property value="#tex.teacher.jobDuty.name" /></li>
								<li><span class="text-primary">是否双语教学：</span> <s:property value="#tex.isMultiLanguage" /></li>
								<li><span class="text-primary">毕业院校：</span> <s:property value="#tex.teacher.graduation" /></li>
								<li><span class="text-primary">所学专业：</span> <s:property value="#tex.teacher.major" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">最高学历：</span> <s:property value="#tex.teacher.eductionBackground.name" /></li>
								<li><span class="text-primary">主要教学学段：</span> <s:property value="#tex.mainTeachingClass.grade.name" /></li>
								<li><span class="text-primary">主要教学学科：</span> <s:property value="#tex.mainTeachingCourse.subject.name" /></li>
								<li><span class="text-primary">主要教学语言：</span> <s:property value="#tex.mainTeachingLanguage.language.name" /></li>
								<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt"><s:property value="#tex.areaString" /></div></li>

							</ul>
						</div>
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">汉语言水平：</span> <s:property value="#tex.teacher.chineseLanguageLevel.name" /></li>
								<li><span class="text-primary">填报人：</span> <s:property value="#tex.creator "/></li>
								<li><span class="text-primary">填报时间：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt"> <s:date  name="#tex.teacher.creattime" format="yyyy-MM-dd HH:mm:ss" /></div></li>
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
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>


	</div>

</div>
<script>
var teacherStatus = (url('?status')!= null) ? url('?status') : '0';
var opt =  (url('?opt')!= null) ? url('?opt') : '0';
	$(function() {//添加按钮
		$(".stateDiv a").each(function(index, val) {
			if ($(this).attr("value") == teacherStatus) {
				$(this).addClass("light").siblings().removeClass("light");
			}
		})
		if(teacherStatus == null){
			teacherStatus = $(".stateDiv a").attr("value");
		}
		$(".stateDiv a").click(
			function(e) {
				var urls = "../admin/teacherManage_initPage.action?";
				$(this).addClass("light").siblings().removeClass("light");
				e.preventDefault();
				teacherStatus = $('.stateDiv a.light').attr("value");
				if(opt == '1'){
					urls+='opt=1&search=&status='+ teacherStatus;
				} else {
					urls+='status=' + teacherStatus; 
				}
				window.location.href = urls; 
			})
		getNumber();
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "860px",
			height : "900px",
			maxWidth : '1600px',
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
	
		$(".ifrmbox2").colorbox({
			iframe : true,
			width : "860px",
			height : "420px",
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
	
	$.getJSON('../admin/teacherManage_getPageJson.action?', {
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
		$(".longtxt").css("width",$(".list-item-bd .list-5-1 li").width()-90+"px");
		$(".longtxt_hover").css("width",$(".list-item-bd .list-5-1 li").width()-90+"px");
		$(window).resize(function(){
			$(".longtxt").css("width",$(".list-item-bd .list-5-1 li").width()-90+"px");
			$(".longtxt_hover").css("width",$(".list-item-bd .list-5-1 li").width()-90+"px");
		})
		var sort = (url('?sort')) ? url('?sort') : 'name-asc';
		var _ = sort.split('-');
		$('#sorting-' + _[0]).addClass('crt');
		$('#sorting-' + _[0]).find('span').addClass(_[1]);

	})

	$('.sorting-btns a').click(function() {
		var name = $(this).attr('data-name');
		sortasc(name);
		return false;
	})
	function getNumber(){
		var pp = (typeof (pp) == 'undefined') ? 1 : pp;
		var str = $("#aduTaskTeacher").serialize() + '&page=' + pp;
		if(opt == '1'){
			str+='&opt=1&search=';
		}
		$.getJSON('../admin/teacherManage_getStatusCount.action?' + str,
			function(r) {
				$("#span1").html("(" +r.totalCount+ ")");
				$("#span2").html("(" +r.zaizhi+ ")");
				$("#span3").html("(" +r.lizhi+ ")");
				$("#span4").html("(" +r.zhuanchu+ ")");
				$("#span5").html("(" +r.tuixiu+ ")");
				$("#span6").html("(" +r.siwang+ ")");
// 				$('#list-content').html(html).removeClass('loading');
			});
	}
	
</script>


<jsp:include page="foot.jsp"></jsp:include>