$(document).ready(function() {
	var province = (url('?province') != null) ? url('?province') : '';
	var provinceName = "";
	var html = '<option value="">全部</option>';
	html += '<option value="unchecked">未审核</option>';
	html += '<option value="checked">已通过</option>';
	html += '<option value="nopass">未通过</option>';
	$('#componentSelect').html(html);
	$.getJSON('../front/admin/province!execute?uid=l0002&id='+province, function(r) {
		if(r.status == 'success') {
			provinceName = "（"+r.data.name+"）";
		} else{
			window.location.href = "../views/sourceMain.html";
		}
	}).done(function(){
		$('#TableContainer').jtable({
			title : '<a>留言管理</a>' + provinceName ,
			messages : zhCN, //Lozalize
			paging : true, //Enable paging
			pageSize : 10, //Set page size (default: 10)
			pageSizes : [ 10, 20, 30, 40, 50 ],
			sorting : true, //Enable sorting
			defaultSorting : 'createtime DESC', //Set default sorting
			dialogShowEffect : 'drop',
			actions : {
				listAction : '../front/admin/leaveMessage!execute?uid=o0001&province='+province,
			},
			
			fields : {
				id : {
					title : '',
					key : true,
					width:'0%',
					list:false
				},
				title : {
					title : '发布标题',
					edit: false,
                    create: false,
					width:'15%',
					sorting: false,
				},
				name : {
					title : '留言用户',
					width: '13%',
					edit: false,
					sorting: false,
				},
				content : {
					title : '内容',
					width: '32%',
					edit: false,
				},
				createtimeCN : {
					title : '留言时间',
					width: '10%',
					edit: false,
                    create: false,
				},
				status: {
					title: "状态",
					options : {
						'checked' : '审核通过',
						'unchecked' : '未审核',
						'nopass' : '审核未通过',
						}
					},
					
				addviewchild: {
                    title: '操作',
                    width: '20%',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (data) {
                    	var add ='';
                    if(data.record.status!="delete"){
                    	if(data.record.status!="nopass"){
                    		if(data.record.status=="unchecked")
                    		{                			
                    			add +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="checked" style="color:#3ebef3">审核通过</a>';
							 	add +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="nopass" style="color:#3ebef3">审核不通过</a>';
							 	add +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="delete" style="color:#3ebef3">删除</a>';
                    			
                    		}else
                    			{
                    			add +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="nopass" style="color:#3ebef3">审核不通过</a>';
                    			add +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="delete" style="color:#3ebef3">删除</a>';
                    			}
                    		}else{
                    			add +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="checked" style="color:#3ebef3">审核通过</a>';
                    			add +='<a onclick="edit(this)" data-id="' + data.record.id + '" data-changestatus="delete" style="color:#3ebef3">删除</a>';
                            }
                    	}	
                    	else{
                    }
						return add;
					}
				}
			},
		});	
		
		$('#searchBtn').click(function (e) {
		    e.preventDefault();
		    $('#TableContainer').jtable('load', {
		    	search: $('#searchheader').val(),
		    	status: $('#componentSelect').val(),
		    });
		});
		
		$('#componentSelect').change(function (e) {
		    e.preventDefault();
		    $('#TableContainer').jtable('load', {
		    	search: $('#searchheader').val(),
		    	status: $('#componentSelect').val(),
		    });
		});
		
		$('#TableContainer').jtable('load');
	});
	
})


function edit(t){
	var obj = $(t),id = obj.attr('data-id'),changestatus = obj.attr('data-changestatus');
	if(changestatus=='delete'){
		if (window.confirm("确认删除这条记录吗?")){
			$.getJSON('../front/admin/leaveMessage!execute?uid=o0002&status='+changestatus+'&id='+id, function(ret) {
				if (ret.status == 'success') {
					$('#TableContainer').jtable('load',{
						search: $('#searchheader').val(),
						status:$('#componentSelect').val(),
					});
				} else {
					alert('失败,' + ret.message);
				}	
			})
		}
	}
	else{
		$.getJSON('../front/admin/leaveMessage!execute?uid=o0002&status='+changestatus+'&id='+id, function(ret) {
			if (ret.status == 'success') {		
				$('#TableContainer').jtable('load',{
					search: $('#searchheader').val(),
					status:$('#componentSelect').val(),
				});
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}
