<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>示范性项目人数统计</title>
<style type="text/css">
body{position: relative;}
		a{text-decoration:none;}
		*{margin:0 auto;padding: 0;}
		.clear{clear:both;}
		#select1{border:1px solid #CCCCCC; border-radius: 6px; display: inline-block;width: 528px;position: relative;margin: 10px 0;}
		.options{ z-index:2;display: none;position: absolute;top: 0;left: 0;background: #FFFFFF;border: 1px solid #313131;border-radius: 6px;min-width: 528px;height:400px;overflow-y:auto;}
		.options p label{margin-left: 5px;}
		.optionTitle{vertical-align: top; line-height: 32px; margin: 10px 5px;display: inline-block;float:left;}
		.optionTitle1{margin-top: 0;}
		#select1 p{line-height: 30px;padding-left: 10px;}
		table{margin: 30px 100px;border-collapse:collapse;}
		table th,td{border-bottom: 1px solid #ddd;padding: 10px 20px;text-align:center;}
		table th{background:#efefef;border-top: 1px solid #ddd;white-space:nowrap;}
		table tbody > tr:hover{background:#e8eaef;}
		#select2{border:1px solid #CCCCCC; border-radius: 6px; display: inline-block;width: 528px;position: relative;float:left;}
		#select2 p{line-height: 30px;padding-left: 10px;}
		#select2 .options1{display: none;z-index: 1;position: absolute;top: 0;left: 0;background: #FFFFFF;border: 1px solid #313131;border-radius: 6px;min-width: 528px;height:400px;overflow-y:auto;}
		a{width: 90px;height: 30px; background: #2E91DA;border-radius: 4px; color: #fff; display: inline-block;text-align: center;line-height: 30px;margin:10px 30px;cursor:pointer;}
		a.linka{background:none;color:#2E91DA;text-decoration:none;border-bottom:1px solid #2E91DA;border-radius:0;}
		h1{font-size:18px;margin-left:100px;line-height:50px;}
		.red{color:#f00;}
		#layerDiv{background:rgba(0,0,0,0.4);display:none;position: absolute;z-index:999;left:0;top:0;}
		#layerDiv span{color:#fff;font-size:16px;position: absolute;width:100px;height:32px;display:block; top:50%;left:50%;margin-left:-50px;margin-top:-16px;text-align:center;line-height:32px;}
		button{width: 90px;height: 30px; background: #2E91DA;border-radius: 4px; color: #fff; display: inline-block;text-align: center;line-height: 30px;margin:10px 30px;cursor:pointer;border:none;font-size:16px;}
		</style>
		<link rel="stylesheet" type="text/css" media="screen"
			href="css/jquery-ui.min.css" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="css/ui.jqgrid.css" />
		<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
		<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
		
		<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript">
			<script src="js/jquery-ui-i18n.js" type="application/x-javascript">
		</script>
		<script src="js/jquery.ui.datepicker-zh-CN.js"
			type="application/x-javascript"></script>
	</head>
	<body onclick="closeSelect()">
		<div class="container">
			<h1>示范性项目人数统计<span><a style="width: auto;" href="adminTraineeNumForMW.jsp" class="linka">中西部和幼师国培项目人数统计</a></span></h1>
			<form action="trainee_importTraineeNumForSF.action" method="post">
			<div style="margin-left:100px;">
				<label class="optionTitle">培训项目</label>
				<div id="select1">
					<p class="choose">请选择</p>
					<div class="options">
						<p onclick="closeSelect()"><input type="checkbox" class="checkbox1" id="all" value="全选"/><label for="all">全选</label></p>
					</div>
					
				</div>
				<br>
				<label class="optionTitle optionTitle1">培训学科</label>
				<div id="select2">
					<p class="choose1">请选择</p>
					<div class="options1">
						<p onclick="closeSelect()"><input type="checkbox" class="checkbox2" id="all1" value="全选"/><label for="all1">全选</label></p>
					</div>
					
				</div>
				<br>
				<div class="clear"></div>
				<div style="margin-left: 100px">
					<a class="screen" onclick="initGrid()">筛选</a>

					<button class="export" type="submit">导出</button>
				</div>
				<input type="hidden" class="subjectInput" name="subject">
				<input type="hidden" class="parentInput" name="parent">
			</div>
			</form>
			<div class="content">
				<div class="list">
					<table>
						<tr class='tr1'><th>省份</th><th>报送人数</th><th>审核人数</th><th>报到人数</th><th>结业人数</th></tr>
<!-- 						<tr><td>北京</td><td>aaaaaaaaaaaaaaaaa</td><td></td><td></td><td></td></tr> -->
					</table>
<!-- 					<div id="pager2"></div> -->
				</div>
			</div>
		</div>
		<div id="layerDiv">
			<span>加载中...</span>
		</div>
		<script type="text/javascript">
			
		</script>
		<script src="js/adminTraineeNumForSF.js" type="text/javascript"></script>
	</body>
</html>