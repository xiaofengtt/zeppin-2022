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
					var parent = (url('?parent') != null) ? url('?parent') : '';
					if (params == undefined){
						params = '';
					}
					var page = (typeof pagenum == 'undefined') ? 1 : pagenum;
					var d = dialog({
					    title: '系统提示',
						width : 220,
						height:60,
					    content: '<p style="line-height:50px;">加载中...</p>'
					});
					d.showModal();
					$.getJSON('../front/admin/videoPublish!execute?uid=h0007&category='+parent,function(r) {
						if(r.status == 'success') {
							$('#status_checked').html(r.data.checked);
							$('#status_unchecked').html(r.data.unchecked);
						}
					});
					var filterStatus= $("#statusChecked").val();
					$.getJSON('../front/admin/videoPublish!execute?uid=h0001&pagesize=10&sort=createtime desc'+params+'&pagenum='+page+'&status='+filterStatus+'&category='+parent, function(r) {
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
					var parent = (url('?parent') != null) ? url('?parent') : '';
					$(".btn-create").attr('href','../views/videoPublishAdd.html?cid='+parent);
					function init() {
						var level = (url('?level') != null) ? '' : 1; 
						var navarr = []; 
						$.get('../front/admin/category!execute?uid=a0006&parent='+parent,function(r) {
							if(r.status =='success') {
								for ( var i = 0, l = r.data.length; i < l; i++ ) {
								    var navhtml = ' &gt; <a href="../views/videoPublishList.html?parent='+ r.data[i].id +'&level='+r.data[i].level +'">'+ r.data[i].name +'</a>';
									navarr.push(navhtml);
						
								}
								
							}
						}).done(function(){
							$(".titleList").append(navarr.join(''));	
						});
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