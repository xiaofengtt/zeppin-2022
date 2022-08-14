/**
 * 商品管理
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var typeObj = {
}
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='goodsAdd.html?pagesize='+pageSize+'&pagenum='+pageNum
	});
	$.ajax({
        url: '../back/goodsType/list',
        type: 'get',
        async:false,
        data:{
        	'pageSize':'10000',
        	'pageNum':'1'
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var html = '<option value="">全部分类</option>';
    			for(var i = 0;i<r.data.length;i++){
    				typeObj[r.data[i].code] = r.data[i].name;
    				html += '<option value="'+r.data[i].code+'">'+r.data[i].name+'</option>';
    			}
    			$("select[name='type']").html(html);
    		} else {
    			
    		}
    }).fail(function(r) {
        
    }); 

	getList();	
});
function getList(){
	$.ajax({
        url: '../back/goods/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'type':$("select[name='type']").val(),
        	'name':$("input.layui-id").val().replace(/(^\s*)|(\s*$)/g, "")
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="5%" class="text-center">序号</th>'+
								'<th width="18%" class="text-center">商品简称</th>'+
								'<th width="10%" class="text-center">商品类型</th>'+
								'<th width="10%" class="text-center">商品成本价(元)</th>'+
								'<th width="10%" class="text-center">商品价值(元)</th>'+
								'<th width="15%" class="text-center">描述</th>'+
								'<th width="10%" class="text-center">商品列表图</th>'+
								'<th width="6%" class="text-center">商品编码</th>'+
								'<th class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var status = "";//状态
					var source = "";//来源
					var opteration = "";//操作按钮
					var img = "";//商品列表图
					var categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="sourceBtn mr-5" data-source="'+r.data[i].sourceUrl
					+'">查看来源</a><a class="deleteBtn">删除</a>';//分类
					for(var m=0;m<r.data[i].imageList.length;m++){
						if(r.data[i].imageList[m].type=="type_list"){
							if(img == ''){
								img = '<img src="..'+r.data[i].imageList[m].imageUrl+'" />';
							}
						}
					}
					tableHtml += '<tr data-url="goodsAdd.html?uuid='+r.data[i].uuid+
					'"><td class="text-center">'+(pageSize*(pageNum-1)+i+1)+
					'</td><td class="text-center">'+r.data[i].shortname+
					'</td><td class="text-center">'+typeObj[r.data[i].type]+
					'</td><td class="text-center">'+(r.data[i].costs).toFixed(2)+
					'</td><td class="text-center">'+(r.data[i].price).toFixed(2)+
					'</td><td class=""><div style="position:relative;"><b class="onlyOneLine" style="padding-right:25px;display:block;">'
					+r.data[i].description+'</b><p class="layerMoreBtn"></p></div>'+
					'</td><td class="text-center">'+img+
					'</td><td class="text-center">'+r.data[i].code+'</td><td class="text-center" data-id="'+r.data[i].uuid+
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

				$(".deleteBtn").click(function(){
					deleteList($(this).parent().attr("data-id"));
					return false;
				});
				$(".sourceBtn").click(function(){
					if($(this).attr("data-source")!="null"){
						layer.open({
						  type: 2, 
						  title:false,
						  area: ['80%', '80%'],
						  content: $(this).attr("data-source") //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
						}); 
					}else{
						layer.msg("未设置相关信息")
					}
					
					return false;
				});				
				$(".editBtn").click(function(){
					window.location.href = 'goodsAdd.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum;
					return false;
				});
//				$(".table-list tr").click(function(){
//					var dataUrl = $(this).attr("data-url");
//					if(dataUrl){
//						window.location.href = dataUrl;
//					}
//				});
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
//设为默认
function defaultList(uuid){
	$.ajax({
        url: '../back/robot/isAll',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
        	'isAll':true
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("设置成功", {
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
        url: '../back/goods/changeStatus',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
        	'status':'delete'
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
layui.use(['form'], function(){
	  var form = layui.form
	  
});