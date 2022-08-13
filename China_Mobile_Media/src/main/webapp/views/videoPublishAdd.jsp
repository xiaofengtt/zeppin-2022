<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发布视频</title>
<link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/themes/light.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">
<link href="../assets/css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link rel="stylesheet" href="../assets/css/uploadfile_n.css">
<link rel="stylesheet" href="../assets/css/bootstrap2.css">
<link rel="stylesheet" href="../assets/css/main.css" type="text/css"/>
<link rel="stylesheet" href="../assets/css/columLists.css" type="text/css"/>

<script src="../assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/scripts/app.js" type="text/javascript"></script>
<script type="text/javascript" src="../app/js/index.js"></script>
<script src="../assets/plugins/jquery-ui.js"></script>
<script src="../assets/plugins/url.min.js"></script>
<script src="../assets/plugins/jquery.uploadfile.min.js"></script>
<script src="../assets/plugins/layer-v2.2/layer/layer.js" type="text/javascript"></script>
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
						<p class="title"><span>发布视频</span></p>
							<div class="ifrcnt container" style="width:100%">
								<div class="bd">
									<div class="alert alert-danger"
										style="display: none; margin: 0 15px 15px;"></div>
									<form id="formsubmit" class="form-horizontal" role="form" action="#" method="post">
										<input type="hidden" id="category" name="category" value="">
										<div class="clearfix">
											<div id="HtmlTpl">
												<div class="control-group">
													<label class="control-label" for="">添加视频</label>
													<div class="controls">
														<select name="video" id="video">
														</select>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">发布标题</label>
													<div class="controls">
														<input type="text" id="" name="title" value="">
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">发布短标题</label>
													<div class="controls">
														<input type="text" id="" name="shortTitle" value="">
													</div>
												</div>
												
												<div class="control-group" id="uploadcon1">
													<label class="control-label" for="">封面资源</label>
													<div class="controls">
														<div id="resourceId"><span>&nbsp;选择文件&nbsp;&nbsp;</span>
														</div>
													</div>
												</div>
												<div id="resourceAdd"><input type="hidden" name="cover" value=""></div>
												
											</div>
										</div>
						
										<div class="row actionbar">
											<div class="offset7">
												<button class="btn" type="submit">确定</button>
												<button id="colorboxcancel" onclick="backToList();"
													class="btn btn-default" type="button">返回</button>
												<button class="btn" type="submit" onclick="buttonType=1">继续添加</button>
											</div>
										</div>
									</form>
								</div>
						
							</div>
							<script>
							var buttonType;
							function backToList(){
								var parent = (url('?cid') != null) ? url('?cid') : '';
								window.location.href="../views/videoPublishList.jsp?parent="+parent;
							}
							$(function() {
								var parent = (url('?cid') != null) ? url('?cid') : '';
								$("#category").val(parent);
								var html="<option name='video' value='0'>请选择</option>";
								$.getJSON('../rest/videoinfo/list?pagesize=&sort=createtime desc&pagenum=&status=checked', function(r) {
									
									if(r.status == 'success' && r.data.length > 0) {
										
										$.each(r.data,function(i,v){
											html+="<option name='video' value='"+v.id+"'>"+v.title+"</option>"
										})
									}else{
										$('.alert-danger').html(r.message).show();
									}
								}).done(function(){
									$("#video").append(html);
								});
								
								$('#formsubmit').submit(function() {
									var str = $(this).serialize();
									$.post('../rest/videopublish/add?' + str, function(data) {
										if (data.status == "success") {
											if(buttonType==1){
												window.top.location.reload(true);
												layer.msg("添加成功！");
											}else{
												window.location.href="../views/videoPublishList.jsp?parent="+parent;
												layer.msg("添加成功！");
											}
										} else {
											$('.alert-danger').html(data.message).show();
										}
									})
									return false;
								});

								//项目类型 helper 函数
								$(document).on("click", function(e) {
									if (!$(e.target).parents().is('.ufc')) {
										$('.uul').hide();
									}
									if(!$(e.target).parent().is('#bido') && !$(e.target).is('#clId') && !$(e.target).parent().is('.listnode')){
										$('#clListBox').hide();
									}
								});
								
							});
							
							$("#resourceId").uploadFile({
								url:"../rest/resource/add",
								allowedTypes:"jpg,png,jpeg,bmp,tiff",
								maxFileSize:1024*1024*1024*10,
								fileName:"video",
								maxFileCount : 1,
								dragDropStr: "",
								extErrorStr:"文件格式不正确，请上传指定类型类型的视频文件",
								multiple:false,
								showDelete: true,
								showStatusAfterSuccess:true,
								showProgress: true,
								deletelStr : '删除',
								showAbort:false,
								showDone:false,
								maxFileCountErrorStr: "文件数量过多，请先删除。",
								onSuccess:function(files,data,xhr)
								{
									if(data.status == 'success'){
										$('input[name="cover"]').val(data.data.id);
										
										layer.msg("上传成功！");
									}else{
										$('.alert-danger').html(data.message).show();
									}
									
								},
							});
							
							</script>
						</div>
					<!--content end-->

                </div>
            </div>
            <!-- END CONTENT -->
        </div>
		<!--left end-->
		<jsp:include page="footer.jsp"></jsp:include>


</body>
</html>