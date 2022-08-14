/**
 * 金币管理
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var datas = {};
var statusObj = {
	'normal':'待审核',
	'checked':'已审核',
	'cancel':'未通过',
	'close':'已关闭'
}
$(".lay-submit").click(function(){	
	pageNum=1;
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
	  //日期
	  laydate.render({
	    elem: '.worktimeBegin'
	    ,type: 'datetime'
	    ,range: '_'
	    ,theme: '#3D99FF'
	  });
});
$(function(){
	getList();
	$(".addCoin").click(function(){
		layer.open({
		  type: 2, 
		  title:['增加金币','text-align:left'],
		  area: ['800px', '550px'],
		  content:['adminOffsetOrderDetail.html?type=admin_add&pagesize='+pageSize+'&pagenum='+pageNum,'no']
		}); 
	});
	$(".subCoin").click(function(){
		layer.open({
		  type: 2, 
		  title:['扣除金币','text-align:left'],
		  area: ['800px', '550px'],
		  content:['adminOffsetOrderDetail.html?type=admin_sub&pagesize='+pageSize+'&pagenum='+pageNum,'no']
		}); 
	});
});
function getList(size,num){
//	datas = $("form").serializeObject();
//	datas['pageSize'] = pageSize;
//	datas['pageNum'] = pageNum;
	var datas = {
			'pageSize':size?size:pageSize,
		    'pageNum':num?num:pageNum,
	    	'userType':$("select[name=userType]").val(),
	    	'createtime':$("input[name=createtime]").val(),
	    	'mobile':$("input[name=mobile]").val(),
	    	'showid':$("input[name=showid]").val(),
	    	'sort':'createtime desc'
	    }
	$.ajax({
        url: '../back/adminOffsetOrder/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="5%" class="text-center">序号</th>'+
				'<th width="8%" class="text-center">用户ID</th>'+
				'<th width="8%" class="text-center">用户昵称</th>'+
				'<th width="8%" class="text-center">手机号</th>'+
				'<th width="8%" class="text-center">变动前余额</th>'+
				'<th width="8%" class="text-center">金币变动</th>'+
				'<th width="8%" class="text-center">用户余额</th>'+
				'<th width="7%" class="text-center">审核人员</th>'+
				'<th width="12%" class="text-center">审核时间</th>'+
				'<th width="8%" class="text-center">备注</th>'+
				'<th width="5%" class="text-center">状态</th>'+
				'<th width="5%" class="text-center">操作</th>'+
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
				var categoryHtml = '';
				if(r.data[i].status == 'normal'){
					categoryHtml = '<a class="closeBtn">关闭</a>'
				}
				tableHtml += '<tr data-showId="'+r.data[i].frontUser+'">'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center">'+r.data[i].frontUserShowId+'</td>'
					+'<td class="text-center showDetail">'+r.data[i].frontUserNickname+'</td>'
					+'<td class="text-center">'+r.data[i].frontUserMobile+'</td>'
					+'<td class="text-center">'+(r.data[i].balanceBefore!=null?r.data[i].balanceBefore.toFixed(2):'-')+'</td>'
					+'<td class="text-center">'+typeStr+(r.data[i].dAmount).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].balanceAfter?(r.data[i].balanceAfter).toFixed(2):'-')+'</td>'
					+'<td class="text-center">'+(r.data[i].operatorName==null?'':r.data[i].operatorName)+'</td>'
					+'<td class="text-center">'+(r.data[i].operattime==null?'':formatDate(r.data[i].operattime))+'</td>'
					+'<td class="text-center">'+(r.data[i].remark==null?'':r.data[i].remark)+'</td>'
					+'<td class="text-center '+classColor+'">'+statusObj[r.data[i].status]+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'"><a class="closeBtn">关闭</a></td>'
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
				tableHtml += '<tr><td colspan="12" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".closeBtn").click(function(){
				closeData($(this).parent().attr("data-id"));
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
//关闭
function closeData(uuid){
	layer.confirm('是否确认关闭这个订单？',function(index, layero){
    	$.ajax({
            url: '../back/adminOffsetOrder/close',
            type: 'post',
            async:false,
            data: {
            	'uuid':uuid
            }
        }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("关闭成功", {
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
    		layer.close(index);
        })
    },function(){
    	
    });
}

layui.use(['table','form', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
//	form.on('select(userType)',function(data){
//		getList(data.value);
//	})
});