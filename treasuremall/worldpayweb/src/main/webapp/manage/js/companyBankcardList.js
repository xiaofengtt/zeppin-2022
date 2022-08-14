/**
 * 
 */
var company = (url('?company') != null) ? url('?company') : '';
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'normal',
	'disable':'disable',
}
$(function(){
	getList();
});
layui.use(['table', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
});

function getList(){
	$.ajax({
        url: '../system/companyBankcard/list',
        type: 'get',
        async:false,
        data: {
        	'company':company,
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="100px" class="text-left">Store</th>'+
				'<th width="100px" class="text-center">BankName</th>'+
				'<th width="100px" class="text-center">Holder</th>'+
				'<th width="150px" class="text-center">Bankcard number</th>'+
				'<th width="200px" class="text-center">Area</th>'+
				'<th width="100px" class="text-center">Branch</th>'+
				'<th width="80px" class="text-center">Status</th>'+
				'<th width="100px" class="text-center">Operation</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '';
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data[i].companyName+'</td>'
					+'<td class="text-center">'+r.data[i].bank+'</td>'
					+'<td class="text-center">'+r.data[i].holder+'</td>'
					+'<td class="text-center">'+r.data[i].accountNum+'</td>'
					+'<td class="text-center">'+r.data[i].area+'</td>'
					+'<td class="text-center">'+r.data[i].branchBank+'</td>'
					+'<td class="text-center">'+statusObj[r.data[i].status]+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'">'+processHtml+'</td>'
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
				tableHtml += '<tr><td colspan="8" align="center">No relevant data</td></tr>';
			}
			$("table").html(tableHtml);
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