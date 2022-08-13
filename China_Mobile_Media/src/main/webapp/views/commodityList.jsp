<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品列表</title>

<link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/themes/light.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">
<link href="../assets/css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../assets/css/ui-dialog.css">
<link rel="stylesheet" href="../assets/css/searchbox.css">
<link rel="stylesheet" href="../assets/css/main.css" type="text/css"/>
<link rel="stylesheet" href="../assets/css/simplePagination.css" type="text/css"/>
<link rel="stylesheet" href="../assets/css/columLists.css" type="text/css"/>

<script src="../assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/scripts/app.js" type="text/javascript"></script>
<script type="text/javascript" src="../app/js/index.js"></script>
<script src="../assets/plugins/jquery-ui.js"></script>
<script src="../assets/plugins/url.min.js"></script>
<script src="../assets/plugins/jquery.colorbox.js"></script>
<script src="../assets/plugins/dialog-min.js"></script>
<script src="../assets/plugins/jquery.simplePagination.js"></script>
<script src="../assets/plugins/jsrender.min.js"></script>
<script src="../assets/plugins/underscore-min.js"></script>
<style type="text/css">
button.btn{ margin-top:24px;}
.btn{ border:none;}
.content{ padding-bottom:100px;}
.content p.title{ color:#428bca; font-size:15px;font-weight:bold;margin:10px 0px;}
hr{border-top:2px solid #adb5bb;margin:10px 0px;}
.statusbar{ margin-top:20px;font-weight:bolder;}
.statusbar a.filterStatus{ display:inline-block; margin:0px; padding:10px 15px;color:#9c9c9c; cursor:pointer; border:1px solid #ccc;border-radius:3px;}
.statusbar a.light{ background:#ff6525; color:#f0f0f0;}
</style>


<script id="queboxTpl" type="text/template">
		
						<tr>
							<td width="auto" style="line-height:76px"><span class="text-primary"><img src="../{{:coverURL}}" style="max-width:90%;max-height:200px;"></td>
							<td width="auto" style="line-height:76px"><span class="text-primary">{{:name}}</td>
							<td width="auto" style="line-height:76px"><span class="text-primary">{{:originalPrice}}</td>
							<td width="auto" style="line-height:76px"><span class="text-primary">{{:price}}</td>
							<td width="auto" style="line-height:76px"><span class="text-primary">{{:creator}}</td>
							<td width="auto" style="line-height:76px"><span class="text-primary">{{:createtimeCN}}</td>
							<td width="320px">
							<a href="../views/commodityEdit.jsp?id={{:id}}" data-fancybox-type="iframe" class="btn btn-primary btn-edit">编辑</a>
							<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/commodity/remove?id={{:id}}" class="btn btn-primary">删除</button>
							</td>
						</tr>
				
</script>
</head>
<body>

<!--head-->
<jsp:include page="header.jsp"></jsp:include>
		<!--head end-->
		<div class="clearfix"></div>
		<!--left-->
			<div class="page-container">
            <!-- BEGIN SIDEBAR -->
            <jsp:include page="left_admin.jsp"></jsp:include>
            <!-- END SIDEBAR -->
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <div class="page-content">
                    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
                    <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title">Modal title</h4>
                                </div>
                                <div class="modal-body">
                                    Widget settings form goes here
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn blue">
                                        Save changes
                                    </button>
                                    <button type="button" class="btn default" data-dismiss="modal">
                                        Close
                                    </button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
                    </div>
                    <!-- /.modal -->
                    <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
                    <!-- BEGIN STYLE CUSTOMIZER -->
                   
                    <!-- END STYLE CUSTOMIZER -->
					
					<!--content-->
						<div class="content">
							
							<p class="title">商品管理</p>
							<hr>
								<a class="btn-create btn" href="../views/commodityAdd.jsp" style="margin-top:0px;">
									添加商品
								</a>
							
							<!--搜索框-->
							<div id="searchbox" class="searchbox" style="right:50px;top:100px">
								<form class="search search_form" id="searchForm" action="#" method="POST">
									<fieldset>
										<label for="searchheader" class="placeholder overlabel">商品名称</label>
										<input autocomplete="off" id="searchheader" value="" type="text" name="q">
										<input type="hidden" name="" value="">
										<div class="show_dropdown">
											<div class="dropdown" style="display: block;">
												<ul>
													<li><label for="type_name">按商品名称</label><input type="radio" id="type_name" name="stype" value="name" checked="checked">
													</li>
												</ul>
												<span class="bl"></span>
												<span class="br"></span>
											</div>
										</div>
										<button type="submit" id="searchBtn" class="search-button"><i class="glyphicon glyphicon-search icon-white"></i></button>
									</fieldset>
								</form>
							</div>
<!-- 							<div class="statusbar"> -->
<!-- 								<a class="filterStatus light">未审核 (<span id="status_unchecked" name="unchecked">0</span>)</a> -->
<!-- 								<a class="filterStatus">已审核 (<span id="status_checked" name="checked">0</span>)</a> -->
<!-- 								<a class="filterStatus">处理中 (<span id="status_transcoding" name="transcoding">0</span>)</a> -->
<!-- 								<a class="filterStatus">处理失败 (<span id="status_failed" name="failed">0</span>)</a> -->
<!-- 								<a class="filterStatus">已删除 (<span id="status_deleted" name="deleted">0</span>)</a> -->
<!-- 								<input type="hidden" id="statusChecked" value="unchecked"> -->
<!-- 							</div> -->
							<div id="" class="list-item" style="font-size:16px">
							<div class="list-item-hd">
								<table class="table table-bordered">
									<thead>
										<tr>
											<td width="auto" style="line-height:76px"><span class="text-primary">封面</span></td>
											<td width="auto" style="line-height:76px"><span class="text-primary">商品名称</span></td>
											<td width="auto" style="line-height:76px"><span class="text-primary">原价</span></td>
											<td width="auto" style="line-height:76px"><span class="text-primary">销售价格</span></td>
											<td width="auto" style="line-height:76px"><span class="text-primary">创建人</span></td>
											<td width="auto" style="line-height:76px"><span class="text-primary">创建时间</span></td>
											<td width="auto" style="line-height:76px"><span class="text-primary">操作</span></td>
											
										</tr>
									</thead>	
									<tbody id="queboxCnt"></tbody>
<!-- 									<div id="queboxCnt"></div> -->
								</table>
							</div>
							<div class="pull-right pagination  compact-theme" id="pagnationPaper"></div>
							
				<script type="text/javascript">
				var pagenum = '1';
				
				function changeStatus(t) {
					var obj = $(t),cUrl = obj.attr('data-url');
					$.getJSON(cUrl, function(ret) {
						if (ret.status == 'success') {
							getList();
						} else {
							alert('失败,' + ret.message);
						}
					})
					return false;
				}
				
				$('#searchForm').submit(function(){
					var key = $('input[name="stype"]:checked').val(),obj = {};
					obj[key] = $('#searchheader').val();
					var str = '&'+key+'='+obj[key];
					getList(str);
					return false;
				});
				
				
				//加载列表
				function getList(params) {
					if (params == undefined){
						params = '';
					}
					var page = (typeof pagenum == 'undefined') ? 1 : pagenum;
					var d = dialog({
					    title: '系统提示',
						width : 320,
					    content: '加载中...'
					});
					d.showModal();
// 					$.getJSON('../rest/videoinfo/statusList',function(r) {
// 						if(r.status == 'success') {
// 							$('#status_checked').html(r.data.checked);
// 							$('#status_unchecked').html(r.data.unchecked);
// 							$('#status_deleted').html(r.data.deleted);
// 							$('#status_failed').html(r.data.failed);
// 							$('#status_transcoding').html(r.data.transcoding);
// 						}
// 					});
// 					var filterStatus= $("#statusChecked").val();
					$.getJSON('../rest/commodity/all?pagesize=10&sorts=createtime desc'+params+'&pagenum='+page, function(r) {
						r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);

						if(r.status == 'success' && r.data.length > 0) {
						    var template = $.templates('#queboxTpl');
						    var html = template.render(r.data);
						    $('#queboxCnt').html(html);
						    
						    $(".btn-edit").colorbox({
								iframe : true,
								width : "860px",
								height : "500px",
								opacity : '0.5',
								overlayClose : false,
								escKey : true
							});
						    
						} else if (r.status == 'success' && r.data.length == 0) {
							$('#pagnationPaper').html('');
							$('#queboxCnt').html('<div class="no_data">无搜索结果</div>');
						}
						$(".main").animate({scrollTop: 0}, 1e3);
						
						d.close().remove();
						
					}).done(function(r){//分页
						$('#pagnationPaper').pagination({
							currentPage : r.pageNum,
					        items: r.totalResultCount,
							edges: 3,
					        itemsOnPage : r.pageSize,
					        
							onPageClick : function(pageNumber,event) {
								pagenum = pageNumber;
								getList();
							}
					    });
						
					});
				}
				
				
				
				$(function(){
					function init() {
						getList();
					};
					init();
					$(".statusbar a").click(function(){
						$(this).addClass("light").siblings().removeClass("light");
						$("#statusChecked").val($(this).find("span").attr("name"));
						pagenum = '1';
						setTimeout("getList()",100);
					});
				})
				</script>
					<!--content end-->

                </div>
            </div>
            <!-- END CONTENT -->
        </div>
		<!--left end-->
		<jsp:include page="footer.jsp"></jsp:include>
	</div>

</body>
</html>