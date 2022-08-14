/**
 * 
 */
var pageNum = 1,
	pageSize = 50;
var statusObj = {
		'normal':'待审核',
		'checked':'已审核',
		'nopass':'未通过'
	}
$(function(){
	getList();
});
layui.use(['table','form', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
	form.on('select(status)',function(data){
		pageNum = 1;
		getList(data.value);
	})
});
function getList(status){
	var datas = {
    	'pageSize':pageSize,
    	'pageNum':pageNum,
    	'status':status?status:$("select").val()
    }
	$.ajax({
        url: '../back/userEdit/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="6%" class="text-center">序号</th>'+
				'<th width="8%" class="text-center">用户ID</th>'+
				'<th width="14%" class="text-center">修改前昵称</th>'+
				'<th width="14%" class="text-center">修改后昵称</th>'+
				'<th width="8%" class="text-center">审核状态</th>'+
				'<th width="14%" class="text-center">修改时间</th>'+
				'<th width="14%" class="text-center">审核时间</th>'+
				'<th width="15%" class="text-center">审核原因</th>'+
				'<th width="7%" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var categoryHtml = '';
				var classColor = "";
				if(r.data[i].status=='normal'){
					categoryHtml = '<a class="checkBtn">审核</a>';
				}
				if(r.data[i].status=="normal"){
					classColor = "color-green"
				}else if(r.data[i].status=="checked"){
					classColor = "color-blue"
				}else if(r.data[i].status=="nopass"){
					classColor = "color-orange"
				}
				tableHtml += '<tr data-showId="'+r.data[i].frontUser+'">'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center showDetail">'+r.data[i].frontUserShowId+'</td>'
					+'<td class="text-center showDetail">'+r.data[i].infoBefore+'</td>'
					+'<td class="text-center">'+r.data[i].infoAfter+'</td>'
					+'<td class="text-center '+classColor+'">'+statusObj[r.data[i].status]+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
					+'<td class="text-center">'+(r.data[i].operattime==null?'-':formatDate(r.data[i].operattime))+'</td>'
					+'<td class="text-center">'+(r.data[i].reason==null?'-':r.data[i].reason)+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'">'+categoryHtml+'</td>'
					+'</tr>'; 
			}
			if (r.totalPageCount!=0) {
			    $('#pageTool').Paging({
			        prevTpl: "<",
			        nextTpl: ">",
			        pagesize: pageSize,
			        count: r.totalResultCount,
			        current: pageNum,
			        toolbar: true,
			        pageSizeList: [50, 200, 1000],
			        callback: function(page, size, count) {
			            pageNum = page;
			            getList();
			            flag = false;
			            document.body.scrollTop = document.documentElement.scrollTop = 0;
			        }
			    });
			    $(".ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
			    $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
			}else{
				$("#pageTool").hide();
			}
			$(".ui-select-pagesize").unbind("change").change(function() {
			    pageSize = $(this).val();
			    pageNum = 1;
			    getList();
			});
			if(r.data.length==0){
				tableHtml += '<tr><td colspan="9" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".checkBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.prompt({
					title: '审核',
					formType: 0,
					btnAlign: 'c',
					btn: ['通过','不通过'],
					yes: function(value, index, elem){
						checkData(dataUuid,'checked',$(".layui-layer-input").val());
					},
					btn2: function(value, index, elem){
						checkData(dataUuid,'nopass',$(".layui-layer-input").val());
					}
				});
				return false;
			});
			$(".showDetail").click(function(){
				layer.open({
					type: 2, 
					title:false, 
					area: ['90%', '90%'],
					content: ['userDetail.html?uuid='+$(this).parent().attr("data-showId")] 
				});
				return false;
			});
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
	}).fail(function(r) {
	    layer.msg("error", {
	        time: 2000
	    },function(){
	    	window.location.href=window.location.href;
	    });
	});   	
}
//审核
function checkData(uuid,status,reason){
	if(reason.replace(/(^\s*)|(\s*$)/g, "")==''){
		layer.msg('审核原因不能为空');
		return false;
	}
	$.ajax({
        url: '../back/userEdit/check',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'status': status,
        	'reason': reason
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg('审核成功', {
				time: 1000 
			}, function(){
				getList();
				layer.closeAll();
			}); 
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	window.location.href=window.location.href;
        });
    }); 
}