<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传视频</title>
<link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/themes/light.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">
<link href="../assets/css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link rel="stylesheet" href="../assets/css/uploadfile.css">
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
<script src="../assets/plugins/jquery.uploadfile.video.js"></script>
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
							<div class="ifrcnt container" style="width:100%">
								<div class="bd">
									 <div class="alert alert-danger"
										style="display: none; margin: 0 15px 15px;"></div> 
									<form id="formsubmit" class="form-horizontal" role="form" action="#" method="post">
										<div class="clearfix">
											<div id="HtmlTpl">
												<div class="control-group" id="uploadcon" style="border:2px dotted #A5A5C7">
													<div class="controls"style="text-align:center;margin-left: 0px">
														<div id="resourceId"><span>&nbsp;上传视频&nbsp;&nbsp;</span>
														</div>
														<span id="i1"></span><span id="i2" style="padding-left:20%;padding-right:20%"></span><span id="i3"></span>
													</div>
												</div>
												<div id="resourceAdd"></div>
												<div class="control-group">
													<label class="control-label" for="">标题</label>
													<div class="controls">
														<input type="text" id="" name="title" value="">
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">标签</label>
													<div class="controls">
														<input type="text" id="" name="tag" value="">
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">描述</label>
													<div class="controls">
														<textarea id="" name="context" rows="3"></textarea>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">来源</label>
													<div class="controls">
														<input type="text" id="" name="source" value="">
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">版权方</label>
													<div class="controls">
														<input type="text" id="" name="copyright" value="">
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">作者</label>
													<div class="controls">
														<input type="text" id="" name="author" value="">
													</div>
												</div>
											</div>
										</div>
						
										<div class="row actionbar">
											<div class="offset7">
												<button class="btn" type="submit" onclick="buttonType=0">确定</button>
												<button id="colorboxcancel" onclick="backToList();" class="btn btn-default" type="button">返回</button>
												<button class="btn" type="submit" onclick="buttonType=1">继续添加</button>
											</div>
										</div>
									</form>
								</div>
						
							</div>
							<script>
							var buttonType;
							function backToList(){
								window.location.href="../views/videoList.jsp";
							}
							$(function() {
								$('#formsubmit').submit(function() {
									var str = $(this).serialize();
									$.post('../rest/videoinfo/add?' + str, function(data) {
										if (data.status == "success") {
											if(buttonType==1){
												window.top.location.reload(true);
												layer.msg("添加成功！");
											}else{
												window.location.href="../views/videoList.jsp";
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
								allowedTypes:"mp4,avi,mov,3gp,flv,wmv,rmvb",
								maxFileSize:1024*1024*1024*10,
								fileName:"video",
								maxFileCount : 1,
								dragDropStr: "",
								extErrorStr:"文件格式不正确，请以下类型的视频文件：",
								showStatusAfterSuccess:false,
								showDelete : false,
								showProgress: true,
								deletelStr : '删除',
								showAbort:false,
								onSuccess:function(files,data,xhr)
								{
									if(data.status == 'success'){
										if($('input[name="id"]').length > 0) {
											$('input[name="id"]').val(data.data.id);
										}else {
											$("#resourceAdd").append('<input type="hidden" name="originalVideo" value="' + data.data.id + '">');
										}
										layer.msg("上传成功！");
									}else{
										$('.alert-danger').html(data.message).show();
									}
								}
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