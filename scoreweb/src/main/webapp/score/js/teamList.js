var pageNum = 1,
    pageSize = 10;
var flagSubmit = true;
var categroyArr = [];
var categoryId = (url('?category') != null) ? url('?category') : '';//查category接口用的参数值
var category = '';//记录列表参数值
$(function(){
	getCategroy();
	getList();
	getChooseCategroy();
});


//获取列表
function getList(){
	var str = 'pageSize='+pageSize+'&pageNum='+pageNum;
	if(category!=""){
		str+='&category='+category;
	}
	$.get('../back/team/list?'+str,function(r){
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
							'<th width="15%">图标</th>'+
							'<th width="15%">名称</th>'+
							'<th width="15%">英文名称</th>'+
							'<th width="20%">所属赛事</th>'+
							'<th width="10%">状态</th>'+
							'<th width="15%">操作</th>'+
						'</tr>';
			for(var i=0;i<r.data.length;i++){
				var status = "";//状态
				var categoryHtml = '';
				if(r.data[i].status=="normal"){
					status = "正常";
				}else if(r.data[i].status=="disable"){
					status = "停用";
				}
				for(var m=0;m<r.data[i].categoryList.length;m++){
					categoryHtml += (categroyArr[r.data[i].categoryList[m]]?categroyArr[r.data[i].categoryList[m]]:"其他赛事")+",";
				}
				categoryHtml = categoryHtml.substring(0,categoryHtml.length-1)
				tableHtml += '<tr>'
					+'<td class="table-Logo"><img src="'+(r.data[i].iconUrl?".."+r.data[i].iconUrl:"img/default-logo.png")+'" /></td>'
    				+'<td><b>'+r.data[i].name+'</b></td>'
    				+'<td>'+r.data[i].shortname+'</td>'
    				+'<td>'+categoryHtml+'</td>';
				tableHtml = tableHtml+'<td>'+status+'</td>'
    				+'<td data-id="'+r.data[i].uuid+'"><a class="mr-5" href="teamDetail.html?uuid='+r.data[i].uuid+'">详情</a>'
    				+'</tr>';
			}
            $('#pageTool').Paging({
                prevTpl: "<",
                nextTpl: ">",
                pagesize: pageSize,
                count: r.totalResultCount,
                current: pageNum,
                toolbar: true,
                pageSizeList: [10, 20, 50],
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
			$(".deleteBtn").click(function(){
				deleteList($(this).parent().attr("data-id"));
				return false;
			});
			$(".disableBtn").click(function(){
				disableList($(this).parent().attr("data-id"));
				return false;
			});
			$(".table-list tr").click(function(){
				var dataUrl = $(this).attr("data-url");
				if(dataUrl){
					window.location.href = dataUrl;
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
        	
        });
    });   	
}

//获取category列表
//处理子级循环
function treeCategorys(r){
	if(r.length>0){
		for(var i=0;i<r.length;i++){
			categroyArr[r[i].uuid] = r[i].name ;
			if(r[i].children && r[i].children.length>0){
				treeCategorys(r[i].children);
			}
		}
		
	}	
}
//获取类别
function getCategroy(){
	$.ajax({
      url: '../back/category/list',
      type: 'get',
      async:false,
      data: {
      	"istag":true
      }
  }).done(function(r) {
  		if (r.status == "SUCCESS") {
  			for(var i = 0;i<r.data.length;i++){
  				treeCategorys(r.data[i].children);
  				categroyArr[r.data[i].uuid] = r.data[i].name ;
  			}
  			
  		} else {
  			
  		}
  }).fail(function(r) {
      
  });   	
}
//选择类别
function getChooseCategroy(){
	var str="istag=true&level=3";
	if(categoryId!=""){
		str+="&category="+categoryId;
	}
	$.get('../back/category/list?'+str,function(r){
		if (r.status == "SUCCESS") {
			var option="<option value=''>全部</option>";
  			for(var i = 0;i<r.data.length;i++){
  				option+='<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
  			}  
  			$("#categorySelect").html(option).change(function(){
  				pageNum = 1;
  				category = $(this).val();
  				getList();
  			});
  			
  		} else {
  			
  		}
	});	
}
