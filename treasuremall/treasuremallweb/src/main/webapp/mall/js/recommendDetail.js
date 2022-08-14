/**
 * 
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
    pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var datas={}
$(function(){
	getList();
})
$(".lay-submit").click(function(){
	getList();
	return false;
});
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate
	  ,$ = layui.jquery
	  ,upload = layui.upload
	  ,element = layui.element;	
});

//列表
function getList(size,num){
	datas = $("form").serializeObject();
	datas['pageSize'] = size?size:pageSize;
	datas['pageNum'] = num?num:pageNum;
	$.ajax({
        url: '../back/userRecommend/list',
        type: 'get',
        async:false,
        data:datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
						'<th width="8%" class="text-center">序号</th>'+
						'<th width="20%" class="text-center">邀请人</th>'+
						'<th width="9%" class="text-center">邀请人ID</th>'+
						'<th width="10%" class="text-center">获利金额</th>'+
						'<th width="20%" class="text-center">被邀请人</th>'+
						'<th width="9%" class="text-center">被邀请人ID</th>'+
						'<th width="9%" class="text-center">充值金额/元</th>'+
						'<th width="15%" class="text-center">获利时间</th>'+
					'</tr>';
			for(var i=0;i<r.data.length;i++){
				var imgUrl = r.data[i].imageURL?('..'+r.data[i].imageURL):'../image/img-defaultAvatar.png';
				var recommendimgUrl = r.data[i].recommendImageURL?('..'+r.data[i].recommendImageURL):'../image/img-defaultAvatar.png';
				tableHtml += '<tr data-showId="'+r.data[i].frontUser+'" data-recommendshowId="'+r.data[i].recommendFrontUser+'">'+
					'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'+
					'<td class="text-center showDetail" style="position:relative;"><div style="width:30%;float:left;text-align: right;padding-right: 5px;"><img src="'+
					imgUrl+'"/></div><p class="activity-center">昵称：'+
					r.data[i].nickname+'</p></td>'+
					'<td class="text-center">'+r.data[i].showId+'</td>'+
					'<td class="text-center">'+r.data[i].awardAmount+'</td>'+
					'<td class="text-center recommendshowDetail" style="position:relative;"><div style="width:30%;float:left;text-align: right;padding-right: 5px;"><img src="'+
					recommendimgUrl+'"/></div><p class="activity-center">昵称：'+
					r.data[i].recommendNickname+'</p></td>'+
					'<td class="text-center">'+r.data[i].recommendShowId+'</td>'+
					'<td class="text-center">'+r.data[i].rechargeAmount+'</td>'+
					'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td></tr>'; 
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
			$(".showDetail").click(function(){
				layer.open({
					type: 2, 
					title:false, 
					area: ['90%', '90%'],
					content: ['userDetail.html?uuid='+$(this).parent().attr("data-showId")] 
				});
				return false;
			});
			$(".recommendshowDetail").click(function(){
				layer.open({
					type: 2, 
					title:false, 
					area: ['90%', '90%'],
					content: ['userDetail.html?uuid='+$(this).parent().attr("data-recommendshowId")] 
				});
				return false;
			});
			$(".awardBtn").click(function(){
				window.localStorage.setItem("uuid",$(this).parent().attr("data-uuid"));
				window.localStorage.setItem("data",decodeURI($(this).parent().attr("data-data")));
				layer.open({
					type: 2, 
					title:false, 
					area: ['500px', '500px'],
					content: ['activityFreeDetailAddress.html?pagesize='+pageSize+'&pagenum='+pageNum+'&type=score'] 
				});
			});
			$(".sureBtn").click(function(){
				var uuid = $(this).parent().attr("data-uuid");
				layer.confirm('确定要确认收货吗？', {
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
			        url: '../back/userScorelottery/confirm',
			        type: 'post',
			        async:false,
			        data: {
			        	'uuid':uuid,
			        	'status':'confirm'
			        }
			    }).done(function(r) {
			    		if (r.status == "SUCCESS") {
			    			layer.msg("确认收货成功", {
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
			});
			$(".resetBtn").click(function(){
				var uuid = $(this).parent().attr("data-uuid");
				layer.confirm('确定要重置派奖吗？', {
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
			        url: '../back/userScorelottery/reset',
			        type: 'post',
			        async:false,
			        data: {
			        	'uuid':uuid
			        }
			    }).done(function(r) {
			    		if (r.status == "SUCCESS") {
			    			layer.msg("重置成功", {
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
			});
		}
    })
}
