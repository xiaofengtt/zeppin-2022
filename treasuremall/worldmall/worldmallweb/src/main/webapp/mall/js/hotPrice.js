/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var flagSubmit = true;
var sortType = '';//列表sort
var selectDate = null;
$(function(){	
});
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
	    ,value:formatDateBefore(new Date(),'7')+' '+formatDates(new Date())+' - '+formatDate(new Date())
	    ,type: 'datetime'
	    ,range: '-'
		,theme: '#3D99FF'
	    ,max:formatDate(new Date())
    	,done: function(value, date){
     		selectDate = value;

//     		getList();
        }
	  });
	getList();
});
//list
function getList(sort){
	$.ajax({
        url: '../back/userPayment/getStatistics',
        type: 'get',
        async:false,
        data: {
        	'starttime':selectDate?selectDate.substring(0,19):formatDateBefore(new Date(),'7')+' '+formatDates(new Date()),
    		'endtime':selectDate?selectDate.substring(22):formatDate(new Date()),
        	'sort':sortType
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="5%" class="text-center">序号</th>'+
								'<th width="45%" class="text-center">奖品</th>'+
								'<th width="10%" class="text-center">奖品金额</th>'+
								'<th width="10%" class="text-center">投注量(人次)'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="paymentCount asc"?"asc":(sortType=="paymentCount desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="paymentCount asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="paymentCount desc"></i></span></th>'+
								'<th width="10%" class="text-center">覆盖期数</th>'+
								'<th width="10%" class="text-center">参与人数</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<tr><td class="text-center">'+
					(pageSize*(pageNum-1)+i+1)+'</td><td style="display:flex;align-items:center;"><div style="width:60px;text-align:center;"><img src="..'
					+r.data[i].imageUrl+'"/></div><div style="padding-left:5px;"><p>'
					+r.data[i].title+'</p></div>'+
					'</td><td class="text-center">'
					+r.data[i].dPrice+'</td><td class="text-center">'
					+r.data[i].paymentCount+'</td><td class="text-center">'
					+r.data[i].currentIssueNum+'</td><td class="text-center">'
					+r.data[i].userCount+'</td></tr>'; 
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
					tableHtml += '<tr><td colspan="6" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);
				$("th i").click(function(){
					$(this).parent().attr("lay-sort",$(this).attr("title"));
					sortType = $(this).attr("attr-sort");
					getList();
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



