var buttonType;
							function backToList(){
								var parent = (url('?cid') != null) ? url('?cid') : '';
								$("#contentIframe",window.top.document).attr("src","../views/videoPublishList.html?parent="+parent);
							}
							$(function() {
								var parent = (url('?cid') != null) ? url('?cid') : '';
								$("#category").val(parent);
								var html="<option name='video' value='0'>请选择</option>";
								$.getJSON('../front/admin/videoinfo!execute?uid=g0001&pagesize=&sort=createtime desc&pagenum=&status=checked', function(r) {
									
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
									$.post('../front/admin/videoPublish!execute?uid=h0003&' + str, function(data) {
										if (data.status == "success") {
											if(buttonType==1){
												$("#contentIframe",window.top.document).attr("src","../views/videoPublishList.html");
												layer.msg("添加成功！");
											}else{
												$("#contentIframe",window.top.document).attr("src","../views/videoPublishList.html?parent="+parent);
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
								url:"../front/admin/resource!execute?uid=d0001",
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