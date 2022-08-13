<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>当前报名信息</title>
	
<link rel="stylesheet" href="../css/bootstrap.css">
<!--[if lt IE 9]>
  <script src="../js/html5shiv.js"></script>
  <script src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../css/app.css">	
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../css/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script> 
<script src="../js/dialog-min.js"></script>

</head>
<body>
	<jsp:include page="head.jsp" />
	<jsp:include page="left.jsp" />
<div class="main">
	<div class="listwrap">
		<div class="list_bar">当前报名信息</div>


		<div id="list-content" class="list-content clearfix">
			<s:iterator value="orderFormHash" id="ofh"></s:iterator>
				<div id="<s:property value="key" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="200px"><span class="text-primary">教师姓名：</span><s:property value="value[2]" /></td>
									<td width="auto"><span class="text-primary" >教师编号：</span><span id="teacherId"><s:property value="value[3]" /></span></td>
									<td width="280px"><span class="text-primary">身份证号：</span><span><s:property value="value[4]" /></span></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">
						
						<div class="list-item-col list-5-05">
							<ul>
								<li style="width:800px"><span class="text-primary">报名项目名称：</span><s:property value="value[7]" /></li>
								<li><span class="text-primary">报名学科：</span> <s:property value="value[5]" /></li>
								<li><span class="text-primary">承训单位：</span> <s:property value="value[6]" /></li>
								<li><span class="text-primary">需缴金额：</span> <s:property value="value[8]" />元</li>
								

							</ul>
						</div>
						
					</div>
					<div class="list-item-bd clearfix">
						<div class="list-item-col list-5-05" style="margin-left: 40% ">
							
							<input class="btn btn-primary" type="button" id="btn_makesure" value="确认报名" />
							<input class="btn btn-default" type="button" id="btn_cancel" value="取消" />
							
						</div>
					</div>
				</div>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>
	</div>

	</div>
<script type="text/javascript">
$(function(){
	$("#btn_cancel").click(function(){
		window.history.back();
	});
	$("#btn_makesure").click(function(){
		var id = url('?id');
		var teacherId = '${value[3]}';
		$.getJSON('../teacher/autoenro_signup.action?id='+id+'&teacherId='+teacherId,
				function(data) {
					if (data.Result == 'OK') {
						var d = dialog({
						    title: '系统提示',
							width : 320,
						    content: data.Message ,
						});
						d.showModal();
						setTimeout(function () {
						    d.close().remove();
						    window.location.href ="../teacher/trainInfo_initPage.action";
						}, 3000);
						
					} else {
						var d = dialog({
						    title: '系统提示',
							width : 200,
						    content: data.Message ,
// 						    okValue: '确定',
// 		   					ok: function () {}
						});
						d.showModal();
						setTimeout(function () {
						    d.close().remove();
						}, 3000);
					}
		}).fail(function(){
			var d = dialog({
			    title: '系统提示',
				width : 320,
			    content: '报名失败',
// 			    okValue: '确定',
// 					ok: function () {}
			});
			d.showModal();
			setTimeout(function () {
			    d.close().remove();
			}, 3000);
		});
	});
})
</script>

<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>