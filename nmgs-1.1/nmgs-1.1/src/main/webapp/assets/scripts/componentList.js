$(document).ready(function() {
	$('#TableContainer').jtable({
		title : '<a href="../views/componentList.html">组件管理</a>',
		messages : zhCN, //Lozalize
		paging : true, //Enable paging
		pageSize : 10, //Set page size (default: 10)
		pageSizes : [ 10, 20, 30, 40, 50 ],
		sorting : true, //Enable sorting
		defaultSorting : 'createtime ASC', //Set default sorting
		dialogShowEffect : 'drop',
		addRecordButton : $('.btn-create'),
		actions : {
			listAction : '../front/admin/component!execute?uid=k0001'
		},
		fields : {
			id : {
				title : '',
				key : true,
				width:'0%',
				list:false
			},
			name : {
				title : '组件名称',
				width: '40%',
				optionsSorting : 'value'
			},
			status: {
				title: '状态',
				width: '40%',
				options : {
					'normal' : '正常',
					'stopped' : '停用'
				},
				defaultValue : 'normal'
			},
			customedit : {
				title: '操作',
				width : '20%',
				edit : false,
				create : false,
				sorting: false,
				visibility : 'fixed',
				display : function(data) {
					var html = '<a class="btn-edit" style="color:#3ebef3" data-fancybox-type="iframe" href="../views/componentEdit.html?id=' + data.record.id + '">编辑</a>';
/*					html = html + '<a onclick="deleteRow(this)" data-id="' + data.record.id + '" style="color:#3ebef3">删除</a>'*/
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
				height : "290px",//300
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
	height : "290px",//300
	opacity : '0.5',
	overlayClose : false,
	escKey : true
});

function deleteRow(t) {
	if (window.confirm("确认删除这条记录吗?")) {
		var obj = $(t),id = obj.attr('data-id');
		$.getJSON('../front/admin/component!execute?uid=k0005&id='+id, function(ret) {
			if (ret.status == 'success') {
				window.location.reload();
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}