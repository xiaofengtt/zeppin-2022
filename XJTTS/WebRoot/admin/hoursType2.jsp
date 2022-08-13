<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学时认定设置</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<style type="text/css">
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
	margin-top: 0px;
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

table div.td5_0 {
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

.iconDiv p img {
	left: 3px;
}
.delete{margin-left:8px;}
</style>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">
			<a href="../admin/hoursType.jsp">学时认定设置（按项目类型）</a>&nbsp;&nbsp;&nbsp;
			学时认定设置 (特殊学科）&nbsp;&nbsp;&nbsp;
			<a href="../admin/hoursType3.jsp">学时认定设置（学科分年份）</a>&nbsp;&nbsp;&nbsp;
			<a href="../admin/hoursType4.jsp">学时认定设置（特殊项目）</a>
		</div>
		<div class="iconDiv">
			<a class="btn btn-create btnMyself btn-screen"
				href="../admin/identifyClasshours_initAddPage"
				data-fancybox-type="iframe"> <span><img
					src="../img/kaishexiangmu.png" alt="icon"> <b>添加</b> </span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角"> <b>添加</b>
				</p>
			</a>
		</div>
		<div id="list-content" class="list-content clearfix"
			style="padding-top: 15px;">
			<table class="table table-hover">
				<tr>
					<th width="18%" style="text-align: left;">项目类型一级</th>
					<th width="16%" style="text-align: left;">项目类型二级</th>
					<th width="10%" style="text-align: left;">学科</th>
					<th width="8%" align="center">集中培训</th>
					<th width="8%" align="center">信息技术</th>
					<th width="8%" align="center">区域特色</th>
					<th width="8%" align="center">校本培训</th>
					<th width="8%" align="center">培训总学时</th>
					<th width="8%" align="center">培训总学分</th>
					<th width="8%">操作</th>
				</tr>



			</table>

		</div>

	<div id="pagination" style="float:right;" class="pull-right"></div>

	</div>

</div>
<script type="text/javascript">
	$(function() {
		getTable();
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "620px",
			height : "600px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true

		});
	})

	function getTable() {
		var page = (url('?page') != null) ? url('?page') : '1';
		var urlstr = "";
		$
				.getJSON(
						'../admin/identifyClasshours_getSpecialList.action?page='+page,
						function(data) {
							if (data.status == "OK") {
								for (var i = 0; i < data.records.length; i++) {

									var centralizeCHs;
									var informationCHs;
									var regionalCHs;
									var schoolCHs;
									var totalhoursCHs;
									var creditCHs;
									if (data.records[i].centralizeCH == ""
											|| data.records[i].centralizeCH == "0") {
										centralizeCHs = "--";
									} else {
										centralizeCHs = data.records[i].centralizeCH
												+ "学时";
									}
									if (data.records[i].informationCH == ""
											|| data.records[i].informationCH == "0") {
										informationCHs = "--";
									} else {
										informationCHs = data.records[i].informationCH
												+ "学时";
									}
									if (data.records[i].regionalCH == ""
											|| data.records[i].regionalCH == "0") {

										regionalCHs = "--";
									} else {
										regionalCHs = data.records[i].regionalCH
												+ "学时";
									}
									if (data.records[i].schoolCH == ""
											|| data.records[i].schoolCH == "0") {
										schoolCHs = "--";
									} else {
										schoolCHs = data.records[i].schoolCH
												+ "学时";
									}
									if (data.records[i].totalhours == ""
											|| data.records[i].totalhours == "0") {
										totalhoursCHs = "--";
									} else {
										totalhoursCHs = data.records[i].totalhours
												+ "学时";
									}
									if (data.records[i].credit == ""
										|| data.records[i].credit == "0") {
										creditCHs = "--";
									} else {
										creditCHs = data.records[i].credit
												+ "学分";
									}
									var parentType = '';
									if(data.records[i].parentType != '0'){
										parentType = data.records[i].parentType;
										urlstr += '<tr><td class="td">'
											+ parentType
											+ '</td><td class="td">'
											+data.records[i].name+'</td>'
									}else{
										urlstr += '<tr><td class="td">'
											+ data.records[i].name
											+ '</td><td class="td"></td>'
									}
									urlstr += '<td class="td">'
											+ data.records[i].trainingSubject
											+ '</td><td><div class="td1 td6_0">'
											+ centralizeCHs
											+ '</div><div class="td1_0 td5_0"><input class="form-control" value="'+data.records[i].centralizeCH+'" type="text"></div></td><td><div class="td2 td6_0">'
											+ informationCHs
											+ '</div><div class="td2_0 td5_0"><input class="form-control" value="'+data.records[i].informationCH+'" type="text"></div></td><td><div class="td3 td6_0">'
											+ regionalCHs
											+ '</div><div class="td3_0 td5_0"><input class="form-control" value="'+data.records[i].regionalCH+'" type="text"></td><td><div class="td4 td6_0">'
											+ schoolCHs
											+ '</div><div class="td4_0 td5_0"><input class="form-control" value="'+data.records[i].schoolCH+'" type="text"></div></td><td><div class="td5 td6_0">'
											+ totalhoursCHs
											+ '</div><div class="td7_0 td5_0"><input class="form-control" value="'+data.records[i].totalhours+'" type="text"></div></td><td><div class="td6 td6_0">'
											+ creditCHs
											+ '</div><div class="td8_0 td5_0"><input class="form-control" value="'+data.records[i].credit+'" type="text"></div></td><td><a href="javascript:" class="modify">修改</a><a class="save" name="'
											+ data.records[i].id
											+ '" custom="'
											+ data.records[i].id
											+ '" onclick="modify(this)">保存</a><a class="delete" onclick="dele('+data.records[i].id+')">删除</a></td></tr>';
								}
								$("table").append(urlstr);
								var options = {
										currentPage : data.currentPage,
										totalPages : data.totalPage,
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
							} else {

							}

						}).done(
						function() {
							$(".modify").click(
									function() {
										$(this).parent().parent().find(
												"div.td6_0").css("display",
												"none");
										$(this).parent().parent().find(
												"div.td5_0").css("display",
												"inline-block");
										$(this).css("display", "none");
										$(this).parent().parent().find(".save")
												.css("display", "inline-block");
									})
							$(".save").click(function() {

							})
							$("input").blur(function() {
								var value = $(this).val();
								value = value.replace(/(^\s*)|(\s*$)/g, "");
								var reg = /^\d+$/; 
								if (reg.test(value) == true) {
									$(this).removeAttr("style");
									return true;
								} else {
									if(value==""){
										$(this).removeAttr("style");
									    return true;
									}else{
										$(this).css({"border-color":"#f00","box-shadow":"none"});
										layer.confirm('填写必须全部为数字且为正整数', {
											  btn: ['确定'] //按钮
											}, function(){
											  layer.closeAll();
											});
									    return false;
									}
								}
							})
						})
	}

	//修改
	function modify(obj) {
		var rs1 = tirm($(obj).parent().parent().find("div.td1_0 input").val());
		var rs2 = tirm($(obj).parent().parent().find("div.td2_0 input").val());
		var rs3 = tirm($(obj).parent().parent().find("div.td3_0 input").val());
		var rs4 = tirm($(obj).parent().parent().find("div.td4_0 input").val());
		var rs5 = tirm($(obj).parent().parent().find("div.td7_0 input").val());
		var rs6 = tirm($(obj).parent().parent().find("div.td8_0 input").val());
		if (rs1 && rs2 && rs3 && rs4 && rs5 && rs6) {
			var id = $(obj).attr("name");
			var projectTypeId = $(obj).attr("custom");
			var centralizeCH = $(obj).parent().parent().find("div.td1_0 input")
					.val().replace(/(^\s*)|(\s*$)/g, "");
			var informationCH = $(obj).parent().parent()
					.find("div.td2_0 input").val().replace(/(^\s*)|(\s*$)/g, "");
			var regionalCH = $(obj).parent().parent().find("div.td3_0 input")
					.val().replace(/(^\s*)|(\s*$)/g, "");
			var schoolCH = $(obj).parent().parent().find("div.td4_0 input")
					.val().replace(/(^\s*)|(\s*$)/g, "");
			var totalhours = $(obj).parent().parent().find("div.td7_0 input")
					.val().replace(/(^\s*)|(\s*$)/g, "");
			var credit = $(obj).parent().parent().find("div.td8_0 input")
					.val().replace(/(^\s*)|(\s*$)/g, "");
			var centralizeCHs;
			var informationCHs;
			var regionalCHs;
			var schoolCHs;
			var totalhoursCHs;
			var creditCHs;
			if (id == "0") {
				id = "";
			} else {
				id = id;
			}
			if (centralizeCH == "" || centralizeCH == "0") {
				centralizeCHs = "--";
			} else {
				centralizeCHs = centralizeCH + "学时";
			}
			if (informationCH == "" || informationCH == "0") {
				informationCHs = "--";
			} else {
				informationCHs = informationCH + "学时";
			}
			if (regionalCH == "" || regionalCH == "0") {
				regionalCHs = "--";
			} else {
				regionalCHs = regionalCH + "学时";
			}
			if (schoolCH == "" || schoolCH == "0") {
				schoolCHs = "--";
			} else {
				schoolCHs = schoolCH + "学时";
			}
			if (totalhours == "" || totalhours == "0") {
				totalhoursCHs = "--";
			} else {
				totalhoursCHs = totalhours + "学时";
			}
			if (credit == "" || credit == "0") {
				creditCHs = "--";
			} else {
				creditCHs = credit + "学分";
			}

			$.getJSON('../admin/identifyClasshours_specialAdd.action?method=edit&id=' + id
					+ '&projectTypeId=' + projectTypeId + '&centralizeCH='
					+ centralizeCH + '&informationCH=' + informationCH
					+ '&regionalCH=' + regionalCH + '&schoolCH=' + schoolCH + '&totalhours=' + totalhours + '&credit=' + credit,
					function(data) {
						if (data.status == "OK") {
							$(obj).attr("name", data.id);
							$(obj).parent().parent().find("div.td1").html(
									centralizeCHs);
							$(obj).parent().parent().find("div.td2").html(
									informationCHs);
							$(obj).parent().parent().find("div.td3").html(
									regionalCHs);
							$(obj).parent().parent().find("div.td4").html(
									schoolCHs);
							$(obj).parent().parent().find("div.td5").html(
									totalhoursCHs);
							$(obj).parent().parent().find("div.td6").html(
									creditCHs);
							$(obj).parent().parent().find("div.td6_0").css(
									"display", "block");
							$(obj).parent().parent().find("div.td5_0").css(
									"display", "none");
							$(obj).css("display", "none");
							$(obj).parent().parent().find(".modify").css(
									"display", "inline-block");
						} else {
							layer.confirm(data.message, {
								btn : [ '确定' ]
							}, function() {
								layer.closeAll();
							});
						}
					})
		} else {
			layer.confirm('填写必须全部为数字且为正整数', {
				btn : [ '确定' ]
			}, function() {
				layer.closeAll();
			});
		}

	}
	//验证
	function tirm(value) {
		var values=value.replace(/(^\s*)|(\s*$)/g, "");
		var reg = /^\d+$/; 
		if (reg.test(values) == true) {
			return true;
		} else {
			if(values==""){
				return true;
			}else{
				$(this).css({
					"border-color" : "#f00",
					"box-shadow" : "none"
				});
				return false;
			}			
		}
	}
	//删除
	function dele(obj){
		
		layer.confirm('确定要删除吗？', {
			btn : [ '确定', '取消' ]
		//按钮
		}, function() {
			$.getJSON('../admin/identifyClasshours_delete.action?id=' + obj,
					function(data) {
						if (data.status == "OK") {
							window.location.href = window.location.href;

						} else {
							layer.confirm(data.message, {
								btn : [ '确定' ]
							}, function() {
								layer.closeAll();
							});
						}
					})
		}, function() {
			layer.close()
		});
	}
</script>


<jsp:include page="foot.jsp"></jsp:include>