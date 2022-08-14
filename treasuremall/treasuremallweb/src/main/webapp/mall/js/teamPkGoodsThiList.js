/**
 * 
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var pageNum = 1,
    pageSize = 50;
var flagSubmit = true;
var teamGroup = {
		'lucky':'幸运队',
		'raider':'夺宝队'
}

$(function(){
	$(".goPrePage").click(function(){
		window.location.href='teamPkGoodsList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
	$.ajax({
        url: '../back/luckygameGoods/issueget',
        type: 'get',
        async:false,
        data:{
        	'uuid':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			 $(".coverImg").attr("src",".."+r.data.coverImg);
    			 $(".shortTitle").html(r.data.shortTitle);
    			 $(".dPrice").html(r.data.issueNum);
    			 $(".createtime").html(r.data.luckyNumber);
    			 $(".endIssueNum").html(r.data.nickname);
    			 $(".currentIssueNum").html(r.data.actualDAmount);
    			 $(".restIssueNum").html(r.data.paymenttime?formatDate(r.data.paymenttime):'-');
    			 $(".luckyGroup").html(r.data.luckyGroup?teamGroup[r.data.luckyGroup]:'-');
    			 if(r.data.status=="finished"){
    				 $(".totalIssueNum").html((r.data.finishedtime-r.data.createtime)==0?'-':formatDateTime(r.data.finishedtime-r.data.createtime));
    			 }else{
    				 $(".totalIssueNum").html('-');
    			 }
    			 
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
        url: '../back/userPayment/list',
        type: 'get',
        async:false,
        data: {
        	'goodsIssue':uuid,
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="8%" class="text-center">序号</th>'+
								'<th width="10%" class="text-center">投注人</th>'+
								'<th width="10%" class="text-center">投注量</th>'+
								'<th width="10%" class="text-center">投注战队</th>'+
								'<th width="10%" class="text-center">IP</th>'+
								'<th width="10%" class="text-center">位置</th>'+
								'<th width="15%" class="text-center">投注时间</th>'+
								'<th width="" class="text-center" style="padding-right:25px;">抽奖号</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<tr data-url="teamPkGoodsThiList.html?uuid='+r.data[i].goodsId+
					'"><td class="text-center">'+(pageSize*(pageNum-1)+(i+1))+'</td><td class="text-center">'+r.data[i].nickname+
					'</td><td class="text-center">'+r.data[i].totalDAmount+'</td><td class="text-center">'
					+teamGroup[r.data[i].paymentGroup]+'</td><td class="text-center">'
					+r.data[i].ip+'</td><td class="text-center">'+(r.data[i].area?r.data[i].area:'-')+'</td><td class="text-center">'+
					formatDate(r.data[i].createtime)+'</td><td class="text-center" data-id="'+r.data[i].uuid+
					'"><div style="position:relative;"><b class="onlyOneLine" style="padding-right:25px;display:block;word-break: break-all;">'
					+(r.data[i].listNum?r.data[i].listNum:'-')+'</b><p class="layerMoreBtn"></p></div></td></tr>'; 
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
					tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);
				$(".layerMoreBtn").parent().parent().click(function(){
					if($(this).find("b").hasClass("onlyOneLine")){
						$(this).find("b").removeClass("onlyOneLine");
						 $(this).find("p").removeClass("rotate1").addClass("rotate");
					}else{
						$(this).find("b").addClass("onlyOneLine");
						 $(this).find("p").removeClass("rotate").addClass("rotate1");
					}
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