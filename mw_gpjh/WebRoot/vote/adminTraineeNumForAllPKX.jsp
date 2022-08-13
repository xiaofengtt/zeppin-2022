<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>县区维度培训人数统计(分中西部)</title>
<style type="text/css">
body{position: relative;}
		a{text-decoration:none;}
		*{margin:0 auto;padding: 0;}
		.clear{clear:both;}
		#select1{display: inline;width: 528px;position: relative;margin: 10px 0;}
		.options{ z-index:2;display: none;position: absolute;top: 0;left: 0;background: #FFFFFF;border: 1px solid #313131;border-radius: 6px;min-width: 528px;height:400px;overflow-y:auto;}
		.options p label{margin-left: 5px;}
		.optionTitle{vertical-align: top; line-height: 32px; margin: 10px 5px;display: inline-block;float:left;}
		.optionTitle1{margin-top: 0;}
		#select1 p{line-height: 30px;padding-left: 10px;}
		#select1 a{width: 460px;text-align: left;}
		table{margin: 30px 100px;border-collapse:collapse;}
		table th,td{border-bottom: 1px solid #ddd;padding: 10px 20px;text-align:center;}
		table th{background:#efefef;border-top: 1px solid #ddd;white-space:nowrap;}
		table tbody > tr:hover{background:#e8eaef;}
		a{width: 90px;height: 30px; background: #2E91DA;border-radius: 4px; color: #fff; display: inline-block;text-align: center;line-height: 30px;margin:10px 30px;cursor:pointer;}
		button{width: 90px;height: 30px; background: #2E91DA;border-radius: 4px; color: #fff; display: inline-block;text-align: center;line-height: 30px;margin:10px 30px;cursor:pointer;border:none;font-size:16px;}
		a.linka{background:none;color:#2E91DA;text-decoration:none;border-bottom:1px solid #2E91DA;border-radius:0;}
		h1{font-size:18px;margin-left:100px;line-height:50px;}
		.red{color:#f00;}
		#layerDiv{background:rgba(0,0,0,0.4);display:none;position: absolute;z-index:999;left:0;top:0;}
		#layerDiv span{color:#fff;font-size:16px;position: absolute;width:100px;height:32px;display:block; top:50%;left:50%;margin-left:-50px;margin-top:-16px;text-align:center;line-height:32px;}
		</style>
		<link rel="stylesheet" type="text/css" media="screen"
			href="css/jquery-ui.min.css" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="css/ui.jqgrid.css" />
		<link href="css/select2.css" rel="stylesheet" />
		<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
		<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript">
			<script src="js/jquery-ui-i18n.js" type="application/x-javascript">
		</script>
		<script src="js/jquery.ui.datepicker-zh-CN.js"
			type="application/x-javascript"></script>
		<script src="js/select2.js"></script>
		<script src="js/select2_locale_zh-CN.js"></script>
	</head>
	<body>
		<div class="container">
			<h1>县区级培训人数统计(分中西部)</h1>
			<form action="" method="post">
			<div style="margin-left:100px;position:relative">
				<label class="optionTitle">培训县区</label>
				<div id="select1">
<!-- 						<p onclick="closeSelect()"><input type="checkbox" class="checkbox1" id="all" value="全选"/><label for="all">全选</label></p> -->
					<select class="form-control" name="county" id='county'>
						<option value="all">请选择</option>
					</select>
					
				</div>
				<br>
				<div class="clear"></div>
				<div style="position:absolute;left:590px;top:5px;">
					<a class="screen" onclick="initGrid()">筛选</a>
				</div>
<!-- 				<input type="hidden" class="countyInput" name="county"> -->
				
			</div>
			</form>
			<div class="content">
				<div class="list">
					<table cellpadding="0" cellspacting="0" style="border:1px solid #c1c1c1">
						<tr class='tr1'><th>地区</th><th>示范性项目培训人数</th><th>中西部项目培训人数</th></tr>
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
		<script src="js/adminTraineeNumForAllPKX.js" type="text/javascript"></script>
	</body>
</html>