/**
 * 金币审核管理
 */
var pageNum = 1,
	pageSize = 50;
var statusObj = {
	'normal':'待审核',
	'checked':'已审核',
	'cancel':'未通过',
	'close':'已关闭'
}
$(function(){
	getList();
});
function getList(){
	$.ajax({
        url: '../back/adminOffsetOrder/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize': pageSize,
        	'pageNum': pageNum,
        	'status': 'normal'
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="8%" class="text-center">序号</th>'+
				'<th width="8%" class="text-center">用户ID</th>'+
				'<th width="8%" class="text-center">用户昵称</th>'+
				'<th width="8%" class="text-center">金币变动</th>'+
				'<th width="8%" class="text-center">用户余额</th>'+
				'<th width="8%" class="text-center">操作员</th>'+
				'<th width="12%" class="text-center">操作时间</th>'+
				'<th width="15%" class="text-center">备注</th>'+
				'<th width="8%" class="text-center">状态</th>'+
				'<th width="7%" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var typeStr = '';
				var classColor = "";
				if(r.data[i].type == 'admin_add'){
					typeStr = '+'
				}else if(r.data[i].type == 'admin_sub'){
					typeStr = '-'
				}
				if(r.data[i].status=="normal"){
					classColor = "color-green"
				}else if(r.data[i].status=="checked"){
					classColor = "color-blue"
				}else if(r.data[i].status=="cancel"){
					classColor = ""
				}else if(r.data[i].status=="close"){
					classColor = ""
				}
				var categoryHtml = '<a class="checkBtn">审核</a>';
				tableHtml += '<tr data-showId="'+r.data[i].frontUser+'">'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center showDetail">'+r.data[i].frontUserShowId+'</td>'
					+'<td class="text-center showDetail">'+r.data[i].frontUserNickname+'</td>'
					+'<td class="text-center">'+typeStr+(r.data[i].dAmount).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].frontUserBalance).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].operatorName==null?'':r.data[i].operatorName)+'</td>'
					+'<td class="text-center">'+(r.data[i].operattime==null?'':formatDate(r.data[i].operattime))+'</td>'
					+'<td class="text-center">'+(r.data[i].remark==null?'':r.data[i].remark)+'</td>'
					+'<td class="text-center '+classColor+'">'+statusObj[r.data[i].status]+'</td>'
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
				tableHtml += '<tr><td colspan="10" align="center">暂无相关数据</td></tr>';
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
	$.ajax({
        url: '../back/adminOffsetOrder/check',
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
layui.use(['table', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
});