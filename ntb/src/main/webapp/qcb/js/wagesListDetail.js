var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pageNum = 1;
var pageSize = 10;
var order = "price desc";
var sendState;
var flag = true;
$(document).ready(function() {
    var pageNum = 1;
    $(".main-left-item:eq(2)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-3.png");
    $(".main-left-item:eq(2) ul li:eq(1) a").addClass("color-orange");

    getdetail();
});

//排序
$(".sort").unbind("click").click(function() {
    $(this).addClass("color-orange");
    $(this).siblings(".sort").removeClass("color-orange");
    $(this).removeClass("asc").removeClass("desc");
    order = order == 'price desc' ? 'price asc' : 'price desc';
    $(this).addClass(order).siblings(".sort").removeClass("asc").removeClass("desc");

    getList(uuid);
});

//获取详细信息
function getdetail() {
    $.ajax({
        url: '../rest/qcb/companyPayroll/get',
        type: 'get',
        data: {
            "uuid": uuid,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            $(".box-title small").html("-" + r.data.title);
            if (r.data.status == "success") {
                $(".item-num").html('发送<span class="color-green">&nbsp;' + r.data.payrollCount +
                    '&nbsp;</span><i>/</i><span>&nbsp;' + r.data.payrollCount +
                    '&nbsp;</span>&nbsp;确认<span class="color-orange">&nbsp;' + r.data.comfirmCount +
                    '&nbsp;</span><i>/</i><span>&nbsp;' + r.data.payrollCount +
                    '&nbsp;</span>&nbsp;反馈<span class="color-orange">&nbsp;' + r.data.feedbackCount + '&nbsp;</span>');
                sendState = "发送成功";
            } else {
                $(".item-num").html('发送<span class="color-green">&nbsp;0&nbsp;</span><i>/</i><span>&nbsp;' + r.data.payrollCount +
                    '&nbsp;</span>&nbsp;确认<span class="color-orange">&nbsp;' + r.data.comfirmCount +
                    '&nbsp;</span><i>/</i><span>&nbsp;' + r.data.payrollCount +
                    '&nbsp;</span>&nbsp;反馈<span class="color-orange">&nbsp;' + r.data.feedbackCount + '&nbsp;</span>');
                sendState = "待发送";
            }

            getList(r.data.uuid);
        } else {
            layer.msg(r.message);
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });
}
//获取列表详细信息
function getList(uuid) {
    $.ajax({
        url: '../rest/qcb/companyPayrollRecords/list',
        type: 'get',
        data: {
            "qcbCompanyPayroll": uuid,
            "pageNum": pageNum,
            "pageSize": pageSize,
            "sorts": order,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            var html = '';
            if (r.data.length > 0) {
                for (var i = 0; i < r.data.length; i++) {
                    var statusClass;
                    var feedbacklist;
                    if (r.data[i].statusCN == "	已确认") {
                        statusClass = 'color-green';
                    } else {
                        statusClass = '';
                    }
                    if(r.data[i].feedbackCount != 0){
                    	feedbacklist = '<a onclick="feedbacklist(\''+r.data[i].uuid+'\')">查看</a>';
                    }else{
                    	feedbacklist = '未反馈';
                    }
                    html += '<tr><td class="td-padding-item">' + r.data[i].qcbEmployeeName +
                        '</td><td>' + r.data[i].qcbEmployeeIdcard + '</td><td>' + r.data[i].qcbEmployeeMobile +
                        '</td><td>' + r.data[i].price.toFixed(2).split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('') +
                        '</td><td class="' + statusClass + '">' + r.data[i].statusCN +
                        '</td><td>' + sendState + '</td><td>' + feedbacklist + '</td></tr>';
                }

            } else {
                html += '<tr><td colspan="7" style="text-align:center">暂无发放记录</td></tr>';
            }
            $("#tbody").html(html);

            if (r.totalPageCount!=0) {
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
	                    getList(uuid);
	                    flag = false;
	                    document.body.scrollTop = document.documentElement.scrollTop = 0;
	                }
	            });
	            $("#pageTool").find(".ui-paging-container:last").siblings().remove();
            }
            $(".ui-select-pagesize").unbind("change").change(function() {
                pageSize = $(this).val();
                pageNum = 1;
                getList(uuid);
            });
            $(".loadingOver").show();
        	$(".loadingDiv").hide();
        } else {
        	$(".loadingDiv").hide();
            layer.msg(r.message);
        }
    }).fail(function(r) {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}
//反馈信息详情
function feedbacklist(uuid){
	$.ajax({
	    url: '../rest/qcb/companyPayrollRecords/feedbackList',
	    type: 'get',
        traditional: "true",
	    data: {
	    	"qcbCompanyPayrollRecords":uuid,
            "timestamp":new Date().getTime() 
	    }
	}).done(function(r) {		
		if (r.status == "SUCCESS") {
			var html = '';
			for(var i=0;i<r.data.length;i++){
				html += '<tr><td class="td-padding-item">'+r.data[i].content+'</td><td style="padding-left:15px;">'+getTimetimeStamp(r.data[i].createtime)+'</td></tr>'
			}
			html = '<table class="table" cellspacing="0" cellpadding="0" style="margin-top:0;max-height:80%;overflow:auto;">'+''
			+'<tr class="first-tr"><th class="td-padding-item" width="80%">反馈内容</th><th width="20%" style="padding-left:15px;">时间</th></tr>'
				+html+'</table>';
			layer.open({
			  type: 1,
			  shade: false,
			  title: false, 
			  area:['60%',''],
			  content: '<div style="padding:10px;">'+html+'</div>', 
			  cancel: function(){
			    
			  }
			});			
			
        }else{
        	layer.msg(r.message);
        }
	}).fail(function(r){
		layer.msg("error");
	});
}