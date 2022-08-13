$(document).ready(function() {
	$('#TableContainer').jtable({
		title : '<a href="../views/provinceList.html">接入管理</a>',
		messages : zhCN, //Lozalize
		paging : true, //Enable paging
		pageSize : 10, //Set page size (default: 10)
		pageSizes : [ 10, 20, 30, 40, 50 ],
		defaultSorting : 'id ASC', //Set default sorting
		dialogShowEffect : 'drop',
		actions : {
			listAction : '../front/admin/province!execute?uid=l0001'
		},
		fields : {
			id : {
				title : '',
				key : true,
				width:'0%',
				list:false
			},
			name : {
				title : '地区名称',
				width: '20%',
				optionsSorting : 'value'
			},
			templateName : {
				title : '模板',
				width: '20%',
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
			customedit : {
				title: '操作',
				width : '10%',
				edit : false,
				create : false,
				sorting: false,
				visibility : 'fixed',
				display : function(data) {
					var html = '<a class="btn-edit" style="color:#3ebef3" data-fancybox-type="iframe" href="../views/provinceEdit.html?id=' + data.record.id + '">编辑</a>';
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
				height : "600px",//300
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		}
	});
		
	$('#TableContainer').jtable('load');
})

$(".btn-create").colorbox({
	iframe : true,
	width : "860px",//860
	height : "600px",//300
	opacity : '0.5',
	overlayClose : false,
	escKey : true
});

function deleteRow(t) {
	if (window.confirm("确认删除这条记录吗?")) {
		var obj = $(t),id = obj.attr('data-id');
		$.getJSON('../front/admin/province!execute?uid=l0005&id='+id, function(ret) {
			if (ret.status == 'success') {
				window.location.reload();
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}