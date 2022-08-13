<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目周期管理</s:param>
</s:action>
<link href="../css/datepicker3.css" rel="stylesheet">
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<script src="../js/jsrender.min.js"></script>
<style>
.stateDiv label {
	width: 100px;
}

div.jtable-main-container>div.jtable-title div.jtable-title-text {
	line-height: 58px;
	font-size: 18px;
}

* {
	margin: 0 auto;
	padding: 0;
}

table {
	width: 100%;
	text-align: left;
	border-collapse: collapse;
	color: #1a1a1a;
	font-size: 14px;
	margin-top: 15px;
}

table tr {
	
}

table th {
	color: #333;
	background: #efefef;
}

tr th, td {
	border-bottom: 1px solid #ddd;
	padding: 10px 5px;
	text-align: center;
	position:relative;
}

a {
	text-decoration: none;
	color: #4365be;
	cursor: pointer;
}

a:hover {
	text-decoration: underline;
}

table input {
	width: 90%;
	height: 30px;
	font-size: 14px;
	color: #666666;
	padding-left: 5px;
}

table div {
	line-height: 30px;
	text-align: center;
}

table div.td1 {
	display: none;
}

.save {
	display: none;
}

.form-control {
	font-size: 14px;
}

.td {
	text-align: left;
}

.stateDiv a:hover {
	text-decoration: none;
}

.delete {
	margin-left: 8px;
}
.iconDiv p img{left:0;}
.list-item-bd .list-5-1{
	    width: 20%;
	    padding: 10px 0 10px 25px;
}
ul{
	list-style:none;
}
.small-label{
	float:left;
	line-height:30px;
}
.small-box{
	float:left;
}
.list-item input,.saveBtn{
	display:none;
	padding-left: 5px;
}
.dateB{
	display:none;
}
.position-label{
	position:absolute;
	left:5px;
	top:8px;
	line-height:30px;
}
.position-box{
	padding-left:70px;
	text-align:left;
}
.datepicker{
	border-radius:0;
	border:1px solid #ddd;
}
</style>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">
			项目周期设置(周期内，包含起始年份和结束年份)
		</div>
	</div>

	
	<div class="tablewrap">

		<div class="cui-menu2" style="width: 100%; margin: 0;">
			<div>
				<form id="reprtTask" class="form-horizontal" role="form" action=""
					style="position: relative; padding-top: 15px; display: none;">
					<div class="row clearfix">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-2 control-label" for="">年份</label>
								<div class="col-sm-9">
									<select id="year" class="form-control" name="year"
										onchange="changeYear(this)">
										<option value="0">全部</option>
										<s:iterator value="searchYear" id="yt">
											<option value='<s:property value="#yt" />'
												<s:if test="#yt==selectYear"> selected </s:if>><s:property
													value="#yt" /></option>
										</s:iterator>
									</select>
								</div>
							</div>

						</div>

						<div style="clear: both"></div>
					</div>
					<div style="clear: both"></div>
					<div class="row actionbar">
						<div class="text-center" style="padding-bottom: 10px">
							<button class="btn btn-primary btn-myself" id="findRecord"
								type="button">查询</button>
						</div>
					</div>
				</form>
			</div>
		</div>


		<input id="pid" type="hidden" value="<s:property value="parentId" />">
		<input id="ptitle" type="hidden" value="<s:property value="navi" />">
		<input id="level" type="hidden" value="<s:property value="level" />">
		<input id="projectLevelStr" type="hidden"
			value="<s:property value="projectLevelString" />"> <input
			id="areaStr" type="hidden" value="<s:property value="areaString" />">

	</div>

	<div id="list-content-demo" class="list-content clearfix" style=""></div>
				<script id="projectCycleTmp" type="text/x-jsrender">
					<div id="" class="list-item">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td width="8%"><span>ID:{{:id}}</span></td>
										<td style="min-width:220px;" width="30%" class="text-left"><span class="text-primary position-label">周期名称：</span>
											<div class="position-box"><span class="valueSpan">{{:name}}</span>
											<input class="" type="text" name="name" value="{{:name}}" /></div>
											<div class="clear"></div>
										</td>
										<td width="25%" class="text-left"><span class="text-primary position-label">起止年份：</span>
											<div class="position-box"><span class="valueSpan valueYear">{{:startyear}}年&nbsp;至&nbsp;{{:endyear}}年</span>
											<input type="text" readonly data-provide="datepicker" class="datepicker"
									name="startyear" value="{{:startyear}}" style="width:48%;"><b class="dateB" style="width:2%;">-</b><input type="text" readonly data-provide="datepicker" class="datepicker"
									name="endyear" value="{{:endyear}}" style="width:48%;"></div><div class="clear"></div></td>
										<td width="10%" class="text-center">
											<a class="editBtn" onclick="editBtn(this)">编辑</a>
											<a class="saveBtn" onclick="modify(this)" name="{{:id}}">保存</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="list-item-bd clearfix">
							{{for studyhour}}
								<div class="list-item-col list-5-1">
									<ul>
										<li><span class="text-primary">名称：</span><span class="value-grn">{{:nameCN}}</span></li>
										<li><span class="text-primary small-label">标准值：</span>
											<div class="small-box"><span class="num-grn">{{:value}}学时</span>
											<input class="" type="number" name="{{:name}}" value="{{:value}}" /><div class="clear"></div></div>
										</li>
									</ul>
								</div>
							{{/for}}

						</div>
					</div>
			
			
				</script>

	<div id="pagination" style="float: right;" class="pull-right"></div>



</div>
<script>
	var flagSubmit = true;
	$(function() {
		getTable();	
	})
	var urlstr = "";
	function getTable() {
		var page = (url('?page') != null) ? url('?page') : '1';

		var index = (page - 1) * 10;
		$
				.getJSON(
						'../admin/projectCycle_getList.action?pid=&jtStartIndex='
								+ index + '&jtPageSize=10&jtSorting=id ASC',
						function(data) {
							if (data.Result == "OK") {
								
								var template = $.templates('#projectCycleTmp');
								var html = template.render(data.Records);
								$('#list-content-demo').html(html);
								
								var totalPage = Math
										.ceil(data.TotalRecordCount / 10);
								var options = {
									currentPage : page,
									totalPages : totalPage,
									shouldShowPage : function(type, page,
											current) {
										switch (type) {
										default:
											return true;
										}
									},
									onPageClicked : function(e, originalEvent,
											type, page) {
										window.location = updateURLParameter(
												url(), 'page', page);
									}

								}
								$('#pagination').bootstrapPaginator(options);
								$('.datepicker').datepicker({
									language : "zh-CN",
									format : 'yyyy',
									startDate : "1y",
									autoclose : true,
									startView:2,
									maxViewMode: 2,
									minViewMode:2
								})
							} else {

							}

						})
	}
	

	//修改
	function modify(obj) {
		var rs1 = tirm($(obj).parents('.list-item').find("input[name='name']").val());
		var rs6 = $(obj).parents('.list-item').find("input[name='startyear']").val();
		var rs7 = $(obj).parents('.list-item').find("input[name='endyear']").val();
		if(!rs1){
			layer.confirm('填写名称不能为空', {
				btn : [ '确定' ]
			}, function() {
				layer.closeAll();
			});
			return false;
		}
		if(rs6>rs7){
			layer.confirm('起始年份不能大于结束年份', {
				btn : [ '确定' ]
			}, function() {
				layer.closeAll();
			});
			return false;
		}
		var id = $(obj).attr("name");
		$(obj).parents('.list-item').find("input[name='name']").prev().html($(obj).parents('.list-item').find("input[name='name']").val());
		$(".valueYear").html($(obj).parents('.list-item').find("input[name='startyear']").val()+'&nbsp;至&nbsp;'+
				$(obj).parents('.list-item').find("input[name='endyear']").val());
		$(obj).parents('.list-item').find(".small-box").each(function(key,value){
			if($(this).find("input").val()!=""){
				$(this).find(".num-grn").html($(this).find("input").val()+"学时");
			}else{
				$(this).find(".num-grn").html("0学时");
			}
			
		});
		var id = $(obj).attr("name");
		var option = "";
		$(obj).parents(".list-item").find("input").each(function(key,value){
			option+="&"+$(this).attr("name")+"="+$(this).val();
		})			
		$(obj).parents('.list-item').find("input").hide();
		$(obj).parents('.list-item').find(".dateB").hide();
		$(obj).hide();
		$(obj).parents('.list-item').find(".valueSpan").show();
		$(obj).parents('.list-item').find(".num-grn").show();
		$(obj).parent().find(".editBtn").show();
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.getJSON(
				'../admin/projectCycle_add.action?method=edit&id='+ id 
						+ option, function(data) {
					flagSubmit = true;
					if (data.Result == "OK") {
						layer.msg("修改成功",{
		        			  icon: 1,
		        			  time: 2000 
		        			});
						$(obj).attr("name", data.id);
					} else {
						layer.confirm(data.Message, {
							btn : [ '确定' ]
						}, function() {
							layer.closeAll();
						});
					}
				})

	}
	//验证
	function tirm(value) {
		var values = value.replace(/(^\s*)|(\s*$)/g, "");

		if (values == "") {
			$(this).css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			return false;
		} else {

			return true;
		}
	}
	
	//验证数字
	function tirm2(value){
		var values=value.replace(/(^\s*)|(\s*$)/g, "");
		var reg=/^\d+$/; 
		if(reg.test(values)==true){
		    return true;
		}else{
			if(values==""){
				return true;
			}else{
				return false;
			}			
		}
	}
	
	//验证
	function tirm3(value) {
		var values = value.replace(/(^\s*)|(\s*$)/g, "");

		if (values == "0") {
			$(this).parent().css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			});
			return false;
		} else {

			return true;
		}
	}

	//点击编辑按钮
	function editBtn(obj){
		$(obj).parents('.list-item').find("input").show();
		$(obj).parents('.list-item').find(".dateB").show();
		$(obj).hide();
		$(obj).parents('.list-item').find(".valueSpan").hide();
		$(obj).parents('.list-item').find(".num-grn").hide();
		$(obj).parent().find(".saveBtn").show();
	}
</script>

<jsp:include page="foot.jsp"></jsp:include>