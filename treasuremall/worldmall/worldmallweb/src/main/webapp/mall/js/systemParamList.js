var pageNum = 1,
    pageSize = 50;
var flagSubmit = true;
var boolean ={
            	  "true":"是",
            	  "false":"否"
               }
               
$(function(){
	getList();
});


//获取列表
function getList(){
	$.ajax({
        url: '../back/param/list',
        type: 'get',
        async:false,
        data: {
        	"partitional":$("#partitionalSelect").val(),
            "pageSize":pageSize,
            "pageNum":pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
							'<th class="text-center" width="20%">参数描述</th>'+
							'<th width="30%">参数值</th>'+
							'<th class="text-center" width="20%">功能分类</th>'+
							'<th class="text-center" width="15%">类型</th>'+
							'<th class="text-center" width="15%" class="text-right">操作</th>'+
						'</tr>';
			for(var i=0;i<r.data.length;i++){
				var partitional = "", type = "", value='',valueUuid='';
				if(r.data[i].type == "string"){
					type = "字符串";
				}else if(r.data[i].type == "numberic"){
					type = "数字型";
				}else if(r.data[i].type == "currency"){
					type = "货币型";
				}else if(r.data[i].type == "primarykey"){
					type = "主键值";
					
				}else if(r.data[i].type == "map"){
					type = "字典型";
				}else if(r.data[i].type == "boolean"){
					type = "布尔型";
				}else if(r.data[i].type == "richtext"){
					type = "富文本";
				}
				if(r.data[i].partitional == "user_withdraw"){
					partitional = "用户提现"
				}else if(r.data[i].partitional == "user_recharge"){
					partitional = "用户充值"
				}else if(r.data[i].partitional == "user_bet"){
					partitional = "用户投注"
				}else if(r.data[i].partitional == "full_system"){
					partitional = "系统全局"
				}
				
				if(r.data[i].type == "map"){
					var mapValue = JSON.parse(r.data[i].paramValue);
					for(var key in mapValue){
						value = value + key + "：" + mapValue[key] + "<br/>";
						valueUuid = valueUuid + key + "：" + mapValue[key] + "<br/>";
					}
				}else if(r.data[i].type == "primarykey"){
					if(r.data[i].paramKey == 'withdraw_default_account'){
						$.ajax({
					        url: '../back/capitalAccount/get',
					        type: 'get',
					        async:false,
					        data: {
					        	"uuid":r.data[i].paramValue
					        }
					    }).done(function(rs) {
					    	if (rs.status == "SUCCESS") {					    		
					    		value=rs.data.name;
					    	}
					    });
					}
					valueUuid = r.data[i].paramValue;
				}else if(r.data[i].type == "boolean"){
					value = boolean[r.data[i].paramValue];
					valueUuid = r.data[i].paramValue;
				}else if(r.data[i].type == "richtext"){
					value = new Base64().decode(r.data[i].paramValue);
					valueUuid = new Base64().decode(r.data[i].paramValue);
				}else{
					value = r.data[i].paramValue;
					valueUuid = r.data[i].paramValue;
				}
				tableHtml += '<tr>'
    				+'<td class="text-center"><b>'+r.data[i].description+'</b></td>'
    				+'<td>'+value+'</td>'
    				+'<td class="text-center">'+partitional+'</td>'
    				+'<td class="text-center">'+type+'</td>'
    				+'<td class="text-center" data-id="'+r.data[i].uuid+'" class="text-right"><a class="editBtn" data-key="'
    				+r.data[i].paramKey+'" data-type="'+r.data[i].type+'" data-value="'+encodeURI(valueUuid)
    				+'" data-storage="'+encodeURI(JSON.stringify(r.data[i]))+'">修改</a>'
    				+'</tr>';
			}
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
            
            $(".ui-select-pagesize").unbind("change").change(function() {
                pageSize = $(this).val();
                pageNum = 1;
                getList();
            });
            if(r.data.length==0){
				tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
			}
			$(".table-list").html(tableHtml);
			allCheck();
			$(".editBtn").click(function(){
				window.localStorage.setItem('paramValue',JSON.parse(decodeURI($(this).attr("data-storage"))).paramValue)
				layer.open({
				  type: 2,
				  title: '编辑参数值',
				  shadeClose: true,
				  shade: 0.2,
				  area: ['550px'],
				  content: 'systemParamEdit.html?key='+$(this).attr("data-key")
				  +'&type='+$(this).attr("data-type")+'&value='+$(this).attr("data-value"),
                  success: function (layero, index) {
                      layer.iframeAuto(index)
                      
                  }
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
        	
        });
    });   	
}

$("#partitionalSelect").change(function(){
	pageNum = 1;
	getList();
});
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})
