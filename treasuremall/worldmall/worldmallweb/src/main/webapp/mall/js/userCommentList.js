/**
 * 用户充值审核管理
 */
var pageNum = 1,
    pageSize = 50;
function getStatus(status){
	switch (status){
		case 'normal':
			return '待审核'
		case 'checked':
			return '已完成'
		case 'nopass':
			return '已取消'
		default:
			return''
	}
}
$(function(){
	getList();	
});
function processSetting(uuid,status,remark){
	$.ajax({
        url: '../back/userComment/check',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'status': status,
        	'reason': remark
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('手工处理成功', {
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
function getList(){
	var datas = {
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
	if($('select[name=status]').val() != ''){
		datas['status'] = $('select[name=status]').val();
	}
	if($('input[name=orderNum]').val() != ''){
		datas['orderNum'] = $('input[name=orderNum]').val();
	}
	if($('input[name=frontUserShowId]').val() != ''){
		datas['showId'] = $('input[name=frontUserShowId]').val();
	}
	if($('#starttime').val() != ''){
		datas['starttime'] = $('#starttime').val();
	}
	if($('#endtime').val() != ''){
		datas['endtime'] = $('#endtime').val();
	}
	$.ajax({
        url: '../back/userComment/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="90px" class="text-center">用户ID</th>'+
				'<th width="100px" class="text-center">用户昵称</th>'+
				'<th width="150px" class="text-center">晒单奖品</th>'+
				'<th width="150px" class="text-center">订单号</th>'+
				'<th width="250px" class="text-center">晒单图片</th>'+
				'<th width="120px" class="text-center">晒单视频</th>'+
				'<th width="250px" class="text-center">晒单描述</th>'+
				'<th width="180px" class="text-center">晒单时间</th>'+
				'<th width="80px" class="text-center">状态</th>'+
				'<th width="120px" class="text-center">未通过原因</th>'+
				'<th width="80px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var categoryHtml = '';
				var imgList = '';
				var videoUrl = '';
				var reason = '';
				var classColor = "";
				if(r.data[i].status == 'normal' || r.data[i].status == 'checking'){
					categoryHtml = '<a class="processBtn mr-5">审核</a>'
				}
				if(r.data[i].imageList != null){
					for(var j=0;j<r.data[i].imageList.length;j++){
						imgList += '<img src="..'+r.data[i].imageList[j].image+'" />'
					}
				}
				if(r.data[i].videoList != null){
					videoUrl = '<video src="..'+r.data[i].videoList[0]+'"></video>'
				}
				
				if(r.data[i].reason != null){
					reason = r.data[i].reason;
				}
				if(r.data[i].status=="normal"){
					classColor = "color-green"
				}else if(r.data[i].status=="checked"){
					classColor = "color-blue"
				}else if(r.data[i].status=="nopass"){
					classColor = "color-orange"
				}
				tableHtml += '<tr data-showId="'+r.data[i].frontUser+'">'
					+'<td class="text-center"><div>'+r.data[i].frontUserShowId+'</div></td>'
					+'<td class="text-center showDetail">'+r.data[i].nickName+'</td>'
					+'<td class="text-left"><div style="position:relative;"><b class="onlyOneLine" style="padding-right:25px;display:block;">'+(r.data[i].title?r.data[i].title:'')
					+'</b><p class="layerMoreBtn"></p></div></td><td class="text-center" style="word-break:break-all">'+r.data[i].orderNum+'</td>'
					+'<td class="text-left imgList" data-img="'+(imgList!=""?encodeURI(JSON.stringify(r.data[i].imageList)):"")+'">'+imgList
					+'<td class="text-center">'+videoUrl+'</td>'
					+'<td class="text-center"><div style="position:relative;"><b class="onlyOneLine" style="padding-right:25px;display:block;">'
					+r.data[i].detail+'</b><p class="layerMoreBtn"></p></div></td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
					+'<td class="text-center '+classColor+'">'+getStatus(r.data[i].status)+'</td>'
					+'<td class="text-center"><div style="position:relative;"><b class="onlyOneLine" style="padding-right:25px;display:block;">'
					+reason+'</b><p class="layerMoreBtn"></p></div></td>'
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
				tableHtml += '<tr><td colspan="11" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".processBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.prompt({
					title: '手工审核',
					formType: 0,
					btn: ['审核通过','拒绝通过'],
					yes: function(value, index, elem){
						processSetting(dataUuid,'checked',$(".layui-layer-input").val());
					},
					btn2: function(value, index, elem){
						processSetting(dataUuid,'nopass',$(".layui-layer-input").val());
					}
				});
				return false;
			});
			$(".layerMoreBtn").parent().parent().click(function(){
				if($(this).find("b").hasClass("onlyOneLine")){
					$(this).find("b").removeClass("onlyOneLine");
					 $(this).find("p").removeClass("rotate1").addClass("rotate");
				}else{
					$(this).find("b").addClass("onlyOneLine");
					 $(this).find("p").removeClass("rotate").addClass("rotate1");
				}
			});
			$(".imgList").click(function(){
				var dataImg = JSON.parse(decodeURI($(this).attr("data-img")));
				var img = '';
				for(var m=0;m<dataImg.length;m++){
					img +='<div class="swiper-slide"><img src="..'+dataImg[m].image+'" /></div>'
				}
				
				layer.open({
					  type: 1,
					  title:'',
					  area: ['600px', '80%'], //宽高
					  content: '<div class="swiper-container">'+
									'<div class="swiper-wrapper">'+
										 img+
									'</div>'+
								    '<div class="swiper-pagination"></div>'+
								    '<div class="swiper-button-prev"></div>'+
								    '<div class="swiper-button-next"></div>'+
								'</div>'
					});
				var mySwiper = new Swiper ('.swiper-container', {
				    loop: true, // 循环模式选项
				    pagination: {
				      el: '.swiper-pagination',
				    },
				    navigation: {
				      nextEl: '.swiper-button-next',
				      prevEl: '.swiper-button-prev',
				    },
				  }) 
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

layui.use(['table', 'layer','laydate','element','carousel', 'form'], function(){
	var form = layui.form
		,layer = layui.layer
		,layedit = layui.layedit
		,laydate = layui.laydate
		,$ = layui.jquery
		,element = layui.element;
	
	laydate.render({
		elem: '#starttime',
		type: 'datetime'
		,theme: '#3D99FF'
	});
	laydate.render({
		elem: '#endtime',
		type: 'datetime'
		,theme: '#3D99FF'
	});

});