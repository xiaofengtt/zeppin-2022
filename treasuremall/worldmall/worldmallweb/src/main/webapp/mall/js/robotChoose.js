/**
 * 商品管理
 */
var pageNum = 1,
    pageSize = 50;
var classname = (url('?classname') != null) ? url('?classname') : '';
var tab = url('?tab');
var type = url('?type');
var flagSubmit = true;
$(function(){
	getList();	
});
function getList(){
	$.ajax({
        url: '../back/robot/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'showid':$("input.layui-id").val().replace(/(^\s*)|(\s*$)/g, "")
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
    			'<th width="15%" class="text-center">序号</th>'+
    			'<th width="25%" class="text-center">ID</th>'+
				'<th width="40%" class="text-left">头像/昵称</th>'+
				'<th width="20%" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var imgUrl = r.data[i].imageURL?(".."+r.data[i].imageURL):'../image/img-defaultAvatar.png';
				var categoryHtml = '<a class="chooseBtn mr-5">选择</a>';//分类
				tableHtml += '<tr>'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center">'+r.data[i].showId+'</td>'
					+'<td class="showDetail"><img src="'+imgUrl+'" class="touxiang" />'+r.data[i].nickname+'</td>'
					+'<td class="text-center" data-id="'+encodeURI(JSON.stringify(r.data[i]))+
					'">'+categoryHtml+'</td>'
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
				tableHtml += '<tr><td colspan="4" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);

				$(".chooseBtn").click(function(){
					var data = JSON.parse(decodeURI($(this).parent().attr("data-id")))
					window.localStorage.setItem("goodsUuid",data.uuid);
					window.localStorage.setItem("goodsId",data.showId);
					var index = parent.layer.getFrameIndex(window.name); 
					if(classname!=''){
						window.localStorage.setItem("goodsName",data.nickname);
						window.parent.addRobot(classname,tab,type);
					}else{
	    				parent.location.reload();
					}
    				parent.layer.close(index);
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
