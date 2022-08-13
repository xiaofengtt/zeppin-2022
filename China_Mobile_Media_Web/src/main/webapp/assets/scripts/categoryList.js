$(document).ready(
									function() {
										var parent = (url('?parent') != null) ? url('?parent') : '';
										var level = (url('?level') != null) ? '' : 1; 
										var navarr = []; 
										$.get('../front/admin/category!execute?uid=a0006&parent='+parent,function(r) {
											if(r.status =='success') {
												for ( var i = 0, l = r.data.length; i < l; i++ ) {
												    var navhtml = ' &gt; <a href="../views/categoryList.html?parent='+ r.data[i].id +'&level='+r.data[i].level +'">'+ r.data[i].name +'</a>';
													navarr.push(navhtml);
										
												}
												
											}
											
										}).done(function(){
											$('#TableContainer')
												.jtable(
														{
															title : '<a href="../views/categoryList.html">栏目管理</a> '+ navarr.join(''),
															messages : zhCN, //Lozalize
															paging : true, //Enable paging
															pageSize : 10, //Set page size (default: 10)
															pageSizes : [ 10, 20, 30, 40, 50 ],
															sorting : true, //Enable sorting
															defaultSorting : 'scode ASC', //Set default sorting
															dialogShowEffect : 'drop',
															addRecordButton : $('.btn-create'),
															actions : {
																listAction : '../front/admin/category!execute?uid=a0001&parent='+ parent +'&level='+level,
																updateAction : '../front/admin/category!execute?uid=a0004',
																deleteAction :'../front/admin/category!execute?uid=a0005',
																createAction : '../front/admin/category!execute?uid=a0003&parent='+ parent
															},
															fields : {
																id : {
																	title : '',
																	key : true,
																	width:'0%',
																	list:false
																},
																scode : {
																	title : '序号',
																	edit: false,
												                    create: false,
																	width:'5%'
																},
																name : {
																	title : '栏目名称',
																	width: '8%',
																	optionsSorting : 'value'
																	
																},
																status: {
																	title: '状态',
																	options : {
																		'normal' : '正常',
																		'stopped' : '停用'
																		
																	},
																	defaultValue : 'normal'
																	
																	
																},
																addviewchild: {
												                    title: '操作',
												                    width: '10%',
												                    sorting: false,
												                    edit: false,
												                    create: false,
												                    display: function (data) {
																			var html ='<a href="../views/categoryList.html?parent=' + data.record.id+'&level='+data.record.level +'">管理下级栏目</a>';
																		return html;
																	}
									
																}
					
														
															},
															
															columnResizable : false,
															deleteConfirmation: function(data) {
															  data.deleteConfirmMessage = '确定要停用个栏目?';
															},
															formClosed : function(event,data) {
																
															},
															recordsLoaded :function (data) {
										
															}
															
														});

										$('#TableContainer').jtable('load');
		
										
									});
								})