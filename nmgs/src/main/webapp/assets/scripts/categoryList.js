$(document).ready(function() {
	var parent = (url('?parent') != null) ? url('?parent') : '';
	var component = (url('?component') != null) ? url('?component') : '';
	var level = (url('?level') != null) ? '' : 1; 
	var navarr = []; 
	$("#createBtn").attr("href","../views/categoryAdd.html?parent="+parent+"&component="+component);
	$.get('../front/admin/component!execute?uid=k0001&status=normal',function(r) {
		if(r.status =='success') {
			var html = '<option value="">全部</option>';
			if(r.data.length>0){
				$.each(r.data,function(i,v){
					html += '<option value="'+v.id+'" '+(v.id==component?'selected':'')+'>'+v.name+'</option>';
				})
			}
			$('#componentSelect').html(html);
		}
	})
	$.get('../front/admin/category!execute?uid=a0006&parent='+parent,function(r) {
		if(r.status =='success') {
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
			    var navhtml = ' &gt; <a href="../views/categoryList.html?parent='+ r.data[i].id +'&level='+r.data[i].level +'">'+ r.data[i].name +'</a>';
				navarr.push(navhtml);
			}
		}
	}).done(function(){
		$('#TableContainer').jtable({
			title : '<a href="../views/categoryList.html">栏目管理</a> '+ navarr.join(''),
			messages : zhCN, //Lozalize
			paging : true, //Enable paging
			pageSize : 10, //Set page size (default: 10)
			pageSizes : [ 10],
			sorting : true, //Enable sorting
			defaultSorting : 'scode ASC', //Set default sorting
			dialogShowEffect : 'drop',
			addRecordButton : $('.btn-create'),
			actions : {
				listAction : '../front/admin/category!execute?uid=a0001&parent='+ parent +'&level='+level +'&component='+component
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
					width:'20%'
				},
				name : {
					title : '栏目名称',
					width: '30%',
					optionsSorting : 'value'
				},
				status: {
					title: '状态',
					width: '20%',
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
							var html ='<a href="../views/categoryList.html?parent=' + data.record.id+'&level='+data.record.level+'&component='+data.record.component +'" style="color:#3ebef3">管理下级栏目</a>';
							html = html + '<a class="btn-edit" style="color:#3ebef3" data-fancybox-type="iframe" href="../views/categoryEdit.html?id=' + data.record.id + '">编辑</a>'
							html = html + '<a onclick="deleteRow(this)" data-id="' + data.record.id + '" style="color:#3ebef3">删除</a>'
						return html;
					}
				}
			},
			
			columnResizable : false,
			formClosed : function(event,data) {},
			recordsLoaded :function (data) {
				$(".btn-edit").colorbox({
					iframe : true,
					width : "860px",//860
					height : "330px",//300
					opacity : '0.5',
					overlayClose : false,
					escKey : true
				});
			}
		});
		
	$('#TableContainer').jtable('load');
	});
})

function changeComponent(t){
	var component = $(t).val();
	window.location.href="../views/categoryList.html?component="+component;
}

$(".btn-create").colorbox({
	iframe : true,
	width : "860px",//860
	height : "330px",//300
	opacity : '0.5',
	overlayClose : false,
	escKey : true
});

function deleteRow(t) {
	if (window.confirm("确认删除这条记录吗?")) {
		var obj = $(t),id = obj.attr('data-id');
		$.getJSON('../front/admin/category!execute?uid=a0005&id='+id, function(ret) {
			if (ret.status == 'success') {
				window.location.reload();
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}