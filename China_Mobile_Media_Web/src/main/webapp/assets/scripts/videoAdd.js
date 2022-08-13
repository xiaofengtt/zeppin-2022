var buttonType;
							function backToList(){
								window.location.href="../views/videoList.html";
							}
							$(function() {
								$('#formsubmit').submit(function() {
									$('.btn-single').attr("disabled",true);
									var str = $(this).serialize();
									$.post('../front/admin/videoinfo!execute?uid=g0004&' + str, function(data) {
										if (data.status == "success") {
											if(buttonType==1){
												window.top.location.href="../views/videoAdd.html";
												layer.msg("添加成功！");
											}else{
												window.top.location.href="../views/videoList.html";
												layer.msg("添加成功！");
											}
										} else {
											$('.alert-danger').html(data.message).show();
											$('.btn-single').attr("disabled",false);
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