<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频列表</title>

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
button.btn{ margin-top:5px;}
a.btn{ margin-top:5px;}
.btn{ border:none;}
.content{ padding-bottom:100px;}
.content p.title{ color:#428bca; font-size:15px;font-weight:bold;margin:10px 0px;}
hr{border-top:2px solid #adb5bb;margin:10px 0px;}
.statusbar{ margin-top:20px;font-weight:bolder;}
.statusbar a.filterStatus{ display:inline-block; margin:0px; padding:10px 15px;color:#9c9c9c; cursor:pointer; border:1px solid #ccc;border-radius:3px;}
.statusbar a.light{ background:#ff6525; color:#f0f0f0;}
</style>

<script id="queboxTpl" type="text/template">
		<div id="{{:id}}" class="list-item" style="font-size:16px">
			<div class="list-item-hd">
				<table class="table table-bordered" style="margin-bottom:0px;">
					<tbody>
						<tr>
							<td width="auto" style="line-height:40px;text-align:left;padding-left:30px;"><span class="text-primary">标题：</span>{{:title}}</td>
							<td width="100px"style="line-height:40px;">{{:statusCN}}</td> 
							<td width="320px">
							{{if status=='unchecked'}}
								<a href="../views/videoEdit.jsp?id={{:id}}" data-fancybox-type="iframe" class="btn btn-primary btn-edit">编辑</a>
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/changeStatus?status=checked&id={{:id}}" class="btn btn-primary">审核通过</button>
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/delete?id={{:id}}" class="btn btn-primary">删除</button>
								<a href="../views/videoPoint.jsp?id={{:id}}" class="btn btn-primary">关联商品</a>
							{{else status=='checked'}}
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/changeStatus?status=unchecked&id={{:id}}" class="btn btn-primary">取消审核</button>
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/delete?id={{:id}}" class="btn btn-primary">删除</button>
								<a href="../views/videoPoint.jsp?id={{:id}}" class="btn btn-primary">关联商品</a>
							{{else status=='transcoding'}}
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/changeStatus?status=uploaded&id={{:id}}" class="btn btn-primary">重新处理</button>
							{{else status=='failed'}}
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/changeStatus?status=uploaded&id={{:id}}" class="btn btn-primary">重新处理</button>
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/changeStatus?status=destroy&id={{:id}}" class="btn btn-primary">彻底删除</button>
							{{else status=='deleted'}}
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/changeStatus?status=unchecked&id={{:id}}" class="btn btn-primary">恢复</button>
								<button onclick="changeStatus(this)" data-id="{{:id}}" data-url="../rest/videoinfo/changeStatus?status=destroy&id={{:id}}" class="btn btn-primary">彻底删除</button>
							{{/if}}
							</td>
						</tr>
					</tbody>	
				</table>
			</div>
				
			<div class="list-item-bd clearfix">
										
				<div class="list-item-col list-5-05" style="text-align:center;vertical-align:middle;padding-left:0">
					<img src="../{{:thumbnail}}" style="max-width:90%;max-height:200px;">
				</div>
				<div class="list-item-col list-5-1">
					<ul>
						<li class="text-toolong" title="{{:tag}}"><span class="text-primary">标签：</span> {{:tag}}</li>
						<li class="text-toolong" title="{{:context}}"><span class="text-primary">描述：</span> {{:context}}</li>
					</ul>
				</div>
				<div class="list-item-col list-5-1">
					<ul>
						<li><span class="text-primary">上传者：</span> {{:creatorName}}</li>
						<li><span class="text-primary">上传时间：</span> {{:createtimeCN}}</li>
						<li><span class="text-primary">播放时长：</span> {{:timeLength}}</li>
					</ul>
				</div>
				<div class="list-item-col list-5-1">
					<ul>
						<li><span class="text-primary">作者：</span> {{:author}}</li>
						<li class="text-toolong" title="{{:comment}}"><span class="text-primary">来源：</span> {{:source}}</li>
						<li class="text-toolong" title="{{:meaning}}"><span class="text-primary">版权方：</span> {{:copyright}}</li>
					</ul>
				</div>
			</div>
		</div>
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
							
							<p class="title">视频管理</p>
							<hr>
								<a class="btn-create btn" href="../views/videoAdd.jsp" style="margin-top:0px;">
									上传视频
								</a>
							
							<!--搜索框-->
							<div id="searchbox" class="searchbox" style="right:50px;top:100px">
								<form class="search search_form" id="searchForm" action="#" method="POST">
									<fieldset>
										<label for="searchheader" class="placeholder overlabel">标题</label>
										<input autocomplete="off" id="searchheader" value="" type="text" name="q">
										<input type="hidden" name="" value="">
										<div class="show_dropdown">
											<div class="dropdown" style="display: block;">
												<ul>
													<li><label for="type_name">按标题</label><input type="radio" id="type_name" name="stype" value="title" checked="checked">
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
							<div class="statusbar">
								<a class="filterStatus light">未审核 (<span id="status_unchecked" name="unchecked">0</span>)</a>
								<a class="filterStatus">已审核 (<span id="status_checked" name="checked">0</span>)</a>
								<a class="filterStatus">待处理 (<span id="status_uploaded" name="uploaded">0</span>)</a>
								<a class="filterStatus">处理中 (<span id="status_transcoding" name="transcoding">0</span>)</a>
								<a class="filterStatus">处理失败 (<span id="status_failed" name="failed">0</span>)</a>
								<a class="filterStatus">已删除 (<span id="status_deleted" name="deleted">0</span>)</a>
								<input type="hidden" id="statusChecked" value="unchecked">
							</div>
							<div id="queboxCnt"></div>
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
					$.getJSON('../rest/videoinfo/statusList',function(r) {
						if(r.status == 'success') {
							$('#status_checked').html(r.data.checked);
							$('#status_unchecked').html(r.data.unchecked);
							$('#status_deleted').html(r.data.deleted);
							$('#status_uploaded').html(r.data.uploaded);
							$('#status_failed').html(r.data.failed);
							$('#status_transcoding').html(r.data.transcoding);
						}
					});
					var filterStatus= $("#statusChecked").val();
					$.getJSON('../rest/videoinfo/list?pagesize=10&sort=createtime desc'+params+'&pagenum='+page+'&status='+filterStatus, function(r) {
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