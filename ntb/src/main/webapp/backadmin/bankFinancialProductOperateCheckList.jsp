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
							{{if type=='add'}}
								<td width="40px">
									<span class="stageTag back-green">添加</span>
								</td>
							{{else type=='edit'}}
								<td width="40px">
									<span class="stageTag back-grey">修改</span>
								</td>
							{{else type=='delete'}}
								<td width="40px">
									<span class="stageTag back-red">删除</span>
								</td>
							{{else type=='netvalue'}}
								<td width="40px">
									<span class="stageTag back-blue">净值</span>
								</td>
							{{/if}}
							<td width="80%">
								{{if type=='netvalue'}}
									<span><a href="bankFinancialProductOperateCheckNetvalue.jsp?uuid={{:uuid}}">【{{:custodianName}}】{{:bankFinancialProductName}} ({{:scode}})</a></span>
								{{else}}
									<span><a href="bankFinancialProductOperateCheckDetail.jsp?uuid={{:uuid}}">【{{:custodianName}}】{{:bankFinancialProductName}} ({{:scode}})</a></span>
								{{/if}}

							</td>
							<td width="20%" class="text-center operate">
								{{if status=='checked'}}
									{{if type=='netvalue'}}
										<a href="bankFinancialProductOperateCheckNetvalue.jsp?uuid={{:uuid}}" class="color_gray">查看</a>
									{{else}}
										<a href="bankFinancialProductOperateCheckDetail.jsp?uuid={{:uuid}}" class="color_gray">查看</a>
									{{/if}}
								{{else status=='unchecked'}}
									{{if type=='netvalue'}}
										<a href="bankFinancialProductOperateCheckNetvalue.jsp?uuid={{:uuid}}">审核</a>
									{{else}}
										<a href="bankFinancialProductOperateCheckDetail.jsp?uuid={{:uuid}}">审核</a>
									{{/if}}
									<!-- <div class="btn-group">

										<div class="popover">
											<div class="popover-content">
												<p><input class="form-control reason" type="text" name="reason"></p>
												<div>
													<button class="btn btn-primary" type="button" data-uuid="{{:uuid}}" data-status="checked" onclick="checkThis(this)">通过</button>
													<button class="btn btn-primary" type="button" data-uuid="{{:uuid}}" data-status="unpassed" onclick="checkThis(this)">不通过</button>
												</div>
											</div>
										</div>
									</div> -->
								{{else status=='unpassed'}}
									{{if type=='netvalue'}}
										<a href="bankFinancialProductOperateCheckNetvalue.jsp?uuid={{:uuid}}">查看</a>
									{{else}}
										<a href="bankFinancialProductOperateCheckDetail.jsp?uuid={{:uuid}}">查看</a>
									{{/if}}
								{{/if}}
							</td>
						</tr>
					</table>
				</div>
				<div class="list-operate-bd">
					<table>
						<tr>
							<td width="33%">
								<p>填报人：{{:creatorName}}</p>
							</td>
							<td width="33%">
								<p>填报时间：{{:createtimeCN}}</p>
							</td>
							<td width="33%">
								<p>提交时间：{{:submittimeCN}}</p>
							</td>
						</tr>
						<tr>
							<td width="33%">
								<p>审核人：{{:checkerName}}</p>
							</td>
							<td width="33%">
								<p>审核时间：{{:checktimeCN}}</p>
							</td>
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
				{{if status == "unchecked"}}
		            <img src="./img/uncheck.png" class="status_img" />
		        {{else status == "checked"}}
		            <img src="./img/check.png" class="status_img" />
		        {{else status == "unpassed"}}
		            <img src="./img/failed.png" class="status_img" />
		        {{/if}}
			</div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00300032" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a class="current">理财产品信息审核管理</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16" style="min-width:1002px;">
<!-- 					<div class="searchDiv"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}"> -->
<!-- 							<label class="input-group-addon" onclick="searchBtn()"></label> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="condition">
						<div class="statusDiv shortStatusDiv filter">
							<label>审核状态：</label>
							<div>
								<a id="all">全部<span id="allCount">(0)</span></a>
								<a id="checked">审核通过<span id="checkedCount">(0)</span></a>
								<a id="unchecked" class="statusLight">待审核<span id="uncheckedCount">(0)</span></a>
								<a id="unpassed">审核不通过<span id="unpassedCount">(0)</span></a>
							</div>
						</div>
						<div class="statusDiv filter1">
							<label>审核类型：</label>
							<div>
								<a class="statusLight1" id="all">全部<span id="typeCount">(0)</span></a>
								<a id="add">添加<span id="addCount">(0)</span></a>
								<a id="edit">修改<span id="editCount">(0)</span></a>
								<a id="delete">删除<span id="deleteCount">(0)</span></a>
								<a id="netvalue">净值<span id="netvalueCount">(0)</span></a>
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
		<script type="text/javascript" src="./js/getHtmlDocName.js"></script>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/bankFinancialProductOperateCheckList.js" ></script>
	</body>
</html>
