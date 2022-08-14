/**
 * 
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var pageNum = 1,
    pageSize = 50;
var flagSubmit = true;

$(function(){
	$(".goPrePage").click(function(){
		window.location.href='luckyGoodsList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
	$.ajax({
        url: '../back/luckygameGoods/get',
        type: 'get',
        async:false,
        data:{
        	'uuid':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			 $(".coverImg").attr("src",".."+r.data.coverImg);
    			 $(".shortTitle").html(r.data.shortTitle);
    			 $(".dPrice").html(r.data.dPrice);
    			 $(".createtime").html(formatDate(r.data.createtime));
    			 $(".endIssueNum").html(r.data.endIssueNum);
    			 $(".currentIssueNum").html(r.data.currentIssueNum);
    			 $(".restIssueNum").html(r.data.totalIssueNum==-1?'无期限':(r.data.totalIssueNum-r.data.currentIssueNum));
    			 $(".totalIssueNum").html(r.data.totalIssueNum==-1?'无期限':r.data.totalIssueNum);
    			 $(".betPerShare").html(r.data.betPerShare);
    			 $(".shares").html(r.data.shares);
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
        
    }); 
	getList();
});
//list
function getList(){
	$.ajax({
        url: '../back/luckygameGoods/issuelist',
        type: 'get',
        async:false,
        data: {
        	'luckygameGoods':uuid,
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'sort':'issue_num desc'
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="10%" class="text-center">期号</th>'+
								'<th width="20%" class="text-center">中奖人</th>'+
								'<th width="20%" class="text-center">投注量</th>'+
								'<th width="30%" class="text-center">中奖时间</th>'+
								'<th width="" class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var categoryHtml = '';
					categoryHtml = '<a href="luckyGoodsThiList.html?uuid='+r.data[i].uuid+'&pagesize='+pagesize+'&pagenum='+pagenum+'">查看详情</a>';
					tableHtml += '<tr data-url="luckyGoodsThiList.html?uuid='+r.data[i].uuid+
					'"><td class="text-center">'+r.data[i].issueNum+'</td><td class="text-center">'
					+(r.data[i].status=="finished"?r.data[i].nickname:'-')+
					'</td><td class="text-center">'+r.data[i].dAmount+'</td><td class="text-center">'
					+(r.data[i].status=="finished"?formatDate(r.data[i].finishedtime):'-')+'</td><td class="text-center" data-id="'+r.data[i].uuid+
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
					tableHtml += '<tr><td colspan="5" align="center">暂无相关数据</td></tr>';
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
//设置包尾
function isAll(uuid,isall){
	$.ajax({
        url: '../back/luckygameGoods/changeStatus',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
        	'status':isall
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("设置成功", {
		            time: 2000
		        },function(){
		        	getList();
		        });
    			return true;
    		}else {
    			if(r.errorCode=="302"){
    				layer.msg(r.message, {
    		            time: 2000
    		        },function(){
    		        	window.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    			return false;
    		}
    })
}
//排序
function sortsList(uuid,type){
	$.ajax({
        url: '../back/luckygameGoods/sorts',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
        	'type':type
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("设置成功", {
		            time: 2000
		        },function(){
		        	getList();
		        });
    			return true;
    		}else {
    			if(r.errorCode=="302"){
    				layer.msg(r.message, {
    		            time: 2000
    		        },function(){
    		        	window.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    			return false;
    		}
    })
}
//删除
function deleteList(uuid){
	layer.confirm('确定要删除吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){		
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
	$.ajax({
        url: '../back/luckygameGoods/delete',
        type: 'get',
        async:false,
        data: {
        	'uuid':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("删除成功", {
		            time: 2000
		        },function(){
		        	getList();	
		        });
    		}else {
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
    })
	}, function(){
		layer.closeAll();
	});
}