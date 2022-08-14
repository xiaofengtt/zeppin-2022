/**
 * 
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='userVoucherAdd.html?pagesize='+pageSize+'&pagenum='+pageNum
	});
	getList();
});
function getList(){
	$.ajax({
        url: '../back/userVoucher/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'showid':$(".layui-name").val().replace(/(^\s*)|(\s*$)/g, "")
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="8%" class="text-center">用户ID</th>'+
				'<th width="10%" class="text-center">用户头像/昵称</th>'+
				'<th width="10%" class="text-center">金券名称</th>'+
				'<th width="8%" class="text-center">金券面值</th>'+
				'<th width="10%" class="text-center">使用最低限额</th>'+
				'<th width="10%" class="text-center">操作时间</th>'+
				'<th width="15%" class="text-center">备注</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var starttime='', endtime='';
				if(r.data[i].starttime != null){
					starttime = r.data[i].starttime.length > 10 ? formatDate(r.data[i].starttime) : r.data[i].starttime;
				}
				if(r.data[i].endtime != null){
					endtime = r.data[i].endtime.length > 10 ? formatDate(r.data[i].endtime) : r.data[i].endtime;
				}
				var imgUrl = r.data[i].frontUserImageURL?(".."+r.data[i].frontUserImageURL):'../image/img-defaultAvatar.png';
				tableHtml += '<tr data-url="userDetail.html?uuid='+r.data[i].frontUser+
				'"><td class="text-center">'+r.data[i].frontUserShowId+'</td>'
					+'<td class="text-center showDetail"><div style="width:40%;float:left;"><img src="'+
					imgUrl+'" class="touxiang" /></div><div style="width:60%;float:left; text-align:left;padding-top:10px;"><p>'
					+r.data[i].frontUserNickname+'</p></div></td>'
					+'<td class="text-center">'+r.data[i].title+'</td>'
					+'<td class="text-center">'+(r.data[i].dAmount).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].payMin).toFixed(2)+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'">系统补发'+r.data[i].title+'</td>'
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
				tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$("td.showDetail").click(function(){
				layer.open({
					type: 2, 
					title:false, 
					area: ['90%', '90%'],
					content: [$(this).parent().attr("data-url")+'?pagesize='+pageSize+'&pagenum='+pageNum] 
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