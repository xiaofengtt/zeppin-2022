var buttonType;
							function backToList(){
								window.top.location.href="../views/commodityList.html";
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
									$.post('../front/admin/commodity!execute?uid=b0003&' + str, function(data) {
										if (data.status == "success") {
											if(buttonType==1){
												window.top.location.href="../views/commodityAdd.html";
												layer.msg("添加成功！");
											}else{
												window.top.location.href="../views/commodityList.html";
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
								extErrorStr:"文件格式不正确，请上传指定类型类型的文件",
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
											$('input[name="cover"]').val('');
											$('input[name="cover"]').val(data.data.id);
										}
										layer.msg("上传成功！");
									}else{
										$('.alert-danger').html(data.message).show();
										layer.msg(data.message);
									}
									
								},
							});
							
							$("#resourceId2").uploadFile({
								url:"../front/admin/resource!execute?uid=d0002",
								allowedTypes:"rar,zip",
								maxFileSize:1024*1024*1024*10,
								fileName:"display",
								maxFileCount : 1,
								dragDropStr: "",
								extErrorStr:"文件格式不正确，请上传指定类型类型的文件",
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
											$('input[name="displays"]').val('');
											$('input[name="displays"]').val(data.data);
										}
										layer.msg("上传成功！");
									}else{
										$('.alert-danger').html(data.message).show();
										layer.msg(data.message);
									}
									
								}
							});