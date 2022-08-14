<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="css/paging.css">
		<link rel="stylesheet" href="css/fundList.css" />
		<script id="queboxTpl" type="text/template">
			<div class="list-item">
				<div class="list-operate-hd">
					<table>
						<tr height="40px">
							{{if status=='checked'}}
								<td width="40px">
									<span class="stageTag back-green">已通过</span>
								</td>
							{{else status=='unchecked'}}
								<td width="40px">
									<span class="stageTag back-grey">待审核</span>
								</td>
							{{else status=='unpassed'}}
								<td width="40px">
									<span class="stageTag back-red">未通过</span>
								</td>
							{{/if}}
							<td width="80%">
								<span><a class="checkbtn" href="investorIdcardImgCheckDetail.jsp?uuid={{:uuid}}">【{{:name}}】--{{:idcard}}</a></span>
							</td>
							<td width="20%" class="text-center operate">
								{{if status=='checked'}}
									<a class="operaBranch checkbtn" href="investorIdcardImgCheckDetail.jsp?uuid={{:uuid}}">查看</a>
								{{else status=='unchecked'}}
									<div class="btn-group">
										<a class="operaBranch checkbtn" href="investorIdcardImgCheckDetail.jsp?uuid={{:uuid}}">审核</a>
									</div>
								{{else status=='unpassed'}}
									<a class="operaBranch checkbtn" href="investorIdcardImgCheckDetail.jsp?uuid={{:uuid}}">查看</a>
								{{/if}}
							</td>
						</tr>
					</table>
				</div>
				<div class="list-operate-bd">
					<table>
						<tr>
							<td width="33%">
								<p>提交时间：{{:createtimeCN}}</p>
							</td>
							<td width="33%">
								<p>审核人：{{:checkerName}}</p>
							</td>
							<td width="33%">
								<p>审核时间：{{:checktimeCN}}</p>
							</td>
						</tr>
						<tr>
							<td width="33%">
								{{if status=='unchecked'}}
									<p>审核信息：<span class="unpass">{{:reason}}</span></p>
								{{else status=='checked'}}
									<p>审核信息：<span class="pass">{{:reason}}</span></p>
								{{else status=='unpassed'}}
									<p>审核信息：<span class="unpass">{{:reason}}</span></p>
								{{/if}}
							</td>
						</tr>
					</table>
				</div>
			</div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00700001" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a class="current">用户证件信息审核管理</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16" style="min-width:1002px;">
					<div class="searchDiv">
						<div class="input-group">
							<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
							<label class="input-group-addon" onclick="searchBtn()"></label>
						</div>
					</div>
					<div class="condition">
						<div class="statusDiv shortStatusDiv filter">
							<label>审核状态：</label>
							<div>
								<a id="all">全部<span id="allCount">(0)</span></a>
								<a id="unchecked" class="statusLight">待审核<span id="uncheckedCount">(0)</span></a>
								<a id="checked">审核通过<span id="checkedCount">(0)</span></a>
								<a id="unpassed">审核不通过<span id="unpassedCount">(0)</span></a>
							</div>
						</div>
					</div>

					<div class="list-content" id="queboxCnt">

					</div>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/investorIdcardImgCheckList.js" ></script>
	</body>
</html>
