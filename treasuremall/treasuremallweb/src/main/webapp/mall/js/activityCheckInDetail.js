/**
 * 
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
    pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var datas={}
var companyType = {//京东快递、一号店、顺丰速运、邮政EMS、圆通快递、申通快递、韵达快递、百世快递、中通快递、宅急送、天天快递、德邦快递、优速快递、快捷快递
		'shunfeng':'顺丰速运',
		'yuantong':'圆通快递',
		'zhongtong':'中通快递',
		'jingdong':'京东快递',
		'yihaodian':'一号店',
		'youzhengems':'邮政EMS',
		'yunda':'韵达快递',
		'baishi':'百世快递',
		'shentong':'申通快递',
		'zhaijisong':'宅急送',
		'tiantian':'天天快递',
		'debang':'德邦快递',
		'yousu':'优速快递',
		'kuaijie':'快捷快递'
}
var statusType = {
		'normal':'未领奖',
		'finished':'已派奖',
		'receive':'未派奖',
		'finished':'已派奖',
		'return':'已退货',
		'confirm':'已收货'
}
$(function(){
	getList();
	$(".lay-export").click(function(){
		exportList();
	});
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
	datas['sort'] = 'createtime desc';
	$.ajax({
        url: '../back/userCheckin/list',
        type: 'get',
        async:false,
        data:datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
						'<th width="5%" class="text-center">序号</th>'+
						'<th width="18%" class="text-center">用户信息</th>'+
						'<th width="10%" class="text-center">手机号</th>'+
						'<th width="17%" class="text-center">奖品信息</th>'+
						'<th width="15%" class="text-center">抽奖时间</th>'+
						'<th width="8%" class="text-center">领奖状态</th>'+
						'<th width="18%" class="text-center">派奖信息</th>'+
						'<th width="" class="text-center">操作</th>'+
					'</tr>';
			for(var i=0;i<r.data.length;i++){
				var expressNumber = r.data[i].detailInfo == null ? '--' : r.data[i].detailInfo.expressNumber != null ? r.data[i].detailInfo.expressNumber : '--';
				var company = r.data[i].detailInfo == null ? '--' : r.data[i].detailInfo.company != null ? companyType[r.data[i].detailInfo.company] : '--';
				var operattime = r.data[i].operattime == null ? '--' : formatDate(r.data[i].operattime);
				var imgUrl = r.data[i].imageUrl?('..'+r.data[i].imageUrl):'../image/img-defaultAvatar.png';
				tableHtml += '<tr data-showId="'+r.data[i].frontUser+'">'+
					'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'+
					'<td class="text-center showDetail"><div style="width:40%;float:left;text-align: right;padding-right: 5px;"><img src="'+
					imgUrl+'"/></div><div style="width:60%;float:left; text-align:left;"><p>昵称：'+
					r.data[i].nickname+'</p><p>ID：'+r.data[i].frontUserShowId+'</p></div></td>'+
					'<td class="text-center">'+r.data[i].mobile+'</td>'+
					'<td class="text-center" style="position:relative;"><div style="width:30%;float:left;text-align: right;padding-right: 5px;"><img src="'+
						(r.data[i].prizeCoverUrl?".."+r.data[i].prizeCoverUrl:r.data[i].prizeType=="voucher"?"img/activity-voucher.png":"img/activity-gold.png")+
						'"/></div><p class="activity-center">'+
						r.data[i].prizeTitle+'</p></td>'+
					'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'+
					'<td class="text-center">'+statusType[r.data[i].status]+'</td>'+
					'<td><p>运单号：'+expressNumber+
					'</p><p>快递：'+company+
					'</p><p>时间：'+operattime+
					'</p></td>'+'<td class="text-center" data-uuid="'+r.data[i].uuid
					+'" data-data="'+encodeURI(r.data[i].provideInfo)
					+'">'+(r.data[i].status=="finished"&&r.data[i].receiveType=="entity"?"<a class='sureBtn mr-5'>确认收货</a>":
//					+'">'+(r.data[i].status=="finished"?"<a class='sureBtn mr-5'>确认收货</a><br/><a class='returnBtn'>退货</a>":
						r.data[i].status=="receive"?"<a class='awardBtn mr-5'>派奖</a><a class='resetBtn'>重置</a>":"")
					+'</td></tr>'; 
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
			$(".awardBtn").click(function(){
				window.localStorage.setItem("uuid",$(this).parent().attr("data-uuid"));
				window.localStorage.setItem("data",decodeURI($(this).parent().attr("data-data")));
				layer.open({
					type: 2, 
					title:false, 
					area: ['500px', '500px'],
					content: ['activityFreeDetailAddress.html?pagesize='+pageSize+'&pagenum='+pageNum+'&type=checkin'] 
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
			        url: '../back/userCheckin/confirm',
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
			        url: '../back/userCheckin/reset',
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
//导出
function exportList(){
	var layerIndex = layer.open({
		type: 3
	});
	if(flagSubmit == false) {
		return false;
	}
	flagSubmit = false;
	setTimeout(function() {
		flagSubmit = true;
	}, 3000);
	var data = $("form").serialize();
	var link = document.createElement('a');
	link.setAttribute("download", "");
	link.href = "../back/userCheckin/export?"+data;
	link.click();
}