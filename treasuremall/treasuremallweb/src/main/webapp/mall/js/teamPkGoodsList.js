/**
 * 
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var statusType = {
		'normal':'启用',
		'disable':'停用',
		'delete':'已删除'
	}
$(function(){
	getList();
	$(".goPrePage").click(function(){
		window.location.href='teamPkGoodsAdd.html?pagesize='+pageSize+'&pagenum=1'
	});
});
//list
function getList(size,num){
	$.ajax({
        url: '../back/luckygameGoods/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':size?size:pageSize,
        	'pageNum':num?num:pageNum,
        	'name':$(".layui-name").val().replace(/(^\s*)|(\s*$)/g, ""),
        	'gameType':'group2'
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="5%" class="text-center">序号</th>'+
								'<th width="10%" class="text-center">奖品缩略图</th>'+
								'<th width="15%" class="">奖品信息</th>'+
								'<th width="18%" class="">配置信息</th>'+
								'<th width="15%" class="">开奖信息</th>'+
								'<th width="6%" class="text-center">盈利比例</th>'+
								'<th width="8%" class="text-center">是否上架</th>'+
								'<th width="10%" class="text-center">排序</th>'+
								'<th width="" class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var status = '';//状态
					var source = "";//来源
					var opteration = "<a class='upBtn mr-5'>向上</a><a class='downBtn mr-5'>向下</a><a class='topBtn'>置顶</a>";//排序
					var categoryHtml = '';
					var currentIssueNum = '无';//已揭晓期数
					if(r.data[i].status=="disable"){
						categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="deleteBtn mr-5">删除</a><a href="teamPkGoodsSecList.html?uuid='+r.data[i].uuid+'&pagesize='+pageSize+'&pagenum='+pageNum+'">查看详情</a>';
					}else{
						categoryHtml = '<a href="teamPkGoodsSecList.html?uuid='+r.data[i].uuid+'&pagesize='+pageSize+'&pagenum='+pageNum+'">查看详情</a>';
					}
					if(r.data[i].status=="disable"){
						status = '<label class="switch" data-id="'+r.data[i].uuid
						+'"><input type="checkbox"><div class="slider round"></div></label>';
					}else if(r.data[i].status=="normal"){
						status = '<label class="switch" data-id="'+r.data[i].uuid
						+'"><input type="checkbox" checked><div class="slider round"></div></label>';
						currentIssueNum = r.data[i].currentIssueNum;
					}
					tableHtml += '<tr class="robot'+r.data[i].status+'" data-url="teamPkGoodsAdd.html?uuid='+r.data[i].uuid+
					'"><td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td><td class="text-center"><img src="..'+r.data[i].coverImg+'"/>'+
					'</td><td class=""><p class="displaySingle">名称：'+r.data[i].title+'</p><p>总需人次：'+r.data[i].shares+'</p></td><td>总期数：'+
					(r.data[i].totalIssueNum=="-1"?'无期限':r.data[i].totalIssueNum)+'<br/>单人次金币：'+r.data[i].betPerShare
					+'<br/>创建时间：'+formatDate(r.data[i].createtime)+'</td><td>已揭晓期数：'+r.data[i].endIssueNum
					+'<br/>当前进行中期号：'+currentIssueNum
					+'<br/>剩余未进行期数：'+(r.data[i].totalIssueNum=="-1"?'无期限':r.data[i].totalIssueNum-r.data[i].endIssueNum)
					+'</td><td class="text-center">'+r.data[i].profitRate+'</td><td class="text-center">'+
					status+'</td><td class="text-center" data-id="'+r.data[i].uuid+
					'">'+opteration+'</td><td class="text-center" data-id="'+r.data[i].uuid+
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
					tableHtml += '<tr><td colspan="9" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);
				$(".switch").click(function(){
					if($(this).find("input").prop("checked")){
						if(isAll($(this).attr("data-id"),'disable')){
							$(this).find("input").prop("checked",false);						
						}	
					}else{
						if(isAll($(this).attr("data-id"),'normal')){
							$(this).find("input").prop("checked",true);
						}
					}
					return false;
				})
				//向上
				$(".upBtn").click(function(){
					sortsList($(this).parent().attr("data-id"),'up')
				});
				$(".downBtn").click(function(){
					sortsList($(this).parent().attr("data-id"),'down')
				});
				$(".topBtn").click(function(){
					sortsList($(this).parent().attr("data-id"),'top')
				});
				$(".editBtn").click(function(){
					window.location.href = 'teamPkGoodsAdd.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum;
					return false;
				});
				$(".deleteBtn").click(function(){
					deleteList($(this).parent().attr("data-id"));
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
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})