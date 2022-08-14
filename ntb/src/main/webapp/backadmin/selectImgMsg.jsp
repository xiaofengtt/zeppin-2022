<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>投资银行理财产品</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="./css/msg_table.css" />
		<link rel="stylesheet" type="text/css" href="css/paging.css">
<!-- 		<link rel="stylesheet" href="css/fundList.css" /> -->
		<script id="queboxTpl" type="text/template">

		</script>
	</head>
	<body style="min-width:0;">
		<div class="layerOpen">
			<p class="titles">发布图文内容到栏目：Banner >> App首页幻灯片</p>
			<div class="contents">
				<div class="layerContent">
					<div class="searchDivs">
						<div class="input-group">
							<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
							<label class="input-group-addon" onclick="searchBtn()"></label>
						</div>
					</div>

					<table class="msg_table" cellpadding="0" cellspacing="0">
						<tr class="first_tr">
							<th width="30%" style="background:none;">图文内容标题</th>
							<th width="5%" style="background:none;">封面图片</th>
							<th width="6%" style="background:none;">创建人</th>
							<th width="11%" style="background:none;">截止时间</th>
							<th width="10%" style="background:none;">操作</th>
						</tr>
						<tbody id="queboxCnt">
							<td><a href="">牛头理财品牌介绍H5页面(app版)</a></td>
							<td>有</td>
							<td>用户1</td>
							<td>2018-11-23 09:08:12</td>
							<td>
								<a href="javascript:void(0);">发布</a>
							</td>
						</tbody>
					</table>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>

		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/query.js"></script>
		<script type="text/javascript" src="js/paging.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/highcharts.js" ></script>
		<script type="text/javascript" src="./js/cselectImgMsg.js" ></script>
	</body>
</html>
