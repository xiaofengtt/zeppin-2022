<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商品</title>
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
						<p class="title"><a href="../views/commodityList.jsp">商品管理</a>>><span>添加商品</span></p>
							<div class="ifrcnt container" style="width:100%">
								<div class="bd">
									<div class="alert alert-danger"
										style="display: none; margin: 0 15px 15px;"></div>
									<form id="formsubmit" class="form-horizontal" role="form" action="#" method="post">
										<div class="clearfix">
											<div id="HtmlTpl">
												<div class="control-group">
													<label class="control-label" for="">商品名称</label>
													<div class="controls">
														<input type="text" id="" name="name" value=""><span class="alert alert-danger3" style="display: none; margin: 0 15px 15px;"></span>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">原价</label>
													<div class="controls">
														<input type="text" id="" name="originalPrice" value=""><span class="alert alert-danger1" style="display: none; margin: 0 15px 15px;"></span>
													</div>
												</div>
												
												<div class="control-group">
													<label class="control-label" for="">销售价格</label>
													<div class="controls">
														<input type="text" id="" name="price" value=""><span class="alert alert-danger2" style="display: none; margin: 0 15px 15px;"></span>
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
												
												<div class="control-group">
													<label class="control-label" for="">商品URL</label>
													<div class="controls">
														<input type="text" id="" name="url" value=""><span class="alert alert-danger4" style="display: none; margin: 0 15px 15px;"></span>
													</div>
												</div>
												
												<div class="control-group" id="uploadcon2">
													<label class="control-label" for="">商品360度展示</label>
													<div class="controls">
														<div id="resourceId2"><span>&nbsp;选择文件&nbsp;&nbsp;</span>
														</div>
														<span id="i1"></span><span id="i2" style="padding-left:10%;padding-right:10%"></span><span id="i3"></span>
													</div>
												</div>
												<div id="displayAdd"><input type="hidden" name="displays" value="" /></div>
												
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
								window.location.href="../views/commodityList.jsp";
							}
							function isMoney(obj){
							   if (! obj) return false;
							   return (/^\d+(\.\d{1,2})?$/).test(obj);   
							}
							$(function() {
								$('#formsubmit').submit(function() {
									$('.alert-danger1').hide();
									$('.alert-danger2').hide();
									$('.alert-danger3').hide();
									$('.alert-danger4').hide();
									var name = $('input[name="name"]').val();
									var url = $('input[name="url"]').val();
									var price = $('input[name="price"]').val();
									var oprice = $('input[name="originalPrice"]').val();
									
									if(name == ""){
										$('.alert-danger3').html("请填写名称").show();
										return false;
									}
									
									if(oprice == ""){
										$('.alert-danger1').html("请填写原价").show();
										return false;
									}
									if(price == ""){
										$('.alert-danger2').html("请填写销售价格").show();
										return false;
									}
									
									if(!isMoney(oprice)){
										$('.alert-danger1').html("格式非法（只允许填写整数、小数）").show();
										return false;
									}
									if(!isMoney(price)){
										$('.alert-danger2').html("格式非法（只允许填写整数、小数）").show();
										return false;
									}
									if(url == ""){
										$('.alert-danger4').html("请填写商品URL").show();
										return false;
									}
									var str = $(this).serialize();
									$.post('../rest/commodity/add?' + str, function(data) {
										if (data.status == "success") {
											if(buttonType==1){
												window.top.location.reload(true);
												layer.msg("添加成功！");
											}else{
												window.location.href="../views/commodityList.jsp";
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
										if($('input[name="id"]').length > 0) {
											$('input[name="id"]').val(data.data.id);
										}else {
											$('input[name="cover"]').val(data.data.id);
										}
										layer.msg("上传成功！");
									}else{
										$('.alert-danger').html(data.message).show();
									}
									
								},
							});
							
							$("#resourceId2").uploadFile({
								url:"../rest/resource/displayAdd",
								allowedTypes:"rar,zip",
								maxFileSize:1024*1024*1024*10,
								fileName:"display",
								maxFileCount : 1,
								dragDropStr: "",
								extErrorStr:"文件格式不正确，请上传指定类型类型的视频文件",
								multiple:false,
								showDelete: true,
								showStatusAfterSuccess:false,
								
								showDelete : false,
								showProgress: true,
								deletelStr : '删除',
								showAbort:false,
								onSuccess:function(files,data,xhr)
								{
									if(data.status == 'success'){
										if($('input[name="id"]').length > 0) {
											$('input[name="id"]').val(data.Records.id);
										}else {
											$('input[name="displays"]').val(data.data);
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