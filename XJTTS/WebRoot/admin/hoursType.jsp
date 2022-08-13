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
<script src="../js/iframe.js"></script>
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
.studyhour-box{
	text-align:left;
}
.position-label{
	line-height:30px;
}
input{
	display:none;
}
.projectCycleDiv{
	position:absolute;
	top:15px;
	right:210px;
}
</style>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">
			学时认定设置（按项目类型）
		</div>
		
		<div class="col-md-3 projectCycleDiv">
			<select id="projectCycle" onchange="getList();" class="form-control" name="projectCycle">				
			</select>
		</div>
		<div class="iconDiv">
			<a class="btn btn-lg btnMyself btn-screen">
				<span><img src="../img/sousuo.png" alt="icon">
				<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选项目</b>
				</p>
			</a>
			<a class="btn btn-create btnMyself"
				href="../admin/identifyStudyhour_initAddPage?projectCycle=1"
				data-fancybox-type="iframe"> <span><img
					src="../img/kaishexiangmu.png" alt="icon"> <b>添加</b> </span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角"> <b>添加</b>
				</p>
			</a>
		</div>
		<div class="cui-menu2" style="position:relative">
			<div>
				<form id="reprtTask" class="form-horizontal" role="form" action="" style="position:relative;display:none;">
					<div class="row clearfix">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-2 control-label" for="">年份</label>
								<div class="col-sm-9">
									<select id="year" class="form-control" name="year">
										<option value="0">全部</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-6 control-group" id="categoryDiv">
							<label class="col-sm-2 control-label" for="">项目类型</label>
							<div class="controls" style="margin-left:115px">
							<div class="companylocation" style="z-index: 1;width:87%;height:30px;padding:0;border-radius:4px;">
								<span class="clId" id="clId" style="height: 30px;border-radius:4px;line-height:30px;padding-left:8px;" 
									onclick="getsnode();changeClbtn($(this));"></span>
								<div id="clListBox" class="listSub" style="position: initial;">  
									<div id="clList" class="list_sub sm_icon">
										<div id="bido"></div>
									</div>
								</div>
							</div>

							<input type="hidden" id="organization" name="projectTypeId"
								value="" placeholder="项目类型">
						</div>
						</div>
						
						
						<div style="clear:both"></div>
					</div>
					<div style="clear:both"></div>
					<div class="row actionbar">
						<div class="text-center" style="padding-bottom:10px">
							<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div id="list-content" class="list-content clearfix"
			style="padding-top: 15px;">
			<table class="table table-hover">
				<tr>
					<th width="18%" style="text-align: left;">项目类型一级</th>
					<th width="18%" style="text-align: left;">项目类型二级</th>
					<th width="18%" style="text-align: left;">项目名称</th>
					<th width="7%" style="text-align: left;">年份</th>
					<th width="18%" style="text-align: left;">培训学时</th>
					<th width="10%" align="center">培训总学分</th>
					<th width="11%">操作</th>
				</tr>
			</table>

		</div>

	<div id="pagination" style="float:right;" class="pull-right"></div>

	</div>

</div>
<script type="text/javascript">
	var flagSubmit = true;
	var projectCycle;
	var page = (url('?page') != null) ? url('?page') : '1';
	$(function() {
		getProjectCycle();
		getYear();
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "620px",
			height : "600px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true

		});
		$(".btn-screen").click(function(e) {
			e.preventDefault();
			$('#reprtTask').toggle();
		})
		//项目类型 helper 函数
		$(document).on("click",function(a) {
			if(!$(a.target).parents().is('.companylocation'))
				$('.listSub').hide();
		});
		
		$('#findRecord').click(
				function(e) {
					getTable(page);
				});
	})
	function forTree(o,l){
		
		for(var i=0;i<o.length;i++){		 
			var urlstr = "";
			
				try {
					if (typeof o[i]["id"] == "undefined") {
						urlstr = "<div onclick='getname(this)'><span>" + o[i]["name"]
								+ "</span><ul>";
					} else {
						urlstr = "<div onclick='getname(this)'><span class='span"+l+"'><a name="+ o[i]["id"] +">"
								+ o[i]["name"]
								+ "</a></span><ul>";
						for(var j=0;j<o.length;j++){
							if(o[j]["id"]==projectType){
								$("#calId").html(o[j]["name"]);	
								break;
							}else{
								$("#calId").html("全部");
								
							}
						}
					}
					str += urlstr;
					if (o[i]["records"] != null) {
						forTree(o[i]["records"],"2");
					}
					str += "</ul></div>";
				} catch (e) {
				}
			}
		$("#menuTree").html(str);
		var menuTree = function() {
			//给有子对象的元素加[+-]
			$("#menuTree ul").each(function(index, element) {
				var ulContent = $(element).html();
				var spanContent = $(element).siblings("span").html();
				if (ulContent) {
					$(element).siblings("span").html("[+] " + spanContent)
				}
			});
			$("#menuTree").find("div span").click(function() {
				var ul = $(this).siblings("ul");
				var spanStr = $(this).html();
				var spanContent = spanStr.substr(3, spanStr.length);
				if (ul.find("div").html() != null) {
					if (ul.css("display") == "none") {
						ul.show(300);
						$(this).html("[-] " + spanContent);
					} else {
						ul.hide(300);
						$(this).html("[+] " + spanContent);
					}
				}
			})
		}()
	}
	function changeToggle(){
		$("#menuTree").toggle();
	}
	function getProjectCycle() {
		var this_ = $('#projectCycle');
		var sortStr = 'id desc';
		$.getJSON('../admin/projectCycle_getList.action',{
			jtSorting : sortStr
		},function(data){
			if(data.Result == 'OK'){
				var pcStr = '';
				for(var i=0;i< data.Records.length;i++){
					var options = '';
					if(i == 0){
						options = '<option value="'+data.Records[i].id+'" selected="selected">'+data.Records[i].name+'</option>';
						var addUrl = '../admin/identifyStudyhour_initAddPage?projectCycle='+data.Records[i].id;
						$('.btn-create').attr('href',addUrl);
						projectCycle = data.Records[i].id;
					} else {
						options = '<option value="'+data.Records[i].id+'">'+data.Records[i].name+'</option>';
					}
					
					pcStr += options;
				}
				this_.html(pcStr);
				
			}
			
		}).done(function(){
			getTable(page);
		})
	}
	
	function getYear() {
		var this_ = $('#year');
		$.getJSON('../admin/projectCycle_getYearList.action',function(data){
			if(data.Result == 'OK'){
				var yearStr = '';
				for(var i=0;i< data.Options.length;i++){
					var options = '<option value="'+data.Options[i].Value+'">'+data.Options[i].DisplayText+'</option>'
					
					yearStr += options;
				}
				this_.html(yearStr);
				
			}
			
		})
	}
	function getList() {
		page = 1;
		projectCycle = $('#projectCycle').val();
		getTable(page);
	}
	function getTable(page) {
		var urlstr = "";
		var projectType = $('#organization').val() == null ? '' : $('#organization').val();
		var yearStr = $('#year').val() == '0' ? '' :  $('#year').val();
		var projectCycleStr = projectCycle == null ? $('#projectCycle').val() : projectCycle;
		$.getJSON('../admin/identifyStudyhour_getList.action',{
			page : page,
			projectCycleId : projectCycleStr,
			projectTypeId : projectType,
			year : yearStr
		},function(data) {
							if (data.status == "OK") {
								urlstr += '<tr>'+
									'<th width="18%" style="text-align: left;">项目类型一级</th>'+
									'<th width="18%" style="text-align: left;">项目类型二级</th>'+
									'<th width="18%" style="text-align: left;">项目名称</th>'+
									'<th width="7%" style="text-align: left;">年份</th>'+
									'<th width="18%" style="text-align: left;">培训学时</th>'+
									'<th width="10%" align="center">培训总学分</th>'+
									'<th width="11%">操作</th>'+
								'</tr>';
								for (var i = 0; i < data.records.length; i++) {
									var year = data.records[i].year;
									var studyhour = "";
									for(var j=0;j<data.records[i].studyhour.length;j++){
										studyhour += '<div class="studyhour-box"><span class="position-label">'+data.records[i].studyhour[j].nameCN+':</span>'+
															'<span class="value-span">'+data.records[i].studyhour[j].value
															+'学时</span><input name="'+data.records[i].studyhour[j].name+'" type="number" value="'+data.records[i].studyhour[j].value
															+'" /></div>'; 
									}
									urlstr += '<tr><td class="td">'
										+ data.records[i].projectTypeFirst
										+ '</td><td class="td">'
										+ data.records[i].projectTypeSecond
										+ '</td><td class="td">'
										+ data.records[i].name
										+ '</td>';
									urlstr += '<td class="td">'
											+ year
											+ '</td><td>'
											+ studyhour+'</td><td><span class="value-span">'
											+ data.records[i].credit+'学分</span><input name="credit" type="number" value="'+data.records[i].credit
											+'" /></td><td><a href="javascript:" class="modify">修改</a><a class="save" name="'
											+ data.records[i].id
											+ '" custom="'
											+ data.records[i].id
											+ '" onclick="modify(this)">保存</a><a class="delete" onclick="dele('+data.records[i].id+')">删除</a></td></tr>';
								}
								if(data.records.length==0){
									urlstr += '<tr><td colspan="7">暂无相关数据</td></tr>'
								}
								$("table").html(urlstr); 
								if(data.totalcount!=0){
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
												getTable(page);
											}

										}
										$('#pagination').bootstrapPaginator(options);
										$('#pagination').show();
								}else{
									$('#pagination').hide();
								}
								
							} else {
								layer.confirm(data.message, {
									btn : [ '确定' ]
								}, function() {
									layer.closeAll();
								});
							}

						}).done(
						function() {
							$(".modify").click(
									function() {
										$(this).parents("tr").find("input").show();
										$(this).parents("tr").find(".value-span").hide();
										$(this).hide();
										$(this).next().show();
									})
						})
	}

	//修改
	function modify(obj) {		
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		var id = $(obj).attr("name");
		var option = "";
		$(obj).parents("tr").find("input").each(function(key,value){
			option+="&"+$(this).attr("name")+"="+$(this).val();
			if($(this).val()!=""){
				$(this).prev().html($(this).val()+"学时");
			}else{
				$(this).prev().html("0学时");
			}
		})
		$.getJSON('../admin/identifyStudyhour_add.action?method=edit&id=' + id
				+ option,
				function(data) {
					flagSubmit = true;
					if (data.status == "OK") {
						layer.msg("修改成功",{
		        			  icon: 1,
		        			  time: 2000 
		        			});
						$(obj).attr("name", data.id);
						$(obj).parents("tr").find("input").hide();
						$(obj).parents("tr").find(".value-span").show();
						$(obj).hide();
						$(obj).prev().show();
					} else {
						layer.confirm(data.message, {
							btn : [ '确定' ]
						}, function() {
							layer.closeAll();
						});
					}
				})

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
	//删除
	function dele(obj){
		
		layer.confirm('确定要删除吗？', {
			btn : [ '确定', '取消' ]
		//按钮
		}, function() {
			$.getJSON('../admin/identifyStudyhour_delete.action?id=' + obj,
					function(data) {
						if (data.status == "OK") {
							layer.msg("删除成功",{
			        			  icon: 1,
			        			  time: 2000 
			        			},function(){
								window.location.href = window.location.href;
							});
							

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
	
	function changeClbtn(e) {
		e.next('.listSub').toggle();
	}
	// 工作单位 请求子元素
	function getsnode(bid) {
		var cUrl = "../admin/projectType_getListByPid";
		bid = (typeof (bid) == 'undefined') ? '' : bid;
		var e = (bid) ? $('#bido' + bid) : $('#bido');
		cUrl += (bid) ? '?id=' + bid : '?id=0';
		if (bid)
			e.css('display') == 'none' ? e.show() : e.hide();
			$.getJSON(cUrl,
			function(data) {
				var cLis = '没有找到';
				if (data.length > 0) {
					var cLis = '';
					$.each(
						data,
						function(i, c) {
							emClass = (c.haschild == 1) ? ' class="c"'
									: '';
							emClick = (c.haschild == 1) ? ' onclick="getsnode(\''
									+ c.id
									+ '\');changeIcon($(this))"'
									: '';
							aClick = (c.haschild == 1) ?' onclick="getsnode(\''
									+ c.id
									+ '\');changeIcon($(this))"':' onclick="setnode(\''
									+ c.id + '\', \''
									+ c.name + '\')"';
							cLis += '<div class="listnode" id="'
									+ c.id
									+ '"><em'
									+ emClass
									+ emClick
									+ '></em><a href="javascript:void(0)" '
									+ aClick
									+ '>'
									+ c.name
									+ '</a><div id="bido'
									+ c.id
									+ '" class="cSub" style="display:none">加载中...</div></div>';

						});
				}
				e.html(cLis)
		});
	}
</script>


<jsp:include page="foot.jsp"></jsp:include>