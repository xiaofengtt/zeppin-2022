/**
 * 商品管理
 */
var pageNum = 1,
    pageSize = 50;
var classname = (url('?classname') != null) ? url('?classname') : '';
var tab = url('?tab');
var flagSubmit = true;
var typeObj = {
	'cashcoupon':'现金卡券',
	'goldjewelry':'黄金珠宝',
	'brandmobile':'手机数码',
	'equipment':'家用电器',
	'makeups':'美妆大牌',
	'necessities':'生活用品',
	'foodstuff':'食品饮料'
}
$(function(){
	getList();	
});
function getList(){
	$.ajax({
        url: '../back/goods/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="5%" class="text-center">序号</th>'+
								'<th width="25%" class="text-center">商品简称</th>'+
								'<th width="10%" class="text-center">商品类型</th>'+
								'<th width="15%" class="text-center">商品成本价(元)</th>'+
								'<th width="15%" class="text-center">商品价值(金币)</th>'+
								'<th width="20%" class="text-center">创建时间</th>'+
								'<th class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var status = "";//状态
					var source = "";//来源
					var opteration = "";//操作按钮
					var categoryHtml = '<a class="chooseBtn mr-5">选择</a>';//分类
					tableHtml += '<tr data-url="goodsAdd.html?uuid='+r.data[i].uuid+
					'"><td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td><td class="text-center">'+r.data[i].shortname+
					'</td><td class="text-center">'+typeObj[r.data[i].type]+'</td><td class="text-center">'+(r.data[i].costs).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].price).toFixed(2)+'</td><td class="text-center">'+formatDate(r.data[i].createtime)+'</td><td class="text-center" data-id="'+encodeURI(JSON.stringify(r.data[i]))+
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

				$(".chooseBtn").click(function(){
					var data = JSON.parse(decodeURI($(this).parent().attr("data-id")))
					window.localStorage.setItem("goodsId",data.uuid);
					window.localStorage.setItem("goodsTitle",data.shortname);
					var img = "";//商品列表图
					console.log(data.imageList)
					for(var m=0;m<data.imageList.length;m++){
						if(data.imageList[m].type=="type_list"){
							if(img == ''){
								img = data.imageList[m].imageUrl;
							}
						}
					}
					window.localStorage.setItem("goodsImg",img);
					window.localStorage.setItem("costs",data.costs);
					window.localStorage.setItem("price",data.price);
					window.localStorage.setItem("code",data.code);
					var index = parent.layer.getFrameIndex(window.name); 
					if(classname!=''){
						window.localStorage.setItem("goodsName",data.name);
						window.parent.goodsItem(classname,tab);
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
