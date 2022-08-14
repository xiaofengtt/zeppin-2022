$(document).ready(function() {
	var province = (url('?province') != null) ? url('?province') : '';
	var provinceName = "";
	$("#createBtn").attr("href","../views/updateApp.html?province="+province);
	$.get('../front/admin/component!execute?uid=k0001&status=normal',function(r) {
		if(r.status =='success') {
			var html = '<option value="634ce983-ac42-46cd-9984-b927c6231a07">Android</option>';
			$('#componentSelect').html(html);
		}
	})
	$.getJSON('../front/admin/province!execute?uid=l0002&id='+province, function(r) {
		if(r.status == 'success') {
			provinceName = "（"+r.data.name+"）";
		} else{
			window.location.href = "../views/sourceMain.html";
		}
	}).done(function(){
		$.get('../front/admin/appVersion!execute?uid=x0002&province='+province+'&component=634ce983-ac42-46cd-9984-b927c6231a07&status=normal',function(r) {
			if(r.status =='success') {
			}
		}).done(function(){
			$('#TableContainer').jtable({
				title : '版本管理'+provinceName,
				messages : zhCN, //Lozalize
				paging : true, //Enable paging
				pageSize : 10, //Set page size (default: 10)
				pageSizes : [ 10 ],
				sorting : true, //Enable sorting
				defaultSorting : 'createtime DESC', //Set default sorting
				addRecordButton : $('.btn-create'),
				actions : {
					listAction : '../front/admin/appVersion!execute?uid=x0002&province='+province+'&component=634ce983-ac42-46cd-9984-b927c6231a07&status=normal'
				},
				fields : {
					versionna : {
						title : '',
						key : true,
						width:'0%',
						list:false,
					},
					versionnu : {
						title : '版本号',
						edit: false,
	                    create: false,
						width:'20%'
					},
					versionna : {
						title : '版本名称',
						width: '20%',
						sorting: false,
						optionsSorting : 'value',
						
					},
					createtimeCN : {
						title : '上传时间',
						width: '20%',
						sorting: false,
					},
					status: {
						title: '状态',
						width: '20%',
						sorting: false,
						options : {
							'normal' : '正常',
							'stopped' : '停用'
						},
						defaultValue : 'normal'
					},
					addviewchild: {
	                    title: '操作',
	                    width: '20%',
	                    sorting: false,
	                    edit: false,
	                    create: false,
	                    display: function (data) {
	                    	var html ='';
	                    	if(data.record.status=='normal'){
	                    		html +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="stopped" style="color:#3ebef3">停用</a>';
	                    	}else{
	                    		html +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="normal" style="color:#3ebef3">启用</a>';
	                    	}
							return html;
						}
					}
				},
				
				columnResizable : false,
				formClosed : function(event,data) {},
			});
			
			$('#TableContainer').jtable('load');
		});
	});
})

$(".btn-create").colorbox({
	iframe : true,
	width : "790px",//860
	height : "550px",//300
	opacity : '0.5',
	overlayClose : false,
	escKey : true
});

function edit(t){
	var obj = $(t),id = obj.attr('data-id'),changestatus = obj.attr('data-changestatus');
	if(changestatus=='stopped'){
		if (window.confirm("确认停用此版本?将不可恢复！")){
			$.getJSON('../front/admin/appVersion!execute?uid=x0003&status='+changestatus+'&id='+id, function(ret) {
				if (ret.status == 'success') {
					$('#TableContainer').jtable('load',{
					});
				} else {
					alert('失败,' + ret.message);
				}	
			})
		}
	}
	else{
		$.getJSON('../front/admin/appVersion!execute?uid=x0003&status='+changestatus+'&id='+id, function(ret) {
			if (ret.status == 'success') {		
				$('#TableContainer').jtable('load',{
				});
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}
