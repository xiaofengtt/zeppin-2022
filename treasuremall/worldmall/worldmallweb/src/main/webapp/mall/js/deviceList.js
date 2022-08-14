var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
    pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
// 设备类型
var deviceType = {
	'01':'iOS',
	'02':'Android'
}
// 用户群组
var frontUserGroup = {
	'registered':'注册用户',
	'recharged':'充值用户',
	'VIP':'VIP用户',
	'demo':'测试用户',
	'visitor':'游客'
}
$(function(){
	topicList();
	getList();
	$(".addMessage").click(function(){
		var uuid = "";
		$(".check.light").each(function(){
			uuid += $(this).val() + ",";
		});
		if(uuid==""){
			layer.msg("请选择要批量发送的数据");
			return false;
		}else{
			uuid = uuid.substring(0,uuid.length-1);
			window.localStorage.setItem("deviceUuid",JSON.stringify(uuid))
		}
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '300px'],
		  content:['deviceDetail.html?pagesize='+pageSize+'&pagenum='+pageNum,'no']
		}); 
	});
	$(".addTheme").click(function(){
		var uuid = "";
		$(".check.light").each(function(){
			uuid += $(this).val() + ",";
		});
		if(uuid==""){
			layer.msg("请选择要批量绑定主题的数据");
			return false;
		}else{
			uuid = uuid.substring(0,uuid.length-1);
			window.localStorage.setItem("deviceUuid",JSON.stringify(uuid))
		}
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '180px'],
		  content:['deviceSelect.html?pagesize='+pageSize+'&pagenum='+pageNum,'no']
		}); 
	});
})

	function topicList(){
		$.ajax({
		    url: '../back/topic/list',
		    type: 'get',
		    async:false,
		    data:{
				'status':'normal',
		    	'pageSize':'10000',
		    	'pageNum':'1'
		    }
		}).done(function(r) {
				if (r.status == "SUCCESS") {
					var html = '<option value="">全部</option>';
					for(var i = 0;i<r.data.length;i++){
						html += '<option value="'+r.data[i].uuid+'">'+ r.data[i].displayName+'</option>';
					}
					$("select[name='topic']").html(html);
				} else {
					
				}
		}).fail(function(r) {
		    
		}); 
	}

// 列表
function getList(){
	$.ajax({
	        url: '../back/device/list',
	        type: 'get',
	        async:false,
	        data: {
				'deviceType':$("select[name='deviceType']").val(),
				'topic':$("select[name='topic']").val(),
				'frontUserGroup':$("select[name='frontUserGroup']").val(),
	        	'pageSize':pageSize,
	        	'pageNum':pageNum
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			var tableHtml = '<tr>'+
									'<th width="5%;" class="text-center"><input type="checkbox" id="allCheck" name="" value=""></th>'+
									'<th width="5%" class="text-center">序号</th>'+
									'<th width="20%" class="text-center">设备编码</th>'+
									'<th width="10%" class="text-center">设备类型</th>'+
									'<th width="15%" class="text-center">用户信息</th>'+
									'<th width="10%" class="text-center">用户群组</th>'+
									'<th width="10%" class="text-center">接入时间</th>'+
									'<th width="10%" class="text-center">更新时间</th>'+
									'<th width="15%" class="text-center">所属主题</th>'+
									'<th width="5%" class="text-center">操作</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						var status = "";//状态
						var categoryHtml = '<a class="editBtn">发送</a>';
						var topic = "";// 所属主题
						if(r.data[i].topicList){
							for(var j=0;j<r.data[i].topicList.length;j++){
								if(r.data[i].topicList[j].isBind==true){
									topic+='<p>'+r.data[i].topicList[j].displayName+'--已绑定</p>'
								}else{
									topic+='<p>'+r.data[i].topicList[j].displayName+'--未绑定</p>'
								}
							}
						}else{
							topic = "<p>未绑定</p>"
						}
						tableHtml += '<tr><td class="text-center"><input class="check" type="checkbox" name="" value="'+r.data[i].uuid+
	    				'"></td><td class="text-center">'+(pageSize*(pageNum-1)+i+1)+
						'</td><td class="text-center">'+r.data[i].uniqueToken+
						'</td><td class="text-center">'+(r.data[i].deviceType?deviceType[r.data[i].deviceType]:'-')+
						'</td><td class="text-center"><p>昵称：'+(r.data[i].nickname?r.data[i].nickname:'-')+
						'</p><p>ID：'+(r.data[i].showId?r.data[i].showId:'-')+'</p>'+
						'</td><td class="text-center">'+(r.data[i].frontUserGroup?frontUserGroup[r.data[i].frontUserGroup]:'-')+
						'</td><td class="text-center">'+formatDate(r.data[i].createtime)+
						'</td><td class="text-center">'+formatDate(r.data[i].updatetime)+
						'</td><td class="text-center">'+topic+
						'</td><td class="text-center" data-nickname="'+r.data[i].nickname
						+'" data-deivce="'+deviceType[r.data[i].deviceType]+'" data-id="'+r.data[i].uuid+
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
						tableHtml += '<tr><td colspan="10" align="center">暂无相关数据</td></tr>';
					}
					$("table").html(tableHtml);
					allCheck();			
					$(".editBtn").click(function(){
						window.localStorage.setItem("deviceUuid",JSON.stringify($(this).parent().attr("data-id")))
						layer.open({
						  type: 2, 
						  title:false,
						  area: ['500px', '450px'],
						  content:['deviceDetail.html?pagesize='+pageSize+'&pagenum='+pageNum+'&nickname='
						  +$(this).parent().attr("data-nickname")+'&device='+$(this).parent().attr("data-deivce"),'no']
						});
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
layui.use(['form'], function(){
	  var form = layui.form 
});
