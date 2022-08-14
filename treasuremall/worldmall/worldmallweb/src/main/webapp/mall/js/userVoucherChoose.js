/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var flagSubmit = true;
$(function(){
	getList();	
	$(".tableBox").css("height",window.innerHeight-150)
});
function getList(){
	$.ajax({
        url: '../back/voucher/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'title':$(".layui-name").val().replace(/(^\s*)|(\s*$)/g, "")
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
				    			'<th width="8%" class="text-center">序号</th>'+
								'<th width="12%" class="text-center">金券名称</th>'+
								'<th width="8%" class="text-center">金券面值</th>'+
								'<th width="10%" class="text-center">使用最低限额</th>'+
								'<th width="15%" class="text-center">限制描述</th>'+
								'<th width="10%" class="text-center">开始时间</th>'+
								'<th width="10%" class="text-center">结束时间</th>'+
								'<th width="60px" class="text-center"><input type="checkbox" id="allCheck" name="" value=""></th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var starttime='', endtime='';
					if(r.data[i].starttime != null){
						starttime = r.data[i].starttime.length > 10 ? formatDate(r.data[i].starttime) : r.data[i].starttime;
					}
					if(r.data[i].endtime != null){
						endtime = r.data[i].endtime.length > 10 ? formatDate(r.data[i].endtime) : r.data[i].endtime;
					}
					tableHtml += '<tr data-url="goodsAdd.html?uuid='+r.data[i].uuid+
					'"><td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center">'+r.data[i].title+'</td>'
					+'<td class="text-center">'+(r.data[i].dAmount).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].payMin).toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data[i].discription+'</td>'
					+'<td class="text-center">'+starttime+'</td>'
					+'<td class="text-center">'+endtime+'</td>'
					+'<td class="text-center"><input class="check" type="checkbox" name="'+r.data[i].title+'" value="'+r.data[i].uuid+
    				'"></td></tr>'; 
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
				allCheck();
				$(".chooseSure").click(function(){
					var uuid = "",name="";
					$(".check.light").each(function(){
						uuid += $(this).val() + ",";
						name += $(this).attr("name") + ",";
					});
					if(uuid!=""){
						uuid = uuid.substring(0,uuid.length-1);
						name = name.substring(0,name.length-1);
					}
					window.localStorage.setItem("userVoucher",uuid);
					window.localStorage.setItem("userVoucherName",name);
					var index = parent.layer.getFrameIndex(window.name);
    				parent.layer.close(index);
    				parent.getValue();
    				return false
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
