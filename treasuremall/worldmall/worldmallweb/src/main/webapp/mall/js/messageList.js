var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
    pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var statusArr = {
	'normal':'待发送',
	'finished':'发送成功',
	'fail':'发送失败'
}
$(function(){
	topicList();
	getList();
	$(".addMessage").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '350px'],
		  content:['messageDetail.html?pagesize='+pageSize+'&pagenum='+pageNum,'no']
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
	        url: '../back/topicMessage/list',
	        type: 'get',
	        async:false,
	        data: {
				'type':$("select[name='type']").val(),
				'topic':$("select[name='topic']").val(),
				'sendtime':$("input[name='sendtime']").val(),
	        	'pageSize':pageSize,
	        	'pageNum':pageNum
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			var tableHtml = '<tr>'+
									'<th width="5%" class="text-center">序号</th>'+
									'<th width="15%" class="text-center">标题</th>'+
									'<th width="25%" class="text-center">内容</th>'+
									'<th width="10%" class="text-center">关联主题</th>'+
									'<th width="10%" class="text-center">发送时间</th>'+
									'<th width="10%" class="text-center">状态</th>'+
									'<th width="10%" class="text-center">操作</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						var status = "";//状态
						var categoryHtml = ""
						if(r.data[i].status != 'fail'){
							categoryHtml = '<a class="editBtn">查看</a>';
						}else{
							categoryHtml = '<a class="editBtn mr-5">查看</a><a class="deleteBtn">重发</a>';
						}
						tableHtml += '<tr><td class="text-center">'+(pageSize*(pageNum-1)+i+1)+
						'</td><td class="text-center"><div style="position:relative;"><b class="onlyOneLine" style="padding-right:25px;display:block;">'
						+r.data[i].title+'</b><p class="layerMoreBtn"></p></div>'+
						'</td><td class="text-center"><div style="position:relative;"><b class="onlyOneLine" style="padding-right:25px;display:block;">'
						+r.data[i].content+'</b><p class="layerMoreBtn"></p></div>'+
						'</td><td class="text-center">'+r.data[i].displayName+
						'</td><td class="text-center">'+formatDate(r.data[i].sendtime)+
						'</td><td class="text-center">'+statusArr[r.data[i].status]+
						'</td><td class="text-center" data-id="'+r.data[i].uuid+
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
						tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
					}
					$("table").html(tableHtml);			
					$(".editBtn").click(function(){
						layer.open({
						  type: 2, 
						  title:false,
						  area: ['500px', '350px'],
						  content:['messageDetail.html?pagesize='+pageSize+'&pagenum='+pageNum+'&uuid='+$(this).parent().attr("data-id")+'&status=success','no']
						});
					});
					$(".deleteBtn").click(function(){
						layer.open({
						  type: 2, 
						  title:false,
						  area: ['500px', '350px'],
						  content:['messageDetail.html?pagesize='+pageSize+'&pagenum='+pageNum+'&uuid='+$(this).parent().attr("data-id")+'&status=fail','no']
						});
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
layui.use(['form', 'laydate'], function(){
	var form = layui.form
	,laydate = layui.laydate
    laydate.render({
	 elem: '.worktimeEnd,.worktimeBegin'
	 ,type: 'datetime'
	 ,range: '_'
		,theme: '#3D99FF'
    });
});
