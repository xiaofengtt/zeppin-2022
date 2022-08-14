/**
 * 汇率管理
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
    pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var statusType = {
	'normal':'待确认',
	'disable':'已失效',
	'checked':'已确认'
}
$(function(){
	getList();	
});
layui.use(['table', 'layer','laydate','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,layedit = layui.layedit
		,laydate = layui.laydate
		,$ = layui.jquery
		,element = layui.element;
	
	laydate.render({
		elem: '.worktimeBegin',
		type: 'date',
		theme: '#3D99FF'
	});
});
//list
function getList(size,num){
	$.ajax({
        url: '../back/exchangeRate/checkList',
        type: 'get',
        async:false,
        data: {
        	'pageSize':size?size:pageSize,
        	'pageNum':num?num:pageNum,
        	'status':$("select[name='status']").val(),
        	'dailyDate':$("input[name='dailyDate']").val(),
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
    							'<th width="10%" class="text-center">序号</th>'+
								'<th width="15%" class="text-center">汇率日期</th>'+
								'<th width="15%" class="text-center">创建时间</th>'+
								'<th width="15%" class="text-center">更新时间</th>'+
								'<th width="10%" class="text-center">状态</th>'+
								'<th width="10%" class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var status = "";//状态
					var opteration = "";//操作按钮
					var categoryHtml = '';
					if(r.data[i].status=="normal"){
						categoryHtml = '<a class="editBtn">确认</a>';
					}else{
						categoryHtml = '<a class="editBtn">查看</a>';
					}
					
					tableHtml += '<tr data-url="exchangeRateDetail.html?status='+r.data[i].status+
					'&date='+r.data[i].dailyDate+
					'&uuid='+r.data[i].uuid+
					'" data-data="'+encodeURIComponent(r.data[i].dataInfo)+'">'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+
					'</td><td class="text-center">'+r.data[i].dailyDate+
					'</td><td class="text-center">'+formatDate(r.data[i].createtime)+
					'</td><td class="text-center">'+formatDate(r.data[i].updatetime)+
					'</td><td class="text-center">'+statusType[r.data[i].status]+'</td><td class="text-center" data-id="'+r.data[i].uuid+
					'">'+categoryHtml+'</td></tr>'; 
					
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
					tableHtml += '<tr><td colspan="6" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);	
				$(".editBtn").click(function(){
					window.localStorage.setItem("exchangeRate",decodeURIComponent($(this).parent().parent().attr("data-data")));
		    		layer.open({
						type: 2, 
						title:false, 
						area: ['750px', '730px'],
						content: [$(this).parent().parent().attr("data-url")] 
					});
				})
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