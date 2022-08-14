/**
 * 机器人管理
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
    pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var statusType = {
		'normal':'启用',
		'disable':'停用',
		'delete':'已删除'
	}
var gameTypes = {
		'tradition':'传统玩法',
		'group2':'战队PK'
	}
$(function(){
	getList();	
	$(".changeStatusStart").click(function(){
		changeStaus('normal');
	});
	$(".changeStatusStop").click(function(){
		changeStaus('disable');
	});
});
//批量启用停用
function changeStaus(status){
	var uuid = "";
	$(".check.light").each(function(){
		uuid += $(this).val() + ",";
	});
	if(uuid==""){
		layer.msg("请选择要批量操作的数据");
		return false;
	}else{
		uuid = uuid.substring(0,uuid.length-1);
	}
	$.ajax({
        url: '../back/robot/changeStatus',
        type: 'post',
        async:false,
        data: {
        	'uuids':uuid,
        	'status':status
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
//list
function getList(size,num){
	$.ajax({
        url: '../back/robot/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':size?size:pageSize,
        	'pageNum':num?num:pageNum,
        	'status':$("select[name='status']").val(),
        	'showid':$("input.layui-id").val().replace(/(^\s*)|(\s*$)/g, ""),
        	'gameType':$("select[name='gameType']").val()
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
    							'<th width="60px;"><input type="checkbox" id="allCheck" name="" value=""></th>'+
								'<th width="80px" class="text-center">ID</th>'+
								'<th width="150px" class="text-center">头像/昵称</th>'+
								'<th width="80px" class="text-center">玩法类型</th>'+
								'<th width="110px" class="text-center">手机号</th>'+
								'<th width="110px" class="text-center">IP地址</th>'+
								'<th width="80px" class="text-center">剩余金币</th>'+
								'<th width="80px" class="text-center">已投金币</th>'+
								'<th width="80px" class="text-center">参与次数</th>'+
								'<th width="90px" class="text-center">投注时间段</th>'+
								'<th width="100px" class="text-center">投注间隔</th>'+
								'<th width="100px" class="text-center">投注金额</th>'+
								'<th width="80px" class="text-center">包尾开关</th>'+
								'<th width="60px" class="text-center">状态</th>'+
								'<th width="100px" class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var status = "";//状态
					var source = "";//来源
					var opteration = "";//操作按钮
					var categoryHtml = '';
					var imgUrl = r.data[i].imageURL?(".."+r.data[i].imageURL):'../image/img-defaultAvatar.png';
					var gameType = r.data[i].gameType == null ? '':gameTypes[r.data[i].gameType];
					if(r.data[i].status!="delete"){
						categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="deleteBtn">删除</a>';
						if(r.data[i].isAll!=false){
							status = '<label class="switch" data-id="'+r.data[i].uuid
							+'"><input type="checkbox"><div class="slider round"></div></label>'
						}else {
							status = '<label class="switch" data-id="'+r.data[i].uuid
							+'"><input type="checkbox" checked><div class="slider round"></div></label>'
						}
						tableHtml += '<tr class="robot'+r.data[i].status+'" data-url="robotAdd.html?uuid='+r.data[i].uuid+
						'" data-showId="'+r.data[i].uuid+'"><td><input class="check" type="checkbox" name="" value="'+r.data[i].uuid+
	    				'"></td><td class="text-center">'+r.data[i].showId+'</td><td class="showDetail"><img src="'+imgUrl+'" class="touxiang" />'+r.data[i].nickname+
						'</td><td class="text-center">'+gameType+'</td><td class="text-center">'+r.data[i].mobile+'</td><td class="text-center">'+r.data[i].ip+'</td><td class="text-center">'+r.data[i].balance+
						'</td><td class="text-center">'+r.data[i].totalPayment+'</td><td class="text-center">'+r.data[i].paymentTimes
						+'</td><td class="text-center">'+
						(r.data[i].worktimeBegin!=null?(formatDates(r.data[i].worktimeBegin)+'-'+formatDates(r.data[i].worktimeEnd)):'-')+
						'</td><td class="text-center">'+(r.data[i].periodMin!=null?(r.data[i].periodMin+'-'+r.data[i].periodRandom+"s"):'-')+
						'</td><td class="text-center">'+(r.data[i].minPay!=null?(r.data[i].minPay+'-'+r.data[i].maxPay):'-')+
						'</td><td class="text-center">'+status+
						'</td><td class="text-center">'+statusType[r.data[i].status]+'</td><td class="text-center" data-id="'+r.data[i].uuid+
						'">'+categoryHtml+'</td></tr>'; 
					}
					
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
					tableHtml += '<tr><td colspan="15" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);
				allCheck();
				$(".deleteBtn").click(function(){
					deleteList($(this).parent().attr("data-id"));
					return false;
				});			
				$(".editBtn").click(function(){
					window.location.href = 'robotAdd.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum;
					return false;
				});
				$(".switch").click(function(){
					if(flagSubmit){
						if($(this).find("input").prop("checked")){
							if(isAll($(this).attr("data-id"),false)){
								$(this).find("input").prop("checked",false);
							}	
						}else{
							if(isAll($(this).attr("data-id"),true)){
								$(this).find("input").prop("checked",true);
							}
						}
						flagSubmit = false;
						setTimeout(function() {
							flagSubmit = true;
						}, 3000);
					}
					
					return false;
				})
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
//设置包尾
function isAll(uuid,isall){
	$.ajax({
        url: '../back/robot/isAll',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
        	'isAll':isall
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
        url: '../back/robot/delete',
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
$(".coinOperation").click(function(){
	var uuid = "";
	$(".check.light").each(function(){
		uuid += $(this).val() + ",";
	});
	if(uuid==""){
		layer.msg("请选择要批量操作的数据");
		return false;
	}else{
		uuid = uuid.substring(0,uuid.length-1);
		window.localStorage.setItem("uuid",JSON.stringify(uuid))
	}
	layer.open({
		type: 2, 
		title:false, 
		area: ['460px', '250px'],
		content: ['robotCoinOp.html?pagesize='+pageSize+'&pagenum='+pageNum,'no'] 
	});
});
$(".robotSet").click(function(){
	var uuid = "";
	$(".check.light").each(function(){
		uuid += $(this).val() + ",";
	});
	if(uuid==""){
		layer.msg("请选择要批量操作的数据");
		return false;
	}else{
		uuid = uuid.substring(0,uuid.length-1);
		window.localStorage.setItem("uuid",JSON.stringify(uuid))
	}
	parent.layer.open({
		type: 2, 
		title:false, 
		area: ['80%', '90%'],
		content: ['robotSetting.html?pagesize='+pageSize+'&pagenum='+pageNum] 
	});
});
layui.use(['form'], function(){
	  var form = layui.form
	  
});