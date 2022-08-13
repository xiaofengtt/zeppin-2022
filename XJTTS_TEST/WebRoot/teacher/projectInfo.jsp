<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>报名项目及学科信息</title>

<link rel="stylesheet" href="../css/bootstrap.css">
<!--[if lt IE 9]>
  <script src="../js/html5shiv.js"></script>
  <script src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../css/app.css">	
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../css/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script> 
	
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<jsp:include page="left.jsp"></jsp:include>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">报名项目及学科信息</div>		
		<div class="sorting clearfix">
			<div class="order-option">排序 :</div>

			<ul class="sorting-btns">
				<li id="sorting-year0"><a
					href="../teacher/trainInfo_initPage.action?sort=year0-asc"
					data-name="year0"><span>年份</span></a></li>
				<li id="sorting-name0" class=""><a
					href="../teacher/trainInfo_initPage.action?sort=name0-asc"
					data-name="name0"><span>名称</span></a></li>
				<li id="sorting-name0" class=""><a
					href="../teacher/trainInfo_initPage.action?sort=subject-asc"
					data-name="name0"><span>学科</span></a></li>
				<li id="sorting-name0" class=""><a
					href="../teacher/trainInfo_initPage.action?sort=trainingCollege-asc"
					data-name="name0"><span>承训单位</span></a></li>
				<li id="sorting-creattime" class=""><a
					href="../teacher/trainInfo_initPage.action?sort=creattime-asc"
					data-name="creattime"><span>创建时间</span></a></li>

			</ul>

		</div>


		<div id="list-content" class="list-content clearfix">


			<s:iterator value="projectApplyHash" id="pros">
				<div id="<s:property value="key" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="135px"><span class="text-primary" id="trainSubject">学科：</span><s:property value="value[1]" /></td>
									<td width="auto" ><span class="text-primary" id="projectName">名称：</span><span><s:property
												value="value[0]" /><s:if test="value[10]==2" ><font color=red>(自主报名)</font></s:if></span></td>
									<td width="250px"><span class="text-primary" id="trainCollege">承训单位：</span><s:property value="value[2]" /></td>
									<td width="100px" align="center" id="status">
										<s:if test="value[11]==1" >未审核</s:if><s:elseif test="value[11]==null"><a href="../teacher/autoenro_initSginUpPage.action?id=<s:property value="key" />" data-fancybox-type="iframe">报名</a></s:elseif><s:elseif test="value[11]==0">未通过</s:elseif><s:else>已审核</s:else>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">
						<div class="list-item-col list-5-2">
							<ul>
								<li><span class="text-primary">项目年份：</span> <s:property
										value="value[3]" /></li>
								<li><span class="text-primary">培训形式：</span> <s:property
										value="value[4]" /></li>
								<li><span class="text-primary">培训开始时间：</span> <s:property
										value="value[5]" /></li>
								<li><span class="text-primary">培训结束时间：</span> <s:property
										value="value[6]" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">培训课时：</span> <s:property
										value="value[7]" /></li>
								<li><span class="text-primary">培训人数：</span> <s:property
										value="value[8]" /></li>
								<li><span class="text-primary">已报名人数：</span> <s:property
										value="value[12]" /></li>
								<li><span class="text-primary">培训金额：</span> <s:property
										value="value[9]" /></li>
							</ul>
						</div>

					</div>
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>
		
		
		


	</div>

</div>
<div class="scorelist left">
	<div class="arrow"></div>
	<div class="bd" id="scorelistcnt"></div>
</div>
<script>

	$(function() {
		var sort = (url('?sort')) ? url('?sort') : 'year-asc';
		var _ = sort.split('-');
		$('#sorting-' + _[0]).addClass('crt');
		$('#sorting-' + _[0]).find('span').addClass(_[1]);

// 		$("#status").each(function(i,e){
// 			//如果已超出报名人数的120%,那么此学科项目不可报名
// 			var str_count = '${value[8]}';
// 			alert(str_count);
// 			var totalcount = Math.ceil(parseInt(str_count) * 1.2);
// 			alert(totalcount);
// 			var str_count1 = '${value[12]}';
// // 			alert(str_count1);
// 			var count = parseInt(str_count1);
// // 			alert(count);
// 			if(totalcount-count <= 0){
// 				$('#status').val("已满额");
// 			}
			
// 		});
			
		
	})

	$('.sorting-btns a').click(function() {
		var name = $(this).attr('data-name');
		sortasc(name);
		return false;
	})

	function sortasc(name) {
		var order = (url('?sort').split('-')[1] == 'asc') ? 'desc' : 'asc';
		window.location = url('protocol') + '://' + url('hostname')
				+ url('path') + '?sort=' + name + '-' + order;
	}
</script>


<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>